/*      */ package org.apache.jsp.jsp;
/*      */ 
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
/*      */ public final class WSMLeftArea_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   51 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   54 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   55 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   56 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   63 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   68 */     ArrayList list = null;
/*   69 */     StringBuffer sbf = new StringBuffer();
/*   70 */     ManagedApplication mo = new ManagedApplication();
/*   71 */     if (distinct)
/*      */     {
/*   73 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   77 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   80 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   82 */       ArrayList row = (ArrayList)list.get(i);
/*   83 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   84 */       if (distinct) {
/*   85 */         sbf.append(row.get(0));
/*      */       } else
/*   87 */         sbf.append(row.get(1));
/*   88 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   91 */     return sbf.toString(); }
/*      */   
/*   93 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   96 */     if (severity == null)
/*      */     {
/*   98 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  100 */     if (severity.equals("5"))
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  104 */     if (severity.equals("1"))
/*      */     {
/*  106 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  111 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  118 */     if (severity == null)
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  122 */     if (severity.equals("1"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("4"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  130 */     if (severity.equals("5"))
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  137 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  143 */     if (severity == null)
/*      */     {
/*  145 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  147 */     if (severity.equals("5"))
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  151 */     if (severity.equals("1"))
/*      */     {
/*  153 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  157 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  163 */     if (severity == null)
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  167 */     if (severity.equals("1"))
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  171 */     if (severity.equals("4"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  175 */     if (severity.equals("5"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  181 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  187 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  193 */     if (severity == 5)
/*      */     {
/*  195 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  197 */     if (severity == 1)
/*      */     {
/*  199 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  204 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  210 */     if (severity == null)
/*      */     {
/*  212 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  214 */     if (severity.equals("5"))
/*      */     {
/*  216 */       if (isAvailability) {
/*  217 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  220 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  223 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  225 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  227 */     if (severity.equals("1"))
/*      */     {
/*  229 */       if (isAvailability) {
/*  230 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  233 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  240 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  247 */     if (severity == null)
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  251 */     if (severity.equals("5"))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("4"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  259 */     if (severity.equals("1"))
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  266 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  272 */     if (severity == null)
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("5"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("4"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("1"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  291 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  298 */     if (severity == null)
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  302 */     if (severity.equals("5"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  306 */     if (severity.equals("4"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  310 */     if (severity.equals("1"))
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  317 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  325 */     StringBuffer out = new StringBuffer();
/*  326 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  327 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  328 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  329 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  330 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  331 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  332 */     out.append("</tr>");
/*  333 */     out.append("</form></table>");
/*  334 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  341 */     if (val == null)
/*      */     {
/*  343 */       return "-";
/*      */     }
/*      */     
/*  346 */     String ret = FormatUtil.formatNumber(val);
/*  347 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  348 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  351 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  355 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  363 */     StringBuffer out = new StringBuffer();
/*  364 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  365 */     out.append("<tr>");
/*  366 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  368 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  370 */     out.append("</tr>");
/*  371 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  375 */       if (j % 2 == 0)
/*      */       {
/*  377 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  381 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  384 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  386 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  389 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  393 */       out.append("</tr>");
/*      */     }
/*  395 */     out.append("</table>");
/*  396 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  397 */     out.append("<tr>");
/*  398 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  399 */     out.append("</tr>");
/*  400 */     out.append("</table>");
/*  401 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  407 */     StringBuffer out = new StringBuffer();
/*  408 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  409 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  410 */     out.append("<tr>");
/*  411 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  412 */     out.append("<tr>");
/*  413 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  414 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  415 */     out.append("</tr>");
/*  416 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  419 */       out.append("<tr>");
/*  420 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  421 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  422 */       out.append("</tr>");
/*      */     }
/*      */     
/*  425 */     out.append("</table>");
/*  426 */     out.append("</table>");
/*  427 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  432 */     if (severity.equals("0"))
/*      */     {
/*  434 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  438 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  445 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  458 */     StringBuffer out = new StringBuffer();
/*  459 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  460 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  462 */       out.append("<tr>");
/*  463 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  464 */       out.append("</tr>");
/*      */       
/*      */ 
/*  467 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  469 */         String borderclass = "";
/*      */         
/*      */ 
/*  472 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  474 */         out.append("<tr>");
/*      */         
/*  476 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  477 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  478 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  484 */     out.append("</table><br>");
/*  485 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  486 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  488 */       List sLinks = secondLevelOfLinks[0];
/*  489 */       List sText = secondLevelOfLinks[1];
/*  490 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  493 */         out.append("<tr>");
/*  494 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  495 */         out.append("</tr>");
/*  496 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  498 */           String borderclass = "";
/*      */           
/*      */ 
/*  501 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  503 */           out.append("<tr>");
/*      */           
/*  505 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  506 */           if (sLinks.get(i).toString().length() == 0) {
/*  507 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  510 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  512 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  516 */     out.append("</table>");
/*  517 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  524 */     StringBuffer out = new StringBuffer();
/*  525 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  526 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  528 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  530 */         out.append("<tr>");
/*  531 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  532 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  536 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  538 */           String borderclass = "";
/*      */           
/*      */ 
/*  541 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  543 */           out.append("<tr>");
/*      */           
/*  545 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  546 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  547 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  550 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  553 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  558 */     out.append("</table><br>");
/*  559 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  560 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  562 */       List sLinks = secondLevelOfLinks[0];
/*  563 */       List sText = secondLevelOfLinks[1];
/*  564 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  567 */         out.append("<tr>");
/*  568 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  569 */         out.append("</tr>");
/*  570 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  572 */           String borderclass = "";
/*      */           
/*      */ 
/*  575 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  577 */           out.append("<tr>");
/*      */           
/*  579 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  580 */           if (sLinks.get(i).toString().length() == 0) {
/*  581 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  584 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  586 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  590 */     out.append("</table>");
/*  591 */     return out.toString();
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
/*  604 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  619 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  622 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  625 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  633 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  638 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  643 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  648 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  653 */     if (val != null)
/*      */     {
/*  655 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  659 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  664 */     if (val == null) {
/*  665 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  669 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  674 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  680 */     if (val != null)
/*      */     {
/*  682 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  686 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  692 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  697 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  701 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  706 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  711 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  716 */     String hostaddress = "";
/*  717 */     String ip = request.getHeader("x-forwarded-for");
/*  718 */     if (ip == null)
/*  719 */       ip = request.getRemoteAddr();
/*  720 */     InetAddress add = null;
/*  721 */     if (ip.equals("127.0.0.1")) {
/*  722 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  726 */       add = InetAddress.getByName(ip);
/*      */     }
/*  728 */     hostaddress = add.getHostName();
/*  729 */     if (hostaddress.indexOf('.') != -1) {
/*  730 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  731 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  735 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  740 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  746 */     if (severity == null)
/*      */     {
/*  748 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  750 */     if (severity.equals("5"))
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  754 */     if (severity.equals("1"))
/*      */     {
/*  756 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  761 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  766 */     ResultSet set = null;
/*  767 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  768 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  770 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  771 */       if (set.next()) { String str1;
/*  772 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  773 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  776 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  781 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  784 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  786 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  790 */     StringBuffer rca = new StringBuffer();
/*  791 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  792 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  795 */     int rcalength = key.length();
/*  796 */     String split = "6. ";
/*  797 */     int splitPresent = key.indexOf(split);
/*  798 */     String div1 = "";String div2 = "";
/*  799 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  801 */       if (rcalength > 180) {
/*  802 */         rca.append("<span class=\"rca-critical-text\">");
/*  803 */         getRCATrimmedText(key, rca);
/*  804 */         rca.append("</span>");
/*      */       } else {
/*  806 */         rca.append("<span class=\"rca-critical-text\">");
/*  807 */         rca.append(key);
/*  808 */         rca.append("</span>");
/*      */       }
/*  810 */       return rca.toString();
/*      */     }
/*  812 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  813 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  814 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  815 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  816 */     getRCATrimmedText(div1, rca);
/*  817 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  820 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  821 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  822 */     getRCATrimmedText(div2, rca);
/*  823 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  825 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  830 */     String[] st = msg.split("<br>");
/*  831 */     for (int i = 0; i < st.length; i++) {
/*  832 */       String s = st[i];
/*  833 */       if (s.length() > 180) {
/*  834 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  836 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  840 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  841 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  843 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  847 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  848 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  849 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  852 */       if (key == null) {
/*  853 */         return ret;
/*      */       }
/*      */       
/*  856 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  857 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  860 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  861 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  862 */       set = AMConnectionPool.executeQueryStmt(query);
/*  863 */       if (set.next())
/*      */       {
/*  865 */         String helpLink = set.getString("LINK");
/*  866 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  869 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  875 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  894 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  885 */         if (set != null) {
/*  886 */           AMConnectionPool.closeStatement(set);
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
/*  900 */     Properties temp = FaultUtil.getStatus(entitylist, false);
/*  901 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  903 */       String entityStr = (String)keys.nextElement();
/*  904 */       String mmessage = temp.getProperty(entityStr);
/*  905 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  906 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  908 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  914 */     Properties temp = FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/*      */   private void debug(String debugMessage)
/*      */   {
/*  927 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  937 */     String des = new String();
/*  938 */     while (str.indexOf(find) != -1) {
/*  939 */       des = des + str.substring(0, str.indexOf(find));
/*  940 */       des = des + replace;
/*  941 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  943 */     des = des + str;
/*  944 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  951 */       if (alert == null)
/*      */       {
/*  953 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  955 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  957 */         return "&nbsp;";
/*      */       }
/*      */       
/*  960 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  962 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  965 */       int rcalength = test.length();
/*  966 */       if (rcalength < 300)
/*      */       {
/*  968 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  972 */       StringBuffer out = new StringBuffer();
/*  973 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  974 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  975 */       out.append("</div>");
/*  976 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  977 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  978 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  983 */       ex.printStackTrace();
/*      */     }
/*  985 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  991 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  996 */     ArrayList attribIDs = new ArrayList();
/*  997 */     ArrayList resIDs = new ArrayList();
/*  998 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1000 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1002 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1004 */       String resourceid = "";
/* 1005 */       String resourceType = "";
/* 1006 */       if (type == 2) {
/* 1007 */         resourceid = (String)row.get(0);
/* 1008 */         resourceType = (String)row.get(3);
/*      */       }
/* 1010 */       else if (type == 3) {
/* 1011 */         resourceid = (String)row.get(0);
/* 1012 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1015 */         resourceid = (String)row.get(6);
/* 1016 */         resourceType = (String)row.get(7);
/*      */       }
/* 1018 */       resIDs.add(resourceid);
/* 1019 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1020 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1022 */       String healthentity = null;
/* 1023 */       String availentity = null;
/* 1024 */       if (healthid != null) {
/* 1025 */         healthentity = resourceid + "_" + healthid;
/* 1026 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1029 */       if (availid != null) {
/* 1030 */         availentity = resourceid + "_" + availid;
/* 1031 */         entitylist.add(availentity);
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
/* 1045 */     Properties alert = getStatus(entitylist);
/* 1046 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1051 */     int size = monitorList.size();
/*      */     
/* 1053 */     String[] severity = new String[size];
/*      */     
/* 1055 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1057 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1058 */       String resourceName1 = (String)row1.get(7);
/* 1059 */       String resourceid1 = (String)row1.get(6);
/* 1060 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1061 */       if (severity[j] == null)
/*      */       {
/* 1063 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1067 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1069 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1071 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1074 */         if (sev > 0) {
/* 1075 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1076 */           monitorList.set(k, monitorList.get(j));
/* 1077 */           monitorList.set(j, t);
/* 1078 */           String temp = severity[k];
/* 1079 */           severity[k] = severity[j];
/* 1080 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1086 */     int z = 0;
/* 1087 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1090 */       int i = 0;
/* 1091 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1094 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1098 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1102 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1104 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1107 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1111 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1114 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1115 */       String resourceName1 = (String)row1.get(7);
/* 1116 */       String resourceid1 = (String)row1.get(6);
/* 1117 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1118 */       if (hseverity[j] == null)
/*      */       {
/* 1120 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1125 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1127 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1130 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1133 */         if (hsev > 0) {
/* 1134 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1135 */           monitorList.set(k, monitorList.get(j));
/* 1136 */           monitorList.set(j, t);
/* 1137 */           String temp1 = hseverity[k];
/* 1138 */           hseverity[k] = hseverity[j];
/* 1139 */           hseverity[j] = temp1;
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
/* 1151 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1152 */     boolean forInventory = false;
/* 1153 */     String trdisplay = "none";
/* 1154 */     String plusstyle = "inline";
/* 1155 */     String minusstyle = "none";
/* 1156 */     String haidTopLevel = "";
/* 1157 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1159 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1161 */         haidTopLevel = request.getParameter("haid");
/* 1162 */         forInventory = true;
/* 1163 */         trdisplay = "table-row;";
/* 1164 */         plusstyle = "none";
/* 1165 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1172 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1175 */     ArrayList listtoreturn = new ArrayList();
/* 1176 */     StringBuffer toreturn = new StringBuffer();
/* 1177 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1178 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1179 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1181 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1183 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1184 */       String childresid = (String)singlerow.get(0);
/* 1185 */       String childresname = (String)singlerow.get(1);
/* 1186 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1187 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1188 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1189 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1190 */       String unmanagestatus = (String)singlerow.get(5);
/* 1191 */       String actionstatus = (String)singlerow.get(6);
/* 1192 */       String linkclass = "monitorgp-links";
/* 1193 */       String titleforres = childresname;
/* 1194 */       String titilechildresname = childresname;
/* 1195 */       String childimg = "/images/trcont.png";
/* 1196 */       String flag = "enable";
/* 1197 */       String dcstarted = (String)singlerow.get(8);
/* 1198 */       String configMonitor = "";
/* 1199 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1200 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1202 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1204 */       if (singlerow.get(7) != null)
/*      */       {
/* 1206 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1208 */       String haiGroupType = "0";
/* 1209 */       if ("HAI".equals(childtype))
/*      */       {
/* 1211 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1213 */       childimg = "/images/trend.png";
/* 1214 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1215 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1216 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1218 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1220 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1222 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1223 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1226 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1228 */         linkclass = "disabledtext";
/* 1229 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1231 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1232 */       String availmouseover = "";
/* 1233 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1235 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1237 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1238 */       String healthmouseover = "";
/* 1239 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1241 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1244 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1245 */       int spacing = 0;
/* 1246 */       if (level >= 1)
/*      */       {
/* 1248 */         spacing = 40 * level;
/*      */       }
/* 1250 */       if (childtype.equals("HAI"))
/*      */       {
/* 1252 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1253 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1254 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1256 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1257 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1258 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1259 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1260 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1261 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1262 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1263 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1264 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1265 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1266 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1268 */         if (!forInventory)
/*      */         {
/* 1270 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1273 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1275 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1277 */           actions = editlink + actions;
/*      */         }
/* 1279 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1281 */           actions = actions + associatelink;
/*      */         }
/* 1283 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1284 */         String arrowimg = "";
/* 1285 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1287 */           actions = "";
/* 1288 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1289 */           checkbox = "";
/* 1290 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1292 */         if (isIt360)
/*      */         {
/* 1294 */           actionimg = "";
/* 1295 */           actions = "";
/* 1296 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1297 */           checkbox = "";
/*      */         }
/*      */         
/* 1300 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1302 */           actions = "";
/*      */         }
/* 1304 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1306 */           checkbox = "";
/*      */         }
/*      */         
/* 1309 */         String resourcelink = "";
/*      */         
/* 1311 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1313 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1317 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1320 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1326 */         if (!isIt360)
/*      */         {
/* 1328 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1332 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1335 */         toreturn.append("</tr>");
/* 1336 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1338 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1339 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1343 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1344 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1347 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1351 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1353 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1355 */             toreturn.append(assocMessage);
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1357 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1365 */         String resourcelink = null;
/* 1366 */         boolean hideEditLink = false;
/* 1367 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1369 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1370 */           hideEditLink = true;
/* 1371 */           if (isIt360)
/*      */           {
/* 1373 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1377 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1379 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1381 */           hideEditLink = true;
/* 1382 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1383 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1388 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1391 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1392 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1393 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1394 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1395 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1396 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1397 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1398 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1399 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1400 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1401 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1402 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1403 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1405 */         if (hideEditLink)
/*      */         {
/* 1407 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1409 */         if (!forInventory)
/*      */         {
/* 1411 */           removefromgroup = "";
/*      */         }
/* 1413 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1414 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1415 */           actions = actions + configcustomfields;
/*      */         }
/* 1417 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1419 */           actions = editlink + actions;
/*      */         }
/* 1421 */         String managedLink = "";
/* 1422 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1424 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1425 */           actions = "";
/* 1426 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1427 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1430 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1432 */           checkbox = "";
/*      */         }
/*      */         
/* 1435 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1437 */           actions = "";
/*      */         }
/* 1439 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1440 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1441 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1442 */         if (isIt360)
/*      */         {
/* 1444 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1448 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1450 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1451 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1452 */         if (!isIt360)
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1458 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1460 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1463 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1470 */       StringBuilder toreturn = new StringBuilder();
/* 1471 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1472 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1473 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1474 */       String title = "";
/* 1475 */       message = EnterpriseUtil.decodeString(message);
/* 1476 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1477 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1478 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1480 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1482 */       else if ("5".equals(severity))
/*      */       {
/* 1484 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1488 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1490 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1491 */       toreturn.append(v);
/*      */       
/* 1493 */       toreturn.append(link);
/* 1494 */       if (severity == null)
/*      */       {
/* 1496 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1498 */       else if (severity.equals("5"))
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("4"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       else if (severity.equals("1"))
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1515 */       toreturn.append("</a>");
/* 1516 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1520 */       ex.printStackTrace();
/*      */     }
/* 1522 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1529 */       StringBuilder toreturn = new StringBuilder();
/* 1530 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1531 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1532 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1533 */       if (message == null)
/*      */       {
/* 1535 */         message = "";
/*      */       }
/*      */       
/* 1538 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1539 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1541 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1542 */       toreturn.append(v);
/*      */       
/* 1544 */       toreturn.append(link);
/*      */       
/* 1546 */       if (severity == null)
/*      */       {
/* 1548 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1550 */       else if (severity.equals("5"))
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1554 */       else if (severity.equals("1"))
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1563 */       toreturn.append("</a>");
/* 1564 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1570 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1573 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1574 */     if (invokeActions != null) {
/* 1575 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1576 */       while (iterator.hasNext()) {
/* 1577 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1578 */         if (actionmap.containsKey(actionid)) {
/* 1579 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1584 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1588 */     String actionLink = "";
/* 1589 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1590 */     String query = "";
/* 1591 */     ResultSet rs = null;
/* 1592 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1593 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1594 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1595 */       actionLink = "method=" + methodName;
/*      */     }
/* 1597 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1598 */       actionLink = methodName;
/*      */     }
/* 1600 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1601 */     Iterator itr = methodarglist.iterator();
/* 1602 */     boolean isfirstparam = true;
/* 1603 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1604 */     while (itr.hasNext()) {
/* 1605 */       HashMap argmap = (HashMap)itr.next();
/* 1606 */       String argtype = (String)argmap.get("TYPE");
/* 1607 */       String argname = (String)argmap.get("IDENTITY");
/* 1608 */       String paramname = (String)argmap.get("PARAMETER");
/* 1609 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1610 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1611 */         isfirstparam = false;
/* 1612 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1614 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1618 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1622 */         actionLink = actionLink + "&";
/*      */       }
/* 1624 */       String paramValue = null;
/* 1625 */       String tempargname = argname;
/* 1626 */       if (commonValues.getProperty(tempargname) != null) {
/* 1627 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1630 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1631 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1632 */           if (dbType.equals("mysql")) {
/* 1633 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1636 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1638 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1640 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1641 */             if (rs.next()) {
/* 1642 */               paramValue = rs.getString("VALUE");
/* 1643 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1647 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1651 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1654 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1659 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1660 */           paramValue = rowId;
/*      */         }
/* 1662 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1663 */           paramValue = managedObjectName;
/*      */         }
/* 1665 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1666 */           paramValue = resID;
/*      */         }
/* 1668 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1669 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1672 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1674 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1675 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1676 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1678 */     return actionLink;
/*      */   }
/*      */   
/* 1681 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1682 */     String dependentAttribute = null;
/* 1683 */     String align = "left";
/*      */     
/* 1685 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1686 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1687 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1688 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1689 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1690 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1691 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1692 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1693 */       align = "center";
/*      */     }
/*      */     
/* 1696 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1697 */     String actualdata = "";
/*      */     
/* 1699 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1700 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1701 */         actualdata = availValue;
/*      */       }
/* 1703 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1704 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1708 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1709 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1712 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1718 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1719 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1720 */       toreturn.append("<table>");
/* 1721 */       toreturn.append("<tr>");
/* 1722 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1723 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1724 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1725 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1726 */         String toolTip = "";
/* 1727 */         String hideClass = "";
/* 1728 */         String textStyle = "";
/* 1729 */         boolean isreferenced = true;
/* 1730 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1731 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1732 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1733 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1735 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1736 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1737 */           while (valueList.hasMoreTokens()) {
/* 1738 */             String dependentVal = valueList.nextToken();
/* 1739 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1740 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1741 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1743 */               toolTip = "";
/* 1744 */               hideClass = "";
/* 1745 */               isreferenced = false;
/* 1746 */               textStyle = "disabledtext";
/* 1747 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1751 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1752 */           toolTip = "";
/* 1753 */           hideClass = "";
/* 1754 */           isreferenced = false;
/* 1755 */           textStyle = "disabledtext";
/* 1756 */           if (dependentImageMap != null) {
/* 1757 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1758 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1761 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1765 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1766 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1767 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1768 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1769 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1770 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1772 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1773 */           if (isreferenced) {
/* 1774 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1778 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1779 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1780 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1781 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1782 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1783 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1785 */           toreturn.append("</span>");
/* 1786 */           toreturn.append("</a>");
/* 1787 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1790 */       toreturn.append("</tr>");
/* 1791 */       toreturn.append("</table>");
/* 1792 */       toreturn.append("</td>");
/*      */     } else {
/* 1794 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1797 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1801 */     String colTime = null;
/* 1802 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1803 */     if ((rows != null) && (rows.size() > 0)) {
/* 1804 */       Iterator<String> itr = rows.iterator();
/* 1805 */       String maxColQuery = "";
/* 1806 */       for (;;) { if (itr.hasNext()) {
/* 1807 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1808 */           ResultSet maxCol = null;
/*      */           try {
/* 1810 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1811 */             while (maxCol.next()) {
/* 1812 */               if (colTime == null) {
/* 1813 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1816 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1825 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1827 */               if (maxCol != null)
/* 1828 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1830 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1825 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1827 */               if (maxCol != null)
/* 1828 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1830 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1835 */     return colTime;
/*      */   }
/*      */   
/* 1838 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1839 */     tablename = null;
/* 1840 */     ResultSet rsTable = null;
/* 1841 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1843 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1844 */       while (rsTable.next()) {
/* 1845 */         tablename = rsTable.getString("DATATABLE");
/* 1846 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1847 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1860 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1851 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1854 */         if (rsTable != null)
/* 1855 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1857 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1863 */     String argsList = "";
/* 1864 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1866 */       if (showArgsMap.get(row) != null) {
/* 1867 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1868 */         if (showArgslist != null) {
/* 1869 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1870 */             if (argsList.trim().equals("")) {
/* 1871 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1874 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1881 */       e.printStackTrace();
/* 1882 */       return "";
/*      */     }
/* 1884 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1889 */     String argsList = "";
/* 1890 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1893 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1895 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1896 */         if (hideArgsList != null)
/*      */         {
/* 1898 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1900 */             if (argsList.trim().equals(""))
/*      */             {
/* 1902 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1906 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1914 */       ex.printStackTrace();
/*      */     }
/* 1916 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1920 */     StringBuilder toreturn = new StringBuilder();
/* 1921 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1928 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1929 */       Iterator itr = tActionList.iterator();
/* 1930 */       while (itr.hasNext()) {
/* 1931 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1932 */         String confirmmsg = "";
/* 1933 */         String link = "";
/* 1934 */         String isJSP = "NO";
/* 1935 */         HashMap tactionMap = (HashMap)itr.next();
/* 1936 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1937 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1938 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1939 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1940 */           (actionmap.containsKey(actionId))) {
/* 1941 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1942 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1943 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1944 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1945 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1947 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1953 */           if (isTableAction) {
/* 1954 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1957 */             tableName = "Link";
/* 1958 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1959 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1960 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1961 */             toreturn.append("</a></td>");
/*      */           }
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1972 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1978 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1980 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1981 */       Properties prop = (Properties)node.getUserObject();
/* 1982 */       String mgID = prop.getProperty("label");
/* 1983 */       String mgName = prop.getProperty("value");
/* 1984 */       String isParent = prop.getProperty("isParent");
/* 1985 */       int mgIDint = Integer.parseInt(mgID);
/* 1986 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1988 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1990 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1991 */       if (node.getChildCount() > 0)
/*      */       {
/* 1993 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1995 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1997 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1999 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2003 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2008 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2010 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2012 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2014 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2018 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2021 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2022 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2024 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2028 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2030 */       if (node.getChildCount() > 0)
/*      */       {
/* 2032 */         builder.append("<UL>");
/* 2033 */         printMGTree(node, builder);
/* 2034 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2039 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2040 */     StringBuffer toReturn = new StringBuffer();
/* 2041 */     String table = "-";
/*      */     try {
/* 2043 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2044 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2045 */       float total = 0.0F;
/* 2046 */       while (it.hasNext()) {
/* 2047 */         String attName = (String)it.next();
/* 2048 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2049 */         boolean roundOffData = false;
/* 2050 */         if ((data != null) && (!data.equals(""))) {
/* 2051 */           if (data.indexOf(",") != -1) {
/* 2052 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2055 */             float value = Float.parseFloat(data);
/* 2056 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2059 */             total += value;
/* 2060 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2063 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2068 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2069 */       while (attVsWidthList.hasNext()) {
/* 2070 */         String attName = (String)attVsWidthList.next();
/* 2071 */         String data = (String)attVsWidthProps.get(attName);
/* 2072 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2073 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2074 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2075 */         String className = (String)graphDetails.get("ClassName");
/* 2076 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2077 */         if (percentage < 1.0F)
/*      */         {
/* 2079 */           data = percentage + "";
/*      */         }
/* 2081 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2083 */       if (toReturn.length() > 0) {
/* 2084 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2088 */       e.printStackTrace();
/*      */     }
/* 2090 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2096 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2097 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2098 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2099 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2100 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2101 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2102 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2103 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2104 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2107 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2108 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2109 */       splitvalues[0] = multiplecondition.toString();
/* 2110 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2113 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2118 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2119 */     if (thresholdType != 3) {
/* 2120 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2121 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2122 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2123 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2124 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2125 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2127 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2128 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2129 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2130 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2131 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2132 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2134 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2135 */     if (updateSelected != null) {
/* 2136 */       updateSelected[0] = "selected";
/*      */     }
/* 2138 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2143 */       StringBuffer toreturn = new StringBuffer("");
/* 2144 */       if (commaSeparatedMsgId != null) {
/* 2145 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2146 */         int count = 0;
/* 2147 */         while (msgids.hasMoreTokens()) {
/* 2148 */           String id = msgids.nextToken();
/* 2149 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2150 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2151 */           count++;
/* 2152 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2153 */             if (toreturn.length() == 0) {
/* 2154 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2156 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2157 */             if (!image.trim().equals("")) {
/* 2158 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2160 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2161 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2164 */         if (toreturn.length() > 0) {
/* 2165 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2169 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2172 */       e.printStackTrace(); }
/* 2173 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2179 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2185 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2186 */   static { _jspx_dependants.put("/jsp/includes/associatedMonitorGroups.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/includes/CiLinks.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2221 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2233 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2241 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2244 */     JspWriter out = null;
/* 2245 */     Object page = this;
/* 2246 */     JspWriter _jspx_out = null;
/* 2247 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2251 */       response.setContentType("text/html;charset=UTF-8");
/* 2252 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2254 */       _jspx_page_context = pageContext;
/* 2255 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2256 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2257 */       session = pageContext.getSession();
/* 2258 */       out = pageContext.getOut();
/* 2259 */       _jspx_out = out;
/*      */       
/* 2261 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n");
/* 2262 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2264 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2265 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2266 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2268 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2270 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2275 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2276 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2277 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2280 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2281 */         String available = null;
/* 2282 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2283 */         out.write(10);
/*      */         
/* 2285 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2286 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2287 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2289 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2291 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2296 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2297 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2298 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2301 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2302 */           String unavailable = null;
/* 2303 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2304 */           out.write(10);
/*      */           
/* 2306 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2307 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2308 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2310 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2312 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2317 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2318 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2319 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2322 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2323 */             String unmanaged = null;
/* 2324 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2325 */             out.write(10);
/*      */             
/* 2327 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2328 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2329 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2331 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2333 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2338 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2339 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2340 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2343 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2344 */               String scheduled = null;
/* 2345 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2346 */               out.write(10);
/*      */               
/* 2348 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2349 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2350 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2352 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2354 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2359 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2360 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2361 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2364 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2365 */                 String critical = null;
/* 2366 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2367 */                 out.write(10);
/*      */                 
/* 2369 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2370 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2371 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2373 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2375 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2380 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2381 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2382 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2385 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2386 */                   String clear = null;
/* 2387 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2388 */                   out.write(10);
/*      */                   
/* 2390 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2391 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2392 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2394 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2396 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2401 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2402 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2403 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2406 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2407 */                     String warning = null;
/* 2408 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2409 */                     out.write(10);
/* 2410 */                     out.write(10);
/*      */                     
/* 2412 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2413 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2415 */                     out.write(10);
/* 2416 */                     out.write(10);
/* 2417 */                     out.write(10);
/* 2418 */                     out.write("\n\n<script>\n");
/*      */                     
/* 2420 */                     if (request.getParameter("editPage") != null)
/*      */                     {
/*      */ 
/* 2423 */                       out.write("\n    toggleDiv('Reconfigure');\n ");
/*      */                     }
/*      */                     
/* 2426 */                     out.write("\n</script>\n\n");
/*      */                     
/* 2428 */                     String type = FormatUtil.getString("am.webclient.webappdetails.type1.text");
/* 2429 */                     String resourceid = request.getParameter("resourceid");
/*      */                     
/* 2431 */                     ArrayList attribIDs = new ArrayList();
/* 2432 */                     ArrayList resIDs = new ArrayList();
/* 2433 */                     resIDs.add(resourceid);
/* 2434 */                     attribIDs.add("3501");
/* 2435 */                     attribIDs.add("3502");
/* 2436 */                     Properties alert = getStatus(resIDs, attribIDs);
/* 2437 */                     String methodname = request.getParameter("method");
/* 2438 */                     String configure_link = (String)request.getAttribute("configurelink");
/*      */                     
/* 2440 */                     out.write(10);
/* 2441 */                     if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                       return;
/* 2443 */                     out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" class=\"leftlinksheading\">");
/* 2444 */                     out.print(FormatUtil.getString("Web Service"));
/* 2445 */                     out.write("</td>\n  </tr>\n  <tr>\n    <td height=\"21\" class=\"leftlinkstd\">\n");
/*      */                     
/* 2447 */                     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2448 */                     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2449 */                     _jspx_th_c_005fchoose_005f0.setParent(null);
/* 2450 */                     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2451 */                     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                       for (;;) {
/* 2453 */                         out.write(10);
/* 2454 */                         out.write(32);
/* 2455 */                         out.write(32);
/*      */                         
/* 2457 */                         WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2458 */                         _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2459 */                         _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                         
/* 2461 */                         _jspx_th_c_005fwhen_005f0.setTest("${param.method=='showdetails' && param.all!='true'}");
/* 2462 */                         int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2463 */                         if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                           for (;;) {
/* 2465 */                             out.write("\n        ");
/* 2466 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2467 */                             out.write(10);
/* 2468 */                             out.write(32);
/* 2469 */                             out.write(32);
/* 2470 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2471 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2475 */                         if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2476 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                         }
/*      */                         
/* 2479 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2480 */                         out.write(10);
/* 2481 */                         out.write(32);
/* 2482 */                         out.write(32);
/*      */                         
/* 2484 */                         OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2485 */                         _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2486 */                         _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2487 */                         int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2488 */                         if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                           for (;;) {
/* 2490 */                             out.write("\n\n <a href=\"../showresource.do?method=showdetails&resourceid=");
/* 2491 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                               return;
/* 2493 */                             out.write("&haid=");
/* 2494 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                               return;
/* 2496 */                             out.write("&type=Web Service\"\n       class=\"staticlinks\">");
/* 2497 */                             out.print(FormatUtil.getString("am.webclient.common.snapshotview.text"));
/* 2498 */                             out.write("</a>\n");
/* 2499 */                             int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2500 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2504 */                         if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2505 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                         }
/*      */                         
/* 2508 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2509 */                         out.write(10);
/* 2510 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2511 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2515 */                     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2516 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/*      */                     }
/*      */                     else {
/* 2519 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2520 */                       out.write("\n </td>\n  </tr>\n");
/*      */                       
/* 2522 */                       IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2523 */                       _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2524 */                       _jspx_th_c_005fif_005f1.setParent(null);
/*      */                       
/* 2526 */                       _jspx_th_c_005fif_005f1.setTest("${!empty ADMIN || !empty DEMO}");
/* 2527 */                       int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2528 */                       if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                         for (;;) {
/* 2530 */                           out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n");
/*      */                           
/* 2532 */                           ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2533 */                           _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2534 */                           _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_c_005fif_005f1);
/* 2535 */                           int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2536 */                           if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                             for (;;) {
/* 2538 */                               out.write(10);
/* 2539 */                               out.write(10);
/* 2540 */                               out.write(32);
/* 2541 */                               out.write(32);
/*      */                               
/* 2543 */                               WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2544 */                               _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2545 */                               _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                               
/* 2547 */                               _jspx_th_c_005fwhen_005f1.setTest("${(param.all=='true')}");
/* 2548 */                               int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2549 */                               if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                 for (;;) {
/* 2551 */                                   out.write(10);
/* 2552 */                                   out.write(9);
/* 2553 */                                   out.print(ALERTCONFIG_TEXT);
/* 2554 */                                   out.write(10);
/* 2555 */                                   out.write(32);
/* 2556 */                                   out.write(32);
/* 2557 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2558 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2562 */                               if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2563 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                               }
/*      */                               
/* 2566 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2567 */                               out.write(10);
/* 2568 */                               out.write(32);
/* 2569 */                               out.write(32);
/*      */                               
/* 2571 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2572 */                               _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2573 */                               _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2574 */                               int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2575 */                               if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                 for (;;) {
/* 2577 */                                   out.write("\n       <a href=\"/showActionProfiles.do?method=getResourceProfiles&admin=true&monitor=true&all=true&resourceid=");
/* 2578 */                                   if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f1, _jspx_page_context))
/*      */                                     return;
/* 2580 */                                   out.write("\" class=\"new-left-links\">");
/* 2581 */                                   out.print(ALERTCONFIG_TEXT);
/* 2582 */                                   out.write("</a>\n");
/* 2583 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2584 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2588 */                               if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2589 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                               }
/*      */                               
/* 2592 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2593 */                               out.write(10);
/* 2594 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2595 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2599 */                           if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2600 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                           }
/*      */                           
/* 2603 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2604 */                           out.write("\n   </td>\n  </tr>\n");
/* 2605 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2606 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2610 */                       if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2611 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                       }
/*      */                       else {
/* 2614 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2615 */                         out.write(10);
/* 2616 */                         out.write(32);
/*      */                         
/* 2618 */                         PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2619 */                         _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2620 */                         _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                         
/* 2622 */                         _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2623 */                         int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2624 */                         if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                           for (;;) {
/* 2626 */                             out.write("\n  <tr>\n   <td class=\"leftlinkstd\" > <a target=\"mas_window\" href=\"/showresource.do?resourceid=");
/* 2627 */                             out.print(request.getParameter("resourceid"));
/* 2628 */                             out.write("&type=");
/* 2629 */                             out.print(request.getParameter("type"));
/* 2630 */                             out.write("&moname=");
/* 2631 */                             out.print(request.getParameter("moname"));
/* 2632 */                             out.write("&method=showdetails&resourcename=");
/* 2633 */                             out.print(request.getParameter("resourcename"));
/* 2634 */                             out.write("&aam_jump=true&editPage=true\" class=\"new-left-links\">\n ");
/* 2635 */                             out.print(FormatUtil.getString("am.webclient.dotnet.edit"));
/* 2636 */                             out.write("</a> </td>\n  </tr>\n ");
/* 2637 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2638 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2642 */                         if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2643 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                         }
/*      */                         else {
/* 2646 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2647 */                           out.write(10);
/* 2648 */                           out.write(10);
/*      */                           
/* 2650 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2651 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2652 */                           _jspx_th_c_005fif_005f2.setParent(null);
/*      */                           
/* 2654 */                           _jspx_th_c_005fif_005f2.setTest("${(!empty ADMIN || !empty DEMO) && param.method!='showoperations'}");
/* 2655 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2656 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 2658 */                               out.write("\n  <tr>\n    <td class=\"leftlinkstd\">\n");
/*      */                               
/* 2660 */                               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2661 */                               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2662 */                               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fif_005f2);
/* 2663 */                               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2664 */                               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                 for (;;) {
/* 2666 */                                   out.write(10);
/* 2667 */                                   out.write(32);
/* 2668 */                                   out.write(32);
/*      */                                   
/* 2670 */                                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2671 */                                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2672 */                                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                   
/* 2674 */                                   _jspx_th_c_005fwhen_005f2.setTest("${false}");
/* 2675 */                                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2676 */                                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                     for (;;) {
/* 2678 */                                       out.write("\n         ");
/* 2679 */                                       out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2680 */                                       out.write(10);
/* 2681 */                                       out.write(32);
/* 2682 */                                       out.write(32);
/* 2683 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2684 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2688 */                                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2689 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                   }
/*      */                                   
/* 2692 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2693 */                                   out.write(10);
/* 2694 */                                   out.write(32);
/* 2695 */                                   out.write(32);
/*      */                                   
/* 2697 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2698 */                                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2699 */                                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2700 */                                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2701 */                                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                     for (;;) {
/* 2703 */                                       out.write(10);
/* 2704 */                                       out.write(9);
/*      */                                       
/* 2706 */                                       ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2707 */                                       _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2708 */                                       _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fotherwise_005f2);
/* 2709 */                                       int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2710 */                                       if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                         for (;;) {
/* 2712 */                                           out.write(10);
/* 2713 */                                           out.write(9);
/*      */                                           
/* 2715 */                                           WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2716 */                                           _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2717 */                                           _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                           
/* 2719 */                                           _jspx_th_c_005fwhen_005f3.setTest("${param.method=='manageoperations'}");
/* 2720 */                                           int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2721 */                                           if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                             for (;;) {
/* 2723 */                                               out.write("\n\t<a href=\"../showresource.do?method=showdetails&resourceid=");
/* 2724 */                                               if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f3, _jspx_page_context))
/*      */                                                 return;
/* 2726 */                                               out.write("&editPage=true&type=Web Service\"\n      class=\"new-left-links\">");
/* 2727 */                                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2728 */                                               out.write("</a>\n\t");
/* 2729 */                                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2730 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2734 */                                           if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2735 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                           }
/*      */                                           
/* 2738 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2739 */                                           out.write(10);
/* 2740 */                                           out.write(9);
/*      */                                           
/* 2742 */                                           OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2743 */                                           _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2744 */                                           _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2745 */                                           int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2746 */                                           if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                                             for (;;) {
/* 2748 */                                               out.write("\n      <a href=\"javascript:toggleDiv('Reconfigure')\"\n      class=\"new-left-links\">");
/* 2749 */                                               out.print(FormatUtil.getString("am.webclient.common.editmonitor.text"));
/* 2750 */                                               out.write("</a>\n\t");
/* 2751 */                                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2752 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 2756 */                                           if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2757 */                                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                                           }
/*      */                                           
/* 2760 */                                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2761 */                                           out.write(10);
/* 2762 */                                           out.write(9);
/* 2763 */                                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2764 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2768 */                                       if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2769 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                       }
/*      */                                       
/* 2772 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2773 */                                       out.write(10);
/* 2774 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2775 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2779 */                                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2780 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                   }
/*      */                                   
/* 2783 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2784 */                                   out.write(10);
/* 2785 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2786 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2790 */                               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2791 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                               }
/*      */                               
/* 2794 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2795 */                               out.write("\n   </td>\n  </tr>\n");
/* 2796 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2797 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2801 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2802 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                           }
/*      */                           else {
/* 2805 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2806 */                             out.write("\n\n\n <script language=\"JavaScript\">\n\tfunction confirmDelete()\n \t {\n\t\t ");
/* 2807 */                             if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                               return;
/* 2809 */                             out.write("\n  var s = confirm(\"");
/* 2810 */                             out.print(FormatUtil.getString("am.webclient.urlmonitor.jsalertformonitor.text"));
/* 2811 */                             out.write("\");\n  if (s)\n\n \tdocument.location.href=\"/deleteMO.do?method=deleteMO&type=Web Service&select=");
/* 2812 */                             if (_jspx_meth_c_005fout_005f5(_jspx_page_context))
/*      */                               return;
/* 2814 */                             out.write("\";\n\t }\n\n         function confirmManage()\n \t {\n\t\t ");
/* 2815 */                             if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                               return;
/* 2817 */                             out.write("\n  var s = confirm(\"");
/* 2818 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.onemanage.text"));
/* 2819 */                             out.write("\");\n  if (s)\n \tdocument.location.href=\"/deleteMO.do?method=manageMonitors&resourceid=");
/* 2820 */                             if (_jspx_meth_c_005fout_005f6(_jspx_page_context))
/*      */                               return;
/* 2822 */                             out.write("\";\n\t }\n\n         function confirmUnManage()\n \t {\n\t\t ");
/* 2823 */                             if (_jspx_meth_logic_005fpresent_005f3(_jspx_page_context))
/*      */                               return;
/* 2825 */                             out.write("\n\t\t  var show_msg=\"false\";\n\t      var url=\"/deleteMO.do?method=showUnmanageMessage&resid=\"+");
/* 2826 */                             out.print(request.getParameter("resourceid"));
/* 2827 */                             out.write("; //No i18n\n\t      $.ajax({\n\t        type:'POST', //No i18n\n\t        url:url,\n\t        async:false,\n\t        success: function(data)\n\t        {\n\t          show_msg=data\n\t        }\n\t      });\n\t      if(show_msg.indexOf(\"true\")>-1)\n\t      {\n\t          alert(\"");
/* 2828 */                             out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.after.ds.text"));
/* 2829 */                             out.write("\");\n\t\t \tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2830 */                             if (_jspx_meth_c_005fout_005f7(_jspx_page_context))
/*      */                               return;
/* 2832 */                             out.write("\";\n\t     }\n       else { \n    \t\tvar s = confirm(\"");
/* 2833 */                             out.print(FormatUtil.getString("am.webclient.common.confirm.oneunmanage.text"));
/* 2834 */                             out.write("\");\n    \t\tif (s){\n    \t\t\t\tdocument.location.href=\"/deleteMO.do?method=unManageMonitors&resourceid=");
/* 2835 */                             if (_jspx_meth_c_005fout_005f8(_jspx_page_context))
/*      */                               return;
/* 2837 */                             out.write("\"; //No I18N\n\t\t\t\t}\n\t\t   } \n\t }\n  </script>\n  ");
/*      */                             
/* 2839 */                             PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2840 */                             _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2841 */                             _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                             
/* 2843 */                             _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,DEMO");
/* 2844 */                             int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2845 */                             if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                               for (;;) {
/* 2847 */                                 out.write("\n  <tr>\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmDelete();\" class=\"new-left-links\">");
/* 2848 */                                 out.print(FormatUtil.getString("am.webclient.common.deletemonitor.text"));
/* 2849 */                                 out.write("</A></td>\n  </tr>\n  ");
/* 2850 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2851 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2855 */                             if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2856 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                             }
/*      */                             else {
/* 2859 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2860 */                               out.write(10);
/* 2861 */                               out.write(32);
/* 2862 */                               out.write(32);
/*      */                               
/* 2864 */                               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2865 */                               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2866 */                               _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */                               
/* 2868 */                               _jspx_th_logic_005fpresent_005f5.setRole("ADMIN,DEMO");
/* 2869 */                               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2870 */                               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                 for (;;) {
/* 2872 */                                   out.write("\n  <tr>\n  ");
/*      */                                   
/* 2874 */                                   if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(request.getParameter("resourceid")))
/*      */                                   {
/*      */ 
/* 2877 */                                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmManage();\" class=\"new-left-links\">");
/* 2878 */                                     out.print(FormatUtil.getString("Manage"));
/* 2879 */                                     out.write("</A></td>\n    ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 2885 */                                     out.write("\n    <td class=\"leftlinkstd\" ><A href=\"javascript:confirmUnManage();\" class=\"new-left-links\">");
/* 2886 */                                     out.print(FormatUtil.getString("UnManage"));
/* 2887 */                                     out.write("</A></td>\n    ");
/*      */                                   }
/*      */                                   
/*      */ 
/* 2891 */                                   out.write("\n  </tr>\n  ");
/* 2892 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2893 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2897 */                               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2898 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/*      */                               }
/*      */                               else {
/* 2901 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2902 */                                 out.write(10);
/* 2903 */                                 out.write(32);
/* 2904 */                                 out.write(32);
/*      */                                 
/* 2906 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2907 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2908 */                                 _jspx_th_logic_005fpresent_005f6.setParent(null);
/*      */                                 
/* 2910 */                                 _jspx_th_logic_005fpresent_005f6.setRole("ADMIN,DEMO");
/* 2911 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2912 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 2914 */                                     out.write("\n  \t<tr>\n      \t <td colspan=\"2\" class=\"leftlinkstd\">\n      \t ");
/* 2915 */                                     out.print(FaultUtil.getAlertTemplateURL(resourceid, (String)request.getAttribute("displayname"), "Web Service", "Web Service"));
/* 2916 */                                     out.write("\n      \t </td>\n     \t</tr>\n  ");
/* 2917 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2918 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2922 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2923 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/*      */                                 }
/*      */                                 else {
/* 2926 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 2927 */                                   out.write(10);
/* 2928 */                                   out.write(32);
/* 2929 */                                   out.write(32);
/*      */                                   
/* 2931 */                                   PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2932 */                                   _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 2933 */                                   _jspx_th_logic_005fpresent_005f7.setParent(null);
/*      */                                   
/* 2935 */                                   _jspx_th_logic_005fpresent_005f7.setRole("ADMIN,DEMO");
/* 2936 */                                   int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 2937 */                                   if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                                     for (;;) {
/* 2939 */                                       out.write("\n  \t<tr>\n      \t <td colspan=\"2\" class=\"leftlinkstd\">\n\t ");
/*      */                                       
/* 2941 */                                       IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2942 */                                       _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2943 */                                       _jspx_th_c_005fif_005f3.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                                       
/* 2945 */                                       _jspx_th_c_005fif_005f3.setTest("${param.method!='showoperations'}");
/* 2946 */                                       int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2947 */                                       if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                         for (;;) {
/* 2949 */                                           out.write("\n\t <a class=\"new-left-links\" href=\"/wsm.do?method=showoperations&resourceid=");
/* 2950 */                                           if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                             return;
/* 2952 */                                           out.write(34);
/* 2953 */                                           out.write(62);
/* 2954 */                                           out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 2955 */                                           out.write("</a>\n\t");
/* 2956 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2957 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2961 */                                       if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2962 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                       }
/*      */                                       
/* 2965 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2966 */                                       out.write(10);
/* 2967 */                                       out.write(9);
/* 2968 */                                       out.write(32);
/*      */                                       
/* 2970 */                                       IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2971 */                                       _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2972 */                                       _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                                       
/* 2974 */                                       _jspx_th_c_005fif_005f4.setTest("${param.method=='showoperations'}");
/* 2975 */                                       int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2976 */                                       if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                         for (;;) {
/* 2978 */                                           out.write(10);
/* 2979 */                                           out.write(9);
/* 2980 */                                           out.write(32);
/* 2981 */                                           out.print(FormatUtil.getString("am.webclient.wsm.showoperationsbc.text"));
/* 2982 */                                           out.write(10);
/* 2983 */                                           out.write(9);
/* 2984 */                                           out.write(32);
/* 2985 */                                           int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2986 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 2990 */                                       if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2991 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                       }
/*      */                                       
/* 2994 */                                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2995 */                                       out.write("\n      \t </td>\n     \t</tr>\n  ");
/* 2996 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 2997 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3001 */                                   if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3002 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/*      */                                   }
/*      */                                   else {
/* 3005 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3006 */                                     out.write(10);
/* 3007 */                                     out.write(32);
/* 3008 */                                     out.write(32);
/*      */                                     
/* 3010 */                                     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3011 */                                     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3012 */                                     _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */                                     
/* 3014 */                                     _jspx_th_logic_005fpresent_005f8.setRole("ADMIN,DEMO");
/* 3015 */                                     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3016 */                                     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                                       for (;;) {
/* 3018 */                                         out.write("\n  \t<tr>\n      \t <td colspan=\"2\" class=\"leftlinkstd\">\n\t ");
/*      */                                         
/* 3020 */                                         IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3021 */                                         _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3022 */                                         _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fpresent_005f8);
/*      */                                         
/* 3024 */                                         _jspx_th_c_005fif_005f5.setTest("${param.method!='manageoperations'}");
/* 3025 */                                         int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3026 */                                         if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                                           for (;;) {
/* 3028 */                                             out.write("\n\t <a class=\"new-left-links\" href=\"/wsm.do?method=manageoperations&resourceid=");
/* 3029 */                                             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                               return;
/* 3031 */                                             out.write(34);
/* 3032 */                                             out.write(62);
/* 3033 */                                             out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 3034 */                                             out.write("</a>\n\t");
/* 3035 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3036 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3040 */                                         if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3041 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                                         }
/*      */                                         
/* 3044 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3045 */                                         out.write(10);
/* 3046 */                                         out.write(9);
/* 3047 */                                         out.write(32);
/*      */                                         
/* 3049 */                                         IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3050 */                                         _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3051 */                                         _jspx_th_c_005fif_005f6.setParent(_jspx_th_logic_005fpresent_005f8);
/*      */                                         
/* 3053 */                                         _jspx_th_c_005fif_005f6.setTest("${param.method=='manageoperations'}");
/* 3054 */                                         int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3055 */                                         if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                           for (;;) {
/* 3057 */                                             out.write(10);
/* 3058 */                                             out.write(9);
/* 3059 */                                             out.write(32);
/* 3060 */                                             out.print(FormatUtil.getString("am.webclient.wsm.manageoperationsbc.text"));
/* 3061 */                                             out.write(10);
/* 3062 */                                             out.write(9);
/* 3063 */                                             out.write(32);
/* 3064 */                                             int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3065 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3069 */                                         if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3070 */                                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                         }
/*      */                                         
/* 3073 */                                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3074 */                                         out.write("\n      \t </td>\n     \t</tr>\n  ");
/* 3075 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3076 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3080 */                                     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3081 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/*      */                                     }
/*      */                                     else {
/* 3084 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3085 */                                       out.write(10);
/* 3086 */                                       out.write(10);
/*      */                                       
/* 3088 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3089 */                                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3090 */                                       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                                       
/* 3092 */                                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 3093 */                                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3094 */                                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                         for (;;) {
/* 3096 */                                           out.write(10);
/* 3097 */                                           out.write(32);
/* 3098 */                                           out.write(32);
/*      */                                           
/* 3100 */                                           String resourceid_poll = request.getParameter("resourceid");
/* 3101 */                                           String resourcetype_poll = request.getParameter("type");
/*      */                                           
/* 3103 */                                           out.write("\n    <tr>\n    <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n    <a href=\"/GlobalActions.do?method=pollNow&resourceid=");
/* 3104 */                                           out.print(resourceid_poll);
/* 3105 */                                           out.write("&resourcetype=Web Service\" class=\"new-left-links\"> ");
/* 3106 */                                           out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3107 */                                           out.write("</a></td>\n  </tr>\n  ");
/* 3108 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 3109 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3113 */                                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 3114 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                       }
/*      */                                       else {
/* 3117 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3118 */                                         out.write(10);
/* 3119 */                                         out.write(32);
/* 3120 */                                         out.write(32);
/*      */                                         
/* 3122 */                                         PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3123 */                                         _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3124 */                                         _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                                         
/* 3126 */                                         _jspx_th_logic_005fpresent_005f9.setRole("DEMO");
/* 3127 */                                         int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3128 */                                         if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                                           for (;;) {
/* 3130 */                                             out.write("\n        <tr>\n        <td width=\"49%\" height=\"21\" class=\"leftlinkstd\" >\n        <a href=\"javascript:alertUser()\" class=\"new-left-links\">");
/* 3131 */                                             out.print(FormatUtil.getString("am.webclient.commonleftpage.pollnow"));
/* 3132 */                                             out.write("</a></td>\n        </td>\n        </tr>\n    ");
/* 3133 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3134 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3138 */                                         if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3139 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/*      */                                         }
/*      */                                         else {
/* 3142 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3143 */                                           out.write("\n    ");
/* 3144 */                                           out.write("<!-- $Id$-->\n\n\n  \n");
/*      */                                           
/* 3146 */                                           if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                                           {
/* 3148 */                                             Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(request);
/* 3149 */                                             String ciLinksDisplay = request.getAttribute("CI_LINKS_DISPLAY") != null ? (String)request.getAttribute("CI_LINKS_DISPLAY") : "DEFAULT";
/*      */                                             
/* 3151 */                                             String ciInfoUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL) : null;
/* 3152 */                                             String ciRLUrl = (ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)) ? (String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL) : null;
/* 3153 */                                             if ((ciInfoUrl != null) && (ciRLUrl != null))
/*      */                                             {
/* 3155 */                                               if ((ciLinksDisplay == null) || ("DEFAULT".equalsIgnoreCase(ciLinksDisplay)))
/*      */                                               {
/*      */ 
/* 3158 */                                                 out.write("\n\t\t\t\t\t<tr>\n  \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3159 */                                                 out.print(ciInfoUrl);
/* 3160 */                                                 out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3161 */                                                 out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3162 */                                                 out.write("</a></td>");
/* 3163 */                                                 out.write("\n  \t\t\t\t\t</tr>\n  \t\t\t\t\t<tr>\n   \t\t\t\t\t\t<td class=\"leftlinkstd\"><a onclick=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3164 */                                                 out.print(ciRLUrl);
/* 3165 */                                                 out.write("','950','500','100','100')\" class=\"new-left-links\" href=\"javascript:void(0)\">");
/* 3166 */                                                 out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3167 */                                                 out.write("</a></td>");
/* 3168 */                                                 out.write("\n\t    \t\t\t</tr>\n  \t\t\t\t\t\n\t\t\t\t");
/*      */ 
/*      */ 
/*      */                                               }
/* 3172 */                                               else if ("MG_ACTION_LINKS".equalsIgnoreCase(ciLinksDisplay))
/*      */                                               {
/*      */ 
/* 3175 */                                                 out.write("\n\t\t\t\t\t<tr><td height=\"10\"></td></tr>\n\n\t\t\t\t<tr><td class=\"tabLink\"  style=\"line-height:24px;\"><b style=\"cursor:text;\">&nbsp;");
/* 3176 */                                                 out.print(FormatUtil.getString("am.webclient.footer.cilink.text"));
/* 3177 */                                                 out.write("</b></td></tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3178 */                                                 out.print(ciInfoUrl);
/* 3179 */                                                 out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/CI_Details.gif\" border=\"0\"/>  ");
/* 3180 */                                                 out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 3181 */                                                 out.write("</td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t<td><a href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 3182 */                                                 out.print(ciRLUrl);
/* 3183 */                                                 out.write("','950','500','100','100')\"  class=\"staticlinks1\"><img src=\"/images/cmdb-rship-icon.gif\" border=\"0\"/>  ");
/* 3184 */                                                 out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 3185 */                                                 out.write("</td>\n\t\t\t\t</tr> \n\t\t\t\t\t\n\t\t\t");
/*      */                                               }
/*      */                                             }
/*      */                                           }
/*      */                                           
/* 3190 */                                           out.write("\n \n \n\n");
/* 3191 */                                           out.write("\n  </table>\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr>\n    <td height=\"21\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3192 */                                           out.print(FormatUtil.getString("am.webclient.common.rca.text"));
/* 3193 */                                           out.write("</td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3194 */                                           if (_jspx_meth_c_005fout_005f11(_jspx_page_context))
/*      */                                             return;
/* 3196 */                                           out.write("&attributeid=3502')\" class=\"new-left-links\">");
/* 3197 */                                           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 3198 */                                           out.write("</a></td>\n    <td ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3199 */                                           if (_jspx_meth_c_005fout_005f12(_jspx_page_context))
/*      */                                             return;
/* 3201 */                                           out.write("&attributeid=3502')\" >");
/* 3202 */                                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3502")));
/* 3203 */                                           out.write("</a></td>\n  </tr>\n  <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n    <td width=\"49%\"><a class=\"new-left-links\" href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3204 */                                           out.print(resourceid);
/* 3205 */                                           out.write("&attributeid=3501')\">");
/* 3206 */                                           out.print(FormatUtil.getString("Availability"));
/* 3207 */                                           out.write("</a></td>\n    <td width=\"51%\" > <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3208 */                                           out.print(resourceid);
/* 3209 */                                           out.write("&attributeid=3501')\">");
/* 3210 */                                           out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "3501")));
/* 3211 */                                           out.write("</a></td>\n  </tr>\n</table>\n<br>\n");
/* 3212 */                                           out.write("<!--$Id$-->\n\n\n\n\n\n\n");
/*      */                                           
/*      */ 
/*      */ 
/* 3216 */                                           boolean showAssociatedBSG = !request.isUserInRole("OPERATOR");
/* 3217 */                                           if (EnterpriseUtil.isIt360MSPEdition)
/*      */                                           {
/* 3219 */                                             showAssociatedBSG = false;
/*      */                                             
/*      */ 
/*      */ 
/* 3223 */                                             CustomerManagementAPI.getInstance();Properties assBsgSiteProp = CustomerManagementAPI.getSiteProp(request);
/* 3224 */                                             CustomerManagementAPI.getInstance();String customerId = CustomerManagementAPI.getCustomerId(request);
/* 3225 */                                             String loginName = request.getUserPrincipal().getName();
/* 3226 */                                             CustomerManagementAPI.getInstance();boolean showAllBSGs = CustomerManagementAPI.isUserPriviligedToViewAllSitesOfTheCustomer(loginName, customerId);
/*      */                                             
/* 3228 */                                             if (((assBsgSiteProp == null) || (assBsgSiteProp.isEmpty())) && (showAllBSGs))
/*      */                                             {
/* 3230 */                                               showAssociatedBSG = true;
/*      */                                             }
/*      */                                           }
/* 3233 */                                           String monitorType = request.getParameter("type");
/* 3234 */                                           ConfMonitorConfiguration conf1 = ConfMonitorConfiguration.getInstance();
/* 3235 */                                           boolean mon = conf1.isConfMonitor(monitorType);
/* 3236 */                                           if (showAssociatedBSG)
/*      */                                           {
/* 3238 */                                             Hashtable associatedmgs = new Hashtable();
/* 3239 */                                             String resId = request.getParameter("resourceid");
/* 3240 */                                             request.setAttribute("associatedmgs", FaultUtil.getAdminAssociatedMG(resId, request));
/* 3241 */                                             if ((monitorType != null) && (monitorType.equals("QueryMonitor")))
/*      */                                             {
/* 3243 */                                               mon = false;
/*      */                                             }
/*      */                                             
/* 3246 */                                             if (!mon)
/*      */                                             {
/* 3248 */                                               out.write(10);
/* 3249 */                                               out.write(10);
/*      */                                               
/* 3251 */                                               IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3252 */                                               _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3253 */                                               _jspx_th_c_005fif_005f7.setParent(null);
/*      */                                               
/* 3255 */                                               _jspx_th_c_005fif_005f7.setTest("${!empty associatedmgs}");
/* 3256 */                                               int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3257 */                                               if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                                 for (;;) {
/* 3259 */                                                   out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n        <tr>\n         <td width=\"100%\" colspan=\"2\" class=\"leftlinksheading\">");
/* 3260 */                                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3261 */                                                   out.write("</td>\n        </tr>\n        ");
/*      */                                                   
/* 3263 */                                                   ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3264 */                                                   _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3265 */                                                   _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f7);
/*      */                                                   
/* 3267 */                                                   _jspx_th_c_005fforEach_005f0.setVar("ha");
/*      */                                                   
/* 3269 */                                                   _jspx_th_c_005fforEach_005f0.setItems("${associatedmgs}");
/*      */                                                   
/* 3271 */                                                   _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 3272 */                                                   int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                                   try {
/* 3274 */                                                     int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3275 */                                                     if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                                       for (;;) {
/* 3277 */                                                         out.write("\n        <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n         <td width=\"100%\">\n         <a href=\"/showapplication.do?haid=");
/* 3278 */                                                         if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3336 */                                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                         }
/* 3280 */                                                         out.write("&method=showApplication\" title=\"");
/* 3281 */                                                         if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3336 */                                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                         }
/* 3283 */                                                         out.write("\"  class=\"new-left-links\">\n         ");
/* 3284 */                                                         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3336 */                                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                         }
/* 3286 */                                                         out.write("\n    \t");
/* 3287 */                                                         out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3288 */                                                         out.write("\n         </a></td>\n        <td>");
/*      */                                                         
/* 3290 */                                                         PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3291 */                                                         _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3292 */                                                         _jspx_th_logic_005fpresent_005f10.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                                         
/* 3294 */                                                         _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 3295 */                                                         int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3296 */                                                         if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                                                           for (;;) {
/* 3298 */                                                             out.write("\n        <a href=\"javascript:deleteMGFromMonitor('");
/* 3299 */                                                             if (_jspx_meth_c_005fout_005f16(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3336 */                                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                             }
/* 3301 */                                                             out.write(39);
/* 3302 */                                                             out.write(44);
/* 3303 */                                                             out.write(39);
/* 3304 */                                                             out.print(resId);
/* 3305 */                                                             out.write(39);
/* 3306 */                                                             out.write(44);
/* 3307 */                                                             out.write(39);
/* 3308 */                                                             out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3309 */                                                             out.write("');\"><img src=\"/images/icon_removefromgroup.gif\" alt=\"");
/* 3310 */                                                             out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3311 */                                                             out.write("\"  border=\"0\"  style=\"position:relative; right:10px;\">");
/* 3312 */                                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3313 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/*      */                                                         }
/* 3317 */                                                         if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3318 */                                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/*      */                                                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3336 */                                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                         }
/* 3321 */                                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3322 */                                                         out.write("</td>\n        </tr>\n\t");
/* 3323 */                                                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3324 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 3328 */                                                     if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3336 */                                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                                     }
/*      */                                                   }
/*      */                                                   catch (Throwable _jspx_exception)
/*      */                                                   {
/*      */                                                     for (;;)
/*      */                                                     {
/* 3332 */                                                       int tmp7041_7040 = 0; int[] tmp7041_7038 = _jspx_push_body_count_c_005fforEach_005f0; int tmp7043_7042 = tmp7041_7038[tmp7041_7040];tmp7041_7038[tmp7041_7040] = (tmp7043_7042 - 1); if (tmp7043_7042 <= 0) break;
/* 3333 */                                                       out = _jspx_page_context.popBody(); }
/* 3334 */                                                     _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                                   } finally {
/* 3336 */                                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 3337 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                                   }
/* 3339 */                                                   out.write("\n      </table>\n ");
/* 3340 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3341 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3345 */                                               if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3346 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                               }
/*      */                                               
/* 3349 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3350 */                                               out.write(10);
/* 3351 */                                               out.write(32);
/*      */                                               
/* 3353 */                                               IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3354 */                                               _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3355 */                                               _jspx_th_c_005fif_005f8.setParent(null);
/*      */                                               
/* 3357 */                                               _jspx_th_c_005fif_005f8.setTest("${empty associatedmgs}");
/* 3358 */                                               int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3359 */                                               if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                                                 for (;;) {
/* 3361 */                                                   out.write("\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n         <tr>\n           <td  colspan=\"2\" class=\"leftlinksheading\">");
/* 3362 */                                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 3363 */                                                   out.write("</td>\n         </tr>\n                <tr onmouseout=\"this.className='RCAHeader'\" onmouseover=\"this.className='RCAHeaderHover'\" class=\"RCAHeader\">\n        <td width=\"100%\"  colspan=\"2\" class=\"bodytext\">\n       &nbsp; &nbsp;  ");
/* 3364 */                                                   out.print(FormatUtil.getString("am.webclient.urlmonitor.none.text"));
/* 3365 */                                                   out.write("\n         </td>\n        </tr>\n\t</table>\n ");
/* 3366 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3367 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3371 */                                               if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3372 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                                               }
/*      */                                               
/* 3375 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3376 */                                               out.write(10);
/* 3377 */                                               out.write(32);
/* 3378 */                                               out.write(10);
/*      */ 
/*      */                                             }
/* 3381 */                                             else if (mon)
/*      */                                             {
/*      */ 
/*      */ 
/* 3385 */                                               out.write(10);
/*      */                                               
/* 3387 */                                               IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3388 */                                               _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3389 */                                               _jspx_th_c_005fif_005f9.setParent(null);
/*      */                                               
/* 3391 */                                               _jspx_th_c_005fif_005f9.setTest("${!empty associatedmgs}");
/* 3392 */                                               int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3393 */                                               if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                                                 for (;;) {
/* 3395 */                                                   out.write("\n\t\t\t<td align=\"left\" width=\"29%\" class=\"monitorinfoodd-conf\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"/images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\">\n\n\t\t\t");
/*      */                                                   
/* 3397 */                                                   ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 3398 */                                                   _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 3399 */                                                   _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_c_005fif_005f9);
/*      */                                                   
/* 3401 */                                                   _jspx_th_c_005fforEach_005f1.setVar("ha");
/*      */                                                   
/* 3403 */                                                   _jspx_th_c_005fforEach_005f1.setItems("${associatedmgs}");
/*      */                                                   
/* 3405 */                                                   _jspx_th_c_005fforEach_005f1.setVarStatus("status");
/* 3406 */                                                   int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                                   try {
/* 3408 */                                                     int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 3409 */                                                     if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                                       for (;;) {
/* 3411 */                                                         out.write("\n<span>\n\t\t\t<a href=\"/showapplication.do?haid=");
/* 3412 */                                                         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3470 */                                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                         }
/* 3414 */                                                         out.write("&method=showApplication\" title=\"");
/* 3415 */                                                         if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3470 */                                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                         }
/* 3417 */                                                         out.write("\"  class=\"staticlinks\">\n         ");
/* 3418 */                                                         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3470 */                                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                         }
/* 3420 */                                                         out.write("\n    \t");
/* 3421 */                                                         out.print(getTrimmedText((String)pageContext.getAttribute("monitorName"), 20));
/* 3422 */                                                         out.write("</a></span>\t\n\t\t ");
/*      */                                                         
/* 3424 */                                                         PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3425 */                                                         _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 3426 */                                                         _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f1);
/*      */                                                         
/* 3428 */                                                         _jspx_th_logic_005fpresent_005f11.setRole("ADMIN");
/* 3429 */                                                         int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 3430 */                                                         if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                                           for (;;) {
/* 3432 */                                                             out.write("\n        <a href=\"#\" onClick=\"javascript:deleteMGFromMonitor('");
/* 3433 */                                                             if (_jspx_meth_c_005fout_005f20(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/*      */                                                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3470 */                                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                             }
/* 3435 */                                                             out.write(39);
/* 3436 */                                                             out.write(44);
/* 3437 */                                                             out.write(39);
/* 3438 */                                                             out.print(resId);
/* 3439 */                                                             out.write(39);
/* 3440 */                                                             out.write(44);
/* 3441 */                                                             out.write(39);
/* 3442 */                                                             out.print(FormatUtil.getString("am.webclient.monitorgroup.jsalertforremovemg.text"));
/* 3443 */                                                             out.write("');\">\n\t\t<img src=\"/images/icon-mg-close.png\" alt=\"");
/* 3444 */                                                             out.print(FormatUtil.getString("am.webclient.quickremoval.monitorgroup.txt"));
/* 3445 */                                                             out.write("\"  title=\"<fmt:message key=\"am.webclient.quickremoval.monitorgroup.txt\" />\" border=\"0\" />\n\t\t</a>&nbsp;\n\t\t");
/* 3446 */                                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 3447 */                                                             if (evalDoAfterBody != 2)
/*      */                                                               break;
/*      */                                                           }
/*      */                                                         }
/* 3451 */                                                         if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 3452 */                                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3470 */                                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                         }
/* 3455 */                                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 3456 */                                                         out.write("\n\n\t\t \t");
/* 3457 */                                                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 3458 */                                                         if (evalDoAfterBody != 2)
/*      */                                                           break;
/*      */                                                       }
/*      */                                                     }
/* 3462 */                                                     if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3470 */                                                       _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                                     }
/*      */                                                   }
/*      */                                                   catch (Throwable _jspx_exception)
/*      */                                                   {
/*      */                                                     for (;;)
/*      */                                                     {
/* 3466 */                                                       int tmp7993_7992 = 0; int[] tmp7993_7990 = _jspx_push_body_count_c_005fforEach_005f1; int tmp7995_7994 = tmp7993_7990[tmp7993_7992];tmp7993_7990[tmp7993_7992] = (tmp7995_7994 - 1); if (tmp7995_7994 <= 0) break;
/* 3467 */                                                       out = _jspx_page_context.popBody(); }
/* 3468 */                                                     _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                                   } finally {
/* 3470 */                                                     _jspx_th_c_005fforEach_005f1.doFinally();
/* 3471 */                                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                                   }
/* 3473 */                                                   out.write("\n\t\n\t\t\t</td>\n\t ");
/* 3474 */                                                   int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3475 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/*      */                                               }
/* 3479 */                                               if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3480 */                                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                                               }
/*      */                                               
/* 3483 */                                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3484 */                                               out.write(10);
/* 3485 */                                               out.write(32);
/* 3486 */                                               if (_jspx_meth_c_005fif_005f10(_jspx_page_context))
/*      */                                                 return;
/* 3488 */                                               out.write(32);
/* 3489 */                                               out.write(10);
/*      */                                             }
/*      */                                             
/*      */                                           }
/* 3493 */                                           else if (mon)
/*      */                                           {
/*      */ 
/* 3496 */                                             out.write("\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"></td>\n");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3500 */                                           out.write(9);
/* 3501 */                                           out.write(9);
/* 3502 */                                           out.write("\n\n\n\n\n\n\n\n");
/*      */                                         }
/* 3504 */                                       } } } } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3505 */         out = _jspx_out;
/* 3506 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3507 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3508 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3511 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3517 */     PageContext pageContext = _jspx_page_context;
/* 3518 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3520 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3521 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3522 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3524 */     _jspx_th_c_005fif_005f0.setTest("${param.reconfig=='true'}");
/* 3525 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3526 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3528 */         out.write(10);
/* 3529 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3530 */           return true;
/* 3531 */         out.write(10);
/* 3532 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3533 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3537 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3538 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3539 */       return true;
/*      */     }
/* 3541 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3547 */     PageContext pageContext = _jspx_page_context;
/* 3548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3550 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3551 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3552 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3554 */     _jspx_th_c_005fset_005f0.setVar("baseurl");
/* 3555 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3556 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3557 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3558 */         out = _jspx_page_context.pushBody();
/* 3559 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3560 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3563 */         out.write("/showresource.do?method=showResourceForResourceID&resourceid=");
/* 3564 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3565 */           return true;
/* 3566 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3567 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3570 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3571 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3574 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3575 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3576 */       return true;
/*      */     }
/* 3578 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3584 */     PageContext pageContext = _jspx_page_context;
/* 3585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3587 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3588 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3589 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3591 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 3592 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3593 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3594 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3595 */       return true;
/*      */     }
/* 3597 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3607 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3610 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3611 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3612 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3613 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3614 */       return true;
/*      */     }
/* 3616 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3622 */     PageContext pageContext = _jspx_page_context;
/* 3623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3625 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3626 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3627 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 3629 */     _jspx_th_c_005fout_005f2.setValue("${param.haid}");
/* 3630 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3631 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3632 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3633 */       return true;
/*      */     }
/* 3635 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3636 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3641 */     PageContext pageContext = _jspx_page_context;
/* 3642 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3644 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3645 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3646 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f1);
/*      */     
/* 3648 */     _jspx_th_c_005fout_005f3.setValue("${param.resourceid}");
/* 3649 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3650 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3651 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3652 */       return true;
/*      */     }
/* 3654 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3655 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3660 */     PageContext pageContext = _jspx_page_context;
/* 3661 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3663 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3664 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3665 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3667 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3668 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3669 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3670 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3671 */       return true;
/*      */     }
/* 3673 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3679 */     PageContext pageContext = _jspx_page_context;
/* 3680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3682 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3683 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3684 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3686 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3687 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3688 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3690 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 3691 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3692 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3696 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3697 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3698 */       return true;
/*      */     }
/* 3700 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3701 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3706 */     PageContext pageContext = _jspx_page_context;
/* 3707 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3709 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3710 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3711 */     _jspx_th_c_005fout_005f5.setParent(null);
/*      */     
/* 3713 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 3714 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3715 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3716 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3717 */       return true;
/*      */     }
/* 3719 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3720 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3725 */     PageContext pageContext = _jspx_page_context;
/* 3726 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3728 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3729 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3730 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 3732 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 3733 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3734 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 3736 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 3737 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3738 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3742 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3743 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3744 */       return true;
/*      */     }
/* 3746 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3747 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3752 */     PageContext pageContext = _jspx_page_context;
/* 3753 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3755 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3756 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3757 */     _jspx_th_c_005fout_005f6.setParent(null);
/*      */     
/* 3759 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 3760 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3761 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3762 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3763 */       return true;
/*      */     }
/* 3765 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3766 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3771 */     PageContext pageContext = _jspx_page_context;
/* 3772 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3774 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3775 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3776 */     _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */     
/* 3778 */     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 3779 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3780 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 3782 */         out.write("\n\t\t\t alertUser();\n\t\t \treturn;\n\t\t");
/* 3783 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3784 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3788 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3789 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3790 */       return true;
/*      */     }
/* 3792 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3793 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3798 */     PageContext pageContext = _jspx_page_context;
/* 3799 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3801 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3802 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3803 */     _jspx_th_c_005fout_005f7.setParent(null);
/*      */     
/* 3805 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 3806 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3807 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3808 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3809 */       return true;
/*      */     }
/* 3811 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3812 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3817 */     PageContext pageContext = _jspx_page_context;
/* 3818 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3820 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3821 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3822 */     _jspx_th_c_005fout_005f8.setParent(null);
/*      */     
/* 3824 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 3825 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3826 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3827 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3828 */       return true;
/*      */     }
/* 3830 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3831 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3836 */     PageContext pageContext = _jspx_page_context;
/* 3837 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3839 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3840 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3841 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3843 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 3844 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3845 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3846 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3847 */       return true;
/*      */     }
/* 3849 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3850 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3855 */     PageContext pageContext = _jspx_page_context;
/* 3856 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3858 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3859 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3860 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3862 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 3863 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3864 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3865 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3866 */       return true;
/*      */     }
/* 3868 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3869 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3874 */     PageContext pageContext = _jspx_page_context;
/* 3875 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3877 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3878 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3879 */     _jspx_th_c_005fout_005f11.setParent(null);
/*      */     
/* 3881 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 3882 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3883 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3884 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3885 */       return true;
/*      */     }
/* 3887 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3888 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3893 */     PageContext pageContext = _jspx_page_context;
/* 3894 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3896 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3897 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3898 */     _jspx_th_c_005fout_005f12.setParent(null);
/*      */     
/* 3900 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 3901 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3902 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3903 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3904 */       return true;
/*      */     }
/* 3906 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3907 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3912 */     PageContext pageContext = _jspx_page_context;
/* 3913 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3915 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3916 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3917 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3919 */     _jspx_th_c_005fout_005f13.setValue("${ha.key}");
/* 3920 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3921 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3922 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3923 */       return true;
/*      */     }
/* 3925 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3931 */     PageContext pageContext = _jspx_page_context;
/* 3932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3934 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3935 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3936 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3938 */     _jspx_th_c_005fout_005f14.setValue("${ha.value}");
/* 3939 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3940 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3941 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3942 */       return true;
/*      */     }
/* 3944 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3950 */     PageContext pageContext = _jspx_page_context;
/* 3951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3953 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3954 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3955 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3957 */     _jspx_th_c_005fset_005f1.setVar("monitorName");
/* 3958 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3959 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3960 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3961 */         out = _jspx_page_context.pushBody();
/* 3962 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3963 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3964 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3967 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3968 */           return true;
/* 3969 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3970 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3973 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3974 */         out = _jspx_page_context.popBody();
/* 3975 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3978 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3979 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3980 */       return true;
/*      */     }
/* 3982 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3988 */     PageContext pageContext = _jspx_page_context;
/* 3989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3991 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3992 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 3993 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3995 */     _jspx_th_c_005fout_005f15.setValue("${ha.value}");
/* 3996 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 3997 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 3998 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3999 */       return true;
/*      */     }
/* 4001 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4002 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4007 */     PageContext pageContext = _jspx_page_context;
/* 4008 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4010 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4011 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4012 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 4014 */     _jspx_th_c_005fout_005f16.setValue("${ha.key}");
/* 4015 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4016 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4017 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4018 */       return true;
/*      */     }
/* 4020 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4021 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4026 */     PageContext pageContext = _jspx_page_context;
/* 4027 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4029 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4030 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4031 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4033 */     _jspx_th_c_005fout_005f17.setValue("${ha.key}");
/* 4034 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4035 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4036 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4037 */       return true;
/*      */     }
/* 4039 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4040 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4045 */     PageContext pageContext = _jspx_page_context;
/* 4046 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4048 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4049 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4050 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4052 */     _jspx_th_c_005fout_005f18.setValue("${ha.value}");
/* 4053 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4054 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4055 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4056 */       return true;
/*      */     }
/* 4058 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4059 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4064 */     PageContext pageContext = _jspx_page_context;
/* 4065 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4067 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4068 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 4069 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 4071 */     _jspx_th_c_005fset_005f2.setVar("monitorName");
/* 4072 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 4073 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 4074 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4075 */         out = _jspx_page_context.pushBody();
/* 4076 */         _jspx_push_body_count_c_005fforEach_005f1[0] += 1;
/* 4077 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 4078 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4081 */         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
/* 4082 */           return true;
/* 4083 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 4084 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4087 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 4088 */         out = _jspx_page_context.popBody();
/* 4089 */         _jspx_push_body_count_c_005fforEach_005f1[0] -= 1;
/*      */       }
/*      */     }
/* 4092 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 4093 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4094 */       return true;
/*      */     }
/* 4096 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 4097 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4102 */     PageContext pageContext = _jspx_page_context;
/* 4103 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4105 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4106 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4107 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 4109 */     _jspx_th_c_005fout_005f19.setValue("${ha.value}");
/* 4110 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4111 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4112 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4113 */       return true;
/*      */     }
/* 4115 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4116 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 4121 */     PageContext pageContext = _jspx_page_context;
/* 4122 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4124 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4125 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4126 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 4128 */     _jspx_th_c_005fout_005f20.setValue("${ha.key}");
/* 4129 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4130 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4131 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4132 */       return true;
/*      */     }
/* 4134 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4135 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4140 */     PageContext pageContext = _jspx_page_context;
/* 4141 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4143 */     IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4144 */     _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 4145 */     _jspx_th_c_005fif_005f10.setParent(null);
/*      */     
/* 4147 */     _jspx_th_c_005fif_005f10.setTest("${empty associatedmgs}");
/* 4148 */     int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 4149 */     if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */       for (;;) {
/* 4151 */         out.write("\t\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\" width=\"29%\"><b><fmt:message key=\"am.webclient.urlmonitor.associatedgroups.text\"/></b></td>\n\t\t\t<td class=\"monitorinfoodd-conf \" width=\"1%\" ><img src=\"images/spacer.gif\" height=\"10\" width=\"10\" >\n\t\t\t<td align=\"left\" class=\"monitorinfoodd-conf\"><fmt:message key=\"am.webclient.urlmonitor.none.text\"/></td>\n\t ");
/* 4152 */         int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 4153 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4157 */     if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 4158 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4159 */       return true;
/*      */     }
/* 4161 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 4162 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\WSMLeftArea_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */