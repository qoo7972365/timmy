/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
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
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.RadioTag;
/*      */ import org.apache.struts.taglib.html.ResetTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.html.TextareaTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class Scriptconf_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   54 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   57 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   58 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   59 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   66 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   71 */     ArrayList list = null;
/*   72 */     StringBuffer sbf = new StringBuffer();
/*   73 */     ManagedApplication mo = new ManagedApplication();
/*   74 */     if (distinct)
/*      */     {
/*   76 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   80 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   83 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   85 */       ArrayList row = (ArrayList)list.get(i);
/*   86 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   87 */       if (distinct) {
/*   88 */         sbf.append(row.get(0));
/*      */       } else
/*   90 */         sbf.append(row.get(1));
/*   91 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   94 */     return sbf.toString(); }
/*      */   
/*   96 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   99 */     if (severity == null)
/*      */     {
/*  101 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  103 */     if (severity.equals("5"))
/*      */     {
/*  105 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  107 */     if (severity.equals("1"))
/*      */     {
/*  109 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  114 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  121 */     if (severity == null)
/*      */     {
/*  123 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  125 */     if (severity.equals("1"))
/*      */     {
/*  127 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  129 */     if (severity.equals("4"))
/*      */     {
/*  131 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  133 */     if (severity.equals("5"))
/*      */     {
/*  135 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  140 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  146 */     if (severity == null)
/*      */     {
/*  148 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  150 */     if (severity.equals("5"))
/*      */     {
/*  152 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  154 */     if (severity.equals("1"))
/*      */     {
/*  156 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  160 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  166 */     if (severity == null)
/*      */     {
/*  168 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  170 */     if (severity.equals("1"))
/*      */     {
/*  172 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  174 */     if (severity.equals("4"))
/*      */     {
/*  176 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  178 */     if (severity.equals("5"))
/*      */     {
/*  180 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  184 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  190 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  196 */     if (severity == 5)
/*      */     {
/*  198 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  200 */     if (severity == 1)
/*      */     {
/*  202 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  207 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  213 */     if (severity == null)
/*      */     {
/*  215 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  217 */     if (severity.equals("5"))
/*      */     {
/*  219 */       if (isAvailability) {
/*  220 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  223 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  226 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  228 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  230 */     if (severity.equals("1"))
/*      */     {
/*  232 */       if (isAvailability) {
/*  233 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  236 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  243 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  250 */     if (severity == null)
/*      */     {
/*  252 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  254 */     if (severity.equals("5"))
/*      */     {
/*  256 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  258 */     if (severity.equals("4"))
/*      */     {
/*  260 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  262 */     if (severity.equals("1"))
/*      */     {
/*  264 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  269 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  275 */     if (severity == null)
/*      */     {
/*  277 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  279 */     if (severity.equals("5"))
/*      */     {
/*  281 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  283 */     if (severity.equals("4"))
/*      */     {
/*  285 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  287 */     if (severity.equals("1"))
/*      */     {
/*  289 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  294 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  301 */     if (severity == null)
/*      */     {
/*  303 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  305 */     if (severity.equals("5"))
/*      */     {
/*  307 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  309 */     if (severity.equals("4"))
/*      */     {
/*  311 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  313 */     if (severity.equals("1"))
/*      */     {
/*  315 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  320 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  328 */     StringBuffer out = new StringBuffer();
/*  329 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  330 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  331 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  332 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  333 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  334 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  335 */     out.append("</tr>");
/*  336 */     out.append("</form></table>");
/*  337 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  344 */     if (val == null)
/*      */     {
/*  346 */       return "-";
/*      */     }
/*      */     
/*  349 */     String ret = FormatUtil.formatNumber(val);
/*  350 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  351 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  354 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  358 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  366 */     StringBuffer out = new StringBuffer();
/*  367 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  368 */     out.append("<tr>");
/*  369 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  371 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  373 */     out.append("</tr>");
/*  374 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  378 */       if (j % 2 == 0)
/*      */       {
/*  380 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  384 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  387 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  389 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  392 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  396 */       out.append("</tr>");
/*      */     }
/*  398 */     out.append("</table>");
/*  399 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  400 */     out.append("<tr>");
/*  401 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  402 */     out.append("</tr>");
/*  403 */     out.append("</table>");
/*  404 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
/*      */   {
/*  410 */     StringBuffer out = new StringBuffer();
/*  411 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  412 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  413 */     out.append("<tr>");
/*  414 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  417 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  418 */     out.append("</tr>");
/*  419 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  422 */       out.append("<tr>");
/*  423 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  424 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  425 */       out.append("</tr>");
/*      */     }
/*      */     
/*  428 */     out.append("</table>");
/*  429 */     out.append("</table>");
/*  430 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  435 */     if (severity.equals("0"))
/*      */     {
/*  437 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  441 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  448 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  461 */     StringBuffer out = new StringBuffer();
/*  462 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  463 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  465 */       out.append("<tr>");
/*  466 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  467 */       out.append("</tr>");
/*      */       
/*      */ 
/*  470 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  472 */         String borderclass = "";
/*      */         
/*      */ 
/*  475 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  477 */         out.append("<tr>");
/*      */         
/*  479 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  480 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  481 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  487 */     out.append("</table><br>");
/*  488 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  489 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  491 */       List sLinks = secondLevelOfLinks[0];
/*  492 */       List sText = secondLevelOfLinks[1];
/*  493 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  496 */         out.append("<tr>");
/*  497 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  498 */         out.append("</tr>");
/*  499 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  501 */           String borderclass = "";
/*      */           
/*      */ 
/*  504 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  506 */           out.append("<tr>");
/*      */           
/*  508 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  509 */           if (sLinks.get(i).toString().length() == 0) {
/*  510 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  513 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  515 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  519 */     out.append("</table>");
/*  520 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  527 */     StringBuffer out = new StringBuffer();
/*  528 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  529 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  531 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  533 */         out.append("<tr>");
/*  534 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  535 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  539 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  541 */           String borderclass = "";
/*      */           
/*      */ 
/*  544 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  546 */           out.append("<tr>");
/*      */           
/*  548 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  549 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  550 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  553 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  556 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  561 */     out.append("</table><br>");
/*  562 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  563 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  565 */       List sLinks = secondLevelOfLinks[0];
/*  566 */       List sText = secondLevelOfLinks[1];
/*  567 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  570 */         out.append("<tr>");
/*  571 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  572 */         out.append("</tr>");
/*  573 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  575 */           String borderclass = "";
/*      */           
/*      */ 
/*  578 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  580 */           out.append("<tr>");
/*      */           
/*  582 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  583 */           if (sLinks.get(i).toString().length() == 0) {
/*  584 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  587 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  589 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  593 */     out.append("</table>");
/*  594 */     return out.toString();
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
/*  607 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  622 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  628 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  636 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  641 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  646 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  651 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  656 */     if (val != null)
/*      */     {
/*  658 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  662 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  667 */     if (val == null) {
/*  668 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  672 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  677 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  683 */     if (val != null)
/*      */     {
/*  685 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  689 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  695 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  700 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  704 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  709 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  714 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  719 */     String hostaddress = "";
/*  720 */     String ip = request.getHeader("x-forwarded-for");
/*  721 */     if (ip == null)
/*  722 */       ip = request.getRemoteAddr();
/*  723 */     InetAddress add = null;
/*  724 */     if (ip.equals("127.0.0.1")) {
/*  725 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  729 */       add = InetAddress.getByName(ip);
/*      */     }
/*  731 */     hostaddress = add.getHostName();
/*  732 */     if (hostaddress.indexOf('.') != -1) {
/*  733 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  734 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  738 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  743 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  749 */     if (severity == null)
/*      */     {
/*  751 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  753 */     if (severity.equals("5"))
/*      */     {
/*  755 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  757 */     if (severity.equals("1"))
/*      */     {
/*  759 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  764 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  769 */     ResultSet set = null;
/*  770 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  771 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  773 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  774 */       if (set.next()) { String str1;
/*  775 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  776 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  779 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  784 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  787 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  789 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  793 */     StringBuffer rca = new StringBuffer();
/*  794 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  795 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  798 */     int rcalength = key.length();
/*  799 */     String split = "6. ";
/*  800 */     int splitPresent = key.indexOf(split);
/*  801 */     String div1 = "";String div2 = "";
/*  802 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  804 */       if (rcalength > 180) {
/*  805 */         rca.append("<span class=\"rca-critical-text\">");
/*  806 */         getRCATrimmedText(key, rca);
/*  807 */         rca.append("</span>");
/*      */       } else {
/*  809 */         rca.append("<span class=\"rca-critical-text\">");
/*  810 */         rca.append(key);
/*  811 */         rca.append("</span>");
/*      */       }
/*  813 */       return rca.toString();
/*      */     }
/*  815 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  816 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  817 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  818 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  819 */     getRCATrimmedText(div1, rca);
/*  820 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  823 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  824 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  825 */     getRCATrimmedText(div2, rca);
/*  826 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  828 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  833 */     String[] st = msg.split("<br>");
/*  834 */     for (int i = 0; i < st.length; i++) {
/*  835 */       String s = st[i];
/*  836 */       if (s.length() > 180) {
/*  837 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  839 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  843 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  844 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  846 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  850 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  851 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  852 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  855 */       if (key == null) {
/*  856 */         return ret;
/*      */       }
/*      */       
/*  859 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  860 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  863 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  864 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  865 */       set = AMConnectionPool.executeQueryStmt(query);
/*  866 */       if (set.next())
/*      */       {
/*  868 */         String helpLink = set.getString("LINK");
/*  869 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  872 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  878 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  897 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  888 */         if (set != null) {
/*  889 */           AMConnectionPool.closeStatement(set);
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
/*  903 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  904 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  906 */       String entityStr = (String)keys.nextElement();
/*  907 */       String mmessage = temp.getProperty(entityStr);
/*  908 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  909 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  911 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  917 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  918 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  920 */       String entityStr = (String)keys.nextElement();
/*  921 */       String mmessage = temp.getProperty(entityStr);
/*  922 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  923 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  925 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  930 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  940 */     String des = new String();
/*  941 */     while (str.indexOf(find) != -1) {
/*  942 */       des = des + str.substring(0, str.indexOf(find));
/*  943 */       des = des + replace;
/*  944 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  946 */     des = des + str;
/*  947 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  954 */       if (alert == null)
/*      */       {
/*  956 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  958 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  960 */         return "&nbsp;";
/*      */       }
/*      */       
/*  963 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  965 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  968 */       int rcalength = test.length();
/*  969 */       if (rcalength < 300)
/*      */       {
/*  971 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  975 */       StringBuffer out = new StringBuffer();
/*  976 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  977 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  978 */       out.append("</div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  980 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  981 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  986 */       ex.printStackTrace();
/*      */     }
/*  988 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  994 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  999 */     ArrayList attribIDs = new ArrayList();
/* 1000 */     ArrayList resIDs = new ArrayList();
/* 1001 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1003 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1005 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1007 */       String resourceid = "";
/* 1008 */       String resourceType = "";
/* 1009 */       if (type == 2) {
/* 1010 */         resourceid = (String)row.get(0);
/* 1011 */         resourceType = (String)row.get(3);
/*      */       }
/* 1013 */       else if (type == 3) {
/* 1014 */         resourceid = (String)row.get(0);
/* 1015 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1018 */         resourceid = (String)row.get(6);
/* 1019 */         resourceType = (String)row.get(7);
/*      */       }
/* 1021 */       resIDs.add(resourceid);
/* 1022 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1023 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1025 */       String healthentity = null;
/* 1026 */       String availentity = null;
/* 1027 */       if (healthid != null) {
/* 1028 */         healthentity = resourceid + "_" + healthid;
/* 1029 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1032 */       if (availid != null) {
/* 1033 */         availentity = resourceid + "_" + availid;
/* 1034 */         entitylist.add(availentity);
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
/* 1048 */     Properties alert = getStatus(entitylist);
/* 1049 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1054 */     int size = monitorList.size();
/*      */     
/* 1056 */     String[] severity = new String[size];
/*      */     
/* 1058 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1060 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1061 */       String resourceName1 = (String)row1.get(7);
/* 1062 */       String resourceid1 = (String)row1.get(6);
/* 1063 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1064 */       if (severity[j] == null)
/*      */       {
/* 1066 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1070 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1072 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1074 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1077 */         if (sev > 0) {
/* 1078 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1079 */           monitorList.set(k, monitorList.get(j));
/* 1080 */           monitorList.set(j, t);
/* 1081 */           String temp = severity[k];
/* 1082 */           severity[k] = severity[j];
/* 1083 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1089 */     int z = 0;
/* 1090 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1093 */       int i = 0;
/* 1094 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1097 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1101 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1105 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1107 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1110 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1114 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1117 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1118 */       String resourceName1 = (String)row1.get(7);
/* 1119 */       String resourceid1 = (String)row1.get(6);
/* 1120 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1121 */       if (hseverity[j] == null)
/*      */       {
/* 1123 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1128 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1130 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1133 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1136 */         if (hsev > 0) {
/* 1137 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1138 */           monitorList.set(k, monitorList.get(j));
/* 1139 */           monitorList.set(j, t);
/* 1140 */           String temp1 = hseverity[k];
/* 1141 */           hseverity[k] = hseverity[j];
/* 1142 */           hseverity[j] = temp1;
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
/* 1154 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1155 */     boolean forInventory = false;
/* 1156 */     String trdisplay = "none";
/* 1157 */     String plusstyle = "inline";
/* 1158 */     String minusstyle = "none";
/* 1159 */     String haidTopLevel = "";
/* 1160 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1162 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1164 */         haidTopLevel = request.getParameter("haid");
/* 1165 */         forInventory = true;
/* 1166 */         trdisplay = "table-row;";
/* 1167 */         plusstyle = "none";
/* 1168 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1175 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1178 */     ArrayList listtoreturn = new ArrayList();
/* 1179 */     StringBuffer toreturn = new StringBuffer();
/* 1180 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1181 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1182 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1184 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1186 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1187 */       String childresid = (String)singlerow.get(0);
/* 1188 */       String childresname = (String)singlerow.get(1);
/* 1189 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1190 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1191 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1192 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1193 */       String unmanagestatus = (String)singlerow.get(5);
/* 1194 */       String actionstatus = (String)singlerow.get(6);
/* 1195 */       String linkclass = "monitorgp-links";
/* 1196 */       String titleforres = childresname;
/* 1197 */       String titilechildresname = childresname;
/* 1198 */       String childimg = "/images/trcont.png";
/* 1199 */       String flag = "enable";
/* 1200 */       String dcstarted = (String)singlerow.get(8);
/* 1201 */       String configMonitor = "";
/* 1202 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1203 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1205 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1207 */       if (singlerow.get(7) != null)
/*      */       {
/* 1209 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1211 */       String haiGroupType = "0";
/* 1212 */       if ("HAI".equals(childtype))
/*      */       {
/* 1214 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1216 */       childimg = "/images/trend.png";
/* 1217 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1218 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1219 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1221 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1223 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1225 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1226 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1229 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1231 */         linkclass = "disabledtext";
/* 1232 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1234 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1235 */       String availmouseover = "";
/* 1236 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1238 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1240 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1241 */       String healthmouseover = "";
/* 1242 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1244 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1247 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1248 */       int spacing = 0;
/* 1249 */       if (level >= 1)
/*      */       {
/* 1251 */         spacing = 40 * level;
/*      */       }
/* 1253 */       if (childtype.equals("HAI"))
/*      */       {
/* 1255 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1256 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1257 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1259 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1260 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1261 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1262 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1263 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1264 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1265 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1266 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1267 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1268 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1269 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1271 */         if (!forInventory)
/*      */         {
/* 1273 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1276 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1278 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1280 */           actions = editlink + actions;
/*      */         }
/* 1282 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1284 */           actions = actions + associatelink;
/*      */         }
/* 1286 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1287 */         String arrowimg = "";
/* 1288 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1290 */           actions = "";
/* 1291 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1292 */           checkbox = "";
/* 1293 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1295 */         if (isIt360)
/*      */         {
/* 1297 */           actionimg = "";
/* 1298 */           actions = "";
/* 1299 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1300 */           checkbox = "";
/*      */         }
/*      */         
/* 1303 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1305 */           actions = "";
/*      */         }
/* 1307 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1309 */           checkbox = "";
/*      */         }
/*      */         
/* 1312 */         String resourcelink = "";
/*      */         
/* 1314 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1316 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1320 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1323 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1329 */         if (!isIt360)
/*      */         {
/* 1331 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1335 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1338 */         toreturn.append("</tr>");
/* 1339 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1341 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1342 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1346 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1347 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1350 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1354 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1356 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1358 */             toreturn.append(assocMessage);
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1368 */         String resourcelink = null;
/* 1369 */         boolean hideEditLink = false;
/* 1370 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1372 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1373 */           hideEditLink = true;
/* 1374 */           if (isIt360)
/*      */           {
/* 1376 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1380 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1382 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1384 */           hideEditLink = true;
/* 1385 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1386 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1391 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1394 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1395 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1396 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1397 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1398 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1399 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1400 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1401 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1402 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1403 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1404 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1405 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1406 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1408 */         if (hideEditLink)
/*      */         {
/* 1410 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1412 */         if (!forInventory)
/*      */         {
/* 1414 */           removefromgroup = "";
/*      */         }
/* 1416 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1417 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1418 */           actions = actions + configcustomfields;
/*      */         }
/* 1420 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1422 */           actions = editlink + actions;
/*      */         }
/* 1424 */         String managedLink = "";
/* 1425 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1427 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1428 */           actions = "";
/* 1429 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1430 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1433 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1435 */           checkbox = "";
/*      */         }
/*      */         
/* 1438 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1440 */           actions = "";
/*      */         }
/* 1442 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1445 */         if (isIt360)
/*      */         {
/* 1447 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1451 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1454 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1455 */         if (!isIt360)
/*      */         {
/* 1457 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1461 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1463 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1466 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1473 */       StringBuilder toreturn = new StringBuilder();
/* 1474 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1475 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1476 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1477 */       String title = "";
/* 1478 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1479 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1480 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1481 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1483 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1485 */       else if ("5".equals(severity))
/*      */       {
/* 1487 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1491 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1493 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1494 */       toreturn.append(v);
/*      */       
/* 1496 */       toreturn.append(link);
/* 1497 */       if (severity == null)
/*      */       {
/* 1499 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1501 */       else if (severity.equals("5"))
/*      */       {
/* 1503 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1505 */       else if (severity.equals("4"))
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       else if (severity.equals("1"))
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1518 */       toreturn.append("</a>");
/* 1519 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1523 */       ex.printStackTrace();
/*      */     }
/* 1525 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1532 */       StringBuilder toreturn = new StringBuilder();
/* 1533 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1534 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1535 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1536 */       if (message == null)
/*      */       {
/* 1538 */         message = "";
/*      */       }
/*      */       
/* 1541 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1542 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1544 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1545 */       toreturn.append(v);
/*      */       
/* 1547 */       toreturn.append(link);
/*      */       
/* 1549 */       if (severity == null)
/*      */       {
/* 1551 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1553 */       else if (severity.equals("5"))
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       else if (severity.equals("1"))
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1566 */       toreturn.append("</a>");
/* 1567 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1573 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1576 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1577 */     if (invokeActions != null) {
/* 1578 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1579 */       while (iterator.hasNext()) {
/* 1580 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1581 */         if (actionmap.containsKey(actionid)) {
/* 1582 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1587 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1591 */     String actionLink = "";
/* 1592 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1593 */     String query = "";
/* 1594 */     ResultSet rs = null;
/* 1595 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1596 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1597 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1598 */       actionLink = "method=" + methodName;
/*      */     }
/* 1600 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1601 */       actionLink = methodName;
/*      */     }
/* 1603 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1604 */     Iterator itr = methodarglist.iterator();
/* 1605 */     boolean isfirstparam = true;
/* 1606 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1607 */     while (itr.hasNext()) {
/* 1608 */       HashMap argmap = (HashMap)itr.next();
/* 1609 */       String argtype = (String)argmap.get("TYPE");
/* 1610 */       String argname = (String)argmap.get("IDENTITY");
/* 1611 */       String paramname = (String)argmap.get("PARAMETER");
/* 1612 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1613 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1614 */         isfirstparam = false;
/* 1615 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1617 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1621 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1625 */         actionLink = actionLink + "&";
/*      */       }
/* 1627 */       String paramValue = null;
/* 1628 */       String tempargname = argname;
/* 1629 */       if (commonValues.getProperty(tempargname) != null) {
/* 1630 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1633 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1634 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1635 */           if (dbType.equals("mysql")) {
/* 1636 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1639 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1641 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1643 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1644 */             if (rs.next()) {
/* 1645 */               paramValue = rs.getString("VALUE");
/* 1646 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1650 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1654 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1657 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1662 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1663 */           paramValue = rowId;
/*      */         }
/* 1665 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1666 */           paramValue = managedObjectName;
/*      */         }
/* 1668 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1669 */           paramValue = resID;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1672 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1675 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1677 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1678 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1679 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1681 */     return actionLink;
/*      */   }
/*      */   
/* 1684 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1685 */     String dependentAttribute = null;
/* 1686 */     String align = "left";
/*      */     
/* 1688 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1689 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1690 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1691 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1692 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1693 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1694 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1695 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1696 */       align = "center";
/*      */     }
/*      */     
/* 1699 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1700 */     String actualdata = "";
/*      */     
/* 1702 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1703 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1704 */         actualdata = availValue;
/*      */       }
/* 1706 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1707 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1711 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1712 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1715 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1721 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1722 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1723 */       toreturn.append("<table>");
/* 1724 */       toreturn.append("<tr>");
/* 1725 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1726 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1727 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1728 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1729 */         String toolTip = "";
/* 1730 */         String hideClass = "";
/* 1731 */         String textStyle = "";
/* 1732 */         boolean isreferenced = true;
/* 1733 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1734 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1735 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1736 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1738 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1739 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1740 */           while (valueList.hasMoreTokens()) {
/* 1741 */             String dependentVal = valueList.nextToken();
/* 1742 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1743 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1744 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1746 */               toolTip = "";
/* 1747 */               hideClass = "";
/* 1748 */               isreferenced = false;
/* 1749 */               textStyle = "disabledtext";
/* 1750 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1754 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1755 */           toolTip = "";
/* 1756 */           hideClass = "";
/* 1757 */           isreferenced = false;
/* 1758 */           textStyle = "disabledtext";
/* 1759 */           if (dependentImageMap != null) {
/* 1760 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1761 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1764 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1768 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1769 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1770 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1771 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1772 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1773 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1775 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1776 */           if (isreferenced) {
/* 1777 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1781 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1782 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1783 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1784 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1785 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1786 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1788 */           toreturn.append("</span>");
/* 1789 */           toreturn.append("</a>");
/* 1790 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1793 */       toreturn.append("</tr>");
/* 1794 */       toreturn.append("</table>");
/* 1795 */       toreturn.append("</td>");
/*      */     } else {
/* 1797 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1800 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1804 */     String colTime = null;
/* 1805 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1806 */     if ((rows != null) && (rows.size() > 0)) {
/* 1807 */       Iterator<String> itr = rows.iterator();
/* 1808 */       String maxColQuery = "";
/* 1809 */       for (;;) { if (itr.hasNext()) {
/* 1810 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1811 */           ResultSet maxCol = null;
/*      */           try {
/* 1813 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1814 */             while (maxCol.next()) {
/* 1815 */               if (colTime == null) {
/* 1816 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1819 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1828 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1828 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1830 */               if (maxCol != null)
/* 1831 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1833 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1838 */     return colTime;
/*      */   }
/*      */   
/* 1841 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1842 */     tablename = null;
/* 1843 */     ResultSet rsTable = null;
/* 1844 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1846 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1847 */       while (rsTable.next()) {
/* 1848 */         tablename = rsTable.getString("DATATABLE");
/* 1849 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1850 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1863 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1854 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1857 */         if (rsTable != null)
/* 1858 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1860 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1866 */     String argsList = "";
/* 1867 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1869 */       if (showArgsMap.get(row) != null) {
/* 1870 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1871 */         if (showArgslist != null) {
/* 1872 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1873 */             if (argsList.trim().equals("")) {
/* 1874 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1877 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1884 */       e.printStackTrace();
/* 1885 */       return "";
/*      */     }
/* 1887 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1892 */     String argsList = "";
/* 1893 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1896 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1898 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1899 */         if (hideArgsList != null)
/*      */         {
/* 1901 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1903 */             if (argsList.trim().equals(""))
/*      */             {
/* 1905 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1909 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1917 */       ex.printStackTrace();
/*      */     }
/* 1919 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1923 */     StringBuilder toreturn = new StringBuilder();
/* 1924 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1931 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1932 */       Iterator itr = tActionList.iterator();
/* 1933 */       while (itr.hasNext()) {
/* 1934 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1935 */         String confirmmsg = "";
/* 1936 */         String link = "";
/* 1937 */         String isJSP = "NO";
/* 1938 */         HashMap tactionMap = (HashMap)itr.next();
/* 1939 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1940 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1941 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1942 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1943 */           (actionmap.containsKey(actionId))) {
/* 1944 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1945 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1946 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1947 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1948 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1950 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1956 */           if (isTableAction) {
/* 1957 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1960 */             tableName = "Link";
/* 1961 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1962 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1963 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1964 */             toreturn.append("</a></td>");
/*      */           }
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1975 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1981 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1983 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1984 */       Properties prop = (Properties)node.getUserObject();
/* 1985 */       String mgID = prop.getProperty("label");
/* 1986 */       String mgName = prop.getProperty("value");
/* 1987 */       String isParent = prop.getProperty("isParent");
/* 1988 */       int mgIDint = Integer.parseInt(mgID);
/* 1989 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1991 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1993 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1994 */       if (node.getChildCount() > 0)
/*      */       {
/* 1996 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1998 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2000 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2002 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2006 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2011 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2013 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2015 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2017 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2021 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2024 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2025 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2027 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2031 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2033 */       if (node.getChildCount() > 0)
/*      */       {
/* 2035 */         builder.append("<UL>");
/* 2036 */         printMGTree(node, builder);
/* 2037 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2042 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2043 */     StringBuffer toReturn = new StringBuffer();
/* 2044 */     String table = "-";
/*      */     try {
/* 2046 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2047 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2048 */       float total = 0.0F;
/* 2049 */       while (it.hasNext()) {
/* 2050 */         String attName = (String)it.next();
/* 2051 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2052 */         boolean roundOffData = false;
/* 2053 */         if ((data != null) && (!data.equals(""))) {
/* 2054 */           if (data.indexOf(",") != -1) {
/* 2055 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2058 */             float value = Float.parseFloat(data);
/* 2059 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2062 */             total += value;
/* 2063 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2066 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2071 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2072 */       while (attVsWidthList.hasNext()) {
/* 2073 */         String attName = (String)attVsWidthList.next();
/* 2074 */         String data = (String)attVsWidthProps.get(attName);
/* 2075 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2076 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2077 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2078 */         String className = (String)graphDetails.get("ClassName");
/* 2079 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2080 */         if (percentage < 1.0F)
/*      */         {
/* 2082 */           data = percentage + "";
/*      */         }
/* 2084 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2086 */       if (toReturn.length() > 0) {
/* 2087 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2091 */       e.printStackTrace();
/*      */     }
/* 2093 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2099 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2100 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2101 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2102 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2103 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2104 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2105 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2106 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2107 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2110 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2111 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2112 */       splitvalues[0] = multiplecondition.toString();
/* 2113 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2116 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2121 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2122 */     if (thresholdType != 3) {
/* 2123 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2124 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2125 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2126 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2127 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2128 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2130 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2131 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2132 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2133 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2134 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2135 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2137 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2138 */     if (updateSelected != null) {
/* 2139 */       updateSelected[0] = "selected";
/*      */     }
/* 2141 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2146 */       StringBuffer toreturn = new StringBuffer("");
/* 2147 */       if (commaSeparatedMsgId != null) {
/* 2148 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2149 */         int count = 0;
/* 2150 */         while (msgids.hasMoreTokens()) {
/* 2151 */           String id = msgids.nextToken();
/* 2152 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2153 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2154 */           count++;
/* 2155 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2156 */             if (toreturn.length() == 0) {
/* 2157 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2159 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2160 */             if (!image.trim().equals("")) {
/* 2161 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2163 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2164 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2167 */         if (toreturn.length() > 0) {
/* 2168 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2172 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2175 */       e.printStackTrace(); }
/* 2176 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2182 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2188 */   private static Map<String, Long> _jspx_dependants = new HashMap(4);
/* 2189 */   static { _jspx_dependants.put("/jsp/includes/ScriptLeftPage.jspf", Long.valueOf(1473429417000L));
/* 2190 */     _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2191 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2232 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2236 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2242 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2243 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2245 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2247 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2248 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2249 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2250 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2251 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2252 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2253 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2254 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2255 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2256 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2257 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2258 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2259 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2269 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2273 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2274 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2275 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2276 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2277 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2278 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2279 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2280 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2281 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2282 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2284 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.release();
/* 2286 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2287 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.release();
/* 2288 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.release();
/* 2289 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.release();
/* 2290 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.release();
/* 2291 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.release();
/* 2292 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.release();
/* 2293 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.release();
/* 2294 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.release();
/* 2295 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/* 2296 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.release();
/* 2297 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.release();
/* 2298 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fnobody.release();
/* 2299 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.release();
/* 2300 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2301 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fnobody.release();
/* 2302 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.release();
/* 2303 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2304 */     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2311 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2314 */     JspWriter out = null;
/* 2315 */     Object page = this;
/* 2316 */     JspWriter _jspx_out = null;
/* 2317 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2321 */       response.setContentType("text/html;charset=UTF-8");
/* 2322 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2324 */       _jspx_page_context = pageContext;
/* 2325 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2326 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2327 */       session = pageContext.getSession();
/* 2328 */       out = pageContext.getOut();
/* 2329 */       _jspx_out = out;
/*      */       
/* 2331 */       out.write("<!DOCTYPE html>\n");
/* 2332 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!--$Id$-->\n\n");
/*      */       
/*      */ 
/* 2335 */       String mtype = request.getParameter("type");
/*      */       
/* 2337 */       if ((mtype.equals("file")) || (mtype.equals("directory")) || (mtype.equals("File System Monitor")))
/*      */       {
/* 2339 */         request.setAttribute("HelpKey", "Configuring File Monitor");
/*      */       }
/*      */       else {
/* 2342 */         request.setAttribute("HelpKey", "Configuring Script Monitor");
/*      */       }
/*      */       
/*      */ 
/* 2346 */       out.write("\n\n\n\n\n\n\n\n");
/* 2347 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2349 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2350 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2351 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2353 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2355 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2357 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2359 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2360 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2361 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2362 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2365 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2366 */         String available = null;
/* 2367 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2368 */         out.write(10);
/*      */         
/* 2370 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2372 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2374 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2376 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2378 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2380 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2381 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2382 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2383 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2386 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2387 */           String unavailable = null;
/* 2388 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2389 */           out.write(10);
/*      */           
/* 2391 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2393 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2395 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2397 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2399 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2401 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2402 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2403 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2404 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2407 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2408 */             String unmanaged = null;
/* 2409 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2410 */             out.write(10);
/*      */             
/* 2412 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2413 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2414 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2416 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2418 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2420 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2422 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2423 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2424 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2425 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2428 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2429 */               String scheduled = null;
/* 2430 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2431 */               out.write(10);
/*      */               
/* 2433 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2434 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2435 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2437 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2439 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2441 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2443 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2444 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2445 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2446 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2449 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2450 */                 String critical = null;
/* 2451 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2452 */                 out.write(10);
/*      */                 
/* 2454 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2455 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2456 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2458 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2460 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2462 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2464 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2465 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2466 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2467 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2470 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2471 */                   String clear = null;
/* 2472 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2473 */                   out.write(10);
/*      */                   
/* 2475 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2476 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2477 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2479 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2481 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2483 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2485 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2486 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2487 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2488 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2491 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2492 */                     String warning = null;
/* 2493 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2494 */                     out.write(10);
/* 2495 */                     out.write(10);
/*      */                     
/* 2497 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2498 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2500 */                     out.write(10);
/* 2501 */                     out.write(10);
/* 2502 */                     out.write(10);
/* 2503 */                     out.write(10);
/* 2504 */                     ManagedApplication mo = null;
/* 2505 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2506 */                     if (mo == null) {
/* 2507 */                       mo = new ManagedApplication();
/* 2508 */                       _jspx_page_context.setAttribute("mo", mo, 1);
/*      */                     }
/* 2510 */                     out.write(10);
/* 2511 */                     Hashtable applications = null;
/* 2512 */                     synchronized (application) {
/* 2513 */                       applications = (Hashtable)_jspx_page_context.getAttribute("applications", 4);
/* 2514 */                       if (applications == null) {
/* 2515 */                         applications = new Hashtable();
/* 2516 */                         _jspx_page_context.setAttribute("applications", applications, 4);
/*      */                       }
/*      */                     }
/* 2519 */                     out.write(" \n\n\n");
/*      */                     
/*      */ 
/*      */ 
/* 2523 */                     String resourceid = request.getParameter("resourceid");
/* 2524 */                     String haid = request.getParameter("haid");
/* 2525 */                     String customType = request.getParameter("customType");
/* 2526 */                     String appendParams1 = "";
/* 2527 */                     String basetype = request.getParameter("basetype");
/* 2528 */                     String baseid1 = request.getParameter("baseid");
/* 2529 */                     if ((customType != null) && (customType.equals("true")))
/*      */                     {
/* 2531 */                       appendParams1 = basetype;
/*      */                     }
/*      */                     
/* 2534 */                     String resourceName = (String)request.getAttribute("resourcename");
/* 2535 */                     String resID = request.getParameter("resourceid");
/* 2536 */                     request.setAttribute("resourceid", resID);
/* 2537 */                     ArrayList attribIDs = new ArrayList();
/* 2538 */                     String moname = request.getParameter("moname");
/* 2539 */                     ArrayList resIDs = new ArrayList();
/* 2540 */                     resIDs.add(resID);
/* 2541 */                     attribIDs.add("2200");
/* 2542 */                     attribIDs.add("2201");
/* 2543 */                     attribIDs.add("2202");
/* 2544 */                     String resourcetype = request.getParameter("type");
/* 2545 */                     attribIDs.add("6000");
/* 2546 */                     attribIDs.add("6001");
/* 2547 */                     attribIDs.add("6002");
/* 2548 */                     attribIDs.add("6003");
/* 2549 */                     attribIDs.add("6004");
/* 2550 */                     attribIDs.add("6005");
/* 2551 */                     attribIDs.add("6006");
/* 2552 */                     attribIDs.add("6007");
/* 2553 */                     attribIDs.add("6008");
/* 2554 */                     attribIDs.add("6009");
/* 2555 */                     attribIDs.add("6010");
/* 2556 */                     attribIDs.add("6011");
/* 2557 */                     attribIDs.add("6012");
/* 2558 */                     attribIDs.add("6013");
/* 2559 */                     Properties ess_atts = null;
/* 2560 */                     if ((!resourcetype.equals("File System Monitor")) && (!resourcetype.equals("file")) && (!resourcetype.equals("directory")))
/*      */                     {
/* 2562 */                       ess_atts = (Properties)request.getAttribute("ess_atts");
/* 2563 */                       attribIDs.add(ess_atts.getProperty("Availability"));
/* 2564 */                       attribIDs.add(ess_atts.getProperty("Health"));
/* 2565 */                       attribIDs.add(ess_atts.getProperty("ResponseTime"));
/*      */                     }
/* 2567 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2568 */                     String serversite = (String)request.getAttribute("serversite");
/* 2569 */                     Hashtable table_atts = new Hashtable();
/* 2570 */                     ArrayList tableids = new ArrayList();
/* 2571 */                     if (request.getAttribute("tableids") != null)
/*      */                     {
/* 2573 */                       table_atts = (Hashtable)request.getAttribute("table_atts");
/* 2574 */                       tableids = (ArrayList)request.getAttribute("tableids");
/*      */                     }
/* 2576 */                     org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2577 */                     token.saveToken(request);
/* 2578 */                     int contentChkCount = 5;
/*      */                     try {
/* 2580 */                       contentChkCount = com.adventnet.appmanager.util.Constants.fContentChkCount;
/*      */                     } catch (Exception ex) {
/* 2582 */                       ex.printStackTrace();
/*      */                     }
/* 2584 */                     request.setAttribute("contentChkCount", Integer.valueOf(contentChkCount));
/* 2585 */                     String isDataFromFile = (String)request.getAttribute("opfile");
/*      */                     
/* 2587 */                     out.write("\n<script>\n\nvar count=1;\n");
/*      */                     
/* 2589 */                     if ((tableids != null) && (tableids.size() > 0))
/*      */                     {
/*      */ 
/* 2592 */                       out.write("\n\t\tcount=");
/* 2593 */                       out.print(tableids.size());
/* 2594 */                       out.write(10);
/* 2595 */                       out.write(9);
/*      */                     }
/*      */                     
/*      */ 
/* 2599 */                     out.write("\n\t\nfunction doInitStuffOnBodyLoad()\n{\n}\n\nfunction showTables()\n{\n");
/*      */                     
/* 2601 */                     if ((customType == null) || (!customType.equals("true")))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2607 */                       out.write("\n\n  if(document.AMActionForm.tablespresent.checked)\n  {\n    //javascript:showDiv(\"tableid\");\n\t$('#tableid').slideDown();//No I18N\n  }\n  else\n  {\n\t$('#tableid').slideUp();//No I18N\n   //javascript:hideDiv(\"tableid\");\n  }\n");
/*      */                     }
/*      */                     
/*      */ 
/* 2611 */                     out.write("\n\n}\n\nfunction loadTextBox()\n{\n\t\n\tif(document.getElementsByName(\"selectRuleType\")[0].value==0)\n\t{\n\t\tdocument.getElementById(\"cntval11\").width=\"7%\";\n\t\tdocument.getElementById(\"selectValue\").style.display='block'; //No I18N\n\t}\n\telse if(document.getElementsByName(\"selectRuleType\")[0].value==1)\n\t{\n\t\tdocument.getElementById(\"cntval11\").width=\"1%\";\n\t\tdocument.getElementById(\"selectValue\").style.display='none'; //No I18N\n\t\t\n\t}\n}\n\nfunction loadTextBox1()\n{\n\t\n\tif(document.getElementsByName(\"clearConditionRuleType\")[0].value==0)\n\t{\n\t\tdocument.getElementById(\"cntval22\").width=\"7%\";\n\t\tdocument.getElementById(\"selectValue2\").style.display='block'; //No I18N\n\t}\n\telse if(document.getElementsByName(\"clearConditionRuleType\")[0].value==1)\n\t{\n\t\tdocument.getElementById(\"cntval22\").width=\"1%\";\n\t\tdocument.getElementById(\"selectValue2\").style.display='none'; //No I18N\n\t\t\n\t\t\n\t}\n}\n\nfunction showClearCondnTitle() {\n\t\n\tif(document.getElementsByName(\"selectStatusType\")[0].value==0)\n\t{\n\t\tdocument.getElementById(\"ClearCondnUp\").style.display='block'; //No I18N\n");
/* 2612 */                     out.write("\t\tdocument.getElementById(\"ClearCondnDown\").style.display='none'; //No I18N\n\t}\n\telse if(document.getElementsByName(\"selectStatusType\")[0].value==1)\n\t{\n\t\tdocument.getElementById(\"ClearCondnUp\").style.display='none'; //No I18N\n\t\tdocument.getElementById(\"ClearCondnDown\").style.display='block'; //No I18N\n\t}\n}\nfunction ContentCheck(){\n\n\tif(document.AMActionForm.contentChk.checked==true)\n\t {\n\t\t//showDiv('contentChk11');\n\t\t document.getElementById(\"contentChk11\").style.display='table-row';\n\t\t if(document.AMActionForm.fileCheckType[0].checked == false && document.AMActionForm.fileCheckType[1].checked == false){\n\t\t\t document.AMActionForm.fileCheckType[0].checked=true;\n\t\t }\n\t      if(document.AMActionForm.fileCheckType[1].checked)\n          {\n             document.getElementById(\"checkEmptyId\").style.display='none';\n          }\t\n\t\t loadTextBox();\n\t\t loadTextBox1();\n\t\t showClearCondnTitle();\n\t }\n\telse{\n\t\thideDiv('contentChk11');\n\t}\n}\n\nfunction showHideCheckEmpty()\n{\n    if(document.AMActionForm.fileCheckType[0].checked==true)\n");
/* 2613 */                     out.write("    {\n        document.getElementById(\"checkEmptyId\").style.display='table-row';\n    }\n    else\n    {\n        document.getElementById(\"checkEmptyId\").style.display='none';\n    }\n}\n\nfunction RegexCheck(){\n\n\tif(document.AMActionForm.regexChk.checked==true)\n\t {\n\t\thideDiv('NormalChk');\n\t\tshowDiv('regexChk1');\n\t }\n\telse{\n\t \tshowDiv('NormalChk');\n\t\thideDiv('regexChk1');\n\t}\n}\n\nfunction FileDirAgeCheck(){\n\tif(document.AMActionForm.fileDirAge.checked==true)\n\t {\n\n\t\t//showDiv('fileDirAge11');\n\t\t document.getElementById(\"fileDirAge11\").style.display='table-row';\n\t }\n\telse{\n\t\thideDiv('fileDirAge11');\n\t}\n}\n\n\nfunction managecontent(){\n\n\t");
/* 2614 */                     if (("file".equals(request.getParameter("type"))) || ("file".equals(request.getParameter("mtype"))))
/*      */                     {
/* 2616 */                       out.write("\n\t\t // javascript:showDiv('content');\n\t\tdocument.getElementById(\"content\").style.display='table-row';\n\t\thideDiv('subDirCountChk');\n                \n\t\tdocument.getElementById(\"location\").innerHTML ='");
/* 2617 */                       out.print(FormatUtil.getString("File Location"));
/* 2618 */                       out.write("'\n                document.getElementById(\"name\").innerHTML ='<a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,\\'");
/* 2619 */                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 2620 */                       out.write("\\',false,true,\\'#000000\\',200,\\'lightyellow\\')\" onmouseout=\"hideddrivetip()\">");
/* 2621 */                       out.print(FormatUtil.getString("File Name"));
/* 2622 */                       out.write("</a> <span class=\"mandatory\">*</span>'\n\n       ");
/*      */                     }
/* 2624 */                     else if (("directory".equals(request.getParameter("type"))) || ("directory".equals(request.getParameter("mtype")))) {
/* 2625 */                       out.write("\n        \n        \n               javascript:hideDiv('content');\n        \t\tdocument.getElementById(\"subDirCountChk\").style.display='table-row';\n                document.getElementById(\"location\").innerHTML = '");
/* 2626 */                       out.print(FormatUtil.getString("Directory Location"));
/* 2627 */                       out.write("'\n                document.getElementById(\"name\").innerHTML = '<a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,\\'");
/* 2628 */                       out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 2629 */                       out.write("\\',false,true,\\'#000000\\',200,\\'lightyellow\\')\" onmouseout=\"hideddrivetip()\">");
/* 2630 */                       out.print(FormatUtil.getString("Directory Name"));
/* 2631 */                       out.write("</a> <span class=\"mandatory\">*</span>'\n\n        ");
/*      */                     }
/* 2633 */                     out.write("\n}\n\nfunction validateAndSubmit()\n{\n\tvar isFile = false;\n\tvar mtype1 = '");
/* 2634 */                     out.print(request.getParameter("mtype"));
/* 2635 */                     out.write("'\n\tif (mtype1 != null) {\n\t\tisFile = (mtype1.toLowerCase() == \"file\"); // NO I18N\n\t} else {\n\t\tisFile = document.AMActionForm.mtype[0].checked;\n\t}\n    var pollinterval=trimAll(document.AMActionForm.pollInterval.value);\n   ");
/* 2636 */                     if ((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) {
/* 2637 */                       out.write("\n  \t\t\t\t\t\tvar timeVal=trimAll(document.AMActionForm.timeval.value);\n  \t\t\t\t\t\tvar searchcontent=trimAll(document.AMActionForm.ccontent.value);\n  \t\t\t\t\t\tvar montype='");
/* 2638 */                       out.print(request.getParameter("mtype"));
/* 2639 */                       out.write("';\n                        if(document.AMActionForm.displayname.value == ''){\n\t\t\t alert('");
/* 2640 */                       out.print(FormatUtil.getString("am.webclient.common.validatename.text"));
/* 2641 */                       out.write("')\n                        document.AMActionForm.displayname.focus();\n                        return;\n                        }\n\n                        if(document.AMActionForm.filepath.value == ''){\n                       alert('");
/* 2642 */                       out.print(FormatUtil.getString("am.webclient.path.text"));
/* 2643 */                       out.write("')\n                        document.AMActionForm.filepath.focus();\n                        return;\n                        }\n                        else\n            \t        {\n                        \t\n            \t\t\t\tif(document.AMActionForm.filepath){\n            \t\t\t\t\tfilepath=trimAll(document.AMActionForm.filepath.value);\n            \t\t\t\t}\n            \t\t\t\t            \t\t\t\t\n            \t\t\t }\n/*              if(document.AMActionForm.mtype[0].checked ==true){\n                        if(document.AMActionForm.filename.value == ''){\n                        alert('File Name cannot be Empty')\n                        document.AMActionForm.filename.focus();\n                        return;\n                        }\n                }*/\n                                        \n                        if(timeVal == '' || timeVal =='0' ||!(isPositiveInteger(timeVal))){\n            \t\t\t\tif(timeVal == ''){\n            \t\t\t\t\talert('");
/* 2644 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.timevalEmpty.text"));
/* 2645 */                       out.write("')\n            \t\t\t\t\tdocument.AMActionForm.timeval.focus();\n            \t\t\t\t\treturn;\n            \t\t\t\t}\n            \t\t\t\telse{\n            \t\t\t\t\talert('");
/* 2646 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.timeval.text"));
/* 2647 */                       out.write("')\n            \t\t\t\t\tdocument.AMActionForm.timeval.focus();\n            \t\t\t\t\treturn;\n            \t\t\t\t}\n            \t\t\t}\n                        if(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n                        {\n                                alert(\"");
/* 2648 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2649 */                       out.write("\");\n                                document.AMActionForm.pollInterval.focus();\n                                return;\n                        }\n\n             //           document.AMActionForm.action=\"/updateScript.do?method=updatefile&type=File System Monitor\";\n              //          document.AMActionForm.submit();\n                        if(isFile && document.AMActionForm.contentChk.checked == true){\n            \t\t\t\tif(document.AMActionForm.ccontent.value == ''){\n            \t\t\t\talert('");
/* 2650 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.content.text"));
/* 2651 */                       out.write("')\n            \t\t\t\tdocument.AMActionForm.ccontent.focus();\n            \t\t\t\treturn;\n            \t\t\t\t}\n                        }\n                        var searchContArr=searchcontent.split(\",\");\n            \t\t\tif(searchContArr.length > ");
/* 2652 */                       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                         return;
/* 2654 */                       out.write("){\n            \t\t\t\talert('");
/* 2655 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.contentCnt.text", new String[] { "" + contentChkCount }));
/* 2656 */                       out.write("')\n            \t\t\t\tdocument.AMActionForm.ccontent.focus();\n            \t\t\t\treturn;\n            \t\t\t}\n            \t\t\tif(isFile && document.AMActionForm.contentChk.checked == true){\n            \t\t\t\tif (document.AMActionForm.clearCondition[1].checked == true) {\n            \t\t\t\t\tif(document.AMActionForm.clearConditionContent.value == '') {\n            \t\t\t\t\t\t// empty\n            \t\t\t\t\t\talert('");
/* 2657 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.content.text"));
/* 2658 */                       out.write("')\n            \t\t\t\t\t\tdocument.AMActionForm.clearConditionContent.focus();\n            \t\t\t\t\t\treturn\n            \t\t\t\t\t} else {\n            \t\t\t\t\t\tvar searchcontent=trimAll(document.AMActionForm.clearConditionContent.value);\n            \t\t\t\t\t\tvar searchContArr=searchcontent.split(\",\");\n            \t\t\t\t\t\tif(searchContArr.length > ");
/* 2659 */                       if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                         return;
/* 2661 */                       out.write("){\n            \t\t\t\t\t\t\talert('");
/* 2662 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.contentCnt.text", new String[] { "" + contentChkCount }));
/* 2663 */                       out.write("')\n            \t\t\t\t\t\t\tdocument.AMActionForm.clearConditionContent.focus();\n            \t\t\t\t\t\t\treturn;\n            \t\t\t\t\t\t}\n            \t\t\t\t\t}\n            \t\t\t\t}\n            \t\t\t\t\n            \t\t\t}\n            \t\t\t\n\n");
/*      */                     } else {
/* 2665 */                       out.write("\n//if(customType!=null && customType.equals(\"true\"))\n    var timeout=trimAll(document.AMActionForm.timeout.value);\n    var displayname=trimAll(document.AMActionForm.displayname.value);\n      if(displayname == \"\")\n        {\n        alert(\"");
/* 2666 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameempty.text"));
/* 2667 */                       out.write("\");\n            document.AMActionForm.displayname.focus();\n            return;\n        }\n        else\n        {\n            if(checkSpecialchar(document.AMActionForm.displayname.value)==false)\n            {\n            alert(\"");
/* 2668 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.displaynameillegal.text"));
/* 2669 */                       out.write("\");\n                document.AMActionForm.displayname.focus();\n                return false;\n            }\n        }\n         if(pollinterval ==\"\" ||!(isPositiveInteger(pollinterval)) || pollinterval =='0' )\n            {\n            alert(\"");
/* 2670 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2671 */                       out.write("\");\n                document.AMActionForm.pollInterval.focus();\n                return;\n        }\n    \tif(timeout==\"\")\n    \t{\n                alert(\"");
/* 2672 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.timeoutempty.text"));
/* 2673 */                       out.write("\");\n                document.AMActionForm.timeout.focus();\n                return;\n    }\n   ");
/*      */                       
/* 2675 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2676 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2677 */                       _jspx_th_c_005fif_005f0.setParent(null);
/*      */                       
/* 2679 */                       _jspx_th_c_005fif_005f0.setTest("${param.type!='QENGINE'}");
/* 2680 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2681 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                         for (;;) {
/* 2683 */                           out.write("    \n    //var exp_results=trimAll(document.AMActionForm.description.value);\n    var arguments=trimAll(document.AMActionForm.message.value);\n    var mode=\"\";\n    ");
/*      */                           
/* 2685 */                           if ((!System.getProperty("os.name").startsWith("Windows")) && (!System.getProperty("os.name").startsWith("windows")))
/*      */                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2691 */                             out.write("\n        mode=trimAll(document.AMActionForm.mode.value);\n        if(mode==\"\")\n        {\n        }\n        ");
/*      */                           }
/*      */                           
/*      */ 
/* 2695 */                           out.write("\n    \n    var serverpath=trimAll(document.AMActionForm.serverpath.value);\n    var exec_dir=trimAll(document.AMActionForm.workingdirectory.value);\n    var outputfile=trimAll(document.AMActionForm.outputfile.value);\n    if(serverpath==\"\")\n    {\n        alert(\"");
/* 2696 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.scriptempty.text"));
/* 2697 */                           out.write("\");\n        document.AMActionForm.serverpath.focus();\n        return;\n    }\n    else\n    {   \n        if(document.AMActionForm.isCommand[0].checked==false && checkSpecialchar(document.AMActionForm.serverpath.value)==false)\n        {\n        alert(\"");
/* 2698 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.scriptnameillegal.text"));
/* 2699 */                           out.write("\");\n            document.AMActionForm.serverpath.focus();\n            return false;\n        }\n    }\n    if(arguments==\"\")\n    {\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.message.value)==false)\n        {\n        alert(\"");
/* 2700 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.attributesillegal.text"));
/* 2701 */                           out.write("\");\n                document.AMActionForm.message.focus();\n                return false;\n        }\n    }\n    if(exec_dir==\"\")\n    {\n    alert(\"");
/* 2702 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.workingdirectoryempty.text"));
/* 2703 */                           out.write("\");\n        document.AMActionForm.workingdirectory.focus();\n        return;\n    }\n    else\n    {   \n        if(checkSpecialchar(document.AMActionForm.workingdirectory.value)==false)\n        {\n        alert(\"");
/* 2704 */                           out.print(FormatUtil.getString("am.webclient.newscript.alert.workingdirectoryillegal.text"));
/* 2705 */                           out.write("\");\n            document.AMActionForm.workingdirectory.focus();\n            return false;\n        }\n    }\n    ");
/*      */                           
/*      */ 
/* 2708 */                           if ((customType != null) && (customType.equals("true")))
/*      */                           {
/*      */ 
/* 2711 */                             out.write("\n            if(outputfile==\"\" && document.AMActionForm.isFile.checked==true)\n        {\n            alert(\"");
/* 2712 */                             out.print(FormatUtil.getString("am.webclient.newscript.alert.outputfileempty.text"));
/* 2713 */                             out.write("\");\n            document.AMActionForm.outputfile.focus();\n            return;\n        }\n        ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2719 */                             out.write("\n    var string_att=trimAll(document.AMActionForm.string_att.value);\n    var numeric_att=trimAll(document.AMActionForm.numeric_att.value);\n    var delimiter=trimAll(document.AMActionForm.delimiter.value);\n    if(string_att==\"\")\n    {\n    }\n    else\n    {\n\tif(checkSpecialchar(document.AMActionForm.string_att.value)==false)\n        {\n             alert(\"");
/* 2720 */                             out.print(FormatUtil.getString("am.webclient.newscript.alert.attributesillegal.text"));
/* 2721 */                             out.write("\");\n            document.AMActionForm.string_att.focus();\n            return false;\n        }\n    }\n\n   if(numeric_att==\"\")\n    {\n    }\n    else\n    {\n        if(checkSpecialchar(document.AMActionForm.numeric_att.value)==false)\n        {\n        alert(\"");
/* 2722 */                             out.print(FormatUtil.getString("am.webclient.newscript.alert.attributesillegal.text"));
/* 2723 */                             out.write("\");\n            document.AMActionForm.numeric_att.focus();\n            return false;\n        }\n    }\t\n   \n    if(outputfile!=\"\" && string_att==\"\" && numeric_att==\"\")\n    {\n\tif(document.AMActionForm.tablespresent.checked)\n        {\n        }\n        else\n        {\n\t\talert(\"");
/* 2724 */                             out.print(FormatUtil.getString("am.webclient.newscript.alert.numericorstringempty.text"));
/* 2725 */                             out.write("\");\n\t\tif(string_att==\"\")\n\t\t{\n\t\tdocument.AMActionForm.string_att.focus();\n\t\t}\n\t\telse\n\t\t{\n\t\tdocument.AMActionForm.numeric_att.focus();\n\t\t}\n\t\treturn;\n\t}\n    }\n    if(outputfile==\"\" && (string_att!=\"\" || numeric_att!=\"\")  && (document.AMActionForm.isFile.checked==true))\n    {\n        alert(\"");
/* 2726 */                             out.print(FormatUtil.getString("am.webclient.newscript.alert.outputfileempty.text"));
/* 2727 */                             out.write("\");\n        document.AMActionForm.outputfile.focus();\n        return;\n    }\n    if(document.AMActionForm.tablespresent.checked)\n    {\n        for(var i=1;i<=count;i++)\n        {\t\n            var table_name=document.getElementById(\"tab\"+i).value;\n            if(table_name.trim()==\"\")\n            {\n                    alert(\"");
/* 2728 */                             out.print(FormatUtil.getString("am.webclient.newscript.tablename.empty"));
/* 2729 */                             out.write("\");\n               return;\n            }\n            var str_att_tab=document.getElementById(\"sa\"+i).value;\n            var num_att_tab=document.getElementById(\"na\"+i).value;\n            if(str_att_tab.trim()==\"\" && num_att_tab.trim()==\"\")\n            {\n                    alert(\"");
/* 2730 */                             out.print(FormatUtil.getString("am.webclient.newscript.enterattributes.text"));
/* 2731 */                             out.write("\");\n               return;\n            }\n            var str_att_tab=document.getElementById(\"pri_col_att\"+i).value;\n            if(str_att_tab.trim()==\"\")\n            {\n                    alert(\"");
/* 2732 */                             out.print(FormatUtil.getString("am.webclient.newscript.uniquecolumn.empty"));
/* 2733 */                             out.write("\");\n               return;\n            }\n        }\n    }\n    ");
/*      */                           }
/* 2735 */                           out.write(10);
/* 2736 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2737 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2741 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2742 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                       }
/*      */                       
/* 2745 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2746 */                       out.write("\ndocument.AMActionForm.table_row.value=count;\n");
/*      */                     }
/* 2748 */                     out.write("\n    document.AMActionForm.submit();\n}\n\nfunction checkSpecialchar(value)\n{\n    var iChars =\"\\'\";\n    for (var i = 0; i < value.length; i++)\n    {\n  \tif (iChars.indexOf(value.charAt(i)) != -1)\n        {\n            return false;\n  \t}\n    }\n}\n\nfunction formReload()\n{\n    var v=document.AMActionForm.type.value;\n    //var portNo=v.substring(v.indexOf(\":\")+1,v.length);\n    //document.AMActionForm.method=\"post\";\n    document.AMActionForm.action=\"/adminAction.do?method=reloadHostDiscoveryForm&type=\"+v;\n    //enableAll();\n    document.AMActionForm.submit();\n}\n\nfunction managenewHost()\n{\n  //if(document.AMActionForm.newserver.checked==false)\n  if(document.AMActionForm.choosehost.value=='-1')\n  {\n        javascript:showDiv('newhost');\n  }\n  else\n  {\n\t");
/* 2749 */                     if (("file".equals(request.getParameter("type"))) || ("directory".equals(request.getParameter("type"))) || ("File System Monitor".equals(request.getParameter("type")))) {
/* 2750 */                       out.write("\n\n       \t\t\t choosehostval=document.AMActionForm.choosehost.value;\n\t \t\t\t var choosehost;\n\t \t\t\t if(choosehostval!= ''){\n\t \t\t\t\tchoosehost=choosehostval.split(\",\");\n  \t\t\t\t\tURL='/jsp/formpages/Error.jsp?id='+choosehost[1];\n       \t\t\t\thttp1=getHTTPObject();\n       \t\t\t\thttp1.open(\"GET\", URL, true);\n       \t\t\t\thttp1.onreadystatechange =handleResponse;\n       \t\t\t\thttp1.send(null);\n\t \t\t\t}\n\t\t ");
/*      */                     }
/* 2752 */                     out.write("\n\t\t javascript:hideDiv('newhost');\n\n  }\n}\nfunction myOnLoad()\n{\n\n\n    manageremotescript();\n");
/* 2753 */                     if ((!resourcetype.equals("File System Monitor")) && (!resourcetype.equals("file")) && (!resourcetype.equals("directory"))) {
/* 2754 */                       out.write("\n\n    manageopFile();\n    manageCommand();\n    ");
/*      */                       
/* 2756 */                       if ((tableids != null) && (tableids.size() > 0))
/*      */                       {
/*      */ 
/* 2759 */                         out.write("\n    document.AMActionForm.tablespresent.checked=true;\n    ");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/*      */ 
/* 2765 */                         out.write("\n //   document.AMActionForm.tablespresent.checked=false;\n\t\t");
/*      */                       }
/*      */                       
/*      */ 
/* 2769 */                       out.write("\n    showTables();\n");
/*      */                     } else {
/* 2771 */                       out.write("\n\t\tmanagecontent();\n\t\tContentCheck();\n\t\tRegexCheck();\n\t\tFileDirAgeCheck();\n");
/*      */                     }
/* 2773 */                     out.write("\n\t\tchoosehostval1=document.AMActionForm.choosehost.value;\n\t\tvar choosehost1;\n\t\tif(choosehostval1!= ''){\n\t\t\tchoosehost1=choosehostval1.split(\",\");\n\t\t\tURL='/jsp/formpages/Error.jsp?id='+choosehost1[1];\n\t\t\t http1=getHTTPObject();\n\t\t     http1.open(\"GET\", URL, true);\n\t\t     http1.onreadystatechange =handleResponse;\n\t\t     http1.send(null);\n\t\t}\n\n}\nfunction handleResponse()\n{\n\t\t\n        if(http1.readyState == 4)\n        {\n\t\t\t\t\n        \t ");
/* 2774 */                     if (!"directory".equals(request.getParameter("mtype"))) {
/* 2775 */                       out.write("\n      \t\t\n\t\t \t\t//javascript:showDiv('content');\n     \t\t\tdocument.getElementById(\"content\").style.display='table-row';\n\t   \t");
/*      */                     }
/*      */                     else {
/* 2778 */                       out.write("\n\n\t\t    \t javascript:hideDiv('content');                         \n         \n\t\t\t ");
/*      */                     }
/* 2780 */                     out.write("\n        }\n}\n\n\nfunction manageremotescript()\n{\nif(document.AMActionForm.serversite[0].checked == true)\n    {\n        javascript:hideDiv('remotescript');\n        javascript:hideDiv('windowsmode');\n    }\n    else\n    {\n        //javascript:showDiv('remotescript');\n\t\tdocument.getElementById(\"remotescript\").style.display='table-row';\n\t\tjavascript:showDiv('windowsmode');\n    }\n}\n\nfunction manageopFile()\n{\n");
/*      */                     
/* 2782 */                     if ((customType == null) || (!customType.equals("true")))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/* 2787 */                       out.write("\n  if(document.AMActionForm.opfile.checked==false)\n  {\n\t  document.getElementById(\"opdetailstd\").className=\"\";\n      //javascript:hideDiv('opdetails');\n\t  $('#opdetails').slideUp();//No I18N\n  }\n  else\n  {\n\t  document.getElementById(\"opdetailstd\").className=\"monitorinfoeven-actions\";//No I18N\n      //javascript:showDiv('opdetails');\n\t  $('#opdetails').slideDown();//No I18N\n  }\n");
/*      */                     }
/* 2789 */                     out.write(10);
/* 2790 */                     if ("-1".equals(isDataFromFile)) {
/* 2791 */                       out.write("\n\tdocument.AMActionForm.outputfile.disabled=true;\n");
/*      */                     } else {
/* 2793 */                       out.write("\n\tdocument.AMActionForm.isFile.checked=true;\n");
/*      */                     }
/* 2795 */                     out.write("\n}\n\nfunction Edithostdetails()\n{\n    ");
/* 2796 */                     if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                       return;
/* 2798 */                     out.write(10);
/* 2799 */                     out.write(9);
/*      */                     
/* 2801 */                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2802 */                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2803 */                     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                     
/* 2805 */                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2806 */                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2807 */                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2809 */                         out.write(" \n        \n\tvar choosehostval=document.AMActionForm.choosehost.value;\n    var mapperid=choosehostval.split(\",\");\n    \n    if(!mapperid[1] || trimAll(mapperid[1])==\"\")\n    {\n     alert('");
/* 2810 */                         out.print(FormatUtil.getString("am.webclient.newscript.noremotehosts.text"));
/* 2811 */                         out.write("');\n     return;\n    }\n    if(mapperid[0]!=2){\n    \tMM_openBrWindow('/updateScript.do?method=getScriptHostDetails&mtype=");
/* 2812 */                         out.print(resourcetype);
/* 2813 */                         out.write("&mapperid='+mapperid[1]+'&resourceid=");
/* 2814 */                         out.print(request.getParameter("resourceid"));
/* 2815 */                         out.write("','EditHostDetails','width=760,height=460,top=0,left=0,scrollbars=yes,resizable=yes');//No I18N\n    }\n    else{\n    \talert('");
/* 2816 */                         out.print(FormatUtil.getString("am.webclient.wmi.edithost.server.text"));
/* 2817 */                         out.write("');\n    }\n\t");
/* 2818 */                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2819 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2823 */                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2824 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                     }
/*      */                     else {
/* 2827 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2828 */                       out.write("\n}\n\nfunction enableDisableFile() {\n\tif(document.AMActionForm.isFile.checked==true)\t{\n\t\tdocument.AMActionForm.outputfile.disabled=false;\n\t} else {\n\t\tdocument.AMActionForm.outputfile.disabled=true;\n\t}\n}\n\nfunction manageCommand() {\n\tif(document.AMActionForm.isCommand[0].checked == true) {\n\t\tdocument.AMActionForm.mode.disabled=true;\n\t} else {\n\t\tdocument.AMActionForm.mode.disabled=false;\n\t}\n}\n\n\n\n</script>\n\n");
/* 2829 */                       String bcrumb = null;
/* 2830 */                       String theading = null;
/* 2831 */                       if ((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory")))
/*      */                       {
/* 2833 */                         bcrumb = "File System Monitor";
/* 2834 */                         theading = "File / Directory  Monitoring";
/*      */                       }
/*      */                       else
/*      */                       {
/* 2838 */                         bcrumb = "Script Monitor";
/* 2839 */                         theading = "Script Monitoring";
/* 2840 */                         if (request.getParameter("typedisplayname") != null)
/*      */                         {
/* 2842 */                           bcrumb = request.getParameter("typedisplayname");
/* 2843 */                           theading = request.getParameter("typedisplayname");
/*      */                         }
/* 2845 */                         else if (request.getAttribute("typedisplayname") != null)
/*      */                         {
/* 2847 */                           bcrumb = (String)request.getAttribute("typedisplayname");
/* 2848 */                           theading = (String)request.getAttribute("typedisplayname");
/*      */                         }
/*      */                       }
/*      */                       
/* 2852 */                       out.write("\n\n<body onLoad=\"javascript:myOnLoad()\"></body>\n    ");
/*      */                       
/* 2854 */                       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2855 */                       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2856 */                       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                       
/* 2858 */                       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2859 */                       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2860 */                       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                         for (;;) {
/* 2862 */                           out.write("\n    ");
/*      */                           
/* 2864 */                           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2865 */                           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2866 */                           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2868 */                           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                           
/* 2870 */                           _jspx_th_tiles_005fput_005f0.setValue(theading);
/* 2871 */                           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2872 */                           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2873 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                           }
/*      */                           
/* 2876 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2877 */                           out.write("\n    ");
/* 2878 */                           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 2880 */                           out.write("\n    ");
/*      */                           
/* 2882 */                           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2883 */                           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2884 */                           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 2886 */                           _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */                           
/* 2888 */                           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2889 */                           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2890 */                           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2891 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2892 */                               out = _jspx_page_context.pushBody();
/* 2893 */                               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2894 */                               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2897 */                               out.write("\n    ");
/* 2898 */                               out.write("<!--$Id$-->\n\n\n\n");
/*      */                               
/* 2900 */                               int healthid = 2201;
/* 2901 */                               int availabilityid = 2200;
/* 2902 */                               String haid1 = "";
/* 2903 */                               String head = "Script Monitor";
/* 2904 */                               String mon_type = null;
/* 2905 */                               String rtype = request.getParameter("type");
/* 2906 */                               head = rtype;
/* 2907 */                               if (rtype.equals("File System Monitor"))
/*      */                               {
/* 2909 */                                 head = "File / Directory Monitor";
/*      */                               }
/*      */                               
/* 2912 */                               String typedisplayname = head;
/* 2913 */                               if (request.getParameter("typedisplayname") != null)
/*      */                               {
/* 2915 */                                 typedisplayname = request.getParameter("typedisplayname");
/*      */                               }
/* 2917 */                               else if (request.getAttribute("typedisplayname") != null)
/*      */                               {
/* 2919 */                                 typedisplayname = (String)request.getAttribute("typedisplayname");
/*      */                               }
/*      */                               
/* 2922 */                               int baseid = -1;
/*      */                               try
/*      */                               {
/* 2925 */                                 if (request.getAttribute("baseid") != null) {
/* 2926 */                                   baseid = Integer.parseInt((String)request.getAttribute("baseid"));
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception e2)
/*      */                               {
/* 2931 */                                 e2.printStackTrace();
/*      */                               }
/* 2933 */                               String appendParams = "&baseid=" + baseid;
/* 2934 */                               if (baseid != -1)
/*      */                               {
/* 2936 */                                 appendParams = appendParams + "&customType=true&basetype=Script Monitor";
/*      */                               }
/* 2938 */                               if ((rtype.equals("file")) || (rtype.equals("directory")) || (rtype.equals("File System Monitor")))
/*      */                               {
/* 2940 */                                 rtype = "File System Monitor";
/* 2941 */                                 head = "File / Directory Monitor";
/* 2942 */                                 typedisplayname = "File / Directory Monitor";
/* 2943 */                               } else if (rtype.equals("Ping Monitor"))
/*      */                               {
/* 2945 */                                 head = "Ping Monitor";
/* 2946 */                                 rtype = "Ping Monitor";
/* 2947 */                                 typedisplayname = "Ping Monitor";
/* 2948 */                                 healthid = 9001;
/* 2949 */                                 availabilityid = 9000;
/*      */                               }
/*      */                               else
/*      */                               {
/* 2953 */                                 availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2954 */                                 healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                               }
/*      */                               
/*      */ 
/* 2958 */                               if (request.getParameter("mtype") != null) {
/* 2959 */                                 mon_type = request.getParameter("mtype");
/*      */                               }
/*      */                               else {
/* 2962 */                                 mon_type = request.getParameter("type");
/*      */                               }
/*      */                               
/* 2965 */                               if (request.getParameter("type").equals("QENGINE"))
/*      */                               {
/* 2967 */                                 healthid = 4301;
/* 2968 */                                 availabilityid = 4300;
/*      */                               }
/* 2970 */                               else if (mon_type.equals("file")) {
/* 2971 */                                 head = "File System Monitor";
/*      */                                 
/* 2973 */                                 healthid = 6009;
/* 2974 */                                 availabilityid = 6008;
/*      */                               }
/* 2976 */                               else if (mon_type.equals("directory"))
/*      */                               {
/* 2978 */                                 head = "File System Monitor";
/* 2979 */                                 healthid = 6001;
/* 2980 */                                 availabilityid = 6000;
/*      */ 
/*      */                               }
/* 2983 */                               else if (request.getParameter("type").equals("Script Monitor"))
/*      */                               {
/* 2985 */                                 healthid = 2201;
/* 2986 */                                 availabilityid = 2200;
/*      */                               }
/* 2988 */                               else if (mon_type.equals("Ping Monitor"))
/*      */                               {
/* 2990 */                                 healthid = 9001;
/* 2991 */                                 availabilityid = 9000;
/*      */                               }
/*      */                               else
/*      */                               {
/* 2995 */                                 availabilityid = Integer.parseInt(ess_atts.getProperty("Availability"));
/* 2996 */                                 healthid = Integer.parseInt(ess_atts.getProperty("Health"));
/*      */                               }
/*      */                               
/*      */ 
/* 3000 */                               out.write("\n\n<script language=\"JavaScript\">\n");
/*      */                               
/* 3002 */                               if (request.getParameter("editPage") != null)
/*      */                               {
/* 3004 */                                 out.write(10);
/*      */                               }
/*      */                               
/* 3007 */                               out.write("\n\n\n\tfunction confirmDelete()\n \t {\n  var s = confirm(\"");
/* 3008 */                               out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 3009 */                               out.write("\");\n  if (s)\n         document.location.href=\"/deleteMO.do?method=deleteMO&type=");
/* 3010 */                               out.print(head);
/* 3011 */                               out.write("&baseid=");
/* 3012 */                               out.print(baseid);
/* 3013 */                               out.write("&select=");
/* 3014 */                               if (_jspx_meth_c_005fout_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3016 */                               out.write("\";\n\t }\n        function confirmManage()\n \t {\n  var s = confirm(\"");
/* 3017 */                               out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 3018 */                               out.write("\");\n  if (s){\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 3019 */                               if (_jspx_meth_c_005fout_005f3(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3021 */                               out.write("&type=");
/* 3022 */                               out.print(rtype);
/* 3023 */                               out.write("&moname=");
/* 3024 */                               out.print(moname);
/* 3025 */                               out.write("&resourcename=");
/* 3026 */                               out.print(resourceName);
/* 3027 */                               out.write("\";\n\n}\n\t }\n\n         function confirmUnManage()\n \t {\n        \t  var show_msg=\"false\";\n              var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 3028 */                               out.print(request.getParameter("resourceid"));
/* 3029 */                               out.write("; //No i18n\n              $.ajax({\n                type:'POST', //No i18n\n                url:url,\n                async:false,\n                success: function(data)\n                {\n                  show_msg=data\n                }\n              });\n              if(show_msg.indexOf(\"true\")>-1)\n              {\n                  alert(\"");
/* 3030 */                               out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 3031 */                               out.write("\");\n\t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3032 */                               if (_jspx_meth_c_005fout_005f4(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3034 */                               out.write("&type=");
/* 3035 */                               out.print(rtype);
/* 3036 */                               out.write("&moname=");
/* 3037 */                               out.print(moname);
/* 3038 */                               out.write("&resourcename=");
/* 3039 */                               out.print(resourceName);
/* 3040 */                               out.write("\";\n             }\n           else { \n        \t   \tvar s = confirm(\"");
/* 3041 */                               out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 3042 */                               out.write("\");\n       \t\t if (s){\n   \t\t \t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 3043 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3045 */                               out.write("&type=");
/* 3046 */                               out.print(rtype);
/* 3047 */                               out.write("&moname=");
/* 3048 */                               out.print(moname);
/* 3049 */                               out.write("&resourcename=");
/* 3050 */                               out.print(resourceName);
/* 3051 */                               out.write("\";\n\t\t      }\n\t      }\n       }\n  </script>\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td width=\"90%\" height=\"21\" class=\"leftlinksheading\">\n    ");
/* 3052 */                               out.print(FormatUtil.getString(typedisplayname));
/* 3053 */                               out.write("\n      </td>\n  </tr>\n      ");
/* 3054 */                               if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3056 */                               out.write("\n      ");
/* 3057 */                               if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3059 */                               out.write(10);
/* 3060 */                               out.write(9);
/* 3061 */                               out.write(9);
/* 3062 */                               if (_jspx_meth_c_005fif_005f3(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3064 */                               out.write(10);
/* 3065 */                               out.write("\n\n\n\n");
/*      */                               
/* 3067 */                               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3068 */                               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3069 */                               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3071 */                               _jspx_th_c_005fif_005f4.setTest("${!empty param.haid && empty invalidhaid}");
/* 3072 */                               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3073 */                               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                 for (;;) {
/* 3075 */                                   out.write(10);
/*      */                                   
/* 3077 */                                   haid1 = "&haid=" + request.getParameter("haid");
/*      */                                   
/* 3079 */                                   out.write(10);
/* 3080 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3081 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3085 */                               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3086 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                               }
/*      */                               
/* 3089 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3090 */                               out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                               
/* 3092 */                               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3093 */                               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3094 */                               _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3096 */                               _jspx_th_c_005fif_005f5.setTest("${param.method=='editScript'}");
/* 3097 */                               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3098 */                               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                 for (;;) {
/* 3100 */                                   out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 3101 */                                   if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                     return;
/* 3103 */                                   out.write("&method=showResourceForResourceID");
/* 3104 */                                   out.print(haid1);
/* 3105 */                                   out.write("\"  class=\"new-left-links\">\n    ");
/* 3106 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3107 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3111 */                               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3112 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                               }
/*      */                               
/* 3115 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3116 */                               out.write("\n    ");
/* 3117 */                               out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 3118 */                               out.write("\n    ");
/* 3119 */                               if (_jspx_meth_c_005fif_005f6(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 3121 */                               out.write("\n     </td>\n  </tr>\n");
/* 3122 */                               out.write(10);
/* 3123 */                               out.write(32);
/* 3124 */                               out.write(32);
/*      */                               
/* 3126 */                               IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3127 */                               _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3128 */                               _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3130 */                               _jspx_th_c_005fif_005f7.setTest("${!empty ADMIN || !empty DEMO }");
/* 3131 */                               int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3132 */                               if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                 for (;;) {
/* 3134 */                                   out.write("\n\n\n\t<tr>\n  ");
/*      */                                   
/* 3136 */                                   PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3137 */                                   _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3138 */                                   _jspx_th_logic_005fpresent_005f1.setParent(_jspx_th_c_005fif_005f7);
/*      */                                   
/* 3140 */                                   _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3141 */                                   int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3142 */                                   if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                                     for (;;) {
/* 3144 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3145 */                                       out.print(ALERTCONFIG_TEXT);
/* 3146 */                                       out.write("</a> </td>\n  ");
/* 3147 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3148 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3152 */                                   if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3153 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1); return;
/*      */                                   }
/*      */                                   
/* 3156 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3157 */                                   out.write(10);
/* 3158 */                                   out.write(9);
/*      */                                   
/* 3160 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3161 */                                   _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 3162 */                                   _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_c_005fif_005f7);
/*      */                                   
/* 3164 */                                   _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 3165 */                                   int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 3166 */                                   if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                     for (;;) {
/* 3168 */                                       out.write("\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                       
/* 3170 */                                       IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3171 */                                       _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3172 */                                       _jspx_th_c_005fif_005f8.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                       
/* 3174 */                                       _jspx_th_c_005fif_005f8.setTest("${param.method=='editScript'}");
/* 3175 */                                       int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3176 */                                       if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                         for (;;) {
/* 3178 */                                           out.write("\n    <a href=\"/showresource.do?resourceid=");
/* 3179 */                                           if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f8, _jspx_page_context))
/*      */                                             return;
/* 3181 */                                           out.write("&method=showResourceForResourceID&alert=true");
/* 3182 */                                           out.print(haid1);
/* 3183 */                                           out.write("\"  class=\"new-left-links\">\n    ");
/* 3184 */                                           out.print(ALERTCONFIG_TEXT);
/* 3185 */                                           out.write("\n    </a>\n    ");
/* 3186 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3187 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3191 */                                       if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3192 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                       }
/*      */                                       
/* 3195 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3196 */                                       out.write("\n    ");
/*      */                                       
/* 3198 */                                       IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3199 */                                       _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3200 */                                       _jspx_th_c_005fif_005f9.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                       
/* 3202 */                                       _jspx_th_c_005fif_005f9.setTest("${param.method!='editScript'}");
/* 3203 */                                       int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3204 */                                       if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                         for (;;) {
/* 3206 */                                           out.write("\n    <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 3207 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f9, _jspx_page_context))
/*      */                                             return;
/* 3209 */                                           out.write("&mtype=");
/* 3210 */                                           out.print(mon_type);
/* 3211 */                                           out.write("\" class=\"new-left-links\">\n    ");
/* 3212 */                                           out.print(ALERTCONFIG_TEXT);
/* 3213 */                                           out.write("\n    </a>\n    ");
/* 3214 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3215 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3219 */                                       if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3220 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                       }
/*      */                                       
/* 3223 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3224 */                                       out.write("\n    </td>\n");
/* 3225 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 3226 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3230 */                                   if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 3231 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                                   }
/*      */                                   
/* 3234 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 3235 */                                   out.write("\n  </tr>\n  ");
/* 3236 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3237 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3241 */                               if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3242 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                               }
/*      */                               
/* 3245 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3246 */                               out.write(10);
/*      */                               
/* 3248 */                               IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3249 */                               _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3250 */                               _jspx_th_c_005fif_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3252 */                               _jspx_th_c_005fif_005f10.setTest("${param.type=='Script Monitor'}");
/* 3253 */                               int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3254 */                               if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                                 for (;;) {
/* 3256 */                                   out.write(10);
/* 3257 */                                   out.write(32);
/* 3258 */                                   out.write(32);
/*      */                                   
/* 3260 */                                   IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3261 */                                   _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3262 */                                   _jspx_th_c_005fif_005f11.setParent(_jspx_th_c_005fif_005f10);
/*      */                                   
/* 3264 */                                   _jspx_th_c_005fif_005f11.setTest("${!empty ADMIN || !empty DEMO }");
/* 3265 */                                   int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3266 */                                   if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                                     for (;;) {
/* 3268 */                                       out.write(10);
/* 3269 */                                       out.write(32);
/* 3270 */                                       out.write(32);
/*      */                                       
/* 3272 */                                       IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3273 */                                       _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3274 */                                       _jspx_th_c_005fif_005f12.setParent(_jspx_th_c_005fif_005f11);
/*      */                                       
/* 3276 */                                       _jspx_th_c_005fif_005f12.setTest("${param.method=='editScript'}");
/* 3277 */                                       int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3278 */                                       if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                                         for (;;) {
/* 3280 */                                           out.write("\n\t <tr>\n  ");
/*      */                                           
/* 3282 */                                           PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3283 */                                           _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3284 */                                           _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fif_005f12);
/*      */                                           
/* 3286 */                                           _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3287 */                                           int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3288 */                                           if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                             for (;;) {
/* 3290 */                                               out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3291 */                                               out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3292 */                                               out.write("</a> </td>\n  ");
/* 3293 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3294 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3298 */                                           if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3299 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                           }
/*      */                                           
/* 3302 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3303 */                                           out.write("\n        ");
/*      */                                           
/* 3305 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3306 */                                           _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3307 */                                           _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f12);
/*      */                                           
/* 3309 */                                           _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 3310 */                                           int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3311 */                                           if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                             for (;;) {
/* 3313 */                                               out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n\n    <a href=\"/showresource.do?resourceid=");
/* 3314 */                                               if (_jspx_meth_c_005fout_005f12(_jspx_th_logic_005fnotPresent_005f2, _jspx_page_context))
/*      */                                                 return;
/* 3316 */                                               out.write("&method=showResourceForResourceID&reports=true");
/* 3317 */                                               out.print(haid1);
/* 3318 */                                               out.write("\"  class=\"new-left-links\">\n    ");
/* 3319 */                                               out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3320 */                                               out.write("\n    </a>\n     </td>\n");
/* 3321 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3322 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3326 */                                           if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3327 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                           }
/*      */                                           
/* 3330 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3331 */                                           out.write("\n  </tr>\n  ");
/* 3332 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3333 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3337 */                                       if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3338 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                                       }
/*      */                                       
/* 3341 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3342 */                                       out.write(10);
/* 3343 */                                       out.write(32);
/* 3344 */                                       out.write(32);
/* 3345 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3346 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3350 */                                   if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3351 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */                                   }
/*      */                                   
/* 3354 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3355 */                                   out.write("\n\n  ");
/*      */                                   
/* 3357 */                                   IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3358 */                                   _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3359 */                                   _jspx_th_c_005fif_005f13.setParent(_jspx_th_c_005fif_005f10);
/*      */                                   
/* 3361 */                                   _jspx_th_c_005fif_005f13.setTest("${!empty ADMIN || !empty DEMO }");
/* 3362 */                                   int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3363 */                                   if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                                     for (;;) {
/* 3365 */                                       out.write(10);
/* 3366 */                                       out.write(32);
/* 3367 */                                       out.write(32);
/*      */                                       
/* 3369 */                                       IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3370 */                                       _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3371 */                                       _jspx_th_c_005fif_005f14.setParent(_jspx_th_c_005fif_005f13);
/*      */                                       
/* 3373 */                                       _jspx_th_c_005fif_005f14.setTest("${param.method!='editScript'}");
/* 3374 */                                       int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3375 */                                       if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                                         for (;;) {
/* 3377 */                                           out.write("\n  <tr>\n");
/*      */                                           
/* 3379 */                                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3380 */                                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3381 */                                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fif_005f14);
/*      */                                           
/* 3383 */                                           _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3384 */                                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3385 */                                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                             for (;;) {
/* 3387 */                                               out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3388 */                                               out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3389 */                                               out.write("</a> </td>\n  ");
/* 3390 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3391 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3395 */                                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3396 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                           }
/*      */                                           
/* 3399 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3400 */                                           out.write("\n        ");
/*      */                                           
/* 3402 */                                           NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3403 */                                           _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3404 */                                           _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_c_005fif_005f14);
/*      */                                           
/* 3406 */                                           _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3407 */                                           int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3408 */                                           if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                             for (;;) {
/* 3410 */                                               out.write("\n\n    <td class=\"leftlinkstd\"><a href=\"javascript:enableReports()\" class=\"new-left-links\">");
/* 3411 */                                               out.print(FormatUtil.getString("am.webclient.script.enabledisablereports"));
/* 3412 */                                               out.write("</a></td>\n   </td>\n");
/* 3413 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3414 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3418 */                                           if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3419 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                           }
/*      */                                           
/* 3422 */                                           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3423 */                                           out.write("\n  </tr>\n  ");
/* 3424 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3425 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3429 */                                       if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3430 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                                       }
/*      */                                       
/* 3433 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3434 */                                       out.write(10);
/* 3435 */                                       out.write(32);
/* 3436 */                                       out.write(32);
/* 3437 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3438 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3442 */                                   if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3443 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                                   }
/*      */                                   
/* 3446 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3447 */                                   out.write(10);
/* 3448 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3449 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3453 */                               if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3454 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                               }
/*      */                               
/* 3457 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3458 */                               out.write(10);
/* 3459 */                               out.write(10);
/*      */                               
/* 3461 */                               PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3462 */                               _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3463 */                               _jspx_th_logic_005fpresent_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3465 */                               _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 3466 */                               int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3467 */                               if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                                 for (;;) {
/* 3469 */                                   out.write("\n  \n");
/*      */                                   
/* 3471 */                                   PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3472 */                                   _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3473 */                                   _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                   
/* 3475 */                                   _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 3476 */                                   int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3477 */                                   if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                     for (;;) {
/* 3479 */                                       out.write("\n<tr>\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3480 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3481 */                                       out.write("</a> </td> \n</tr>\n");
/* 3482 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3483 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3487 */                                   if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3488 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                   }
/*      */                                   
/* 3491 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3492 */                                   out.write(10);
/* 3493 */                                   out.write(10);
/*      */                                   
/* 3495 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3496 */                                   _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 3497 */                                   _jspx_th_logic_005fnotPresent_005f4.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                                   
/* 3499 */                                   _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 3500 */                                   int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 3501 */                                   if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                                     for (;;) {
/* 3503 */                                       out.write(10);
/* 3504 */                                       if (com.adventnet.appmanager.server.framework.AMAutomaticPortChanger.isSsoEnabled()) {
/* 3505 */                                         out.write("\n\t<tr>\n   <td height=\"21\" class=\"leftlinkstd\">\n   ");
/*      */                                         
/* 3507 */                                         String temphaid = request.getParameter("haid");
/*      */                                         try
/*      */                                         {
/* 3510 */                                           Integer.parseInt(temphaid);
/*      */                                           
/* 3512 */                                           if (rtype.equals("Ping Monitor")) {
/* 3513 */                                             out.write("\n <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3514 */                                             if (_jspx_meth_c_005fout_005f13(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3516 */                                             out.write("&method=editScript");
/* 3517 */                                             out.print(appendParams);
/* 3518 */                                             out.write("&resourceid=");
/* 3519 */                                             if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3521 */                                             out.write("&aam_jump=true&type=");
/* 3522 */                                             out.print(request.getParameter("type"));
/* 3523 */                                             if (_jspx_meth_c_005fout_005f15(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3525 */                                             out.write("&resourcename=");
/* 3526 */                                             out.print(resourceName);
/* 3527 */                                             out.write("&mtype=");
/* 3528 */                                             out.print(mon_type);
/* 3529 */                                             out.write("\"  class=\"new-left-links\">\n\n");
/*      */                                           } else {
/* 3531 */                                             out.write("\n    <a target=\"mas_window\" href=\"/updateScript.do?haid=");
/* 3532 */                                             if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3534 */                                             out.write("&method=editScript");
/* 3535 */                                             out.print(appendParams);
/* 3536 */                                             out.write("&resourceid=");
/* 3537 */                                             if (_jspx_meth_c_005fout_005f17(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3539 */                                             out.write("&aam_jump=true&type=");
/* 3540 */                                             out.print(request.getParameter("type"));
/* 3541 */                                             if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3543 */                                             out.write("&resourcename=");
/* 3544 */                                             out.print(resourceName);
/* 3545 */                                             out.write("&mtype=");
/* 3546 */                                             out.print(mon_type);
/* 3547 */                                             out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                         catch (NumberFormatException ne)
/*      */                                         {
/* 3553 */                                           if (rtype.equals("Ping Monitor")) {
/* 3554 */                                             out.write("\n<a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 3555 */                                             out.print(request.getParameter("resourceid"));
/* 3556 */                                             out.write("&type=");
/* 3557 */                                             out.print(request.getParameter("type"));
/* 3558 */                                             out.write("&moname=");
/* 3559 */                                             out.print(moname);
/* 3560 */                                             out.write("&method=showdetails&resourcename=");
/* 3561 */                                             out.print(request.getParameter("resourcename"));
/* 3562 */                                             out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n\n");
/*      */                                           } else {
/* 3564 */                                             out.write("\n\n    <a target=\"mas_window\" href=\"/updateScript.do?method=editScript");
/* 3565 */                                             out.print(appendParams);
/* 3566 */                                             out.write("&resourceid=");
/* 3567 */                                             if (_jspx_meth_c_005fout_005f19(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3569 */                                             out.write("&aam_jump=true&type=");
/* 3570 */                                             out.print(request.getParameter("type"));
/* 3571 */                                             if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fnotPresent_005f4, _jspx_page_context))
/*      */                                               return;
/* 3573 */                                             out.write("&resourcename=");
/* 3574 */                                             out.print(resourceName);
/* 3575 */                                             out.write("&mtype=");
/* 3576 */                                             out.print(mon_type);
/* 3577 */                                             out.write("\"  class=\"new-left-links\">\n   ");
/*      */                                           }
/*      */                                         }
/*      */                                         
/* 3581 */                                         out.write(10);
/* 3582 */                                         out.write(32);
/* 3583 */                                         out.write(32);
/* 3584 */                                         out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3585 */                                         out.write("\n   </td> </tr>\n   ");
/*      */                                       }
/* 3587 */                                       out.write(10);
/* 3588 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 3589 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3593 */                                   if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 3594 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                                   }
/*      */                                   
/* 3597 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 3598 */                                   out.write(10);
/* 3599 */                                   out.write(32);
/* 3600 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3601 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3605 */                               if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3606 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4); return;
/*      */                               }
/*      */                               
/* 3609 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3610 */                               out.write(10);
/* 3611 */                               out.write(32);
/* 3612 */                               out.write(10);
/*      */                               
/* 3614 */                               IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3615 */                               _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3616 */                               _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3618 */                               _jspx_th_c_005fif_005f15.setTest("${(!empty ADMIN || !empty DEMO) }");
/* 3619 */                               int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3620 */                               if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                                 for (;;) {
/* 3622 */                                   out.write("\n  <tr>\n");
/*      */                                   
/* 3624 */                                   PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3625 */                                   _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3626 */                                   _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f15);
/*      */                                   
/* 3628 */                                   _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 3629 */                                   int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3630 */                                   if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3632 */                                       out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3633 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3634 */                                       out.write("</a> </td>\n  ");
/* 3635 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3636 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3640 */                                   if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3641 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3644 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3645 */                                   out.write("\n        ");
/*      */                                   
/* 3647 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3648 */                                   _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 3649 */                                   _jspx_th_logic_005fnotPresent_005f5.setParent(_jspx_th_c_005fif_005f15);
/*      */                                   
/* 3651 */                                   _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 3652 */                                   int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 3653 */                                   if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                                     for (;;) {
/* 3655 */                                       out.write("\n\n\n    <td height=\"21\" class=\"leftlinkstd\">\n    ");
/*      */                                       
/* 3657 */                                       IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3658 */                                       _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3659 */                                       _jspx_th_c_005fif_005f16.setParent(_jspx_th_logic_005fnotPresent_005f5);
/*      */                                       
/* 3661 */                                       _jspx_th_c_005fif_005f16.setTest("${param.actionmethod!='editScript' }");
/* 3662 */                                       int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3663 */                                       if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                                         for (;;) {
/* 3665 */                                           out.write("\n    ");
/*      */                                           
/* 3667 */                                           String temphaid = request.getParameter("haid");
/*      */                                           try
/*      */                                           {
/* 3670 */                                             Integer.parseInt(temphaid);
/*      */                                             
/* 3672 */                                             if (rtype.equals("Ping Monitor")) {
/* 3673 */                                               out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                             } else {
/* 3675 */                                               out.write("\n\n<a href=\"/updateScript.do?haid=");
/* 3676 */                                               if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                                 return;
/* 3678 */                                               out.write("&method=editScript");
/* 3679 */                                               out.print(appendParams);
/* 3680 */                                               out.write("&resourceid=");
/* 3681 */                                               if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                                 return;
/* 3683 */                                               out.write("&type=");
/* 3684 */                                               out.print(rtype);
/* 3685 */                                               if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                                 return;
/* 3687 */                                               out.write("&resourcename=");
/* 3688 */                                               out.print(resourceName);
/* 3689 */                                               out.write("&mtype=");
/* 3690 */                                               out.print(mon_type);
/* 3691 */                                               out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                             }
/*      */                                             
/*      */                                           }
/*      */                                           catch (NumberFormatException ne)
/*      */                                           {
/* 3697 */                                             if (rtype.equals("Ping Monitor")) {
/* 3698 */                                               out.write("\n<a href=\"javascript:toggleDiv('edit')\" class=\"new-left-links\">\n");
/*      */                                             } else {
/* 3700 */                                               out.write("\n\n\n<a href=\"/updateScript.do?method=editScript");
/* 3701 */                                               out.print(appendParams);
/* 3702 */                                               out.write("&resourceid=");
/* 3703 */                                               if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                                 return;
/* 3705 */                                               out.write("&type=");
/* 3706 */                                               out.print(rtype);
/* 3707 */                                               if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fif_005f16, _jspx_page_context))
/*      */                                                 return;
/* 3709 */                                               out.write("&resourcename=");
/* 3710 */                                               out.print(resourceName);
/* 3711 */                                               out.write("&mtype=");
/* 3712 */                                               out.print(mon_type);
/* 3713 */                                               out.write("\"  class=\"new-left-links\">\n    \t");
/*      */                                             }
/*      */                                           }
/*      */                                           
/* 3717 */                                           out.write(10);
/* 3718 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3719 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3723 */                                       if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3724 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                                       }
/*      */                                       
/* 3727 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3728 */                                       out.write("\n\n    ");
/* 3729 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 3730 */                                       out.write("\n    ");
/* 3731 */                                       if (_jspx_meth_c_005fif_005f17(_jspx_th_logic_005fnotPresent_005f5, _jspx_page_context))
/*      */                                         return;
/* 3733 */                                       out.write("</td>\n");
/* 3734 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 3735 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3739 */                                   if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 3740 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                                   }
/*      */                                   
/* 3743 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 3744 */                                   out.write("\n  </tr>\n  ");
/* 3745 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3746 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3750 */                               if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3751 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                               }
/*      */                               
/* 3754 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3755 */                               out.write("\n\n  ");
/*      */                               
/* 3757 */                               IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3758 */                               _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 3759 */                               _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3761 */                               _jspx_th_c_005fif_005f18.setTest("${!empty ADMIN || !empty DEMO }");
/* 3762 */                               int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 3763 */                               if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                                 for (;;) {
/* 3765 */                                   out.write(10);
/*      */                                   
/* 3767 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3768 */                                   _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 3769 */                                   _jspx_th_logic_005fnotPresent_005f6.setParent(_jspx_th_c_005fif_005f18);
/*      */                                   
/* 3771 */                                   _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 3772 */                                   int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 3773 */                                   if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                                     for (;;) {
/* 3775 */                                       out.write("\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\"><a href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 3776 */                                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3777 */                                       out.write("</a></td>\n  </tr>\n");
/* 3778 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 3779 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3783 */                                   if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 3784 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                                   }
/*      */                                   
/* 3787 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 3788 */                                   out.write(10);
/*      */                                   
/* 3790 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3791 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3792 */                                   _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_c_005fif_005f18);
/*      */                                   
/* 3794 */                                   _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3795 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3796 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 3798 */                                       out.write("\n\n<td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3799 */                                       out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 3800 */                                       out.write("</a></td>\n\n");
/* 3801 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3802 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3806 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3807 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                                   }
/*      */                                   
/* 3810 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3811 */                                   out.write("\n\n  ");
/* 3812 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 3813 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3817 */                               if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 3818 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                               }
/*      */                               
/* 3821 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 3822 */                               out.write(10);
/* 3823 */                               out.write(32);
/* 3824 */                               out.write(32);
/*      */                               
/* 3826 */                               IfTag _jspx_th_c_005fif_005f19 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3827 */                               _jspx_th_c_005fif_005f19.setPageContext(_jspx_page_context);
/* 3828 */                               _jspx_th_c_005fif_005f19.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3830 */                               _jspx_th_c_005fif_005f19.setTest("${!empty ADMIN || !empty DEMO || !empty OPERATOR}");
/* 3831 */                               int _jspx_eval_c_005fif_005f19 = _jspx_th_c_005fif_005f19.doStartTag();
/* 3832 */                               if (_jspx_eval_c_005fif_005f19 != 0) {
/*      */                                 for (;;) {
/* 3834 */                                   out.write("\n\n  <tr>\n\n  ");
/*      */                                   
/* 3836 */                                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                   {
/*      */ 
/* 3839 */                                     out.write(10);
/* 3840 */                                     out.write(9);
/*      */                                     
/* 3842 */                                     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3843 */                                     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3844 */                                     _jspx_th_logic_005fpresent_005f8.setParent(_jspx_th_c_005fif_005f19);
/*      */                                     
/* 3846 */                                     _jspx_th_logic_005fpresent_005f8.setRole("DEMO");
/* 3847 */                                     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3848 */                                     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                       for (;;) {
/* 3850 */                                         out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3851 */                                         out.print(FormatUtil.getString("Manage"));
/* 3852 */                                         out.write("</a> </td>\n  ");
/* 3853 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3854 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3858 */                                     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3859 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                                     }
/*      */                                     
/* 3862 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3863 */                                     out.write("\n        ");
/*      */                                     
/* 3865 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3866 */                                     _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 3867 */                                     _jspx_th_logic_005fnotPresent_005f7.setParent(_jspx_th_c_005fif_005f19);
/*      */                                     
/* 3869 */                                     _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 3870 */                                     int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 3871 */                                     if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                                       for (;;) {
/* 3873 */                                         out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 3874 */                                         out.print(FormatUtil.getString("Manage"));
/* 3875 */                                         out.write("</A></td>\n");
/* 3876 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 3877 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3881 */                                     if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 3882 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                                     }
/*      */                                     
/* 3885 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 3886 */                                     out.write("\n    ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 3892 */                                     out.write(10);
/*      */                                     
/* 3894 */                                     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3895 */                                     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3896 */                                     _jspx_th_logic_005fpresent_005f9.setParent(_jspx_th_c_005fif_005f19);
/*      */                                     
/* 3898 */                                     _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3899 */                                     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3900 */                                     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                       for (;;) {
/* 3902 */                                         out.write("\n  <td height=\"21\" class=\"leftlinkstd\" > <a href=\"javascript:alertUser()\" class=\"new-left-links\">\n  ");
/* 3903 */                                         out.print(FormatUtil.getString("UnManage"));
/* 3904 */                                         out.write("</a> </td>\n  ");
/* 3905 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3906 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3910 */                                     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3911 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                                     }
/*      */                                     
/* 3914 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3915 */                                     out.write("\n        ");
/*      */                                     
/* 3917 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f8 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3918 */                                     _jspx_th_logic_005fnotPresent_005f8.setPageContext(_jspx_page_context);
/* 3919 */                                     _jspx_th_logic_005fnotPresent_005f8.setParent(_jspx_th_c_005fif_005f19);
/*      */                                     
/* 3921 */                                     _jspx_th_logic_005fnotPresent_005f8.setRole("DEMO");
/* 3922 */                                     int _jspx_eval_logic_005fnotPresent_005f8 = _jspx_th_logic_005fnotPresent_005f8.doStartTag();
/* 3923 */                                     if (_jspx_eval_logic_005fnotPresent_005f8 != 0) {
/*      */                                       for (;;) {
/* 3925 */                                         out.write("\n\n\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 3926 */                                         out.print(FormatUtil.getString("UnManage"));
/* 3927 */                                         out.write("</A></td>\n");
/* 3928 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f8.doAfterBody();
/* 3929 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3933 */                                     if (_jspx_th_logic_005fnotPresent_005f8.doEndTag() == 5) {
/* 3934 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8); return;
/*      */                                     }
/*      */                                     
/* 3937 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f8);
/* 3938 */                                     out.write("\n    ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3942 */                                   out.write("\n  </tr>\n  ");
/* 3943 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f19.doAfterBody();
/* 3944 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3948 */                               if (_jspx_th_c_005fif_005f19.doEndTag() == 5) {
/* 3949 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19); return;
/*      */                               }
/*      */                               
/* 3952 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f19);
/* 3953 */                               out.write("\n   ");
/*      */                               
/* 3955 */                               NotPresentTag _jspx_th_logic_005fnotPresent_005f9 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3956 */                               _jspx_th_logic_005fnotPresent_005f9.setPageContext(_jspx_page_context);
/* 3957 */                               _jspx_th_logic_005fnotPresent_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3959 */                               _jspx_th_logic_005fnotPresent_005f9.setRole("DEMO");
/* 3960 */                               int _jspx_eval_logic_005fnotPresent_005f9 = _jspx_th_logic_005fnotPresent_005f9.doStartTag();
/* 3961 */                               if (_jspx_eval_logic_005fnotPresent_005f9 != 0) {
/*      */                                 for (;;) {
/* 3963 */                                   out.write("\n    ");
/*      */                                   
/* 3965 */                                   String resourceid_poll = request.getParameter("resourceid");
/* 3966 */                                   String resourcetype_poll = request.getParameter("type");
/*      */                                   
/* 3968 */                                   out.write("\n      <tr>\n      <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n      <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3969 */                                   out.print(resourceid_poll);
/* 3970 */                                   out.write("&baseid=");
/* 3971 */                                   out.print(baseid);
/* 3972 */                                   out.write("&resourcetype=");
/* 3973 */                                   out.print(resourcetype_poll);
/* 3974 */                                   out.write("\" class=\"new-left-links\"> ");
/* 3975 */                                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3976 */                                   out.write("</a></td>\n    </tr>\n    ");
/* 3977 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f9.doAfterBody();
/* 3978 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3982 */                               if (_jspx_th_logic_005fnotPresent_005f9.doEndTag() == 5) {
/* 3983 */                                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9); return;
/*      */                               }
/*      */                               
/* 3986 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f9);
/* 3987 */                               out.write("\n    ");
/*      */                               
/* 3989 */                               PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3990 */                               _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3991 */                               _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 3993 */                               _jspx_th_logic_005fpresent_005f10.setRole("DEMO");
/* 3994 */                               int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3995 */                               if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                 for (;;) {
/* 3997 */                                   out.write("\n          <tr>\n          <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n          <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3998 */                                   out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3999 */                                   out.write("</a></td>\n          </td>\n    ");
/* 4000 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4001 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4005 */                               if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4006 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                               }
/*      */                               
/* 4009 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4010 */                               out.write("\n    ");
/* 4011 */                               out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                               
/* 4013 */                               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                               {
/* 4015 */                                 Map<com.me.apm.cmdb.APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 4016 */                                 String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                                 
/* 4018 */                                 String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 4019 */                                 String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(com.me.apm.cmdb.APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 4020 */                                 if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                                 {
/* 4022 */                                   if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                   {
/*      */ 
/* 4025 */                                     out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4026 */                                     out.print(ciInfoUrl);
/* 4027 */                                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4028 */                                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4029 */                                     out.write("</a></td>");
/* 4030 */                                     out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4031 */                                     out.print(ciRLUrl);
/* 4032 */                                     out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 4033 */                                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4034 */                                     out.write("</a></td>");
/* 4035 */                                     out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                   }
/* 4039 */                                   else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                   {
/*      */ 
/* 4042 */                                     out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 4043 */                                     out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 4044 */                                     out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4045 */                                     out.print(ciInfoUrl);
/* 4046 */                                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 4047 */                                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4048 */                                     out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4049 */                                     out.print(ciRLUrl);
/* 4050 */                                     out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 4051 */                                     out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4052 */                                     out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                               
/* 4057 */                               out.write("\n \n \n\n");
/* 4058 */                               out.write("\n</table>\n\n <br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4059 */                               out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 4060 */                               out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4061 */                               if (_jspx_meth_c_005fout_005f26(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 4063 */                               out.write("&attributeid=");
/* 4064 */                               out.print(healthid);
/* 4065 */                               out.write("')\"\n      class=\"new-left-links\">");
/* 4066 */                               out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4067 */                               out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4068 */                               if (_jspx_meth_c_005fout_005f27(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 4070 */                               out.write("&attributeid=");
/* 4071 */                               out.print(healthid);
/* 4072 */                               out.write("')\">");
/* 4073 */                               out.print(getSeverityImageForHealth(alert.getProperty(request.getParameter("resourceid") + "#" + healthid)));
/* 4074 */                               out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4075 */                               if (_jspx_meth_c_005fout_005f28(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 4077 */                               out.write("&attributeid=");
/* 4078 */                               out.print(availabilityid);
/* 4079 */                               out.write("')\"\n      class=\"new-left-links\">");
/* 4080 */                               out.print(FormatUtil.getString("Availability"));
/* 4081 */                               out.write("</a> </td>\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4082 */                               if (_jspx_meth_c_005fout_005f29(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                 return;
/* 4084 */                               out.write("&attributeid=");
/* 4085 */                               out.print(availabilityid);
/* 4086 */                               out.write("')\">");
/* 4087 */                               out.print(getSeverityImageForAvailability(alert.getProperty(request.getParameter("resourceid") + "#" + availabilityid)));
/* 4088 */                               out.write("</a></td>\n  </tr>\n</table>\n\n<br>\n\n<br>\n");
/* 4089 */                               out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                               
/*      */ 
/*      */ 
/* 4093 */                               boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 4094 */                               if (com.adventnet.appmanager.util.EnterpriseUtil.isIt360MSPEdition)
/*      */                               {
/* 4096 */                                 showAssociatedBSG = false;
/*      */                                 
/*      */ 
/*      */ 
/* 4100 */                                 CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 4101 */                                 CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 4102 */                                 String loginName = request.getUserPrincipal().getName();
/* 4103 */                                 CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                                 
/* 4105 */                                 if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                                 {
/* 4107 */                                   showAssociatedBSG = true;
/*      */                                 }
/*      */                               }
/* 4110 */                               String monitorType = request.getParameter("type");
/* 4111 */                               ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 4112 */                               boolean mon = conf1.isConfMonitor(monitorType);
/* 4113 */                               if (showAssociatedBSG)
/*      */                               {
/* 4115 */                                 Hashtable associatedmgs = new Hashtable();
/* 4116 */                                 String resId = request.getParameter("resourceid");
/* 4117 */                                 request.setAttribute("associatedmgs", com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resId, request));
/* 4118 */                                 if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                                 {
/* 4120 */                                   mon = false;
/*      */                                 }
/*      */                                 
/* 4123 */                                 if (!mon)
/*      */                                 {
/* 4125 */                                   out.write(10);
/* 4126 */                                   out.write(10);
/*      */                                   
/* 4128 */                                   IfTag _jspx_th_c_005fif_005f20 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4129 */                                   _jspx_th_c_005fif_005f20.setPageContext(_jspx_page_context);
/* 4130 */                                   _jspx_th_c_005fif_005f20.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 4132 */                                   _jspx_th_c_005fif_005f20.setTest("${!empty associatedmgs}");
/* 4133 */                                   int _jspx_eval_c_005fif_005f20 = _jspx_th_c_005fif_005f20.doStartTag();
/* 4134 */                                   if (_jspx_eval_c_005fif_005f20 != 0) {
/*      */                                     for (;;) {
/* 4136 */                                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 4137 */                                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4138 */                                       out.write("</td>\n        </tr>\n        ");
/*      */                                       
/* 4140 */                                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4141 */                                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 4142 */                                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f20);
/*      */                                       
/* 4144 */                                       _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                       
/* 4146 */                                       _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                       
/* 4148 */                                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 4149 */                                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                       try {
/* 4151 */                                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 4152 */                                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                           for (;;) {
/* 4154 */                                             out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 4155 */                                             if (_jspx_meth_c_005fout_005f30(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4213 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 4157 */                                             out.write("&method=showApplication\" title=\"");
/* 4158 */                                             if (_jspx_meth_c_005fout_005f31(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4213 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 4160 */                                             out.write("\"  class=\"new-left-links\">\n         ");
/* 4161 */                                             if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4213 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 4163 */                                             out.write("\n    \t");
/* 4164 */                                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4165 */                                             out.write("\n         </a></td>\n        <td>");
/*      */                                             
/* 4167 */                                             PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4168 */                                             _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4169 */                                             _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                             
/* 4171 */                                             _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 4172 */                                             int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4173 */                                             if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                               for (;;) {
/* 4175 */                                                 out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 4176 */                                                 if (_jspx_meth_c_005fout_005f33(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4213 */                                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                 }
/* 4178 */                                                 out.write(39);
/* 4179 */                                                 out.write(44);
/* 4180 */                                                 out.write(39);
/* 4181 */                                                 out.print(resId);
/* 4182 */                                                 out.write(39);
/* 4183 */                                                 out.write(44);
/* 4184 */                                                 out.write(39);
/* 4185 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4186 */                                                 out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 4187 */                                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4188 */                                                 out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 4189 */                                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 4190 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4194 */                                             if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 4195 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4213 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 4198 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 4199 */                                             out.write("</td>\n        </tr>\n\t");
/* 4200 */                                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 4201 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4205 */                                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4213 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Throwable _jspx_exception)
/*      */                                       {
/*      */                                         for (;;)
/*      */                                         {
/* 4209 */                                           int tmp12368_12367 = 0; int[] tmp12368_12365 = _jspx_push_body_count_c_005fforEach_005f0; int tmp12370_12369 = tmp12368_12365[tmp12368_12367];tmp12368_12365[tmp12368_12367] = (tmp12370_12369 - 1); if (tmp12370_12369 <= 0) break;
/* 4210 */                                           out = _jspx_page_context.popBody(); }
/* 4211 */                                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                       } finally {
/* 4213 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 4214 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                       }
/* 4216 */                                       out.write("\n      </table>\n ");
/* 4217 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f20.doAfterBody();
/* 4218 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4222 */                                   if (_jspx_th_c_005fif_005f20.doEndTag() == 5) {
/* 4223 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20); return;
/*      */                                   }
/*      */                                   
/* 4226 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f20);
/* 4227 */                                   out.write(10);
/* 4228 */                                   out.write(32);
/*      */                                   
/* 4230 */                                   IfTag _jspx_th_c_005fif_005f21 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4231 */                                   _jspx_th_c_005fif_005f21.setPageContext(_jspx_page_context);
/* 4232 */                                   _jspx_th_c_005fif_005f21.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 4234 */                                   _jspx_th_c_005fif_005f21.setTest("${empty associatedmgs}");
/* 4235 */                                   int _jspx_eval_c_005fif_005f21 = _jspx_th_c_005fif_005f21.doStartTag();
/* 4236 */                                   if (_jspx_eval_c_005fif_005f21 != 0) {
/*      */                                     for (;;) {
/* 4238 */                                       out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 4239 */                                       out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 4240 */                                       out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 4241 */                                       out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 4242 */                                       out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 4243 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f21.doAfterBody();
/* 4244 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4248 */                                   if (_jspx_th_c_005fif_005f21.doEndTag() == 5) {
/* 4249 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21); return;
/*      */                                   }
/*      */                                   
/* 4252 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f21);
/* 4253 */                                   out.write(10);
/* 4254 */                                   out.write(32);
/* 4255 */                                   out.write(10);
/*      */ 
/*      */                                 }
/* 4258 */                                 else if (mon)
/*      */                                 {
/*      */ 
/*      */ 
/* 4262 */                                   out.write(10);
/*      */                                   
/* 4264 */                                   IfTag _jspx_th_c_005fif_005f22 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4265 */                                   _jspx_th_c_005fif_005f22.setPageContext(_jspx_page_context);
/* 4266 */                                   _jspx_th_c_005fif_005f22.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                                   
/* 4268 */                                   _jspx_th_c_005fif_005f22.setTest("${!empty associatedmgs}");
/* 4269 */                                   int _jspx_eval_c_005fif_005f22 = _jspx_th_c_005fif_005f22.doStartTag();
/* 4270 */                                   if (_jspx_eval_c_005fif_005f22 != 0) {
/*      */                                     for (;;) {
/* 4272 */                                       out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b>");
/* 4273 */                                       if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f22, _jspx_page_context))
/*      */                                         return;
/* 4275 */                                       out.write("</b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                       
/* 4277 */                                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 4278 */                                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 4279 */                                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f22);
/*      */                                       
/* 4281 */                                       _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                       
/* 4283 */                                       _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                       
/* 4285 */                                       _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 4286 */                                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                       try {
/* 4288 */                                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 4289 */                                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                           for (;;) {
/* 4291 */                                             out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 4292 */                                             if (_jspx_meth_c_005fout_005f34(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                             }
/* 4294 */                                             out.write("&method=showApplication\" title=\"");
/* 4295 */                                             if (_jspx_meth_c_005fout_005f35(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                             }
/* 4297 */                                             out.write("\"  class=\"staticlinks\">\n         ");
/* 4298 */                                             if (_jspx_meth_c_005fset_005f4(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                             }
/* 4300 */                                             out.write("\n    \t");
/* 4301 */                                             out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 4302 */                                             out.write("</a></span>\t\n\t\t ");
/*      */                                             
/* 4304 */                                             PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4305 */                                             _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 4306 */                                             _jspx_th_logic_005fpresent_005f12.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                             
/* 4308 */                                             _jspx_th_logic_005fpresent_005f12.setRole("ADMIN");
/* 4309 */                                             int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 4310 */                                             if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                                               for (;;) {
/* 4312 */                                                 out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 4313 */                                                 if (_jspx_meth_c_005fout_005f37(_jspx_th_logic_005fpresent_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                 }
/* 4315 */                                                 out.write(39);
/* 4316 */                                                 out.write(44);
/* 4317 */                                                 out.write(39);
/* 4318 */                                                 out.print(resId);
/* 4319 */                                                 out.write(39);
/* 4320 */                                                 out.write(44);
/* 4321 */                                                 out.write(39);
/* 4322 */                                                 out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 4323 */                                                 out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 4324 */                                                 out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 4325 */                                                 out.write("\"  title=\"");
/* 4326 */                                                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f12, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                                   _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                 }
/* 4328 */                                                 out.write("\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 4329 */                                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 4330 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 4334 */                                             if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 4335 */                                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/*      */                                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                             }
/* 4338 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 4339 */                                             out.write("\n\n\t\t \t");
/* 4340 */                                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 4341 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 4345 */                                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4353 */                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Throwable _jspx_exception)
/*      */                                       {
/*      */                                         for (;;)
/*      */                                         {
/* 4349 */                                           int tmp13394_13393 = 0; int[] tmp13394_13391 = _jspx_push_body_count_c_005fforEach_005f1; int tmp13396_13395 = tmp13394_13391[tmp13394_13393];tmp13394_13391[tmp13394_13393] = (tmp13396_13395 - 1); if (tmp13396_13395 <= 0) break;
/* 4350 */                                           out = _jspx_page_context.popBody(); }
/* 4351 */                                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                       } finally {
/* 4353 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 4354 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                       }
/* 4356 */                                       out.write("\n\t\n\t\t\t</td>\n\t ");
/* 4357 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f22.doAfterBody();
/* 4358 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4362 */                                   if (_jspx_th_c_005fif_005f22.doEndTag() == 5) {
/* 4363 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22); return;
/*      */                                   }
/*      */                                   
/* 4366 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f22);
/* 4367 */                                   out.write(10);
/* 4368 */                                   out.write(32);
/* 4369 */                                   if (_jspx_meth_c_005fif_005f23(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                     return;
/* 4371 */                                   out.write(32);
/* 4372 */                                   out.write(10);
/*      */                                 }
/*      */                                 
/*      */                               }
/* 4376 */                               else if (mon)
/*      */                               {
/*      */ 
/* 4379 */                                 out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 4380 */                                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                                   return;
/* 4382 */                                 out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                               }
/*      */                               
/*      */ 
/* 4386 */                               out.write(9);
/* 4387 */                               out.write(9);
/* 4388 */                               out.write(10);
/*      */                               
/* 4390 */                               ArrayList menupos = new ArrayList(5);
/* 4391 */                               menupos.add("350");
/* 4392 */                               pageContext.setAttribute("menupos", menupos);
/*      */                               
/* 4394 */                               out.write(10);
/* 4395 */                               out.write("\n    ");
/* 4396 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 4397 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 4400 */                             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 4401 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 4404 */                           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 4405 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                           }
/*      */                           
/* 4408 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 4409 */                           out.write("\n    ");
/*      */                           
/* 4411 */                           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 4412 */                           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 4413 */                           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                           
/* 4415 */                           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                           
/* 4417 */                           _jspx_th_tiles_005fput_005f3.setType("string");
/* 4418 */                           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 4419 */                           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 4420 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4421 */                               out = _jspx_page_context.pushBody();
/* 4422 */                               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 4423 */                               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 4426 */                               out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n\t");
/*      */                               
/* 4428 */                               Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 4429 */                               String aid = request.getParameter("haid");
/* 4430 */                               String haName = null;
/* 4431 */                               if (aid != null)
/*      */                               {
/* 4433 */                                 haName = (String)ht.get(aid);
/*      */                               }
/*      */                               
/* 4436 */                               out.write(10);
/* 4437 */                               out.write(9);
/*      */                               
/* 4439 */                               IfTag _jspx_th_c_005fif_005f24 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4440 */                               _jspx_th_c_005fif_005f24.setPageContext(_jspx_page_context);
/* 4441 */                               _jspx_th_c_005fif_005f24.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 4443 */                               _jspx_th_c_005fif_005f24.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 4444 */                               int _jspx_eval_c_005fif_005f24 = _jspx_th_c_005fif_005f24.doStartTag();
/* 4445 */                               if (_jspx_eval_c_005fif_005f24 != 0) {
/*      */                                 for (;;) {
/* 4447 */                                   out.write("\n        <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 4448 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHomePage(request));
/* 4449 */                                   out.write(" &gt; ");
/* 4450 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getHAPage(request.getParameter("haid"), haName));
/* 4451 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 4452 */                                   out.print(resourceName);
/* 4453 */                                   out.write(" </span></td>\n\t");
/* 4454 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f24.doAfterBody();
/* 4455 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4459 */                               if (_jspx_th_c_005fif_005f24.doEndTag() == 5) {
/* 4460 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24); return;
/*      */                               }
/*      */                               
/* 4463 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f24);
/* 4464 */                               out.write(10);
/* 4465 */                               out.write(9);
/*      */                               
/* 4467 */                               IfTag _jspx_th_c_005fif_005f25 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4468 */                               _jspx_th_c_005fif_005f25.setPageContext(_jspx_page_context);
/* 4469 */                               _jspx_th_c_005fif_005f25.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 4471 */                               _jspx_th_c_005fif_005f25.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 4472 */                               int _jspx_eval_c_005fif_005f25 = _jspx_th_c_005fif_005f25.doStartTag();
/* 4473 */                               if (_jspx_eval_c_005fif_005f25 != 0) {
/*      */                                 for (;;) {
/* 4475 */                                   out.write("\t\n      <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 4476 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorsPage());
/* 4477 */                                   out.write(" &gt; ");
/* 4478 */                                   out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getMonitorResourceTypes(bcrumb));
/* 4479 */                                   out.write(" &gt; <span class=\"bcactive\"> ");
/* 4480 */                                   out.print(resourceName);
/* 4481 */                                   out.write("</span></td>\n\t");
/* 4482 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f25.doAfterBody();
/* 4483 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4487 */                               if (_jspx_th_c_005fif_005f25.doEndTag() == 5) {
/* 4488 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25); return;
/*      */                               }
/*      */                               
/* 4491 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f25);
/* 4492 */                               out.write("\n    </tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>    \n    \n\n\t");
/*      */                               
/* 4494 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.get(FormTag.class);
/* 4495 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 4496 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                               
/* 4498 */                               _jspx_th_html_005fform_005f0.setAction("/updateScript");
/*      */                               
/* 4500 */                               _jspx_th_html_005fform_005f0.setFocus("displayname");
/*      */                               
/* 4502 */                               _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 4503 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 4504 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 4506 */                                   out.write(32);
/* 4507 */                                   out.write(10);
/* 4508 */                                   out.write(9);
/* 4509 */                                   if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 4511 */                                   out.write("\n        <input type=\"hidden\" name=\"table_row\" value=1/>\n\t");
/* 4512 */                                   if (_jspx_meth_am_005fhiddenparam_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 4514 */                                   out.write(10);
/* 4515 */                                   out.write(9);
/* 4516 */                                   if (_jspx_meth_am_005fhiddenparam_005f2(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 4518 */                                   out.write("\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n");
/* 4519 */                                   if ((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) {
/* 4520 */                                     out.write("\n <input type=\"hidden\" name=\"method\" value=\"updateFile\"/>\n  <input type=\"hidden\" name=\"mtype\" value='");
/* 4521 */                                     out.print(request.getParameter("mtype"));
/* 4522 */                                     out.write("'/>\n<tr>\n<td width=\"100%\" height=\"29\" class=\"tableheading\">");
/* 4523 */                                     out.print(FormatUtil.getString("am.webclient.filedirectory.config"));
/* 4524 */                                     out.write("</td>\n</tr>\n");
/*      */                                   } else {
/* 4526 */                                     out.write("\n <input type=\"hidden\" name=\"method\" value=\"updateScript\"/>\n <input type=\"hidden\" name=\"basetype\" value=\"");
/* 4527 */                                     out.print(basetype);
/* 4528 */                                     out.write("\"/>\n <input type=\"hidden\" name=\"baseid\" value=\"");
/* 4529 */                                     out.print(baseid1);
/* 4530 */                                     out.write("\"/>\n\t\n        ");
/*      */                                     
/* 4532 */                                     if ((customType != null) && (customType.equals("true")))
/*      */                                     {
/*      */ 
/* 4535 */                                       out.write("\n        <input type=\"hidden\" name=\"customType\" value=\"true\">\n        ");
/*      */                                     }
/*      */                                     
/*      */ 
/* 4539 */                                     out.write("\n<tr>\n<td width=\"100%\" height=\"29\" class=\"tableheading\">");
/* 4540 */                                     out.print(FormatUtil.getString("am.webclient.newscript.alert.configurescriptmonitor.text"));
/* 4541 */                                     out.write("</td>\n</tr>\n");
/*      */                                   }
/* 4543 */                                   out.write("\n\n</table>\n<input type=\"hidden\" name=\"wmihost\"/>\n<table width=\"99%\" border=\"0\" cellpadding=\"6\" cellspacing=\"0\" class=\"lrborder\">\n  <tr> \n    <td height=\"20\" class=\"bodytext label-align\" width=\"25%\">");
/* 4544 */                                   out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 4545 */                                   out.write("<span class=\"mandatory\">*</span></td>\n    <td height=\"20\" colspan=\"2\" width=\"75%\"> ");
/* 4546 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 4548 */                                   out.write("</td> \n    <td></td>\n  </tr>\n\n");
/* 4549 */                                   if ((resourcetype.equals("file")) || (resourcetype.equals("directory")) || (resourcetype.equals("File System Monitor"))) {
/* 4550 */                                     out.write("\n<tr>\n<td class=\"bodytext label-align\" width=\"25%\"> ");
/* 4551 */                                     out.print(FormatUtil.getString("Type"));
/* 4552 */                                     out.write("</td>\n<td class=\"bodytext\" width=\"75%\" valign=\"middle\">\n\t<table width=\"99%\" cellspacing=\"0\">\n\t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\"> ");
/* 4553 */                                     if (_jspx_meth_html_005fradio_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4555 */                                     out.write(" </td>\n\t\t\t  <td height=\"20\" width=\"13%\" class=\"bodytext\">");
/* 4556 */                                     out.print(FormatUtil.getString("File"));
/* 4557 */                                     out.write("</td>\n\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">");
/* 4558 */                                     if (_jspx_meth_html_005fradio_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                       return;
/* 4560 */                                     out.write("</td>\n\t\t\t  <td height=\"20\" width=\"85%\" class=\"bodytext\">");
/* 4561 */                                     out.print(FormatUtil.getString("Directory"));
/* 4562 */                                     out.write("</td>\n\t\t</tr>\n\t </table>\n</td>\n</tr>\n<tr>                                                                                                                          <td class=\"bodytext label-align\" width=\"25%\"><div id=\"location\">");
/* 4563 */                                     out.print(FormatUtil.getString("File Location"));
/* 4564 */                                     out.write("</div></td>\n\n");
/* 4565 */                                   } else if (!resourcetype.equals("QENGINE")) {
/* 4566 */                                     out.write("\n<tr>                                                                                                                          <td class=\"bodytext label-align\" width=\"25%\"><div id=\"location\">");
/* 4567 */                                     out.print(FormatUtil.getString("Script Location"));
/* 4568 */                                     out.write("</div></td>\n");
/*      */                                   }
/* 4570 */                                   out.write("\n\n  ");
/*      */                                   
/* 4572 */                                   IfTag _jspx_th_c_005fif_005f26 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4573 */                                   _jspx_th_c_005fif_005f26.setPageContext(_jspx_page_context);
/* 4574 */                                   _jspx_th_c_005fif_005f26.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 4576 */                                   _jspx_th_c_005fif_005f26.setTest("${param.type!='QENGINE'}");
/* 4577 */                                   int _jspx_eval_c_005fif_005f26 = _jspx_th_c_005fif_005f26.doStartTag();
/* 4578 */                                   if (_jspx_eval_c_005fif_005f26 != 0) {
/*      */                                     for (;;) {
/* 4580 */                                       out.write("\n          <td class=\"bodytext\" colspan=\"2\" width=\"75%\" valign=\"middle\">\n\t          <table width=\"99%\" cellspacing=\"0\">\n\t\t\t\t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">\n\t\t\t\t\t\t  \t");
/* 4581 */                                       if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.localscript.remove")) {
/* 4582 */                                         out.write(" \n\t\t\t\t\t\t  \t\t");
/* 4583 */                                         if (_jspx_meth_html_005fradio_005f2(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4585 */                                         out.write("</td>\n\t\t\t\t\t\t  <td height=\"20\" width=\"14%\" class=\"bodytext\">");
/* 4586 */                                         out.print(FormatUtil.getString("am.webclient.script.localserver"));
/* 4587 */                                         out.write("\n\t\t\t\t\t\t  \t");
/*      */                                       }
/* 4589 */                                       out.write("\n\t\t\t\t\t\t  </td>\n\t\t\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">");
/* 4590 */                                       if (_jspx_meth_html_005fradio_005f3(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                         return;
/* 4592 */                                       out.write("</td>\n\t\t\t\t\t\t  <td height=\"20\" width=\"81%\" class=\"bodytext\">");
/* 4593 */                                       out.print(FormatUtil.getString("am.webclient.script.remoteserver"));
/* 4594 */                                       out.write("</td>\n\t\t\t\t\t</tr>\n\t\t\t  </table>\n          </td>\n   </tr>\n   \n   <tr id=\"remotescript\" style=\"display:table-row;\">\n  \t <td class=\"bodytext label-align\" width=\"25%\">\t");
/* 4595 */                                       out.print(FormatUtil.getString("am.webclient.script.choosehost"));
/* 4596 */                                       out.write(" </td>  \n \t <td width=\"75%\" colspan=\"2\">\n    \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n\t      \t<td>\n\t        \n\t\t        ");
/*      */                                       
/* 4598 */                                       SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4599 */                                       _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 4600 */                                       _jspx_th_html_005fselect_005f0.setParent(_jspx_th_c_005fif_005f26);
/*      */                                       
/* 4602 */                                       _jspx_th_html_005fselect_005f0.setProperty("choosehost");
/*      */                                       
/* 4604 */                                       _jspx_th_html_005fselect_005f0.setStyleClass("formtext default");
/*      */                                       
/* 4606 */                                       _jspx_th_html_005fselect_005f0.setOnchange("javascript:managenewHost();");
/* 4607 */                                       int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 4608 */                                       if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 4609 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4610 */                                           out = _jspx_page_context.pushBody();
/* 4611 */                                           _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 4612 */                                           _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4615 */                                           out.write("\n\t\t        ");
/*      */                                           
/* 4617 */                                           String hostquery = null;
/*      */                                           try
/*      */                                           {
/* 4620 */                                             String localhostaddr = InetAddress.getLocalHost().getHostName();
/* 4621 */                                             if ((resourcetype.equals("file")) || (resourcetype.equals("directory")) || (resourcetype.equals("File System Monitor")))
/*      */                                             {
/*      */ 
/* 4624 */                                               if (System.getProperty("os.name").startsWith("Windows"))
/*      */                                               {
/* 4626 */                                                 hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY','WMI') and h.RESOURCENAME not like '" + localhostaddr + "%' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY','WMI') order by RESOURCENAME";
/*      */                                               }
/*      */                                               else {
/* 4629 */                                                 hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') order by RESOURCENAME";
/*      */                                               }
/*      */                                               
/*      */                                             }
/*      */                                             else {
/* 4634 */                                               hostquery = "select m.RESOURCEID,m.RESOURCENAME,h.USERNAME,2 as flag from HostDetails h,AM_ManagedObject m,AM_HOSTINFO inf where h.RESOURCENAME=m.RESOURCENAME and h.COMPONENTNAME='HOST' and m.RESOURCEID=inf.RESOURCEID and inf.MODE in ('TELNET','SSH','SSH_KEY') and h.RESOURCENAME not like '" + localhostaddr + "%' UNION select ID,HOSTNAME,USERNAME,1 as flag from AM_SCRIPTHOSTDETAILS where MODE in ('TELNET','SSH','SSH_KEY') order by RESOURCENAME";
/*      */                                             }
/*      */                                             
/* 4637 */                                             AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */                                             
/*      */ 
/* 4640 */                                             List hostinfo = new ArrayList();
/* 4641 */                                             ArrayList arr1 = new ArrayList();
/* 4642 */                                             String optval = null;String hostIp = null;
/*      */                                             
/* 4644 */                                             ResultSet rs = AMConnectionPool.executeQueryStmt(hostquery);
/* 4645 */                                             while (rs.next())
/*      */                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                               try
/*      */                                               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 4657 */                                                 arr1.add(rs.getString(1));
/* 4658 */                                                 arr1.add(rs.getString(2));
/* 4659 */                                                 arr1.add(rs.getString(3));
/* 4660 */                                                 arr1.add(rs.getString(4));
/* 4661 */                                                 hostinfo.add(arr1);
/* 4662 */                                                 arr1 = new ArrayList();
/*      */                                               }
/*      */                                               catch (Exception ex)
/*      */                                               {
/* 4666 */                                                 ex.printStackTrace();
/*      */                                               }
/*      */                                             }
/* 4669 */                                             Iterator it = hostinfo.iterator();
/*      */                                             
/* 4671 */                                             while (it.hasNext())
/*      */                                             {
/* 4673 */                                               ArrayList arrlist = (ArrayList)it.next();
/* 4674 */                                               optval = arrlist.get(3) + "," + arrlist.get(0);
/*      */                                               
/*      */ 
/* 4677 */                                               out.write("\n\t\t           ");
/*      */                                               
/* 4679 */                                               OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.get(OptionTag.class);
/* 4680 */                                               _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 4681 */                                               _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                               
/* 4683 */                                               _jspx_th_html_005foption_005f0.setValue(optval);
/* 4684 */                                               int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 4685 */                                               if (_jspx_eval_html_005foption_005f0 != 0) {
/* 4686 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 4687 */                                                   out = _jspx_page_context.pushBody();
/* 4688 */                                                   _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 4689 */                                                   _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 4692 */                                                   out.print(arrlist.get(1));
/* 4693 */                                                   out.write(32);
/* 4694 */                                                   out.write(40);
/* 4695 */                                                   out.print(arrlist.get(2));
/* 4696 */                                                   out.write(41);
/* 4697 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 4698 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4701 */                                                 if (_jspx_eval_html_005foption_005f0 != 1) {
/* 4702 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4705 */                                               if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 4706 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                               }
/*      */                                               
/* 4709 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue.reuse(_jspx_th_html_005foption_005f0);
/* 4710 */                                               out.write("\n\t\t           ");
/*      */                                             }
/*      */                                             
/* 4713 */                                             AMConnectionPool.closeStatement(rs);
/*      */                                           }
/*      */                                           catch (Exception exc)
/*      */                                           {
/* 4717 */                                             exc.printStackTrace();
/*      */                                           }
/*      */                                           
/* 4720 */                                           out.write("\n\t\t        ");
/* 4721 */                                           int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 4722 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4725 */                                         if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 4726 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4729 */                                       if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 4730 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                       }
/*      */                                       
/* 4733 */                                       this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 4734 */                                       out.write(" <a href=\"javascript:Edithostdetails()\" class=\"staticlinks\">");
/* 4735 */                                       out.print(FormatUtil.getString("am.webclient.newscript.edithostdetails.text"));
/* 4736 */                                       out.write("</a>\t\n\t        </td>       \t\t\n        </tr>\n    \t</table>\n\t</td>\n </tr>\n \n         \n              ");
/* 4737 */                                       if ((resourcetype.equals("File System Monitor")) || (resourcetype.equals("file")) || (resourcetype.equals("directory"))) {
/* 4738 */                                         out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"25%\" height=\"35\" class=\"bodytext label-align\"><div id=\"name\">");
/* 4739 */                                         out.print(FormatUtil.getString("File Name"));
/* 4740 */                                         out.write("</div>\n\t\t\t\t\t</td>\n\t\t\t\t\t<td height=\"35\" width=\"75%\" colspan=\"3\" class=\"bodytext\">");
/* 4741 */                                         if (_jspx_meth_html_005ftext_005f1(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4743 */                                         out.write("\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"content\" style=\"display: table-row;\">\n\t\t\t\t\t<td height=\"20\" width=\"25%\" class=\"bodytext label-align\">\n\t\t\t\t\t\t");
/* 4744 */                                         out.print(FormatUtil.getString("am.webclient.filedir.doconchk.text"));
/* 4745 */                                         out.write("\n\t\t\t\t\t\t\t\t</td>\n                    <td height=\"20\">");
/* 4746 */                                         if (_jspx_meth_html_005fcheckbox_005f0(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4748 */                                         out.write("</td> \n\t\t\t\t</tr>\n\n\t\t\t\t<tr id=\"contentChk11\" class=\"bg-lite\" style=\"display: none; padding-left: 5px; padding-top: 10px; padding-botton: 15px;\">\n\t\t\t\t\t<td colspan=\"2\" class=\"dottedline\">\n\t\t\t\t\t\t<table width=\"100%\" cellpadding=\"6\" cellspacing=\"0\">\n\t\t\t\t\t\t \t<tr style=\"padding-top:5px\">\n\t\t\t\t\t\t    \t<td class=\"bodytext label-align addmonitor-label\" width=\"25%\" height=\"35\"><label>");
/* 4749 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conParseType.text"));
/* 4750 */                                         out.write("</label></td>\n\t\t\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"middle\" height=\"35\">\n\t\t\t\t\t\t\t\t\t <table width=\"99%\" cellspacing=\"0\">\n\t\t\t\t\t\t  \t\t \t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t\t\t\t\t  \t\t \t\t <td height=\"20\" width=\"2%\" class=\"bodytext alignTdata\">  \n\t\t\t\t\t\t\t  \t\t \t\t \t");
/* 4751 */                                         if (_jspx_meth_html_005fradio_005f4(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4753 */                                         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4754 */                                         out.print(FormatUtil.getString("am.webclient.filedir.parseAppended.text"));
/* 4755 */                                         out.write(" &nbsp; &nbsp;\n                                                ");
/* 4756 */                                         if (_jspx_meth_html_005fradio_005f5(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4758 */                                         out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4759 */                                         out.print(FormatUtil.getString("am.webclient.filedir.parseWhole.text"));
/* 4760 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t  \t  \t\t</tr>\n\t\t\t\t\t  \t\t \t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\t\n\t\t\t\t\t\t    <tr style=\"padding-top:5px\">\n\t\t\t\t\t\t    \t<td  height=\"35\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 4761 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conchk.text"));
/* 4762 */                                         out.write("&nbsp;<span class=\"mandatory\">*</span></label></td>\n\t\t\t\t\t\t \t\t<td  height=\"35\"  class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t<table width=\"99%\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t<tr style=\"padding-top:5px\" colspan=2>\n\t\t\t\t\t\t\t\t\t\t\t<td height=\"20\" width=\"50%\" class=\"bodytext alignTdata\">\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4763 */                                         if (_jspx_meth_html_005ftextarea_005f0(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4765 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4766 */                                         if (_jspx_meth_html_005fcheckbox_005f1(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4768 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4769 */                                         out.print(FormatUtil.getString("am.webclient.filedir.regexp.text"));
/* 4770 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\n                            <tr id=\"checkEmptyId\" style=\"display: table-row;\">\n            \t\t\t\t\t<td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td> \n            \t\t\t\t\t<td colspan=\"4\">");
/* 4771 */                                         if (_jspx_meth_html_005fcheckbox_005f2(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4773 */                                         out.print(FormatUtil.getString("am.webclient.filedir.donotCheckEmptyContent"));
/* 4774 */                                         out.write("</td> \n       \t\t\t\t\t    </tr>\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t    \t<td  height=\"35\" width=\"25%\" class=\"bodytext label-align addmonitor-label\"><label>");
/* 4775 */                                         out.print(FormatUtil.getString("am.webclient.filedir.monstatus.text"));
/* 4776 */                                         out.write("</label></td>\n\t\t\t\t\t\t \t\t<td  height=\"35\" width=\"75%\" colspan=\"5\" class=\"bodytext alignTdata\">\n\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t\t<td width=\"10%\" class=\"alignTdata\">\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4778 */                                         SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4779 */                                         _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 4780 */                                         _jspx_th_html_005fselect_005f1.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 4782 */                                         _jspx_th_html_005fselect_005f1.setProperty("selectStatusType");
/*      */                                         
/* 4784 */                                         _jspx_th_html_005fselect_005f1.setStyleClass("formtext");
/*      */                                         
/* 4786 */                                         _jspx_th_html_005fselect_005f1.setOnchange("javascript:showClearCondnTitle()");
/* 4787 */                                         int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 4788 */                                         if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 4789 */                                           if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4790 */                                             out = _jspx_page_context.pushBody();
/* 4791 */                                             _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 4792 */                                             _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4795 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t  ");
/*      */                                             
/* 4797 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 4798 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 4799 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                             
/* 4801 */                                             _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.filedir.down.text"));
/*      */                                             
/* 4803 */                                             _jspx_th_html_005foption_005f1.setValue("0");
/* 4804 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 4805 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 4806 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 4809 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/* 4810 */                                             out.write("  \t \n\t\t\t\t     \t\t\t\t\t\t\t\t  ");
/*      */                                             
/* 4812 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 4813 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 4814 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                             
/* 4816 */                                             _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.filedir.up.text"));
/*      */                                             
/* 4818 */                                             _jspx_th_html_005foption_005f2.setValue("1");
/* 4819 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 4820 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 4821 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                             }
/*      */                                             
/* 4824 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/* 4825 */                                             out.write(" \n\t\t\t\t\t   \t\t\t\t\t\t\t\t    \t \n\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4826 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 4827 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4830 */                                           if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 4831 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4834 */                                         if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 4835 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                         }
/*      */                                         
/* 4838 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 4839 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"regexChk1\"  style=\"display:none;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td  height=\"20\" class=\"bodytext\" align=\"left\" >");
/* 4840 */                                         out.print(FormatUtil.getString("am.webclient.filedir.Regex.conmatch.text"));
/* 4841 */                                         out.write(".</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"NormalChk\" style=\"display: block;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr >\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" height=\"20\" class=\"bodytext alignTdata\" align='left'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span>");
/* 4842 */                                         out.print(FormatUtil.getString("am.webclient.filedir.if.text"));
/* 4843 */                                         out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4845 */                                         SelectTag _jspx_th_html_005fselect_005f2 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4846 */                                         _jspx_th_html_005fselect_005f2.setPageContext(_jspx_page_context);
/* 4847 */                                         _jspx_th_html_005fselect_005f2.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 4849 */                                         _jspx_th_html_005fselect_005f2.setProperty("selectRuleType");
/*      */                                         
/* 4851 */                                         _jspx_th_html_005fselect_005f2.setStyleClass("formtext");
/*      */                                         
/* 4853 */                                         _jspx_th_html_005fselect_005f2.setOnchange("javascript:loadTextBox()");
/* 4854 */                                         int _jspx_eval_html_005fselect_005f2 = _jspx_th_html_005fselect_005f2.doStartTag();
/* 4855 */                                         if (_jspx_eval_html_005fselect_005f2 != 0) {
/* 4856 */                                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4857 */                                             out = _jspx_page_context.pushBody();
/* 4858 */                                             _jspx_th_html_005fselect_005f2.setBodyContent((BodyContent)out);
/* 4859 */                                             _jspx_th_html_005fselect_005f2.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4862 */                                             out.write("  \t \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 4864 */                                             OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 4865 */                                             _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 4866 */                                             _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                             
/* 4868 */                                             _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.filedir.any.text"));
/*      */                                             
/* 4870 */                                             _jspx_th_html_005foption_005f3.setValue("0");
/* 4871 */                                             int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 4872 */                                             if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 4873 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                             }
/*      */                                             
/* 4876 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/* 4877 */                                             out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 4879 */                                             OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 4880 */                                             _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 4881 */                                             _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f2);
/*      */                                             
/* 4883 */                                             _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.filedir.all.text"));
/*      */                                             
/* 4885 */                                             _jspx_th_html_005foption_005f4.setValue("1");
/* 4886 */                                             int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 4887 */                                             if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 4888 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                             }
/*      */                                             
/* 4891 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/* 4892 */                                             out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4893 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f2.doAfterBody();
/* 4894 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4897 */                                           if (_jspx_eval_html_005fselect_005f2 != 1) {
/* 4898 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4901 */                                         if (_jspx_th_html_005fselect_005f2.doEndTag() == 5) {
/* 4902 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2); return;
/*      */                                         }
/*      */                                         
/* 4905 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f2);
/* 4906 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"cntval11\" width=\"7%\" class=\"bodytext\" align='left'>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"selectValue\" style=\"Display:none\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4908 */                                         SelectTag _jspx_th_html_005fselect_005f3 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 4909 */                                         _jspx_th_html_005fselect_005f3.setPageContext(_jspx_page_context);
/* 4910 */                                         _jspx_th_html_005fselect_005f3.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 4912 */                                         _jspx_th_html_005fselect_005f3.setProperty("countval");
/*      */                                         
/* 4914 */                                         _jspx_th_html_005fselect_005f3.setStyleClass("formtext");
/* 4915 */                                         int _jspx_eval_html_005fselect_005f3 = _jspx_th_html_005fselect_005f3.doStartTag();
/* 4916 */                                         if (_jspx_eval_html_005fselect_005f3 != 0) {
/* 4917 */                                           if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4918 */                                             out = _jspx_page_context.pushBody();
/* 4919 */                                             _jspx_th_html_005fselect_005f3.setBodyContent((BodyContent)out);
/* 4920 */                                             _jspx_th_html_005fselect_005f3.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4923 */                                             out.write("  \t \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 4925 */                                             for (int i = 1; i < contentChkCount; i++) {
/* 4926 */                                               String cval = "" + i;
/*      */                                               
/* 4928 */                                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                               
/* 4930 */                                               OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 4931 */                                               _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 4932 */                                               _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f3);
/*      */                                               
/* 4934 */                                               _jspx_th_html_005foption_005f5.setKey(cval);
/*      */                                               
/* 4936 */                                               _jspx_th_html_005foption_005f5.setValue(cval);
/* 4937 */                                               int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 4938 */                                               if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 4939 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                               }
/*      */                                               
/* 4942 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/* 4943 */                                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             }
/* 4945 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4946 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f3.doAfterBody();
/* 4947 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4950 */                                           if (_jspx_eval_html_005fselect_005f3 != 1) {
/* 4951 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4954 */                                         if (_jspx_th_html_005fselect_005f3.doEndTag() == 5) {
/* 4955 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f3); return;
/*      */                                         }
/*      */                                         
/* 4958 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f3);
/* 4959 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 4960 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conmatch.text"));
/* 4961 */                                         out.write(".\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t    \t<td valign=\"top\" class=\"bodytext\" width=\"25%\" height=\"40\"></td>\n\t\t\t\t\t\t\t\t<td class=\"bodytext\" width=\"75%\" valign=\"top\" height=\"35\">\n\t\t\t\t\t\t\t\t<fieldset class=\"legendField\">\n\t\t\t\t  \t\t\t\t\t<legend id=\"ClearCondnUp\" style=\"display:''\">");
/* 4962 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conmatch.clear.condn.up.text"));
/* 4963 */                                         out.write("</legend>\n\t\t\t\t  \t\t\t\t\t<legend id=\"ClearCondnDown\" style=\"display:none\">");
/* 4964 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conmatch.clear.condn.down.text"));
/* 4965 */                                         out.write("</legend>\n\t\t\t\t\t\t\t\t\t\t<table width=\"99%\" cellpadding=\"0\">\n\t\t\t\t\t\t\t\t  \t\t \t<tr>\n\t\t\t\t\t\t\t\t\t  \t\t\t<td class=\"bodytext alignTdata\">\n\t\t\t\t\t\t\t\t\t\t  \t\t\t");
/* 4966 */                                         if (_jspx_meth_html_005fradio_005f6(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4968 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t  \t\t \t");
/* 4969 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conmatch.clear.not.matched.text"));
/* 4970 */                                         out.write("\n\t\t\t\t\t\t\t\t\t  \t\t \t</td>\n\t\t\t\t\t\t\t\t\t  \t\t</tr>\n\t\t\t\t\t\t\t\t\t  \t\t<tr>\n\t\t\t\t\t\t\t\t\t  \t\t\t<td height=\"40\" width=\"1%\" class=\"bodytext alignTdata\">\n\t\t\t\t\t\t\t\t\t  \t\t\t");
/* 4971 */                                         if (_jspx_meth_html_005fradio_005f7(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 4973 */                                         out.write("\n\t\t\t\t\t\t\t\t\t  \t\t \t\n\t\t\t\t\t\t\t\t\t  \t\t \t\n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"regexChk2\" style=\"display:none;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"50%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td height=\"20\" class=\"bodytext\" align=\"left\">");
/* 4974 */                                         out.print(FormatUtil.getString("am.webclient.filedir.Regex.conmatch.text"));
/* 4975 */                                         out.write(".</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"NormalChk2\" style=\"display: block;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"3%\" height=\"20\" class=\"bodytext alignTdata\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<span>");
/* 4976 */                                         out.print(FormatUtil.getString("am.webclient.filedir.if.text"));
/* 4977 */                                         out.write("</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4979 */                                         SelectTag _jspx_th_html_005fselect_005f4 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.get(SelectTag.class);
/* 4980 */                                         _jspx_th_html_005fselect_005f4.setPageContext(_jspx_page_context);
/* 4981 */                                         _jspx_th_html_005fselect_005f4.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 4983 */                                         _jspx_th_html_005fselect_005f4.setProperty("clearConditionRuleType");
/*      */                                         
/* 4985 */                                         _jspx_th_html_005fselect_005f4.setOnchange("javascript:loadTextBox1()");
/*      */                                         
/* 4987 */                                         _jspx_th_html_005fselect_005f4.setStyleClass("formtext");
/* 4988 */                                         int _jspx_eval_html_005fselect_005f4 = _jspx_th_html_005fselect_005f4.doStartTag();
/* 4989 */                                         if (_jspx_eval_html_005fselect_005f4 != 0) {
/* 4990 */                                           if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 4991 */                                             out = _jspx_page_context.pushBody();
/* 4992 */                                             _jspx_th_html_005fselect_005f4.setBodyContent((BodyContent)out);
/* 4993 */                                             _jspx_th_html_005fselect_005f4.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4996 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 4998 */                                             OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 4999 */                                             _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 5000 */                                             _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                             
/* 5002 */                                             _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.filedir.any.text"));
/*      */                                             
/* 5004 */                                             _jspx_th_html_005foption_005f6.setValue("0");
/* 5005 */                                             int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 5006 */                                             if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 5007 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                             }
/*      */                                             
/* 5010 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/* 5011 */                                             out.write(" \n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5013 */                                             OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5014 */                                             _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 5015 */                                             _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f4);
/*      */                                             
/* 5017 */                                             _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.filedir.all.text"));
/*      */                                             
/* 5019 */                                             _jspx_th_html_005foption_005f7.setValue("1");
/* 5020 */                                             int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 5021 */                                             if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 5022 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                             }
/*      */                                             
/* 5025 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/* 5026 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5027 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f4.doAfterBody();
/* 5028 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5031 */                                           if (_jspx_eval_html_005fselect_005f4 != 1) {
/* 5032 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5035 */                                         if (_jspx_th_html_005fselect_005f4.doEndTag() == 5) {
/* 5036 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4); return;
/*      */                                         }
/*      */                                         
/* 5039 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f4);
/* 5040 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"cntval22\" width=\"7%\" class=\"bodytext\" align=\"left\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<div id=\"selectValue2\" style=\"display: block;\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 5042 */                                         SelectTag _jspx_th_html_005fselect_005f5 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5043 */                                         _jspx_th_html_005fselect_005f5.setPageContext(_jspx_page_context);
/* 5044 */                                         _jspx_th_html_005fselect_005f5.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 5046 */                                         _jspx_th_html_005fselect_005f5.setProperty("clearConditionCountVal");
/*      */                                         
/* 5048 */                                         _jspx_th_html_005fselect_005f5.setStyleClass("formtext");
/* 5049 */                                         int _jspx_eval_html_005fselect_005f5 = _jspx_th_html_005fselect_005f5.doStartTag();
/* 5050 */                                         if (_jspx_eval_html_005fselect_005f5 != 0) {
/* 5051 */                                           if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5052 */                                             out = _jspx_page_context.pushBody();
/* 5053 */                                             _jspx_th_html_005fselect_005f5.setBodyContent((BodyContent)out);
/* 5054 */                                             _jspx_th_html_005fselect_005f5.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5057 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5059 */                                             for (int i = 1; i < contentChkCount; i++) {
/* 5060 */                                               String cval = "" + i;
/*      */                                               
/* 5062 */                                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                               
/* 5064 */                                               OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5065 */                                               _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 5066 */                                               _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f5);
/*      */                                               
/* 5068 */                                               _jspx_th_html_005foption_005f8.setKey(cval);
/*      */                                               
/* 5070 */                                               _jspx_th_html_005foption_005f8.setValue(cval);
/* 5071 */                                               int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 5072 */                                               if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 5073 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                               }
/*      */                                               
/* 5076 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/* 5077 */                                               out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             }
/* 5079 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5080 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f5.doAfterBody();
/* 5081 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5084 */                                           if (_jspx_eval_html_005fselect_005f5 != 1) {
/* 5085 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5088 */                                         if (_jspx_th_html_005fselect_005f5.doEndTag() == 5) {
/* 5089 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f5); return;
/*      */                                         }
/*      */                                         
/* 5092 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f5);
/* 5093 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5094 */                                         if (_jspx_meth_html_005ftextarea_005f1(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5096 */                                         out.write("<span class=\"mandatory\" style=\"vertical-align: top;\">*</span>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5097 */                                         out.print(FormatUtil.getString("am.webclient.filedir.conmatch.text"));
/* 5098 */                                         out.write(".\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t  \t\t\t</tr>\n\t\t\t\t\t\t  \t\t\t\t</table>\n\t\t\t\t\t  \t\t\t\t</fieldset>\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t</tr>\t\t\t\t\t\t\t\t\n\n\t\t\t\t\t\t\t</table>\n\n\t\t\t\t\t\t</div></td>\n\t\t\t\t</tr>\n\t\t\t\n\t\t\t\t<tr>\n\t\t\t\t\t<td height=\"20\" width=\"25%\" class=\"bodytext label-align\">");
/* 5099 */                                         out.print(FormatUtil.getString("am.webclient.fileDirAgeChk.text"));
/* 5100 */                                         out.write("</td>\n\t\t\t\t\t<td height=\"20\" width=\"75%\" class=\"bodytext\">");
/* 5101 */                                         if (_jspx_meth_html_005fcheckbox_005f3(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5103 */                                         out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\n\t\t\t\t<tr id=\"fileDirAge11\" class=\"bg-lite\" style=\"display: none;  padding-left: 5px; padding-top: 10px; padding-botton: 5px;\">\n\n\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<table width=\"99%\" cellspacing=\"0\" cellpadding=\"5\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td height=\"35\" width=\"25%\" class=\"bodytext label-align\">");
/* 5104 */                                         out.print(FormatUtil.getString("am.webclient.filedir.monstatus.text"));
/* 5105 */                                         out.write("</td>\n\t\t\t\t\t\t\t\t\t<td height=\"35\" width=\"75%\" colspan=\"6\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"5%\" height=\"20\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 5107 */                                         SelectTag _jspx_th_html_005fselect_005f6 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5108 */                                         _jspx_th_html_005fselect_005f6.setPageContext(_jspx_page_context);
/* 5109 */                                         _jspx_th_html_005fselect_005f6.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 5111 */                                         _jspx_th_html_005fselect_005f6.setProperty("selectMonStatus");
/*      */                                         
/* 5113 */                                         _jspx_th_html_005fselect_005f6.setStyleClass("formtext");
/* 5114 */                                         int _jspx_eval_html_005fselect_005f6 = _jspx_th_html_005fselect_005f6.doStartTag();
/* 5115 */                                         if (_jspx_eval_html_005fselect_005f6 != 0) {
/* 5116 */                                           if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 5117 */                                             out = _jspx_page_context.pushBody();
/* 5118 */                                             _jspx_th_html_005fselect_005f6.setBodyContent((BodyContent)out);
/* 5119 */                                             _jspx_th_html_005fselect_005f6.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5122 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5124 */                                             OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5125 */                                             _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 5126 */                                             _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f6);
/*      */                                             
/* 5128 */                                             _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.filedir.down.text"));
/*      */                                             
/* 5130 */                                             _jspx_th_html_005foption_005f9.setValue("0");
/* 5131 */                                             int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 5132 */                                             if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 5133 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                             }
/*      */                                             
/* 5136 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/* 5137 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5139 */                                             OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5140 */                                             _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 5141 */                                             _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f6);
/*      */                                             
/* 5143 */                                             _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.filedir.up.text"));
/*      */                                             
/* 5145 */                                             _jspx_th_html_005foption_005f10.setValue("1");
/* 5146 */                                             int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 5147 */                                             if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 5148 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                             }
/*      */                                             
/* 5151 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/* 5152 */                                             out.write("\n\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5153 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f6.doAfterBody();
/* 5154 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5157 */                                           if (_jspx_eval_html_005fselect_005f6 != 1) {
/* 5158 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5161 */                                         if (_jspx_th_html_005fselect_005f6.doEndTag() == 5) {
/* 5162 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f6); return;
/*      */                                         }
/*      */                                         
/* 5165 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f6);
/* 5166 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"18%\" height=\"20\" class=\"bodytext\" align='center'>");
/* 5167 */                                         out.print(FormatUtil.getString("am.webclient.fileDirAgeChk.if.text"));
/* 5168 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"18%\" height=\"20\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 5170 */                                         SelectTag _jspx_th_html_005fselect_005f7 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5171 */                                         _jspx_th_html_005fselect_005f7.setPageContext(_jspx_page_context);
/* 5172 */                                         _jspx_th_html_005fselect_005f7.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 5174 */                                         _jspx_th_html_005fselect_005f7.setProperty("selectChangeType");
/*      */                                         
/* 5176 */                                         _jspx_th_html_005fselect_005f7.setStyleClass("formtext");
/* 5177 */                                         int _jspx_eval_html_005fselect_005f7 = _jspx_th_html_005fselect_005f7.doStartTag();
/* 5178 */                                         if (_jspx_eval_html_005fselect_005f7 != 0) {
/* 5179 */                                           if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5180 */                                             out = _jspx_page_context.pushBody();
/* 5181 */                                             _jspx_th_html_005fselect_005f7.setBodyContent((BodyContent)out);
/* 5182 */                                             _jspx_th_html_005fselect_005f7.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5185 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5187 */                                             OptionTag _jspx_th_html_005foption_005f11 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5188 */                                             _jspx_th_html_005foption_005f11.setPageContext(_jspx_page_context);
/* 5189 */                                             _jspx_th_html_005foption_005f11.setParent(_jspx_th_html_005fselect_005f7);
/*      */                                             
/* 5191 */                                             _jspx_th_html_005foption_005f11.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.NotModified.text"));
/*      */                                             
/* 5193 */                                             _jspx_th_html_005foption_005f11.setValue("0");
/* 5194 */                                             int _jspx_eval_html_005foption_005f11 = _jspx_th_html_005foption_005f11.doStartTag();
/* 5195 */                                             if (_jspx_th_html_005foption_005f11.doEndTag() == 5) {
/* 5196 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11); return;
/*      */                                             }
/*      */                                             
/* 5199 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f11);
/* 5200 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5202 */                                             OptionTag _jspx_th_html_005foption_005f12 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5203 */                                             _jspx_th_html_005foption_005f12.setPageContext(_jspx_page_context);
/* 5204 */                                             _jspx_th_html_005foption_005f12.setParent(_jspx_th_html_005fselect_005f7);
/*      */                                             
/* 5206 */                                             _jspx_th_html_005foption_005f12.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.Modified.text"));
/*      */                                             
/* 5208 */                                             _jspx_th_html_005foption_005f12.setValue("1");
/* 5209 */                                             int _jspx_eval_html_005foption_005f12 = _jspx_th_html_005foption_005f12.doStartTag();
/* 5210 */                                             if (_jspx_th_html_005foption_005f12.doEndTag() == 5) {
/* 5211 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12); return;
/*      */                                             }
/*      */                                             
/* 5214 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f12);
/* 5215 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5216 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f7.doAfterBody();
/* 5217 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5220 */                                           if (_jspx_eval_html_005fselect_005f7 != 1) {
/* 5221 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5224 */                                         if (_jspx_th_html_005fselect_005f7.doEndTag() == 5) {
/* 5225 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f7); return;
/*      */                                         }
/*      */                                         
/* 5228 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f7);
/* 5229 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"8%\" class=\"bodytext\" align=\"left\">");
/* 5230 */                                         out.print(FormatUtil.getString("am.webclient.fileDirAgeChk.within.text"));
/* 5231 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"9%\" class=\"bodytext\" align=\"left\">");
/* 5232 */                                         if (_jspx_meth_html_005ftext_005f2(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5234 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t\t\t<td width=\"41%\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 5236 */                                         SelectTag _jspx_th_html_005fselect_005f8 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.get(SelectTag.class);
/* 5237 */                                         _jspx_th_html_005fselect_005f8.setPageContext(_jspx_page_context);
/* 5238 */                                         _jspx_th_html_005fselect_005f8.setParent(_jspx_th_c_005fif_005f26);
/*      */                                         
/* 5240 */                                         _jspx_th_html_005fselect_005f8.setProperty("timeUnit");
/*      */                                         
/* 5242 */                                         _jspx_th_html_005fselect_005f8.setStyleClass("formtext");
/* 5243 */                                         int _jspx_eval_html_005fselect_005f8 = _jspx_th_html_005fselect_005f8.doStartTag();
/* 5244 */                                         if (_jspx_eval_html_005fselect_005f8 != 0) {
/* 5245 */                                           if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 5246 */                                             out = _jspx_page_context.pushBody();
/* 5247 */                                             _jspx_th_html_005fselect_005f8.setBodyContent((BodyContent)out);
/* 5248 */                                             _jspx_th_html_005fselect_005f8.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 5251 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5253 */                                             OptionTag _jspx_th_html_005foption_005f13 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5254 */                                             _jspx_th_html_005foption_005f13.setPageContext(_jspx_page_context);
/* 5255 */                                             _jspx_th_html_005foption_005f13.setParent(_jspx_th_html_005fselect_005f8);
/*      */                                             
/* 5257 */                                             _jspx_th_html_005foption_005f13.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.mins.text"));
/*      */                                             
/* 5259 */                                             _jspx_th_html_005foption_005f13.setValue("Minutes");
/* 5260 */                                             int _jspx_eval_html_005foption_005f13 = _jspx_th_html_005foption_005f13.doStartTag();
/* 5261 */                                             if (_jspx_th_html_005foption_005f13.doEndTag() == 5) {
/* 5262 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13); return;
/*      */                                             }
/*      */                                             
/* 5265 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f13);
/* 5266 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                             
/* 5268 */                                             OptionTag _jspx_th_html_005foption_005f14 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 5269 */                                             _jspx_th_html_005foption_005f14.setPageContext(_jspx_page_context);
/* 5270 */                                             _jspx_th_html_005foption_005f14.setParent(_jspx_th_html_005fselect_005f8);
/*      */                                             
/* 5272 */                                             _jspx_th_html_005foption_005f14.setKey(FormatUtil.getString("am.webclient.fileDirAgeChk.hrs.text"));
/*      */                                             
/* 5274 */                                             _jspx_th_html_005foption_005f14.setValue("Hours");
/* 5275 */                                             int _jspx_eval_html_005foption_005f14 = _jspx_th_html_005foption_005f14.doStartTag();
/* 5276 */                                             if (_jspx_th_html_005foption_005f14.doEndTag() == 5) {
/* 5277 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f14); return;
/*      */                                             }
/*      */                                             
/* 5280 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f14);
/* 5281 */                                             out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 5282 */                                             int evalDoAfterBody = _jspx_th_html_005fselect_005f8.doAfterBody();
/* 5283 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 5286 */                                           if (_jspx_eval_html_005fselect_005f8 != 1) {
/* 5287 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 5290 */                                         if (_jspx_th_html_005fselect_005f8.doEndTag() == 5) {
/* 5291 */                                           this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f8); return;
/*      */                                         }
/*      */                                         
/* 5294 */                                         this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fproperty.reuse(_jspx_th_html_005fselect_005f8);
/* 5295 */                                         out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t</td>\n\n\t\t\t\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</div></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr id=\"subDirCountChk\" style=\"display: none;\">\n\t\t\t\t\t<td height=\"20\" width=\"25%\" class=\"bodytex label-alignt\">");
/* 5296 */                                         out.print(FormatUtil.getString("am.webclient.subDirCount.text"));
/* 5297 */                                         out.write("</td>\n\t\t\t\t\t<td height=\"20\" width=\"95%\" class=\"bodytext\">");
/* 5298 */                                         if (_jspx_meth_html_005fcheckbox_005f4(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5300 */                                         out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\n\t\t\t\t");
/*      */                                       } else {
/* 5302 */                                         out.write("\n\t\t\t\t ");
/* 5303 */                                         if ((!mtype.equals("file")) && (!mtype.equals("directory")) && (!mtype.equals("File System Monitor"))) {
/* 5304 */                                           out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t    <td class=\"bodytext label-align addmonitor-label\"><label>");
/* 5305 */                                           out.print(FormatUtil.getString("Script Location"));
/* 5306 */                                           out.write("</label></td>\n\t\t\t\t\t     <td class=\"bodytext\" width=\"75%\" valign=\"middle\">\n\t\t\t\t\t\t     <table width=\"99%\" cellspacing=\"0\">\n\t\t\t\t\t\t\t\t <tr style=\"padding-top:5px\" colspan=4>\n\t\t\t\t\t\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">\n\t\t\t\t\t\t\t\t\t\t");
/* 5307 */                                           if (_jspx_meth_html_005fradio_005f8(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5309 */                                           out.write("\n\t\t\t\t\t\t\t\t\t  </td>\n\t\t\t\t\t\t\t\t\t  <td height=\"20\" width=\"14%\" class=\"bodytext\">");
/* 5310 */                                           out.print(FormatUtil.getString("am.webclient.script.command"));
/* 5311 */                                           out.write("</td>\n\t\t\t\t\t\t\t\t\t  <td height=\"20\" width=\"2%\" class=\"bodytext\">");
/* 5312 */                                           if (_jspx_meth_html_005fradio_005f9(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5314 */                                           out.write("</td>\n\t\t\t\t\t\t\t\t\t  <td height=\"20\" width=\"81%\" class=\"bodytext\">");
/* 5315 */                                           out.print(FormatUtil.getString("am.webclient.script.script"));
/* 5316 */                                           out.write("</td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t     </table>\n\t\t\t\t\t     </td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                         }
/* 5318 */                                         out.write("\n              <td width=\"25%\" height=\"35\" class=\"bodytext label-align\" >");
/* 5319 */                                         out.print(FormatUtil.getString("am.webclient.newscript.scripttomonitor.text"));
/* 5320 */                                         out.write("<span class=\"mandatory\">*</span> </td>\n              <td height=\"35\" width=\"75%\" colspan=\"3\"class=\"bodytext\">");
/* 5321 */                                         if (_jspx_meth_html_005ftext_005f3(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5323 */                                         out.write("\n              <span class=\"footer\">");
/* 5324 */                                         out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 5325 */                                         out.write("</span></td>\n          </tr>\n          <tr>\n              <td width=\"25%\" height=\"35\" class=\"bodytext label-align\" >");
/* 5326 */                                         out.print(FormatUtil.getString("am.webclient.newscript.scriptdirectory.text"));
/* 5327 */                                         out.write("<span class=\"mandatory\">*</span> </td>\n              <td height=\"35\" colspan=\"3\" class=\"bodytext\">");
/* 5328 */                                         if (_jspx_meth_html_005ftext_005f4(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5330 */                                         out.write("\n              <span class=\"footer\">");
/* 5331 */                                         out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 5332 */                                         out.write("</span></td>\n          </tr>\n          ");
/*      */                                         
/* 5334 */                                         if ((customType != null) && (customType.equals("true")))
/*      */                                         {
/*      */ 
/* 5337 */                                           out.write("\n          <tr>\n\t  \t    <td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td>\n\t  \t     <td colspan=\"4\"><input type=\"checkbox\" name=\"isFile\" value=\"true\" onClick=\"javascript:enableDisableFile()\">\n\t  \t     ");
/* 5338 */                                           out.print(FormatUtil.getString("Get output from file"));
/* 5339 */                                           out.write("</td>\n          </tr>\n          <tr>\n          <td width=\"25%\" height=\"35\" class=\"bodytext label-align\" >");
/* 5340 */                                           out.print(FormatUtil.getString("am.webclient.newscript.outputfile.text"));
/* 5341 */                                           out.write("<span class=\"mandatory\">*</span> </td>\n          <td height=\"35\" width=\"75%\" colspan=\"3\"class=\"bodytext\">");
/* 5342 */                                           if (_jspx_meth_html_005ftext_005f5(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5344 */                                           out.write("\n      <span class=\"footer\">");
/* 5345 */                                           out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 5346 */                                           out.write("</span></td></tr>\n      ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 5351 */                                           out.write("\n        <tr>\n            <td class=\"bodytext\" colspan=\"3\">\n               ");
/* 5352 */                                           if (_jspx_meth_html_005fcheckbox_005f5(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5354 */                                           out.print(FormatUtil.getString("am.webclient.newscript.outputsettings.text"));
/* 5355 */                                           out.write("\n            </td>\n        </tr>\n    </tr>\n<tr> \n    \n    <td colspan=\"4\" id=\"opdetailstd\">\n<div width=\"100%\" id=\"opdetails\" style=\"display:block;\"> \n        <table width=\"99%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\"  >\n          <tr>\n\t    <td class=\"bodytext label-align addmonitor-label\" width=\"25%\">&nbsp;</td>\n\t     <td colspan=\"4\"><input type=\"checkbox\" name=\"isFile\" value=\"true\" onClick=\"javascript:enableDisableFile()\">\n\t     ");
/* 5356 */                                           out.print(FormatUtil.getString("am.script.output.from.file"));
/* 5357 */                                           out.write("</td>\n          </tr>\n        \n          <tr > \n            <td width=\"25%\" height=\"34\" class=\"bodytext label-align\" >");
/* 5358 */                                           out.print(FormatUtil.getString("am.webclient.newscript.outputfile.text"));
/* 5359 */                                           out.write("<span class=\"mandatory\"></span> \n            </td>\n        \t    <td colspan=\"2\" height=\"34\" class=\"bodytext\">\n        \t    <table><tr>\n            \t\t<td> ");
/* 5360 */                                           if (_jspx_meth_html_005ftext_005f6(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5362 */                                           out.write(" </td>\n            \t\t<td> <span class=\"footer\" style=\"padding:0px 0px 0px 5px\">");
/* 5363 */                                           out.print(FormatUtil.getString("am.webclient.newscript.absolutepath.text"));
/* 5364 */                                           out.write("</span></td>\n            \t</tr></table>\t\n            </td>       \n          </tr>\n          <tr> \n            <td  height=\"29\" class=\"bodytext label-align\">");
/* 5365 */                                           out.print(FormatUtil.getString("String Attributes"));
/* 5366 */                                           out.write("</td>\n            <td height=\"29\" colspan=\"2\" class=\"bodytext\">\n            \t<table><tr>\n            \t\t<td>");
/* 5367 */                                           if (_jspx_meth_html_005ftextarea_005f2(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5369 */                                           out.write("</td>\n            \t\t<td> <span class=\"footer\"  style=\"padding:0px 0px 0px 8px\">");
/* 5370 */                                           out.print(FormatUtil.getString("am.webclient.newscript.stringattributeshelp.text"));
/* 5371 */                                           out.write("</span></td>\n          \t\t</tr></table>\n          \t</td>  \n            \n          </tr>\n          <tr> \n            <td  height=\"35\" class=\"bodytext label-align\" >");
/* 5372 */                                           out.print(FormatUtil.getString("Numeric Attributes"));
/* 5373 */                                           out.write("</td>\n            <td height=\"35\" class=\"bodytext\" colspan=\"2\">\n            \t<table><tr>\n            \t\t<td> ");
/* 5374 */                                           if (_jspx_meth_html_005ftextarea_005f3(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5376 */                                           out.write("</td>\n           \t\t\t<td><span class=\"footer\" style=\"padding:0px 0px 0px 8px\">");
/* 5377 */                                           out.print(FormatUtil.getString("am.webclient.newscript.numericattributeshelp.text"));
/* 5378 */                                           out.write("</span></td>\n           \t\t</tr></table>\n           \t</td>\t\n          </tr>\n          <tr > \n            <td  height=\"35\" class=\"bodytext label-align\" >");
/* 5379 */                                           out.print(FormatUtil.getString("am.webclient.newscript.outputfiledelimiter.text"));
/* 5380 */                                           out.write("<span class=\"mandatory\"></span> \n            </td>\n            <td height=\"35\" colspan=\"2\" class=\"bodytext\">");
/* 5381 */                                           if (_jspx_meth_html_005ftext_005f7(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                             return;
/* 5383 */                                           out.write(" \n            </td>\n          </tr>\n          <tr><td height=\"2\" class=\"bcstrip\" colspan=\"2\" style=\"padding:8px; 0px 10px 0px\"><img width=\"10\" height=\"2\" src=\"../images/spacer.gif\"></td></tr>\n\t  <tr>\n\t  \n\t  <td class=\"bodytext\" ><input type=\"checkbox\" name=\"tablespresent\" onclick=javascript:showTables()>");
/* 5384 */                                           out.print(FormatUtil.getString("am.webclient.newscript.tablesinoutput.text"));
/* 5385 */                                           out.write("</td>\n\t    <td class=\"bodytext\" colspan=\"3\"><img src=\"../images/icon_quicknote_help.gif\" width=\"15\" height=\"11\"  border=\"0\" align=\"absmiddle\" ><a href=\"/help/managing-business-applications/script-monitor.html#example\" target=\"_blank\" class=\"selectedmenu\"> ");
/* 5386 */                                           out.print(FormatUtil.getString("am.webclient.hometab.availabilitycheck.helpmessage"));
/* 5387 */                                           out.write("</a></td>\n           \n\t  </tr>\n\t  \n\t  <tr >\n\t  <td colspan=\"5\">\n\t<div id=\"tableid\" style=\"display:block;\"> \n\t<table  width=\"100%\" border=\"0\">\n\t<tbody id=\"filtertable\">\n        <tr>\n\t  <td width=\"25%\" height=\"34\" class=\"bodytext label-align\"  >");
/* 5388 */                                           out.print(FormatUtil.getString("Table Name"));
/* 5389 */                                           out.write("<span class=\"mandatory\"></span></td>\n\t  <td width=\"20%\" height=\"34\" class=\"bodytext\">");
/* 5390 */                                           out.print(FormatUtil.getString("Numeric Attributes"));
/* 5391 */                                           out.write("</td>\n\t  <td width=\"20%\" height=\"35\" class=\"bodytext\" >");
/* 5392 */                                           out.print(FormatUtil.getString("String Attributes"));
/* 5393 */                                           out.write("</td>\n\t  <td width=\"20%\" height=\"35\" class=\"bodytext\" >");
/* 5394 */                                           out.print(FormatUtil.getString("Unique Column"));
/* 5395 */                                           out.write(" </td>\n\t  <td width=\"15%\" height=\"35\" class=\"bodytext\" >");
/* 5396 */                                           out.print(FormatUtil.getString("Column Delimiter"));
/* 5397 */                                           out.write("<span class=\"mandatory\"></span> </td>\n\t     \t   \n\t </tr>\n\n\t");
/*      */                                           
/* 5399 */                                           for (int i = 0; i < tableids.size(); i++)
/*      */                                           {
/* 5401 */                                             String tabname = "table" + (i + 1);
/* 5402 */                                             String tabid = "tab" + (i + 1);
/* 5403 */                                             String sattname = "stringatt" + (i + 1);
/* 5404 */                                             String sattid = "sa" + (i + 1);
/* 5405 */                                             String nattname = "numericatt" + (i + 1);
/* 5406 */                                             String nattid = "na" + (i + 1);
/* 5407 */                                             String pcolname = "pcatt" + (i + 1);
/* 5408 */                                             String pcolid = "pri_col_att" + (i + 1);
/* 5409 */                                             String delname = "cdl" + (i + 1);
/* 5410 */                                             String delid = "col_del" + (i + 1);
/* 5411 */                                             String filterrow = "filterrow" + (i + 1);
/*      */                                             
/* 5413 */                                             out.write("\n\t<tr id=\"");
/* 5414 */                                             out.print(filterrow);
/* 5415 */                                             out.write("\"> \n            <td height=\"35\" class=\"\" valign=\"top\"><input type=\"textbox\" name=\"");
/* 5416 */                                             out.print(tabname);
/* 5417 */                                             out.write("\" id=\"");
/* 5418 */                                             out.print(tabid);
/* 5419 */                                             out.write("\"  size=\"25\" value=\"");
/* 5420 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("name"));
/* 5421 */                                             out.write("\" class=\"formtext\"></td>\n            <td height=\"35\"  class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"");
/* 5422 */                                             out.print(nattname);
/* 5423 */                                             out.write("\" id=\"");
/* 5424 */                                             out.print(nattid);
/* 5425 */                                             out.write("\"  class=\"formtextarea\">");
/* 5426 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("numerics").toString().trim());
/* 5427 */                                             out.write("</textarea></td>\n            <td height=\"35\" class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"");
/* 5428 */                                             out.print(sattname);
/* 5429 */                                             out.write("\" id=\"");
/* 5430 */                                             out.print(sattid);
/* 5431 */                                             out.write("\"  class=\"formtextarea\">");
/* 5432 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("strings").toString().trim());
/* 5433 */                                             out.write("</textarea></td>\n\t    <td  class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"");
/* 5434 */                                             out.print(pcolname);
/* 5435 */                                             out.write("\" id=\"");
/* 5436 */                                             out.print(pcolid);
/* 5437 */                                             out.write("\" class=\"formtextarea\">");
/* 5438 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("primary").toString().trim());
/* 5439 */                                             out.write("</textarea></td>\n            <td height=\"35\" class=\"bodytext\" valign=\"top\"><input type=\"textbox\" size=\"2\" name=\"");
/* 5440 */                                             out.print(delname);
/* 5441 */                                             out.write("\" id=\"");
/* 5442 */                                             out.print(delid);
/* 5443 */                                             out.write("\"  value=\"");
/* 5444 */                                             out.print(((Hashtable)table_atts.get(tableids.get(i))).get("delimiter").toString().trim());
/* 5445 */                                             out.write("\" class=\"formtext\"></td>\n\t    \n          </tr>\n\t");
/*      */                                           }
/*      */                                           
/* 5448 */                                           if (tableids.size() == 0)
/*      */                                           {
/*      */ 
/* 5451 */                                             out.write("\n\t<tr>\n\t<td height=\"35\" class=\"\" valign=\"top\" ><input type=\"textbox\" name=\"table1\" id=\"tab1\"  size=\"25\" class=\"formtext\"></td>\n\t<td height=\"35\"   class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"numericatt1\" id=\"na1\"  class=\"formtextarea\"></textarea></td>\n    <td height=\"35\" class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"stringatt1\" id=\"sa1\"  class=\"formtextarea\"></textarea></td>\n    <td class=\"bodytext\" valign=\"top\"><textarea cols=\"15\" name=\"pcatt1\" id=\"pri_col_att1\" class=\"formtextarea\"></textarea></td>\n    <td height=\"35\"  class=\"bodytext\" valign=\"top\"><input type=\"textbox\" size=\"1\" name=\"cdl1\" id=\"col_del1\" class=\"formtext\"></td>\n\t</tr>\n\t");
/*      */                                           }
/*      */                                           
/*      */ 
/* 5455 */                                           out.write("\n    </tbody>\t\n\t</table>\n\t<table>\n\t<tr>\n\t\t<td><a class=\"bulkmon-tag\" href=\"javascript:addFilterRow(++count)\">");
/* 5456 */                                           out.print(FormatUtil.getString("am.webclient.maintenance.more"));
/* 5457 */                                           out.write("</a></td>\n\t\t<td colspan=4><a class=\"bulkmon-tag\" href=\"javascript:removeFilterRow(count--)\">");
/* 5458 */                                           out.print(FormatUtil.getString("am.webclient.maintenance.fewer"));
/* 5459 */                                           out.write("</a></td>\n\t</tr>\n\t</table>\n\t</div>\n\t  </td>\n\t  </tr>\n        </table>\n      </div></td>\n  </tr>\n");
/*      */                                         }
/*      */                                         
/*      */ 
/* 5463 */                                         out.write("\n  <tr> \n    <td width=\"25%\" height=\"35\" class=\"bodytext label-align\" >");
/* 5464 */                                         out.print(FormatUtil.getString("am.webclient.newscript.arguments.text"));
/* 5465 */                                         out.write("</td>\n    <td height=\"35\" colspan=\"3\" class=\"bodytext\"> ");
/* 5466 */                                         if (_jspx_meth_html_005ftext_005f8(_jspx_th_c_005fif_005f26, _jspx_page_context))
/*      */                                           return;
/* 5468 */                                         out.write(" \n    <span class=\"footer\">");
/* 5469 */                                         out.print(FormatUtil.getString("am.webclient.newscript.argseg.text"));
/* 5470 */                                         out.write("</span></td>\n    <!--value=\"appmanager,port,check\"-->\n  </tr>\n");
/*      */                                       }
/* 5472 */                                       out.write(10);
/* 5473 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f26.doAfterBody();
/* 5474 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5478 */                                   if (_jspx_th_c_005fif_005f26.doEndTag() == 5) {
/* 5479 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26); return;
/*      */                                   }
/*      */                                   
/* 5482 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f26);
/* 5483 */                                   out.write(32);
/* 5484 */                                   out.write(32);
/* 5485 */                                   out.write(10);
/* 5486 */                                   if (_jspx_meth_c_005fif_005f27(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5488 */                                   out.write("\n  <tr>\n    <td width=\"25%\" height=\"26\" class=\"bodytext label-align\">");
/* 5489 */                                   out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 5490 */                                   out.write(" <span class=\"mandatory\">*</span></td>\n    <td height=\"24\" colspan=\"2\" class=\"bodytext\" width=\"75%\"> ");
/* 5491 */                                   if (_jspx_meth_html_005ftext_005f10(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5493 */                                   out.write(" \n      ");
/* 5494 */                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.unitofpoll.text"));
/* 5495 */                                   out.write("</td>\n  </tr>\n  <tr>\n\n                ");
/* 5496 */                                   String tout = null;
/* 5497 */                                   if ((resourcetype.equals("file")) || (resourcetype.equals("directory")) || (resourcetype.equals("File System Monitor"))) {
/* 5498 */                                     tout = "am.webclient.newfsm.insecondshelp.text";
/*      */                                   }
/*      */                                   else {
/* 5501 */                                     tout = "am.webclient.newscript.insecondshelp.text";
/*      */                                   }
/* 5503 */                                   out.write("\n    <td height=\"20\" class=\"bodytext label-align\" width=\"25%\"><a style=\"cursor:pointer\" class=\"dotteduline\" onMouseOver=\"ddrivetip(this,event,'");
/* 5504 */                                   out.print(FormatUtil.getString(tout));
/* 5505 */                                   out.write("',false,true,'#000000',400,'lightyellow')\" onMouseOut=\"hideddrivetip()\">");
/* 5506 */                                   out.print(FormatUtil.getString("am.webclient.newscript.tiemout.text"));
/* 5507 */                                   out.write("</a><span class=\"mandatory\">*</span></td>\n    <td height=\"20\" colspan=\"2\" width=\"75%\"> ");
/* 5508 */                                   if (_jspx_meth_html_005ftext_005f11(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5510 */                                   out.write("<span class=\"bodytext\"> ");
/* 5511 */                                   out.print(FormatUtil.getString("am.webclient.hostdiscovery.second"));
/* 5512 */                                   out.write("</span>\n     </td>\n\n\n  </tr>\n");
/*      */                                   
/* 5514 */                                   if ((!resourcetype.equals("File System Monitor")) && (!resourcetype.equals("file")) && (!resourcetype.equals("directory"))) {
/* 5515 */                                     if ((!System.getProperty("os.name").startsWith("windows")) && (!System.getProperty("os.name").startsWith("Windows")))
/*      */                                     {
/*      */ 
/* 5518 */                                       out.write("\n  <tr> \n    <td height=\"20\" class=\"bodytext label-align\">");
/* 5519 */                                       out.print(FormatUtil.getString("am.webclient.newscript.enterthemode.text"));
/* 5520 */                                       out.write("<span class=\"mandatory\"></span></td>\n    <td height=\"20\" colspan=\"3\" > ");
/* 5521 */                                       if (_jspx_meth_html_005ftext_005f12(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5523 */                                       out.write("\n    <span class=\"footer\">");
/* 5524 */                                       out.print(FormatUtil.getString("am.webclient.newscript.alert.notforwindows.text"));
/* 5525 */                                       out.write("</span></td>\n  </tr>\n");
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 5531 */                                       out.write("\n<tr>\n<td colspan=\"3\">\n<div id=\"windowsmode\" style=\"display:block;\">\n<table border=\"0\" cellspacing=\"0\" cellpadding=\"2\" width=\"100%\">\n<tr>\n<td height=\"20\" width=\"25%\" class=\"bodytext label-align\">");
/* 5532 */                                       out.print(FormatUtil.getString("am.webclient.newscript.enterthemode.text"));
/* 5533 */                                       out.write("<span class=\"mandatory\"></span></td>\n<td height=\"20\"> ");
/* 5534 */                                       if (_jspx_meth_html_005ftext_005f13(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                         return;
/* 5536 */                                       out.write("\n</td>\n</tr>\n</table>\n</div>\n</td>\n</tr>\n    ");
/*      */                                     }
/*      */                                   }
/*      */                                   
/* 5540 */                                   String update_script = FormatUtil.getString("am.webclient.newscript.updatescript.text");
/* 5541 */                                   String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/* 5542 */                                   String update = FormatUtil.getString("am.webclient.common.update.text");
/*      */                                   
/* 5544 */                                   out.write("\n</table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n    <tr> \n      <td width=\"25%\" height=\"22\" class=\"tablebottom\" >&nbsp;</td>\n      <td width=\"75%\" height=\"26\" class=\"tablebottom\" >");
/*      */                                   
/* 5546 */                                   IfTag _jspx_th_c_005fif_005f28 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5547 */                                   _jspx_th_c_005fif_005f28.setPageContext(_jspx_page_context);
/* 5548 */                                   _jspx_th_c_005fif_005f28.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5550 */                                   _jspx_th_c_005fif_005f28.setTest("${empty method}");
/* 5551 */                                   int _jspx_eval_c_005fif_005f28 = _jspx_th_c_005fif_005f28.doStartTag();
/* 5552 */                                   if (_jspx_eval_c_005fif_005f28 != 0) {
/*      */                                     for (;;) {
/* 5554 */                                       out.write(32);
/*      */                                       
/* 5556 */                                       ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5557 */                                       _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 5558 */                                       _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_c_005fif_005f28);
/*      */                                       
/* 5560 */                                       _jspx_th_html_005fbutton_005f0.setStyleClass("buttons btn_highlt");
/*      */                                       
/* 5562 */                                       _jspx_th_html_005fbutton_005f0.setValue(update_script);
/*      */                                       
/* 5564 */                                       _jspx_th_html_005fbutton_005f0.setOnclick("validateAndSubmit();");
/*      */                                       
/* 5566 */                                       _jspx_th_html_005fbutton_005f0.setProperty("submitbutton1");
/* 5567 */                                       int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 5568 */                                       if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 5569 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                       }
/*      */                                       
/* 5572 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 5573 */                                       out.write(" \n        ");
/* 5574 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f28.doAfterBody();
/* 5575 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5579 */                                   if (_jspx_th_c_005fif_005f28.doEndTag() == 5) {
/* 5580 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28); return;
/*      */                                   }
/*      */                                   
/* 5583 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f28);
/* 5584 */                                   out.write(32);
/*      */                                   
/* 5586 */                                   IfTag _jspx_th_c_005fif_005f29 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5587 */                                   _jspx_th_c_005fif_005f29.setPageContext(_jspx_page_context);
/* 5588 */                                   _jspx_th_c_005fif_005f29.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5590 */                                   _jspx_th_c_005fif_005f29.setTest("${!empty method}");
/* 5591 */                                   int _jspx_eval_c_005fif_005f29 = _jspx_th_c_005fif_005f29.doStartTag();
/* 5592 */                                   if (_jspx_eval_c_005fif_005f29 != 0) {
/*      */                                     for (;;) {
/* 5594 */                                       out.write(" \n        ");
/*      */                                       
/* 5596 */                                       ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 5597 */                                       _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 5598 */                                       _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_c_005fif_005f29);
/*      */                                       
/* 5600 */                                       _jspx_th_html_005fbutton_005f1.setOnclick("return validateAndSubmit();");
/*      */                                       
/* 5602 */                                       _jspx_th_html_005fbutton_005f1.setStyleClass("buttons btn_highlt");
/*      */                                       
/* 5604 */                                       _jspx_th_html_005fbutton_005f1.setProperty("submitbutton1");
/*      */                                       
/* 5606 */                                       _jspx_th_html_005fbutton_005f1.setValue(update);
/* 5607 */                                       int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 5608 */                                       if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 5609 */                                         this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                                       }
/*      */                                       
/* 5612 */                                       this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 5613 */                                       out.write("\n        ");
/* 5614 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f29.doAfterBody();
/* 5615 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5619 */                                   if (_jspx_th_c_005fif_005f29.doEndTag() == 5) {
/* 5620 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29); return;
/*      */                                   }
/*      */                                   
/* 5623 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f29);
/* 5624 */                                   out.write(" &nbsp;");
/*      */                                   
/* 5626 */                                   ResetTag _jspx_th_html_005freset_005f0 = (ResetTag)this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.get(ResetTag.class);
/* 5627 */                                   _jspx_th_html_005freset_005f0.setPageContext(_jspx_page_context);
/* 5628 */                                   _jspx_th_html_005freset_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 5630 */                                   _jspx_th_html_005freset_005f0.setStyleClass("buttons btn_link");
/*      */                                   
/* 5632 */                                   _jspx_th_html_005freset_005f0.setValue(cancel);
/*      */                                   
/* 5634 */                                   _jspx_th_html_005freset_005f0.setOnclick("javascript:history.back();");
/* 5635 */                                   int _jspx_eval_html_005freset_005f0 = _jspx_th_html_005freset_005f0.doStartTag();
/* 5636 */                                   if (_jspx_th_html_005freset_005f0.doEndTag() == 5) {
/* 5637 */                                     this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0); return;
/*      */                                   }
/*      */                                   
/* 5640 */                                   this._005fjspx_005ftagPool_005fhtml_005freset_0026_005fvalue_005fstyleClass_005fonclick_005fnobody.reuse(_jspx_th_html_005freset_005f0);
/* 5641 */                                   out.write("</td>\n    </tr>\n</table>\n\n<!--table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<tr>\n<td>\nNote: If there are any modifications in the script file itself,please change it yourself.The file will be present in the AppManager_home/working/scripts/s1_dir directory\n</td>\n</tr>\n</table-->\n\n\n    \n");
/* 5642 */                                   if (_jspx_meth_c_005fif_005f30(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5644 */                                   out.write("\n        ");
/* 5645 */                                   if (_jspx_meth_c_005fif_005f31(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 5647 */                                   out.write(10);
/* 5648 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 5649 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5653 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 5654 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 5657 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005ffocus_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 5658 */                               out.write(32);
/* 5659 */                               out.write(10);
/* 5660 */                               out.write(10);
/* 5661 */                               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 5662 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 5665 */                             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 5666 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 5669 */                           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 5670 */                             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                           }
/*      */                           
/* 5673 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 5674 */                           out.write(10);
/* 5675 */                           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                             return;
/* 5677 */                           out.write(10);
/* 5678 */                           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 5679 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 5683 */                       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 5684 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                       }
/*      */                       else {
/* 5687 */                         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 5688 */                         out.write(10);
/* 5689 */                         out.write(10);
/* 5690 */                         out.write(10);
/*      */                       }
/* 5692 */                     } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5693 */         out = _jspx_out;
/* 5694 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5695 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5696 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5699 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5705 */     PageContext pageContext = _jspx_page_context;
/* 5706 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5708 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5709 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5710 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5712 */     _jspx_th_c_005fout_005f0.setValue("${contentChkCount}");
/* 5713 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5714 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5715 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5716 */       return true;
/*      */     }
/* 5718 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5719 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5724 */     PageContext pageContext = _jspx_page_context;
/* 5725 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5727 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5728 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5729 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 5731 */     _jspx_th_c_005fout_005f1.setValue("${contentChkCount}");
/* 5732 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5733 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5734 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5735 */       return true;
/*      */     }
/* 5737 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5738 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5743 */     PageContext pageContext = _jspx_page_context;
/* 5744 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5746 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5747 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5748 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 5750 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5751 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5752 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5754 */         out.write(" \n\t\talertUser();\n\t\treturn;\n\t");
/* 5755 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5756 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5760 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5761 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5762 */       return true;
/*      */     }
/* 5764 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5765 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5770 */     PageContext pageContext = _jspx_page_context;
/* 5771 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5773 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5774 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 5775 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5777 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 5779 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 5780 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 5781 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 5782 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5783 */       return true;
/*      */     }
/* 5785 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 5786 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5791 */     PageContext pageContext = _jspx_page_context;
/* 5792 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5794 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5795 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5796 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5798 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5799 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5800 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5801 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5802 */       return true;
/*      */     }
/* 5804 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5805 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5810 */     PageContext pageContext = _jspx_page_context;
/* 5811 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5813 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5814 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5815 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5817 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 5818 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5819 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5820 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5821 */       return true;
/*      */     }
/* 5823 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5824 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5829 */     PageContext pageContext = _jspx_page_context;
/* 5830 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5832 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5833 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5834 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5836 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5837 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5838 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5839 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5840 */       return true;
/*      */     }
/* 5842 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5843 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5848 */     PageContext pageContext = _jspx_page_context;
/* 5849 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5851 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5852 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5853 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5855 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5856 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5857 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5858 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5859 */       return true;
/*      */     }
/* 5861 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5862 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5867 */     PageContext pageContext = _jspx_page_context;
/* 5868 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5870 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5871 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 5872 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5874 */     _jspx_th_c_005fif_005f1.setTest("${!empty param.parentid}");
/* 5875 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 5876 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 5878 */         out.write("\n      ");
/* 5879 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 5880 */           return true;
/* 5881 */         out.write("\n      ");
/* 5882 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 5883 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5887 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 5888 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5889 */       return true;
/*      */     }
/* 5891 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 5892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5897 */     PageContext pageContext = _jspx_page_context;
/* 5898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5900 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 5901 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 5902 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5904 */     _jspx_th_c_005fset_005f0.setVar("parentids");
/* 5905 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 5906 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 5907 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5908 */         out = _jspx_page_context.pushBody();
/* 5909 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 5910 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5913 */         out.write("\n      &parentname=");
/* 5914 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5915 */           return true;
/* 5916 */         out.write("&parentid=");
/* 5917 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 5918 */           return true;
/* 5919 */         out.write("\n      ");
/* 5920 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 5921 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5924 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 5925 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5928 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 5929 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5930 */       return true;
/*      */     }
/* 5932 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 5933 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5938 */     PageContext pageContext = _jspx_page_context;
/* 5939 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5941 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5942 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5943 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5945 */     _jspx_th_c_005fout_005f6.setValue("${param.parentname}");
/* 5946 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5947 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5948 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5949 */       return true;
/*      */     }
/* 5951 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5952 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5957 */     PageContext pageContext = _jspx_page_context;
/* 5958 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5960 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5961 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5962 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 5964 */     _jspx_th_c_005fout_005f7.setValue("${param.parentid}");
/* 5965 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5966 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5967 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5968 */       return true;
/*      */     }
/* 5970 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5976 */     PageContext pageContext = _jspx_page_context;
/* 5977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5979 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5980 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 5981 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 5983 */     _jspx_th_c_005fif_005f2.setTest("${empty param.parentid}");
/* 5984 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 5985 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 5987 */         out.write("\n            ");
/* 5988 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 5989 */           return true;
/* 5990 */         out.write("\n      ");
/* 5991 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5992 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5996 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5997 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5998 */       return true;
/*      */     }
/* 6000 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 6001 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6006 */     PageContext pageContext = _jspx_page_context;
/* 6007 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6009 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 6010 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 6011 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6013 */     _jspx_th_c_005fset_005f1.setVar("parentids");
/*      */     
/* 6015 */     _jspx_th_c_005fset_005f1.setValue("");
/* 6016 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 6017 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 6018 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 6019 */       return true;
/*      */     }
/* 6021 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 6022 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6027 */     PageContext pageContext = _jspx_page_context;
/* 6028 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6030 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6031 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 6032 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6034 */     _jspx_th_c_005fif_005f3.setTest("${!empty param.haid}");
/* 6035 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 6036 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 6038 */         out.write(10);
/* 6039 */         out.write(9);
/* 6040 */         out.write(9);
/* 6041 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 6042 */           return true;
/* 6043 */         out.write(10);
/* 6044 */         out.write(9);
/* 6045 */         out.write(9);
/* 6046 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 6047 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6051 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 6052 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6053 */       return true;
/*      */     }
/* 6055 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 6056 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6061 */     PageContext pageContext = _jspx_page_context;
/* 6062 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6064 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6065 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 6066 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 6068 */     _jspx_th_c_005fset_005f2.setVar("haid");
/* 6069 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 6070 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 6071 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 6072 */         out = _jspx_page_context.pushBody();
/* 6073 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 6074 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6077 */         out.write("\n\t\t&haid=");
/* 6078 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 6079 */           return true;
/* 6080 */         out.write(10);
/* 6081 */         out.write(9);
/* 6082 */         out.write(9);
/* 6083 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 6084 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6087 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 6088 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6091 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 6092 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 6093 */       return true;
/*      */     }
/* 6095 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 6096 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6101 */     PageContext pageContext = _jspx_page_context;
/* 6102 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6104 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6105 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 6106 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 6108 */     _jspx_th_c_005fout_005f8.setValue("${param.haid}");
/* 6109 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 6110 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 6111 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6112 */       return true;
/*      */     }
/* 6114 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 6115 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6120 */     PageContext pageContext = _jspx_page_context;
/* 6121 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6123 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6124 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 6125 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6127 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 6128 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 6129 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 6130 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6131 */       return true;
/*      */     }
/* 6133 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 6134 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6139 */     PageContext pageContext = _jspx_page_context;
/* 6140 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6142 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6143 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 6144 */     _jspx_th_c_005fif_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6146 */     _jspx_th_c_005fif_005f6.setTest("${param.method=='editScript'}");
/* 6147 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 6148 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 6150 */         out.write("\n    </a>\n    ");
/* 6151 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 6152 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6156 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 6157 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6158 */       return true;
/*      */     }
/* 6160 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 6161 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6166 */     PageContext pageContext = _jspx_page_context;
/* 6167 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6169 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6170 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6171 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f8);
/*      */     
/* 6173 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6174 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6175 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6176 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6177 */       return true;
/*      */     }
/* 6179 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6180 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f9, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6185 */     PageContext pageContext = _jspx_page_context;
/* 6186 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6188 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6189 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6190 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f9);
/*      */     
/* 6192 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 6193 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6194 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6195 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6196 */       return true;
/*      */     }
/* 6198 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_logic_005fnotPresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6204 */     PageContext pageContext = _jspx_page_context;
/* 6205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6207 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6208 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6209 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_logic_005fnotPresent_005f2);
/*      */     
/* 6211 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6212 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6213 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6214 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6215 */       return true;
/*      */     }
/* 6217 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6218 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6223 */     PageContext pageContext = _jspx_page_context;
/* 6224 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6226 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6227 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6228 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6230 */     _jspx_th_c_005fout_005f13.setValue("${param.haid}");
/* 6231 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6232 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6233 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6234 */       return true;
/*      */     }
/* 6236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6237 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6242 */     PageContext pageContext = _jspx_page_context;
/* 6243 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6245 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6246 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6247 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6249 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6250 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6251 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6252 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6253 */       return true;
/*      */     }
/* 6255 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6261 */     PageContext pageContext = _jspx_page_context;
/* 6262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6264 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6265 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6266 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6268 */     _jspx_th_c_005fout_005f15.setValue("${parentids}");
/* 6269 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6270 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6271 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6272 */       return true;
/*      */     }
/* 6274 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6275 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6280 */     PageContext pageContext = _jspx_page_context;
/* 6281 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6283 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6284 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6285 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6287 */     _jspx_th_c_005fout_005f16.setValue("${param.haid}");
/* 6288 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6289 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6290 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6291 */       return true;
/*      */     }
/* 6293 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6294 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6299 */     PageContext pageContext = _jspx_page_context;
/* 6300 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6302 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6303 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6304 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6306 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 6307 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6308 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6309 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6310 */       return true;
/*      */     }
/* 6312 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6318 */     PageContext pageContext = _jspx_page_context;
/* 6319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6321 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6322 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6323 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6325 */     _jspx_th_c_005fout_005f18.setValue("${parentids}");
/* 6326 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6327 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6328 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6329 */       return true;
/*      */     }
/* 6331 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6332 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6337 */     PageContext pageContext = _jspx_page_context;
/* 6338 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6340 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6341 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6342 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6344 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6345 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6346 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6347 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6348 */       return true;
/*      */     }
/* 6350 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fnotPresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6356 */     PageContext pageContext = _jspx_page_context;
/* 6357 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6359 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6360 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6361 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fnotPresent_005f4);
/*      */     
/* 6363 */     _jspx_th_c_005fout_005f20.setValue("${parentids}");
/* 6364 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6365 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6366 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6367 */       return true;
/*      */     }
/* 6369 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6370 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6375 */     PageContext pageContext = _jspx_page_context;
/* 6376 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6378 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6379 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6380 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6382 */     _jspx_th_c_005fout_005f21.setValue("${param.haid}");
/* 6383 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6384 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6385 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6386 */       return true;
/*      */     }
/* 6388 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6389 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6394 */     PageContext pageContext = _jspx_page_context;
/* 6395 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6397 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6398 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6399 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6401 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 6402 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6403 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6404 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6405 */       return true;
/*      */     }
/* 6407 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6408 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6413 */     PageContext pageContext = _jspx_page_context;
/* 6414 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6416 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6417 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 6418 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6420 */     _jspx_th_c_005fout_005f23.setValue("${parentids}");
/* 6421 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 6422 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 6423 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6424 */       return true;
/*      */     }
/* 6426 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 6427 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6432 */     PageContext pageContext = _jspx_page_context;
/* 6433 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6435 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6436 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 6437 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6439 */     _jspx_th_c_005fout_005f24.setValue("${param.resourceid}");
/* 6440 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 6441 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 6442 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6443 */       return true;
/*      */     }
/* 6445 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 6446 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fif_005f16, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6451 */     PageContext pageContext = _jspx_page_context;
/* 6452 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6454 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6455 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 6456 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fif_005f16);
/*      */     
/* 6458 */     _jspx_th_c_005fout_005f25.setValue("${parentids}");
/* 6459 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 6460 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 6461 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6462 */       return true;
/*      */     }
/* 6464 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 6465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f17(JspTag _jspx_th_logic_005fnotPresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6470 */     PageContext pageContext = _jspx_page_context;
/* 6471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6473 */     IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6474 */     _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 6475 */     _jspx_th_c_005fif_005f17.setParent((Tag)_jspx_th_logic_005fnotPresent_005f5);
/*      */     
/* 6477 */     _jspx_th_c_005fif_005f17.setTest("${param.actionmethod!='editScript'}");
/* 6478 */     int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 6479 */     if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */       for (;;) {
/* 6481 */         out.write("\n    </a>\n    ");
/* 6482 */         int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 6483 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6487 */     if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 6488 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 6489 */       return true;
/*      */     }
/* 6491 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 6492 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6497 */     PageContext pageContext = _jspx_page_context;
/* 6498 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6500 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6501 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 6502 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6504 */     _jspx_th_c_005fout_005f26.setValue("${param.resourceid}");
/* 6505 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 6506 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 6507 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6508 */       return true;
/*      */     }
/* 6510 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 6511 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6516 */     PageContext pageContext = _jspx_page_context;
/* 6517 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6519 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6520 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 6521 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6523 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 6524 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 6525 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 6526 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6527 */       return true;
/*      */     }
/* 6529 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 6530 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6535 */     PageContext pageContext = _jspx_page_context;
/* 6536 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6538 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6539 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 6540 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6542 */     _jspx_th_c_005fout_005f28.setValue("${param.resourceid}");
/* 6543 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 6544 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 6545 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6546 */       return true;
/*      */     }
/* 6548 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 6549 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6554 */     PageContext pageContext = _jspx_page_context;
/* 6555 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6557 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6558 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 6559 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6561 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 6562 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 6563 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 6564 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6565 */       return true;
/*      */     }
/* 6567 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 6568 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6573 */     PageContext pageContext = _jspx_page_context;
/* 6574 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6576 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6577 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 6578 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6580 */     _jspx_th_c_005fout_005f30.setValue("${ha.key}");
/* 6581 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 6582 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 6583 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6584 */       return true;
/*      */     }
/* 6586 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 6587 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6592 */     PageContext pageContext = _jspx_page_context;
/* 6593 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6595 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6596 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 6597 */     _jspx_th_c_005fout_005f31.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6599 */     _jspx_th_c_005fout_005f31.setValue("${ha.value}");
/* 6600 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 6601 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 6602 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6603 */       return true;
/*      */     }
/* 6605 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 6606 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6611 */     PageContext pageContext = _jspx_page_context;
/* 6612 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6614 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6615 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 6616 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6618 */     _jspx_th_c_005fset_005f3.setVar("monitorName");
/* 6619 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 6620 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 6621 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6622 */         out = _jspx_page_context.pushBody();
/* 6623 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 6624 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 6625 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6628 */         if (_jspx_meth_c_005fout_005f32(_jspx_th_c_005fset_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 6629 */           return true;
/* 6630 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 6631 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6634 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 6635 */         out = _jspx_page_context.popBody();
/* 6636 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 6639 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 6640 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 6641 */       return true;
/*      */     }
/* 6643 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 6644 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f32(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6649 */     PageContext pageContext = _jspx_page_context;
/* 6650 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6652 */     OutTag _jspx_th_c_005fout_005f32 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6653 */     _jspx_th_c_005fout_005f32.setPageContext(_jspx_page_context);
/* 6654 */     _jspx_th_c_005fout_005f32.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 6656 */     _jspx_th_c_005fout_005f32.setValue("${ha.value}");
/* 6657 */     int _jspx_eval_c_005fout_005f32 = _jspx_th_c_005fout_005f32.doStartTag();
/* 6658 */     if (_jspx_th_c_005fout_005f32.doEndTag() == 5) {
/* 6659 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6660 */       return true;
/*      */     }
/* 6662 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f32);
/* 6663 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f33(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6668 */     PageContext pageContext = _jspx_page_context;
/* 6669 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6671 */     OutTag _jspx_th_c_005fout_005f33 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6672 */     _jspx_th_c_005fout_005f33.setPageContext(_jspx_page_context);
/* 6673 */     _jspx_th_c_005fout_005f33.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 6675 */     _jspx_th_c_005fout_005f33.setValue("${ha.key}");
/* 6676 */     int _jspx_eval_c_005fout_005f33 = _jspx_th_c_005fout_005f33.doStartTag();
/* 6677 */     if (_jspx_th_c_005fout_005f33.doEndTag() == 5) {
/* 6678 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6679 */       return true;
/*      */     }
/* 6681 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f33);
/* 6682 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f22, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6687 */     PageContext pageContext = _jspx_page_context;
/* 6688 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6690 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6691 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 6692 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f22);
/*      */     
/* 6694 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6695 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 6696 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 6697 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6698 */       return true;
/*      */     }
/* 6700 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 6701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f34(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6706 */     PageContext pageContext = _jspx_page_context;
/* 6707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6709 */     OutTag _jspx_th_c_005fout_005f34 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6710 */     _jspx_th_c_005fout_005f34.setPageContext(_jspx_page_context);
/* 6711 */     _jspx_th_c_005fout_005f34.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6713 */     _jspx_th_c_005fout_005f34.setValue("${ha.key}");
/* 6714 */     int _jspx_eval_c_005fout_005f34 = _jspx_th_c_005fout_005f34.doStartTag();
/* 6715 */     if (_jspx_th_c_005fout_005f34.doEndTag() == 5) {
/* 6716 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6717 */       return true;
/*      */     }
/* 6719 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f34);
/* 6720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f35(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6725 */     PageContext pageContext = _jspx_page_context;
/* 6726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6728 */     OutTag _jspx_th_c_005fout_005f35 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6729 */     _jspx_th_c_005fout_005f35.setPageContext(_jspx_page_context);
/* 6730 */     _jspx_th_c_005fout_005f35.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6732 */     _jspx_th_c_005fout_005f35.setValue("${ha.value}");
/* 6733 */     int _jspx_eval_c_005fout_005f35 = _jspx_th_c_005fout_005f35.doStartTag();
/* 6734 */     if (_jspx_th_c_005fout_005f35.doEndTag() == 5) {
/* 6735 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6736 */       return true;
/*      */     }
/* 6738 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f35);
/* 6739 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6744 */     PageContext pageContext = _jspx_page_context;
/* 6745 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6747 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 6748 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 6749 */     _jspx_th_c_005fset_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6751 */     _jspx_th_c_005fset_005f4.setVar("monitorName");
/* 6752 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 6753 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 6754 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6755 */         out = _jspx_page_context.pushBody();
/* 6756 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 6757 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 6758 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6761 */         if (_jspx_meth_c_005fout_005f36(_jspx_th_c_005fset_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 6762 */           return true;
/* 6763 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 6764 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6767 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 6768 */         out = _jspx_page_context.popBody();
/* 6769 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 6772 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 6773 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 6774 */       return true;
/*      */     }
/* 6776 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 6777 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f36(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6782 */     PageContext pageContext = _jspx_page_context;
/* 6783 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6785 */     OutTag _jspx_th_c_005fout_005f36 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6786 */     _jspx_th_c_005fout_005f36.setPageContext(_jspx_page_context);
/* 6787 */     _jspx_th_c_005fout_005f36.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 6789 */     _jspx_th_c_005fout_005f36.setValue("${ha.value}");
/* 6790 */     int _jspx_eval_c_005fout_005f36 = _jspx_th_c_005fout_005f36.doStartTag();
/* 6791 */     if (_jspx_th_c_005fout_005f36.doEndTag() == 5) {
/* 6792 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6793 */       return true;
/*      */     }
/* 6795 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f36);
/* 6796 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f37(JspTag _jspx_th_logic_005fpresent_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6801 */     PageContext pageContext = _jspx_page_context;
/* 6802 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6804 */     OutTag _jspx_th_c_005fout_005f37 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6805 */     _jspx_th_c_005fout_005f37.setPageContext(_jspx_page_context);
/* 6806 */     _jspx_th_c_005fout_005f37.setParent((Tag)_jspx_th_logic_005fpresent_005f12);
/*      */     
/* 6808 */     _jspx_th_c_005fout_005f37.setValue("${ha.key}");
/* 6809 */     int _jspx_eval_c_005fout_005f37 = _jspx_th_c_005fout_005f37.doStartTag();
/* 6810 */     if (_jspx_th_c_005fout_005f37.doEndTag() == 5) {
/* 6811 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6812 */       return true;
/*      */     }
/* 6814 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f37);
/* 6815 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f12, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6820 */     PageContext pageContext = _jspx_page_context;
/* 6821 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6823 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6824 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 6825 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f12);
/*      */     
/* 6827 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.quickremoval.monitorgroup.txt");
/* 6828 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 6829 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 6830 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6831 */       return true;
/*      */     }
/* 6833 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 6834 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f23(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6839 */     PageContext pageContext = _jspx_page_context;
/* 6840 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6842 */     IfTag _jspx_th_c_005fif_005f23 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6843 */     _jspx_th_c_005fif_005f23.setPageContext(_jspx_page_context);
/* 6844 */     _jspx_th_c_005fif_005f23.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6846 */     _jspx_th_c_005fif_005f23.setTest("${empty associatedmgs}");
/* 6847 */     int _jspx_eval_c_005fif_005f23 = _jspx_th_c_005fif_005f23.doStartTag();
/* 6848 */     if (_jspx_eval_c_005fif_005f23 != 0) {
/*      */       for (;;) {
/* 6850 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b>");
/* 6851 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f23, _jspx_page_context))
/* 6852 */           return true;
/* 6853 */         out.write("</b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">");
/* 6854 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f23, _jspx_page_context))
/* 6855 */           return true;
/* 6856 */         out.write("</td>\n\t ");
/* 6857 */         int evalDoAfterBody = _jspx_th_c_005fif_005f23.doAfterBody();
/* 6858 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6862 */     if (_jspx_th_c_005fif_005f23.doEndTag() == 5) {
/* 6863 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 6864 */       return true;
/*      */     }
/* 6866 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f23);
/* 6867 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6872 */     PageContext pageContext = _jspx_page_context;
/* 6873 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6875 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6876 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 6877 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6879 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6880 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 6881 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 6882 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6883 */       return true;
/*      */     }
/* 6885 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 6886 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f23, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6891 */     PageContext pageContext = _jspx_page_context;
/* 6892 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6894 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6895 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 6896 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f23);
/*      */     
/* 6898 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.urlmonitor.none.text");
/* 6899 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 6900 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 6901 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6902 */       return true;
/*      */     }
/* 6904 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 6905 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6910 */     PageContext pageContext = _jspx_page_context;
/* 6911 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6913 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(MessageTag.class);
/* 6914 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 6915 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 6917 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.urlmonitor.associatedgroups.text");
/* 6918 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 6919 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6920 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6921 */       return true;
/*      */     }
/* 6923 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6924 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6929 */     PageContext pageContext = _jspx_page_context;
/* 6930 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6932 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 6933 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 6934 */     _jspx_th_am_005fhiddenparam_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6936 */     _jspx_th_am_005fhiddenparam_005f0.setName("wiz");
/* 6937 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 6938 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 6939 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 6940 */       return true;
/*      */     }
/* 6942 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 6943 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6948 */     PageContext pageContext = _jspx_page_context;
/* 6949 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6951 */     HiddenParam _jspx_th_am_005fhiddenparam_005f1 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 6952 */     _jspx_th_am_005fhiddenparam_005f1.setPageContext(_jspx_page_context);
/* 6953 */     _jspx_th_am_005fhiddenparam_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6955 */     _jspx_th_am_005fhiddenparam_005f1.setName("type");
/* 6956 */     int _jspx_eval_am_005fhiddenparam_005f1 = _jspx_th_am_005fhiddenparam_005f1.doStartTag();
/* 6957 */     if (_jspx_th_am_005fhiddenparam_005f1.doEndTag() == 5) {
/* 6958 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 6959 */       return true;
/*      */     }
/* 6961 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f1);
/* 6962 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f2(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6967 */     PageContext pageContext = _jspx_page_context;
/* 6968 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6970 */     HiddenParam _jspx_th_am_005fhiddenparam_005f2 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 6971 */     _jspx_th_am_005fhiddenparam_005f2.setPageContext(_jspx_page_context);
/* 6972 */     _jspx_th_am_005fhiddenparam_005f2.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6974 */     _jspx_th_am_005fhiddenparam_005f2.setName("resourceid");
/* 6975 */     int _jspx_eval_am_005fhiddenparam_005f2 = _jspx_th_am_005fhiddenparam_005f2.doStartTag();
/* 6976 */     if (_jspx_th_am_005fhiddenparam_005f2.doEndTag() == 5) {
/* 6977 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 6978 */       return true;
/*      */     }
/* 6980 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f2);
/* 6981 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6986 */     PageContext pageContext = _jspx_page_context;
/* 6987 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6989 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 6990 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 6991 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 6993 */     _jspx_th_html_005ftext_005f0.setSize("25");
/*      */     
/* 6995 */     _jspx_th_html_005ftext_005f0.setProperty("displayname");
/*      */     
/* 6997 */     _jspx_th_html_005ftext_005f0.setStyleClass("formtext default");
/* 6998 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 6999 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 7000 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 7001 */       return true;
/*      */     }
/* 7003 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 7004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7009 */     PageContext pageContext = _jspx_page_context;
/* 7010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7012 */     RadioTag _jspx_th_html_005fradio_005f0 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.get(RadioTag.class);
/* 7013 */     _jspx_th_html_005fradio_005f0.setPageContext(_jspx_page_context);
/* 7014 */     _jspx_th_html_005fradio_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7016 */     _jspx_th_html_005fradio_005f0.setProperty("mtype");
/*      */     
/* 7018 */     _jspx_th_html_005fradio_005f0.setDisabled(true);
/*      */     
/* 7020 */     _jspx_th_html_005fradio_005f0.setValue("file");
/*      */     
/* 7022 */     _jspx_th_html_005fradio_005f0.setOnclick("javascript:managecontent()");
/* 7023 */     int _jspx_eval_html_005fradio_005f0 = _jspx_th_html_005fradio_005f0.doStartTag();
/* 7024 */     if (_jspx_th_html_005fradio_005f0.doEndTag() == 5) {
/* 7025 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 7026 */       return true;
/*      */     }
/* 7028 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f0);
/* 7029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7034 */     PageContext pageContext = _jspx_page_context;
/* 7035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7037 */     RadioTag _jspx_th_html_005fradio_005f1 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.get(RadioTag.class);
/* 7038 */     _jspx_th_html_005fradio_005f1.setPageContext(_jspx_page_context);
/* 7039 */     _jspx_th_html_005fradio_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7041 */     _jspx_th_html_005fradio_005f1.setProperty("mtype");
/*      */     
/* 7043 */     _jspx_th_html_005fradio_005f1.setDisabled(true);
/*      */     
/* 7045 */     _jspx_th_html_005fradio_005f1.setValue("directory");
/*      */     
/* 7047 */     _jspx_th_html_005fradio_005f1.setOnclick("javascript:managecontent()");
/* 7048 */     int _jspx_eval_html_005fradio_005f1 = _jspx_th_html_005fradio_005f1.doStartTag();
/* 7049 */     if (_jspx_th_html_005fradio_005f1.doEndTag() == 5) {
/* 7050 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 7051 */       return true;
/*      */     }
/* 7053 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fdisabled_005fnobody.reuse(_jspx_th_html_005fradio_005f1);
/* 7054 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f2(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7059 */     PageContext pageContext = _jspx_page_context;
/* 7060 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7062 */     RadioTag _jspx_th_html_005fradio_005f2 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7063 */     _jspx_th_html_005fradio_005f2.setPageContext(_jspx_page_context);
/* 7064 */     _jspx_th_html_005fradio_005f2.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7066 */     _jspx_th_html_005fradio_005f2.setProperty("serversite");
/*      */     
/* 7068 */     _jspx_th_html_005fradio_005f2.setValue("local");
/*      */     
/* 7070 */     _jspx_th_html_005fradio_005f2.setOnclick("javascript:manageremotescript()");
/* 7071 */     int _jspx_eval_html_005fradio_005f2 = _jspx_th_html_005fradio_005f2.doStartTag();
/* 7072 */     if (_jspx_th_html_005fradio_005f2.doEndTag() == 5) {
/* 7073 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 7074 */       return true;
/*      */     }
/* 7076 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f2);
/* 7077 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f3(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7082 */     PageContext pageContext = _jspx_page_context;
/* 7083 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7085 */     RadioTag _jspx_th_html_005fradio_005f3 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7086 */     _jspx_th_html_005fradio_005f3.setPageContext(_jspx_page_context);
/* 7087 */     _jspx_th_html_005fradio_005f3.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7089 */     _jspx_th_html_005fradio_005f3.setProperty("serversite");
/*      */     
/* 7091 */     _jspx_th_html_005fradio_005f3.setValue("remote");
/*      */     
/* 7093 */     _jspx_th_html_005fradio_005f3.setOnclick("javascript:manageremotescript()");
/* 7094 */     int _jspx_eval_html_005fradio_005f3 = _jspx_th_html_005fradio_005f3.doStartTag();
/* 7095 */     if (_jspx_th_html_005fradio_005f3.doEndTag() == 5) {
/* 7096 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 7097 */       return true;
/*      */     }
/* 7099 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f3);
/* 7100 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7105 */     PageContext pageContext = _jspx_page_context;
/* 7106 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7108 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7109 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 7110 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7112 */     _jspx_th_html_005ftext_005f1.setSize("52");
/*      */     
/* 7114 */     _jspx_th_html_005ftext_005f1.setProperty("filepath");
/*      */     
/* 7116 */     _jspx_th_html_005ftext_005f1.setStyleClass("formtext");
/* 7117 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 7118 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 7119 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 7120 */       return true;
/*      */     }
/* 7122 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 7123 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f0(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7128 */     PageContext pageContext = _jspx_page_context;
/* 7129 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7131 */     CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7132 */     _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 7133 */     _jspx_th_html_005fcheckbox_005f0.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7135 */     _jspx_th_html_005fcheckbox_005f0.setProperty("contentChk");
/*      */     
/* 7137 */     _jspx_th_html_005fcheckbox_005f0.setOnclick("javascript:ContentCheck()");
/* 7138 */     int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 7139 */     if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 7140 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 7141 */       return true;
/*      */     }
/* 7143 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 7144 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f4(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7149 */     PageContext pageContext = _jspx_page_context;
/* 7150 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7152 */     RadioTag _jspx_th_html_005fradio_005f4 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7153 */     _jspx_th_html_005fradio_005f4.setPageContext(_jspx_page_context);
/* 7154 */     _jspx_th_html_005fradio_005f4.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7156 */     _jspx_th_html_005fradio_005f4.setProperty("fileCheckType");
/*      */     
/* 7158 */     _jspx_th_html_005fradio_005f4.setValue("0");
/*      */     
/* 7160 */     _jspx_th_html_005fradio_005f4.setOnclick("javascript:showHideCheckEmpty()");
/* 7161 */     int _jspx_eval_html_005fradio_005f4 = _jspx_th_html_005fradio_005f4.doStartTag();
/* 7162 */     if (_jspx_th_html_005fradio_005f4.doEndTag() == 5) {
/* 7163 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 7164 */       return true;
/*      */     }
/* 7166 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f4);
/* 7167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f5(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7172 */     PageContext pageContext = _jspx_page_context;
/* 7173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7175 */     RadioTag _jspx_th_html_005fradio_005f5 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7176 */     _jspx_th_html_005fradio_005f5.setPageContext(_jspx_page_context);
/* 7177 */     _jspx_th_html_005fradio_005f5.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7179 */     _jspx_th_html_005fradio_005f5.setProperty("fileCheckType");
/*      */     
/* 7181 */     _jspx_th_html_005fradio_005f5.setValue("1");
/*      */     
/* 7183 */     _jspx_th_html_005fradio_005f5.setOnclick("javascript:showHideCheckEmpty()");
/* 7184 */     int _jspx_eval_html_005fradio_005f5 = _jspx_th_html_005fradio_005f5.doStartTag();
/* 7185 */     if (_jspx_th_html_005fradio_005f5.doEndTag() == 5) {
/* 7186 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 7187 */       return true;
/*      */     }
/* 7189 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f5);
/* 7190 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f0(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7195 */     PageContext pageContext = _jspx_page_context;
/* 7196 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7198 */     TextareaTag _jspx_th_html_005ftextarea_005f0 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7199 */     _jspx_th_html_005ftextarea_005f0.setPageContext(_jspx_page_context);
/* 7200 */     _jspx_th_html_005ftextarea_005f0.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7202 */     _jspx_th_html_005ftextarea_005f0.setRows("3");
/*      */     
/* 7204 */     _jspx_th_html_005ftextarea_005f0.setCols("50");
/*      */     
/* 7206 */     _jspx_th_html_005ftextarea_005f0.setProperty("ccontent");
/*      */     
/* 7208 */     _jspx_th_html_005ftextarea_005f0.setStyleClass("formtextarea xlarge");
/* 7209 */     int _jspx_eval_html_005ftextarea_005f0 = _jspx_th_html_005ftextarea_005f0.doStartTag();
/* 7210 */     if (_jspx_th_html_005ftextarea_005f0.doEndTag() == 5) {
/* 7211 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 7212 */       return true;
/*      */     }
/* 7214 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f0);
/* 7215 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f1(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7220 */     PageContext pageContext = _jspx_page_context;
/* 7221 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7223 */     CheckboxTag _jspx_th_html_005fcheckbox_005f1 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7224 */     _jspx_th_html_005fcheckbox_005f1.setPageContext(_jspx_page_context);
/* 7225 */     _jspx_th_html_005fcheckbox_005f1.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7227 */     _jspx_th_html_005fcheckbox_005f1.setProperty("regexChk");
/*      */     
/* 7229 */     _jspx_th_html_005fcheckbox_005f1.setOnclick("javascript:RegexCheck()");
/* 7230 */     int _jspx_eval_html_005fcheckbox_005f1 = _jspx_th_html_005fcheckbox_005f1.doStartTag();
/* 7231 */     if (_jspx_th_html_005fcheckbox_005f1.doEndTag() == 5) {
/* 7232 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 7233 */       return true;
/*      */     }
/* 7235 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f1);
/* 7236 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f2(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7241 */     PageContext pageContext = _jspx_page_context;
/* 7242 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7244 */     CheckboxTag _jspx_th_html_005fcheckbox_005f2 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7245 */     _jspx_th_html_005fcheckbox_005f2.setPageContext(_jspx_page_context);
/* 7246 */     _jspx_th_html_005fcheckbox_005f2.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7248 */     _jspx_th_html_005fcheckbox_005f2.setProperty("checkEmpty");
/* 7249 */     int _jspx_eval_html_005fcheckbox_005f2 = _jspx_th_html_005fcheckbox_005f2.doStartTag();
/* 7250 */     if (_jspx_th_html_005fcheckbox_005f2.doEndTag() == 5) {
/* 7251 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 7252 */       return true;
/*      */     }
/* 7254 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f2);
/* 7255 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f6(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7260 */     PageContext pageContext = _jspx_page_context;
/* 7261 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7263 */     RadioTag _jspx_th_html_005fradio_005f6 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7264 */     _jspx_th_html_005fradio_005f6.setPageContext(_jspx_page_context);
/* 7265 */     _jspx_th_html_005fradio_005f6.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7267 */     _jspx_th_html_005fradio_005f6.setProperty("clearCondition");
/*      */     
/* 7269 */     _jspx_th_html_005fradio_005f6.setValue("0");
/* 7270 */     int _jspx_eval_html_005fradio_005f6 = _jspx_th_html_005fradio_005f6.doStartTag();
/* 7271 */     if (_jspx_th_html_005fradio_005f6.doEndTag() == 5) {
/* 7272 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 7273 */       return true;
/*      */     }
/* 7275 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f6);
/* 7276 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f7(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7281 */     PageContext pageContext = _jspx_page_context;
/* 7282 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7284 */     RadioTag _jspx_th_html_005fradio_005f7 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.get(RadioTag.class);
/* 7285 */     _jspx_th_html_005fradio_005f7.setPageContext(_jspx_page_context);
/* 7286 */     _jspx_th_html_005fradio_005f7.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7288 */     _jspx_th_html_005fradio_005f7.setProperty("clearCondition");
/*      */     
/* 7290 */     _jspx_th_html_005fradio_005f7.setValue("1");
/* 7291 */     int _jspx_eval_html_005fradio_005f7 = _jspx_th_html_005fradio_005f7.doStartTag();
/* 7292 */     if (_jspx_th_html_005fradio_005f7.doEndTag() == 5) {
/* 7293 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 7294 */       return true;
/*      */     }
/* 7296 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fradio_005f7);
/* 7297 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f1(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7302 */     PageContext pageContext = _jspx_page_context;
/* 7303 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7305 */     TextareaTag _jspx_th_html_005ftextarea_005f1 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7306 */     _jspx_th_html_005ftextarea_005f1.setPageContext(_jspx_page_context);
/* 7307 */     _jspx_th_html_005ftextarea_005f1.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7309 */     _jspx_th_html_005ftextarea_005f1.setProperty("clearConditionContent");
/*      */     
/* 7311 */     _jspx_th_html_005ftextarea_005f1.setCols("50");
/*      */     
/* 7313 */     _jspx_th_html_005ftextarea_005f1.setRows("3");
/*      */     
/* 7315 */     _jspx_th_html_005ftextarea_005f1.setStyleClass("formtextarea xlarge");
/* 7316 */     int _jspx_eval_html_005ftextarea_005f1 = _jspx_th_html_005ftextarea_005f1.doStartTag();
/* 7317 */     if (_jspx_th_html_005ftextarea_005f1.doEndTag() == 5) {
/* 7318 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 7319 */       return true;
/*      */     }
/* 7321 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005frows_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f1);
/* 7322 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f3(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7327 */     PageContext pageContext = _jspx_page_context;
/* 7328 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7330 */     CheckboxTag _jspx_th_html_005fcheckbox_005f3 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7331 */     _jspx_th_html_005fcheckbox_005f3.setPageContext(_jspx_page_context);
/* 7332 */     _jspx_th_html_005fcheckbox_005f3.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7334 */     _jspx_th_html_005fcheckbox_005f3.setProperty("fileDirAge");
/*      */     
/* 7336 */     _jspx_th_html_005fcheckbox_005f3.setOnclick("javascript:FileDirAgeCheck()");
/* 7337 */     int _jspx_eval_html_005fcheckbox_005f3 = _jspx_th_html_005fcheckbox_005f3.doStartTag();
/* 7338 */     if (_jspx_th_html_005fcheckbox_005f3.doEndTag() == 5) {
/* 7339 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 7340 */       return true;
/*      */     }
/* 7342 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f3);
/* 7343 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f2(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7348 */     PageContext pageContext = _jspx_page_context;
/* 7349 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7351 */     TextTag _jspx_th_html_005ftext_005f2 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7352 */     _jspx_th_html_005ftext_005f2.setPageContext(_jspx_page_context);
/* 7353 */     _jspx_th_html_005ftext_005f2.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7355 */     _jspx_th_html_005ftext_005f2.setSize("3");
/*      */     
/* 7357 */     _jspx_th_html_005ftext_005f2.setProperty("timeval");
/*      */     
/* 7359 */     _jspx_th_html_005ftext_005f2.setStyleClass("formtext");
/* 7360 */     int _jspx_eval_html_005ftext_005f2 = _jspx_th_html_005ftext_005f2.doStartTag();
/* 7361 */     if (_jspx_th_html_005ftext_005f2.doEndTag() == 5) {
/* 7362 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 7363 */       return true;
/*      */     }
/* 7365 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f2);
/* 7366 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f4(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7371 */     PageContext pageContext = _jspx_page_context;
/* 7372 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7374 */     CheckboxTag _jspx_th_html_005fcheckbox_005f4 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.get(CheckboxTag.class);
/* 7375 */     _jspx_th_html_005fcheckbox_005f4.setPageContext(_jspx_page_context);
/* 7376 */     _jspx_th_html_005fcheckbox_005f4.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7378 */     _jspx_th_html_005fcheckbox_005f4.setProperty("subDirCntChk");
/* 7379 */     int _jspx_eval_html_005fcheckbox_005f4 = _jspx_th_html_005fcheckbox_005f4.doStartTag();
/* 7380 */     if (_jspx_th_html_005fcheckbox_005f4.doEndTag() == 5) {
/* 7381 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 7382 */       return true;
/*      */     }
/* 7384 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f4);
/* 7385 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f8(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7390 */     PageContext pageContext = _jspx_page_context;
/* 7391 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7393 */     RadioTag _jspx_th_html_005fradio_005f8 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7394 */     _jspx_th_html_005fradio_005f8.setPageContext(_jspx_page_context);
/* 7395 */     _jspx_th_html_005fradio_005f8.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7397 */     _jspx_th_html_005fradio_005f8.setProperty("isCommand");
/*      */     
/* 7399 */     _jspx_th_html_005fradio_005f8.setValue("true");
/*      */     
/* 7401 */     _jspx_th_html_005fradio_005f8.setOnclick("javascript:manageCommand()");
/* 7402 */     int _jspx_eval_html_005fradio_005f8 = _jspx_th_html_005fradio_005f8.doStartTag();
/* 7403 */     if (_jspx_th_html_005fradio_005f8.doEndTag() == 5) {
/* 7404 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 7405 */       return true;
/*      */     }
/* 7407 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f8);
/* 7408 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fradio_005f9(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7413 */     PageContext pageContext = _jspx_page_context;
/* 7414 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7416 */     RadioTag _jspx_th_html_005fradio_005f9 = (RadioTag)this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.get(RadioTag.class);
/* 7417 */     _jspx_th_html_005fradio_005f9.setPageContext(_jspx_page_context);
/* 7418 */     _jspx_th_html_005fradio_005f9.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7420 */     _jspx_th_html_005fradio_005f9.setProperty("isCommand");
/*      */     
/* 7422 */     _jspx_th_html_005fradio_005f9.setValue("false");
/*      */     
/* 7424 */     _jspx_th_html_005fradio_005f9.setOnclick("javascript:manageCommand()");
/* 7425 */     int _jspx_eval_html_005fradio_005f9 = _jspx_th_html_005fradio_005f9.doStartTag();
/* 7426 */     if (_jspx_th_html_005fradio_005f9.doEndTag() == 5) {
/* 7427 */       this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 7428 */       return true;
/*      */     }
/* 7430 */     this._005fjspx_005ftagPool_005fhtml_005fradio_0026_005fvalue_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fradio_005f9);
/* 7431 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f3(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7436 */     PageContext pageContext = _jspx_page_context;
/* 7437 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7439 */     TextTag _jspx_th_html_005ftext_005f3 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7440 */     _jspx_th_html_005ftext_005f3.setPageContext(_jspx_page_context);
/* 7441 */     _jspx_th_html_005ftext_005f3.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7443 */     _jspx_th_html_005ftext_005f3.setSize("25");
/*      */     
/* 7445 */     _jspx_th_html_005ftext_005f3.setProperty("serverpath");
/*      */     
/* 7447 */     _jspx_th_html_005ftext_005f3.setStyleClass("formtext");
/* 7448 */     int _jspx_eval_html_005ftext_005f3 = _jspx_th_html_005ftext_005f3.doStartTag();
/* 7449 */     if (_jspx_th_html_005ftext_005f3.doEndTag() == 5) {
/* 7450 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 7451 */       return true;
/*      */     }
/* 7453 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f3);
/* 7454 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f4(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7459 */     PageContext pageContext = _jspx_page_context;
/* 7460 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7462 */     TextTag _jspx_th_html_005ftext_005f4 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7463 */     _jspx_th_html_005ftext_005f4.setPageContext(_jspx_page_context);
/* 7464 */     _jspx_th_html_005ftext_005f4.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7466 */     _jspx_th_html_005ftext_005f4.setSize("25");
/*      */     
/* 7468 */     _jspx_th_html_005ftext_005f4.setProperty("workingdirectory");
/*      */     
/* 7470 */     _jspx_th_html_005ftext_005f4.setStyleClass("formtext");
/* 7471 */     int _jspx_eval_html_005ftext_005f4 = _jspx_th_html_005ftext_005f4.doStartTag();
/* 7472 */     if (_jspx_th_html_005ftext_005f4.doEndTag() == 5) {
/* 7473 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 7474 */       return true;
/*      */     }
/* 7476 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f4);
/* 7477 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f5(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7482 */     PageContext pageContext = _jspx_page_context;
/* 7483 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7485 */     TextTag _jspx_th_html_005ftext_005f5 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7486 */     _jspx_th_html_005ftext_005f5.setPageContext(_jspx_page_context);
/* 7487 */     _jspx_th_html_005ftext_005f5.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7489 */     _jspx_th_html_005ftext_005f5.setSize("25");
/*      */     
/* 7491 */     _jspx_th_html_005ftext_005f5.setProperty("outputfile");
/*      */     
/* 7493 */     _jspx_th_html_005ftext_005f5.setStyleClass("formtext");
/* 7494 */     int _jspx_eval_html_005ftext_005f5 = _jspx_th_html_005ftext_005f5.doStartTag();
/* 7495 */     if (_jspx_th_html_005ftext_005f5.doEndTag() == 5) {
/* 7496 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 7497 */       return true;
/*      */     }
/* 7499 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f5);
/* 7500 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fcheckbox_005f5(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7505 */     PageContext pageContext = _jspx_page_context;
/* 7506 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7508 */     CheckboxTag _jspx_th_html_005fcheckbox_005f5 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.get(CheckboxTag.class);
/* 7509 */     _jspx_th_html_005fcheckbox_005f5.setPageContext(_jspx_page_context);
/* 7510 */     _jspx_th_html_005fcheckbox_005f5.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7512 */     _jspx_th_html_005fcheckbox_005f5.setProperty("opfile");
/*      */     
/* 7514 */     _jspx_th_html_005fcheckbox_005f5.setOnclick("javascript:manageopFile()");
/* 7515 */     int _jspx_eval_html_005fcheckbox_005f5 = _jspx_th_html_005fcheckbox_005f5.doStartTag();
/* 7516 */     if (_jspx_th_html_005fcheckbox_005f5.doEndTag() == 5) {
/* 7517 */       this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 7518 */       return true;
/*      */     }
/* 7520 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fcheckbox_005f5);
/* 7521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f6(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7526 */     PageContext pageContext = _jspx_page_context;
/* 7527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7529 */     TextTag _jspx_th_html_005ftext_005f6 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7530 */     _jspx_th_html_005ftext_005f6.setPageContext(_jspx_page_context);
/* 7531 */     _jspx_th_html_005ftext_005f6.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7533 */     _jspx_th_html_005ftext_005f6.setSize("25");
/*      */     
/* 7535 */     _jspx_th_html_005ftext_005f6.setProperty("outputfile");
/*      */     
/* 7537 */     _jspx_th_html_005ftext_005f6.setStyleClass("formtext");
/*      */     
/* 7539 */     _jspx_th_html_005ftext_005f6.setStyle("width:190px;");
/* 7540 */     int _jspx_eval_html_005ftext_005f6 = _jspx_th_html_005ftext_005f6.doStartTag();
/* 7541 */     if (_jspx_th_html_005ftext_005f6.doEndTag() == 5) {
/* 7542 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 7543 */       return true;
/*      */     }
/* 7545 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fstyle_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f6);
/* 7546 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f2(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7551 */     PageContext pageContext = _jspx_page_context;
/* 7552 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7554 */     TextareaTag _jspx_th_html_005ftextarea_005f2 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7555 */     _jspx_th_html_005ftextarea_005f2.setPageContext(_jspx_page_context);
/* 7556 */     _jspx_th_html_005ftextarea_005f2.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7558 */     _jspx_th_html_005ftextarea_005f2.setCols("22");
/*      */     
/* 7560 */     _jspx_th_html_005ftextarea_005f2.setProperty("string_att");
/*      */     
/* 7562 */     _jspx_th_html_005ftextarea_005f2.setStyleClass("formtextarea");
/*      */     
/* 7564 */     _jspx_th_html_005ftextarea_005f2.setStyle("width:190px;");
/* 7565 */     int _jspx_eval_html_005ftextarea_005f2 = _jspx_th_html_005ftextarea_005f2.doStartTag();
/* 7566 */     if (_jspx_th_html_005ftextarea_005f2.doEndTag() == 5) {
/* 7567 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 7568 */       return true;
/*      */     }
/* 7570 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f2);
/* 7571 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftextarea_005f3(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7576 */     PageContext pageContext = _jspx_page_context;
/* 7577 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7579 */     TextareaTag _jspx_th_html_005ftextarea_005f3 = (TextareaTag)this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.get(TextareaTag.class);
/* 7580 */     _jspx_th_html_005ftextarea_005f3.setPageContext(_jspx_page_context);
/* 7581 */     _jspx_th_html_005ftextarea_005f3.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7583 */     _jspx_th_html_005ftextarea_005f3.setCols("22");
/*      */     
/* 7585 */     _jspx_th_html_005ftextarea_005f3.setProperty("numeric_att");
/*      */     
/* 7587 */     _jspx_th_html_005ftextarea_005f3.setStyleClass("formtextarea");
/*      */     
/* 7589 */     _jspx_th_html_005ftextarea_005f3.setStyle("width:190px;");
/* 7590 */     int _jspx_eval_html_005ftextarea_005f3 = _jspx_th_html_005ftextarea_005f3.doStartTag();
/* 7591 */     if (_jspx_th_html_005ftextarea_005f3.doEndTag() == 5) {
/* 7592 */       this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f3);
/* 7593 */       return true;
/*      */     }
/* 7595 */     this._005fjspx_005ftagPool_005fhtml_005ftextarea_0026_005fstyleClass_005fstyle_005fproperty_005fcols_005fnobody.reuse(_jspx_th_html_005ftextarea_005f3);
/* 7596 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f7(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7601 */     PageContext pageContext = _jspx_page_context;
/* 7602 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7604 */     TextTag _jspx_th_html_005ftext_005f7 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7605 */     _jspx_th_html_005ftext_005f7.setPageContext(_jspx_page_context);
/* 7606 */     _jspx_th_html_005ftext_005f7.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7608 */     _jspx_th_html_005ftext_005f7.setSize("1");
/*      */     
/* 7610 */     _jspx_th_html_005ftext_005f7.setProperty("delimiter");
/*      */     
/* 7612 */     _jspx_th_html_005ftext_005f7.setStyleClass("formtext");
/* 7613 */     int _jspx_eval_html_005ftext_005f7 = _jspx_th_html_005ftext_005f7.doStartTag();
/* 7614 */     if (_jspx_th_html_005ftext_005f7.doEndTag() == 5) {
/* 7615 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 7616 */       return true;
/*      */     }
/* 7618 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f7);
/* 7619 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f8(JspTag _jspx_th_c_005fif_005f26, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7624 */     PageContext pageContext = _jspx_page_context;
/* 7625 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7627 */     TextTag _jspx_th_html_005ftext_005f8 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7628 */     _jspx_th_html_005ftext_005f8.setPageContext(_jspx_page_context);
/* 7629 */     _jspx_th_html_005ftext_005f8.setParent((Tag)_jspx_th_c_005fif_005f26);
/*      */     
/* 7631 */     _jspx_th_html_005ftext_005f8.setSize("25");
/*      */     
/* 7633 */     _jspx_th_html_005ftext_005f8.setProperty("message");
/*      */     
/* 7635 */     _jspx_th_html_005ftext_005f8.setStyleClass("formtext");
/* 7636 */     int _jspx_eval_html_005ftext_005f8 = _jspx_th_html_005ftext_005f8.doStartTag();
/* 7637 */     if (_jspx_th_html_005ftext_005f8.doEndTag() == 5) {
/* 7638 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 7639 */       return true;
/*      */     }
/* 7641 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f8);
/* 7642 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f27(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7647 */     PageContext pageContext = _jspx_page_context;
/* 7648 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7650 */     IfTag _jspx_th_c_005fif_005f27 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7651 */     _jspx_th_c_005fif_005f27.setPageContext(_jspx_page_context);
/* 7652 */     _jspx_th_c_005fif_005f27.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7654 */     _jspx_th_c_005fif_005f27.setTest("${param.type=='QENGINE'}");
/* 7655 */     int _jspx_eval_c_005fif_005f27 = _jspx_th_c_005fif_005f27.doStartTag();
/* 7656 */     if (_jspx_eval_c_005fif_005f27 != 0) {
/*      */       for (;;) {
/* 7658 */         out.write("\n  <tr> \n    <td width=\"25%\" height=\"35\" class=\"bodytext label-align\" >Script file</td>");
/* 7659 */         out.write("\n    <td height=\"35\" colspan=\"3\" class=\"bodytext\"> \n    <!--");
/* 7660 */         if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_c_005fif_005f27, _jspx_page_context))
/* 7661 */           return true;
/* 7662 */         out.write("-->\n    ");
/* 7663 */         if (_jspx_meth_html_005ftext_005f9(_jspx_th_c_005fif_005f27, _jspx_page_context))
/* 7664 */           return true;
/* 7665 */         out.write(" \n    \n    </td>\n    \n  </tr>\n");
/* 7666 */         int evalDoAfterBody = _jspx_th_c_005fif_005f27.doAfterBody();
/* 7667 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7671 */     if (_jspx_th_c_005fif_005f27.doEndTag() == 5) {
/* 7672 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 7673 */       return true;
/*      */     }
/* 7675 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f27);
/* 7676 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_c_005fif_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7681 */     PageContext pageContext = _jspx_page_context;
/* 7682 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7684 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 7685 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 7686 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_c_005fif_005f27);
/*      */     
/* 7688 */     _jspx_th_bean_005fwrite_005f0.setName("AMActionForm");
/*      */     
/* 7690 */     _jspx_th_bean_005fwrite_005f0.setProperty("message");
/* 7691 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 7692 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 7693 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 7694 */       return true;
/*      */     }
/* 7696 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 7697 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f9(JspTag _jspx_th_c_005fif_005f27, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7702 */     PageContext pageContext = _jspx_page_context;
/* 7703 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7705 */     TextTag _jspx_th_html_005ftext_005f9 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fnobody.get(TextTag.class);
/* 7706 */     _jspx_th_html_005ftext_005f9.setPageContext(_jspx_page_context);
/* 7707 */     _jspx_th_html_005ftext_005f9.setParent((Tag)_jspx_th_c_005fif_005f27);
/*      */     
/* 7709 */     _jspx_th_html_005ftext_005f9.setSize("80");
/*      */     
/* 7711 */     _jspx_th_html_005ftext_005f9.setProperty("message");
/*      */     
/* 7713 */     _jspx_th_html_005ftext_005f9.setStyleClass("formtext");
/*      */     
/* 7715 */     _jspx_th_html_005ftext_005f9.setOnfocus("javascript:this.blur()");
/* 7716 */     int _jspx_eval_html_005ftext_005f9 = _jspx_th_html_005ftext_005f9.doStartTag();
/* 7717 */     if (_jspx_th_html_005ftext_005f9.doEndTag() == 5) {
/* 7718 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 7719 */       return true;
/*      */     }
/* 7721 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fonfocus_005fnobody.reuse(_jspx_th_html_005ftext_005f9);
/* 7722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f10(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7727 */     PageContext pageContext = _jspx_page_context;
/* 7728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7730 */     TextTag _jspx_th_html_005ftext_005f10 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7731 */     _jspx_th_html_005ftext_005f10.setPageContext(_jspx_page_context);
/* 7732 */     _jspx_th_html_005ftext_005f10.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7734 */     _jspx_th_html_005ftext_005f10.setProperty("pollInterval");
/*      */     
/* 7736 */     _jspx_th_html_005ftext_005f10.setStyleClass("formtext small");
/*      */     
/* 7738 */     _jspx_th_html_005ftext_005f10.setSize("25");
/* 7739 */     int _jspx_eval_html_005ftext_005f10 = _jspx_th_html_005ftext_005f10.doStartTag();
/* 7740 */     if (_jspx_th_html_005ftext_005f10.doEndTag() == 5) {
/* 7741 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 7742 */       return true;
/*      */     }
/* 7744 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f10);
/* 7745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f11(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7750 */     PageContext pageContext = _jspx_page_context;
/* 7751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7753 */     TextTag _jspx_th_html_005ftext_005f11 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7754 */     _jspx_th_html_005ftext_005f11.setPageContext(_jspx_page_context);
/* 7755 */     _jspx_th_html_005ftext_005f11.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7757 */     _jspx_th_html_005ftext_005f11.setProperty("timeout");
/*      */     
/* 7759 */     _jspx_th_html_005ftext_005f11.setStyleClass("formtext small");
/*      */     
/* 7761 */     _jspx_th_html_005ftext_005f11.setSize("25");
/* 7762 */     int _jspx_eval_html_005ftext_005f11 = _jspx_th_html_005ftext_005f11.doStartTag();
/* 7763 */     if (_jspx_th_html_005ftext_005f11.doEndTag() == 5) {
/* 7764 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 7765 */       return true;
/*      */     }
/* 7767 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f11);
/* 7768 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f12(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7773 */     PageContext pageContext = _jspx_page_context;
/* 7774 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7776 */     TextTag _jspx_th_html_005ftext_005f12 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.get(TextTag.class);
/* 7777 */     _jspx_th_html_005ftext_005f12.setPageContext(_jspx_page_context);
/* 7778 */     _jspx_th_html_005ftext_005f12.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7780 */     _jspx_th_html_005ftext_005f12.setProperty("mode");
/*      */     
/* 7782 */     _jspx_th_html_005ftext_005f12.setStyleClass("formtext");
/* 7783 */     int _jspx_eval_html_005ftext_005f12 = _jspx_th_html_005ftext_005f12.doStartTag();
/* 7784 */     if (_jspx_th_html_005ftext_005f12.doEndTag() == 5) {
/* 7785 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 7786 */       return true;
/*      */     }
/* 7788 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f12);
/* 7789 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f13(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7794 */     PageContext pageContext = _jspx_page_context;
/* 7795 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7797 */     TextTag _jspx_th_html_005ftext_005f13 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.get(TextTag.class);
/* 7798 */     _jspx_th_html_005ftext_005f13.setPageContext(_jspx_page_context);
/* 7799 */     _jspx_th_html_005ftext_005f13.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7801 */     _jspx_th_html_005ftext_005f13.setProperty("mode");
/*      */     
/* 7803 */     _jspx_th_html_005ftext_005f13.setStyleClass("formtext");
/*      */     
/* 7805 */     _jspx_th_html_005ftext_005f13.setSize("25");
/* 7806 */     int _jspx_eval_html_005ftext_005f13 = _jspx_th_html_005ftext_005f13.doStartTag();
/* 7807 */     if (_jspx_th_html_005ftext_005f13.doEndTag() == 5) {
/* 7808 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 7809 */       return true;
/*      */     }
/* 7811 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleClass_005fsize_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f13);
/* 7812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f30(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7817 */     PageContext pageContext = _jspx_page_context;
/* 7818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7820 */     IfTag _jspx_th_c_005fif_005f30 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7821 */     _jspx_th_c_005fif_005f30.setPageContext(_jspx_page_context);
/* 7822 */     _jspx_th_c_005fif_005f30.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7824 */     _jspx_th_c_005fif_005f30.setTest("${!empty param.haid && empty invalidhaid}");
/* 7825 */     int _jspx_eval_c_005fif_005f30 = _jspx_th_c_005fif_005f30.doStartTag();
/* 7826 */     if (_jspx_eval_c_005fif_005f30 != 0) {
/*      */       for (;;) {
/* 7828 */         out.write("\n\t<input type=\"hidden\" name=\"haid\"  value='");
/* 7829 */         if (_jspx_meth_c_005fout_005f38(_jspx_th_c_005fif_005f30, _jspx_page_context))
/* 7830 */           return true;
/* 7831 */         out.write("'>\n\t");
/* 7832 */         int evalDoAfterBody = _jspx_th_c_005fif_005f30.doAfterBody();
/* 7833 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7837 */     if (_jspx_th_c_005fif_005f30.doEndTag() == 5) {
/* 7838 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 7839 */       return true;
/*      */     }
/* 7841 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f30);
/* 7842 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f38(JspTag _jspx_th_c_005fif_005f30, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7847 */     PageContext pageContext = _jspx_page_context;
/* 7848 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7850 */     OutTag _jspx_th_c_005fout_005f38 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7851 */     _jspx_th_c_005fout_005f38.setPageContext(_jspx_page_context);
/* 7852 */     _jspx_th_c_005fout_005f38.setParent((Tag)_jspx_th_c_005fif_005f30);
/*      */     
/* 7854 */     _jspx_th_c_005fout_005f38.setValue("${param.haid}");
/* 7855 */     int _jspx_eval_c_005fout_005f38 = _jspx_th_c_005fout_005f38.doStartTag();
/* 7856 */     if (_jspx_th_c_005fout_005f38.doEndTag() == 5) {
/* 7857 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7858 */       return true;
/*      */     }
/* 7860 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f38);
/* 7861 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f31(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7866 */     PageContext pageContext = _jspx_page_context;
/* 7867 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7869 */     IfTag _jspx_th_c_005fif_005f31 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 7870 */     _jspx_th_c_005fif_005f31.setPageContext(_jspx_page_context);
/* 7871 */     _jspx_th_c_005fif_005f31.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 7873 */     _jspx_th_c_005fif_005f31.setTest("${!empty param.name}");
/* 7874 */     int _jspx_eval_c_005fif_005f31 = _jspx_th_c_005fif_005f31.doStartTag();
/* 7875 */     if (_jspx_eval_c_005fif_005f31 != 0) {
/*      */       for (;;) {
/* 7877 */         out.write("\n        <input type=\"hidden\" name=\"name\"  value='");
/* 7878 */         if (_jspx_meth_c_005fout_005f39(_jspx_th_c_005fif_005f31, _jspx_page_context))
/* 7879 */           return true;
/* 7880 */         out.write("'>\n        ");
/* 7881 */         int evalDoAfterBody = _jspx_th_c_005fif_005f31.doAfterBody();
/* 7882 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 7886 */     if (_jspx_th_c_005fif_005f31.doEndTag() == 5) {
/* 7887 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 7888 */       return true;
/*      */     }
/* 7890 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f31);
/* 7891 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f39(JspTag _jspx_th_c_005fif_005f31, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7896 */     PageContext pageContext = _jspx_page_context;
/* 7897 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7899 */     OutTag _jspx_th_c_005fout_005f39 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 7900 */     _jspx_th_c_005fout_005f39.setPageContext(_jspx_page_context);
/* 7901 */     _jspx_th_c_005fout_005f39.setParent((Tag)_jspx_th_c_005fif_005f31);
/*      */     
/* 7903 */     _jspx_th_c_005fout_005f39.setValue("${param.name}");
/* 7904 */     int _jspx_eval_c_005fout_005f39 = _jspx_th_c_005fout_005f39.doStartTag();
/* 7905 */     if (_jspx_th_c_005fout_005f39.doEndTag() == 5) {
/* 7906 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7907 */       return true;
/*      */     }
/* 7909 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f39);
/* 7910 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7915 */     PageContext pageContext = _jspx_page_context;
/* 7916 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7918 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 7919 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 7920 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 7922 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 7924 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 7925 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 7926 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 7927 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 7928 */       return true;
/*      */     }
/* 7930 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 7931 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\Scriptconf_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */