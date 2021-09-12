/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.struts.beans.ResourceNames;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
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
/*      */ import javax.servlet.http.Cookie;
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
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class PlasmaView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   53 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   56 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   57 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   58 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   65 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   70 */     ArrayList list = null;
/*   71 */     StringBuffer sbf = new StringBuffer();
/*   72 */     ManagedApplication mo = new ManagedApplication();
/*   73 */     if (distinct)
/*      */     {
/*   75 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   79 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   82 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   84 */       ArrayList row = (ArrayList)list.get(i);
/*   85 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   86 */       if (distinct) {
/*   87 */         sbf.append(row.get(0));
/*      */       } else
/*   89 */         sbf.append(row.get(1));
/*   90 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   93 */     return sbf.toString(); }
/*      */   
/*   95 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   98 */     if (severity == null)
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  102 */     if (severity.equals("5"))
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("1"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  113 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  120 */     if (severity == null)
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("1"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("4"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("5"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  139 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  145 */     if (severity == null)
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  149 */     if (severity.equals("5"))
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  153 */     if (severity.equals("1"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  159 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  165 */     if (severity == null)
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  169 */     if (severity.equals("1"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  173 */     if (severity.equals("4"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  177 */     if (severity.equals("5"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  183 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  189 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  195 */     if (severity == 5)
/*      */     {
/*  197 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  199 */     if (severity == 1)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  206 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  212 */     if (severity == null)
/*      */     {
/*  214 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  216 */     if (severity.equals("5"))
/*      */     {
/*  218 */       if (isAvailability) {
/*  219 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  222 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  225 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  227 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  229 */     if (severity.equals("1"))
/*      */     {
/*  231 */       if (isAvailability) {
/*  232 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  235 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  242 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  249 */     if (severity == null)
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("5"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("4"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("1"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  268 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  274 */     if (severity == null)
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("5"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("4"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("1"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  293 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  300 */     if (severity == null)
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  304 */     if (severity.equals("5"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  308 */     if (severity.equals("4"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  312 */     if (severity.equals("1"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  319 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  327 */     StringBuffer out = new StringBuffer();
/*  328 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  329 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  330 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  331 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  332 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  333 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  334 */     out.append("</tr>");
/*  335 */     out.append("</form></table>");
/*  336 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  343 */     if (val == null)
/*      */     {
/*  345 */       return "-";
/*      */     }
/*      */     
/*  348 */     String ret = FormatUtil.formatNumber(val);
/*  349 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  350 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  353 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  357 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  365 */     StringBuffer out = new StringBuffer();
/*  366 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  367 */     out.append("<tr>");
/*  368 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  370 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  372 */     out.append("</tr>");
/*  373 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  377 */       if (j % 2 == 0)
/*      */       {
/*  379 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  383 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  386 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  388 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  391 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  395 */       out.append("</tr>");
/*      */     }
/*  397 */     out.append("</table>");
/*  398 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  399 */     out.append("<tr>");
/*  400 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  401 */     out.append("</tr>");
/*  402 */     out.append("</table>");
/*  403 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  409 */     StringBuffer out = new StringBuffer();
/*  410 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  411 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  412 */     out.append("<tr>");
/*  413 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  414 */     out.append("<tr>");
/*  415 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  416 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  417 */     out.append("</tr>");
/*  418 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  421 */       out.append("<tr>");
/*  422 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  423 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  424 */       out.append("</tr>");
/*      */     }
/*      */     
/*  427 */     out.append("</table>");
/*  428 */     out.append("</table>");
/*  429 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  434 */     if (severity.equals("0"))
/*      */     {
/*  436 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  440 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  447 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  460 */     StringBuffer out = new StringBuffer();
/*  461 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  462 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  464 */       out.append("<tr>");
/*  465 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  466 */       out.append("</tr>");
/*      */       
/*      */ 
/*  469 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  471 */         String borderclass = "";
/*      */         
/*      */ 
/*  474 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  476 */         out.append("<tr>");
/*      */         
/*  478 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  479 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  480 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  486 */     out.append("</table><br>");
/*  487 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  488 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  490 */       List sLinks = secondLevelOfLinks[0];
/*  491 */       List sText = secondLevelOfLinks[1];
/*  492 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  495 */         out.append("<tr>");
/*  496 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  497 */         out.append("</tr>");
/*  498 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  500 */           String borderclass = "";
/*      */           
/*      */ 
/*  503 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  505 */           out.append("<tr>");
/*      */           
/*  507 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  508 */           if (sLinks.get(i).toString().length() == 0) {
/*  509 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  512 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  514 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  518 */     out.append("</table>");
/*  519 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  526 */     StringBuffer out = new StringBuffer();
/*  527 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  528 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  530 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  532 */         out.append("<tr>");
/*  533 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  534 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  538 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  540 */           String borderclass = "";
/*      */           
/*      */ 
/*  543 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  545 */           out.append("<tr>");
/*      */           
/*  547 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  548 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  549 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  552 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  555 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  560 */     out.append("</table><br>");
/*  561 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  562 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  564 */       List sLinks = secondLevelOfLinks[0];
/*  565 */       List sText = secondLevelOfLinks[1];
/*  566 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  569 */         out.append("<tr>");
/*  570 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  571 */         out.append("</tr>");
/*  572 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  574 */           String borderclass = "";
/*      */           
/*      */ 
/*  577 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  579 */           out.append("<tr>");
/*      */           
/*  581 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  582 */           if (sLinks.get(i).toString().length() == 0) {
/*  583 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  586 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  588 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  592 */     out.append("</table>");
/*  593 */     return out.toString();
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
/*  606 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  621 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  624 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  627 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  635 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  640 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  645 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  650 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  655 */     if (val != null)
/*      */     {
/*  657 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  661 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  666 */     if (val == null) {
/*  667 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  671 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  676 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  682 */     if (val != null)
/*      */     {
/*  684 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  688 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  694 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  699 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  708 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  713 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  718 */     String hostaddress = "";
/*  719 */     String ip = request.getHeader("x-forwarded-for");
/*  720 */     if (ip == null)
/*  721 */       ip = request.getRemoteAddr();
/*  722 */     InetAddress add = null;
/*  723 */     if (ip.equals("127.0.0.1")) {
/*  724 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  728 */       add = InetAddress.getByName(ip);
/*      */     }
/*  730 */     hostaddress = add.getHostName();
/*  731 */     if (hostaddress.indexOf('.') != -1) {
/*  732 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  733 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  737 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  742 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  748 */     if (severity == null)
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  752 */     if (severity.equals("5"))
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("1"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  763 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  768 */     ResultSet set = null;
/*  769 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  770 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  772 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  773 */       if (set.next()) { String str1;
/*  774 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  775 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  778 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  783 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  786 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  788 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  792 */     StringBuffer rca = new StringBuffer();
/*  793 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  794 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  797 */     int rcalength = key.length();
/*  798 */     String split = "6. ";
/*  799 */     int splitPresent = key.indexOf(split);
/*  800 */     String div1 = "";String div2 = "";
/*  801 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  803 */       if (rcalength > 180) {
/*  804 */         rca.append("<span class=\"rca-critical-text\">");
/*  805 */         getRCATrimmedText(key, rca);
/*  806 */         rca.append("</span>");
/*      */       } else {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         rca.append(key);
/*  810 */         rca.append("</span>");
/*      */       }
/*  812 */       return rca.toString();
/*      */     }
/*  814 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  815 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  816 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  817 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div1, rca);
/*  819 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  822 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  823 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  824 */     getRCATrimmedText(div2, rca);
/*  825 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  827 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  832 */     String[] st = msg.split("<br>");
/*  833 */     for (int i = 0; i < st.length; i++) {
/*  834 */       String s = st[i];
/*  835 */       if (s.length() > 180) {
/*  836 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  838 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  842 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  843 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  845 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  849 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  850 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  851 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  854 */       if (key == null) {
/*  855 */         return ret;
/*      */       }
/*      */       
/*  858 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  859 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  862 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  863 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  864 */       set = AMConnectionPool.executeQueryStmt(query);
/*  865 */       if (set.next())
/*      */       {
/*  867 */         String helpLink = set.getString("LINK");
/*  868 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  871 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  877 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  896 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  887 */         if (set != null) {
/*  888 */           AMConnectionPool.closeStatement(set);
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
/*  902 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  903 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  905 */       String entityStr = (String)keys.nextElement();
/*  906 */       String mmessage = temp.getProperty(entityStr);
/*  907 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  908 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  910 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  916 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  917 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  919 */       String entityStr = (String)keys.nextElement();
/*  920 */       String mmessage = temp.getProperty(entityStr);
/*  921 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  922 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  924 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  929 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  939 */     String des = new String();
/*  940 */     while (str.indexOf(find) != -1) {
/*  941 */       des = des + str.substring(0, str.indexOf(find));
/*  942 */       des = des + replace;
/*  943 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  945 */     des = des + str;
/*  946 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  953 */       if (alert == null)
/*      */       {
/*  955 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  957 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  959 */         return "&nbsp;";
/*      */       }
/*      */       
/*  962 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  964 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  967 */       int rcalength = test.length();
/*  968 */       if (rcalength < 300)
/*      */       {
/*  970 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  974 */       StringBuffer out = new StringBuffer();
/*  975 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  976 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  977 */       out.append("</div>");
/*  978 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  979 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  980 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  985 */       ex.printStackTrace();
/*      */     }
/*  987 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  993 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  998 */     ArrayList attribIDs = new ArrayList();
/*  999 */     ArrayList resIDs = new ArrayList();
/* 1000 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1002 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1004 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1006 */       String resourceid = "";
/* 1007 */       String resourceType = "";
/* 1008 */       if (type == 2) {
/* 1009 */         resourceid = (String)row.get(0);
/* 1010 */         resourceType = (String)row.get(3);
/*      */       }
/* 1012 */       else if (type == 3) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1017 */         resourceid = (String)row.get(6);
/* 1018 */         resourceType = (String)row.get(7);
/*      */       }
/* 1020 */       resIDs.add(resourceid);
/* 1021 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1022 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1024 */       String healthentity = null;
/* 1025 */       String availentity = null;
/* 1026 */       if (healthid != null) {
/* 1027 */         healthentity = resourceid + "_" + healthid;
/* 1028 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1031 */       if (availid != null) {
/* 1032 */         availentity = resourceid + "_" + availid;
/* 1033 */         entitylist.add(availentity);
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
/* 1047 */     Properties alert = getStatus(entitylist);
/* 1048 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1053 */     int size = monitorList.size();
/*      */     
/* 1055 */     String[] severity = new String[size];
/*      */     
/* 1057 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1059 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1060 */       String resourceName1 = (String)row1.get(7);
/* 1061 */       String resourceid1 = (String)row1.get(6);
/* 1062 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1063 */       if (severity[j] == null)
/*      */       {
/* 1065 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1069 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1071 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1073 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1076 */         if (sev > 0) {
/* 1077 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1078 */           monitorList.set(k, monitorList.get(j));
/* 1079 */           monitorList.set(j, t);
/* 1080 */           String temp = severity[k];
/* 1081 */           severity[k] = severity[j];
/* 1082 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1088 */     int z = 0;
/* 1089 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1092 */       int i = 0;
/* 1093 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1096 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1100 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1104 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1106 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1109 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1113 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1116 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1117 */       String resourceName1 = (String)row1.get(7);
/* 1118 */       String resourceid1 = (String)row1.get(6);
/* 1119 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1120 */       if (hseverity[j] == null)
/*      */       {
/* 1122 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1127 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1129 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1132 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1135 */         if (hsev > 0) {
/* 1136 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1137 */           monitorList.set(k, monitorList.get(j));
/* 1138 */           monitorList.set(j, t);
/* 1139 */           String temp1 = hseverity[k];
/* 1140 */           hseverity[k] = hseverity[j];
/* 1141 */           hseverity[j] = temp1;
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
/* 1153 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1154 */     boolean forInventory = false;
/* 1155 */     String trdisplay = "none";
/* 1156 */     String plusstyle = "inline";
/* 1157 */     String minusstyle = "none";
/* 1158 */     String haidTopLevel = "";
/* 1159 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1161 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1163 */         haidTopLevel = request.getParameter("haid");
/* 1164 */         forInventory = true;
/* 1165 */         trdisplay = "table-row;";
/* 1166 */         plusstyle = "none";
/* 1167 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1174 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1177 */     ArrayList listtoreturn = new ArrayList();
/* 1178 */     StringBuffer toreturn = new StringBuffer();
/* 1179 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1180 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1181 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1183 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1185 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1186 */       String childresid = (String)singlerow.get(0);
/* 1187 */       String childresname = (String)singlerow.get(1);
/* 1188 */       childresname = ExtProdUtil.decodeString(childresname);
/* 1189 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1190 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1191 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1192 */       String unmanagestatus = (String)singlerow.get(5);
/* 1193 */       String actionstatus = (String)singlerow.get(6);
/* 1194 */       String linkclass = "monitorgp-links";
/* 1195 */       String titleforres = childresname;
/* 1196 */       String titilechildresname = childresname;
/* 1197 */       String childimg = "/images/trcont.png";
/* 1198 */       String flag = "enable";
/* 1199 */       String dcstarted = (String)singlerow.get(8);
/* 1200 */       String configMonitor = "";
/* 1201 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1202 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1204 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1206 */       if (singlerow.get(7) != null)
/*      */       {
/* 1208 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1210 */       String haiGroupType = "0";
/* 1211 */       if ("HAI".equals(childtype))
/*      */       {
/* 1213 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1215 */       childimg = "/images/trend.png";
/* 1216 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1217 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1218 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1220 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1222 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1224 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1225 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1228 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1230 */         linkclass = "disabledtext";
/* 1231 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1233 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1234 */       String availmouseover = "";
/* 1235 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1237 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1239 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1240 */       String healthmouseover = "";
/* 1241 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1243 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1246 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1247 */       int spacing = 0;
/* 1248 */       if (level >= 1)
/*      */       {
/* 1250 */         spacing = 40 * level;
/*      */       }
/* 1252 */       if (childtype.equals("HAI"))
/*      */       {
/* 1254 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1255 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1256 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1258 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1259 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1260 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1261 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1262 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1263 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1264 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1265 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1266 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1267 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1268 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1270 */         if (!forInventory)
/*      */         {
/* 1272 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1275 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1277 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1279 */           actions = editlink + actions;
/*      */         }
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = actions + associatelink;
/*      */         }
/* 1285 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1286 */         String arrowimg = "";
/* 1287 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1289 */           actions = "";
/* 1290 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1291 */           checkbox = "";
/* 1292 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1294 */         if (isIt360)
/*      */         {
/* 1296 */           actionimg = "";
/* 1297 */           actions = "";
/* 1298 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1299 */           checkbox = "";
/*      */         }
/*      */         
/* 1302 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1304 */           actions = "";
/*      */         }
/* 1306 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1308 */           checkbox = "";
/*      */         }
/*      */         
/* 1311 */         String resourcelink = "";
/*      */         
/* 1313 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1315 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1322 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1324 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1325 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1328 */         if (!isIt360)
/*      */         {
/* 1330 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1337 */         toreturn.append("</tr>");
/* 1338 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1340 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1341 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1345 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1346 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1349 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1353 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1355 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1357 */             toreturn.append(assocMessage);
/* 1358 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1361 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1367 */         String resourcelink = null;
/* 1368 */         boolean hideEditLink = false;
/* 1369 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1371 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1372 */           hideEditLink = true;
/* 1373 */           if (isIt360)
/*      */           {
/* 1375 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1379 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1381 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1383 */           hideEditLink = true;
/* 1384 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1385 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1390 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1393 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1394 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1395 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1396 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1397 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1398 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1399 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1400 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1401 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1402 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1403 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1404 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1405 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1407 */         if (hideEditLink)
/*      */         {
/* 1409 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1411 */         if (!forInventory)
/*      */         {
/* 1413 */           removefromgroup = "";
/*      */         }
/* 1415 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1416 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1417 */           actions = actions + configcustomfields;
/*      */         }
/* 1419 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1421 */           actions = editlink + actions;
/*      */         }
/* 1423 */         String managedLink = "";
/* 1424 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1426 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1427 */           actions = "";
/* 1428 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1429 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1432 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1434 */           checkbox = "";
/*      */         }
/*      */         
/* 1437 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1439 */           actions = "";
/*      */         }
/* 1441 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1442 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1443 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1444 */         if (isIt360)
/*      */         {
/* 1446 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1452 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1453 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1454 */         if (!isIt360)
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1462 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1465 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1472 */       StringBuilder toreturn = new StringBuilder();
/* 1473 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1474 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1475 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1476 */       String title = "";
/* 1477 */       message = EnterpriseUtil.decodeString(message);
/* 1478 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1479 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1480 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1482 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1484 */       else if ("5".equals(severity))
/*      */       {
/* 1486 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1492 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1493 */       toreturn.append(v);
/*      */       
/* 1495 */       toreturn.append(link);
/* 1496 */       if (severity == null)
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("5"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("4"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("1"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1515 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1517 */       toreturn.append("</a>");
/* 1518 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1522 */       ex.printStackTrace();
/*      */     }
/* 1524 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1531 */       StringBuilder toreturn = new StringBuilder();
/* 1532 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1533 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1534 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1535 */       if (message == null)
/*      */       {
/* 1537 */         message = "";
/*      */       }
/*      */       
/* 1540 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1541 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1543 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1544 */       toreturn.append(v);
/*      */       
/* 1546 */       toreturn.append(link);
/*      */       
/* 1548 */       if (severity == null)
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1552 */       else if (severity.equals("5"))
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("1"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1563 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1565 */       toreturn.append("</a>");
/* 1566 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1572 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1575 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1576 */     if (invokeActions != null) {
/* 1577 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1578 */       while (iterator.hasNext()) {
/* 1579 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1580 */         if (actionmap.containsKey(actionid)) {
/* 1581 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1586 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1590 */     String actionLink = "";
/* 1591 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1592 */     String query = "";
/* 1593 */     ResultSet rs = null;
/* 1594 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1595 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1596 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1597 */       actionLink = "method=" + methodName;
/*      */     }
/* 1599 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1600 */       actionLink = methodName;
/*      */     }
/* 1602 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1603 */     Iterator itr = methodarglist.iterator();
/* 1604 */     boolean isfirstparam = true;
/* 1605 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1606 */     while (itr.hasNext()) {
/* 1607 */       HashMap argmap = (HashMap)itr.next();
/* 1608 */       String argtype = (String)argmap.get("TYPE");
/* 1609 */       String argname = (String)argmap.get("IDENTITY");
/* 1610 */       String paramname = (String)argmap.get("PARAMETER");
/* 1611 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1612 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1613 */         isfirstparam = false;
/* 1614 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1616 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1620 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1624 */         actionLink = actionLink + "&";
/*      */       }
/* 1626 */       String paramValue = null;
/* 1627 */       String tempargname = argname;
/* 1628 */       if (commonValues.getProperty(tempargname) != null) {
/* 1629 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1632 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1633 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1634 */           if (dbType.equals("mysql")) {
/* 1635 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1638 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1640 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1642 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1643 */             if (rs.next()) {
/* 1644 */               paramValue = rs.getString("VALUE");
/* 1645 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1649 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1653 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1656 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1661 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1662 */           paramValue = rowId;
/*      */         }
/* 1664 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1665 */           paramValue = managedObjectName;
/*      */         }
/* 1667 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1668 */           paramValue = resID;
/*      */         }
/* 1670 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1671 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1674 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1676 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1677 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1678 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1680 */     return actionLink;
/*      */   }
/*      */   
/* 1683 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1684 */     String dependentAttribute = null;
/* 1685 */     String align = "left";
/*      */     
/* 1687 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1688 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1689 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1690 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1691 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1692 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1693 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1694 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1695 */       align = "center";
/*      */     }
/*      */     
/* 1698 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1699 */     String actualdata = "";
/*      */     
/* 1701 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1702 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1703 */         actualdata = availValue;
/*      */       }
/* 1705 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1706 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1710 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1711 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1714 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1720 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1721 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1722 */       toreturn.append("<table>");
/* 1723 */       toreturn.append("<tr>");
/* 1724 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1725 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1726 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1727 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1728 */         String toolTip = "";
/* 1729 */         String hideClass = "";
/* 1730 */         String textStyle = "";
/* 1731 */         boolean isreferenced = true;
/* 1732 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1733 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1734 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1735 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1737 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1738 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1739 */           while (valueList.hasMoreTokens()) {
/* 1740 */             String dependentVal = valueList.nextToken();
/* 1741 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1742 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1743 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1745 */               toolTip = "";
/* 1746 */               hideClass = "";
/* 1747 */               isreferenced = false;
/* 1748 */               textStyle = "disabledtext";
/* 1749 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1753 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1754 */           toolTip = "";
/* 1755 */           hideClass = "";
/* 1756 */           isreferenced = false;
/* 1757 */           textStyle = "disabledtext";
/* 1758 */           if (dependentImageMap != null) {
/* 1759 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1760 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1763 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1767 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1768 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1769 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1770 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1771 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1772 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1774 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1775 */           if (isreferenced) {
/* 1776 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1780 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1781 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1782 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1783 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1784 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1785 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1787 */           toreturn.append("</span>");
/* 1788 */           toreturn.append("</a>");
/* 1789 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1792 */       toreturn.append("</tr>");
/* 1793 */       toreturn.append("</table>");
/* 1794 */       toreturn.append("</td>");
/*      */     } else {
/* 1796 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1799 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1803 */     String colTime = null;
/* 1804 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1805 */     if ((rows != null) && (rows.size() > 0)) {
/* 1806 */       Iterator<String> itr = rows.iterator();
/* 1807 */       String maxColQuery = "";
/* 1808 */       for (;;) { if (itr.hasNext()) {
/* 1809 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1810 */           ResultSet maxCol = null;
/*      */           try {
/* 1812 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1813 */             while (maxCol.next()) {
/* 1814 */               if (colTime == null) {
/* 1815 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1818 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1827 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1829 */               if (maxCol != null)
/* 1830 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1832 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1827 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1829 */               if (maxCol != null)
/* 1830 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1832 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1837 */     return colTime;
/*      */   }
/*      */   
/* 1840 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1841 */     tablename = null;
/* 1842 */     ResultSet rsTable = null;
/* 1843 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1845 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1846 */       while (rsTable.next()) {
/* 1847 */         tablename = rsTable.getString("DATATABLE");
/* 1848 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1849 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1862 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1853 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1856 */         if (rsTable != null)
/* 1857 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1859 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1865 */     String argsList = "";
/* 1866 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1868 */       if (showArgsMap.get(row) != null) {
/* 1869 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1870 */         if (showArgslist != null) {
/* 1871 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1872 */             if (argsList.trim().equals("")) {
/* 1873 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1876 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1883 */       e.printStackTrace();
/* 1884 */       return "";
/*      */     }
/* 1886 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1891 */     String argsList = "";
/* 1892 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1895 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1897 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1898 */         if (hideArgsList != null)
/*      */         {
/* 1900 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1902 */             if (argsList.trim().equals(""))
/*      */             {
/* 1904 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1908 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1916 */       ex.printStackTrace();
/*      */     }
/* 1918 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1922 */     StringBuilder toreturn = new StringBuilder();
/* 1923 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1930 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1931 */       Iterator itr = tActionList.iterator();
/* 1932 */       while (itr.hasNext()) {
/* 1933 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1934 */         String confirmmsg = "";
/* 1935 */         String link = "";
/* 1936 */         String isJSP = "NO";
/* 1937 */         HashMap tactionMap = (HashMap)itr.next();
/* 1938 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1939 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1940 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1941 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1942 */           (actionmap.containsKey(actionId))) {
/* 1943 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1944 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1945 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1946 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1947 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1949 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1955 */           if (isTableAction) {
/* 1956 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1959 */             tableName = "Link";
/* 1960 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1961 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1962 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1963 */             toreturn.append("</a></td>");
/*      */           }
/* 1965 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1966 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1967 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1974 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1980 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1982 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1983 */       Properties prop = (Properties)node.getUserObject();
/* 1984 */       String mgID = prop.getProperty("label");
/* 1985 */       String mgName = prop.getProperty("value");
/* 1986 */       String isParent = prop.getProperty("isParent");
/* 1987 */       int mgIDint = Integer.parseInt(mgID);
/* 1988 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1990 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1992 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1993 */       if (node.getChildCount() > 0)
/*      */       {
/* 1995 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1997 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1999 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2001 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2010 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2012 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2016 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2023 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2024 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2026 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2030 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2032 */       if (node.getChildCount() > 0)
/*      */       {
/* 2034 */         builder.append("<UL>");
/* 2035 */         printMGTree(node, builder);
/* 2036 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2041 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2042 */     StringBuffer toReturn = new StringBuffer();
/* 2043 */     String table = "-";
/*      */     try {
/* 2045 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2046 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2047 */       float total = 0.0F;
/* 2048 */       while (it.hasNext()) {
/* 2049 */         String attName = (String)it.next();
/* 2050 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2051 */         boolean roundOffData = false;
/* 2052 */         if ((data != null) && (!data.equals(""))) {
/* 2053 */           if (data.indexOf(",") != -1) {
/* 2054 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2057 */             float value = Float.parseFloat(data);
/* 2058 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2061 */             total += value;
/* 2062 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2065 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2070 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2071 */       while (attVsWidthList.hasNext()) {
/* 2072 */         String attName = (String)attVsWidthList.next();
/* 2073 */         String data = (String)attVsWidthProps.get(attName);
/* 2074 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2075 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2076 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2077 */         String className = (String)graphDetails.get("ClassName");
/* 2078 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2079 */         if (percentage < 1.0F)
/*      */         {
/* 2081 */           data = percentage + "";
/*      */         }
/* 2083 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2085 */       if (toReturn.length() > 0) {
/* 2086 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2090 */       e.printStackTrace();
/*      */     }
/* 2092 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2098 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2099 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2100 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2101 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2102 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2103 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2104 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2105 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2106 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2109 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2110 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2111 */       splitvalues[0] = multiplecondition.toString();
/* 2112 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2115 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2120 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2121 */     if (thresholdType != 3) {
/* 2122 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2123 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2124 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2125 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2126 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2127 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2129 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2130 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2131 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2132 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2133 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2134 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2136 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2137 */     if (updateSelected != null) {
/* 2138 */       updateSelected[0] = "selected";
/*      */     }
/* 2140 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2145 */       StringBuffer toreturn = new StringBuffer("");
/* 2146 */       if (commaSeparatedMsgId != null) {
/* 2147 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2148 */         int count = 0;
/* 2149 */         while (msgids.hasMoreTokens()) {
/* 2150 */           String id = msgids.nextToken();
/* 2151 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2152 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2153 */           count++;
/* 2154 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2155 */             if (toreturn.length() == 0) {
/* 2156 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2158 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2159 */             if (!image.trim().equals("")) {
/* 2160 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2162 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2163 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2166 */         if (toreturn.length() > 0) {
/* 2167 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2171 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2174 */       e.printStackTrace(); }
/* 2175 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2181 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2187 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2188 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2189 */     _jspx_dependants.put("/jsp/includes/jqueryutility.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2230 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.release();
/* 2242 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2244 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2245 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2246 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2247 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2254 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2257 */     JspWriter out = null;
/* 2258 */     Object page = this;
/* 2259 */     JspWriter _jspx_out = null;
/* 2260 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2264 */       response.setContentType("text/html;charset=UTF-8");
/* 2265 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2267 */       _jspx_page_context = pageContext;
/* 2268 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2269 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2270 */       session = pageContext.getSession();
/* 2271 */       out = pageContext.getOut();
/* 2272 */       _jspx_out = out;
/*      */       
/* 2274 */       out.write("<!--$Id$ -->\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n\n\n\n\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n<link href=\"/images/");
/* 2275 */       if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */         return;
/* 2277 */       out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/* 2278 */       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */         return;
/* 2280 */       out.write(10);
/* 2281 */       out.write(10);
/* 2282 */       out.write(10);
/* 2283 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2285 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2286 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2295 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2296 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2297 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2298 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2301 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2302 */         String available = null;
/* 2303 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2304 */         out.write(10);
/*      */         
/* 2306 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2307 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2316 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2317 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2318 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2319 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2322 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2323 */           String unavailable = null;
/* 2324 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2325 */           out.write(10);
/*      */           
/* 2327 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2328 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2337 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2338 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2339 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2340 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2343 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2344 */             String unmanaged = null;
/* 2345 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2346 */             out.write(10);
/*      */             
/* 2348 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2349 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2358 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2359 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2360 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2361 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2364 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2365 */               String scheduled = null;
/* 2366 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2367 */               out.write(10);
/*      */               
/* 2369 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2370 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2379 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2380 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2381 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2382 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2385 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2386 */                 String critical = null;
/* 2387 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2388 */                 out.write(10);
/*      */                 
/* 2390 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2391 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2400 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2401 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2402 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2403 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2406 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2407 */                   String clear = null;
/* 2408 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2409 */                   out.write(10);
/*      */                   
/* 2411 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2412 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2421 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2422 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2423 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2424 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2427 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2428 */                     String warning = null;
/* 2429 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2430 */                     out.write(10);
/* 2431 */                     out.write(10);
/*      */                     
/* 2433 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2434 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/* 2438 */                     out.write(10);
/* 2439 */                     out.write(10);
/* 2440 */                     out.write("\n\n<link href=\"/images/jquery-ui.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n<script SRC=\"/template/jquery-1.11.0.min.js\" type=\"text/javascript\"></script>\n<script SRC=\"/template/jquery-migrate-1.2.1.min.js\" type=\"text/javascript\"></script>\n<script src=\"/template/jquery-ui.min.js\" type=\"text/javascript\"></script>\n\n");
/* 2441 */                     out.write("\n\n\n\n\n\n\n");
/* 2442 */                     ManagedApplication mo = null;
/* 2443 */                     synchronized (application) {
/* 2444 */                       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 4);
/* 2445 */                       if (mo == null) {
/* 2446 */                         mo = new ManagedApplication();
/* 2447 */                         _jspx_page_context.setAttribute("mo", mo, 4);
/*      */                       }
/*      */                     }
/* 2450 */                     out.write(10);
/* 2451 */                     Hashtable availabilitykeys = null;
/* 2452 */                     synchronized (application) {
/* 2453 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2454 */                       if (availabilitykeys == null) {
/* 2455 */                         availabilitykeys = new Hashtable();
/* 2456 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2459 */                     out.write(10);
/* 2460 */                     Hashtable healthkeys = null;
/* 2461 */                     synchronized (application) {
/* 2462 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2463 */                       if (healthkeys == null) {
/* 2464 */                         healthkeys = new Hashtable();
/* 2465 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2468 */                     out.write(10);
/* 2469 */                     ResourceNames resourcenames = null;
/* 2470 */                     resourcenames = (ResourceNames)_jspx_page_context.getAttribute("resourcenames", 2);
/* 2471 */                     if (resourcenames == null) {
/* 2472 */                       resourcenames = new ResourceNames();
/* 2473 */                       _jspx_page_context.setAttribute("resourcenames", resourcenames, 2);
/*      */                     }
/* 2475 */                     out.write(10);
/* 2476 */                     Hashtable motypeImage = null;
/* 2477 */                     synchronized (application) {
/* 2478 */                       motypeImage = (Hashtable)_jspx_page_context.getAttribute("motypeImage", 4);
/* 2479 */                       if (motypeImage == null) {
/* 2480 */                         motypeImage = new Hashtable();
/* 2481 */                         _jspx_page_context.setAttribute("motypeImage", motypeImage, 4);
/*      */                       }
/*      */                     }
/* 2484 */                     out.write(10);
/* 2485 */                     Hashtable motypedisplaynames = null;
/* 2486 */                     synchronized (application) {
/* 2487 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2488 */                       if (motypedisplaynames == null) {
/* 2489 */                         motypedisplaynames = new Hashtable();
/* 2490 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2493 */                     out.write("\n<script type=\"text/javascript\" src=\"../template/dnd.js\"></script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/Dialog.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/Utils.js\"></SCRIPT>\n<style>\nbody{margin-top:0px;}\n\n.forSqlDBplus{margin-top:0px;}\n\n.gnormallable {\n    color: #333333;\n    font-family: Arial,Helvetica,sans-serif;\n    font-size: 11px;\n    font-style: normal;\n    font-weight: normal;\n    text-align: left;\n}\n\n\n.floatlf {\n    float: left;\n}\n\n</style>\n<script>\n\nfunction changehealthfilter()\n{\n    var selObj = document.getElementById(\"healthfilter\");\n    var selVal = selObj.options[selObj.selectedIndex].value;\n\tSet_Cookie('plasmaview_healthfilter',selVal); //NO I18N\n\twindow.location.reload();\n}\n\nfunction init(){\n\t//alert(\"init\");\n\t//First time no cookies would have been set\n\tfor(i=1;i<4;i++){\n\t\tcheckCookie=Get_Cookie('check'+i) ;\t");
/* 2494 */                     out.write("\n\t//alert(checkCookie+\".......>check\"+i);\n\t\tif(checkCookie==null){\n\t\t\t//alert(\"init\"+i);\n\t\t\tSet_Cookie('check'+i,'true');\t");
/* 2495 */                     out.write("\n\t\t}\n\t}\n\tvar audioicon = Get_Cookie('check4');\t//NO I18N\n\tif(audioicon != null && audioicon == 'true'){\n\t\tgeneratesoundalarm('enable')\t//NO I18N\n\t}\n\tcheck_col=Get_Cookie('View_cols') ;\t");
/* 2496 */                     out.write("\n\t//alert(\"check_col\"+check_col);\n\tif(check_col==null){\n\t\tSet_Cookie('View_cols','2');\t");
/* 2497 */                     out.write("\n\t}\n\tvar check_allmon_cols = Get_Cookie('View_AllMonitor_cols');\t//NO I18N\n\tif(check_allmon_cols == null){\n\tSet_Cookie('View_AllMonitor_cols','1' ); ");
/* 2498 */                     out.write("\n\t}\n\tsetPref('plasma');\t");
/* 2499 */                     out.write("\n\n\tsetDivParams();\n}\n\nfunction setDivParams(){\n\n\t//alert(\"setDivParams\");\n\tvar cols_count = Get_Cookie('View_cols') ;\t");
/* 2500 */                     out.write("\n\tvar  table_node = document.getElementById('main_table');\n\t//alert(table_node.rows[0].cells.length);\n\tvar count =0;\n\tfor(var i=1;i<4;i++) {\n\n\t\tcheckCookie=Get_Cookie('check'+i) ;\t");
/* 2501 */                     out.write("\n\t\t//alert(\"checkCookie\"+checkCookie.checked);\n\t\tif(checkCookie!=null){\n\t\t\t// if CookieValue  = true  display tab else dont display tab\n\t\t    // Also  if CookieValue  = true  set checkbox as checked else unchecked.\n\t\t    // please note that here cookie value will be string so single quotes should be used for true or false\n\t\t    // in remaining cases it will be boolean value only\n\t\t\tif(checkCookie == 'true'){\n\t\t  \t\t  document.getElementById('DRAG_'+i).style.display='block';\n\t\t  \t\t     count ++;\n\t\t\t}\n\t\t\telse {\n\t\t\t\t document.getElementById('DRAG_'+i).style.display='none';\n\t\t\t}\n\t\t}\n\t}\n\tif(count==0){\n\t\tdocument.getElementById('notabs').style.display='block';\n\t}\n\telse{\n\n \t\tif(cols_count == '1'){\n\t\t\ttable_node.insertRow(1);\n\t\t\tvar node=table_node.rows[0].cells[0];\n\t\t\tnode.width='100%';\n\t\t\ttable_node.rows[0].removeChild(table_node.rows[0].cells[0]);\n\t\t\ttable_node.rows[1].appendChild(node);\n\t\t\tjQuery(\"#DRAG_APPEND_1\").removeAttr(\"rowspan\")\t\t// NO I18N\n\t\t}\n\t\telse if (count<3 && count != 0) {\n\t\t\tvar divsElem1= table_node.rows[0].cells[0].getElementsByTagName('div');\n");
/* 2502 */                     out.write("\t\t\tvar divsElem2= table_node.rows[0].cells[1].getElementsByTagName('div');\n\t\t\t//alert(divsElem1.length +'' +divsElem2.length);\n\t\t\tif(divsElem1.length == 2 && divsElem2[0].style.display =='none') {\n\t\t\t\ttable_node.rows[0].cells[1].appendChild(divsElem1[1]);\n\t\t\t}else {\n\t\t\t\tif(divsElem2.length == 2 && divsElem1[0].style.display =='none') {\n\t\t\t\t\ttable_node.rows[0].cells[0].appendChild(divsElem2[1]);\n\t\t\t\t}\n\t\t\t}\n\t\t\t//alert(divs_array.length + ' '+divs_array[i].style.display+divs_array[i].id);\n\t\t}\n\t}\n}\n\n\nfunction generatesoundalarm(optionvalue){\n\tif(optionvalue == 'enable'){\n\t\tvar today = new Date();\n\t\tvar expires = new Date(today.getTime() + (365 * 86400000));\n\t\tSet_Cookie('check4', true,expires);\t\t//NO I18N\t\n\t\tjQuery(\"#enableSoundAlarm\").show();\t//NO I18N\n\t\tjQuery(\"#disableSoundAlarm\").hide();\t//NO I18N\n\t}else{\n\t\tSet_Cookie('check4', false);\t//NO I18N\n\t\tjQuery(\"#disableSoundAlarm\").show();\t//NO I18N\n\t\tjQuery(\"#enableSoundAlarm\").hide();\t//NO I18N\n\t}\n}\n\n</script>\n\n<div id=\"GHOST\" style=\"background-color:transparent;position:absolute;cursor:default;cursor:default;z-index:999;visibility:hidden;filter:alpha(opacity=50);-moz-opacity:0.5;opacity: 0.5;\" class=\"bodyText\"></div>\n");
/* 2503 */                     out.write("\n");
/*      */                     
/* 2505 */                     out.println("<div style=\"width:55px\" id=\"loading\"><span class=\"bodytextboldwhite\">&nbsp;Loading...&nbsp;</span></div>");
/* 2506 */                     out.println("<div id=\"dhtmltooltip\"></div>");
/* 2507 */                     out.println("<div id=\"dhtmlpointer\"><img src=\"/images/arrow2.gif\"></div>");
/*      */                     
/* 2509 */                     out.write(10);
/* 2510 */                     out.write(10);
/*      */                     
/*      */                     try
/*      */                     {
/* 2514 */                       String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/* 2515 */                       String unmanagedResourcesCondition = " and AM_ManagedObject.resourceid NOT IN ( select resid from AM_UnManagedNodes) ";
/* 2516 */                       eumChildListCond = eumChildListCond + unmanagedResourcesCondition;
/* 2517 */                       String loggedUserName = com.manageengine.it360.sp.customermanagement.CustomerManagementAPI.getLoggedInUserName(request);
/* 2518 */                       boolean isIT360CustSpecifcMSPFlag = EnterpriseUtil.getIT360CustSpecifcMSPFlag(loggedUserName);
/* 2519 */                       String siteFilterCondn = "";
/* 2520 */                       String withGroup = "";
/* 2521 */                       if (isIT360CustSpecifcMSPFlag)
/*      */                       {
/* 2523 */                         Vector resourceids = EnterpriseUtil.filterResourceIds(request);
/* 2524 */                         siteFilterCondn = " AND " + EnterpriseUtil.getCondition("RESOURCEID", resourceids);
/*      */                       }
/*      */                       
/* 2527 */                       withGroup = " and AM_ManagedResourceType.SUBGROUP not in ('OpManager-Interface') ";
/*      */                       
/* 2529 */                       String query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  and " + eumChildListCond + " and AM_ManagedResourceType.RESOURCEGROUP in " + com.adventnet.appmanager.util.Constants.resourceGroups + " and AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + siteFilterCondn + withGroup + " group by resourcename, AM_ManagedObject.displayname, AM_ManagedObject.RESOURCEID, AM_ManagedObject.TYPE, AM_ManagedResourceType.IMAGEPATH, AM_ManagedResourceType.SHORTNAME ";
/* 2530 */                       if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2531 */                         query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  and " + eumChildListCond + " and AM_ManagedResourceType.RESOURCEGROUP='DBS' and AM_ManagedObject.TYPE='MSSQL-DB-server' " + siteFilterCondn + " group by resourcename, AM_ManagedObject.displayname, AM_ManagedObject.RESOURCEID, AM_ManagedObject.TYPE, AM_ManagedResourceType.IMAGEPATH, AM_ManagedResourceType.SHORTNAME ";
/*      */                       }
/*      */                       
/* 2534 */                       String healthfilterindex = "0";
/* 2535 */                       String allmontitle = FormatUtil.getString("am.monitortab.allmonitors.text");
/* 2536 */                       Cookie[] cookies = request.getCookies();
/* 2537 */                       for (int i = 0; i < cookies.length; i++) {
/* 2538 */                         if (cookies[i].getName().equals("plasmaview_healthfilter")) {
/* 2539 */                           if (!cookies[i].getValue().equals("Critical/Warning")) break;
/* 2540 */                           query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH,Alert.SEVERITY from AM_ManagedObject , AM_ManagedResourceType,Alert where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  and " + eumChildListCond + " and AM_ManagedResourceType.RESOURCEGROUP in " + com.adventnet.appmanager.util.Constants.resourceGroups + " and AM_ManagedObject.TYPE in " + com.adventnet.appmanager.util.Constants.resourceTypes + siteFilterCondn + withGroup + " and  Alert.source=AM_ManagedObject.resourceid and Alert.groupname='AppManager' and Alert.severity!=5 order by modtime desc";
/* 2541 */                           if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2542 */                             query = "select resourcename ,-1 , 0 , AM_ManagedResourceType.SHORTNAME ,resourceid ,AM_ManagedObject.displayname,AM_ManagedObject.RESOURCEID , AM_ManagedObject.TYPE ,AM_ManagedResourceType.IMAGEPATH,Alert.SEVERITY from AM_ManagedObject , AM_ManagedResourceType,Alert where AM_ManagedObject.type=AM_ManagedResourceType.RESOURCETYPE  and " + eumChildListCond + " and AM_ManagedResourceType.RESOURCEGROUP='DBS' and AM_ManagedObject.TYPE='MSSQL-DB-server' and  Alert.source=AM_ManagedObject.resourceid and Alert.groupname='AppManager' and Alert.severity!=5 order by modtime desc";
/*      */                           }
/* 2544 */                           healthfilterindex = "1";
/* 2545 */                           allmontitle = FormatUtil.getString("am.monitortab.plasmaview.allmonitor.critical.text"); break;
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2553 */                       ArrayList list = mo.getRows(query);
/*      */                       
/* 2555 */                       if ((com.adventnet.appmanager.struts.beans.ClientDBUtil.isPrivilegedUser(request)) && (!isIT360CustSpecifcMSPFlag))
/*      */                       {
/* 2557 */                         String owner = request.getRemoteUser();
/* 2558 */                         String MontListQry = "select distinct AM_PARENTCHILDMAPPER.CHILDID as ResourceId from AM_PARENTCHILDMAPPER,AM_HOLISTICAPPLICATION_OWNERS,AM_UserPasswordTable where AM_PARENTCHILDMAPPER.PARENTID=AM_HOLISTICAPPLICATION_OWNERS.HAID and AM_HOLISTICAPPLICATION_OWNERS.OWNERID=AM_UserPasswordTable.USERID and AM_UserPasswordTable.USERNAME='" + owner + "'";
/* 2559 */                         if (com.adventnet.appmanager.util.Constants.isSsoEnabled()) {
/* 2560 */                           String loginUserid = com.adventnet.appmanager.util.Constants.getLoginUserid(request);
/* 2561 */                           MontListQry = "select distinct AM_PARENTCHILDMAPPER.CHILDID as ResourceId from AM_PARENTCHILDMAPPER,AM_USERRESOURCESTABLE where AM_PARENTCHILDMAPPER.PARENTID=AM_USERRESOURCESTABLE.RESOURCEID and AM_USERRESOURCESTABLE.USERID=" + loginUserid;
/*      */                         }
/* 2563 */                         ArrayList oprMonList = mo.getRows(MontListQry);
/* 2564 */                         ArrayList newList = new ArrayList();
/* 2565 */                         for (int i = 0; i < oprMonList.size(); i++)
/*      */                         {
/* 2567 */                           String resID = (String)((ArrayList)oprMonList.get(i)).get(0);
/* 2568 */                           resID.trim();
/* 2569 */                           for (int j = 0; j < list.size(); j++)
/*      */                           {
/* 2571 */                             String targetID = (String)((ArrayList)list.get(j)).get(4);
/* 2572 */                             targetID.trim();
/* 2573 */                             if (resID.equals(targetID))
/*      */                             {
/* 2575 */                               newList.add((ArrayList)list.get(j));
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                         
/* 2580 */                         list = newList;
/*      */                       }
/*      */                       
/* 2583 */                       String devLink = "";
/* 2584 */                       HashMap extDeviceMap = null;
/* 2585 */                       HashMap site24x7List = null;
/* 2586 */                       if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */                       {
/* 2588 */                         extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/* 2589 */                         if (OEMUtil.isRemove("enable.proxyForExtProd"))
/*      */                         {
/* 2591 */                           extDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpManager");
/*      */                           
/* 2593 */                           HashMap opstorExtDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpStor");
/* 2594 */                           extDeviceMap.putAll(opstorExtDeviceMap);
/*      */                         }
/*      */                         
/* 2597 */                         site24x7List = DBUtil.getAllsite24x7MonitorsLink();
/*      */                       }
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/* 2603 */                       String encodeurl = URLEncoder.encode("/applications.do");
/* 2604 */                       Properties alert = getAlerts(list, availabilitykeys, healthkeys);
/* 2605 */                       request.setAttribute("alert", alert);
/* 2606 */                       getSortedMonitorList(list, alert, availabilitykeys, healthkeys);
/*      */                       
/*      */ 
/* 2609 */                       out.write("\n<title> ");
/* 2610 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 2611 */                       out.write(" </title>\n");
/* 2612 */                       if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 2613 */                         out.write("\n<body onLoad=\"init();\" onunload=\"savePref('plasma');\" >\n");
/*      */                       } else {
/* 2615 */                         out.write("\n<body onLoad=\"init();\" onunload=\"savePref('plasma');\" class=\"forSqlDBplus\" >\n");
/*      */                       }
/* 2617 */                       out.write(10);
/*      */                       
/* 2619 */                       if ((EnterpriseUtil.isAdminServer()) && (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                       {
/* 2621 */                         boolean isAnyMASDown = false;
/*      */                         
/*      */                         try
/*      */                         {
/* 2625 */                           String selectStateQuery = com.adventnet.appmanager.db.DBQueryUtil.getTopNValues("select HOST,SSLPORT,STATE from AM_MAS_SERVER where STATE !=1000 AND SERVERSTATUS=1", "1");
/* 2626 */                           AMConnectionPool.getInstance();ResultSet rs = AMConnectionPool.executeQueryStmt(selectStateQuery);
/* 2627 */                           if (rs.next())
/*      */                           {
/* 2629 */                             isAnyMASDown = true;
/*      */                           }
/* 2631 */                           AMConnectionPool.closeStatement(rs);
/*      */                         }
/*      */                         catch (Exception e)
/*      */                         {
/* 2635 */                           e.printStackTrace();
/*      */                         }
/* 2637 */                         if (isAnyMASDown)
/*      */                         {
/*      */ 
/* 2640 */                           out.write("\n\n <table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n  <tr>\n   <td width=\"5%\" align=\"left\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n   <td width=\"95%\" class=\"message\"> ");
/* 2641 */                           out.print(FormatUtil.getString("am.webclient.managedserver.down.plasmaview.error.message"));
/* 2642 */                           out.write("</td>\n  </tr>\n </table>\n <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n   <td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n  </tr>\n </table>\n");
/*      */                         } }
/* 2644 */                       out.write("\n\n<!-- Filter Added -->\n\t\t<table align=\"center\" width=\"98%\" class=\"alert-bg-tile\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n\t\t\t<tr>\n\t\t\t\t<td class=\"alert-left-corn\" align=\"left\" width=\"1%\">&nbsp;</td>\n\t\t\t\t<td align=\"left\" width=\"25%\" nowrap><strong>");
/* 2645 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.text"));
/* 2646 */                       out.write("</strong></td>\n\t\t\t\t<td align=\"right\" valign=\"middle\" width=\"5%\" nowrap>");
/* 2647 */                       out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 2648 */                       out.write(" :</td>\n\t\t\t\t<td align=\"left\" valign=\"middle\" width=\"1%\"> <img src=\"/images/alert-arrow.gif\" class=\"alarms-filter-arrow\" /></td>\n\t\t\t\t<td align=\"left\" valign=\"middle\" width=\"1%\"></td>\n\t\t  \t\t<td valign=\"middle\" align=\"left\" width=\"25%\">\n\t\t  \t\t\t  <select property=\"healthfilter\" id=\"healthfilter\"   onchange=\"javascript:changehealthfilter()\">\n\t    \t      \t\t <option value=\"All\">");
/* 2649 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.allalert.text"));
/* 2650 */                       out.write("</option>\n\t    \t      \t\t <option value=\"Critical/Warning\">");
/* 2651 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.criticalalert.text"));
/* 2652 */                       out.write("</option>\n\t    \t          </select>\n\t\t  \t\t</td>\n\t\t\t\t<td width=\"25%\" align=\"right\"><div id=\"disableSoundAlarm\" style=\"display: block;\"><a href=\"javascript:void(0)\" title=\"");
/* 2653 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.soundicon.tooltip.disable"));
/* 2654 */                       out.write("\"> <img border=\"0\" src=\"images/icon_alarm_disable.gif\" onclick=\"generatesoundalarm('enable')\"> </a></div> <div id=\"enableSoundAlarm\" style=\"display: none;\"><a title=\"");
/* 2655 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.soundicon.tooltip.enable"));
/* 2656 */                       out.write("\" href=\"javascript:void(0)\"><img border=\"0\" src=\"images/icon_alarm_enable.gif\" onclick=\"generatesoundalarm('disable')\"></a></div></td>\n\t\t  \t\t<!--  ************ customize table link added ************** -->\n\t\t  \t\t<td align=\"center\" valign=\"middle\" width=\"20%\" >\n\t\t\t\t\t<div ><img src=\"images/spacer.gif\" width=\"1\" height=\"1\"></div>\n\t\t\t\t\t<strong><a href=\"javascript:void(0)\" id=\"customize\" class=\"staticlinks\" onclick=\"showURLInDialog('/jsp/customizePlasmaView.jsp','position=relative,srcElement=customize,left=-160,top=-3,title=");
/* 2657 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.customizetab.header.text"));
/* 2658 */                       out.write("')\" >");
/* 2659 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.customizetab.text"));
/* 2660 */                       out.write("</a>\n\t\t\t\t\t</strong>\n\t\t\t\t</td>\n\t\t\t\t<!--  ************ customize table link ended ************** -->\n\n\t\t\t\t<td align=\"left\" valign=\"middle\" width=\"1%\" ></td>\n\t\t\t\t<td style=\"padding-left:6%\"width=\"25%\" valign=\"middle\">\n\t\t\t\t</td>\n\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t<td class=\"alert-right-corn\" width=\"3\">&nbsp;</td>\n\t\t\t</tr>\n\t\t</table>\n\n\t   <script>\n\t   document.getElementById(\"healthfilter\").selectedIndex = '");
/* 2661 */                       out.print(healthfilterindex);
/* 2662 */                       out.write("';\n       </script>\n\n <!-- Filter Added -->\n\n<div id=\"notabs\" style=\"display:none\">\n  \t         <br><br>\n  \t         <table width=\"99%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  \t         <tbody>\n  \t         <tr>\n  \t                 <td class=\"helpCardHdrTopLeft\"></td>\n  \t                 <td class=\"helpCardHdrTopBg\">\n  \t                         <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  \t                         <tbody>\n  \t                         <tr>\n  \t                                 <td class=\"helpCardContentBg\" valign=\"middle\" align=\"left\">\n  \t                                         <span class=\"helpHdrTxt\">Help Card</span> ");
/* 2663 */                       out.write("\n  \t                                         <img src=\"/images/helpCue.gif\" width=\"19\" align=\"texttop\" height=\"16\">\n  \t                                 </td>\n  \t                                 <td class=\"helpCardHdrRightEar\" valign=\"middle\" align=\"left\">&nbsp;</td>\n  \t                                 <td valign=\"middle\" align=\"left\">&nbsp;</td>\n  \t                         </tr>\n  \t                         </tbody>\n  \t                         </table>\n  \t                 </td>\n  \t                 <td class=\"helpCardHdrRightTop\">&nbsp;</td>\n  \t         </tr>\n  \t         <tr>\n  \t                 <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n  \t                 <td valign=\"top\">\n  \t                         <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"2\" cellspacing=\"0\">\n  \t                         <tbody>\n  \t                         <tr>\n  \t                                 <td style=\"padding-top: 10px;\" class=\"boxedContent\">\n  \t                                         <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n");
/* 2664 */                       out.write("  \t                                         <tbody>\n  \t                                         <tr>\n  \t                                                 <td>\n  \t                                                         <table width=\"100%\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n  \t                                                         <tbody>\n  \t                                                         <tr>\n  \t                                                                 <td class=\"hCardInnerTopLeft\"></td>\n  \t                                                                 <td class=\"hCardInnerTopBg\"></td>\n  \t                                                                 <td class=\"hCardInnerTopRight\"></td>\n  \t                                                         </tr>\n\n  \t                                                         <tr>\n  \t                                                                 <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n  \t                                                                 <td class=\"hCardInnerBoxBg product-help\" style=\"padding-left:30px;padding-top:5px;padding-bottom:10px\">\n");
/* 2665 */                       out.write("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
/* 2666 */                       out.print(FormatUtil.getString("am.monitortab.plasmaview.notable.text"));
/* 2667 */                       out.write("\n\t \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t     </td>\n  \t                                                                 <td class=\"hCardInnerBoxBg\">&nbsp;</td>\n  \t                                                         </tr>\n  \t                                                         </tbody>\n  \t                                                         </table>\n  \t                                                 </td>\n  \t                                         </tr>\n  \t                                         </tbody>\n  \t                                         </table>\n  \t                                 </td>\n  \t                         </tr>\n  \t                         </tbody>\n  \t                         </table>\n  \t                 </td>\n  \t                 <td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n  \t         </tr>\n  \t         <tr>\n  \t                 <td class=\"helpCardContentBg\"></td>\n  \t                 <td class=\"helpCardContentBg\" style=\"padding-left:10px;padding-top:5px;padding-bottom:10px\">\n  \t                         </td>\n");
/* 2668 */                       out.write("  \t                 <td class=\"helpCardContentBg\" ></td>\n  \t         </tr>\n  \t         </tbody>\n  \t         </table>\n  \t         &nbsp;\n  \t </div>\n\n");
/* 2669 */                       int listsize = list.size();
/* 2670 */                       Cookie[] cookies1 = request.getCookies();
/* 2671 */                       int col_cnt = 0;
/* 2672 */                       int allmonitor_colcnt = 1;
/* 2673 */                       for (int i = 0; i < cookies1.length; i++) {
/* 2674 */                         if (cookies1[i].getName().equals("View_cols")) {
/* 2675 */                           col_cnt = Integer.parseInt(cookies1[i].getValue());
/* 2676 */                         } else if (cookies1[i].getName().equals("View_AllMonitor_cols")) {
/* 2677 */                           allmonitor_colcnt = Integer.parseInt(cookies1[i].getValue());
/*      */                         }
/*      */                       }
/* 2680 */                       int trimmonitorname = 35;
/* 2681 */                       int mgtext = 30;
/* 2682 */                       if (col_cnt == 2) {
/* 2683 */                         trimmonitorname = 20;
/* 2684 */                         mgtext = 25;
/*      */                       }
/* 2686 */                       int columnWidth = 100 / allmonitor_colcnt;
/* 2687 */                       int columnHeadingWidth = columnWidth * 30 / 100;
/* 2688 */                       int plasmawidth = 50;
/* 2689 */                       if ((allmonitor_colcnt == 2) && (listsize > 5)) {
/* 2690 */                         plasmawidth = 60;
/*      */                       }
/*      */                       
/* 2693 */                       out.write("\n\n<div style=\"height:15px;\"></div>\n<table width=\"100%\" cellpadding=\"0\" border=\"0\" cellspacing=\"4\" align=\"center\" id='main_table'> <!-- class=\"lrtbdarkborder\" -->\n<tr valign=\"top\" >\n<td  width=\"");
/* 2694 */                       out.print(plasmawidth);
/* 2695 */                       out.write("%\" rowspan=2 id=\"DRAG_APPEND_1\" onmousedown = \"drag(event)\" onMouseMove=\"move(event)\" onMouseUp=\"drop(event)\">\n<!----------------------------- Display the critical monitors  --------------------------------->\n\t<div id=\"DRAG_1\" style=\"display:block;\">\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  align=\"center\" class=\"lrtbdarkborder\">\n\t\n");
/* 2696 */                       if (listsize > 5) {
/* 2697 */                         out.write("\n\t<tr >\n\t    <td  COLSPAN=\"15\" class=\"dragndroptblheading\" title=\"");
/* 2698 */                         out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 2699 */                         out.write("\"><span id=\"allmon\">");
/* 2700 */                         out.print(FormatUtil.getString("am.monitortab.allmonitors.text"));
/* 2701 */                         out.write("</span> </td>\n\t</tr>\n\t<tr class=\"bodytextbold\">\n\t");
/* 2702 */                         for (int k = 0; k < allmonitor_colcnt; k++) {
/* 2703 */                           out.write("\n\t\n\t\t<td width=\"");
/* 2704 */                           out.print(columnHeadingWidth);
/* 2705 */                           out.write("%\" class=\"columnheading\" >");
/* 2706 */                           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2707 */                           out.write("</td>\n\t\t<td width=\"");
/* 2708 */                           out.print(columnWidth * 15 / 100);
/* 2709 */                           out.write("%\" class=\"columnheading\" align=\"left\" >");
/* 2710 */                           out.print(FormatUtil.getString("Type"));
/* 2711 */                           out.write("</td>\n\t\t<td width=\"");
/* 2712 */                           out.print(columnWidth * 25 / 100);
/* 2713 */                           out.write("%\" class=\"columnheading\" align=\"left\" >");
/* 2714 */                           out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 2715 */                           out.write("</td>\n\t  \t<td width=\"");
/* 2716 */                           out.print(columnWidth * 15 / 100);
/* 2717 */                           out.write("%\"  class=\"columnheading\" align=\"center\" >");
/* 2718 */                           out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2719 */                           out.write("</td>\n\t  \t");
/* 2720 */                           if (k == allmonitor_colcnt - 1) {
/* 2721 */                             out.write("\n\t  \t<td width=\"");
/* 2722 */                             out.print(columnWidth * 10 / 100);
/* 2723 */                             out.write("%\"  class=\"columnheading\" align=\"center\" >");
/* 2724 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2725 */                             out.write("</td>\n\t  \t");
/*      */                           } else {
/* 2727 */                             out.write("\n\t  \t<td  width=\"");
/* 2728 */                             out.print(columnWidth * 10 / 100);
/* 2729 */                             out.write("%\"  style=\"border-right:1px solid #A7BFCF; padding: 1px;\"  class=\"columnheading\" align=\"center\"  >");
/* 2730 */                             out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2731 */                             out.write("</td>\n\t  \t");
/*      */                           }
/* 2733 */                           out.write("\n\n    ");
/*      */                         }
/* 2735 */                         out.write("\n    </tr>\n\t    ");
/*      */                       }
/*      */                       else {
/* 2738 */                         out.write("\n\t <tr >\n\t    <td  COLSPAN=\"15\"\tclass=\"dragndroptblheading\" title=\"");
/* 2739 */                         out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 2740 */                         out.write("\"><span id=\"allmon\">");
/* 2741 */                         out.print(FormatUtil.getString("am.monitortab.allmonitors.text"));
/* 2742 */                         out.write("</span> </td>\n\t</tr><tr>\n\t\t\n\t\n\t\t<td width=\"50%\" class=\"columnheading\" >");
/* 2743 */                         out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2744 */                         out.write("</td>\n\t\t<td width=\"");
/* 2745 */                         out.print(columnWidth * 15 / 100);
/* 2746 */                         out.write("%\" class=\"columnheading\" align=\"left\" >");
/* 2747 */                         out.print(FormatUtil.getString("Type"));
/* 2748 */                         out.write("</td>\n\t\t<td width=\"");
/* 2749 */                         out.print(columnWidth * 25 / 100);
/* 2750 */                         out.write("%\" class=\"columnheading\" align=\"left\" >");
/* 2751 */                         out.print(FormatUtil.getString("am.webclient.urlmonitor.associatedgroups.text"));
/* 2752 */                         out.write("</td>\n\t  \t<td width=\"15%\"  class=\"columnheading\" align=\"center\" >");
/* 2753 */                         out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2754 */                         out.write("</td>\n\t  \t<td width=\"10%\"   class=\"columnheading\" align=\"center\" >");
/* 2755 */                         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2756 */                         out.write("</td>\n\n    </tr>\n\t  ");
/*      */                       }
/*      */                       
/* 2759 */                       out.write("\n\t    <tr>\n\t");
/*      */                       
/* 2761 */                       int x = 1;
/* 2762 */                       for (int j = 0; (list != null) && (j < list.size()); j++)
/*      */                       {
/* 2764 */                         ArrayList row = (ArrayList)list.get(j);
/* 2765 */                         String link = (String)row.get(0);
/* 2766 */                         String monType = (String)row.get(3);
/* 2767 */                         String displayname = (String)row.get(5);
/* 2768 */                         String decDispName = ExtProdUtil.decodeString(displayname);
/* 2769 */                         String resourceid = (String)row.get(6);
/* 2770 */                         String resourceName = (String)row.get(7);
/* 2771 */                         String temp = link;
/* 2772 */                         String wsSeverity = null;
/* 2773 */                         Hashtable associatedmgs = new Hashtable();
/* 2774 */                         if ((!request.isUserInRole("ENTERPRISEADMIN")) || (!EnterpriseUtil.isManagedServer()))
/*      */                         {
/*      */ 
/* 2777 */                           associatedmgs = com.adventnet.appmanager.struts.beans.ClientDBUtil.getAssociatedMonitors(resourceid);
/*      */                         }
/*      */                         else
/*      */                         {
/* 2781 */                           associatedmgs = com.adventnet.appmanager.fault.FaultUtil.getAdminAssociatedMG(resourceid);
/*      */                         }
/*      */                         
/* 2784 */                         request.setAttribute("associatedmgs", associatedmgs);
/* 2785 */                         if (j % 2 == 0)
/*      */                         {
/* 2787 */                           wsSeverity = "class=\"whitegrayborder\"";
/*      */                         }
/*      */                         else
/*      */                         {
/* 2791 */                           wsSeverity = "class=\"yellowgrayborder\"";
/*      */                         }
/*      */                         
/* 2794 */                         String alertcount = (String)row.get(2);
/* 2795 */                         String source = (String)row.get(4);
/* 2796 */                         if (source == null)
/*      */                         {
/* 2798 */                           alertcount = "0";
/*      */                         }
/* 2800 */                         if ((extDeviceMap != null) && (extDeviceMap.containsKey(resourceid))) {
/* 2801 */                           devLink = (String)extDeviceMap.get(resourceid);
/* 2802 */                         } else if ((site24x7List != null) && (site24x7List.containsKey(resourceid)))
/*      */                         {
/* 2804 */                           devLink = URLEncoder.encode((String)site24x7List.get(resourceid));
/*      */                         }
/*      */                         else {
/* 2807 */                           devLink = "/showresource.do?resourceid=" + resourceid + "&type=" + resourceName + "&moname=" + link + "&method=showdetails&resourcename=" + displayname;
/*      */                         }
/*      */                         
/* 2810 */                         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + resourceid;
/*      */                         
/* 2812 */                         out.write("\n\n\t\t\t\t");
/*      */                         
/* 2814 */                         String class1 = "widget-links";
/* 2815 */                         String tooltip = displayname;
/* 2816 */                         if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid)))
/*      */                         {
/* 2818 */                           class1 = "disabledtext";
/* 2819 */                           tooltip = displayname + "-UnManaged";
/*      */                         }
/* 2821 */                         if (listsize > 5)
/*      */                         {
/* 2823 */                           out.write("\n\t\t\t\t\t\t\t<td colspan=\"5\" width=\"");
/* 2824 */                           out.print(columnWidth);
/* 2825 */                           out.write("%\">\n\t\t\t\t\t\t\t");
/*      */                         } else {
/* 2827 */                           out.write("\n\t\t\t\t\t\t\t<td colspan=\"5\">\n\t\t\t\t\t\t\t");
/*      */                         }
/* 2829 */                         out.write("\n\t\t\t \t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" border=\"0\">\n\t\t\t\t\t\t\t<tr  onmouseover=\"this.className='ajaxMapHeaderHover'\"  onmouseout=\"this.className='ajaxMapHeader'\">\n\t\t\t\t <td ");
/* 2830 */                         out.print(wsSeverity);
/* 2831 */                         out.write("  width=\"");
/* 2832 */                         out.print(columnHeadingWidth);
/* 2833 */                         out.write("%\"  title=\"");
/* 2834 */                         out.print(tooltip);
/* 2835 */                         out.write("\" >\n\t\t\t\n\t\t\t\t");
/* 2836 */                         if ((site24x7List != null) && (site24x7List.containsKey(resourceid))) {
/* 2837 */                           out.write("\n\t\t\t\t <a href=\"javascript:MM_openBrWindow('");
/* 2838 */                           out.print(URLEncoder.encode((String)site24x7List.get(resourceid)));
/* 2839 */                           out.write("','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class='");
/* 2840 */                           out.print(class1);
/* 2841 */                           out.write(39);
/* 2842 */                           out.write(62);
/* 2843 */                           out.write(32);
/* 2844 */                           out.print(getTrimmedText(displayname, trimmonitorname / allmonitor_colcnt));
/* 2845 */                           out.write("</a>&nbsp;&nbsp;\n\t\t\t\t");
/*      */                         } else {
/* 2847 */                           out.write("\n\n\t\t   <a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('");
/* 2848 */                           out.print(devLink);
/* 2849 */                           out.write("&viewType=");
/* 2850 */                           out.print(request.getParameter("method"));
/* 2851 */                           out.write("&PRINTER_FRIENDLY=true')\" class='");
/* 2852 */                           out.print(class1);
/* 2853 */                           out.write(39);
/* 2854 */                           out.write(62);
/* 2855 */                           out.print(getTrimmedText(decDispName, trimmonitorname / allmonitor_colcnt));
/* 2856 */                           out.write("</a>  \n\t\t\t\t");
/*      */                         }
/* 2858 */                         out.write(10);
/* 2859 */                         out.write(9);
/*      */                         
/* 2861 */                         if ((EnterpriseUtil.isAdminServer()) && 
/* 2862 */                           (Integer.parseInt(resourceid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*      */                         {
/*      */ 
/* 2865 */                           out.write("\n\t\t<a target=\"mas_window\" href=\"");
/* 2866 */                           out.print(devLink);
/* 2867 */                           out.write("&aam_jump=true&useHTTP=true\">\n\t\t\t\t");
/* 2868 */                           if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                             return;
/* 2870 */                           out.write("\n\t\t\t\t");
/*      */                           
/* 2872 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2873 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2874 */                           _jspx_th_c_005fif_005f1.setParent(null);
/*      */                           
/* 2876 */                           _jspx_th_c_005fif_005f1.setTest("${jumpIconValue=='false'}");
/* 2877 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2878 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/* 2880 */                               out.write("<img border=\"0\" title=\"");
/* 2881 */                               out.print(FormatUtil.getString("am.monitortab.plasmaview.jump.icon.title.text"));
/* 2882 */                               out.write("\" src=\"/images/jump.gif\"/>");
/* 2883 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2884 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2888 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2889 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                           }
/*      */                           
/* 2892 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2893 */                           out.write("\n\t  \t</a>\n\t\t\t\t");
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 2898 */                         out.write("\n\t         </td>\n\t         <td ");
/* 2899 */                         out.print(wsSeverity);
/* 2900 */                         out.write("  width=\"");
/* 2901 */                         out.print(columnWidth * 15 / 100);
/* 2902 */                         out.write("%\" title=\"");
/* 2903 */                         out.print(monType);
/* 2904 */                         out.write(34);
/* 2905 */                         out.write(32);
/* 2906 */                         out.write(62);
/* 2907 */                         out.print(getTrimmedText(monType, mgtext / allmonitor_colcnt));
/* 2908 */                         out.write("</td>\n\t               ");
/*      */                         
/* 2910 */                         StringBuffer mgroupBuffer = new StringBuffer();
/*      */                         try
/*      */                         {
/* 2913 */                           if (associatedmgs != null) {
/* 2914 */                             Iterator it = associatedmgs.values().iterator();
/* 2915 */                             while (it.hasNext()) {
/* 2916 */                               mgroupBuffer.append(it.next() + ", ");
/*      */                             }
/*      */                           }
/* 2919 */                           if (mgroupBuffer.length() > 2) {
/* 2920 */                             mgroupBuffer.delete(mgroupBuffer.length() - 2, mgroupBuffer.length());
/*      */                           }
/*      */                         } catch (Exception ex) {
/* 2923 */                           ex.printStackTrace();
/*      */                         }
/* 2925 */                         String mgName = mgroupBuffer.toString();
/* 2926 */                         if ((!mgName.trim().equals("")) && (EnterpriseUtil.isAdminServer()))
/*      */                         {
/* 2928 */                           mgName = mgName + "_" + CommDBUtil.getManagedServerNameWithPort(resourceid);
/*      */                         }
/*      */                         
/* 2931 */                         out.write("\n <td align=\"left\" width=\"");
/* 2932 */                         out.print(columnWidth * 25 / 100);
/* 2933 */                         out.write(37);
/* 2934 */                         out.write(34);
/* 2935 */                         out.write(32);
/* 2936 */                         out.print(wsSeverity);
/* 2937 */                         out.write(" title=\"");
/* 2938 */                         out.print(mgroupBuffer.toString());
/* 2939 */                         out.write("\">\n\n\t       ");
/* 2940 */                         out.print(getTrimmedText(mgName, mgtext / allmonitor_colcnt));
/* 2941 */                         out.write("<img src=\"../images/spacer.gif\" >\n\t      \n\t         </td>\n\t         \n\t                 ");
/*      */                         
/* 2943 */                         SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2944 */                         _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2945 */                         _jspx_th_c_005fset_005f1.setParent(null);
/*      */                         
/* 2947 */                         _jspx_th_c_005fset_005f1.setVar("key");
/* 2948 */                         int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2949 */                         if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2950 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2951 */                             out = _jspx_page_context.pushBody();
/* 2952 */                             _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2953 */                             _jspx_th_c_005fset_005f1.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2956 */                             out.write(32);
/* 2957 */                             out.print(resourceid + "#" + availabilitykeys.get(resourceName) + "#" + "MESSAGE");
/* 2958 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2959 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2962 */                           if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2963 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2966 */                         if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2967 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                         }
/*      */                         
/* 2970 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2971 */                         out.write("\n\n\t\t\t\t<td ");
/* 2972 */                         out.print(wsSeverity);
/* 2973 */                         out.write(" width=\"");
/* 2974 */                         out.print(columnWidth * 15 / 100);
/* 2975 */                         out.write("%\"  align=\"center\">\n\t\t\t\n\n<a href=\"javascript:void(0)\" ");
/* 2976 */                         if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */                           return;
/* 2978 */                         out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2979 */                         out.print(resourceid);
/* 2980 */                         out.write("&attributeid=");
/* 2981 */                         out.print(availabilitykeys.get(resourceName));
/* 2982 */                         out.write("&alertconfigurl=");
/* 2983 */                         out.print(URLEncoder.encode(thresholdurl));
/* 2984 */                         out.write("')\">");
/* 2985 */                         out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + availabilitykeys.get(resourceName))));
/* 2986 */                         out.write("</a></td> <!-- This columns gets the availability of the resource--> ");
/* 2987 */                         out.write("\n        ");
/*      */                         
/* 2989 */                         SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2990 */                         _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 2991 */                         _jspx_th_c_005fset_005f2.setParent(null);
/*      */                         
/* 2993 */                         _jspx_th_c_005fset_005f2.setVar("key");
/* 2994 */                         int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 2995 */                         if (_jspx_eval_c_005fset_005f2 != 0) {
/* 2996 */                           if (_jspx_eval_c_005fset_005f2 != 1) {
/* 2997 */                             out = _jspx_page_context.pushBody();
/* 2998 */                             _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 2999 */                             _jspx_th_c_005fset_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3002 */                             out.write(32);
/* 3003 */                             out.print(resourceid + "#" + healthkeys.get(resourceName) + "#" + "MESSAGE");
/* 3004 */                             int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3005 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3008 */                           if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3009 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3012 */                         if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3013 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                         }
/*      */                         
/* 3016 */                         this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3017 */                         out.write("\n\n\n                 ");
/* 3018 */                         if ((listsize > 5) && (x % allmonitor_colcnt != 0)) {
/* 3019 */                           out.write("\n\t\t\t\t <td ");
/* 3020 */                           out.print(wsSeverity);
/* 3021 */                           out.write(" width=\"");
/* 3022 */                           out.print(columnWidth * 10 / 100);
/* 3023 */                           out.write("%\" align=\"center\" style=\"border-right: 1px solid #A7BFCF; padding: 1px;padding-left:1px\">\n\t\t\t\t");
/*      */                         } else {
/* 3025 */                           out.write("\n\t\t\t\t<td ");
/* 3026 */                           out.print(wsSeverity);
/* 3027 */                           out.write(" width=\"");
/* 3028 */                           out.print(columnWidth * 10 / 100);
/* 3029 */                           out.write("%\" align=\"center\">\n\t\t\t\t");
/*      */                         }
/* 3031 */                         out.write("\n\n\n\n                 <a href=\"javascript:void(0)\" ");
/* 3032 */                         if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                           return;
/* 3034 */                         out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3035 */                         out.print(resourceid);
/* 3036 */                         out.write("&attributeid=");
/* 3037 */                         out.print(healthkeys.get(resourceName));
/* 3038 */                         out.write("&alertconfigurl=");
/* 3039 */                         out.print(URLEncoder.encode(thresholdurl));
/* 3040 */                         out.write("')\">");
/* 3041 */                         out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + healthkeys.get(resourceName))));
/* 3042 */                         out.write("</a></td> ");
/* 3043 */                         out.write("\n                 </td>\n\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t");
/* 3044 */                         if (listsize > 5)
/*      */                         {
/* 3046 */                           if (x % allmonitor_colcnt == 0)
/*      */                           {
/* 3048 */                             out.write("\n\n\t</tr>\n\t<tr >\n\t");
/*      */                           }
/*      */                           
/*      */                         }
/*      */                         else {
/* 3053 */                           out.write(" </tr><tr>");
/*      */                         }
/*      */                         
/* 3056 */                         x++;
/*      */                       }
/*      */                       
/* 3059 */                       if ((col_cnt == 1) && (x % allmonitor_colcnt == 0))
/*      */                       {
/* 3061 */                         out.write(9);
/* 3062 */                         out.write(10);
/* 3063 */                         out.write(9);
/*      */                       }
/*      */                       
/*      */ 
/* 3067 */                       out.write("\n\t</tr>\n\t");
/*      */                       
/* 3069 */                       if (list.isEmpty())
/*      */                       {
/*      */ 
/* 3072 */                         out.write("\n\t       <tr >\n               <td  height=\"40\" class=\"tdindent\">\n                  <span class=\"bodytext\">\n                  ");
/* 3073 */                         out.print(FormatUtil.getString("webclient.fault.eventlist.nodata"));
/* 3074 */                         out.write("\n                  </span>\n              </td>\n           </tr>\n\t");
/*      */                       }
/*      */                       
/*      */ 
/* 3078 */                       out.write("\n\t</tr>\n\t</table>\n\n\t\t <!-- Filter Added -->\n\t   \t <script>\n\t   \t\tdocument.getElementById(\"allmon\").innerHTML='");
/* 3079 */                       out.print(allmontitle);
/* 3080 */                       out.write("';\n      \t </script>\n     \t<!-- Filter Added -->\n<br>\n\t</div>\n\t</td>\n<!-----------------------------  End  Display the critical monitors  --------------------------------->\n\n<!------------------------------ Display the critical and warning alerts ------------------------------->\n<td width=\"");
/* 3081 */                       out.print(100 - plasmawidth);
/* 3082 */                       out.write("%\" id=\"DRAG_APPEND_2\" onmousedown = \"drag(event)\" onMouseMove=\"move(event)\" onMouseUp=\"drop(event)\">\n<div id=\"DRAG_2\" style=\"display:block;\">\n");
/*      */                       
/* 3084 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3085 */                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3086 */                       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                       
/* 3088 */                       _jspx_th_logic_005fnotEmpty_005f0.setName("recentAlarmsList");
/* 3089 */                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3090 */                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                         for (;;) {
/* 3092 */                           out.write("\n\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"F1F1F1\" class=\"lrtbdarkborder\" align=\"center\">\n\t<tr>\n\t<td width=\"72%\" height=\"26\" class=\"dragndroptblheading\" title=\"");
/* 3093 */                           out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 3094 */                           out.write("\">\t ");
/* 3095 */                           out.print(FormatUtil.getString("am.monitortab.plasmaview.alert.text"));
/* 3096 */                           out.write(" \t </td>\n\t</tr>\n\t</table>\n\n\t<table width=\"99%\" height=\"5\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n\t  <TR >\n\t    <TD  width=\"65%\"  class=\"columnheading\" > ");
/* 3097 */                           out.print(FormatUtil.getString("Monitors"));
/* 3098 */                           out.write("</TD>\n\t    <TD  width=\"10%\"  class=\"columnheading\" align=\"left\"> ");
/* 3099 */                           out.print(FormatUtil.getString("Type"));
/* 3100 */                           out.write("</TD>\n\t    <TD  width=\"7%\" align=\"center\"  class=\"columnheading\" > ");
/* 3101 */                           out.print(FormatUtil.getString("Status"));
/* 3102 */                           out.write("</TD>\n\t    <td  width=\"17%\" class=\"columnheading\" >");
/* 3103 */                           out.print(FormatUtil.getString("Time"));
/* 3104 */                           out.write(" </TD>\n  \t </TR>\n\t");
/* 3105 */                           int j = 0;
/* 3106 */                           out.write("\n\t  ");
/*      */                           
/* 3108 */                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.get(IterateTag.class);
/* 3109 */                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3110 */                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                           
/* 3112 */                           _jspx_th_logic_005fiterate_005f0.setName("recentAlarmsList");
/*      */                           
/* 3114 */                           _jspx_th_logic_005fiterate_005f0.setId("recentAlarm");
/*      */                           
/* 3116 */                           _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3117 */                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3118 */                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3119 */                             ArrayList recentAlarm = null;
/* 3120 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3121 */                               out = _jspx_page_context.pushBody();
/* 3122 */                               _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3123 */                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                             }
/* 3125 */                             recentAlarm = (ArrayList)_jspx_page_context.findAttribute("recentAlarm");
/*      */                             for (;;) {
/* 3127 */                               out.write(10);
/* 3128 */                               out.write(32);
/* 3129 */                               out.write(32);
/*      */                               
/* 3131 */                               String alarm = (String)recentAlarm.get(1);
/* 3132 */                               if ((alarm.trim().equals("1")) || (alarm.trim().equals("4")))
/*      */                               {
/* 3134 */                                 j++;
/* 3135 */                                 String wsSeverity = (String)recentAlarm.get(0);
/* 3136 */                                 String category = (String)recentAlarm.get(6);
/*      */                                 
/* 3138 */                                 String sourcetype = (String)recentAlarm.get(7);
/* 3139 */                                 String annotation = (String)recentAlarm.get(10);
/*      */                                 
/* 3141 */                                 String sourcename = resourcenames.getResourceName(category);
/* 3142 */                                 String resourcetype1 = resourcenames.getResourceType(category);
/* 3143 */                                 String url = null;
/*      */                                 
/* 3145 */                                 if (resourcetype1.equals("HAI"))
/*      */                                 {
/* 3147 */                                   url = "/showapplication.do?haid=" + category + "&method=showApplication&PRINTER_FRIENDLY=true";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3151 */                                   url = "/showresource.do?resourceid=" + category + "&type=" + resourcetype1 + "&moname=" + sourcename + "&method=showdetails&resourcename=" + (String)recentAlarm.get(4) + "&PRINTER_FRIENDLY=true";
/*      */                                 }
/*      */                                 
/* 3154 */                                 String resId = (String)recentAlarm.get(6);
/* 3155 */                                 if ((extDeviceMap != null) && (extDeviceMap.containsKey(resId))) {
/* 3156 */                                   url = (String)extDeviceMap.get(resId);
/*      */                                 }
/*      */                                 
/* 3159 */                                 if ((site24x7List != null) && (site24x7List.containsKey(resId))) {
/* 3160 */                                   url = (String)site24x7List.get(resId);
/*      */                                 }
/*      */                                 
/*      */ 
/* 3164 */                                 if (j % 2 == 0)
/*      */                                 {
/* 3166 */                                   wsSeverity = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3170 */                                   wsSeverity = "class=\"yellowgrayborder\"";
/*      */                                 }
/* 3172 */                                 boolean show = true;
/* 3173 */                                 if ((com.adventnet.appmanager.util.Constants.sqlManager) && 
/* 3174 */                                   (!sourcetype.equals("HAI")) && (!sourcetype.equals("MSSQL-DB-server"))) {
/* 3175 */                                   show = false;
/*      */                                 }
/*      */                                 
/* 3178 */                                 if (show)
/*      */                                 {
/* 3180 */                                   out.write("\n\n                        <TR  class=\"widget-links\" onmouseover=\"this.className='ajaxMapHeaderHover'\"  onmouseout=\"this.className='ajaxMapHeader'\">\n\n                        <TD ");
/* 3181 */                                   out.print(wsSeverity);
/* 3182 */                                   out.write(" >\n\t\t\t\t\t\t\n\t\t\t\t\t\t\n                                                <a class=\"widget-links\" title=");
/* 3183 */                                   out.print(recentAlarm.get(4));
/* 3184 */                                   out.write("  href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('");
/* 3185 */                                   out.print(url);
/* 3186 */                                   out.write("')\" > ");
/* 3187 */                                   out.print(ExtProdUtil.decodeString(getTrimmedText((String)recentAlarm.get(4), 40)));
/* 3188 */                                   out.write(" </a></TD>\n                                                \n\t\t\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                   
/* 3190 */                                   pageContext.setAttribute("type", sourcetype);
/* 3191 */                                   String ResType = (String)recentAlarm.get(9);
/* 3192 */                                   if (ResType.equals("Monitor Group"))
/*      */                                   {
/*      */ 
/* 3195 */                                     ResType = resourcenames.getGroupType(category);
/*      */                                   }
/*      */                                   
/* 3198 */                                   String alarmMessage = ExtProdUtil.decodeString((String)recentAlarm.get(3)).replace("\"", "&quot;");
/*      */                                   
/* 3200 */                                   out.write("\n\t\t\t\t\t<TD ");
/* 3201 */                                   out.print(wsSeverity);
/* 3202 */                                   out.write(" width=\"10%\" align=\"left\"> <span class=\"footer\">");
/* 3203 */                                   out.print(FormatUtil.getString(ResType));
/* 3204 */                                   out.write("</span></TD>\n                                                <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n\t\t\t\t\t\t<TD ");
/* 3205 */                                   out.print(wsSeverity);
/* 3206 */                                   out.write(" align=\"center\" width=\"5%\"><a  href=\"javascript:void(0)\" onmouseover=\"ddrivetip(this,event,'");
/* 3207 */                                   out.print(alarmMessage);
/* 3208 */                                   out.write("<br>'+v+'");
/* 3209 */                                   out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3210 */                                   out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3211 */                                   out.print(recentAlarm.get(6));
/* 3212 */                                   out.write("&attributeid=");
/* 3213 */                                   out.print(recentAlarm.get(0));
/* 3214 */                                   out.write("')\"> ");
/* 3215 */                                   out.print(getSeverityImageForHealth((String)recentAlarm.get(1)));
/* 3216 */                                   out.write(" </a>\n                                                </TD>\n\n                                              <TD ");
/* 3217 */                                   out.print(wsSeverity);
/* 3218 */                                   out.write(" align=\"left\" >");
/* 3219 */                                   out.print(formatDT((String)recentAlarm.get(5)));
/* 3220 */                                   out.write("\n                                                </TD>\n                                                                </TR>\n                                                                ");
/*      */                                 }
/*      */                               }
/*      */                               
/* 3224 */                               out.write(10);
/* 3225 */                               out.write(32);
/* 3226 */                               out.write(32);
/* 3227 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3228 */                               recentAlarm = (ArrayList)_jspx_page_context.findAttribute("recentAlarm");
/* 3229 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3232 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3233 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3236 */                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3237 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                           }
/*      */                           
/* 3240 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3241 */                           out.write("\n</table>\n");
/* 3242 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3243 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3247 */                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3248 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                       }
/*      */                       
/* 3251 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3252 */                       out.write(10);
/* 3253 */                       out.write(10);
/*      */                       
/* 3255 */                       EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3256 */                       _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 3257 */                       _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                       
/* 3259 */                       _jspx_th_logic_005fempty_005f0.setName("recentAlarmsList");
/* 3260 */                       int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 3261 */                       if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                         for (;;) {
/* 3263 */                           out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\" align=\"center\">\n\t<tr>\n\t<td height=\"26\" class=\"dragndroptblheading\" title=\"");
/* 3264 */                           out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 3265 */                           out.write(34);
/* 3266 */                           out.write(32);
/* 3267 */                           out.write(62);
/* 3268 */                           out.print(FormatUtil.getString("am.monitortab.plasmaview.alert.text"));
/* 3269 */                           out.write("</td>\n\t</tr>\n               <tr >\n                  <td  height=\"40\" class=\"tdindent\">\n                  <span class=\"bodytext\">\n                  ");
/* 3270 */                           out.print(FormatUtil.getString("am.webclient.alerttab.adminserver.noalert"));
/* 3271 */                           out.write("\n                  </span></td>\n               </tr>\n               <tr >\n                 <td class=\"tablebottom\">&nbsp;\n                  </td>\n\t\t</tr>\n\n</table>\n");
/* 3272 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 3273 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3277 */                       if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 3278 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0); return;
/*      */                       }
/*      */                       
/* 3281 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 3282 */                       out.write("\n<br>\n</div>\n</td >\n\n<!------------------ End of Display the critical and warning alerts --------------------------->\n\n<!---------------------- Display the critical Monitor groups ------------------>\n\t<td width=\"50%\" id=\"DRAG_APPEND_3\" onmousedown = \"drag(event)\" onMouseMove=\"move(event)\" onMouseUp=\"drop(event)\">\n");
/*      */                       
/* 3284 */                       ArrayList monitorGroupList = (ArrayList)request.getAttribute("monitorgrouplist");
/* 3285 */                       alert = getAlerts(monitorGroupList, availabilitykeys, healthkeys);
/* 3286 */                       getSortedMonitorList(monitorGroupList, alert, availabilitykeys, healthkeys);
/* 3287 */                       Map monitorsNosAndErrors = null;
/*      */                       
/* 3289 */                       if (isIT360CustSpecifcMSPFlag)
/*      */                       {
/* 3291 */                         monitorsNosAndErrors = com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil.getMonitorGroupsInfo(request);
/*      */                       }
/*      */                       else
/*      */                       {
/* 3295 */                         monitorsNosAndErrors = com.adventnet.appmanager.server.framework.datacollection.DataCollectionDBUtil.getMonitorGroupsInfo();
/*      */                       }
/* 3297 */                       request.setAttribute("monitorgrouplist", monitorGroupList);
/* 3298 */                       request.setAttribute("monitorsnosanderrors", monitorsNosAndErrors);
/* 3299 */                       request.setAttribute("alert", alert);
/*      */                       
/* 3301 */                       out.write("\n\t<div id=\"DRAG_3\" style=\"display:block;\">\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\" align=\"center\">\n\t    <tr>\n                  <td  width=\"99%\" colspan=\"5\" class=\"dragndroptblheading\" title=\"");
/* 3302 */                       out.print(FormatUtil.getString("am.webclient.dragdrop.titletext"));
/* 3303 */                       out.write(34);
/* 3304 */                       out.write(62);
/* 3305 */                       out.write(32);
/* 3306 */                       out.print(FormatUtil.getString("am.webclient.common.util.MGSTRs"));
/* 3307 */                       out.write("\n              </td>\n\t    </tr>\n\n\t");
/*      */                       
/* 3309 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3310 */                       _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 3311 */                       _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */                       
/* 3313 */                       _jspx_th_logic_005fnotEmpty_005f1.setName("monitorgrouplist");
/* 3314 */                       int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 3315 */                       if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                         for (;;) {
/* 3317 */                           out.write("\n\t    <tr>\n        \t<td width=\"40%\"  class=\"columnheading\">");
/* 3318 */                           out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/* 3319 */                           out.write("</td>\n\t      <td width=\"15%\" align=\"center\"  class=\"columnheading\">");
/* 3320 */                           out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.availability"));
/* 3321 */                           out.write("</td>\n\t      <td width=\"15%\" align=\"center\"  class=\"columnheading\" >");
/* 3322 */                           out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.health"));
/* 3323 */                           out.write("</td>\n\t\t <td width=\"30%\"  class=\"columnheading\" align=\"left\">");
/* 3324 */                           out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.monitorstatus"));
/* 3325 */                           out.write("</td>\n    \t   </tr>\n\t");
/* 3326 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 3327 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3331 */                       if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 3332 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                       }
/*      */                       
/* 3335 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 3336 */                       out.write("\n\n\t  ");
/*      */                       
/* 3338 */                       IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3339 */                       _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 3340 */                       _jspx_th_logic_005fiterate_005f1.setParent(null);
/*      */                       
/* 3342 */                       _jspx_th_logic_005fiterate_005f1.setName("monitorgrouplist");
/*      */                       
/* 3344 */                       _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                       
/* 3346 */                       _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/*      */                       
/* 3348 */                       _jspx_th_logic_005fiterate_005f1.setIndexId("i");
/* 3349 */                       int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 3350 */                       if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 3351 */                         ArrayList row = null;
/* 3352 */                         Integer i = null;
/* 3353 */                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3354 */                           out = _jspx_page_context.pushBody();
/* 3355 */                           _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 3356 */                           _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                         }
/* 3358 */                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3359 */                         i = (Integer)_jspx_page_context.findAttribute("i");
/*      */                         for (;;) {
/* 3361 */                           out.write("\n\t    ");
/*      */                           
/* 3363 */                           String bgcolor = null;
/* 3364 */                           if (i.intValue() % 2 == 0)
/*      */                           {
/* 3366 */                             bgcolor = "class=\"whitegrayborder\"";
/*      */                           }
/*      */                           else
/*      */                           {
/* 3370 */                             bgcolor = "class=\"yellowgrayborder\"";
/*      */                           }
/*      */                           
/* 3373 */                           out.write("\n\t<tr  class=\"widget-links\" onmouseover=\"this.className='ajaxMapHeaderHover'\"  onmouseout=\"this.className='ajaxMapHeader'\">\n        ");
/*      */                           
/* 3375 */                           int resIdTOCheck = -1;
/*      */                           try
/*      */                           {
/* 3378 */                             resIdTOCheck = Integer.parseInt((String)row.get(6));
/*      */                           }
/*      */                           catch (Exception e) {}
/*      */                           
/*      */ 
/*      */ 
/* 3384 */                           if ((EnterpriseUtil.isAdminServer()) && (resIdTOCheck > com.adventnet.appmanager.server.framework.comm.Constants.RANGE))
/*      */                           {
/*      */ 
/* 3387 */                             String groupName = row.get(1) + "_" + CommDBUtil.getManagedServerNameWithPort((String)row.get(6));
/*      */                             
/* 3389 */                             out.write("\n      <td ");
/* 3390 */                             out.print(bgcolor);
/* 3391 */                             out.write(" title=\"");
/* 3392 */                             out.print(row.get(1));
/* 3393 */                             out.write(95);
/* 3394 */                             out.print(CommDBUtil.getManagedServerNameWithPort((String)row.get(6)));
/* 3395 */                             out.write("\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showapplication.do?haid=");
/* 3396 */                             out.print(row.get(6));
/* 3397 */                             out.write("&method=showApplication&PRINTER_FRIENDLY=true')\" class=\"widget-links\">");
/* 3398 */                             out.print(getTrimmedText(groupName, 35));
/* 3399 */                             out.write("</\na></td>\n         ");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 3405 */                             out.write("\n          <td ");
/* 3406 */                             out.print(bgcolor);
/* 3407 */                             out.write(" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showapplication.do?haid=");
/* 3408 */                             out.print(row.get(6));
/* 3409 */                             out.write("&method=showApplication&PRINTER_FRIENDLY=true')\" class=\"widget-links\">");
/* 3410 */                             out.print(row.get(1));
/* 3411 */                             out.write("</a></td>\n\n         ");
/*      */                           }
/*      */                           
/*      */ 
/* 3415 */                           out.write("\n      <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n          ");
/*      */                           
/* 3417 */                           SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3418 */                           _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3419 */                           _jspx_th_c_005fset_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                           
/* 3421 */                           _jspx_th_c_005fset_005f3.setVar("key");
/* 3422 */                           int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3423 */                           if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3424 */                             if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3425 */                               out = _jspx_page_context.pushBody();
/* 3426 */                               _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3427 */                               _jspx_th_c_005fset_005f3.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3430 */                               out.write(32);
/* 3431 */                               out.print(row.get(6) + "#" + "17" + "#" + "MESSAGE");
/* 3432 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3433 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3436 */                             if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3437 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3440 */                           if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3441 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                           }
/*      */                           
/* 3444 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3445 */                           out.write("\n<td ");
/* 3446 */                           out.print(bgcolor);
/* 3447 */                           out.write(" align=\"center\"><a href=\"javascript:void(0)\" ");
/*      */                           
/* 3449 */                           IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3450 */                           _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3451 */                           _jspx_th_c_005fif_005f4.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                           
/* 3453 */                           _jspx_th_c_005fif_005f4.setTest("${alert[key]!=null}");
/* 3454 */                           int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3455 */                           if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                             for (;;) {
/* 3457 */                               out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 3458 */                               if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f4, _jspx_page_context))
/*      */                                 return;
/* 3460 */                               out.write("<br>'+v+'");
/* 3461 */                               out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3462 */                               out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3463 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3464 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3468 */                           if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3469 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                           }
/*      */                           
/* 3472 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3473 */                           out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3474 */                           out.print(row.get(6));
/* 3475 */                           out.write("&attributeid=17&alertconfigurl=");
/* 3476 */                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(6) + "&attributeIDs=17,18&attributeToSelect=17&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(6)).append("&method=showApplication").toString())));
/* 3477 */                           out.write("')\">\n      ");
/* 3478 */                           out.print(getSeverityImageForAvailability(alert.getProperty(row.get(6) + "#" + "17")));
/* 3479 */                           out.write(" </a></td>\n          ");
/*      */                           
/* 3481 */                           SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3482 */                           _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3483 */                           _jspx_th_c_005fset_005f4.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                           
/* 3485 */                           _jspx_th_c_005fset_005f4.setVar("key");
/* 3486 */                           int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3487 */                           if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3488 */                             if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3489 */                               out = _jspx_page_context.pushBody();
/* 3490 */                               _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3491 */                               _jspx_th_c_005fset_005f4.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3494 */                               out.write(32);
/* 3495 */                               out.print(row.get(6) + "#" + "18" + "#" + "MESSAGE");
/* 3496 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3497 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3500 */                             if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3501 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3504 */                           if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3505 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                           }
/*      */                           
/* 3508 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 3509 */                           out.write("\n      <script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>\n<td ");
/* 3510 */                           out.print(bgcolor);
/* 3511 */                           out.write(" align=\"center\"><a href=\"javascript:void(0)\" ");
/*      */                           
/* 3513 */                           IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3514 */                           _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3515 */                           _jspx_th_c_005fif_005f5.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                           
/* 3517 */                           _jspx_th_c_005fif_005f5.setTest("${alert[key]!=null}");
/* 3518 */                           int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3519 */                           if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                             for (;;) {
/* 3521 */                               out.write(" onmouseover=\"ddrivetip(this,event,'");
/* 3522 */                               if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f5, _jspx_page_context))
/*      */                                 return;
/* 3524 */                               out.write("<br>'+v+'");
/* 3525 */                               out.print(FormatUtil.getString("am.webclient.tooltip.text"));
/* 3526 */                               out.write("</span>',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3527 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3528 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3532 */                           if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3533 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                           }
/*      */                           
/* 3536 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3537 */                           out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3538 */                           out.print(row.get(6));
/* 3539 */                           out.write("&attributeid=18&alertconfigurl=");
/* 3540 */                           out.print(URLEncoder.encode("/jsp/ThresholdActionConfiguration.jsp?resourceid=" + row.get(6) + "&attributeIDs=17,18&attributeToSelect=18&redirectto=" + URLEncoder.encode(new StringBuilder().append("/showapplication.do?haid=").append(row.get(6)).append("&method=showApplication").toString())));
/* 3541 */                           out.write("')\">\n      ");
/* 3542 */                           out.print(getSeverityImageForHealth(alert.getProperty(row.get(6) + "#" + "18")));
/* 3543 */                           out.write("</a></td>\n\n      ");
/*      */                           
/* 3545 */                           Map currMGInfo = (Map)monitorsNosAndErrors.get(row.get(6));
/* 3546 */                           String noOfMons = "";
/* 3547 */                           String monsInErr = "";
/*      */                           
/* 3549 */                           if (currMGInfo != null) {
/* 3550 */                             noOfMons = (String)currMGInfo.get("TOTALCHILDCOUNT");
/* 3551 */                             if ((noOfMons != null) && (noOfMons.equals("0"))) {
/* 3552 */                               monsInErr = "0";
/*      */                             } else {
/* 3554 */                               monsInErr = " " + (String)currMGInfo.get("CHILDRENINERROR") + "/";
/*      */                               
/* 3556 */                               if (monsInErr.equals(" None/"))
/*      */                               {
/* 3558 */                                 monsInErr = "0/";
/*      */                               }
/* 3560 */                               monsInErr = monsInErr + noOfMons + " " + FormatUtil.getString("am.webclient.hometab.inerror.text");
/*      */                             }
/*      */                           }
/*      */                           
/* 3564 */                           out.write("\n\t<td ");
/* 3565 */                           out.print(bgcolor);
/* 3566 */                           out.write(" align=\"left\">\n\t      ");
/* 3567 */                           out.print(monsInErr);
/* 3568 */                           out.write("  </td>\n\t</tr>\n    ");
/* 3569 */                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 3570 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3571 */                           i = (Integer)_jspx_page_context.findAttribute("i");
/* 3572 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3575 */                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 3576 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3579 */                       if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 3580 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                       }
/*      */                       
/* 3583 */                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 3584 */                       out.write(10);
/*      */                       
/* 3586 */                       EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 3587 */                       _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 3588 */                       _jspx_th_logic_005fempty_005f1.setParent(null);
/*      */                       
/* 3590 */                       _jspx_th_logic_005fempty_005f1.setName("monitorgrouplist");
/* 3591 */                       int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 3592 */                       if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                         for (;;) {
/* 3594 */                           out.write("\n\n<tr  class=\"widget-links\" onmouseover=\"this.className='ajaxMapHeaderHover'\"  onmouseout=\"this.className='ajaxMapHeader'\">\n      <td  height=\"40\">&nbsp;</td>\n      ");
/*      */                           
/* 3596 */                           PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3597 */                           _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3598 */                           _jspx_th_logic_005fpresent_005f0.setParent(_jspx_th_logic_005fempty_005f1);
/*      */                           
/* 3600 */                           _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 3601 */                           int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3602 */                           if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                             for (;;) {
/* 3604 */                               out.write("\n      <td  class=\"bodytext\">\n\t\t");
/*      */                               
/* 3606 */                               ArrayList applications1 = com.adventnet.appmanager.struts.beans.AlarmUtil.getConfiguredMonitorGroups();
/* 3607 */                               if (applications1.size() > 0)
/*      */                               {
/*      */ 
/* 3610 */                                 out.write("\n \t\t");
/* 3611 */                                 out.print(FormatUtil.getString("am.webclient.alerttab.nodatamessageview.text"));
/* 3612 */                                 out.write("\n      ");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 3617 */                                 String url = "/jsp/CreateApplication.jsp";
/* 3618 */                                 if (com.adventnet.appmanager.util.Constants.sqlManager) {
/* 3619 */                                   url = "/admin/createapplication.do?method=createapp&grouptype=1";
/*      */                                 }
/*      */                                 
/* 3622 */                                 out.write("\n\t   ");
/* 3623 */                                 out.print(FormatUtil.getString("am.webclient.hometab.nomgmessage.text", new String[] { MGSTRs }));
/* 3624 */                                 out.write(32);
/* 3625 */                                 out.write(32);
/*      */                                 
/* 3627 */                                 AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3628 */                                 _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3629 */                                 _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_logic_005fpresent_005f0);
/*      */                                 
/* 3631 */                                 _jspx_th_am_005fadminlink_005f0.setHref(url);
/*      */                                 
/* 3633 */                                 _jspx_th_am_005fadminlink_005f0.setEnableClass("widget-links");
/* 3634 */                                 int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3635 */                                 if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3636 */                                   if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3637 */                                     out = _jspx_page_context.pushBody();
/* 3638 */                                     _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3639 */                                     _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3642 */                                     out.write(60);
/* 3643 */                                     out.write(73);
/* 3644 */                                     out.write(62);
/* 3645 */                                     out.print(FormatUtil.getString("am.webclient.associatemonitors.new"));
/* 3646 */                                     out.write(32);
/* 3647 */                                     out.print(MGSTR);
/* 3648 */                                     out.write("\n  \t                 </I>");
/* 3649 */                                     int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3650 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3653 */                                   if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3654 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3657 */                                 if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3658 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                 }
/*      */                                 
/* 3661 */                                 this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3662 */                                 out.write(32);
/* 3663 */                                 out.print(FormatUtil.getString("am.webclient.hometab.linkmessage.text"));
/* 3664 */                                 out.write("\n  \t   ");
/*      */                               }
/* 3666 */                               out.write("\n      ");
/* 3667 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3668 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3672 */                           if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3673 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0); return;
/*      */                           }
/*      */                           
/* 3676 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3677 */                           out.write("\n          ");
/*      */                           
/* 3679 */                           if (!EnterpriseUtil.isAdminServer())
/*      */                           {
/* 3681 */                             out.write("\n      ");
/* 3682 */                             if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_th_logic_005fempty_005f1, _jspx_page_context))
/*      */                               return;
/* 3684 */                             out.write("\n      </td>\n          ");
/*      */                           }
/*      */                           else
/*      */                           {
/* 3688 */                             out.write("\n           <td  class=\"bodytext\">\n           ");
/* 3689 */                             if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fempty_005f1, _jspx_page_context))
/*      */                               return;
/* 3691 */                             out.write("\n           </td>\n           ");
/*      */                           }
/* 3693 */                           out.write("\n</tr>\n");
/* 3694 */                           int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 3695 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3699 */                       if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 3700 */                         this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                       }
/*      */                       
/* 3703 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 3704 */                       out.write("\n</table> \n<br>\n</div>\n<!-- End ... Display the critical Monitor gr... -->\n</td>\n</tr>\n</table>\n");
/* 3705 */                       if ((OEMUtil.getOEMString("product.name") != null) && (OEMUtil.getOEMString("product.name").equals("IT360"))) {
/* 3706 */                         out.write(10);
/* 3707 */                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/footer.jsp", out, false);
/* 3708 */                         out.write(10);
/* 3709 */                       } } catch (Exception e) { System.out.println("ERROR in PlasmaView.jsp:" + e.getMessage()); }
/* 3710 */                     out.write(10);
/* 3711 */                     out.write(10);
/*      */                     
/* 3713 */                     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3714 */                     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3715 */                     _jspx_th_c_005fif_005f6.setParent(null);
/*      */                     
/* 3717 */                     _jspx_th_c_005fif_005f6.setTest("${soundalarm ne null && soundalarm == true && alarmSound ne \"\"}");
/* 3718 */                     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3719 */                     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                       for (;;) {
/* 3721 */                         out.write("\n\n<audio id=\"alarmSound\"  preload=\"auto\" >\t\t\t");
/* 3722 */                         out.write("\n<source src=\"");
/* 3723 */                         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                           return;
/* 3725 */                         out.write(".wav\" type=\"audio/wav\" />\t");
/* 3726 */                         out.write("\n</audio>\t");
/* 3727 */                         out.write("\n\n<embed  id=\"ieplayer\" style=\"display: none;\" src=\"");
/* 3728 */                         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f6, _jspx_page_context))
/*      */                           return;
/* 3730 */                         out.write(".mp3\" hidden=\"true\" type=\"audio/mpeg\">\t");
/* 3731 */                         out.write("\n\n<script>\n  $(document).ready(function(){\n\t  var obj = ");
/* 3732 */                         out.print(request.getAttribute("soundalarm"));
/* 3733 */                         out.write("\n\t  if(obj ){\n\t\t$(\"#alarmSound\").attr('autoplay',true);\t// NO I18N\n\t  }\n  });\n</script>\n");
/* 3734 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3735 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3739 */                     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3740 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/*      */                     }
/*      */                     else {
/* 3743 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3744 */                       out.write("\t\n\n</body>\n");
/*      */                     }
/* 3746 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3747 */         out = _jspx_out;
/* 3748 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3749 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3750 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3753 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3759 */     PageContext pageContext = _jspx_page_context;
/* 3760 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3762 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3763 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3764 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 3766 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3768 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3769 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3770 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3771 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3772 */       return true;
/*      */     }
/* 3774 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3775 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3780 */     PageContext pageContext = _jspx_page_context;
/* 3781 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3783 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3784 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3785 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3787 */     _jspx_th_c_005fif_005f0.setTest("${!empty reloadperiod}");
/* 3788 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3789 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3791 */         out.write("\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"");
/* 3792 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3793 */           return true;
/* 3794 */         out.write(34);
/* 3795 */         out.write(62);
/* 3796 */         out.write(10);
/* 3797 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3798 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3802 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3803 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3804 */       return true;
/*      */     }
/* 3806 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3812 */     PageContext pageContext = _jspx_page_context;
/* 3813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3815 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3816 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3817 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3819 */     _jspx_th_c_005fout_005f1.setValue("${reloadperiod}");
/* 3820 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3821 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3822 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3823 */       return true;
/*      */     }
/* 3825 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3826 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3831 */     PageContext pageContext = _jspx_page_context;
/* 3832 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3834 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 3835 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3836 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 3838 */     _jspx_th_c_005fset_005f0.setVar("jumpIconValue");
/*      */     
/* 3840 */     _jspx_th_c_005fset_005f0.setValue("${requestScope[\"jumpIcon\"]}");
/* 3841 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3842 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3843 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3844 */       return true;
/*      */     }
/* 3846 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3847 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3852 */     PageContext pageContext = _jspx_page_context;
/* 3853 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3855 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3856 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3857 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 3859 */     _jspx_th_c_005fif_005f2.setTest("${alert[key]!=null}");
/* 3860 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3861 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3863 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3864 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3865 */           return true;
/* 3866 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3867 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3868 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3872 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3873 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3874 */       return true;
/*      */     }
/* 3876 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3882 */     PageContext pageContext = _jspx_page_context;
/* 3883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3885 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3886 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3887 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3889 */     _jspx_th_c_005fout_005f2.setValue("${alert[key]}");
/* 3890 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3891 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3892 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3893 */       return true;
/*      */     }
/* 3895 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3901 */     PageContext pageContext = _jspx_page_context;
/* 3902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3904 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3905 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3906 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 3908 */     _jspx_th_c_005fif_005f3.setTest("${alert[key]!=null}");
/* 3909 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3910 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3912 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3913 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3914 */           return true;
/* 3915 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3916 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3917 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3921 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3922 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3923 */       return true;
/*      */     }
/* 3925 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3926 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3931 */     PageContext pageContext = _jspx_page_context;
/* 3932 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3934 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3935 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3936 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3938 */     _jspx_th_c_005fout_005f3.setValue("${alert[key]}");
/* 3939 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3940 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3941 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3942 */       return true;
/*      */     }
/* 3944 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3945 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3950 */     PageContext pageContext = _jspx_page_context;
/* 3951 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3953 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3954 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3955 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3957 */     _jspx_th_c_005fout_005f4.setValue("${alert[key]}");
/* 3958 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3959 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3960 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3961 */       return true;
/*      */     }
/* 3963 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3964 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3969 */     PageContext pageContext = _jspx_page_context;
/* 3970 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3972 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3973 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3974 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3976 */     _jspx_th_c_005fout_005f5.setValue("${alert[key]}");
/* 3977 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3978 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3979 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3980 */       return true;
/*      */     }
/* 3982 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3983 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(JspTag _jspx_th_logic_005fempty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3988 */     PageContext pageContext = _jspx_page_context;
/* 3989 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3991 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3992 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 3993 */     _jspx_th_logic_005fnotPresent_005f0.setParent((Tag)_jspx_th_logic_005fempty_005f1);
/*      */     
/* 3995 */     _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN");
/* 3996 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 3997 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 3999 */         out.write("\n      <td  class=\"bodytext\">\n      ");
/* 4000 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 4001 */           return true;
/* 4002 */         out.write("\n      ");
/* 4003 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 4004 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4008 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 4009 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4010 */       return true;
/*      */     }
/* 4012 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 4013 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4018 */     PageContext pageContext = _jspx_page_context;
/* 4019 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4021 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4022 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4023 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 4024 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4025 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 4026 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4027 */         out = _jspx_page_context.pushBody();
/* 4028 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 4029 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4032 */         out.write("operator.nobuisness.applications");
/* 4033 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 4034 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4037 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4038 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4041 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4042 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4043 */       return true;
/*      */     }
/* 4045 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fempty_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4051 */     PageContext pageContext = _jspx_page_context;
/* 4052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4054 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4055 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4056 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fempty_005f1);
/* 4057 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4058 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 4059 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4060 */         out = _jspx_page_context.pushBody();
/* 4061 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 4062 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4065 */         out.write("operator.nobuisness.applications.adminserver");
/* 4066 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 4067 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4070 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4071 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4074 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4075 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4076 */       return true;
/*      */     }
/* 4078 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4084 */     PageContext pageContext = _jspx_page_context;
/* 4085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4087 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4088 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 4089 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4091 */     _jspx_th_c_005fout_005f6.setValue("${alarmSound}");
/* 4092 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 4093 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 4094 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4095 */       return true;
/*      */     }
/* 4097 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 4098 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4103 */     PageContext pageContext = _jspx_page_context;
/* 4104 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4106 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4107 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 4108 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 4110 */     _jspx_th_c_005fout_005f7.setValue("${alarmSound}");
/* 4111 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 4112 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 4113 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4114 */       return true;
/*      */     }
/* 4116 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 4117 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\PlasmaView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */