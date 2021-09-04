/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.server.framework.confparser.PreConfMonitorXMLParser;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.ExtProdUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.me.apm.cmdb.APMHelpDeskUtil.CIUrl;
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
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
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
/*      */ public final class displayresources_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   57 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   60 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   61 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   62 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   69 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   74 */     ArrayList list = null;
/*   75 */     StringBuffer sbf = new StringBuffer();
/*   76 */     ManagedApplication mo = new ManagedApplication();
/*   77 */     if (distinct)
/*      */     {
/*   79 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   83 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   86 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   88 */       ArrayList row = (ArrayList)list.get(i);
/*   89 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   90 */       if (distinct) {
/*   91 */         sbf.append(row.get(0));
/*      */       } else
/*   93 */         sbf.append(row.get(1));
/*   94 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   97 */     return sbf.toString(); }
/*      */   
/*   99 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  102 */     if (severity == null)
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  106 */     if (severity.equals("5"))
/*      */     {
/*  108 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  110 */     if (severity.equals("1"))
/*      */     {
/*  112 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  117 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  124 */     if (severity == null)
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("1"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  132 */     if (severity.equals("4"))
/*      */     {
/*  134 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  136 */     if (severity.equals("5"))
/*      */     {
/*  138 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  143 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  149 */     if (severity == null)
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  153 */     if (severity.equals("5"))
/*      */     {
/*  155 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  157 */     if (severity.equals("1"))
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  163 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  169 */     if (severity == null)
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  173 */     if (severity.equals("1"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  177 */     if (severity.equals("4"))
/*      */     {
/*  179 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  181 */     if (severity.equals("5"))
/*      */     {
/*  183 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  187 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  193 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  199 */     if (severity == 5)
/*      */     {
/*  201 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  203 */     if (severity == 1)
/*      */     {
/*  205 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  210 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  216 */     if (severity == null)
/*      */     {
/*  218 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  220 */     if (severity.equals("5"))
/*      */     {
/*  222 */       if (isAvailability) {
/*  223 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  226 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  229 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  231 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  233 */     if (severity.equals("1"))
/*      */     {
/*  235 */       if (isAvailability) {
/*  236 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  239 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  246 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  253 */     if (severity == null)
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("5"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  261 */     if (severity.equals("4"))
/*      */     {
/*  263 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  265 */     if (severity.equals("1"))
/*      */     {
/*  267 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  272 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  278 */     if (severity == null)
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("5"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  286 */     if (severity.equals("4"))
/*      */     {
/*  288 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  290 */     if (severity.equals("1"))
/*      */     {
/*  292 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  297 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  304 */     if (severity == null)
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  308 */     if (severity.equals("5"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  312 */     if (severity.equals("4"))
/*      */     {
/*  314 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  316 */     if (severity.equals("1"))
/*      */     {
/*  318 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  323 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  331 */     StringBuffer out = new StringBuffer();
/*  332 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  333 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  334 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  335 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  336 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  337 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  338 */     out.append("</tr>");
/*  339 */     out.append("</form></table>");
/*  340 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  347 */     if (val == null)
/*      */     {
/*  349 */       return "-";
/*      */     }
/*      */     
/*  352 */     String ret = FormatUtil.formatNumber(val);
/*  353 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  354 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  357 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  361 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  369 */     StringBuffer out = new StringBuffer();
/*  370 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  371 */     out.append("<tr>");
/*  372 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  374 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  376 */     out.append("</tr>");
/*  377 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  381 */       if (j % 2 == 0)
/*      */       {
/*  383 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  387 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  390 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  392 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  395 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  399 */       out.append("</tr>");
/*      */     }
/*  401 */     out.append("</table>");
/*  402 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  403 */     out.append("<tr>");
/*  404 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  405 */     out.append("</tr>");
/*  406 */     out.append("</table>");
/*  407 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  413 */     StringBuffer out = new StringBuffer();
/*  414 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  415 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  416 */     out.append("<tr>");
/*  417 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  418 */     out.append("<tr>");
/*  419 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  420 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  421 */     out.append("</tr>");
/*  422 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  425 */       out.append("<tr>");
/*  426 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  427 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  428 */       out.append("</tr>");
/*      */     }
/*      */     
/*  431 */     out.append("</table>");
/*  432 */     out.append("</table>");
/*  433 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  438 */     if (severity.equals("0"))
/*      */     {
/*  440 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  444 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  451 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  464 */     StringBuffer out = new StringBuffer();
/*  465 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  466 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  468 */       out.append("<tr>");
/*  469 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  470 */       out.append("</tr>");
/*      */       
/*      */ 
/*  473 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  475 */         String borderclass = "";
/*      */         
/*      */ 
/*  478 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  480 */         out.append("<tr>");
/*      */         
/*  482 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  483 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  484 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  490 */     out.append("</table><br>");
/*  491 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  492 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  494 */       List sLinks = secondLevelOfLinks[0];
/*  495 */       List sText = secondLevelOfLinks[1];
/*  496 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  499 */         out.append("<tr>");
/*  500 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  501 */         out.append("</tr>");
/*  502 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  504 */           String borderclass = "";
/*      */           
/*      */ 
/*  507 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  509 */           out.append("<tr>");
/*      */           
/*  511 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  512 */           if (sLinks.get(i).toString().length() == 0) {
/*  513 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  516 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  518 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  522 */     out.append("</table>");
/*  523 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  530 */     StringBuffer out = new StringBuffer();
/*  531 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  532 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  534 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  536 */         out.append("<tr>");
/*  537 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  538 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  542 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  544 */           String borderclass = "";
/*      */           
/*      */ 
/*  547 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  549 */           out.append("<tr>");
/*      */           
/*  551 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  552 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  553 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  556 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  559 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  564 */     out.append("</table><br>");
/*  565 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  566 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  568 */       List sLinks = secondLevelOfLinks[0];
/*  569 */       List sText = secondLevelOfLinks[1];
/*  570 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  573 */         out.append("<tr>");
/*  574 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  575 */         out.append("</tr>");
/*  576 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  578 */           String borderclass = "";
/*      */           
/*      */ 
/*  581 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  583 */           out.append("<tr>");
/*      */           
/*  585 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  586 */           if (sLinks.get(i).toString().length() == 0) {
/*  587 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  590 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  592 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  596 */     out.append("</table>");
/*  597 */     return out.toString();
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
/*  610 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  613 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  616 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  619 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  622 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  625 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  628 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  631 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  639 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  644 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  649 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  654 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  659 */     if (val != null)
/*      */     {
/*  661 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  665 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  670 */     if (val == null) {
/*  671 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  675 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  680 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  686 */     if (val != null)
/*      */     {
/*  688 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  692 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  698 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  703 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  707 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  712 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  717 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  722 */     String hostaddress = "";
/*  723 */     String ip = request.getHeader("x-forwarded-for");
/*  724 */     if (ip == null)
/*  725 */       ip = request.getRemoteAddr();
/*  726 */     InetAddress add = null;
/*  727 */     if (ip.equals("127.0.0.1")) {
/*  728 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  732 */       add = InetAddress.getByName(ip);
/*      */     }
/*  734 */     hostaddress = add.getHostName();
/*  735 */     if (hostaddress.indexOf('.') != -1) {
/*  736 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  737 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  741 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  746 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  752 */     if (severity == null)
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  756 */     if (severity.equals("5"))
/*      */     {
/*  758 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  760 */     if (severity.equals("1"))
/*      */     {
/*  762 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  767 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  772 */     ResultSet set = null;
/*  773 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  774 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  776 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  777 */       if (set.next()) { String str1;
/*  778 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  779 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  782 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  787 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  790 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  792 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  796 */     StringBuffer rca = new StringBuffer();
/*  797 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  798 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  801 */     int rcalength = key.length();
/*  802 */     String split = "6. ";
/*  803 */     int splitPresent = key.indexOf(split);
/*  804 */     String div1 = "";String div2 = "";
/*  805 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  807 */       if (rcalength > 180) {
/*  808 */         rca.append("<span class=\"rca-critical-text\">");
/*  809 */         getRCATrimmedText(key, rca);
/*  810 */         rca.append("</span>");
/*      */       } else {
/*  812 */         rca.append("<span class=\"rca-critical-text\">");
/*  813 */         rca.append(key);
/*  814 */         rca.append("</span>");
/*      */       }
/*  816 */       return rca.toString();
/*      */     }
/*  818 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  819 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  820 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  821 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  822 */     getRCATrimmedText(div1, rca);
/*  823 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  826 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  827 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  828 */     getRCATrimmedText(div2, rca);
/*  829 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  831 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  836 */     String[] st = msg.split("<br>");
/*  837 */     for (int i = 0; i < st.length; i++) {
/*  838 */       String s = st[i];
/*  839 */       if (s.length() > 180) {
/*  840 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  842 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  846 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  847 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  849 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  853 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  854 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  855 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  858 */       if (key == null) {
/*  859 */         return ret;
/*      */       }
/*      */       
/*  862 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  863 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  866 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  867 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  868 */       set = AMConnectionPool.executeQueryStmt(query);
/*  869 */       if (set.next())
/*      */       {
/*  871 */         String helpLink = set.getString("LINK");
/*  872 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  875 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  881 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  900 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  891 */         if (set != null) {
/*  892 */           AMConnectionPool.closeStatement(set);
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
/*  906 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  907 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  909 */       String entityStr = (String)keys.nextElement();
/*  910 */       String mmessage = temp.getProperty(entityStr);
/*  911 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  912 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  914 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  920 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  921 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  923 */       String entityStr = (String)keys.nextElement();
/*  924 */       String mmessage = temp.getProperty(entityStr);
/*  925 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  926 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  928 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  933 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  943 */     String des = new String();
/*  944 */     while (str.indexOf(find) != -1) {
/*  945 */       des = des + str.substring(0, str.indexOf(find));
/*  946 */       des = des + replace;
/*  947 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  949 */     des = des + str;
/*  950 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  957 */       if (alert == null)
/*      */       {
/*  959 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  961 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  963 */         return "&nbsp;";
/*      */       }
/*      */       
/*  966 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  968 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  971 */       int rcalength = test.length();
/*  972 */       if (rcalength < 300)
/*      */       {
/*  974 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  978 */       StringBuffer out = new StringBuffer();
/*  979 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  980 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  981 */       out.append("</div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  983 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  984 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  989 */       ex.printStackTrace();
/*      */     }
/*  991 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  997 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1002 */     ArrayList attribIDs = new ArrayList();
/* 1003 */     ArrayList resIDs = new ArrayList();
/* 1004 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1006 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1008 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1010 */       String resourceid = "";
/* 1011 */       String resourceType = "";
/* 1012 */       if (type == 2) {
/* 1013 */         resourceid = (String)row.get(0);
/* 1014 */         resourceType = (String)row.get(3);
/*      */       }
/* 1016 */       else if (type == 3) {
/* 1017 */         resourceid = (String)row.get(0);
/* 1018 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1021 */         resourceid = (String)row.get(6);
/* 1022 */         resourceType = (String)row.get(7);
/*      */       }
/* 1024 */       resIDs.add(resourceid);
/* 1025 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1026 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1028 */       String healthentity = null;
/* 1029 */       String availentity = null;
/* 1030 */       if (healthid != null) {
/* 1031 */         healthentity = resourceid + "_" + healthid;
/* 1032 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1035 */       if (availid != null) {
/* 1036 */         availentity = resourceid + "_" + availid;
/* 1037 */         entitylist.add(availentity);
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
/* 1051 */     Properties alert = getStatus(entitylist);
/* 1052 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1057 */     int size = monitorList.size();
/*      */     
/* 1059 */     String[] severity = new String[size];
/*      */     
/* 1061 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1063 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1064 */       String resourceName1 = (String)row1.get(7);
/* 1065 */       String resourceid1 = (String)row1.get(6);
/* 1066 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1067 */       if (severity[j] == null)
/*      */       {
/* 1069 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1073 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1075 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1077 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1080 */         if (sev > 0) {
/* 1081 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1082 */           monitorList.set(k, monitorList.get(j));
/* 1083 */           monitorList.set(j, t);
/* 1084 */           String temp = severity[k];
/* 1085 */           severity[k] = severity[j];
/* 1086 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1092 */     int z = 0;
/* 1093 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1096 */       int i = 0;
/* 1097 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1100 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1104 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1108 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1110 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1113 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1117 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1120 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1121 */       String resourceName1 = (String)row1.get(7);
/* 1122 */       String resourceid1 = (String)row1.get(6);
/* 1123 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1124 */       if (hseverity[j] == null)
/*      */       {
/* 1126 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1131 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1133 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1136 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1139 */         if (hsev > 0) {
/* 1140 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1141 */           monitorList.set(k, monitorList.get(j));
/* 1142 */           monitorList.set(j, t);
/* 1143 */           String temp1 = hseverity[k];
/* 1144 */           hseverity[k] = hseverity[j];
/* 1145 */           hseverity[j] = temp1;
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
/* 1157 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1158 */     boolean forInventory = false;
/* 1159 */     String trdisplay = "none";
/* 1160 */     String plusstyle = "inline";
/* 1161 */     String minusstyle = "none";
/* 1162 */     String haidTopLevel = "";
/* 1163 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1165 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1167 */         haidTopLevel = request.getParameter("haid");
/* 1168 */         forInventory = true;
/* 1169 */         trdisplay = "table-row;";
/* 1170 */         plusstyle = "none";
/* 1171 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1178 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1181 */     ArrayList listtoreturn = new ArrayList();
/* 1182 */     StringBuffer toreturn = new StringBuffer();
/* 1183 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1184 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1185 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1187 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1189 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1190 */       String childresid = (String)singlerow.get(0);
/* 1191 */       String childresname = (String)singlerow.get(1);
/* 1192 */       childresname = ExtProdUtil.decodeString(childresname);
/* 1193 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1194 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1195 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1196 */       String unmanagestatus = (String)singlerow.get(5);
/* 1197 */       String actionstatus = (String)singlerow.get(6);
/* 1198 */       String linkclass = "monitorgp-links";
/* 1199 */       String titleforres = childresname;
/* 1200 */       String titilechildresname = childresname;
/* 1201 */       String childimg = "/images/trcont.png";
/* 1202 */       String flag = "enable";
/* 1203 */       String dcstarted = (String)singlerow.get(8);
/* 1204 */       String configMonitor = "";
/* 1205 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1206 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1208 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1210 */       if (singlerow.get(7) != null)
/*      */       {
/* 1212 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1214 */       String haiGroupType = "0";
/* 1215 */       if ("HAI".equals(childtype))
/*      */       {
/* 1217 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1219 */       childimg = "/images/trend.png";
/* 1220 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1221 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1222 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1224 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1226 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1228 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1229 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1232 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1234 */         linkclass = "disabledtext";
/* 1235 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1237 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1238 */       String availmouseover = "";
/* 1239 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1241 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1243 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1244 */       String healthmouseover = "";
/* 1245 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1247 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1250 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1251 */       int spacing = 0;
/* 1252 */       if (level >= 1)
/*      */       {
/* 1254 */         spacing = 40 * level;
/*      */       }
/* 1256 */       if (childtype.equals("HAI"))
/*      */       {
/* 1258 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1259 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1260 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1262 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1263 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1264 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1265 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1266 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1267 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1268 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1269 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1270 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1271 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1272 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1274 */         if (!forInventory)
/*      */         {
/* 1276 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1279 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1281 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1283 */           actions = editlink + actions;
/*      */         }
/* 1285 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1287 */           actions = actions + associatelink;
/*      */         }
/* 1289 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1290 */         String arrowimg = "";
/* 1291 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1293 */           actions = "";
/* 1294 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1295 */           checkbox = "";
/* 1296 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1298 */         if (isIt360)
/*      */         {
/* 1300 */           actionimg = "";
/* 1301 */           actions = "";
/* 1302 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1303 */           checkbox = "";
/*      */         }
/*      */         
/* 1306 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1308 */           actions = "";
/*      */         }
/* 1310 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1312 */           checkbox = "";
/*      */         }
/*      */         
/* 1315 */         String resourcelink = "";
/*      */         
/* 1317 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1319 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1323 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1326 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1332 */         if (!isIt360)
/*      */         {
/* 1334 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1338 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1341 */         toreturn.append("</tr>");
/* 1342 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1344 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1345 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1349 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1350 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1353 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1357 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1359 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1360 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1361 */             toreturn.append(assocMessage);
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1371 */         String resourcelink = null;
/* 1372 */         boolean hideEditLink = false;
/* 1373 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1375 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1376 */           hideEditLink = true;
/* 1377 */           if (isIt360)
/*      */           {
/* 1379 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1383 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1385 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1387 */           hideEditLink = true;
/* 1388 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1389 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1394 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1397 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1398 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1399 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1400 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1401 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1402 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1403 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1404 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1405 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1406 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1407 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1408 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1409 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1411 */         if (hideEditLink)
/*      */         {
/* 1413 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1415 */         if (!forInventory)
/*      */         {
/* 1417 */           removefromgroup = "";
/*      */         }
/* 1419 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1420 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1421 */           actions = actions + configcustomfields;
/*      */         }
/* 1423 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1425 */           actions = editlink + actions;
/*      */         }
/* 1427 */         String managedLink = "";
/* 1428 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1430 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1431 */           actions = "";
/* 1432 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1433 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1436 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1438 */           checkbox = "";
/*      */         }
/*      */         
/* 1441 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1443 */           actions = "";
/*      */         }
/* 1445 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1448 */         if (isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1457 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1458 */         if (!isIt360)
/*      */         {
/* 1460 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1464 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1466 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1469 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1476 */       StringBuilder toreturn = new StringBuilder();
/* 1477 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1478 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1479 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1480 */       String title = "";
/* 1481 */       message = EnterpriseUtil.decodeString(message);
/* 1482 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1483 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1484 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1486 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1488 */       else if ("5".equals(severity))
/*      */       {
/* 1490 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1494 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1496 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1497 */       toreturn.append(v);
/*      */       
/* 1499 */       toreturn.append(link);
/* 1500 */       if (severity == null)
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("5"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1508 */       else if (severity.equals("4"))
/*      */       {
/* 1510 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1512 */       else if (severity.equals("1"))
/*      */       {
/* 1514 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1519 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1521 */       toreturn.append("</a>");
/* 1522 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1526 */       ex.printStackTrace();
/*      */     }
/* 1528 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1535 */       StringBuilder toreturn = new StringBuilder();
/* 1536 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1537 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1538 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1539 */       if (message == null)
/*      */       {
/* 1541 */         message = "";
/*      */       }
/*      */       
/* 1544 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1545 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1547 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1548 */       toreturn.append(v);
/*      */       
/* 1550 */       toreturn.append(link);
/*      */       
/* 1552 */       if (severity == null)
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1556 */       else if (severity.equals("5"))
/*      */       {
/* 1558 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1560 */       else if (severity.equals("1"))
/*      */       {
/* 1562 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1567 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1569 */       toreturn.append("</a>");
/* 1570 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1576 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1579 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1580 */     if (invokeActions != null) {
/* 1581 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1582 */       while (iterator.hasNext()) {
/* 1583 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1584 */         if (actionmap.containsKey(actionid)) {
/* 1585 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1590 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1594 */     String actionLink = "";
/* 1595 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1596 */     String query = "";
/* 1597 */     ResultSet rs = null;
/* 1598 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1599 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1600 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1601 */       actionLink = "method=" + methodName;
/*      */     }
/* 1603 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1604 */       actionLink = methodName;
/*      */     }
/* 1606 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1607 */     Iterator itr = methodarglist.iterator();
/* 1608 */     boolean isfirstparam = true;
/* 1609 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1610 */     while (itr.hasNext()) {
/* 1611 */       HashMap argmap = (HashMap)itr.next();
/* 1612 */       String argtype = (String)argmap.get("TYPE");
/* 1613 */       String argname = (String)argmap.get("IDENTITY");
/* 1614 */       String paramname = (String)argmap.get("PARAMETER");
/* 1615 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1616 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1617 */         isfirstparam = false;
/* 1618 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1620 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1624 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1628 */         actionLink = actionLink + "&";
/*      */       }
/* 1630 */       String paramValue = null;
/* 1631 */       String tempargname = argname;
/* 1632 */       if (commonValues.getProperty(tempargname) != null) {
/* 1633 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1636 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1637 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1638 */           if (dbType.equals("mysql")) {
/* 1639 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1642 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1644 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1646 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1647 */             if (rs.next()) {
/* 1648 */               paramValue = rs.getString("VALUE");
/* 1649 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1653 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1657 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1660 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1665 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1666 */           paramValue = rowId;
/*      */         }
/* 1668 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1669 */           paramValue = managedObjectName;
/*      */         }
/* 1671 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1672 */           paramValue = resID;
/*      */         }
/* 1674 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1675 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1678 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1680 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1681 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1682 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1684 */     return actionLink;
/*      */   }
/*      */   
/* 1687 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1688 */     String dependentAttribute = null;
/* 1689 */     String align = "left";
/*      */     
/* 1691 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1692 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1693 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1694 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1695 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1696 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1697 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1698 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1699 */       align = "center";
/*      */     }
/*      */     
/* 1702 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1703 */     String actualdata = "";
/*      */     
/* 1705 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1706 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1707 */         actualdata = availValue;
/*      */       }
/* 1709 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1710 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1714 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1715 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1718 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1724 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1725 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1726 */       toreturn.append("<table>");
/* 1727 */       toreturn.append("<tr>");
/* 1728 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1729 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1730 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1731 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1732 */         String toolTip = "";
/* 1733 */         String hideClass = "";
/* 1734 */         String textStyle = "";
/* 1735 */         boolean isreferenced = true;
/* 1736 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1737 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1738 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1739 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1741 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1742 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1743 */           while (valueList.hasMoreTokens()) {
/* 1744 */             String dependentVal = valueList.nextToken();
/* 1745 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1746 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1747 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1749 */               toolTip = "";
/* 1750 */               hideClass = "";
/* 1751 */               isreferenced = false;
/* 1752 */               textStyle = "disabledtext";
/* 1753 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1757 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1758 */           toolTip = "";
/* 1759 */           hideClass = "";
/* 1760 */           isreferenced = false;
/* 1761 */           textStyle = "disabledtext";
/* 1762 */           if (dependentImageMap != null) {
/* 1763 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1764 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1767 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1771 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1772 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1773 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1774 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1775 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1776 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1778 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1779 */           if (isreferenced) {
/* 1780 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1784 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1785 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1786 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1787 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1788 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1789 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1791 */           toreturn.append("</span>");
/* 1792 */           toreturn.append("</a>");
/* 1793 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1796 */       toreturn.append("</tr>");
/* 1797 */       toreturn.append("</table>");
/* 1798 */       toreturn.append("</td>");
/*      */     } else {
/* 1800 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1803 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1807 */     String colTime = null;
/* 1808 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1809 */     if ((rows != null) && (rows.size() > 0)) {
/* 1810 */       Iterator<String> itr = rows.iterator();
/* 1811 */       String maxColQuery = "";
/* 1812 */       for (;;) { if (itr.hasNext()) {
/* 1813 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1814 */           ResultSet maxCol = null;
/*      */           try {
/* 1816 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1817 */             while (maxCol.next()) {
/* 1818 */               if (colTime == null) {
/* 1819 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1822 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1831 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1831 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1833 */               if (maxCol != null)
/* 1834 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1836 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1841 */     return colTime;
/*      */   }
/*      */   
/* 1844 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1845 */     tablename = null;
/* 1846 */     ResultSet rsTable = null;
/* 1847 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1849 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1850 */       while (rsTable.next()) {
/* 1851 */         tablename = rsTable.getString("DATATABLE");
/* 1852 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1853 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1866 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1857 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1860 */         if (rsTable != null)
/* 1861 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1863 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1869 */     String argsList = "";
/* 1870 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1872 */       if (showArgsMap.get(row) != null) {
/* 1873 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1874 */         if (showArgslist != null) {
/* 1875 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1876 */             if (argsList.trim().equals("")) {
/* 1877 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1880 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1887 */       e.printStackTrace();
/* 1888 */       return "";
/*      */     }
/* 1890 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1895 */     String argsList = "";
/* 1896 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1899 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1901 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1902 */         if (hideArgsList != null)
/*      */         {
/* 1904 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1906 */             if (argsList.trim().equals(""))
/*      */             {
/* 1908 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1912 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1920 */       ex.printStackTrace();
/*      */     }
/* 1922 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1926 */     StringBuilder toreturn = new StringBuilder();
/* 1927 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1934 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1935 */       Iterator itr = tActionList.iterator();
/* 1936 */       while (itr.hasNext()) {
/* 1937 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1938 */         String confirmmsg = "";
/* 1939 */         String link = "";
/* 1940 */         String isJSP = "NO";
/* 1941 */         HashMap tactionMap = (HashMap)itr.next();
/* 1942 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1943 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1944 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1945 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1946 */           (actionmap.containsKey(actionId))) {
/* 1947 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1948 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1949 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1950 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1951 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1953 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1959 */           if (isTableAction) {
/* 1960 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1963 */             tableName = "Link";
/* 1964 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1965 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1966 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1967 */             toreturn.append("</a></td>");
/*      */           }
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1978 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1984 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1986 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1987 */       Properties prop = (Properties)node.getUserObject();
/* 1988 */       String mgID = prop.getProperty("label");
/* 1989 */       String mgName = prop.getProperty("value");
/* 1990 */       String isParent = prop.getProperty("isParent");
/* 1991 */       int mgIDint = Integer.parseInt(mgID);
/* 1992 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1994 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1996 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1997 */       if (node.getChildCount() > 0)
/*      */       {
/* 1999 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2001 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2003 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2005 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2009 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2014 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2016 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2018 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2020 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2027 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2028 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2030 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2034 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2036 */       if (node.getChildCount() > 0)
/*      */       {
/* 2038 */         builder.append("<UL>");
/* 2039 */         printMGTree(node, builder);
/* 2040 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2045 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2046 */     StringBuffer toReturn = new StringBuffer();
/* 2047 */     String table = "-";
/*      */     try {
/* 2049 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2050 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2051 */       float total = 0.0F;
/* 2052 */       while (it.hasNext()) {
/* 2053 */         String attName = (String)it.next();
/* 2054 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2055 */         boolean roundOffData = false;
/* 2056 */         if ((data != null) && (!data.equals(""))) {
/* 2057 */           if (data.indexOf(",") != -1) {
/* 2058 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2061 */             float value = Float.parseFloat(data);
/* 2062 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2065 */             total += value;
/* 2066 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2069 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2074 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2075 */       while (attVsWidthList.hasNext()) {
/* 2076 */         String attName = (String)attVsWidthList.next();
/* 2077 */         String data = (String)attVsWidthProps.get(attName);
/* 2078 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2079 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2080 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2081 */         String className = (String)graphDetails.get("ClassName");
/* 2082 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2083 */         if (percentage < 1.0F)
/*      */         {
/* 2085 */           data = percentage + "";
/*      */         }
/* 2087 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2089 */       if (toReturn.length() > 0) {
/* 2090 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2094 */       e.printStackTrace();
/*      */     }
/* 2096 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2102 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2103 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2104 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2105 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2106 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2107 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2108 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2109 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2110 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2113 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2114 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2115 */       splitvalues[0] = multiplecondition.toString();
/* 2116 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2119 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2124 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2125 */     if (thresholdType != 3) {
/* 2126 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2127 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2128 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2129 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2130 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2131 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2133 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2134 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2135 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2136 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2137 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2138 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2140 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2141 */     if (updateSelected != null) {
/* 2142 */       updateSelected[0] = "selected";
/*      */     }
/* 2144 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2149 */       StringBuffer toreturn = new StringBuffer("");
/* 2150 */       if (commaSeparatedMsgId != null) {
/* 2151 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2152 */         int count = 0;
/* 2153 */         while (msgids.hasMoreTokens()) {
/* 2154 */           String id = msgids.nextToken();
/* 2155 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2156 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2157 */           count++;
/* 2158 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2159 */             if (toreturn.length() == 0) {
/* 2160 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2162 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2163 */             if (!image.trim().equals("")) {
/* 2164 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2166 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2167 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2170 */         if (toreturn.length() > 0) {
/* 2171 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2175 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2178 */       e.printStackTrace(); }
/* 2179 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSeverity(int status)
/*      */   {
/* 2188 */     switch (status)
/*      */     {
/*      */     case 1: 
/* 2191 */       return "<span class=\"alerttext\">Error</span>";
/*      */     case 2: 
/* 2193 */       return "<span class=\"alerttext\">Error</span>";
/*      */     case 3: 
/* 2195 */       return "<span class=\"alerttext\">Error</span>";
/*      */     case 4: 
/* 2197 */       return "<span class=\"alerttext\">Warning</span>";
/*      */     case 5: 
/* 2199 */       return "Running";
/*      */     case 6: 
/* 2201 */       return "Running";
/*      */     case 7: 
/* 2203 */       return "UnManaged";
/*      */     }
/*      */     
/* 2206 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   private ArrayList checkForGroup(HttpServletRequest request, HashMap<String, ArrayList> MyGroup, String networktype)
/*      */   {
/* 2212 */     if (MyGroup.containsKey(networktype)) {
/* 2213 */       return (ArrayList)MyGroup.get(networktype);
/*      */     }
/*      */     
/* 2216 */     ManagedApplication mo = (ManagedApplication)request.getAttribute("mo");
/* 2217 */     ArrayList listnetwork = mo.getRows("select SUBGROUP,RESOURCEGROUP from AM_ManagedResourceType where RESOURCETYPE='" + networktype + "'");
/* 2218 */     MyGroup.put(networktype, listnetwork);
/* 2219 */     return listnetwork;
/*      */   }
/*      */   
/*      */ 
/* 2223 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2229 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2230 */   static { _jspx_dependants.put("/jsp/includes/bulkActions.jspf", Long.valueOf(1473429417000L));
/* 2231 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2255 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2259 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2260 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2261 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2262 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2263 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2264 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2265 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2266 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2267 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2268 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2269 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2270 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2271 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2272 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2273 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2274 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2275 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2276 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2280 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2281 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2282 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2283 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2284 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2285 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2286 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2287 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2288 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2289 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2290 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.release();
/* 2291 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
/* 2292 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2293 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2294 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2295 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2302 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2305 */     JspWriter out = null;
/* 2306 */     Object page = this;
/* 2307 */     JspWriter _jspx_out = null;
/* 2308 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2312 */       response.setContentType("text/html;charset=UTF-8");
/* 2313 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2315 */       _jspx_page_context = pageContext;
/* 2316 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2317 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2318 */       session = pageContext.getSession();
/* 2319 */       out = pageContext.getOut();
/* 2320 */       _jspx_out = out;
/*      */       
/* 2322 */       out.write(" <!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2323 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2325 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2327 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2329 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2331 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2333 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2335 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2336 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2337 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2338 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2341 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2342 */         String available = null;
/* 2343 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2344 */         out.write(10);
/*      */         
/* 2346 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2348 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2350 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2352 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2354 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2356 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2357 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2358 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2359 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2362 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2363 */           String unavailable = null;
/* 2364 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2365 */           out.write(10);
/*      */           
/* 2367 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2369 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2371 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2373 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2375 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2377 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2378 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2379 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2380 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2383 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2384 */             String unmanaged = null;
/* 2385 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2386 */             out.write(10);
/*      */             
/* 2388 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2390 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2392 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2394 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2396 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2398 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2399 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2400 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2401 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2404 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2405 */               String scheduled = null;
/* 2406 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2407 */               out.write(10);
/*      */               
/* 2409 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2411 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2413 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2415 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2417 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2419 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2420 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2421 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2422 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2425 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2426 */                 String critical = null;
/* 2427 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2428 */                 out.write(10);
/*      */                 
/* 2430 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2431 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2432 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2434 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2436 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2438 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2440 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2441 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2442 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2443 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2446 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2447 */                   String clear = null;
/* 2448 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2449 */                   out.write(10);
/*      */                   
/* 2451 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2452 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2453 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2455 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2457 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2459 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2461 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2462 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2463 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2464 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2467 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2468 */                     String warning = null;
/* 2469 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2470 */                     out.write(10);
/* 2471 */                     out.write(10);
/*      */                     
/* 2473 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2474 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2476 */                     out.write(10);
/* 2477 */                     out.write(10);
/* 2478 */                     out.write(10);
/* 2479 */                     out.write("\n\n\n\n\n<script type=\"text/javascript\" src=\"/template/validation.js\"></script>\n<script>\n");
/*      */                     
/*      */                     try
/*      */                     {
/* 2483 */                       String showallstyle = "new-monitordiv-link";
/* 2484 */                       String managedStyle = "new-monitordiv-link";
/* 2485 */                       String unManagedStyle = "new-monitordiv-link";
/* 2486 */                       String criticalStyle = "new-monitordiv-link";
/* 2487 */                       int pagenois = 1;
/* 2488 */                       String selectedpage = request.getParameter("selectedPage");
/* 2489 */                       if ((selectedpage != null) && (selectedpage.trim().length() > 0)) {
/* 2490 */                         pagenois = Integer.parseInt(selectedpage);
/*      */                       }
/* 2492 */                       if (request.getParameter("showmanage") != null) {
/* 2493 */                         if ("All".equalsIgnoreCase(request.getParameter("showmanage")))
/*      */                         {
/* 2495 */                           showallstyle = "bulkmon-tag";
/*      */                         }
/* 2497 */                         else if ("true".equalsIgnoreCase(request.getParameter("showmanage")))
/*      */                         {
/* 2499 */                           managedStyle = "bulkmon-tag";
/*      */                         }
/* 2501 */                         else if ("false".equalsIgnoreCase(request.getParameter("showmanage")))
/*      */                         {
/* 2503 */                           unManagedStyle = "bulkmon-tag";
/*      */                         }
/* 2505 */                         else if ("critical".equalsIgnoreCase(request.getParameter("showmanage")))
/*      */                         {
/* 2507 */                           criticalStyle = "bulkmon-tag";
/*      */                         }
/*      */                       }
/*      */                       else {
/* 2511 */                         showallstyle = "bulkmon-tag";
/*      */                       }
/*      */                       
/* 2514 */                       out.write("\n\tfunction redirectToHome(url)   \n\t{\n\t\twindow.open(url, '_top' );//No I18N\n\t}\n\tfunction openDeviceSnapshotPage(url,openInNewWindow,reqFromSearch){\n\t\n\n\t\tif(reqFromSearch=='null'){\n\t\t\tif(url.indexOf(\"produrl\")!=-1){\n\t\t\t\tvar prefix = url.substring(0,url.indexOf(\"produrl\")+8);\n\t\t\t\tvar extParameter = url.substring(url.indexOf(\"produrl\")+8);\n\t\t\t\turl = prefix+encodeURIComponent(extParameter);\n\t\t\t}\n\t\t\twindow.location.href=url;\n\n\t\t\t}else{\t\n\tif(url.indexOf(\"produrl\")!=-1){\n\t\tvar prefix = url.substring(0,url.indexOf(\"produrl\")+8);\n\t\tvar extParameter = url.substring(url.indexOf(\"produrl\")+8);\n\t\turl = prefix+encodeURIComponent(extParameter);\n\t}\n\n\tif(!openInNewWindow){\n\t\tajaxBackButton(\"backtoSearch\", url, false);//No I18N\n\t\twindow.open(url, '_self' );//No I18N\n\t}else{\n\t\twindow.open(url, '_blank' );//No I18N\n\t}\n\t}\n\n}\nfunction showCustomFieldValuesforFilter(http1,b,divValue)\n{\n        if(http1.readyState==4)\n        {\n\t\t\tif(divValue == ''){\n\t\t\t\tjQuery(\"#customFieldValuesFilterby\").show();//NO I18N\n\t\t\t\tjQuery(\"#customFieldValuesFilterby\").html(http1.responseText);//NO I18N\n");
/* 2515 */                       out.write("        \t\tvar obj = document.getElementById('customFieldValuesFilterbybottom');\n        \t\tif(obj != null){\n        \t\t\tjQuery(\"#customFieldValuesFilterbybottom\").show();//NO I18N\n    \t\t\t\tjQuery(\"#customFieldValuesFilterbybottom\").html(http1.responseText);//NO I18N\n        \t\t}\n\t\t\t}else{\n\t\t\t\tjQuery(\"#\"+divValue).show();\n\t\t\t\tjQuery(\"#\"+divValue).html(http1.responseText);\n\t\t\t}\n\t\t\t$('.chzn-select').chosen();\n        }\n}\n\n\n\nfunction loadUrl(option){\n\n\tif(option !=null && option != '-')\n\n        location.href=\"/showresource.do?method=showResourceTypesAll&group=All&viewmontype=All&customValue=\"+option;\n\n}\n\nfunction searchcustomfield(){\n\nvar custom = document.getElementById('customoptionvalue').value;\nvar searchtext = document.getElementById('searchtext').value.trim();\nif(searchtext == 'Search...' || searchtext == ''){\n\talert('please enter some value to search')\t// NO I18N\n\treturn;\n}\nlocation.href='/showresource.do?method=showResourceTypesAll&group=All&viewmontype=All&customValue='+custom+'&customquery='+searchtext;\n}\n\n");
/* 2516 */                       out.write("\nfunction disableEnterKey(e)\n{\n     var key;\n\n     if(window.event)\n          key = window.event.keyCode;     //IE\n     else\n          key = e.which;     //firefox\n\n \tif(key == 13){\n\n    searchcustomfield();\n \t   return false;\n\n\n     }else\n          return true;\n}\n\nfunction loadURLType(option,frm,a,showdiv,forBulkAssign)\n{\n\tvar methodname=\"");
/* 2517 */                       out.print(request.getParameter("method"));
/* 2518 */                       out.write("\";\n\tvar oldtab=\"");
/* 2519 */                       out.print(request.getParameter("oldtab"));
/* 2520 */                       out.write("\";\n    var isReqFromAdmin = \"");
/* 2521 */                       out.print(request.getParameter("isReqFromAdmin"));
/* 2522 */                       out.write("\";\n    var showmanage = \"");
/* 2523 */                       out.print(request.getParameter("showmanage"));
/* 2524 */                       out.write("\"; //NO I18N\n\tif( isReqFromAdmin == 'true' || oldtab == null || oldtab=='null')\n\t{\n\t   oldtab=\"6\";\n\t}\n\t\tif(option!='-')\n\t\t{\n\t\t\tvar networkname=\"");
/* 2525 */                       out.print(request.getParameter("network"));
/* 2526 */                       out.write("\";\n\t\t\tvar groupname=\"");
/* 2527 */                       out.print(request.getParameter("group"));
/* 2528 */                       out.write("\";\n\n\t\t\tif(option=='All')\n\t\t\t{\n\t\t\t\tfrm.action=\"/showresource.do?method=showResourceTypesAll&oldtab=\"+oldtab+\"&group=All&viewmontype=All\"; //NO I18N\n\t\t\t\tfrm.submit();\n\t\t\t}\n\t\t\telse if(option.indexOf(\"SYSTEMDATA\") != -1 || option.indexOf(\"USERDATA\") != -1 || option.indexOf(\"LOCATION_NAME\") != -1 || option.indexOf(\"USERNAME\") != -1 || option.indexOf(\"VALUEID\") != -1){\n\n\t\t\t\tif(a != null){\n\t\t\t\ta.style.backgroundColor = '#FFF8C6';\n\t\t\t\t}\n\n\n\t\t\t\tvar http1=getHTTPObject();\n\n\t\t\t\thttp1.onreadystatechange= function (){showCustomFieldValuesforFilter(http1,a,showdiv);};//No I18N\n\n\t\t\t\t if(option.indexOf(\"$\") != -1){\n\n\t\t\t\t\t\t\t\t//\t\talert(option.substring(0,option.indexOf(\"$\")))\n\t\t\t\t\t\t\t\t//\t\talert(option.substring(option.indexOf(\"$\")+1))\n                                        URL = \"/myFields.do?method=getFieldValues&aliasName=\"+option.substring(0,option.indexOf(\"$\"))+\"&optionSel=\"+option.substring(option.indexOf(\"$\")+1)+\"&forBulkAssign=\"+forBulkAssign; // NO I18N\n\n                                }else{\n\n                                URL=\"/myFields.do?method=getFieldValues&aliasName=\"+option+\"&forBulkAssign=\"+forBulkAssign+\"&optionSel=\";//No I18N\n");
/* 2529 */                       out.write("                                }\n\t\t\t\thttp1.open(\"GET\",URL,true);//No I18N\n\t\t\t\thttp1.send(null);//No I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\tif(option=='VMWare ESX/ESXi')\n\t\t\t{\n\t\t\tlocation.href=\"/showresource.do?method=showResourceTypes&oldtab=\"+oldtab+\"&detailspage=true&network=\"+option+\"&viewmontype=\"+option+\"&showmanage=\"+showmanage; //NO I18N\n\t\t\t}\t\t\t\n\t\t\telse if(groupname=='null')\n\t\t\t{\t\t\t\t\t\n\t\t\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&oldtab=\"+oldtab+\"&detailspage=true&network=\"+option+\"&viewmontype=\"+option+\"&showmanage=\"+showmanage; //NO I18N\n\t\t\t}\t\n\t\t\telse\n\t\t\t{\t\t\t\t  \n\t\t\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&oldtab=\"+oldtab+\"&detailspage=true&network=\"+option+\"&viewmontype=\"+option+\"&showmanage=\"+showmanage; //NO I18N\n\t\t\t}\n\n\t\t\t}\n\t\t}\n}\n\n\nfunction showManageType(manage)\n{\n\tvar methodname=\"");
/* 2530 */                       out.print(request.getParameter("method"));
/* 2531 */                       out.write("\";\n\tvar viewmontype=\"");
/* 2532 */                       out.print(request.getParameter("viewmontype"));
/* 2533 */                       out.write("\";\n\tvar oldtab=\"");
/* 2534 */                       out.print(request.getParameter("oldtab"));
/* 2535 */                       out.write("\";\n    var isReqFromAdmin = \"");
/* 2536 */                       out.print(request.getParameter("isReqFromAdmin"));
/* 2537 */                       out.write("\";\n\tif( isReqFromAdmin == 'true' || oldtab == null || oldtab=='null')\n\t{\n\t   oldtab=\"6\";\n\t}\n\n\tif(methodname=='showResourceTypesAll')\n\t{\n\t\tif(manage=='All')\n\t\t{\n\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&oldtab=\"+oldtab+\"&group=All&viewmontype=\"+viewmontype; //NO I18N\n\t\t}\n\t\tif(viewmontype=='null')\n\t\t{\n\t\tviewmontype=\"All\"; //NO I18N\n\t\t}\n       location.href=\"/showresource.do?method=showResourceTypesAll&group=All&oldtab=\"+oldtab+\"&viewmontype=\"+viewmontype+\"&showmanage=\"+manage; //NO I18N\n\t}\n\telse if(methodname=='showResourceTypes')\n\t{\n\t\tvar networkname=\"");
/* 2538 */                       out.print(request.getParameter("network"));
/* 2539 */                       out.write("\";\n\t\tvar groupname=\"");
/* 2540 */                       out.print(request.getParameter("group"));
/* 2541 */                       out.write("\";\n\t\tif(manage=='All')\n\t\t{\n\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&oldtab=\"+oldtab+\"&group=All&viewmontype=All&showmanage=\"+manage; //NO I18N\n\t\t}\n\t\tif(viewmontype=='All')\n\t\t{\n\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&oldtab=\"+oldtab+\"&group=All&viewmontype=All&showmanage=\"+manage; //NO I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\tif(networkname=='null' || groupname!='null')\n\t\t\t{   if(viewmontype=='null') viewmontype=groupname; //NO I18N\n\t\t\t\tif(manage=='All')\n\t\t\t\t{\n\t\t\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&detailspage=true&oldtab=\"+oldtab+\"&group=\"+groupname; //NO I18N\n\t\t\t\t}\n\t\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&detailspage=true&oldtab=\"+oldtab+\"&group=\"+groupname+\"&showmanage=\"+manage; //NO I18N\n\t\t\t}\n\t\t\telse\n\t\t\t{\tif(viewmontype=='null') viewmontype=networkname; //NO I18N\n\t\t\t\tif(manage=='All')\n\t\t\t\t{\n\t\t\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&detailspage=true&oldtab=\"+oldtab+\"&network=\"+networkname+\"&viewmontype=\"+viewmontype; //NO I18N\n");
/* 2542 */                       out.write("\t\t\t\t}\n\t\t\t\tlocation.href=\"/showresource.do?method=showResourceTypesAll&detailspage=true&oldtab=\"+oldtab+\"&network=\"+networkname+\"&viewmontype=\"+viewmontype+\"&showmanage=\"+manage; //NO I18N\n\t\t\t}\n\t\t}\n\t}\n}\nfunction invokeOperations(obj){\n\t");
/* 2543 */                       if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                       {
/*      */ 
/* 2546 */                         out.write("\n\t\thideIT360LicViolStatusBar();\n\t");
/*      */                       }
/* 2548 */                       out.write("\t\t\n\t\n\tvar search360 = document.getElementById('isExternalDevicePresent'); //No I18N\n\tif(search360!=null) \n\t{\n\t   alert( \"BulkActions are not supported for Networks/OpStor Devices\" ); //No I18N\n\t}\n\n    var option=obj.value;\n    if(option=='Delete'){\n        deleteMO(document.deletemonitor);\n    }else if(option=='editDisplayNames'){\n    editDisplaynames(document.deletemonitor);\n    }else if(option=='updateIP'){\n    (updateIP(document.deletemonitor)); //NO I18N\n    }else if(option=='Manage'){\t\t//NO I18N\n        manageMO(document.deletemonitor);\n    }else if(option=='Unmanage'){\t//NO I18N\n        unManageMO(document.deletemonitor);\n    }else if(option=='updateauth'){\t//NO I18N\n        updateAuthentication(document.deletemonitor);\n    }else if(option=='updatepoll'){\t//NO I18N\n        updateAuthentication(document.deletemonitor,'updatePolling'); //NO I18N\n    }else if(option=='copyPaste'){ //NO I18N\n            copyPasteMO(document.deletemonitor);\n    }else if(option=='unManageAndReset'){ //NO I18N\n\tunManageAndResetMO(document.deletemonitor);\n");
/* 2549 */                       out.write("\t}else if(option=='customField'){\t// NO I18N\n\tassignCustomField(document.deletemonitor);\n    }\n}\n\nfunction invokeOperations1() {\n        var option=document.deletemonitor1.operation.value;\n        if(option=='delete'){\n            deleteMO(document.deletemonitor1);\n        }else if(option=='editDisplayNames'){\n            editDisplaynames(document.deletemonitor1);\n        }else if(option=='updateIP'){\n\t    (updateIP(document.deletemonitor1)); //NO I18N\n        }else if(option=='manage'){\n            manageMO(document.deletemonitor1);\n        }else if(option=='unManage'){\n            unManageMO(document.deletemonitor1);\n        }else if(option=='updateAuthentication'){\n            updateAuthentication(document.deletemonitor1);\n        }else if(option=='updatePoll'){\n            updateAuthentication(document.deletemonitor1,'updatePolling'); //NO I18N\n        }else if(option=='copyPaste'){ //NO I18N\n            copyPasteMO(document.deletemonitor1);\n        }else if(option=='unManageAndResetMO'){ //NO I18N\n\t\tunManageAndResetMO(document.deletemonitor);\n");
/* 2550 */                       out.write("\t}\n    }\n\nfunction myOnLoad()\n{\n\tSORTTABLENAME = 'allResourceTable';\n\t");
/* 2551 */                       boolean hasresourceGroups = false;
/* 2552 */                       String selectedtype = request.getParameter("viewmontype");
/* 2553 */                       if (selectedtype == null) selectedtype = request.getParameter("network");
/* 2554 */                       if (request.getParameter("group") != null) selectedtype = request.getParameter("group");
/* 2555 */                       for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                       {
/* 2557 */                         if (com.adventnet.appmanager.util.Constants.categoryLink[i].equals(selectedtype))
/*      */                         {
/* 2559 */                           hasresourceGroups = true;
/* 2560 */                           break;
/*      */                         }
/*      */                       }
/* 2563 */                       out.write("\n\tvar numberOfColumnsToBeSorted=4;\n\t");
/* 2564 */                       if ((!hasresourceGroups) && (selectedtype != null) && (!selectedtype.equals("All"))) {
/* 2565 */                         out.write(" numberOfColumnsToBeSorted=3; ");
/*      */                       }
/* 2567 */                       out.write(10);
/* 2568 */                       out.write(9);
/* 2569 */                       if (request.getParameter("method").equals("showResourceTypesAll")) {
/* 2570 */                         out.write(" numberOfColumnsToBeSorted=4; ");
/*      */                       }
/* 2572 */                       out.write(10);
/* 2573 */                       out.write(9);
/* 2574 */                       if ((selectedtype != null) && (selectedtype.equals("RBM"))) {
/* 2575 */                         out.write(" numberOfColumnsToBeSorted=5; ");
/*      */                       }
/* 2577 */                       out.write("\n\tvar ignoreCheckBox =true;\n\tvar ignoreDefaultSorting=false;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting,true);\n\tmyOnLoad1();\n\n\tvar option = '");
/* 2578 */                       out.print(request.getParameter("customValue"));
/* 2579 */                       out.write("';\n\t// var textboxvalue = 'request.getParameter(\"customquery\")%>';\n\n\tif (option != 'null'){\n/*\n\t\tif(option.indexOf(\"$\") == -1){\n\t\t\toption = option+'$'+textboxvalue;\n\t\t}\n\t\t*/\n       loadURLType(option,null,null,'','false');\n\n        }\n\n}\n\nfunction myOnLoad1()\n{\n\tSORTTABLENAME = 'VMListForOS'; //No I18N\n\tvar numberOfColumnsToBeSorted = 2;\n\tvar ignoreCheckBox = false;\n\tvar ignoreDefaultSorting=true;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n\n\tSORTTABLENAME = 'EC2InstanceListForOS'; //No I18N\n\tnumberOfColumnsToBeSorted = 2;\n\tignoreCheckBox = false;\n\tignoreDefaultSorting=true;\n\tsortables_init(numberOfColumnsToBeSorted,ignoreCheckBox,ignoreDefaultSorting);\n}\n\n\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.deletemonitor,name);\n}\nfunction showAlarms(resourceid)\n{\n\tvar d=new Date();\n\tMM_openBrWindow('/RecentAlarms.do?method=getAlarmsForResource&resourceid='+resourceid+'&d='+d,'AlarmSummary','width=600,height=450, scrollbars=yes');\n\n}\nfunction deleteMO(selectname)\n{\n\n\t");
/* 2580 */                       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                         return;
/* 2582 */                       out.write(10);
/* 2583 */                       out.write(9);
/*      */                       
/* 2585 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2586 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2587 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                       
/* 2589 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2590 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2591 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2593 */                           out.write("\n\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 2594 */                           out.print(FormatUtil.getString("am.webclient.common.alert.delete.text"));
/* 2595 */                           out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\tvar viewmontype=\"");
/* 2596 */                           out.print(request.getParameter("viewmontype"));
/* 2597 */                           out.write("\";\n\tvar showmanage=\"");
/* 2598 */                           out.print(request.getParameter("showmanage"));
/* 2599 */                           out.write("\";\n\tif(viewmontype=='null')\n\t{\n\t\tviewmontype=\"All\"; //NO I18N\n\t}\n\tif(document.deletemonitor.type1.value == 'Unknown' || document.deletemonitor.type1.value == 'Linux' || document.deletemonitor.type1.value == 'Sun Solaris' || document.deletemonitor.type1.value == 'Windows')\n\t{\n\t\tvar msg=\"");
/* 2600 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 2601 */                           out.write("\";\n\t\tif(document.deletemonitor.type1.value == 'Unknown' || document.deletemonitor.type1.value == 'Linux' || document.deletemonitor.type1.value == 'Windows'){\n\t\t\tmsg=\"");
/* 2602 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.deleteFDAlso.text"));
/* 2603 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 2604 */                           out.write("\";\n\t\t}\n\t\tif(confirm(msg))\n\t\t{\n\tdocument.deletemonitor.action=\"/deleteMO.do?method=deleteMO&listview=true&viewmontype=\"+viewmontype+\"&showmanage=\"+showmanage;\n\tdocument.deletemonitor.submit();\n\t\t}\n\t}\n\telse\n\t{\n\t\tif(confirm(\"");
/* 2605 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.delete.text"));
/* 2606 */                           out.write("\"))\n\t\t{\n\tdocument.deletemonitor.action=\"/deleteMO.do?method=deleteMO&listview=true&viewmontype=\"+viewmontype+\"&showmanage=\"+showmanage;\n\tdocument.deletemonitor.submit();\n\t\t}\n\t}\n\t");
/* 2607 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2608 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2612 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2613 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                       }
/*      */                       
/* 2616 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2617 */                       out.write("\n}\n\nfunction unManageMO(selectname)\n{\n\n\t");
/* 2618 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2620 */                       out.write(10);
/* 2621 */                       out.write(9);
/*      */                       
/* 2623 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2624 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2625 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                       
/* 2627 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2628 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2629 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2631 */                           out.write("\n\tvar viewmontype=\"");
/* 2632 */                           out.print(request.getParameter("viewmontype"));
/* 2633 */                           out.write("\";\n\tvar showmanage=\"");
/* 2634 */                           out.print(request.getParameter("showmanage"));
/* 2635 */                           out.write("\";\n\tvar network=\"");
/* 2636 */                           out.print(request.getParameter("network"));
/* 2637 */                           out.write("\";\n\tif(viewmontype=='null')\n\t{\n\t\tviewmontype=\"All\"; //NO I18N\n\t}\n\t");
/* 2638 */                           ArrayList listdel = (ArrayList)request.getAttribute("resourcetable");
/* 2639 */                           if (listdel.size() == 1)
/*      */                           {
/* 2641 */                             out.write("\n\t\t\tshowmanage=\"All\"; //NO I18N\n\t");
/*      */                           }
/* 2643 */                           out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 2644 */                           out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.text"));
/* 2645 */                           out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\n\t\tif(confirm(\"");
/* 2646 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.unmanage.text"));
/* 2647 */                           out.write("\"))\n\t\t{\n                    document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitors&listview=true&viewmontype=\"+viewmontype+\"&showmanage=\"+showmanage+\"&network=\"+network;\n                    document.deletemonitor.submit();\n\t\t}\n\t}\n\t");
/* 2648 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2649 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2653 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2654 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/* 2657 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2658 */                       out.write("\n}\n\nfunction assignCustomField(selectname){\n\n\t");
/* 2659 */                       if (_jspx_meth_logic_005fpresent_005f2(_jspx_page_context))
/*      */                         return;
/* 2661 */                       out.write("\n\t        ");
/*      */                       
/* 2663 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2664 */                       _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2665 */                       _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                       
/* 2667 */                       _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2668 */                       int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2669 */                       if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                         for (;;) {
/* 2671 */                           out.write("\n\n\t if(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t        {\n\t                alert(\"");
/* 2672 */                           out.print(FormatUtil.getString("am.webclient.alert.bulkassign.text"));
/* 2673 */                           out.write("\");//No i18n\n\t                selectname.operation.selectedIndex=0;\n\t                return;\n\t        }\n\t        else\n\t        {\n\n\t\t//\t\tdocument.getElementById(\"customFieldValuesFilterby\").style.display =\"none\";\n\t\t\t\t                var len = document.deletemonitor.select.length;\n\t                var temp=\"\";\n\t                if(len > 0)\n\t                {\n\t                        for( i=0; i<len; i++)\n\t                        {\n\t                                if(document.deletemonitor.select[i].checked)\n\t                                        temp = temp + document.deletemonitor.select[i].value + \",\";\n\t                        }\n\t                }\n\t                else\n\t                {\n\t                        temp=document.deletemonitor.select.value;\n\t                }\n\n\n\t\t\t\t\t\t\tdocument.deletemonitor.action=\"/myFields.do?method=assignCustomFields&resids=\"+temp;\n\t                        window.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=700,height=400,top=200,left=200');\n\n\n\t        }\n");
/* 2674 */                           out.write("\t        ");
/* 2675 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2676 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2680 */                       if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2681 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                       }
/*      */                       
/* 2684 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2685 */                       out.write("\n\n\n\t}\n\n\tfunction assignCustomValues(value){\n\n\t\t\tif(value != '-'){\n\n\n\n\t\t\t document.deletemonitor.action=\"/myFields.do?method=bulkAssign&customVal=\"+value;//No I18N\n\t\t        document.deletemonitor.submit();\n\n\n\t\t\t}\n\n\t}\nfunction unManageAndResetMO(selectname)\n{\n\n\t");
/* 2686 */                       if (_jspx_meth_logic_005fpresent_005f3(_jspx_page_context))
/*      */                         return;
/* 2688 */                       out.write(10);
/* 2689 */                       out.write(9);
/*      */                       
/* 2691 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2692 */                       _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 2693 */                       _jspx_th_logic_005fnotPresent_005f3.setParent(null);
/*      */                       
/* 2695 */                       _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 2696 */                       int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 2697 */                       if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                         for (;;) {
/* 2699 */                           out.write("\n\tvar viewmontype=\"");
/* 2700 */                           out.print(request.getParameter("viewmontype"));
/* 2701 */                           out.write("\";\n\tvar showmanage=\"");
/* 2702 */                           out.print(request.getParameter("showmanage"));
/* 2703 */                           out.write("\";\n\tvar network=\"");
/* 2704 */                           out.print(request.getParameter("network"));
/* 2705 */                           out.write("\";\n\tif(viewmontype=='null')\n\t{\n\t\tviewmontype=\"All\"; //NO I18N\n\t}\n\t");
/* 2706 */                           ArrayList listdel = (ArrayList)request.getAttribute("resourcetable");
/* 2707 */                           if (listdel.size() == 1)
/*      */                           {
/* 2709 */                             out.write("\n\t\t\tshowmanage=\"All\"; //NO I18N\n\t");
/*      */                           }
/* 2711 */                           out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 2712 */                           out.print(FormatUtil.getString("am.webclient.common.alert.unmanage.text"));
/* 2713 */                           out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\n\t\tif(confirm(\"");
/* 2714 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.reset.text"));
/* 2715 */                           out.write("\"))\n\t\t{\n                    document.deletemonitor.action=\"/deleteMO.do?method=unManageMonitors&listview=true&viewmontype=\"+viewmontype+\"&showmanage=\"+showmanage+\"&isReset=true&network=\"+network;\n                    document.deletemonitor.submit();\n\t\t}\n\t}\n\t");
/* 2716 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 2717 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2721 */                       if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 2722 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                       }
/*      */                       
/* 2725 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 2726 */                       out.write("\n}\nfunction editDisplaynames(selectname)\n{\n\t ");
/* 2727 */                       if (_jspx_meth_logic_005fpresent_005f4(_jspx_page_context))
/*      */                         return;
/* 2729 */                       out.write(10);
/* 2730 */                       out.write(9);
/*      */                       
/* 2732 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f4 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2733 */                       _jspx_th_logic_005fnotPresent_005f4.setPageContext(_jspx_page_context);
/* 2734 */                       _jspx_th_logic_005fnotPresent_005f4.setParent(null);
/*      */                       
/* 2736 */                       _jspx_th_logic_005fnotPresent_005f4.setRole("DEMO");
/* 2737 */                       int _jspx_eval_logic_005fnotPresent_005f4 = _jspx_th_logic_005fnotPresent_005f4.doStartTag();
/* 2738 */                       if (_jspx_eval_logic_005fnotPresent_005f4 != 0) {
/*      */                         for (;;) {
/* 2740 */                           out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 2741 */                           out.print(FormatUtil.getString("am.webclient.alert.displayname.select.text"));
/* 2742 */                           out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n     \t{\n\t\tvar len = document.deletemonitor.select.length;\n\t\tvar temp=\"\";\n\t\tif(len > 0)\n\t\t{\n\t\t\tfor( i=0; i<len; i++)\n\t\t\t{\n\t\t\t\tif(document.deletemonitor.select[i].checked) {\n\t\t\t\t\ttemp = temp + document.deletemonitor.select[i].value + \",\";\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\ttemp=document.deletemonitor.select.value;\n\t\t}\n\t\tvar vmResids='");
/* 2743 */                           out.print(request.getAttribute("VirtualMachines"));
/* 2744 */                           out.write("';\n\t\tdocument.deletemonitor.action=\"/jsp/EditDisplaynames.jsp?resids=\"+temp+\"&vmResids=\"+vmResids;\n\t\twindow.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=780,height=300,top=200,left=200');\n\t}\n\t");
/* 2745 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f4.doAfterBody();
/* 2746 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2750 */                       if (_jspx_th_logic_005fnotPresent_005f4.doEndTag() == 5) {
/* 2751 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4); return;
/*      */                       }
/*      */                       
/* 2754 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f4);
/* 2755 */                       out.write("\n}\nfunction updateIP(selectname)\n{\n\t");
/* 2756 */                       if (_jspx_meth_logic_005fpresent_005f5(_jspx_page_context))
/*      */                         return;
/* 2758 */                       out.write(10);
/* 2759 */                       out.write(9);
/*      */                       
/* 2761 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f5 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2762 */                       _jspx_th_logic_005fnotPresent_005f5.setPageContext(_jspx_page_context);
/* 2763 */                       _jspx_th_logic_005fnotPresent_005f5.setParent(null);
/*      */                       
/* 2765 */                       _jspx_th_logic_005fnotPresent_005f5.setRole("DEMO");
/* 2766 */                       int _jspx_eval_logic_005fnotPresent_005f5 = _jspx_th_logic_005fnotPresent_005f5.doStartTag();
/* 2767 */                       if (_jspx_eval_logic_005fnotPresent_005f5 != 0) {
/*      */                         for (;;) {
/* 2769 */                           out.write("\n\t\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t\t{\n\t\t\talert(\"");
/* 2770 */                           out.print(FormatUtil.getString("am.webclient.alert.updateip.select.text"));
/* 2771 */                           out.write("\");//No i18n\n\t\t\tselectname.operation.selectedIndex=0;\n\t\t\treturn;\n\t\t}\n\t\telse\n\t\t{\n\t\t\tvar len = document.deletemonitor.select.length;\n\t\t\tvar temp=\"\";\n\t\t\tif(len > 0)\n\t\t\t{\n\t\t\t\tfor( i=0; i<len; i++)\n\t\t\t\t{\n\t\t\t\t\tif(document.deletemonitor.select[i].checked)\n\t\t\t\t\ttemp = temp + document.deletemonitor.select[i].value + \",\";\n\t\t\t\t}\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\ttemp=document.deletemonitor.select.value;\n\t\t\t}\n\t\t\tdocument.deletemonitor.action=\"/jsp/UpdateIp.jsp?resids=\"+temp;\n\t\t\twindow.open(document.deletemonitor.action,'','resizable=yes,scrollbars=yes,width=800,height=400,top=200,left=300');\n\t\t}\n\t");
/* 2772 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f5.doAfterBody();
/* 2773 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2777 */                       if (_jspx_th_logic_005fnotPresent_005f5.doEndTag() == 5) {
/* 2778 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5); return;
/*      */                       }
/*      */                       
/* 2781 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f5);
/* 2782 */                       out.write("\n}\nfunction hideIT360LicViolStatusBar()\n{\n\tdocument.getElementById(\"it360licensestatus\").style.display=\"none\";\n}\n\nfunction showLicenseViolationMessages(licViolMessages)\n{\n\tif(licViolMessages!=null && licViolMessages.length>0)\n\t{\n\tdocument.getElementById(\"it360licensestatus\").innerHTML='';//No I18N\t\n\tvar innerHtml='<ul style=\"background: none repeat scroll 0 0 #F5F9FD;border: 1px solid #AEAEAE;border-radius: 5px 5px 5px 5px;font: 11px/18px Arial,Helvetica,sans-serif;list-style: none outside none;margin: 0;padding: 0;\"><li>&nbsp;</li>'\n\tfor(i=0;i<licViolMessages.length;i++)\n\t{\n\t\tinnerHtml+='<li style=\"padding:1\">&nbsp;&nbsp;<img align=\"absmiddle\" src=\"/it360/images/diagnostics/diagno-critical.png\">&nbsp;&nbsp;'+licViolMessages[i]+'</li>'\n\t}\n\tinnerHtml+='<li>&nbsp;</li></ul><ul/>';\n\tdocument.getElementById(\"it360licensestatus\").innerHTML=innerHtml;//No I18N\n\tdocument.getElementById(\"it360licensestatus\").style.display=\"block\";\n\t}\n}\n\nfunction it360LicenseCheck()\n{\n\tvar len = document.deletemonitor.select.length;\n\tvar toReturn=\"true\";\n");
/* 2783 */                       out.write("\n\t\tif(len>0)\n\t\t{\n\t\t\tvar url = \"/deleteMO.do\";//No I18N\n\t\t\tvar params = \"method=it360LicenseCheckForManageMonitors\";//No I18N\n\t\t\tvar j=0;\n\t\t\tfor( i=0; i<len; i++)\n\t\t\t{\n\t\t\t\tif(document.deletemonitor.select[i].checked)\n\t\t\t\t{\n\t\t\t\t\t\n\t\t\t\t\tparams+=\"&select=\"+document.deletemonitor.select[i].value; //NO I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\txmlhttp.open(\"POST\", url, false);\n\t\t\txmlhttp.setRequestHeader(\"Content-type\", \"application/x-www-form-urlencoded\");\n\t\t\txmlhttp.setRequestHeader(\"Content-length\", params.length);\n\t\t\txmlhttp.setRequestHeader(\"Connection\", \"close\");\n\t\t\txmlhttp.onreadystatechange=function(){\n\t\t\tif(xmlhttp.readyState == 4 && xmlhttp.status == 200) {\n\t\t\t\tvar responseText=xmlhttp.responseText;\n\t\t\t\tresponseText=trimAll(responseText);\n\t\t\t\tif(responseText=='')\n\t\t\t\t{\n\t\t\t\t\ttoReturn='true';\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\tvar violMessageArray=eval(responseText);\n\t\t\t\t\tshowLicenseViolationMessages(violMessageArray);\n\t\t\t\t\ttoReturn='false';\n\t\t\t\t}\n\t        \t}\n\t\t\t}\n\t\t\txmlhttp.send(params);\n\t\t}\n\t\treturn toReturn;\n\n}\nfunction manageMO(selectname)\n");
/* 2784 */                       out.write("{\n\n\t");
/* 2785 */                       if (_jspx_meth_logic_005fpresent_005f6(_jspx_page_context))
/*      */                         return;
/* 2787 */                       out.write(10);
/* 2788 */                       out.write(9);
/*      */                       
/* 2790 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f6 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2791 */                       _jspx_th_logic_005fnotPresent_005f6.setPageContext(_jspx_page_context);
/* 2792 */                       _jspx_th_logic_005fnotPresent_005f6.setParent(null);
/*      */                       
/* 2794 */                       _jspx_th_logic_005fnotPresent_005f6.setRole("DEMO");
/* 2795 */                       int _jspx_eval_logic_005fnotPresent_005f6 = _jspx_th_logic_005fnotPresent_005f6.doStartTag();
/* 2796 */                       if (_jspx_eval_logic_005fnotPresent_005f6 != 0) {
/*      */                         for (;;) {
/* 2798 */                           out.write("\n\tvar viewmontype=\"");
/* 2799 */                           out.print(request.getParameter("viewmontype"));
/* 2800 */                           out.write("\";\n\tvar showmanage=\"");
/* 2801 */                           out.print(request.getParameter("showmanage"));
/* 2802 */                           out.write("\";\n\tvar network=\"");
/* 2803 */                           out.print(request.getParameter("network"));
/* 2804 */                           out.write("\";\n\tif(viewmontype=='null')\n\t{\n\t\tviewmontype=\"All\"; //NO I18N\n\t}\n\t");
/* 2805 */                           ArrayList listdel = (ArrayList)request.getAttribute("resourcetable");
/* 2806 */                           if (listdel.size() == 1)
/*      */                           {
/* 2808 */                             out.write("\n\t\t\tshowmanage=\"All\"; //NO I18N\n\t");
/*      */                           }
/* 2810 */                           out.write("\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\talert(\"");
/* 2811 */                           out.print(FormatUtil.getString("am.webclient.common.alert.manage.text"));
/* 2812 */                           out.write("\");//No i18n\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n\n\t\tif(confirm(\"");
/* 2813 */                           out.print(FormatUtil.getString("am.webclient.common.confirm.manage.text"));
/* 2814 */                           out.write("\"))\n\t\t{\n\t\t  \t");
/* 2815 */                           if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/*      */ 
/* 2818 */                             out.write("\n\t\t\t\tvar resp=it360LicenseCheck();\t\n\t\t\t\tif(resp=='false')\n\t\t\t\t{\n\t\t\t\t\treturn;\n\t\t\t\t}\t\n\t\t\t");
/*      */                           }
/* 2820 */                           out.write("\n\t\t\t\t\t\t\t\n                  document.deletemonitor.action=\"/deleteMO.do?method=manageMonitors&listview=true&viewmontype=\"+viewmontype+\"&showmanage=\"+showmanage+\"&network=\"+network;\n                  document.deletemonitor.submit();\n\t\t}\n\t}\n\t");
/* 2821 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f6.doAfterBody();
/* 2822 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2826 */                       if (_jspx_th_logic_005fnotPresent_005f6.doEndTag() == 5) {
/* 2827 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6); return;
/*      */                       }
/*      */                       
/* 2830 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f6);
/* 2831 */                       out.write("\n}\n\nvar xmlhttp = getHTTPObject(); // We create the HTTP Object\nfunction getHTTPObject() {\n  var xmlhttp;\n  if (window.ActiveXObject){\n    try {\n      xmlhttp = new ActiveXObject(\"Msxml2.XMLHTTP\");\n    } catch (e) {\n      try {\n        xmlhttp = new ActiveXObject(\"Microsoft.XMLHTTP\");\n      } catch (E) {\n        xmlhttp = false;\n      }\n    }\n}\n  else if (typeof XMLHttpRequest != 'undefined') {\n    try {\n      xmlhttp = new XMLHttpRequest();\n    } catch (e) {\n      xmlhttp = false;\n    }\n  }\n  return xmlhttp;\n}\n\n\nfunction updateAuthentication(selectname,polling)\n{\n    var polling=polling;\n    var viewmontype=\"");
/* 2832 */                       out.print(request.getParameter("viewmontype"));
/* 2833 */                       out.write("\";\n    var showmanage=\"");
/* 2834 */                       out.print(request.getParameter("showmanage"));
/* 2835 */                       out.write("\";\n\tif(viewmontype=='null')\n\t{\n\t\tviewmontype=\"All\"; //NO I18N\n\t}\n\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t{\n\t\tif(polling ==\"updatePolling\")\n\t\t{\n\t\t  alert(\"");
/* 2836 */                       out.print(FormatUtil.getString("am.webclient.common.alert.bulkupdate.polling.text"));
/* 2837 */                       out.write("\");//No i18n\n\t\t}\n\t\telse\n\t\t{\n\t\t alert(\"");
/* 2838 */                       out.print(FormatUtil.getString("am.webclient.common.alert.bulkupdate.text"));
/* 2839 */                       out.write("\");//No i18n\n\t\t}\n\t\tselectname.operation.selectedIndex=0;\n\t\treturn;\n\t}\n\telse\n\t{\n                var len = document.deletemonitor.elements.length;\n\t\tvar ml=document.deletemonitor;\n\t\tvar temp='';\n\t\tfor (var i = 0; i < len; i++) {\n\t\t    var e = document.deletemonitor.elements[i];\n\t\t    if (ml.elements[i].name == 'select') {\n                        if(ml.elements[i].checked==true)\n\t\t\t if(temp=='')\n\t\t\t{\n\t\t\t\ttemp=temp+ml.elements[i].value;\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\ttemp=temp+','+ml.elements[i].value;\n\t\t\t}\n\t\t    }\n\t\t}\n//\t\tif(confirm(\"");
/* 2840 */                       out.print(FormatUtil.getString("am.webclient.common.confirm.bulkupdate.text"));
/* 2841 */                       out.write("\"))\n//\t\t{\n         ");
/*      */                       
/* 2843 */                       String methodName = request.getParameter("method");
/* 2844 */                       String networkName = request.getParameter("network");
/* 2845 */                       String groupName = request.getParameter("group");
/*      */                       
/* 2847 */                       String isFromEnterpriseSearch = (String)request.getAttribute("isFromEnterpriseSearch");
/*      */                       
/* 2849 */                       out.write("\n\n\t\tvar isEE = ");
/* 2850 */                       out.print(isFromEnterpriseSearch);
/* 2851 */                       out.write("; \n\n\t\t var action1=\"\";\n\t\t var d=new Date();\n\t\t if(polling == 'updatePolling')\n\t\t {\n\t\t \taction1='/jsp/ChangeBulkAuthentication.jsp?values='+temp+'&isFromEnterpriseSearch='+isEE+'&methodName=");
/* 2852 */                       out.print(methodName);
/* 2853 */                       out.write("&networkName=");
/* 2854 */                       out.print(networkName);
/* 2855 */                       out.write("&groupName=");
/* 2856 */                       out.print(groupName);
/* 2857 */                       out.write("&viewmontype='+viewmontype+'&showmanage='+showmanage+'&polling=\"updatePolling\"'; //NO I18N\n\t\t   MM_openBrWindow(action1+'&'+d,'secondWindow','resizable=yes,scrollbars=yes,width=450,height=200,top=300,left=300');//NO I18N\n\t\t }\n\t\t else\n\t\t {\n\t\t \taction1='/jsp/ChangeBulkAuthentication.jsp?values='+temp+'&isFromEnterpriseSearch='+isEE+'&methodName=");
/* 2858 */                       out.print(methodName);
/* 2859 */                       out.write("&networkName=");
/* 2860 */                       out.print(networkName);
/* 2861 */                       out.write("&groupName=");
/* 2862 */                       out.print(groupName);
/* 2863 */                       out.write("&viewmontype='+viewmontype+'&showmanage='+showmanage ; //NO I18N; //NO I18N\n\t\t   MM_openBrWindow(action1+'&'+d,'secondWindow','resizable=yes,scrollbars=yes,width=680,height=320,top=200,left=300');//NO I18N\n\t\t }\n\n//\t\t}\n\t}\n\n}\nfunction copyPasteMO(selectname)\n{\n\t");
/* 2864 */                       if (_jspx_meth_logic_005fpresent_005f7(_jspx_page_context))
/*      */                         return;
/* 2866 */                       out.write(10);
/* 2867 */                       out.write(9);
/*      */                       
/* 2869 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f7 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2870 */                       _jspx_th_logic_005fnotPresent_005f7.setPageContext(_jspx_page_context);
/* 2871 */                       _jspx_th_logic_005fnotPresent_005f7.setParent(null);
/*      */                       
/* 2873 */                       _jspx_th_logic_005fnotPresent_005f7.setRole("DEMO");
/* 2874 */                       int _jspx_eval_logic_005fnotPresent_005f7 = _jspx_th_logic_005fnotPresent_005f7.doStartTag();
/* 2875 */                       if (_jspx_eval_logic_005fnotPresent_005f7 != 0) {
/*      */                         for (;;) {
/* 2877 */                           out.write("\n\t\tif(!checkforOneSelected(document.deletemonitor,\"select\"))\n\t\t{\n\t\t\talert(\"");
/* 2878 */                           out.print(FormatUtil.getString("am.webclient.alert.copypastemonitor.select.text"));
/* 2879 */                           out.write("\"); //NO I18N\n\t\t\tselectname.operation.selectedIndex=0;\n\t\t\treturn;\n\t\t}\n\t\tvar len = document.deletemonitor.select.length;\n\t\tvar temp=0;\n\t\tvar resid=\"\";\n\t\tif(len>0)\n\t\t{\n\t\t\tfor( i=0; i<len; i++)\n\t\t\t{\n\t\t\t\tif(document.deletemonitor.select[i].checked)\n\t\t\t\t{\n\t\t\t\t\ttemp = temp + 1; //NO I18N\n\t\t\t\t\tif(temp==1)\n\t\t\t\t\t\tresid=document.deletemonitor.select[i].value; //NO I18N\n\t\t\t\t}\n\t\t\t}\n\t\t\tif(temp==1)\n\t\t\t{\n\t\t\t\tdocument.deletemonitor.action=\"/jsp/CopyMonitor.jsp?resourceid=\"+resid;\n\t\t\t\twindow.open(document.deletemonitor.action,'Password','resizable=yes,scrollbars=yes,width=650,height=300,top=250,left=275');\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\talert(\"");
/* 2880 */                           out.print(FormatUtil.getString("am.webclient.alert.copypastemonitor.select.onlyone.text"));
/* 2881 */                           out.write("\"); //NO I18N\n\t\t\t\tselectname.operation.selectedIndex=0;\n\t\t\t\treturn;\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.deletemonitor.action=\"/jsp/CopyMonitor.jsp?resourceid=\"+document.deletemonitor.select.value;\n\t\t\twindow.open(document.deletemonitor.action,'Password','resizable=yes,scrollbars=yes,width=650,height=300,top=250,left=275');\n\t\t}\n\t");
/* 2882 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f7.doAfterBody();
/* 2883 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2887 */                       if (_jspx_th_logic_005fnotPresent_005f7.doEndTag() == 5) {
/* 2888 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7); return;
/*      */                       }
/*      */                       
/* 2891 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f7);
/* 2892 */                       out.write("\n}\nvar scriptmanager;\nfunction openScriptManager(name,tab)\n{\n\tscriptmanager = window.open('/jsp/RBMScriptManager.jsp?from=urlseqdetails&bcname='+name+'&tab='+tab,'Application_ManagerRBM_Script_Manager','toolbar=no,status=no,menubar=no,width=1000,height=650,scrollbars=yes,location=no');// NO I18N\n\n}\n</script>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n<body onLoad=\"javascript:myOnLoad()\"></body>\n");
/* 2893 */                       ManagedApplication mo = null;
/* 2894 */                       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 2);
/* 2895 */                       if (mo == null) {
/* 2896 */                         mo = new ManagedApplication();
/* 2897 */                         _jspx_page_context.setAttribute("mo", mo, 2);
/*      */                       }
/* 2899 */                       out.write(10);
/* 2900 */                       Hashtable motypedisplaynames = null;
/* 2901 */                       synchronized (application) {
/* 2902 */                         motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2903 */                         if (motypedisplaynames == null) {
/* 2904 */                           motypedisplaynames = new Hashtable();
/* 2905 */                           _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                         }
/*      */                       }
/* 2908 */                       out.write(10);
/* 2909 */                       Hashtable availabilitykeys = null;
/* 2910 */                       synchronized (application) {
/* 2911 */                         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2912 */                         if (availabilitykeys == null) {
/* 2913 */                           availabilitykeys = new Hashtable();
/* 2914 */                           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                         }
/*      */                       }
/* 2917 */                       out.write(10);
/* 2918 */                       Hashtable healthkeys = null;
/* 2919 */                       synchronized (application) {
/* 2920 */                         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2921 */                         if (healthkeys == null) {
/* 2922 */                           healthkeys = new Hashtable();
/* 2923 */                           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                         }
/*      */                       }
/* 2926 */                       out.write(10);
/* 2927 */                       out.write(10);
/*      */                       
/*      */ 
/* 2930 */                       String isEnterpriseSearch = request.getParameter("isFromEnterpriseSearch");
/*      */                       
/* 2932 */                       Object operPerm = new Hashtable();
/* 2933 */                       ArrayList<ArrayList<String>> globalslist = mo.getRows("select NAME,VALUE from AM_GLOBALCONFIG where NAME in ('allowOperatorEdit','allowOperatorManage','allowOperatorUnmanageAndReset','allowOperatorUpdateIP')");
/* 2934 */                       for (ArrayList<String> perm : globalslist) {
/* 2935 */                         ((Hashtable)operPerm).put(perm.get(0), perm.get(1));
/*      */                       }
/* 2937 */                       HashMap<String, ArrayList> groupList = new HashMap();
/* 2938 */                       pageContext.setAttribute("allowEdit", ((Hashtable)operPerm).get("allowOperatorEdit"));
/* 2939 */                       pageContext.setAttribute("allowUpdateIP", ((Hashtable)operPerm).get("allowOperatorUpdateIP"));
/* 2940 */                       pageContext.setAttribute("allowManage", ((Hashtable)operPerm).get("allowOperatorManage"));
/* 2941 */                       pageContext.setAttribute("allowReset", ((Hashtable)operPerm).get("allowOperatorUnmanageAndReset"));
/* 2942 */                       String totquery = (String)request.getAttribute("totalmonitors");
/*      */                       try
/*      */                       {
/* 2945 */                         String actionPath = "";
/* 2946 */                         int totalObjCount = 0;
/* 2947 */                         ArrayList list = (ArrayList)request.getAttribute("resourcetable");
/*      */                         
/*      */ 
/*      */ 
/* 2951 */                         ArrayList agentList = (ArrayList)request.getAttribute("rbmagenttable");
/* 2952 */                         ArrayList webScripts = new ArrayList();
/* 2953 */                         ArrayList resIDs = new ArrayList();
/*      */                         
/* 2955 */                         for (int j = 0; (agentList != null) && (j < agentList.size()); j++)
/*      */                         {
/* 2957 */                           ArrayList row = (ArrayList)agentList.get(j);
/* 2958 */                           String resourceid = (String)row.get(0);
/* 2959 */                           String scr = (String)row.get(1);
/* 2960 */                           resIDs.add(resourceid);
/* 2961 */                           webScripts.add(scr);
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2969 */                         String resourceName = request.getParameter("network");
/* 2970 */                         String network = request.getParameter("network");
/* 2971 */                         String group = request.getParameter("group");
/* 2972 */                         if (group != null)
/*      */                         {
/* 2974 */                           resourceName = group;
/*      */                         }
/* 2976 */                         String applicationName = (String)request.getAttribute("appName");
/* 2977 */                         String haid = (String)request.getAttribute("haid");
/* 2978 */                         boolean displayresourcetype = false;
/*      */                         
/* 2980 */                         int rowcount = list.size();
/* 2981 */                         String eumChildString = null;
/*      */                         
/* 2983 */                         if (request.getAttribute("eumChildString") != null) {
/* 2984 */                           eumChildString = (String)request.getAttribute("eumChildString");
/*      */                         } else {
/* 2986 */                           eumChildString = com.adventnet.appmanager.util.Constants.getEUMChildString();
/*      */                         }
/* 2988 */                         String eumChildListCond = " AM_ManagedObject.RESOURCEID NOT IN (" + eumChildString + ")";
/* 2989 */                         ArrayList tags = null;
/*      */                         
/* 2991 */                         out.write("\n\t\n\n<form action=\"/deleteMO.do?\" name=\"deletemonitor\" method=\"post\" style=\"display:inline\">\n\n\t");
/*      */                         
/* 2993 */                         String EnterpriseSearch = "false";
/* 2994 */                         String isEE = (String)request.getAttribute("isFromEnterpriseSearch");
/* 2995 */                         if (isEE != null)
/*      */                         {
/* 2997 */                           EnterpriseSearch = "true";
/*      */                         }
/*      */                         
/* 3000 */                         out.write("\n\t<input type=\"hidden\" id=\"oldtab\" name=\"oldtab\" value='");
/* 3001 */                         out.print(request.getParameter("oldtab"));
/* 3002 */                         out.write("'/>\n\t<input type=\"hidden\" name=\"isFromEnterpriseSearch\" value='");
/* 3003 */                         out.print(EnterpriseSearch);
/* 3004 */                         out.write("'></input>\n\n\t<input type=\"hidden\" name=\"method\" value=\"deleteMO\"></input>\n");
/* 3005 */                         if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                           return;
/* 3007 */                         out.write("\n    <div id=\"it360licensestatus\" style=\"width:98%;display:none;\"  >\n</div>\n\n\t\t");
/*      */                         
/* 3009 */                         String tempSelectedTab = EnterpriseUtil.getSelectedTab(request);
/* 3010 */                         if ((tempSelectedTab == null) || (!tempSelectedTab.equals("Networks")))
/*      */                         {
/*      */ 
/* 3013 */                           out.write("\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtborder\">\n        <tr>\n\t\t\t<td align=\"left\" height=\"26\" class=\"bodytextbold tableheadingbborder\" style=\"height:50px;\">\n\t\t\t");
/*      */                           
/* 3015 */                           if (list.size() > 0)
/*      */                           {
/*      */ 
/* 3018 */                             out.write("\n\t\t\t");
/* 3019 */                             out.write("<!--$Id$-->\n<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n ");
/*      */                             
/* 3021 */                             PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3022 */                             _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 3023 */                             _jspx_th_logic_005fpresent_005f8.setParent(null);
/*      */                             
/* 3025 */                             _jspx_th_logic_005fpresent_005f8.setRole("DEMO,ADMIN");
/* 3026 */                             int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 3027 */                             if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */                               for (;;) {
/* 3029 */                                 out.write("&nbsp;<span class=\"filterby-txt input-uptime\">");
/* 3030 */                                 out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 3031 */                                 out.write(" :</span>\n                 <select style=\"width:220px\"  data-placeholder=\"");
/* 3032 */                                 out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 3033 */                                 out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">\n                     <option value=\"\">   </option>\n\t\t\t\t\t <option value=\"customField\">");
/* 3034 */                                 out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 3035 */                                 out.write("</option>\n                     <option value=\"Delete\">");
/* 3036 */                                 out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3037 */                                 out.write("</option>\n\t\t\t");
/* 3038 */                                 if ((request.getParameter("method").equals("showMonitorGroupView")) || (request.getParameter("method").equals("showSubGroupTree"))) {
/* 3039 */                                   out.write("\n\t\t     <option VALUE=\"Disable Actions\">");
/* 3040 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.disableaction.text"));
/* 3041 */                                   out.write("</option>\n\t\t     <option VALUE=\"Enable Actions\">");
/* 3042 */                                   out.print(FormatUtil.getString("am.webclient.monitorgroupview.enableaction.text"));
/* 3043 */                                   out.write("  </option>\n\n");
/*      */                                 }
/*      */                                 
/*      */ 
/*      */ 
/* 3048 */                                 if (!request.getParameter("method").equals("showMonitorGroupView"))
/*      */                                 {
/* 3050 */                                   if (!"VirtualMachine".equals(resourceName))
/*      */                                   {
/*      */ 
/*      */ 
/* 3054 */                                     out.write("\n                     <option value=\"editDisplayNames\">");
/* 3055 */                                     out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 3056 */                                     out.write("</option>\n                    ");
/*      */                                     
/* 3058 */                                     if (!request.getParameter("method").equals("showSubGroupTree"))
/*      */                                     {
/* 3060 */                                       out.write("\n\t\t\t\t\t\t<option value=\"updateIP\">");
/* 3061 */                                       out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 3062 */                                       out.write("</option>\n\t\t\t\t\t");
/*      */                                     }
/* 3064 */                                     out.write("\n\t\t\t");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/* 3069 */                                 out.write("\n\t\t     <option value=\"Manage\">");
/* 3070 */                                 out.print(FormatUtil.getString("Manage"));
/* 3071 */                                 out.write("</option>\n             <option value=\"Unmanage\">");
/* 3072 */                                 out.print(FormatUtil.getString("UnManage"));
/* 3073 */                                 out.write("</option>\n             <option value=\"unManageAndReset\">");
/* 3074 */                                 out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 3075 */                                 out.write("</option>\n\t\t\t");
/*      */                                 
/* 3077 */                                 if (!"VirtualMachine".equals(resourceName))
/*      */                                 {
/*      */ 
/* 3080 */                                   out.write("\n                     <option value=\"updateauth\">");
/* 3081 */                                   out.print(FormatUtil.getString("am.webclient.updateauthentication.text"));
/* 3082 */                                   out.write("</option>\n                     <option value=\"updatepoll\">");
/* 3083 */                                   out.print(FormatUtil.getString("am.webclient.updatepolling.text"));
/* 3084 */                                   out.write("</option>\n\t\t\t");
/*      */                                 }
/*      */                                 
/* 3087 */                                 if ((!request.getParameter("method").equals("showMonitorGroupView")) && (!request.getParameter("method").equals("showSubGroupTree")))
/*      */                                 {
/* 3089 */                                   out.write("\n                     <option value=\"copyPaste\">");
/* 3090 */                                   out.print(FormatUtil.getString("am.webclient.copypaste.headtitle"));
/* 3091 */                                   out.write("</option>\n\n\t\t\t");
/*      */                                 }
/* 3093 */                                 out.write("\n                 </select>\n            ");
/* 3094 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 3095 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3099 */                             if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 3100 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8); return;
/*      */                             }
/*      */                             
/* 3103 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 3104 */                             out.write("\n\t\t\t");
/*      */                             
/* 3106 */                             PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3107 */                             _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 3108 */                             _jspx_th_logic_005fpresent_005f9.setParent(null);
/*      */                             
/* 3110 */                             _jspx_th_logic_005fpresent_005f9.setRole("ENTERPRISEADMIN");
/* 3111 */                             int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 3112 */                             if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */                               for (;;) {
/* 3114 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 3116 */                                 ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3117 */                                 _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3118 */                                 _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fpresent_005f9);
/* 3119 */                                 int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3120 */                                 if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                                   for (;;) {
/* 3122 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 3124 */                                     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3125 */                                     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3126 */                                     _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                                     
/* 3128 */                                     _jspx_th_c_005fwhen_005f0.setTest("${param.method eq 'showMonitorGroupView'}");
/* 3129 */                                     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3130 */                                     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                       for (;;) {
/* 3132 */                                         out.write("\n\t\t\t <select style=\"width:220px\"  data-placeholder=\"");
/* 3133 */                                         out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 3134 */                                         out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">");
/* 3135 */                                         out.write("\n\t\t\t <option value=\"\">   </option>\n\t\t\t <option value=\"Manage\">");
/* 3136 */                                         out.print(FormatUtil.getString("Manage"));
/* 3137 */                                         out.write("</option>");
/* 3138 */                                         out.write("\n\t\t\t <option value=\"Unmanage\">");
/* 3139 */                                         out.print(FormatUtil.getString("UnManage"));
/* 3140 */                                         out.write("</option>");
/* 3141 */                                         out.write("\n\t\t\t <option value=\"unManageAndReset\">");
/* 3142 */                                         out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 3143 */                                         out.write("</option>");
/* 3144 */                                         out.write("\n\t\t\t </select>\n\t\t\t");
/* 3145 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3146 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3150 */                                     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3151 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                                     }
/*      */                                     
/* 3154 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3155 */                                     out.write("\n\t\t\t");
/* 3156 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3157 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3161 */                                 if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3162 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                                 }
/*      */                                 
/* 3165 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3166 */                                 out.write("\n\t\t\t");
/* 3167 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 3168 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3172 */                             if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 3173 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9); return;
/*      */                             }
/*      */                             
/* 3176 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 3177 */                             out.write("\n\n    ");
/*      */                             
/* 3179 */                             PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3180 */                             _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 3181 */                             _jspx_th_logic_005fpresent_005f10.setParent(null);
/*      */                             
/* 3183 */                             _jspx_th_logic_005fpresent_005f10.setRole("OPERATOR");
/* 3184 */                             int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 3185 */                             if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */                               for (;;) {
/* 3187 */                                 out.write(" \n                ");
/*      */                                 
/* 3189 */                                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3190 */                                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 3191 */                                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fpresent_005f10);
/* 3192 */                                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 3193 */                                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                   for (;;) {
/* 3195 */                                     out.write("\n                    ");
/*      */                                     
/* 3197 */                                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3198 */                                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 3199 */                                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                     
/* 3201 */                                     _jspx_th_c_005fwhen_005f1.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' || allowReset=='true'}");
/* 3202 */                                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 3203 */                                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                       for (;;) {
/* 3205 */                                         out.write("&nbsp<span class=\"filterby-txt input-uptime\">");
/* 3206 */                                         out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 3207 */                                         out.write(" :</span>\n                        <select style=\"width:220px\" data-placeholder=\"");
/* 3208 */                                         out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 3209 */                                         out.write("\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\"  name=\"operation\">\n                            <option VALUE=\"selectactions\" selected=true></option>\n                            ");
/*      */                                         
/* 3211 */                                         ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3212 */                                         _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3213 */                                         _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fwhen_005f1);
/* 3214 */                                         int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3215 */                                         if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                           for (;;) {
/* 3217 */                                             out.write("\n\t                   \t\t\t ");
/*      */                                             
/* 3219 */                                             WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3220 */                                             _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3221 */                                             _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                             
/* 3223 */                                             _jspx_th_c_005fwhen_005f2.setTest("${allowEdit=='true'}");
/* 3224 */                                             int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3225 */                                             if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                               for (;;) {
/* 3227 */                                                 out.write("\n\t                            \t<option value=\"editDisplayNames\">");
/* 3228 */                                                 out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 3229 */                                                 out.write("</option>\n\t                             ");
/* 3230 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3231 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3235 */                                             if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3236 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                             }
/*      */                                             
/* 3239 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3240 */                                             out.write("\n                \t\t\t");
/* 3241 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3242 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3246 */                                         if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3247 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                         }
/*      */                                         
/* 3250 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3251 */                                         out.write("\n                \t\t\t");
/*      */                                         
/* 3253 */                                         ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3254 */                                         _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3255 */                                         _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fwhen_005f1);
/* 3256 */                                         int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3257 */                                         if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                                           for (;;) {
/* 3259 */                                             out.write("\n\t                   \t\t\t ");
/*      */                                             
/* 3261 */                                             WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3262 */                                             _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3263 */                                             _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                             
/* 3265 */                                             _jspx_th_c_005fwhen_005f3.setTest("${allowUpdateIP=='true'}");
/* 3266 */                                             int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3267 */                                             if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                               for (;;) {
/* 3269 */                                                 out.write("\n\t\t\t\t\t\t\t\t\t <option value=\"updateIP\">");
/* 3270 */                                                 out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 3271 */                                                 out.write("</option>\n\t                             ");
/* 3272 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3273 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3277 */                                             if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3278 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                                             }
/*      */                                             
/* 3281 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3282 */                                             out.write("\n                \t\t\t");
/* 3283 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3284 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3288 */                                         if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3289 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                                         }
/*      */                                         
/* 3292 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3293 */                                         out.write("\n                \t\t\t");
/*      */                                         
/* 3295 */                                         ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3296 */                                         _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3297 */                                         _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_c_005fwhen_005f1);
/* 3298 */                                         int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3299 */                                         if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                                           for (;;) {
/* 3301 */                                             out.write("\n\t                   \t\t\t ");
/*      */                                             
/* 3303 */                                             WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3304 */                                             _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3305 */                                             _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                                             
/* 3307 */                                             _jspx_th_c_005fwhen_005f4.setTest("${allowManage=='true' || allowReset=='true'}");
/* 3308 */                                             int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3309 */                                             if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                                               for (;;) {
/* 3311 */                                                 out.write("\n\t                            \t<option value=\"Manage\">");
/* 3312 */                                                 out.print(FormatUtil.getString("Manage"));
/* 3313 */                                                 out.write("</option>\n                            \t\t<option value=\"Unmanage\">");
/* 3314 */                                                 out.print(FormatUtil.getString("UnManage"));
/* 3315 */                                                 out.write("</option>\n\t                             ");
/* 3316 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3317 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3321 */                                             if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3322 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                                             }
/*      */                                             
/* 3325 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3326 */                                             out.write("\n\t\t\t\t\t");
/* 3327 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3328 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3332 */                                         if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3333 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                                         }
/*      */                                         
/* 3336 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3337 */                                         out.write("\n                \t\t\t");
/*      */                                         
/* 3339 */                                         ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3340 */                                         _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 3341 */                                         _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fwhen_005f1);
/* 3342 */                                         int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 3343 */                                         if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                                           for (;;) {
/* 3345 */                                             out.write("\n\t\t\t\t\t");
/*      */                                             
/* 3347 */                                             WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3348 */                                             _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 3349 */                                             _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                             
/* 3351 */                                             _jspx_th_c_005fwhen_005f5.setTest("${allowReset=='true'}");
/* 3352 */                                             int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 3353 */                                             if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                               for (;;) {
/* 3355 */                                                 out.write("\n\t\t\t\t\t <option value=\"unManageAndReset\">");
/* 3356 */                                                 out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 3357 */                                                 out.write("</option>");
/* 3358 */                                                 out.write("\n\t\t\t\t\t");
/* 3359 */                                                 int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 3360 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 3364 */                                             if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 3365 */                                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                                             }
/*      */                                             
/* 3368 */                                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 3369 */                                             out.write("\n                \t\t\t");
/* 3370 */                                             int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 3371 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 3375 */                                         if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 3376 */                                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                                         }
/*      */                                         
/* 3379 */                                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 3380 */                                         out.write("\n                        </select>\n                    ");
/* 3381 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 3382 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3386 */                                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 3387 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                     }
/*      */                                     
/* 3390 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 3391 */                                     out.write("\n                ");
/* 3392 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 3393 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3397 */                                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 3398 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                                 }
/*      */                                 
/* 3401 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3402 */                                 out.write("\n            ");
/* 3403 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 3404 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3408 */                             if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 3409 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10); return;
/*      */                             }
/*      */                             
/* 3412 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 3413 */                             out.write("\n          \n");
/* 3414 */                             out.write("&nbsp;&nbsp;\n            ");
/*      */                           }
/*      */                           
/* 3417 */                           com.adventnet.appmanager.customfields.MyFields fields = new com.adventnet.appmanager.customfields.MyFields();
/* 3418 */                           tags = com.adventnet.appmanager.customfields.MyFields.getDollarTags(false, "All", request, false);
/* 3419 */                           String selectedValue = "All";
/* 3420 */                           String customcolor = "";
/* 3421 */                           if ((request.getParameter("assignCustomValues") != null) && (!request.getParameter("assignCustomValues").equals("true")) && (!request.getParameter("assignCustomValues").equals("null")))
/*      */                           {
/* 3423 */                             if ((request.getParameter("customValue") != null) && (!request.getParameter("customValue").equals("null")))
/*      */                             {
/* 3425 */                               if (request.getParameter("customValue").contains("$"))
/*      */                               {
/* 3427 */                                 selectedValue = request.getParameter("customValue").substring(0, request.getParameter("customValue").indexOf("$"));
/*      */                               }
/*      */                               else
/*      */                               {
/* 3431 */                                 selectedValue = request.getParameter("customValue");
/*      */                               }
/* 3433 */                               customcolor = "background-color:#FFF8C6;";
/*      */                             }
/*      */                           }
/* 3436 */                           else if ((("showResourceTypesAll".equals(request.getParameter("method"))) || ("showResourceTypes".equals(request.getParameter("method")))) && (request.getParameter("group") != null))
/*      */                           {
/* 3438 */                             selectedValue = request.getParameter("group");
/*      */                           }
/*      */                           else
/*      */                           {
/* 3442 */                             selectedValue = request.getParameter("network");
/*      */                           }
/*      */                           
/* 3445 */                           if ((!isTablet) && (rowcount > 0) && (isEnterpriseSearch == null))
/*      */                           {
/*      */ 
/* 3448 */                             out.write("\n\t\t\t");
/*      */                             
/* 3450 */                             NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 3451 */                             _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 3452 */                             _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                             
/* 3454 */                             _jspx_th_logic_005fnotEmpty_005f0.setName("listviewresourcetype");
/* 3455 */                             int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 3456 */                             if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                               for (;;) {
/* 3458 */                                 out.write("\n\t\t\t<span class=\"filterby-txt input-uptime\">\n\t\t\t");
/* 3459 */                                 out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 3460 */                                 out.write("&nbsp;:&nbsp;\n\t\t\t</span>\n\t\t\t");
/*      */                                 
/* 3462 */                                 SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 3463 */                                 _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 3464 */                                 _jspx_th_html_005fselect_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                                 
/* 3466 */                                 _jspx_th_html_005fselect_005f0.setProperty("type");
/*      */                                 
/* 3468 */                                 _jspx_th_html_005fselect_005f0.setValue(selectedValue);
/*      */                                 
/* 3470 */                                 _jspx_th_html_005fselect_005f0.setOnchange("javascript:loadURLType(this.options[this.selectedIndex].value,this.form,this,'customFieldValuesFilterby');");
/*      */                                 
/* 3472 */                                 _jspx_th_html_005fselect_005f0.setStyleClass("chzn-select");
/*      */                                 
/* 3474 */                                 _jspx_th_html_005fselect_005f0.setStyle(customcolor + "width:220px");
/*      */                                 
/* 3476 */                                 _jspx_th_html_005fselect_005f0.setTabindex("5");
/* 3477 */                                 int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 3478 */                                 if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 3479 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3480 */                                     out = _jspx_page_context.pushBody();
/* 3481 */                                     _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 3482 */                                     _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3485 */                                     out.write(" \n\t\t\t\t");
/*      */                                     
/* 3487 */                                     IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 3488 */                                     _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 3489 */                                     _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3491 */                                     _jspx_th_logic_005fiterate_005f0.setName("listviewresourcetype");
/*      */                                     
/* 3493 */                                     _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                                     
/* 3495 */                                     _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                     
/* 3497 */                                     _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 3498 */                                     int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 3499 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 3500 */                                       ArrayList row = null;
/* 3501 */                                       Integer j = null;
/* 3502 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3503 */                                         out = _jspx_page_context.pushBody();
/* 3504 */                                         _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 3505 */                                         _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                       }
/* 3507 */                                       row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3508 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                       for (;;) {
/* 3510 */                                         out.write("\n\t\t\t\t");
/*      */                                         
/*      */ 
/*      */ 
/* 3514 */                                         boolean hasresourceGroup = false;
/* 3515 */                                         for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                                         {
/* 3517 */                                           if (com.adventnet.appmanager.util.Constants.categoryLink[i].equals((String)row.get(1)))
/*      */                                           {
/* 3519 */                                             hasresourceGroup = true;
/* 3520 */                                             break;
/*      */                                           }
/*      */                                         }
/* 3523 */                                         if (!com.adventnet.appmanager.util.Constants.sqlManager) { String style;
/* 3524 */                                           String style; if (((String)row.get(1) != null) && ((((String)row.get(1)).equals("All")) || (hasresourceGroup)))
/*      */                                           {
/* 3526 */                                             style = "font-weight:bold;font-size:11px;";
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 3531 */                                             style = "font-size:11px;";
/*      */                                           }
/*      */                                           
/*      */ 
/* 3535 */                                           out.write("\n\t\t\t\t\t");
/*      */                                           
/* 3537 */                                           OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3538 */                                           _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 3539 */                                           _jspx_th_html_005foption_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                           
/* 3541 */                                           _jspx_th_html_005foption_005f0.setValue((String)row.get(1));
/*      */                                           
/* 3543 */                                           _jspx_th_html_005foption_005f0.setStyle(style);
/* 3544 */                                           int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 3545 */                                           if (_jspx_eval_html_005foption_005f0 != 0) {
/* 3546 */                                             if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3547 */                                               out = _jspx_page_context.pushBody();
/* 3548 */                                               _jspx_th_html_005foption_005f0.setBodyContent((BodyContent)out);
/* 3549 */                                               _jspx_th_html_005foption_005f0.doInitBody();
/*      */                                             }
/*      */                                             for (;;) {
/* 3552 */                                               out.print(row.get(0));
/* 3553 */                                               int evalDoAfterBody = _jspx_th_html_005foption_005f0.doAfterBody();
/* 3554 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 3557 */                                             if (_jspx_eval_html_005foption_005f0 != 1) {
/* 3558 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 3561 */                                           if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 3562 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                           }
/*      */                                           
/* 3565 */                                           this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f0);
/* 3566 */                                           out.write("\n\t\t\t\t");
/*      */                                         } else {
/* 3568 */                                           if (((String)row.get(1) != null) && (((String)row.get(1)).equals("All")))
/*      */                                           {
/* 3570 */                                             String style = "font-weight:bold; font-size:11px;";
/*      */                                             
/* 3572 */                                             out.write("\n\t\t\t\t\t\t\t");
/*      */                                             
/* 3574 */                                             OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 3575 */                                             _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 3576 */                                             _jspx_th_html_005foption_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                             
/* 3578 */                                             _jspx_th_html_005foption_005f1.setValue((String)row.get(1));
/*      */                                             
/* 3580 */                                             _jspx_th_html_005foption_005f1.setStyle(style);
/* 3581 */                                             int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 3582 */                                             if (_jspx_eval_html_005foption_005f1 != 0) {
/* 3583 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3584 */                                                 out = _jspx_page_context.pushBody();
/* 3585 */                                                 _jspx_th_html_005foption_005f1.setBodyContent((BodyContent)out);
/* 3586 */                                                 _jspx_th_html_005foption_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 3589 */                                                 out.print(row.get(0));
/* 3590 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f1.doAfterBody();
/* 3591 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 3594 */                                               if (_jspx_eval_html_005foption_005f1 != 1) {
/* 3595 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 3598 */                                             if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 3599 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                             }
/*      */                                             
/* 3602 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f1);
/* 3603 */                                             out.write("\n\t\t\t\t\t\t\t");
/*      */                                           }
/*      */                                           
/*      */ 
/* 3607 */                                           out.write("\t\t\t\t\t\n\t\t\t\t");
/*      */                                         }
/* 3609 */                                         out.write("\n\t\t\t");
/* 3610 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3611 */                                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 3612 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 3613 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3616 */                                       if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3617 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3620 */                                     if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3621 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                     }
/*      */                                     
/* 3624 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3625 */                                     out.write("\n\t\t\t\t");
/*      */                                     
/* 3627 */                                     String customField = request.getParameter("customField");
/* 3628 */                                     request.setAttribute("tags", tags);
/* 3629 */                                     String selected = "";
/* 3630 */                                     int k = 0;
/*      */                                     
/* 3632 */                                     out.write("\n                <optgroup label=\"");
/* 3633 */                                     out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 3634 */                                     out.write("\" >\n\t\t\t\t");
/*      */                                     
/* 3636 */                                     ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 3637 */                                     _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 3638 */                                     _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                     
/* 3640 */                                     _jspx_th_c_005fforEach_005f0.setVar("eachtag");
/*      */                                     
/* 3642 */                                     _jspx_th_c_005fforEach_005f0.setItems("${tags}");
/* 3643 */                                     int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                     try {
/* 3645 */                                       int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 3646 */                                       if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                         for (;;) {
/* 3648 */                                           out.write("\n\t\t\t\t");
/*      */                                           
/* 3650 */                                           if ((selectedValue != null) && (selectedValue.equals(((Properties)tags.get(k)).getProperty("labelalias"))))
/*      */                                           {
/* 3652 */                                             selected = "selected";
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 3656 */                                             selected = "";
/*      */                                           }
/* 3658 */                                           k++;
/*      */                                           
/* 3660 */                                           out.write("\n                <option ");
/* 3661 */                                           out.print(selected);
/* 3662 */                                           out.write(" value=\"");
/* 3663 */                                           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3683 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3684 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3665 */                                           out.write(34);
/* 3666 */                                           out.write(62);
/* 3667 */                                           if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3683 */                                             _jspx_th_c_005fforEach_005f0.doFinally();
/* 3684 */                                             this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                           }
/* 3669 */                                           out.write("</option>\n\t\t\t\t");
/* 3670 */                                           int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 3671 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3675 */                                       if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3683 */                                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 3684 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                       }
/*      */                                     }
/*      */                                     catch (Throwable _jspx_exception)
/*      */                                     {
/*      */                                       for (;;)
/*      */                                       {
/* 3679 */                                         int tmp8936_8935 = 0; int[] tmp8936_8933 = _jspx_push_body_count_c_005fforEach_005f0; int tmp8938_8937 = tmp8936_8933[tmp8936_8935];tmp8936_8933[tmp8936_8935] = (tmp8938_8937 - 1); if (tmp8938_8937 <= 0) break;
/* 3680 */                                         out = _jspx_page_context.popBody(); }
/* 3681 */                                       _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                     } finally {
/* 3683 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 3684 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                     }
/* 3686 */                                     out.write("\n\t\t\t\t</optgroup>\n\t\t\t\t");
/* 3687 */                                     int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 3688 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3691 */                                   if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 3692 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3695 */                                 if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 3696 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                 }
/*      */                                 
/* 3699 */                                 this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 3700 */                                 out.write("\n\t\t\t\t");
/* 3701 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 3702 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 3706 */                             if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 3707 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0); return;
/*      */                             }
/*      */                             
/* 3710 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 3711 */                             out.write("\n\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3715 */                           out.write("\n\t\t\t\t&nbsp;&nbsp;\n\t\t       <span id=\"customFieldValuesFilterby\"></span></td>\n       \t\t\t");
/*      */                           
/* 3717 */                           String monTypeAtt = "All";
/* 3718 */                           if (request.getParameter("viewmontype") != null)
/*      */                           {
/* 3720 */                             monTypeAtt = request.getParameter("viewmontype");
/*      */                           }
/* 3722 */                           List unmanagednodes = new ArrayList();
/*      */                           
/* 3724 */                           String resIds = "";
/*      */                           
/*      */ 
/* 3727 */                           out.write("\n\t\t\t");
/*      */                           
/* 3729 */                           String selectedNetwork = request.getParameter("selectedNetwork");
/* 3730 */                           String method = request.getParameter("method");
/* 3731 */                           String customValue = request.getParameter("customValue");
/* 3732 */                           String queries = request.getParameter("query");
/* 3733 */                           String viewMonitorType = request.getParameter("viewmontype");
/* 3734 */                           String showManage1 = request.getParameter("showmanage");
/* 3735 */                           String detailspage = request.getParameter("detailspage");
/* 3736 */                           String tileName = request.getParameter("quickLinks");
/* 3737 */                           String type = request.getParameter("type");
/* 3738 */                           String oldtab = request.getParameter("oldtab");
/* 3739 */                           String sortBy = request.getParameter("sortBy");
/* 3740 */                           String orderBy = request.getParameter("orderBy");
/* 3741 */                           actionPath = "/showresource.do?method=" + method;
/*      */                           
/* 3743 */                           if (group != null)
/*      */                           {
/* 3745 */                             actionPath = actionPath + "&group=" + group;
/*      */                           }
/* 3747 */                           if ((resourceName != null) && (!resourceName.equals("null")))
/*      */                           {
/* 3749 */                             actionPath = actionPath + "&network=" + resourceName;
/*      */                           }
/* 3751 */                           if (selectedNetwork != null)
/*      */                           {
/* 3753 */                             actionPath = actionPath + "&selectedNetwork=" + selectedNetwork;
/*      */                           }
/* 3755 */                           if (haid != null)
/*      */                           {
/* 3757 */                             actionPath = actionPath + "&haid=" + haid;
/*      */                           }
/* 3759 */                           if (customValue != null)
/*      */                           {
/* 3761 */                             actionPath = actionPath + "&customValue=" + customValue;
/*      */                           }
/* 3763 */                           if (oldtab != null)
/*      */                           {
/* 3765 */                             actionPath = actionPath + "&oldtab=" + oldtab;
/*      */                           }
/* 3767 */                           if (viewMonitorType != null)
/*      */                           {
/* 3769 */                             actionPath = actionPath + "&viewmontype=" + viewMonitorType;
/*      */                           }
/* 3771 */                           if (showManage1 != null)
/*      */                           {
/* 3773 */                             actionPath = actionPath + "&showmanage=" + showManage1;
/*      */                           }
/*      */                           
/* 3776 */                           if (detailspage != null)
/*      */                           {
/* 3778 */                             actionPath = actionPath + "&detailspage=" + detailspage;
/*      */                           }
/*      */                           
/* 3781 */                           if (sortBy != null)
/*      */                           {
/* 3783 */                             actionPath = actionPath + "&sortBy=" + sortBy;
/*      */                           }
/*      */                           
/* 3786 */                           if (orderBy != null)
/*      */                           {
/* 3788 */                             actionPath = actionPath + "&orderBy=" + orderBy;
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/* 3793 */                           out.write("\n\t\t\t<td colspan=\"7\"  height=\"26\"  class=\"bodytext tableheadingbborder\" align=\"right\" nowrap=\"nowrap\" style=\"padding-right:5px\">\n\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\">\n    \t\t\t<tr>\n        \t\t\t<td class=\"bodytext\">\n                    <span id=\"monitors-switchtab\">\n      \t\t\t\t");
/*      */                           
/* 3795 */                           ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3796 */                           _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 3797 */                           _jspx_th_c_005fchoose_005f6.setParent(null);
/* 3798 */                           int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 3799 */                           if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                             for (;;) {
/* 3801 */                               out.write("\n\t\t\t\t\t");
/* 3802 */                               if (_jspx_meth_c_005fwhen_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/*      */                                 return;
/* 3804 */                               out.write("\n\t\t\t\t\t");
/*      */                               
/* 3806 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3807 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3808 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f6);
/* 3809 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3810 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/* 3812 */                                   out.write("\n\t\t\t\t\t<a href=\"javascript:showManageType('All')\" class=\"");
/* 3813 */                                   out.print(showallstyle);
/* 3814 */                                   out.write(34);
/* 3815 */                                   out.write(32);
/* 3816 */                                   out.write(62);
/* 3817 */                                   out.print(FormatUtil.getString("am.webclient.common.bulkconfigview.showall.text"));
/* 3818 */                                   out.write("</a>");
/* 3819 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/* 3821 */                                   if (request.getParameter("selectedNetwork") == null)
/*      */                                   {
/*      */ 
/* 3824 */                                     out.write("\n\t\t\t  \n\t    \t   \t\t <a href=\"javascript:showManageType('true')\" class=\"");
/* 3825 */                                     out.print(managedStyle);
/* 3826 */                                     out.write(34);
/* 3827 */                                     out.write(32);
/* 3828 */                                     out.write(62);
/* 3829 */                                     out.print(FormatUtil.getString("Managed"));
/* 3830 */                                     out.write("</a>\n\t\t\t\t\t <a href=\"javascript:showManageType('false')\"  class=\"");
/* 3831 */                                     out.print(unManagedStyle);
/* 3832 */                                     out.write(34);
/* 3833 */                                     out.write(32);
/* 3834 */                                     out.write(62);
/* 3835 */                                     out.print(FormatUtil.getString("UnManaged"));
/* 3836 */                                     out.write("</a>\t\t\t\t\t \n\t\t\t\t\t<a href=\"javascript:showManageType('critical')\"  class=\"");
/* 3837 */                                     out.print(criticalStyle);
/* 3838 */                                     out.write("\" > \n\t\t\t\t\t");
/* 3839 */                                     out.print(FormatUtil.getString("am.monitortab.plasmaview.criticalalert.text"));
/* 3840 */                                     out.write("</a>\n\t\t\t    \t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 3844 */                                   out.write("\t\n\t\t\t    \t");
/* 3845 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3846 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3850 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3851 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/* 3854 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3855 */                               out.write("\n\t\t\t\t\t");
/* 3856 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3857 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3861 */                           if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3862 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                           }
/*      */                           
/* 3865 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3866 */                           out.write("\n\t\t\t\t\t&nbsp;\n                    </span>\n\t\t\t\t\t</td>\n        \t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t</table>\n        <script>\n        $(\"#monitors-switchtab\").buttonset(); //NO I18N\n        </script>\n\t\t");
/*      */                         }
/*      */                         
/*      */ 
/* 3870 */                         out.write("\n\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t\t<tr>\n\t\t\t\t<td colspan=\"4\" valign=\"top\">\n\t\t\t\t");
/*      */                         
/* 3872 */                         if (rowcount > 0)
/*      */                         {
/* 3874 */                           Properties alert = getAlerts(list, availabilitykeys, healthkeys);
/* 3875 */                           request.setAttribute("alert", alert);
/* 3876 */                           String restype = request.getParameter("network");
/* 3877 */                           if ((restype != null) && (restype.equals("WLS-Cluster")))
/*      */                           {
/* 3879 */                             restype = "WEBLOGIC-server";
/*      */                           }
/*      */                           
/* 3882 */                           out.write("\n\t\t\t\t<input type=\"hidden\" name=\"type1\" value=\"");
/* 3883 */                           out.print(restype);
/* 3884 */                           out.write("\" />\n\t\t\t\t");
/*      */                           
/* 3886 */                           IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3887 */                           _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3888 */                           _jspx_th_c_005fif_005f1.setParent(null);
/*      */                           
/* 3890 */                           _jspx_th_c_005fif_005f1.setTest("${!empty param.group}");
/* 3891 */                           int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3892 */                           if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                             for (;;) {
/* 3894 */                               out.write("\n\t\t\t\t\t<input type=\"hidden\" name=\"group\" value=\"");
/* 3895 */                               out.print(request.getParameter("group"));
/* 3896 */                               out.write("\" />\n\t\t\t\t");
/* 3897 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3898 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3902 */                           if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3903 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                           }
/*      */                           
/* 3906 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3907 */                           out.write("\n\t\t\t\t");
/* 3908 */                           if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_page_context))
/*      */                             return;
/* 3910 */                           out.write("\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"50%\" valign=\"top\">\n\t\t\t\t\t\t\t<table width=\"100%\" id=\"allResourceTable\" border=\"0\" cellpadding=\"8\" cellspacing=\"0\" style=\"background-color:#fff;\">\n\t\t\t\t\t\t\t\t<tr class=\"bodytextbold\">\n\t \t\t\t\t\t\t\t\t");
/*      */                           
/* 3912 */                           if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")))
/*      */                           {
/*      */ 
/* 3915 */                             out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"1%\"height=\"28\"  align=\"center\"  valign=\"center\"  class=\"columnheading\">\n\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"headercheckbox1\"  onClick=\"javascript:fnSelectAll(this,'select');\">\n\t\t\t\t\t\t\t\t\t</td>\n\t \t\t\t\t\t\t\t\t");
/*      */ 
/*      */                           }
/* 3918 */                           else if (request.isUserInRole("OPERATOR"))
/*      */                           {
/*      */ 
/* 3921 */                             out.write("\n\t\t\t\t\t\t\t\t\t");
/* 3922 */                             if (_jspx_meth_c_005fchoose_005f7(_jspx_page_context))
/*      */                               return;
/* 3924 */                             out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3928 */                           out.write("\n\t\t\t\t\t\t\t\t\t<td height=\"28\" width=\"32%\" class=\"columnheading\" id=\"displayName\">\n\t\t\t\t\t\t\t\t\t");
/*      */                           
/* 3930 */                           if ((!request.isUserInRole("DEMO")) && (!request.isUserInRole("ADMIN")))
/*      */                           {
/*      */ 
/* 3933 */                             out.write("\n\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3937 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 3938 */                           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 3939 */                           out.write("</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           
/* 3941 */                           IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3942 */                           _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3943 */                           _jspx_th_c_005fif_005f2.setParent(null);
/*      */                           
/* 3945 */                           _jspx_th_c_005fif_005f2.setTest("${!empty param.group}");
/* 3946 */                           int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3947 */                           if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                             for (;;) {
/* 3949 */                               out.write("\n\t\t\t\t\t\t\t \t\t\t");
/*      */                               
/* 3951 */                               if ((request.getParameter("viewmontype") == null) || (request.getParameter("viewmontype").equals("All")))
/*      */                               {
/*      */ 
/* 3954 */                                 out.write("\n\t\t          \t\t\t\t\t<td height=\"28\" width=\"10%\" class=\"columnheading\" align=\"left\" width=\"15%\" id=\"type\">&nbsp; &nbsp; &nbsp;");
/* 3955 */                                 out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3956 */                                 out.write("</td>\n\t\t          \t\t\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 3960 */                               out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 3961 */                               int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3962 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3966 */                           if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3967 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                           }
/*      */                           
/* 3970 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3971 */                           out.write("\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           
/* 3973 */                           boolean hasresourceGroup1 = false;
/* 3974 */                           for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                           {
/* 3976 */                             if (com.adventnet.appmanager.util.Constants.categoryLink[i].equals(request.getParameter("viewmontype")))
/*      */                             {
/* 3978 */                               hasresourceGroup1 = true;
/* 3979 */                               break;
/*      */                             }
/*      */                           }
/* 3982 */                           if (hasresourceGroup1)
/*      */                           {
/*      */ 
/* 3985 */                             out.write("\n\t\t\t\t   \t\t\t\t\t<td height=\"28\" width=\"10%\" class=\"columnheading\" align=\"left\" width=\"15%\" id=\"type\">&nbsp;&nbsp;&nbsp;\n\t\t\t\t   \t\t\t\t\t\t");
/* 3986 */                             out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 3987 */                             out.write("\n\t\t\t\t   \t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           
/* 3990 */                           if ((restype != null) && (restype.equals("RBM")))
/*      */                           {
/*      */ 
/* 3993 */                             out.write("\n\t\t\t\t\t\t\t\t\t<td height=\"28\"  class=\"columnheading\" align=\"left\" width=\"12%\">\n\t\t\t\t\t\t\t\t\t\t");
/* 3994 */                             out.print(FormatUtil.getString("am.webclient.rbm.script.text"));
/* 3995 */                             out.write("\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3999 */                           out.write("\n\t\t          \t\t\t\t\t<td height=\"28\" class=\"columnheading\" align=\"center\" width=\"12%\">\n\t\t          \t\t\t\t\t\t");
/* 4000 */                           out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 4001 */                           out.write("\n\t\t          \t\t\t\t\t</td>\n\t\t          \t\t\t\t\t<td height=\"28\" class=\"columnheading\" align=\"center\"  width=\"9%\">\n\t\t          \t\t\t\t\t\t");
/* 4002 */                           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4003 */                           out.write("\n\t\t          \t\t\t\t\t</td>\n\t\t\t\t  \t\t\t\t\t\t");
/*      */                           
/* 4005 */                           if ((EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                           {
/*      */ 
/* 4008 */                             out.write("\n\t\t\t\t  \t\t\t\t\t<td height=\"28\" class=\"columnheading\" align=\"center\" width=\"28%\" id=\"mas\">\n\t\t\t\t  \t\t\t\t\t\t");
/* 4009 */                             out.print(FormatUtil.getString("am.webclient.adminserver.title"));
/* 4010 */                             out.write("\n\t\t\t\t  \t\t\t\t\t</td>\n\t\t\t \t\t\t\t\t\t\t");
/*      */                           }
/*      */                           
/* 4013 */                           if ((request.isUserInRole("ADMIN")) || (request.isUserInRole("DEMO")))
/*      */                           {
/*      */ 
/* 4016 */                             out.write("\n        \t\t\t\t\t\t\t<td class=\"columnheading\" align=\"center\" colspan=\"2\" width=\"5%\">&nbsp;</td>\n        \t\t\t\t\t\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 4020 */                           out.write("\n        \t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/*      */                           
/* 4022 */                           if (request.getAttribute("isCritical") == null) {}
/*      */                           
/*      */ 
/*      */ 
/* 4026 */                           HashMap extDeviceMap = null;
/* 4027 */                           if (com.adventnet.appmanager.util.Constants.isExtDeviceConfigured())
/*      */                           {
/* 4029 */                             if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                             {
/* 4031 */                               extDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpManager", true);
/*      */                               
/* 4033 */                               HashMap opstorExtDeviceMap = ExtProdUtil.getDeviceLinksOfExtProduct("OpStor");
/* 4034 */                               extDeviceMap.putAll(opstorExtDeviceMap);
/*      */ 
/*      */                             }
/*      */                             else
/*      */                             {
/*      */ 
/* 4040 */                               extDeviceMap = com.adventnet.appmanager.server.framework.extprod.IntegProdDBUtil.getExtAllDevicesLink(false);
/*      */                             }
/*      */                           }
/* 4043 */                           boolean isOpManagerDemoDone = false;
/* 4044 */                           boolean isOpStorDemoDone = false;
/* 4045 */                           for (int j = 0; (list != null) && (j < list.size()); j++)
/*      */                           {
/*      */ 
/* 4048 */                             Boolean isExternalDevice = Boolean.valueOf(false);
/*      */                             
/* 4050 */                             ArrayList row = (ArrayList)list.get(j);
/* 4051 */                             String link = (String)row.get(0);
/* 4052 */                             String displayname = (String)row.get(5);
/* 4053 */                             displayname = ExtProdUtil.decodeString(displayname);
/* 4054 */                             String resourceid = (String)row.get(6);
/* 4055 */                             resourceName = (String)row.get(7);
/* 4056 */                             String temp = link;
/* 4057 */                             String wsSeverity = null;
/* 4058 */                             if (j % 2 == 0)
/*      */                             {
/* 4060 */                               wsSeverity = "class=\"whitegrayborder\"";
/*      */                             }
/*      */                             else
/*      */                             {
/* 4064 */                               wsSeverity = "class=\"yellowgrayborder\"";
/*      */                             }
/*      */                             
/* 4067 */                             if ((extDeviceMap != null) && (extDeviceMap.get(resourceid) != null))
/*      */                             {
/* 4069 */                               isExternalDevice = Boolean.valueOf(true);
/* 4070 */                               if (isEnterpriseSearch != null)
/*      */                               {
/*      */ 
/* 4073 */                                 out.write("\n\t\t\t\t\t\t\t\t<input type=\"hidden\" id=\"isExternalDevicePresent\" name=\"isExternalDevicePresent\" value=\"true\" />       \n\t\t\t\t\t\t\t\t\t");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 4078 */                             String alertcount = (String)row.get(2);
/* 4079 */                             String source = (String)row.get(4);
/* 4080 */                             String wscript = "";
/*      */                             
/* 4082 */                             if (((!isOpStorDemoDone) || (resourceName.toUpperCase().indexOf("OPSTOR") == -1)) && ((!isOpManagerDemoDone) || (resourceName.toUpperCase().indexOf("OPMANAGER") == -1)))
/*      */                             {
/*      */ 
/* 4085 */                               if ((restype != null) && (restype.equals("RBM")))
/*      */                               {
/* 4087 */                                 for (int jj = 0; (webScripts != null) && (jj < webScripts.size()); jj++)
/*      */                                 {
/* 4089 */                                   if (((String)resIDs.get(jj)).equals(resourceid))
/*      */                                   {
/* 4091 */                                     wscript = (String)webScripts.get(jj);
/*      */                                   }
/*      */                                 }
/*      */                               }
/* 4095 */                               if (source == null)
/*      */                               {
/* 4097 */                                 alertcount = "0";
/*      */                               }
/* 4099 */                               String encodeurl = null;
/* 4100 */                               if (group == null)
/*      */                               {
/* 4102 */                                 encodeurl = "/showresource.do?method=showResourceTypesAll&network=" + network + "&detailspage=true";
/*      */ 
/*      */ 
/*      */                               }
/* 4106 */                               else if (group.equals("All"))
/*      */                               {
/* 4108 */                                 encodeurl = "/showresource.do?method=showResourceTypesAll&group=" + group + "&detailspage=true";
/*      */                               }
/*      */                               else
/*      */                               {
/* 4112 */                                 encodeurl = "/showresource.do?method=showResourceTypesAll&group=" + group + "&detailspage=true";
/*      */                               }
/*      */                               
/* 4115 */                               String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + resourceid;
/* 4116 */                               Properties poolProp = null;
/* 4117 */                               if (resourceName.equals("HAI"))
/*      */                               {
/* 4119 */                                 poolProp = com.adventnet.appmanager.util.DBUtil.getXenResourcePoolDetailsForHaid(resourceid);
/*      */                               }
/*      */                               
/* 4122 */                               out.write("\n        \t\t\t\t\t\t<tr onMouseOver=\"this.className='bulkmonHeaderHover'\"  onmouseout=\"this.className='bulkmonHeader'\">\n        \t\t\t\t\t\t\t");
/*      */                               
/* 4124 */                               if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")))
/*      */                               {
/*      */ 
/* 4127 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"5%\"  align=\"center\"  ");
/* 4128 */                                 out.print(wsSeverity);
/* 4129 */                                 out.write("> \n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\t");
/* 4130 */                                 if (isExternalDevice.booleanValue()) {
/* 4131 */                                   out.write("\n \t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"select\" value=\"\" disabled>   \n\t\t\t\t\t\t\t\t\t\t");
/*      */                                 } else {
/* 4133 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"select\" value=\"");
/* 4134 */                                   out.print(resourceid);
/* 4135 */                                   out.write("\" >     \n\t\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/* 4137 */                                 out.write("\n\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                               }
/* 4140 */                               else if (request.isUserInRole("OPERATOR"))
/*      */                               {
/*      */ 
/* 4143 */                                 out.write("\n            \t\t\t\t\t\t");
/*      */                                 
/* 4145 */                                 ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4146 */                                 _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 4147 */                                 _jspx_th_c_005fchoose_005f8.setParent(null);
/* 4148 */                                 int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 4149 */                                 if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                                   for (;;) {
/* 4151 */                                     out.write("\n                    \t\t\t\t\t");
/*      */                                     
/* 4153 */                                     WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4154 */                                     _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 4155 */                                     _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                                     
/* 4157 */                                     _jspx_th_c_005fwhen_005f8.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' || allowReset=='true'}");
/* 4158 */                                     int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 4159 */                                     if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                                       for (;;) {
/* 4161 */                                         out.write("\n            \t\t\t\t\t\t<td width=\"5%\" ");
/* 4162 */                                         out.print(wsSeverity);
/* 4163 */                                         out.write("> \n\t\t\t\t\t\t\t\t\t");
/* 4164 */                                         if (isExternalDevice.booleanValue()) {
/* 4165 */                                           out.write("\n \t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"select\" value=\"\" disabled>   \n\t\t\t\t\t\t\t\t\t\t");
/*      */                                         } else {
/* 4167 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"select\" value=\"");
/* 4168 */                                           out.print(resourceid);
/* 4169 */                                           out.write("\" >     \n\t\t\t\t\t\t\t\t\t\t");
/*      */                                         }
/* 4171 */                                         out.write("\n\t\t\t\t\t\t\t\t\t</td>\n                   \t\t\t\t\t");
/* 4172 */                                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 4173 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 4177 */                                     if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 4178 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                                     }
/*      */                                     
/* 4181 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 4182 */                                     out.write("\n            \t\t\t\t\t\t");
/* 4183 */                                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 4184 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 4188 */                                 if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 4189 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */                                 }
/*      */                                 
/* 4192 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 4193 */                                 out.write("\t\n        \t\t\t\t\t\t\t");
/*      */                               }
/*      */                               
/* 4196 */                               String class1 = "bulkmon-links";
/* 4197 */                               String tooltip = displayname;
/* 4198 */                               if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid)))
/*      */                               {
/* 4200 */                                 class1 = "disabledtext";
/* 4201 */                                 tooltip = displayname + " - " + FormatUtil.getString("UnManaged");
/*      */                               }
/*      */                               
/* 4204 */                               String method = request.getParameter("method");
/*      */                               
/* 4206 */                               String link1 = null;
/* 4207 */                               boolean hideEditLink = false;
/* 4208 */                               if ((extDeviceMap != null) && (extDeviceMap.get(resourceid) != null))
/*      */                               {
/* 4210 */                                 link1 = (String)extDeviceMap.get(resourceid);
/* 4211 */                                 hideEditLink = true;
/*      */                                 
/* 4213 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"45%\"  title=\"");
/* 4214 */                                 out.print(tooltip);
/* 4215 */                                 out.write(34);
/* 4216 */                                 out.write(32);
/* 4217 */                                 out.print(wsSeverity);
/* 4218 */                                 out.write(">\n\t\t\t\t\t\t\t\t\t");
/*      */                                 
/* 4220 */                                 if ((!request.isUserInRole("DEMO")) && (!request.isUserInRole("ADMIN")))
/*      */                                 {
/*      */ 
/* 4223 */                                   out.write(" \n\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4227 */                                 if ((request.isUserInRole("DEMO")) && (group.equals("NWD")) && (resourceName.toUpperCase().indexOf("OPMANAGER") != -1)) {
/* 4228 */                                   isOpManagerDemoDone = true;
/* 4229 */                                   out.write("\n            \t\t\t\t\t\t\t\t  \t\t\t<a href=\"http://demo.appmanager.com/networkdevices/networkdevices.htm\" class=\"staticlinks\">");
/* 4230 */                                   out.print(getTrimmedText(displayname, 45));
/* 4231 */                                   out.write("</a>&nbsp;&nbsp;\n            \t\t\t\t\t\t\t\t\t ");
/* 4232 */                                 } else if ((request.isUserInRole("DEMO")) && (group.equals("SAN")) && (resourceName.toUpperCase().indexOf("OPSTOR") != -1)) {
/* 4233 */                                   isOpStorDemoDone = true;
/* 4234 */                                   out.write("\n            \t\t\t\t\t\t\t\t\t  <a href=\"http://demo.appmanager.com/networkdevices/opstor.htm\" class=\"staticlinks\">");
/* 4235 */                                   out.print(getTrimmedText(displayname, 45));
/* 4236 */                                   out.write("</a>&nbsp;&nbsp;\n            \t\t\t\t\t\t\t\t  \t");
/*      */                                 } else {
/* 4238 */                                   out.write("    \n\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:MM_openBrWindow('");
/* 4239 */                                   out.print(link1);
/* 4240 */                                   out.write("','extDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class='");
/* 4241 */                                   out.print(class1);
/* 4242 */                                   out.write(39);
/* 4243 */                                   out.write(62);
/* 4244 */                                   out.print(getTrimmedText(displayname, 45));
/* 4245 */                                   out.write("</a>&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                               }
/* 4248 */                               else if (resourceName.contains("Site24x7"))
/*      */                               {
/*      */ 
/* 4251 */                                 link1 = "javascript:MM_openBrWindow('/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')";
/* 4252 */                                 hideEditLink = true;
/*      */                                 
/* 4254 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"45%\"  title=\"");
/* 4255 */                                 out.print(tooltip);
/* 4256 */                                 out.write(34);
/* 4257 */                                 out.write(32);
/* 4258 */                                 out.print(wsSeverity);
/* 4259 */                                 out.write(">\n\t\t\t\t\t\t\t\t\t");
/*      */                                 
/* 4261 */                                 if ((!request.isUserInRole("DEMO")) && (!request.isUserInRole("ADMIN")))
/*      */                                 {
/*      */ 
/* 4264 */                                   out.write(" \n\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4268 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<a href=\"");
/* 4269 */                                 out.print(link1);
/* 4270 */                                 out.write("\" class='");
/* 4271 */                                 out.print(class1);
/* 4272 */                                 out.write(39);
/* 4273 */                                 out.write(62);
/* 4274 */                                 out.print(getTrimmedText(displayname, 45));
/* 4275 */                                 out.write("</a>&nbsp;&nbsp;\n\t  \t\t\t\t\t\t\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 4280 */                                 if ((resourceName != null) && (resourceName.equals("HAI")))
/*      */                                 {
/* 4282 */                                   link1 = "/showapplication.do?method=showApplication&haid=" + resourceid;
/*      */                                 }
/* 4284 */                                 else if ((resourceName.equalsIgnoreCase("RBM")) || (resourceName.equalsIgnoreCase("file")))
/*      */                                 {
/* 4286 */                                   link1 = "/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid;
/*      */ 
/*      */ 
/*      */                                 }
/* 4290 */                                 else if (resourceName.equalsIgnoreCase("network"))
/*      */                                 {
/* 4292 */                                   link1 = "/showresource.do?resourceid=" + resourceid + "&type=" + resourceName + "&moname=" + URLEncoder.encode(link) + "&method=showResourceTypesAll&resourcename=" + URLEncoder.encode(displayname) + "&viewType=" + method;
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 4296 */                                   link1 = "/showresource.do?resourceid=" + resourceid + "&method=showResourceForResourceID";
/*      */                                 }
/*      */                                 
/*      */ 
/* 4300 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"40%\"  title=\"");
/* 4301 */                                 out.print(tooltip);
/* 4302 */                                 out.write(34);
/* 4303 */                                 out.write(32);
/* 4304 */                                 out.print(wsSeverity);
/* 4305 */                                 out.write(">\n\t\t\t\t\t\t\t\t\t");
/*      */                                 
/* 4307 */                                 if ((!request.isUserInRole("DEMO")) && (!request.isUserInRole("ADMIN")))
/*      */                                 {
/*      */ 
/* 4310 */                                   out.write(" \n\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 4314 */                                 out.write("\n\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:openDeviceSnapshotPage('");
/* 4315 */                                 out.print(link1);
/* 4316 */                                 out.write("',true,'");
/* 4317 */                                 out.print(isEnterpriseSearch);
/* 4318 */                                 out.write("')\" class='");
/* 4319 */                                 out.print(class1);
/* 4320 */                                 out.write(39);
/* 4321 */                                 out.write(62);
/* 4322 */                                 out.print(displayname);
/* 4323 */                                 out.write("</a>&nbsp;&nbsp;\n     \t\t\t\t\t\t\t\t");
/*      */                               }
/*      */                               
/* 4326 */                               if ((EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                               {
/* 4328 */                                 if ((Integer.parseInt(resourceid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) || (request.getRemoteUser().equals("systemadmin_enterprise")))
/*      */                                 {
/* 4330 */                                   boolean useHTTP = !com.adventnet.appmanager.util.Constants.isIt360;
/* 4331 */                                   String link2 = "/showresource.do?resourceid=" + resourceid + "&type=" + resourceName + "&moname=" + URLEncoder.encode(link) + "&method=showdetails&resourcename=" + URLEncoder.encode(displayname) + "&aam_jump=true&useHTTP=" + useHTTP;
/*      */                                   
/* 4333 */                                   if ((extDeviceMap != null) && (extDeviceMap.get(resourceid) != null))
/*      */                                   {
/* 4335 */                                     link2 = (String)extDeviceMap.get(resourceid);
/* 4336 */                                     link2 = link2 + "&aam_jump=true&useHTTP=" + useHTTP;
/*      */                                   }
/*      */                                   
/*      */ 
/* 4340 */                                   out.write("\n\t\t\t\t\t\t\t\t\t<a target=\"mas_window\" href=\"");
/* 4341 */                                   out.print(link2);
/* 4342 */                                   out.write("\">\n  \t        \t\t\t\t\t\t\t<img border=\"0\" title=\"");
/* 4343 */                                   out.print(FormatUtil.getString("am.adminserver.viewmonitordetails.text"));
/* 4344 */                                   out.write("\" src=\"/images/jump.gif\"/>\n  \t        \t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/* 4349 */                               out.write("\n          \t\t\t\t\t\t\t</td>\n\t\t  \t\t\t\t\t\t\t");
/*      */                               
/* 4351 */                               if ((!EnterpriseUtil.isAdminServer()) && (!request.isUserInRole("OPERATOR")))
/*      */                               {
/* 4353 */                                 if ((restype != null) && (restype.equals("RBM")))
/*      */                                 {
/*      */ 
/* 4356 */                                   out.write("\n\t\t\t\t\t\t\t\t\t<td ");
/* 4357 */                                   out.print(wsSeverity);
/* 4358 */                                   out.write(" >\n\t\t\t\t\t\t\t\t\t");
/*      */                                   
/* 4360 */                                   if ((!request.isUserInRole("DEMO")) && (!request.isUserInRole("ADMIN")))
/*      */                                   {
/*      */ 
/* 4363 */                                     out.write(" \n\t\t\t\t\t\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4367 */                                   out.write("\n\t\t\t\t\t\t\t\t\t<a href=\"#\" class=\"staticlinks\" onClick=\"javascript:openScriptManager('");
/* 4368 */                                   out.print(wscript);
/* 4369 */                                   out.write("','webscripttab');\"  title='");
/* 4370 */                                   out.print(wscript);
/* 4371 */                                   out.write(39);
/* 4372 */                                   out.write(32);
/* 4373 */                                   out.write(62);
/* 4374 */                                   out.print(wscript);
/* 4375 */                                   out.write("</a>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 
/*      */ 
/*      */                               }
/* 4381 */                               else if ((restype != null) && (restype.equals("RBM")))
/*      */                               {
/*      */ 
/* 4384 */                                 out.write("\n\t\t\t\t\t\t\t\t\t<td ");
/* 4385 */                                 out.print(wsSeverity);
/* 4386 */                                 out.write(" title='");
/* 4387 */                                 out.print(wscript);
/* 4388 */                                 out.write("' >\n\t\t\t\t\t\t\t\t\t");
/* 4389 */                                 out.print(wscript);
/* 4390 */                                 out.write("</td>\n\t\t\t\t\t\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 4395 */                               out.write("\n\t\t  \t\t\t\t\t\t\t");
/*      */                               
/* 4397 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4398 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 4399 */                               _jspx_th_c_005fif_005f3.setParent(null);
/*      */                               
/* 4401 */                               _jspx_th_c_005fif_005f3.setTest("${!empty param.group}");
/* 4402 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 4403 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 4405 */                                   out.write("\n\t\t  \t\t\t\t\t\t\t");
/*      */                                   
/* 4407 */                                   if ((request.getParameter("viewmontype") == null) || (request.getParameter("viewmontype").equals("All")))
/*      */                                   {
/*      */ 
/* 4410 */                                     out.write("\n\t\t  \t\t\t\t\t\t\t");
/*      */                                     
/* 4412 */                                     String typeofmonitor = FormatUtil.getString((String)row.get(3));
/* 4413 */                                     String networktype = (String)row.get(7);
/* 4414 */                                     ArrayList listnetwork = checkForGroup(request, groupList, networktype);
/* 4415 */                                     if (listnetwork.size() > 0)
/*      */                                     {
/* 4417 */                                       networktype = (String)((ArrayList)listnetwork.get(0)).get(0);
/* 4418 */                                       if (((String)((ArrayList)listnetwork.get(0)).get(1)).equals("NWD"))
/*      */                                       {
/* 4420 */                                         networktype = "NWD";
/*      */                                       }
/* 4422 */                                       if (((String)((ArrayList)listnetwork.get(0)).get(1)).equals("SAN"))
/*      */                                       {
/* 4424 */                                         networktype = "SAN";
/*      */                                       }
/*      */                                       
/* 4427 */                                       if (((String)((ArrayList)listnetwork.get(0)).get(1)).equals("EMO"))
/*      */                                       {
/* 4429 */                                         networktype = "EMO";
/*      */                                       }
/*      */                                     }
/* 4432 */                                     String imageurl = null;
/* 4433 */                                     if ((resourceName != null) && (resourceName.equals("HAI")))
/*      */                                     {
/* 4435 */                                       imageurl = "/showresource.do?method=showMonitorGroupView";
/* 4436 */                                       if (poolProp != null)
/*      */                                       {
/* 4438 */                                         typeofmonitor = FormatUtil.getString("am.webclient.mg.type.xenresourcepool");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4442 */                                         typeofmonitor = com.adventnet.appmanager.util.ReportUtil.getMonitorGroupType(resourceid);
/*      */                                       }
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 4447 */                                       imageurl = "/showresource.do?method=showResourceTypesAll&detailspage=true&network=" + networktype + "&viewmontype=" + networktype;
/*      */                                     }
/*      */                                     
/* 4450 */                                     out.write("\n\t\t  \t\t\t\t\t\t\t<td width=\"20%\"  align=\"left\" ");
/* 4451 */                                     out.print(wsSeverity);
/* 4452 */                                     out.write(">\n\t\t  \t\t\t\t\t\t\t\t<img src=\"");
/* 4453 */                                     out.print((String)row.get(8));
/* 4454 */                                     out.write("\" border=0 align=\"absmiddle\" hspace=\"5\" title='");
/* 4455 */                                     out.print(typeofmonitor);
/* 4456 */                                     out.write("'>\n\t\t  \t\t\t\t\t\t\t\t");
/*      */                                     
/* 4458 */                                     boolean isFilterTypeLink = true;
/* 4459 */                                     if ((isExternalDevice.booleanValue()) && (isEnterpriseSearch != null))
/*      */                                     {
/* 4461 */                                       isFilterTypeLink = false;
/*      */                                     }
/*      */                                     
/* 4464 */                                     out.write("\n\n\t\t\t\t\t\t\t\t\t");
/* 4465 */                                     if (isFilterTypeLink) {
/* 4466 */                                       out.write("\n\t\t\t\t\t\t\t\t\t<a class=\"staticlinks\" style=\"padding-left: 0px; text-decoration: none;\" href=\"javascript:openDeviceSnapshotPage('");
/* 4467 */                                       out.print(imageurl);
/* 4468 */                                       out.write("',true,'");
/* 4469 */                                       out.print(isEnterpriseSearch);
/* 4470 */                                       out.write("')\">\n\t\t  \t\t\t\t\t\t\t\t\t<span class=\"bulkmon-links\">");
/* 4471 */                                       out.print(getTrimmedText(typeofmonitor, 22));
/* 4472 */                                       out.write("</span>\n\t\t  \t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t");
/*      */                                     } else {
/* 4474 */                                       out.write("\n\t\t\t\t\t\t\t\t\t\t");
/* 4475 */                                       out.print(getTrimmedText(typeofmonitor, 22));
/* 4476 */                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                     }
/* 4478 */                                     out.write("\n\t\t\t\t\t\t\t\t\t\n\t\t  \t\t\t\t\t\t\t</td>\n\t\t  \t\t\t\t\t\t\t");
/*      */                                   }
/*      */                                   
/*      */ 
/* 4482 */                                   out.write("\n\t\t  \t\t\t\t\t\t\t");
/* 4483 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 4484 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4488 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 4489 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                               }
/*      */                               
/* 4492 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 4493 */                               out.write("\n\t\t \t\t\t\t\t\t\t");
/*      */                               
/* 4495 */                               boolean hasresourceGroup2 = false;
/* 4496 */                               for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                               {
/* 4498 */                                 if (com.adventnet.appmanager.util.Constants.categoryLink[i].equals(request.getParameter("viewmontype")))
/*      */                                 {
/* 4500 */                                   hasresourceGroup2 = true;
/* 4501 */                                   break;
/*      */                                 }
/*      */                               }
/* 4504 */                               if (hasresourceGroup2)
/*      */                               {
/* 4506 */                                 String networktype1 = FormatUtil.getString((String)row.get(7));
/*      */                                 
/*      */ 
/* 4509 */                                 ArrayList listnetwork1 = checkForGroup(request, groupList, networktype1);
/* 4510 */                                 if (listnetwork1.size() > 0)
/*      */                                 {
/* 4512 */                                   networktype1 = (String)((ArrayList)listnetwork1.get(0)).get(0);
/* 4513 */                                   if (((String)((ArrayList)listnetwork1.get(0)).get(1)).equals("NWD"))
/*      */                                   {
/* 4515 */                                     networktype1 = "NWD";
/*      */                                   }
/* 4517 */                                   if (((String)((ArrayList)listnetwork1.get(0)).get(1)).equals("SAN"))
/*      */                                   {
/* 4519 */                                     networktype1 = "SAN";
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 4523 */                                 out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4524 */                                 String typeofmonitor = FormatUtil.getString((String)row.get(3));
/* 4525 */                                 out.write("\n\t\t  \t\t\t\t\t\t\t<td width=\"15%\"  align=\"left\" ");
/* 4526 */                                 out.print(wsSeverity);
/* 4527 */                                 out.write(">\n\t\t  \t\t\t\t\t\t\t\t<img src=\"");
/* 4528 */                                 out.print((String)row.get(8));
/* 4529 */                                 out.write("\" border=0 align=\"absmiddle\" hspace=\"5\" title='");
/* 4530 */                                 out.print(getTrimmedText(typeofmonitor, 15));
/* 4531 */                                 out.write("'>\n\n\t\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t");
/* 4532 */                                 out.write("\n\t\t\t\t\t\t\n\t\t\t\t\t<a class=\"staticlinks\" style=\"padding-left: 0px; text-decoration: none;\" href=\"javascript:openDeviceSnapshotPage('/showresource.do?method=showResourceTypes&detailspage=true&network=");
/* 4533 */                                 out.print(networktype1);
/* 4534 */                                 out.write("&viewmontype=");
/* 4535 */                                 out.print(networktype1);
/* 4536 */                                 out.write("',true,'");
/* 4537 */                                 out.print(isEnterpriseSearch);
/* 4538 */                                 out.write("')\">\n\n\n\t\t  \t\t\t\t\t\t\t\t\t<span class=\"bulkmon-links\">");
/* 4539 */                                 out.print(getTrimmedText(typeofmonitor, 15));
/* 4540 */                                 out.write("</span>\n\t\t  \t\t\t\t\t\t\t\t</a>\n\t\t  \t\t\t\t\t\t\t</td>\n       \t\t\t\t\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 4544 */                               out.write("\n        \t\t\t\t\t\t\t");
/*      */                               
/* 4546 */                               SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4547 */                               _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4548 */                               _jspx_th_c_005fset_005f0.setParent(null);
/*      */                               
/* 4550 */                               _jspx_th_c_005fset_005f0.setVar("key");
/* 4551 */                               int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4552 */                               if (_jspx_eval_c_005fset_005f0 != 0) {
/* 4553 */                                 if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4554 */                                   out = _jspx_page_context.pushBody();
/* 4555 */                                   _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 4556 */                                   _jspx_th_c_005fset_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4559 */                                   out.write(32);
/* 4560 */                                   out.print(resourceid + "#" + availabilitykeys.get(resourceName) + "#" + "MESSAGE");
/* 4561 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 4562 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4565 */                                 if (_jspx_eval_c_005fset_005f0 != 1) {
/* 4566 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4569 */                               if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4570 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                               }
/*      */                               
/* 4573 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 4574 */                               out.write("\n          \t\t\t\t\t\t\t<td width=\"10%\" align=\"center\" ");
/* 4575 */                               out.print(wsSeverity);
/* 4576 */                               out.write(">\n          \t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" ");
/* 4577 */                               if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                                 return;
/* 4579 */                               out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4580 */                               out.print(resourceid);
/* 4581 */                               out.write("&attributeid=");
/* 4582 */                               out.print(availabilitykeys.get(resourceName));
/* 4583 */                               out.write("&alertconfigurl=");
/* 4584 */                               out.print(URLEncoder.encode(thresholdurl));
/* 4585 */                               out.write("')\">");
/* 4586 */                               out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + availabilitykeys.get(resourceName))));
/* 4587 */                               out.write("</a><span style=\"display:none\" >");
/* 4588 */                               out.print(alert.getProperty(resourceid + "#" + availabilitykeys.get(resourceName)));
/* 4589 */                               out.write("</span> ");
/* 4590 */                               out.write("\n          \t\t\t\t\t\t\t</td> <!-- This columns gets the availability of the resource-->\n        \t\t\t\t\t\t\t");
/*      */                               
/* 4592 */                               SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 4593 */                               _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4594 */                               _jspx_th_c_005fset_005f1.setParent(null);
/*      */                               
/* 4596 */                               _jspx_th_c_005fset_005f1.setVar("key");
/* 4597 */                               int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4598 */                               if (_jspx_eval_c_005fset_005f1 != 0) {
/* 4599 */                                 if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4600 */                                   out = _jspx_page_context.pushBody();
/* 4601 */                                   _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 4602 */                                   _jspx_th_c_005fset_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 4605 */                                   out.write(32);
/* 4606 */                                   out.print(resourceid + "#" + healthkeys.get(resourceName) + "#" + "MESSAGE");
/* 4607 */                                   int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 4608 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 4611 */                                 if (_jspx_eval_c_005fset_005f1 != 1) {
/* 4612 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 4615 */                               if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4616 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                               }
/*      */                               
/* 4619 */                               this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 4620 */                               out.write("\n\t\t\t\t\t\t\t\t\t<td align=\"center\" ");
/* 4621 */                               out.print(wsSeverity);
/* 4622 */                               out.write("> \n\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:void(0)\" ");
/* 4623 */                               if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                                 return;
/* 4625 */                               out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4626 */                               out.print(resourceid);
/* 4627 */                               out.write("&attributeid=");
/* 4628 */                               out.print(healthkeys.get(resourceName));
/* 4629 */                               out.write("&alertconfigurl=");
/* 4630 */                               out.print(URLEncoder.encode(thresholdurl));
/* 4631 */                               out.write("')\">");
/* 4632 */                               out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + healthkeys.get(resourceName))));
/* 4633 */                               out.write("</a><span style=\"display:none\">");
/* 4634 */                               out.print(alert.getProperty(resourceid + "#" + healthkeys.get(resourceName)));
/* 4635 */                               out.write("</span> ");
/* 4636 */                               out.write("\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/*      */                               
/* 4638 */                               if (com.me.apm.cmdb.APMHelpDeskUtil.isCILinksToBeShown(request))
/*      */                               {
/* 4640 */                                 Map<APMHelpDeskUtil.CIUrl, String> ciLinksMap = com.me.apm.cmdb.APMHelpDeskUtil.getCILinks(Long.parseLong(resourceid));
/* 4641 */                                 if ((ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_INFO_URL)))
/*      */                                 {
/* 4643 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td><a class=\"staticlinks\" href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4644 */                                   out.print((String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_INFO_URL));
/* 4645 */                                   out.write("','950','500','100','100')\"><img  align=\"absmiddle\"  src=\"/images/CI_Details.gif\" border=\"0\" alt=\"");
/* 4646 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4647 */                                   out.write("\" hspace=\"4\" title=\"");
/* 4648 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.info"));
/* 4649 */                                   out.write("\"></a></td>\n\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 4653 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/* 4655 */                                 if ((ciLinksMap != null) && (ciLinksMap.containsKey(APMHelpDeskUtil.CIUrl.CI_RL_URL)))
/*      */                                 {
/* 4657 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td><a class=\"staticlinks\" href=\"javascript:fnOpenNewWindowWithHeightWidthPlacement('");
/* 4658 */                                   out.print((String)ciLinksMap.get(APMHelpDeskUtil.CIUrl.CI_RL_URL));
/* 4659 */                                   out.write("','950','500','100','100')\"><img  align=\"absmiddle\"  src=\"/images/cmdb-rship-icon.gif\" border=\"0\" alt=\"");
/* 4660 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4661 */                                   out.write("\" hspace=\"4\" title=\"");
/* 4662 */                                   out.print(FormatUtil.getString("am.webclient.cmdb.ci.rl"));
/* 4663 */                                   out.write("\"></a></td>\n\t\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 4667 */                                   out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t<td align=\"center\">-</td>\n\t\t\t\t\t\t\t\t\t\t\t");
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 4673 */                               out.write("\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/*      */                               
/* 4675 */                               ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4676 */                               _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 4677 */                               _jspx_th_c_005fchoose_005f9.setParent(null);
/* 4678 */                               int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 4679 */                               if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                                 for (;;) {
/* 4681 */                                   out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                   
/* 4683 */                                   WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4684 */                                   _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 4685 */                                   _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                                   
/* 4687 */                                   _jspx_th_c_005fwhen_005f9.setTest("${ !empty OPERATOR }");
/* 4688 */                                   int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 4689 */                                   if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                                     for (;;) {
/* 4691 */                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 4693 */                                       if (EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 4696 */                                         out.write("\n\t\t\t\t\t\t\t\t\t<td colspan=\"2\" ");
/* 4697 */                                         out.print(wsSeverity);
/* 4698 */                                         out.write(" align=\"center\">\n\t\t\t\t\t\t\t\t\t\t");
/* 4699 */                                         out.print(CommDBUtil.getManagedServerNameWithPort(resourceid));
/* 4700 */                                         out.write("\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4704 */                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4705 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 4706 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4710 */                                   if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 4711 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                                   }
/*      */                                   
/* 4714 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 4715 */                                   out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                   
/* 4717 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4718 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 4719 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f9);
/* 4720 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 4721 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 4723 */                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 4725 */                                       if ((EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */                                       {
/*      */ 
/* 4728 */                                         out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4730 */                                         if ((request.getRemoteUser() != null) && (request.getRemoteUser().equals("systemadmin_enterprise")))
/*      */                                         {
/*      */ 
/* 4733 */                                           out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" ");
/* 4734 */                                           out.print(wsSeverity);
/* 4735 */                                           out.write("> demo.appmanager_9090 </td>\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 4741 */                                           out.write("\n\t\t    \t\t\t\t\t\t<td width=\"5%\" align=\"center\" ");
/* 4742 */                                           out.print(wsSeverity);
/* 4743 */                                           out.write("> \n\t\t    \t\t\t\t\t\t\t");
/* 4744 */                                           out.print(CommDBUtil.getManagedServerNameWithPort(resourceid));
/* 4745 */                                           out.write(" \n\t\t    \t\t\t\t\t\t</td>\n\t\t    \t\t\t\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4749 */                                         out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                       }
/* 4752 */                                       else if (request.isUserInRole("ADMIN"))
/*      */                                       {
/*      */ 
/* 4755 */                                         out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" ");
/* 4756 */                                         out.print(wsSeverity);
/* 4757 */                                         out.write(" title=\"");
/* 4758 */                                         out.print(FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT"));
/* 4759 */                                         out.write("\">\n\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:openDeviceSnapshotPage('");
/* 4760 */                                         out.print(thresholdurl);
/* 4761 */                                         out.write("',true,'");
/* 4762 */                                         out.print(isEnterpriseSearch);
/* 4763 */                                         out.write("')\">\n\t\t\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" border=\"0\" >\n\t\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t<!-- <td width=\"5%\" ");
/* 4764 */                                         out.print(wsSeverity);
/* 4765 */                                         out.write(" align=\"center\" title=\"");
/* 4766 */                                         out.print(FormatUtil.getString("am.webclient.copypaste.title"));
/* 4767 */                                         out.write("\"><a href=\"javascript:MM_openBrWindow('/jsp/CopyMonitor.jsp?resourceid=");
/* 4768 */                                         out.print(resourceid);
/* 4769 */                                         out.write("','Password','width=650,height=300,top=250,left=275 scrollbars=yes,resizable=yes')\" class=\"footer\" ><img src=\"/images/icon_monitors_copypaste.gif\" border=\"0\" ></a></td> -->\n\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                       
/* 4772 */                                       if ((!EnterpriseUtil.isAdminServer()) && (!request.isUserInRole("ENTERPRISEADMIN")) && (request.isUserInRole("ADMIN")))
/*      */                                       {
/* 4774 */                                         if (hideEditLink)
/*      */                                         {
/*      */ 
/* 4777 */                                           out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" title=\"\" ");
/* 4778 */                                           out.print(wsSeverity);
/* 4779 */                                           out.write(" >\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/* 4782 */                                         else if (ConfMonitorConfiguration.getInstance().isDependentMonitor(resourceName))
/*      */                                         {
/*      */ 
/* 4785 */                                           out.write("\n                \t\t\t\t\t<td width=\"5%\" align=\"center\" title=\"");
/* 4786 */                                           out.print(FormatUtil.getString("am.webclient.editdisabled.text", new String[] { resourceName }));
/* 4787 */                                           out.write(34);
/* 4788 */                                           out.write(32);
/* 4789 */                                           out.print(wsSeverity);
/* 4790 */                                           out.write(" >\n                \t\t\t\t\t");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 4796 */                                           out.write("\n\t\t\t\t\t\t\t\t\t<td width=\"5%\" align=\"center\" title=\"");
/* 4797 */                                           out.print(FormatUtil.getString("jmxnotification.edit"));
/* 4798 */                                           out.write(34);
/* 4799 */                                           out.write(32);
/* 4800 */                                           out.print(wsSeverity);
/* 4801 */                                           out.write(" >\n\t\t\t\t\t\t\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4805 */                                         out.write("\n\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4807 */                                         if (resourceName.equals("UrlMonitor"))
/*      */                                         {
/*      */ 
/* 4810 */                                           out.write("\n\n\t\t\t\t\t\t\t\t\t<a href=\"javascript:openDeviceSnapshotPage('/updateUrl.do?actionmethod=editUrlMonitor&resourceid=");
/* 4811 */                                           out.print(resourceid);
/* 4812 */                                           out.write("&type=UrlMonitor',true,'");
/* 4813 */                                           out.print(isEnterpriseSearch);
/* 4814 */                                           out.write("')\" class=\"staticlinks\"><img src=\"/images/icon_edit.gif\" border=\"0\" ></a>\n\n\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/* 4817 */                                         else if (resourceName.equals("RBM"))
/*      */                                         {
/*      */ 
/* 4820 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t");
/* 4821 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:openDeviceSnapshotPage('manageConfMons.do?haid=null&method=editMonitor&resourceid=");
/* 4822 */                                           out.print(resourceid);
/* 4823 */                                           out.write("&type=");
/* 4824 */                                           out.print(resourceName);
/* 4825 */                                           out.write("&resourcename=null',true,'");
/* 4826 */                                           out.print(isEnterpriseSearch);
/* 4827 */                                           out.write("')\" class=\"staticlinks\"><img src=\"/images/icon_edit.gif\" border=\"0\" ></a>\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/* 4830 */                                         else if (resourceName.equals("HAI"))
/*      */                                         {
/* 4832 */                                           if (poolProp != null)
/*      */                                           {
/* 4834 */                                             String grpType = poolProp.getProperty("GROUPTYPE");
/* 4835 */                                             if ((grpType != null) && (grpType.equals("1013")))
/*      */                                             {
/*      */ 
/* 4838 */                                               out.write("\n\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_disable_edit.gif\" border=\"0\" >\n\t\t\t\t\t\t\t\t\t");
/*      */                                             }
/*      */                                             
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 4845 */                                             out.write("\n\t\t\t\t\t\t\t\t\t<a href=\"showapplication.do?method=editApplication&haid=");
/* 4846 */                                             out.print(resourceid);
/* 4847 */                                             out.write("\" class=\"staticlinks\" title=\"\"><img src=\"/images/icon_edit.gif\" border=\"0\" ></a>\n\t\t\t\t\t\t\t\t\t");
/*      */                                           }
/*      */                                           
/*      */                                         }
/* 4851 */                                         else if ((!com.adventnet.appmanager.server.confmonitor.ConfMonitorUtil.getInstance().showEditLink(resourceid)) || (ConfMonitorConfiguration.getInstance().isDependentMonitor(resourceName)))
/*      */                                         {
/*      */ 
/* 4854 */                                           out.write("\n\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_disable_edit.gif\" border=\"0\" >\n                \t\t\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */                                         }
/* 4859 */                                         else if (hideEditLink)
/*      */                                         {
/*      */ 
/* 4862 */                                           out.write("\n\t\t\t\t\t\t\t\t\t&nbsp;\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                                         }
/* 4868 */                                         else if (ConfMonitorConfiguration.getInstance().isConfMonitor(resourceName))
/*      */                                         {
/*      */ 
/* 4871 */                                           out.write("\n\n\n\n");
/* 4872 */                                           out.write("\n\n\n\t<a href=\"javascript:openDeviceSnapshotPage('manageConfMons.do?haid=null&method=editMonitor&resourceid=");
/* 4873 */                                           out.print(resourceid);
/* 4874 */                                           out.write("&type=");
/* 4875 */                                           out.print(resourceName);
/* 4876 */                                           out.write("&resourcename=null',true,'");
/* 4877 */                                           out.print(isEnterpriseSearch);
/* 4878 */                                           out.write("')\" class=\"staticlinks\"><img src=\"/images/icon_edit.gif\" border=\"0\" ></a>\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t\n\n\n\n\n\n\t\t\t\t\t\t\t\t\t\n\t\t\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/* 4881 */                                         else if ((new PreConfMonitorXMLParser().getPreConfMonitorListSupported().containsKey(resourceName)) || (new PreConfMonitorXMLParser().getPreConfMonitorListSupported().containsValue(resourceName)))
/*      */                                         {
/*      */ 
/* 4884 */                                           if (resourceName.indexOf("Windows") != -1) {
/* 4885 */                                             resourceName = "Windows";
/*      */                                           }
/* 4887 */                                           out.write("\n                                                                         <a href=\"manageConfMons.do?haid=null&resourceid=");
/* 4888 */                                           out.print(resourceid);
/* 4889 */                                           out.write("&method=editPreConfMonitor&type=");
/* 4890 */                                           out.print(resourceName);
/* 4891 */                                           out.write("&resourcename=");
/* 4892 */                                           out.print(URLEncoder.encode(link));
/* 4893 */                                           out.write("\" class=\"staticlinks\"><img src=\"/images/icon_edit.gif\" border=\"0\" ></a>\n                                                                                                ");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/* 4898 */                                           out.write("\n\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:openDeviceSnapshotPage('/showresource.do?resourceid=");
/* 4899 */                                           out.print(resourceid);
/* 4900 */                                           out.write("&type=");
/* 4901 */                                           out.print(resourceName);
/* 4902 */                                           out.write("&moname=");
/* 4903 */                                           out.print(URLEncoder.encode(link));
/* 4904 */                                           out.write("&method=showdetails&resourcename=");
/* 4905 */                                           out.print(URLEncoder.encode(displayname));
/* 4906 */                                           out.write("&editPage=true',true,'");
/* 4907 */                                           out.print(isEnterpriseSearch);
/* 4908 */                                           out.write("')\" class=\"staticlinks\"><img src=\"/images/icon_edit.gif\" border=\"0\" ></a>\n\t\t\t\t\t\t\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/*      */ 
/*      */ 
/* 4914 */                                         out.write("\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4918 */                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4919 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 4920 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 4924 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 4925 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 4928 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 4929 */                                   out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4930 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 4931 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 4935 */                               if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 4936 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */                               }
/*      */                               
/* 4939 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 4940 */                               out.write("\n        \t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t");
/*      */                             }
/*      */                           }
/*      */                           
/* 4944 */                           out.write("\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n   \t\t\t</td>\n\t\t</tr>\n\t</table>\n\t<!-- Paging component at the bottom of the table starts here -->\n\t");
/*      */                           
/* 4946 */                           boolean showBottomFilter1 = true;
/* 4947 */                           String selectedType = group != null ? group : request.getParameter("network");
/* 4948 */                           boolean isSYSTypeSelected = selectedType != null ? selectedType.equals("SYS") : false;
/* 4949 */                           if (isSYSTypeSelected)
/*      */                           {
/* 4951 */                             showBottomFilter1 = false;
/*      */                           }
/*      */                           
/* 4954 */                           out.write(10);
/* 4955 */                           out.write(9);
/*      */                           
/* 4957 */                           if ((pagenois > 1) || ((list.size() > 24) && (showBottomFilter1)))
/*      */                           {
/*      */ 
/* 4960 */                             out.write("\n\t<table width=\"100%\" id=\"allResourceTable\" border=\"0\" cellpadding=\"4\" cellspacing=\"0\"  class=\"conig-mon-tile-dark\">\n        <tr>\n        \t<form action=\"/deleteMO.do?method=deleteMO\" name=\"deletemonitor1\" method=\"post\" style=\"display:inline\">\n        \t<td align=\"left\" height=\"26\" class=\"bodytextbold \" style=\"height:50px; \">\n        \t");
/*      */                             
/* 4962 */                             if (list.size() > 0)
/*      */                             {
/*      */ 
/* 4965 */                               out.write("\n            ");
/* 4966 */                               if (isEnterpriseSearch == null) {
/* 4967 */                                 out.write("\n            ");
/* 4968 */                                 out.write("<!--$Id$-->\n<script type=\"text/javascript\" src=\"/template/chosen.jquery.min.js\"></script>\n<link href=\"/images/chosen.css\" rel=\"stylesheet\" type=\"text/css\">\n ");
/*      */                                 
/* 4970 */                                 PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4971 */                                 _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 4972 */                                 _jspx_th_logic_005fpresent_005f11.setParent(null);
/*      */                                 
/* 4974 */                                 _jspx_th_logic_005fpresent_005f11.setRole("DEMO,ADMIN");
/* 4975 */                                 int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 4976 */                                 if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                   for (;;) {
/* 4978 */                                     out.write("&nbsp;<span class=\"filterby-txt input-uptime\">");
/* 4979 */                                     out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 4980 */                                     out.write(" :</span>\n                 <select style=\"width:220px\"  data-placeholder=\"");
/* 4981 */                                     out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 4982 */                                     out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">\n                     <option value=\"\">   </option>\n\t\t\t\t\t <option value=\"customField\">");
/* 4983 */                                     out.print(FormatUtil.getString("am.myfield.assign.text"));
/* 4984 */                                     out.write("</option>\n                     <option value=\"Delete\">");
/* 4985 */                                     out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 4986 */                                     out.write("</option>\n\t\t\t");
/* 4987 */                                     if ((request.getParameter("method").equals("showMonitorGroupView")) || (request.getParameter("method").equals("showSubGroupTree"))) {
/* 4988 */                                       out.write("\n\t\t     <option VALUE=\"Disable Actions\">");
/* 4989 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.disableaction.text"));
/* 4990 */                                       out.write("</option>\n\t\t     <option VALUE=\"Enable Actions\">");
/* 4991 */                                       out.print(FormatUtil.getString("am.webclient.monitorgroupview.enableaction.text"));
/* 4992 */                                       out.write("  </option>\n\n");
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/* 4997 */                                     if (!request.getParameter("method").equals("showMonitorGroupView"))
/*      */                                     {
/* 4999 */                                       if (!"VirtualMachine".equals(resourceName))
/*      */                                       {
/*      */ 
/*      */ 
/* 5003 */                                         out.write("\n                     <option value=\"editDisplayNames\">");
/* 5004 */                                         out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 5005 */                                         out.write("</option>\n                    ");
/*      */                                         
/* 5007 */                                         if (!request.getParameter("method").equals("showSubGroupTree"))
/*      */                                         {
/* 5009 */                                           out.write("\n\t\t\t\t\t\t<option value=\"updateIP\">");
/* 5010 */                                           out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 5011 */                                           out.write("</option>\n\t\t\t\t\t");
/*      */                                         }
/* 5013 */                                         out.write("\n\t\t\t");
/*      */                                       }
/*      */                                     }
/*      */                                     
/*      */ 
/* 5018 */                                     out.write("\n\t\t     <option value=\"Manage\">");
/* 5019 */                                     out.print(FormatUtil.getString("Manage"));
/* 5020 */                                     out.write("</option>\n             <option value=\"Unmanage\">");
/* 5021 */                                     out.print(FormatUtil.getString("UnManage"));
/* 5022 */                                     out.write("</option>\n             <option value=\"unManageAndReset\">");
/* 5023 */                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 5024 */                                     out.write("</option>\n\t\t\t");
/*      */                                     
/* 5026 */                                     if (!"VirtualMachine".equals(resourceName))
/*      */                                     {
/*      */ 
/* 5029 */                                       out.write("\n                     <option value=\"updateauth\">");
/* 5030 */                                       out.print(FormatUtil.getString("am.webclient.updateauthentication.text"));
/* 5031 */                                       out.write("</option>\n                     <option value=\"updatepoll\">");
/* 5032 */                                       out.print(FormatUtil.getString("am.webclient.updatepolling.text"));
/* 5033 */                                       out.write("</option>\n\t\t\t");
/*      */                                     }
/*      */                                     
/* 5036 */                                     if ((!request.getParameter("method").equals("showMonitorGroupView")) && (!request.getParameter("method").equals("showSubGroupTree")))
/*      */                                     {
/* 5038 */                                       out.write("\n                     <option value=\"copyPaste\">");
/* 5039 */                                       out.print(FormatUtil.getString("am.webclient.copypaste.headtitle"));
/* 5040 */                                       out.write("</option>\n\n\t\t\t");
/*      */                                     }
/* 5042 */                                     out.write("\n                 </select>\n            ");
/* 5043 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 5044 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5048 */                                 if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 5049 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11); return;
/*      */                                 }
/*      */                                 
/* 5052 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 5053 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 5055 */                                 PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5056 */                                 _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 5057 */                                 _jspx_th_logic_005fpresent_005f12.setParent(null);
/*      */                                 
/* 5059 */                                 _jspx_th_logic_005fpresent_005f12.setRole("ENTERPRISEADMIN");
/* 5060 */                                 int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 5061 */                                 if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */                                   for (;;) {
/* 5063 */                                     out.write("\n\t\t\t");
/*      */                                     
/* 5065 */                                     ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5066 */                                     _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 5067 */                                     _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_logic_005fpresent_005f12);
/* 5068 */                                     int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 5069 */                                     if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                                       for (;;) {
/* 5071 */                                         out.write("\n\t\t\t");
/*      */                                         
/* 5073 */                                         WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5074 */                                         _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 5075 */                                         _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                                         
/* 5077 */                                         _jspx_th_c_005fwhen_005f10.setTest("${param.method eq 'showMonitorGroupView'}");
/* 5078 */                                         int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 5079 */                                         if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                                           for (;;) {
/* 5081 */                                             out.write("\n\t\t\t <select style=\"width:220px\"  data-placeholder=\"");
/* 5082 */                                             out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 5083 */                                             out.write("\" tabindex=\"5\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\" name=\"operation\">");
/* 5084 */                                             out.write("\n\t\t\t <option value=\"\">   </option>\n\t\t\t <option value=\"Manage\">");
/* 5085 */                                             out.print(FormatUtil.getString("Manage"));
/* 5086 */                                             out.write("</option>");
/* 5087 */                                             out.write("\n\t\t\t <option value=\"Unmanage\">");
/* 5088 */                                             out.print(FormatUtil.getString("UnManage"));
/* 5089 */                                             out.write("</option>");
/* 5090 */                                             out.write("\n\t\t\t <option value=\"unManageAndReset\">");
/* 5091 */                                             out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 5092 */                                             out.write("</option>");
/* 5093 */                                             out.write("\n\t\t\t </select>\n\t\t\t");
/* 5094 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 5095 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5099 */                                         if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 5100 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                                         }
/*      */                                         
/* 5103 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 5104 */                                         out.write("\n\t\t\t");
/* 5105 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 5106 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5110 */                                     if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 5111 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                                     }
/*      */                                     
/* 5114 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 5115 */                                     out.write("\n\t\t\t");
/* 5116 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 5117 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5121 */                                 if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 5122 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12); return;
/*      */                                 }
/*      */                                 
/* 5125 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 5126 */                                 out.write("\n\n    ");
/*      */                                 
/* 5128 */                                 PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5129 */                                 _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 5130 */                                 _jspx_th_logic_005fpresent_005f13.setParent(null);
/*      */                                 
/* 5132 */                                 _jspx_th_logic_005fpresent_005f13.setRole("OPERATOR");
/* 5133 */                                 int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 5134 */                                 if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                                   for (;;) {
/* 5136 */                                     out.write(" \n                ");
/*      */                                     
/* 5138 */                                     ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5139 */                                     _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 5140 */                                     _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_logic_005fpresent_005f13);
/* 5141 */                                     int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 5142 */                                     if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                                       for (;;) {
/* 5144 */                                         out.write("\n                    ");
/*      */                                         
/* 5146 */                                         WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5147 */                                         _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 5148 */                                         _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                                         
/* 5150 */                                         _jspx_th_c_005fwhen_005f11.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' || allowReset=='true'}");
/* 5151 */                                         int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 5152 */                                         if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                                           for (;;) {
/* 5154 */                                             out.write("&nbsp<span class=\"filterby-txt input-uptime\">");
/* 5155 */                                             out.print(FormatUtil.getString("am.webclient.common.actions.text"));
/* 5156 */                                             out.write(" :</span>\n                        <select style=\"width:220px\" data-placeholder=\"");
/* 5157 */                                             out.print(FormatUtil.getString("am.webclient.configurealert.selectaction"));
/* 5158 */                                             out.write("\" onchange=\"javascript:invokeOperations(this)\" class=\"chzn-select\"  name=\"operation\">\n                            <option VALUE=\"selectactions\" selected=true></option>\n                            ");
/*      */                                             
/* 5160 */                                             ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5161 */                                             _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 5162 */                                             _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_c_005fwhen_005f11);
/* 5163 */                                             int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 5164 */                                             if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                                               for (;;) {
/* 5166 */                                                 out.write("\n\t                   \t\t\t ");
/*      */                                                 
/* 5168 */                                                 WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5169 */                                                 _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 5170 */                                                 _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                                                 
/* 5172 */                                                 _jspx_th_c_005fwhen_005f12.setTest("${allowEdit=='true'}");
/* 5173 */                                                 int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 5174 */                                                 if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                                                   for (;;) {
/* 5176 */                                                     out.write("\n\t                            \t<option value=\"editDisplayNames\">");
/* 5177 */                                                     out.print(FormatUtil.getString("am.webclient.displayname.edit.text"));
/* 5178 */                                                     out.write("</option>\n\t                             ");
/* 5179 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 5180 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5184 */                                                 if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 5185 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                                                 }
/*      */                                                 
/* 5188 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 5189 */                                                 out.write("\n                \t\t\t");
/* 5190 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 5191 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5195 */                                             if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 5196 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                                             }
/*      */                                             
/* 5199 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 5200 */                                             out.write("\n                \t\t\t");
/*      */                                             
/* 5202 */                                             ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5203 */                                             _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 5204 */                                             _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_c_005fwhen_005f11);
/* 5205 */                                             int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 5206 */                                             if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                                               for (;;) {
/* 5208 */                                                 out.write("\n\t                   \t\t\t ");
/*      */                                                 
/* 5210 */                                                 WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5211 */                                                 _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 5212 */                                                 _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                                                 
/* 5214 */                                                 _jspx_th_c_005fwhen_005f13.setTest("${allowUpdateIP=='true'}");
/* 5215 */                                                 int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 5216 */                                                 if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                                                   for (;;) {
/* 5218 */                                                     out.write("\n\t\t\t\t\t\t\t\t\t <option value=\"updateIP\">");
/* 5219 */                                                     out.print(FormatUtil.getString("am.webclient.common.updateip.text"));
/* 5220 */                                                     out.write("</option>\n\t                             ");
/* 5221 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 5222 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5226 */                                                 if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 5227 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                                                 }
/*      */                                                 
/* 5230 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 5231 */                                                 out.write("\n                \t\t\t");
/* 5232 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 5233 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5237 */                                             if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 5238 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */                                             }
/*      */                                             
/* 5241 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 5242 */                                             out.write("\n                \t\t\t");
/*      */                                             
/* 5244 */                                             ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5245 */                                             _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 5246 */                                             _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_c_005fwhen_005f11);
/* 5247 */                                             int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 5248 */                                             if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                                               for (;;) {
/* 5250 */                                                 out.write("\n\t                   \t\t\t ");
/*      */                                                 
/* 5252 */                                                 WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5253 */                                                 _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 5254 */                                                 _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                                                 
/* 5256 */                                                 _jspx_th_c_005fwhen_005f14.setTest("${allowManage=='true' || allowReset=='true'}");
/* 5257 */                                                 int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 5258 */                                                 if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                                                   for (;;) {
/* 5260 */                                                     out.write("\n\t                            \t<option value=\"Manage\">");
/* 5261 */                                                     out.print(FormatUtil.getString("Manage"));
/* 5262 */                                                     out.write("</option>\n                            \t\t<option value=\"Unmanage\">");
/* 5263 */                                                     out.print(FormatUtil.getString("UnManage"));
/* 5264 */                                                     out.write("</option>\n\t                             ");
/* 5265 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 5266 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5270 */                                                 if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 5271 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                                                 }
/*      */                                                 
/* 5274 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 5275 */                                                 out.write("\n\t\t\t\t\t");
/* 5276 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 5277 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5281 */                                             if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 5282 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                                             }
/*      */                                             
/* 5285 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 5286 */                                             out.write("\n                \t\t\t");
/*      */                                             
/* 5288 */                                             ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 5289 */                                             _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 5290 */                                             _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_c_005fwhen_005f11);
/* 5291 */                                             int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 5292 */                                             if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                                               for (;;) {
/* 5294 */                                                 out.write("\n\t\t\t\t\t");
/*      */                                                 
/* 5296 */                                                 WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 5297 */                                                 _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 5298 */                                                 _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                                                 
/* 5300 */                                                 _jspx_th_c_005fwhen_005f15.setTest("${allowReset=='true'}");
/* 5301 */                                                 int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 5302 */                                                 if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                                                   for (;;) {
/* 5304 */                                                     out.write("\n\t\t\t\t\t <option value=\"unManageAndReset\">");
/* 5305 */                                                     out.print(FormatUtil.getString("am.webclient.bulkconfigview.unmanagereset"));
/* 5306 */                                                     out.write("</option>");
/* 5307 */                                                     out.write("\n\t\t\t\t\t");
/* 5308 */                                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 5309 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/*      */                                                 }
/* 5313 */                                                 if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 5314 */                                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                                                 }
/*      */                                                 
/* 5317 */                                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 5318 */                                                 out.write("\n                \t\t\t");
/* 5319 */                                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 5320 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 5324 */                                             if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 5325 */                                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */                                             }
/*      */                                             
/* 5328 */                                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 5329 */                                             out.write("\n                        </select>\n                    ");
/* 5330 */                                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 5331 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5335 */                                         if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 5336 */                                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                                         }
/*      */                                         
/* 5339 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 5340 */                                         out.write("\n                ");
/* 5341 */                                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 5342 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 5346 */                                     if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 5347 */                                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                                     }
/*      */                                     
/* 5350 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 5351 */                                     out.write("\n            ");
/* 5352 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 5353 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 5357 */                                 if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 5358 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                                 }
/*      */                                 
/* 5361 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 5362 */                                 out.write("\n          \n");
/* 5363 */                                 out.write("\n            ");
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 5368 */                             out.write("\n            &nbsp;&nbsp;");
/* 5369 */                             out.write("\n            ");
/*      */                             
/* 5371 */                             String selectedValue = "All";
/* 5372 */                             String customcolor = "";
/* 5373 */                             if ((request.getParameter("assignCustomValues") != null) && (!request.getParameter("assignCustomValues").equals("true")) && (!request.getParameter("assignCustomValues").equals("null")))
/*      */                             {
/* 5375 */                               if ((request.getParameter("customValue") != null) && (!request.getParameter("customValue").equals("null")))
/*      */                               {
/* 5377 */                                 if (request.getParameter("customValue").contains("$"))
/*      */                                 {
/* 5379 */                                   selectedValue = request.getParameter("customValue").substring(0, request.getParameter("customValue").indexOf("$"));
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 5383 */                                   selectedValue = request.getParameter("customValue");
/*      */                                 }
/* 5385 */                                 customcolor = "background-color:#FFF8C6";
/*      */ 
/*      */ 
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */                             }
/* 5394 */                             else if (((request.getParameter("method").equals("showResourceTypesAll")) || (request.getParameter("method").equals("showResourceTypes"))) && (request.getParameter("group") != null))
/*      */                             {
/* 5396 */                               selectedValue = request.getParameter("group");
/*      */                             }
/*      */                             else
/*      */                             {
/* 5400 */                               selectedValue = request.getParameter("network");
/*      */                             }
/* 5402 */                             if ((!com.adventnet.appmanager.util.Constants.sqlManager) && (isEnterpriseSearch == null))
/*      */                             {
/*      */ 
/* 5405 */                               out.write("\n                ");
/*      */                               
/* 5407 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 5408 */                               _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 5409 */                               _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */                               
/* 5411 */                               _jspx_th_logic_005fnotEmpty_005f1.setName("listviewresourcetype");
/* 5412 */                               int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 5413 */                               if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                                 for (;;) {
/* 5415 */                                   out.write("\n\t\t\t<span class=\"filterby-txt input-uptime\" >\n\t\t\t");
/* 5416 */                                   out.print(FormatUtil.getString("am.webclient.common.filterby.text"));
/* 5417 */                                   out.write("&nbsp;:&nbsp;\n\t\t\t</span>\n\t\t\t");
/*      */                                   
/* 5419 */                                   SelectTag _jspx_th_html_005fselect_005f1 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 5420 */                                   _jspx_th_html_005fselect_005f1.setPageContext(_jspx_page_context);
/* 5421 */                                   _jspx_th_html_005fselect_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                                   
/* 5423 */                                   _jspx_th_html_005fselect_005f1.setProperty("type");
/*      */                                   
/* 5425 */                                   _jspx_th_html_005fselect_005f1.setValue(selectedValue);
/*      */                                   
/* 5427 */                                   _jspx_th_html_005fselect_005f1.setOnchange("javascript:loadURLType(this.options[this.selectedIndex].value,this.form,this,'customFieldValuesFilterbybottom');");
/*      */                                   
/* 5429 */                                   _jspx_th_html_005fselect_005f1.setStyleClass("chzn-select");
/*      */                                   
/* 5431 */                                   _jspx_th_html_005fselect_005f1.setStyle(customcolor + "width:220px");
/*      */                                   
/* 5433 */                                   _jspx_th_html_005fselect_005f1.setTabindex("5");
/* 5434 */                                   int _jspx_eval_html_005fselect_005f1 = _jspx_th_html_005fselect_005f1.doStartTag();
/* 5435 */                                   if (_jspx_eval_html_005fselect_005f1 != 0) {
/* 5436 */                                     if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5437 */                                       out = _jspx_page_context.pushBody();
/* 5438 */                                       _jspx_th_html_005fselect_005f1.setBodyContent((BodyContent)out);
/* 5439 */                                       _jspx_th_html_005fselect_005f1.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5442 */                                       out.write(" \n\t\t\t\t\t");
/*      */                                       
/* 5444 */                                       IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5445 */                                       _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 5446 */                                       _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                       
/* 5448 */                                       _jspx_th_logic_005fiterate_005f1.setName("listviewresourcetype");
/*      */                                       
/* 5450 */                                       _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                       
/* 5452 */                                       _jspx_th_logic_005fiterate_005f1.setIndexId("jj");
/*      */                                       
/* 5454 */                                       _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 5455 */                                       int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 5456 */                                       if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 5457 */                                         ArrayList row = null;
/* 5458 */                                         Integer jj = null;
/* 5459 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5460 */                                           out = _jspx_page_context.pushBody();
/* 5461 */                                           _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 5462 */                                           _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                         }
/* 5464 */                                         row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5465 */                                         jj = (Integer)_jspx_page_context.findAttribute("jj");
/*      */                                         for (;;) {
/* 5467 */                                           out.write("\n                    ");
/*      */                                           
/*      */ 
/*      */ 
/* 5471 */                                           boolean hasresourceGroup = false;
/* 5472 */                                           for (int i = 0; i < com.adventnet.appmanager.util.Constants.categoryLink.length; i++)
/*      */                                           {
/* 5474 */                                             if (com.adventnet.appmanager.util.Constants.categoryLink[i].equals((String)row.get(1)))
/*      */                                             {
/* 5476 */                                               hasresourceGroup = true;
/* 5477 */                                               break;
/*      */                                             }
/*      */                                           }
/* 5480 */                                           if (!com.adventnet.appmanager.util.Constants.sqlManager) { String style;
/* 5481 */                                             String style; if (((String)row.get(1) != null) && ((((String)row.get(1)).equals("All")) || (hasresourceGroup)))
/*      */                                             {
/*      */ 
/* 5484 */                                               style = "font-size:11px;font-weight:bold;";
/*      */ 
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5489 */                                               style = "font-size:11px;";
/*      */                                             }
/*      */                                             
/* 5492 */                                             out.write("\n\t\t\t\t\t");
/*      */                                             
/* 5494 */                                             OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5495 */                                             _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 5496 */                                             _jspx_th_html_005foption_005f2.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                             
/* 5498 */                                             _jspx_th_html_005foption_005f2.setValue((String)row.get(1));
/*      */                                             
/* 5500 */                                             _jspx_th_html_005foption_005f2.setStyle(style);
/* 5501 */                                             int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 5502 */                                             if (_jspx_eval_html_005foption_005f2 != 0) {
/* 5503 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 5504 */                                                 out = _jspx_page_context.pushBody();
/* 5505 */                                                 _jspx_th_html_005foption_005f2.setBodyContent((BodyContent)out);
/* 5506 */                                                 _jspx_th_html_005foption_005f2.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 5509 */                                                 out.print(row.get(0));
/* 5510 */                                                 int evalDoAfterBody = _jspx_th_html_005foption_005f2.doAfterBody();
/* 5511 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 5514 */                                               if (_jspx_eval_html_005foption_005f2 != 1) {
/* 5515 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 5518 */                                             if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 5519 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                             }
/*      */                                             
/* 5522 */                                             this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f2);
/* 5523 */                                             out.write("\n\t\t\t\t");
/*      */                                           } else {
/* 5525 */                                             if (((String)row.get(1) != null) && (((String)row.get(1)).equals("All")))
/*      */                                             {
/* 5527 */                                               String style = "background-color: #f2f9ff; padding:3px; float:left; font-weight:bold; font-size:11px;";
/*      */                                               
/* 5529 */                                               out.write("\n\t\t\t\t\t\t\t");
/*      */                                               
/* 5531 */                                               OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.get(OptionTag.class);
/* 5532 */                                               _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 5533 */                                               _jspx_th_html_005foption_005f3.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                               
/* 5535 */                                               _jspx_th_html_005foption_005f3.setValue((String)row.get(1));
/*      */                                               
/* 5537 */                                               _jspx_th_html_005foption_005f3.setStyle(style);
/* 5538 */                                               int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 5539 */                                               if (_jspx_eval_html_005foption_005f3 != 0) {
/* 5540 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 5541 */                                                   out = _jspx_page_context.pushBody();
/* 5542 */                                                   _jspx_th_html_005foption_005f3.setBodyContent((BodyContent)out);
/* 5543 */                                                   _jspx_th_html_005foption_005f3.doInitBody();
/*      */                                                 }
/*      */                                                 for (;;) {
/* 5546 */                                                   out.print(row.get(0));
/* 5547 */                                                   int evalDoAfterBody = _jspx_th_html_005foption_005f3.doAfterBody();
/* 5548 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 5551 */                                                 if (_jspx_eval_html_005foption_005f3 != 1) {
/* 5552 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 5555 */                                               if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 5556 */                                                 this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                               }
/*      */                                               
/* 5559 */                                               this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fstyle.reuse(_jspx_th_html_005foption_005f3);
/* 5560 */                                               out.write("\n\t\t\t\t\t\t\t");
/*      */                                             }
/*      */                                             
/*      */ 
/* 5564 */                                             out.write("\n\t\t\t\t");
/*      */                                           }
/* 5566 */                                           out.write("\n\t\t\t\t\t");
/* 5567 */                                           int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 5568 */                                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 5569 */                                           jj = (Integer)_jspx_page_context.findAttribute("jj");
/* 5570 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 5573 */                                         if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 5574 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 5577 */                                       if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 5578 */                                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                       }
/*      */                                       
/* 5581 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 5582 */                                       out.write("\n\t\t\t\t\t");
/*      */                                       
/* 5584 */                                       String customField = request.getParameter("customField");
/* 5585 */                                       request.setAttribute("tags", tags);
/* 5586 */                                       String selected = "";
/* 5587 */                                       int kk = 0;
/*      */                                       
/* 5589 */                                       out.write("\n                    <optgroup label=\"");
/* 5590 */                                       out.print(FormatUtil.getString("am.myfield.customfield.text"));
/* 5591 */                                       out.write("\">\n\t\t\t\t\t");
/*      */                                       
/* 5593 */                                       ForEachTag _jspx_th_c_005fforEach_005f1 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(ForEachTag.class);
/* 5594 */                                       _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
/* 5595 */                                       _jspx_th_c_005fforEach_005f1.setParent(_jspx_th_html_005fselect_005f1);
/*      */                                       
/* 5597 */                                       _jspx_th_c_005fforEach_005f1.setVar("eachtag");
/*      */                                       
/* 5599 */                                       _jspx_th_c_005fforEach_005f1.setItems("${tags}");
/* 5600 */                                       int[] _jspx_push_body_count_c_005fforEach_005f1 = { 0 };
/*      */                                       try {
/* 5602 */                                         int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
/* 5603 */                                         if (_jspx_eval_c_005fforEach_005f1 != 0) {
/*      */                                           for (;;) {
/* 5605 */                                             out.write("\n\t\t\t\t\t");
/*      */                                             
/* 5607 */                                             if ((selectedValue != null) && (selectedValue.equals(((Properties)tags.get(kk)).getProperty("labelalias"))))
/*      */                                             {
/* 5609 */                                               selected = "selected";
/*      */                                             }
/*      */                                             else
/*      */                                             {
/* 5613 */                                               selected = "";
/*      */                                             }
/* 5615 */                                             kk++;
/*      */                                             
/* 5617 */                                             out.write("\n                <option ");
/* 5618 */                                             out.print(selected);
/* 5619 */                                             out.write(" value=\"");
/* 5620 */                                             if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 5640 */                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 5641 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                             }
/* 5622 */                                             out.write(34);
/* 5623 */                                             out.write(62);
/* 5624 */                                             if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f1))
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
/* 5640 */                                               _jspx_th_c_005fforEach_005f1.doFinally();
/* 5641 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                             }
/* 5626 */                                             out.write("</option>\n                    ");
/* 5627 */                                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
/* 5628 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 5632 */                                         if (_jspx_th_c_005fforEach_005f1.doEndTag() == 5)
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 5640 */                                           _jspx_th_c_005fforEach_005f1.doFinally();
/* 5641 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1); return;
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Throwable _jspx_exception)
/*      */                                       {
/*      */                                         for (;;)
/*      */                                         {
/* 5636 */                                           int tmp20963_20962 = 0; int[] tmp20963_20960 = _jspx_push_body_count_c_005fforEach_005f1; int tmp20965_20964 = tmp20963_20960[tmp20963_20962];tmp20963_20960[tmp20963_20962] = (tmp20965_20964 - 1); if (tmp20965_20964 <= 0) break;
/* 5637 */                                           out = _jspx_page_context.popBody(); }
/* 5638 */                                         _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
/*      */                                       } finally {
/* 5640 */                                         _jspx_th_c_005fforEach_005f1.doFinally();
/* 5641 */                                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f1);
/*      */                                       }
/* 5643 */                                       out.write("\n\t\t\t\t</optgroup> \n        \t\t\t");
/* 5644 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f1.doAfterBody();
/* 5645 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5648 */                                     if (_jspx_eval_html_005fselect_005f1 != 1) {
/* 5649 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5652 */                                   if (_jspx_th_html_005fselect_005f1.doEndTag() == 5) {
/* 5653 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1); return;
/*      */                                   }
/*      */                                   
/* 5656 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fvalue_005ftabindex_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f1);
/* 5657 */                                   out.write("\n\t\t\t\t");
/* 5658 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 5659 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 5663 */                               if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 5664 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1); return;
/*      */                               }
/*      */                               
/* 5667 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 5668 */                               out.write("\n\t\t\t\t");
/*      */                             }
/*      */                             
/*      */ 
/* 5672 */                             out.write("\n\t\t\t\t&nbsp;&nbsp;\n\t\t\t\t<span id=\"customFieldValuesFilterbybottom\" ></span>\n\t\t\t</td>\n\t\t\t");
/* 5673 */                             if (isEnterpriseSearch == null) {
/* 5674 */                               out.write("\n        \t<td align=\"right\">\n        \t\t<div align=\"right\">\t\n\t\t\t\t\t");
/* 5675 */                               JspRuntimeLibrary.include(request, response, "/jsp/includes/NewPagingComp.jsp" + ("/jsp/includes/NewPagingComp.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("actionPath", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(actionPath), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showOnlyAtBottom", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("totalquery", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(totquery), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("rowcount", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(rowcount), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("showcountspan", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("true", request.getCharacterEncoding()), out, true);
/* 5676 */                               out.write("\n\t\t\t\t</div>\n        \t</td>\n        \t");
/*      */                             }
/* 5678 */                             out.write("\n       \t\t</form>\n       \t</tr>\n\t</table>\n\t");
/*      */                           }
/*      */                           
/*      */ 
/* 5682 */                           out.write("\n    <!-- Ends here -->\n");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 5688 */                           out.write("\n</td></tr></table>\n   \t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\"  style=\"background-color:#fff;\">\t   \n<tr>\n  <td   ");
/* 5689 */                           if (group != null) out.println("colspan='6'"); else out.println("colspan='5'");
/* 5690 */                           out.write(" height=\"30\" align=\"center\" class=\"emptyTableMsg\"><span class=\"bulkmon-links emptyTableMsg\">\n  ");
/*      */                           
/* 5692 */                           String tmpnet = request.getParameter("network");
/* 5693 */                           if (tmpnet == null) {
/* 5694 */                             tmpnet = "SYSTEM:9999";
/*      */                           }
/* 5696 */                           if ((tmpnet.trim().equals("Custom-Application")) && (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE")))
/*      */                           {
/* 5698 */                             tmpnet = "Script Monitor";
/*      */                           }
/* 5700 */                           else if (tmpnet.equals("JBOSS")) {
/* 5701 */                             tmpnet = "JBOSS-server";
/*      */                           }
/* 5703 */                           else if (tmpnet.equals("WTA")) {
/* 5704 */                             tmpnet = "WTA";
/*      */                           }
/* 5706 */                           else if (tmpnet.equals("DB2-server")) {
/* 5707 */                             tmpnet = "DB2-server";
/*      */                           }
/* 5709 */                           else if (tmpnet.equals("SYBASE-DB-server")) {
/* 5710 */                             tmpnet = "SYBASE-DB-server";
/*      */                           }
/* 5712 */                           else if (tmpnet.equals("MAIL-server")) {
/* 5713 */                             tmpnet = "MAIL-server";
/*      */                           }
/* 5715 */                           else if (tmpnet.equals("MX1.2-MX4J-RMI:1099")) {
/* 5716 */                             tmpnet = "JMX1.2-MX4J-RMI";
/*      */                           }
/* 5718 */                           else if (tmpnet.equals("SYS")) {
/* 5719 */                             tmpnet = "Windows";
/*      */                           }
/* 5721 */                           else if (tmpnet.equals("UrlMonitor")) {
/* 5722 */                             tmpnet = "UrlMonitor";
/*      */                           }
/* 5724 */                           else if (tmpnet.equals("WLS-Cluster")) {
/* 5725 */                             tmpnet = "WEBLOGIC-server";
/*      */                           }
/* 5727 */                           if (tmpnet != null)
/*      */                           {
/* 5729 */                             tmpnet = tmpnet.trim();
/*      */                           }
/* 5731 */                           String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=" + tmpnet + "&haid=" + request.getParameter("haid");
/* 5732 */                           if (request.getParameter("network").equals("EMO")) {
/* 5733 */                             link = "/extDeviceAction.do?method=addNewExtDevice&prodName=Site24x7";
/*      */                           }
/* 5735 */                           if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */                           {
/* 5737 */                             boolean showhere = true;
/* 5738 */                             if ("true".equals(request.getParameter("showmanage")))
/*      */                             {
/* 5740 */                               showhere = false;
/*      */                               
/* 5742 */                               out.write(10);
/* 5743 */                               out.write(9);
/* 5744 */                               out.write(32);
/* 5745 */                               out.print(FormatUtil.getString("am.monitortab.nomanaged.text"));
/* 5746 */                               out.write(".\t \n\t ");
/*      */                             }
/* 5748 */                             else if ("false".equals(request.getParameter("showmanage"))) {
/* 5749 */                               showhere = false;
/*      */                               
/* 5751 */                               out.write(10);
/* 5752 */                               out.write(9);
/* 5753 */                               out.print(FormatUtil.getString("am.monitortab.nounmanaged.text"));
/* 5754 */                               out.write(46);
/* 5755 */                               out.write(10);
/*      */                             }
/* 5757 */                             else if ("critical".equals(request.getParameter("showmanage")))
/*      */                             {
/* 5759 */                               showhere = false;
/*      */                               
/* 5761 */                               out.write(10);
/* 5762 */                               out.write(9);
/* 5763 */                               out.print(FormatUtil.getString("am.monitortab.nocritical.text"));
/* 5764 */                               out.write(46);
/* 5765 */                               out.write(10);
/*      */                             }
/*      */                             else
/*      */                             {
/* 5769 */                               out.print(FormatUtil.getString("am.monitortab.nomonitors.text"));
/* 5770 */                               out.write(46);
/* 5771 */                               out.write(10);
/*      */                             }
/*      */                             
/* 5774 */                             out.write(10);
/*      */                             
/* 5776 */                             if (showhere) {
/* 5777 */                               if (!EnterpriseUtil.isAdminServer())
/*      */                               {
/* 5779 */                                 if (request.getParameter("customValue") == null)
/*      */                                 {
/* 5781 */                                   out.print(FormatUtil.getString("am.monitortab.all.message.text"));
/* 5782 */                                   out.write(10);
/* 5783 */                                   out.write(32);
/*      */                                   
/* 5785 */                                   AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 5786 */                                   _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 5787 */                                   _jspx_th_am_005fadminlink_005f0.setParent(null);
/*      */                                   
/* 5789 */                                   _jspx_th_am_005fadminlink_005f0.setHref(link);
/*      */                                   
/* 5791 */                                   _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/* 5792 */                                   int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 5793 */                                   if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 5794 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 5795 */                                       out = _jspx_page_context.pushBody();
/* 5796 */                                       _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 5797 */                                       _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 5800 */                                       out.write(10);
/* 5801 */                                       out.write(32);
/* 5802 */                                       out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 5803 */                                       int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 5804 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 5807 */                                     if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 5808 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 5811 */                                   if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 5812 */                                     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                                   }
/*      */                                   
/* 5815 */                                   this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 5816 */                                   out.write(32);
/* 5817 */                                   out.write(10);
/* 5818 */                                   out.write(32);
/* 5819 */                                   out.print(FormatUtil.getString("am.monitortab.all.add.text"));
/* 5820 */                                   out.write(".</span></td>\n");
/*      */                                 }
/*      */                               }
/*      */                               else {
/* 5824 */                                 out.write(10);
/* 5825 */                                 out.write(9);
/* 5826 */                                 out.print(FormatUtil.getString("am.monitortab.nomonitors.admin.text"));
/* 5827 */                                 out.write("</span></td>\n");
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                           else
/*      */                           {
/* 5833 */                             String nwDevLink = "/showIT360Tile.do?TileName=IT360.QActions&service=OpManager&tabtoselect=6&url=/topo/AddNodeAction.do?requestid=addDevice&qAction_product=OpManager_Admin";
/*      */                             
/* 5835 */                             out.write(10);
/* 5836 */                             out.write(9);
/* 5837 */                             out.print(FormatUtil.getString("it360.nosearchresult.txt"));
/* 5838 */                             out.write("<br><br>\n\t");
/*      */                             
/* 5840 */                             boolean isPrivileged = request.isUserInRole("ADMIN");
/* 5841 */                             if ((!EnterpriseUtil.isAdminServer()) && (isPrivileged))
/*      */                             {
/* 5843 */                               if ((request.getParameter("oldtab") != null) && (request.getParameter("oldtab").equals("3")))
/*      */                               {
/*      */ 
/* 5846 */                                 out.write("\n\t\t\t");
/* 5847 */                                 out.print(FormatUtil.getString("it360.add.servappl.applicaton.txt", new String[] { link }));
/* 5848 */                                 out.write(10);
/* 5849 */                                 out.write(9);
/*      */ 
/*      */                               }
/* 5852 */                               else if ((request.getParameter("oldtab") != null) && (request.getParameter("oldtab").equals("2")))
/*      */                               {
/*      */ 
/* 5855 */                                 out.write("\n\t\t\t");
/* 5856 */                                 out.print(FormatUtil.getString("it360.add.servappl.server.txt", new String[] { link }));
/* 5857 */                                 out.write(10);
/* 5858 */                                 out.write(9);
/*      */ 
/*      */                               }
/* 5861 */                               else if ((request.getParameter("oldtab") != null) && (request.getParameter("oldtab").equals("1")))
/*      */                               {
/*      */ 
/* 5864 */                                 out.write("\n\t\t\t");
/* 5865 */                                 out.print(FormatUtil.getString("it360.add.nwdevice.txt", new String[] { nwDevLink }));
/* 5866 */                                 out.write(10);
/* 5867 */                                 out.write(9);
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 5872 */                             out.write("\n\t</span></td>\n");
/*      */                           }
/*      */                           
/* 5875 */                           out.write("\n</tr>\n</table>\n");
/*      */                         }
/*      */                         
/* 5878 */                         String selectedTyp = request.getParameter("network");
/*      */                         
/* 5880 */                         out.write(" \n\n <div id=\"virtualmachinetable\" style=\"display:block;\">\n\t\t\t\t\t");
/* 5881 */                         JspRuntimeLibrary.include(request, response, "/jsp/displayvirtualmachines.jsp" + ("/jsp/displayvirtualmachines.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("network", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(request.getParameter("network")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("group", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(group), request.getCharacterEncoding()), out, true);
/* 5882 */                         out.write(32);
/* 5883 */                         out.write("\n\t\t\t</div>\n\n\t<div id=\"ec2instancetable\" style=\"display:block;\">\n\t\t\t\t\t");
/* 5884 */                         JspRuntimeLibrary.include(request, response, "/jsp/displayEC2.jsp" + ("/jsp/displayEC2.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("network", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(request.getParameter("network")), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("group", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(group), request.getCharacterEncoding()), out, true);
/* 5885 */                         out.write(32);
/* 5886 */                         out.write("\n\t\t\t</div>\n</form>\n\n");
/*      */                       }
/*      */                       catch (Exception ee)
/*      */                       {
/* 5890 */                         ee.printStackTrace();
/*      */                       }
/*      */                       
/*      */                     }
/*      */                     catch (Exception e)
/*      */                     {
/* 5896 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 5899 */                     out.write(10);
/* 5900 */                     out.write(10);
/* 5901 */                     out.write(10);
/* 5902 */                     out.write(10);
/*      */                   }
/* 5904 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5905 */         out = _jspx_out;
/* 5906 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5907 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5908 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5911 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5917 */     PageContext pageContext = _jspx_page_context;
/* 5918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5920 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5921 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 5922 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 5924 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 5925 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 5926 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 5928 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 5929 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 5930 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5934 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 5935 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5936 */       return true;
/*      */     }
/* 5938 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 5939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5944 */     PageContext pageContext = _jspx_page_context;
/* 5945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5947 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5948 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 5949 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 5951 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 5952 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 5953 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 5955 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 5956 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 5957 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5961 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 5962 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5963 */       return true;
/*      */     }
/* 5965 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 5966 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5971 */     PageContext pageContext = _jspx_page_context;
/* 5972 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5974 */     PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5975 */     _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 5976 */     _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */     
/* 5978 */     _jspx_th_logic_005fpresent_005f2.setRole("DEMO");
/* 5979 */     int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 5980 */     if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */       for (;;) {
/* 5982 */         out.write("\n\t                alertUser();\n\t                return;\n\t        ");
/* 5983 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 5984 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5988 */     if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 5989 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 5990 */       return true;
/*      */     }
/* 5992 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 5993 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5998 */     PageContext pageContext = _jspx_page_context;
/* 5999 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6001 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6002 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 6003 */     _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */     
/* 6005 */     _jspx_th_logic_005fpresent_005f3.setRole("DEMO");
/* 6006 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 6007 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 6009 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 6010 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 6011 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6015 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 6016 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 6017 */       return true;
/*      */     }
/* 6019 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 6020 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6025 */     PageContext pageContext = _jspx_page_context;
/* 6026 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6028 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6029 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 6030 */     _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */     
/* 6032 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 6033 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 6034 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 6036 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 6037 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 6038 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6042 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 6043 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 6044 */       return true;
/*      */     }
/* 6046 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 6047 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6052 */     PageContext pageContext = _jspx_page_context;
/* 6053 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6055 */     PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6056 */     _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 6057 */     _jspx_th_logic_005fpresent_005f5.setParent(null);
/*      */     
/* 6059 */     _jspx_th_logic_005fpresent_005f5.setRole("DEMO");
/* 6060 */     int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 6061 */     if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */       for (;;) {
/* 6063 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 6064 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 6065 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6069 */     if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 6070 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 6071 */       return true;
/*      */     }
/* 6073 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 6074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6079 */     PageContext pageContext = _jspx_page_context;
/* 6080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6082 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6083 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 6084 */     _jspx_th_logic_005fpresent_005f6.setParent(null);
/*      */     
/* 6086 */     _jspx_th_logic_005fpresent_005f6.setRole("DEMO");
/* 6087 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 6088 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 6090 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 6091 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 6092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6096 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 6097 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 6098 */       return true;
/*      */     }
/* 6100 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 6101 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6106 */     PageContext pageContext = _jspx_page_context;
/* 6107 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6109 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 6110 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 6111 */     _jspx_th_logic_005fpresent_005f7.setParent(null);
/*      */     
/* 6113 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 6114 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 6115 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 6117 */         out.write("\n\t\talertUser();\n\t\treturn;\n\t");
/* 6118 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 6119 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6123 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 6124 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 6125 */       return true;
/*      */     }
/* 6127 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 6128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6133 */     PageContext pageContext = _jspx_page_context;
/* 6134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6136 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6137 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 6138 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 6140 */     _jspx_th_c_005fif_005f0.setTest("${param.search=='true'}");
/* 6141 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 6142 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 6144 */         out.write("\n\t<input type=\"hidden\" name=\"searchword\" id=\"searchword\" value='");
/* 6145 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 6146 */           return true;
/* 6147 */         out.write(39);
/* 6148 */         out.write(62);
/* 6149 */         out.write(10);
/* 6150 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 6151 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6155 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 6156 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6157 */       return true;
/*      */     }
/* 6159 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 6160 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6165 */     PageContext pageContext = _jspx_page_context;
/* 6166 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6168 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6169 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 6170 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 6172 */     _jspx_th_c_005fout_005f0.setValue("${param.query}");
/* 6173 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 6174 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 6175 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6176 */       return true;
/*      */     }
/* 6178 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 6179 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6184 */     PageContext pageContext = _jspx_page_context;
/* 6185 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6187 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6188 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 6189 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6191 */     _jspx_th_c_005fout_005f1.setValue("${eachtag.labelalias}");
/* 6192 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 6193 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 6194 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6195 */       return true;
/*      */     }
/* 6197 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 6198 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 6203 */     PageContext pageContext = _jspx_page_context;
/* 6204 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6206 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6207 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 6208 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 6210 */     _jspx_th_c_005fout_005f2.setValue("${eachtag.label}");
/* 6211 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 6212 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 6213 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6214 */       return true;
/*      */     }
/* 6216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 6217 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6222 */     PageContext pageContext = _jspx_page_context;
/* 6223 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6225 */     WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6226 */     _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 6227 */     _jspx_th_c_005fwhen_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/*      */     
/* 6229 */     _jspx_th_c_005fwhen_005f6.setTest("${param.search=='true'}");
/* 6230 */     int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 6231 */     if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */       for (;;) {
/* 6233 */         out.write("\n\t\t\t\t\t");
/* 6234 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 6235 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6239 */     if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 6240 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 6241 */       return true;
/*      */     }
/* 6243 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 6244 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6249 */     PageContext pageContext = _jspx_page_context;
/* 6250 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6252 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 6253 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 6254 */     _jspx_th_am_005fhiddenparam_005f0.setParent(null);
/*      */     
/* 6256 */     _jspx_th_am_005fhiddenparam_005f0.setName("selectedNetwork");
/* 6257 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 6258 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 6259 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 6260 */       return true;
/*      */     }
/* 6262 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 6263 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6268 */     PageContext pageContext = _jspx_page_context;
/* 6269 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6271 */     ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 6272 */     _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 6273 */     _jspx_th_c_005fchoose_005f7.setParent(null);
/* 6274 */     int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 6275 */     if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */       for (;;) {
/* 6277 */         out.write("\n              \t\t\t\t\t\t");
/* 6278 */         if (_jspx_meth_c_005fwhen_005f7(_jspx_th_c_005fchoose_005f7, _jspx_page_context))
/* 6279 */           return true;
/* 6280 */         out.write("\n      \t\t\t\t\t\t\t\t");
/* 6281 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 6282 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6286 */     if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 6287 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 6288 */       return true;
/*      */     }
/* 6290 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 6291 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f7(JspTag _jspx_th_c_005fchoose_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6296 */     PageContext pageContext = _jspx_page_context;
/* 6297 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6299 */     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 6300 */     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 6301 */     _jspx_th_c_005fwhen_005f7.setParent((Tag)_jspx_th_c_005fchoose_005f7);
/*      */     
/* 6303 */     _jspx_th_c_005fwhen_005f7.setTest("${allowEdit=='true' || allowUpdateIP=='true' || allowManage=='true' || allowReset=='true'}");
/* 6304 */     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 6305 */     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */       for (;;) {
/* 6307 */         out.write("\n      \t\t\t\t\t\t\t\t<td width=\"1%\"height=\"28\" valign=\"center\"  class=\"columnheading\">\n      \t\t\t\t\t\t\t\t\t<input type=\"checkbox\" name=\"headercheckbox1\"  onClick=\"javascript:fnSelectAll(this,'select');\">\n      \t\t\t\t\t\t\t\t</td>\n             \t\t\t\t\t\t");
/* 6308 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 6309 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6313 */     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 6314 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 6315 */       return true;
/*      */     }
/* 6317 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 6318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6323 */     PageContext pageContext = _jspx_page_context;
/* 6324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6326 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6327 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 6328 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 6330 */     _jspx_th_c_005fif_005f4.setTest("${alert[key]!=null}");
/* 6331 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 6332 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 6334 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 6335 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 6336 */           return true;
/* 6337 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 6338 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 6339 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6343 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 6344 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6345 */       return true;
/*      */     }
/* 6347 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 6348 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6353 */     PageContext pageContext = _jspx_page_context;
/* 6354 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6356 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6357 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 6358 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 6360 */     _jspx_th_c_005fout_005f3.setValue("${alert[key]}");
/* 6361 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 6362 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 6363 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6364 */       return true;
/*      */     }
/* 6366 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 6367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6372 */     PageContext pageContext = _jspx_page_context;
/* 6373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6375 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 6376 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 6377 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 6379 */     _jspx_th_c_005fif_005f5.setTest("${alert[key]!=null}");
/* 6380 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 6381 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 6383 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 6384 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 6385 */           return true;
/* 6386 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 6387 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 6388 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 6392 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 6393 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6394 */       return true;
/*      */     }
/* 6396 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 6397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6402 */     PageContext pageContext = _jspx_page_context;
/* 6403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6405 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6406 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 6407 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 6409 */     _jspx_th_c_005fout_005f4.setValue("${alert[key]}");
/* 6410 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 6411 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 6412 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6413 */       return true;
/*      */     }
/* 6415 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 6416 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6421 */     PageContext pageContext = _jspx_page_context;
/* 6422 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6424 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6425 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 6426 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6428 */     _jspx_th_c_005fout_005f5.setValue("${eachtag.labelalias}");
/* 6429 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 6430 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 6431 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6432 */       return true;
/*      */     }
/* 6434 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 6435 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f1) throws Throwable
/*      */   {
/* 6440 */     PageContext pageContext = _jspx_page_context;
/* 6441 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6443 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6444 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 6445 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f1);
/*      */     
/* 6447 */     _jspx_th_c_005fout_005f6.setValue("${eachtag.label}");
/* 6448 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 6449 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 6450 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6451 */       return true;
/*      */     }
/* 6453 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 6454 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\displayresources_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */