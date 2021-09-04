/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.CpuGraph;
/*      */ import com.adventnet.appmanager.bean.HostResourceBean;
/*      */ import com.adventnet.appmanager.bean.HostResourceGraph;
/*      */ import com.adventnet.appmanager.bean.MemGraph;
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.bean.SysloadGraph;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import java.util.TreeMap;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class HostOverViewDetails_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   65 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   68 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   69 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   70 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   77 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   82 */     ArrayList list = null;
/*   83 */     StringBuffer sbf = new StringBuffer();
/*   84 */     ManagedApplication mo = new ManagedApplication();
/*   85 */     if (distinct)
/*      */     {
/*   87 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   91 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   94 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   96 */       ArrayList row = (ArrayList)list.get(i);
/*   97 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   98 */       if (distinct) {
/*   99 */         sbf.append(row.get(0));
/*      */       } else
/*  101 */         sbf.append(row.get(1));
/*  102 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  105 */     return sbf.toString(); }
/*      */   
/*  107 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  110 */     if (severity == null)
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  114 */     if (severity.equals("5"))
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  118 */     if (severity.equals("1"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  125 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  132 */     if (severity == null)
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("1"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  140 */     if (severity.equals("4"))
/*      */     {
/*  142 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  144 */     if (severity.equals("5"))
/*      */     {
/*  146 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  151 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  157 */     if (severity == null)
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  161 */     if (severity.equals("5"))
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  165 */     if (severity.equals("1"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  171 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  177 */     if (severity == null)
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  181 */     if (severity.equals("1"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  185 */     if (severity.equals("4"))
/*      */     {
/*  187 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  189 */     if (severity.equals("5"))
/*      */     {
/*  191 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  195 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  201 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  207 */     if (severity == 5)
/*      */     {
/*  209 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  211 */     if (severity == 1)
/*      */     {
/*  213 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  218 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  224 */     if (severity == null)
/*      */     {
/*  226 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  228 */     if (severity.equals("5"))
/*      */     {
/*  230 */       if (isAvailability) {
/*  231 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  234 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  237 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  239 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  241 */     if (severity.equals("1"))
/*      */     {
/*  243 */       if (isAvailability) {
/*  244 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  247 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  254 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  261 */     if (severity == null)
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("5"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  269 */     if (severity.equals("4"))
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("1"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  280 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  286 */     if (severity == null)
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("5"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  294 */     if (severity.equals("4"))
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  298 */     if (severity.equals("1"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  305 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  312 */     if (severity == null)
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  316 */     if (severity.equals("5"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  320 */     if (severity.equals("4"))
/*      */     {
/*  322 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  324 */     if (severity.equals("1"))
/*      */     {
/*  326 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  331 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  339 */     StringBuffer out = new StringBuffer();
/*  340 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  341 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  342 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  343 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  344 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  345 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  346 */     out.append("</tr>");
/*  347 */     out.append("</form></table>");
/*  348 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  355 */     if (val == null)
/*      */     {
/*  357 */       return "-";
/*      */     }
/*      */     
/*  360 */     String ret = FormatUtil.formatNumber(val);
/*  361 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  362 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  365 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  369 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  377 */     StringBuffer out = new StringBuffer();
/*  378 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  379 */     out.append("<tr>");
/*  380 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  382 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  384 */     out.append("</tr>");
/*  385 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  389 */       if (j % 2 == 0)
/*      */       {
/*  391 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  395 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  398 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  400 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  403 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  407 */       out.append("</tr>");
/*      */     }
/*  409 */     out.append("</table>");
/*  410 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  411 */     out.append("<tr>");
/*  412 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  413 */     out.append("</tr>");
/*  414 */     out.append("</table>");
/*  415 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  421 */     StringBuffer out = new StringBuffer();
/*  422 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  423 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  424 */     out.append("<tr>");
/*  425 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  426 */     out.append("<tr>");
/*  427 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  428 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  429 */     out.append("</tr>");
/*  430 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  433 */       out.append("<tr>");
/*  434 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  435 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  436 */       out.append("</tr>");
/*      */     }
/*      */     
/*  439 */     out.append("</table>");
/*  440 */     out.append("</table>");
/*  441 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  446 */     if (severity.equals("0"))
/*      */     {
/*  448 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  452 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  459 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  472 */     StringBuffer out = new StringBuffer();
/*  473 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  474 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  476 */       out.append("<tr>");
/*  477 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  478 */       out.append("</tr>");
/*      */       
/*      */ 
/*  481 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  483 */         String borderclass = "";
/*      */         
/*      */ 
/*  486 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  488 */         out.append("<tr>");
/*      */         
/*  490 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  491 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  492 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  498 */     out.append("</table><br>");
/*  499 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  500 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  502 */       List sLinks = secondLevelOfLinks[0];
/*  503 */       List sText = secondLevelOfLinks[1];
/*  504 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  507 */         out.append("<tr>");
/*  508 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  509 */         out.append("</tr>");
/*  510 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  512 */           String borderclass = "";
/*      */           
/*      */ 
/*  515 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  517 */           out.append("<tr>");
/*      */           
/*  519 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  520 */           if (sLinks.get(i).toString().length() == 0) {
/*  521 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  524 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  526 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  530 */     out.append("</table>");
/*  531 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  538 */     StringBuffer out = new StringBuffer();
/*  539 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  540 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  542 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  544 */         out.append("<tr>");
/*  545 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  546 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  550 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  552 */           String borderclass = "";
/*      */           
/*      */ 
/*  555 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  557 */           out.append("<tr>");
/*      */           
/*  559 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  560 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  561 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  564 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  567 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  572 */     out.append("</table><br>");
/*  573 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  574 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  576 */       List sLinks = secondLevelOfLinks[0];
/*  577 */       List sText = secondLevelOfLinks[1];
/*  578 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  581 */         out.append("<tr>");
/*  582 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  583 */         out.append("</tr>");
/*  584 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  586 */           String borderclass = "";
/*      */           
/*      */ 
/*  589 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  591 */           out.append("<tr>");
/*      */           
/*  593 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  594 */           if (sLinks.get(i).toString().length() == 0) {
/*  595 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  598 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  600 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  604 */     out.append("</table>");
/*  605 */     return out.toString();
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
/*  618 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  627 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  630 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  633 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  636 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  639 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  647 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  652 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  657 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  662 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  667 */     if (val != null)
/*      */     {
/*  669 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  673 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  678 */     if (val == null) {
/*  679 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  683 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  688 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  694 */     if (val != null)
/*      */     {
/*  696 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  700 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  706 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  711 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  715 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  720 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  725 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  730 */     String hostaddress = "";
/*  731 */     String ip = request.getHeader("x-forwarded-for");
/*  732 */     if (ip == null)
/*  733 */       ip = request.getRemoteAddr();
/*  734 */     InetAddress add = null;
/*  735 */     if (ip.equals("127.0.0.1")) {
/*  736 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  740 */       add = InetAddress.getByName(ip);
/*      */     }
/*  742 */     hostaddress = add.getHostName();
/*  743 */     if (hostaddress.indexOf('.') != -1) {
/*  744 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  745 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  749 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  754 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  760 */     if (severity == null)
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  764 */     if (severity.equals("5"))
/*      */     {
/*  766 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  768 */     if (severity.equals("1"))
/*      */     {
/*  770 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  775 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  780 */     ResultSet set = null;
/*  781 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  782 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  784 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  785 */       if (set.next()) { String str1;
/*  786 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  787 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  790 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  795 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  798 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  800 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  804 */     StringBuffer rca = new StringBuffer();
/*  805 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  806 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  809 */     int rcalength = key.length();
/*  810 */     String split = "6. ";
/*  811 */     int splitPresent = key.indexOf(split);
/*  812 */     String div1 = "";String div2 = "";
/*  813 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  815 */       if (rcalength > 180) {
/*  816 */         rca.append("<span class=\"rca-critical-text\">");
/*  817 */         getRCATrimmedText(key, rca);
/*  818 */         rca.append("</span>");
/*      */       } else {
/*  820 */         rca.append("<span class=\"rca-critical-text\">");
/*  821 */         rca.append(key);
/*  822 */         rca.append("</span>");
/*      */       }
/*  824 */       return rca.toString();
/*      */     }
/*  826 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  827 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  828 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  829 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  830 */     getRCATrimmedText(div1, rca);
/*  831 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  834 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  835 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  836 */     getRCATrimmedText(div2, rca);
/*  837 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  839 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  844 */     String[] st = msg.split("<br>");
/*  845 */     for (int i = 0; i < st.length; i++) {
/*  846 */       String s = st[i];
/*  847 */       if (s.length() > 180) {
/*  848 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  850 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  854 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  855 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  857 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  861 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  862 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  863 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  866 */       if (key == null) {
/*  867 */         return ret;
/*      */       }
/*      */       
/*  870 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  871 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  874 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  875 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  876 */       set = AMConnectionPool.executeQueryStmt(query);
/*  877 */       if (set.next())
/*      */       {
/*  879 */         String helpLink = set.getString("LINK");
/*  880 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  883 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  889 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  908 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  899 */         if (set != null) {
/*  900 */           AMConnectionPool.closeStatement(set);
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
/*  914 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  915 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  917 */       String entityStr = (String)keys.nextElement();
/*  918 */       String mmessage = temp.getProperty(entityStr);
/*  919 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  920 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  922 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  928 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  929 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  931 */       String entityStr = (String)keys.nextElement();
/*  932 */       String mmessage = temp.getProperty(entityStr);
/*  933 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  934 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  936 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  941 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  951 */     String des = new String();
/*  952 */     while (str.indexOf(find) != -1) {
/*  953 */       des = des + str.substring(0, str.indexOf(find));
/*  954 */       des = des + replace;
/*  955 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  957 */     des = des + str;
/*  958 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  965 */       if (alert == null)
/*      */       {
/*  967 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  969 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  971 */         return "&nbsp;";
/*      */       }
/*      */       
/*  974 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  976 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  979 */       int rcalength = test.length();
/*  980 */       if (rcalength < 300)
/*      */       {
/*  982 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  986 */       StringBuffer out = new StringBuffer();
/*  987 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  988 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  989 */       out.append("</div>");
/*  990 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  991 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  992 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  997 */       ex.printStackTrace();
/*      */     }
/*  999 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1005 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1010 */     ArrayList attribIDs = new ArrayList();
/* 1011 */     ArrayList resIDs = new ArrayList();
/* 1012 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1014 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1016 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1018 */       String resourceid = "";
/* 1019 */       String resourceType = "";
/* 1020 */       if (type == 2) {
/* 1021 */         resourceid = (String)row.get(0);
/* 1022 */         resourceType = (String)row.get(3);
/*      */       }
/* 1024 */       else if (type == 3) {
/* 1025 */         resourceid = (String)row.get(0);
/* 1026 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1029 */         resourceid = (String)row.get(6);
/* 1030 */         resourceType = (String)row.get(7);
/*      */       }
/* 1032 */       resIDs.add(resourceid);
/* 1033 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1034 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1036 */       String healthentity = null;
/* 1037 */       String availentity = null;
/* 1038 */       if (healthid != null) {
/* 1039 */         healthentity = resourceid + "_" + healthid;
/* 1040 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1043 */       if (availid != null) {
/* 1044 */         availentity = resourceid + "_" + availid;
/* 1045 */         entitylist.add(availentity);
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
/* 1059 */     Properties alert = getStatus(entitylist);
/* 1060 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1065 */     int size = monitorList.size();
/*      */     
/* 1067 */     String[] severity = new String[size];
/*      */     
/* 1069 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1071 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1072 */       String resourceName1 = (String)row1.get(7);
/* 1073 */       String resourceid1 = (String)row1.get(6);
/* 1074 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1075 */       if (severity[j] == null)
/*      */       {
/* 1077 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1081 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1083 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1085 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1088 */         if (sev > 0) {
/* 1089 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1090 */           monitorList.set(k, monitorList.get(j));
/* 1091 */           monitorList.set(j, t);
/* 1092 */           String temp = severity[k];
/* 1093 */           severity[k] = severity[j];
/* 1094 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1100 */     int z = 0;
/* 1101 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1104 */       int i = 0;
/* 1105 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1108 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1112 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1116 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1118 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1121 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1125 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1128 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1129 */       String resourceName1 = (String)row1.get(7);
/* 1130 */       String resourceid1 = (String)row1.get(6);
/* 1131 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1132 */       if (hseverity[j] == null)
/*      */       {
/* 1134 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1139 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1141 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1144 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1147 */         if (hsev > 0) {
/* 1148 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1149 */           monitorList.set(k, monitorList.get(j));
/* 1150 */           monitorList.set(j, t);
/* 1151 */           String temp1 = hseverity[k];
/* 1152 */           hseverity[k] = hseverity[j];
/* 1153 */           hseverity[j] = temp1;
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
/* 1165 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1166 */     boolean forInventory = false;
/* 1167 */     String trdisplay = "none";
/* 1168 */     String plusstyle = "inline";
/* 1169 */     String minusstyle = "none";
/* 1170 */     String haidTopLevel = "";
/* 1171 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1173 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1175 */         haidTopLevel = request.getParameter("haid");
/* 1176 */         forInventory = true;
/* 1177 */         trdisplay = "table-row;";
/* 1178 */         plusstyle = "none";
/* 1179 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1186 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1189 */     ArrayList listtoreturn = new ArrayList();
/* 1190 */     StringBuffer toreturn = new StringBuffer();
/* 1191 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1192 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1193 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1195 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1197 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1198 */       String childresid = (String)singlerow.get(0);
/* 1199 */       String childresname = (String)singlerow.get(1);
/* 1200 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1201 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1202 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1203 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1204 */       String unmanagestatus = (String)singlerow.get(5);
/* 1205 */       String actionstatus = (String)singlerow.get(6);
/* 1206 */       String linkclass = "monitorgp-links";
/* 1207 */       String titleforres = childresname;
/* 1208 */       String titilechildresname = childresname;
/* 1209 */       String childimg = "/images/trcont.png";
/* 1210 */       String flag = "enable";
/* 1211 */       String dcstarted = (String)singlerow.get(8);
/* 1212 */       String configMonitor = "";
/* 1213 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1214 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1216 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1218 */       if (singlerow.get(7) != null)
/*      */       {
/* 1220 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1222 */       String haiGroupType = "0";
/* 1223 */       if ("HAI".equals(childtype))
/*      */       {
/* 1225 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1227 */       childimg = "/images/trend.png";
/* 1228 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1229 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1230 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1232 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1234 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1236 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1237 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1240 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1242 */         linkclass = "disabledtext";
/* 1243 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1245 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1246 */       String availmouseover = "";
/* 1247 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1249 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1251 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1252 */       String healthmouseover = "";
/* 1253 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1255 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1258 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1259 */       int spacing = 0;
/* 1260 */       if (level >= 1)
/*      */       {
/* 1262 */         spacing = 40 * level;
/*      */       }
/* 1264 */       if (childtype.equals("HAI"))
/*      */       {
/* 1266 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1267 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1268 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1270 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1271 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1272 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1273 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1274 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1275 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1276 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1277 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1278 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1279 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1280 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1282 */         if (!forInventory)
/*      */         {
/* 1284 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1287 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1289 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1291 */           actions = editlink + actions;
/*      */         }
/* 1293 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1295 */           actions = actions + associatelink;
/*      */         }
/* 1297 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1298 */         String arrowimg = "";
/* 1299 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1301 */           actions = "";
/* 1302 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1303 */           checkbox = "";
/* 1304 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1306 */         if (isIt360)
/*      */         {
/* 1308 */           actionimg = "";
/* 1309 */           actions = "";
/* 1310 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1311 */           checkbox = "";
/*      */         }
/*      */         
/* 1314 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1316 */           actions = "";
/*      */         }
/* 1318 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1320 */           checkbox = "";
/*      */         }
/*      */         
/* 1323 */         String resourcelink = "";
/*      */         
/* 1325 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1327 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1331 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1334 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1335 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1336 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1337 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1338 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1339 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1340 */         if (!isIt360)
/*      */         {
/* 1342 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1349 */         toreturn.append("</tr>");
/* 1350 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1352 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1353 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1357 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1358 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1361 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1365 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1367 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1368 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1369 */             toreturn.append(assocMessage);
/* 1370 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1371 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1372 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1373 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1379 */         String resourcelink = null;
/* 1380 */         boolean hideEditLink = false;
/* 1381 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1383 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1384 */           hideEditLink = true;
/* 1385 */           if (isIt360)
/*      */           {
/* 1387 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1391 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1393 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1395 */           hideEditLink = true;
/* 1396 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1397 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1402 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1405 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1406 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1407 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1408 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1409 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1410 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1411 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1412 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1413 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1414 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1415 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1416 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1417 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1419 */         if (hideEditLink)
/*      */         {
/* 1421 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1423 */         if (!forInventory)
/*      */         {
/* 1425 */           removefromgroup = "";
/*      */         }
/* 1427 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1428 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1429 */           actions = actions + configcustomfields;
/*      */         }
/* 1431 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1433 */           actions = editlink + actions;
/*      */         }
/* 1435 */         String managedLink = "";
/* 1436 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1438 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1439 */           actions = "";
/* 1440 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1441 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1444 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1446 */           checkbox = "";
/*      */         }
/*      */         
/* 1449 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1451 */           actions = "";
/*      */         }
/* 1453 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1456 */         if (isIt360)
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1464 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1465 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1466 */         if (!isIt360)
/*      */         {
/* 1468 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1472 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1474 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1477 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1484 */       StringBuilder toreturn = new StringBuilder();
/* 1485 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1486 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1487 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1488 */       String title = "";
/* 1489 */       message = EnterpriseUtil.decodeString(message);
/* 1490 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1491 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1492 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1494 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1496 */       else if ("5".equals(severity))
/*      */       {
/* 1498 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1502 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1504 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1505 */       toreturn.append(v);
/*      */       
/* 1507 */       toreturn.append(link);
/* 1508 */       if (severity == null)
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("5"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1516 */       else if (severity.equals("4"))
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       else if (severity.equals("1"))
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1527 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1529 */       toreturn.append("</a>");
/* 1530 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1534 */       ex.printStackTrace();
/*      */     }
/* 1536 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1543 */       StringBuilder toreturn = new StringBuilder();
/* 1544 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1545 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1546 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1547 */       if (message == null)
/*      */       {
/* 1549 */         message = "";
/*      */       }
/*      */       
/* 1552 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1553 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1555 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1556 */       toreturn.append(v);
/*      */       
/* 1558 */       toreturn.append(link);
/*      */       
/* 1560 */       if (severity == null)
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1564 */       else if (severity.equals("5"))
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1568 */       else if (severity.equals("1"))
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1575 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1577 */       toreturn.append("</a>");
/* 1578 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1584 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1587 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1588 */     if (invokeActions != null) {
/* 1589 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1590 */       while (iterator.hasNext()) {
/* 1591 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1592 */         if (actionmap.containsKey(actionid)) {
/* 1593 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1598 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1602 */     String actionLink = "";
/* 1603 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1604 */     String query = "";
/* 1605 */     ResultSet rs = null;
/* 1606 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1607 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1608 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1609 */       actionLink = "method=" + methodName;
/*      */     }
/* 1611 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1612 */       actionLink = methodName;
/*      */     }
/* 1614 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1615 */     Iterator itr = methodarglist.iterator();
/* 1616 */     boolean isfirstparam = true;
/* 1617 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1618 */     while (itr.hasNext()) {
/* 1619 */       HashMap argmap = (HashMap)itr.next();
/* 1620 */       String argtype = (String)argmap.get("TYPE");
/* 1621 */       String argname = (String)argmap.get("IDENTITY");
/* 1622 */       String paramname = (String)argmap.get("PARAMETER");
/* 1623 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1624 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1625 */         isfirstparam = false;
/* 1626 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1628 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1632 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1636 */         actionLink = actionLink + "&";
/*      */       }
/* 1638 */       String paramValue = null;
/* 1639 */       String tempargname = argname;
/* 1640 */       if (commonValues.getProperty(tempargname) != null) {
/* 1641 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1644 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1645 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1646 */           if (dbType.equals("mysql")) {
/* 1647 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1650 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1652 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1654 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1655 */             if (rs.next()) {
/* 1656 */               paramValue = rs.getString("VALUE");
/* 1657 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1661 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1665 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1668 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1673 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1674 */           paramValue = rowId;
/*      */         }
/* 1676 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1677 */           paramValue = managedObjectName;
/*      */         }
/* 1679 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1680 */           paramValue = resID;
/*      */         }
/* 1682 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1683 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1686 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1688 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1689 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1690 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1692 */     return actionLink;
/*      */   }
/*      */   
/* 1695 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1696 */     String dependentAttribute = null;
/* 1697 */     String align = "left";
/*      */     
/* 1699 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1700 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1701 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1702 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1703 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1704 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1705 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1706 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1707 */       align = "center";
/*      */     }
/*      */     
/* 1710 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1711 */     String actualdata = "";
/*      */     
/* 1713 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1714 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1715 */         actualdata = availValue;
/*      */       }
/* 1717 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1718 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1722 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1723 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1726 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1732 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1733 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1734 */       toreturn.append("<table>");
/* 1735 */       toreturn.append("<tr>");
/* 1736 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1737 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1738 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1739 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1740 */         String toolTip = "";
/* 1741 */         String hideClass = "";
/* 1742 */         String textStyle = "";
/* 1743 */         boolean isreferenced = true;
/* 1744 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1745 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1746 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1747 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1749 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1750 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1751 */           while (valueList.hasMoreTokens()) {
/* 1752 */             String dependentVal = valueList.nextToken();
/* 1753 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1754 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1755 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1757 */               toolTip = "";
/* 1758 */               hideClass = "";
/* 1759 */               isreferenced = false;
/* 1760 */               textStyle = "disabledtext";
/* 1761 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1765 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1766 */           toolTip = "";
/* 1767 */           hideClass = "";
/* 1768 */           isreferenced = false;
/* 1769 */           textStyle = "disabledtext";
/* 1770 */           if (dependentImageMap != null) {
/* 1771 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1772 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1775 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1779 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1780 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1781 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1782 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1783 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1784 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1786 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1787 */           if (isreferenced) {
/* 1788 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1792 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1793 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1794 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1795 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1796 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1797 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1799 */           toreturn.append("</span>");
/* 1800 */           toreturn.append("</a>");
/* 1801 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1804 */       toreturn.append("</tr>");
/* 1805 */       toreturn.append("</table>");
/* 1806 */       toreturn.append("</td>");
/*      */     } else {
/* 1808 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1811 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1815 */     String colTime = null;
/* 1816 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1817 */     if ((rows != null) && (rows.size() > 0)) {
/* 1818 */       Iterator<String> itr = rows.iterator();
/* 1819 */       String maxColQuery = "";
/* 1820 */       for (;;) { if (itr.hasNext()) {
/* 1821 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1822 */           ResultSet maxCol = null;
/*      */           try {
/* 1824 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1825 */             while (maxCol.next()) {
/* 1826 */               if (colTime == null) {
/* 1827 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1830 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1839 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1841 */               if (maxCol != null)
/* 1842 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1844 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1839 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1841 */               if (maxCol != null)
/* 1842 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1844 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1849 */     return colTime;
/*      */   }
/*      */   
/* 1852 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1853 */     tablename = null;
/* 1854 */     ResultSet rsTable = null;
/* 1855 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1857 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1858 */       while (rsTable.next()) {
/* 1859 */         tablename = rsTable.getString("DATATABLE");
/* 1860 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1861 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1874 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1865 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1868 */         if (rsTable != null)
/* 1869 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1871 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1877 */     String argsList = "";
/* 1878 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1880 */       if (showArgsMap.get(row) != null) {
/* 1881 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1882 */         if (showArgslist != null) {
/* 1883 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1884 */             if (argsList.trim().equals("")) {
/* 1885 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1888 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1895 */       e.printStackTrace();
/* 1896 */       return "";
/*      */     }
/* 1898 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1903 */     String argsList = "";
/* 1904 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1907 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1909 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1910 */         if (hideArgsList != null)
/*      */         {
/* 1912 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1914 */             if (argsList.trim().equals(""))
/*      */             {
/* 1916 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1920 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1928 */       ex.printStackTrace();
/*      */     }
/* 1930 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1934 */     StringBuilder toreturn = new StringBuilder();
/* 1935 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1942 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1943 */       Iterator itr = tActionList.iterator();
/* 1944 */       while (itr.hasNext()) {
/* 1945 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1946 */         String confirmmsg = "";
/* 1947 */         String link = "";
/* 1948 */         String isJSP = "NO";
/* 1949 */         HashMap tactionMap = (HashMap)itr.next();
/* 1950 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1951 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1952 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1953 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1954 */           (actionmap.containsKey(actionId))) {
/* 1955 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1956 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1957 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1958 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1959 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1961 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1967 */           if (isTableAction) {
/* 1968 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1971 */             tableName = "Link";
/* 1972 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1973 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1974 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1975 */             toreturn.append("</a></td>");
/*      */           }
/* 1977 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1978 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1979 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1980 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1986 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1992 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1994 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1995 */       Properties prop = (Properties)node.getUserObject();
/* 1996 */       String mgID = prop.getProperty("label");
/* 1997 */       String mgName = prop.getProperty("value");
/* 1998 */       String isParent = prop.getProperty("isParent");
/* 1999 */       int mgIDint = Integer.parseInt(mgID);
/* 2000 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2002 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2004 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2005 */       if (node.getChildCount() > 0)
/*      */       {
/* 2007 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2009 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2011 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2013 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2017 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2022 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2024 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2026 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2028 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2032 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2035 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2036 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2038 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2042 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2044 */       if (node.getChildCount() > 0)
/*      */       {
/* 2046 */         builder.append("<UL>");
/* 2047 */         printMGTree(node, builder);
/* 2048 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2053 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2054 */     StringBuffer toReturn = new StringBuffer();
/* 2055 */     String table = "-";
/*      */     try {
/* 2057 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2058 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2059 */       float total = 0.0F;
/* 2060 */       while (it.hasNext()) {
/* 2061 */         String attName = (String)it.next();
/* 2062 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2063 */         boolean roundOffData = false;
/* 2064 */         if ((data != null) && (!data.equals(""))) {
/* 2065 */           if (data.indexOf(",") != -1) {
/* 2066 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2069 */             float value = Float.parseFloat(data);
/* 2070 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2073 */             total += value;
/* 2074 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2077 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2082 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2083 */       while (attVsWidthList.hasNext()) {
/* 2084 */         String attName = (String)attVsWidthList.next();
/* 2085 */         String data = (String)attVsWidthProps.get(attName);
/* 2086 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2087 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2088 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2089 */         String className = (String)graphDetails.get("ClassName");
/* 2090 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2091 */         if (percentage < 1.0F)
/*      */         {
/* 2093 */           data = percentage + "";
/*      */         }
/* 2095 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2097 */       if (toReturn.length() > 0) {
/* 2098 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2102 */       e.printStackTrace();
/*      */     }
/* 2104 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2110 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2111 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2112 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2113 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2114 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2115 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2116 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2117 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2118 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2121 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2122 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2123 */       splitvalues[0] = multiplecondition.toString();
/* 2124 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2127 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2132 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2133 */     if (thresholdType != 3) {
/* 2134 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2135 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2136 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2137 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2138 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2139 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2141 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2142 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2143 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2144 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2145 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2146 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2148 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2149 */     if (updateSelected != null) {
/* 2150 */       updateSelected[0] = "selected";
/*      */     }
/* 2152 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2157 */       StringBuffer toreturn = new StringBuffer("");
/* 2158 */       if (commaSeparatedMsgId != null) {
/* 2159 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2160 */         int count = 0;
/* 2161 */         while (msgids.hasMoreTokens()) {
/* 2162 */           String id = msgids.nextToken();
/* 2163 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2164 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2165 */           count++;
/* 2166 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2167 */             if (toreturn.length() == 0) {
/* 2168 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2170 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2171 */             if (!image.trim().equals("")) {
/* 2172 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2174 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2175 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2178 */         if (toreturn.length() > 0) {
/* 2179 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2183 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2186 */       e.printStackTrace(); }
/* 2187 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2193 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2199 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2200 */   static { _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2201 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2202 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2203 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2204 */     _jspx_dependants.put("/jsp/includes/HostCpuMemDiskGraph.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2231 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2236 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2255 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2259 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2260 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2261 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2264 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2265 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2267 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2268 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2269 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2270 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2271 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2272 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2273 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2274 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2275 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2276 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
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
/* 2305 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2306 */       out.write(10);
/*      */       
/* 2308 */       request.setAttribute("HelpKey", "Monitors System Details");
/*      */       
/* 2310 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2311 */       CpuGraph cpugraph = null;
/* 2312 */       cpugraph = (CpuGraph)_jspx_page_context.getAttribute("cpugraph", 1);
/* 2313 */       if (cpugraph == null) {
/* 2314 */         cpugraph = new CpuGraph();
/* 2315 */         _jspx_page_context.setAttribute("cpugraph", cpugraph, 1);
/*      */       }
/* 2317 */       out.write(10);
/* 2318 */       MemGraph memgraph = null;
/* 2319 */       memgraph = (MemGraph)_jspx_page_context.getAttribute("memgraph", 1);
/* 2320 */       if (memgraph == null) {
/* 2321 */         memgraph = new MemGraph();
/* 2322 */         _jspx_page_context.setAttribute("memgraph", memgraph, 1);
/*      */       }
/* 2324 */       out.write(10);
/* 2325 */       SysloadGraph sysgraph = null;
/* 2326 */       sysgraph = (SysloadGraph)_jspx_page_context.getAttribute("sysgraph", 1);
/* 2327 */       if (sysgraph == null) {
/* 2328 */         sysgraph = new SysloadGraph();
/* 2329 */         _jspx_page_context.setAttribute("sysgraph", sysgraph, 1);
/*      */       }
/* 2331 */       out.write(10);
/* 2332 */       HostResourceBean hrbean = null;
/* 2333 */       hrbean = (HostResourceBean)_jspx_page_context.getAttribute("hrbean", 1);
/* 2334 */       if (hrbean == null) {
/* 2335 */         hrbean = new HostResourceBean();
/* 2336 */         _jspx_page_context.setAttribute("hrbean", hrbean, 1);
/*      */       }
/* 2338 */       out.write(10);
/* 2339 */       GetWLSGraph wlsGraph = null;
/* 2340 */       wlsGraph = (GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2341 */       if (wlsGraph == null) {
/* 2342 */         wlsGraph = new GetWLSGraph();
/* 2343 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2345 */       out.write(10);
/* 2346 */       PerformanceBean perfgraph = null;
/* 2347 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2348 */       if (perfgraph == null) {
/* 2349 */         perfgraph = new PerformanceBean();
/* 2350 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2352 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2354 */       Properties alert = null;
/* 2355 */       String resourcename = request.getParameter("name");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2360 */       String resType = (String)request.getAttribute("hostResType");
/* 2361 */       String hostname = (String)request.getAttribute("hostResName");
/* 2362 */       java.util.HashSet<String> regexprocess = (java.util.HashSet)request.getAttribute("regexprocess");
/* 2363 */       if (resourcename == null)
/*      */       {
/* 2365 */         resourcename = hostname;
/*      */       }
/* 2367 */       boolean configuredState = ((Boolean)request.getAttribute("hostConfigured")).booleanValue();
/* 2368 */       String errorMessage = (String)request.getAttribute("hostErrorMessage");
/* 2369 */       if (resType == null) {
/* 2370 */         resType = request.getParameter("resourceType");
/* 2371 */         request.setAttribute("hostResType", resType);
/*      */       }
/* 2373 */       if (resourcename == null) {
/* 2374 */         resourcename = request.getParameter("resourcename");
/* 2375 */         request.setAttribute("hostResName", resourcename);
/*      */       }
/* 2377 */       ArrayList repoEnabledAttribs = com.adventnet.appmanager.util.ReportUtil.getRepoEnabledAttribs();
/*      */       
/* 2379 */       out.write(10);
/* 2380 */       out.write(10);
/* 2381 */       if (_jspx_meth_c_005fcatch_005f0(_jspx_page_context))
/*      */         return;
/* 2383 */       out.write(32);
/* 2384 */       out.write(10);
/* 2385 */       out.write(10);
/*      */       
/* 2387 */       String Colur = null;
/* 2388 */       String haid = null;
/* 2389 */       String appname = null;
/* 2390 */       String network = null;
/*      */       
/* 2392 */       out.write(10);
/*      */       
/* 2394 */       haid = (String)request.getAttribute("haid");
/* 2395 */       appname = request.getParameter("appName");
/* 2396 */       network = request.getParameter("network");
/* 2397 */       String displayname = (String)request.getAttribute("displayname");
/* 2398 */       request.setAttribute("name", resourcename);
/* 2399 */       request.setAttribute("haid", request.getParameter("haid"));
/* 2400 */       request.setAttribute("appName", request.getParameter("appName"));
/* 2401 */       String resourceid = (String)request.getAttribute("resourceid");
/* 2402 */       HashMap<String, String> ids = (HashMap)request.getAttribute("ids");
/* 2403 */       String redirect = "/HostResource.do?name=" + resourcename + "&haid=" + request.getParameter("haid") + "&appName=" + request.getParameter("appName") + "&resourceid=" + resourceid + "";
/* 2404 */       String encodeurl = URLEncoder.encode(redirect);
/* 2405 */       String redirecting = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid;
/* 2406 */       String encoding = URLEncoder.encode(redirecting);
/* 2407 */       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/* 2408 */       String tab = "1";
/* 2409 */       String search = null;
/*      */       
/* 2411 */       request.setAttribute("breadcumps", search);
/* 2412 */       request.setAttribute("tabtoselect", tab);
/*      */       
/* 2414 */       out.write(10);
/* 2415 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2417 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2418 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2419 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2421 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2423 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2425 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2427 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2428 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2429 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2430 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2433 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2434 */         String available = null;
/* 2435 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2436 */         out.write(10);
/*      */         
/* 2438 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2439 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2440 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2442 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2444 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2446 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2448 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2449 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2450 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2451 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2454 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2455 */           String unavailable = null;
/* 2456 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2457 */           out.write(10);
/*      */           
/* 2459 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2460 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2461 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2463 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2465 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2467 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2469 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2470 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2471 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2472 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2475 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2476 */             String unmanaged = null;
/* 2477 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2478 */             out.write(10);
/*      */             
/* 2480 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2481 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2482 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2484 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2486 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2488 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2490 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2491 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2492 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2493 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2496 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2497 */               String scheduled = null;
/* 2498 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2499 */               out.write(10);
/*      */               
/* 2501 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2502 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2503 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2505 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2507 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2509 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2511 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2512 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2513 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2514 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2517 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2518 */                 String critical = null;
/* 2519 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2520 */                 out.write(10);
/*      */                 
/* 2522 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2523 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2524 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2526 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2528 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2530 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2532 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2533 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2534 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2535 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2538 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2539 */                   String clear = null;
/* 2540 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2541 */                   out.write(10);
/*      */                   
/* 2543 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2544 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2545 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2547 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2549 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2551 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2553 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2554 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2555 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2556 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2559 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2560 */                     String warning = null;
/* 2561 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2562 */                     out.write(10);
/* 2563 */                     out.write(10);
/*      */                     
/* 2565 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2566 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2568 */                     out.write(10);
/* 2569 */                     out.write(10);
/* 2570 */                     out.write(10);
/* 2571 */                     out.write(10);
/*      */                     
/* 2573 */                     String mode = (String)request.getAttribute("mode");
/* 2574 */                     Vector v = (Vector)request.getAttribute("category");
/* 2575 */                     boolean cpudata = false;
/* 2576 */                     boolean showmem = false;
/* 2577 */                     boolean showdisk = false;
/* 2578 */                     boolean showsys = false;
/* 2579 */                     String showdata = (String)request.getAttribute("showdata");
/* 2580 */                     for (int k = 0; k < v.size(); k++)
/*      */                     {
/* 2582 */                       if (((String)v.get(k)).equals("CPU Utilization"))
/*      */                       {
/* 2584 */                         cpudata = true;
/* 2585 */                         request.setAttribute("cpudata", "true");
/*      */                       }
/* 2587 */                       else if (((String)v.get(k)).equals("Disk Utilization"))
/*      */                       {
/* 2589 */                         showdisk = true;
/*      */                       }
/* 2591 */                       else if (((String)v.get(k)).equals("Memory Utilization"))
/*      */                       {
/* 2593 */                         showmem = true;
/*      */ 
/*      */                       }
/* 2596 */                       else if ((((String)v.get(k)).equals("System Load")) && (!resType.equals("Novell")))
/*      */                       {
/* 2598 */                         showsys = true;
/*      */                       }
/*      */                     }
/* 2601 */                     wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2602 */                     perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 2603 */                     perfgraph.setEntity("Response Time");
/* 2604 */                     Properties memprops1 = null;
/* 2605 */                     double c = 0.0D;
/*      */                     
/* 2607 */                     if (configuredState)
/*      */                     {
/*      */ 
/*      */ 
/* 2611 */                       out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"84%\" valign=\"top\">\n      ");
/*      */                       
/* 2613 */                       hrbean.setresourceName(resourcename);
/* 2614 */                       hrbean.setResourceId(resourceid);
/* 2615 */                       long maxcollectiontime = hrbean.getmaxcollectiontime();
/* 2616 */                       Properties cpuprops = cpugraph.getLatestCpuUsage(resourcename, maxcollectiontime, resourceid);
/*      */                       
/* 2618 */                       String errormessage = errorMessage;
/* 2619 */                       if (v.size() != 0)
/*      */                       {
/*      */ 
/*      */ 
/* 2623 */                         ArrayList attribIDs = new ArrayList();
/* 2624 */                         ArrayList resIDs = new ArrayList();
/* 2625 */                         attribIDs.add((String)systeminfo.get("HEALTHID"));
/* 2626 */                         attribIDs.add(ids.get("Availability"));
/* 2627 */                         attribIDs.add(ids.get("Response Time"));
/* 2628 */                         attribIDs.add(ids.get("CPU Utilization"));
/* 2629 */                         attribIDs.add("715");
/* 2630 */                         attribIDs.add("716");
/* 2631 */                         attribIDs.add("1420");
/* 2632 */                         attribIDs.add("1426");
/* 2633 */                         attribIDs.add("1427");
/* 2634 */                         attribIDs.add("1428");
/* 2635 */                         attribIDs.add("1429");
/* 2636 */                         if (ids.get("Swap Memory Utilization") != null)
/*      */                         {
/* 2638 */                           attribIDs.add(ids.get("Swap Memory Utilization"));
/*      */                         }
/* 2640 */                         if (ids.get("Physical Memory Utilization") != null)
/*      */                         {
/* 2642 */                           attribIDs.add(ids.get("Physical Memory Utilization"));
/*      */                         }
/* 2644 */                         if (ids.get("Free Physical Memory (MB)") != null)
/*      */                         {
/* 2646 */                           attribIDs.add(ids.get("Free Physical Memory (MB)"));
/*      */                         }
/* 2648 */                         if (ids.get("Jobs in Minute") != null)
/*      */                         {
/* 2650 */                           attribIDs.add(ids.get("Jobs in Minute"));
/*      */                         }
/* 2652 */                         if (ids.get("Jobs in 15 Minutes") != null)
/*      */                         {
/* 2654 */                           attribIDs.add(ids.get("Jobs in 15 Minutes"));
/*      */                         }
/* 2656 */                         if (ids.get("Jobs in 5 Minutes") != null)
/*      */                         {
/* 2658 */                           attribIDs.add(ids.get("Jobs in 5 Minutes"));
/*      */                         }
/* 2660 */                         if (ids.get("Run Queue") != null)
/*      */                         {
/* 2662 */                           attribIDs.add(ids.get("Run Queue"));
/*      */                         }
/* 2664 */                         if (ids.get("Blocked Process") != null)
/*      */                         {
/* 2666 */                           attribIDs.add(ids.get("Blocked Process"));
/*      */                         }
/* 2668 */                         if (ids.get("User Time") != null)
/*      */                         {
/* 2670 */                           attribIDs.add(ids.get("User Time"));
/*      */                         }
/* 2672 */                         if (ids.get("System Time") != null)
/*      */                         {
/* 2674 */                           attribIDs.add(ids.get("System Time"));
/*      */                         }
/* 2676 */                         if (ids.get("I/O Wait Time") != null)
/*      */                         {
/* 2678 */                           attribIDs.add(ids.get("I/O Wait Time"));
/*      */                         }
/* 2680 */                         if (ids.get("Idle Time") != null)
/*      */                         {
/* 2682 */                           attribIDs.add(ids.get("Idle Time"));
/*      */                         }
/* 2684 */                         if (ids.get("InterruptsPerSec") != null)
/*      */                         {
/* 2686 */                           attribIDs.add(ids.get("InterruptsPerSec"));
/*      */                         }
/* 2688 */                         if (ids.get("Thread Count") != null)
/*      */                         {
/* 2690 */                           attribIDs.add(ids.get("Thread Count"));
/*      */                         }
/* 2692 */                         resIDs.add(request.getParameter("resourceid"));
/*      */                         
/* 2694 */                         ArrayList processlists = (ArrayList)request.getAttribute("process");
/* 2695 */                         if (processlists != null)
/*      */                         {
/* 2697 */                           for (int i = 0; i < processlists.size(); i++)
/*      */                           {
/* 2699 */                             ArrayList processlist = (ArrayList)processlists.get(i);
/* 2700 */                             resIDs.add((String)processlist.get(0));
/*      */                           }
/*      */                         }
/* 2703 */                         alert = getStatus(resIDs, attribIDs);
/*      */                         
/* 2705 */                         out.write("\n\t\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t\t\t    <tr>\n");
/*      */                         
/* 2707 */                         Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2708 */                         String appId = request.getParameter("haid");
/* 2709 */                         String haName = null;
/* 2710 */                         if (appId != null) {
/*      */                           try {
/* 2712 */                             if (Integer.parseInt(appId) < 0) {
/* 2713 */                               throw new NumberFormatException();
/*      */                             }
/* 2715 */                             haName = (String)ht.get(appId);
/*      */                           }
/*      */                           catch (NumberFormatException ex) {
/* 2718 */                             System.out.println("Not a Number: " + ex.getMessage());
/*      */                           }
/*      */                         }
/* 2721 */                         HashMap hm = (HashMap)request.getAttribute("systeminfo");
/* 2722 */                         String systemType = (String)hm.get("HOSTOS");
/* 2723 */                         if (systemType != null)
/*      */                         {
/* 2725 */                           if (systemType.startsWith("Windows"))
/*      */                           {
/* 2727 */                             systemType = "Windows";
/*      */                           }
/* 2729 */                           else if (systemType.equals("SUN"))
/*      */                           {
/* 2731 */                             systemType = "Sun Solaris";
/*      */                           }
/* 2733 */                           else if ((systemType.equals("HP-UX")) || (systemType.equals("HP-TRU64")))
/*      */                           {
/* 2735 */                             systemType = "HP-UX / Tru64";
/*      */                           }
/* 2737 */                           else if (systemType.equals("FreeBSD")) {
/* 2738 */                             systemType = "FreeBSD / OpenBSD";
/*      */                           }
/*      */                         }
/*      */                         
/* 2742 */                         out.write(10);
/* 2743 */                         out.write(9);
/* 2744 */                         out.write(9);
/*      */                         
/* 2746 */                         ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2747 */                         _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2748 */                         _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2749 */                         int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2750 */                         if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                           for (;;) {
/* 2752 */                             out.write(10);
/* 2753 */                             out.write(9);
/* 2754 */                             out.write(9);
/*      */                             
/* 2756 */                             WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2757 */                             _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2758 */                             _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                             
/* 2760 */                             _jspx_th_c_005fwhen_005f0.setTest("${ param.alert!='true' && param.all!='true' }");
/* 2761 */                             int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2762 */                             if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                               for (;;) {
/* 2764 */                                 out.write("\n\n\t\t<!-- ###### Display Monitor information & Edit monitor information div & total availability graph - start ######## -->\n      \t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\n        <tr>\n          <td width=\"60%\" height=\"26\" valign=\"top\" >\n\t\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n            \t<tr>\n                \t<td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2765 */                                 out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2766 */                                 out.write("</td>\n              \t</tr>\n              \t<tr>\n                \t<td width=\"30%\" class=\"monitorinfoodd\">");
/* 2767 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2768 */                                 out.write(" </td>\n                \t<td width=\"70%\" class=\"monitorinfoodd\">");
/* 2769 */                                 out.print(displayname);
/* 2770 */                                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2771 */                                 out.write("<!--$Id$-->\n");
/*      */                                 
/* 2773 */                                 String hostName = "localhost";
/*      */                                 try {
/* 2775 */                                   hostName = InetAddress.getLocalHost().getHostName();
/*      */                                 } catch (Exception ex) {
/* 2777 */                                   ex.printStackTrace();
/*      */                                 }
/* 2779 */                                 String portNumber = System.getProperty("webserver.port");
/* 2780 */                                 String styleClass = "monitorinfoodd";
/* 2781 */                                 if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2782 */                                   styleClass = "whitegrayborder-conf-mon";
/*      */                                 }
/*      */                                 
/* 2785 */                                 out.write(10);
/*      */                                 
/* 2787 */                                 PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2788 */                                 _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2789 */                                 _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 2791 */                                 _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2792 */                                 int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2793 */                                 if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                                   for (;;) {
/* 2795 */                                     out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2796 */                                     out.print(styleClass);
/* 2797 */                                     out.write(34);
/* 2798 */                                     out.write(62);
/* 2799 */                                     out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2800 */                                     out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2801 */                                     out.print(styleClass);
/* 2802 */                                     out.write(34);
/* 2803 */                                     out.write(62);
/* 2804 */                                     out.print(hostName);
/* 2805 */                                     out.write(95);
/* 2806 */                                     out.print(portNumber);
/* 2807 */                                     out.write("</td>\n</tr>\n");
/* 2808 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2809 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2813 */                                 if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2814 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                                 }
/*      */                                 
/* 2817 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2818 */                                 out.write(10);
/* 2819 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 2821 */                                 EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2822 */                                 _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2823 */                                 _jspx_th_logic_005fempty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 2825 */                                 _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2826 */                                 int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2827 */                                 if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                                   for (;;) {
/* 2829 */                                     out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2830 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.syshealth"));
/* 2831 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2832 */                                     out.print(resourceid);
/* 2833 */                                     out.write("&attributeid=");
/* 2834 */                                     out.print((String)systeminfo.get("HEALTHID"));
/* 2835 */                                     out.write("')\">");
/* 2836 */                                     out.print(getSeverityImageForHealth("-1"));
/* 2837 */                                     out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2838 */                                     int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2839 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2843 */                                 if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2844 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                                 }
/*      */                                 
/* 2847 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2848 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 2850 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2851 */                                 _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2852 */                                 _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 2854 */                                 _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2855 */                                 int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2856 */                                 if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                   for (;;) {
/* 2858 */                                     out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2859 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.syshealth"));
/* 2860 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2861 */                                     out.print(resourceid);
/* 2862 */                                     out.write("&attributeid=");
/* 2863 */                                     out.print((String)systeminfo.get("HEALTHID"));
/* 2864 */                                     out.write("')\">");
/* 2865 */                                     out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + (String)systeminfo.get("HEALTHID"))));
/* 2866 */                                     out.write("</a>\n\t\t\t\t\t");
/* 2867 */                                     out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + (String)systeminfo.get("HEALTHID") + "#" + "MESSAGE"), (String)systeminfo.get("HEALTHID"), alert.getProperty(resourceid + "#" + (String)systeminfo.get("HEALTHID")), resourceid));
/* 2868 */                                     out.write("\n\t\t\t\t\t");
/* 2869 */                                     if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, (String)systeminfo.get("HEALTHID")) != 0) {
/* 2870 */                                       out.write("\n\t\t\t\t\t<br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2871 */                                       out.print(resourceid + "_" + (String)systeminfo.get("HEALTHID"));
/* 2872 */                                       out.write("&monitortype=");
/* 2873 */                                       out.print(resType);
/* 2874 */                                       out.write("')\">");
/* 2875 */                                       out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2876 */                                       out.write("</a></span>\n                  ");
/*      */                                     }
/* 2878 */                                     out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2879 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2880 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2884 */                                 if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2885 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                 }
/*      */                                 
/* 2888 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2889 */                                 out.write("\n              \t<tr>\n                \t<td class=\"monitorinfoodd\">");
/* 2890 */                                 out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2891 */                                 out.write(" </td>\n                \t<td class=\"monitorinfoodd\">");
/* 2892 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.server"));
/* 2893 */                                 out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                 
/* 2895 */                                 EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2896 */                                 _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2897 */                                 _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 2899 */                                 _jspx_th_logic_005fempty_005f1.setName("systeminfo");
/* 2900 */                                 int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2901 */                                 if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                   for (;;) {
/* 2903 */                                     out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2904 */                                     out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2905 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">-&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2906 */                                     out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2907 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2908 */                                     out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2909 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2910 */                                     out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2911 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 2912 */                                     int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 2913 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2917 */                                 if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 2918 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                 }
/*      */                                 
/* 2921 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 2922 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 2924 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2925 */                                 _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2926 */                                 _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 2928 */                                 _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 2929 */                                 int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2930 */                                 if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                   for (;;) {
/* 2932 */                                     out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2933 */                                     out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2934 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\" title='");
/* 2935 */                                     out.print(systeminfo.get("HOSTNAME"));
/* 2936 */                                     out.write("&nbsp;(");
/* 2937 */                                     out.print(systeminfo.get("HOSTIP"));
/* 2938 */                                     out.write(41);
/* 2939 */                                     out.write(39);
/* 2940 */                                     out.write(62);
/* 2941 */                                     out.print(FormatUtil.getTrimmedText(systeminfo.get("HOSTNAME").toString(), 20));
/* 2942 */                                     out.write("&nbsp;(");
/* 2943 */                                     out.print(systeminfo.get("HOSTIP"));
/* 2944 */                                     out.write(")</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2945 */                                     out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2946 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">\n");
/*      */                                     
/* 2948 */                                     if (systeminfo.get("HOSTOS").equals("SUN"))
/*      */                                     {
/*      */ 
/* 2951 */                                       out.write("\n\t\t\t\t\t");
/* 2952 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.sunname"));
/* 2953 */                                       out.write(10);
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 2959 */                                       out.write("\n\t\t\t\t\t");
/* 2960 */                                       out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 2961 */                                       out.write(10);
/*      */                                     }
/*      */                                     
/*      */ 
/* 2965 */                                     out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2966 */                                     out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2967 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2968 */                                     out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 2969 */                                     out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2970 */                                     out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2971 */                                     out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2972 */                                     out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 2973 */                                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t");
/* 2974 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2975 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2979 */                                 if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2980 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                 }
/*      */                                 
/* 2983 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2984 */                                 out.write("\n\t\t\t<!--<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/*      */                                 
/* 2986 */                                 out.write("</td>\n\t\t\t\t");
/* 2987 */                                 if (_jspx_meth_c_005fchoose_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 2989 */                                 out.write("\n\t\t\t</tr>-->\n\t\t\t");
/* 2990 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 2991 */                                 out.write("\n            </table>\n            <br>\n");
/*      */                                 
/* 2993 */                                 int resTimeStatus = -1;
/* 2994 */                                 if (showdata.equals("2"))
/*      */                                 {
/*      */ 
/* 2997 */                                   out.write("\n            ");
/* 2998 */                                   out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 2999 */                                   out.write("\n            <table border=0 cellspacing =0 cellpadding = 0 width=\"100%\">\n            \t<tr>\n            \t\t<td class=\"bodytextbold\" width=\"60%\">");
/* 3000 */                                   out.print(FormatUtil.getString("am.webclient.hometab.highresptimemonitors.columnheader.resptime"));
/* 3001 */                                   out.write("     \n            \t\t");
/*      */                                   
/* 3003 */                                   if (alert.getProperty("Response Time") != null)
/*      */                                   {
/* 3005 */                                     resTimeStatus = Integer.parseInt(alert.getProperty("Response Time"));
/*      */                                   }
/*      */                                   
/* 3008 */                                   out.write("\n\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3009 */                                   out.print(resourceid);
/* 3010 */                                   out.write("&attributeid=");
/* 3011 */                                   out.print((String)ids.get("Response Time"));
/* 3012 */                                   out.write("&alertconfigurl=");
/* 3013 */                                   out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + (String)ids.get("Response Time") + "&attributeToSelect=" + (String)ids.get("Response Time") + "&redirectto=" + encodeurl));
/* 3014 */                                   out.write("')\">");
/* 3015 */                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + (String)ids.get("Response Time"))));
/* 3016 */                                   out.write("</a>\n\t            \t</td>\n\t            \t<td width=\"30%\" align=\"right\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3017 */                                   if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                     return;
/* 3019 */                                   out.write("&attributeid=");
/* 3020 */                                   out.print((String)ids.get("Response Time"));
/* 3021 */                                   out.write("&period=-7',740,550)\">\n\t      \t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3022 */                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3023 */                                   out.write("\"></a>\n\t      \t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3024 */                                   if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                     return;
/* 3026 */                                   out.write("&attributeid=");
/* 3027 */                                   out.print((String)ids.get("Response Time"));
/* 3028 */                                   out.write("&period=-30',740,550)\">\n\t      \t\t\t<img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3029 */                                   out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3030 */                                   out.write("\"></a>\n\t      \t\t\t</td>\n\t      \t\t</tr>\n\n\t      \t\t<tr>\n\t      \t\t\t<td colspan=\"0\" align=\"center\" class=\"textResponseTime\">\n");
/*      */                                   
/* 3032 */                                   if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) != -1L)
/*      */                                   {
/* 3034 */                                     out.write("\n                  ");
/* 3035 */                                     out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3036 */                                     out.write("\n                  ");
/* 3037 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.ms"));
/* 3038 */                                     out.write(10);
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3043 */                                     out.write("\n                  -\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3047 */                                   out.write("     \t\t\t</td>\n\t      \t\t\t<td valign=bottom align=right>\n");
/*      */                                   
/* 3049 */                                   if (!EnterpriseUtil.isAdminServer())
/*      */                                   {
/*      */ 
/* 3052 */                                     out.write("\n\n\t\t\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3053 */                                     out.print(resourceid);
/* 3054 */                                     out.write("&attributeIDs=");
/* 3055 */                                     out.print((String)ids.get("Response Time"));
/* 3056 */                                     out.write("&attributeToSelect=");
/* 3057 */                                     out.print((String)ids.get("Response Time"));
/* 3058 */                                     out.write("&redirectto=");
/* 3059 */                                     out.print(encodeurl);
/* 3060 */                                     out.write("\" class=\"staticlinks\">");
/* 3061 */                                     out.print(ALERTCONFIG_TEXT);
/* 3062 */                                     out.write("</a>&nbsp;</td>\n\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3066 */                                   out.write("\t\t\t\t</td>\n  \t    \t\t</tr>\n\n         \t  </table>\n\n\n\n\n\n            \t");
/* 3067 */                                   out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3068 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */ 
/* 3072 */                                 out.write("\n\n\n            ");
/*      */                                 
/* 3074 */                                 PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3075 */                                 _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3076 */                                 _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 3078 */                                 _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 3079 */                                 int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3080 */                                 if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                   for (;;) {
/* 3082 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3084 */                                     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3085 */                                     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3086 */                                     _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                     
/* 3088 */                                     _jspx_th_c_005fif_005f0.setTest("${empty cpudata && showdata=='1'}");
/* 3089 */                                     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3090 */                                     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                       for (;;) {
/* 3092 */                                         out.write("\n\t\t\t\t\t<div align=\"center\">\n\t\t\t\t\t\t<a style=cursor:pointer;>                                                    \n                                                    ");
/* 3093 */                                         if ("Node".equalsIgnoreCase(request.getParameter("resourceType")))
/*      */                                         {
/* 3095 */                                           out.write("\n                                                     <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick=\"javascript:toggleDiv('edit')\">\n                                                    ");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 3099 */                                           out.write("\n                                                    <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" onclick='javascript:location.href=\"/manageConfMons.do?haid=");
/* 3100 */                                           out.print(haid);
/* 3101 */                                           out.write("&method=editPreConfMonitor&resourceid=");
/* 3102 */                                           out.print(request.getParameter("resourceid"));
/* 3103 */                                           out.write("&type=");
/* 3104 */                                           out.print(request.getParameter("resourceType"));
/* 3105 */                                           out.write("&componentName=host&mode=");
/* 3106 */                                           out.print(mode);
/* 3107 */                                           out.write("&\"'>\n                                                    ");
/*      */                                         }
/* 3109 */                                         out.write("\n\t\t\t            \t<tr>\n\t\t\t              \t\t<td>&nbsp;</td>\n\t\t\t            \t</tr>\n\t\t\t            \t<tr>\n\t\t\t\t    \t\t\t<td><table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"getmoredatatable\">\n\t\t\t\t\t    \t\t<tr>\n\t\t\t\t\t\t\t\t    <td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img src=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\" border=\"0\" vspace=\"2\" hspace=\"5\"></td>\n\t\t\t\t\t\t\t\t    <td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 3110 */                                         out.print(FormatUtil.getString("am.webclient.configureimage.host.text"));
/* 3111 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t    </tr>\n\t\t\t\t\t\t\t\t    </table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t   \t\t\t</tr>\n\t\t\t\t    \t</table>\n\t\t\t\t    \t</a>\n\t\t\t    \t</div>\n\t\t\t\t\t\n\t\t\t\t");
/* 3112 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3113 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3117 */                                     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3118 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                     }
/*      */                                     
/* 3121 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3122 */                                     out.write("\n            ");
/* 3123 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3124 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3128 */                                 if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3129 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                 }
/*      */                                 
/* 3132 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3133 */                                 out.write("\n          </td>\n          <td width=\"2%\"></td>\n          <td width=\"40%\" valign=\"top\">\n          \t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n            \t<tr>\n                \t<td class=\"tableheadingbborder\">");
/* 3134 */                                 out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 3135 */                                 out.write("</td>\n              \t</tr>\n              \t<tr>\n\t                <td align=\"center\">\n\t                \t<table  align=\"right\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t          \t\t\t<tr>\n\t\t\t          \t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3136 */                                 if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 3138 */                                 out.write("&period=1&resourcename=");
/* 3139 */                                 if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 3141 */                                 out.write("')\">\n\t\t\t      \t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3142 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3143 */                                 out.write("\"></a></td>\n\t\t\t            \t\t<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3144 */                                 if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 3146 */                                 out.write("&period=2&resourcename=");
/* 3147 */                                 if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                   return;
/* 3149 */                                 out.write("')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3150 */                                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3151 */                                 out.write("\"></a></td>\n\t\t\t      \t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t \t\t</td>\n               </tr>\n              <tr>\n                <td align=\"center\"> ");
/*      */                                 
/* 3153 */                                 AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 3154 */                                 _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 3155 */                                 _jspx_th_awolf_005fpiechart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 3157 */                                 _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                                 
/* 3159 */                                 _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                                 
/* 3161 */                                 _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                                 
/* 3163 */                                 _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                                 
/* 3165 */                                 _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                                 
/* 3167 */                                 _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                                 
/* 3169 */                                 _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 3170 */                                 int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 3171 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 3172 */                                   if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3173 */                                     out = _jspx_page_context.pushBody();
/* 3174 */                                     _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 3175 */                                     _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3178 */                                     out.write("\n                  ");
/*      */                                     
/* 3180 */                                     Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 3181 */                                     _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 3182 */                                     _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                     
/* 3184 */                                     _jspx_th_awolf_005fmap_005f0.setId("color");
/* 3185 */                                     int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 3186 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 3187 */                                       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3188 */                                         out = _jspx_page_context.pushBody();
/* 3189 */                                         _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 3190 */                                         _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3193 */                                         out.write(32);
/*      */                                         
/* 3195 */                                         AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3196 */                                         _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 3197 */                                         _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                         
/* 3199 */                                         _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                         
/* 3201 */                                         _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 3202 */                                         int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 3203 */                                         if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 3204 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                         }
/*      */                                         
/* 3207 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 3208 */                                         out.write("\n                  ");
/*      */                                         
/* 3210 */                                         AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 3211 */                                         _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 3212 */                                         _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                         
/* 3214 */                                         _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                         
/* 3216 */                                         _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 3217 */                                         int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 3218 */                                         if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 3219 */                                           this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                         }
/*      */                                         
/* 3222 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 3223 */                                         out.write(32);
/* 3224 */                                         int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3225 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3228 */                                       if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3229 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3232 */                                     if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3233 */                                       this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                     }
/*      */                                     
/* 3236 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3237 */                                     out.write("\n                  ");
/* 3238 */                                     int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3239 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3242 */                                   if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3243 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3246 */                                 if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3247 */                                   this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0); return;
/*      */                                 }
/*      */                                 
/* 3250 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3251 */                                 out.write(" </td>");
/* 3252 */                                 out.write("\n              </tr>\n\t\t\t\t<tr>\n\t\t\t\t<td><table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"padding:10px 0px 10px 0px;\">\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"49%\"  colspan=\"2\" class=\"bodytext\" nowrap>&nbsp; ");
/* 3253 */                                 out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3254 */                                 out.write("\n\n\t\t\t\t <a style=\"position:relative; top:2px;\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3255 */                                 out.print(resourceid);
/* 3256 */                                 out.write("&attributeid=");
/* 3257 */                                 out.print((String)ids.get("Availability"));
/* 3258 */                                 out.write("&alertconfigurl=");
/* 3259 */                                 out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + (String)ids.get("Availability") + "&attributeToSelect=" + (String)ids.get("Availability") + "&redirectto=" + encodeurl));
/* 3260 */                                 out.write("')\">\n\t\t\t\t ");
/* 3261 */                                 out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + (String)ids.get("Availability"))));
/* 3262 */                                 out.write("</a> </td>\n\t\t\t\t");
/*      */                                 
/* 3264 */                                 if (!EnterpriseUtil.isAdminServer())
/*      */                                 {
/*      */ 
/* 3267 */                                   out.write("\n\t\t\t\t  <td width=\"50%\" class=\"bodytext\" align=\"right\">\n\t\t\t\t <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3268 */                                   out.print(resourceid);
/* 3269 */                                   out.write("&attributeIDs=");
/* 3270 */                                   out.print((String)ids.get("Availability"));
/* 3271 */                                   out.write(44);
/* 3272 */                                   out.print((String)ids.get("Health"));
/* 3273 */                                   out.write("&attributeToSelect=");
/* 3274 */                                   out.print((String)ids.get("Availability"));
/* 3275 */                                   out.write("&redirectto=");
/* 3276 */                                   out.print(encodeurl);
/* 3277 */                                   out.write("\" class=\"staticlinks\">");
/* 3278 */                                   out.print(ALERTCONFIG_TEXT);
/* 3279 */                                   out.write("</a>&nbsp;</td>\n\t\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3283 */                                 out.write("\n\t\t\t\t</tr>\n\n\t\t\t\t</table>\n\n\t\t\t\t<tr>\n\n\t\t\t\t<!-- ######## Display the Thread Count - Start  ########-->\n\t\t\t\t");
/*      */                                 
/* 3285 */                                 String monitorMode = (String)request.getAttribute("mode");
/* 3286 */                                 if ((resType.equals("Linux")) && (
/* 3287 */                                   (monitorMode.equals("TELNET")) || (monitorMode.equals("SSH"))))
/*      */                                 {
/* 3289 */                                   out.write("\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\"  class=\"lrtbdarkborder\"  style =\"padding:5px 0px 13px 0px\">\n\t\t\t\t\t\t<tr>&nbsp;\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t  \t <tr>\n\t\t\t\t\t    \t");
/*      */                                   
/* 3291 */                                   String threadCount = (String)request.getAttribute("threadCount");
/* 3292 */                                   String threadCountAttribute = (String)ids.get("Thread Count");
/* 3293 */                                   String resourceidThreadCount = (String)request.getAttribute("resourceid");
/*      */                                   
/* 3295 */                                   out.write("\n\t\t\t\t\t    \t\t<td width=\"8%\"><b>&nbsp;");
/* 3296 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.threadcount"));
/* 3297 */                                   out.write("</b></td>\n\t\t\t\t\t    \t");
/* 3298 */                                   if (threadCount != null)
/*      */                                   {
/* 3300 */                                     out.write("\n\t\t\t\t\t    \t\t<td width=\"8%\" >\n\t\t\t\t\t    \t\t <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3301 */                                     out.print(resourceid);
/* 3302 */                                     out.write("&attributeid=");
/* 3303 */                                     out.print(threadCountAttribute);
/* 3304 */                                     out.write("&alertconfigurl=");
/* 3305 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + (String)ids.get("Thread Count") + "&attributeToSelect=" + (String)ids.get("Thread Count") + "&redirectto=" + encodeurl));
/* 3306 */                                     out.write("')\">\n\t\t\t\t\t    \t\t\t ");
/* 3307 */                                     out.print(getSeverityImage(alert.getProperty(resourceidThreadCount + "#" + 768)));
/* 3308 */                                     out.write("</a>\n\t\t\t\t\t    \t\t</td>\n\n\t\t\t\t\t    \t\t<td width=\"25%\" align=\"left\">\n\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3309 */                                     out.print(resourceid);
/* 3310 */                                     out.write("&attributeid=");
/* 3311 */                                     out.print(threadCountAttribute);
/* 3312 */                                     out.write("&period=20&businessPeriod=oni')\" class=\"staticlinks\">\n\t\t\t               \t\t <span>");
/* 3313 */                                     out.print(request.getAttribute("threadCount"));
/* 3314 */                                     out.write("</span></a>\n\t\t\t\t\t\t\t\t</td>\n\n\t\t\t\t\t    \t\t<!--<td width=\"25%\" align=\"right\"> <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3315 */                                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 3317 */                                     out.write("&attributeid=");
/* 3318 */                                     out.print((String)ids.get("Thread Count"));
/* 3319 */                                     out.write("&period=-7',740,550)\">\n\t\t\t\t      \t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3320 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3321 */                                     out.write("\"></a>\n\t\t\t\t      \t\t\t</td>\n\n\t\t\t\t      \t\t\t<td width=\"9%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3322 */                                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 3324 */                                     out.write("&attributeid=");
/* 3325 */                                     out.print((String)ids.get("Thread Count"));
/* 3326 */                                     out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3327 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3328 */                                     out.write("\"></a></td>-->\n\n\t\t\t\t\t    \t\t\n\t\t\t\t\t    \t");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3332 */                                     out.write("\n\t\t\t\t\t    \t\t <td  align=\"center\" colspan=\"6\">");
/* 3333 */                                     out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3334 */                                     out.write("</td>\n\t\t\t\t\t    \t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3338 */                                   out.write("\n\t\t\t\t\t   \t\t\n\t\t\t\t        </tr>\n\t\t\t\t        <tr>\n\t\t\t\t\t\t\t\t<td  align=\"right\" colspan=\"6\">\n\t\t\t\t\t\t\t\t");
/* 3339 */                                   if (threadCount != null) {
/* 3340 */                                     out.write("\n\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" border=\"0\" title=\"\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3341 */                                     out.print(resourceid);
/* 3342 */                                     out.write("&attributeIDs=");
/* 3343 */                                     out.print(threadCountAttribute);
/* 3344 */                                     out.write("&attributeToSelect=");
/* 3345 */                                     out.print(threadCountAttribute);
/* 3346 */                                     out.write("&redirectto=");
/* 3347 */                                     out.print(encoding);
/* 3348 */                                     out.write("\" class=\"staticlinks\" title=\"\">");
/* 3349 */                                     out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 3350 */                                     out.write("</a>&nbsp;\n\t\t\t\t\t\t\t\t");
/*      */                                   }
/* 3352 */                                   out.write("\n\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\n\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3357 */                                 out.write("\n\n\t\t\t<!-- ######## Display the Thread Count - End  ########-->\n\t\t\t\t</tr>\n            </td>\n\t\t\t </tr>\n             </table>\n          </tr>\n       </table>\n\n\n\n       <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3358 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3359 */                                 out.write("</td></tr></table>\n      <!-- ######## Display Monitor information & Edit monitor information div & total availability graph - end ######## -->\n\n      <br>\n\t  \t");
/*      */                                 
/* 3361 */                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3362 */                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3363 */                                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 3365 */                                 _jspx_th_c_005fif_005f1.setTest("${(empty cpudata && showdata=='1') || (empty cpudata && showdata=='2')}");
/* 3366 */                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3367 */                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                   for (;;) {
/* 3369 */                                     out.write("\n        <table class=\"lrtbdarkborder\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"99%\">\n        \t<tr>\n                <td class=\"tableheadingtrans\" height=\"29\" width=\"99%\">");
/* 3370 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.response"));
/* 3371 */                                     out.write("&nbsp;</td>\n        \t</tr>\n        </table>\n        <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n            <tr>\n\t    \t\t<td width=\"405\" height=\"127\" valign=\"top\">\n\t    \t\t\t<table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n\t    \t\t\t\t<tr>\n\t    \t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3372 */                                     if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3374 */                                     out.write("&attributeid=");
/* 3375 */                                     out.print((String)ids.get("Response Time"));
/* 3376 */                                     out.write("&period=-7',740,550)\">\n\t    \t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3377 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3378 */                                     out.write("\"></td>\n\t    \t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3379 */                                     if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3381 */                                     out.write("&attributeid=");
/* 3382 */                                     out.print((String)ids.get("Response Time"));
/* 3383 */                                     out.write("&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3384 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3385 */                                     out.write("\"></a></td>\n\t    \t\t\t\t</tr>\n\t    \t\t\t\t<tr>\n\t\t    \t\t\t\t<td colspan=\"2\">\n\t\t    \t\t\t\t");
/*      */                                     
/* 3387 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3388 */                                     _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3389 */                                     _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */                                     
/* 3391 */                                     _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfgraph");
/*      */                                     
/* 3393 */                                     _jspx_th_awolf_005ftimechart_005f0.setWidth("320");
/*      */                                     
/* 3395 */                                     _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                     
/* 3397 */                                     _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                                     
/* 3399 */                                     _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 3401 */                                     _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.responsetimeinms"));
/* 3402 */                                     int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3403 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3404 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3405 */                                         out = _jspx_page_context.pushBody();
/* 3406 */                                         _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3407 */                                         _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3410 */                                         out.write("\n\t\t    \t\t\t\t");
/* 3411 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3412 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3415 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3416 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3419 */                                     if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3420 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                     }
/*      */                                     
/* 3423 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3424 */                                     out.write("\n\t    \t\t\t\t</tr>\n\t    \t\t\t</table>\n\t    \t\t</td>\n\t    \t\t<td width=\"562\" valign=\"top\"> <br> <br>\n\t    \t\t\t<table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbtborder\">\n\t    \t\t\t\t<tr>\n\t    \t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3425 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3427 */                                     out.write("</span></td>");
/* 3428 */                                     out.write("\n\t    \t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3429 */                                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3431 */                                     out.write("</span></td>");
/* 3432 */                                     out.write("\n\t    \t\t\t\t\t<td class=\"columnheadingnotop\" colspan=\"2\"><span class=\"bodytextbold\">");
/* 3433 */                                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3435 */                                     out.write("</span></td>");
/* 3436 */                                     out.write("\n\t    \t\t\t\t</tr>\n\t    \t\t\t\t<tr>\n\t    \t\t\t\t\n\t    \t\t\t\t\t<td width=\"56%\" height=\"19\" class=\"whitegrayborder\" >");
/* 3437 */                                     out.print(FormatUtil.getString("am.webclient.oracle.currentresponsetime"));
/* 3438 */                                     out.write(" </td>\n\t    \t\t\t\t\t<td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n");
/*      */                                     
/* 3440 */                                     if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) != -1L)
/*      */                                     {
/* 3442 */                                       out.write("\n\t\t\t    ");
/* 3443 */                                       out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3444 */                                       out.write("\n\t\t\t    ");
/* 3445 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.ms"));
/* 3446 */                                       out.write(10);
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3451 */                                       out.write("\n\t\t\t    -\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3455 */                                     out.write("\n\t\t\t\t\t\t\t</td>\n\n\t\t\t\t\t\t\t<td width=\"18%\" class=\"whitegrayborder\">\n");
/*      */                                     
/* 3457 */                                     if (alert.getProperty("Response Time") != null) {
/* 3458 */                                       resTimeStatus = Integer.parseInt(alert.getProperty("Response Time"));
/*      */                                     }
/*      */                                     
/* 3461 */                                     out.write("\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3462 */                                     out.print(resourceid);
/* 3463 */                                     out.write("&attributeid=");
/* 3464 */                                     out.print((String)ids.get("Response Time"));
/* 3465 */                                     out.write("&alertconfigurl=");
/* 3466 */                                     out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + (String)ids.get("Response Time") + "&attributeToSelect=" + (String)ids.get("Response Time") + "&redirectto=" + encodeurl));
/* 3467 */                                     out.write("')\">");
/* 3468 */                                     out.print(getSeverityImage(alert.getProperty(resourceid + "#" + (String)ids.get("Response Time"))));
/* 3469 */                                     out.write("</a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n");
/*      */                                     
/* 3471 */                                     if (!EnterpriseUtil.isAdminServer())
/*      */                                     {
/*      */ 
/* 3474 */                                       out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td height=\"32\" colspan=\"4\" class=\"bodytext\" align=\"right\">\n\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3475 */                                       out.print(resourceid);
/* 3476 */                                       out.write("&attributeIDs=");
/* 3477 */                                       out.print((String)ids.get("Response Time"));
/* 3478 */                                       out.write("&attributeToSelect=");
/* 3479 */                                       out.print((String)ids.get("Response Time"));
/* 3480 */                                       out.write("&redirectto=");
/* 3481 */                                       out.print(encodeurl);
/* 3482 */                                       out.write("\" class=\"staticlinks\">");
/* 3483 */                                       out.print(ALERTCONFIG_TEXT);
/* 3484 */                                       out.write("</a>&nbsp;</td>\n\t\t\t\t\t\t</tr>\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 3488 */                                     out.write("\n\n\t\t\t\t\t </table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t");
/* 3489 */                                     if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3491 */                                     out.write("\n\t\t\t");
/* 3492 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3493 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3497 */                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3498 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                                 }
/*      */                                 
/* 3501 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3502 */                                 out.write("\n\n\n\t\t<!-- ######## Display CPU and Memory and Disk Utilization dial char - start ######## -->\n\n\t\t");
/* 3503 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n  \n\n");
/* 3504 */                                 DialChartSupport dialGraph = null;
/* 3505 */                                 dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 3506 */                                 if (dialGraph == null) {
/* 3507 */                                   dialGraph = new DialChartSupport();
/* 3508 */                                   _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                                 }
/* 3510 */                                 out.write(10);
/*      */                                 
/*      */                                 try
/*      */                                 {
/* 3514 */                                   String hostos = (String)hm.get("HOSTOS");
/* 3515 */                                   String hostname1 = (String)hm.get("HOSTNAME");
/*      */                                   
/* 3517 */                                   com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 3518 */                                   Properties property = new Properties();
/*      */                                   
/* 3520 */                                   if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")))
/*      */                                   {
/* 3522 */                                     property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname1, hostos);
/* 3523 */                                     if ((property != null) && (property.size() > 0))
/*      */                                     {
/*      */ 
/*      */ 
/* 3527 */                                       out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr> \n    <td height=\"26\" class=\"tableheading\" align=center>");
/* 3528 */                                       out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3529 */                                       out.write("</td>\n     <td height=\"26\" class=\"tableheading\" align=center>");
/* 3530 */                                       out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3531 */                                       out.write("</td>\n     <td height=\"26\" class=\"tableheading\" align=center>");
/* 3532 */                                       out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 3533 */                                       out.write("</td>\n  </tr>\n</table>\n\n              \t\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr> \n    <td width=\"30%\" valign=\"top\">\n    ");
/* 3534 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3535 */                                       out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">     \n        \n        <tr> \n         ");
/*      */                                       
/* 3537 */                                       String cpuid = property.getProperty("cpuid");
/* 3538 */                                       String memid = property.getProperty("memid");
/* 3539 */                                       String diskid = property.getProperty("diskid");
/*      */                                       
/* 3541 */                                       String cpuvalue = property.getProperty("CPU Utilization");
/* 3542 */                                       if (cpuvalue != null)
/*      */                                       {
/* 3544 */                                         String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + (String)request.getAttribute("resourceid") + "&attributeid=" + cpuid + "&period=0')";
/* 3545 */                                         dialGraph.setValue(Long.parseLong(cpuvalue));
/* 3546 */                                         out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3547 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3548 */                                         out.write(45);
/* 3549 */                                         out.print(cpuvalue);
/* 3550 */                                         out.write(" %'> \n   \n");
/*      */                                         
/* 3552 */                                         DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3553 */                                         _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 3554 */                                         _jspx_th_awolf_005fdialchart_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                         
/* 3556 */                                         _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                         
/* 3558 */                                         _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                         
/* 3560 */                                         _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                         
/* 3562 */                                         _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                         
/* 3564 */                                         _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                         
/* 3566 */                                         _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                         
/* 3568 */                                         _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                         
/* 3570 */                                         _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                         
/* 3572 */                                         _jspx_th_awolf_005fdialchart_005f0.setResourceId((String)request.getAttribute("resourceid"));
/*      */                                         
/* 3574 */                                         _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 3575 */                                         int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 3576 */                                         if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 3577 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 3578 */                                             out = _jspx_page_context.pushBody();
/* 3579 */                                             _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 3580 */                                             _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3583 */                                             out.write(32);
/* 3584 */                                             out.write(10);
/* 3585 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 3586 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3589 */                                           if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 3590 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3593 */                                         if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 3594 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                         }
/*      */                                         
/* 3597 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 3598 */                                         out.write("\n         </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3602 */                                         out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\t\t\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 3603 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3604 */                                         out.write(32);
/* 3605 */                                         out.write(62);
/* 3606 */                                         out.write(10);
/* 3607 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3608 */                                         out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n    \n  ");
/*      */                                       }
/* 3610 */                                       out.write("\n      </tr> \n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 3611 */                                       if (cpuvalue != null)
/*      */                                       {
/* 3613 */                                         out.write("\n<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3614 */                                         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                           return;
/* 3616 */                                         out.write("&attributeid=");
/* 3617 */                                         out.print(cpuid);
/* 3618 */                                         out.write("&period=-7')\" class='staticlinks'>");
/* 3619 */                                         out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 3620 */                                         out.write(32);
/* 3621 */                                         out.write(45);
/* 3622 */                                         out.write(32);
/* 3623 */                                         out.print(cpuvalue);
/* 3624 */                                         out.write("</a> %\n");
/*      */                                       }
/* 3626 */                                       out.write("\n  </td>\n       </tr>\n       </table>");
/* 3627 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3628 */                                       out.write("</td>\n      <td width=\"30%\"> ");
/* 3629 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3630 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/* 3631 */                                       String memvalue = property.getProperty("Memory Utilization");
/* 3632 */                                       if (memvalue != null)
/*      */                                       {
/* 3634 */                                         String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + (String)request.getAttribute("resourceid") + "&attributeid=" + memid + "&period=0')";
/* 3635 */                                         dialGraph.setValue(Long.parseLong(memvalue));
/* 3636 */                                         out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3637 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3638 */                                         out.write(45);
/* 3639 */                                         out.print(memvalue);
/* 3640 */                                         out.write(" %' >\n\t\t\t\t\t");
/*      */                                         
/* 3642 */                                         DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3643 */                                         _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 3644 */                                         _jspx_th_awolf_005fdialchart_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                         
/* 3646 */                                         _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                         
/* 3648 */                                         _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                         
/* 3650 */                                         _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                         
/* 3652 */                                         _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                         
/* 3654 */                                         _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                         
/* 3656 */                                         _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                         
/* 3658 */                                         _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                         
/* 3660 */                                         _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                         
/* 3662 */                                         _jspx_th_awolf_005fdialchart_005f1.setResourceId((String)request.getAttribute("resourceid"));
/*      */                                         
/* 3664 */                                         _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 3665 */                                         int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 3666 */                                         if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 3667 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 3668 */                                             out = _jspx_page_context.pushBody();
/* 3669 */                                             _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 3670 */                                             _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3673 */                                             out.write(" \n      \t\t        ");
/* 3674 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 3675 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3678 */                                           if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 3679 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3682 */                                         if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 3683 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                         }
/*      */                                         
/* 3686 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 3687 */                                         out.write("\n            </td>\n");
/*      */                                       } else {
/* 3689 */                                         out.write("\n<tr>\n\t<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 3690 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3691 */                                         out.write(" >\n\n");
/* 3692 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3693 */                                         out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n      \n  ");
/*      */                                       }
/* 3695 */                                       out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 3696 */                                       if (memvalue != null)
/*      */                                       {
/* 3698 */                                         out.write("\n<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3699 */                                         if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                           return;
/* 3701 */                                         out.write("&attributeid=");
/* 3702 */                                         out.print(memid);
/* 3703 */                                         out.write("&period=-7')\" class='staticlinks'>");
/* 3704 */                                         out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 3705 */                                         out.write(45);
/* 3706 */                                         out.print(memvalue);
/* 3707 */                                         out.write("</a> %\n  ");
/*      */                                       }
/* 3709 */                                       out.write("\n  </td>\n       </tr>\n    </table>");
/* 3710 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3711 */                                       out.write("</td>\n      <td width=\"30%\">");
/* 3712 */                                       out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 3713 */                                       out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">  \n       <tr>\n  ");
/*      */                                       
/* 3715 */                                       String diskvalue = property.getProperty("Disk Utilization");
/* 3716 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/*      */ 
/* 3719 */                                         String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + (String)request.getAttribute("resourceid") + "&attributeid=" + diskid + "&period=0')";
/*      */                                         
/* 3721 */                                         dialGraph.setValue(Long.parseLong(diskvalue));
/* 3722 */                                         out.write("\n   \n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 3723 */                                         out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 3724 */                                         out.write(45);
/* 3725 */                                         out.print(diskvalue);
/* 3726 */                                         out.write("%' >\n");
/*      */                                         
/* 3728 */                                         DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 3729 */                                         _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 3730 */                                         _jspx_th_awolf_005fdialchart_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                         
/* 3732 */                                         _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                         
/* 3734 */                                         _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                         
/* 3736 */                                         _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                         
/* 3738 */                                         _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                         
/* 3740 */                                         _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                         
/* 3742 */                                         _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                         
/* 3744 */                                         _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                         
/* 3746 */                                         _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                         
/* 3748 */                                         _jspx_th_awolf_005fdialchart_005f2.setResourceId((String)request.getAttribute("resourceid"));
/*      */                                         
/* 3750 */                                         _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 3751 */                                         int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 3752 */                                         if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 3753 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 3754 */                                             out = _jspx_page_context.pushBody();
/* 3755 */                                             _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 3756 */                                             _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3759 */                                             out.write(" \n            ");
/* 3760 */                                             int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 3761 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3764 */                                           if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 3765 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3768 */                                         if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 3769 */                                           this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                         }
/*      */                                         
/* 3772 */                                         this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 3773 */                                         out.write(" </td>\n            ");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3777 */                                         out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 3778 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3779 */                                         out.write(32);
/* 3780 */                                         out.write(62);
/* 3781 */                                         out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 3782 */                                         out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n      \n  ");
/*      */                                       }
/* 3784 */                                       out.write(" \n  </tr>\n  <tr>\n \n  \n  \n  <td align='center' class='bodytextbold'>\n");
/* 3785 */                                       if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                       {
/*      */ 
/* 3788 */                                         out.write("\n     <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3789 */                                         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                           return;
/* 3791 */                                         out.write("&attributeid=");
/* 3792 */                                         out.print(diskid);
/* 3793 */                                         out.write("&period=-7')\" class='staticlinks'>");
/* 3794 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 3795 */                                         out.write(45);
/* 3796 */                                         out.print(diskvalue);
/* 3797 */                                         out.write("</a> % \n     ");
/*      */                                       }
/* 3799 */                                       out.write("\n  </td>\n  </tr>\n</table>");
/* 3800 */                                       out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 3801 */                                       out.write("</td></tr></table>\n \n");
/*      */                                     }
/*      */                                     
/*      */                                   }
/*      */                                 }
/*      */                                 catch (Exception e)
/*      */                                 {
/* 3808 */                                   e.printStackTrace();
/*      */                                 }
/* 3810 */                                 out.write(10);
/* 3811 */                                 out.write("\n\n\t\t<!-- ######## Display CPU and Memory and Disk Utilization dial char - end ######## -->\n\n\n\t\t<!--########  Display the Memory and CPU Utilization - start ######## -->\n");
/*      */                                 
/* 3813 */                                 if (showdisk)
/*      */                                 {
/*      */ 
/* 3816 */                                   out.write("\n\t\t<br>\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t\t  <tr>\n\t\t  <td width=\"100%\" height=\"29\" class=\"tableheadingtrans\">&nbsp;");
/* 3817 */                                   out.print(FormatUtil.getString("am.webclient.server.cpu.mem.text"));
/* 3818 */                                   out.write("<a name=\"Memory Utilization\"></a> </td>\n\t\t  </tr>\n\t\t</table>\n\n\n\t\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrbborder\" height=\"200\">\n\t\t\t<tr>\n\t  \t\t\t<td valign=\"top\" colspan=\"2\">\n");
/*      */                                   
/* 3820 */                                   if (showmem)
/*      */                                   {
/* 3822 */                                     memgraph.setresourceName(resourcename);
/* 3823 */                                     memgraph.setCategory("Memory Utilization");
/* 3824 */                                     memgraph.setResourceId(resourceid);
/* 3825 */                                     Hashtable memdata = memgraph.getMemoryData(resourcename, maxcollectiontime, resourceid);
/* 3826 */                                     String attributeid = (String)ids.get("Swap Memory Utilization");
/* 3827 */                                     if (ids.get("Physical Memory Utilization") != null)
/*      */                                     {
/* 3829 */                                       attributeid = attributeid + "," + (String)ids.get("Physical Memory Utilization");
/* 3830 */                                       if ((mode != null) && (mode.equals("WMI")))
/*      */                                       {
/* 3832 */                                         attributeid = attributeid + "," + (String)ids.get("Free Physical Memory (MB)");
/*      */                                       }
/*      */                                     }
/* 3835 */                                     String aid = null;
/* 3836 */                                     if (resType.equals("SUN"))
/*      */                                     {
/* 3838 */                                       if ((mode != null) && (mode.equals("SNMP")))
/*      */                                       {
/* 3840 */                                         aid = (String)ids.get("Physical Memory Utilization");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3844 */                                         aid = (String)ids.get("Swap Memory Utilization");
/*      */                                       }
/*      */                                       
/*      */                                     }
/*      */                                     else {
/* 3849 */                                       aid = (String)ids.get("Physical Memory Utilization");
/*      */                                     }
/*      */                                     
/* 3852 */                                     out.write("\n\n          \t\t\t<table width=\"77%\" border=\"0\" cellpadding=\"0\" align=\"center\" cellspacing=\"0\" >\n            \t\t\t<tr>\n\t\t\t \t\t\t\t<td>&nbsp;</td>\n      \t  \t\t\t\t</tr>\n      \t  \t\t\t\t<tr>\n      \t  \t\t \t\t\t<td>\n\t\t\t      \t       ");
/*      */                                     
/* 3854 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3855 */                                     _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3856 */                                     _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                     
/* 3858 */                                     _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("memgraph");
/*      */                                     
/* 3860 */                                     _jspx_th_awolf_005ftimechart_005f1.setWidth("600");
/*      */                                     
/* 3862 */                                     _jspx_th_awolf_005ftimechart_005f1.setHeight("230");
/*      */                                     
/* 3864 */                                     _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                     
/* 3866 */                                     _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 3868 */                                     _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/* 3869 */                                     int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3870 */                                     if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3871 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3872 */                                         out = _jspx_page_context.pushBody();
/* 3873 */                                         _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3874 */                                         _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3877 */                                         out.write("\n\t\t\t               ");
/* 3878 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3879 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3882 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3883 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3886 */                                     if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3887 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                     }
/*      */                                     
/* 3890 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3891 */                                     out.write("\n      \t  \t \t\t\t\t</td>\n      \t  \t\t\t\t</tr>\n          \t\t\t</table>\n      \t\t\t</td>\n\t  \t\t</tr>\n\t  \t\t<tr>\n\t    \t\t<td class=\"bottomborder\" colspan=\"2\" >&nbsp;</td>\n\t  \t\t</tr>\n   \t \t\t<tr>\n");
/*      */                                     
/* 3893 */                                     int i = 0;
/*      */                                     
/* 3895 */                                     out.write("\n\t\t      <td valign=\"top\" width=\"50%\" align=\"center\">\n\t\t       <table  border=0 cellspacing=0 cellpadding=0 align=\"center\" width=\"100%\" class=\"rborder\">\n\t\t            <tr>\n\t\t\t           <td class=\"columnheadingnotop\">");
/* 3896 */                                     if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 3898 */                                     out.write("</td>");
/* 3899 */                                     out.write("\n\t\t\t\t       <td class=\"columnheadingnotop\">&nbsp; %</td>");
/* 3900 */                                     out.write("\n\t\t\t\t       <td class=\"columnheadingnotop\">&nbsp;</td>\n\t\t\t\t       <td class=\"columnheadingnotop\">&nbsp;");
/* 3901 */                                     out.print(FormatUtil.getString("MB"));
/* 3902 */                                     out.write("</td>\n\t\t\t\t       <td class=\"columnheadingnotop\">");
/* 3903 */                                     if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 3905 */                                     out.write("</td>");
/* 3906 */                                     out.write("\n\t\t\t       </tr>\n");
/*      */                                     
/* 3908 */                                     if (memdata.isEmpty())
/*      */                                     {
/*      */ 
/* 3911 */                                       out.write("\n\t\t\t      <tr>\n");
/*      */                                       
/* 3913 */                                       if (resType.equals("AIX"))
/*      */                                       {
/*      */ 
/* 3916 */                                         out.write("\n\n\n\n\t\t\t\t\t      <td class=\"whitegrayborder\" align=\"center\" colspan=\"5\">");
/* 3917 */                                         out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3918 */                                         out.write(46);
/* 3919 */                                         out.write(32);
/* 3920 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.aix.message"));
/* 3921 */                                         out.write(" </td>\n\t\t\t\t\t      ");
/*      */                                         
/* 3923 */                                         Colur = "whitegrayborder";
/*      */                                       }
/*      */                                       
/* 3926 */                                       if ((mode.equals("TELNET")) || (mode.equals("SSH"))) {
/* 3927 */                                         if (resType.equals("SUN"))
/*      */                                         {
/* 3929 */                                           out.write("\n\t\t\t\t\t      <td class=\"whitegrayborder\" align=\"center\" colspan=\"5\">");
/* 3930 */                                           out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3931 */                                           out.write(46);
/* 3932 */                                           out.write(32);
/* 3933 */                                           out.print(FormatUtil.getString("am.webclient.memsun.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 3934 */                                           out.write(" </td>\n\t\t\t\t\t      ");
/*      */                                           
/* 3936 */                                           Colur = "whitegrayborder";
/*      */ 
/*      */ 
/*      */                                         }
/* 3940 */                                         else if (resType.equals("HP-UX"))
/*      */                                         {
/*      */ 
/*      */ 
/* 3944 */                                           out.write("\n\n\t\t\t\t      \t\t<td class=\"whitegrayborder\" align=\"center\" colspan=\"5\">");
/* 3945 */                                           out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3946 */                                           out.write(46);
/* 3947 */                                           out.write(32);
/* 3948 */                                           out.print(FormatUtil.getString("am.webclient.memhp.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 3949 */                                           out.write("\n\t\t\t\t      \t\t</td>\n\t\t\t\t      ");
/*      */                                           
/* 3951 */                                           Colur = "whitegrayborder";
/*      */ 
/*      */ 
/*      */                                         }
/* 3955 */                                         else if (!resType.equals("AIX")) {
/* 3956 */                                           out.write("\n\n\n\t\t     \t\t\t \t<td class=\"whitegrayborder\" align=\"center\" colspan=\"5\">");
/* 3957 */                                           out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3958 */                                           out.write(46);
/* 3959 */                                           out.write(32);
/* 3960 */                                           out.print(FormatUtil.getString("am.webclient.diskcli.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 3961 */                                           out.write("\n\t\t\t      \t\t\t</td>");
/*      */                                           
/* 3963 */                                           Colur = "whitegrayborder";
/*      */                                         }
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 3968 */                                         out.write("\n\n\n\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" colspan=\"5\">");
/* 3969 */                                         out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 3970 */                                         out.write(46);
/* 3971 */                                         out.write(32);
/* 3972 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.snmp.message", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 3973 */                                         out.write(" </td>\n\t\t\t\t");
/*      */                                         
/* 3975 */                                         Colur = "whitegrayborder";
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3982 */                                       out.write("\n            \t\t</tr>\n            ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3986 */                                       i = 0;
/* 3987 */                                       for (Enumeration e = memdata.keys(); e.hasMoreElements();)
/*      */                                       {
/* 3989 */                                         String entity = (String)e.nextElement();
/* 3990 */                                         Properties memprops = (Properties)memdata.get(entity);
/* 3991 */                                         memprops1 = (Properties)memdata.get(entity);
/* 3992 */                                         if (i % 2 == 0)
/*      */                                         {
/* 3994 */                                           Colur = "whitegrayborder";
/*      */                                         }
/*      */                                         else {
/* 3997 */                                           Colur = "yellowgrayborder";
/*      */                                         }
/* 3999 */                                         i++;
/* 4000 */                                         String attrId = "702";
/*      */                                         
/* 4002 */                                         int memStatus = -1;
/*      */                                         String entitytoshow;
/* 4004 */                                         if (entity.equals("SwapMemUtilization"))
/*      */                                         {
/* 4006 */                                           attrId = (String)ids.get("Swap Memory Utilization");
/* 4007 */                                           String entitytoshow = FormatUtil.getString("Swap Memory Utilization");
/* 4008 */                                           if (alert.getProperty("Swap Memory Utilization") != null) {
/* 4009 */                                             memStatus = Integer.parseInt(alert.getProperty("Swap Memory Utilization"));
/*      */                                           }
/*      */                                         }
/* 4012 */                                         else if (entity.equals("VirtualMemUtilization"))
/*      */                                         {
/* 4014 */                                           attrId = (String)ids.get("Swap Memory Utilization");
/* 4015 */                                           String entitytoshow = FormatUtil.getString("Swap Memory Utilization");
/* 4016 */                                           if (alert.getProperty("Swap Memory Utilization") != null)
/*      */                                           {
/* 4018 */                                             memStatus = Integer.parseInt(alert.getProperty("Swap Memory Utilization"));
/*      */                                           }
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4023 */                                           attrId = (String)ids.get("Physical Memory Utilization");
/* 4024 */                                           entitytoshow = FormatUtil.getString("Physical Memory Utilization");
/* 4025 */                                           if (alert.getProperty("Physical Memory Utilization") != null)
/*      */                                           {
/* 4027 */                                             memStatus = Integer.parseInt(alert.getProperty("Physical Memory Utilization"));
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 4031 */                                         out.write("\n\t\t            <tr>\n\t\t              <td class=\"");
/* 4032 */                                         out.print(Colur);
/* 4033 */                                         out.write(34);
/* 4034 */                                         out.write(32);
/* 4035 */                                         out.write(62);
/* 4036 */                                         out.print(entitytoshow);
/* 4037 */                                         out.write("</td>\n\t\t\t\t\t  <td class=\"");
/* 4038 */                                         out.print(Colur);
/* 4039 */                                         out.write("\">\n\t\t\t\t\t  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4040 */                                         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                           return;
/* 4042 */                                         out.write("&attributeid=");
/* 4043 */                                         out.print(attrId);
/* 4044 */                                         out.write("&period=20&businessPeriod=oni')\" class=\"staticlinks\">\n\t\t\t\t\t  <span>");
/* 4045 */                                         out.print(memprops.getProperty("CURVALUE"));
/* 4046 */                                         out.write("</span></a>\n\t\t\t\t\t  </td>\n\t\t\t\t\t  <td class=\"");
/* 4047 */                                         out.print(Colur);
/* 4048 */                                         out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4049 */                                         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                           return;
/* 4051 */                                         out.write("&attributeid=");
/* 4052 */                                         out.print(attrId);
/* 4053 */                                         out.write("&period=-7')\">\n\t\t\t\t\t  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4054 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4055 */                                         out.write("\"></a></td>\n\n\t\t              <td class=\"");
/* 4056 */                                         out.print(Colur);
/* 4057 */                                         out.write(34);
/* 4058 */                                         out.write(32);
/* 4059 */                                         out.write(62);
/* 4060 */                                         out.print(formatNumber(memprops.getProperty("CURVALUEMB")));
/* 4061 */                                         out.write("&nbsp;</td>\n\t\t              <td class=\"");
/* 4062 */                                         out.print(Colur);
/* 4063 */                                         out.write("\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4064 */                                         out.print(resourceid);
/* 4065 */                                         out.write("&attributeid=");
/* 4066 */                                         out.print(attrId);
/* 4067 */                                         out.write("&alertconfigurl=");
/* 4068 */                                         out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + attributeid + "&attributeToSelect=" + aid + "&redirectto=" + encodeurl));
/* 4069 */                                         out.write("')\">");
/* 4070 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + attrId)));
/* 4071 */                                         out.write("</a></td>\n\t\t            </tr>\n");
/*      */                                         
/* 4073 */                                         if (memprops.getProperty("FREEPHYMEMMB") != null) {
/* 4074 */                                           attrId = (String)ids.get("Free Physical Memory (MB)");
/*      */                                           
/* 4076 */                                           out.write("\n\t\t\t     <tr>\n\t\t\t\t    <td class=\"whitegrayborder\">");
/* 4077 */                                           out.print(FormatUtil.getString("Free Physical Memory (MB)"));
/* 4078 */                                           out.write("</td>\n\t\t\t\t    <td class=\"whitegrayborder\">&nbsp;</td>\n\t\t\t\t    <td class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4079 */                                           if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 4081 */                                           out.write("&attributeid=");
/* 4082 */                                           out.print(attrId);
/* 4083 */                                           out.write("&period=-7')\">\n\t\t\t\t\t  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4084 */                                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4085 */                                           out.write("\"></a></td>\n\t\t\t\t    <td class=\"whitegrayborder\">\n\t\t\t\t    <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4086 */                                           if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                             return;
/* 4088 */                                           out.write("&attributeid=");
/* 4089 */                                           out.print(attrId);
/* 4090 */                                           out.write("&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 4091 */                                           out.print(memprops.getProperty("FREEPHYMEMMB"));
/* 4092 */                                           out.write("</span>\n\t\t\t\t    </td>\n\t\t\t\t    <td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4093 */                                           out.print(resourceid);
/* 4094 */                                           out.write("&attributeid=");
/* 4095 */                                           out.print(attrId);
/* 4096 */                                           out.write("&alertconfigurl=");
/* 4097 */                                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + attributeid + "&attributeToSelect=" + aid + "&redirectto=" + encodeurl));
/* 4098 */                                           out.write("')\">");
/* 4099 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + attrId)));
/* 4100 */                                           out.write("</a></td>\n\t\t\t     </tr>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4104 */                                         out.write(10);
/*      */                                       }
/*      */                                       
/*      */ 
/* 4108 */                                       out.write(10);
/*      */                                       
/* 4110 */                                       if ((Colur != null) && (Colur.equals("yellowgrayborder"))) {
/* 4111 */                                         Colur = "whitegrayborder";
/*      */                                       } else {
/* 4113 */                                         Colur = "yellowgrayborder";
/*      */                                       }
/* 4115 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 4118 */                                         out.write("\n\n<td colspan=\"5\" height=\"21\" class=\"\"  align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" vspace=\"6\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4119 */                                         out.print(resourceid);
/* 4120 */                                         out.write("&attributeIDs=");
/* 4121 */                                         out.print(attributeid);
/* 4122 */                                         out.write("&attributeToSelect=");
/* 4123 */                                         out.print(aid);
/* 4124 */                                         out.write("&redirectto=");
/* 4125 */                                         out.print(encodeurl);
/* 4126 */                                         out.write("\" class=\"staticlinks\">");
/* 4127 */                                         out.print(ALERTCONFIG_TEXT);
/* 4128 */                                         out.write("</a> &nbsp;\n");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 4133 */                                     out.write("</td>\n</table>\n</td>\n");
/* 4134 */                                     if ((resType.equals("HP-UX")) || (resType.equals("HP-TRU64")))
/*      */                                     {
/*      */                                       try
/*      */                                       {
/* 4138 */                                         String a = memprops1.getProperty("CURVALUE");
/* 4139 */                                         double a2 = Double.parseDouble(a);
/* 4140 */                                         String b = memprops1.getProperty("CURVALUEMB");
/* 4141 */                                         double b2 = Double.parseDouble(b);
/* 4142 */                                         c = b2 * 1024.0D / a2;
/*      */                                       }
/*      */                                       catch (Exception e)
/*      */                                       {
/* 4146 */                                         c = 0.0D;
/*      */                                       }
/*      */                                     }
/* 4149 */                                     out.write("\n\t\t\t\t\t <!--this is to show the cpu values-->\n\n\t\t\t <td valign=\"top\" width=\"50%\">\n\t\t\t       <table  border=0 cellspacing=0 cellpadding=0 align=\"center\" width=\"100%\" >\n\t\t\t            <tr>\n\t\t\t              <td class=\"columnheadingnotop\" >");
/* 4150 */                                     if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 4152 */                                     out.write("</td>");
/* 4153 */                                     out.write("\n\n\t\t\t              <td class=\"columnheadingnotop\" colspan=\"2\">%</td>");
/* 4154 */                                     out.write("\n\t\t\t                          <!--td class=\"columnheadingnotop\">&nbsp;</td>");
/* 4155 */                                     out.write("\n\t\t\t              <td class=\"columnheadingnotop\">&nbsp;");
/*      */                                     
/* 4157 */                                     out.write("</td-->\n\t\t\t              <td class=\"columnheadingnotop\" align=\"center\">");
/* 4158 */                                     if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 4160 */                                     out.write("</td>");
/* 4161 */                                     out.write("\n\t\t\t            </tr>\n\n\n\t    ");
/*      */                                     
/* 4163 */                                     if (cpuprops.getProperty("CURVALUE") != null)
/*      */                                     {
/* 4165 */                                       if ((Colur != null) && (Colur.equals("whitegrayborder")))
/*      */                                       {
/* 4167 */                                         Colur = "yellowgrayborder";
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4171 */                                         Colur = "whitegrayborder";
/*      */                                       }
/*      */                                       
/* 4174 */                                       out.write("\n\n<tr>\n              <td class=\"whitegrayborder\" >");
/* 4175 */                                       out.print(FormatUtil.getString("am.webclient.hometab.highcpuservers.columnheader.cpuutil"));
/* 4176 */                                       out.write("</td>\n\t\t\t  <td class=\"whitegrayborder\" width=\"15%\" >\n\t\t\t  <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4177 */                                       if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 4179 */                                       out.write("&attributeid=");
/* 4180 */                                       out.print((String)ids.get("CPU Utilization"));
/* 4181 */                                       out.write("&period=20&businessPeriod=oni')\" class=\"staticlinks\">\n\t\t\t  <span>");
/* 4182 */                                       out.print(formatNumber(cpuprops.getProperty("CURVALUE")));
/* 4183 */                                       out.write("</span></a>\n\t\t\t  </td>\n\t\t\t  <td class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4184 */                                       if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 4186 */                                       out.write("&attributeid=");
/* 4187 */                                       out.print((String)ids.get("CPU Utilization"));
/* 4188 */                                       out.write("&period=-7')\">\n\t\t\t  <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 4189 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4190 */                                       out.write("\"></a></td>\n\n              <!--td class=\"whitegrayborder\" >");
/*      */                                       
/* 4192 */                                       out.write("&nbsp;</td-->");
/* 4193 */                                       out.write("\n              <td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4194 */                                       out.print(resourceid);
/* 4195 */                                       out.write("&attributeid=");
/* 4196 */                                       out.print((String)ids.get("CPU Utilization"));
/* 4197 */                                       out.write("')\">");
/* 4198 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + (String)ids.get("CPU Utilization"))));
/* 4199 */                                       out.write("</a></td>\n            </tr>\n\n\n");
/*      */                                       
/* 4201 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/* 4203 */                                         if (i == 1)
/*      */                                         {
/* 4205 */                                           out.write("\n<td colspan=\"5\" height=\"21\" class=\"\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" vspace=\"7\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4206 */                                           out.print(resourceid);
/* 4207 */                                           out.write("&attributeIDs=");
/* 4208 */                                           out.print((String)ids.get("CPU Utilization"));
/* 4209 */                                           out.write("&attributeToSelect=");
/* 4210 */                                           out.print((String)ids.get("CPU Utilization"));
/* 4211 */                                           out.write("&redirectto=");
/* 4212 */                                           out.print(encodeurl);
/* 4213 */                                           out.write("\" class=\"staticlinks\">");
/* 4214 */                                           out.print(ALERTCONFIG_TEXT);
/* 4215 */                                           out.write("</a> &nbsp;</td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4219 */                                           out.write("\n<td colspan=\"5\" height=\"21\" class=\"bodytext\" align=\"right\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" vspace=\"7\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4220 */                                           out.print(resourceid);
/* 4221 */                                           out.write("&attributeIDs=");
/* 4222 */                                           out.print((String)ids.get("CPU Utilization"));
/* 4223 */                                           out.write("&attributeToSelect=");
/* 4224 */                                           out.print((String)ids.get("CPU Utilization"));
/* 4225 */                                           out.write("&redirectto=");
/* 4226 */                                           out.print(encodeurl);
/* 4227 */                                           out.write("\" class=\"staticlinks\">");
/* 4228 */                                           out.print(ALERTCONFIG_TEXT);
/* 4229 */                                           out.write("</a> &nbsp;</td>\n");
/*      */                                         }
/*      */                                       }
/*      */                                       
/*      */ 
/* 4234 */                                       out.write("\n</table>\n</td>\n");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4239 */                                       out.write("\n\n\n             <td class=\"whitegrayborder\" colspan=5 align=\"center\">");
/* 4240 */                                       out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4241 */                                       out.write(46);
/* 4242 */                                       out.write(32);
/* 4243 */                                       out.print(FormatUtil.getString("am.webclient.cpu.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 4244 */                                       out.write("\n</td></tr>\n</table>\n\n");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4248 */                                     out.write("\n</tr>\n</table>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4252 */                                   out.write("\n\n  <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"100%\">&nbsp;</td>\n        </tr>\n </table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4256 */                                 out.write("\n\t\t<!--######## Display the Memory and CPU Utilization - end ######## -->\n\n\t\t<!--######## Display the Break up of CPU Utilization - start ########-->\n");
/*      */                                 
/* 4258 */                                 boolean isWMIMode = (mode != null) && (mode.equalsIgnoreCase("wmi"));
/* 4259 */                                 boolean isSNMPMode = (mode != null) && (mode.equalsIgnoreCase("snmp"));
/* 4260 */                                 boolean isWindowsType = (resType != null) && (resType.toLowerCase().indexOf("windows") != -1);
/* 4261 */                                 boolean showBrkCpuUtil = true;
/* 4262 */                                 if ((isWindowsType) && (isSNMPMode)) {
/* 4263 */                                   showBrkCpuUtil = false;
/*      */                                 }
/* 4265 */                                 if ((cpudata) && (showBrkCpuUtil))
/*      */                                 {
/* 4267 */                                   out.write(10);
/* 4268 */                                   HostResourceGraph hostCPUGraph = null;
/* 4269 */                                   hostCPUGraph = (HostResourceGraph)_jspx_page_context.getAttribute("hostCPUGraph", 2);
/* 4270 */                                   if (hostCPUGraph == null) {
/* 4271 */                                     hostCPUGraph = new HostResourceGraph();
/* 4272 */                                     _jspx_page_context.setAttribute("hostCPUGraph", hostCPUGraph, 2);
/*      */                                   }
/* 4274 */                                   out.write(10);
/*      */                                   
/* 4276 */                                   String attrbIds = "";
/*      */                                   
/* 4278 */                                   String[] cpuAttrbsId = { (String)ids.get("Run Queue"), (String)ids.get("Blocked Process"), (String)ids.get("User Time"), (String)ids.get("System Time"), (String)ids.get("I/O Wait Time"), (String)ids.get("Idle Time"), (String)ids.get("CPU Utilization"), (String)ids.get("InterruptsPerSec") };
/* 4279 */                                   String healthAttrbId = (String)ids.get("Health");
/* 4280 */                                   String thresholdRedirect = "/HostResource.do?resourceid=" + request.getParameter("resourceid") + "&haid=null&name=" + request.getParameter("resourcename");
/* 4281 */                                   thresholdRedirect = URLEncoder.encode(thresholdRedirect);
/*      */                                   
/* 4283 */                                   hostCPUGraph.setEntity("cpu");
/* 4284 */                                   hostCPUGraph.setResourceId(resourceid);
/* 4285 */                                   HashMap polledData = hostCPUGraph.getCPUPolledData();
/* 4286 */                                   hostCPUGraph.setCpuUsagePolledData(polledData);
/*      */                                   
/* 4288 */                                   out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t  \t<tr>\n\t    \t<td colspan=\"2\" width=\"48%\" height=\"29\" class=\"tableheading\">&nbsp;");
/* 4289 */                                   out.print(FormatUtil.getString("am.webclient.server.cpucore.usage.individual.popup"));
/* 4290 */                                   out.write("<a name=\"System Load\"></a> </td>\n\t  \t</tr>\n\t</table>\n\t");
/*      */                                   
/*      */ 
/* 4293 */                                   HashMap attrbsValsMap = (HashMap)polledData.get(resourceid);
/* 4294 */                                   if ((attrbsValsMap != null) && (attrbsValsMap.size() > 0))
/*      */                                   {
/* 4296 */                                     out.write("\n\t\t<table  width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">\n        <tr>\n          <td align=\"center\" width=\"48%\" height=\"60\">\n\t\t\t");
/* 4297 */                                     if (_jspx_meth_awolf_005ftimechart_005f2(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                       return;
/* 4299 */                                     out.write(32);
/* 4300 */                                     out.write("\n          </td>\n        </tr>\n        <tr>\n\t\t\t<td width=\"52%\" valign=\"top\"> <br>\n\t\t\t\t<table  border=\"0\" cellspacing=0 cellpadding=0 align=\"center\" width=\"77%\" class=\"lrtbdarkborder\">\n              \t\t<tr>\n\t\t                <td class=\"columnheadingnotop\">");
/* 4301 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.parname"));
/* 4302 */                                     out.write("</td>\n\t\t                <td class=\"columnheadingnotop\">");
/* 4303 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.currentvalue"));
/* 4304 */                                     out.write("</td>\n\t\t                <td class=\"columnheadingnotop\" colspan=\"2\">");
/* 4305 */                                     out.print(FormatUtil.getString("webclient.fault.alarm.severity"));
/* 4306 */                                     out.write("</td>\n\t\t\t\t\t</tr>\n\t");
/*      */                                     
/*      */ 
/* 4309 */                                     HashMap usageVals = (HashMap)attrbsValsMap.get(hostCPUGraph.cpuAttributes[2]);
/* 4310 */                                     TreeMap sortedMap = new TreeMap(java.util.Collections.reverseOrder());
/* 4311 */                                     sortedMap.putAll(usageVals);
/* 4312 */                                     Long latestPolledTime = (Long)sortedMap.firstKey();
/*      */                                     
/* 4314 */                                     for (int j = 0; j < hostCPUGraph.cpuAttributes.length; j++)
/*      */                                     {
/* 4316 */                                       if (j != 6)
/*      */                                       {
/*      */ 
/*      */ 
/* 4320 */                                         if ((!isSNMPMode) || ((j != 0) && (j != 1)))
/*      */                                         {
/*      */ 
/* 4323 */                                           if ((!isWMIMode) || (j != 1))
/*      */                                           {
/*      */ 
/*      */ 
/* 4327 */                                             if ((j != 4) || ((!resType.equals("HP-UX")) && (!resType.equals("HP-TRU64"))))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/* 4332 */                                               HashMap data = (HashMap)attrbsValsMap.get(hostCPUGraph.cpuAttributes[j]);
/* 4333 */                                               if (data != null)
/*      */                                               {
/*      */ 
/* 4336 */                                                 int value = ((Integer)data.get(latestPolledTime)).intValue();
/* 4337 */                                                 boolean isRepoEnabled = (repoEnabledAttribs != null) && (repoEnabledAttribs.contains(cpuAttrbsId[j]));
/* 4338 */                                                 String title = "";
/* 4339 */                                                 if (j == 0) {
/* 4340 */                                                   title = "am.webclient.server.cpu.usage.runqueue";
/* 4341 */                                                 } else if (j == 1) {
/* 4342 */                                                   title = "am.webclient.server.cpu.usage.blockedprocs";
/* 4343 */                                                 } else if (j == 2) {
/* 4344 */                                                   title = "am.webclient.server.cpu.usage.user.percent";
/* 4345 */                                                 } else if (j == 3) {
/* 4346 */                                                   title = "am.webclient.server.cpu.usage.system.percent";
/* 4347 */                                                 } else if (j == 4) {
/* 4348 */                                                   title = "am.webclient.server.cpu.usage.wait.percent";
/* 4349 */                                                 } else if (j == 5) {
/* 4350 */                                                   title = "am.webclient.server.cpu.usage.idle.percent";
/* 4351 */                                                 } else if (j == 6) {
/* 4352 */                                                   title = "am.webclient.server.cpucore.usage.coreutil.percent";
/* 4353 */                                                 } else if (j == 7) {
/* 4354 */                                                   title = "am.webclient.server.cpu.intr.per.sec";
/*      */                                                 }
/* 4356 */                                                 attrbIds = attrbIds + cpuAttrbsId[j] + ",";
/*      */                                                 
/*      */ 
/* 4359 */                                                 out.write("\n    \t\t\t\t\t<tr>\n\t    \t\t\t\t\t<td class=\"whitegrayborder\" align=\"left\" style=\"height:26px\">");
/* 4360 */                                                 out.print(FormatUtil.getString(title));
/* 4361 */                                                 out.write("</td>\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"left\">\n\t");
/*      */                                                 
/* 4363 */                                                 if (value == -1)
/*      */                                                 {
/* 4365 */                                                   out.write("\n\t\t\t\t\t\t\t<span>-</span>\n\t");
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 4369 */                                                   out.write("\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4370 */                                                   out.print(resourceid);
/* 4371 */                                                   out.write("&attributeid=");
/* 4372 */                                                   out.print(cpuAttrbsId[j]);
/* 4373 */                                                   out.write("&period=20&businessPeriod=oni')\" class=\"staticlinks\">\n\t\t\t\t\t\t\t<span>");
/* 4374 */                                                   out.print(value);
/* 4375 */                                                   out.write("</span></a>\n\t");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 4379 */                                                 out.write("\n\t\t\t\t\t\t\t</td>\n\t");
/*      */                                                 
/* 4381 */                                                 if (isRepoEnabled)
/*      */                                                 {
/* 4383 */                                                   out.write("\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"left\">\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4384 */                                                   out.print(resourceid);
/* 4385 */                                                   out.write("&attributeid=");
/* 4386 */                                                   out.print(cpuAttrbsId[j]);
/* 4387 */                                                   out.write("&alertconfigurl=");
/* 4388 */                                                   out.print(thresholdRedirect);
/* 4389 */                                                   out.write("')\">");
/* 4390 */                                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + cpuAttrbsId[j])));
/* 4391 */                                                   out.write("</a></td>\n\t   \t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4392 */                                                   out.print(resourceid);
/* 4393 */                                                   out.write("&attributeid=");
/* 4394 */                                                   out.print(cpuAttrbsId[j]);
/* 4395 */                                                   out.write("&period=-7')\">\n   \t\t\t\t\t\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4396 */                                                   out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4397 */                                                   out.write("\"></a></td>\n\t");
/*      */                                                 }
/*      */                                                 else
/*      */                                                 {
/* 4401 */                                                   out.write("\n\t\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"left\" colspan=\"2\">\n\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4402 */                                                   out.print(resourceid);
/* 4403 */                                                   out.write("&attributeid=");
/* 4404 */                                                   out.print(cpuAttrbsId[j]);
/* 4405 */                                                   out.write("&alertconfigurl=");
/* 4406 */                                                   out.print(thresholdRedirect);
/* 4407 */                                                   out.write("')\">");
/* 4408 */                                                   out.print(getSeverityImage(alert.getProperty(resourceid + "#" + cpuAttrbsId[j])));
/* 4409 */                                                   out.write("</a></td>\n\t");
/*      */                                                 }
/*      */                                                 
/*      */ 
/* 4413 */                                                 out.write("\n   \t\t\t\t\t\t</tr>\n    ");
/*      */                                               }
/*      */                                             } } } } }
/* 4416 */                                     if (attrbIds.length() > 0) {
/* 4417 */                                       attrbIds = attrbIds.substring(0, attrbIds.length() - 1);
/*      */                                     }
/*      */                                     
/* 4420 */                                     out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"tablebottom1\" align=\"right\" colspan=\"4\"><img src=\"/images/icon_associateaction.gif\" border=\"0\" title=\"\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4421 */                                     out.print(resourceid);
/* 4422 */                                     out.write("&attributeIDs=");
/* 4423 */                                     out.print(attrbIds);
/* 4424 */                                     out.write("&attributeToSelect=");
/* 4425 */                                     out.print(cpuAttrbsId[2]);
/* 4426 */                                     out.write("&redirectto=");
/* 4427 */                                     out.print(thresholdRedirect);
/* 4428 */                                     out.write("\" class=\"staticlinks\" title=\"\">");
/* 4429 */                                     out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 4430 */                                     out.write("</a>&nbsp;\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4434 */                                     out.write("\n\t<table  width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td class=\"bodytext\" colspan=5 align=\"center\">");
/* 4435 */                                     out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4436 */                                     out.write(46);
/* 4437 */                                     out.write(32);
/* 4438 */                                     out.print(FormatUtil.getString("am.webclient.cpu.text", new String[] { OEMUtil.getOEMString("company.troubleshoot.link") }));
/* 4439 */                                     out.write("</td>\n\t\t</tr>\n\t</table>\n");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/* 4444 */                                 out.write("\n\t\t\t<!--######## Display the CPU Utilization - end ########-->\n\n\t\t\t<!--######## Display the System Load - start ########-->\n");
/*      */                                 
/* 4446 */                                 if (showsys)
/*      */                                 {
/* 4448 */                                   sysgraph.setresourceName(resourcename);
/* 4449 */                                   sysgraph.setCategory("System Load");
/* 4450 */                                   sysgraph.setResourceId(resourceid);
/* 4451 */                                   Hashtable sysdata = sysgraph.getSysloadData(resourcename, maxcollectiontime, resourceid);
/*      */                                   
/* 4453 */                                   out.write("\n\t<br>\n    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" width=\"48%\" height=\"29\" class=\"tableheading\">&nbsp;");
/* 4454 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.sysloadlastone"));
/* 4455 */                                   out.write("<a name=\"System Load\"></a> </td>\n        </tr>\n      </table>\n");
/*      */                                   
/* 4457 */                                   sysgraph.setCategory("System Load");
/*      */                                   
/* 4459 */                                   out.write("\n\t  <table  width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">\n");
/*      */                                   
/* 4461 */                                   if (sysdata.isEmpty())
/*      */                                   {
/* 4463 */                                     out.write("\n<tr>\n           <td colspan=\"4\" align=\"center\" class=\"bodytext\">");
/* 4464 */                                     out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4465 */                                     out.write("</td>\n            </tr>\n\n");
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 4469 */                                     out.write("\n\n\n        <tr>\n          <td align=\"center\" width=\"48%\" height=\"60\">\n\n\t");
/*      */                                     
/* 4471 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4472 */                                     _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 4473 */                                     _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                     
/* 4475 */                                     _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("sysgraph");
/*      */                                     
/* 4477 */                                     _jspx_th_awolf_005ftimechart_005f3.setWidth("600");
/*      */                                     
/* 4479 */                                     _jspx_th_awolf_005ftimechart_005f3.setHeight("230");
/*      */                                     
/* 4481 */                                     _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                                     
/* 4483 */                                     _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 4485 */                                     _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.jobnumber.text"));
/* 4486 */                                     int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 4487 */                                     if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 4488 */                                       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 4489 */                                         out = _jspx_page_context.pushBody();
/* 4490 */                                         _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 4491 */                                         _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 4494 */                                         out.write("\n        ");
/* 4495 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 4496 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 4499 */                                       if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 4500 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 4503 */                                     if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 4504 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                     }
/*      */                                     
/* 4507 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 4508 */                                     out.write("</tr>");
/* 4509 */                                     out.write("\n          </td>\n\t\t<tr>\n          <td width=\"52%\" valign=\"top\"> <br><table  border=\"0\" cellspacing=0 cellpadding=0 align=\"center\" width=\"77%\" class=\"lrtbdarkborder\">\n              <tr>\n                <td class=\"columnheadingnotop\">");
/* 4510 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.parname"));
/* 4511 */                                     out.write("</td>\n                <td class=\"columnheadingnotop\">");
/* 4512 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.currentvalue"));
/* 4513 */                                     out.write("</td>\n                <td class=\"columnheadingnotop\">");
/* 4514 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.peakvalue"));
/* 4515 */                                     out.write("</td>\n                <td class=\"columnheadingnotop\">");
/* 4516 */                                     out.print(FormatUtil.getString("webclient.fault.alarm.severity"));
/* 4517 */                                     out.write("</td>\n\t\t<td class=\"columnheadingnotop\">&nbsp;</td>\n              </tr>\n              ");
/*      */                                     
/* 4519 */                                     int i = 0;
/* 4520 */                                     if (sysdata.isEmpty())
/*      */                                     {
/*      */ 
/* 4523 */                                       out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"whitegrayborder\" align=\"center\" colspan=\"4\">");
/* 4524 */                                       out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4525 */                                       out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                     }
/*      */                                     
/* 4528 */                                     for (Enumeration e = sysdata.keys(); e.hasMoreElements();) {
/* 4529 */                                       String atId = "705";
/* 4530 */                                       String entity = (String)e.nextElement();
/* 4531 */                                       Properties sysprops = (Properties)sysdata.get(entity);
/* 4532 */                                       if (i % 2 == 0)
/*      */                                       {
/* 4534 */                                         Colur = "whitegrayborder";
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4538 */                                         Colur = "yellowgrayborder";
/*      */                                       }
/* 4540 */                                       i++;
/* 4541 */                                       int jobsStatus = -1;
/* 4542 */                                       if (entity.equals("jobsPerMin"))
/*      */                                       {
/* 4544 */                                         entity = FormatUtil.getString("Jobs in Minute");
/* 4545 */                                         atId = (String)ids.get("Jobs in Minute");
/* 4546 */                                         if (alert.getProperty(resourceid + "#" + atId) != null)
/*      */                                         {
/* 4548 */                                           jobsStatus = Integer.parseInt(alert.getProperty(resourceid + "#" + atId));
/*      */                                         }
/*      */                                         
/*      */                                       }
/* 4552 */                                       else if (entity.equals("jobsPer5Min"))
/*      */                                       {
/* 4554 */                                         entity = FormatUtil.getString("Jobs in 5 Minutes");
/* 4555 */                                         atId = (String)ids.get("Jobs in 5 Minutes");
/* 4556 */                                         if (alert.getProperty(resourceid + "#" + atId) != null)
/*      */                                         {
/* 4558 */                                           jobsStatus = Integer.parseInt(alert.getProperty(resourceid + "#" + atId));
/*      */                                         }
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4563 */                                         entity = FormatUtil.getString("Jobs in 15 Minutes");
/* 4564 */                                         atId = (String)ids.get("Jobs in 15 Minutes");
/* 4565 */                                         if (alert.getProperty("Jobs in 15 Minutes") != null) {
/* 4566 */                                           jobsStatus = Integer.parseInt(alert.getProperty("Jobs in 15 Minutes"));
/*      */                                         }
/*      */                                       }
/*      */                                       
/*      */ 
/*      */ 
/*      */ 
/* 4573 */                                       out.write("\n               <tr>\n                <td align=left class=\"");
/* 4574 */                                       out.print(Colur);
/* 4575 */                                       out.write(34);
/* 4576 */                                       out.write(62);
/* 4577 */                                       out.print(entity);
/* 4578 */                                       out.write("</td>\n                <td align=left class=\"");
/* 4579 */                                       out.print(Colur);
/* 4580 */                                       out.write("\">\n                <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4581 */                                       out.print(resourceid);
/* 4582 */                                       out.write("&attributeid=");
/* 4583 */                                       out.print(atId);
/* 4584 */                                       out.write("&period=20&businessPeriod=oni')\" class=\"staticlinks\">\n                <span>");
/* 4585 */                                       out.print(formatNumber(sysprops.getProperty("CURVALUE")));
/* 4586 */                                       out.write("</span></a></td>\n                <td align=left class=\"");
/* 4587 */                                       out.print(Colur);
/* 4588 */                                       out.write(34);
/* 4589 */                                       out.write(62);
/* 4590 */                                       out.print(formatNumber(sysprops.getProperty("MAXVALUE")));
/* 4591 */                                       out.write("</td>\n\t\t<td align=left width=\"10\" class=\"");
/* 4592 */                                       out.print(Colur);
/* 4593 */                                       out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4594 */                                       out.print(resourceid);
/* 4595 */                                       out.write("&attributeid=");
/* 4596 */                                       out.print(atId);
/* 4597 */                                       out.write("&alertconfigurl=");
/* 4598 */                                       out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + resourceid + "&attributeIDs=" + (String)ids.get("Jobs in Minute") + "," + (String)ids.get("Jobs in 15 Minutes") + "," + (String)ids.get("Jobs in 5 Minutes") + "&attributeToSelect=" + (String)ids.get("Jobs in Minute") + "&redirectto=" + encodeurl));
/* 4599 */                                       out.write("')\">");
/* 4600 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + atId)));
/* 4601 */                                       out.write("</a></td>\n\t\t<td align=left class=\"");
/* 4602 */                                       out.print(Colur);
/* 4603 */                                       out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4604 */                                       if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/*      */                                         return;
/* 4606 */                                       out.write("&attributeid=");
/* 4607 */                                       out.print(atId);
/* 4608 */                                       out.write("&period=-7')\">\n\t\t<img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 4609 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4610 */                                       out.write("\"></a>\n\n\t\t</td>\n\n              </tr>\n\n             ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4614 */                                     if (Colur.equals("whitegrayborder"))
/*      */                                     {
/* 4616 */                                       Colur = "yellowgrayborder";
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4620 */                                       Colur = "whitegrayborder";
/*      */                                     }
/* 4622 */                                     if (!EnterpriseUtil.isAdminServer())
/*      */                                     {
/*      */ 
/*      */ 
/* 4626 */                                       out.write("\n\t        <tr>\n                <td height=\"20\" colspan=\"6\" align=\"right\" class=\"tablebottom1\">\n\t              <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4627 */                                       out.print(resourceid);
/* 4628 */                                       out.write("&attributeIDs=");
/* 4629 */                                       out.print((String)ids.get("Jobs in Minute"));
/* 4630 */                                       out.write(44);
/* 4631 */                                       out.print((String)ids.get("Jobs in 15 Minutes"));
/* 4632 */                                       out.write(44);
/* 4633 */                                       out.print((String)ids.get("Jobs in 5 Minutes"));
/* 4634 */                                       out.write("&attributeToSelect=");
/* 4635 */                                       out.print((String)ids.get("Jobs in Minute"));
/* 4636 */                                       out.write("&redirectto=");
/* 4637 */                                       out.print(encodeurl);
/* 4638 */                                       out.write("\" class=\"staticlinks\">");
/* 4639 */                                       out.print(ALERTCONFIG_TEXT);
/* 4640 */                                       out.write("</a>&nbsp;\n\t         </td>\n\t         </tr>\n\n\t\t\t ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4644 */                                     out.write("\n\t</table>\n\t    </td>\n\t</tr>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4648 */                                   out.write("\n       </tr>\n      </table>\n\n    <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n      <tr>\n        <td>&nbsp;</td>\n      </tr>\n    </table>\n    ");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 4653 */                                 out.write("\n\t\t\t<!-- ######## Display the System Load - end  ########-->\n\n\n\t\t\t\n\n\t\t\t<!-- ######## Display Page Space details for AIX server - start ########-->\n\t");
/*      */                                 
/* 4655 */                                 String SPercent = null;
/* 4656 */                                 String SValue = null;
/* 4657 */                                 if (showdata.equals("2"))
/*      */                                 {
/* 4659 */                                   if (resType.equals("AIX"))
/*      */                                   {
/* 4661 */                                     int Percent = 0;
/* 4662 */                                     SPercent = Integer.toString(Percent);
/* 4663 */                                     int Value = 0;
/* 4664 */                                     SValue = Integer.toString(Value);
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */                                     try
/*      */                                     {
/* 4670 */                                       SPercent = memprops1.getProperty("CURVALUE");
/* 4671 */                                       SValue = memprops1.getProperty("CURVALUEMB");
/*      */                                     }
/*      */                                     catch (Exception e) {}
/*      */                                   }
/*      */                                   
/*      */ 
/* 4677 */                                   if (resType.equals("AIX"))
/*      */                                   {
/*      */ 
/* 4680 */                                     out.write("\n\t\t<br>\n\t \t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t \t <tr>\n\t \t <td colspan=\"8\" width=\"99%\" height=\"31\" class=\"tableheadingbborder\">");
/* 4681 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.pagespace"));
/* 4682 */                                     out.write(" <a name=\"Page Space\" id=\"Page Space\"></a> </td> </tr>\n\n\t \t <tr>\n\t \t <td width=\"18%\" height=\"28\"  class=\"columnheading\">");
/* 4683 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.pagespace"));
/* 4684 */                                     out.write("</td>\n\t \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4685 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.size"));
/* 4686 */                                     out.write("</td>\n\t \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4687 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.pagespaceused"));
/* 4688 */                                     out.write("</td>\n\t \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4689 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.pagespaceusedMB"));
/* 4690 */                                     out.write("</td>\n\t \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4691 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.pagespacefree"));
/* 4692 */                                     out.write("</td>\n\t \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4693 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.pagespacefreeMB"));
/* 4694 */                                     out.write("</td>\n\t \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4695 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.health"));
/* 4696 */                                     out.write("</td>\n\t  \t <td width=\"12%\" height=\"28\"  class=\"columnheading\">");
/* 4697 */                                     out.print(FormatUtil.getString("table.heading.status"));
/* 4698 */                                     out.write("</td>\n\t  \t </tr>\n\n\t  \t");
/*      */                                     
/*      */                                     try
/*      */                                     {
/* 4702 */                                       String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link") + "#m70";
/* 4703 */                                       sysgraph.setresourceName(resourcename);
/* 4704 */                                       sysgraph.setCategory("Page Space");
/* 4705 */                                       sysgraph.setResourceId(resourceid);
/* 4706 */                                       Hashtable pageSpacedata = sysgraph.getPageSpaceData(resourcename, maxcollectiontime, resourceid);
/* 4707 */                                       int i; Enumeration e; if (!pageSpacedata.isEmpty())
/*      */                                       {
/* 4709 */                                         i = 0;
/* 4710 */                                         for (e = pageSpacedata.keys(); e.hasMoreElements();)
/*      */                                         {
/* 4712 */                                           String resourceName = (String)e.nextElement();
/* 4713 */                                           Properties pageSpaceTable = new Properties();
/* 4714 */                                           pageSpaceTable = (Properties)pageSpacedata.get(resourceName);
/* 4715 */                                           String Colour = "";
/* 4716 */                                           if (i % 2 == 0)
/*      */                                           {
/* 4718 */                                             Colour = "whitegrayborder";
/*      */                                           }
/*      */                                           else {
/* 4721 */                                             Colour = "yellowgrayborder";
/*      */                                           }
/* 4723 */                                           i++;
/* 4724 */                                           String pageSpaceId = resourceName.substring(resourceName.indexOf("-") + 1);
/* 4725 */                                           String size = (pageSpaceTable.getProperty("SIZE") != null) && (!pageSpaceTable.getProperty("SIZE").equals("-1")) ? pageSpaceTable.getProperty("SIZE") : "-";
/* 4726 */                                           String used = (pageSpaceTable.getProperty("USED") != null) && (!pageSpaceTable.getProperty("USED").equals("-1")) ? pageSpaceTable.getProperty("USED") : "-";
/* 4727 */                                           String usedMB = (pageSpaceTable.getProperty("USEDMB") != null) && (!pageSpaceTable.getProperty("USEDMB").equals("-1")) ? pageSpaceTable.getProperty("USEDMB") : "-";
/* 4728 */                                           String free = (pageSpaceTable.getProperty("FREE") != null) && (!pageSpaceTable.getProperty("FREE").equals("-1")) ? pageSpaceTable.getProperty("FREE") : "-";
/* 4729 */                                           String freeMB = (pageSpaceTable.getProperty("FREEMB") != null) && (!pageSpaceTable.getProperty("FREEMB").equals("-1")) ? pageSpaceTable.getProperty("FREEMB") : "-";
/* 4730 */                                           String rid = pageSpaceTable.getProperty("ID") != null ? pageSpaceTable.getProperty("ID") : "-";
/* 4731 */                                           resIDs.add(String.valueOf(rid));
/* 4732 */                                           alert = getStatus(resIDs, attribIDs);
/*      */                                           
/*      */ 
/* 4735 */                                           out.write("\n\t\t\t\t<tr>\n\t\t\t\t <td width=\"12%\" class=\"");
/* 4736 */                                           out.print(Colour);
/* 4737 */                                           out.write(34);
/* 4738 */                                           out.write(62);
/* 4739 */                                           out.print(pageSpaceId);
/* 4740 */                                           out.write("</td>\n\t\t\t\t <td width=\"12%\" class=\"");
/* 4741 */                                           out.print(Colour);
/* 4742 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4743 */                                           out.print(rid);
/* 4744 */                                           out.write("&attributeid=1425&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 4745 */                                           out.print(size);
/* 4746 */                                           out.write("</span></a></td>\n\t\t\t\t <td width=\"12%\" class=\"");
/* 4747 */                                           out.print(Colour);
/* 4748 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4749 */                                           out.print(rid);
/* 4750 */                                           out.write("&attributeid=1426&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 4751 */                                           out.print(used);
/* 4752 */                                           out.write("</span></a></td>\n\t\t\t\t <td width=\"12%\" class=\"");
/* 4753 */                                           out.print(Colour);
/* 4754 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4755 */                                           out.print(rid);
/* 4756 */                                           out.write("&attributeid=1428&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 4757 */                                           out.print(usedMB);
/* 4758 */                                           out.write("</span></a></td>\n\t\t\t\t <td width=\"12%\" class=\"");
/* 4759 */                                           out.print(Colour);
/* 4760 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4761 */                                           out.print(rid);
/* 4762 */                                           out.write("&attributeid=1427&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 4763 */                                           out.print(free);
/* 4764 */                                           out.write("</span></a></td>\n\t \t\t \t <td width=\"12%\" class=\"");
/* 4765 */                                           out.print(Colour);
/* 4766 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4767 */                                           out.print(rid);
/* 4768 */                                           out.write("&attributeid=1429&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span>");
/* 4769 */                                           out.print(freeMB);
/* 4770 */                                           out.write("</span></a></td>\n\t \t\t \t <td class=\"");
/* 4771 */                                           out.print(Colour);
/* 4772 */                                           out.write("\" align=\"center\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4773 */                                           out.print(rid);
/* 4774 */                                           out.write("&attributeid=1420&alertconfigurl=");
/* 4775 */                                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + rid + "&attributeIDs=1420&attributeToSelect=1420&redirectto=" + encodeurl));
/* 4776 */                                           out.write("')\">");
/* 4777 */                                           out.print(getSeverityImage(alert.getProperty(rid + "#" + 1420)));
/* 4778 */                                           out.write("</a>\n\n\t \t\t \t </td>\n\t \t\t \t <td  class=\"");
/* 4779 */                                           out.print(Colour);
/* 4780 */                                           out.write("\" align=\"center\"><a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4781 */                                           out.print(rid);
/* 4782 */                                           out.write("&attributeIDs=1426,1427,1428,1429&attributeToSelect=1426&redirectto=");
/* 4783 */                                           out.print(encodeurl);
/* 4784 */                                           out.write("\" class=\"staticlinks\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\"  border=0></a></td>\n\t \t\t    </tr>\n\t \t          ");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 4792 */                                         out.write("\n\t \t          \t<tr>\n\t \t          \t<td colspan=\"8\" align=\"center\" class=\"whitegrayborder\">");
/* 4793 */                                         out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4794 */                                         out.write(".<a href=\"");
/* 4795 */                                         out.print(troubleshootlink);
/* 4796 */                                         out.write("\" target=\"_blank\"><u>");
/* 4797 */                                         out.print(FormatUtil.getString("am.webclient.dotnet.troubleshoot"));
/* 4798 */                                         out.write("</u></a></td>\t </tr>\n\t \t           ");
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Exception e) {
/* 4802 */                                       e.printStackTrace();
/*      */                                     }
/*      */                                     
/* 4805 */                                     out.write("\n\t \t          <tr>\n\t \t\t\t<td style=\"height:8px\" colspan=\"8\" align=\"right\" class=\"tablebottom1\"><a href=\"#top1\" class=\"staticlinks\">");
/* 4806 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 4807 */                                     out.write("</a>&nbsp;</td>\n\t \t\t </tr>\n\n\t \t\t </table> <br>\n\n\t ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4811 */                                   out.write("\n\t \t\t<!-- ######## Display Page Space details for AIX server - end ########-->\n\n\t \t\t<!-- ######## Display Process details - start ######## -->\n\t <br>\n    <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n\t <tr>\n\t <td width=\"72%\" height=\"31\" class=\"tableheadingtrans\">");
/* 4812 */                                   out.print(FormatUtil.getString("am.webclient.hostResource.servers.processdetail"));
/* 4813 */                                   out.write(" <a name=\"Process\" id=\"Process\"></a></td>\n\t \t");
/*      */                                   
/* 4815 */                                   PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4816 */                                   _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 4817 */                                   _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                   
/* 4819 */                                   _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 4820 */                                   int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 4821 */                                   if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                     for (;;) {
/* 4823 */                                       out.write("\n\t\t<td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:alertUser()\" class=\"bodytextboldwhiteun\">");
/* 4824 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink"));
/* 4825 */                                       out.write("</a>&nbsp;</td>\n\t\t");
/* 4826 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 4827 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4831 */                                   if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 4832 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                   }
/*      */                                   
/* 4835 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 4836 */                                   out.write(10);
/* 4837 */                                   out.write(9);
/* 4838 */                                   out.write(9);
/*      */                                   
/* 4840 */                                   PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4841 */                                   _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 4842 */                                   _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                   
/* 4844 */                                   _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 4845 */                                   int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 4846 */                                   if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                     for (;;) {
/* 4848 */                                       out.write("\n\t\t<td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:void(0)\" class=\"bodytextboldwhiteun\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&resourceid=");
/* 4849 */                                       if (_jspx_meth_c_005fout_005f21(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                                         return;
/* 4851 */                                       out.write("&PercentMB=");
/* 4852 */                                       out.print(SPercent);
/* 4853 */                                       out.write("&MBValue=");
/* 4854 */                                       out.print(SValue);
/* 4855 */                                       out.write("&resType=");
/* 4856 */                                       out.print(resType);
/* 4857 */                                       out.write("')\">");
/* 4858 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink"));
/* 4859 */                                       out.write("</a>&nbsp;</td>\n\t  \t <!-- <td width=\"28%\" height=\"31\" align=\"right\" class=\"tableheading\">&nbsp;<a href=\"javascript:fnAddProcess()\" class=\"bodytextboldwhiteun\">Add\n          New Process</a>&nbsp;</td>-->\n\t\t  ");
/* 4860 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 4861 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4865 */                                   if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 4866 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                   }
/*      */                                   
/* 4869 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 4870 */                                   out.write("\n\t </tr>\n\t </table>\n\t ");
/*      */                                 }
/*      */                                 
/* 4873 */                                 Hashtable globalConfig = (Hashtable)request.getSession().getServletContext().getAttribute("globalconfig");
/* 4874 */                                 String allowManage = (String)globalConfig.get("allowOperatorManage");
/* 4875 */                                 boolean allowManageAction = false;
/* 4876 */                                 if ((allowManage != null) && (allowManage.equalsIgnoreCase("true")))
/*      */                                 {
/* 4878 */                                   allowManageAction = true;
/*      */                                 }
/*      */                                 
/* 4881 */                                 out.write(10);
/* 4882 */                                 out.write(9);
/* 4883 */                                 out.write(32);
/*      */                                 
/* 4885 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4886 */                                 _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 4887 */                                 _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 4889 */                                 _jspx_th_logic_005fnotEmpty_005f2.setName("process");
/* 4890 */                                 int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 4891 */                                 if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                   for (;;) {
/* 4893 */                                     out.write("\n\t <form action=\"/HostResource.do?removeProcess=true\" name=\"removemonitor\" method=\"post\" style=\"display:inline\">\n\t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t ");
/*      */                                     
/* 4895 */                                     List pslist = (ArrayList)request.getAttribute("process");
/* 4896 */                                     if ((pslist != null) && (pslist.size() > 20))
/*      */                                     {
/* 4898 */                                       out.write("\n\t <tr>\n\t <!--<td width=\"10%\" height=\"28\"  class=\"columnheading\">-->\n\t <td colspan=\"13\" align=\"left\" class=\"columnheadingdelete\">\n\t  ");
/*      */                                       
/* 4900 */                                       PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4901 */                                       _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 4902 */                                       _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                       
/* 4904 */                                       _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 4905 */                                       int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 4906 */                                       if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                         for (;;) {
/* 4908 */                                           out.write("\n\t  <a href=\"javascript:alertUser();\" class=\"staticlinks\">");
/* 4909 */                                           out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 4910 */                                           out.write("</a>\n\t ");
/* 4911 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 4912 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4916 */                                       if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 4917 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                                       }
/*      */                                       
/* 4920 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 4921 */                                       out.write(10);
/* 4922 */                                       out.write(9);
/* 4923 */                                       out.write(32);
/*      */                                       
/* 4925 */                                       PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4926 */                                       _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 4927 */                                       _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                       
/* 4929 */                                       _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 4930 */                                       int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 4931 */                                       if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                         for (;;) {
/* 4933 */                                           out.write("\n\t &nbsp;<a href=\"javascript:removeMonitors();\" class=\"staticlinks\">");
/* 4934 */                                           out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 4935 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(true,'process');\" class=\"staticlinks\">");
/* 4936 */                                           out.print(FormatUtil.getString("Manage"));
/* 4937 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(false,'process');\" class=\"staticlinks\">");
/* 4938 */                                           out.print(FormatUtil.getString("UnManage"));
/* 4939 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(false,'process',true);\" class=\"staticlinks\">");
/* 4940 */                                           out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 4941 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t <A HREF=\"javascript:enableReports();\" title=\"");
/* 4942 */                                           out.print(FormatUtil.getString("am.webclient.hostresourceprocess.tooltip.enablemessage"));
/* 4943 */                                           out.write("\" class=\"staticlinks\">");
/* 4944 */                                           out.print(FormatUtil.getString("am.webclient.script.enablereports"));
/* 4945 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<A HREF=\"javascript:disableReports();\" title=\"");
/* 4946 */                                           out.print(FormatUtil.getString("am.webclient.hostresourceprocess.tooltip.disablemessage"));
/* 4947 */                                           out.write("\" class=\"staticlinks\">");
/* 4948 */                                           out.print(FormatUtil.getString("am.webclient.script.disablereports"));
/* 4949 */                                           out.write("</a></td>\n\t ");
/* 4950 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 4951 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4955 */                                       if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 4956 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                       }
/*      */                                       
/* 4959 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 4960 */                                       out.write(10);
/* 4961 */                                       out.write(9);
/* 4962 */                                       out.write(32);
/*      */                                       
/* 4964 */                                       PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4965 */                                       _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 4966 */                                       _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                       
/* 4968 */                                       _jspx_th_logic_005fpresent_005f6.setRole("OPERATOR");
/* 4969 */                                       int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 4970 */                                       if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                         for (;;) {
/* 4972 */                                           out.write(10);
/* 4973 */                                           out.write(9);
/* 4974 */                                           out.write(32);
/*      */                                           
/* 4976 */                                           if (allowManageAction)
/*      */                                           {
/*      */ 
/* 4979 */                                             out.write("\n\t &nbsp;<a href=\"javascript:manageMonitors(true,'process');\" class=\"staticlinks\">");
/* 4980 */                                             out.print(FormatUtil.getString("Manage"));
/* 4981 */                                             out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(false,'process');\" class=\"staticlinks\">");
/* 4982 */                                             out.print(FormatUtil.getString("UnManage"));
/* 4983 */                                             out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"javascript:manageMonitors(false,'process',true);\" class=\"staticlinks\">");
/* 4984 */                                             out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 4985 */                                             out.write("\n\t</td>\n\t ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 4989 */                                           out.write(10);
/* 4990 */                                           out.write(9);
/* 4991 */                                           out.write(32);
/* 4992 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 4993 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4997 */                                       if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 4998 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                       }
/*      */                                       
/* 5001 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 5002 */                                       out.write("\n\t </tr>\n\t ");
/*      */                                     }
/* 5004 */                                     out.write("\n\t <tr>\n\t   <td>\n\t   <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"hostProcessDetails");
/* 5005 */                                     out.print(resourceid);
/* 5006 */                                     out.write("\">\n\t   <tr>\n\t   <input type=\"hidden\" name=\"haid\" value=\"");
/* 5007 */                                     out.print(haid);
/* 5008 */                                     out.write("\"/>\n\t<input type=\"hidden\" name=\"enabled\" value=\"NA\"/>\n\t<input type=\"hidden\" name=\"managedprocess\" value=\"NA\"/>\n\t<input type=\"hidden\" name=\"unmanageAndResetProcess\" value=\"NA\"/>\n\t<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 5009 */                                     out.print(resourceid);
/* 5010 */                                     out.write("\"/>\n\t<input type=\"hidden\" size=\"15\" name=\"configured\" value=\"true\"/>\n\t<input type=\"hidden\" name=\"appName\" value=\"");
/* 5011 */                                     out.print(appname);
/* 5012 */                                     out.write("\"/>\n\t<input type=\"hidden\" name=\"name\" value=\"");
/* 5013 */                                     out.print(resourcename);
/* 5014 */                                     out.write("\"/>\n     <td width=\"2%\" height=\"28\" class=\"columnheading\"><input type=\"checkbox\" name=\"headercheckbox\" onClick=\"javascript:fnSelectAll(this)\"></td>\n\t <td width=\"12%\" height=\"28\" class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5015 */                                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 5016 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"18%\" height=\"28\" class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5017 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.process"));
/* 5018 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"12%\" height=\"28\" class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5019 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.availability"));
/* 5020 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"10%\" height=\"28\" class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5021 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.health"));
/* 5022 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"10%\" height=\"28\" class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5023 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.instance"));
/* 5024 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"6%\" height=\"28\"  class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5025 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.cpu"));
/* 5026 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"6%\" height=\"28\"  class=\"columnheading\">&nbsp;</td>\n\t <td width=\"6%\" height=\"28\"  class=\"columnheading\"><span class=\"bodytext\" style=\"font-weight: bold; color: rgb(0, 0, 0);\">");
/* 5027 */                                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.mem"));
/* 5028 */                                     out.write("</span><span class=\"sortarrow\">&nbsp;</span></td>\n\t <td width=\"6%\" height=\"28\"  class=\"columnheading\">&nbsp;</td>\n  \t <td width=\"12%\" height=\"28\" align=\"center\" class=\"columnheading\">");
/* 5029 */                                     out.print(FormatUtil.getString("Actions"));
/* 5030 */                                     out.write("</td>\n\t </tr>\n\t ");
/*      */                                     
/* 5032 */                                     Hashtable reportsEnabled = new Hashtable();
/*      */                                     try
/*      */                                     {
/* 5035 */                                       AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5036 */                                       String selectQuery = "select RESOURCEID as RESID from AM_HOST_PROCESS_ENABLEREPORTS where PARENTID=" + resourceid;
/* 5037 */                                       ResultSet rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 5038 */                                       while (rs.next())
/*      */                                       {
/* 5040 */                                         String resId = rs.getString("RESID");
/* 5041 */                                         reportsEnabled.put(resId, resId);
/*      */                                       }
/* 5043 */                                       rs.close();
/*      */                                     }
/*      */                                     catch (Exception e)
/*      */                                     {
/* 5047 */                                       e.printStackTrace();
/*      */                                     }
/*      */                                     
/* 5050 */                                     out.write(10);
/* 5051 */                                     out.write(9);
/* 5052 */                                     out.write(32);
/*      */                                     
/* 5054 */                                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5055 */                                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 5056 */                                     _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                     
/* 5058 */                                     _jspx_th_logic_005fiterate_005f0.setName("process");
/*      */                                     
/* 5060 */                                     _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                     
/* 5062 */                                     _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                     
/* 5064 */                                     _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 5065 */                                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 5066 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 5067 */                                       ArrayList row = null;
/* 5068 */                                       Integer j = null;
/* 5069 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5070 */                                         out = _jspx_page_context.pushBody();
/* 5071 */                                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 5072 */                                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                       }
/* 5074 */                                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5075 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                       for (;;) {
/* 5077 */                                         out.write(10);
/* 5078 */                                         out.write(9);
/* 5079 */                                         out.write(32);
/*      */                                         
/* 5081 */                                         String processid = (String)row.get(0);
/* 5082 */                                         String processname = (String)row.get(1);
/* 5083 */                                         String process = (String)row.get(2);
/* 5084 */                                         String command = (String)row.get(3);
/* 5085 */                                         String instance1 = (String)row.get(4);
/* 5086 */                                         String pcpu = (String)row.get(5);
/* 5087 */                                         ArrayList unManagedList = (ArrayList)request.getAttribute("UnManagedChildList");
/* 5088 */                                         if ((instance1 == null) || (instance1.equals("null")) || (instance1.equals("NULL")) || (instance1.equals("-1")) || (instance1.equals("-1.0")))
/*      */                                         {
/* 5090 */                                           instance1 = "-";
/*      */                                         }
/* 5092 */                                         if ((pcpu == null) || (pcpu.equals("null")) || (pcpu.equals("NULL")) || (pcpu.equals("-1")) || (pcpu.equals("-1.0")) || (pcpu.equals("Idle")))
/*      */                                         {
/* 5094 */                                           pcpu = "-";
/*      */                                         }
/* 5096 */                                         String pmem = (String)row.get(6);
/* 5097 */                                         if ((pmem == null) || (pmem.equals("null")) || (pmem.equals("NULL")) || (pmem.equals("-1.0")) || (pmem.equals("-1")) || (pmem.equals("Process")))
/*      */                                         {
/* 5099 */                                           pmem = "-";
/*      */                                         }
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5123 */                                         String bgclass = "class=\"whitegrayborder\"";
/* 5124 */                                         if (j.intValue() % 2 == 0)
/*      */                                         {
/* 5126 */                                           bgclass = "class=\"whitegrayborder\"";
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5130 */                                           bgclass = "class=\"yellowgrayborder\"";
/*      */                                         }
/* 5132 */                                         String disableText = "";
/* 5133 */                                         if ((unManagedList != null) && (unManagedList.contains(processid)))
/*      */                                         {
/* 5135 */                                           disableText = "disabledtext";
/*      */                                         }
/*      */                                         
/* 5138 */                                         out.write("\n\t<tr>\n\t<td   ");
/* 5139 */                                         out.print(bgclass);
/* 5140 */                                         out.write(" ><input type=\"checkbox\" name=\"monitors\" value=\"");
/* 5141 */                                         out.print(processid);
/* 5142 */                                         out.write("\">\n\t </td>\n\t <td  ");
/* 5143 */                                         out.print(bgclass);
/* 5144 */                                         out.write(" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 5145 */                                         out.print(processid);
/* 5146 */                                         out.write("&period=0&resourcename=");
/* 5147 */                                         out.print(processname);
/* 5148 */                                         out.write("')\" class=\"resourcename ");
/* 5149 */                                         out.print(disableText);
/* 5150 */                                         out.write(34);
/* 5151 */                                         out.write(62);
/* 5152 */                                         out.print(processname);
/* 5153 */                                         out.write("</a> ");
/* 5154 */                                         if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.underMaintenance(processid)) {
/* 5155 */                                           out.write("<img src=\"/images/down-time-icon.gif\">");
/*      */                                         }
/* 5157 */                                         out.write(" </td>\n\t <td  ");
/* 5158 */                                         out.print(bgclass);
/* 5159 */                                         out.write(" title=\"");
/* 5160 */                                         out.print(process);
/* 5161 */                                         out.write("\" ><span class=\"");
/* 5162 */                                         out.print(disableText);
/* 5163 */                                         out.write(34);
/* 5164 */                                         out.write(62);
/* 5165 */                                         out.print(getTrimmedText(process, 15));
/* 5166 */                                         out.write("</span></td>\n\t <td  ");
/* 5167 */                                         out.print(bgclass);
/* 5168 */                                         out.write("  ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5169 */                                         out.print(processid);
/* 5170 */                                         out.write("&attributeid=715')\">");
/* 5171 */                                         out.print(getSeverityImageForAvailability(alert.getProperty(processid + "#" + "715")));
/* 5172 */                                         out.write("</a><span style=\"display: none;\">");
/* 5173 */                                         out.print(alert.getProperty(processid + "#" + "715"));
/* 5174 */                                         out.write("</span></td>\n\t <td  ");
/* 5175 */                                         out.print(bgclass);
/* 5176 */                                         out.write("  ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5177 */                                         out.print(processid);
/* 5178 */                                         out.write("&attributeid=716')\">");
/* 5179 */                                         out.print(getSeverityImageForHealth(alert.getProperty(processid + "#" + "716")));
/* 5180 */                                         out.write("&nbsp;</a><span style=\"display: none;\">");
/* 5181 */                                         out.print(alert.getProperty(processid + "#" + "716"));
/* 5182 */                                         out.write("</span></td>\n");
/*      */                                         
/* 5184 */                                         if (instance1.equals("-"))
/*      */                                         {
/* 5186 */                                           out.write("\n\t<td  ");
/* 5187 */                                           out.print(bgclass);
/* 5188 */                                           out.write(" align=\"center\" title=\"");
/* 5189 */                                           out.print(instance1);
/* 5190 */                                           out.write("\"><span class=\"");
/* 5191 */                                           out.print(disableText);
/* 5192 */                                           out.write(34);
/* 5193 */                                           out.write(62);
/* 5194 */                                           out.print(instance1);
/* 5195 */                                           out.write("</span></td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5199 */                                           out.write("\n\t<td  ");
/* 5200 */                                           out.print(bgclass);
/* 5201 */                                           out.write(" align=\"center\" title=\"");
/* 5202 */                                           out.print(instance1);
/* 5203 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5204 */                                           out.print(processid);
/* 5205 */                                           out.write("&attributeid=717&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span class=\"");
/* 5206 */                                           out.print(disableText);
/* 5207 */                                           out.write(34);
/* 5208 */                                           out.write(62);
/* 5209 */                                           out.print(instance1);
/* 5210 */                                           out.write("</span></a></td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5214 */                                         out.write("\n\t <!--<td   ");
/* 5215 */                                         out.print(bgclass);
/* 5216 */                                         out.write(" title=\"");
/* 5217 */                                         out.print(command);
/* 5218 */                                         out.write("\" > ");
/* 5219 */                                         out.print(getTrimmedText(command, 15));
/* 5220 */                                         out.write("</td>-->\n");
/*      */                                         
/* 5222 */                                         if (pcpu.equals("-"))
/*      */                                         {
/* 5224 */                                           out.write("\n\t<td  ");
/* 5225 */                                           out.print(bgclass);
/* 5226 */                                           out.write(" align=\"center\" title=\"");
/* 5227 */                                           out.print(pcpu);
/* 5228 */                                           out.write("\"><span class=\"");
/* 5229 */                                           out.print(disableText);
/* 5230 */                                           out.write(34);
/* 5231 */                                           out.write(62);
/* 5232 */                                           out.print(pcpu);
/* 5233 */                                           out.write("</span></td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5237 */                                           out.write("\n\t<td   ");
/* 5238 */                                           out.print(bgclass);
/* 5239 */                                           out.write(" align=\"center\" title=\"");
/* 5240 */                                           out.print(pcpu);
/* 5241 */                                           out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5242 */                                           out.print(processid);
/* 5243 */                                           out.write("&attributeid=718&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span class=\"");
/* 5244 */                                           out.print(disableText);
/* 5245 */                                           out.write(34);
/* 5246 */                                           out.write(62);
/* 5247 */                                           out.print(FormatUtil.formatNumber(pcpu));
/* 5248 */                                           out.write("</span></a></td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5252 */                                         out.write("\n\n\t ");
/*      */                                         
/* 5254 */                                         String modeOfMon = (String)request.getAttribute("mode");
/* 5255 */                                         if ((reportsEnabled.get(processid) != null) && (!modeOfMon.equals("SNMP")) && (!modeOfMon.equals("WMI")))
/*      */                                         {
/*      */ 
/* 5258 */                                           out.write("\n\t <td   ");
/* 5259 */                                           out.print(bgclass);
/* 5260 */                                           out.write(" align=\"left\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5261 */                                           out.print(processid);
/* 5262 */                                           out.write("&attributeid=718&period=-7')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 5263 */                                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 5264 */                                           out.write("\"></a></td>\n\t ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 5270 */                                           out.write("\n\t   <td   ");
/* 5271 */                                           out.print(bgclass);
/* 5272 */                                           out.write(" align=\"left\">&nbsp;</td>\n\t   ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5276 */                                         out.write(10);
/*      */                                         
/* 5278 */                                         if (pmem.equals("-"))
/*      */                                         {
/* 5280 */                                           out.write("\n\t<td  ");
/* 5281 */                                           out.print(bgclass);
/* 5282 */                                           out.write(" align=\"center\" title=\"");
/* 5283 */                                           out.print(pmem);
/* 5284 */                                           out.write("\"><span class=\"");
/* 5285 */                                           out.print(disableText);
/* 5286 */                                           out.write(34);
/* 5287 */                                           out.write(62);
/* 5288 */                                           out.print(pmem);
/* 5289 */                                           out.write("</span></td>\n");
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5293 */                                           out.write("\n\t<td   ");
/* 5294 */                                           out.print(bgclass);
/* 5295 */                                           out.write(" align=\"center\" title=\"");
/* 5296 */                                           out.print(pmem);
/* 5297 */                                           out.write("\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5298 */                                           out.print(processid);
/* 5299 */                                           out.write("&attributeid=719&period=20&businessPeriod=oni')\" class=\"staticlinks\"><span class=\"");
/* 5300 */                                           out.print(disableText);
/* 5301 */                                           out.write(34);
/* 5302 */                                           out.write(62);
/* 5303 */                                           out.print(getTrimmedText(FormatUtil.formatNumber(pmem), 7));
/* 5304 */                                           out.write("</span></a></td>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5308 */                                         out.write(10);
/* 5309 */                                         out.write(9);
/* 5310 */                                         out.write(32);
/*      */                                         
/* 5312 */                                         if (reportsEnabled.get(processid) != null)
/*      */                                         {
/*      */ 
/* 5315 */                                           out.write("\n\t <td   ");
/* 5316 */                                           out.print(bgclass);
/* 5317 */                                           out.write(" align=\"left\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5318 */                                           out.print(processid);
/* 5319 */                                           out.write("&attributeid=719&period=-7')\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 5320 */                                           out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 5321 */                                           out.write("\"></a></td>\n\t ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 5327 */                                           out.write("\n\t  <td  ");
/* 5328 */                                           out.print(bgclass);
/* 5329 */                                           out.write(" align=\"left\">&nbsp;</td>\n\t ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5333 */                                         out.write("\n\t <td ");
/* 5334 */                                         out.print(bgclass);
/* 5335 */                                         out.write("  align=\"center\">\n\t    ");
/*      */                                         
/* 5337 */                                         PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5338 */                                         _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 5339 */                                         _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                         
/* 5341 */                                         _jspx_th_logic_005fpresent_005f7.setRole("ADMIN");
/* 5342 */                                         int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 5343 */                                         if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                           for (;;) {
/* 5345 */                                             out.write("\n\t\t<a href=\"/HostResource.do?editProcess=true&processid=");
/* 5346 */                                             out.print(processid);
/* 5347 */                                             out.write("&resourceid=");
/* 5348 */                                             out.print(resourceid);
/* 5349 */                                             out.write("&name=");
/* 5350 */                                             out.print(resourcename);
/* 5351 */                                             out.write("&haid=");
/* 5352 */                                             out.print(haid);
/* 5353 */                                             out.write("&appName=");
/* 5354 */                                             out.print(appname);
/* 5355 */                                             out.write("&disableEdit=false\"><img src=\"/images/icon_edit.gif\" title=\"");
/* 5356 */                                             out.print(FormatUtil.getString("am.webclient.hostresourceconfig.editprocess"));
/* 5357 */                                             out.write("\"  border=\"0\"></a>&nbsp;&nbsp;&nbsp;\n\t    ");
/* 5358 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 5359 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5363 */                                         if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 5364 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                         }
/*      */                                         
/* 5367 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 5368 */                                         out.write("\n    \t<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5369 */                                         out.print(processid);
/* 5370 */                                         out.write("&attributeIDs=715,716,717,718,719&attributeToSelect=715&redirectto=");
/* 5371 */                                         out.print(encodeurl);
/* 5372 */                                         out.write("'><img src=\"/images/icon_associateaction.gif\" title=\"");
/* 5373 */                                         out.print(ALERTCONFIG_TEXT);
/* 5374 */                                         out.write("\" border=\"0\" ></a>\n    </td>\n\t</tr>\n\t");
/* 5375 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5376 */                                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5377 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 5378 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5381 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5382 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5385 */                                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5386 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                     }
/*      */                                     
/* 5389 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5390 */                                     out.write("\n\t</table>\n\t</td>\n\t</tr>\n\n<tr>\n\n\t<td colspan=\"12\" align=\"left\" >\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" class=\"tablebottom1\">\n\t<tr><td style=\"height:30px\">\n\t");
/*      */                                     
/* 5392 */                                     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5393 */                                     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 5394 */                                     _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                     
/* 5396 */                                     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 5397 */                                     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 5398 */                                     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                       for (;;) {
/* 5400 */                                         out.write("\n\t<a href=\"javascript:alertUser();\" class=\"staticlinks\">");
/* 5401 */                                         out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 5402 */                                         out.write("</a>\n\t");
/* 5403 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 5404 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5408 */                                     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 5409 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                     }
/*      */                                     
/* 5412 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 5413 */                                     out.write(10);
/* 5414 */                                     out.write(9);
/*      */                                     
/* 5416 */                                     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5417 */                                     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 5418 */                                     _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                     
/* 5420 */                                     _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 5421 */                                     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 5422 */                                     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                       for (;;) {
/* 5424 */                                         out.write("\n<span class=\"bodytext\">\t<a href=\"javascript:removeMonitors();\" class=\"staticlinks\">");
/* 5425 */                                         out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 5426 */                                         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(true,'process');\" class=\"staticlinks\">");
/* 5427 */                                         out.print(FormatUtil.getString("Manage"));
/* 5428 */                                         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(false,'process');\" class=\"staticlinks\">");
/* 5429 */                                         out.print(FormatUtil.getString("UnManage"));
/* 5430 */                                         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(false,'process',true);\" class=\"staticlinks\">");
/* 5431 */                                         out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 5432 */                                         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n<A HREF=\"javascript:enableReports();\" class=\"staticlinks\">");
/* 5433 */                                         out.print(FormatUtil.getString("am.webclient.script.enablereports"));
/* 5434 */                                         out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;<A HREF=\"javascript:disableReports();\" title=\"");
/* 5435 */                                         out.print(FormatUtil.getString("am.webclient.hostresourceprocess.tooltip.enablemessage"));
/* 5436 */                                         out.write("\" class=\"staticlinks\">");
/* 5437 */                                         out.print(FormatUtil.getString("am.webclient.script.disablereports"));
/* 5438 */                                         out.write("</a></span></td>\n\t<td height=\"15\" align=\"right\" ><a href=\"#top1\" class=\"staticlinks\">");
/* 5439 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 5440 */                                         out.write("</a>&nbsp;</td>\n\t");
/* 5441 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 5442 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5446 */                                     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 5447 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                     }
/*      */                                     
/* 5450 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 5451 */                                     out.write(10);
/* 5452 */                                     out.write(9);
/* 5453 */                                     out.write(32);
/*      */                                     
/* 5455 */                                     PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5456 */                                     _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 5457 */                                     _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                     
/* 5459 */                                     _jspx_th_logic_005fpresent_005f10.setRole("OPERATOR");
/* 5460 */                                     int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 5461 */                                     if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                       for (;;) {
/* 5463 */                                         out.write(10);
/* 5464 */                                         out.write(9);
/* 5465 */                                         out.write(32);
/*      */                                         
/* 5467 */                                         if (allowManageAction)
/*      */                                         {
/*      */ 
/* 5470 */                                           out.write("\n\t &nbsp;<a href=\"javascript:manageMonitors(true,'process');\" class=\"staticlinks\">");
/* 5471 */                                           out.print(FormatUtil.getString("Manage"));
/* 5472 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t &nbsp;<a href=\"javascript:manageMonitors(false,'process');\" class=\"staticlinks\">");
/* 5473 */                                           out.print(FormatUtil.getString("UnManage"));
/* 5474 */                                           out.write("</a>&nbsp;&nbsp;|&nbsp;&nbsp;\n\t\t<a href=\"javascript:manageMonitors(false,'process',true);\" class=\"staticlinks\">");
/* 5475 */                                           out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 5476 */                                           out.write("\n\t\t</td>\n\t ");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5480 */                                         out.write(10);
/* 5481 */                                         out.write(9);
/* 5482 */                                         out.write(32);
/* 5483 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 5484 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5488 */                                     if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 5489 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                                     }
/*      */                                     
/* 5492 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 5493 */                                     out.write("\n\t</tr>\n\t</table>\n\t</td>\n\t</tr>\n\t</table>\n\t</form>\n\t ");
/* 5494 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 5495 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5499 */                                 if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 5500 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                 }
/*      */                                 
/* 5503 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 5504 */                                 out.write(10);
/* 5505 */                                 out.write(9);
/* 5506 */                                 out.write(32);
/*      */                                 
/* 5508 */                                 EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5509 */                                 _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 5510 */                                 _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                 
/* 5512 */                                 _jspx_th_logic_005fempty_005f2.setName("process");
/* 5513 */                                 int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 5514 */                                 if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                                   for (;;) {
/* 5516 */                                     out.write(10);
/* 5517 */                                     out.write(9);
/* 5518 */                                     out.write(32);
/*      */                                     
/* 5520 */                                     if (showdata.equals("2"))
/*      */                                     {
/*      */ 
/*      */ 
/* 5524 */                                       out.write("\n\t <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n\t <tr>\n\t    <td class=\"bodytext\" align=\"center\" height=\"26\"> ");
/* 5525 */                                       out.print(FormatUtil.getString("am.webclient.hostResource.servers.noprocess"));
/* 5526 */                                       out.write(32);
/* 5527 */                                       out.write(9);
/*      */                                       
/* 5529 */                                       PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5530 */                                       _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 5531 */                                       _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_logic_005fempty_005f2);
/*      */                                       
/* 5533 */                                       _jspx_th_logic_005fpresent_005f11.setRole("DEMO");
/* 5534 */                                       int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 5535 */                                       if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                         for (;;) {
/* 5537 */                                           out.write("<a href=\"javascript:alertUser()\" class=\"staticlinks\">");
/* 5538 */                                           out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink.second"));
/* 5539 */                                           out.write("</a>\t");
/* 5540 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 5541 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 5545 */                                       if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 5546 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                                       }
/*      */                                       
/* 5549 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 5550 */                                       out.write(32);
/*      */                                       
/* 5552 */                                       PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5553 */                                       _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 5554 */                                       _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_logic_005fempty_005f2);
/*      */                                       
/* 5556 */                                       _jspx_th_logic_005fpresent_005f12.setRole("ADMIN");
/* 5557 */                                       int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 5558 */                                       if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                                         for (;;) {
/* 5560 */                                           out.write("<a href=\"#\" onClick=\"fnOpenNewWindow('../HostResource.do?getProcessList=true&resourceid=");
/* 5561 */                                           if (_jspx_meth_c_005fout_005f22(_jspx_th_logic_005fpresent_005f12, _jspx_page_context))
/*      */                                             return;
/* 5563 */                                           out.write("&PercentMB=");
/* 5564 */                                           out.print(SPercent);
/* 5565 */                                           out.write("&MBValue=");
/* 5566 */                                           out.print(SValue);
/* 5567 */                                           out.write("&resType=");
/* 5568 */                                           out.print(resType);
/* 5569 */                                           out.write("')\" class=\"staticlinks\"\t>");
/* 5570 */                                           out.print(FormatUtil.getString("am.webclient.hostResource.servers.addprocesslink.second"));
/* 5571 */                                           out.write(9);
/* 5572 */                                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 5573 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 5577 */                                       if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 5578 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                                       }
/*      */                                       
/* 5581 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 5582 */                                       out.write("</a>\n\n\t\t  </td>\n\n\t </tr>\n\t </table>\n\t  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n\t \t<tr>\n\t \t<td height=\"15\" align=\"right\" class=\"tablebottom1\"><a href=\"#top1\" class=\"staticlinks\">");
/* 5583 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 5584 */                                       out.write("</a>&nbsp;</td>\n\t \t</tr>\n\t</table>\n");
/*      */                                     }
/*      */                                     
/* 5587 */                                     out.write(10);
/* 5588 */                                     out.write(9);
/* 5589 */                                     out.write(32);
/* 5590 */                                     int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 5591 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5595 */                                 if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 5596 */                                   this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                                 }
/*      */                                 
/* 5599 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 5600 */                                 out.write("\n\t<!-- ######## Display Process details - end ######## -->\n\n\t<!-- ######## Display window services details - start ######## -->\n");
/*      */                                 
/* 5602 */                                 String collectionmode = (String)request.getAttribute("mode");
/* 5603 */                                 if ((collectionmode != null) && (collectionmode.equalsIgnoreCase("WMI")))
/*      */                                 {
/*      */ 
/* 5606 */                                   out.write("\n\t     <form action=\"/HostResource.do?actiononServices=true\" name=\"removeservice\" method=\"post\" style=\"display:inline\">\n\t     <div id='servicedetail' >");
/* 5607 */                                   org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "Windows_Services.jsp", out, false);
/* 5608 */                                   out.write("</div>\n\t     </form>\n\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5612 */                                 out.write("\n\t<!-- ######## Display window services details - end ######## -->\n");
/* 5613 */                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5614 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5618 */                             if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5619 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                             }
/*      */                             
/* 5622 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5623 */                             out.write(10);
/*      */                             
/* 5625 */                             OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5626 */                             _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 5627 */                             _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f0);
/* 5628 */                             int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 5629 */                             if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                               for (;;) {
/* 5631 */                                 out.write("\n <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n   <td align=\"center\" class=\"bodytextbold11\"><a href=\"/showresource.do?method=showResourceForResourceID&resourceid=");
/* 5632 */                                 if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                   return;
/* 5634 */                                 if (_jspx_meth_c_005fif_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                   return;
/* 5636 */                                 out.write("\" class=\"staticlinks\">");
/* 5637 */                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.gotosnapshot"));
/* 5638 */                                 out.write("</a></td>\n  </tr>\n </table>\n");
/* 5639 */                                 int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 5640 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5644 */                             if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 5645 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                             }
/*      */                             
/* 5648 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 5649 */                             out.write(10);
/* 5650 */                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5651 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 5655 */                         if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5656 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                         }
/*      */                         
/* 5659 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5660 */                         out.write("\n</div>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 5664 */                       out.write("\n</td></tr></table>\n");
/*      */                     }
/*      */                     
/*      */ 
/* 5668 */                     out.write("\n<script language=\"javascript\">\n\tSORTTABLENAME = 'hostProcessDetails'+");
/* 5669 */                     out.print(resourceid);
/* 5670 */                     out.write("; //No I18N\n\tvar numberOfColumnsToBeSorted=7;\n\tsortables_init(numberOfColumnsToBeSorted,true,false,true);\n\t\n\t\n\tfunction map_dependencies(){\n\t    var dataString = '&method=mapDependencies&resourceId=");
/* 5671 */                     out.print(resourceid);
/* 5672 */                     out.write("&isGlobalView=true'; //No I18N\n\t\tvar waitString = \"");
/* 5673 */                     out.print(FormatUtil.getString("am.webclient.hostDiscoveryForm.pleasewait"));
/* 5674 */                     out.write("\";\n\t     $.ajax({\n\t                 type: \"POST\", //No I18N\n\t \t         url: '/dependencyMapping.do', // Action URL //No I18N\n\t \t         data: dataString,                                                        // Query String parameters\n\t \t         success: function(response)\n\t \t         {\n\t \t                                alert(\"Mapping Dependency.. check netstat_output file..\"+response);       // Set response into particular div ID .. //No I18N\n\t \t                               //callbackMethodCalling();\n\t \t         }\n\t \t });\n\t}\n\n</script>");
/*      */                   }
/* 5676 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5677 */         out = _jspx_out;
/* 5678 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5679 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5680 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5683 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5689 */     PageContext pageContext = _jspx_page_context;
/* 5690 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5692 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 5693 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 5694 */     _jspx_th_c_005fcatch_005f0.setParent(null);
/*      */     
/* 5696 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 5697 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 5699 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 5700 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 5702 */           out.write(10);
/* 5703 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 5704 */             return true;
/* 5705 */           out.write(10);
/* 5706 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 5707 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5711 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 5712 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5715 */         int tmp177_176 = 0; int[] tmp177_174 = _jspx_push_body_count_c_005fcatch_005f0; int tmp179_178 = tmp177_174[tmp177_176];tmp177_174[tmp177_176] = (tmp179_178 - 1); if (tmp179_178 <= 0) break;
/* 5716 */         out = _jspx_page_context.popBody(); }
/* 5717 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5719 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 5720 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 5722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 5727 */     PageContext pageContext = _jspx_page_context;
/* 5728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5730 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 5731 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 5732 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 5734 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 5736 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 5737 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 5738 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 5739 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5740 */       return true;
/*      */     }
/* 5742 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 5743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5748 */     PageContext pageContext = _jspx_page_context;
/* 5749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5751 */     ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5752 */     _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 5753 */     _jspx_th_c_005fchoose_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 5754 */     int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 5755 */     if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */       for (;;) {
/* 5757 */         out.write("\n\t\t\t\t\t");
/* 5758 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5759 */           return true;
/* 5760 */         out.write("\n\t\t\t\t\t");
/* 5761 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/* 5762 */           return true;
/* 5763 */         out.write("\n\t\t\t\t");
/* 5764 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 5765 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5769 */     if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 5770 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5771 */       return true;
/*      */     }
/* 5773 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 5774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5779 */     PageContext pageContext = _jspx_page_context;
/* 5780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5782 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5783 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5784 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/*      */     
/* 5786 */     _jspx_th_c_005fwhen_005f1.setTest("${not empty server_uptime}");
/* 5787 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5788 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5790 */         out.write("\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 5791 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5792 */           return true;
/* 5793 */         out.write("</td>\n\t\t\t\t\t");
/* 5794 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5795 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5799 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5800 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5801 */       return true;
/*      */     }
/* 5803 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5809 */     PageContext pageContext = _jspx_page_context;
/* 5810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5812 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5813 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5814 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5816 */     _jspx_th_c_005fout_005f0.setValue("${server_uptime}");
/* 5817 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5818 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5819 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5820 */       return true;
/*      */     }
/* 5822 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5828 */     PageContext pageContext = _jspx_page_context;
/* 5829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5831 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5832 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5833 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 5834 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5835 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5837 */         out.write("\n\t\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t\t");
/* 5838 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5839 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5843 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5844 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5845 */       return true;
/*      */     }
/* 5847 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5848 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5853 */     PageContext pageContext = _jspx_page_context;
/* 5854 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5856 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5857 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5858 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5860 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 5861 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5862 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5863 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5864 */       return true;
/*      */     }
/* 5866 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5872 */     PageContext pageContext = _jspx_page_context;
/* 5873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5875 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5876 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5877 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5879 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5880 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5881 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5882 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5883 */       return true;
/*      */     }
/* 5885 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5891 */     PageContext pageContext = _jspx_page_context;
/* 5892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5894 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5895 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5896 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5898 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 5899 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5900 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5901 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5902 */       return true;
/*      */     }
/* 5904 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5910 */     PageContext pageContext = _jspx_page_context;
/* 5911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5913 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5914 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5915 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5917 */     _jspx_th_c_005fout_005f4.setValue("${param.displayname}");
/* 5918 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5919 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5920 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5921 */       return true;
/*      */     }
/* 5923 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5929 */     PageContext pageContext = _jspx_page_context;
/* 5930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5932 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5933 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5934 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5936 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5937 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5938 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5939 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5940 */       return true;
/*      */     }
/* 5942 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5943 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5948 */     PageContext pageContext = _jspx_page_context;
/* 5949 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5951 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5952 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5953 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5955 */     _jspx_th_c_005fout_005f6.setValue("${param.displayname}");
/* 5956 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5957 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5958 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5959 */       return true;
/*      */     }
/* 5961 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5962 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5967 */     PageContext pageContext = _jspx_page_context;
/* 5968 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5970 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5971 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5972 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5974 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5975 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5976 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5977 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5978 */       return true;
/*      */     }
/* 5980 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5986 */     PageContext pageContext = _jspx_page_context;
/* 5987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5989 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5990 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5991 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5993 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5994 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5995 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5996 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5997 */       return true;
/*      */     }
/* 5999 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6005 */     PageContext pageContext = _jspx_page_context;
/* 6006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6008 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6009 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6010 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6012 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 6013 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6014 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6015 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6016 */       return true;
/*      */     }
/* 6018 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6024 */     PageContext pageContext = _jspx_page_context;
/* 6025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6027 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6028 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6029 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6031 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6032 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6033 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6034 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6035 */       return true;
/*      */     }
/* 6037 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6038 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6043 */     PageContext pageContext = _jspx_page_context;
/* 6044 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6046 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6047 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6048 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 6049 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6050 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 6051 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6052 */         out = _jspx_page_context.pushBody();
/* 6053 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 6054 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6057 */         out.write("table.heading.attribute");
/* 6058 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 6059 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6062 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 6063 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6066 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6067 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6068 */       return true;
/*      */     }
/* 6070 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6076 */     PageContext pageContext = _jspx_page_context;
/* 6077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6079 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6080 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6081 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 6082 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6083 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 6084 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 6085 */         out = _jspx_page_context.pushBody();
/* 6086 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 6087 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6090 */         out.write("table.heading.value");
/* 6091 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 6092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6095 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 6096 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6099 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6100 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6101 */       return true;
/*      */     }
/* 6103 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6109 */     PageContext pageContext = _jspx_page_context;
/* 6110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6112 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6113 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6114 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 6115 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6116 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 6117 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 6118 */         out = _jspx_page_context.pushBody();
/* 6119 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 6120 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6123 */         out.write("table.heading.status");
/* 6124 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 6125 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6128 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 6129 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6132 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6133 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6134 */       return true;
/*      */     }
/* 6136 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6142 */     PageContext pageContext = _jspx_page_context;
/* 6143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6145 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6146 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6147 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6149 */     _jspx_th_c_005fif_005f2.setTest("${empty cpudata && showdata=='2'}");
/* 6150 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6151 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6153 */         out.write("\n\t\t\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"bborder\">\n\t\t\t\t<tr><td></td></tr>\n\t\t\t\t</table>\n\t\t\t\t<br>\n\t\t\t");
/* 6154 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6155 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6159 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6160 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6161 */       return true;
/*      */     }
/* 6163 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6169 */     PageContext pageContext = _jspx_page_context;
/* 6170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6172 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6173 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6174 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6176 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 6177 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6178 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6180 */       return true;
/*      */     }
/* 6182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6188 */     PageContext pageContext = _jspx_page_context;
/* 6189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6191 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6192 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6193 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6195 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6196 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6197 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6211 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6214 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 6215 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6216 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6217 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6218 */       return true;
/*      */     }
/* 6220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6226 */     PageContext pageContext = _jspx_page_context;
/* 6227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6229 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6230 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6231 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6232 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6233 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 6234 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6235 */         out = _jspx_page_context.pushBody();
/* 6236 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 6237 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6240 */         out.write("Memory Usage");
/* 6241 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 6242 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6245 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 6246 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6249 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6250 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6251 */       return true;
/*      */     }
/* 6253 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6259 */     PageContext pageContext = _jspx_page_context;
/* 6260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6262 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6263 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6264 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6265 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6266 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 6267 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6268 */         out = _jspx_page_context.pushBody();
/* 6269 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 6270 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6273 */         out.write("table.heading.status");
/* 6274 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 6275 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6278 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6279 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6282 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6283 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6284 */       return true;
/*      */     }
/* 6286 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6292 */     PageContext pageContext = _jspx_page_context;
/* 6293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6295 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6296 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6297 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6299 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6300 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6301 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6302 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6303 */       return true;
/*      */     }
/* 6305 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6306 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6311 */     PageContext pageContext = _jspx_page_context;
/* 6312 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6314 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6315 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6316 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6318 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 6319 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6320 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6321 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6322 */       return true;
/*      */     }
/* 6324 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6325 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6330 */     PageContext pageContext = _jspx_page_context;
/* 6331 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6333 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6334 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6335 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6337 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 6338 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6339 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6340 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6341 */       return true;
/*      */     }
/* 6343 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6344 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6349 */     PageContext pageContext = _jspx_page_context;
/* 6350 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6352 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6353 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6354 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6356 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 6357 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6358 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6359 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6360 */       return true;
/*      */     }
/* 6362 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6363 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6368 */     PageContext pageContext = _jspx_page_context;
/* 6369 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6371 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6372 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6373 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6374 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6375 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6376 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6377 */         out = _jspx_page_context.pushBody();
/* 6378 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6379 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6382 */         out.write("CPU Usage");
/* 6383 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6384 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6387 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6388 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6391 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6392 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6393 */       return true;
/*      */     }
/* 6395 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6396 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6401 */     PageContext pageContext = _jspx_page_context;
/* 6402 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6404 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6405 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6406 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/* 6407 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6408 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6409 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6410 */         out = _jspx_page_context.pushBody();
/* 6411 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6412 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6415 */         out.write("table.heading.status");
/* 6416 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6417 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6420 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6421 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6424 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6425 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6426 */       return true;
/*      */     }
/* 6428 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6429 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6434 */     PageContext pageContext = _jspx_page_context;
/* 6435 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6437 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6438 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6439 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6441 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 6442 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6443 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6444 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6445 */       return true;
/*      */     }
/* 6447 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6448 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6453 */     PageContext pageContext = _jspx_page_context;
/* 6454 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6456 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6457 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6458 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6460 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6461 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6462 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6463 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6464 */       return true;
/*      */     }
/* 6466 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6467 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005ftimechart_005f2(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6472 */     PageContext pageContext = _jspx_page_context;
/* 6473 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6475 */     TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 6476 */     _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 6477 */     _jspx_th_awolf_005ftimechart_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6479 */     _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("hostCPUGraph");
/*      */     
/* 6481 */     _jspx_th_awolf_005ftimechart_005f2.setWidth("650");
/*      */     
/* 6483 */     _jspx_th_awolf_005ftimechart_005f2.setHeight("250");
/*      */     
/* 6485 */     _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */     
/* 6487 */     _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel("");
/*      */     
/* 6489 */     _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel("( % )");
/*      */     
/* 6491 */     _jspx_th_awolf_005ftimechart_005f2.setYaxisUnit("%");
/* 6492 */     int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 6493 */     if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 6494 */       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 6495 */         out = _jspx_page_context.pushBody();
/* 6496 */         _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 6497 */         _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6500 */         out.write("\n        \t");
/* 6501 */         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 6502 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6505 */       if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 6506 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6509 */     if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 6510 */       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 6511 */       return true;
/*      */     }
/* 6513 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisUnit_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 6514 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6519 */     PageContext pageContext = _jspx_page_context;
/* 6520 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6522 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6523 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6524 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6526 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 6527 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6528 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6529 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6530 */       return true;
/*      */     }
/* 6532 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6533 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6538 */     PageContext pageContext = _jspx_page_context;
/* 6539 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6541 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6542 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6543 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 6545 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 6546 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6547 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6548 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6549 */       return true;
/*      */     }
/* 6551 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6552 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_logic_005fpresent_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6557 */     PageContext pageContext = _jspx_page_context;
/* 6558 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6560 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6561 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6562 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_logic_005fpresent_005f12);
/*      */     
/* 6564 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 6565 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6566 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6567 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6568 */       return true;
/*      */     }
/* 6570 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6576 */     PageContext pageContext = _jspx_page_context;
/* 6577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6579 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6580 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6581 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 6583 */     _jspx_th_c_005fout_005f23.setValue("${param.resourceid}");
/* 6584 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6585 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6586 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6587 */       return true;
/*      */     }
/* 6589 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6590 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6595 */     PageContext pageContext = _jspx_page_context;
/* 6596 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6598 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6599 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 6600 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 6602 */     _jspx_th_c_005fif_005f3.setTest("${ !empty param.haid && param.haid!='null' }");
/* 6603 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 6604 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 6606 */         out.write("&haid=");
/* 6607 */         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 6608 */           return true;
/* 6609 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 6610 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6614 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 6615 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6616 */       return true;
/*      */     }
/* 6618 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6624 */     PageContext pageContext = _jspx_page_context;
/* 6625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6627 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6628 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6629 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6631 */     _jspx_th_c_005fout_005f24.setValue("${param.haid}");
/* 6632 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6633 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6634 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6635 */       return true;
/*      */     }
/* 6637 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6638 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\HostOverViewDetails_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */