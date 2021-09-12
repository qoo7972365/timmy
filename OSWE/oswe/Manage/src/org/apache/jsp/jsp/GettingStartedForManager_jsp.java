/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.CpuGraph;
/*      */ import com.adventnet.appmanager.bean.HostResourceBean;
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.mysql.bean.MySqlGraphs;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class GettingStartedForManager_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  900 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*  914 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
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
/* 2185 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2186 */   static { _jspx_dependants.put("/jsp/includes/AdminLeftLinks.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2205 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2209 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2220 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2224 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2225 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2227 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2232 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2240 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2243 */     JspWriter out = null;
/* 2244 */     Object page = this;
/* 2245 */     JspWriter _jspx_out = null;
/* 2246 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2250 */       response.setContentType("text/html;charset=UTF-8");
/* 2251 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2253 */       _jspx_page_context = pageContext;
/* 2254 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2255 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2256 */       session = pageContext.getSession();
/* 2257 */       out = pageContext.getOut();
/* 2258 */       _jspx_out = out;
/*      */       
/* 2260 */       out.write("<!--$Id$-->\n");
/*      */       
/* 2262 */       request.setAttribute("HelpKey", "Manager Console");
/*      */       
/* 2264 */       out.write(10);
/* 2265 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2266 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2267 */       if (wlsGraph == null) {
/* 2268 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2269 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2271 */       out.write(10);
/* 2272 */       MySqlGraphs mysqlgraph = null;
/* 2273 */       mysqlgraph = (MySqlGraphs)_jspx_page_context.getAttribute("mysqlgraph", 1);
/* 2274 */       if (mysqlgraph == null) {
/* 2275 */         mysqlgraph = new MySqlGraphs();
/* 2276 */         _jspx_page_context.setAttribute("mysqlgraph", mysqlgraph, 1);
/*      */       }
/* 2278 */       out.write(10);
/* 2279 */       CpuGraph cpugraph = null;
/* 2280 */       cpugraph = (CpuGraph)_jspx_page_context.getAttribute("cpugraph", 1);
/* 2281 */       if (cpugraph == null) {
/* 2282 */         cpugraph = new CpuGraph();
/* 2283 */         _jspx_page_context.setAttribute("cpugraph", cpugraph, 1);
/*      */       }
/* 2285 */       out.write(10);
/* 2286 */       HostResourceBean hrbean = null;
/* 2287 */       hrbean = (HostResourceBean)_jspx_page_context.getAttribute("hrbean", 1);
/* 2288 */       if (hrbean == null) {
/* 2289 */         hrbean = new HostResourceBean();
/* 2290 */         _jspx_page_context.setAttribute("hrbean", hrbean, 1);
/*      */       }
/* 2292 */       out.write(10);
/* 2293 */       PerformanceBean perfgraph = null;
/* 2294 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2295 */       if (perfgraph == null) {
/* 2296 */         perfgraph = new PerformanceBean();
/* 2297 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2299 */       out.write("\n\n \n\n\n\n\n\n\n\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/appmanager.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/webclient/common/js/validation.js\"></SCRIPT>\n\n\n\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\">\n");
/*      */       
/* 2301 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2302 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2303 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2305 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2306 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2307 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2309 */           out.write(32);
/*      */           
/* 2311 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2312 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2313 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2315 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2317 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.gsformanager.text"));
/* 2318 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2319 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2320 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2323 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2324 */           out.write(32);
/* 2325 */           out.write(10);
/* 2326 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2328 */           out.write(32);
/*      */           
/* 2330 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2331 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2332 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2334 */           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */           
/* 2336 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2337 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2338 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2339 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2340 */               out = _jspx_page_context.pushBody();
/* 2341 */               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2342 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2345 */               out.write(32);
/* 2346 */               out.write(10);
/* 2347 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2349 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2350 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2351 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2355 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2357 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2359 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2360 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2361 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2362 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2365 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2366 */               String available = null;
/* 2367 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2368 */               out.write(10);
/*      */               
/* 2370 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2372 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2374 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2376 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2378 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2380 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2381 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2382 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2383 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2386 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2387 */               String unavailable = null;
/* 2388 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2389 */               out.write(10);
/*      */               
/* 2391 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2393 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2395 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2397 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2399 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2401 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2402 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2403 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2404 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2407 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2408 */               String unmanaged = null;
/* 2409 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2410 */               out.write(10);
/*      */               
/* 2412 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2413 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2414 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2416 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2418 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2420 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2422 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2423 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2424 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2425 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2428 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2429 */               String scheduled = null;
/* 2430 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2431 */               out.write(10);
/*      */               
/* 2433 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2434 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2435 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f2);
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
/* 2446 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2449 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2450 */               String critical = null;
/* 2451 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2452 */               out.write(10);
/*      */               
/* 2454 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2455 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2456 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2458 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2460 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2462 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2464 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2465 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2466 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2467 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2470 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2471 */               String clear = null;
/* 2472 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2473 */               out.write(10);
/*      */               
/* 2475 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2476 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2477 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2479 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2481 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2483 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2485 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2486 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2487 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2488 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2491 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2492 */               String warning = null;
/* 2493 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2494 */               out.write(10);
/* 2495 */               out.write(10);
/*      */               
/* 2497 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2498 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2500 */               out.write(10);
/* 2501 */               out.write(10);
/* 2502 */               out.write(10);
/* 2503 */               out.write(10);
/* 2504 */               out.write("<!--$Id$-->\n\n\n\n\n\n");
/*      */               
/*      */ 
/* 2507 */               String usertype = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().getUserType().trim();
/*      */               
/* 2509 */               out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\t\nfunction Call()\n{\n alert(\"");
/* 2510 */               out.print(FormatUtil.getString("wizard.disabled"));
/* 2511 */               out.write("\");\n}\n</script>\n    \n     <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n  <tr> \n    <td height=\"21\"  class=\"leftlinksheading\">");
/* 2512 */               out.print(FormatUtil.getString("am.webclient.admin.heading"));
/* 2513 */               out.write("</td>\n  </tr>\n  \n ");
/*      */               
/* 2515 */               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */               {
/*      */ 
/* 2518 */                 out.write("  \n  <tr>\n\n  ");
/*      */                 
/* 2520 */                 if (request.getParameter("wiz") != null)
/*      */                 {
/* 2522 */                   out.write("\n\t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\" title=\"");
/* 2523 */                   out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2524 */                   out.write("\" class='disabledlink'>");
/* 2525 */                   out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2526 */                   out.write("</a></td>\n  ");
/*      */                 }
/*      */                 else
/*      */                 {
/* 2530 */                   out.write("\n\t<td class=\"leftlinkstd\" >\n");
/*      */                   
/* 2532 */                   ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2533 */                   _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2534 */                   _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/* 2535 */                   int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2536 */                   if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                     for (;;) {
/* 2538 */                       out.write(10);
/*      */                       
/* 2540 */                       WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2541 */                       _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2542 */                       _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                       
/* 2544 */                       _jspx_th_c_005fwhen_005f0.setTest("${uri !='/jsp/CreateApplication.jsp' && uri !='/admin/createapplication.do' && uri!='/admin/createapplicationwiz.do'}");
/* 2545 */                       int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2546 */                       if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                         for (;;) {
/* 2548 */                           out.write("    \n            <a href=\"/admin/createapplication.do?method=createapp&grouptype=1\" class=\"new-left-links\" access=\"110\">\n              ");
/* 2549 */                           out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2550 */                           out.write("\n    </a>\n ");
/* 2551 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2552 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2556 */                       if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2557 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                       }
/*      */                       
/* 2560 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2561 */                       out.write(10);
/* 2562 */                       out.write(32);
/*      */                       
/* 2564 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2565 */                       _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2566 */                       _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2567 */                       int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2568 */                       if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                         for (;;) {
/* 2570 */                           out.write(10);
/* 2571 */                           out.write(9);
/* 2572 */                           out.write(32);
/* 2573 */                           out.print(FormatUtil.getString("am.webclient.admin.monitorgroup.link"));
/* 2574 */                           out.write(10);
/* 2575 */                           out.write(32);
/* 2576 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2577 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2581 */                       if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2582 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                       }
/*      */                       
/* 2585 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2586 */                       out.write(10);
/* 2587 */                       out.write(32);
/* 2588 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2589 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2593 */                   if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2594 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                   }
/*      */                   
/* 2597 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2598 */                   out.write("\n    </td>\n\t");
/*      */                 }
/* 2600 */                 out.write("\n</tr>  \n        <tr>\n    \n   ");
/*      */                 
/* 2602 */                 if (request.getParameter("wiz") != null)
/*      */                 {
/* 2604 */                   out.write("\n    \t<td height=\"19\" class=\"leftlinkstd\"><a href='#' onClick=\"javascript:Call()\"title=\"");
/* 2605 */                   out.print(FormatUtil.getString("am.webclient.header.title.wizdisabled.text"));
/* 2606 */                   out.write("\" class='disabledlink'>");
/* 2607 */                   out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2608 */                   out.write("</a></td>\n   ");
/*      */                 }
/*      */                 else
/*      */                 {
/* 2612 */                   out.write("\n    <td class=\"leftlinkstd\">\n    \n");
/*      */                   
/* 2614 */                   ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2615 */                   _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2616 */                   _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2617 */                   int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2618 */                   if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                     for (;;) {
/* 2620 */                       out.write(10);
/*      */                       
/* 2622 */                       WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2623 */                       _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2624 */                       _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                       
/* 2626 */                       _jspx_th_c_005fwhen_005f1.setTest("${param.method =='showMonitorTemplates' || param.method == 'reloadHostDiscoveryForm'}");
/* 2627 */                       int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2628 */                       if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                         for (;;) {
/* 2630 */                           out.write("\n   ");
/* 2631 */                           out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2632 */                           out.write(10);
/* 2633 */                           out.write(32);
/* 2634 */                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2635 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2639 */                       if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2640 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                       }
/*      */                       
/* 2643 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2644 */                       out.write(10);
/* 2645 */                       out.write(32);
/*      */                       
/* 2647 */                       OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2648 */                       _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2649 */                       _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2650 */                       int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2651 */                       if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                         for (;;) {
/* 2653 */                           out.write(10);
/* 2654 */                           String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=SYSTEM:9999";
/* 2655 */                           out.write("\n\t \n <a href=\"");
/* 2656 */                           out.print(link);
/* 2657 */                           out.write("\" class=\"new-left-links\">\n               ");
/* 2658 */                           out.print(FormatUtil.getString("am.webclient.admin.newmonitor.link"));
/* 2659 */                           out.write("\n    </a>    \n ");
/* 2660 */                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2661 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2665 */                       if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2666 */                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                       }
/*      */                       
/* 2669 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2670 */                       out.write(10);
/* 2671 */                       out.write(32);
/* 2672 */                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2673 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2677 */                   if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2678 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                   }
/*      */                   
/* 2681 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2682 */                   out.write("\n</td>\n");
/*      */                 }
/* 2684 */                 out.write("\n</tr>\n\n ");
/*      */               }
/*      */               
/*      */ 
/* 2688 */               out.write("\n \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/* 2690 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2691 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2692 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2693 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2694 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                 for (;;) {
/* 2696 */                   out.write(10);
/*      */                   
/* 2698 */                   WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2699 */                   _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2700 */                   _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                   
/* 2702 */                   _jspx_th_c_005fwhen_005f2.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Actionalert'}");
/* 2703 */                   int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2704 */                   if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                     for (;;) {
/* 2706 */                       out.write("\n    \n       ");
/* 2707 */                       out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2708 */                       out.write(10);
/* 2709 */                       out.write(32);
/* 2710 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2711 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2715 */                   if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2716 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                   }
/*      */                   
/* 2719 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2720 */                   out.write(10);
/* 2721 */                   out.write(32);
/*      */                   
/* 2723 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2724 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2725 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2726 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2727 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/* 2729 */                       out.write("\n       <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Actionalert\" class=\"new-left-links\">\n ");
/* 2730 */                       out.print(FormatUtil.getString("am.webclient.admin.action.link"));
/* 2731 */                       out.write("\n    </a>\n ");
/* 2732 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2733 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2737 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2738 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/* 2741 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2742 */                   out.write(10);
/* 2743 */                   out.write(32);
/* 2744 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2745 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2749 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2750 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */               }
/*      */               
/* 2753 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2754 */               out.write("\n    </td>\n</tr>   \n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/* 2756 */               ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2757 */               _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2758 */               _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/* 2759 */               int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2760 */               if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                 for (;;) {
/* 2762 */                   out.write(10);
/*      */                   
/* 2764 */                   WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2765 */                   _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2766 */                   _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                   
/* 2768 */                   _jspx_th_c_005fwhen_005f3.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='Availablity'}");
/* 2769 */                   int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2770 */                   if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                     for (;;) {
/* 2772 */                       out.write("\n    \n       ");
/* 2773 */                       out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2774 */                       out.write(10);
/* 2775 */                       out.write(32);
/* 2776 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2777 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2781 */                   if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2782 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                   }
/*      */                   
/* 2785 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2786 */                   out.write(10);
/* 2787 */                   out.write(32);
/*      */                   
/* 2789 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2790 */                   _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 2791 */                   _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 2792 */                   int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 2793 */                   if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                     for (;;) {
/* 2795 */                       out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=Availablity\" class=\"new-left-links\">\n\t ");
/* 2796 */                       out.print(FormatUtil.getString("am.webclient.admin.availability.link"));
/* 2797 */                       out.write("\n\t </a>\n ");
/* 2798 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 2799 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2803 */                   if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 2804 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                   }
/*      */                   
/* 2807 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 2808 */                   out.write(10);
/* 2809 */                   out.write(32);
/* 2810 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2811 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2815 */               if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2816 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */               }
/*      */               
/* 2819 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2820 */               out.write("\n    </td>\n</tr>  \n\n  ");
/*      */               
/* 2822 */               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */               {
/*      */ 
/* 2825 */                 out.write(32);
/* 2826 */                 out.write(32);
/* 2827 */                 out.write(10);
/*      */                 
/* 2829 */                 ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2830 */                 _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 2831 */                 _jspx_th_c_005fchoose_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/* 2832 */                 int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 2833 */                 if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */                   for (;;) {
/* 2835 */                     out.write(10);
/*      */                     
/* 2837 */                     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2838 */                     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 2839 */                     _jspx_th_c_005fwhen_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/*      */                     
/* 2841 */                     _jspx_th_c_005fwhen_005f4.setTest("${param.method !='showNetworkDiscoveryForm'}");
/* 2842 */                     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 2843 */                     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */                       for (;;) {
/* 2845 */                         out.write("\n<tr>\n    ");
/* 2846 */                         if (!request.isUserInRole("OPERATOR")) {
/* 2847 */                           out.write("\n    <td class=\"leftlinkstd\" >    \n        <a href=\"/jsp/DiscoveryProfiles.jsp?showlink=network\" class=\"new-left-links\">\n           ");
/* 2848 */                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2849 */                           out.write("\n    </a>\n        </td>\n     ");
/*      */                         } else {
/* 2851 */                           out.write("\n\t<td class=\"leftlinkstd\" > <a href=\"javascript:void(0)\" class=\"disabledlink\">\n\t ");
/* 2852 */                           out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2853 */                           out.write("\n\t</a>\n\t </td>\n\t");
/*      */                         }
/* 2855 */                         out.write("\n    </tr>\n ");
/* 2856 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 2857 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2861 */                     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 2862 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4); return;
/*      */                     }
/*      */                     
/* 2865 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 2866 */                     out.write(10);
/* 2867 */                     out.write(32);
/*      */                     
/* 2869 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2870 */                     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 2871 */                     _jspx_th_c_005fotherwise_005f4.setParent(_jspx_th_c_005fchoose_005f4);
/* 2872 */                     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 2873 */                     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */                       for (;;) {
/* 2875 */                         out.write("\n \t<td class=\"leftlinkstd\" > \n\t ");
/* 2876 */                         out.print(FormatUtil.getString("am.webclient.admin.discovery.link"));
/* 2877 */                         out.write("\n\t </td>\n ");
/* 2878 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 2879 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2883 */                     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 2884 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4); return;
/*      */                     }
/*      */                     
/* 2887 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 2888 */                     out.write(10);
/* 2889 */                     out.write(32);
/* 2890 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 2891 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2895 */                 if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 2896 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4); return;
/*      */                 }
/*      */                 
/* 2899 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 2900 */                 out.write("\n \n  ");
/*      */               }
/*      */               
/*      */ 
/* 2904 */               out.write("  \n \n ");
/*      */               
/* 2906 */               if (!usertype.equals("F"))
/*      */               {
/* 2908 */                 out.write("\n \n  <tr>   \n     <td class=\"leftlinkstd\" >\n\t");
/*      */                 
/* 2910 */                 ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2911 */                 _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2912 */                 _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/* 2913 */                 int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2914 */                 if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                   for (;;) {
/* 2916 */                     out.write(10);
/* 2917 */                     out.write(9);
/*      */                     
/* 2919 */                     WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2920 */                     _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2921 */                     _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                     
/* 2923 */                     _jspx_th_c_005fwhen_005f5.setTest("${param.method !='maintenanceTaskListView'}");
/* 2924 */                     int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2925 */                     if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                       for (;;) {
/* 2927 */                         out.write("     \n        \t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 2928 */                         out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2929 */                         out.write("</a>\n  \t");
/* 2930 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2931 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2935 */                     if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2936 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5); return;
/*      */                     }
/*      */                     
/* 2939 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2940 */                     out.write("\n  \t");
/*      */                     
/* 2942 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2943 */                     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 2944 */                     _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/* 2945 */                     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 2946 */                     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                       for (;;) {
/* 2948 */                         out.write("\n \t\t");
/* 2949 */                         out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 2950 */                         out.write("\n  \t");
/* 2951 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 2952 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2956 */                     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 2957 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                     }
/*      */                     
/* 2960 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2961 */                     out.write("\n  \t");
/* 2962 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2963 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2967 */                 if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2968 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5); return;
/*      */                 }
/*      */                 
/* 2971 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2972 */                 out.write("\n     </td>\n </tr>   \n \n ");
/*      */                 
/* 2974 */                 if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */                 {
/*      */ 
/* 2977 */                   out.write(32);
/* 2978 */                   out.write(32);
/* 2979 */                   out.write(10);
/*      */                   
/* 2981 */                   IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2982 */                   _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2983 */                   _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                   
/* 2985 */                   _jspx_th_c_005fif_005f0.setTest("${category!='LAMP'}");
/* 2986 */                   int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2987 */                   if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                     for (;;) {
/* 2989 */                       out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n \t");
/*      */                       
/* 2991 */                       ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2992 */                       _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2993 */                       _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_c_005fif_005f0);
/* 2994 */                       int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2995 */                       if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                         for (;;) {
/* 2997 */                           out.write(10);
/* 2998 */                           out.write(32);
/* 2999 */                           out.write(9);
/*      */                           
/* 3001 */                           WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3002 */                           _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 3003 */                           _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                           
/* 3005 */                           _jspx_th_c_005fwhen_005f6.setTest("${param.method !='listTrapListener'}");
/* 3006 */                           int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 3007 */                           if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                             for (;;) {
/* 3009 */                               out.write("     \n         \t<a href=\"/adminAction.do?method=listTrapListener\" class=\"new-left-links\">");
/* 3010 */                               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3011 */                               out.write("</a>\n   \t");
/* 3012 */                               int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 3013 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3017 */                           if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 3018 */                             this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                           }
/*      */                           
/* 3021 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 3022 */                           out.write("\n   \t");
/*      */                           
/* 3024 */                           OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3025 */                           _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3026 */                           _jspx_th_c_005fotherwise_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/* 3027 */                           int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3028 */                           if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */                             for (;;) {
/* 3030 */                               out.write("\n  \t\t");
/* 3031 */                               out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3032 */                               out.write(" \n   \t");
/* 3033 */                               int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3034 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3038 */                           if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3039 */                             this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6); return;
/*      */                           }
/*      */                           
/* 3042 */                           this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3043 */                           out.write("\n   \t");
/* 3044 */                           int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 3045 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3049 */                       if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 3050 */                         this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */                       }
/*      */                       
/* 3053 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 3054 */                       out.write("\n      </td>\n  </tr>   \n");
/* 3055 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3056 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3060 */                   if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3061 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                   }
/*      */                   
/* 3064 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3065 */                   out.write(10);
/* 3066 */                   out.write(32);
/*      */                 }
/*      */                 
/*      */ 
/* 3070 */                 out.write("  \n\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                 
/* 3072 */                 ChooseTag _jspx_th_c_005fchoose_005f7 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3073 */                 _jspx_th_c_005fchoose_005f7.setPageContext(_jspx_page_context);
/* 3074 */                 _jspx_th_c_005fchoose_005f7.setParent(_jspx_th_tiles_005fput_005f2);
/* 3075 */                 int _jspx_eval_c_005fchoose_005f7 = _jspx_th_c_005fchoose_005f7.doStartTag();
/* 3076 */                 if (_jspx_eval_c_005fchoose_005f7 != 0) {
/*      */                   for (;;) {
/* 3078 */                     out.write(10);
/*      */                     
/* 3080 */                     WhenTag _jspx_th_c_005fwhen_005f7 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3081 */                     _jspx_th_c_005fwhen_005f7.setPageContext(_jspx_page_context);
/* 3082 */                     _jspx_th_c_005fwhen_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/*      */                     
/* 3084 */                     _jspx_th_c_005fwhen_005f7.setTest("${param.method =='showScheduleReports'}");
/* 3085 */                     int _jspx_eval_c_005fwhen_005f7 = _jspx_th_c_005fwhen_005f7.doStartTag();
/* 3086 */                     if (_jspx_eval_c_005fwhen_005f7 != 0) {
/*      */                       for (;;) {
/* 3088 */                         out.write("\n       ");
/* 3089 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3090 */                         out.write(10);
/* 3091 */                         out.write(32);
/* 3092 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f7.doAfterBody();
/* 3093 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3097 */                     if (_jspx_th_c_005fwhen_005f7.doEndTag() == 5) {
/* 3098 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7); return;
/*      */                     }
/*      */                     
/* 3101 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f7);
/* 3102 */                     out.write(10);
/* 3103 */                     out.write(32);
/*      */                     
/* 3105 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f7 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3106 */                     _jspx_th_c_005fotherwise_005f7.setPageContext(_jspx_page_context);
/* 3107 */                     _jspx_th_c_005fotherwise_005f7.setParent(_jspx_th_c_005fchoose_005f7);
/* 3108 */                     int _jspx_eval_c_005fotherwise_005f7 = _jspx_th_c_005fotherwise_005f7.doStartTag();
/* 3109 */                     if (_jspx_eval_c_005fotherwise_005f7 != 0) {
/*      */                       for (;;) {
/* 3111 */                         out.write("\n     <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n\t");
/* 3112 */                         out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3113 */                         out.write("\n\t </a>\n ");
/* 3114 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f7.doAfterBody();
/* 3115 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3119 */                     if (_jspx_th_c_005fotherwise_005f7.doEndTag() == 5) {
/* 3120 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7); return;
/*      */                     }
/*      */                     
/* 3123 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f7);
/* 3124 */                     out.write(10);
/* 3125 */                     out.write(32);
/* 3126 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f7.doAfterBody();
/* 3127 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3131 */                 if (_jspx_th_c_005fchoose_005f7.doEndTag() == 5) {
/* 3132 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7); return;
/*      */                 }
/*      */                 
/* 3135 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f7);
/* 3136 */                 out.write("\n    </td>\n</tr> \n");
/*      */               } else {
/* 3138 */                 out.write("\n <tr>   \n     <td class=\"leftlinkstd\">\n\t<a href=\"/downTimeScheduler.do?method=maintenanceTaskListView\" class=\"new-left-links\">");
/* 3139 */                 out.print(FormatUtil.getString("am.webclient.admin.downtimeschedular.link"));
/* 3140 */                 out.write("</a>\n     </td>\n </tr>   \n");
/*      */                 
/* 3142 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3143 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3144 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                 
/* 3146 */                 _jspx_th_c_005fif_005f1.setTest("${category!='LAMP'}");
/* 3147 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3148 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/* 3150 */                     out.write("\t\t      \n   <tr>   \n      <td class=\"leftlinkstd\" >\n\t\t<a  class=\"disabledlink\">");
/* 3151 */                     out.print(FormatUtil.getString("am.webclient.admin.traplistener.link"));
/* 3152 */                     out.write("</a>\n\t  </td>\n  </tr>   \n");
/* 3153 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3154 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3158 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3159 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/* 3162 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3163 */                 out.write("\n\n<tr>\n    <td class=\"leftlinkstd\" >\n\t <a href=\"/scheduleReports.do?method=showScheduleReports\" class=\"new-left-links\">\n        ");
/* 3164 */                 out.print(FormatUtil.getString("am.webclient.schedulereport.showschedule.schedulereports.text"));
/* 3165 */                 out.write("\n         </a>\n\n    </td>\n</tr> \n");
/*      */               }
/* 3167 */               out.write("\n <tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/* 3169 */               ChooseTag _jspx_th_c_005fchoose_005f8 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3170 */               _jspx_th_c_005fchoose_005f8.setPageContext(_jspx_page_context);
/* 3171 */               _jspx_th_c_005fchoose_005f8.setParent(_jspx_th_tiles_005fput_005f2);
/* 3172 */               int _jspx_eval_c_005fchoose_005f8 = _jspx_th_c_005fchoose_005f8.doStartTag();
/* 3173 */               if (_jspx_eval_c_005fchoose_005f8 != 0) {
/*      */                 for (;;) {
/* 3175 */                   out.write(10);
/*      */                   
/* 3177 */                   WhenTag _jspx_th_c_005fwhen_005f8 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3178 */                   _jspx_th_c_005fwhen_005f8.setPageContext(_jspx_page_context);
/* 3179 */                   _jspx_th_c_005fwhen_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/*      */                   
/* 3181 */                   _jspx_th_c_005fwhen_005f8.setTest("${param.method =='showGlobalSettingsConfiguration' && param.typetoshow =='general'}");
/* 3182 */                   int _jspx_eval_c_005fwhen_005f8 = _jspx_th_c_005fwhen_005f8.doStartTag();
/* 3183 */                   if (_jspx_eval_c_005fwhen_005f8 != 0) {
/*      */                     for (;;) {
/* 3185 */                       out.write("\n        ");
/* 3186 */                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3187 */                       out.write(10);
/* 3188 */                       out.write(32);
/* 3189 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f8.doAfterBody();
/* 3190 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3194 */                   if (_jspx_th_c_005fwhen_005f8.doEndTag() == 5) {
/* 3195 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8); return;
/*      */                   }
/*      */                   
/* 3198 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f8);
/* 3199 */                   out.write(10);
/* 3200 */                   out.write(32);
/*      */                   
/* 3202 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f8 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3203 */                   _jspx_th_c_005fotherwise_005f8.setPageContext(_jspx_page_context);
/* 3204 */                   _jspx_th_c_005fotherwise_005f8.setParent(_jspx_th_c_005fchoose_005f8);
/* 3205 */                   int _jspx_eval_c_005fotherwise_005f8 = _jspx_th_c_005fotherwise_005f8.doStartTag();
/* 3206 */                   if (_jspx_eval_c_005fotherwise_005f8 != 0) {
/*      */                     for (;;) {
/* 3208 */                       out.write("\n     <a href=\"/adminAction.do?method=showGlobalSettingsConfiguration&typetoshow=general\" class=\"new-left-links\">\n\t ");
/* 3209 */                       out.print(FormatUtil.getString("am.webclient.admin.globalsettings.link"));
/* 3210 */                       out.write("\n\t </a>\n ");
/* 3211 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f8.doAfterBody();
/* 3212 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3216 */                   if (_jspx_th_c_005fotherwise_005f8.doEndTag() == 5) {
/* 3217 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8); return;
/*      */                   }
/*      */                   
/* 3220 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f8);
/* 3221 */                   out.write(10);
/* 3222 */                   out.write(32);
/* 3223 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f8.doAfterBody();
/* 3224 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3228 */               if (_jspx_th_c_005fchoose_005f8.doEndTag() == 5) {
/* 3229 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8); return;
/*      */               }
/*      */               
/* 3232 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f8);
/* 3233 */               out.write("\n    </td>\n</tr>   \n\n<tr>\n\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */               
/* 3235 */               ChooseTag _jspx_th_c_005fchoose_005f9 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3236 */               _jspx_th_c_005fchoose_005f9.setPageContext(_jspx_page_context);
/* 3237 */               _jspx_th_c_005fchoose_005f9.setParent(_jspx_th_tiles_005fput_005f2);
/* 3238 */               int _jspx_eval_c_005fchoose_005f9 = _jspx_th_c_005fchoose_005f9.doStartTag();
/* 3239 */               if (_jspx_eval_c_005fchoose_005f9 != 0) {
/*      */                 for (;;) {
/* 3241 */                   out.write(10);
/*      */                   
/* 3243 */                   WhenTag _jspx_th_c_005fwhen_005f9 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3244 */                   _jspx_th_c_005fwhen_005f9.setPageContext(_jspx_page_context);
/* 3245 */                   _jspx_th_c_005fwhen_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/*      */                   
/* 3247 */                   _jspx_th_c_005fwhen_005f9.setTest("${param.method!='showMailServerConfiguration'}");
/* 3248 */                   int _jspx_eval_c_005fwhen_005f9 = _jspx_th_c_005fwhen_005f9.doStartTag();
/* 3249 */                   if (_jspx_eval_c_005fwhen_005f9 != 0) {
/*      */                     for (;;) {
/* 3251 */                       out.write("    \n    <a href=\"/adminAction.do?method=showMailServerConfiguration\" class=\"new-left-links\">\n    ");
/* 3252 */                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3253 */                       out.write("\n    </a>    \n ");
/* 3254 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f9.doAfterBody();
/* 3255 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3259 */                   if (_jspx_th_c_005fwhen_005f9.doEndTag() == 5) {
/* 3260 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9); return;
/*      */                   }
/*      */                   
/* 3263 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f9);
/* 3264 */                   out.write(10);
/* 3265 */                   out.write(32);
/*      */                   
/* 3267 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f9 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3268 */                   _jspx_th_c_005fotherwise_005f9.setPageContext(_jspx_page_context);
/* 3269 */                   _jspx_th_c_005fotherwise_005f9.setParent(_jspx_th_c_005fchoose_005f9);
/* 3270 */                   int _jspx_eval_c_005fotherwise_005f9 = _jspx_th_c_005fotherwise_005f9.doStartTag();
/* 3271 */                   if (_jspx_eval_c_005fotherwise_005f9 != 0) {
/*      */                     for (;;) {
/* 3273 */                       out.write(10);
/* 3274 */                       out.write(9);
/* 3275 */                       out.write(32);
/* 3276 */                       out.print(FormatUtil.getString("am.webclient.admin.mailserver.link"));
/* 3277 */                       out.write(10);
/* 3278 */                       out.write(32);
/* 3279 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f9.doAfterBody();
/* 3280 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3284 */                   if (_jspx_th_c_005fotherwise_005f9.doEndTag() == 5) {
/* 3285 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9); return;
/*      */                   }
/*      */                   
/* 3288 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f9);
/* 3289 */                   out.write(10);
/* 3290 */                   out.write(32);
/* 3291 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f9.doAfterBody();
/* 3292 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3296 */               if (_jspx_th_c_005fchoose_005f9.doEndTag() == 5) {
/* 3297 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9); return;
/*      */               }
/*      */               
/* 3300 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f9);
/* 3301 */               out.write("\n    </td>\n</tr>\n\n\n");
/* 3302 */               if ((System.getProperty("os.name").startsWith("Windows")) || (System.getProperty("os.name").startsWith("windows"))) {
/* 3303 */                 out.write("<tr>\n\n    <td class=\"leftlinkstd\" >\n");
/*      */                 
/* 3305 */                 ChooseTag _jspx_th_c_005fchoose_005f10 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3306 */                 _jspx_th_c_005fchoose_005f10.setPageContext(_jspx_page_context);
/* 3307 */                 _jspx_th_c_005fchoose_005f10.setParent(_jspx_th_tiles_005fput_005f2);
/* 3308 */                 int _jspx_eval_c_005fchoose_005f10 = _jspx_th_c_005fchoose_005f10.doStartTag();
/* 3309 */                 if (_jspx_eval_c_005fchoose_005f10 != 0) {
/*      */                   for (;;) {
/* 3311 */                     out.write(10);
/*      */                     
/* 3313 */                     WhenTag _jspx_th_c_005fwhen_005f10 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3314 */                     _jspx_th_c_005fwhen_005f10.setPageContext(_jspx_page_context);
/* 3315 */                     _jspx_th_c_005fwhen_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/*      */                     
/* 3317 */                     _jspx_th_c_005fwhen_005f10.setTest("${param.method!='SMSServerConfiguration'}");
/* 3318 */                     int _jspx_eval_c_005fwhen_005f10 = _jspx_th_c_005fwhen_005f10.doStartTag();
/* 3319 */                     if (_jspx_eval_c_005fwhen_005f10 != 0) {
/*      */                       for (;;) {
/* 3321 */                         out.write("\n    <a href=\"/adminAction.do?method=SMSServerConfiguration&admin=true\" class=\"new-left-links\">\n    ");
/* 3322 */                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3323 */                         out.write("\n    </a>\n ");
/* 3324 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f10.doAfterBody();
/* 3325 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3329 */                     if (_jspx_th_c_005fwhen_005f10.doEndTag() == 5) {
/* 3330 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10); return;
/*      */                     }
/*      */                     
/* 3333 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f10);
/* 3334 */                     out.write(10);
/* 3335 */                     out.write(32);
/*      */                     
/* 3337 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f10 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3338 */                     _jspx_th_c_005fotherwise_005f10.setPageContext(_jspx_page_context);
/* 3339 */                     _jspx_th_c_005fotherwise_005f10.setParent(_jspx_th_c_005fchoose_005f10);
/* 3340 */                     int _jspx_eval_c_005fotherwise_005f10 = _jspx_th_c_005fotherwise_005f10.doStartTag();
/* 3341 */                     if (_jspx_eval_c_005fotherwise_005f10 != 0) {
/*      */                       for (;;) {
/* 3343 */                         out.write("\n         ");
/* 3344 */                         out.print(FormatUtil.getString("am.webclient.mailsettings.configuresmsserver.text"));
/* 3345 */                         out.write(10);
/* 3346 */                         out.write(32);
/* 3347 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f10.doAfterBody();
/* 3348 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3352 */                     if (_jspx_th_c_005fotherwise_005f10.doEndTag() == 5) {
/* 3353 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10); return;
/*      */                     }
/*      */                     
/* 3356 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f10);
/* 3357 */                     out.write(10);
/* 3358 */                     out.write(32);
/* 3359 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f10.doAfterBody();
/* 3360 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3364 */                 if (_jspx_th_c_005fchoose_005f10.doEndTag() == 5) {
/* 3365 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10); return;
/*      */                 }
/*      */                 
/* 3368 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f10);
/* 3369 */                 out.write("\n    </td>\n</tr>\n");
/*      */               }
/* 3371 */               out.write("\n\n\n ");
/*      */               
/* 3373 */               if (!com.adventnet.appmanager.util.Constants.sqlManager)
/*      */               {
/*      */ 
/* 3376 */                 out.write("  \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */                 
/* 3378 */                 ChooseTag _jspx_th_c_005fchoose_005f11 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3379 */                 _jspx_th_c_005fchoose_005f11.setPageContext(_jspx_page_context);
/* 3380 */                 _jspx_th_c_005fchoose_005f11.setParent(_jspx_th_tiles_005fput_005f2);
/* 3381 */                 int _jspx_eval_c_005fchoose_005f11 = _jspx_th_c_005fchoose_005f11.doStartTag();
/* 3382 */                 if (_jspx_eval_c_005fchoose_005f11 != 0) {
/*      */                   for (;;) {
/* 3384 */                     out.write(10);
/*      */                     
/* 3386 */                     WhenTag _jspx_th_c_005fwhen_005f11 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3387 */                     _jspx_th_c_005fwhen_005f11.setPageContext(_jspx_page_context);
/* 3388 */                     _jspx_th_c_005fwhen_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/*      */                     
/* 3390 */                     _jspx_th_c_005fwhen_005f11.setTest("${uri !='/jsp/ProxyConfiguration.jsp'}");
/* 3391 */                     int _jspx_eval_c_005fwhen_005f11 = _jspx_th_c_005fwhen_005f11.doStartTag();
/* 3392 */                     if (_jspx_eval_c_005fwhen_005f11 != 0) {
/*      */                       for (;;) {
/* 3394 */                         out.write("    \n    <a href=\"/jsp/ProxyConfiguration.jsp\" class=\"new-left-links\">\n    ");
/* 3395 */                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3396 */                         out.write("\n    </a>\n ");
/* 3397 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f11.doAfterBody();
/* 3398 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3402 */                     if (_jspx_th_c_005fwhen_005f11.doEndTag() == 5) {
/* 3403 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11); return;
/*      */                     }
/*      */                     
/* 3406 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f11);
/* 3407 */                     out.write(10);
/* 3408 */                     out.write(32);
/*      */                     
/* 3410 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f11 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3411 */                     _jspx_th_c_005fotherwise_005f11.setPageContext(_jspx_page_context);
/* 3412 */                     _jspx_th_c_005fotherwise_005f11.setParent(_jspx_th_c_005fchoose_005f11);
/* 3413 */                     int _jspx_eval_c_005fotherwise_005f11 = _jspx_th_c_005fotherwise_005f11.doStartTag();
/* 3414 */                     if (_jspx_eval_c_005fotherwise_005f11 != 0) {
/*      */                       for (;;) {
/* 3416 */                         out.write(10);
/* 3417 */                         out.write(9);
/* 3418 */                         out.print(FormatUtil.getString("am.webclient.admin.configproxy.link"));
/* 3419 */                         out.write(10);
/* 3420 */                         out.write(32);
/* 3421 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f11.doAfterBody();
/* 3422 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3426 */                     if (_jspx_th_c_005fotherwise_005f11.doEndTag() == 5) {
/* 3427 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11); return;
/*      */                     }
/*      */                     
/* 3430 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f11);
/* 3431 */                     out.write(10);
/* 3432 */                     out.write(32);
/* 3433 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f11.doAfterBody();
/* 3434 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3438 */                 if (_jspx_th_c_005fchoose_005f11.doEndTag() == 5) {
/* 3439 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11); return;
/*      */                 }
/*      */                 
/* 3442 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f11);
/* 3443 */                 out.write("\n    </td>\n</tr>\n<tr>\n    \n    <td class=\"leftlinkstd\" >\n");
/*      */                 
/* 3445 */                 ChooseTag _jspx_th_c_005fchoose_005f12 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3446 */                 _jspx_th_c_005fchoose_005f12.setPageContext(_jspx_page_context);
/* 3447 */                 _jspx_th_c_005fchoose_005f12.setParent(_jspx_th_tiles_005fput_005f2);
/* 3448 */                 int _jspx_eval_c_005fchoose_005f12 = _jspx_th_c_005fchoose_005f12.doStartTag();
/* 3449 */                 if (_jspx_eval_c_005fchoose_005f12 != 0) {
/*      */                   for (;;) {
/* 3451 */                     out.write(10);
/*      */                     
/* 3453 */                     WhenTag _jspx_th_c_005fwhen_005f12 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3454 */                     _jspx_th_c_005fwhen_005f12.setPageContext(_jspx_page_context);
/* 3455 */                     _jspx_th_c_005fwhen_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/*      */                     
/* 3457 */                     _jspx_th_c_005fwhen_005f12.setTest("${uri !='/Upload.do'}");
/* 3458 */                     int _jspx_eval_c_005fwhen_005f12 = _jspx_th_c_005fwhen_005f12.doStartTag();
/* 3459 */                     if (_jspx_eval_c_005fwhen_005f12 != 0) {
/*      */                       for (;;) {
/* 3461 */                         out.write("   \n        ");
/*      */                         
/* 3463 */                         AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3464 */                         _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3465 */                         _jspx_th_am_005fadminlink_005f0.setParent(_jspx_th_c_005fwhen_005f12);
/*      */                         
/* 3467 */                         _jspx_th_am_005fadminlink_005f0.setHref("/Upload.do");
/*      */                         
/* 3469 */                         _jspx_th_am_005fadminlink_005f0.setEnableClass("new-left-links");
/* 3470 */                         int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3471 */                         if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3472 */                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3473 */                             out = _jspx_page_context.pushBody();
/* 3474 */                             _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3475 */                             _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3478 */                             out.write("\n           ");
/* 3479 */                             out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3480 */                             out.write("\n            ");
/* 3481 */                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3482 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3485 */                           if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3486 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3489 */                         if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3490 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                         }
/*      */                         
/* 3493 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3494 */                         out.write(10);
/* 3495 */                         out.write(10);
/* 3496 */                         out.write(32);
/* 3497 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f12.doAfterBody();
/* 3498 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3502 */                     if (_jspx_th_c_005fwhen_005f12.doEndTag() == 5) {
/* 3503 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12); return;
/*      */                     }
/*      */                     
/* 3506 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f12);
/* 3507 */                     out.write(10);
/* 3508 */                     out.write(32);
/*      */                     
/* 3510 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f12 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3511 */                     _jspx_th_c_005fotherwise_005f12.setPageContext(_jspx_page_context);
/* 3512 */                     _jspx_th_c_005fotherwise_005f12.setParent(_jspx_th_c_005fchoose_005f12);
/* 3513 */                     int _jspx_eval_c_005fotherwise_005f12 = _jspx_th_c_005fotherwise_005f12.doStartTag();
/* 3514 */                     if (_jspx_eval_c_005fotherwise_005f12 != 0) {
/*      */                       for (;;) {
/* 3516 */                         out.write(10);
/* 3517 */                         out.write(9);
/* 3518 */                         out.print(FormatUtil.getString("am.webclient.admin.upload.link"));
/* 3519 */                         out.write(10);
/* 3520 */                         out.write(32);
/* 3521 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f12.doAfterBody();
/* 3522 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3526 */                     if (_jspx_th_c_005fotherwise_005f12.doEndTag() == 5) {
/* 3527 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12); return;
/*      */                     }
/*      */                     
/* 3530 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f12);
/* 3531 */                     out.write(10);
/* 3532 */                     out.write(32);
/* 3533 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f12.doAfterBody();
/* 3534 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3538 */                 if (_jspx_th_c_005fchoose_005f12.doEndTag() == 5) {
/* 3539 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12); return;
/*      */                 }
/*      */                 
/* 3542 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f12);
/* 3543 */                 out.write("\n    </td>\n</tr>\n \n ");
/*      */               }
/*      */               
/*      */ 
/* 3547 */               out.write("  \n \n<tr>\n    \n    <td class=\"leftlinkstd\">\n");
/*      */               
/* 3549 */               ChooseTag _jspx_th_c_005fchoose_005f13 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3550 */               _jspx_th_c_005fchoose_005f13.setPageContext(_jspx_page_context);
/* 3551 */               _jspx_th_c_005fchoose_005f13.setParent(_jspx_th_tiles_005fput_005f2);
/* 3552 */               int _jspx_eval_c_005fchoose_005f13 = _jspx_th_c_005fchoose_005f13.doStartTag();
/* 3553 */               if (_jspx_eval_c_005fchoose_005f13 != 0) {
/*      */                 for (;;) {
/* 3555 */                   out.write(10);
/*      */                   
/* 3557 */                   WhenTag _jspx_th_c_005fwhen_005f13 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3558 */                   _jspx_th_c_005fwhen_005f13.setPageContext(_jspx_page_context);
/* 3559 */                   _jspx_th_c_005fwhen_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/*      */                   
/* 3561 */                   _jspx_th_c_005fwhen_005f13.setTest("${uri !='/admin/userconfiguration.do'}");
/* 3562 */                   int _jspx_eval_c_005fwhen_005f13 = _jspx_th_c_005fwhen_005f13.doStartTag();
/* 3563 */                   if (_jspx_eval_c_005fwhen_005f13 != 0) {
/*      */                     for (;;) {
/* 3565 */                       out.write("\n    \n        ");
/*      */                       
/* 3567 */                       AdminLink _jspx_th_am_005fadminlink_005f1 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3568 */                       _jspx_th_am_005fadminlink_005f1.setPageContext(_jspx_page_context);
/* 3569 */                       _jspx_th_am_005fadminlink_005f1.setParent(_jspx_th_c_005fwhen_005f13);
/*      */                       
/* 3571 */                       _jspx_th_am_005fadminlink_005f1.setHref("/admin/userconfiguration.do?method=showUsers");
/*      */                       
/* 3573 */                       _jspx_th_am_005fadminlink_005f1.setEnableClass("new-left-links");
/* 3574 */                       int _jspx_eval_am_005fadminlink_005f1 = _jspx_th_am_005fadminlink_005f1.doStartTag();
/* 3575 */                       if (_jspx_eval_am_005fadminlink_005f1 != 0) {
/* 3576 */                         if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3577 */                           out = _jspx_page_context.pushBody();
/* 3578 */                           _jspx_th_am_005fadminlink_005f1.setBodyContent((BodyContent)out);
/* 3579 */                           _jspx_th_am_005fadminlink_005f1.doInitBody();
/*      */                         }
/*      */                         for (;;) {
/* 3582 */                           out.write("\n       ");
/* 3583 */                           out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3584 */                           out.write("\n        ");
/* 3585 */                           int evalDoAfterBody = _jspx_th_am_005fadminlink_005f1.doAfterBody();
/* 3586 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/* 3589 */                         if (_jspx_eval_am_005fadminlink_005f1 != 1) {
/* 3590 */                           out = _jspx_page_context.popBody();
/*      */                         }
/*      */                       }
/* 3593 */                       if (_jspx_th_am_005fadminlink_005f1.doEndTag() == 5) {
/* 3594 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1); return;
/*      */                       }
/*      */                       
/* 3597 */                       this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f1);
/* 3598 */                       out.write(10);
/* 3599 */                       out.write(10);
/* 3600 */                       out.write(32);
/* 3601 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f13.doAfterBody();
/* 3602 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3606 */                   if (_jspx_th_c_005fwhen_005f13.doEndTag() == 5) {
/* 3607 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13); return;
/*      */                   }
/*      */                   
/* 3610 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f13);
/* 3611 */                   out.write(10);
/* 3612 */                   out.write(32);
/*      */                   
/* 3614 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f13 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3615 */                   _jspx_th_c_005fotherwise_005f13.setPageContext(_jspx_page_context);
/* 3616 */                   _jspx_th_c_005fotherwise_005f13.setParent(_jspx_th_c_005fchoose_005f13);
/* 3617 */                   int _jspx_eval_c_005fotherwise_005f13 = _jspx_th_c_005fotherwise_005f13.doStartTag();
/* 3618 */                   if (_jspx_eval_c_005fotherwise_005f13 != 0) {
/*      */                     for (;;) {
/* 3620 */                       out.write(10);
/* 3621 */                       out.write(9);
/* 3622 */                       out.write(32);
/* 3623 */                       out.print(FormatUtil.getString("am.webclient.admin.useradmin.link"));
/* 3624 */                       out.write(10);
/* 3625 */                       out.write(32);
/* 3626 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f13.doAfterBody();
/* 3627 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3631 */                   if (_jspx_th_c_005fotherwise_005f13.doEndTag() == 5) {
/* 3632 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13); return;
/*      */                   }
/*      */                   
/* 3635 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f13);
/* 3636 */                   out.write(10);
/* 3637 */                   out.write(32);
/* 3638 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f13.doAfterBody();
/* 3639 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3643 */               if (_jspx_th_c_005fchoose_005f13.doEndTag() == 5) {
/* 3644 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13); return;
/*      */               }
/*      */               
/* 3647 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f13);
/* 3648 */               out.write("\n    </td>\n</tr>\n   \n\n ");
/* 3649 */               if (!com.adventnet.appmanager.util.OEMUtil.isOEM()) {
/* 3650 */                 out.write("\n\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */                 
/* 3652 */                 ChooseTag _jspx_th_c_005fchoose_005f14 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3653 */                 _jspx_th_c_005fchoose_005f14.setPageContext(_jspx_page_context);
/* 3654 */                 _jspx_th_c_005fchoose_005f14.setParent(_jspx_th_tiles_005fput_005f2);
/* 3655 */                 int _jspx_eval_c_005fchoose_005f14 = _jspx_th_c_005fchoose_005f14.doStartTag();
/* 3656 */                 if (_jspx_eval_c_005fchoose_005f14 != 0) {
/*      */                   for (;;) {
/* 3658 */                     out.write("\n   ");
/*      */                     
/* 3660 */                     WhenTag _jspx_th_c_005fwhen_005f14 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3661 */                     _jspx_th_c_005fwhen_005f14.setPageContext(_jspx_page_context);
/* 3662 */                     _jspx_th_c_005fwhen_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/*      */                     
/* 3664 */                     _jspx_th_c_005fwhen_005f14.setTest("${param.method!='showExtDeviceConfigurations'}");
/* 3665 */                     int _jspx_eval_c_005fwhen_005f14 = _jspx_th_c_005fwhen_005f14.doStartTag();
/* 3666 */                     if (_jspx_eval_c_005fwhen_005f14 != 0) {
/*      */                       for (;;) {
/* 3668 */                         out.write("\n    ");
/*      */                         
/* 3670 */                         AdminLink _jspx_th_am_005fadminlink_005f2 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3671 */                         _jspx_th_am_005fadminlink_005f2.setPageContext(_jspx_page_context);
/* 3672 */                         _jspx_th_am_005fadminlink_005f2.setParent(_jspx_th_c_005fwhen_005f14);
/*      */                         
/* 3674 */                         _jspx_th_am_005fadminlink_005f2.setHref("/extDeviceAction.do?method=showExtDeviceConfigurations");
/*      */                         
/* 3676 */                         _jspx_th_am_005fadminlink_005f2.setEnableClass("new-left-links");
/* 3677 */                         int _jspx_eval_am_005fadminlink_005f2 = _jspx_th_am_005fadminlink_005f2.doStartTag();
/* 3678 */                         if (_jspx_eval_am_005fadminlink_005f2 != 0) {
/* 3679 */                           if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3680 */                             out = _jspx_page_context.pushBody();
/* 3681 */                             _jspx_th_am_005fadminlink_005f2.setBodyContent((BodyContent)out);
/* 3682 */                             _jspx_th_am_005fadminlink_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 3685 */                             out.write(10);
/* 3686 */                             out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3687 */                             out.write("\n    ");
/* 3688 */                             int evalDoAfterBody = _jspx_th_am_005fadminlink_005f2.doAfterBody();
/* 3689 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3692 */                           if (_jspx_eval_am_005fadminlink_005f2 != 1) {
/* 3693 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3696 */                         if (_jspx_th_am_005fadminlink_005f2.doEndTag() == 5) {
/* 3697 */                           this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2); return;
/*      */                         }
/*      */                         
/* 3700 */                         this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f2);
/* 3701 */                         out.write("\n   ");
/* 3702 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f14.doAfterBody();
/* 3703 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3707 */                     if (_jspx_th_c_005fwhen_005f14.doEndTag() == 5) {
/* 3708 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14); return;
/*      */                     }
/*      */                     
/* 3711 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f14);
/* 3712 */                     out.write("\n   ");
/*      */                     
/* 3714 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f14 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3715 */                     _jspx_th_c_005fotherwise_005f14.setPageContext(_jspx_page_context);
/* 3716 */                     _jspx_th_c_005fotherwise_005f14.setParent(_jspx_th_c_005fchoose_005f14);
/* 3717 */                     int _jspx_eval_c_005fotherwise_005f14 = _jspx_th_c_005fotherwise_005f14.doStartTag();
/* 3718 */                     if (_jspx_eval_c_005fotherwise_005f14 != 0) {
/*      */                       for (;;) {
/* 3720 */                         out.write(10);
/* 3721 */                         out.print(FormatUtil.getString("am.webclient.admin.addon.link"));
/* 3722 */                         out.write("\n   ");
/* 3723 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f14.doAfterBody();
/* 3724 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3728 */                     if (_jspx_th_c_005fotherwise_005f14.doEndTag() == 5) {
/* 3729 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14); return;
/*      */                     }
/*      */                     
/* 3732 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f14);
/* 3733 */                     out.write(10);
/* 3734 */                     out.write(32);
/* 3735 */                     out.write(32);
/* 3736 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f14.doAfterBody();
/* 3737 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3741 */                 if (_jspx_th_c_005fchoose_005f14.doEndTag() == 5) {
/* 3742 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14); return;
/*      */                 }
/*      */                 
/* 3745 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f14);
/* 3746 */                 out.write("\n </td>\n</tr>\n  ");
/*      */               }
/* 3748 */               out.write("\n<tr>\n <td class=\"leftlinkstd\" >\n  ");
/*      */               
/* 3750 */               ChooseTag _jspx_th_c_005fchoose_005f15 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3751 */               _jspx_th_c_005fchoose_005f15.setPageContext(_jspx_page_context);
/* 3752 */               _jspx_th_c_005fchoose_005f15.setParent(_jspx_th_tiles_005fput_005f2);
/* 3753 */               int _jspx_eval_c_005fchoose_005f15 = _jspx_th_c_005fchoose_005f15.doStartTag();
/* 3754 */               if (_jspx_eval_c_005fchoose_005f15 != 0) {
/*      */                 for (;;) {
/* 3756 */                   out.write("\n   ");
/*      */                   
/* 3758 */                   WhenTag _jspx_th_c_005fwhen_005f15 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3759 */                   _jspx_th_c_005fwhen_005f15.setPageContext(_jspx_page_context);
/* 3760 */                   _jspx_th_c_005fwhen_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/*      */                   
/* 3762 */                   _jspx_th_c_005fwhen_005f15.setTest("${param.method!='showDataCleanUp'}");
/* 3763 */                   int _jspx_eval_c_005fwhen_005f15 = _jspx_th_c_005fwhen_005f15.doStartTag();
/* 3764 */                   if (_jspx_eval_c_005fwhen_005f15 != 0) {
/*      */                     for (;;) {
/* 3766 */                       out.write("\n    <a href=\"/adminAction.do?method=showDataCleanUp\" class=\"new-left-links\">\n");
/* 3767 */                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3768 */                       out.write("\n    </a>\n   ");
/* 3769 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f15.doAfterBody();
/* 3770 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3774 */                   if (_jspx_th_c_005fwhen_005f15.doEndTag() == 5) {
/* 3775 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15); return;
/*      */                   }
/*      */                   
/* 3778 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f15);
/* 3779 */                   out.write("\n   ");
/*      */                   
/* 3781 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f15 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3782 */                   _jspx_th_c_005fotherwise_005f15.setPageContext(_jspx_page_context);
/* 3783 */                   _jspx_th_c_005fotherwise_005f15.setParent(_jspx_th_c_005fchoose_005f15);
/* 3784 */                   int _jspx_eval_c_005fotherwise_005f15 = _jspx_th_c_005fotherwise_005f15.doStartTag();
/* 3785 */                   if (_jspx_eval_c_005fotherwise_005f15 != 0) {
/*      */                     for (;;) {
/* 3787 */                       out.write(10);
/* 3788 */                       out.print(FormatUtil.getString("am.webclient.admintab.reportssetting.text"));
/* 3789 */                       out.write("\n   ");
/* 3790 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f15.doAfterBody();
/* 3791 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3795 */                   if (_jspx_th_c_005fotherwise_005f15.doEndTag() == 5) {
/* 3796 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15); return;
/*      */                   }
/*      */                   
/* 3799 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f15);
/* 3800 */                   out.write(10);
/* 3801 */                   out.write(32);
/* 3802 */                   out.write(32);
/* 3803 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f15.doAfterBody();
/* 3804 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3808 */               if (_jspx_th_c_005fchoose_005f15.doEndTag() == 5) {
/* 3809 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15); return;
/*      */               }
/*      */               
/* 3812 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f15);
/* 3813 */               out.write("\n </td>\n</tr>\n\n</table>\n\n");
/* 3814 */               out.write("\n \n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr> \n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 3815 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 3816 */               out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 3817 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 3819 */               out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n  </tr>\n  <tr> \n    <td colspan=\"2\" class=\"quicknote\"> ");
/* 3820 */               out.print(FormatUtil.getString("am.webclient.gsformanager.quicknote.text"));
/* 3821 */               out.write(".</td>\n  </tr>\n</table>\n");
/* 3822 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3823 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3826 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3827 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3830 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3831 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 3834 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3835 */           out.write(32);
/*      */           
/* 3837 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 3838 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3839 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 3841 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 3843 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 3844 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3845 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 3846 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3847 */               out = _jspx_page_context.pushBody();
/* 3848 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 3849 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 3852 */               out.write(" \n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t  <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 3853 */               out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 3854 */               out.write(" &gt;<span class=\"bcactive\">");
/* 3855 */               out.print(FormatUtil.getString("am.webclient.gsformanager.text"));
/* 3856 */               out.write("</span></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"/images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n</table>\n\n<table width=\"98%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n          <tr> \n            <td width=\"2%\" class=\"tableheading\" >&nbsp;</td>\n            \n    <td align=\"left\"  class=\"tableheading\">");
/* 3857 */               out.print(FormatUtil.getString("am.webclient.gsformanager.heading.text"));
/* 3858 */               out.write("</td>\n          </tr>\n          <tr> \n            <td width=\"2%\" >&nbsp;</td>\n            <td height=\"20\">\n            \t<span class=\"bodytext\">");
/* 3859 */               out.print(FormatUtil.getString("am.webclient.gsformanager.steps.text"));
/* 3860 */               out.write(".</span>\n            \t\n             </td>\n          </tr>\n          <tr> \n            <td width=\"2%\" >&nbsp;</td>\n            <td height=\"20\">\n            \t<span class=\"bodytextbold\">");
/* 3861 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.step1"));
/* 3862 */               out.write(" </span><span class=\"bodytext\"><a href='/showTile.do?TileName=Tile.usergroups.Conf&haid=null' ><span class=\"staticlinks\">");
/* 3863 */               out.print(FormatUtil.getString("am.webclient.gsformanager.createnewuser.text"));
/* 3864 */               out.write("\n            \t\n             </td>\n          </tr>\n          <tr> \n            <td width=\"2%\" >&nbsp;</td>\n            <td height=\"20\">\n            \t<span class=\"bodytextbold\">");
/* 3865 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.step2"));
/* 3866 */               out.write("  </span><span class=\"bodytext\">");
/* 3867 */               out.print(FormatUtil.getString("am.webclient.gsformanager.step2.text"));
/* 3868 */               out.write("</span>\n            \t\n             </td>\n          </tr>\n\t  <tr> \n            <td width=\"2%\" >&nbsp;</td>\n            <td height=\"20\">\n            \t<span class=\"bodytextbold\">");
/* 3869 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.step3"));
/* 3870 */               out.write(" </span><span class=\"bodytext\">");
/* 3871 */               out.print(FormatUtil.getString("am.webclient.gsformanager.createSLA.text"));
/* 3872 */               out.write(".</span>\n            \t\n             </td>\n          </tr>\n\n\t \n\n\t  <tr> \n            <td width=\"2%\" >&nbsp;</td>\n            <td height=\"20\">\n            \t<span class=\"bodytextbold\">");
/* 3873 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.step4"));
/* 3874 */               out.write("  </span><span class=\"bodytext\">");
/* 3875 */               out.print(FormatUtil.getString("am.webclient.gsformanager.associatesla.text"));
/* 3876 */               out.write(" .</span>\n            \t<a href=\"/help/manager-console.html\" target=\"_blank\" class=\"selectedmenu\"><img src=\"/images/icon_quicknote_help.gif\" hspace=\"5\" vspace=\"5\" border=\"0\" align=\"absmiddle\">");
/* 3877 */               out.print(FormatUtil.getString("am.webclient.monitorgroupfirst.morehelp"));
/* 3878 */               out.write("</a>              \n             </td>\n          </tr>\n           \t  \n\n          <tr>\n            <td  colspan=\"2\" height=\"16\" class=\"bodytext\"><img src=\"/images/spacer.gif\" width=\"5\" height=\"10\"></td>\n          </tr>\n \n    \n  \n</table>\n\n\n\n");
/* 3879 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3880 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3883 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3884 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3887 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3888 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 3891 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3892 */           out.write(32);
/* 3893 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3895 */           out.write(32);
/* 3896 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3897 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3901 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3902 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3905 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3906 */         out.write(" \n\n\n");
/*      */       }
/* 3908 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3909 */         out = _jspx_out;
/* 3910 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3911 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3912 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3915 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3921 */     PageContext pageContext = _jspx_page_context;
/* 3922 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3924 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3925 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3926 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3928 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3930 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 3931 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3932 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3933 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3934 */       return true;
/*      */     }
/* 3936 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3937 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3942 */     PageContext pageContext = _jspx_page_context;
/* 3943 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3945 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3946 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3947 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3949 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3951 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3952 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3953 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3954 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3955 */       return true;
/*      */     }
/* 3957 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3963 */     PageContext pageContext = _jspx_page_context;
/* 3964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3966 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3967 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3968 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3970 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3972 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3973 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3974 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3975 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3976 */       return true;
/*      */     }
/* 3978 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3979 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\GettingStartedForManager_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */