/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.struts.form.AMActionForm;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
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
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class WSM_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   69 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   72 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   73 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   74 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   81 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   86 */     ArrayList list = null;
/*   87 */     StringBuffer sbf = new StringBuffer();
/*   88 */     ManagedApplication mo = new ManagedApplication();
/*   89 */     if (distinct)
/*      */     {
/*   91 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   95 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   98 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*  100 */       ArrayList row = (ArrayList)list.get(i);
/*  101 */       sbf.append("<option value='" + row.get(0) + "'>");
/*  102 */       if (distinct) {
/*  103 */         sbf.append(row.get(0));
/*      */       } else
/*  105 */         sbf.append(row.get(1));
/*  106 */       sbf.append("</option>");
/*      */     }
/*      */     
/*  109 */     return sbf.toString(); }
/*      */   
/*  111 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  114 */     if (severity == null)
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  118 */     if (severity.equals("5"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  122 */     if (severity.equals("1"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  129 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  136 */     if (severity == null)
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  140 */     if (severity.equals("1"))
/*      */     {
/*  142 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  144 */     if (severity.equals("4"))
/*      */     {
/*  146 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  148 */     if (severity.equals("5"))
/*      */     {
/*  150 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  155 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  161 */     if (severity == null)
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  165 */     if (severity.equals("5"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  169 */     if (severity.equals("1"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  175 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  181 */     if (severity == null)
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  185 */     if (severity.equals("1"))
/*      */     {
/*  187 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  189 */     if (severity.equals("4"))
/*      */     {
/*  191 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  193 */     if (severity.equals("5"))
/*      */     {
/*  195 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  199 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  205 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  211 */     if (severity == 5)
/*      */     {
/*  213 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  215 */     if (severity == 1)
/*      */     {
/*  217 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  222 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  228 */     if (severity == null)
/*      */     {
/*  230 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  232 */     if (severity.equals("5"))
/*      */     {
/*  234 */       if (isAvailability) {
/*  235 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  238 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  241 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  243 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  245 */     if (severity.equals("1"))
/*      */     {
/*  247 */       if (isAvailability) {
/*  248 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  251 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  258 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  265 */     if (severity == null)
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  269 */     if (severity.equals("5"))
/*      */     {
/*  271 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  273 */     if (severity.equals("4"))
/*      */     {
/*  275 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  277 */     if (severity.equals("1"))
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  284 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  290 */     if (severity == null)
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  294 */     if (severity.equals("5"))
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  298 */     if (severity.equals("4"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  302 */     if (severity.equals("1"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  309 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  316 */     if (severity == null)
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  320 */     if (severity.equals("5"))
/*      */     {
/*  322 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  324 */     if (severity.equals("4"))
/*      */     {
/*  326 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  328 */     if (severity.equals("1"))
/*      */     {
/*  330 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  335 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  343 */     StringBuffer out = new StringBuffer();
/*  344 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  345 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  346 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  347 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  348 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  349 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  350 */     out.append("</tr>");
/*  351 */     out.append("</form></table>");
/*  352 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  359 */     if (val == null)
/*      */     {
/*  361 */       return "-";
/*      */     }
/*      */     
/*  364 */     String ret = FormatUtil.formatNumber(val);
/*  365 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  366 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  369 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  373 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  381 */     StringBuffer out = new StringBuffer();
/*  382 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  383 */     out.append("<tr>");
/*  384 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  386 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  388 */     out.append("</tr>");
/*  389 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  393 */       if (j % 2 == 0)
/*      */       {
/*  395 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  399 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  402 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  404 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  407 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  411 */       out.append("</tr>");
/*      */     }
/*  413 */     out.append("</table>");
/*  414 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  417 */     out.append("</tr>");
/*  418 */     out.append("</table>");
/*  419 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  425 */     StringBuffer out = new StringBuffer();
/*  426 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  427 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  428 */     out.append("<tr>");
/*  429 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  430 */     out.append("<tr>");
/*  431 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  432 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  433 */     out.append("</tr>");
/*  434 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  437 */       out.append("<tr>");
/*  438 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  439 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  440 */       out.append("</tr>");
/*      */     }
/*      */     
/*  443 */     out.append("</table>");
/*  444 */     out.append("</table>");
/*  445 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  450 */     if (severity.equals("0"))
/*      */     {
/*  452 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  456 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  463 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  476 */     StringBuffer out = new StringBuffer();
/*  477 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  478 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  480 */       out.append("<tr>");
/*  481 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  482 */       out.append("</tr>");
/*      */       
/*      */ 
/*  485 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  487 */         String borderclass = "";
/*      */         
/*      */ 
/*  490 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  492 */         out.append("<tr>");
/*      */         
/*  494 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  495 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  496 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  502 */     out.append("</table><br>");
/*  503 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  504 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  506 */       List sLinks = secondLevelOfLinks[0];
/*  507 */       List sText = secondLevelOfLinks[1];
/*  508 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  511 */         out.append("<tr>");
/*  512 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  513 */         out.append("</tr>");
/*  514 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  516 */           String borderclass = "";
/*      */           
/*      */ 
/*  519 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  521 */           out.append("<tr>");
/*      */           
/*  523 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  524 */           if (sLinks.get(i).toString().length() == 0) {
/*  525 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  528 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  530 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  534 */     out.append("</table>");
/*  535 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  542 */     StringBuffer out = new StringBuffer();
/*  543 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  544 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  546 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  548 */         out.append("<tr>");
/*  549 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  550 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  554 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  556 */           String borderclass = "";
/*      */           
/*      */ 
/*  559 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  561 */           out.append("<tr>");
/*      */           
/*  563 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  564 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  565 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  568 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  571 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  576 */     out.append("</table><br>");
/*  577 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  578 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  580 */       List sLinks = secondLevelOfLinks[0];
/*  581 */       List sText = secondLevelOfLinks[1];
/*  582 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  585 */         out.append("<tr>");
/*  586 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  587 */         out.append("</tr>");
/*  588 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  590 */           String borderclass = "";
/*      */           
/*      */ 
/*  593 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  595 */           out.append("<tr>");
/*      */           
/*  597 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  598 */           if (sLinks.get(i).toString().length() == 0) {
/*  599 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  602 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  604 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  608 */     out.append("</table>");
/*  609 */     return out.toString();
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
/*  622 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  625 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  628 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  631 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  634 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  637 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  640 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  643 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  651 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  656 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  661 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  666 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  671 */     if (val != null)
/*      */     {
/*  673 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  677 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  682 */     if (val == null) {
/*  683 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  687 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  692 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  698 */     if (val != null)
/*      */     {
/*  700 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  704 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  710 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  715 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  719 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  724 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  729 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  734 */     String hostaddress = "";
/*  735 */     String ip = request.getHeader("x-forwarded-for");
/*  736 */     if (ip == null)
/*  737 */       ip = request.getRemoteAddr();
/*  738 */     InetAddress add = null;
/*  739 */     if (ip.equals("127.0.0.1")) {
/*  740 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  744 */       add = InetAddress.getByName(ip);
/*      */     }
/*  746 */     hostaddress = add.getHostName();
/*  747 */     if (hostaddress.indexOf('.') != -1) {
/*  748 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  749 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  753 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  758 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  764 */     if (severity == null)
/*      */     {
/*  766 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  768 */     if (severity.equals("5"))
/*      */     {
/*  770 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  772 */     if (severity.equals("1"))
/*      */     {
/*  774 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  779 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  784 */     ResultSet set = null;
/*  785 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  786 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  788 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  789 */       if (set.next()) { String str1;
/*  790 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  791 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  794 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  799 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  802 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  804 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  808 */     StringBuffer rca = new StringBuffer();
/*  809 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  810 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  813 */     int rcalength = key.length();
/*  814 */     String split = "6. ";
/*  815 */     int splitPresent = key.indexOf(split);
/*  816 */     String div1 = "";String div2 = "";
/*  817 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  819 */       if (rcalength > 180) {
/*  820 */         rca.append("<span class=\"rca-critical-text\">");
/*  821 */         getRCATrimmedText(key, rca);
/*  822 */         rca.append("</span>");
/*      */       } else {
/*  824 */         rca.append("<span class=\"rca-critical-text\">");
/*  825 */         rca.append(key);
/*  826 */         rca.append("</span>");
/*      */       }
/*  828 */       return rca.toString();
/*      */     }
/*  830 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  831 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  832 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  833 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  834 */     getRCATrimmedText(div1, rca);
/*  835 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  838 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  839 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  840 */     getRCATrimmedText(div2, rca);
/*  841 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  843 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  848 */     String[] st = msg.split("<br>");
/*  849 */     for (int i = 0; i < st.length; i++) {
/*  850 */       String s = st[i];
/*  851 */       if (s.length() > 180) {
/*  852 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  854 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  858 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  859 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  861 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  865 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  866 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  867 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  870 */       if (key == null) {
/*  871 */         return ret;
/*      */       }
/*      */       
/*  874 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  875 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  878 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  879 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  880 */       set = AMConnectionPool.executeQueryStmt(query);
/*  881 */       if (set.next())
/*      */       {
/*  883 */         String helpLink = set.getString("LINK");
/*  884 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  887 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  893 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  912 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  903 */         if (set != null) {
/*  904 */           AMConnectionPool.closeStatement(set);
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
/*  918 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  932 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  933 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  935 */       String entityStr = (String)keys.nextElement();
/*  936 */       String mmessage = temp.getProperty(entityStr);
/*  937 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  938 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  940 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  945 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  955 */     String des = new String();
/*  956 */     while (str.indexOf(find) != -1) {
/*  957 */       des = des + str.substring(0, str.indexOf(find));
/*  958 */       des = des + replace;
/*  959 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  961 */     des = des + str;
/*  962 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  969 */       if (alert == null)
/*      */       {
/*  971 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  973 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  975 */         return "&nbsp;";
/*      */       }
/*      */       
/*  978 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  980 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  983 */       int rcalength = test.length();
/*  984 */       if (rcalength < 300)
/*      */       {
/*  986 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  990 */       StringBuffer out = new StringBuffer();
/*  991 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  992 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  993 */       out.append("</div>");
/*  994 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  995 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  996 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1001 */       ex.printStackTrace();
/*      */     }
/* 1003 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1009 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1014 */     ArrayList attribIDs = new ArrayList();
/* 1015 */     ArrayList resIDs = new ArrayList();
/* 1016 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1018 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1020 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1022 */       String resourceid = "";
/* 1023 */       String resourceType = "";
/* 1024 */       if (type == 2) {
/* 1025 */         resourceid = (String)row.get(0);
/* 1026 */         resourceType = (String)row.get(3);
/*      */       }
/* 1028 */       else if (type == 3) {
/* 1029 */         resourceid = (String)row.get(0);
/* 1030 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1033 */         resourceid = (String)row.get(6);
/* 1034 */         resourceType = (String)row.get(7);
/*      */       }
/* 1036 */       resIDs.add(resourceid);
/* 1037 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1038 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1040 */       String healthentity = null;
/* 1041 */       String availentity = null;
/* 1042 */       if (healthid != null) {
/* 1043 */         healthentity = resourceid + "_" + healthid;
/* 1044 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1047 */       if (availid != null) {
/* 1048 */         availentity = resourceid + "_" + availid;
/* 1049 */         entitylist.add(availentity);
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
/* 1063 */     Properties alert = getStatus(entitylist);
/* 1064 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1069 */     int size = monitorList.size();
/*      */     
/* 1071 */     String[] severity = new String[size];
/*      */     
/* 1073 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1075 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1076 */       String resourceName1 = (String)row1.get(7);
/* 1077 */       String resourceid1 = (String)row1.get(6);
/* 1078 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1079 */       if (severity[j] == null)
/*      */       {
/* 1081 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1085 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1087 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1089 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1092 */         if (sev > 0) {
/* 1093 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1094 */           monitorList.set(k, monitorList.get(j));
/* 1095 */           monitorList.set(j, t);
/* 1096 */           String temp = severity[k];
/* 1097 */           severity[k] = severity[j];
/* 1098 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1104 */     int z = 0;
/* 1105 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1108 */       int i = 0;
/* 1109 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1112 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1116 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1120 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1122 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1125 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1129 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1132 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1133 */       String resourceName1 = (String)row1.get(7);
/* 1134 */       String resourceid1 = (String)row1.get(6);
/* 1135 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1136 */       if (hseverity[j] == null)
/*      */       {
/* 1138 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1143 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1145 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1148 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1151 */         if (hsev > 0) {
/* 1152 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1153 */           monitorList.set(k, monitorList.get(j));
/* 1154 */           monitorList.set(j, t);
/* 1155 */           String temp1 = hseverity[k];
/* 1156 */           hseverity[k] = hseverity[j];
/* 1157 */           hseverity[j] = temp1;
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
/* 1169 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1170 */     boolean forInventory = false;
/* 1171 */     String trdisplay = "none";
/* 1172 */     String plusstyle = "inline";
/* 1173 */     String minusstyle = "none";
/* 1174 */     String haidTopLevel = "";
/* 1175 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1177 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1179 */         haidTopLevel = request.getParameter("haid");
/* 1180 */         forInventory = true;
/* 1181 */         trdisplay = "table-row;";
/* 1182 */         plusstyle = "none";
/* 1183 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1190 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1193 */     ArrayList listtoreturn = new ArrayList();
/* 1194 */     StringBuffer toreturn = new StringBuffer();
/* 1195 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1196 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1197 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1199 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1201 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1202 */       String childresid = (String)singlerow.get(0);
/* 1203 */       String childresname = (String)singlerow.get(1);
/* 1204 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1205 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1206 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1207 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1208 */       String unmanagestatus = (String)singlerow.get(5);
/* 1209 */       String actionstatus = (String)singlerow.get(6);
/* 1210 */       String linkclass = "monitorgp-links";
/* 1211 */       String titleforres = childresname;
/* 1212 */       String titilechildresname = childresname;
/* 1213 */       String childimg = "/images/trcont.png";
/* 1214 */       String flag = "enable";
/* 1215 */       String dcstarted = (String)singlerow.get(8);
/* 1216 */       String configMonitor = "";
/* 1217 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1218 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1220 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1222 */       if (singlerow.get(7) != null)
/*      */       {
/* 1224 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1226 */       String haiGroupType = "0";
/* 1227 */       if ("HAI".equals(childtype))
/*      */       {
/* 1229 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1231 */       childimg = "/images/trend.png";
/* 1232 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1233 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1234 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1236 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1238 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1240 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1241 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1244 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1246 */         linkclass = "disabledtext";
/* 1247 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1249 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1250 */       String availmouseover = "";
/* 1251 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1253 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1255 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1256 */       String healthmouseover = "";
/* 1257 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1259 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1262 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1263 */       int spacing = 0;
/* 1264 */       if (level >= 1)
/*      */       {
/* 1266 */         spacing = 40 * level;
/*      */       }
/* 1268 */       if (childtype.equals("HAI"))
/*      */       {
/* 1270 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1271 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1272 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1274 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1275 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1276 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1277 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1278 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1279 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1280 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1281 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1282 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1283 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1284 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1286 */         if (!forInventory)
/*      */         {
/* 1288 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1291 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1293 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1295 */           actions = editlink + actions;
/*      */         }
/* 1297 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1299 */           actions = actions + associatelink;
/*      */         }
/* 1301 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1302 */         String arrowimg = "";
/* 1303 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1305 */           actions = "";
/* 1306 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1307 */           checkbox = "";
/* 1308 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1310 */         if (isIt360)
/*      */         {
/* 1312 */           actionimg = "";
/* 1313 */           actions = "";
/* 1314 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1315 */           checkbox = "";
/*      */         }
/*      */         
/* 1318 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1320 */           actions = "";
/*      */         }
/* 1322 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1324 */           checkbox = "";
/*      */         }
/*      */         
/* 1327 */         String resourcelink = "";
/*      */         
/* 1329 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1331 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1335 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1338 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1339 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1340 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1341 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1342 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1343 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1344 */         if (!isIt360)
/*      */         {
/* 1346 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1350 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1353 */         toreturn.append("</tr>");
/* 1354 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1356 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1357 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1361 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1362 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1365 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1369 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1371 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1372 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1373 */             toreturn.append(assocMessage);
/* 1374 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1375 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1376 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1377 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1383 */         String resourcelink = null;
/* 1384 */         boolean hideEditLink = false;
/* 1385 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1387 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1388 */           hideEditLink = true;
/* 1389 */           if (isIt360)
/*      */           {
/* 1391 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1395 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1397 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1399 */           hideEditLink = true;
/* 1400 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1401 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1406 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1409 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1410 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1411 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1412 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1413 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1414 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1415 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1416 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1417 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1418 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1419 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1420 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1421 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1423 */         if (hideEditLink)
/*      */         {
/* 1425 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1427 */         if (!forInventory)
/*      */         {
/* 1429 */           removefromgroup = "";
/*      */         }
/* 1431 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1432 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1433 */           actions = actions + configcustomfields;
/*      */         }
/* 1435 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1437 */           actions = editlink + actions;
/*      */         }
/* 1439 */         String managedLink = "";
/* 1440 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1442 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1443 */           actions = "";
/* 1444 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1445 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1448 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1450 */           checkbox = "";
/*      */         }
/*      */         
/* 1453 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1455 */           actions = "";
/*      */         }
/* 1457 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1460 */         if (isIt360)
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1466 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1468 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1469 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1470 */         if (!isIt360)
/*      */         {
/* 1472 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1476 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1478 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1481 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1488 */       StringBuilder toreturn = new StringBuilder();
/* 1489 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1490 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1491 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1492 */       String title = "";
/* 1493 */       message = EnterpriseUtil.decodeString(message);
/* 1494 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1495 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1496 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1498 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1500 */       else if ("5".equals(severity))
/*      */       {
/* 1502 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1506 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1508 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1509 */       toreturn.append(v);
/*      */       
/* 1511 */       toreturn.append(link);
/* 1512 */       if (severity == null)
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1516 */       else if (severity.equals("5"))
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       else if (severity.equals("4"))
/*      */       {
/* 1522 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1524 */       else if (severity.equals("1"))
/*      */       {
/* 1526 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1531 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1533 */       toreturn.append("</a>");
/* 1534 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1538 */       ex.printStackTrace();
/*      */     }
/* 1540 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1547 */       StringBuilder toreturn = new StringBuilder();
/* 1548 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1549 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1550 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1551 */       if (message == null)
/*      */       {
/* 1553 */         message = "";
/*      */       }
/*      */       
/* 1556 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1557 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1559 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1560 */       toreturn.append(v);
/*      */       
/* 1562 */       toreturn.append(link);
/*      */       
/* 1564 */       if (severity == null)
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1568 */       else if (severity.equals("5"))
/*      */       {
/* 1570 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1572 */       else if (severity.equals("1"))
/*      */       {
/* 1574 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1579 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1581 */       toreturn.append("</a>");
/* 1582 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1588 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1591 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1592 */     if (invokeActions != null) {
/* 1593 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1594 */       while (iterator.hasNext()) {
/* 1595 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1596 */         if (actionmap.containsKey(actionid)) {
/* 1597 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1602 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1606 */     String actionLink = "";
/* 1607 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1608 */     String query = "";
/* 1609 */     ResultSet rs = null;
/* 1610 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1611 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1612 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1613 */       actionLink = "method=" + methodName;
/*      */     }
/* 1615 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1616 */       actionLink = methodName;
/*      */     }
/* 1618 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1619 */     Iterator itr = methodarglist.iterator();
/* 1620 */     boolean isfirstparam = true;
/* 1621 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1622 */     while (itr.hasNext()) {
/* 1623 */       HashMap argmap = (HashMap)itr.next();
/* 1624 */       String argtype = (String)argmap.get("TYPE");
/* 1625 */       String argname = (String)argmap.get("IDENTITY");
/* 1626 */       String paramname = (String)argmap.get("PARAMETER");
/* 1627 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1628 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1629 */         isfirstparam = false;
/* 1630 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1632 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1636 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1640 */         actionLink = actionLink + "&";
/*      */       }
/* 1642 */       String paramValue = null;
/* 1643 */       String tempargname = argname;
/* 1644 */       if (commonValues.getProperty(tempargname) != null) {
/* 1645 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1648 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1649 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1650 */           if (dbType.equals("mysql")) {
/* 1651 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1654 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1656 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1658 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1659 */             if (rs.next()) {
/* 1660 */               paramValue = rs.getString("VALUE");
/* 1661 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1665 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1669 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1672 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1677 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1678 */           paramValue = rowId;
/*      */         }
/* 1680 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1681 */           paramValue = managedObjectName;
/*      */         }
/* 1683 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1684 */           paramValue = resID;
/*      */         }
/* 1686 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1687 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1690 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1692 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1693 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1694 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1696 */     return actionLink;
/*      */   }
/*      */   
/* 1699 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1700 */     String dependentAttribute = null;
/* 1701 */     String align = "left";
/*      */     
/* 1703 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1704 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1705 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1706 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1707 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1708 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1709 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1710 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1711 */       align = "center";
/*      */     }
/*      */     
/* 1714 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1715 */     String actualdata = "";
/*      */     
/* 1717 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1718 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1719 */         actualdata = availValue;
/*      */       }
/* 1721 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1722 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1726 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1727 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1730 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1736 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1737 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1738 */       toreturn.append("<table>");
/* 1739 */       toreturn.append("<tr>");
/* 1740 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1741 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1742 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1743 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1744 */         String toolTip = "";
/* 1745 */         String hideClass = "";
/* 1746 */         String textStyle = "";
/* 1747 */         boolean isreferenced = true;
/* 1748 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1749 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1750 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1751 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1753 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1754 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1755 */           while (valueList.hasMoreTokens()) {
/* 1756 */             String dependentVal = valueList.nextToken();
/* 1757 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1758 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1759 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1761 */               toolTip = "";
/* 1762 */               hideClass = "";
/* 1763 */               isreferenced = false;
/* 1764 */               textStyle = "disabledtext";
/* 1765 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1769 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1770 */           toolTip = "";
/* 1771 */           hideClass = "";
/* 1772 */           isreferenced = false;
/* 1773 */           textStyle = "disabledtext";
/* 1774 */           if (dependentImageMap != null) {
/* 1775 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1776 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1779 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1783 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1784 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1785 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1786 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1787 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1788 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1790 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1791 */           if (isreferenced) {
/* 1792 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1796 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1797 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1798 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1799 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1800 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1801 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1803 */           toreturn.append("</span>");
/* 1804 */           toreturn.append("</a>");
/* 1805 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1808 */       toreturn.append("</tr>");
/* 1809 */       toreturn.append("</table>");
/* 1810 */       toreturn.append("</td>");
/*      */     } else {
/* 1812 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1815 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1819 */     String colTime = null;
/* 1820 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1821 */     if ((rows != null) && (rows.size() > 0)) {
/* 1822 */       Iterator<String> itr = rows.iterator();
/* 1823 */       String maxColQuery = "";
/* 1824 */       for (;;) { if (itr.hasNext()) {
/* 1825 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1826 */           ResultSet maxCol = null;
/*      */           try {
/* 1828 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1829 */             while (maxCol.next()) {
/* 1830 */               if (colTime == null) {
/* 1831 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1834 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1843 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1845 */               if (maxCol != null)
/* 1846 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1848 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1843 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1845 */               if (maxCol != null)
/* 1846 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1848 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1853 */     return colTime;
/*      */   }
/*      */   
/* 1856 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1857 */     tablename = null;
/* 1858 */     ResultSet rsTable = null;
/* 1859 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1861 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1862 */       while (rsTable.next()) {
/* 1863 */         tablename = rsTable.getString("DATATABLE");
/* 1864 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1865 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1878 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1869 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1872 */         if (rsTable != null)
/* 1873 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1875 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1881 */     String argsList = "";
/* 1882 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1884 */       if (showArgsMap.get(row) != null) {
/* 1885 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1886 */         if (showArgslist != null) {
/* 1887 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1888 */             if (argsList.trim().equals("")) {
/* 1889 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1892 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1899 */       e.printStackTrace();
/* 1900 */       return "";
/*      */     }
/* 1902 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1907 */     String argsList = "";
/* 1908 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1911 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1913 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1914 */         if (hideArgsList != null)
/*      */         {
/* 1916 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1918 */             if (argsList.trim().equals(""))
/*      */             {
/* 1920 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1924 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1932 */       ex.printStackTrace();
/*      */     }
/* 1934 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1938 */     StringBuilder toreturn = new StringBuilder();
/* 1939 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1946 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1947 */       Iterator itr = tActionList.iterator();
/* 1948 */       while (itr.hasNext()) {
/* 1949 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1950 */         String confirmmsg = "";
/* 1951 */         String link = "";
/* 1952 */         String isJSP = "NO";
/* 1953 */         HashMap tactionMap = (HashMap)itr.next();
/* 1954 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1955 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1956 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1957 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1958 */           (actionmap.containsKey(actionId))) {
/* 1959 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1960 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1961 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1962 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1963 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1965 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1971 */           if (isTableAction) {
/* 1972 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1975 */             tableName = "Link";
/* 1976 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1977 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1978 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1979 */             toreturn.append("</a></td>");
/*      */           }
/* 1981 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1982 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1983 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1984 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1990 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1996 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1998 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1999 */       Properties prop = (Properties)node.getUserObject();
/* 2000 */       String mgID = prop.getProperty("label");
/* 2001 */       String mgName = prop.getProperty("value");
/* 2002 */       String isParent = prop.getProperty("isParent");
/* 2003 */       int mgIDint = Integer.parseInt(mgID);
/* 2004 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 2006 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 2008 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 2009 */       if (node.getChildCount() > 0)
/*      */       {
/* 2011 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2013 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2015 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2017 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2021 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2026 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2028 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2030 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2032 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2036 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2039 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2040 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2042 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2046 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2048 */       if (node.getChildCount() > 0)
/*      */       {
/* 2050 */         builder.append("<UL>");
/* 2051 */         printMGTree(node, builder);
/* 2052 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2057 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2058 */     StringBuffer toReturn = new StringBuffer();
/* 2059 */     String table = "-";
/*      */     try {
/* 2061 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2062 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2063 */       float total = 0.0F;
/* 2064 */       while (it.hasNext()) {
/* 2065 */         String attName = (String)it.next();
/* 2066 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2067 */         boolean roundOffData = false;
/* 2068 */         if ((data != null) && (!data.equals(""))) {
/* 2069 */           if (data.indexOf(",") != -1) {
/* 2070 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2073 */             float value = Float.parseFloat(data);
/* 2074 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2077 */             total += value;
/* 2078 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2081 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2086 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2087 */       while (attVsWidthList.hasNext()) {
/* 2088 */         String attName = (String)attVsWidthList.next();
/* 2089 */         String data = (String)attVsWidthProps.get(attName);
/* 2090 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2091 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2092 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2093 */         String className = (String)graphDetails.get("ClassName");
/* 2094 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2095 */         if (percentage < 1.0F)
/*      */         {
/* 2097 */           data = percentage + "";
/*      */         }
/* 2099 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2101 */       if (toReturn.length() > 0) {
/* 2102 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2106 */       e.printStackTrace();
/*      */     }
/* 2108 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2114 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2115 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2116 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2117 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2118 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2119 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2120 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2121 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2122 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2125 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2126 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2127 */       splitvalues[0] = multiplecondition.toString();
/* 2128 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2131 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2136 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2137 */     if (thresholdType != 3) {
/* 2138 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2139 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2140 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2141 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2142 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2143 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2145 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2146 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2147 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2148 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2149 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2150 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2152 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2153 */     if (updateSelected != null) {
/* 2154 */       updateSelected[0] = "selected";
/*      */     }
/* 2156 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2161 */       StringBuffer toreturn = new StringBuffer("");
/* 2162 */       if (commaSeparatedMsgId != null) {
/* 2163 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2164 */         int count = 0;
/* 2165 */         while (msgids.hasMoreTokens()) {
/* 2166 */           String id = msgids.nextToken();
/* 2167 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2168 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2169 */           count++;
/* 2170 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2171 */             if (toreturn.length() == 0) {
/* 2172 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2174 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2175 */             if (!image.trim().equals("")) {
/* 2176 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2178 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2179 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2182 */         if (toreturn.length() > 0) {
/* 2183 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2187 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2190 */       e.printStackTrace(); }
/* 2191 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ArrayList getMGroupsCreatedInAdminServer(ArrayList aListOfAllMonitorGroups)
/*      */   {
/* 2200 */     ArrayList aListAdminMonitorGrps = new ArrayList();
/*      */     try {
/* 2202 */       for (int i = 0; i < aListOfAllMonitorGroups.size(); i++) {
/* 2203 */         ArrayList innerList = (ArrayList)aListOfAllMonitorGroups.get(i);
/* 2204 */         if ((innerList != null) && (innerList.size() >= 2))
/*      */         {
/*      */           try
/*      */           {
/* 2208 */             String strMgId = (String)innerList.get(1);
/* 2209 */             int mgId = Integer.parseInt(strMgId);
/* 2210 */             if (mgId < 10000000) {
/* 2211 */               aListAdminMonitorGrps.add(innerList);
/*      */             }
/* 2213 */             String grpCreatedMasName = CommDBUtil.getManagedServerNameWithPort(strMgId);
/* 2214 */             innerList.add(grpCreatedMasName);
/*      */           }
/*      */           catch (Exception ex1) {}
/*      */         }
/*      */       }
/*      */     } catch (Exception ex2) {
/* 2220 */       ex2.printStackTrace();
/*      */     }
/* 2222 */     return aListAdminMonitorGrps;
/*      */   }
/*      */   
/* 2225 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2231 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2232 */   static { _jspx_dependants.put("/jsp/includes/monitorGroups.jsp", Long.valueOf(1473429417000L));
/* 2233 */     _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2234 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2235 */     _jspx_dependants.put("/jsp/includes/MonitorDiscoveryStatus.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2267 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2271 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2276 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2277 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2278 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2279 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2280 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2281 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2282 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2287 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2288 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2289 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2290 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2291 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2295 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2296 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2300 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2301 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2302 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2303 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2305 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2306 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2307 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.release();
/* 2308 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2309 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2310 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2311 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2312 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2313 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2314 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2315 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2316 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2317 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2319 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.release();
/* 2320 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2321 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty.release();
/* 2322 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.release();
/* 2323 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2330 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2333 */     JspWriter out = null;
/* 2334 */     Object page = this;
/* 2335 */     JspWriter _jspx_out = null;
/* 2336 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2340 */       response.setContentType("text/html;charset=UTF-8");
/* 2341 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2343 */       _jspx_page_context = pageContext;
/* 2344 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2345 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2346 */       session = pageContext.getSession();
/* 2347 */       out = pageContext.getOut();
/* 2348 */       _jspx_out = out;
/*      */       
/* 2350 */       out.write("<!DOCTYPE html>\n");
/* 2351 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2352 */       out.write(10);
/*      */       
/* 2354 */       request.setAttribute("HelpKey", "Adding Web Service");
/*      */       
/* 2356 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2357 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2359 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2360 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2361 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2363 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2365 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2367 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2369 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2370 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2371 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2372 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2375 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2376 */         String available = null;
/* 2377 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2378 */         out.write(10);
/*      */         
/* 2380 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2381 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2382 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2384 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2386 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2388 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2390 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2391 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2392 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2393 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2396 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2397 */           String unavailable = null;
/* 2398 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2399 */           out.write(10);
/*      */           
/* 2401 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2402 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2403 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2405 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2407 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2409 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2411 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2412 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2413 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2414 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2417 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2418 */             String unmanaged = null;
/* 2419 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2420 */             out.write(10);
/*      */             
/* 2422 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2423 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2424 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2426 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2428 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2430 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2432 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2433 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2434 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2435 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2438 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2439 */               String scheduled = null;
/* 2440 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2441 */               out.write(10);
/*      */               
/* 2443 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2444 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2445 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2447 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2449 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2451 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2453 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2454 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2455 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2456 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2459 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2460 */                 String critical = null;
/* 2461 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2462 */                 out.write(10);
/*      */                 
/* 2464 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2465 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2466 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2468 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2470 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2472 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2474 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2475 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2476 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2477 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2480 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2481 */                   String clear = null;
/* 2482 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2483 */                   out.write(10);
/*      */                   
/* 2485 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2486 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2487 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2489 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2491 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2493 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2495 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2496 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2497 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2498 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2501 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2502 */                     String warning = null;
/* 2503 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2504 */                     out.write(10);
/* 2505 */                     out.write(10);
/*      */                     
/* 2507 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2508 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2510 */                     out.write(10);
/* 2511 */                     out.write(10);
/* 2512 */                     out.write(10);
/* 2513 */                     out.write(10);
/* 2514 */                     ManagedApplication mo = null;
/* 2515 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2516 */                     if (mo == null) {
/* 2517 */                       mo = new ManagedApplication();
/* 2518 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2520 */                     out.write(10);
/* 2521 */                     Hashtable applications = null;
/* 2522 */                     synchronized (application) {
/* 2523 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2524 */                       if (applications == null) {
/* 2525 */                         applications = new Hashtable();
/* 2526 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2529 */                     out.write(10);
/* 2530 */                     out.write(10);
/* 2531 */                     out.write(10);
/* 2532 */                     out.write("<!--$Id$-->\n\n\n\n");
/*      */                     
/*      */                     try
/*      */                     {
/* 2536 */                       boolean isprivilege = false;
/* 2537 */                       if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/* 2538 */                         isprivilege = true;
/*      */                       }
/* 2540 */                       request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*      */                       
/*      */ 
/* 2543 */                       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2544 */                       if (dynamicSites != null)
/*      */                       {
/* 2546 */                         request.setAttribute("dynamicSites", dynamicSites);
/*      */                       }
/*      */                       
/* 2549 */                       ArrayList mgList = new ArrayList();
/* 2550 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*      */                       {
/* 2552 */                         AMActionForm form = (AMActionForm)request.getAttribute("AMActionForm");
/* 2553 */                         ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                         
/* 2555 */                         if (orgs != null)
/*      */                         {
/* 2557 */                           request.setAttribute("customers", orgs);
/*      */                         }
/*      */                         
/*      */ 
/* 2561 */                         if (form != null)
/*      */                         {
/* 2563 */                           String customerName = form.getOrganization();
/* 2564 */                           if (customerName != null)
/*      */                           {
/* 2566 */                             mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/* 2573 */                       else if (isprivilege)
/*      */                       {
/* 2575 */                         mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2579 */                         mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*      */                       }
/*      */                       
/* 2582 */                       if (mgList != null)
/*      */                       {
/* 2584 */                         request.setAttribute("applications", mgList);
/* 2585 */                         if (EnterpriseUtil.isAdminServer()) {
/* 2586 */                           ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/* 2587 */                           request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/* 2588 */                           request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2594 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2597 */                     out.write(10);
/* 2598 */                     out.write(10);
/* 2599 */                     out.write(10);
/*      */                     
/* 2601 */                     String haid = request.getParameter("haid");
/* 2602 */                     String title = request.getParameter("type");
/*      */                     
/* 2604 */                     out.write("\n\n<script>\n\nfunction loadSite()\n{\n\tdocument.AMActionForm.haid.options.length=0;\n\tvar formCustomerId = document.AMActionForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.AMActionForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2605 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                       return;
/* 2607 */                     out.write("\n}\n\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.AMActionForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.AMActionForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.AMActionForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 2608 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2609 */                     out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.AMActionForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.AMActionForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 2610 */                     out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2611 */                     out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 2612 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2613 */                     out.write("\");\t\t\n\t\tdocument.AMActionForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.AMActionForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 2614 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2615 */                     out.write("\");\n\t\t\tdocument.AMActionForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.AMActionForm.subgroupname.value;\n\t\t\tvar haid=document.AMActionForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[document.AMActionForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n");
/* 2616 */                     out.write("\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\nfunction formReload()\n{\n    var v=document.AMActionForm.type.value;\n    //alert(v);\n    document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2617 */                     out.print(request.getParameter("hideFieldsForIT360"));
/* 2618 */                     out.write("\";\n    document.AMActionForm.submit();\n\n}\n\nfunction toggleAdvanced()\n{\n\tif (document.all) newDisplay = \"block\"; //IE4+ specific code\n        else newDisplay = \"table-row\"; //Netscape and Mozilla\n\n\tvar curdis = document.getElementById(\"timeout\").style.display;\n\tif(curdis=='none')\n\t{\n\t\tdocument.getElementById(\"timeout\").style.display=newDisplay;\n\t\tdocument.getElementById(\"username\").style.display=newDisplay;\n\t\tdocument.getElementById(\"password\").style.display=newDisplay;\n\t}\n\telse\n\t{\n\t\tdocument.getElementById(\"timeout\").style.display='none';\n\t\tdocument.getElementById(\"username\").style.display='none';\n\t\tdocument.getElementById(\"password\").style.display='none';\n\t}\n}\nfunction validateAndSubmit(value)\n{\n    var pollinterval = document.AMActionForm.pollInterval.value;\n    if(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n    {\n        alert(\"");
/* 2619 */                     out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2620 */                     out.write("\");\n        document.AMActionForm.pollInterval.focus();\n        return;\n    }\n\n    var wsdlurl = document.AMActionForm.WSDLUrl.value;\n    if(trimAll(wsdlurl) == '')\n    {\n        alert(\"");
/* 2621 */                     out.print(FormatUtil.getString("am.webclient.wsm.emptywsdl.text"));
/* 2622 */                     out.write("\");\n        return;\n    }\n    if(trimAll(document.AMActionForm.displayname.value) == '')\n\t{\n        alert(\"");
/* 2623 */                     out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2624 */                     out.write("\");\n\t\tdocument.AMActionForm.displayname.select();\n\t\treturn;\n\t}\n\tdocument.AMActionForm.addtoha.value=\"true\";\n    ");
/*      */                     
/* 2626 */                     if (EnterpriseUtil.isAdminServer())
/*      */                     {
/*      */ 
/* 2629 */                       out.write("                                \n        if (document.AMActionForm.masSelectType[1].checked) {\n        \tvar selectedVal=document.AMActionForm.masGroupName.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/* 2630 */                       out.print(FormatUtil.getString("am.webclient.admin.add.monitor.select.masgroup.text"));
/* 2631 */                       out.write("');\n                document.AMActionForm.masGroupName.focus();\n                return;\n            }                                \t\n        } else if (document.AMActionForm.masSelectType[2].checked) {\n        \tselectedVal=document.AMActionForm.selectedServer.value;\n        \tif (selectedVal != null && selectedVal == \"-\") {\n                alert('");
/* 2632 */                       out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2633 */                       out.write("');\n                document.AMActionForm.selectedServer.focus();\n                return;\n            }                                 \t\n        }\n    ");
/*      */                     }
/*      */                     
/*      */ 
/* 2637 */                     out.write(" \n    \n\tif(value==3)\n\t{\n\t  document.AMActionForm.action=\"/wsm.do?method=addMonitor&value=3&hideFieldsForIT360=");
/* 2638 */                     out.print(request.getParameter("hideFieldsForIT360"));
/* 2639 */                     out.write("\";\n\t}\n\telse\n\t{\n\t\tdocument.AMActionForm.action=\"/wsm.do?method=addMonitor&hideFieldsForIT360=");
/* 2640 */                     out.print(request.getParameter("hideFieldsForIT360"));
/* 2641 */                     out.write("\";\n\t}\n\n\t//var escapedurl = escape(document.AMActionForm.WSDLUrl.value);\n\t//document.AMActionForm.WSDLUrl.value = escapedurl;\n\n\n /***  IT360-1762 ISSUES CHANGES STARTS HERE ***/\n\n\t");
/* 2642 */                     if (EnterpriseUtil.isIt360MSPEdition()) {
/* 2643 */                       out.write("\n\n    if (document.AMActionForm.organization.value== \"-\")\n     {\n    \talert(\"");
/* 2644 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checkcustomer"));
/* 2645 */                       out.write("\");\n    \treturn;\n     }\n\n    if (trimAll(document.AMActionForm.haid.value) == '' || document.AMActionForm.haid.value== \"-\")\n     {\n        alert(\"");
/* 2646 */                       out.print(FormatUtil.getString("it360.addnewmonitor.err.checksite"));
/* 2647 */                       out.write("\");\n        return;\n     }\n    ");
/*      */                     }
/* 2649 */                     out.write("\n\n/***  IT360-1762 ISSUES CHANGES ENDS HERE ***/\n\n\n    document.AMActionForm.submit();\n}\n\nfunction checkSpecialchar(value)\n{\n    var iChars =\"\\'\";\n    for (var i = 0; i < value.length; i++)\n    {\n  \tif (iChars.indexOf(value.charAt(i)) != -1)\n        {\n            return false;\n  \t}\n    }\n}\n\n\n\t\n\nfunction myOnLoad(){\n\tif(");
/* 2650 */                     out.print(EnterpriseUtil.isAdminServer());
/* 2651 */                     out.write(") {\n\t\tvar radioObj = document.AMActionForm.masSelectType;\n\t\tif (radioObj !=null && radioObj != \"undefined\") {\n\t\t\tvar val='0';\n\t\t\tif (radioObj[1].checked) {\n\t\t\t\tval='1';\n\t\t\t} else if (radioObj[2].checked){\n\t\t\t\tval='2';\n\t\t\t}\n\t\t\tshowAsPerSelection(val);\n\t\t}\t\n\t}\n}\n</script>\n");
/*      */                     
/* 2653 */                     boolean isDiscoveryComplete = false;
/* 2654 */                     boolean isDiscoverySuccess = false;
/*      */                     
/* 2656 */                     String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2657 */                     if (hideFieldsForIT360 == null)
/*      */                     {
/* 2659 */                       hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                     }
/* 2661 */                     boolean hideFields = false;
/* 2662 */                     String hideStyle = "";
/* 2663 */                     if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                     {
/* 2665 */                       hideFields = true;
/* 2666 */                       hideStyle = "hideContent";
/*      */                     }
/*      */                     
/* 2669 */                     out.write("\n<body>\n    ");
/*      */                     
/* 2671 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2672 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2673 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2675 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2676 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2677 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2679 */                         out.write("\n    ");
/* 2680 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2682 */                         out.write("\n    ");
/* 2683 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2685 */                         out.write("\n    \n    ");
/*      */                         
/* 2687 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2688 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2689 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2691 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2693 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2694 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2695 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2696 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2697 */                             out = _jspx_page_context.pushBody();
/* 2698 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2699 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2702 */                             out.write(10);
/* 2703 */                             out.write(10);
/* 2704 */                             out.write(9);
/*      */                             
/* 2706 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.get(FormTag.class);
/* 2707 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2708 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2710 */                             _jspx_th_html_005fform_005f0.setAction("/wsm");
/*      */                             
/* 2712 */                             _jspx_th_html_005fform_005f0.setFocus("WSDLUrl");
/*      */                             
/* 2714 */                             _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 2715 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2716 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2718 */                                 out.write("\n\t<input type=\"hidden\" name=\"addtoha\" value=\"");
/* 2719 */                                 out.print(request.getParameter("addtoha"));
/* 2720 */                                 out.write("\">\n\t");
/*      */                                 
/* 2722 */                                 NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2723 */                                 _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2724 */                                 _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2726 */                                 _jspx_th_logic_005fnotEmpty_005f0.setName("discoverystatus");
/* 2727 */                                 int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2728 */                                 if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                   for (;;) {
/* 2730 */                                     out.write(32);
/* 2731 */                                     out.write(32);
/* 2732 */                                     out.write("<!--$Id$-->\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/diagnosepage.js\"></SCRIPT>\n");
/*      */                                     
/* 2734 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2735 */                                     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2736 */                                     _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                     
/* 2738 */                                     _jspx_th_logic_005fnotEmpty_005f1.setName("discoverystatus");
/* 2739 */                                     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2740 */                                     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                       for (;;) {
/* 2742 */                                         out.write(10);
/*      */                                         
/*      */ 
/* 2745 */                                         if ((request.getAttribute("type") == null) || ((!request.getAttribute("type").equals("Script Monitor")) && ((request.getAttribute("basetype") == null) || ((request.getAttribute("basetype") != null) && (!request.getAttribute("basetype").equals("Script Monitor")))) && (!request.getAttribute("type").equals("QENGINE")) && (!request.getAttribute("type").equals("Web Service")) && (!request.getAttribute("type").equals("file")) && (!request.getAttribute("type").equals("directory")) && (!request.getAttribute("type").equals("File System Monitor")) && (!request.getAttribute("type").equals("Ping Monitor")) && (!request.getAttribute("type").equals("SAP-CCMS"))))
/*      */                                         {
/*      */ 
/* 2748 */                                           out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n  <tr>\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2749 */                                           out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2750 */                                           out.write("</span> </td>\n    <td width=\"7%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2751 */                                           out.print(FormatUtil.getString("am.webclient.intro.prerequisistes.smtpserverport"));
/* 2752 */                                           out.write("\n      </span> </td>\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2753 */                                           out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2754 */                                           out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2755 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2756 */                                           out.write("\n </span></td>\n  <tr>\n  ");
/*      */                                           
/* 2758 */                                           int failedNumber = 1;
/*      */                                           
/* 2760 */                                           out.write(10);
/*      */                                           
/* 2762 */                                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2763 */                                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2764 */                                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                           
/* 2766 */                                           _jspx_th_logic_005fiterate_005f0.setName("discoverystatus");
/*      */                                           
/* 2768 */                                           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                           
/* 2770 */                                           _jspx_th_logic_005fiterate_005f0.setIndexId("i");
/*      */                                           
/* 2772 */                                           _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2773 */                                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2774 */                                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2775 */                                             ArrayList row = null;
/* 2776 */                                             Integer i = null;
/* 2777 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2778 */                                               out = _jspx_page_context.pushBody();
/* 2779 */                                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2780 */                                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                             }
/* 2782 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2783 */                                             i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                                             for (;;) {
/* 2785 */                                               out.write("\n<tr>\n<td height=\"18\" class=\"bodytext\">");
/* 2786 */                                               out.print(row.get(0));
/* 2787 */                                               out.write("</td>\n<td height=\"18\" class=\"bodytext\">");
/* 2788 */                                               out.print(row.get(1));
/* 2789 */                                               out.write("</td>\n\n    <td height=\"18\" class=\"bodytext\">\n      ");
/*      */                                               
/* 2791 */                                               if ((row.get(2).equals("Success")) || (row.get(2).equals("Present")))
/*      */                                               {
/* 2793 */                                                 request.setAttribute("isDiscoverySuccess", "true");
/*      */                                                 
/* 2795 */                                                 out.write("\n      <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2796 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2797 */                                                 out.write("\" align=\"absmiddle\">\n      ");
/*      */ 
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 2802 */                                                 request.setAttribute("isDiscoverySuccess", "false");
/*      */                                                 
/* 2804 */                                                 out.write("\n      <img alt=\"");
/* 2805 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.notiniatedimagetip.text"));
/* 2806 */                                                 out.write("\" src=\"/images/icon_monitor_failure.gif\" align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                               }
/*      */                                               
/*      */ 
/* 2810 */                                               out.write("\n      <span class=\"bodytextbold\">");
/* 2811 */                                               out.print(FormatUtil.getString(String.valueOf(row.get(2))));
/* 2812 */                                               out.write("</span> </td>\n\n      ");
/*      */                                               
/* 2814 */                                               pageContext.setAttribute("ret_msg", String.valueOf(row.get(3)));
/*      */                                               
/* 2816 */                                               out.write("\n                     ");
/*      */                                               
/* 2818 */                                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2819 */                                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2820 */                                               _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 2822 */                                               _jspx_th_c_005fif_005f3.setTest("${ret_msg=='Monitoring Initiated Succesfully'}");
/* 2823 */                                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2824 */                                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                                 for (;;) {
/* 2826 */                                                   out.write("\n                           <td height=\"18\" class=\"bodytext\">");
/* 2827 */                                                   out.print(FormatUtil.getString("am.webclient.discovery.host.successfullyiniated.text"));
/* 2828 */                                                   out.write("\n                                 ");
/* 2829 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2830 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2834 */                                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2835 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                               }
/*      */                                               
/* 2838 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2839 */                                               out.write("\n                                       ");
/*      */                                               
/* 2841 */                                               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2842 */                                               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2843 */                                               _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                               
/* 2845 */                                               _jspx_th_c_005fif_005f4.setTest("${ret_msg!='Monitoring Initiated Succesfully'}");
/* 2846 */                                               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2847 */                                               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                                 for (;;) {
/* 2849 */                                                   out.write("\n                                             <td height=\"18\" class=\"bodytext\">");
/* 2850 */                                                   out.print(row.get(3));
/* 2851 */                                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                   
/* 2853 */                                                   if ((request.getAttribute("type") != null) && (!request.getAttribute("type").equals("All")) && (!request.getAttribute("type").equals("TELNET")) && (!request.getAttribute("type").equals("SNMP")) && (!request.getAttribute("type").equals("SERVICE")) && (!request.getAttribute("type").equals("JMX1.2-MX4J-RMI")) && (!request.getAttribute("type").equals("JDK1.5")) && (!request.getAttribute("type").equals("RMI")) && (!request.getAttribute("type").equals("PHP")) && (!request.getAttribute("type").equals("MAIL")) && (!request.getAttribute("type").equals("ORACLEDB")) && (!request.getAttribute("type").equals("MYSQLDB")) && (!request.getAttribute("type").equals("MSSQLDB")) && (!request.getAttribute("type").equals("DB2")) && (!request.getAttribute("type").equals("WTA")) && (!request.getAttribute("type").equals("SAP")))
/*      */                                                   {
/* 2855 */                                                     if (((String)pageContext.getAttribute("ret_msg")).indexOf(FormatUtil.getString("am.webclient.discovery.host.monitoradded.text")) == -1)
/*      */                                                     {
/* 2857 */                                                       String fWhr = request.getParameter("hideFieldsForIT360");
/* 2858 */                                                       if (fWhr == null)
/*      */                                                       {
/* 2860 */                                                         fWhr = (String)request.getAttribute("hideFieldsForIT360");
/*      */                                                       }
/*      */                                                       
/* 2863 */                                                       if (((fWhr == null) || (!fWhr.equals("true"))) && 
/* 2864 */                                                         (!request.getAttribute("type").equals("SYBASEDB")))
/*      */                                                       {
/* 2866 */                                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t &nbsp;&nbsp;&nbsp;&nbsp;<a class=\"bodytext\" href=\"javascript:void(0)\" onClick=\"getAllDetailsOfHost()\">");
/* 2867 */                                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.diagnose.link"));
/* 2868 */                                                         out.write("</a>\n\t\t\t\t\t\t\t\t\t\t\t ");
/*      */                                                       }
/*      */                                                     } }
/* 2871 */                                                   if ((request.getAttribute("showSupportMessage") != null) && (failedNumber == 1) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */                                                   {
/* 2873 */                                                     failedNumber++;
/*      */                                                     
/*      */ 
/* 2876 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t\t<br>");
/* 2877 */                                                     out.print(FormatUtil.getString("am.webclient.discovery.host.support.link", new String[] { OEMUtil.getOEMString("product.talkback.mailid"), OEMUtil.getOEMString("product.tollfree.number") }));
/* 2878 */                                                     out.write("\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                                   }
/* 2880 */                                                   out.write("\n                                                   ");
/* 2881 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2882 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 2886 */                                               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2887 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                               }
/*      */                                               
/* 2890 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2891 */                                               out.write(10);
/* 2892 */                                               out.write(10);
/* 2893 */                                               out.write(10);
/*      */                                               
/*      */ 
/* 2896 */                                               if (row.size() > 4)
/*      */                                               {
/*      */ 
/* 2899 */                                                 out.write("<br>\n");
/* 2900 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)row.get(4) }));
/* 2901 */                                                 out.write(10);
/*      */                                               }
/*      */                                               
/*      */ 
/* 2905 */                                               out.write("\n</td>\n\n</tr>\n");
/* 2906 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2907 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2908 */                                               i = (Integer)_jspx_page_context.findAttribute("i");
/* 2909 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 2912 */                                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2913 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 2916 */                                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2917 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                           }
/*      */                                           
/* 2920 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2921 */                                           out.write("\n</table>\n");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 2926 */                                           ArrayList al1 = (ArrayList)request.getAttribute("discoverystatus");
/*      */                                           
/* 2928 */                                           out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" class=\"grayfullborder\" style=\"margin-bottom:10px; line-height:16px;\">\n<tr>\n");
/* 2929 */                                           String mtype = (String)request.getAttribute("type");
/* 2930 */                                           out.write(10);
/* 2931 */                                           if (mtype.equals("File System Monitor")) {
/* 2932 */                                             out.write("\n  <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2933 */                                             out.print(FormatUtil.getString("File/Directory Name"));
/* 2934 */                                             out.write("</span> </td>\n");
/* 2935 */                                           } else if ((((String)request.getAttribute("type")).equals("Ping Monitor")) || (((String)request.getAttribute("type")).equals("SAP-CCMS"))) {
/* 2936 */                                             out.write("\n\t<td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2937 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.hostip.text"));
/* 2938 */                                             out.write("</span> </td>\n");
/*      */                                           } else {
/* 2940 */                                             out.write("\n    <td width=\"20%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2941 */                                             out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.name"));
/* 2942 */                                             out.write("</span> </td>\n");
/*      */                                           }
/* 2944 */                                           out.write("\n    <td width=\"11%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2945 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.status"));
/* 2946 */                                           out.write("</span></td>\n    <td width=\"62%\" height=\"19\" class=\"columnheadingb\"><span class=\"bodytextbold\">");
/* 2947 */                                           out.print(FormatUtil.getString("am.webclient.recent5alerts.columnheader.message"));
/* 2948 */                                           out.write("</span></td>\n  </tr>\n  <tr>\n   <td height=\"18\" class=\"bodytext\">");
/* 2949 */                                           out.print(al1.get(0));
/* 2950 */                                           out.write("</td>\n   <td height=\"18\" class=\"bodytext\">\n   ");
/*      */                                           
/* 2952 */                                           if (al1.get(1).equals("Success"))
/*      */                                           {
/* 2954 */                                             request.setAttribute("isDiscoverySuccess", "true");
/*      */                                             
/* 2956 */                                             out.write("\n   <img src=\"/images/icon_monitor_success.gif\" border=\"0\" alt=\"");
/* 2957 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2958 */                                             out.write("\" align=\"absmiddle\">\n    ");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2963 */                                             request.setAttribute("isDiscoverySuccess", "false");
/*      */                                             
/*      */ 
/* 2966 */                                             out.write("\n      <img  src=\"/images/icon_monitor_failure.gif\"  align=\"ABSMIDDLE\" border=\"0\">\n      ");
/*      */                                           }
/*      */                                           
/*      */ 
/* 2970 */                                           out.write("\n<span class=\"bodytextbold\">");
/* 2971 */                                           out.print(FormatUtil.getString(String.valueOf(al1.get(1))));
/* 2972 */                                           out.write("</span> </td>\n");
/*      */                                           
/* 2974 */                                           if (al1.get(1).equals("Success"))
/*      */                                           {
/* 2976 */                                             boolean isAdminServer = EnterpriseUtil.isAdminServer();
/* 2977 */                                             if (isAdminServer) {
/* 2978 */                                               String masDisplayName = (String)al1.get(3);
/* 2979 */                                               String format = FormatUtil.getString("am.webclient.admin.add.monitor.successfully.configured.text", new String[] { masDisplayName, "", (String)al1.get(2) });
/*      */                                               
/* 2981 */                                               out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\">");
/* 2982 */                                               out.print(format);
/* 2983 */                                               out.write("</td>\n");
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 2987 */                                               out.write("\t\t\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"> ");
/* 2988 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.iniatedimagetip.text"));
/* 2989 */                                               out.write("<br> ");
/* 2990 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupdetails.doyouwanttoview.text", new String[] { (String)al1.get(2) }));
/* 2991 */                                               out.write("</td>\n");
/*      */                                             }
/*      */                                             
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 2998 */                                             out.write("\n    <td width=\"62%\" height=\"19\" class=\"bodytext\"><span class=\"bodytext\">");
/* 2999 */                                             out.print(al1.get(2));
/* 3000 */                                             out.write("</span></td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3004 */                                           out.write("\n  </tr>\n</table>\n\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 3008 */                                         out.write(10);
/* 3009 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3010 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3014 */                                     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3015 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                     }
/*      */                                     
/* 3018 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3019 */                                     out.write(10);
/* 3020 */                                     out.write(10);
/* 3021 */                                     out.write(9);
/*      */                                     
/* 3023 */                                     String discSucc = (String)request.getAttribute("isDiscoverySuccess");
/* 3024 */                                     isDiscoveryComplete = true;
/* 3025 */                                     if ((discSucc != null) && (discSucc.equals("true")))
/*      */                                     {
/* 3027 */                                       isDiscoverySuccess = true;
/*      */                                     }
/*      */                                     
/* 3030 */                                     out.write(10);
/* 3031 */                                     int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3032 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3036 */                                 if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3037 */                                   this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                 }
/*      */                                 
/* 3040 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3041 */                                 out.write("\n\n<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">\n <input type=\"hidden\" name=\"method\" value=\"addMonitor\"/>\n </table>\n");
/*      */                                 
/* 3043 */                                 if ((!hideFields) || ((!isDiscoveryComplete) && (hideFields)))
/*      */                                 {
/*      */ 
/* 3046 */                                   out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"65%\" valign=\"top\">\n");
/* 3047 */                                   out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 3048 */                                   String message = (String)request.getAttribute("typemessage");
/*      */                                   
/* 3050 */                                   ManagedApplication mo1 = new ManagedApplication();
/* 3051 */                                   Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 3052 */                                   boolean isConfMonitor = false;
/* 3053 */                                   ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 3054 */                                   if (message != null)
/*      */                                   {
/* 3056 */                                     out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 3057 */                                     out.print(message);
/* 3058 */                                     out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3062 */                                   out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 3063 */                                   out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 3064 */                                   out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 3065 */                                   if ("UrlSeq".equals(request.getParameter("type"))) {
/* 3066 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3067 */                                     if (frm != null) {
/* 3068 */                                       frm.set("type", "UrlSeq");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3072 */                                   if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 3073 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 3074 */                                     if (frm != null) {
/* 3075 */                                       frm.set("type", "UrlMonitor");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 3079 */                                   if ("RBM".equals(request.getParameter("type"))) {
/* 3080 */                                     DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 3081 */                                     if (frm != null) {
/* 3082 */                                       frm.set("type", "RBM");
/*      */                                     }
/*      */                                   }
/*      */                                   
/*      */ 
/* 3087 */                                   out.write("\n\n    ");
/*      */                                   
/* 3089 */                                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3090 */                                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3091 */                                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 3093 */                                   _jspx_th_c_005fif_005f5.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 3094 */                                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3095 */                                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                     for (;;) {
/* 3097 */                                       out.write("\n     ");
/*      */                                       
/* 3099 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3100 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3101 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f5);
/*      */                                       
/* 3103 */                                       _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                       
/* 3105 */                                       _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                       
/* 3107 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                       
/* 3109 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 3110 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3111 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3112 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3113 */                                           out = _jspx_page_context.pushBody();
/* 3114 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3115 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3118 */                                           out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                           
/* 3120 */                                           if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                           {
/*      */ 
/*      */ 
/* 3124 */                                             out.write("\n\n\t <optgroup label=\"");
/* 3125 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3126 */                                             out.write("\">\n                                        \n                                        ");
/*      */                                             
/* 3128 */                                             OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3129 */                                             _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3130 */                                             _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3132 */                                             _jspx_th_html_005foption_005f0.setValue("AIX");
/* 3133 */                                             int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3134 */                                             if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3135 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3136 */                                                 out = _jspx_page_context.pushBody();
/* 3137 */                                                 _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3138 */                                                 _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3141 */                                                 out.print(FormatUtil.getString("AIX"));
/* 3142 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3143 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3146 */                                               if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3147 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3150 */                                             if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3151 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                             }
/*      */                                             
/* 3154 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3155 */                                             out.write("\n                                        ");
/*      */                                             
/* 3157 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3158 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3159 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3161 */                                             _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3162 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3163 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3164 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3165 */                                                 out = _jspx_page_context.pushBody();
/* 3166 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3167 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3170 */                                                 out.print(FormatUtil.getString("AS400/iSeries"));
/* 3171 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3172 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3175 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3176 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3179 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3180 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 3183 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3184 */                                             out.write("\n                                        ");
/*      */                                             
/* 3186 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3187 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3188 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3190 */                                             _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3191 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3192 */                                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3193 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3194 */                                                 out = _jspx_page_context.pushBody();
/* 3195 */                                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3196 */                                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3199 */                                                 out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3200 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3201 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3204 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3205 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3208 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3209 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                             }
/*      */                                             
/* 3212 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3213 */                                             out.write("\n                                        ");
/*      */                                             
/* 3215 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3216 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3217 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3219 */                                             _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3220 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3221 */                                             if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3222 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3223 */                                                 out = _jspx_page_context.pushBody();
/* 3224 */                                                 _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3225 */                                                 _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3228 */                                                 out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3229 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3230 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3233 */                                               if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3234 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3237 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3238 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 3241 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3242 */                                             out.write("\n                                        ");
/*      */                                             
/* 3244 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3245 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3246 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3248 */                                             _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3249 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3250 */                                             if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3251 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3252 */                                                 out = _jspx_page_context.pushBody();
/* 3253 */                                                 _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3254 */                                                 _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3257 */                                                 out.print(FormatUtil.getString("Linux"));
/* 3258 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3259 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3262 */                                               if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3263 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3266 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3267 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 3270 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3271 */                                             out.write("\n                                        ");
/*      */                                             
/* 3273 */                                             OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3274 */                                             _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3275 */                                             _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3277 */                                             _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3278 */                                             int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3279 */                                             if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3280 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3281 */                                                 out = _jspx_page_context.pushBody();
/* 3282 */                                                 _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3283 */                                                 _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3286 */                                                 out.print(FormatUtil.getString("Mac OS"));
/* 3287 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3288 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3291 */                                               if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3292 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3295 */                                             if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3296 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                             }
/*      */                                             
/* 3299 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3300 */                                             out.write("\n                                        ");
/*      */                                             
/* 3302 */                                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3303 */                                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3304 */                                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3306 */                                             _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3307 */                                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3308 */                                             if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3309 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3310 */                                                 out = _jspx_page_context.pushBody();
/* 3311 */                                                 _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3312 */                                                 _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3315 */                                                 out.print(FormatUtil.getString("Novell"));
/* 3316 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3317 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3320 */                                               if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3321 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3324 */                                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3325 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                             }
/*      */                                             
/* 3328 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3329 */                                             out.write("\n                                        ");
/*      */                                             
/* 3331 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3332 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3333 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3335 */                                             _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3336 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3337 */                                             if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3338 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3339 */                                                 out = _jspx_page_context.pushBody();
/* 3340 */                                                 _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3341 */                                                 _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3344 */                                                 out.print(FormatUtil.getString("Sun Solaris"));
/* 3345 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3346 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3349 */                                               if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3350 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3353 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3354 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 3357 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3358 */                                             out.write("\n                                        ");
/*      */                                             
/* 3360 */                                             OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3361 */                                             _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3362 */                                             _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3364 */                                             _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3365 */                                             int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3366 */                                             if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3367 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3368 */                                                 out = _jspx_page_context.pushBody();
/* 3369 */                                                 _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3370 */                                                 _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3373 */                                                 out.print(FormatUtil.getString("Windows"));
/* 3374 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3375 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3378 */                                               if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3379 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3382 */                                             if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3383 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                             }
/*      */                                             
/* 3386 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3387 */                                             out.write("\n                                        ");
/*      */                                             
/* 3389 */                                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3390 */                                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3391 */                                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3393 */                                             _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3394 */                                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3395 */                                             if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3396 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3397 */                                                 out = _jspx_page_context.pushBody();
/* 3398 */                                                 _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3399 */                                                 _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3402 */                                                 out.print(FormatUtil.getString("Windows Cluster"));
/* 3403 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3404 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3407 */                                               if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3408 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3411 */                                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3412 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                             }
/*      */                                             
/* 3415 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3416 */                                             out.write("\n                                        \n\n  ");
/*      */                                             
/* 3418 */                                             ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3419 */                                             if ((rows1 != null) && (rows1.size() > 0))
/*      */                                             {
/* 3421 */                                               for (int i = 0; i < rows1.size(); i++)
/*      */                                               {
/* 3423 */                                                 ArrayList row = (ArrayList)rows1.get(i);
/* 3424 */                                                 String res = (String)row.get(0);
/* 3425 */                                                 String dname = (String)row.get(1);
/* 3426 */                                                 String values = props.getProperty(res);
/* 3427 */                                                 if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                 {
/*      */ 
/* 3430 */                                                   out.write("\n\t\t\t\t");
/*      */                                                   
/* 3432 */                                                   OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3433 */                                                   _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3434 */                                                   _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                   
/* 3436 */                                                   _jspx_th_html_005foption_005f10.setValue(values);
/* 3437 */                                                   int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3438 */                                                   if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3439 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3440 */                                                       out = _jspx_page_context.pushBody();
/* 3441 */                                                       _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3442 */                                                       _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 3445 */                                                       out.write(32);
/* 3446 */                                                       out.print(FormatUtil.getString(dname));
/* 3447 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3448 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 3451 */                                                     if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3452 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 3455 */                                                   if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3456 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                   }
/*      */                                                   
/* 3459 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3460 */                                                   out.write("\n\t\t\t");
/*      */                                                 }
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 3466 */                                             String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                             
/* 3468 */                                             String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                             
/*      */ 
/* 3471 */                                             if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                             {
/*      */ 
/* 3474 */                                               categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3475 */                                               categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                             }
/* 3477 */                                             for (int c = 0; c < categoryLink.length; c++)
/*      */                                             {
/* 3479 */                                               ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3480 */                                               if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                               {
/*      */ 
/*      */ 
/* 3484 */                                                 StringBuffer queryBuf = new StringBuffer();
/* 3485 */                                                 queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3486 */                                                 queryBuf.append(categoryLink[c] + "'");
/* 3487 */                                                 queryBuf.append(" ");
/* 3488 */                                                 queryBuf.append("and RESOURCETYPE in");
/* 3489 */                                                 queryBuf.append(" ");
/* 3490 */                                                 queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3491 */                                                 if (categoryLink[c].equals("APP"))
/*      */                                                 {
/* 3493 */                                                   queryBuf.append(" ");
/* 3494 */                                                   queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3495 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3497 */                                                 else if (categoryLink[c].equals("SER"))
/*      */                                                 {
/* 3499 */                                                   queryBuf.append(" ");
/* 3500 */                                                   queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3501 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3503 */                                                 else if (categoryLink[c].equals("CAM"))
/*      */                                                 {
/* 3505 */                                                   queryBuf.append(" ");
/* 3506 */                                                   queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3507 */                                                   queryBuf.append(" ");
/*      */                                                 }
/* 3509 */                                                 queryBuf.append(" ");
/* 3510 */                                                 queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3511 */                                                 ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3512 */                                                 if ((rows != null) && (rows.size() != 0))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/* 3517 */                                                   out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3518 */                                                   out.print(FormatUtil.getString(categoryTitle[c]));
/* 3519 */                                                   out.write(34);
/* 3520 */                                                   out.write(62);
/* 3521 */                                                   out.write(10);
/*      */                                                   
/*      */ 
/* 3524 */                                                   for (int i = 0; i < rows.size(); i++)
/*      */                                                   {
/* 3526 */                                                     ArrayList row = (ArrayList)rows.get(i);
/* 3527 */                                                     String res = (String)row.get(0);
/* 3528 */                                                     if (res.equals("file"))
/*      */                                                     {
/* 3530 */                                                       res = "File / Directory Monitor";
/*      */                                                     }
/* 3532 */                                                     String dname = (String)row.get(1);
/* 3533 */                                                     String values = props.getProperty(res);
/* 3534 */                                                     if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                     {
/*      */ 
/* 3537 */                                                       if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                       {
/*      */ 
/* 3540 */                                                         out.write("\n\t\t\t\t \t");
/*      */                                                         
/* 3542 */                                                         OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3543 */                                                         _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3544 */                                                         _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                         
/* 3546 */                                                         _jspx_th_html_005foption_005f11.setValue(values);
/* 3547 */                                                         int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3548 */                                                         if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3549 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3550 */                                                             out = _jspx_page_context.pushBody();
/* 3551 */                                                             _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3552 */                                                             _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                           }
/*      */                                                           for (;;) {
/* 3555 */                                                             out.write(32);
/* 3556 */                                                             out.print(FormatUtil.getString(dname));
/* 3557 */                                                             int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3558 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/* 3561 */                                                           if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3562 */                                                             out = _jspx_page_context.popBody();
/*      */                                                           }
/*      */                                                         }
/* 3565 */                                                         if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3566 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                         }
/*      */                                                         
/* 3569 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3570 */                                                         out.write("\n\t\t\t\t");
/*      */                                                       }
/*      */                                                     }
/*      */                                                   }
/*      */                                                   
/* 3575 */                                                   if (categoryLink[c].equals("VIR"))
/*      */                                                   {
/*      */ 
/* 3578 */                                                     out.write("\n\t\t\t\t\t");
/*      */                                                     
/* 3580 */                                                     OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3581 */                                                     _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3582 */                                                     _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                     
/* 3584 */                                                     _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3585 */                                                     int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3586 */                                                     if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3587 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3588 */                                                         out = _jspx_page_context.pushBody();
/* 3589 */                                                         _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3590 */                                                         _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                       }
/*      */                                                       for (;;) {
/* 3593 */                                                         out.write(32);
/* 3594 */                                                         out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3595 */                                                         int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3596 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/* 3599 */                                                       if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3600 */                                                         out = _jspx_page_context.popBody();
/*      */                                                       }
/*      */                                                     }
/* 3603 */                                                     if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3604 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                     }
/*      */                                                     
/* 3607 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3608 */                                                     out.write("\n\t\t\t\t");
/*      */                                                   }
/*      */                                                 }
/*      */                                               } }
/* 3612 */                                             String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3613 */                                             if (!usertype.equals("F"))
/*      */                                             {
/* 3615 */                                               if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                               {
/* 3617 */                                                 out.write("\n    </optgroup> <optgroup label=\"");
/* 3618 */                                                 out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3619 */                                                 out.write("\">\n     ");
/*      */                                                 
/* 3621 */                                                 OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3622 */                                                 _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3623 */                                                 _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                 
/* 3625 */                                                 _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3626 */                                                 int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3627 */                                                 if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3628 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3629 */                                                     out = _jspx_page_context.pushBody();
/* 3630 */                                                     _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3631 */                                                     _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 3634 */                                                     out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3635 */                                                     out.write(32);
/* 3636 */                                                     int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3637 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 3640 */                                                   if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3641 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 3644 */                                                 if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3645 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                 }
/*      */                                                 
/* 3648 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3649 */                                                 out.write("\n\n     ");
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             
/*      */                                           }
/* 3655 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                           {
/*      */ 
/* 3658 */                                             out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3659 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3660 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3662 */                                             OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3663 */                                             _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3664 */                                             _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3666 */                                             _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3667 */                                             int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3668 */                                             if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3669 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3670 */                                                 out = _jspx_page_context.pushBody();
/* 3671 */                                                 _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3672 */                                                 _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3675 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3676 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3677 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3680 */                                               if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3681 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3684 */                                             if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3685 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                             }
/*      */                                             
/* 3688 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3689 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3690 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3691 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3693 */                                             OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3694 */                                             _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3695 */                                             _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3697 */                                             _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3698 */                                             int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3699 */                                             if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3700 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3701 */                                                 out = _jspx_page_context.pushBody();
/* 3702 */                                                 _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3703 */                                                 _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3706 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3707 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3708 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3711 */                                               if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3712 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3715 */                                             if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3716 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                             }
/*      */                                             
/* 3719 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3720 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3721 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3722 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3724 */                                             OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3725 */                                             _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3726 */                                             _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3728 */                                             _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3729 */                                             int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3730 */                                             if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3731 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3732 */                                                 out = _jspx_page_context.pushBody();
/* 3733 */                                                 _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3734 */                                                 _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3737 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3738 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3739 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3742 */                                               if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3743 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3746 */                                             if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3747 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                             }
/*      */                                             
/* 3750 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3751 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3753 */                                             OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3754 */                                             _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3755 */                                             _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3757 */                                             _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3758 */                                             int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3759 */                                             if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3760 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3761 */                                                 out = _jspx_page_context.pushBody();
/* 3762 */                                                 _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3763 */                                                 _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3766 */                                                 out.write(32);
/* 3767 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 3768 */                                                 out.write(32);
/* 3769 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3770 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3773 */                                               if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3774 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3777 */                                             if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3778 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                             }
/*      */                                             
/* 3781 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3782 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3784 */                                             OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3785 */                                             _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3786 */                                             _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3788 */                                             _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3789 */                                             int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3790 */                                             if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3791 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3792 */                                                 out = _jspx_page_context.pushBody();
/* 3793 */                                                 _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3794 */                                                 _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3797 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3798 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3799 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3802 */                                               if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3803 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3806 */                                             if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3807 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                             }
/*      */                                             
/* 3810 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3811 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3813 */                                             OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3814 */                                             _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3815 */                                             _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3817 */                                             _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3818 */                                             int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3819 */                                             if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3820 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3821 */                                                 out = _jspx_page_context.pushBody();
/* 3822 */                                                 _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3823 */                                                 _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3826 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 3827 */                                                 out.write(" (V1 or V2c)");
/* 3828 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3829 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3832 */                                               if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3833 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3836 */                                             if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3837 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                             }
/*      */                                             
/* 3840 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3841 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3843 */                                             OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3844 */                                             _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3845 */                                             _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3847 */                                             _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3848 */                                             int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3849 */                                             if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3850 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3851 */                                                 out = _jspx_page_context.pushBody();
/* 3852 */                                                 _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3853 */                                                 _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3856 */                                                 out.print(FormatUtil.getString("Telnet"));
/* 3857 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3858 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3861 */                                               if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3862 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3865 */                                             if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3866 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                             }
/*      */                                             
/* 3869 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3870 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3871 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3872 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 3874 */                                             OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3875 */                                             _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3876 */                                             _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3878 */                                             _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3879 */                                             int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3880 */                                             if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3881 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3882 */                                                 out = _jspx_page_context.pushBody();
/* 3883 */                                                 _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3884 */                                                 _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3887 */                                                 out.write(32);
/* 3888 */                                                 out.print(FormatUtil.getString("Apache Server"));
/* 3889 */                                                 out.write(32);
/* 3890 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3891 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3894 */                                               if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3895 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3898 */                                             if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3899 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                             }
/*      */                                             
/* 3902 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3903 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3905 */                                             OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3906 */                                             _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3907 */                                             _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3909 */                                             _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3910 */                                             int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3911 */                                             if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3912 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3913 */                                                 out = _jspx_page_context.pushBody();
/* 3914 */                                                 _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3915 */                                                 _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3918 */                                                 out.print(FormatUtil.getString("PHP"));
/* 3919 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3920 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3923 */                                               if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3924 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3927 */                                             if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3928 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                             }
/*      */                                             
/* 3931 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3932 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3934 */                                             OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3935 */                                             _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3936 */                                             _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3938 */                                             _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3939 */                                             int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3940 */                                             if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3941 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3942 */                                                 out = _jspx_page_context.pushBody();
/* 3943 */                                                 _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3944 */                                                 _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3947 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 3948 */                                                 out.write(32);
/* 3949 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3950 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3953 */                                               if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3954 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3957 */                                             if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3958 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                             }
/*      */                                             
/* 3961 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3962 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3964 */                                             OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3965 */                                             _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3966 */                                             _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3968 */                                             _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3969 */                                             int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3970 */                                             if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3971 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3972 */                                                 out = _jspx_page_context.pushBody();
/* 3973 */                                                 _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3974 */                                                 _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3977 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3978 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3979 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3982 */                                               if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3983 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3986 */                                             if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3987 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                             }
/*      */                                             
/* 3990 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3991 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 3993 */                                             OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3994 */                                             _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3995 */                                             _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 3997 */                                             _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3998 */                                             int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3999 */                                             if (_jspx_eval_html_005foption_005f25 != 0) {
/* 4000 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4001 */                                                 out = _jspx_page_context.pushBody();
/* 4002 */                                                 _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 4003 */                                                 _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4006 */                                                 out.write(32);
/* 4007 */                                                 out.print(FormatUtil.getString("Web Server"));
/* 4008 */                                                 out.write(32);
/* 4009 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 4010 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4013 */                                               if (_jspx_eval_html_005foption_005f25 != 1) {
/* 4014 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4017 */                                             if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 4018 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                             }
/*      */                                             
/* 4021 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 4022 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4024 */                                             OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4025 */                                             _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 4026 */                                             _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4028 */                                             _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 4029 */                                             int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 4030 */                                             if (_jspx_eval_html_005foption_005f26 != 0) {
/* 4031 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4032 */                                                 out = _jspx_page_context.pushBody();
/* 4033 */                                                 _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 4034 */                                                 _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4037 */                                                 out.write(32);
/* 4038 */                                                 out.print(FormatUtil.getString("Web Service"));
/* 4039 */                                                 out.write(32);
/* 4040 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 4041 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4044 */                                               if (_jspx_eval_html_005foption_005f26 != 1) {
/* 4045 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4048 */                                             if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 4049 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                             }
/*      */                                             
/* 4052 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 4053 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4054 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 4055 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 4057 */                                             OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4058 */                                             _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 4059 */                                             _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4061 */                                             _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 4062 */                                             int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 4063 */                                             if (_jspx_eval_html_005foption_005f27 != 0) {
/* 4064 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4065 */                                                 out = _jspx_page_context.pushBody();
/* 4066 */                                                 _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 4067 */                                                 _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4070 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4071 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 4072 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4075 */                                               if (_jspx_eval_html_005foption_005f27 != 1) {
/* 4076 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4079 */                                             if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 4080 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                             }
/*      */                                             
/* 4083 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 4084 */                                             out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 4085 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4086 */                                             out.write("\">\n\t\t\t   ");
/*      */                                             
/* 4088 */                                             OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4089 */                                             _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 4090 */                                             _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4092 */                                             _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 4093 */                                             int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 4094 */                                             if (_jspx_eval_html_005foption_005f28 != 0) {
/* 4095 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4096 */                                                 out = _jspx_page_context.pushBody();
/* 4097 */                                                 _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 4098 */                                                 _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4101 */                                                 out.write(32);
/* 4102 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4103 */                                                 out.write(32);
/* 4104 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 4105 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4108 */                                               if (_jspx_eval_html_005foption_005f28 != 1) {
/* 4109 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4112 */                                             if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 4113 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                             }
/*      */                                             
/* 4116 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 4117 */                                             out.write("\n\t\t\t   ");
/*      */                                             
/* 4119 */                                             OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4120 */                                             _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 4121 */                                             _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4123 */                                             _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 4124 */                                             int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 4125 */                                             if (_jspx_eval_html_005foption_005f29 != 0) {
/* 4126 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4127 */                                                 out = _jspx_page_context.pushBody();
/* 4128 */                                                 _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 4129 */                                                 _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4132 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4133 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 4134 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4137 */                                               if (_jspx_eval_html_005foption_005f29 != 1) {
/* 4138 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4141 */                                             if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 4142 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                             }
/*      */                                             
/* 4145 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4146 */                                             out.write("\n\n    ");
/*      */ 
/*      */                                           }
/* 4149 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                           {
/*      */ 
/* 4152 */                                             out.write("\n        ");
/*      */                                             
/* 4154 */                                             OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4155 */                                             _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4156 */                                             _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4158 */                                             _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4159 */                                             int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4160 */                                             if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4161 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4162 */                                                 out = _jspx_page_context.pushBody();
/* 4163 */                                                 _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4164 */                                                 _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4167 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4168 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4169 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4172 */                                               if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4173 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4176 */                                             if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4177 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                             }
/*      */                                             
/* 4180 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4181 */                                             out.write("\n       ");
/*      */                                             
/* 4183 */                                             OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4184 */                                             _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4185 */                                             _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4187 */                                             _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4188 */                                             int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4189 */                                             if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4190 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4191 */                                                 out = _jspx_page_context.pushBody();
/* 4192 */                                                 _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4193 */                                                 _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4196 */                                                 out.write(32);
/* 4197 */                                                 out.print(FormatUtil.getString("JBoss Server"));
/* 4198 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4199 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4202 */                                               if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4203 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4206 */                                             if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4207 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                             }
/*      */                                             
/* 4210 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4211 */                                             out.write("\n      ");
/*      */                                             
/* 4213 */                                             OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4214 */                                             _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4215 */                                             _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4217 */                                             _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4218 */                                             int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4219 */                                             if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4220 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4221 */                                                 out = _jspx_page_context.pushBody();
/* 4222 */                                                 _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4223 */                                                 _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4226 */                                                 out.print(FormatUtil.getString("Tomcat Server"));
/* 4227 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4228 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4231 */                                               if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4232 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4235 */                                             if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4236 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                             }
/*      */                                             
/* 4239 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4240 */                                             out.write("\n       ");
/*      */                                             
/* 4242 */                                             OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4243 */                                             _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4244 */                                             _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4246 */                                             _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4247 */                                             int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4248 */                                             if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4249 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4250 */                                                 out = _jspx_page_context.pushBody();
/* 4251 */                                                 _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4252 */                                                 _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4255 */                                                 out.write(32);
/* 4256 */                                                 out.print(FormatUtil.getString("WebLogic Server"));
/* 4257 */                                                 out.write(32);
/* 4258 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4259 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4262 */                                               if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4263 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4266 */                                             if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4267 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                             }
/*      */                                             
/* 4270 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4271 */                                             out.write("\n      ");
/*      */                                             
/* 4273 */                                             OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4274 */                                             _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4275 */                                             _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4277 */                                             _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4278 */                                             int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4279 */                                             if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4280 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4281 */                                                 out = _jspx_page_context.pushBody();
/* 4282 */                                                 _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4283 */                                                 _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4286 */                                                 out.write(32);
/* 4287 */                                                 out.print(FormatUtil.getString("WebSphere Server"));
/* 4288 */                                                 out.write(32);
/* 4289 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4290 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4293 */                                               if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4294 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4297 */                                             if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4298 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                             }
/*      */                                             
/* 4301 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4302 */                                             out.write("\n      ");
/*      */                                             
/* 4304 */                                             OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4305 */                                             _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4306 */                                             _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4308 */                                             _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4309 */                                             int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4310 */                                             if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4311 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4312 */                                                 out = _jspx_page_context.pushBody();
/* 4313 */                                                 _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4314 */                                                 _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4317 */                                                 out.print(FormatUtil.getString("Web Transactions"));
/* 4318 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4319 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4322 */                                               if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4323 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4326 */                                             if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4327 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                             }
/*      */                                             
/* 4330 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4331 */                                             out.write("\n      ");
/*      */                                             
/* 4333 */                                             OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4334 */                                             _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4335 */                                             _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4337 */                                             _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4338 */                                             int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4339 */                                             if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4340 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4341 */                                                 out = _jspx_page_context.pushBody();
/* 4342 */                                                 _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4343 */                                                 _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4346 */                                                 out.print(FormatUtil.getString("Mail Server"));
/* 4347 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4348 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4351 */                                               if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4352 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4355 */                                             if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4356 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                             }
/*      */                                             
/* 4359 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4360 */                                             out.write("\n      ");
/*      */                                             
/* 4362 */                                             OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4363 */                                             _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4364 */                                             _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4366 */                                             _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4367 */                                             int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4368 */                                             if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4369 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4370 */                                                 out = _jspx_page_context.pushBody();
/* 4371 */                                                 _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4372 */                                                 _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4375 */                                                 out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4376 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4377 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4380 */                                               if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4381 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4384 */                                             if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4385 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                             }
/*      */                                             
/* 4388 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4389 */                                             out.write("\n      ");
/*      */                                             
/* 4391 */                                             OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4392 */                                             _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4393 */                                             _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4395 */                                             _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4396 */                                             int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4397 */                                             if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4398 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4399 */                                                 out = _jspx_page_context.pushBody();
/* 4400 */                                                 _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4401 */                                                 _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4404 */                                                 out.write(32);
/* 4405 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4406 */                                                 out.write(32);
/* 4407 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4408 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4411 */                                               if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4412 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4415 */                                             if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4416 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                             }
/*      */                                             
/* 4419 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4420 */                                             out.write("\n      ");
/*      */                                             
/* 4422 */                                             OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4423 */                                             _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4424 */                                             _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4426 */                                             _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4427 */                                             int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4428 */                                             if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4429 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4430 */                                                 out = _jspx_page_context.pushBody();
/* 4431 */                                                 _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4432 */                                                 _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4435 */                                                 out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4436 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4437 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4440 */                                               if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4441 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4444 */                                             if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4445 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                             }
/*      */                                             
/* 4448 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4449 */                                             out.write("\n      ");
/*      */                                             
/* 4451 */                                             OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4452 */                                             _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4453 */                                             _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4455 */                                             _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4456 */                                             int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4457 */                                             if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4458 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4459 */                                                 out = _jspx_page_context.pushBody();
/* 4460 */                                                 _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4461 */                                                 _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4464 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4465 */                                                 out.write(" (V1 or V2c)");
/* 4466 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4467 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4470 */                                               if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4471 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4474 */                                             if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4475 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                             }
/*      */                                             
/* 4478 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4479 */                                             out.write("\n      ");
/*      */                                             
/* 4481 */                                             OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4482 */                                             _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4483 */                                             _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4485 */                                             _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4486 */                                             int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4487 */                                             if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4488 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4489 */                                                 out = _jspx_page_context.pushBody();
/* 4490 */                                                 _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4491 */                                                 _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4494 */                                                 out.write(32);
/* 4495 */                                                 out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4496 */                                                 out.write(32);
/* 4497 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4498 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4501 */                                               if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4502 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4505 */                                             if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4506 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                             }
/*      */                                             
/* 4509 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4510 */                                             out.write("\n      ");
/*      */                                             
/* 4512 */                                             OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4513 */                                             _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4514 */                                             _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4516 */                                             _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4517 */                                             int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4518 */                                             if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4519 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4520 */                                                 out = _jspx_page_context.pushBody();
/* 4521 */                                                 _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4522 */                                                 _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4525 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4526 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4527 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4530 */                                               if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4531 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4534 */                                             if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4535 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                             }
/*      */                                             
/* 4538 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4539 */                                             out.write("\n       ");
/*      */ 
/*      */                                           }
/* 4542 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                           {
/*      */ 
/* 4545 */                                             out.write("\n        ");
/*      */                                             
/* 4547 */                                             OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4548 */                                             _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4549 */                                             _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4551 */                                             _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4552 */                                             int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4553 */                                             if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4554 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4555 */                                                 out = _jspx_page_context.pushBody();
/* 4556 */                                                 _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4557 */                                                 _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4560 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4561 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4562 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4565 */                                               if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4566 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4569 */                                             if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4570 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                             }
/*      */                                             
/* 4573 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4574 */                                             out.write("\n       ");
/*      */                                             
/* 4576 */                                             OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4577 */                                             _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4578 */                                             _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4580 */                                             _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4581 */                                             int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4582 */                                             if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4583 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4584 */                                                 out = _jspx_page_context.pushBody();
/* 4585 */                                                 _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4586 */                                                 _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4589 */                                                 out.print(FormatUtil.getString("Microsoft .NET"));
/* 4590 */                                                 out.write(32);
/* 4591 */                                                 out.write(32);
/* 4592 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4593 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4596 */                                               if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4597 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4600 */                                             if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4601 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                             }
/*      */                                             
/* 4604 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4605 */                                             out.write("\n      ");
/*      */                                             
/* 4607 */                                             OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4608 */                                             _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4609 */                                             _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4611 */                                             _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4612 */                                             int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4613 */                                             if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4614 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4615 */                                                 out = _jspx_page_context.pushBody();
/* 4616 */                                                 _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4617 */                                                 _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4620 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4621 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4622 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4625 */                                               if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4626 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4629 */                                             if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4630 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                             }
/*      */                                             
/* 4633 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4634 */                                             out.write("\n      ");
/*      */                                             
/* 4636 */                                             OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4637 */                                             _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4638 */                                             _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4640 */                                             _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4641 */                                             int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4642 */                                             if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4643 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4644 */                                                 out = _jspx_page_context.pushBody();
/* 4645 */                                                 _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4646 */                                                 _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4649 */                                                 out.print(FormatUtil.getString("Exchange Server"));
/* 4650 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4651 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4654 */                                               if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4655 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4658 */                                             if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4659 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                             }
/*      */                                             
/* 4662 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4663 */                                             out.write("\n\t  ");
/*      */                                             
/* 4665 */                                             OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4666 */                                             _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4667 */                                             _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4669 */                                             _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4670 */                                             int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4671 */                                             if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4672 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4673 */                                                 out = _jspx_page_context.pushBody();
/* 4674 */                                                 _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4675 */                                                 _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4678 */                                                 out.write(32);
/* 4679 */                                                 out.print(FormatUtil.getString("IIS Server"));
/* 4680 */                                                 out.write(32);
/* 4681 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4682 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4685 */                                               if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4686 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4689 */                                             if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4690 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                             }
/*      */                                             
/* 4693 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4694 */                                             out.write("\n      ");
/*      */                                             
/* 4696 */                                             OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4697 */                                             _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4698 */                                             _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4700 */                                             _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4701 */                                             int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4702 */                                             if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4703 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4704 */                                                 out = _jspx_page_context.pushBody();
/* 4705 */                                                 _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4706 */                                                 _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4709 */                                                 out.write(32);
/* 4710 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4711 */                                                 out.write(32);
/* 4712 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4713 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4716 */                                               if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4717 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4720 */                                             if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4721 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                             }
/*      */                                             
/* 4724 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4725 */                                             out.write("\n\t  ");
/*      */                                             
/* 4727 */                                             OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4728 */                                             _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4729 */                                             _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4731 */                                             _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4732 */                                             int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4733 */                                             if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4734 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4735 */                                                 out = _jspx_page_context.pushBody();
/* 4736 */                                                 _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4737 */                                                 _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4740 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 4741 */                                                 out.write(" (V1 or V2c)");
/* 4742 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4743 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4746 */                                               if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4747 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4750 */                                             if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4751 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                             }
/*      */                                             
/* 4754 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4755 */                                             out.write("\n      ");
/*      */                                             
/* 4757 */                                             OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4758 */                                             _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4759 */                                             _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4761 */                                             _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4762 */                                             int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4763 */                                             if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4764 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4765 */                                                 out = _jspx_page_context.pushBody();
/* 4766 */                                                 _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4767 */                                                 _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4770 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 4771 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4772 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4775 */                                               if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4776 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4779 */                                             if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4780 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                             }
/*      */                                             
/* 4783 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4784 */                                             out.write(10);
/*      */ 
/*      */                                           }
/* 4787 */                                           else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                           {
/*      */ 
/* 4790 */                                             out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4791 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4792 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4794 */                                             OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4795 */                                             _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4796 */                                             _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4798 */                                             _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4799 */                                             int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4800 */                                             if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4801 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4802 */                                                 out = _jspx_page_context.pushBody();
/* 4803 */                                                 _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4804 */                                                 _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4807 */                                                 out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4808 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4809 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4812 */                                               if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4813 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4816 */                                             if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4817 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                             }
/*      */                                             
/* 4820 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4821 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4822 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4823 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4825 */                                             OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4826 */                                             _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4827 */                                             _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4829 */                                             _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4830 */                                             int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4831 */                                             if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4832 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4833 */                                                 out = _jspx_page_context.pushBody();
/* 4834 */                                                 _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4835 */                                                 _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4838 */                                                 out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4839 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4840 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4843 */                                               if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4844 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4847 */                                             if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4848 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                             }
/*      */                                             
/* 4851 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4852 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4854 */                                             OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4855 */                                             _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4856 */                                             _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4858 */                                             _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4859 */                                             int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4860 */                                             if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4861 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4862 */                                                 out = _jspx_page_context.pushBody();
/* 4863 */                                                 _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4864 */                                                 _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4867 */                                                 out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4868 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4869 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4872 */                                               if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4873 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4876 */                                             if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4877 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                             }
/*      */                                             
/* 4880 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4881 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4883 */                                             OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4884 */                                             _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4885 */                                             _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4887 */                                             _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4888 */                                             int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4889 */                                             if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4890 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4891 */                                                 out = _jspx_page_context.pushBody();
/* 4892 */                                                 _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4893 */                                                 _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4896 */                                                 out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4897 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4898 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4901 */                                               if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4902 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4905 */                                             if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4906 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                             }
/*      */                                             
/* 4909 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4910 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4912 */                                             OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4913 */                                             _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4914 */                                             _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4916 */                                             _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4917 */                                             int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4918 */                                             if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4919 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4920 */                                                 out = _jspx_page_context.pushBody();
/* 4921 */                                                 _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4922 */                                                 _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4925 */                                                 out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4926 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4927 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4930 */                                               if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4931 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4934 */                                             if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4935 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                             }
/*      */                                             
/* 4938 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4939 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 4941 */                                             OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4942 */                                             _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4943 */                                             _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4945 */                                             _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4946 */                                             int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4947 */                                             if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4948 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4949 */                                                 out = _jspx_page_context.pushBody();
/* 4950 */                                                 _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4951 */                                                 _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4954 */                                                 out.print(FormatUtil.getString("Sybase"));
/* 4955 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4956 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4959 */                                               if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4960 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4963 */                                             if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4964 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                             }
/*      */                                             
/* 4967 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4968 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4969 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4970 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 4972 */                                             OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4973 */                                             _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4974 */                                             _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 4976 */                                             _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4977 */                                             int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4978 */                                             if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4979 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4980 */                                                 out = _jspx_page_context.pushBody();
/* 4981 */                                                 _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4982 */                                                 _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4985 */                                                 out.write(32);
/* 4986 */                                                 out.print(FormatUtil.getString("Service Monitoring"));
/* 4987 */                                                 out.write(32);
/* 4988 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4989 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4992 */                                               if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4993 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4996 */                                             if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4997 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                             }
/*      */                                             
/* 5000 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 5001 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 5003 */                                             OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5004 */                                             _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 5005 */                                             _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5007 */                                             _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 5008 */                                             int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 5009 */                                             if (_jspx_eval_html_005foption_005f58 != 0) {
/* 5010 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5011 */                                                 out = _jspx_page_context.pushBody();
/* 5012 */                                                 _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 5013 */                                                 _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5016 */                                                 out.print(FormatUtil.getString("SNMP"));
/* 5017 */                                                 out.write(" (V1 or V2c)");
/* 5018 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 5019 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5022 */                                               if (_jspx_eval_html_005foption_005f58 != 1) {
/* 5023 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5026 */                                             if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 5027 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                             }
/*      */                                             
/* 5030 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 5031 */                                             out.write("</optgroup>");
/* 5032 */                                             out.write("\n\t\t\t<optgroup label=\"");
/* 5033 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 5034 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 5036 */                                             OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5037 */                                             _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 5038 */                                             _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5040 */                                             _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 5041 */                                             int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 5042 */                                             if (_jspx_eval_html_005foption_005f59 != 0) {
/* 5043 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5044 */                                                 out = _jspx_page_context.pushBody();
/* 5045 */                                                 _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 5046 */                                                 _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5049 */                                                 out.print(FormatUtil.getString("HTTP-URLs"));
/* 5050 */                                                 out.write(32);
/* 5051 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 5052 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5055 */                                               if (_jspx_eval_html_005foption_005f59 != 1) {
/* 5056 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5059 */                                             if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 5060 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                             }
/*      */                                             
/* 5063 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 5064 */                                             out.write("\n\t\t\t");
/*      */                                             
/* 5066 */                                             OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5067 */                                             _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 5068 */                                             _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5070 */                                             _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 5071 */                                             int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 5072 */                                             if (_jspx_eval_html_005foption_005f60 != 0) {
/* 5073 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5074 */                                                 out = _jspx_page_context.pushBody();
/* 5075 */                                                 _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 5076 */                                                 _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5079 */                                                 out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 5080 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 5081 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5084 */                                               if (_jspx_eval_html_005foption_005f60 != 1) {
/* 5085 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5088 */                                             if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 5089 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                             }
/*      */                                             
/* 5092 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 5093 */                                             out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 5094 */                                             out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 5095 */                                             out.write("\">\n\t\t\t");
/*      */                                             
/* 5097 */                                             OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5098 */                                             _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 5099 */                                             _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                             
/* 5101 */                                             _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 5102 */                                             int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 5103 */                                             if (_jspx_eval_html_005foption_005f61 != 0) {
/* 5104 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5105 */                                                 out = _jspx_page_context.pushBody();
/* 5106 */                                                 _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 5107 */                                                 _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5110 */                                                 out.print(FormatUtil.getString("Script Monitor"));
/* 5111 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 5112 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5115 */                                               if (_jspx_eval_html_005foption_005f61 != 1) {
/* 5116 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5119 */                                             if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 5120 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                             }
/*      */                                             
/* 5123 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 5124 */                                             out.write(10);
/* 5125 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */ 
/* 5129 */                                           out.write("\n\n\n\n      ");
/* 5130 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 5131 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5134 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 5135 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5138 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 5139 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 5142 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 5143 */                                       out.write("\n                      \n      ");
/* 5144 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5145 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5149 */                                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5150 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                   }
/*      */                                   
/* 5153 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5154 */                                   out.write("\n      ");
/* 5155 */                                   if (_jspx_meth_c_005fif_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5157 */                                   out.write("\n      </td>\n      \n      ");
/* 5158 */                                   if (request.getParameter("type") != null) {
/* 5159 */                                     isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5160 */                                     String restype = request.getParameter("type");
/* 5161 */                                     if (restype.indexOf(":") != -1) {
/* 5162 */                                       restype = restype.substring(0, restype.indexOf(":"));
/*      */                                     }
/* 5164 */                                     if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5165 */                                       out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5166 */                                       out.print(restype);
/* 5167 */                                       out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5168 */                                       out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5169 */                                       out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                     }
/*      */                                   }
/* 5172 */                                   out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5173 */                                   out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"1\" class=\"lrborder\">\n\t<tr >\n   \t <td width=\"25%\" height=\"28\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5174 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.Displayname"));
/* 5175 */                                   out.write("<span class=\"mandatory\">*</span></label></td>\n   \t <td width=\"75%\" height=\"28\" colspan=\"2\">");
/* 5176 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5178 */                                   out.write(" </td> \n   \t</tr>\n\t<tr>\n    <td width=\"25%\" height=\"26\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5179 */                                   out.print(FormatUtil.getString("am.webclient.wsm.wsdlurl.text"));
/* 5180 */                                   out.write("<span class=\"mandatory\">*</span></label>\n    </td>\n    <td height=\"26\" class=\"bodytext\"> ");
/* 5181 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5183 */                                   out.write("</td>\n  </tr>\n\t<tr>\n    <td width=\"19%\" height=\"26\" class=\"bodytext label-align addmonitor-label\">");
/* 5184 */                                   out.print(FormatUtil.getString("Endpoint Address"));
/* 5185 */                                   out.write("\n    </td>\n    <td height=\"26\" class=\"bodytext\"> ");
/* 5186 */                                   if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5188 */                                   out.write("</td>\n\t</tr>\t\n  <tr>\n    <td width=\"25%\" height=\"26\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5189 */                                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 5190 */                                   out.write("\n      <span class=\"mandatory\">*</span></label></td>\n    <td height=\"26\" class=\"footer\"> ");
/* 5191 */                                   if (_jspx_meth_html_005ftext_005f3(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5193 */                                   out.write("\n      ");
/* 5194 */                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 5195 */                                   out.write("</td>\n  </tr>\n  <!--tr>\n  <td colspan=\"2\" align=\"left\" class=\"bodytext\"><a class=\"staticlinks\" href=\"javascript:toggleAdvanced()\">");
/* 5196 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.advancedlink.text"));
/* 5197 */                                   out.write("</a>  <img src=\"/images/icon_left_arrow.gif\">\n  </td>\n  </tr-->\n  <tr id=\"timeout\" >\n    <td height=\"20\" class=\"bodytext label-align addmonitor-label\"><label><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 5198 */                                   out.print(FormatUtil.getString("am.webclient.newscript.insecondshelp.text"));
/* 5199 */                                   out.write("',false,true,'#000000',400,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 5200 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.timeout"));
/* 5201 */                                   out.write("</a></label></td>\n    <td height=\"20\" class=\"footer\"> ");
/* 5202 */                                   if (_jspx_meth_html_005ftext_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5204 */                                   out.write(" <span class=\"footer\">");
/* 5205 */                                   out.print(FormatUtil.getString("am.webclient.newscript.insecondshelp.seconds"));
/* 5206 */                                   out.write("</span>\n   </td>\n  </tr>\n  <tr id=\"username\">\n  <td height=\"20\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5207 */                                   out.print(FormatUtil.getString("am.webclient.common.username.text"));
/* 5208 */                                   out.write("</label></td>\n  <td height=\"20\" class=\"bodytext\"><input type=\"text\" name=\"username\" class=\"formtext default\" autocomplete=\"off\"/><!--");
/* 5209 */                                   if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5211 */                                   out.write("--></td>\n  </tr>\n  <tr id=\"password\">\n  <td height=\"20\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 5212 */                                   out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 5213 */                                   out.write("</label></td>\n  <td height=\"20\" class=\"bodytext\"><input type=\"password\" name=\"password\" class=\"formtext default\" autocomplete=\"off\" /><!--");
/* 5214 */                                   if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5216 */                                   out.write("--></td>\n  </tr>\n  <tr id=\"headers\">\n  \t<td height=\"20\" class=\"bodytext label-align\">");
/* 5217 */                                   out.print(FormatUtil.getString("am.webclient.wsm.header.text"));
/* 5218 */                                   out.write("</td>\n  \t<td height=\"20\" class=\"bodytext\">");
/* 5219 */                                   if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5221 */                                   out.write("</td>\n  </tr>\n  <tr id=\"authToken\">\n  \t<td height=\"20\" class=\"bodytext label-align\">");
/* 5222 */                                   out.print(FormatUtil.getString("am.webclient.wsm.authtoken.text"));
/* 5223 */                                   out.write("</td>\n  \t<td height=\"20\" class=\"bodytext\">");
/* 5224 */                                   if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5226 */                                   out.write("</td>\n  </tr>\n  <tr height=\"25\">\n  \t<td class=\"bodytext label-align addmonitor-label\"></td>\n\t\t<td nowrap=\"\">\n\t\t\t\t");
/*      */                                   
/* 5228 */                                   CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty.get(CheckboxTag.class);
/* 5229 */                                   _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 5230 */                                   _jspx_th_html_005fcheckbox_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5232 */                                   _jspx_th_html_005fcheckbox_005f0.setProperty("resFulWebservice");
/*      */                                   
/* 5234 */                                   _jspx_th_html_005fcheckbox_005f0.setValue("true");
/* 5235 */                                   int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 5236 */                                   if (_jspx_eval_html_005fcheckbox_005f0 != 0) {
/* 5237 */                                     if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 5238 */                                       out = _jspx_page_context.pushBody();
/* 5239 */                                       _jspx_th_html_005fcheckbox_005f0.setBodyContent((BodyContent)out);
/* 5240 */                                       _jspx_th_html_005fcheckbox_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5243 */                                       out.print(FormatUtil.getString("am.webservice.isrestful.text"));
/* 5244 */                                       int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f0.doAfterBody();
/* 5245 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5248 */                                     if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 5249 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5252 */                                   if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 5253 */                                     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f0); return;
/*      */                                   }
/*      */                                   
/* 5256 */                                   this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fvalue_005fproperty.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 5257 */                                   out.write("\n  \t\t</td>\n  </tr>\n</table>\n\n    ");
/*      */                                   
/* 5259 */                                   if (request.getAttribute("wiz") == null)
/*      */                                   {
/*      */ 
/* 5262 */                                     out.write(10);
/* 5263 */                                     out.write(9);
/* 5264 */                                     out.write(9);
/* 5265 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("managedServerGroups", request.getCharacterEncoding()), out, false);
/* 5266 */                                     out.write(9);
/* 5267 */                                     out.write(10);
/* 5268 */                                     out.write(9);
/*      */                                   }
/*      */                                   
/*      */ 
/* 5272 */                                   out.write("  \t  \n\n");
/*      */                                   
/* 5274 */                                   if (request.getAttribute("wiz") == null)
/*      */                                   {
/*      */ 
/* 5277 */                                     out.write(10);
/* 5278 */                                     out.write(9);
/* 5279 */                                     out.write(9);
/* 5280 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/ShowGroupsDetails.jsp" + ("/jsp/includes/ShowGroupsDetails.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showDetailsOf", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("monitorGroups", request.getCharacterEncoding()), out, false);
/* 5281 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */ 
/* 5285 */                                   String addmonitors = FormatUtil.getString("am.webclient.newscript.addmonitors.text");
/* 5286 */                                   String restoredefaults = FormatUtil.getString("am.webclient.global.Reset.text");
/* 5287 */                                   String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                   
/* 5289 */                                   out.write(10);
/*      */                                   
/* 5291 */                                   if (request.getAttribute("wiz") == null)
/*      */                                   {
/*      */ 
/* 5294 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n<td width=\"25%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"75%\"  align=\"left\" class=\"tablebottom\">\n\n      <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 5295 */                                     out.print(addmonitors);
/* 5296 */                                     out.write("\" onClick=\"validateAndSubmit(1);\">\n      &nbsp; ");
/*      */                                     
/* 5298 */                                     ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.get(ResetTag.class);
/* 5299 */                                     _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 5300 */                                     _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5302 */                                     _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_reset");
/*      */                                     
/* 5304 */                                     _jspx_th_html_005freset_005f0.setValue(restoredefaults);
/* 5305 */                                     int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 5306 */                                     if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 5307 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                     }
/*      */                                     
/* 5310 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 5311 */                                     out.write(" &nbsp; <input type=\"button\" value=\"");
/* 5312 */                                     out.print(cancel);
/* 5313 */                                     out.write("\" onClick=\"history.back();\" class=\"buttons btn_link\" id=\"cancelButtonMod\"/>\n    </td>\n  </tr>\n\n</table>\n");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 5318 */                                     out.write("\n <input type=\"hidden\" name=\"wiz\" value=\"true\">\n<table class=\"lrbborder\" width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\">\n <tbody><tr>\n <td class=\"tablebottom\" align=\"left\" width=\"25%\">&nbsp;</td>\n <td class=\"tablebottom\" align=\"left\" height=\"31\" width=\"75%\">\n <input type=\"button\" name=\"xx2\" value=\"");
/* 5319 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.back"));
/* 5320 */                                     out.write("\" class=\"buttons btn_back\"  onClick=\"javascript:location.href='/showresource.do?method=associateMonitors&haid=");
/* 5321 */                                     if (_jspx_meth_c_005fout_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5323 */                                     out.write("&wiz=true'\">\n  <input type=\"button\" name=\"xx12\" value=\"");
/* 5324 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 5325 */                                     out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:validateAndSubmit(3);\">\n  <input name=\"button1\" type=\"button\" class=\"buttons btn_highlt\" value=\"");
/* 5326 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmore"));
/* 5327 */                                     out.write("\" onClick=\"javascript:validateAndSubmit(2);\">\n  ");
/*      */                                     
/* 5329 */                                     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5330 */                                     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 5331 */                                     _jspx_th_c_005fif_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5333 */                                     _jspx_th_c_005fif_005f7.setTest("${!empty associatedmonitors}");
/* 5334 */                                     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 5335 */                                     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                       for (;;) {
/* 5337 */                                         out.write("\n   <input type=\"button\" name=\"xx\" value=\"");
/* 5338 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealert"));
/* 5339 */                                         out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showActionProfiles.do?method=getHAProfiles&haid=");
/* 5340 */                                         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f7, _jspx_page_context))
/*      */                                           return;
/* 5342 */                                         out.write("&wiz=true'\">\n  ");
/* 5343 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 5344 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5348 */                                     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 5349 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                     }
/*      */                                     
/* 5352 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 5353 */                                     out.write("\n   <input type=\"button\" name=\"xx1\" value=\"");
/* 5354 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finish"));
/* 5355 */                                     out.write("\" class=\"buttons btn_highlt\"  onClick=\"javascript:location.href='/showapplication.do?method=showApplication&haid=");
/* 5356 */                                     if (_jspx_meth_c_005fout_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5358 */                                     out.write("'\"></td>\n </tr>\n</tbody>\n</table>\n\t<table class=\"lrbborder\" width=\"99%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">\n\t <tr><td  class=\"bodytext\">");
/* 5359 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createdesc"));
/* 5360 */                                     out.write("</td></tr>\n      <tr><td  class=\"bodytext\">");
/* 5361 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.createandaddmoredesc"));
/* 5362 */                                     out.write("</td></tr>\n     ");
/*      */                                     
/* 5364 */                                     IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5365 */                                     _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 5366 */                                     _jspx_th_c_005fif_005f8.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5368 */                                     _jspx_th_c_005fif_005f8.setTest("${!empty associatedmonitors}");
/* 5369 */                                     int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 5370 */                                     if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                       for (;;) {
/* 5372 */                                         out.write("\n       <tr><td  class=\"bodytext\">");
/* 5373 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.configurealertdesc"));
/* 5374 */                                         out.write("</td></tr>\n    ");
/* 5375 */                                         int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 5376 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5380 */                                     if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 5381 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                     }
/*      */                                     
/* 5384 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 5385 */                                     out.write("\n     <tr><td  class=\"bodytext\">");
/* 5386 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.button.finishdesc"));
/* 5387 */                                     out.write("</td></tr>\n   </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n   <tr>\n   <td width=\"2%\" background=\"/images/wiz_bg_graylind.gif\"><img src=\"../images/wiz_startimage_bottom.gif\" width=\"20\" height=\"19\"></td>\n   <td width=\"94%\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"19\"></td>\n  <td width=\"4%\" align=\"right\" background=\"../images/wiz_bg_grayline_bottom.gif\"><img src=\"../images/wiz_endicon_bottom.gif\" width=\"32\" height=\"19\"></td>\n </tr>\n</table>\n");
/*      */                                   }
/*      */                                   
/*      */ 
/* 5391 */                                   out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/* 5392 */                                   if (title.equals("Web Service"))
/*      */                                   {
/* 5394 */                                     StringBuffer helpcardKey = new StringBuffer();
/* 5395 */                                     helpcardKey.append(FormatUtil.getString("am.webservices.helpcard.text", new String[] { OEMUtil.getOEMString("product.name") }));
/*      */                                     
/* 5397 */                                     out.write(10);
/* 5398 */                                     JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 5399 */                                     out.write(10);
/* 5400 */                                     out.write(10);
/*      */                                   }
/*      */                                   
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 5406 */                                   out.write("\n\t<TABLE width=\"99%\" BORDER=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n      <input name=\"closeButton\" type=\"button\" class=\"buttons btn_link\" value=\"");
/* 5407 */                                   out.print(FormatUtil.getString("Close Window"));
/* 5408 */                                   out.write("\" onClick=\"closePopUpWindow();\">\n      ");
/* 5409 */                                   if (!isDiscoverySuccess) {
/* 5410 */                                     out.write("\n              ");
/*      */                                     
/* 5412 */                                     ResetTag _jspx_th_html_005freset_005f1 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5413 */                                     _jspx_th_html_005freset_005f1.setPageContext(_jspx_page_context);
/* 5414 */                                     _jspx_th_html_005freset_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5416 */                                     _jspx_th_html_005freset_005f1.setStyleClass("buttons btn_back");
/*      */                                     
/* 5418 */                                     _jspx_th_html_005freset_005f1.setValue(FormatUtil.getString("am.webclient.goback.readd.txt"));
/*      */                                     
/* 5420 */                                     _jspx_th_html_005freset_005f1.setOnclick("javascript:history.back();");
/* 5421 */                                     int _jspx_eval_html_005freset_005f1 = _jspx_th_html_005freset_005f1.doStartTag();
/* 5422 */                                     if (_jspx_th_html_005freset_005f1.doEndTag() == 5) {
/* 5423 */                                       this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1); return;
/*      */                                     }
/*      */                                     
/* 5426 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f1);
/* 5427 */                                     out.write("\n      ");
/*      */                                   }
/* 5429 */                                   out.write("\n      </td>\n      </tr>\n      </table>\n");
/*      */                                 }
/*      */                                 
/*      */ 
/* 5433 */                                 out.write(10);
/* 5434 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5435 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 5439 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5440 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 5443 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5444 */                             out.write("\n</td>\n        </tr>\n        </table>\n");
/* 5445 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 5446 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 5449 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 5450 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 5453 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 5454 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 5457 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 5458 */                         out.write(10);
/* 5459 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 5461 */                         out.write(10);
/* 5462 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5463 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 5467 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5468 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 5471 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5472 */                       out.write(10);
/*      */                       
/* 5474 */                       if (hideFields)
/*      */                       {
/*      */ 
/* 5477 */                         out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */                       }
/*      */                       
/*      */ 
/* 5481 */                       out.write("\n\n</body>\n");
/*      */                     }
/* 5483 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5484 */         out = _jspx_out;
/* 5485 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5486 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 5487 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5490 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5496 */     PageContext pageContext = _jspx_page_context;
/* 5497 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5499 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5500 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 5501 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 5503 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 5505 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 5507 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 5508 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 5510 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 5511 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 5513 */           out.write(10);
/* 5514 */           out.write(9);
/* 5515 */           out.write(9);
/* 5516 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 5517 */             return true;
/* 5518 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.AMActionForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 5519 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 5520 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5524 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 5525 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5528 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 5529 */         out = _jspx_page_context.popBody(); }
/* 5530 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 5532 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 5533 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 5535 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5540 */     PageContext pageContext = _jspx_page_context;
/* 5541 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5543 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 5544 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5545 */     _jspx_th_c_005fforEach_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5547 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 5549 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 5551 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 5552 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 5554 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5555 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 5557 */           out.write("\n\t\t\t");
/* 5558 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5559 */             return true;
/* 5560 */           out.write("\n\t\t\t");
/* 5561 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5562 */             return true;
/* 5563 */           out.write("\n\t\t\t");
/* 5564 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5565 */             return true;
/* 5566 */           out.write(10);
/* 5567 */           out.write(9);
/* 5568 */           out.write(9);
/* 5569 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5570 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 5574 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 5575 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 5578 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 5579 */         out = _jspx_page_context.popBody(); }
/* 5580 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 5582 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 5583 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 5585 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5590 */     PageContext pageContext = _jspx_page_context;
/* 5591 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5593 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5594 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 5595 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5597 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 5598 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 5599 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 5601 */         out.write("\n\t\t\t\tsiteName = '");
/* 5602 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5603 */           return true;
/* 5604 */         out.write("';\n\t\t\t");
/* 5605 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 5606 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5610 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 5611 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5612 */       return true;
/*      */     }
/* 5614 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 5615 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5620 */     PageContext pageContext = _jspx_page_context;
/* 5621 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5623 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5624 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5625 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 5627 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 5628 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5629 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5630 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5631 */       return true;
/*      */     }
/* 5633 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5634 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5639 */     PageContext pageContext = _jspx_page_context;
/* 5640 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5642 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5643 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5644 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5646 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 5647 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5648 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5650 */         out.write("\n\t\t\t\tsiteId = '");
/* 5651 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5652 */           return true;
/* 5653 */         out.write("';\n\t\t\t");
/* 5654 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5655 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5659 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5660 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5661 */       return true;
/*      */     }
/* 5663 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5664 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5669 */     PageContext pageContext = _jspx_page_context;
/* 5670 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5672 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5673 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5674 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5676 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 5677 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5678 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5679 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5680 */       return true;
/*      */     }
/* 5682 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5683 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5688 */     PageContext pageContext = _jspx_page_context;
/* 5689 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5691 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5692 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5693 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 5695 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 5696 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5697 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5699 */         out.write("\n\t\t\t\tcustomerId = '");
/* 5700 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 5701 */           return true;
/* 5702 */         out.write("';\n\t\t\t");
/* 5703 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5704 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5708 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5709 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5710 */       return true;
/*      */     }
/* 5712 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5713 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 5718 */     PageContext pageContext = _jspx_page_context;
/* 5719 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5721 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5722 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5723 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5725 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 5726 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5727 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5728 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5729 */       return true;
/*      */     }
/* 5731 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5737 */     PageContext pageContext = _jspx_page_context;
/* 5738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5740 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5741 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 5742 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5744 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 5746 */     _jspx_th_tiles_005fput_005f0.setValue("Web Services");
/* 5747 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 5748 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 5749 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5750 */       return true;
/*      */     }
/* 5752 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 5753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5758 */     PageContext pageContext = _jspx_page_context;
/* 5759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5761 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5762 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5763 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5765 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5767 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 5768 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5769 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5770 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5771 */       return true;
/*      */     }
/* 5773 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5779 */     PageContext pageContext = _jspx_page_context;
/* 5780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5782 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5783 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5784 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 5786 */     _jspx_th_c_005fif_005f6.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 5787 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5788 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 5790 */         out.write("\n      ");
/* 5791 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5792 */           return true;
/* 5793 */         out.write("\n      ");
/* 5794 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5795 */           return true;
/* 5796 */         out.write("\n      ");
/* 5797 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 5798 */           return true;
/* 5799 */         out.write("\n      ");
/* 5800 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5801 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5805 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5806 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5807 */       return true;
/*      */     }
/* 5809 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5810 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5815 */     PageContext pageContext = _jspx_page_context;
/* 5816 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5818 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5819 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 5820 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/* 5821 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 5822 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 5824 */         out.write("\n        ");
/* 5825 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5826 */           return true;
/* 5827 */         out.write("\n        ");
/* 5828 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5829 */           return true;
/* 5830 */         out.write("\n\n        ");
/* 5831 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 5832 */           return true;
/* 5833 */         out.write("\n      ");
/* 5834 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 5835 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5839 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 5840 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5841 */       return true;
/*      */     }
/* 5843 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 5844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5849 */     PageContext pageContext = _jspx_page_context;
/* 5850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5852 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5853 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 5854 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5856 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 5857 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 5858 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 5860 */         out.write("\n          ");
/* 5861 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 5862 */           return true;
/* 5863 */         out.write("\n        ");
/* 5864 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 5865 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5869 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 5870 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5871 */       return true;
/*      */     }
/* 5873 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 5874 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5879 */     PageContext pageContext = _jspx_page_context;
/* 5880 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5882 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5883 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5884 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 5886 */     _jspx_th_c_005fout_005f3.setValue("Web Transaction Monitor");
/* 5887 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5888 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5889 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5890 */       return true;
/*      */     }
/* 5892 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5898 */     PageContext pageContext = _jspx_page_context;
/* 5899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5901 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5902 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 5903 */     _jspx_th_c_005fwhen_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 5905 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 5906 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 5907 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 5909 */         out.write("\n          ");
/* 5910 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 5911 */           return true;
/* 5912 */         out.write("\n        ");
/* 5913 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 5914 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5918 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 5919 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5920 */       return true;
/*      */     }
/* 5922 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 5923 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5928 */     PageContext pageContext = _jspx_page_context;
/* 5929 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5931 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5932 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5933 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 5935 */     _jspx_th_c_005fout_005f4.setValue("Tomcat Server");
/* 5936 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5937 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5938 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5939 */       return true;
/*      */     }
/* 5941 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5942 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5947 */     PageContext pageContext = _jspx_page_context;
/* 5948 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5950 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 5951 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 5952 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 5953 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 5954 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 5956 */         out.write("\n         ");
/* 5957 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 5958 */           return true;
/* 5959 */         out.write("\n        ");
/* 5960 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 5961 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5965 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 5966 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5967 */       return true;
/*      */     }
/* 5969 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 5970 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5975 */     PageContext pageContext = _jspx_page_context;
/* 5976 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5978 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 5979 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5980 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 5982 */     _jspx_th_c_005fout_005f5.setValue("${param.type}");
/* 5983 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5984 */     if (_jspx_eval_c_005fout_005f5 != 0) {
/* 5985 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 5986 */         out = _jspx_page_context.pushBody();
/* 5987 */         _jspx_th_c_005fout_005f5.setBodyContent((BodyContent)out);
/* 5988 */         _jspx_th_c_005fout_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5991 */         out.write(45);
/* 5992 */         int evalDoAfterBody = _jspx_th_c_005fout_005f5.doAfterBody();
/* 5993 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5996 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 5997 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6000 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6001 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6002 */       return true;
/*      */     }
/* 6004 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6010 */     PageContext pageContext = _jspx_page_context;
/* 6011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6013 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6014 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 6015 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6017 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 6018 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 6019 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 6020 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6021 */       return true;
/*      */     }
/* 6023 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 6024 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6029 */     PageContext pageContext = _jspx_page_context;
/* 6030 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6032 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 6033 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 6034 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 6036 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 6037 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 6038 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 6039 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6040 */       return true;
/*      */     }
/* 6042 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 6043 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6048 */     PageContext pageContext = _jspx_page_context;
/* 6049 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6051 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6052 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 6053 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6055 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 6057 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 6058 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 6059 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 6060 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6061 */       return true;
/*      */     }
/* 6063 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 6064 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6069 */     PageContext pageContext = _jspx_page_context;
/* 6070 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6072 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6073 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 6074 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6076 */     _jspx_th_html_005ftext_005f1.setProperty("WSDLUrl");
/*      */     
/* 6078 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext default");
/* 6079 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 6080 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 6081 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6082 */       return true;
/*      */     }
/* 6084 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 6085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6090 */     PageContext pageContext = _jspx_page_context;
/* 6091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6093 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6094 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 6095 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6097 */     _jspx_th_html_005ftext_005f2.setProperty("endPointUrl");
/*      */     
/* 6099 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 6101 */     _jspx_th_html_005ftext_005f2.setSize("25");
/* 6102 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 6103 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 6104 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6105 */       return true;
/*      */     }
/* 6107 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 6108 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6113 */     PageContext pageContext = _jspx_page_context;
/* 6114 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6116 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6117 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 6118 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6120 */     _jspx_th_html_005ftext_005f3.setProperty("pollInterval");
/*      */     
/* 6122 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext small");
/* 6123 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 6124 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 6125 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6126 */       return true;
/*      */     }
/* 6128 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 6129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6134 */     PageContext pageContext = _jspx_page_context;
/* 6135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6137 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6138 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 6139 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6141 */     _jspx_th_html_005ftext_005f4.setProperty("timeout");
/*      */     
/* 6143 */     _jspx_th_html_005ftext_005f4.setValue("30");
/*      */     
/* 6145 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext small");
/* 6146 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 6147 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 6148 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6149 */       return true;
/*      */     }
/* 6151 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fvalue_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 6152 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6157 */     PageContext pageContext = _jspx_page_context;
/* 6158 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6160 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 6161 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 6162 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6164 */     _jspx_th_html_005ftext_005f5.setProperty("username");
/*      */     
/* 6166 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/* 6167 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 6168 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 6169 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6170 */       return true;
/*      */     }
/* 6172 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 6173 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6178 */     PageContext pageContext = _jspx_page_context;
/* 6179 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6181 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.get(PasswordTag.class);
/* 6182 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 6183 */     _jspx_th_html_005fpassword_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6185 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 6187 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/* 6188 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 6189 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 6190 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 6191 */       return true;
/*      */     }
/* 6193 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 6194 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6199 */     PageContext pageContext = _jspx_page_context;
/* 6200 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6202 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6203 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 6204 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6206 */     _jspx_th_html_005ftext_005f6.setProperty("customHeaders");
/*      */     
/* 6208 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext default");
/*      */     
/* 6210 */     _jspx_th_html_005ftext_005f6.setSize("25");
/* 6211 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 6212 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 6213 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6214 */       return true;
/*      */     }
/* 6216 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 6217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6222 */     PageContext pageContext = _jspx_page_context;
/* 6223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6225 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6226 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 6227 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6229 */     _jspx_th_html_005ftext_005f7.setProperty("tokenAndOperation");
/*      */     
/* 6231 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext default");
/*      */     
/* 6233 */     _jspx_th_html_005ftext_005f7.setSize("25");
/* 6234 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 6235 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 6236 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6237 */       return true;
/*      */     }
/* 6239 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 6240 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6245 */     PageContext pageContext = _jspx_page_context;
/* 6246 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6248 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6249 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6250 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6252 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 6253 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6254 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6255 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6256 */       return true;
/*      */     }
/* 6258 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6259 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6264 */     PageContext pageContext = _jspx_page_context;
/* 6265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6267 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6268 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 6269 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 6271 */     _jspx_th_c_005fout_005f7.setValue("${param.haid}");
/* 6272 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 6273 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 6274 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6275 */       return true;
/*      */     }
/* 6277 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 6278 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6283 */     PageContext pageContext = _jspx_page_context;
/* 6284 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6286 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6287 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6288 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6290 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 6291 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6292 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6293 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6294 */       return true;
/*      */     }
/* 6296 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6302 */     PageContext pageContext = _jspx_page_context;
/* 6303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6305 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6306 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 6307 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6309 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 6311 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 6312 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 6313 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6314 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6315 */       return true;
/*      */     }
/* 6317 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 6318 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WSM_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */