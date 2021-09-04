/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.AlarmUtil;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.ParseHtml;
/*      */ import java.net.InetAddress;
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
/*      */ import java.util.Vector;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.action.DynaActionForm;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.OptionsCollectionTag;
/*      */ import org.apache.struts.taglib.html.PasswordTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.SubmitTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ import org.htmlparser.tags.InputTag;
/*      */ 
/*      */ public final class urlseq_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  365 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
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
/*  687 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  833 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  834 */     getRCATrimmedText(div1, rca);
/*  835 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  838 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  839 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  859 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  945 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
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
/*  992 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 1400 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1445 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/* 1843 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1845 */               if (maxCol != null)
/* 1846 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1848 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1843 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
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
/*      */ 
/*      */   private int findBiggestForm(Vector formurls, ParseHtml ph)
/*      */   {
/* 2228 */     int prevcount = 0;
/* 2229 */     int toreturn = 0;
/* 2230 */     for (int i = 0; i < formurls.size(); i++)
/*      */     {
/* 2232 */       Properties props = (Properties)formurls.get(i);
/* 2233 */       String formaction = props.getProperty("value");
/* 2234 */       Hashtable textbox = ph.getTextBox(formaction);
/* 2235 */       if (textbox.size() > prevcount)
/*      */       {
/* 2237 */         prevcount = textbox.size();
/* 2238 */         toreturn = i;
/*      */       }
/*      */     }
/*      */     
/* 2242 */     return toreturn;
/*      */   }
/*      */   
/*      */ 
/* 2246 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2252 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2253 */   static { _jspx_dependants.put("/jsp/includes/monitorGroups.jsp", Long.valueOf(1473429417000L));
/* 2254 */     _jspx_dependants.put("/jsp/includes/newresourcetypes.jspf", Long.valueOf(1473429417000L));
/* 2255 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2301 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2305 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2306 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2307 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2308 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2309 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2310 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2311 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2312 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2313 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2314 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2315 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2316 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2317 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2318 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2319 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2320 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2321 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2322 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2323 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2324 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2325 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2326 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2327 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2328 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2329 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2330 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2331 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2332 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2333 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2334 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2335 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2336 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2337 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2338 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2339 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2340 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2341 */     this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2342 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2343 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2344 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2348 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2349 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2350 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2351 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2352 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2353 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2354 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2355 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2356 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2357 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2358 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2359 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.release();
/* 2360 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.release();
/* 2361 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2362 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2363 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2364 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2365 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2366 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.release();
/* 2367 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.release();
/* 2368 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2369 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.release();
/* 2370 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2371 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2372 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.release();
/* 2373 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2374 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.release();
/* 2375 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.release();
/* 2376 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.release();
/* 2377 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2378 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty.release();
/* 2379 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.release();
/* 2380 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.release();
/* 2381 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2382 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2383 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2384 */     this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fnobody.release();
/* 2385 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2392 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2395 */     JspWriter out = null;
/* 2396 */     Object page = this;
/* 2397 */     JspWriter _jspx_out = null;
/* 2398 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2402 */       response.setContentType("text/html;charset=UTF-8");
/* 2403 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2405 */       _jspx_page_context = pageContext;
/* 2406 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2407 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2408 */       session = pageContext.getSession();
/* 2409 */       out = pageContext.getOut();
/* 2410 */       _jspx_out = out;
/*      */       
/* 2412 */       out.write("<!DOCTYPE html>\n");
/* 2413 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n\n");
/*      */       
/* 2415 */       request.setAttribute("HelpKey", "Configuring URL Sequence");
/*      */       
/* 2417 */       out.write("\n\n\n\n\n\n\n\n\n\n\n");
/* 2418 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2420 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2421 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2422 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2424 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2426 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2428 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2430 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2431 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2432 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2433 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2436 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2437 */         String available = null;
/* 2438 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2439 */         out.write(10);
/*      */         
/* 2441 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2442 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2443 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2445 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2447 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2449 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2451 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2452 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2453 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2454 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2457 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2458 */           String unavailable = null;
/* 2459 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2460 */           out.write(10);
/*      */           
/* 2462 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2463 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2464 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2466 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2468 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2470 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2472 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2473 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2474 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2475 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2478 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2479 */             String unmanaged = null;
/* 2480 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2481 */             out.write(10);
/*      */             
/* 2483 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2484 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2485 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2487 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2489 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2491 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2493 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2494 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2495 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2496 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2499 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2500 */               String scheduled = null;
/* 2501 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2502 */               out.write(10);
/*      */               
/* 2504 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2505 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2506 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2508 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2510 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2512 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2514 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2515 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2516 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2517 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2520 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2521 */                 String critical = null;
/* 2522 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2523 */                 out.write(10);
/*      */                 
/* 2525 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2526 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2527 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2529 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2531 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2533 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2535 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2536 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2537 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2538 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2541 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2542 */                   String clear = null;
/* 2543 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2544 */                   out.write(10);
/*      */                   
/* 2546 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2547 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2548 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2550 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2552 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2554 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2556 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2557 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2558 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2559 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2562 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2563 */                     String warning = null;
/* 2564 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2565 */                     out.write(10);
/* 2566 */                     out.write(10);
/*      */                     
/* 2568 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2569 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2571 */                     out.write(10);
/* 2572 */                     out.write(10);
/* 2573 */                     out.write(10);
/* 2574 */                     out.write(10);
/* 2575 */                     out.write(10);
/* 2576 */                     ManagedApplication mo = null;
/* 2577 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2578 */                     if (mo == null) {
/* 2579 */                       mo = new ManagedApplication();
/* 2580 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2582 */                     out.write(10);
/* 2583 */                     Hashtable applications = null;
/* 2584 */                     synchronized (application) {
/* 2585 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2586 */                       if (applications == null) {
/* 2587 */                         applications = new Hashtable();
/* 2588 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2591 */                     out.write(10);
/* 2592 */                     out.write(10);
/* 2593 */                     out.write(10);
/* 2594 */                     out.write("<!--$Id$-->\n\n\n\n");
/*      */                     
/*      */                     try
/*      */                     {
/* 2598 */                       boolean isprivilege = false;
/* 2599 */                       if (com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) {
/* 2600 */                         isprivilege = true;
/*      */                       }
/* 2602 */                       request.setAttribute("checkForMonitorGroup", Boolean.valueOf(isprivilege));
/*      */                       
/*      */ 
/* 2605 */                       ArrayList dynamicSites = AlarmUtil.getSiteMonitorGroups();
/* 2606 */                       if (dynamicSites != null)
/*      */                       {
/* 2608 */                         request.setAttribute("dynamicSites", dynamicSites);
/*      */                       }
/*      */                       
/* 2611 */                       ArrayList mgList = new ArrayList();
/* 2612 */                       if (EnterpriseUtil.isIt360MSPEdition())
/*      */                       {
/* 2614 */                         com.adventnet.appmanager.struts.form.AMActionForm form = (com.adventnet.appmanager.struts.form.AMActionForm)request.getAttribute("AMActionForm");
/* 2615 */                         ArrayList orgs = AlarmUtil.getCustomerNames();
/*      */                         
/* 2617 */                         if (orgs != null)
/*      */                         {
/* 2619 */                           request.setAttribute("customers", orgs);
/*      */                         }
/*      */                         
/*      */ 
/* 2623 */                         if (form != null)
/*      */                         {
/* 2625 */                           String customerName = form.getOrganization();
/* 2626 */                           if (customerName != null)
/*      */                           {
/* 2628 */                             mgList = AlarmUtil.getSiteMonitorGroups(customerName);
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         
/*      */ 
/*      */                       }
/* 2635 */                       else if (isprivilege)
/*      */                       {
/* 2637 */                         mgList = AlarmUtil.getConfiguredGroups(request, false, false, true);
/*      */                       }
/*      */                       else
/*      */                       {
/* 2641 */                         mgList = AlarmUtil.getConfiguredGroups(null, false, false, true);
/*      */                       }
/*      */                       
/* 2644 */                       if (mgList != null)
/*      */                       {
/* 2646 */                         request.setAttribute("applications", mgList);
/* 2647 */                         if (EnterpriseUtil.isAdminServer()) {
/* 2648 */                           ArrayList adminMGroups = getMGroupsCreatedInAdminServer(mgList);
/* 2649 */                           request.setAttribute("AllMonitorGroupsInAdminServer", mgList);
/* 2650 */                           request.setAttribute("MonitorGroupsCreatedInAdminServer", adminMGroups);
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 2656 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 2659 */                     out.write(10);
/* 2660 */                     out.write(10);
/* 2661 */                     out.write(10);
/* 2662 */                     out.write(10);
/*      */                     
/*      */ 
/*      */ 
/* 2666 */                     String resourceid = request.getParameter("resourceid");
/* 2667 */                     String haid = request.getParameter("haid");
/* 2668 */                     String isDiscUrlSeqComplete = (String)request.getAttribute("isDiscUrlSeqComplete");
/* 2669 */                     isDiscUrlSeqComplete = "true";
/*      */                     
/* 2671 */                     String host = "http://localhost";
/* 2672 */                     String port = "80";
/*      */                     
/* 2674 */                     org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2675 */                     token.saveToken(request);
/*      */                     
/* 2677 */                     String title = request.getParameter("type");
/* 2678 */                     if (title == null)
/*      */                     {
/* 2680 */                       title = "UrlSeq";
/*      */                     }
/*      */                     
/*      */ 
/* 2684 */                     out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\nfunction newSequence()\n{\n\tlocation.href='");
/* 2685 */                     out.print(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/adminAction.do?method=reloadHostDiscoveryForm&type=UrlSeq&restype=UrlSeq");
/* 2686 */                     out.write("';\t\n}\nfunction toggleAdv(name)\n{\n\tif(name='advanced')\n\t{\n\t\tif(document.UrlForm.advanced.checked)\n\t\t{\n\t\t\tshowDiv('advancedDiv');\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('advancedDiv');\t\n\t\t}\n\t}\t\n}\n\nfunction showLayers(combo)\n{\n\tvar combolength  = combo.length;\n\tvar selectedlayer = 'formlayer'+combo.selectedIndex; //combo.options[combo.selectedIndex].value;\n\tvar bool = true;\n\tfor(k=0;k<combolength;k++)\n\t{\n\t  var temp ='formlayer'+k;// combo.options[k].value;\n\n\t  if ((obj=MM_findObj(temp))!=null)\n\t  {\n\t  \tif((temp == selectedlayer) && (bool == true))\n\t  \t{\n\t  \t\tv='block';\n\n\t  \t\tbool=false; //Already one is shown. So dont come into this loop again\n\t  \t}\n\t  \telse\n\t  \t{\n\t  \t\tv='none';\n\t  \t}\n\t     if (obj.style)\n\t     \t{\n\t     \t\t//obj=obj.style;\n\t     \t\t//v=(v=='show')?'visible':(v=='hide')?'hidden':v;\n\t     \t}\n    \t\t//obj.visibility=v;\n    \t\tobj.style.display = v;\n\n    \t   }\n    \t   else\n    \t   {\n    \t   \talert('");
/* 2687 */                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.alert.notfound"));
/* 2688 */                     out.write("');\n    \t   }\n\t }\n}\nfunction MM_reloadPage(init) {  //reloads the window if Nav4 resized\n  if (init==true) with (navigator) {if ((appName==\"Netscape\")&&(parseInt(appVersion)==4)) {\n    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}\n  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();\n}\nMM_reloadPage(true);\n\nfunction MM_findObj(n, d) { //v4.01\n  var p,i,x;  if(!d) d=document; if((p=n.indexOf(\"?\"))>0&&parent.frames.length) {\n    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}\n  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];\n  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);\n  if(!x && d.getElementById) x=d.getElementById(n); return x;\n}\n\nfunction MM_showHideLayers() { //v6.0\n  var i,p,v,obj,args=MM_showHideLayers.arguments;\n  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];\n    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v=='hide')?'hidden':v; }\n");
/* 2689 */                     out.write("    obj.visibility=v; }\n}\n\n</script>\n\n\n<script>\n\nfunction loadSite()\n{\n\tdocument.UrlForm.haid.options.length=0;\n\tvar formCustomerId = document.UrlForm.organization.value;\n\tvar siteName;\n\tvar siteId;\n\tvar customerId;\n\tvar rowCount=0;\n\tdocument.UrlForm.haid.options[rowCount++] = new Option('-Select Site-','-'); //No I18N\n\t");
/* 2690 */                     if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
/*      */                       return;
/* 2692 */                     out.write("\n}\n\n\nvar http = \"\";\nfunction resetname(name)\n{\n\tif(name='groupname')\n\t{\n\t\tdocument.UrlForm.groupname.value='';\n\t}\n\tif(name='subgroupname')\n\t{\n\t\tdocument.UrlForm.subgroupname.value='';\n\t}\n}\nfunction createGroup()\n{\n\tif(document.UrlForm.groupname.value=='')\n\t{\n\t\talert(\"");
/* 2693 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2694 */                     out.write("\");\n\t\thideDiv('group');\n\t\tshowDiv('creategroup');\n\t\tdocument.UrlForm.groupname.focus();\n\t\treturn false;\n\t}\n\telse\n\t{\n\t\thideDiv('creategroup');\n\t\tvar a=document.UrlForm.groupname.value;\n\t\turl=\"/adminAction.do?method=createMonitorGroup&groupname=\"+encodeURIComponent(a);\n\t\thttp.open(\"GET\",url,true);\n\t\thttp.onreadystatechange = getActionTypes;\n\t\thttp.send(null);\n\t\tshowDiv('group');\n\t}\n\n}  \nfunction check()\n{\n\thideDiv(\"creategroup\");\n\thideDiv(\"createsubgroup\");\n\thideDiv(\"groupmessage\");\n\tvar flag='");
/* 2695 */                     out.print(com.adventnet.appmanager.util.Constants.subGroupsEnabled);
/* 2696 */                     out.write("';\n\tif(flag==\"true\")\n\t{\n\t\tif(document.UrlForm.haid.value=='-')\n\t\t{\n\t\t\thideDiv(\"subgroup\");\n\t\t\tshowDiv(\"group\");\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv(\"group\");\n\t\t\tshowDiv(\"subgroup\");\n\t\t}\n\t}\n\telse\n\t{\n\t\tshowDiv(\"group\");\n\t}\n}\nfunction createsubGroup()\n{\n\tif(document.UrlForm.haid.value=='-')\n\t{\n\t\talert(\"");
/* 2697 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.monitoralert"));
/* 2698 */                     out.write("\");\t\t\n\t\tdocument.UrlForm.haid.focus();\n\t}\n\telse\n\t{\n\t\tif(document.UrlForm.subgroupname.value=='')\n\t\t{\n\t\t\talert(\"");
/* 2699 */                     out.print(FormatUtil.getString("am.webclient.monitorsubgroup.emptyalert"));
/* 2700 */                     out.write("\");\n\t\t\tdocument.UrlForm.subgroupname.focus();\n\t\t\treturn false;\n\t\t}\n\t\telse\n\t\t{\n\t\t\thideDiv('createsubgroup');\n\t\t\tvar a=document.UrlForm.subgroupname.value;\n\t\t\tvar haid=document.UrlForm.haid.value;\n\t\t\turl=\"/adminAction.do?method=createSubGroup&haid=\"+haid+\"&subgroupname=\"+encodeURIComponent(a);\n\t\t\thttp.open(\"GET\",url,true);\n\t\t\thttp.onreadystatechange = getActionTypes;\n\t\t\thttp.send(null);\n\t\t}\n\t\tshowDiv('subgroup');\n    }\n\n}  \n\n function getActionTypes()\n{\n    if(http.readyState == 4)\n    {\n\t\tvar result = http.responseText;\n\t\tvar id=result;\n\t\tvar stringtokens=id.split(\",\");\n\t\tsid = stringtokens[2];\n\t\tsname=stringtokens[1];\n\t\tsname=decodeURIComponent(stringtokens[1]);\n\t\tsmessage=stringtokens[0];\n\t\tif (sname==null || sname=='undefined')\n\t\t{\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.UrlForm.haid.options[document.UrlForm.haid.length] =new Option(sname,sid,false,true);\n\t\t\thideDiv(\"creategroup\");\n\t\t\thideDiv(\"createsubgroup\");\n\t\t\thideDiv(\"group\");\n");
/* 2701 */                     out.write("\t\t\tshowDiv(\"subgroup\");\n\t\t\tshowDiv(\"groupmessage\");\n\t\t\tdocument.getElementById(\"groupmessage\").innerHTML=smessage;\n\t  \t}\n\t}\n}\n\n\n\n\n function urlvalidate()\n {\n ");
/*      */                     
/* 2703 */                     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2704 */                     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2705 */                     _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                     
/* 2707 */                     _jspx_th_logic_005fempty_005f0.setName("UrlForm");
/* 2708 */                     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2709 */                     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                       for (;;) {
/* 2711 */                         out.write("\n       var dispname=document.UrlForm.userseqname.value\n        if(dispname== '')\n          {\n\n            alert('");
/* 2712 */                         out.print(FormatUtil.getString("am.webclient.common.jsalertfordisplayname.text"));
/* 2713 */                         out.write("');\n             document.UrlForm.userseqname.select();\n                return;\n           }\n           var quote1=\"'\" ;\n\tif(dispname.indexOf(quote1) != -1)\n        {\n            alert(\"");
/* 2714 */                         out.print(FormatUtil.getString("am.webclient.common.jsalertforsinglequote.text"));
/* 2715 */                         out.write("\");\n                document.UrlForm.userseqname.select();\n                return;\n         }\n\n\n\n \tvar url=trimAll(document.UrlForm.url.value);\n \tif(url=='')\n \t{\n \t\talert('");
/* 2716 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.alert.url"));
/* 2717 */                         out.write("');\n \t\treturn false\n \t}\n\tif(!checkUrlPattern(url))\n\t{\n\t\talert(\"");
/* 2718 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.urladd"));
/* 2719 */                         out.write("\");\n\t\tdocument.UrlForm.url.focus();\n\t\treturn;\n\t}\n\t");
/*      */                         
/* 2721 */                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2722 */                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2723 */                         _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_logic_005fempty_005f0);
/*      */                         
/* 2725 */                         _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2726 */                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2727 */                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2729 */                             out.write("\n\n\tif(url.indexOf(\"google.com\") != -1 || url.indexOf(\"google.co\") != -1)\n        {\n                alert(\"");
/* 2730 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.google"));
/* 2731 */                             out.write("\");\n                document.UrlForm.url.value='http://';\n                return ;\n        }\n        if(url.indexOf(\"yahoo.com\") != -1 || url.indexOf(\"yahoo.co\") != -1)\n        {\n                alert(\"");
/* 2732 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.yahoo"));
/* 2733 */                             out.write("\");\n                document.UrlForm.url.value='http://';\n                return ;\n        }\n\n\t");
/* 2734 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2735 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2739 */                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2740 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                         }
/*      */                         
/* 2743 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2744 */                         out.write("\n\n\t \tvar poll=trimAll(document.UrlForm.pollInterval.value);\n\t\t \tif(poll == '' || !(isPositiveInteger(poll)) || poll =='0')\n\t\t\t{\n\t\t\t\talert(\"");
/* 2745 */                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.alert.polling"));
/* 2746 */                         out.write("\");\n\t\t\t\t\treturn false\n\t\t \t}\n\n\t");
/*      */                         
/* 2748 */                         PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2749 */                         _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2750 */                         _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_logic_005fempty_005f0);
/*      */                         
/* 2752 */                         _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2753 */                         int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2754 */                         if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                           for (;;) {
/* 2756 */                             out.write("\n\t if(parseInt(poll) <15)\n                        {\n                alert(\"");
/* 2757 */                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.alert.pollgr15"));
/* 2758 */                             out.write("\");\n                                document.UrlForm.pollInterval.value=15;\n                                document.UrlForm.pollInterval.focus();\n                                return;\n                        }\n\n\n\n\t");
/* 2759 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2760 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2764 */                         if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2765 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                         }
/*      */                         
/* 2768 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2769 */                         out.write("\n\n\n\n\n \t//\n ");
/* 2770 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2771 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2775 */                     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2776 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*      */                     }
/*      */                     else {
/* 2779 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2780 */                       out.write("\n\n\n\n \treturn true\n\n }\n\nfunction fnOpenWindow()\n{\n\tvar window2=open('/html/reqparams.html','secondWindow','resizable=no,scrollbars=no,width=250,height=140');\n\twindow2.focus();\n}\nfunction fnsubmit()\n{\n       if(document.UrlForm.opt[0].checked == true)\n            {\n\n                if((document.UrlForm.url.length==1)&&(document.UrlForm.url.value==''))\n                {\n                    alert (\"");
/* 2781 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.alert.addurl"));
/* 2782 */                       out.write("\");\n                     return;\n                }\n\n            }\n        else if(document.UrlForm.opt[1].checked == true)\n            {\n\n                 if((document.UrlForm.formurl.length==1)&&(document.UrlForm.formurl.value==''))\n                {\n                    alert (\"");
/* 2783 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.alert.addurl"));
/* 2784 */                       out.write("\");\n                     return;\n                }\n            }\n         else if(document.UrlForm.opt[2].checked == true)\n            {\n\n                if((document.UrlForm.frame.length==1)&&(document.UrlForm.frame.value==''))\n                {\n                    alert (\"");
/* 2785 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.alert.addurl"));
/* 2786 */                       out.write("\");\n                     return;\n                }\n\n            }\n           else if (document.UrlForm.opt[3].checked == true)\n           {\n                   if (document.UrlForm.otherurl.value==\"\")\n                {\n                    alert (\"");
/* 2787 */                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.alert.addurl"));
/* 2788 */                       out.write("\");\n                     return;\n                }\n           }\n\n\n       if(urlvalidate())\n       {\n\n\n\n\tdocument.UrlForm.finish.value=\"true\";\n\tdocument.UrlForm.submit();\n\t}\n}\n function fnFinish()\n {\n \tif(urlvalidate())\n \t{\n\n \t\tdocument.UrlForm.finish.value=\"true\";\n                document.UrlForm.submit();\n \t}\n }\n\n function fnAddSeq()\n {\n \tif(urlvalidate())\n \t{\n \t\tdocument.UrlForm.submit();\n \t}\n }\n\nfunction formReload()\n{\n    var v=document.UrlForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.UrlForm.method=\"post\";\n    document.UrlForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v+\"&hideFieldsForIT360=");
/* 2789 */                       out.print(request.getParameter("hideFieldsForIT360"));
/* 2790 */                       out.write("\";\n    //enableAll();\n    document.UrlForm.submit();\n}\n\n</script>\n\n<script language=\"JavaScript1.2\">\n\nvar bIsOpenDiscoveryDiv=false;\nvar bIsOpenFilterDiv=false;\nvar bIsOpenThreshDiv=true;\nvar bIsOpenApplicationDiv=true;\nvar bIsOpenUsersDiv=true;\n\n\n            var plusSign                    = \"../images/icon_plus.gif\";\n            var minusSign                   = \"../images/icon_minus.gif\";\n            var currentProfileId            = \"-1\";\n\n\n   // Inverts the state of the given expand menu name + the related div (if any).\n    function InvertDivState(name, divName){\n        var oDiv  = document.getElementById(name+\"Div\");\n        var oSpan = document.getElementById(name+\"Sign\");\n        var oTd   = document.getElementById(name+\"Td\");\n        eval(\"bIsOpen\" + name + \"Div = !bIsOpen\" + name + \"Div\");\n        var bIsOpen = eval(\"bIsOpen\" + name + \"Div\");\n        if(bIsOpen){\n          if(typeof(divName)!=\"undefined\") document.getElementById(divName).style.display = \"block\";\n          oDiv.style.display = \"block\";\n");
/* 2791 */                       out.write("          oSpan.src = minusSign;\n        }else {\n          if(typeof(divName)!=\"undefined\") document.getElementById(divName).style.display = \"none\";\n          oDiv.style.display = \"none\";\n          oSpan.src = plusSign;\n        }\n    }\n\n</script>\n</head>\n\n");
/*      */                       
/*      */ 
/* 2794 */                       boolean hideFields = false;
/* 2795 */                       String hideStyle = "";
/* 2796 */                       String hideFieldsForIT360 = request.getParameter("hideFieldsForIT360");
/* 2797 */                       if (hideFieldsForIT360 == null)
/*      */                       {
/* 2799 */                         hideFieldsForIT360 = (String)request.getAttribute("hideFieldsForIT360");
/*      */                       }
/* 2801 */                       if ((hideFieldsForIT360 != null) && (hideFieldsForIT360.equals("true")))
/*      */                       {
/* 2803 */                         hideFields = true;
/* 2804 */                         hideStyle = "hideContent";
/*      */                       }
/* 2806 */                       String leftArea = com.adventnet.appmanager.util.Constants.isIt360 ? "/jsp/test.jsp" : "/jsp/UrlMonitorsLeftPage.jsp";
/* 2807 */                       String tilesLayout = "/jsp/BasicLayoutNoLeft.jsp";
/*      */                       
/* 2809 */                       out.write("\n\n<!--  Your area begins here -->\n\n");
/*      */                       
/* 2811 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2812 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2813 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2815 */                       _jspx_th_tiles_005finsert_005f0.setPage(tilesLayout);
/* 2816 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2817 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2819 */                           out.write(10);
/* 2820 */                           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2822 */                           out.write(10);
/* 2823 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2825 */                           out.write(10);
/*      */                           
/* 2827 */                           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2828 */                           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2829 */                           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2831 */                           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */                           
/* 2833 */                           _jspx_th_tiles_005fput_005f2.setValue(leftArea);
/* 2834 */                           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2835 */                           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2836 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                           }
/*      */                           
/* 2839 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2840 */                           out.write(10);
/*      */                           
/* 2842 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2843 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2844 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2846 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 2848 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2849 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2850 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2851 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2852 */                               out = _jspx_page_context.pushBody();
/* 2853 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2854 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2857 */                               out.write(10);
/* 2858 */                               out.write(10);
/*      */                               
/* 2860 */                               if ((isDiscUrlSeqComplete == null) || (!isDiscUrlSeqComplete.equals("true")))
/*      */                               {
/*      */ 
/* 2863 */                                 out.write(10);
/* 2864 */                                 if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                                   return;
/* 2866 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td width=\"65%\" valign=\"top\">\n\t");
/*      */                                 
/* 2868 */                                 FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.get(FormTag.class);
/* 2869 */                                 _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2870 */                                 _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 2872 */                                 _jspx_th_html_005fform_005f0.setAction("/updateUrl");
/*      */                                 
/* 2874 */                                 _jspx_th_html_005fform_005f0.setFocus("url");
/* 2875 */                                 int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2876 */                                 if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                   for (;;) {
/* 2878 */                                     out.write(10);
/*      */                                     
/* 2880 */                                     EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 2881 */                                     _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2882 */                                     _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 2884 */                                     _jspx_th_logic_005fempty_005f1.setName("UrlForm");
/*      */                                     
/* 2886 */                                     _jspx_th_logic_005fempty_005f1.setProperty("userseqid");
/* 2887 */                                     int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2888 */                                     if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                       for (;;) {
/* 2890 */                                         out.write(10);
/*      */                                         
/* 2892 */                                         IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2893 */                                         _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2894 */                                         _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fempty_005f1);
/*      */                                         
/* 2896 */                                         _jspx_th_c_005fif_005f3.setTest("${empty param.wiz}");
/* 2897 */                                         int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2898 */                                         if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                           for (;;) {
/* 2900 */                                             out.write("\n    ");
/* 2901 */                                             out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n<link rel=\"stylesheet\" href=\"/images/chosen.css\" />\n");
/* 2902 */                                             String message = (String)request.getAttribute("typemessage");
/*      */                                             
/* 2904 */                                             ManagedApplication mo1 = new ManagedApplication();
/* 2905 */                                             Properties props = com.adventnet.appmanager.util.Constants.getValueForNewMonitor();
/* 2906 */                                             boolean isConfMonitor = false;
/* 2907 */                                             ConfMonitorConfiguration confMonConfig = ConfMonitorConfiguration.getInstance();
/* 2908 */                                             if (message != null)
/*      */                                             {
/* 2910 */                                               out.write("\n    <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\" >\n    <tr>\n      <td><img src=\"/images/icon_message_success.gif\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class=\"bodytext\">\n        ");
/* 2911 */                                               out.print(message);
/* 2912 */                                               out.write("</span> </td>\n    </tr>\n  </table>\n     ");
/*      */                                             }
/*      */                                             
/*      */ 
/* 2916 */                                             out.write("\n\n\n<table id=\"newResourceTypes\" width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n  <tr>\n\t<td width=\"25%\" class=\"tableheading-monitor-config bodytext label-align addmonitor-label\">&nbsp;");
/* 2917 */                                             out.print(FormatUtil.getString("am.webclient.newresourcetypes.addmonitor.text"));
/* 2918 */                                             out.write("</td>\n    <td class=\"tableheading-monitor-config \" valign=\"middle\">\n");
/* 2919 */                                             if ("UrlSeq".equals(request.getParameter("type"))) {
/* 2920 */                                               DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2921 */                                               if (frm != null) {
/* 2922 */                                                 frm.set("type", "UrlSeq");
/*      */                                               }
/*      */                                             }
/*      */                                             
/* 2926 */                                             if ("UrlMonitor".equals(request.getParameter("type"))) {
/* 2927 */                                               DynaActionForm frm = (DynaActionForm)request.getAttribute("UrlForm");
/* 2928 */                                               if (frm != null) {
/* 2929 */                                                 frm.set("type", "UrlMonitor");
/*      */                                               }
/*      */                                             }
/*      */                                             
/* 2933 */                                             if ("RBM".equals(request.getParameter("type"))) {
/* 2934 */                                               DynaActionForm frm = (DynaActionForm)request.getAttribute("RbmForm");
/* 2935 */                                               if (frm != null) {
/* 2936 */                                                 frm.set("type", "RBM");
/*      */                                               }
/*      */                                             }
/*      */                                             
/*      */ 
/* 2941 */                                             out.write("\n\n    ");
/*      */                                             
/* 2943 */                                             IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2944 */                                             _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2945 */                                             _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f3);
/*      */                                             
/* 2947 */                                             _jspx_th_c_005fif_005f4.setTest("${empty param.wiz && empty param.fromAssociate}");
/* 2948 */                                             int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2949 */                                             if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                               for (;;) {
/* 2951 */                                                 out.write("\n     ");
/*      */                                                 
/* 2953 */                                                 SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2954 */                                                 _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2955 */                                                 _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f4);
/*      */                                                 
/* 2957 */                                                 _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                                 
/* 2959 */                                                 _jspx_th_html_005fselect_005f0.setStyle("background-color:#FDFEF2; font-size:13px;");
/*      */                                                 
/* 2961 */                                                 _jspx_th_html_005fselect_005f0.setStyleClass("formtext");
/*      */                                                 
/* 2963 */                                                 _jspx_th_html_005fselect_005f0.setOnchange("javascript:formReload()");
/* 2964 */                                                 int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2965 */                                                 if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2966 */                                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2967 */                                                     out = _jspx_page_context.pushBody();
/* 2968 */                                                     _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2969 */                                                     _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 2972 */                                                     out.write("\n\n      <!-- If you are changing any of the values in this select box then kindly update the corresponding strings in HostDiscoveryHandler.java also-->\n      ");
/*      */                                                     
/* 2974 */                                                     if ((!com.adventnet.appmanager.util.Constants.isMinimizedversion()) || (com.adventnet.appmanager.util.Constants.getUserType().equals("F")) || (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                     {
/*      */ 
/*      */ 
/* 2978 */                                                       out.write("\n\n\t <optgroup label=\"");
/* 2979 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 2980 */                                                       out.write("\">\n                                        \n                                        ");
/*      */                                                       
/* 2982 */                                                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 2983 */                                                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2984 */                                                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 2986 */                                                       _jspx_th_html_005foption_005f0.setValue("AIX");
/* 2987 */                                                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2988 */                                                       if (_jspx_eval_html_005foption_005f0 != 0) {
/* 2989 */                                                         if (_jspx_eval_html_005foption_005f0 != 1) {
/* 2990 */                                                           out = _jspx_page_context.pushBody();
/* 2991 */                                                           _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 2992 */                                                           _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 2995 */                                                           out.print(FormatUtil.getString("AIX"));
/* 2996 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 2997 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3000 */                                                         if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3001 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3004 */                                                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3005 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                                       }
/*      */                                                       
/* 3008 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 3009 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3011 */                                                       OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3012 */                                                       _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3013 */                                                       _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3015 */                                                       _jspx_th_html_005foption_005f1.setValue("AS400");
/* 3016 */                                                       int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3017 */                                                       if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3018 */                                                         if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3019 */                                                           out = _jspx_page_context.pushBody();
/* 3020 */                                                           _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3021 */                                                           _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3024 */                                                           out.print(FormatUtil.getString("AS400/iSeries"));
/* 3025 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3026 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3029 */                                                         if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3030 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3033 */                                                       if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3034 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                                       }
/*      */                                                       
/* 3037 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f1);
/* 3038 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3040 */                                                       OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3041 */                                                       _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 3042 */                                                       _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3044 */                                                       _jspx_th_html_005foption_005f2.setValue("FreeBSD");
/* 3045 */                                                       int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 3046 */                                                       if (_jspx_eval_html_005foption_005f2 != 0) {
/* 3047 */                                                         if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3048 */                                                           out = _jspx_page_context.pushBody();
/* 3049 */                                                           _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 3050 */                                                           _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3053 */                                                           out.print(FormatUtil.getString("FreeBSD/OpenBSD"));
/* 3054 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 3055 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3058 */                                                         if (_jspx_eval_html_005foption_005f2 != 1) {
/* 3059 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3062 */                                                       if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 3063 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                                       }
/*      */                                                       
/* 3066 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f2);
/* 3067 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3069 */                                                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3070 */                                                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 3071 */                                                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3073 */                                                       _jspx_th_html_005foption_005f3.setValue("HP-UX");
/* 3074 */                                                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 3075 */                                                       if (_jspx_eval_html_005foption_005f3 != 0) {
/* 3076 */                                                         if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3077 */                                                           out = _jspx_page_context.pushBody();
/* 3078 */                                                           _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 3079 */                                                           _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3082 */                                                           out.print(FormatUtil.getString("HP-UX  / Tru64"));
/* 3083 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 3084 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3087 */                                                         if (_jspx_eval_html_005foption_005f3 != 1) {
/* 3088 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3091 */                                                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 3092 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                                       }
/*      */                                                       
/* 3095 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f3);
/* 3096 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3098 */                                                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3099 */                                                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 3100 */                                                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3102 */                                                       _jspx_th_html_005foption_005f4.setValue("Linux");
/* 3103 */                                                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 3104 */                                                       if (_jspx_eval_html_005foption_005f4 != 0) {
/* 3105 */                                                         if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3106 */                                                           out = _jspx_page_context.pushBody();
/* 3107 */                                                           _jspx_th_html_005foption_005f4.setBodyContent((BodyContent)out);
/* 3108 */                                                           _jspx_th_html_005foption_005f4.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3111 */                                                           out.print(FormatUtil.getString("Linux"));
/* 3112 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f4.doAfterBody();
/* 3113 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3116 */                                                         if (_jspx_eval_html_005foption_005f4 != 1) {
/* 3117 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3120 */                                                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 3121 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                                       }
/*      */                                                       
/* 3124 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f4);
/* 3125 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3127 */                                                       OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3128 */                                                       _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 3129 */                                                       _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3131 */                                                       _jspx_th_html_005foption_005f5.setValue("Mac OS");
/* 3132 */                                                       int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 3133 */                                                       if (_jspx_eval_html_005foption_005f5 != 0) {
/* 3134 */                                                         if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3135 */                                                           out = _jspx_page_context.pushBody();
/* 3136 */                                                           _jspx_th_html_005foption_005f5.setBodyContent((BodyContent)out);
/* 3137 */                                                           _jspx_th_html_005foption_005f5.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3140 */                                                           out.print(FormatUtil.getString("Mac OS"));
/* 3141 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f5.doAfterBody();
/* 3142 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3145 */                                                         if (_jspx_eval_html_005foption_005f5 != 1) {
/* 3146 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3149 */                                                       if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 3150 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                                       }
/*      */                                                       
/* 3153 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f5);
/* 3154 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3156 */                                                       OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3157 */                                                       _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 3158 */                                                       _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3160 */                                                       _jspx_th_html_005foption_005f6.setValue("Novell");
/* 3161 */                                                       int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 3162 */                                                       if (_jspx_eval_html_005foption_005f6 != 0) {
/* 3163 */                                                         if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3164 */                                                           out = _jspx_page_context.pushBody();
/* 3165 */                                                           _jspx_th_html_005foption_005f6.setBodyContent((BodyContent)out);
/* 3166 */                                                           _jspx_th_html_005foption_005f6.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3169 */                                                           out.print(FormatUtil.getString("Novell"));
/* 3170 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f6.doAfterBody();
/* 3171 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3174 */                                                         if (_jspx_eval_html_005foption_005f6 != 1) {
/* 3175 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3178 */                                                       if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 3179 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                                       }
/*      */                                                       
/* 3182 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f6);
/* 3183 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3185 */                                                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3186 */                                                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 3187 */                                                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3189 */                                                       _jspx_th_html_005foption_005f7.setValue("Sun Solaris");
/* 3190 */                                                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 3191 */                                                       if (_jspx_eval_html_005foption_005f7 != 0) {
/* 3192 */                                                         if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3193 */                                                           out = _jspx_page_context.pushBody();
/* 3194 */                                                           _jspx_th_html_005foption_005f7.setBodyContent((BodyContent)out);
/* 3195 */                                                           _jspx_th_html_005foption_005f7.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3198 */                                                           out.print(FormatUtil.getString("Sun Solaris"));
/* 3199 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f7.doAfterBody();
/* 3200 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3203 */                                                         if (_jspx_eval_html_005foption_005f7 != 1) {
/* 3204 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3207 */                                                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 3208 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                                       }
/*      */                                                       
/* 3211 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f7);
/* 3212 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3214 */                                                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3215 */                                                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 3216 */                                                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3218 */                                                       _jspx_th_html_005foption_005f8.setValue("Windows");
/* 3219 */                                                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 3220 */                                                       if (_jspx_eval_html_005foption_005f8 != 0) {
/* 3221 */                                                         if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3222 */                                                           out = _jspx_page_context.pushBody();
/* 3223 */                                                           _jspx_th_html_005foption_005f8.setBodyContent((BodyContent)out);
/* 3224 */                                                           _jspx_th_html_005foption_005f8.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3227 */                                                           out.print(FormatUtil.getString("Windows"));
/* 3228 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f8.doAfterBody();
/* 3229 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3232 */                                                         if (_jspx_eval_html_005foption_005f8 != 1) {
/* 3233 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3236 */                                                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 3237 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                                       }
/*      */                                                       
/* 3240 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f8);
/* 3241 */                                                       out.write("\n                                        ");
/*      */                                                       
/* 3243 */                                                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3244 */                                                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 3245 */                                                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3247 */                                                       _jspx_th_html_005foption_005f9.setValue("Windows Cluster");
/* 3248 */                                                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 3249 */                                                       if (_jspx_eval_html_005foption_005f9 != 0) {
/* 3250 */                                                         if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3251 */                                                           out = _jspx_page_context.pushBody();
/* 3252 */                                                           _jspx_th_html_005foption_005f9.setBodyContent((BodyContent)out);
/* 3253 */                                                           _jspx_th_html_005foption_005f9.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3256 */                                                           out.print(FormatUtil.getString("Windows Cluster"));
/* 3257 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f9.doAfterBody();
/* 3258 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3261 */                                                         if (_jspx_eval_html_005foption_005f9 != 1) {
/* 3262 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3265 */                                                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 3266 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                                       }
/*      */                                                       
/* 3269 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f9);
/* 3270 */                                                       out.write("\n                                        \n\n  ");
/*      */                                                       
/* 3272 */                                                       ArrayList rows1 = mo1.getRows("select RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH from AM_ManagedResourceType,AM_MONITOR_TYPES where TYPEID=RESOURCETYPEID and RESOURCEGROUP='SYS' and AMCREATED='NO' ORDER BY UPPER(DISPLAYNAME)");
/* 3273 */                                                       if ((rows1 != null) && (rows1.size() > 0))
/*      */                                                       {
/* 3275 */                                                         for (int i = 0; i < rows1.size(); i++)
/*      */                                                         {
/* 3277 */                                                           ArrayList row = (ArrayList)rows1.get(i);
/* 3278 */                                                           String res = (String)row.get(0);
/* 3279 */                                                           String dname = (String)row.get(1);
/* 3280 */                                                           String values = props.getProperty(res);
/* 3281 */                                                           if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                           {
/*      */ 
/* 3284 */                                                             out.write("\n\t\t\t\t");
/*      */                                                             
/* 3286 */                                                             OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3287 */                                                             _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 3288 */                                                             _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                             
/* 3290 */                                                             _jspx_th_html_005foption_005f10.setValue(values);
/* 3291 */                                                             int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 3292 */                                                             if (_jspx_eval_html_005foption_005f10 != 0) {
/* 3293 */                                                               if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3294 */                                                                 out = _jspx_page_context.pushBody();
/* 3295 */                                                                 _jspx_th_html_005foption_005f10.setBodyContent((BodyContent)out);
/* 3296 */                                                                 _jspx_th_html_005foption_005f10.doInitBody();
/*      */                                                               }
/*      */                                                               for (;;) {
/* 3299 */                                                                 out.write(32);
/* 3300 */                                                                 out.print(FormatUtil.getString(dname));
/* 3301 */                                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f10.doAfterBody();
/* 3302 */                                                                 if (evalDoAfterBody != 2)
/*      */                                                                   break;
/*      */                                                               }
/* 3305 */                                                               if (_jspx_eval_html_005foption_005f10 != 1) {
/* 3306 */                                                                 out = _jspx_page_context.popBody();
/*      */                                                               }
/*      */                                                             }
/* 3309 */                                                             if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 3310 */                                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                                             }
/*      */                                                             
/* 3313 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f10);
/* 3314 */                                                             out.write("\n\t\t\t");
/*      */                                                           }
/*      */                                                         }
/*      */                                                       }
/*      */                                                       
/*      */ 
/* 3320 */                                                       String[] categoryLink = { "APP", "ERP", "TM", "SYS", "DBS", "SER", "URL", "MS", "MOM", "CAM", "VIR", "CLD" };
/*      */                                                       
/* 3322 */                                                       String[] categoryTitle = { "am.webclient.monitorgroupsecond.category.appserver", "am.webclient.monitorgroupsecond.category.erp", "am.webclient.monitorgroupsecond.category.transaction", "am.webclient.monitorgroupsecond.category.servers", "am.webclient.monitorgroupsecond.category.dbserver", "am.webclient.monitorgroupsecond.category.services", "am.webclient.monitorgroupsecond.category.webservices.title", "am.webclient.monitorgroupsecond.category.mailserver", "Middleware/Portal", "am.webclient.monitorgroupsecond.category.custom", "am.webclient.monitorgroupsecond.category.virtualserver", "am.webclient.monitorgroupsecond.category.cloudapps" };
/*      */                                                       
/*      */ 
/* 3325 */                                                       if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD"))
/*      */                                                       {
/*      */ 
/* 3328 */                                                         categoryLink = com.adventnet.appmanager.util.Constants.categoryLink;
/* 3329 */                                                         categoryTitle = com.adventnet.appmanager.util.Constants.categoryTitle;
/*      */                                                       }
/* 3331 */                                                       for (int c = 0; c < categoryLink.length; c++)
/*      */                                                       {
/* 3333 */                                                         ArrayList unSupportedTypes = com.adventnet.appmanager.util.Constants.getUnSupportedAsList();
/* 3334 */                                                         if ((!categoryLink[c].equals("SYS")) && (!categoryLink[c].equals("NWD")) && (!categoryLink[c].equals("SAN")) && (!categoryLink[c].equals("EMO")) && (!categoryLink[c].equals("SCR")) && ((!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")) || (!unSupportedTypes.contains(categoryLink[c]))))
/*      */                                                         {
/*      */ 
/*      */ 
/* 3338 */                                                           StringBuffer queryBuf = new StringBuffer();
/* 3339 */                                                           queryBuf.append("SELECT RESOURCETYPE,DISPLAYNAME,SUBGROUP,IMAGEPATH FROM AM_ManagedResourceType where RESOURCEGROUP='");
/* 3340 */                                                           queryBuf.append(categoryLink[c] + "'");
/* 3341 */                                                           queryBuf.append(" ");
/* 3342 */                                                           queryBuf.append("and RESOURCETYPE in");
/* 3343 */                                                           queryBuf.append(" ");
/* 3344 */                                                           queryBuf.append(com.adventnet.appmanager.util.Constants.resourceTypes);
/* 3345 */                                                           if (categoryLink[c].equals("APP"))
/*      */                                                           {
/* 3347 */                                                             queryBuf.append(" ");
/* 3348 */                                                             queryBuf.append("and DISPLAYNAME NOT IN ('WebLogic Clusters')");
/* 3349 */                                                             queryBuf.append(" ");
/*      */                                                           }
/* 3351 */                                                           else if (categoryLink[c].equals("SER"))
/*      */                                                           {
/* 3353 */                                                             queryBuf.append(" ");
/* 3354 */                                                             queryBuf.append("and RESOURCETYPE<>'RMI'");
/* 3355 */                                                             queryBuf.append(" ");
/*      */                                                           }
/* 3357 */                                                           else if (categoryLink[c].equals("CAM"))
/*      */                                                           {
/* 3359 */                                                             queryBuf.append(" ");
/* 3360 */                                                             queryBuf.append("and RESOURCETYPE != 'directory'");
/* 3361 */                                                             queryBuf.append(" ");
/*      */                                                           }
/* 3363 */                                                           queryBuf.append(" ");
/* 3364 */                                                           queryBuf.append("ORDER BY UPPER(DISPLAYNAME)");
/* 3365 */                                                           ArrayList rows = mo1.getRows(queryBuf.toString());
/* 3366 */                                                           if ((rows != null) && (rows.size() != 0))
/*      */                                                           {
/*      */ 
/*      */ 
/*      */ 
/* 3371 */                                                             out.write("\n</optgroup>\t\t\t\t<optgroup label=\"");
/* 3372 */                                                             out.print(FormatUtil.getString(categoryTitle[c]));
/* 3373 */                                                             out.write(34);
/* 3374 */                                                             out.write(62);
/* 3375 */                                                             out.write(10);
/*      */                                                             
/*      */ 
/* 3378 */                                                             for (int i = 0; i < rows.size(); i++)
/*      */                                                             {
/* 3380 */                                                               ArrayList row = (ArrayList)rows.get(i);
/* 3381 */                                                               String res = (String)row.get(0);
/* 3382 */                                                               if (res.equals("file"))
/*      */                                                               {
/* 3384 */                                                                 res = "File / Directory Monitor";
/*      */                                                               }
/* 3386 */                                                               String dname = (String)row.get(1);
/* 3387 */                                                               String values = props.getProperty(res);
/* 3388 */                                                               if ((!EnterpriseUtil.isCloudEdition()) || (!unSupportedTypes.contains(values)))
/*      */                                                               {
/*      */ 
/* 3391 */                                                                 if (!confMonConfig.isDependentMonitor((String)row.get(0)))
/*      */                                                                 {
/*      */ 
/* 3394 */                                                                   out.write("\n\t\t\t\t \t");
/*      */                                                                   
/* 3396 */                                                                   OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3397 */                                                                   _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 3398 */                                                                   _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                                   
/* 3400 */                                                                   _jspx_th_html_005foption_005f11.setValue(values);
/* 3401 */                                                                   int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 3402 */                                                                   if (_jspx_eval_html_005foption_005f11 != 0) {
/* 3403 */                                                                     if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3404 */                                                                       out = _jspx_page_context.pushBody();
/* 3405 */                                                                       _jspx_th_html_005foption_005f11.setBodyContent((BodyContent)out);
/* 3406 */                                                                       _jspx_th_html_005foption_005f11.doInitBody();
/*      */                                                                     }
/*      */                                                                     for (;;) {
/* 3409 */                                                                       out.write(32);
/* 3410 */                                                                       out.print(FormatUtil.getString(dname));
/* 3411 */                                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f11.doAfterBody();
/* 3412 */                                                                       if (evalDoAfterBody != 2)
/*      */                                                                         break;
/*      */                                                                     }
/* 3415 */                                                                     if (_jspx_eval_html_005foption_005f11 != 1) {
/* 3416 */                                                                       out = _jspx_page_context.popBody();
/*      */                                                                     }
/*      */                                                                   }
/* 3419 */                                                                   if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 3420 */                                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                                                   }
/*      */                                                                   
/* 3423 */                                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f11);
/* 3424 */                                                                   out.write("\n\t\t\t\t");
/*      */                                                                 }
/*      */                                                               }
/*      */                                                             }
/*      */                                                             
/* 3429 */                                                             if (categoryLink[c].equals("VIR"))
/*      */                                                             {
/*      */ 
/* 3432 */                                                               out.write("\n\t\t\t\t\t");
/*      */                                                               
/* 3434 */                                                               OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3435 */                                                               _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 3436 */                                                               _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                               
/* 3438 */                                                               _jspx_th_html_005foption_005f12.setValue("VCenter");
/* 3439 */                                                               int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 3440 */                                                               if (_jspx_eval_html_005foption_005f12 != 0) {
/* 3441 */                                                                 if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3442 */                                                                   out = _jspx_page_context.pushBody();
/* 3443 */                                                                   _jspx_th_html_005foption_005f12.setBodyContent((BodyContent)out);
/* 3444 */                                                                   _jspx_th_html_005foption_005f12.doInitBody();
/*      */                                                                 }
/*      */                                                                 for (;;) {
/* 3447 */                                                                   out.write(32);
/* 3448 */                                                                   out.print(FormatUtil.getString("am.webclient.addmonitor.vcenter.name"));
/* 3449 */                                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f12.doAfterBody();
/* 3450 */                                                                   if (evalDoAfterBody != 2)
/*      */                                                                     break;
/*      */                                                                 }
/* 3453 */                                                                 if (_jspx_eval_html_005foption_005f12 != 1) {
/* 3454 */                                                                   out = _jspx_page_context.popBody();
/*      */                                                                 }
/*      */                                                               }
/* 3457 */                                                               if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 3458 */                                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                                               }
/*      */                                                               
/* 3461 */                                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f12);
/* 3462 */                                                               out.write("\n\t\t\t\t");
/*      */                                                             }
/*      */                                                           }
/*      */                                                         } }
/* 3466 */                                                       String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/* 3467 */                                                       if (!usertype.equals("F"))
/*      */                                                       {
/* 3469 */                                                         if (((!EnterpriseUtil.isIt360MSPEdition()) || (!DBUtil.isSharedProbe())) && (!com.adventnet.appmanager.util.Constants.getCategorytype().equals("CLOUD")))
/*      */                                                         {
/* 3471 */                                                           out.write("\n    </optgroup> <optgroup label=\"");
/* 3472 */                                                           out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3473 */                                                           out.write("\">\n     ");
/*      */                                                           
/* 3475 */                                                           OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3476 */                                                           _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 3477 */                                                           _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                           
/* 3479 */                                                           _jspx_th_html_005foption_005f13.setValue("All:1008");
/* 3480 */                                                           int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 3481 */                                                           if (_jspx_eval_html_005foption_005f13 != 0) {
/* 3482 */                                                             if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3483 */                                                               out = _jspx_page_context.pushBody();
/* 3484 */                                                               _jspx_th_html_005foption_005f13.setBodyContent((BodyContent)out);
/* 3485 */                                                               _jspx_th_html_005foption_005f13.doInitBody();
/*      */                                                             }
/*      */                                                             for (;;) {
/* 3488 */                                                               out.print(FormatUtil.getString("am.webclient.mouseover.allservices.text"));
/* 3489 */                                                               out.write(32);
/* 3490 */                                                               int evalDoAfterBody = _jspx_th_html_005foption_005f13.doAfterBody();
/* 3491 */                                                               if (evalDoAfterBody != 2)
/*      */                                                                 break;
/*      */                                                             }
/* 3494 */                                                             if (_jspx_eval_html_005foption_005f13 != 1) {
/* 3495 */                                                               out = _jspx_page_context.popBody();
/*      */                                                             }
/*      */                                                           }
/* 3498 */                                                           if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 3499 */                                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                                           }
/*      */                                                           
/* 3502 */                                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f13);
/* 3503 */                                                           out.write("\n\n     ");
/*      */                                                         }
/*      */                                                         
/*      */                                                       }
/*      */                                                       
/*      */                                                     }
/* 3509 */                                                     else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("LAMP"))
/*      */                                                     {
/*      */ 
/* 3512 */                                                       out.write("\n\t\t\t </optgroup>  <optgroup label=\"");
/* 3513 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 3514 */                                                       out.write("\">\n\t\t\t   ");
/*      */                                                       
/* 3516 */                                                       OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3517 */                                                       _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 3518 */                                                       _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3520 */                                                       _jspx_th_html_005foption_005f14.setValue("SYSTEM:9999");
/* 3521 */                                                       int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 3522 */                                                       if (_jspx_eval_html_005foption_005f14 != 0) {
/* 3523 */                                                         if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3524 */                                                           out = _jspx_page_context.pushBody();
/* 3525 */                                                           _jspx_th_html_005foption_005f14.setBodyContent((BodyContent)out);
/* 3526 */                                                           _jspx_th_html_005foption_005f14.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3529 */                                                           out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 3530 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f14.doAfterBody();
/* 3531 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3534 */                                                         if (_jspx_eval_html_005foption_005f14 != 1) {
/* 3535 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3538 */                                                       if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 3539 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                                       }
/*      */                                                       
/* 3542 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f14);
/* 3543 */                                                       out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3544 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 3545 */                                                       out.write("\">\n\t\t\t   ");
/*      */                                                       
/* 3547 */                                                       OptionTag _jspx_th_html_005foption_005f15 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3548 */                                                       _jspx_th_html_005foption_005f15.setPageContext(_jspx_page_context);
/* 3549 */                                                       _jspx_th_html_005foption_005f15.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3551 */                                                       _jspx_th_html_005foption_005f15.setValue("MYSQLDB:3306");
/* 3552 */                                                       int _jspx_eval_html_005foption_005f15 = _jspx_th_html_005foption_005f15.doStartTag();
/* 3553 */                                                       if (_jspx_eval_html_005foption_005f15 != 0) {
/* 3554 */                                                         if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3555 */                                                           out = _jspx_page_context.pushBody();
/* 3556 */                                                           _jspx_th_html_005foption_005f15.setBodyContent((BodyContent)out);
/* 3557 */                                                           _jspx_th_html_005foption_005f15.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3560 */                                                           out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 3561 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f15.doAfterBody();
/* 3562 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3565 */                                                         if (_jspx_eval_html_005foption_005f15 != 1) {
/* 3566 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3569 */                                                       if (_jspx_th_html_005foption_005f15.doEndTag() == 5) {
/* 3570 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15); return;
/*      */                                                       }
/*      */                                                       
/* 3573 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f15);
/* 3574 */                                                       out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3575 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 3576 */                                                       out.write("\">\n\t\t\t   ");
/*      */                                                       
/* 3578 */                                                       OptionTag _jspx_th_html_005foption_005f16 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3579 */                                                       _jspx_th_html_005foption_005f16.setPageContext(_jspx_page_context);
/* 3580 */                                                       _jspx_th_html_005foption_005f16.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3582 */                                                       _jspx_th_html_005foption_005f16.setValue("JMX1.2-MX4J-RMI:1099");
/* 3583 */                                                       int _jspx_eval_html_005foption_005f16 = _jspx_th_html_005foption_005f16.doStartTag();
/* 3584 */                                                       if (_jspx_eval_html_005foption_005f16 != 0) {
/* 3585 */                                                         if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3586 */                                                           out = _jspx_page_context.pushBody();
/* 3587 */                                                           _jspx_th_html_005foption_005f16.setBodyContent((BodyContent)out);
/* 3588 */                                                           _jspx_th_html_005foption_005f16.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3591 */                                                           out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 3592 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f16.doAfterBody();
/* 3593 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3596 */                                                         if (_jspx_eval_html_005foption_005f16 != 1) {
/* 3597 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3600 */                                                       if (_jspx_th_html_005foption_005f16.doEndTag() == 5) {
/* 3601 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16); return;
/*      */                                                       }
/*      */                                                       
/* 3604 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f16);
/* 3605 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3607 */                                                       OptionTag _jspx_th_html_005foption_005f17 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3608 */                                                       _jspx_th_html_005foption_005f17.setPageContext(_jspx_page_context);
/* 3609 */                                                       _jspx_th_html_005foption_005f17.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3611 */                                                       _jspx_th_html_005foption_005f17.setValue("SERVICE:9090");
/* 3612 */                                                       int _jspx_eval_html_005foption_005f17 = _jspx_th_html_005foption_005f17.doStartTag();
/* 3613 */                                                       if (_jspx_eval_html_005foption_005f17 != 0) {
/* 3614 */                                                         if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3615 */                                                           out = _jspx_page_context.pushBody();
/* 3616 */                                                           _jspx_th_html_005foption_005f17.setBodyContent((BodyContent)out);
/* 3617 */                                                           _jspx_th_html_005foption_005f17.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3620 */                                                           out.write(32);
/* 3621 */                                                           out.print(FormatUtil.getString("Service Monitoring"));
/* 3622 */                                                           out.write(32);
/* 3623 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f17.doAfterBody();
/* 3624 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3627 */                                                         if (_jspx_eval_html_005foption_005f17 != 1) {
/* 3628 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3631 */                                                       if (_jspx_th_html_005foption_005f17.doEndTag() == 5) {
/* 3632 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17); return;
/*      */                                                       }
/*      */                                                       
/* 3635 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f17);
/* 3636 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3638 */                                                       OptionTag _jspx_th_html_005foption_005f18 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3639 */                                                       _jspx_th_html_005foption_005f18.setPageContext(_jspx_page_context);
/* 3640 */                                                       _jspx_th_html_005foption_005f18.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3642 */                                                       _jspx_th_html_005foption_005f18.setValue("RMI:1099");
/* 3643 */                                                       int _jspx_eval_html_005foption_005f18 = _jspx_th_html_005foption_005f18.doStartTag();
/* 3644 */                                                       if (_jspx_eval_html_005foption_005f18 != 0) {
/* 3645 */                                                         if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3646 */                                                           out = _jspx_page_context.pushBody();
/* 3647 */                                                           _jspx_th_html_005foption_005f18.setBodyContent((BodyContent)out);
/* 3648 */                                                           _jspx_th_html_005foption_005f18.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3651 */                                                           out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 3652 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f18.doAfterBody();
/* 3653 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3656 */                                                         if (_jspx_eval_html_005foption_005f18 != 1) {
/* 3657 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3660 */                                                       if (_jspx_th_html_005foption_005f18.doEndTag() == 5) {
/* 3661 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18); return;
/*      */                                                       }
/*      */                                                       
/* 3664 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f18);
/* 3665 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3667 */                                                       OptionTag _jspx_th_html_005foption_005f19 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3668 */                                                       _jspx_th_html_005foption_005f19.setPageContext(_jspx_page_context);
/* 3669 */                                                       _jspx_th_html_005foption_005f19.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3671 */                                                       _jspx_th_html_005foption_005f19.setValue("SNMP:161");
/* 3672 */                                                       int _jspx_eval_html_005foption_005f19 = _jspx_th_html_005foption_005f19.doStartTag();
/* 3673 */                                                       if (_jspx_eval_html_005foption_005f19 != 0) {
/* 3674 */                                                         if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3675 */                                                           out = _jspx_page_context.pushBody();
/* 3676 */                                                           _jspx_th_html_005foption_005f19.setBodyContent((BodyContent)out);
/* 3677 */                                                           _jspx_th_html_005foption_005f19.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3680 */                                                           out.print(FormatUtil.getString("SNMP"));
/* 3681 */                                                           out.write(" (V1 or V2c)");
/* 3682 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f19.doAfterBody();
/* 3683 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3686 */                                                         if (_jspx_eval_html_005foption_005f19 != 1) {
/* 3687 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3690 */                                                       if (_jspx_th_html_005foption_005f19.doEndTag() == 5) {
/* 3691 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19); return;
/*      */                                                       }
/*      */                                                       
/* 3694 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f19);
/* 3695 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3697 */                                                       OptionTag _jspx_th_html_005foption_005f20 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3698 */                                                       _jspx_th_html_005foption_005f20.setPageContext(_jspx_page_context);
/* 3699 */                                                       _jspx_th_html_005foption_005f20.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3701 */                                                       _jspx_th_html_005foption_005f20.setValue("TELNET:23");
/* 3702 */                                                       int _jspx_eval_html_005foption_005f20 = _jspx_th_html_005foption_005f20.doStartTag();
/* 3703 */                                                       if (_jspx_eval_html_005foption_005f20 != 0) {
/* 3704 */                                                         if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3705 */                                                           out = _jspx_page_context.pushBody();
/* 3706 */                                                           _jspx_th_html_005foption_005f20.setBodyContent((BodyContent)out);
/* 3707 */                                                           _jspx_th_html_005foption_005f20.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3710 */                                                           out.print(FormatUtil.getString("Telnet"));
/* 3711 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f20.doAfterBody();
/* 3712 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3715 */                                                         if (_jspx_eval_html_005foption_005f20 != 1) {
/* 3716 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3719 */                                                       if (_jspx_th_html_005foption_005f20.doEndTag() == 5) {
/* 3720 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20); return;
/*      */                                                       }
/*      */                                                       
/* 3723 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f20);
/* 3724 */                                                       out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3725 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 3726 */                                                       out.write("\">\n\t\t\t   ");
/*      */                                                       
/* 3728 */                                                       OptionTag _jspx_th_html_005foption_005f21 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3729 */                                                       _jspx_th_html_005foption_005f21.setPageContext(_jspx_page_context);
/* 3730 */                                                       _jspx_th_html_005foption_005f21.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3732 */                                                       _jspx_th_html_005foption_005f21.setValue("APACHE:80");
/* 3733 */                                                       int _jspx_eval_html_005foption_005f21 = _jspx_th_html_005foption_005f21.doStartTag();
/* 3734 */                                                       if (_jspx_eval_html_005foption_005f21 != 0) {
/* 3735 */                                                         if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3736 */                                                           out = _jspx_page_context.pushBody();
/* 3737 */                                                           _jspx_th_html_005foption_005f21.setBodyContent((BodyContent)out);
/* 3738 */                                                           _jspx_th_html_005foption_005f21.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3741 */                                                           out.write(32);
/* 3742 */                                                           out.print(FormatUtil.getString("Apache Server"));
/* 3743 */                                                           out.write(32);
/* 3744 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f21.doAfterBody();
/* 3745 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3748 */                                                         if (_jspx_eval_html_005foption_005f21 != 1) {
/* 3749 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3752 */                                                       if (_jspx_th_html_005foption_005f21.doEndTag() == 5) {
/* 3753 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21); return;
/*      */                                                       }
/*      */                                                       
/* 3756 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f21);
/* 3757 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3759 */                                                       OptionTag _jspx_th_html_005foption_005f22 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3760 */                                                       _jspx_th_html_005foption_005f22.setPageContext(_jspx_page_context);
/* 3761 */                                                       _jspx_th_html_005foption_005f22.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3763 */                                                       _jspx_th_html_005foption_005f22.setValue("PHP:80");
/* 3764 */                                                       int _jspx_eval_html_005foption_005f22 = _jspx_th_html_005foption_005f22.doStartTag();
/* 3765 */                                                       if (_jspx_eval_html_005foption_005f22 != 0) {
/* 3766 */                                                         if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3767 */                                                           out = _jspx_page_context.pushBody();
/* 3768 */                                                           _jspx_th_html_005foption_005f22.setBodyContent((BodyContent)out);
/* 3769 */                                                           _jspx_th_html_005foption_005f22.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3772 */                                                           out.print(FormatUtil.getString("PHP"));
/* 3773 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f22.doAfterBody();
/* 3774 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3777 */                                                         if (_jspx_eval_html_005foption_005f22 != 1) {
/* 3778 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3781 */                                                       if (_jspx_th_html_005foption_005f22.doEndTag() == 5) {
/* 3782 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22); return;
/*      */                                                       }
/*      */                                                       
/* 3785 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f22);
/* 3786 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3788 */                                                       OptionTag _jspx_th_html_005foption_005f23 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3789 */                                                       _jspx_th_html_005foption_005f23.setPageContext(_jspx_page_context);
/* 3790 */                                                       _jspx_th_html_005foption_005f23.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3792 */                                                       _jspx_th_html_005foption_005f23.setValue("UrlMonitor");
/* 3793 */                                                       int _jspx_eval_html_005foption_005f23 = _jspx_th_html_005foption_005f23.doStartTag();
/* 3794 */                                                       if (_jspx_eval_html_005foption_005f23 != 0) {
/* 3795 */                                                         if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3796 */                                                           out = _jspx_page_context.pushBody();
/* 3797 */                                                           _jspx_th_html_005foption_005f23.setBodyContent((BodyContent)out);
/* 3798 */                                                           _jspx_th_html_005foption_005f23.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3801 */                                                           out.print(FormatUtil.getString("HTTP-URLs"));
/* 3802 */                                                           out.write(32);
/* 3803 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f23.doAfterBody();
/* 3804 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3807 */                                                         if (_jspx_eval_html_005foption_005f23 != 1) {
/* 3808 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3811 */                                                       if (_jspx_th_html_005foption_005f23.doEndTag() == 5) {
/* 3812 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23); return;
/*      */                                                       }
/*      */                                                       
/* 3815 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f23);
/* 3816 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3818 */                                                       OptionTag _jspx_th_html_005foption_005f24 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3819 */                                                       _jspx_th_html_005foption_005f24.setPageContext(_jspx_page_context);
/* 3820 */                                                       _jspx_th_html_005foption_005f24.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3822 */                                                       _jspx_th_html_005foption_005f24.setValue("UrlSeq");
/* 3823 */                                                       int _jspx_eval_html_005foption_005f24 = _jspx_th_html_005foption_005f24.doStartTag();
/* 3824 */                                                       if (_jspx_eval_html_005foption_005f24 != 0) {
/* 3825 */                                                         if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3826 */                                                           out = _jspx_page_context.pushBody();
/* 3827 */                                                           _jspx_th_html_005foption_005f24.setBodyContent((BodyContent)out);
/* 3828 */                                                           _jspx_th_html_005foption_005f24.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3831 */                                                           out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 3832 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f24.doAfterBody();
/* 3833 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3836 */                                                         if (_jspx_eval_html_005foption_005f24 != 1) {
/* 3837 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3840 */                                                       if (_jspx_th_html_005foption_005f24.doEndTag() == 5) {
/* 3841 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24); return;
/*      */                                                       }
/*      */                                                       
/* 3844 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f24);
/* 3845 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3847 */                                                       OptionTag _jspx_th_html_005foption_005f25 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3848 */                                                       _jspx_th_html_005foption_005f25.setPageContext(_jspx_page_context);
/* 3849 */                                                       _jspx_th_html_005foption_005f25.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3851 */                                                       _jspx_th_html_005foption_005f25.setValue("WEB:80");
/* 3852 */                                                       int _jspx_eval_html_005foption_005f25 = _jspx_th_html_005foption_005f25.doStartTag();
/* 3853 */                                                       if (_jspx_eval_html_005foption_005f25 != 0) {
/* 3854 */                                                         if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3855 */                                                           out = _jspx_page_context.pushBody();
/* 3856 */                                                           _jspx_th_html_005foption_005f25.setBodyContent((BodyContent)out);
/* 3857 */                                                           _jspx_th_html_005foption_005f25.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3860 */                                                           out.write(32);
/* 3861 */                                                           out.print(FormatUtil.getString("Web Server"));
/* 3862 */                                                           out.write(32);
/* 3863 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f25.doAfterBody();
/* 3864 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3867 */                                                         if (_jspx_eval_html_005foption_005f25 != 1) {
/* 3868 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3871 */                                                       if (_jspx_th_html_005foption_005f25.doEndTag() == 5) {
/* 3872 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25); return;
/*      */                                                       }
/*      */                                                       
/* 3875 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f25);
/* 3876 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3878 */                                                       OptionTag _jspx_th_html_005foption_005f26 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3879 */                                                       _jspx_th_html_005foption_005f26.setPageContext(_jspx_page_context);
/* 3880 */                                                       _jspx_th_html_005foption_005f26.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3882 */                                                       _jspx_th_html_005foption_005f26.setValue("Web Service");
/* 3883 */                                                       int _jspx_eval_html_005foption_005f26 = _jspx_th_html_005foption_005f26.doStartTag();
/* 3884 */                                                       if (_jspx_eval_html_005foption_005f26 != 0) {
/* 3885 */                                                         if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3886 */                                                           out = _jspx_page_context.pushBody();
/* 3887 */                                                           _jspx_th_html_005foption_005f26.setBodyContent((BodyContent)out);
/* 3888 */                                                           _jspx_th_html_005foption_005f26.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3891 */                                                           out.write(32);
/* 3892 */                                                           out.print(FormatUtil.getString("Web Service"));
/* 3893 */                                                           out.write(32);
/* 3894 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f26.doAfterBody();
/* 3895 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3898 */                                                         if (_jspx_eval_html_005foption_005f26 != 1) {
/* 3899 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3902 */                                                       if (_jspx_th_html_005foption_005f26.doEndTag() == 5) {
/* 3903 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26); return;
/*      */                                                       }
/*      */                                                       
/* 3906 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f26);
/* 3907 */                                                       out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3908 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.mailserver"));
/* 3909 */                                                       out.write("\">\n\t\t\t   ");
/*      */                                                       
/* 3911 */                                                       OptionTag _jspx_th_html_005foption_005f27 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3912 */                                                       _jspx_th_html_005foption_005f27.setPageContext(_jspx_page_context);
/* 3913 */                                                       _jspx_th_html_005foption_005f27.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3915 */                                                       _jspx_th_html_005foption_005f27.setValue("MAIL:25");
/* 3916 */                                                       int _jspx_eval_html_005foption_005f27 = _jspx_th_html_005foption_005f27.doStartTag();
/* 3917 */                                                       if (_jspx_eval_html_005foption_005f27 != 0) {
/* 3918 */                                                         if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3919 */                                                           out = _jspx_page_context.pushBody();
/* 3920 */                                                           _jspx_th_html_005foption_005f27.setBodyContent((BodyContent)out);
/* 3921 */                                                           _jspx_th_html_005foption_005f27.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3924 */                                                           out.print(FormatUtil.getString("Mail Server"));
/* 3925 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f27.doAfterBody();
/* 3926 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3929 */                                                         if (_jspx_eval_html_005foption_005f27 != 1) {
/* 3930 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3933 */                                                       if (_jspx_th_html_005foption_005f27.doEndTag() == 5) {
/* 3934 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27); return;
/*      */                                                       }
/*      */                                                       
/* 3937 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f27);
/* 3938 */                                                       out.write("</optgroup>\n\t\t\t   <optgroup label=\"");
/* 3939 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 3940 */                                                       out.write("\">\n\t\t\t   ");
/*      */                                                       
/* 3942 */                                                       OptionTag _jspx_th_html_005foption_005f28 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3943 */                                                       _jspx_th_html_005foption_005f28.setPageContext(_jspx_page_context);
/* 3944 */                                                       _jspx_th_html_005foption_005f28.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3946 */                                                       _jspx_th_html_005foption_005f28.setValue("Custom-Application");
/* 3947 */                                                       int _jspx_eval_html_005foption_005f28 = _jspx_th_html_005foption_005f28.doStartTag();
/* 3948 */                                                       if (_jspx_eval_html_005foption_005f28 != 0) {
/* 3949 */                                                         if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3950 */                                                           out = _jspx_page_context.pushBody();
/* 3951 */                                                           _jspx_th_html_005foption_005f28.setBodyContent((BodyContent)out);
/* 3952 */                                                           _jspx_th_html_005foption_005f28.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3955 */                                                           out.write(32);
/* 3956 */                                                           out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 3957 */                                                           out.write(32);
/* 3958 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f28.doAfterBody();
/* 3959 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3962 */                                                         if (_jspx_eval_html_005foption_005f28 != 1) {
/* 3963 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3966 */                                                       if (_jspx_th_html_005foption_005f28.doEndTag() == 5) {
/* 3967 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28); return;
/*      */                                                       }
/*      */                                                       
/* 3970 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f28);
/* 3971 */                                                       out.write("\n\t\t\t   ");
/*      */                                                       
/* 3973 */                                                       OptionTag _jspx_th_html_005foption_005f29 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 3974 */                                                       _jspx_th_html_005foption_005f29.setPageContext(_jspx_page_context);
/* 3975 */                                                       _jspx_th_html_005foption_005f29.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 3977 */                                                       _jspx_th_html_005foption_005f29.setValue("Script Monitor");
/* 3978 */                                                       int _jspx_eval_html_005foption_005f29 = _jspx_th_html_005foption_005f29.doStartTag();
/* 3979 */                                                       if (_jspx_eval_html_005foption_005f29 != 0) {
/* 3980 */                                                         if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3981 */                                                           out = _jspx_page_context.pushBody();
/* 3982 */                                                           _jspx_th_html_005foption_005f29.setBodyContent((BodyContent)out);
/* 3983 */                                                           _jspx_th_html_005foption_005f29.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 3986 */                                                           out.print(FormatUtil.getString("Script Monitor"));
/* 3987 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f29.doAfterBody();
/* 3988 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 3991 */                                                         if (_jspx_eval_html_005foption_005f29 != 1) {
/* 3992 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 3995 */                                                       if (_jspx_th_html_005foption_005f29.doEndTag() == 5) {
/* 3996 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29); return;
/*      */                                                       }
/*      */                                                       
/* 3999 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f29);
/* 4000 */                                                       out.write("\n\n    ");
/*      */ 
/*      */                                                     }
/* 4003 */                                                     else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("J2EE"))
/*      */                                                     {
/*      */ 
/* 4006 */                                                       out.write("\n        ");
/*      */                                                       
/* 4008 */                                                       OptionTag _jspx_th_html_005foption_005f30 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4009 */                                                       _jspx_th_html_005foption_005f30.setPageContext(_jspx_page_context);
/* 4010 */                                                       _jspx_th_html_005foption_005f30.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4012 */                                                       _jspx_th_html_005foption_005f30.setValue("SYSTEM:9999");
/* 4013 */                                                       int _jspx_eval_html_005foption_005f30 = _jspx_th_html_005foption_005f30.doStartTag();
/* 4014 */                                                       if (_jspx_eval_html_005foption_005f30 != 0) {
/* 4015 */                                                         if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4016 */                                                           out = _jspx_page_context.pushBody();
/* 4017 */                                                           _jspx_th_html_005foption_005f30.setBodyContent((BodyContent)out);
/* 4018 */                                                           _jspx_th_html_005foption_005f30.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4021 */                                                           out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4022 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f30.doAfterBody();
/* 4023 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4026 */                                                         if (_jspx_eval_html_005foption_005f30 != 1) {
/* 4027 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4030 */                                                       if (_jspx_th_html_005foption_005f30.doEndTag() == 5) {
/* 4031 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30); return;
/*      */                                                       }
/*      */                                                       
/* 4034 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f30);
/* 4035 */                                                       out.write("\n       ");
/*      */                                                       
/* 4037 */                                                       OptionTag _jspx_th_html_005foption_005f31 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4038 */                                                       _jspx_th_html_005foption_005f31.setPageContext(_jspx_page_context);
/* 4039 */                                                       _jspx_th_html_005foption_005f31.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4041 */                                                       _jspx_th_html_005foption_005f31.setValue("JBoss:8080");
/* 4042 */                                                       int _jspx_eval_html_005foption_005f31 = _jspx_th_html_005foption_005f31.doStartTag();
/* 4043 */                                                       if (_jspx_eval_html_005foption_005f31 != 0) {
/* 4044 */                                                         if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4045 */                                                           out = _jspx_page_context.pushBody();
/* 4046 */                                                           _jspx_th_html_005foption_005f31.setBodyContent((BodyContent)out);
/* 4047 */                                                           _jspx_th_html_005foption_005f31.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4050 */                                                           out.write(32);
/* 4051 */                                                           out.print(FormatUtil.getString("JBoss Server"));
/* 4052 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f31.doAfterBody();
/* 4053 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4056 */                                                         if (_jspx_eval_html_005foption_005f31 != 1) {
/* 4057 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4060 */                                                       if (_jspx_th_html_005foption_005f31.doEndTag() == 5) {
/* 4061 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31); return;
/*      */                                                       }
/*      */                                                       
/* 4064 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f31);
/* 4065 */                                                       out.write("\n      ");
/*      */                                                       
/* 4067 */                                                       OptionTag _jspx_th_html_005foption_005f32 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4068 */                                                       _jspx_th_html_005foption_005f32.setPageContext(_jspx_page_context);
/* 4069 */                                                       _jspx_th_html_005foption_005f32.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4071 */                                                       _jspx_th_html_005foption_005f32.setValue("Tomcat:8080");
/* 4072 */                                                       int _jspx_eval_html_005foption_005f32 = _jspx_th_html_005foption_005f32.doStartTag();
/* 4073 */                                                       if (_jspx_eval_html_005foption_005f32 != 0) {
/* 4074 */                                                         if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4075 */                                                           out = _jspx_page_context.pushBody();
/* 4076 */                                                           _jspx_th_html_005foption_005f32.setBodyContent((BodyContent)out);
/* 4077 */                                                           _jspx_th_html_005foption_005f32.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4080 */                                                           out.print(FormatUtil.getString("Tomcat Server"));
/* 4081 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f32.doAfterBody();
/* 4082 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4085 */                                                         if (_jspx_eval_html_005foption_005f32 != 1) {
/* 4086 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4089 */                                                       if (_jspx_th_html_005foption_005f32.doEndTag() == 5) {
/* 4090 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32); return;
/*      */                                                       }
/*      */                                                       
/* 4093 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f32);
/* 4094 */                                                       out.write("\n       ");
/*      */                                                       
/* 4096 */                                                       OptionTag _jspx_th_html_005foption_005f33 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4097 */                                                       _jspx_th_html_005foption_005f33.setPageContext(_jspx_page_context);
/* 4098 */                                                       _jspx_th_html_005foption_005f33.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4100 */                                                       _jspx_th_html_005foption_005f33.setValue("WEBLOGIC:7001");
/* 4101 */                                                       int _jspx_eval_html_005foption_005f33 = _jspx_th_html_005foption_005f33.doStartTag();
/* 4102 */                                                       if (_jspx_eval_html_005foption_005f33 != 0) {
/* 4103 */                                                         if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4104 */                                                           out = _jspx_page_context.pushBody();
/* 4105 */                                                           _jspx_th_html_005foption_005f33.setBodyContent((BodyContent)out);
/* 4106 */                                                           _jspx_th_html_005foption_005f33.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4109 */                                                           out.write(32);
/* 4110 */                                                           out.print(FormatUtil.getString("WebLogic Server"));
/* 4111 */                                                           out.write(32);
/* 4112 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f33.doAfterBody();
/* 4113 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4116 */                                                         if (_jspx_eval_html_005foption_005f33 != 1) {
/* 4117 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4120 */                                                       if (_jspx_th_html_005foption_005f33.doEndTag() == 5) {
/* 4121 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33); return;
/*      */                                                       }
/*      */                                                       
/* 4124 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f33);
/* 4125 */                                                       out.write("\n      ");
/*      */                                                       
/* 4127 */                                                       OptionTag _jspx_th_html_005foption_005f34 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4128 */                                                       _jspx_th_html_005foption_005f34.setPageContext(_jspx_page_context);
/* 4129 */                                                       _jspx_th_html_005foption_005f34.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4131 */                                                       _jspx_th_html_005foption_005f34.setValue("WEBSPHERE:9080");
/* 4132 */                                                       int _jspx_eval_html_005foption_005f34 = _jspx_th_html_005foption_005f34.doStartTag();
/* 4133 */                                                       if (_jspx_eval_html_005foption_005f34 != 0) {
/* 4134 */                                                         if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4135 */                                                           out = _jspx_page_context.pushBody();
/* 4136 */                                                           _jspx_th_html_005foption_005f34.setBodyContent((BodyContent)out);
/* 4137 */                                                           _jspx_th_html_005foption_005f34.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4140 */                                                           out.write(32);
/* 4141 */                                                           out.print(FormatUtil.getString("WebSphere Server"));
/* 4142 */                                                           out.write(32);
/* 4143 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f34.doAfterBody();
/* 4144 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4147 */                                                         if (_jspx_eval_html_005foption_005f34 != 1) {
/* 4148 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4151 */                                                       if (_jspx_th_html_005foption_005f34.doEndTag() == 5) {
/* 4152 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34); return;
/*      */                                                       }
/*      */                                                       
/* 4155 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f34);
/* 4156 */                                                       out.write("\n      ");
/*      */                                                       
/* 4158 */                                                       OptionTag _jspx_th_html_005foption_005f35 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4159 */                                                       _jspx_th_html_005foption_005f35.setPageContext(_jspx_page_context);
/* 4160 */                                                       _jspx_th_html_005foption_005f35.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4162 */                                                       _jspx_th_html_005foption_005f35.setValue("WTA:55555");
/* 4163 */                                                       int _jspx_eval_html_005foption_005f35 = _jspx_th_html_005foption_005f35.doStartTag();
/* 4164 */                                                       if (_jspx_eval_html_005foption_005f35 != 0) {
/* 4165 */                                                         if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4166 */                                                           out = _jspx_page_context.pushBody();
/* 4167 */                                                           _jspx_th_html_005foption_005f35.setBodyContent((BodyContent)out);
/* 4168 */                                                           _jspx_th_html_005foption_005f35.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4171 */                                                           out.print(FormatUtil.getString("Web Transactions"));
/* 4172 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f35.doAfterBody();
/* 4173 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4176 */                                                         if (_jspx_eval_html_005foption_005f35 != 1) {
/* 4177 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4180 */                                                       if (_jspx_th_html_005foption_005f35.doEndTag() == 5) {
/* 4181 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35); return;
/*      */                                                       }
/*      */                                                       
/* 4184 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f35);
/* 4185 */                                                       out.write("\n      ");
/*      */                                                       
/* 4187 */                                                       OptionTag _jspx_th_html_005foption_005f36 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4188 */                                                       _jspx_th_html_005foption_005f36.setPageContext(_jspx_page_context);
/* 4189 */                                                       _jspx_th_html_005foption_005f36.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4191 */                                                       _jspx_th_html_005foption_005f36.setValue("MAIL:25");
/* 4192 */                                                       int _jspx_eval_html_005foption_005f36 = _jspx_th_html_005foption_005f36.doStartTag();
/* 4193 */                                                       if (_jspx_eval_html_005foption_005f36 != 0) {
/* 4194 */                                                         if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4195 */                                                           out = _jspx_page_context.pushBody();
/* 4196 */                                                           _jspx_th_html_005foption_005f36.setBodyContent((BodyContent)out);
/* 4197 */                                                           _jspx_th_html_005foption_005f36.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4200 */                                                           out.print(FormatUtil.getString("Mail Server"));
/* 4201 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f36.doAfterBody();
/* 4202 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4205 */                                                         if (_jspx_eval_html_005foption_005f36 != 1) {
/* 4206 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4209 */                                                       if (_jspx_th_html_005foption_005f36.doEndTag() == 5) {
/* 4210 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36); return;
/*      */                                                       }
/*      */                                                       
/* 4213 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f36);
/* 4214 */                                                       out.write("\n      ");
/*      */                                                       
/* 4216 */                                                       OptionTag _jspx_th_html_005foption_005f37 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4217 */                                                       _jspx_th_html_005foption_005f37.setPageContext(_jspx_page_context);
/* 4218 */                                                       _jspx_th_html_005foption_005f37.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4220 */                                                       _jspx_th_html_005foption_005f37.setValue("JMX1.2-MX4J-RMI:1099");
/* 4221 */                                                       int _jspx_eval_html_005foption_005f37 = _jspx_th_html_005foption_005f37.doStartTag();
/* 4222 */                                                       if (_jspx_eval_html_005foption_005f37 != 0) {
/* 4223 */                                                         if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4224 */                                                           out = _jspx_page_context.pushBody();
/* 4225 */                                                           _jspx_th_html_005foption_005f37.setBodyContent((BodyContent)out);
/* 4226 */                                                           _jspx_th_html_005foption_005f37.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4229 */                                                           out.print(FormatUtil.getString("JMX [MX4J / JDK1.5]"));
/* 4230 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f37.doAfterBody();
/* 4231 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4234 */                                                         if (_jspx_eval_html_005foption_005f37 != 1) {
/* 4235 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4238 */                                                       if (_jspx_th_html_005foption_005f37.doEndTag() == 5) {
/* 4239 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37); return;
/*      */                                                       }
/*      */                                                       
/* 4242 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f37);
/* 4243 */                                                       out.write("\n      ");
/*      */                                                       
/* 4245 */                                                       OptionTag _jspx_th_html_005foption_005f38 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4246 */                                                       _jspx_th_html_005foption_005f38.setPageContext(_jspx_page_context);
/* 4247 */                                                       _jspx_th_html_005foption_005f38.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4249 */                                                       _jspx_th_html_005foption_005f38.setValue("SERVICE:9090");
/* 4250 */                                                       int _jspx_eval_html_005foption_005f38 = _jspx_th_html_005foption_005f38.doStartTag();
/* 4251 */                                                       if (_jspx_eval_html_005foption_005f38 != 0) {
/* 4252 */                                                         if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4253 */                                                           out = _jspx_page_context.pushBody();
/* 4254 */                                                           _jspx_th_html_005foption_005f38.setBodyContent((BodyContent)out);
/* 4255 */                                                           _jspx_th_html_005foption_005f38.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4258 */                                                           out.write(32);
/* 4259 */                                                           out.print(FormatUtil.getString("Service Monitoring"));
/* 4260 */                                                           out.write(32);
/* 4261 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f38.doAfterBody();
/* 4262 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4265 */                                                         if (_jspx_eval_html_005foption_005f38 != 1) {
/* 4266 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4269 */                                                       if (_jspx_th_html_005foption_005f38.doEndTag() == 5) {
/* 4270 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38); return;
/*      */                                                       }
/*      */                                                       
/* 4273 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f38);
/* 4274 */                                                       out.write("\n      ");
/*      */                                                       
/* 4276 */                                                       OptionTag _jspx_th_html_005foption_005f39 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4277 */                                                       _jspx_th_html_005foption_005f39.setPageContext(_jspx_page_context);
/* 4278 */                                                       _jspx_th_html_005foption_005f39.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4280 */                                                       _jspx_th_html_005foption_005f39.setValue("RMI:1099");
/* 4281 */                                                       int _jspx_eval_html_005foption_005f39 = _jspx_th_html_005foption_005f39.doStartTag();
/* 4282 */                                                       if (_jspx_eval_html_005foption_005f39 != 0) {
/* 4283 */                                                         if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4284 */                                                           out = _jspx_page_context.pushBody();
/* 4285 */                                                           _jspx_th_html_005foption_005f39.setBodyContent((BodyContent)out);
/* 4286 */                                                           _jspx_th_html_005foption_005f39.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4289 */                                                           out.print(FormatUtil.getString("AdventNet JMX Agent - RMI Adapter"));
/* 4290 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f39.doAfterBody();
/* 4291 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4294 */                                                         if (_jspx_eval_html_005foption_005f39 != 1) {
/* 4295 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4298 */                                                       if (_jspx_th_html_005foption_005f39.doEndTag() == 5) {
/* 4299 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39); return;
/*      */                                                       }
/*      */                                                       
/* 4302 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f39);
/* 4303 */                                                       out.write("\n      ");
/*      */                                                       
/* 4305 */                                                       OptionTag _jspx_th_html_005foption_005f40 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4306 */                                                       _jspx_th_html_005foption_005f40.setPageContext(_jspx_page_context);
/* 4307 */                                                       _jspx_th_html_005foption_005f40.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4309 */                                                       _jspx_th_html_005foption_005f40.setValue("SNMP:161");
/* 4310 */                                                       int _jspx_eval_html_005foption_005f40 = _jspx_th_html_005foption_005f40.doStartTag();
/* 4311 */                                                       if (_jspx_eval_html_005foption_005f40 != 0) {
/* 4312 */                                                         if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4313 */                                                           out = _jspx_page_context.pushBody();
/* 4314 */                                                           _jspx_th_html_005foption_005f40.setBodyContent((BodyContent)out);
/* 4315 */                                                           _jspx_th_html_005foption_005f40.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4318 */                                                           out.print(FormatUtil.getString("SNMP"));
/* 4319 */                                                           out.write(" (V1 or V2c)");
/* 4320 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f40.doAfterBody();
/* 4321 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4324 */                                                         if (_jspx_eval_html_005foption_005f40 != 1) {
/* 4325 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4328 */                                                       if (_jspx_th_html_005foption_005f40.doEndTag() == 5) {
/* 4329 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40); return;
/*      */                                                       }
/*      */                                                       
/* 4332 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f40);
/* 4333 */                                                       out.write("\n      ");
/*      */                                                       
/* 4335 */                                                       OptionTag _jspx_th_html_005foption_005f41 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4336 */                                                       _jspx_th_html_005foption_005f41.setPageContext(_jspx_page_context);
/* 4337 */                                                       _jspx_th_html_005foption_005f41.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4339 */                                                       _jspx_th_html_005foption_005f41.setValue("Custom-Application");
/* 4340 */                                                       int _jspx_eval_html_005foption_005f41 = _jspx_th_html_005foption_005f41.doStartTag();
/* 4341 */                                                       if (_jspx_eval_html_005foption_005f41 != 0) {
/* 4342 */                                                         if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4343 */                                                           out = _jspx_page_context.pushBody();
/* 4344 */                                                           _jspx_th_html_005foption_005f41.setBodyContent((BodyContent)out);
/* 4345 */                                                           _jspx_th_html_005foption_005f41.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4348 */                                                           out.write(32);
/* 4349 */                                                           out.print(FormatUtil.getString("JMX/SNMP Dashboard"));
/* 4350 */                                                           out.write(32);
/* 4351 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f41.doAfterBody();
/* 4352 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4355 */                                                         if (_jspx_eval_html_005foption_005f41 != 1) {
/* 4356 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4359 */                                                       if (_jspx_th_html_005foption_005f41.doEndTag() == 5) {
/* 4360 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41); return;
/*      */                                                       }
/*      */                                                       
/* 4363 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f41);
/* 4364 */                                                       out.write("\n      ");
/*      */                                                       
/* 4366 */                                                       OptionTag _jspx_th_html_005foption_005f42 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4367 */                                                       _jspx_th_html_005foption_005f42.setPageContext(_jspx_page_context);
/* 4368 */                                                       _jspx_th_html_005foption_005f42.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4370 */                                                       _jspx_th_html_005foption_005f42.setValue("Script Monitor");
/* 4371 */                                                       int _jspx_eval_html_005foption_005f42 = _jspx_th_html_005foption_005f42.doStartTag();
/* 4372 */                                                       if (_jspx_eval_html_005foption_005f42 != 0) {
/* 4373 */                                                         if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4374 */                                                           out = _jspx_page_context.pushBody();
/* 4375 */                                                           _jspx_th_html_005foption_005f42.setBodyContent((BodyContent)out);
/* 4376 */                                                           _jspx_th_html_005foption_005f42.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4379 */                                                           out.print(FormatUtil.getString("Script Monitor"));
/* 4380 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f42.doAfterBody();
/* 4381 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4384 */                                                         if (_jspx_eval_html_005foption_005f42 != 1) {
/* 4385 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4388 */                                                       if (_jspx_th_html_005foption_005f42.doEndTag() == 5) {
/* 4389 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42); return;
/*      */                                                       }
/*      */                                                       
/* 4392 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f42);
/* 4393 */                                                       out.write("\n       ");
/*      */ 
/*      */                                                     }
/* 4396 */                                                     else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("WINDOWS"))
/*      */                                                     {
/*      */ 
/* 4399 */                                                       out.write("\n        ");
/*      */                                                       
/* 4401 */                                                       OptionTag _jspx_th_html_005foption_005f43 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4402 */                                                       _jspx_th_html_005foption_005f43.setPageContext(_jspx_page_context);
/* 4403 */                                                       _jspx_th_html_005foption_005f43.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4405 */                                                       _jspx_th_html_005foption_005f43.setValue("SYSTEM:9999");
/* 4406 */                                                       int _jspx_eval_html_005foption_005f43 = _jspx_th_html_005foption_005f43.doStartTag();
/* 4407 */                                                       if (_jspx_eval_html_005foption_005f43 != 0) {
/* 4408 */                                                         if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4409 */                                                           out = _jspx_page_context.pushBody();
/* 4410 */                                                           _jspx_th_html_005foption_005f43.setBodyContent((BodyContent)out);
/* 4411 */                                                           _jspx_th_html_005foption_005f43.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4414 */                                                           out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4415 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f43.doAfterBody();
/* 4416 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4419 */                                                         if (_jspx_eval_html_005foption_005f43 != 1) {
/* 4420 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4423 */                                                       if (_jspx_th_html_005foption_005f43.doEndTag() == 5) {
/* 4424 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43); return;
/*      */                                                       }
/*      */                                                       
/* 4427 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f43);
/* 4428 */                                                       out.write("\n       ");
/*      */                                                       
/* 4430 */                                                       OptionTag _jspx_th_html_005foption_005f44 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4431 */                                                       _jspx_th_html_005foption_005f44.setPageContext(_jspx_page_context);
/* 4432 */                                                       _jspx_th_html_005foption_005f44.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4434 */                                                       _jspx_th_html_005foption_005f44.setValue(".Net:9080");
/* 4435 */                                                       int _jspx_eval_html_005foption_005f44 = _jspx_th_html_005foption_005f44.doStartTag();
/* 4436 */                                                       if (_jspx_eval_html_005foption_005f44 != 0) {
/* 4437 */                                                         if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4438 */                                                           out = _jspx_page_context.pushBody();
/* 4439 */                                                           _jspx_th_html_005foption_005f44.setBodyContent((BodyContent)out);
/* 4440 */                                                           _jspx_th_html_005foption_005f44.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4443 */                                                           out.print(FormatUtil.getString("Microsoft .NET"));
/* 4444 */                                                           out.write(32);
/* 4445 */                                                           out.write(32);
/* 4446 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f44.doAfterBody();
/* 4447 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4450 */                                                         if (_jspx_eval_html_005foption_005f44 != 1) {
/* 4451 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4454 */                                                       if (_jspx_th_html_005foption_005f44.doEndTag() == 5) {
/* 4455 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44); return;
/*      */                                                       }
/*      */                                                       
/* 4458 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f44);
/* 4459 */                                                       out.write("\n      ");
/*      */                                                       
/* 4461 */                                                       OptionTag _jspx_th_html_005foption_005f45 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4462 */                                                       _jspx_th_html_005foption_005f45.setPageContext(_jspx_page_context);
/* 4463 */                                                       _jspx_th_html_005foption_005f45.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4465 */                                                       _jspx_th_html_005foption_005f45.setValue("MSSQLDB:1433");
/* 4466 */                                                       int _jspx_eval_html_005foption_005f45 = _jspx_th_html_005foption_005f45.doStartTag();
/* 4467 */                                                       if (_jspx_eval_html_005foption_005f45 != 0) {
/* 4468 */                                                         if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4469 */                                                           out = _jspx_page_context.pushBody();
/* 4470 */                                                           _jspx_th_html_005foption_005f45.setBodyContent((BodyContent)out);
/* 4471 */                                                           _jspx_th_html_005foption_005f45.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4474 */                                                           out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4475 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f45.doAfterBody();
/* 4476 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4479 */                                                         if (_jspx_eval_html_005foption_005f45 != 1) {
/* 4480 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4483 */                                                       if (_jspx_th_html_005foption_005f45.doEndTag() == 5) {
/* 4484 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45); return;
/*      */                                                       }
/*      */                                                       
/* 4487 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f45);
/* 4488 */                                                       out.write("\n      ");
/*      */                                                       
/* 4490 */                                                       OptionTag _jspx_th_html_005foption_005f46 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4491 */                                                       _jspx_th_html_005foption_005f46.setPageContext(_jspx_page_context);
/* 4492 */                                                       _jspx_th_html_005foption_005f46.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4494 */                                                       _jspx_th_html_005foption_005f46.setValue("Exchange:25");
/* 4495 */                                                       int _jspx_eval_html_005foption_005f46 = _jspx_th_html_005foption_005f46.doStartTag();
/* 4496 */                                                       if (_jspx_eval_html_005foption_005f46 != 0) {
/* 4497 */                                                         if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4498 */                                                           out = _jspx_page_context.pushBody();
/* 4499 */                                                           _jspx_th_html_005foption_005f46.setBodyContent((BodyContent)out);
/* 4500 */                                                           _jspx_th_html_005foption_005f46.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4503 */                                                           out.print(FormatUtil.getString("Exchange Server"));
/* 4504 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f46.doAfterBody();
/* 4505 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4508 */                                                         if (_jspx_eval_html_005foption_005f46 != 1) {
/* 4509 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4512 */                                                       if (_jspx_th_html_005foption_005f46.doEndTag() == 5) {
/* 4513 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46); return;
/*      */                                                       }
/*      */                                                       
/* 4516 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f46);
/* 4517 */                                                       out.write("\n\t  ");
/*      */                                                       
/* 4519 */                                                       OptionTag _jspx_th_html_005foption_005f47 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4520 */                                                       _jspx_th_html_005foption_005f47.setPageContext(_jspx_page_context);
/* 4521 */                                                       _jspx_th_html_005foption_005f47.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4523 */                                                       _jspx_th_html_005foption_005f47.setValue("IIS:80");
/* 4524 */                                                       int _jspx_eval_html_005foption_005f47 = _jspx_th_html_005foption_005f47.doStartTag();
/* 4525 */                                                       if (_jspx_eval_html_005foption_005f47 != 0) {
/* 4526 */                                                         if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4527 */                                                           out = _jspx_page_context.pushBody();
/* 4528 */                                                           _jspx_th_html_005foption_005f47.setBodyContent((BodyContent)out);
/* 4529 */                                                           _jspx_th_html_005foption_005f47.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4532 */                                                           out.write(32);
/* 4533 */                                                           out.print(FormatUtil.getString("IIS Server"));
/* 4534 */                                                           out.write(32);
/* 4535 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f47.doAfterBody();
/* 4536 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4539 */                                                         if (_jspx_eval_html_005foption_005f47 != 1) {
/* 4540 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4543 */                                                       if (_jspx_th_html_005foption_005f47.doEndTag() == 5) {
/* 4544 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47); return;
/*      */                                                       }
/*      */                                                       
/* 4547 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f47);
/* 4548 */                                                       out.write("\n      ");
/*      */                                                       
/* 4550 */                                                       OptionTag _jspx_th_html_005foption_005f48 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4551 */                                                       _jspx_th_html_005foption_005f48.setPageContext(_jspx_page_context);
/* 4552 */                                                       _jspx_th_html_005foption_005f48.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4554 */                                                       _jspx_th_html_005foption_005f48.setValue("SERVICE:9090");
/* 4555 */                                                       int _jspx_eval_html_005foption_005f48 = _jspx_th_html_005foption_005f48.doStartTag();
/* 4556 */                                                       if (_jspx_eval_html_005foption_005f48 != 0) {
/* 4557 */                                                         if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4558 */                                                           out = _jspx_page_context.pushBody();
/* 4559 */                                                           _jspx_th_html_005foption_005f48.setBodyContent((BodyContent)out);
/* 4560 */                                                           _jspx_th_html_005foption_005f48.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4563 */                                                           out.write(32);
/* 4564 */                                                           out.print(FormatUtil.getString("Service Monitoring"));
/* 4565 */                                                           out.write(32);
/* 4566 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f48.doAfterBody();
/* 4567 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4570 */                                                         if (_jspx_eval_html_005foption_005f48 != 1) {
/* 4571 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4574 */                                                       if (_jspx_th_html_005foption_005f48.doEndTag() == 5) {
/* 4575 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48); return;
/*      */                                                       }
/*      */                                                       
/* 4578 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f48);
/* 4579 */                                                       out.write("\n\t  ");
/*      */                                                       
/* 4581 */                                                       OptionTag _jspx_th_html_005foption_005f49 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4582 */                                                       _jspx_th_html_005foption_005f49.setPageContext(_jspx_page_context);
/* 4583 */                                                       _jspx_th_html_005foption_005f49.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4585 */                                                       _jspx_th_html_005foption_005f49.setValue("SNMP:161");
/* 4586 */                                                       int _jspx_eval_html_005foption_005f49 = _jspx_th_html_005foption_005f49.doStartTag();
/* 4587 */                                                       if (_jspx_eval_html_005foption_005f49 != 0) {
/* 4588 */                                                         if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4589 */                                                           out = _jspx_page_context.pushBody();
/* 4590 */                                                           _jspx_th_html_005foption_005f49.setBodyContent((BodyContent)out);
/* 4591 */                                                           _jspx_th_html_005foption_005f49.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4594 */                                                           out.print(FormatUtil.getString("SNMP"));
/* 4595 */                                                           out.write(" (V1 or V2c)");
/* 4596 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f49.doAfterBody();
/* 4597 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4600 */                                                         if (_jspx_eval_html_005foption_005f49 != 1) {
/* 4601 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4604 */                                                       if (_jspx_th_html_005foption_005f49.doEndTag() == 5) {
/* 4605 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49); return;
/*      */                                                       }
/*      */                                                       
/* 4608 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f49);
/* 4609 */                                                       out.write("\n      ");
/*      */                                                       
/* 4611 */                                                       OptionTag _jspx_th_html_005foption_005f50 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4612 */                                                       _jspx_th_html_005foption_005f50.setPageContext(_jspx_page_context);
/* 4613 */                                                       _jspx_th_html_005foption_005f50.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4615 */                                                       _jspx_th_html_005foption_005f50.setValue("Script Monitor");
/* 4616 */                                                       int _jspx_eval_html_005foption_005f50 = _jspx_th_html_005foption_005f50.doStartTag();
/* 4617 */                                                       if (_jspx_eval_html_005foption_005f50 != 0) {
/* 4618 */                                                         if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4619 */                                                           out = _jspx_page_context.pushBody();
/* 4620 */                                                           _jspx_th_html_005foption_005f50.setBodyContent((BodyContent)out);
/* 4621 */                                                           _jspx_th_html_005foption_005f50.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4624 */                                                           out.print(FormatUtil.getString("Script Monitor"));
/* 4625 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f50.doAfterBody();
/* 4626 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4629 */                                                         if (_jspx_eval_html_005foption_005f50 != 1) {
/* 4630 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4633 */                                                       if (_jspx_th_html_005foption_005f50.doEndTag() == 5) {
/* 4634 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50); return;
/*      */                                                       }
/*      */                                                       
/* 4637 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f50);
/* 4638 */                                                       out.write(10);
/*      */ 
/*      */                                                     }
/* 4641 */                                                     else if (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE"))
/*      */                                                     {
/*      */ 
/* 4644 */                                                       out.write("\n\t\t</optgroup>\t<optgroup label=\"");
/* 4645 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.servers"));
/* 4646 */                                                       out.write("\">\n\t\t\t");
/*      */                                                       
/* 4648 */                                                       OptionTag _jspx_th_html_005foption_005f51 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4649 */                                                       _jspx_th_html_005foption_005f51.setPageContext(_jspx_page_context);
/* 4650 */                                                       _jspx_th_html_005foption_005f51.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4652 */                                                       _jspx_th_html_005foption_005f51.setValue("SYSTEM:9999");
/* 4653 */                                                       int _jspx_eval_html_005foption_005f51 = _jspx_th_html_005foption_005f51.doStartTag();
/* 4654 */                                                       if (_jspx_eval_html_005foption_005f51 != 0) {
/* 4655 */                                                         if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4656 */                                                           out = _jspx_page_context.pushBody();
/* 4657 */                                                           _jspx_th_html_005foption_005f51.setBodyContent((BodyContent)out);
/* 4658 */                                                           _jspx_th_html_005foption_005f51.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4661 */                                                           out.print(FormatUtil.getString("am.monitortab.servers.text"));
/* 4662 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f51.doAfterBody();
/* 4663 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4666 */                                                         if (_jspx_eval_html_005foption_005f51 != 1) {
/* 4667 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4670 */                                                       if (_jspx_th_html_005foption_005f51.doEndTag() == 5) {
/* 4671 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51); return;
/*      */                                                       }
/*      */                                                       
/* 4674 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f51);
/* 4675 */                                                       out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4676 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.dbserver"));
/* 4677 */                                                       out.write("\">\n\t\t\t");
/*      */                                                       
/* 4679 */                                                       OptionTag _jspx_th_html_005foption_005f52 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4680 */                                                       _jspx_th_html_005foption_005f52.setPageContext(_jspx_page_context);
/* 4681 */                                                       _jspx_th_html_005foption_005f52.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4683 */                                                       _jspx_th_html_005foption_005f52.setValue("DB2:50000");
/* 4684 */                                                       int _jspx_eval_html_005foption_005f52 = _jspx_th_html_005foption_005f52.doStartTag();
/* 4685 */                                                       if (_jspx_eval_html_005foption_005f52 != 0) {
/* 4686 */                                                         if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4687 */                                                           out = _jspx_page_context.pushBody();
/* 4688 */                                                           _jspx_th_html_005foption_005f52.setBodyContent((BodyContent)out);
/* 4689 */                                                           _jspx_th_html_005foption_005f52.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4692 */                                                           out.print(FormatUtil.getString("am.webclient.db2.servertype"));
/* 4693 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f52.doAfterBody();
/* 4694 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4697 */                                                         if (_jspx_eval_html_005foption_005f52 != 1) {
/* 4698 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4701 */                                                       if (_jspx_th_html_005foption_005f52.doEndTag() == 5) {
/* 4702 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52); return;
/*      */                                                       }
/*      */                                                       
/* 4705 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f52);
/* 4706 */                                                       out.write("\n\t\t\t");
/*      */                                                       
/* 4708 */                                                       OptionTag _jspx_th_html_005foption_005f53 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4709 */                                                       _jspx_th_html_005foption_005f53.setPageContext(_jspx_page_context);
/* 4710 */                                                       _jspx_th_html_005foption_005f53.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4712 */                                                       _jspx_th_html_005foption_005f53.setValue("MSSQLDB:1433");
/* 4713 */                                                       int _jspx_eval_html_005foption_005f53 = _jspx_th_html_005foption_005f53.doStartTag();
/* 4714 */                                                       if (_jspx_eval_html_005foption_005f53 != 0) {
/* 4715 */                                                         if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4716 */                                                           out = _jspx_page_context.pushBody();
/* 4717 */                                                           _jspx_th_html_005foption_005f53.setBodyContent((BodyContent)out);
/* 4718 */                                                           _jspx_th_html_005foption_005f53.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4721 */                                                           out.print(FormatUtil.getString("am.webclient.mssqldetails.server"));
/* 4722 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f53.doAfterBody();
/* 4723 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4726 */                                                         if (_jspx_eval_html_005foption_005f53 != 1) {
/* 4727 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4730 */                                                       if (_jspx_th_html_005foption_005f53.doEndTag() == 5) {
/* 4731 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53); return;
/*      */                                                       }
/*      */                                                       
/* 4734 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f53);
/* 4735 */                                                       out.write("\n\t\t\t");
/*      */                                                       
/* 4737 */                                                       OptionTag _jspx_th_html_005foption_005f54 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4738 */                                                       _jspx_th_html_005foption_005f54.setPageContext(_jspx_page_context);
/* 4739 */                                                       _jspx_th_html_005foption_005f54.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4741 */                                                       _jspx_th_html_005foption_005f54.setValue("MYSQLDB:3306");
/* 4742 */                                                       int _jspx_eval_html_005foption_005f54 = _jspx_th_html_005foption_005f54.doStartTag();
/* 4743 */                                                       if (_jspx_eval_html_005foption_005f54 != 0) {
/* 4744 */                                                         if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4745 */                                                           out = _jspx_page_context.pushBody();
/* 4746 */                                                           _jspx_th_html_005foption_005f54.setBodyContent((BodyContent)out);
/* 4747 */                                                           _jspx_th_html_005foption_005f54.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4750 */                                                           out.print(FormatUtil.getString("am.webclient.newresourcetypes.mysqlserver.text"));
/* 4751 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f54.doAfterBody();
/* 4752 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4755 */                                                         if (_jspx_eval_html_005foption_005f54 != 1) {
/* 4756 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4759 */                                                       if (_jspx_th_html_005foption_005f54.doEndTag() == 5) {
/* 4760 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54); return;
/*      */                                                       }
/*      */                                                       
/* 4763 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f54);
/* 4764 */                                                       out.write("\n\t\t\t");
/*      */                                                       
/* 4766 */                                                       OptionTag _jspx_th_html_005foption_005f55 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4767 */                                                       _jspx_th_html_005foption_005f55.setPageContext(_jspx_page_context);
/* 4768 */                                                       _jspx_th_html_005foption_005f55.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4770 */                                                       _jspx_th_html_005foption_005f55.setValue("ORACLEDB:1521");
/* 4771 */                                                       int _jspx_eval_html_005foption_005f55 = _jspx_th_html_005foption_005f55.doStartTag();
/* 4772 */                                                       if (_jspx_eval_html_005foption_005f55 != 0) {
/* 4773 */                                                         if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4774 */                                                           out = _jspx_page_context.pushBody();
/* 4775 */                                                           _jspx_th_html_005foption_005f55.setBodyContent((BodyContent)out);
/* 4776 */                                                           _jspx_th_html_005foption_005f55.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4779 */                                                           out.print(FormatUtil.getString("am.webclient.oracle.servertype"));
/* 4780 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f55.doAfterBody();
/* 4781 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4784 */                                                         if (_jspx_eval_html_005foption_005f55 != 1) {
/* 4785 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4788 */                                                       if (_jspx_th_html_005foption_005f55.doEndTag() == 5) {
/* 4789 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55); return;
/*      */                                                       }
/*      */                                                       
/* 4792 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f55);
/* 4793 */                                                       out.write("\n\t\t\t");
/*      */                                                       
/* 4795 */                                                       OptionTag _jspx_th_html_005foption_005f56 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4796 */                                                       _jspx_th_html_005foption_005f56.setPageContext(_jspx_page_context);
/* 4797 */                                                       _jspx_th_html_005foption_005f56.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4799 */                                                       _jspx_th_html_005foption_005f56.setValue("SYBASEDB:5000");
/* 4800 */                                                       int _jspx_eval_html_005foption_005f56 = _jspx_th_html_005foption_005f56.doStartTag();
/* 4801 */                                                       if (_jspx_eval_html_005foption_005f56 != 0) {
/* 4802 */                                                         if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4803 */                                                           out = _jspx_page_context.pushBody();
/* 4804 */                                                           _jspx_th_html_005foption_005f56.setBodyContent((BodyContent)out);
/* 4805 */                                                           _jspx_th_html_005foption_005f56.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4808 */                                                           out.print(FormatUtil.getString("Sybase"));
/* 4809 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f56.doAfterBody();
/* 4810 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4813 */                                                         if (_jspx_eval_html_005foption_005f56 != 1) {
/* 4814 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4817 */                                                       if (_jspx_th_html_005foption_005f56.doEndTag() == 5) {
/* 4818 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56); return;
/*      */                                                       }
/*      */                                                       
/* 4821 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f56);
/* 4822 */                                                       out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4823 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.services"));
/* 4824 */                                                       out.write("\">\n\t\t\t");
/*      */                                                       
/* 4826 */                                                       OptionTag _jspx_th_html_005foption_005f57 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4827 */                                                       _jspx_th_html_005foption_005f57.setPageContext(_jspx_page_context);
/* 4828 */                                                       _jspx_th_html_005foption_005f57.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4830 */                                                       _jspx_th_html_005foption_005f57.setValue("SERVICE:9090");
/* 4831 */                                                       int _jspx_eval_html_005foption_005f57 = _jspx_th_html_005foption_005f57.doStartTag();
/* 4832 */                                                       if (_jspx_eval_html_005foption_005f57 != 0) {
/* 4833 */                                                         if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4834 */                                                           out = _jspx_page_context.pushBody();
/* 4835 */                                                           _jspx_th_html_005foption_005f57.setBodyContent((BodyContent)out);
/* 4836 */                                                           _jspx_th_html_005foption_005f57.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4839 */                                                           out.write(32);
/* 4840 */                                                           out.print(FormatUtil.getString("Service Monitoring"));
/* 4841 */                                                           out.write(32);
/* 4842 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f57.doAfterBody();
/* 4843 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4846 */                                                         if (_jspx_eval_html_005foption_005f57 != 1) {
/* 4847 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4850 */                                                       if (_jspx_th_html_005foption_005f57.doEndTag() == 5) {
/* 4851 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57); return;
/*      */                                                       }
/*      */                                                       
/* 4854 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f57);
/* 4855 */                                                       out.write("\n\t\t\t");
/*      */                                                       
/* 4857 */                                                       OptionTag _jspx_th_html_005foption_005f58 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4858 */                                                       _jspx_th_html_005foption_005f58.setPageContext(_jspx_page_context);
/* 4859 */                                                       _jspx_th_html_005foption_005f58.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4861 */                                                       _jspx_th_html_005foption_005f58.setValue("SNMP:161");
/* 4862 */                                                       int _jspx_eval_html_005foption_005f58 = _jspx_th_html_005foption_005f58.doStartTag();
/* 4863 */                                                       if (_jspx_eval_html_005foption_005f58 != 0) {
/* 4864 */                                                         if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4865 */                                                           out = _jspx_page_context.pushBody();
/* 4866 */                                                           _jspx_th_html_005foption_005f58.setBodyContent((BodyContent)out);
/* 4867 */                                                           _jspx_th_html_005foption_005f58.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4870 */                                                           out.print(FormatUtil.getString("SNMP"));
/* 4871 */                                                           out.write(" (V1 or V2c)");
/* 4872 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f58.doAfterBody();
/* 4873 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4876 */                                                         if (_jspx_eval_html_005foption_005f58 != 1) {
/* 4877 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4880 */                                                       if (_jspx_th_html_005foption_005f58.doEndTag() == 5) {
/* 4881 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58); return;
/*      */                                                       }
/*      */                                                       
/* 4884 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f58);
/* 4885 */                                                       out.write("</optgroup>");
/* 4886 */                                                       out.write("\n\t\t\t<optgroup label=\"");
/* 4887 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.webservices"));
/* 4888 */                                                       out.write("\">\n\t\t\t");
/*      */                                                       
/* 4890 */                                                       OptionTag _jspx_th_html_005foption_005f59 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4891 */                                                       _jspx_th_html_005foption_005f59.setPageContext(_jspx_page_context);
/* 4892 */                                                       _jspx_th_html_005foption_005f59.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4894 */                                                       _jspx_th_html_005foption_005f59.setValue("UrlMonitor");
/* 4895 */                                                       int _jspx_eval_html_005foption_005f59 = _jspx_th_html_005foption_005f59.doStartTag();
/* 4896 */                                                       if (_jspx_eval_html_005foption_005f59 != 0) {
/* 4897 */                                                         if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4898 */                                                           out = _jspx_page_context.pushBody();
/* 4899 */                                                           _jspx_th_html_005foption_005f59.setBodyContent((BodyContent)out);
/* 4900 */                                                           _jspx_th_html_005foption_005f59.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4903 */                                                           out.print(FormatUtil.getString("HTTP-URLs"));
/* 4904 */                                                           out.write(32);
/* 4905 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f59.doAfterBody();
/* 4906 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4909 */                                                         if (_jspx_eval_html_005foption_005f59 != 1) {
/* 4910 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4913 */                                                       if (_jspx_th_html_005foption_005f59.doEndTag() == 5) {
/* 4914 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59); return;
/*      */                                                       }
/*      */                                                       
/* 4917 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f59);
/* 4918 */                                                       out.write("\n\t\t\t");
/*      */                                                       
/* 4920 */                                                       OptionTag _jspx_th_html_005foption_005f60 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4921 */                                                       _jspx_th_html_005foption_005f60.setPageContext(_jspx_page_context);
/* 4922 */                                                       _jspx_th_html_005foption_005f60.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4924 */                                                       _jspx_th_html_005foption_005f60.setValue("UrlSeq");
/* 4925 */                                                       int _jspx_eval_html_005foption_005f60 = _jspx_th_html_005foption_005f60.doStartTag();
/* 4926 */                                                       if (_jspx_eval_html_005foption_005f60 != 0) {
/* 4927 */                                                         if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4928 */                                                           out = _jspx_page_context.pushBody();
/* 4929 */                                                           _jspx_th_html_005foption_005f60.setBodyContent((BodyContent)out);
/* 4930 */                                                           _jspx_th_html_005foption_005f60.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4933 */                                                           out.print(FormatUtil.getString("HTTP-URL Sequence"));
/* 4934 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f60.doAfterBody();
/* 4935 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4938 */                                                         if (_jspx_eval_html_005foption_005f60 != 1) {
/* 4939 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4942 */                                                       if (_jspx_th_html_005foption_005f60.doEndTag() == 5) {
/* 4943 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60); return;
/*      */                                                       }
/*      */                                                       
/* 4946 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f60);
/* 4947 */                                                       out.write("</optgroup>\n\t\t\t<optgroup label=\"");
/* 4948 */                                                       out.print(FormatUtil.getString("am.webclient.monitorgroupsecond.category.custom"));
/* 4949 */                                                       out.write("\">\n\t\t\t");
/*      */                                                       
/* 4951 */                                                       OptionTag _jspx_th_html_005foption_005f61 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4952 */                                                       _jspx_th_html_005foption_005f61.setPageContext(_jspx_page_context);
/* 4953 */                                                       _jspx_th_html_005foption_005f61.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                                       
/* 4955 */                                                       _jspx_th_html_005foption_005f61.setValue("Script Monitor");
/* 4956 */                                                       int _jspx_eval_html_005foption_005f61 = _jspx_th_html_005foption_005f61.doStartTag();
/* 4957 */                                                       if (_jspx_eval_html_005foption_005f61 != 0) {
/* 4958 */                                                         if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4959 */                                                           out = _jspx_page_context.pushBody();
/* 4960 */                                                           _jspx_th_html_005foption_005f61.setBodyContent((BodyContent)out);
/* 4961 */                                                           _jspx_th_html_005foption_005f61.doInitBody();
/*      */                                                         }
/*      */                                                         for (;;) {
/* 4964 */                                                           out.print(FormatUtil.getString("Script Monitor"));
/* 4965 */                                                           int evalDoAfterBody = _jspx_th_html_005foption_005f61.doAfterBody();
/* 4966 */                                                           if (evalDoAfterBody != 2)
/*      */                                                             break;
/*      */                                                         }
/* 4969 */                                                         if (_jspx_eval_html_005foption_005f61 != 1) {
/* 4970 */                                                           out = _jspx_page_context.popBody();
/*      */                                                         }
/*      */                                                       }
/* 4973 */                                                       if (_jspx_th_html_005foption_005f61.doEndTag() == 5) {
/* 4974 */                                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61); return;
/*      */                                                       }
/*      */                                                       
/* 4977 */                                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f61);
/* 4978 */                                                       out.write(10);
/* 4979 */                                                       out.write(10);
/*      */                                                     }
/*      */                                                     
/*      */ 
/* 4983 */                                                     out.write("\n\n\n\n      ");
/* 4984 */                                                     int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4985 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 4988 */                                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4989 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 4992 */                                                 if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4993 */                                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                                 }
/*      */                                                 
/* 4996 */                                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4997 */                                                 out.write("\n                      \n      ");
/* 4998 */                                                 int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 4999 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5003 */                                             if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 5004 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                             }
/*      */                                             
/* 5007 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 5008 */                                             out.write("\n      ");
/* 5009 */                                             if (_jspx_meth_c_005fif_005f5(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                               return;
/* 5011 */                                             out.write("\n      </td>\n      \n      ");
/* 5012 */                                             if (request.getParameter("type") != null) {
/* 5013 */                                               isConfMonitor = confMonConfig.isConfMonitor(request.getParameter("type"));
/* 5014 */                                               String restype = request.getParameter("type");
/* 5015 */                                               if (restype.indexOf(":") != -1) {
/* 5016 */                                                 restype = restype.substring(0, restype.indexOf(":"));
/*      */                                               }
/* 5018 */                                               if (((isConfMonitor) && (!restype.equals("QueryMonitor"))) || ((!restype.equals("JMX1.2-MX4J-RMI")) && (!restype.equals("Generic WMI")) && (!restype.equals("Script Monitor")) && (!restype.equals("Custom-Application")) && (!restype.equals("File System Monitor")) && (!restype.equals("QueryMonitor")) && (!restype.equals("SNMP")) && (!restype.equals("TELNET")) && (!restype.equals("Exchange")) && (!restype.equals("WTA")) && (!restype.equals("WEB")) && (!restype.equals("UrlSeq")) && (!restype.equals("PHP")) && (!restype.equals("IIS")) && (!restype.equals("APACHE")) && (!restype.equals("MAIL")) && (!restype.equals("All")) && (restype.indexOf("SAP") == -1))) {
/* 5019 */                                                 out.write("\n      \t<td class=\"tableheading-monitor-config itadmin-hide\" align=\"right\">\n      \n      \t\t<div id=\"Conf-bulk-configuration\"> \n\t\t\t    \t\t<a href=\"javascript:void(0)\"  onclick=\"window.open('/BulkAddMonitors.do?method=showBulkImportForm&type=");
/* 5020 */                                                 out.print(restype);
/* 5021 */                                                 out.write("','windowName','toolbar=no,status=no,menubar=no,width=1000,height=500,scrollbars=yes')\" class=\"staticlinks\" >");
/* 5022 */                                                 out.print(FormatUtil.getString("am.webclient.admin.bulkimport.label.text"));
/* 5023 */                                                 out.write("</a>\n\t    \t</div><img src=\"/images/script-icon.gif\">\n   \t   </td>\n      \n      \t");
/*      */                                               }
/*      */                                             }
/* 5026 */                                             out.write("     \n      \n  </tr>\n</table>\n <script src=\"/template/chosen.jquery.min.js\" type=\"text/javascript\"></script><script type=\"text/javascript\"> $(\".formtext\").chosen();  </script>\n");
/* 5027 */                                             out.write(10);
/* 5028 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 5029 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5033 */                                         if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 5034 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                         }
/*      */                                         
/* 5037 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 5038 */                                         out.write(10);
/*      */                                         
/* 5040 */                                         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5041 */                                         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 5042 */                                         _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fempty_005f1);
/*      */                                         
/* 5044 */                                         _jspx_th_c_005fif_005f6.setTest("${!empty param.wiz}");
/* 5045 */                                         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 5046 */                                         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                           for (;;) {
/* 5048 */                                             out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"26\" class=\"tableheading\">");
/* 5049 */                                             out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.configure"));
/* 5050 */                                             out.write("</td>\n  </tr>\n</table>\n");
/* 5051 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 5052 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5056 */                                         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 5057 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                         }
/*      */                                         
/* 5060 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 5061 */                                         out.write(10);
/* 5062 */                                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 5063 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5067 */                                     if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 5068 */                                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                                     }
/*      */                                     
/* 5071 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 5072 */                                     out.write("\n\n   ");
/* 5073 */                                     if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5075 */                                     out.write("\n   <input type=\"hidden\" name=\"actionmethod\" value=\"createUrlSequence\">\n\n\t <input type=\"hidden\" name=\"addtoha\" value=\"true\">\n");
/*      */                                     
/* 5077 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5078 */                                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 5079 */                                     _jspx_th_logic_005fnotEmpty_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5081 */                                     _jspx_th_logic_005fnotEmpty_005f0.setName("UrlForm");
/*      */                                     
/* 5083 */                                     _jspx_th_logic_005fnotEmpty_005f0.setProperty("userseqid");
/* 5084 */                                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 5085 */                                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                                       for (;;) {
/* 5087 */                                         out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n\n  ");
/* 5088 */                                         if (_jspx_meth_html_005fhidden_005f2(_jspx_th_logic_005fnotEmpty_005f0, _jspx_page_context))
/*      */                                           return;
/* 5090 */                                         out.write("\n <tr>\n    <td width=\"100%\" height=\"26\" class=\"tableheading\">");
/* 5091 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.configurenext"));
/* 5092 */                                         out.write("\n    </td>\n  </tr>\n</table>\n  ");
/* 5093 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 5094 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5098 */                                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 5099 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                                     }
/*      */                                     
/* 5102 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 5103 */                                     out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"1\" class=\"lrborder\">\n");
/*      */                                     
/* 5105 */                                     EmptyTag _jspx_th_logic_005fempty_005f2 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 5106 */                                     _jspx_th_logic_005fempty_005f2.setPageContext(_jspx_page_context);
/* 5107 */                                     _jspx_th_logic_005fempty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5109 */                                     _jspx_th_logic_005fempty_005f2.setName("UrlForm");
/*      */                                     
/* 5111 */                                     _jspx_th_logic_005fempty_005f2.setProperty("userseqid");
/* 5112 */                                     int _jspx_eval_logic_005fempty_005f2 = _jspx_th_logic_005fempty_005f2.doStartTag();
/* 5113 */                                     if (_jspx_eval_logic_005fempty_005f2 != 0) {
/*      */                                       for (;;) {
/* 5115 */                                         out.write("\n    <tr>\n      <td width=\"20%\" class=\"bodytext\">");
/* 5116 */                                         out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 5117 */                                         out.write("<span class=\"mandatory\">*</span> </td>\n      <td width=\"80%\" colspan=\"2\"> ");
/* 5118 */                                         if (_jspx_meth_html_005ftext_005f0(_jspx_th_logic_005fempty_005f2, _jspx_page_context))
/*      */                                           return;
/* 5120 */                                         out.write("</td>\n  </tr>\n");
/* 5121 */                                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f2.doAfterBody();
/* 5122 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5126 */                                     if (_jspx_th_logic_005fempty_005f2.doEndTag() == 5) {
/* 5127 */                                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f2); return;
/*      */                                     }
/*      */                                     
/* 5130 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f2);
/* 5131 */                                     out.write("\n\n  <tr>\n\n      ");
/*      */                                     
/* 5133 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5134 */                                     _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 5135 */                                     _jspx_th_logic_005fnotEmpty_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5137 */                                     _jspx_th_logic_005fnotEmpty_005f1.setName("UrlForm");
/*      */                                     
/* 5139 */                                     _jspx_th_logic_005fnotEmpty_005f1.setProperty("userseqid");
/* 5140 */                                     int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 5141 */                                     if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                       for (;;) {
/* 5143 */                                         out.write("\n        <td width=\"25%\" class=\"bodytext\">");
/* 5144 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urladd"));
/* 5145 */                                         out.write("</td>\n    \t<td width=\"75%\"  colspan=\"2\" class=\"bodytext\">\n    \t<a href=\"#htmlcontent\" class=\"staticlinks\" title=\"");
/* 5146 */                                         if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/*      */                                           return;
/* 5148 */                                         out.write(34);
/* 5149 */                                         out.write(62);
/* 5150 */                                         if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/*      */                                           return;
/* 5152 */                                         out.write("   </a>\n      \t");
/* 5153 */                                         if (_jspx_meth_html_005fhidden_005f3(_jspx_th_logic_005fnotEmpty_005f1, _jspx_page_context))
/*      */                                           return;
/* 5155 */                                         out.write("\n      \t</td>\n\n      ");
/* 5156 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 5157 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5161 */                                     if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 5162 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                                     }
/*      */                                     
/* 5165 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 5166 */                                     out.write("\n\n      ");
/*      */                                     
/* 5168 */                                     EmptyTag _jspx_th_logic_005fempty_005f3 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 5169 */                                     _jspx_th_logic_005fempty_005f3.setPageContext(_jspx_page_context);
/* 5170 */                                     _jspx_th_logic_005fempty_005f3.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5172 */                                     _jspx_th_logic_005fempty_005f3.setName("UrlForm");
/*      */                                     
/* 5174 */                                     _jspx_th_logic_005fempty_005f3.setProperty("userseqid");
/* 5175 */                                     int _jspx_eval_logic_005fempty_005f3 = _jspx_th_logic_005fempty_005f3.doStartTag();
/* 5176 */                                     if (_jspx_eval_logic_005fempty_005f3 != 0) {
/*      */                                       for (;;) {
/* 5178 */                                         out.write("\n        <td width=\"25%\" class=\"bodytext\">");
/* 5179 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urladd"));
/* 5180 */                                         out.write("<span class=\"mandatory\">*</span></td>\n    \t<td colspan=\"2\">");
/* 5181 */                                         if (_jspx_meth_html_005ftext_005f1(_jspx_th_logic_005fempty_005f3, _jspx_page_context))
/*      */                                           return;
/* 5183 */                                         out.write("\n      \t<span class=\"footer\">");
/* 5184 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urladd.message"));
/* 5185 */                                         out.write("</span>\n      \t</td>\n      ");
/* 5186 */                                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f3.doAfterBody();
/* 5187 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5191 */                                     if (_jspx_th_logic_005fempty_005f3.doEndTag() == 5) {
/* 5192 */                                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f3); return;
/*      */                                     }
/*      */                                     
/* 5195 */                                     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f3);
/* 5196 */                                     out.write("\n\n  </tr>\n  ");
/*      */                                     
/* 5198 */                                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 5199 */                                     _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 5200 */                                     _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                     
/* 5202 */                                     _jspx_th_logic_005fnotEmpty_005f2.setName("UrlForm");
/*      */                                     
/* 5204 */                                     _jspx_th_logic_005fnotEmpty_005f2.setProperty("userseqid");
/* 5205 */                                     int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 5206 */                                     if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                       for (;;) {
/* 5208 */                                         out.write("\n        <tr>\n            <td class=\"bodytext\">");
/* 5209 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.nextseq"));
/* 5210 */                                         out.write(" <div align=\"right\">");
/* 5211 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.link"));
/* 5212 */                                         out.write("<input type=\"radio\" name=\"opt\" value=\"1\" checked></div></td>\n            <td colspan=\"2\">\n              ");
/*      */                                         
/* 5214 */                                         SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(SelectTag.class);
/* 5215 */                                         _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5216 */                                         _jspx_th_html_005fselect_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                         
/* 5218 */                                         _jspx_th_html_005fselect_005f1.setProperty("url");
/* 5219 */                                         int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5220 */                                         if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5221 */                                           if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5222 */                                             out = _jspx_page_context.pushBody();
/* 5223 */                                             _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5224 */                                             _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5227 */                                             out.write("\n\t          ");
/* 5228 */                                             if (_jspx_meth_logic_005fnotEmpty_005f3(_jspx_th_html_005fselect_005f1, _jspx_page_context))
/*      */                                               return;
/* 5230 */                                             out.write("\n\t          ");
/*      */                                             
/* 5232 */                                             EmptyTag _jspx_th_logic_005fempty_005f4 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5233 */                                             _jspx_th_logic_005fempty_005f4.setPageContext(_jspx_page_context);
/* 5234 */                                             _jspx_th_logic_005fempty_005f4.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                             
/* 5236 */                                             _jspx_th_logic_005fempty_005f4.setName("availableurls");
/* 5237 */                                             int _jspx_eval_logic_005fempty_005f4 = _jspx_th_logic_005fempty_005f4.doStartTag();
/* 5238 */                                             if (_jspx_eval_logic_005fempty_005f4 != 0) {
/*      */                                               for (;;) {
/* 5240 */                                                 out.write("\n\t          <option value=\"\">");
/* 5241 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.na"));
/* 5242 */                                                 out.write("</option>\n\t          ");
/* 5243 */                                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f4.doAfterBody();
/* 5244 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5248 */                                             if (_jspx_th_logic_005fempty_005f4.doEndTag() == 5) {
/* 5249 */                                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4); return;
/*      */                                             }
/*      */                                             
/* 5252 */                                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f4);
/* 5253 */                                             out.write("\n        ");
/* 5254 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5255 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5258 */                                           if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5259 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5262 */                                         if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5263 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                         }
/*      */                                         
/* 5266 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f1);
/* 5267 */                                         out.write("\n\n\n            </td>\n    </tr>\n    <tr>\n        <td width=\"25%\" height=\"20\" class=\"bodytext\" valign=\"top\">&nbsp;<div align=\"right\">");
/* 5268 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.form"));
/* 5269 */                                         out.write("<input type=\"radio\" name=\"opt\" value=\"3\"></div></td>\n        <td height=\"20\" width=\"75%\" colspan=\"2\" class=\"bodytext\">\n\t\t");
/*      */                                         
/* 5271 */                                         SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.get(SelectTag.class);
/* 5272 */                                         _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 5273 */                                         _jspx_th_html_005fselect_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                         
/* 5275 */                                         _jspx_th_html_005fselect_005f2.setProperty("formurl");
/*      */                                         
/* 5277 */                                         _jspx_th_html_005fselect_005f2.setOnchange("showLayers(this)");
/* 5278 */                                         int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 5279 */                                         if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 5280 */                                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5281 */                                             out = _jspx_page_context.pushBody();
/* 5282 */                                             _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 5283 */                                             _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5286 */                                             out.write(10);
/* 5287 */                                             out.write(9);
/* 5288 */                                             out.write(9);
/* 5289 */                                             if (_jspx_meth_logic_005fnotEmpty_005f4(_jspx_th_html_005fselect_005f2, _jspx_page_context))
/*      */                                               return;
/* 5291 */                                             out.write(10);
/* 5292 */                                             out.write(9);
/* 5293 */                                             out.write(9);
/*      */                                             
/* 5295 */                                             EmptyTag _jspx_th_logic_005fempty_005f5 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5296 */                                             _jspx_th_logic_005fempty_005f5.setPageContext(_jspx_page_context);
/* 5297 */                                             _jspx_th_logic_005fempty_005f5.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                             
/* 5299 */                                             _jspx_th_logic_005fempty_005f5.setName("availableforms");
/* 5300 */                                             int _jspx_eval_logic_005fempty_005f5 = _jspx_th_logic_005fempty_005f5.doStartTag();
/* 5301 */                                             if (_jspx_eval_logic_005fempty_005f5 != 0) {
/*      */                                               for (;;) {
/* 5303 */                                                 out.write("\n\t\t<option value=\"\">");
/* 5304 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.na"));
/* 5305 */                                                 out.write("</option>\n\t\t");
/* 5306 */                                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f5.doAfterBody();
/* 5307 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5311 */                                             if (_jspx_th_logic_005fempty_005f5.doEndTag() == 5) {
/* 5312 */                                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5); return;
/*      */                                             }
/*      */                                             
/* 5315 */                                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f5);
/* 5316 */                                             out.write(10);
/* 5317 */                                             out.write(9);
/* 5318 */                                             out.write(9);
/* 5319 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 5320 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5323 */                                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 5324 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5327 */                                         if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 5328 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                                         }
/*      */                                         
/* 5331 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 5332 */                                         out.write(10);
/* 5333 */                                         out.write(9);
/*      */                                         
/* 5335 */                                         NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5336 */                                         _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 5337 */                                         _jspx_th_logic_005fnotEmpty_005f5.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                         
/* 5339 */                                         _jspx_th_logic_005fnotEmpty_005f5.setName("availableforms");
/* 5340 */                                         int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 5341 */                                         if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */                                           for (;;) {
/* 5343 */                                             out.write(10);
/* 5344 */                                             out.write(9);
/* 5345 */                                             out.write(9);
/*      */                                             
/* 5347 */                                             ParseHtml ph = (ParseHtml)request.getAttribute("parsedhtml");
/* 5348 */                                             Vector formurls = (Vector)request.getAttribute("availableforms");
/* 5349 */                                             int bigformindex = findBiggestForm(formurls, ph);
/*      */                                             
/* 5351 */                                             out.write("\n\t\t\t<script>\n\t\t\tdocument.UrlForm.formurl.selectedIndex=");
/* 5352 */                                             out.print(bigformindex);
/* 5353 */                                             out.write("\n\t\t\t</script>\n\t\t\t<br>\n\t\t\t");
/*      */                                             
/* 5355 */                                             for (int i = 0; i < formurls.size(); i++)
/*      */                                             {
/* 5357 */                                               Properties props = (Properties)formurls.get(i);
/* 5358 */                                               String formaction = props.getProperty("value");
/*      */                                               
/*      */ 
/* 5361 */                                               out.write("\n\n\t\t\t<!-- Layer definition starts here -->\n\t\t\t<div id=\"");
/* 5362 */                                               out.print("formlayer" + i);
/* 5363 */                                               out.write("\" style=\"DISPLAY: none\">\n\n\t\t\t<TABLE border=\"0\" cellspacing=\"0\" cellpadding=\"3\" class=\"lrtbdarkborder\">\n\t\t\t<tr ><TD class=\"tablebottom bodytextbold\">");
/* 5364 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.name"));
/* 5365 */                                               out.write("</TD><TD class=\"tablebottom bodytextbold\">");
/* 5366 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.value"));
/* 5367 */                                               out.write("</TD></tr>\n\n\t\t\t");
/*      */                                               
/*      */ 
/*      */ 
/* 5371 */                                               Hashtable textbox = ph.getTextBox(formaction);
/*      */                                               
/*      */ 
/*      */ 
/* 5375 */                                               Enumeration keys = textbox.keys();
/* 5376 */                                               while (keys.hasMoreElements())
/*      */                                               {
/* 5378 */                                                 String name = (String)keys.nextElement();
/* 5379 */                                                 org.htmlparser.Tag value = (org.htmlparser.Tag)textbox.get(name);
/* 5380 */                                                 StringTokenizer tokens = new StringTokenizer(name, "&&");
/* 5381 */                                                 name = tokens.nextToken();
/*      */                                                 
/* 5383 */                                                 out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t<TD class=\"monitorinfoeven\">");
/* 5384 */                                                 out.print(name);
/* 5385 */                                                 out.write("</TD>\n\t\t\t\t\t");
/*      */                                                 
/*      */ 
/*      */ 
/* 5389 */                                                 value.setAttribute("name", "adv_" + value.getAttribute("name"));
/*      */                                                 
/*      */ 
/* 5392 */                                                 out.write("\n\t\t\t\t\t<TD class=\"monitorinfoeven\">");
/* 5393 */                                                 out.print(value.toHtml());
/* 5394 */                                                 out.write("\n\t\t\t\t\t");
/*      */                                                 
/* 5396 */                                                 if ((value instanceof InputTag))
/*      */                                                 {
/* 5398 */                                                   InputTag it = (InputTag)value;
/* 5399 */                                                   String hidname = it.getAttribute("name");
/* 5400 */                                                   String hidvalue = it.getAttribute("value");
/* 5401 */                                                   String type = it.getAttribute("type");
/* 5402 */                                                   if (type.equalsIgnoreCase("hidden"))
/*      */                                                   {
/* 5404 */                                                     if (hidvalue != null) {
/* 5405 */                                                       out.println(hidvalue);
/*      */                                                     }
/*      */                                                   }
/*      */                                                 }
/* 5409 */                                                 out.write("\n\t\t\t\t\t&nbsp;</TD>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                               }
/*      */                                               
/*      */ 
/*      */ 
/* 5414 */                                               out.write("\n\n\t\t\t</TABLE>\n\t\t\t</div>\n\t\t\t");
/*      */                                             }
/*      */                                             
/*      */ 
/*      */ 
/*      */ 
/* 5420 */                                             out.write("\n\n\t\t<script>\n\t\t\tshowLayers(document.UrlForm.formurl);\n\t</script>\n\t\t");
/* 5421 */                                             int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 5422 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5426 */                                         if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 5427 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5); return;
/*      */                                         }
/*      */                                         
/* 5430 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 5431 */                                         out.write("\n\n\n        </td>\n    </tr>\n\n    <tr>\n        <td width=\"25%\" height=\"20\" class=\"bodytext\"  valign=\"top\">&nbsp;<div align=\"right\">");
/* 5432 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.frame"));
/* 5433 */                                         out.write("<input type=\"radio\" name=\"opt\" value=\"4\"></div></td>\n        <td height=\"20\" width=\"75%\" colspan=\"2\" >\n\n      \t\t");
/*      */                                         
/* 5435 */                                         SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.get(SelectTag.class);
/* 5436 */                                         _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 5437 */                                         _jspx_th_html_005fselect_005f3.setParent(_jspx_th_logic_005fnotEmpty_005f2);
/*      */                                         
/* 5439 */                                         _jspx_th_html_005fselect_005f3.setProperty("frame");
/* 5440 */                                         int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 5441 */                                         if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 5442 */                                           if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5443 */                                             out = _jspx_page_context.pushBody();
/* 5444 */                                             _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 5445 */                                             _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5448 */                                             out.write(10);
/* 5449 */                                             out.write(9);
/* 5450 */                                             out.write(9);
/* 5451 */                                             if (_jspx_meth_logic_005fnotEmpty_005f6(_jspx_th_html_005fselect_005f3, _jspx_page_context))
/*      */                                               return;
/* 5453 */                                             out.write(10);
/* 5454 */                                             out.write(9);
/* 5455 */                                             out.write(9);
/*      */                                             
/* 5457 */                                             EmptyTag _jspx_th_logic_005fempty_005f6 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 5458 */                                             _jspx_th_logic_005fempty_005f6.setPageContext(_jspx_page_context);
/* 5459 */                                             _jspx_th_logic_005fempty_005f6.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                             
/* 5461 */                                             _jspx_th_logic_005fempty_005f6.setName("availableframes");
/* 5462 */                                             int _jspx_eval_logic_005fempty_005f6 = _jspx_th_logic_005fempty_005f6.doStartTag();
/* 5463 */                                             if (_jspx_eval_logic_005fempty_005f6 != 0) {
/*      */                                               for (;;) {
/* 5465 */                                                 out.write("\n\t\t<option value=\"\">");
/* 5466 */                                                 out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.na"));
/* 5467 */                                                 out.write("</option>\n\t\t");
/* 5468 */                                                 int evalDoAfterBody = _jspx_th_logic_005fempty_005f6.doAfterBody();
/* 5469 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5473 */                                             if (_jspx_th_logic_005fempty_005f6.doEndTag() == 5) {
/* 5474 */                                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f6); return;
/*      */                                             }
/*      */                                             
/* 5477 */                                             this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f6);
/* 5478 */                                             out.write(10);
/* 5479 */                                             out.write(9);
/* 5480 */                                             out.write(9);
/* 5481 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 5482 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5485 */                                           if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 5486 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5489 */                                         if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 5490 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                                         }
/*      */                                         
/* 5493 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 5494 */                                         out.write("\n\n        </td>\n    </tr>\n    <tr>\n        <td width=\"25%\" height=\"20\" class=\"bodytext\"  valign=\"top\">&nbsp;<div align=\"right\">");
/* 5495 */                                         out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.other"));
/* 5496 */                                         out.write("<input type=\"radio\" name=\"opt\" value=\"2\"></div></td>\n        <td height=\"20\" width=\"75%\" colspan=\"2\">\n      <input type=\"text\" name=\"otherurl\" size=\"25\" class=\"formtext\">\n\n\n        </td>\n    </tr>\n\n\n\n");
/* 5497 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 5498 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5502 */                                     if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 5503 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                                     }
/*      */                                     
/* 5506 */                                     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 5507 */                                     out.write("\n    <tr>\n      <td height=\"20\" class=\"bodytext\">");
/* 5508 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.htmlcontent"));
/* 5509 */                                     out.write("</td>\n      <td height=\"20\" colspan=\"2\">");
/* 5510 */                                     if (_jspx_meth_html_005ftext_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5512 */                                     out.write("</td>\n  </tr>\n  <tr>\n    <td width=\"25%\" height=\"26\" class=\"bodytext\">");
/* 5513 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.webpage"));
/* 5514 */                                     out.write(" <span class=\"mandatory\">*</span> </td>\n    <td height=\"26\" colspan=\"2\" class=\"bodytext\">\n\n    ");
/* 5515 */                                     if (_jspx_meth_logic_005fempty_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5517 */                                     out.write("\n\n    ");
/* 5518 */                                     if (_jspx_meth_logic_005fnotEmpty_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5520 */                                     out.write("\n\n      ");
/* 5521 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.minutes"));
/* 5522 */                                     out.write("</td>\n  </tr>\n    <tr>\n      <td width=\"28%\" height=\"20\" class=\"bodytext\">");
/* 5523 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.formsub"));
/* 5524 */                                     out.write("</td>\n      <td  width=\"72%\" height=\"20\" colspan=\"2\">");
/* 5525 */                                     if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5527 */                                     out.write("<span class=\"bodytext\">");
/* 5528 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.post"));
/* 5529 */                                     out.write(" &nbsp;</span>\n        ");
/* 5530 */                                     if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5532 */                                     out.write("<span class=\"bodytext\">");
/* 5533 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.get"));
/* 5534 */                                     out.write("</span>\n        <span style=\"padding-left:180px;\" class=\"bodytextbold\" valign=\"absmiddle\"><input type=\"checkbox\" name=\"advanced\" onclick=\"javascript: toggleAdv('advanced');\"> ");
/* 5535 */                                     out.print(FormatUtil.getString("am.webclient.configurealert.advancedoptions"));
/* 5536 */                                     out.write(" </span>\n      </td>\n  </tr>\n  </table>\n\n<div id=\"advancedDiv\" style=\"display:none\">\n<table width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"1\" class=\"lrborder\">\n  <tr>\n    <td height=\"31\" colspan=\"3\" class=\"tablebottom bodytextbold\">");
/* 5537 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.advanceoptional"));
/* 5538 */                                     out.write("</td>\n  </tr>\n  <tr>\n    <td width=\"25%\" height=\"25\" class=\"bodytext\">");
/* 5539 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.generrorcontent"));
/* 5540 */                                     out.write("\n    </td>\n    <td height=\"25\" colspan=\"2\" class=\"bodytext\"> ");
/* 5541 */                                     if (_jspx_meth_html_005ftext_005f5(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5543 */                                     out.write("\n    </td>\n  </tr>\n  <tr>\n    <td width=\"28%\" height=\"25\" class=\"bodytext\">");
/* 5544 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.generrorresponse"));
/* 5545 */                                     out.write(" </td>\n    <td height=\"25\" class=\"bodytext\" colspan=\"2\"> ");
/* 5546 */                                     if (_jspx_meth_html_005fselect_005f4(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5548 */                                     out.write("\n      ");
/* 5549 */                                     if (_jspx_meth_html_005ftext_005f6(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5551 */                                     out.write(" </td>\n  </tr>\n  <tr>\n    <td width=\"28%\" height=\"25\" class=\"bodytext\">");
/* 5552 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.pagetimeout"));
/* 5553 */                                     out.write(" </td>\n    <td height=\"25\" colspan=\"2\" class=\"bodytext\"> ");
/* 5554 */                                     if (_jspx_meth_html_005ftext_005f7(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5556 */                                     out.write("\n      ");
/* 5557 */                                     out.print(FormatUtil.getString("am.webclient.newaction.seconds"));
/* 5558 */                                     out.write(" </td>\n  </tr>\n  <tr>\n    <td height=\"25\" colspan=\"3\" class=\"bodytext\"> ");
/* 5559 */                                     if (_jspx_meth_html_005fmultibox_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5561 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.errormessage"));
/* 5562 */                                     out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"25\" colspan=\"3\" class=\"bodytext\">");
/* 5563 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlmon.urlauthinfo"));
/* 5564 */                                     out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"25\" align=\"left\" style=\"padding-left:125px;\" class=\"bodytext\" colspan=\"3\">\n      <table width=\"40%\" border=\"0\" cellspacing=\"2\" cellpadding=\"3\" class=\"grayfullborder\">\n        <tr>\n          <td width=\"40%\" class=\"bodytext\">");
/* 5565 */                                     out.print(FormatUtil.getString("webclient.login.username"));
/* 5566 */                                     out.write("</td>\n          <td width=\"60%\" colspan=\"2\" class=\"bodytext\"><input type=\"text\" name=\"userid\" class=\"formtext\" size=\"25\" autocomplete=\"off\" /> <!--");
/* 5567 */                                     if (_jspx_meth_html_005ftext_005f8(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5569 */                                     out.write("-->\n          </td>\n        </tr>\n        <tr>\n          <td width=\"40%\" class=\"bodytext\"> ");
/* 5570 */                                     out.print(FormatUtil.getString("am.webclient.common.password.text"));
/* 5571 */                                     out.write("</td>\n          <td colspan=\"2\" class=\"bodytext\"><input type=\"password\" name=\"password\" class=\"formtext\" size=\"25\" autocomplete=\"off\" /> <!--");
/* 5572 */                                     if (_jspx_meth_html_005fpassword_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 5574 */                                     out.write("-->\n          </td>\n        </tr>\n      </table>\n    </td>\n   </tr>\n  </table>\n</div>\n<TABLE width=\"99%\" border=\"0\" cellpadding=\"8\" cellspacing=\"1\" class=\"lrborder\">\n\n\t\t\t\t<tr>\n\t\t\t\t<td height=\"28\" colspan=\"4\" class=\"tablebottom bodytextbold\">");
/* 5575 */                                     out.print(FormatUtil.getString("am.webclient.newscript.associatemonitorinstance.text"));
/* 5576 */                                     out.write(32);
/* 5577 */                                     out.print(MGSTR);
/* 5578 */                                     out.write(" </td>\n\t\t\t\t</tr>\n\n\t\t        \t      \n        ");
/*      */                                     
/* 5580 */                                     if (EnterpriseUtil.isIt360MSPEdition())
/*      */                                     {
/* 5582 */                                       out.write("\n        <tr><td style=\"height:25px;\"></td></tr>\n\t    <tr>\n\t    \t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 5583 */                                       out.print(FormatUtil.getString("it360.sp.customermgmt.customer.txt", new String[] { MGSTR }));
/* 5584 */                                       out.write("<span class=\"mandatory\">*</span></td>\n\t\t    <td height=\"28\" align=\"left\" >\n\t\t\t\t");
/*      */                                       
/* 5586 */                                       SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5587 */                                       _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 5588 */                                       _jspx_th_html_005fselect_005f5.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5590 */                                       _jspx_th_html_005fselect_005f5.setProperty("organization");
/*      */                                       
/* 5592 */                                       _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/*      */                                       
/* 5594 */                                       _jspx_th_html_005fselect_005f5.setOnchange("javascript:loadSite()");
/* 5595 */                                       int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 5596 */                                       if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 5597 */                                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5598 */                                           out = _jspx_page_context.pushBody();
/* 5599 */                                           _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 5600 */                                           _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5603 */                                           out.write("\n\t\t      \t");
/*      */                                           
/* 5605 */                                           OptionTag _jspx_th_html_005foption_005f62 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5606 */                                           _jspx_th_html_005foption_005f62.setPageContext(_jspx_page_context);
/* 5607 */                                           _jspx_th_html_005foption_005f62.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 5609 */                                           _jspx_th_html_005foption_005f62.setValue("-");
/* 5610 */                                           int _jspx_eval_html_005foption_005f62 = _jspx_th_html_005foption_005f62.doStartTag();
/* 5611 */                                           if (_jspx_eval_html_005foption_005f62 != 0) {
/* 5612 */                                             if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5613 */                                               out = _jspx_page_context.pushBody();
/* 5614 */                                               _jspx_th_html_005foption_005f62.setBodyContent((BodyContent)out);
/* 5615 */                                               _jspx_th_html_005foption_005f62.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5618 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.customer", new String[] { MGSTR }));
/* 5619 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f62.doAfterBody();
/* 5620 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5623 */                                             if (_jspx_eval_html_005foption_005f62 != 1) {
/* 5624 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5627 */                                           if (_jspx_th_html_005foption_005f62.doEndTag() == 5) {
/* 5628 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62); return;
/*      */                                           }
/*      */                                           
/* 5631 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f62);
/* 5632 */                                           out.write("\n\t\t      \t");
/*      */                                           
/* 5634 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f8 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5635 */                                           _jspx_th_logic_005fnotEmpty_005f8.setPageContext(_jspx_page_context);
/* 5636 */                                           _jspx_th_logic_005fnotEmpty_005f8.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                           
/* 5638 */                                           _jspx_th_logic_005fnotEmpty_005f8.setName("customers");
/* 5639 */                                           int _jspx_eval_logic_005fnotEmpty_005f8 = _jspx_th_logic_005fnotEmpty_005f8.doStartTag();
/* 5640 */                                           if (_jspx_eval_logic_005fnotEmpty_005f8 != 0) {
/*      */                                             for (;;) {
/* 5642 */                                               out.write(32);
/*      */                                               
/* 5644 */                                               IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5645 */                                               _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 5646 */                                               _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f8);
/*      */                                               
/* 5648 */                                               _jspx_th_logic_005fiterate_005f0.setName("customers");
/*      */                                               
/* 5650 */                                               _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                               
/* 5652 */                                               _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                               
/* 5654 */                                               _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 5655 */                                               int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 5656 */                                               if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 5657 */                                                 ArrayList row = null;
/* 5658 */                                                 Integer j = null;
/* 5659 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5660 */                                                   out = _jspx_page_context.pushBody();
/* 5661 */                                                   _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 5662 */                                                   _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                                 }
/* 5664 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5665 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 5667 */                                                   out.write("\n\t\t      \t");
/*      */                                                   
/* 5669 */                                                   OptionTag _jspx_th_html_005foption_005f63 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5670 */                                                   _jspx_th_html_005foption_005f63.setPageContext(_jspx_page_context);
/* 5671 */                                                   _jspx_th_html_005foption_005f63.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                                   
/* 5673 */                                                   _jspx_th_html_005foption_005f63.setValue((String)row.get(1));
/* 5674 */                                                   int _jspx_eval_html_005foption_005f63 = _jspx_th_html_005foption_005f63.doStartTag();
/* 5675 */                                                   if (_jspx_eval_html_005foption_005f63 != 0) {
/* 5676 */                                                     if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5677 */                                                       out = _jspx_page_context.pushBody();
/* 5678 */                                                       _jspx_th_html_005foption_005f63.setBodyContent((BodyContent)out);
/* 5679 */                                                       _jspx_th_html_005foption_005f63.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5682 */                                                       out.print(row.get(0));
/* 5683 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f63.doAfterBody();
/* 5684 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5687 */                                                     if (_jspx_eval_html_005foption_005f63 != 1) {
/* 5688 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5691 */                                                   if (_jspx_th_html_005foption_005f63.doEndTag() == 5) {
/* 5692 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63); return;
/*      */                                                   }
/*      */                                                   
/* 5695 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f63);
/* 5696 */                                                   out.write("\n\t\t      \t");
/* 5697 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 5698 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5699 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 5700 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5703 */                                                 if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 5704 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5707 */                                               if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 5708 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                               }
/*      */                                               
/* 5711 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 5712 */                                               out.write(32);
/* 5713 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f8.doAfterBody();
/* 5714 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5718 */                                           if (_jspx_th_logic_005fnotEmpty_005f8.doEndTag() == 5) {
/* 5719 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f8); return;
/*      */                                           }
/*      */                                           
/* 5722 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f8);
/* 5723 */                                           out.write(32);
/* 5724 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 5725 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5728 */                                         if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5729 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5732 */                                       if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 5733 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                                       }
/*      */                                       
/* 5736 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f5);
/* 5737 */                                       out.write("\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td width=\"25%\" height=\"28\" valign=\"middle\" class=\"bodytext\">");
/* 5738 */                                       out.print(FormatUtil.getString("it360.sp.customermgmt.site.txt", new String[] { MGSTR }));
/* 5739 */                                       out.write("<span class=\"mandatory\">*</span></td>\n\t\t\t<td height=\"28\" align=\"left\" >\n\t\t\t\t");
/*      */                                       
/* 5741 */                                       SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5742 */                                       _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 5743 */                                       _jspx_th_html_005fselect_005f6.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5745 */                                       _jspx_th_html_005fselect_005f6.setProperty("haid");
/*      */                                       
/* 5747 */                                       _jspx_th_html_005fselect_005f6.setStyleClass("formtext");
/*      */                                       
/* 5749 */                                       _jspx_th_html_005fselect_005f6.setOnchange("javascript:check()");
/* 5750 */                                       int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 5751 */                                       if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 5752 */                                         if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 5753 */                                           out = _jspx_page_context.pushBody();
/* 5754 */                                           _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 5755 */                                           _jspx_th_html_005fselect_005f6.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5758 */                                           out.write("\n\t\t\t\t      ");
/*      */                                           
/* 5760 */                                           OptionTag _jspx_th_html_005foption_005f64 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5761 */                                           _jspx_th_html_005foption_005f64.setPageContext(_jspx_page_context);
/* 5762 */                                           _jspx_th_html_005foption_005f64.setParent(_jspx_th_html_005fselect_005f6);
/*      */                                           
/* 5764 */                                           _jspx_th_html_005foption_005f64.setValue("-");
/* 5765 */                                           int _jspx_eval_html_005foption_005f64 = _jspx_th_html_005foption_005f64.doStartTag();
/* 5766 */                                           if (_jspx_eval_html_005foption_005f64 != 0) {
/* 5767 */                                             if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5768 */                                               out = _jspx_page_context.pushBody();
/* 5769 */                                               _jspx_th_html_005foption_005f64.setBodyContent((BodyContent)out);
/* 5770 */                                               _jspx_th_html_005foption_005f64.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5773 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.siteGroup", new String[] { MGSTR }));
/* 5774 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f64.doAfterBody();
/* 5775 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5778 */                                             if (_jspx_eval_html_005foption_005f64 != 1) {
/* 5779 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5782 */                                           if (_jspx_th_html_005foption_005f64.doEndTag() == 5) {
/* 5783 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64); return;
/*      */                                           }
/*      */                                           
/* 5786 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f64);
/* 5787 */                                           out.write("\n\t\t\t\t      ");
/*      */                                           
/* 5789 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f9 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5790 */                                           _jspx_th_logic_005fnotEmpty_005f9.setPageContext(_jspx_page_context);
/* 5791 */                                           _jspx_th_logic_005fnotEmpty_005f9.setParent(_jspx_th_html_005fselect_005f6);
/*      */                                           
/* 5793 */                                           _jspx_th_logic_005fnotEmpty_005f9.setName("applications");
/* 5794 */                                           int _jspx_eval_logic_005fnotEmpty_005f9 = _jspx_th_logic_005fnotEmpty_005f9.doStartTag();
/* 5795 */                                           if (_jspx_eval_logic_005fnotEmpty_005f9 != 0) {
/*      */                                             for (;;) {
/* 5797 */                                               out.write(32);
/*      */                                               
/* 5799 */                                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5800 */                                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5801 */                                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f9);
/*      */                                               
/* 5803 */                                               _jspx_th_logic_005fiterate_005f1.setName("applications");
/*      */                                               
/* 5805 */                                               _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                               
/* 5807 */                                               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                               
/* 5809 */                                               _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 5810 */                                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5811 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5812 */                                                 ArrayList row = null;
/* 5813 */                                                 Integer j = null;
/* 5814 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5815 */                                                   out = _jspx_page_context.pushBody();
/* 5816 */                                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5817 */                                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                                 }
/* 5819 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5820 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 5822 */                                                   out.write("\n\t\t\t\t      ");
/*      */                                                   
/* 5824 */                                                   OptionTag _jspx_th_html_005foption_005f65 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5825 */                                                   _jspx_th_html_005foption_005f65.setPageContext(_jspx_page_context);
/* 5826 */                                                   _jspx_th_html_005foption_005f65.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                   
/* 5828 */                                                   _jspx_th_html_005foption_005f65.setValue((String)row.get(1));
/* 5829 */                                                   int _jspx_eval_html_005foption_005f65 = _jspx_th_html_005foption_005f65.doStartTag();
/* 5830 */                                                   if (_jspx_eval_html_005foption_005f65 != 0) {
/* 5831 */                                                     if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5832 */                                                       out = _jspx_page_context.pushBody();
/* 5833 */                                                       _jspx_th_html_005foption_005f65.setBodyContent((BodyContent)out);
/* 5834 */                                                       _jspx_th_html_005foption_005f65.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5837 */                                                       out.print(row.get(0));
/* 5838 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f65.doAfterBody();
/* 5839 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5842 */                                                     if (_jspx_eval_html_005foption_005f65 != 1) {
/* 5843 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 5846 */                                                   if (_jspx_th_html_005foption_005f65.doEndTag() == 5) {
/* 5847 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65); return;
/*      */                                                   }
/*      */                                                   
/* 5850 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f65);
/* 5851 */                                                   out.write("\n\t\t\t\t      ");
/* 5852 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5853 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5854 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 5855 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5858 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5859 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5862 */                                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5863 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                               }
/*      */                                               
/* 5866 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5867 */                                               out.write(32);
/* 5868 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f9.doAfterBody();
/* 5869 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 5873 */                                           if (_jspx_th_logic_005fnotEmpty_005f9.doEndTag() == 5) {
/* 5874 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f9); return;
/*      */                                           }
/*      */                                           
/* 5877 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f9);
/* 5878 */                                           out.write(" \n\t\t\t\t");
/* 5879 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 5880 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5883 */                                         if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 5884 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5887 */                                       if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 5888 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6); return;
/*      */                                       }
/*      */                                       
/* 5891 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f6);
/* 5892 */                                       out.write("\n      \t\t</td>\n      \t</tr>\n  ");
/*      */                                     } else {
/* 5894 */                                       out.write("\n\t\t<tr height=\"35\">\n\t    <td width=\"28%\" style=\"padding-left:10px;\" class=\"bodytext\">");
/* 5895 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.monitorgroupselect", new String[] { MGSTR }));
/* 5896 */                                       out.write("</td>\n\t    <td  align=\"left\" width=\"75%\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tr height=\"35\">\n\t\t\t\t\t<td width=\"25%\" style=\"padding-left:15px;\" >\n\t\t\t\t\t\t");
/*      */                                       
/* 5898 */                                       SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 5899 */                                       _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 5900 */                                       _jspx_th_html_005fselect_005f7.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 5902 */                                       _jspx_th_html_005fselect_005f7.setProperty("haid");
/*      */                                       
/* 5904 */                                       _jspx_th_html_005fselect_005f7.setStyleClass("formtext");
/*      */                                       
/* 5906 */                                       _jspx_th_html_005fselect_005f7.setOnchange("javascript:check()");
/* 5907 */                                       int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 5908 */                                       if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 5909 */                                         if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5910 */                                           out = _jspx_page_context.pushBody();
/* 5911 */                                           _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 5912 */                                           _jspx_th_html_005fselect_005f7.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 5915 */                                           out.write("\n\t\t\t\t      \t\t");
/*      */                                           
/* 5917 */                                           OptionTag _jspx_th_html_005foption_005f66 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5918 */                                           _jspx_th_html_005foption_005f66.setPageContext(_jspx_page_context);
/* 5919 */                                           _jspx_th_html_005foption_005f66.setParent(_jspx_th_html_005fselect_005f7);
/*      */                                           
/* 5921 */                                           _jspx_th_html_005foption_005f66.setValue("-");
/* 5922 */                                           int _jspx_eval_html_005foption_005f66 = _jspx_th_html_005foption_005f66.doStartTag();
/* 5923 */                                           if (_jspx_eval_html_005foption_005f66 != 0) {
/* 5924 */                                             if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5925 */                                               out = _jspx_page_context.pushBody();
/* 5926 */                                               _jspx_th_html_005foption_005f66.setBodyContent((BodyContent)out);
/* 5927 */                                               _jspx_th_html_005foption_005f66.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 5930 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.select.monitorgroup", new String[] { MGSTR }));
/* 5931 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f66.doAfterBody();
/* 5932 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 5935 */                                             if (_jspx_eval_html_005foption_005f66 != 1) {
/* 5936 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 5939 */                                           if (_jspx_th_html_005foption_005f66.doEndTag() == 5) {
/* 5940 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66); return;
/*      */                                           }
/*      */                                           
/* 5943 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f66);
/* 5944 */                                           out.write("\n\t\t\t\t      \t\t");
/*      */                                           
/* 5946 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f10 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5947 */                                           _jspx_th_logic_005fnotEmpty_005f10.setPageContext(_jspx_page_context);
/* 5948 */                                           _jspx_th_logic_005fnotEmpty_005f10.setParent(_jspx_th_html_005fselect_005f7);
/*      */                                           
/* 5950 */                                           _jspx_th_logic_005fnotEmpty_005f10.setName("applications");
/* 5951 */                                           int _jspx_eval_logic_005fnotEmpty_005f10 = _jspx_th_logic_005fnotEmpty_005f10.doStartTag();
/* 5952 */                                           if (_jspx_eval_logic_005fnotEmpty_005f10 != 0) {
/*      */                                             for (;;) {
/* 5954 */                                               out.write(" \n\t\t\t\t      \t\t");
/*      */                                               
/* 5956 */                                               IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5957 */                                               _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 5958 */                                               _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f10);
/*      */                                               
/* 5960 */                                               _jspx_th_logic_005fiterate_005f2.setName("applications");
/*      */                                               
/* 5962 */                                               _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                               
/* 5964 */                                               _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                                               
/* 5966 */                                               _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 5967 */                                               int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 5968 */                                               if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 5969 */                                                 ArrayList row = null;
/* 5970 */                                                 Integer j = null;
/* 5971 */                                                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 5972 */                                                   out = _jspx_page_context.pushBody();
/* 5973 */                                                   _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 5974 */                                                   _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                                 }
/* 5976 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5977 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 5979 */                                                   out.write("\n\t\t\t\t      \t\t\t");
/*      */                                                   
/* 5981 */                                                   OptionTag _jspx_th_html_005foption_005f67 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 5982 */                                                   _jspx_th_html_005foption_005f67.setPageContext(_jspx_page_context);
/* 5983 */                                                   _jspx_th_html_005foption_005f67.setParent(_jspx_th_logic_005fiterate_005f2);
/*      */                                                   
/* 5985 */                                                   _jspx_th_html_005foption_005f67.setValue((String)row.get(1));
/* 5986 */                                                   int _jspx_eval_html_005foption_005f67 = _jspx_th_html_005foption_005f67.doStartTag();
/* 5987 */                                                   if (_jspx_eval_html_005foption_005f67 != 0) {
/* 5988 */                                                     if (_jspx_eval_html_005foption_005f67 != 1) {
/* 5989 */                                                       out = _jspx_page_context.pushBody();
/* 5990 */                                                       _jspx_th_html_005foption_005f67.setBodyContent((BodyContent)out);
/* 5991 */                                                       _jspx_th_html_005foption_005f67.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 5994 */                                                       out.print(row.get(0));
/* 5995 */                                                       int evalDoAfterBody = _jspx_th_html_005foption_005f67.doAfterBody();
/* 5996 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 5999 */                                                     if (_jspx_eval_html_005foption_005f67 != 1) {
/* 6000 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 6003 */                                                   if (_jspx_th_html_005foption_005f67.doEndTag() == 5) {
/* 6004 */                                                     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67); return;
/*      */                                                   }
/*      */                                                   
/* 6007 */                                                   this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f67);
/* 6008 */                                                   out.write("\n\t\t\t\t      \t\t");
/* 6009 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 6010 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 6011 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 6012 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 6015 */                                                 if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 6016 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 6019 */                                               if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 6020 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                               }
/*      */                                               
/* 6023 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 6024 */                                               out.write(" \n\t\t\t\t      \t\t");
/* 6025 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f10.doAfterBody();
/* 6026 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 6030 */                                           if (_jspx_th_logic_005fnotEmpty_005f10.doEndTag() == 5) {
/* 6031 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f10); return;
/*      */                                           }
/*      */                                           
/* 6034 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f10);
/* 6035 */                                           out.write(" \n\t\t\t\t      \t");
/* 6036 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 6037 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 6040 */                                         if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 6041 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 6044 */                                       if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 6045 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7); return;
/*      */                                       }
/*      */                                       
/* 6048 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f7);
/* 6049 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td>\n\t\t\t\t\t\t<table border=\"0\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t\t\t<div id=\"group\" width=\"15%\" nowrap=\"nowrap\" style=\"padding-left:20px;white-space: nowrap;\"><a href=\"#\" class=\"staticlinks\" onClick=\"javascript:hideDiv('group');hideDiv('createsubgroup');hideDiv('groupmessage');showDiv('creategroup');resetname('groupname'); return false;\">");
/* 6050 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.browsertitle"));
/* 6051 */                                       out.write("</a></div>\n\t\t\t\t\t\t\t\t<div id=\"subgroup\" width=\"25%\" nowrap=\"nowrap\" Style=\"Display:none; padding-left:20px;white-space: nowrap;\"><a href=\"#\" class=\"staticlinks\" onClick=\"javascript:hideDiv('subgroup');hideDiv('creategroup');hideDiv('groupmessage');showDiv('createsubgroup');resetname('subgroupname'); return false;\">");
/* 6052 */                                       out.print(FormatUtil.getString("am.webclient.monitorsubgroupfirst.browsertitle"));
/* 6053 */                                       out.write("</a></div>\n\t\t\t\t\t\t\t\t<div id=\"creategroup\" style=\"display:none;padding-left:20px;\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 6054 */                                       out.print(FormatUtil.getString("webclient.map.mapsymboldetails.groupname"));
/* 6055 */                                       out.write(":</span>\n\t\t\t\t\t\t\t\t\t<input name=\"groupname\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons\" value=\"");
/* 6056 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 6057 */                                       out.write("\" onClick=\"javascript:createGroup();\"> <input name=\"cancel\" type=\"button\" class=\"buttons\" value=\"");
/* 6058 */                                       out.print(FormatUtil.getString("Cancel"));
/* 6059 */                                       out.write("\" onClick=\"javascript:hideDiv('creategroup'); showDiv('group'); return false;\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<div id=\"createsubgroup\" style=\"display:none;padding-left:20px;\">\n\t\t\t\t\t\t\t\t\t<span class=\"bodytext\">");
/* 6060 */                                       out.print(FormatUtil.getString("webclient.map.mapsymboldetails.subgroupname"));
/* 6061 */                                       out.write(":</span>\n\t\t\t\t\t\t\t\t\t<input name=\"subgroupname\" type=\"text\" class=\"formtext\">\n\t\t\t\t\t\t\t\t\t<input name=\"Create\" type=\"button\" class=\"buttons\" value=\"");
/* 6062 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.create"));
/* 6063 */                                       out.write("\" onClick=\"javascript:createsubGroup();\"> <input name=\"cancel\" type=\"button\" class=\"buttons\" value=\"");
/* 6064 */                                       out.print(FormatUtil.getString("Cancel"));
/* 6065 */                                       out.write("\" onClick=\"javascript:hideDiv('createsubgroup'); showDiv('subgroup'); return false;\">\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td>\n\t\t\t\t\t\t\t\t<div id=\"groupmessage\" style=\"display:block; padding-left:20px;\" class='error-text'></div>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n ");
/*      */                                     }
/* 6067 */                                     out.write("\n</table>\n\n ");
/* 6068 */                                     if (EnterpriseUtil.isIt360MSPEdition()) {
/* 6069 */                                       out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrbborder\">\n\t<tr>\n\t<td width=\"27%\" height=\"22\" >&nbsp;</td>\n\t<td width=\"73%\" height=\"26\" >\n\t<input type=\"button\" class=\"buttons\" name=\"add\"  value=\"");
/* 6070 */                                       out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.addurl"));
/* 6071 */                                       out.write("\" onClick=\"javascript:fnAddSeq()\" >\n\t<input type=\"button\" class=\"buttons\" name=\"add\"  value=\"");
/* 6072 */                                       out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 6073 */                                       out.write("\"  onclick='javascript:history.back()' id=\"cancelButtonMod\">\n\t");
/*      */                                       
/* 6075 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f11 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6076 */                                       _jspx_th_logic_005fnotEmpty_005f11.setPageContext(_jspx_page_context);
/* 6077 */                                       _jspx_th_logic_005fnotEmpty_005f11.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6079 */                                       _jspx_th_logic_005fnotEmpty_005f11.setName("UrlForm");
/*      */                                       
/* 6081 */                                       _jspx_th_logic_005fnotEmpty_005f11.setProperty("userseqid");
/* 6082 */                                       int _jspx_eval_logic_005fnotEmpty_005f11 = _jspx_th_logic_005fnotEmpty_005f11.doStartTag();
/* 6083 */                                       if (_jspx_eval_logic_005fnotEmpty_005f11 != 0) {
/*      */                                         for (;;) {
/* 6085 */                                           out.write("\n\t<input type=\"hidden\" name=\"finish\" value=\"false\">\n\n\t<input type=\"button\" class=\"buttons\" name=\"btn\" value=\"");
/* 6086 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.addandfinish"));
/* 6087 */                                           out.write("\" onClick=\"javascript:fnsubmit()\">\n\n\t");
/*      */                                           
/* 6089 */                                           IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6090 */                                           _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 6091 */                                           _jspx_th_c_005fif_005f7.setParent(_jspx_th_logic_005fnotEmpty_005f11);
/*      */                                           
/* 6093 */                                           _jspx_th_c_005fif_005f7.setTest("${empty param.wiz}");
/* 6094 */                                           int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 6095 */                                           if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                             for (;;) {
/* 6097 */                                               out.write("\n\t<input type=\"button\" class=\"buttons\" name=\"btn2\" value=\"");
/* 6098 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.finish"));
/* 6099 */                                               out.write("\" onClick=\"javascript:fnFinish()\">\n\t");
/* 6100 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 6101 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 6105 */                                           if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 6106 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                           }
/*      */                                           
/* 6109 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 6110 */                                           out.write(10);
/* 6111 */                                           out.write(9);
/*      */                                           
/* 6113 */                                           IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6114 */                                           _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 6115 */                                           _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fnotEmpty_005f11);
/*      */                                           
/* 6117 */                                           _jspx_th_c_005fif_005f8.setTest("${!empty param.wiz}");
/* 6118 */                                           int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 6119 */                                           if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                             for (;;) {
/* 6121 */                                               out.write("\n\t<input type=\"button\" class=\"buttons\" name=\"btn2\" value=\"");
/* 6122 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.finish"));
/* 6123 */                                               out.write("\" onClick=\"javascript:fnFinish()\">\n\t");
/* 6124 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 6125 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 6129 */                                           if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 6130 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                           }
/*      */                                           
/* 6133 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 6134 */                                           out.write(10);
/* 6135 */                                           out.write(10);
/* 6136 */                                           out.write(9);
/* 6137 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f11.doAfterBody();
/* 6138 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 6142 */                                       if (_jspx_th_logic_005fnotEmpty_005f11.doEndTag() == 5) {
/* 6143 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f11); return;
/*      */                                       }
/*      */                                       
/* 6146 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f11);
/* 6147 */                                       out.write("\n\n\t   </td>\n\t</tr>\n</table>\n\n ");
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 6151 */                                       out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"26%\" height=\"22\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"73%\" height=\"26\" class=\"tablebottom\"> ");
/*      */                                       
/* 6153 */                                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6154 */                                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 6155 */                                       _jspx_th_c_005fif_005f9.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6157 */                                       _jspx_th_c_005fif_005f9.setTest("${empty method}");
/* 6158 */                                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 6159 */                                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                         for (;;) {
/* 6161 */                                           out.write("\n    <input type=\"button\" class=\"buttons\" value=\"");
/* 6162 */                                           out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.addurl"));
/* 6163 */                                           out.write("\" onClick=\"javascript:fnAddSeq()\">\n    <input type=\"button\" class=\"buttons\" value=\"");
/* 6164 */                                           out.print(FormatUtil.getString("webclient.common.date.cancelbutton.text"));
/* 6165 */                                           out.write("\" onClick=\"javascript:history.back()\" id=\"cancelButtonMod\">\n    ");
/*      */                                           
/* 6167 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f12 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6168 */                                           _jspx_th_logic_005fnotEmpty_005f12.setPageContext(_jspx_page_context);
/* 6169 */                                           _jspx_th_logic_005fnotEmpty_005f12.setParent(_jspx_th_c_005fif_005f9);
/*      */                                           
/* 6171 */                                           _jspx_th_logic_005fnotEmpty_005f12.setName("UrlForm");
/*      */                                           
/* 6173 */                                           _jspx_th_logic_005fnotEmpty_005f12.setProperty("userseqid");
/* 6174 */                                           int _jspx_eval_logic_005fnotEmpty_005f12 = _jspx_th_logic_005fnotEmpty_005f12.doStartTag();
/* 6175 */                                           if (_jspx_eval_logic_005fnotEmpty_005f12 != 0) {
/*      */                                             for (;;) {
/* 6177 */                                               out.write("\n    <input type=\"hidden\" name=\"finish\" value=\"false\">\n    <input type=\"button\" class=\"buttons\" name=\"btn\" value=\"");
/* 6178 */                                               out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.addandfinish"));
/* 6179 */                                               out.write("\" onClick=\"javascript:fnsubmit()\">\n    ");
/* 6180 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f12.doAfterBody();
/* 6181 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 6185 */                                           if (_jspx_th_logic_005fnotEmpty_005f12.doEndTag() == 5) {
/* 6186 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f12); return;
/*      */                                           }
/*      */                                           
/* 6189 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f12);
/* 6190 */                                           out.write("\n      ");
/* 6191 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 6192 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 6196 */                                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 6197 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                       }
/*      */                                       
/* 6200 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 6201 */                                       out.write("\n      ");
/*      */                                       
/* 6203 */                                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6204 */                                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 6205 */                                       _jspx_th_c_005fif_005f10.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6207 */                                       _jspx_th_c_005fif_005f10.setTest("${!empty method}");
/* 6208 */                                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 6209 */                                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                         for (;;) {
/* 6211 */                                           out.write(32);
/*      */                                           
/* 6213 */                                           SubmitTag _jspx_th_html_005fsubmit_005f0 = (SubmitTag)this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fnobody.get(SubmitTag.class);
/* 6214 */                                           _jspx_th_html_005fsubmit_005f0.setPageContext(_jspx_page_context);
/* 6215 */                                           _jspx_th_html_005fsubmit_005f0.setParent(_jspx_th_c_005fif_005f10);
/*      */                                           
/* 6217 */                                           _jspx_th_html_005fsubmit_005f0.setStyleClass("buttons");
/*      */                                           
/* 6219 */                                           _jspx_th_html_005fsubmit_005f0.setValue(FormatUtil.getString("am.webclient.common.update.text"));
/* 6220 */                                           int _jspx_eval_html_005fsubmit_005f0 = _jspx_th_html_005fsubmit_005f0.doStartTag();
/* 6221 */                                           if (_jspx_th_html_005fsubmit_005f0.doEndTag() == 5) {
/* 6222 */                                             this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005fsubmit_005f0); return;
/*      */                                           }
/*      */                                           
/* 6225 */                                           this._005fjspx_005ftagPool_005fhtml_005fsubmit_0026_005fvalue_005fstyleClass_005fnobody.reuse(_jspx_th_html_005fsubmit_005f0);
/* 6226 */                                           out.write("\n      ");
/* 6227 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 6228 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 6232 */                                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 6233 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                                       }
/*      */                                       
/* 6236 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 6237 */                                       out.write(" \n      ");
/*      */                                       
/* 6239 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f13 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 6240 */                                       _jspx_th_logic_005fnotEmpty_005f13.setPageContext(_jspx_page_context);
/* 6241 */                                       _jspx_th_logic_005fnotEmpty_005f13.setParent(_jspx_th_html_005fform_005f0);
/*      */                                       
/* 6243 */                                       _jspx_th_logic_005fnotEmpty_005f13.setName("UrlForm");
/*      */                                       
/* 6245 */                                       _jspx_th_logic_005fnotEmpty_005f13.setProperty("userseqid");
/* 6246 */                                       int _jspx_eval_logic_005fnotEmpty_005f13 = _jspx_th_logic_005fnotEmpty_005f13.doStartTag();
/* 6247 */                                       if (_jspx_eval_logic_005fnotEmpty_005f13 != 0) {
/*      */                                         for (;;) {
/* 6249 */                                           out.write("\n      ");
/*      */                                           
/* 6251 */                                           IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6252 */                                           _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 6253 */                                           _jspx_th_c_005fif_005f11.setParent(_jspx_th_logic_005fnotEmpty_005f13);
/*      */                                           
/* 6255 */                                           _jspx_th_c_005fif_005f11.setTest("${empty param.wiz}");
/* 6256 */                                           int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 6257 */                                           if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                             for (;;) {
/* 6259 */                                               out.write("\n\t\t<input type=\"button\" class=\"buttons\" name=\"btn2\" value=\"");
/* 6260 */                                               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.finish"));
/* 6261 */                                               out.write("\" onClick=\"javascript:fnFinish()\">\n\t  ");
/* 6262 */                                               int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 6263 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 6267 */                                           if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 6268 */                                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                           }
/*      */                                           
/* 6271 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 6272 */                                           out.write("\n\t  ");
/* 6273 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f13.doAfterBody();
/* 6274 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 6278 */                                       if (_jspx_th_logic_005fnotEmpty_005f13.doEndTag() == 5) {
/* 6279 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f13); return;
/*      */                                       }
/*      */                                       
/* 6282 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f13);
/* 6283 */                                       out.write("\n      </td>\n  </tr>\n</table>\n");
/*      */                                     }
/*      */                                     
/* 6286 */                                     out.write(32);
/* 6287 */                                     out.write(10);
/* 6288 */                                     if (_jspx_meth_c_005fif_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6290 */                                     out.write("\n        ");
/* 6291 */                                     if (_jspx_meth_c_005fif_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 6293 */                                     out.write(10);
/* 6294 */                                     out.write(10);
/* 6295 */                                     out.write(10);
/* 6296 */                                     int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 6297 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 6301 */                                 if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 6302 */                                   this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                                 }
/*      */                                 
/* 6305 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 6306 */                                 out.write(10);
/*      */                                 
/* 6308 */                                 IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6309 */                                 _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 6310 */                                 _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                                 
/* 6312 */                                 _jspx_th_c_005fif_005f14.setTest("${!empty requestScope.parsedhtml}");
/* 6313 */                                 int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 6314 */                                 if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                   for (;;) {
/* 6316 */                                     out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n<tr>\n<td class=\"tablebottom\">\n<a name=\"htmlcontent\"><span class=\"bodytextbold\">");
/* 6317 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.webcontent"));
/* 6318 */                                     out.write("</span></a>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrbborder\">\n<tr>\n<td>\n<table width=\"100%\">\n        <tr>\n<td>\n<span class=\"bodytext\">\n<TEXTAREA NAME=\"\" cols=\"100\" rows=\"20\">\n");
/* 6319 */                                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f14, _jspx_page_context))
/*      */                                       return;
/* 6321 */                                     out.write("\n</textAREA>\n</span>\n</td>\n</tr>\n</table >\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"21%\" height=\"22\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"79%\" height=\"26\" class=\"tablebottom\" align=\"right\"> <a href=\"#top\" class=\"staticlinks\">");
/* 6322 */                                     out.print(FormatUtil.getString("am.webclient.hostdiscovery.urlseq.top"));
/* 6323 */                                     out.write("</a> </td>\n  </tr>\n</table>\n");
/* 6324 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 6325 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 6329 */                                 if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 6330 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                 }
/*      */                                 
/* 6333 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 6334 */                                 out.write("\n</td>\n<td width=\"30%\" valign=\"top\">\n");
/* 6335 */                                 StringBuffer helpcardKey = new StringBuffer();
/* 6336 */                                 if (title.equals("UrlSeq")) {
/* 6337 */                                   helpcardKey.append(FormatUtil.getString("quicknote.urlconf.firstpage"));
/* 6338 */                                   helpcardKey.append(FormatUtil.getString("quicknote.urlconf.nextpage") + "<br><br>");
/* 6339 */                                   helpcardKey.append(FormatUtil.getString("am.webclient.hostdiscovery.Urlseq.helpcard"));
/*      */                                   
/* 6341 */                                   out.write(10);
/* 6342 */                                   JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(helpcardKey.toString()), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 6343 */                                   out.write("\n\t\t\t\n");
/*      */                                 }
/* 6345 */                                 out.write("\n</td>\n        </tr>\n        </table>\n");
/*      */ 
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 6352 */                                 out.write("<!--  \n<TABLE width=\"99%\" border=\"0\" cellpadding=\"0\" CELLSPACING=\"0\" CELLPADDING=\"0\">\n  <tr>\n    <td align=\"center\">\n    ");
/* 6353 */                                 if (EnterpriseUtil.isIt360MSPEdition())
/*      */                                 {
/* 6355 */                                   out.write("\n      <input name=\"closeButton\" type=\"button\" class=\"buttons\" value=\"");
/* 6356 */                                   out.print(FormatUtil.getString("Close Window"));
/* 6357 */                                   out.write("\" onClick=\"closePopUpWindow();\">\n      ");
/*      */                                 }
/*      */                                 else {
/* 6360 */                                   out.write("\n\t  <input type=\"button\" align=\"center\" value=\"");
/* 6361 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.Urlseq.addNewSeq"));
/* 6362 */                                   out.write("\" class=\"buttons\" onclick=\"javascript: newSequence();\">\n\t  ");
/*      */                                 }
/* 6364 */                                 out.write("\n      </td>\n      </tr>\n      </table>-->\n");
/*      */                               }
/* 6366 */                               out.write(10);
/* 6367 */                               out.write(9);
/* 6368 */                               JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.hostdiscovery.Urlseq.helpcard")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("hideStyle", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hideStyle), request.getCharacterEncoding()), out, false);
/* 6369 */                               out.write(10);
/* 6370 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 6371 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 6374 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 6375 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 6378 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 6379 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 6382 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 6383 */                           out.write(10);
/* 6384 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 6386 */                           out.write(10);
/* 6387 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 6388 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 6392 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 6393 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 6396 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 6397 */                         out.write(10);
/* 6398 */                         out.write(10);
/* 6399 */                         out.write(10);
/*      */                         
/* 6401 */                         if (hideFields)
/*      */                         {
/*      */ 
/* 6404 */                           out.write("\n\t<script>\n\t\t$(document.body).ready(function(){\n\t\tdocument.getElementById(\"cancelButtonMod\").onclick = null;\n\t\t$(\"#cancelButtonMod\").click(function(){ //No I18N\n\t\t\tclosePopUpWindow();\n\t\t});\n\t\t});\n\t</script>\n");
/*      */                         }
/*      */                         
/*      */ 
/* 6408 */                         out.write("\n\n\n<!--  Your area ends here -->\n");
/*      */                       }
/* 6410 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 6411 */         out = _jspx_out;
/* 6412 */         if ((out != null) && (out.getBufferSize() != 0))
/* 6413 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 6414 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 6417 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6423 */     PageContext pageContext = _jspx_page_context;
/* 6424 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6426 */     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6427 */     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 6428 */     _jspx_th_c_005fforEach_005f0.setParent(null);
/*      */     
/* 6430 */     _jspx_th_c_005fforEach_005f0.setItems("${dynamicSites}");
/*      */     
/* 6432 */     _jspx_th_c_005fforEach_005f0.setVar("a");
/*      */     
/* 6434 */     _jspx_th_c_005fforEach_005f0.setVarStatus("rowCounter");
/* 6435 */     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */     try {
/* 6437 */       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 6438 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */         for (;;) {
/* 6440 */           out.write(10);
/* 6441 */           out.write(9);
/* 6442 */           out.write(9);
/* 6443 */           if (_jspx_meth_c_005fforEach_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6444 */             return true;
/* 6445 */           out.write("\n\t\tif(formCustomerId == customerId)\n\t\t{\n\t\t\tdocument.UrlForm.haid.options[rowCount++] = new Option(siteName,siteId);\n\t\t}\n\t");
/* 6446 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 6447 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6451 */       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/* 6452 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6455 */         int tmp206_205 = 0; int[] tmp206_203 = _jspx_push_body_count_c_005fforEach_005f0; int tmp208_207 = tmp206_203[tmp206_205];tmp206_203[tmp206_205] = (tmp208_207 - 1); if (tmp208_207 <= 0) break;
/* 6456 */         out = _jspx_page_context.popBody(); }
/* 6457 */       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6459 */       _jspx_th_c_005fforEach_005f0.doFinally();
/* 6460 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */     }
/* 6462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fforEach_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6467 */     PageContext pageContext = _jspx_page_context;
/* 6468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6470 */     ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 6471 */     _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 6472 */     _jspx_th_c_005fforEach_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6474 */     _jspx_th_c_005fforEach_005f1.setItems("${a}");
/*      */     
/* 6476 */     _jspx_th_c_005fforEach_005f1.setVar("b");
/*      */     
/* 6478 */     _jspx_th_c_005fforEach_005f1.setVarStatus("rowCounter1");
/* 6479 */     int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */     try {
/* 6481 */       int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 6482 */       int evalDoAfterBody; if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */         for (;;) {
/* 6484 */           out.write("\n\t\t\t");
/* 6485 */           boolean bool; if (_jspx_meth_c_005fif_005f0(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6486 */             return true;
/* 6487 */           out.write("\n\t\t\t");
/* 6488 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6489 */             return true;
/* 6490 */           out.write("\n\t\t\t");
/* 6491 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6492 */             return true;
/* 6493 */           out.write(10);
/* 6494 */           out.write(9);
/* 6495 */           out.write(9);
/* 6496 */           evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 6497 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6501 */       if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/* 6502 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6505 */         int tmp295_294 = 0; int[] tmp295_292 = _jspx_push_body_count_c_005fforEach_005f1; int tmp297_296 = tmp295_292[tmp295_294];tmp295_292[tmp295_294] = (tmp297_296 - 1); if (tmp297_296 <= 0) break;
/* 6506 */         out = _jspx_page_context.popBody(); }
/* 6507 */       _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */     } finally {
/* 6509 */       _jspx_th_c_005fforEach_005f1.doFinally();
/* 6510 */       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */     }
/* 6512 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6517 */     PageContext pageContext = _jspx_page_context;
/* 6518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6520 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6521 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6522 */     _jspx_th_c_005fif_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6524 */     _jspx_th_c_005fif_005f0.setTest("${rowCounter1.count == 1}");
/* 6525 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6526 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6528 */         out.write("\n\t\t\t\tsiteName = '");
/* 6529 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6530 */           return true;
/* 6531 */         out.write("';\n\t\t\t");
/* 6532 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6533 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6537 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6538 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6539 */       return true;
/*      */     }
/* 6541 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6547 */     PageContext pageContext = _jspx_page_context;
/* 6548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6550 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6551 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6552 */     _jspx_th_c_005fout_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6554 */     _jspx_th_c_005fout_005f0.setValue("${b}");
/* 6555 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6556 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6557 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6558 */       return true;
/*      */     }
/* 6560 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6561 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6566 */     PageContext pageContext = _jspx_page_context;
/* 6567 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6569 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6570 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 6571 */     _jspx_th_c_005fif_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6573 */     _jspx_th_c_005fif_005f1.setTest("${rowCounter1.count == 2}");
/* 6574 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 6575 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 6577 */         out.write("\n\t\t\t\tsiteId = '");
/* 6578 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6579 */           return true;
/* 6580 */         out.write("';\n\t\t\t");
/* 6581 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 6582 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6586 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 6587 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6588 */       return true;
/*      */     }
/* 6590 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 6591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6596 */     PageContext pageContext = _jspx_page_context;
/* 6597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6599 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6600 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6601 */     _jspx_th_c_005fout_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 6603 */     _jspx_th_c_005fout_005f1.setValue("${b}");
/* 6604 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6605 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6606 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6607 */       return true;
/*      */     }
/* 6609 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6610 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6615 */     PageContext pageContext = _jspx_page_context;
/* 6616 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6618 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6619 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 6620 */     _jspx_th_c_005fif_005f2.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6622 */     _jspx_th_c_005fif_005f2.setTest("${rowCounter1.count == 3}");
/* 6623 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 6624 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 6626 */         out.write("\n\t\t\t\tcustomerId = '");
/* 6627 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6628 */           return true;
/* 6629 */         out.write("';\n\t\t\t");
/* 6630 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 6631 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6635 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 6636 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6637 */       return true;
/*      */     }
/* 6639 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6640 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6645 */     PageContext pageContext = _jspx_page_context;
/* 6646 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6648 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6649 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6650 */     _jspx_th_c_005fout_005f2.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6652 */     _jspx_th_c_005fout_005f2.setValue("${b}");
/* 6653 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6654 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6655 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6656 */       return true;
/*      */     }
/* 6658 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6659 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6664 */     PageContext pageContext = _jspx_page_context;
/* 6665 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6667 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6668 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 6669 */     _jspx_th_tiles_005fput_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6671 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 6673 */     _jspx_th_tiles_005fput_005f0.setValue("URL Sequence Monitoring");
/* 6674 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 6675 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 6676 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 6677 */       return true;
/*      */     }
/* 6679 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 6680 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6685 */     PageContext pageContext = _jspx_page_context;
/* 6686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6688 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 6689 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 6690 */     _jspx_th_tiles_005fput_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 6692 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 6694 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 6695 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 6696 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 6697 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6698 */       return true;
/*      */     }
/* 6700 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 6701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6706 */     PageContext pageContext = _jspx_page_context;
/* 6707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6709 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 6710 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 6711 */     _jspx_th_c_005fcatch_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 6713 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 6714 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 6716 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 6717 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 6719 */           out.write("\n      ");
/* 6720 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 6721 */             return true;
/* 6722 */           out.write(10);
/* 6723 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 6724 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 6728 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 6729 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 6732 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 6733 */         out = _jspx_page_context.popBody(); }
/* 6734 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 6736 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 6737 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 6739 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 6744 */     PageContext pageContext = _jspx_page_context;
/* 6745 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6747 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 6748 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 6749 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 6751 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 6753 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 6754 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 6755 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 6756 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6757 */       return true;
/*      */     }
/* 6759 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 6760 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6765 */     PageContext pageContext = _jspx_page_context;
/* 6766 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6768 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6769 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6770 */     _jspx_th_c_005fif_005f5.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6772 */     _jspx_th_c_005fif_005f5.setTest("${!empty param.wiz ||  !empty param.fromAssociate}");
/* 6773 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6774 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6776 */         out.write("\n      ");
/* 6777 */         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6778 */           return true;
/* 6779 */         out.write("\n      ");
/* 6780 */         if (_jspx_meth_html_005fhidden_005f0(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6781 */           return true;
/* 6782 */         out.write("\n      ");
/* 6783 */         if (_jspx_meth_html_005fhidden_005f1(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6784 */           return true;
/* 6785 */         out.write("\n      ");
/* 6786 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6787 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6791 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6792 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6793 */       return true;
/*      */     }
/* 6795 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6801 */     PageContext pageContext = _jspx_page_context;
/* 6802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6804 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6805 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 6806 */     _jspx_th_c_005fchoose_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f5);
/* 6807 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 6808 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 6810 */         out.write("\n        ");
/* 6811 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6812 */           return true;
/* 6813 */         out.write("\n        ");
/* 6814 */         if (_jspx_meth_c_005fwhen_005f1(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6815 */           return true;
/* 6816 */         out.write("\n\n        ");
/* 6817 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 6818 */           return true;
/* 6819 */         out.write("\n      ");
/* 6820 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 6821 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6825 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 6826 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6827 */       return true;
/*      */     }
/* 6829 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 6830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6835 */     PageContext pageContext = _jspx_page_context;
/* 6836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6838 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6839 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 6840 */     _jspx_th_c_005fwhen_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6842 */     _jspx_th_c_005fwhen_005f0.setTest("${param.type=='WTA'}");
/* 6843 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 6844 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 6846 */         out.write("\n          ");
/* 6847 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f0, _jspx_page_context))
/* 6848 */           return true;
/* 6849 */         out.write("\n        ");
/* 6850 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 6851 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6855 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 6856 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6857 */       return true;
/*      */     }
/* 6859 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 6860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6865 */     PageContext pageContext = _jspx_page_context;
/* 6866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6868 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6869 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6870 */     _jspx_th_c_005fout_005f3.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 6872 */     _jspx_th_c_005fout_005f3.setValue("Web Transaction Monitor");
/* 6873 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6874 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6875 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6876 */       return true;
/*      */     }
/* 6878 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6879 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f1(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6884 */     PageContext pageContext = _jspx_page_context;
/* 6885 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6887 */     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6888 */     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 6889 */     _jspx_th_c_005fwhen_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 6891 */     _jspx_th_c_005fwhen_005f1.setTest("${param.type=='.Net'}");
/* 6892 */     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 6893 */     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */       for (;;) {
/* 6895 */         out.write("\n          ");
/* 6896 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f1, _jspx_page_context))
/* 6897 */           return true;
/* 6898 */         out.write("\n        ");
/* 6899 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 6900 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6904 */     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 6905 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6906 */       return true;
/*      */     }
/* 6908 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 6909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6914 */     PageContext pageContext = _jspx_page_context;
/* 6915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6917 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6918 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6919 */     _jspx_th_c_005fout_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fwhen_005f1);
/*      */     
/* 6921 */     _jspx_th_c_005fout_005f4.setValue("Tomcat Server");
/* 6922 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6923 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6924 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6925 */       return true;
/*      */     }
/* 6927 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6933 */     PageContext pageContext = _jspx_page_context;
/* 6934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6936 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 6937 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 6938 */     _jspx_th_c_005fotherwise_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fchoose_005f0);
/* 6939 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 6940 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 6942 */         out.write("\n         ");
/* 6943 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/* 6944 */           return true;
/* 6945 */         out.write("\n        ");
/* 6946 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 6947 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6951 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 6952 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6953 */       return true;
/*      */     }
/* 6955 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 6956 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6961 */     PageContext pageContext = _jspx_page_context;
/* 6962 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6964 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.get(OutTag.class);
/* 6965 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6966 */     _jspx_th_c_005fout_005f5.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 6968 */     _jspx_th_c_005fout_005f5.setValue("${param.type}");
/* 6969 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6970 */     if (_jspx_eval_c_005fout_005f5 != 0) {
/* 6971 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 6972 */         out = _jspx_page_context.pushBody();
/* 6973 */         _jspx_th_c_005fout_005f5.setBodyContent((BodyContent)out);
/* 6974 */         _jspx_th_c_005fout_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6977 */         out.write(45);
/* 6978 */         int evalDoAfterBody = _jspx_th_c_005fout_005f5.doAfterBody();
/* 6979 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6982 */       if (_jspx_eval_c_005fout_005f5 != 1) {
/* 6983 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6986 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6987 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6988 */       return true;
/*      */     }
/* 6990 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue.reuse(_jspx_th_c_005fout_005f5);
/* 6991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6996 */     PageContext pageContext = _jspx_page_context;
/* 6997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6999 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7000 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 7001 */     _jspx_th_html_005fhidden_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 7003 */     _jspx_th_html_005fhidden_005f0.setProperty("type");
/* 7004 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 7005 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 7006 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 7007 */       return true;
/*      */     }
/* 7009 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 7010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7015 */     PageContext pageContext = _jspx_page_context;
/* 7016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7018 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7019 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 7020 */     _jspx_th_html_005fhidden_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 7022 */     _jspx_th_html_005fhidden_005f1.setProperty("haid");
/* 7023 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 7024 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 7025 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 7026 */       return true;
/*      */     }
/* 7028 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 7029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7034 */     PageContext pageContext = _jspx_page_context;
/* 7035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7037 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 7038 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 7039 */     _jspx_th_am_005fhiddenparam_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7041 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 7042 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 7043 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 7044 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 7045 */       return true;
/*      */     }
/* 7047 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 7048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7053 */     PageContext pageContext = _jspx_page_context;
/* 7054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7056 */     HiddenTag _jspx_th_html_005fhidden_005f2 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7057 */     _jspx_th_html_005fhidden_005f2.setPageContext(_jspx_page_context);
/* 7058 */     _jspx_th_html_005fhidden_005f2.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f0);
/*      */     
/* 7060 */     _jspx_th_html_005fhidden_005f2.setProperty("userseqid");
/* 7061 */     int _jspx_eval_html_005fhidden_005f2 = _jspx_th_html_005fhidden_005f2.doStartTag();
/* 7062 */     if (_jspx_th_html_005fhidden_005f2.doEndTag() == 5) {
/* 7063 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 7064 */       return true;
/*      */     }
/* 7066 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f2);
/* 7067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_logic_005fempty_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7072 */     PageContext pageContext = _jspx_page_context;
/* 7073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7075 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7076 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 7077 */     _jspx_th_html_005ftext_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fempty_005f2);
/*      */     
/* 7079 */     _jspx_th_html_005ftext_005f0.setProperty("userseqname");
/*      */     
/* 7081 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext");
/*      */     
/* 7083 */     _jspx_th_html_005ftext_005f0.setSize("25");
/* 7084 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 7085 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 7086 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 7087 */       return true;
/*      */     }
/* 7089 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 7090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7095 */     PageContext pageContext = _jspx_page_context;
/* 7096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7098 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 7099 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 7100 */     _jspx_th_bean_005fwrite_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 7102 */     _jspx_th_bean_005fwrite_005f0.setName("UrlForm");
/*      */     
/* 7104 */     _jspx_th_bean_005fwrite_005f0.setProperty("url");
/* 7105 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 7106 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 7107 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 7108 */       return true;
/*      */     }
/* 7110 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 7111 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7116 */     PageContext pageContext = _jspx_page_context;
/* 7117 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7119 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 7120 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 7121 */     _jspx_th_bean_005fwrite_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 7123 */     _jspx_th_bean_005fwrite_005f1.setName("UrlForm");
/*      */     
/* 7125 */     _jspx_th_bean_005fwrite_005f1.setProperty("url");
/* 7126 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 7127 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 7128 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 7129 */       return true;
/*      */     }
/* 7131 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 7132 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f3(JspTag _jspx_th_logic_005fnotEmpty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7137 */     PageContext pageContext = _jspx_page_context;
/* 7138 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7140 */     HiddenTag _jspx_th_html_005fhidden_005f3 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7141 */     _jspx_th_html_005fhidden_005f3.setPageContext(_jspx_page_context);
/* 7142 */     _jspx_th_html_005fhidden_005f3.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f1);
/*      */     
/* 7144 */     _jspx_th_html_005fhidden_005f3.setProperty("userseqid");
/* 7145 */     int _jspx_eval_html_005fhidden_005f3 = _jspx_th_html_005fhidden_005f3.doStartTag();
/* 7146 */     if (_jspx_th_html_005fhidden_005f3.doEndTag() == 5) {
/* 7147 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 7148 */       return true;
/*      */     }
/* 7150 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f3);
/* 7151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_logic_005fempty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7156 */     PageContext pageContext = _jspx_page_context;
/* 7157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7159 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7160 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 7161 */     _jspx_th_html_005ftext_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fempty_005f3);
/*      */     
/* 7163 */     _jspx_th_html_005ftext_005f1.setProperty("url");
/*      */     
/* 7165 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/*      */     
/* 7167 */     _jspx_th_html_005ftext_005f1.setSize("25");
/* 7168 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 7169 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 7170 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 7171 */       return true;
/*      */     }
/* 7173 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 7174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f3(JspTag _jspx_th_html_005fselect_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7179 */     PageContext pageContext = _jspx_page_context;
/* 7180 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7182 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 7183 */     _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 7184 */     _jspx_th_logic_005fnotEmpty_005f3.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fselect_005f1);
/*      */     
/* 7186 */     _jspx_th_logic_005fnotEmpty_005f3.setName("availableurls");
/* 7187 */     int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 7188 */     if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */       for (;;) {
/* 7190 */         out.write("\n\t          ");
/* 7191 */         if (_jspx_meth_html_005foptionsCollection_005f0(_jspx_th_logic_005fnotEmpty_005f3, _jspx_page_context))
/* 7192 */           return true;
/* 7193 */         out.write("\n\t          ");
/* 7194 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 7195 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7199 */     if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 7200 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 7201 */       return true;
/*      */     }
/* 7203 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 7204 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f0(JspTag _jspx_th_logic_005fnotEmpty_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7209 */     PageContext pageContext = _jspx_page_context;
/* 7210 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7212 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f0 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 7213 */     _jspx_th_html_005foptionsCollection_005f0.setPageContext(_jspx_page_context);
/* 7214 */     _jspx_th_html_005foptionsCollection_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f3);
/*      */     
/* 7216 */     _jspx_th_html_005foptionsCollection_005f0.setName("availableurls");
/* 7217 */     int _jspx_eval_html_005foptionsCollection_005f0 = _jspx_th_html_005foptionsCollection_005f0.doStartTag();
/* 7218 */     if (_jspx_th_html_005foptionsCollection_005f0.doEndTag() == 5) {
/* 7219 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 7220 */       return true;
/*      */     }
/* 7222 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f0);
/* 7223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f4(JspTag _jspx_th_html_005fselect_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7228 */     PageContext pageContext = _jspx_page_context;
/* 7229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7231 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 7232 */     _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 7233 */     _jspx_th_logic_005fnotEmpty_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fselect_005f2);
/*      */     
/* 7235 */     _jspx_th_logic_005fnotEmpty_005f4.setName("availableforms");
/* 7236 */     int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 7237 */     if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */       for (;;) {
/* 7239 */         out.write(10);
/* 7240 */         out.write(9);
/* 7241 */         out.write(9);
/* 7242 */         if (_jspx_meth_html_005foptionsCollection_005f1(_jspx_th_logic_005fnotEmpty_005f4, _jspx_page_context))
/* 7243 */           return true;
/* 7244 */         out.write(10);
/* 7245 */         out.write(9);
/* 7246 */         out.write(9);
/* 7247 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 7248 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7252 */     if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 7253 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 7254 */       return true;
/*      */     }
/* 7256 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 7257 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f1(JspTag _jspx_th_logic_005fnotEmpty_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7262 */     PageContext pageContext = _jspx_page_context;
/* 7263 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7265 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f1 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 7266 */     _jspx_th_html_005foptionsCollection_005f1.setPageContext(_jspx_page_context);
/* 7267 */     _jspx_th_html_005foptionsCollection_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f4);
/*      */     
/* 7269 */     _jspx_th_html_005foptionsCollection_005f1.setName("availableforms");
/* 7270 */     int _jspx_eval_html_005foptionsCollection_005f1 = _jspx_th_html_005foptionsCollection_005f1.doStartTag();
/* 7271 */     if (_jspx_th_html_005foptionsCollection_005f1.doEndTag() == 5) {
/* 7272 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 7273 */       return true;
/*      */     }
/* 7275 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f1);
/* 7276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f6(JspTag _jspx_th_html_005fselect_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7281 */     PageContext pageContext = _jspx_page_context;
/* 7282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7284 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f6 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 7285 */     _jspx_th_logic_005fnotEmpty_005f6.setPageContext(_jspx_page_context);
/* 7286 */     _jspx_th_logic_005fnotEmpty_005f6.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fselect_005f3);
/*      */     
/* 7288 */     _jspx_th_logic_005fnotEmpty_005f6.setName("availableframes");
/* 7289 */     int _jspx_eval_logic_005fnotEmpty_005f6 = _jspx_th_logic_005fnotEmpty_005f6.doStartTag();
/* 7290 */     if (_jspx_eval_logic_005fnotEmpty_005f6 != 0) {
/*      */       for (;;) {
/* 7292 */         out.write(10);
/* 7293 */         out.write(9);
/* 7294 */         out.write(9);
/* 7295 */         if (_jspx_meth_html_005foptionsCollection_005f2(_jspx_th_logic_005fnotEmpty_005f6, _jspx_page_context))
/* 7296 */           return true;
/* 7297 */         out.write(10);
/* 7298 */         out.write(9);
/* 7299 */         out.write(9);
/* 7300 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f6.doAfterBody();
/* 7301 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7305 */     if (_jspx_th_logic_005fnotEmpty_005f6.doEndTag() == 5) {
/* 7306 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 7307 */       return true;
/*      */     }
/* 7309 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f6);
/* 7310 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f2(JspTag _jspx_th_logic_005fnotEmpty_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7315 */     PageContext pageContext = _jspx_page_context;
/* 7316 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7318 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f2 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 7319 */     _jspx_th_html_005foptionsCollection_005f2.setPageContext(_jspx_page_context);
/* 7320 */     _jspx_th_html_005foptionsCollection_005f2.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f6);
/*      */     
/* 7322 */     _jspx_th_html_005foptionsCollection_005f2.setName("availableframes");
/* 7323 */     int _jspx_eval_html_005foptionsCollection_005f2 = _jspx_th_html_005foptionsCollection_005f2.doStartTag();
/* 7324 */     if (_jspx_th_html_005foptionsCollection_005f2.doEndTag() == 5) {
/* 7325 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 7326 */       return true;
/*      */     }
/* 7328 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f2);
/* 7329 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7334 */     PageContext pageContext = _jspx_page_context;
/* 7335 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7337 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7338 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 7339 */     _jspx_th_html_005ftext_005f2.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7341 */     _jspx_th_html_005ftext_005f2.setProperty("checkfor");
/*      */     
/* 7343 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/*      */     
/* 7345 */     _jspx_th_html_005ftext_005f2.setSize("25");
/* 7346 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 7347 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 7348 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 7349 */       return true;
/*      */     }
/* 7351 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 7352 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fempty_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7357 */     PageContext pageContext = _jspx_page_context;
/* 7358 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7360 */     EmptyTag _jspx_th_logic_005fempty_005f7 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.get(EmptyTag.class);
/* 7361 */     _jspx_th_logic_005fempty_005f7.setPageContext(_jspx_page_context);
/* 7362 */     _jspx_th_logic_005fempty_005f7.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7364 */     _jspx_th_logic_005fempty_005f7.setName("UrlForm");
/*      */     
/* 7366 */     _jspx_th_logic_005fempty_005f7.setProperty("userseqid");
/* 7367 */     int _jspx_eval_logic_005fempty_005f7 = _jspx_th_logic_005fempty_005f7.doStartTag();
/* 7368 */     if (_jspx_eval_logic_005fempty_005f7 != 0) {
/*      */       for (;;) {
/* 7370 */         out.write("\n    ");
/* 7371 */         if (_jspx_meth_html_005ftext_005f3(_jspx_th_logic_005fempty_005f7, _jspx_page_context))
/* 7372 */           return true;
/* 7373 */         out.write("\n    ");
/* 7374 */         int evalDoAfterBody = _jspx_th_logic_005fempty_005f7.doAfterBody();
/* 7375 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7379 */     if (_jspx_th_logic_005fempty_005f7.doEndTag() == 5) {
/* 7380 */       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f7);
/* 7381 */       return true;
/*      */     }
/* 7383 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fempty_005f7);
/* 7384 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_logic_005fempty_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7389 */     PageContext pageContext = _jspx_page_context;
/* 7390 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7392 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7393 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 7394 */     _jspx_th_html_005ftext_005f3.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fempty_005f7);
/*      */     
/* 7396 */     _jspx_th_html_005ftext_005f3.setProperty("pollInterval");
/*      */     
/* 7398 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/*      */     
/* 7400 */     _jspx_th_html_005ftext_005f3.setSize("25");
/* 7401 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 7402 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 7403 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 7404 */       return true;
/*      */     }
/* 7406 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 7407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotEmpty_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7412 */     PageContext pageContext = _jspx_page_context;
/* 7413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7415 */     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f7 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.get(NotEmptyTag.class);
/* 7416 */     _jspx_th_logic_005fnotEmpty_005f7.setPageContext(_jspx_page_context);
/* 7417 */     _jspx_th_logic_005fnotEmpty_005f7.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7419 */     _jspx_th_logic_005fnotEmpty_005f7.setName("UrlForm");
/*      */     
/* 7421 */     _jspx_th_logic_005fnotEmpty_005f7.setProperty("userseqid");
/* 7422 */     int _jspx_eval_logic_005fnotEmpty_005f7 = _jspx_th_logic_005fnotEmpty_005f7.doStartTag();
/* 7423 */     if (_jspx_eval_logic_005fnotEmpty_005f7 != 0) {
/*      */       for (;;) {
/* 7425 */         out.write("\n           ");
/* 7426 */         if (_jspx_meth_html_005fhidden_005f4(_jspx_th_logic_005fnotEmpty_005f7, _jspx_page_context))
/* 7427 */           return true;
/* 7428 */         out.write("\n       ");
/* 7429 */         if (_jspx_meth_html_005ftext_005f4(_jspx_th_logic_005fnotEmpty_005f7, _jspx_page_context))
/* 7430 */           return true;
/* 7431 */         out.write("\n    ");
/* 7432 */         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f7.doAfterBody();
/* 7433 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7437 */     if (_jspx_th_logic_005fnotEmpty_005f7.doEndTag() == 5) {
/* 7438 */       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 7439 */       return true;
/*      */     }
/* 7441 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fproperty_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f7);
/* 7442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7447 */     PageContext pageContext = _jspx_page_context;
/* 7448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7450 */     HiddenTag _jspx_th_html_005fhidden_005f4 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.get(HiddenTag.class);
/* 7451 */     _jspx_th_html_005fhidden_005f4.setPageContext(_jspx_page_context);
/* 7452 */     _jspx_th_html_005fhidden_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f7);
/*      */     
/* 7454 */     _jspx_th_html_005fhidden_005f4.setProperty("pollInterval");
/* 7455 */     int _jspx_eval_html_005fhidden_005f4 = _jspx_th_html_005fhidden_005f4.doStartTag();
/* 7456 */     if (_jspx_th_html_005fhidden_005f4.doEndTag() == 5) {
/* 7457 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 7458 */       return true;
/*      */     }
/* 7460 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f4);
/* 7461 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_logic_005fnotEmpty_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7466 */     PageContext pageContext = _jspx_page_context;
/* 7467 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7469 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.get(TextTag.class);
/* 7470 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 7471 */     _jspx_th_html_005ftext_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_logic_005fnotEmpty_005f7);
/*      */     
/* 7473 */     _jspx_th_html_005ftext_005f4.setProperty("pollInterval");
/*      */     
/* 7475 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/*      */     
/* 7477 */     _jspx_th_html_005ftext_005f4.setSize("25");
/*      */     
/* 7479 */     _jspx_th_html_005ftext_005f4.setDisabled(true);
/* 7480 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 7481 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 7482 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 7483 */       return true;
/*      */     }
/* 7485 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fdisabled_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 7486 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7491 */     PageContext pageContext = _jspx_page_context;
/* 7492 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7494 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7495 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 7496 */     _jspx_th_html_005fradio_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7498 */     _jspx_th_html_005fradio_005f0.setProperty("method");
/*      */     
/* 7500 */     _jspx_th_html_005fradio_005f0.setValue("P");
/* 7501 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 7502 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 7503 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 7504 */       return true;
/*      */     }
/* 7506 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 7507 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7512 */     PageContext pageContext = _jspx_page_context;
/* 7513 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7515 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7516 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 7517 */     _jspx_th_html_005fradio_005f1.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7519 */     _jspx_th_html_005fradio_005f1.setProperty("method");
/*      */     
/* 7521 */     _jspx_th_html_005fradio_005f1.setValue("G");
/* 7522 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 7523 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 7524 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 7525 */       return true;
/*      */     }
/* 7527 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 7528 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7533 */     PageContext pageContext = _jspx_page_context;
/* 7534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7536 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7537 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 7538 */     _jspx_th_html_005ftext_005f5.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7540 */     _jspx_th_html_005ftext_005f5.setProperty("errorcontent");
/*      */     
/* 7542 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/*      */     
/* 7544 */     _jspx_th_html_005ftext_005f5.setSize("25");
/* 7545 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 7546 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 7547 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 7548 */       return true;
/*      */     }
/* 7550 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 7551 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fselect_005f4(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7556 */     PageContext pageContext = _jspx_page_context;
/* 7557 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7559 */     SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty.get(SelectTag.class);
/* 7560 */     _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 7561 */     _jspx_th_html_005fselect_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7563 */     _jspx_th_html_005fselect_005f4.setProperty("httpcondition");
/*      */     
/* 7565 */     _jspx_th_html_005fselect_005f4.setStyle("width:50px");
/* 7566 */     int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 7567 */     if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 7568 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 7569 */         out = _jspx_page_context.pushBody();
/* 7570 */         _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 7571 */         _jspx_th_html_005fselect_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7574 */         out.write("\n      ");
/* 7575 */         if (_jspx_meth_html_005foptionsCollection_005f3(_jspx_th_html_005fselect_005f4, _jspx_page_context))
/* 7576 */           return true;
/* 7577 */         out.write(32);
/* 7578 */         int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 7579 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7582 */       if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 7583 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7586 */     if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 7587 */       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 7588 */       return true;
/*      */     }
/* 7590 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyle_005fproperty.reuse(_jspx_th_html_005fselect_005f4);
/* 7591 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005foptionsCollection_005f3(JspTag _jspx_th_html_005fselect_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7596 */     PageContext pageContext = _jspx_page_context;
/* 7597 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7599 */     OptionsCollectionTag _jspx_th_html_005foptionsCollection_005f3 = (OptionsCollectionTag)this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.get(OptionsCollectionTag.class);
/* 7600 */     _jspx_th_html_005foptionsCollection_005f3.setPageContext(_jspx_page_context);
/* 7601 */     _jspx_th_html_005foptionsCollection_005f3.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fselect_005f4);
/*      */     
/* 7603 */     _jspx_th_html_005foptionsCollection_005f3.setName("AMActionForm");
/*      */     
/* 7605 */     _jspx_th_html_005foptionsCollection_005f3.setProperty("conditions");
/* 7606 */     int _jspx_eval_html_005foptionsCollection_005f3 = _jspx_th_html_005foptionsCollection_005f3.doStartTag();
/* 7607 */     if (_jspx_th_html_005foptionsCollection_005f3.doEndTag() == 5) {
/* 7608 */       this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 7609 */       return true;
/*      */     }
/* 7611 */     this._005fjspx_005ftagPool_005fhtml_005foptionsCollection_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_html_005foptionsCollection_005f3);
/* 7612 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7617 */     PageContext pageContext = _jspx_page_context;
/* 7618 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7620 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7621 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 7622 */     _jspx_th_html_005ftext_005f6.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7624 */     _jspx_th_html_005ftext_005f6.setProperty("httpvalue");
/*      */     
/* 7626 */     _jspx_th_html_005ftext_005f6.setSize("15");
/*      */     
/* 7628 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/* 7629 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 7630 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 7631 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 7632 */       return true;
/*      */     }
/* 7634 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 7635 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7640 */     PageContext pageContext = _jspx_page_context;
/* 7641 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7643 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7644 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 7645 */     _jspx_th_html_005ftext_005f7.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7647 */     _jspx_th_html_005ftext_005f7.setProperty("timeout");
/*      */     
/* 7649 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/*      */     
/* 7651 */     _jspx_th_html_005ftext_005f7.setSize("25");
/* 7652 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 7653 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 7654 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 7655 */       return true;
/*      */     }
/* 7657 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 7658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fmultibox_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7663 */     PageContext pageContext = _jspx_page_context;
/* 7664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7666 */     MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.get(MultiboxTag.class);
/* 7667 */     _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 7668 */     _jspx_th_html_005fmultibox_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7670 */     _jspx_th_html_005fmultibox_005f0.setProperty("verifyerror");
/*      */     
/* 7672 */     _jspx_th_html_005fmultibox_005f0.setValue("true");
/* 7673 */     int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 7674 */     if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 7675 */       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 7676 */       return true;
/*      */     }
/* 7678 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fmultibox_005f0);
/* 7679 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7684 */     PageContext pageContext = _jspx_page_context;
/* 7685 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7687 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7688 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 7689 */     _jspx_th_html_005ftext_005f8.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7691 */     _jspx_th_html_005ftext_005f8.setProperty("userid");
/*      */     
/* 7693 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/*      */     
/* 7695 */     _jspx_th_html_005ftext_005f8.setSize("25");
/* 7696 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 7697 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 7698 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 7699 */       return true;
/*      */     }
/* 7701 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 7702 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fpassword_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7707 */     PageContext pageContext = _jspx_page_context;
/* 7708 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7710 */     PasswordTag _jspx_th_html_005fpassword_005f0 = (PasswordTag)this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(PasswordTag.class);
/* 7711 */     _jspx_th_html_005fpassword_005f0.setPageContext(_jspx_page_context);
/* 7712 */     _jspx_th_html_005fpassword_005f0.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7714 */     _jspx_th_html_005fpassword_005f0.setProperty("password");
/*      */     
/* 7716 */     _jspx_th_html_005fpassword_005f0.setStyleClass("formtext");
/*      */     
/* 7718 */     _jspx_th_html_005fpassword_005f0.setSize("25");
/* 7719 */     int _jspx_eval_html_005fpassword_005f0 = _jspx_th_html_005fpassword_005f0.doStartTag();
/* 7720 */     if (_jspx_th_html_005fpassword_005f0.doEndTag() == 5) {
/* 7721 */       this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 7722 */       return true;
/*      */     }
/* 7724 */     this._005fjspx_005ftagPool_005fhtml_005fpassword_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005fpassword_005f0);
/* 7725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7730 */     PageContext pageContext = _jspx_page_context;
/* 7731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7733 */     IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7734 */     _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 7735 */     _jspx_th_c_005fif_005f12.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7737 */     _jspx_th_c_005fif_005f12.setTest("${!empty param.haid && empty invalidhaid}");
/* 7738 */     int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 7739 */     if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */       for (;;) {
/* 7741 */         out.write("\n\t<input type=\"hidden\" name=\"haid\"  value='");
/* 7742 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f12, _jspx_page_context))
/* 7743 */           return true;
/* 7744 */         out.write("'>\n\t");
/* 7745 */         int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 7746 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7750 */     if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 7751 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 7752 */       return true;
/*      */     }
/* 7754 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 7755 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f12, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7760 */     PageContext pageContext = _jspx_page_context;
/* 7761 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7763 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7764 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 7765 */     _jspx_th_c_005fout_005f6.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f12);
/*      */     
/* 7767 */     _jspx_th_c_005fout_005f6.setValue("${param.haid}");
/* 7768 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 7769 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 7770 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7771 */       return true;
/*      */     }
/* 7773 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 7774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7779 */     PageContext pageContext = _jspx_page_context;
/* 7780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7782 */     IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7783 */     _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 7784 */     _jspx_th_c_005fif_005f13.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7786 */     _jspx_th_c_005fif_005f13.setTest("${!empty param.name}");
/* 7787 */     int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 7788 */     if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */       for (;;) {
/* 7790 */         out.write("\n        <input type=\"hidden\" name=\"name\"  value='");
/* 7791 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f13, _jspx_page_context))
/* 7792 */           return true;
/* 7793 */         out.write("'>\n        ");
/* 7794 */         int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 7795 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7799 */     if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 7800 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 7801 */       return true;
/*      */     }
/* 7803 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 7804 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f13, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7809 */     PageContext pageContext = _jspx_page_context;
/* 7810 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7812 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7813 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 7814 */     _jspx_th_c_005fout_005f7.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f13);
/*      */     
/* 7816 */     _jspx_th_c_005fout_005f7.setValue("${param.name}");
/* 7817 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 7818 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 7819 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7820 */       return true;
/*      */     }
/* 7822 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 7823 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7828 */     PageContext pageContext = _jspx_page_context;
/* 7829 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7831 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.get(OutTag.class);
/* 7832 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 7833 */     _jspx_th_c_005fout_005f8.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_c_005fif_005f14);
/*      */     
/* 7835 */     _jspx_th_c_005fout_005f8.setValue("${requestScope.parsedhtml.htmlContent}");
/*      */     
/* 7837 */     _jspx_th_c_005fout_005f8.setEscapeXml("false");
/* 7838 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 7839 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 7840 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7841 */       return true;
/*      */     }
/* 7843 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fescapeXml_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 7844 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7849 */     PageContext pageContext = _jspx_page_context;
/* 7850 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7852 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7853 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 7854 */     _jspx_th_tiles_005fput_005f4.setParent((javax.servlet.jsp.tagext.Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7856 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 7858 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 7859 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 7860 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 7861 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 7862 */       return true;
/*      */     }
/* 7864 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 7865 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\urlseq_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */