/*      */ package org.apache.jsp.jsp.as400;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
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
/*      */ public final class subsystem_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  669 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
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
/*  815 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  816 */     getRCATrimmedText(div1, rca);
/*  817 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  820 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  821 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  841 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/*  974 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 2185 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2186 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2208 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2212 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2227 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2231 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2234 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2240 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2251 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2254 */     JspWriter out = null;
/* 2255 */     Object page = this;
/* 2256 */     JspWriter _jspx_out = null;
/* 2257 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2261 */       response.setContentType("text/html;charset=UTF-8");
/* 2262 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2264 */       _jspx_page_context = pageContext;
/* 2265 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2266 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2267 */       session = pageContext.getSession();
/* 2268 */       out = pageContext.getOut();
/* 2269 */       _jspx_out = out;
/*      */       
/* 2271 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2272 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2274 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2285 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2286 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2287 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2290 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2291 */         String available = null;
/* 2292 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2293 */         out.write(10);
/*      */         
/* 2295 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2306 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2307 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2308 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2311 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2312 */           String unavailable = null;
/* 2313 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2314 */           out.write(10);
/*      */           
/* 2316 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2327 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2328 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2329 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2332 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2333 */             String unmanaged = null;
/* 2334 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2335 */             out.write(10);
/*      */             
/* 2337 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2348 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2349 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2353 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2354 */               String scheduled = null;
/* 2355 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2356 */               out.write(10);
/*      */               
/* 2358 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2369 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2370 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2371 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2374 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2375 */                 String critical = null;
/* 2376 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2377 */                 out.write(10);
/*      */                 
/* 2379 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2390 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2391 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2392 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2395 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2396 */                   String clear = null;
/* 2397 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2398 */                   out.write(10);
/*      */                   
/* 2400 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2411 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2412 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2413 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2416 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2417 */                     String warning = null;
/* 2418 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2419 */                     out.write(10);
/* 2420 */                     out.write(10);
/*      */                     
/* 2422 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2423 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/* 2427 */                     out.write(10);
/* 2428 */                     out.write("\n\n<script>\n    checkBoxListener();\n</script>\n\n");
/*      */                     
/* 2430 */                     String bgcolor = "";
/* 2431 */                     int tc = 0;
/*      */                     
/* 2433 */                     String resourceid = request.getParameter("resourceid");
/* 2434 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=10");
/* 2435 */                     String SUBSYSTEM_CLEAR = (String)request.getAttribute("SUBSYSTEM_CLEAR");
/* 2436 */                     String SUBSYSTEM_CRITICAL = (String)request.getAttribute("SUBSYSTEM_CRITICAL");
/*      */                     
/*      */ 
/* 2439 */                     ArrayList resIDs = (ArrayList)request.getAttribute("buffdata");
/* 2440 */                     resIDs.add(resourceid);
/*      */                     
/* 2442 */                     ArrayList<String> attribIDs = new ArrayList();
/* 2443 */                     for (int i = 2703; i < 2706; i++)
/*      */                     {
/* 2445 */                       attribIDs.add("" + i);
/*      */                     }
/* 2447 */                     attribIDs.add("2795");
/* 2448 */                     attribIDs.add("2796");
/* 2449 */                     for (int i = 2825; i <= 2828; i++)
/*      */                     {
/* 2451 */                       attribIDs.add("" + i);
/*      */                     }
/* 2453 */                     attribIDs.add("2833");
/* 2454 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2456 */                     Hashtable globals = (Hashtable)application.getAttribute("globalconfig");
/* 2457 */                     String allowSub = (String)globals.get("allowSUB");
/* 2458 */                     boolean allowSUB = false;
/* 2459 */                     String allowAs400 = (String)globals.get("allowAS400");
/* 2460 */                     boolean allowAS400 = false;
/*      */                     
/* 2462 */                     out.write(10);
/*      */                     
/* 2464 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2465 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2466 */                     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                     
/* 2468 */                     _jspx_th_logic_005fpresent_005f0.setRole("OPERATOR");
/* 2469 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2470 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2472 */                         out.write("\n    ");
/*      */                         
/* 2474 */                         if (allowSub.equals("true"))
/*      */                         {
/* 2476 */                           allowSUB = true;
/*      */                         }
/*      */                         
/* 2479 */                         out.write(10);
/* 2480 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2481 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2485 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2486 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                     }
/*      */                     else {
/* 2489 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2490 */                       out.write(10);
/*      */                       
/* 2492 */                       PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2493 */                       _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2494 */                       _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                       
/* 2496 */                       _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2497 */                       int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2498 */                       if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2500 */                           out.write("\n    ");
/*      */                           
/* 2502 */                           if ("true".equals(allowAs400))
/*      */                           {
/* 2504 */                             allowAS400 = true;
/*      */                           }
/*      */                           
/* 2507 */                           out.write(10);
/* 2508 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2509 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2513 */                       if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2514 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                       }
/*      */                       else {
/* 2517 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2518 */                         out.write("\n<br>\n<div style=\"display:none;\" id=\"showoptionsplus\" >\n    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"apmDblClickMenu\" onClick=\"closeDialog();\">\n        ");
/*      */                         
/* 2520 */                         PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2521 */                         _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2522 */                         _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                         
/* 2524 */                         _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,OPERATOR");
/* 2525 */                         int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2526 */                         if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                           for (;;) {
/* 2528 */                             out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2529 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2531 */                             out.write("'target=_blank>");
/* 2532 */                             if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2534 */                             out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2535 */                             if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2537 */                             out.write("'target=_blank>");
/* 2538 */                             if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2540 */                             out.write("</a></td></tr>\n            ");
/* 2541 */                             if (_jspx_meth_logic_005fpresent_005f3(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                               return;
/* 2543 */                             out.write("\n            ");
/* 2544 */                             if ((allowAS400) || (allowSUB)) {
/* 2545 */                               out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#armonitor\").val(\"Delete\").change();'>");
/* 2546 */                               if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2548 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#armonitor\").val(\"End\").change();'>");
/* 2549 */                               if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2551 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#armonitor\").val(\"Start\").change();'>");
/* 2552 */                               if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2554 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#armonitor\").val(\"Refresh\").change();'>");
/* 2555 */                               if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_logic_005fpresent_005f2, _jspx_page_context))
/*      */                                 return;
/* 2557 */                               out.write("</a></td></tr>\n            ");
/*      */                             }
/* 2559 */                             out.write("\n        ");
/* 2560 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2561 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2565 */                         if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2566 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/*      */                         }
/*      */                         else {
/* 2569 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2570 */                           out.write("\n    </table>\n</div>\n\n<div style=\"display:none;\" id=\"showoptions\" >\n    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"apmDblClickMenu\" onClick=\"closeDialog();\">\n        ");
/*      */                           
/* 2572 */                           PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2573 */                           _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 2574 */                           _jspx_th_logic_005fpresent_005f4.setParent(null);
/*      */                           
/* 2576 */                           _jspx_th_logic_005fpresent_005f4.setRole("ADMIN,OPERATOR");
/* 2577 */                           int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 2578 */                           if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */                             for (;;) {
/* 2580 */                               out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2581 */                               if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                 return;
/* 2583 */                               out.write("'target=_blank>");
/* 2584 */                               if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                 return;
/* 2586 */                               out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2587 */                               if (_jspx_meth_c_005fout_005f3(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                 return;
/* 2589 */                               out.write("'target=_blank>");
/* 2590 */                               if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                 return;
/* 2592 */                               out.write("</a></td></tr>\n            ");
/*      */                               
/* 2594 */                               PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2595 */                               _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 2596 */                               _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_logic_005fpresent_005f4);
/*      */                               
/* 2598 */                               _jspx_th_logic_005fpresent_005f5.setRole("ADMIN");
/* 2599 */                               int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 2600 */                               if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                 for (;;) {
/* 2602 */                                   out.write("\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#configuresub\").click();'>");
/* 2603 */                                   out.print(ALERTCONFIG_TEXT);
/* 2604 */                                   out.write("</a></td></tr>\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onclick='$(\"#enabledisable\").click();'>");
/* 2605 */                                   if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_logic_005fpresent_005f5, _jspx_page_context))
/*      */                                     return;
/* 2607 */                                   out.write("</a></td></tr>\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#addsubmon\").click();'>");
/* 2608 */                                   if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_logic_005fpresent_005f5, _jspx_page_context))
/*      */                                     return;
/* 2610 */                                   out.write("</a></td></tr>\n            ");
/* 2611 */                                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 2612 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2616 */                               if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 2617 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                               }
/*      */                               
/* 2620 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 2621 */                               out.write("\n            ");
/* 2622 */                               if ((allowAS400) || (allowSUB)) {
/* 2623 */                                 out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"Delete\").change();'>");
/* 2624 */                                 if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                   return;
/* 2626 */                                 out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"End\").change();'>");
/* 2627 */                                 if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                   return;
/* 2629 */                                 out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"Start\").change();'>");
/* 2630 */                                 if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                   return;
/* 2632 */                                 out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#monitor\").val(\"Refresh\").change();'>");
/* 2633 */                                 if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_logic_005fpresent_005f4, _jspx_page_context))
/*      */                                   return;
/* 2635 */                                 out.write("</a></td></tr>\n            ");
/*      */                               }
/* 2637 */                               out.write("\n        ");
/* 2638 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 2639 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2643 */                           if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 2644 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/*      */                           }
/*      */                           else {
/* 2647 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 2648 */                             out.write("\n    </table>\n</div>\n\n");
/*      */                             
/* 2650 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2651 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2652 */                             _jspx_th_c_005fif_005f0.setParent(null);
/*      */                             
/* 2654 */                             _jspx_th_c_005fif_005f0.setTest("${not disable}");
/* 2655 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2656 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2658 */                                 out.write("\n    <table width=\"100%\" height=\"56\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table-border\" onMouseOver=\"toggledivmo('div1',1)\" onMouseOut=\"toggledivmo('div1',0)\">\n\n        <tr>\n            <td colspan=\"4\" class=\"conf-mon-data-heading bborder\">");
/* 2659 */                                 if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2661 */                                 out.write("</td>\n            <td  colspan=\"1\" align=\"right\" class=\"conf-mon-data-link bborder\">");
/*      */                                 
/* 2663 */                                 PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2664 */                                 _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 2665 */                                 _jspx_th_logic_005fpresent_005f6.setParent(_jspx_th_c_005fif_005f0);
/*      */                                 
/* 2667 */                                 _jspx_th_logic_005fpresent_005f6.setRole("ADMIN,DEMO");
/* 2668 */                                 int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 2669 */                                 if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */                                   for (;;) {
/* 2671 */                                     out.write("<div style=\"visibility: hidden;\" id=\"div1\" > <a href=");
/*      */                                     
/* 2673 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2674 */                                     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2675 */                                     _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_logic_005fpresent_005f6);
/*      */                                     
/* 2677 */                                     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2678 */                                     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2679 */                                     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                       for (;;) {
/* 2681 */                                         out.write("\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2682 */                                         out.print(resourceid);
/* 2683 */                                         out.write("&attributeIDs=2795,2703,2796&attributeToSelect=2795&redirectto=");
/* 2684 */                                         out.print(encodeurl);
/* 2685 */                                         out.write(34);
/* 2686 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2687 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2691 */                                     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2692 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                     }
/*      */                                     
/* 2695 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2696 */                                     if (_jspx_meth_logic_005fpresent_005f7(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
/*      */                                       return;
/* 2698 */                                     out.write(" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" title=\"");
/* 2699 */                                     out.print(ALERTCONFIG_TEXT);
/* 2700 */                                     out.write("\"></a>&nbsp;&nbsp;\n            </div>");
/* 2701 */                                     int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 2702 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2706 */                                 if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 2707 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6); return;
/*      */                                 }
/*      */                                 
/* 2710 */                                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 2711 */                                 out.write("</td>\n        </tr>\n\n        <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n            <td width=37%\">\n            <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                <tr>\n                    <td width=\"35%\" class=\"monitorinfoeven-conf\" align=\"left\" style=\"padding-left:10px;\">");
/* 2712 */                                 out.print(FormatUtil.getString("am.webclient.as400.subsysteminclear"));
/* 2713 */                                 out.write("</td>\n\n                    ");
/* 2714 */                                 if (SUBSYSTEM_CLEAR != null) {
/* 2715 */                                   out.write("\n                    <td width=\"15%\" class=\"tooltip\" onmouseover=\"ddrivetip(this,event,'");
/* 2716 */                                   if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                     return;
/* 2718 */                                   out.write(32);
/* 2719 */                                   if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                     return;
/* 2721 */                                   out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" align=\"left\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=subsystemFilter&resourceid=");
/* 2722 */                                   if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                     return;
/* 2724 */                                   out.write("&status=clear',1050,600,0,0); return false;\" class=\"new-monitordiv-link\" href=\"#\" >");
/* 2725 */                                   out.print(FormatUtil.formatNumber(SUBSYSTEM_CLEAR));
/* 2726 */                                   out.write("</a></td>\n                    ");
/*      */                                 } else {
/* 2728 */                                   out.write("\n                    <td width=\"15%\" class=\"monitorinfoeven-conf\" align=\"left\">-</td>\n                    ");
/*      */                                 }
/* 2730 */                                 out.write("\n\n                    <td  width=\"12%\" class=\"monitorinfoeven-conf\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2731 */                                 out.print(resourceid);
/* 2732 */                                 out.write("&attributeid=2795')\">");
/* 2733 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2795")));
/* 2734 */                                 out.write("</a></td>\n\n                    <td width=\"5%\" class=\"monitorinfoeven-conf\">&nbsp;</td>\n                </tr>\n            </table>\n            </td>\n            <td class=\"monitorinfoeven-conf\" align=\"left\" width=\"1%\" ><img class=\"alarms-filter-arrow\" src=\"/images/sql-dotted-sep.gif\"></td>\n            <td width=\"37%\" >\n                <table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n                    <tr>\n                        <td width=\"35%\" class=\"monitorinfoeven-conf\" align=\"left\">");
/* 2735 */                                 out.print(FormatUtil.getString("am.webclient.as400.subsystemincritical"));
/* 2736 */                                 out.write("</td>\n\n                        ");
/* 2737 */                                 if (SUBSYSTEM_CRITICAL != null) {
/* 2738 */                                   out.write("\n                        <td width=\"15%\" class=\"monitorinfoeven-conf\" onmouseover=\"ddrivetip(this,event,'");
/* 2739 */                                   if (_jspx_meth_fmt_005fmessage_005f20(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                     return;
/* 2741 */                                   out.write(32);
/* 2742 */                                   if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                     return;
/* 2744 */                                   out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" align=\"left\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=subsystemFilter&resourceid=");
/* 2745 */                                   if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                     return;
/* 2747 */                                   out.write("&status=critical',1050,600,0,0); return false;\" class=\"new-monitordiv-link\" href=\"#\" >");
/* 2748 */                                   out.print(FormatUtil.formatNumber(SUBSYSTEM_CRITICAL));
/* 2749 */                                   out.write("</a></td>\n                        ");
/*      */                                 } else {
/* 2751 */                                   out.write("\n                        <td width=\"15%\" class=\"monitorinfoeven-conf\" align=\"left\">-</td>\n                        ");
/*      */                                 }
/* 2753 */                                 out.write("\n                        <td width=\"12%\" class=\"monitorinfoeven-conf\" align=\"center\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2754 */                                 out.print(resourceid);
/* 2755 */                                 out.write("&attributeid=2796')\">");
/* 2756 */                                 out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2796")));
/* 2757 */                                 out.write("</a>\n\n                        <td width=\"5%\" class=\"monitorinfoeven-conf\">&nbsp;</td>\n                    </tr>\n                </table>\n            </td>\n            <td class=\"monitorinfoeven-conf\" align=\"left\" width=\"1%\" ><img class=\"alarms-filter-arrow\" src=\"/images/sql-dotted-sep.gif\"></td>\n            <td width=\"25%\" class=\"monitorinfoeven-conf\" align=\"center\">\n                <table align=\"center\" width =\"60%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\">\n                    <tr>\n                        ");
/* 2758 */                                 if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2760 */                                 out.write("\n                        ");
/* 2761 */                                 if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2763 */                                 out.write("\n                    </tr>\n                </table>\n                <table align=\"bottom\"  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n                    <tr>\n                        <td class=\"clearCount\">");
/* 2764 */                                 if (_jspx_meth_fmt_005fmessage_005f24(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2766 */                                 out.write(32);
/* 2767 */                                 out.write(45);
/* 2768 */                                 out.write(32);
/* 2769 */                                 if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2771 */                                 out.write("%</td>\n                        <td class=\"criticalCount\">");
/* 2772 */                                 if (_jspx_meth_fmt_005fmessage_005f25(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2774 */                                 out.write(32);
/* 2775 */                                 out.write(45);
/* 2776 */                                 out.write(32);
/* 2777 */                                 if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f0, _jspx_page_context))
/*      */                                   return;
/* 2779 */                                 out.write("%</td>\n                    </tr>\n                </table>\n            </td>\n\n\n        </tr>\n    </table>\n\n");
/* 2780 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2781 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2785 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2786 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                             }
/*      */                             else {
/* 2789 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2790 */                               out.write("\n<br>\n");
/*      */                               
/* 2792 */                               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2793 */                               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2794 */                               _jspx_th_c_005fif_005f3.setParent(null);
/*      */                               
/* 2796 */                               _jspx_th_c_005fif_005f3.setTest("${not empty subsystemToMon}");
/* 2797 */                               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2798 */                               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                 for (;;) {
/* 2800 */                                   out.write("\n    <form name=\"formRemove\" id=\"formRemove\" action=\"/as400.do?method=subsystemActions\" method=\"post\">\n\n        <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table-border\" onMouseOver=\"$('#div2').css('opacity',1);\" onMouseOut=\"$('#div2').css('opacity',0.5)\">\n            <tr>\n                <td width=\"16%\" class=\"conf-mon-data-heading\" NOWRAP>");
/* 2801 */                                   if (_jspx_meth_fmt_005fmessage_005f26(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2803 */                                   out.write("</td>\n\n                <td class=\"conf-mon-data-link\"  align=\"left\">\n                    ");
/* 2804 */                                   if ((allowAS400) || (allowSUB)) {
/* 2805 */                                     out.write("\n                    <table cellpadding=\"0\" cellspacing=\"0\">\n                        <tr>\n                            <td class=\"bodytextbold\">\n                                <select id=\"armonitor\"  onchange=\"javascript:fnGetCheckAndSubmit(this)\" onmouseover=\"ddrivetip(this,event,'");
/* 2806 */                                     if (_jspx_meth_fmt_005fmessage_005f27(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2808 */                                     out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\">\n                                    <option value=\"Actions\">");
/* 2809 */                                     if (_jspx_meth_fmt_005fmessage_005f28(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2811 */                                     out.write("</option>\n                                    <option value=\"Delete\">");
/* 2812 */                                     if (_jspx_meth_fmt_005fmessage_005f29(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2814 */                                     out.write("</option>\n                                    <option value=\"End\">");
/* 2815 */                                     if (_jspx_meth_fmt_005fmessage_005f30(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2817 */                                     out.write("</option>\n                                    <option value=\"Start\">");
/* 2818 */                                     if (_jspx_meth_fmt_005fmessage_005f31(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2820 */                                     out.write("</option>\n                                    <option value=\"Refresh\">");
/* 2821 */                                     if (_jspx_meth_fmt_005fmessage_005f32(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                       return;
/* 2823 */                                     out.write("</option>\n                                </select>\n                            </td>\n                        </tr>\n                    </table>\n                    ");
/*      */                                   }
/* 2825 */                                   out.write("\n                </td>\n                <td class=\"conf-mon-data-link\"  align=\"right\">");
/* 2826 */                                   if (_jspx_meth_logic_005fpresent_005f8(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2828 */                                   out.write("</td>\n            </tr>\n        </table>\n        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrborder\" id=\"subsystemDetails1\" onMouseOver=\"$('#div2').css('opacity',1);\" onMouseOut=\"$('#div2').css('opacity',0.5)\" onClick=\"showOptions(this,'showoptionsplus');\">\n            <tr>\n                ");
/* 2829 */                                   if (_jspx_meth_logic_005fpresent_005f9(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2831 */                                   out.write("\n                <td class=\"monitorinfoodd\" align=\"left\">");
/* 2832 */                                   if (_jspx_meth_fmt_005fmessage_005f39(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2834 */                                   out.write("</td>\n                <td class=\"monitorinfoodd\" align=\"left\">");
/* 2835 */                                   if (_jspx_meth_fmt_005fmessage_005f40(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2837 */                                   out.write("</td>\n                <td class=\"monitorinfoodd\" align=\"left\">");
/* 2838 */                                   if (_jspx_meth_fmt_005fmessage_005f41(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2840 */                                   out.write("</td>\n                <td class=\"monitorinfoodd\" align=\"left\">");
/* 2841 */                                   if (_jspx_meth_fmt_005fmessage_005f42(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2843 */                                   out.write("</td>\n                <td class=\"monitorinfoodd\" align=\"left\">");
/* 2844 */                                   if (_jspx_meth_fmt_005fmessage_005f43(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2846 */                                   out.write("</td>\n                <td class=\"monitorinfoodd\" align=\"center\" height=\"28\" width=\"3%\">&nbsp;</td>\n                <td class=\"monitorinfoodd\" align=\"center\" height=\"28\" width=\"3%\">&nbsp;</td>\n            </tr>\n            <input type=\"hidden\" name=\"rowids\" id=\"rowids\" value=\"\">\n            <input type=\"hidden\" name=\"fn\" id=\"fn\" value=\"\">\n            <input type=\"hidden\" name=\"specificmonitor\" id=\"specificmonitor\" value=\"true\"/>\n            <input type=\"hidden\" name=\"resourceid\" id=\"resourceid\" value=\"");
/* 2847 */                                   if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f3, _jspx_page_context))
/*      */                                     return;
/* 2849 */                                   out.write("\">\n            ");
/*      */                                   
/* 2851 */                                   ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2852 */                                   _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2853 */                                   _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fif_005f3);
/*      */                                   
/* 2855 */                                   _jspx_th_c_005fforEach_005f0.setVar("val");
/*      */                                   
/* 2857 */                                   _jspx_th_c_005fforEach_005f0.setItems("${subsystemToMon.subsystem}");
/*      */                                   
/* 2859 */                                   _jspx_th_c_005fforEach_005f0.setVarStatus("row");
/* 2860 */                                   int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                                   try {
/* 2862 */                                     int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2863 */                                     if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                                       for (;;) {
/* 2865 */                                         out.write("\n                ");
/* 2866 */                                         if (_jspx_meth_c_005fchoose_005f0(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2868 */                                         out.write("\n                <tr align=\"center\" onmouseout=\"this.className='mondetailsHeader'; toggledivmo('");
/* 2869 */                                         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2871 */                                         out.write("',0)\" onmouseover=\"this.className='mondetailsHeaderHover'; toggledivmo('");
/* 2872 */                                         if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2874 */                                         out.write("',1)\" class=\"mondetailsHeader\">\n                    ");
/* 2875 */                                         if (_jspx_meth_logic_005fpresent_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2877 */                                         out.write("\n                    <td align=\"left\" class=\"monitorinfoodd\">");
/* 2878 */                                         if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2880 */                                         out.write("</td>\n                    <td align=\"left\" class=\"monitorinfoodd\">");
/* 2881 */                                         if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2883 */                                         out.write("</td>\n                    <td align=\"left\" class=\"monitorinfoodd\" onmouseover=\"ddrivetip(this,event,'");
/* 2884 */                                         if (_jspx_meth_fmt_005fmessage_005f44(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2886 */                                         out.write(32);
/* 2887 */                                         if (_jspx_meth_fmt_005fmessage_005f45(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2889 */                                         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=jobFilter&resourceid=");
/* 2890 */                                         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2892 */                                         out.write("&status=subsystem&objname=");
/* 2893 */                                         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2895 */                                         out.write("&objlib=");
/* 2896 */                                         if (_jspx_meth_c_005fout_005f23(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2898 */                                         out.write("&fromAS400=false',1050,600,0,0)\" class=\"new-monitordiv-link\" href=\"javascript:void(0)\" > ");
/* 2899 */                                         if (_jspx_meth_c_005fout_005f24(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2901 */                                         out.write("</a></td>\n                    <td align=\"left\" class=\"monitorinfoodd\">");
/* 2902 */                                         if (_jspx_meth_c_005fout_005f25(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2904 */                                         out.write("</td>\n                    <td align=\"left\" class=\"monitorinfoodd\">");
/* 2905 */                                         if (_jspx_meth_c_005fout_005f26(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2907 */                                         out.write("</td>\n                    ");
/* 2908 */                                         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2910 */                                         out.write("\n\n                    ");
/* 2911 */                                         String thresholdurl = "/jsp/ThresholdActionConfiguration.jsp?resourceid=" + request.getAttribute("subsystemrid") + "&attributeIDs=2828,2827,2826,2825,2833&attributeToSelect=2828&redirectto=" + encodeurl;
/* 2912 */                                         out.write("\n                    <td align=\"center\" class=\"monitorinfoodd\" height=\"28\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2913 */                                         if (_jspx_meth_c_005fout_005f27(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2915 */                                         out.write("&attributeid=2828')\">");
/* 2916 */                                         out.print(getSeverityImageForHealth(alert.getProperty(request.getAttribute("subsystemrid") + "#" + "2828")));
/* 2917 */                                         out.write("</a></td>\n                    <td align=\"center\" class=\"monitorinfoodd\" height=\"28\"><div style=\"visibility: hidden;\" id=\"");
/* 2918 */                                         if (_jspx_meth_c_005fout_005f28(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2920 */                                         out.write("\" > ");
/*      */                                         
/* 2922 */                                         PresentTag _jspx_th_logic_005fpresent_005f11 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2923 */                                         _jspx_th_logic_005fpresent_005f11.setPageContext(_jspx_page_context);
/* 2924 */                                         _jspx_th_logic_005fpresent_005f11.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                                         
/* 2926 */                                         _jspx_th_logic_005fpresent_005f11.setRole("ADMIN,DEMO");
/* 2927 */                                         int _jspx_eval_logic_005fpresent_005f11 = _jspx_th_logic_005fpresent_005f11.doStartTag();
/* 2928 */                                         if (_jspx_eval_logic_005fpresent_005f11 != 0) {
/*      */                                           for (;;) {
/* 2930 */                                             out.write("\n                        <a href=");
/*      */                                             
/* 2932 */                                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2933 */                                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2934 */                                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f11);
/*      */                                             
/* 2936 */                                             _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2937 */                                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2938 */                                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                                               for (;;) {
/* 2940 */                                                 out.write(39);
/* 2941 */                                                 out.print(thresholdurl);
/* 2942 */                                                 out.write(39);
/* 2943 */                                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2944 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/*      */                                             }
/* 2948 */                                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2949 */                                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/*      */                                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 2952 */                                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2953 */                                             if (_jspx_meth_logic_005fpresent_005f12(_jspx_th_logic_005fpresent_005f11, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2984 */                                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                             }
/* 2955 */                                             out.write(" class=\"staticlinks\">  <img title=\"");
/* 2956 */                                             out.print(ALERTCONFIG_TEXT);
/* 2957 */                                             out.write("\" src=\"/images/icon_associateaction.gif\" alt=\"");
/* 2958 */                                             out.print(ALERTCONFIG_TEXT);
/* 2959 */                                             out.write("\" border=\"0\" hspace=\"5\" align=\"absmiddle\" ></a>\n                    </div>");
/* 2960 */                                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f11.doAfterBody();
/* 2961 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/*      */                                         }
/* 2965 */                                         if (_jspx_th_logic_005fpresent_005f11.doEndTag() == 5) {
/* 2966 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/*      */                                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                         }
/* 2969 */                                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f11);
/* 2970 */                                         out.write("</td>\n                </tr>\n            ");
/* 2971 */                                         int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2972 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 2976 */                                     if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2984 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/*      */                                   }
/*      */                                   catch (Throwable _jspx_exception)
/*      */                                   {
/*      */                                     for (;;)
/*      */                                     {
/* 2980 */                                       int tmp5511_5510 = 0; int[] tmp5511_5508 = _jspx_push_body_count_c_005fforEach_005f0; int tmp5513_5512 = tmp5511_5508[tmp5511_5510];tmp5511_5508[tmp5511_5510] = (tmp5513_5512 - 1); if (tmp5513_5512 <= 0) break;
/* 2981 */                                       out = _jspx_page_context.popBody(); }
/* 2982 */                                     _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                                   } finally {
/* 2984 */                                     _jspx_th_c_005fforEach_005f0.doFinally();
/* 2985 */                                     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                                   }
/* 2987 */                                   out.write("\n        </table>\n    </form>\n    <br>\n");
/* 2988 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2989 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2993 */                               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2994 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/*      */                               }
/*      */                               else {
/* 2997 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2998 */                                 out.write(10);
/* 2999 */                                 out.write(10);
/*      */                                 
/* 3001 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3002 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3003 */                                 _jspx_th_c_005fif_005f4.setParent(null);
/*      */                                 
/* 3005 */                                 _jspx_th_c_005fif_005f4.setTest("${disable}");
/* 3006 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3007 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 3009 */                                     out.write("\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  >\n        <tr>\n            <td  class='msg-status-tp-left-corn'></td>\n            <td class='msg-status-top-mid-bg'></td>\n            <td  class='msg-status-tp-right-corn'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-left-bg'>&nbsp;</td>\n            <td  width='98%' class='msg-table-width'>\n                <table cellpadding='0' cellspacing='0' width='98%' border='0'>\n                    <tr>\n                        <td width='3%' class='msg-table-width-bg'>\n                            <img src='../images/icon_message_success.gif' alt='icon' height='20' width='20'></td>\n                        <td height=\"28\" class=\"msg-table-width\">&nbsp;");
/*      */                                     
/* 3011 */                                     NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3012 */                                     _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3013 */                                     _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_c_005fif_005f4);
/*      */                                     
/* 3015 */                                     _jspx_th_logic_005fnotPresent_005f2.setRole("ADMIN,DEMO");
/* 3016 */                                     int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3017 */                                     if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                                       for (;;)
/*      */                                       {
/* 3020 */                                         org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 3021 */                                         _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3022 */                                         _jspx_th_bean_005fmessage_005f0.setParent(_jspx_th_logic_005fnotPresent_005f2);
/*      */                                         
/* 3024 */                                         _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.enable.admin.text");
/*      */                                         
/* 3026 */                                         _jspx_th_bean_005fmessage_005f0.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Subsystem"));
/* 3027 */                                         int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 3028 */                                         if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 3029 */                                           this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0); return;
/*      */                                         }
/*      */                                         
/* 3032 */                                         this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 3033 */                                         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3034 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3038 */                                     if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3039 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                                     }
/*      */                                     
/* 3042 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3043 */                                     out.write("\n                            ");
/*      */                                     
/* 3045 */                                     PresentTag _jspx_th_logic_005fpresent_005f13 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3046 */                                     _jspx_th_logic_005fpresent_005f13.setPageContext(_jspx_page_context);
/* 3047 */                                     _jspx_th_logic_005fpresent_005f13.setParent(_jspx_th_c_005fif_005f4);
/*      */                                     
/* 3049 */                                     _jspx_th_logic_005fpresent_005f13.setRole("ADMIN,DEMO");
/* 3050 */                                     int _jspx_eval_logic_005fpresent_005f13 = _jspx_th_logic_005fpresent_005f13.doStartTag();
/* 3051 */                                     if (_jspx_eval_logic_005fpresent_005f13 != 0) {
/*      */                                       for (;;)
/*      */                                       {
/* 3054 */                                         org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 3055 */                                         _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3056 */                                         _jspx_th_bean_005fmessage_005f1.setParent(_jspx_th_logic_005fpresent_005f13);
/*      */                                         
/* 3058 */                                         _jspx_th_bean_005fmessage_005f1.setKey("am.webclient.enable.text");
/*      */                                         
/* 3060 */                                         _jspx_th_bean_005fmessage_005f1.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Subsystem"));
/*      */                                         
/* 3062 */                                         _jspx_th_bean_005fmessage_005f1.setArg1(resourceid);
/* 3063 */                                         int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
/* 3064 */                                         if (_jspx_th_bean_005fmessage_005f1.doEndTag() == 5) {
/* 3065 */                                           this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1); return;
/*      */                                         }
/*      */                                         
/* 3068 */                                         this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 3069 */                                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f13.doAfterBody();
/* 3070 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/*      */                                     }
/* 3074 */                                     if (_jspx_th_logic_005fpresent_005f13.doEndTag() == 5) {
/* 3075 */                                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13); return;
/*      */                                     }
/*      */                                     
/* 3078 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f13);
/* 3079 */                                     out.write("</td>\n                    </tr>\n                </table>\n            </td>\n            <td class='msg-status-right-bg'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-btm-left-corn'>&nbsp;</td>\n            <td class='msg-status-btm-mid-bg'>&nbsp;</td>\n            <td class='msg-status-btm-right-corn'>&nbsp;</td>\n        </tr>\n    </table>\n");
/* 3080 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3081 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3085 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3086 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/*      */                                 }
/*      */                                 else {
/* 3089 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3090 */                                   out.write("\n\n<form name=\"formSpool\" id=\"formSpool\" action=\"/as400.do?method=subsystemActions\" method=\"post\">\n\n\n    <input type=\"hidden\" name=\"rowids\" id=\"rowids\" value=\"\">\n    <input type=\"hidden\" name=\"fn\" id=\"fn\" value=\"\">\n    <input type=\"hidden\" name=\"specificmonitor\" id=\"specificmonitor\" value=\"false\"/>\n    <input type=\"hidden\" name=\"resourceid\" id=\"resourceid\" value=\"");
/* 3091 */                                   out.print(resourceid);
/* 3092 */                                   out.write("\">\n\n\n\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table-border\" onMouseOver=\"$('#div3').css('opacity',1);\" onMouseOut=\"$('#div3').css('opacity',0.5)\">\n        <tr>\n            <td width=\"11%\" class=\"conf-mon-data-heading\" NOWRAP>");
/* 3093 */                                   out.print(FormatUtil.getString("am.webclient.as400.subsystemdetail"));
/* 3094 */                                   out.write("</td>\n\n            <td class=\"conf-mon-data-link\"  align=\"left\">\n                ");
/* 3095 */                                   if ((allowAS400) || (allowSUB)) {
/* 3096 */                                     out.write("\n                <table cellpadding=\"0\" cellspacing=\"0\">\n                    <tr>\n                        <td class=\"bodytextbold\">\n                            <select id=\"monitor\"  onchange=\"javascript:fnGetCheckAndSubmit(this)\" onmouseover=\"ddrivetip(this,event,'");
/* 3097 */                                     if (_jspx_meth_fmt_005fmessage_005f46(_jspx_page_context))
/*      */                                       return;
/* 3099 */                                     out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\">\n                                <option value=\"Actions\">");
/* 3100 */                                     if (_jspx_meth_fmt_005fmessage_005f47(_jspx_page_context))
/*      */                                       return;
/* 3102 */                                     out.write("</option>\n                                <option value=\"Delete\">");
/* 3103 */                                     if (_jspx_meth_fmt_005fmessage_005f48(_jspx_page_context))
/*      */                                       return;
/* 3105 */                                     out.write("</option>\n                                <option value=\"End\">");
/* 3106 */                                     if (_jspx_meth_fmt_005fmessage_005f49(_jspx_page_context))
/*      */                                       return;
/* 3108 */                                     out.write("</option>\n                                <option value=\"Start\">");
/* 3109 */                                     if (_jspx_meth_fmt_005fmessage_005f50(_jspx_page_context))
/*      */                                       return;
/* 3111 */                                     out.write("</option>\n                                <option value=\"Refresh\">");
/* 3112 */                                     if (_jspx_meth_fmt_005fmessage_005f51(_jspx_page_context))
/*      */                                       return;
/* 3114 */                                     out.write("</option>\n                            </select>\n                        </td>\n                    </tr>\n                </table>\n                ");
/*      */                                   }
/* 3116 */                                   out.write("\n            </td>\n            <td class=\"conf-mon-data-link\" align=\"right\">");
/*      */                                   
/* 3118 */                                   PresentTag _jspx_th_logic_005fpresent_005f14 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3119 */                                   _jspx_th_logic_005fpresent_005f14.setPageContext(_jspx_page_context);
/* 3120 */                                   _jspx_th_logic_005fpresent_005f14.setParent(null);
/*      */                                   
/* 3122 */                                   _jspx_th_logic_005fpresent_005f14.setRole("ADMIN,DEMO");
/* 3123 */                                   int _jspx_eval_logic_005fpresent_005f14 = _jspx_th_logic_005fpresent_005f14.doStartTag();
/* 3124 */                                   if (_jspx_eval_logic_005fpresent_005f14 != 0) {
/*      */                                     for (;;) {
/* 3126 */                                       out.write("<div style=\"opacity: 0.5;\" id=\"div3\" ><img title=\"");
/* 3127 */                                       out.print(ALERTCONFIG_TEXT);
/* 3128 */                                       out.write("\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\"><a id=\"configuresub\" onmouseover=\"ddrivetip(this,event,'");
/* 3129 */                                       if (_jspx_meth_fmt_005fmessage_005f52(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3131 */                                       out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onClick=");
/*      */                                       
/* 3133 */                                       NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 3134 */                                       _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 3135 */                                       _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_logic_005fpresent_005f14);
/*      */                                       
/* 3137 */                                       _jspx_th_logic_005fnotPresent_005f3.setRole("DEMO");
/* 3138 */                                       int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 3139 */                                       if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */                                         for (;;) {
/* 3141 */                                           out.write("\"window.location.href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3142 */                                           out.print(resourceid);
/* 3143 */                                           out.write("&attributeIDs=2704&attributeToSelect=2704&redirectto=");
/* 3144 */                                           out.print(encodeurl);
/* 3145 */                                           out.write(39);
/* 3146 */                                           out.write(34);
/* 3147 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 3148 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3152 */                                       if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 3153 */                                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */                                       }
/*      */                                       
/* 3156 */                                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 3157 */                                       if (_jspx_meth_logic_005fpresent_005f15(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3159 */                                       out.write(62);
/* 3160 */                                       out.print(ALERTCONFIG_TEXT);
/* 3161 */                                       out.write("</a><span style=\"color:#b7b7b7;font-family:Arial,Helvetica,Sans-serif;font-size:15px; padding:0px 5px 0px 5px;\">|</span>");
/* 3162 */                                       if (_jspx_meth_c_005fif_005f5(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3164 */                                       out.write("<img title=\"");
/* 3165 */                                       if (_jspx_meth_fmt_005fmessage_005f56(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3167 */                                       out.write("\" src=\"/images/icon_disable.gif\" style=\"position:relative; top:1px; left:1px;\"><a id =\"enabledisable\" onmouseover=\"ddrivetip(this,event,'");
/* 3168 */                                       if (_jspx_meth_fmt_005fmessage_005f57(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3170 */                                       out.write(32);
/* 3171 */                                       if (_jspx_meth_fmt_005fmessage_005f58(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3173 */                                       out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=getenabledetails&type=AS400/iSeries&resourceid=");
/* 3174 */                                       if (_jspx_meth_c_005fout_005f30(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3176 */                                       out.write("',850,400,0,0)\">");
/* 3177 */                                       if (_jspx_meth_fmt_005fmessage_005f59(_jspx_th_logic_005fpresent_005f14, _jspx_page_context))
/*      */                                         return;
/* 3179 */                                       out.write("</a>&nbsp;\n            </div>");
/* 3180 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f14.doAfterBody();
/* 3181 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3185 */                                   if (_jspx_th_logic_005fpresent_005f14.doEndTag() == 5) {
/* 3186 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/*      */                                   }
/*      */                                   else {
/* 3189 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f14);
/* 3190 */                                     out.write("</td>\n        </tr>\n    </table>\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"subsystemDetails\" class=\"lrborder\" onMouseOver=\"$('#div3').css('opacity',1);\" onMouseOut=\"$('#div3').css('opacity',0.5)\" onClick=\"showOptions(this,'showoptions');\">\n        <tr>\n            ");
/* 3191 */                                     if ((allowAS400) || (allowSUB)) {
/* 3192 */                                       out.write("\n            <td class=\"monitorinfoodd\" align=\"center\">&nbsp;\n                <input class=\"checkall\" type=\"checkbox\" name=\"spoolSel\" id=\"spoolSel\" onClick=\"javascript:ToggleAll(this,this.form,'checkuncheck');\" align=\"absmiddle\"/><span style=\"padding-left:5px;\"></span></td>\n            ");
/*      */                                     }
/* 3194 */                                     out.write("\n\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3195 */                                     out.print(FormatUtil.getString("am.webclient.as400.subsystemname"));
/* 3196 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3197 */                                     out.print(FormatUtil.getString("am.webclient.as400.library"));
/* 3198 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"right\" width=\"3%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3199 */                                     out.print(resourceid);
/* 3200 */                                     out.write("&attributeid=2704')\">");
/* 3201 */                                     out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2704")));
/* 3202 */                                     out.write("</a></td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3203 */                                     out.print(FormatUtil.getString("am.webclient.as400.activejobs"));
/* 3204 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3205 */                                     out.print(FormatUtil.getString("am.webclient.as400.mactivejobs"));
/* 3206 */                                     out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3207 */                                     out.print(FormatUtil.getString("am.webclient.as400.status"));
/* 3208 */                                     out.write("</td>\n        </tr>\n\n        ");
/*      */                                     
/* 3210 */                                     HashMap data = (HashMap)request.getAttribute("data");
/* 3211 */                                     if (!data.isEmpty())
/*      */                                     {
/*      */ 
/* 3214 */                                       List k = (ArrayList)data.get("subsystem");
/* 3215 */                                       for (int j = 0; j < k.size(); j++)
/*      */                                       {
/* 3217 */                                         Map p1 = new HashMap();
/* 3218 */                                         p1 = (HashMap)k.get(j);
/*      */                                         
/*      */ 
/*      */ 
/* 3222 */                                         out.write("\n\n        <tr align=\"center\" onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n            ");
/* 3223 */                                         if ((allowAS400) || (allowSUB)) {
/* 3224 */                                           out.write("\n            <td class=\"monitorinfoodd\" align=\"center\"><input class=\"checkthis\" type=\"checkbox\" name=\"checkuncheck\" value=\"");
/* 3225 */                                           out.print(p1.get("ID"));
/* 3226 */                                           out.write("\" ></td>\n            ");
/*      */                                         }
/* 3228 */                                         out.write("\n\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3229 */                                         out.print(p1.get("NAME"));
/* 3230 */                                         out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3231 */                                         out.print(p1.get("LIBRARY"));
/* 3232 */                                         out.write("</td>\n            <td class=\"monitorinfoodd\">&nbsp;</td>\n            <td align=\"left\" class=\"monitorinfoodd\" onmouseover=\"ddrivetip(this,event,'");
/* 3233 */                                         if (_jspx_meth_fmt_005fmessage_005f60(_jspx_page_context))
/*      */                                           return;
/* 3235 */                                         out.write(32);
/* 3236 */                                         if (_jspx_meth_fmt_005fmessage_005f61(_jspx_page_context))
/*      */                                           return;
/* 3238 */                                         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"><a onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=jobFilter&resourceid=");
/* 3239 */                                         if (_jspx_meth_c_005fout_005f31(_jspx_page_context))
/*      */                                           return;
/* 3241 */                                         out.write("&status=subsystem&objname=");
/* 3242 */                                         out.print(p1.get("NAME"));
/* 3243 */                                         out.write("&objlib=");
/* 3244 */                                         out.print(p1.get("LIBRARY"));
/* 3245 */                                         out.write("&fromAS400=false',1050,600,0,0)\" class=\"new-monitordiv-link\" href=\"javascript:void(0)\" > ");
/* 3246 */                                         out.print(p1.get("ACTIVE_JOBS"));
/* 3247 */                                         out.write("</a></td>\n            <td class=\"monitorinfoodd\"align=\"left\">");
/* 3248 */                                         out.print(p1.get("TOTAL_STORAGE"));
/* 3249 */                                         out.write("</td>\n            <td class=\"monitorinfoodd\" align=\"left\">");
/* 3250 */                                         out.print(p1.get("STATUS"));
/* 3251 */                                         out.write("</td>\n        </tr>\n\n        ");
/*      */                                         
/* 3253 */                                         tc++;
/*      */                                       }
/*      */                                       
/*      */                                     }
/*      */                                     else
/*      */                                     {
/* 3259 */                                       out.write("\n\n        <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n            <td colspan=\"7\" class=\"monitorinfoodd\" align=\"center\"><b>");
/* 3260 */                                       out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 3261 */                                       out.write("</b></td>\n        </tr>\n        ");
/*      */                                     }
/*      */                                     
/* 3264 */                                     out.write("\n    </table>\n    ");
/* 3265 */                                     if ((tc > 15) && ((allowAS400) || (allowSUB)))
/*      */                                     {
/*      */ 
/* 3268 */                                       out.write("\n    <table   width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" >\n        <tr>\n            <td class=\"conf-mon-data-link bodytextbold\"  align=\"left\" >  ");
/* 3269 */                                       out.print(FormatUtil.getString("am.webclient.as400.subsystemaction"));
/* 3270 */                                       out.write("&nbsp;\n                <select id=\"monitor1\"  onchange=\"javascript:fnGetCheckAndSubmit(this)\" onmouseover=\"ddrivetip(this,event,'");
/* 3271 */                                       if (_jspx_meth_fmt_005fmessage_005f62(_jspx_page_context))
/*      */                                         return;
/* 3273 */                                       out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\">\n                    <option value=\"Actions\">");
/* 3274 */                                       if (_jspx_meth_fmt_005fmessage_005f63(_jspx_page_context))
/*      */                                         return;
/* 3276 */                                       out.write("</option>\n                    <option value=\"Delete\">");
/* 3277 */                                       if (_jspx_meth_fmt_005fmessage_005f64(_jspx_page_context))
/*      */                                         return;
/* 3279 */                                       out.write("</option>\n                    <option value=\"End\">");
/* 3280 */                                       if (_jspx_meth_fmt_005fmessage_005f65(_jspx_page_context))
/*      */                                         return;
/* 3282 */                                       out.write("</option>\n                    <option value=\"Start\">");
/* 3283 */                                       if (_jspx_meth_fmt_005fmessage_005f66(_jspx_page_context))
/*      */                                         return;
/* 3285 */                                       out.write("</option>\n                    <option value=\"Refresh\">");
/* 3286 */                                       if (_jspx_meth_fmt_005fmessage_005f67(_jspx_page_context))
/*      */                                         return;
/* 3288 */                                       out.write("</option>\n                </select>\n            </td>\n        </tr>\n    </table>\n    ");
/*      */                                     }
/* 3290 */                                     out.write("\n</form>\n\n<script language=\"javascript\">\n\n    SORTTABLENAME = 'subsystemDetails'; //No I18N\n    var numberOfColumnsToBeSorted = 5;\n    sortables_init(numberOfColumnsToBeSorted,false,false,true);\n\n</script>\n<script language=\"javascript\">\n    SORTTABLENAME = 'subsystemDetails1'; //No I18N\n    var numberOfColumnsToBeSorted = 5;\n    sortables_init(numberOfColumnsToBeSorted,false,false,true);\n</script>\n\n");
/* 3291 */                                     if (_jspx_meth_c_005fset_005f3(_jspx_page_context)) return;
/*      */                                   }
/*      */                                 }
/* 3294 */                               } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3295 */         out = _jspx_out;
/* 3296 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3297 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3298 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3301 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3307 */     PageContext pageContext = _jspx_page_context;
/* 3308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3310 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3311 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3312 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3314 */     _jspx_th_c_005fout_005f0.setValue("${Debug_Info_Sub}");
/* 3315 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3316 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3317 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3318 */       return true;
/*      */     }
/* 3320 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3326 */     PageContext pageContext = _jspx_page_context;
/* 3327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3329 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3330 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3331 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3333 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.debug.info");
/* 3334 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3335 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3336 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3337 */       return true;
/*      */     }
/* 3339 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3345 */     PageContext pageContext = _jspx_page_context;
/* 3346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3348 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3349 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3350 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3352 */     _jspx_th_c_005fout_005f1.setValue("${Debug_Info_Sub_Sum}");
/* 3353 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3354 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3355 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3356 */       return true;
/*      */     }
/* 3358 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3359 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3364 */     PageContext pageContext = _jspx_page_context;
/* 3365 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3367 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f1 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3368 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3369 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3371 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.debug.summaryinfo");
/* 3372 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3373 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3374 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3375 */       return true;
/*      */     }
/* 3377 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f3(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3383 */     PageContext pageContext = _jspx_page_context;
/* 3384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3386 */     PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3387 */     _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3388 */     _jspx_th_logic_005fpresent_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3390 */     _jspx_th_logic_005fpresent_005f3.setRole("ADMIN");
/* 3391 */     int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3392 */     if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */       for (;;) {
/* 3394 */         out.write("\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onclick='$(\"#enabledisable\").click();'>");
/* 3395 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/* 3396 */           return true;
/* 3397 */         out.write("</a></td></tr>\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#addsubmon\").click();'>");
/* 3398 */         if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/* 3399 */           return true;
/* 3400 */         out.write("</a></td></tr>\n                <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#removesub\").click();'>");
/* 3401 */         if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/* 3402 */           return true;
/* 3403 */         out.write("</a></td></tr>\n            ");
/* 3404 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3405 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3409 */     if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3410 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3411 */       return true;
/*      */     }
/* 3413 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3414 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3419 */     PageContext pageContext = _jspx_page_context;
/* 3420 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3422 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f2 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3423 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 3424 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3426 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.as400.disable.subsystem");
/* 3427 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 3428 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 3429 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3430 */       return true;
/*      */     }
/* 3432 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 3433 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3438 */     PageContext pageContext = _jspx_page_context;
/* 3439 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3441 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f3 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3442 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 3443 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3445 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.as400.addsubsystemtomonitor");
/* 3446 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 3447 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3448 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3449 */       return true;
/*      */     }
/* 3451 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3452 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3457 */     PageContext pageContext = _jspx_page_context;
/* 3458 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3460 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f4 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3461 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3462 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3464 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.as400.removesubsystemmonitor");
/* 3465 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3466 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3467 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3468 */       return true;
/*      */     }
/* 3470 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3471 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3476 */     PageContext pageContext = _jspx_page_context;
/* 3477 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3479 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f5 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3480 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3481 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3483 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.as400.delete");
/* 3484 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3485 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3486 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3487 */       return true;
/*      */     }
/* 3489 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3495 */     PageContext pageContext = _jspx_page_context;
/* 3496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3498 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f6 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3499 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3500 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3502 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.as400.end");
/* 3503 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3504 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3505 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3506 */       return true;
/*      */     }
/* 3508 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3514 */     PageContext pageContext = _jspx_page_context;
/* 3515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3517 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f7 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3518 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3519 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3521 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.as400.start");
/* 3522 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3523 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3524 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3525 */       return true;
/*      */     }
/* 3527 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3528 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_logic_005fpresent_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3533 */     PageContext pageContext = _jspx_page_context;
/* 3534 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3536 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f8 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3537 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 3538 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_logic_005fpresent_005f2);
/*      */     
/* 3540 */     _jspx_th_fmt_005fmessage_005f8.setKey("am.webclient.as400.refresh");
/* 3541 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 3542 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 3543 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3544 */       return true;
/*      */     }
/* 3546 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 3547 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3552 */     PageContext pageContext = _jspx_page_context;
/* 3553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3555 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3556 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3557 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3559 */     _jspx_th_c_005fout_005f2.setValue("${Debug_Info_Sub}");
/* 3560 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3561 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3562 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3563 */       return true;
/*      */     }
/* 3565 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3566 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3571 */     PageContext pageContext = _jspx_page_context;
/* 3572 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3574 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f9 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3575 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 3576 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3578 */     _jspx_th_fmt_005fmessage_005f9.setKey("am.webclient.as400.debug.info");
/* 3579 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 3580 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 3581 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3582 */       return true;
/*      */     }
/* 3584 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 3585 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3590 */     PageContext pageContext = _jspx_page_context;
/* 3591 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3593 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3594 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3595 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3597 */     _jspx_th_c_005fout_005f3.setValue("${Debug_Info_Sub_Sum}");
/* 3598 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3599 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3600 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3601 */       return true;
/*      */     }
/* 3603 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3604 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3609 */     PageContext pageContext = _jspx_page_context;
/* 3610 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3612 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f10 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3613 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 3614 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3616 */     _jspx_th_fmt_005fmessage_005f10.setKey("am.webclient.as400.debug.summaryinfo");
/* 3617 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 3618 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 3619 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3620 */       return true;
/*      */     }
/* 3622 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 3623 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3628 */     PageContext pageContext = _jspx_page_context;
/* 3629 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3631 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f11 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3632 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 3633 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 3635 */     _jspx_th_fmt_005fmessage_005f11.setKey("am.webclient.as400.disable.subsystem");
/* 3636 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 3637 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 3638 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3639 */       return true;
/*      */     }
/* 3641 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 3642 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_logic_005fpresent_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3647 */     PageContext pageContext = _jspx_page_context;
/* 3648 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3650 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f12 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3651 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 3652 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f5);
/*      */     
/* 3654 */     _jspx_th_fmt_005fmessage_005f12.setKey("am.webclient.as400.addsubsystemtomonitor");
/* 3655 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 3656 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 3657 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3658 */       return true;
/*      */     }
/* 3660 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 3661 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3666 */     PageContext pageContext = _jspx_page_context;
/* 3667 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3669 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f13 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3670 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 3671 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3673 */     _jspx_th_fmt_005fmessage_005f13.setKey("am.webclient.as400.delete");
/* 3674 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 3675 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 3676 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3677 */       return true;
/*      */     }
/* 3679 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 3680 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3685 */     PageContext pageContext = _jspx_page_context;
/* 3686 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3688 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f14 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3689 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 3690 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3692 */     _jspx_th_fmt_005fmessage_005f14.setKey("am.webclient.as400.end");
/* 3693 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 3694 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 3695 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3696 */       return true;
/*      */     }
/* 3698 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 3699 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3704 */     PageContext pageContext = _jspx_page_context;
/* 3705 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3707 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f15 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3708 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 3709 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3711 */     _jspx_th_fmt_005fmessage_005f15.setKey("am.webclient.as400.start");
/* 3712 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 3713 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 3714 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3715 */       return true;
/*      */     }
/* 3717 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 3718 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_logic_005fpresent_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3723 */     PageContext pageContext = _jspx_page_context;
/* 3724 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3726 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f16 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3727 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 3728 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_logic_005fpresent_005f4);
/*      */     
/* 3730 */     _jspx_th_fmt_005fmessage_005f16.setKey("am.webclient.as400.refresh");
/* 3731 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 3732 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 3733 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3734 */       return true;
/*      */     }
/* 3736 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 3737 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3742 */     PageContext pageContext = _jspx_page_context;
/* 3743 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3745 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f17 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3746 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 3747 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3749 */     _jspx_th_fmt_005fmessage_005f17.setKey("am.webclient.as400.subsystemsummary");
/* 3750 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 3751 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 3752 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3753 */       return true;
/*      */     }
/* 3755 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 3756 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f7(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3761 */     PageContext pageContext = _jspx_page_context;
/* 3762 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3764 */     PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3765 */     _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3766 */     _jspx_th_logic_005fpresent_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 3768 */     _jspx_th_logic_005fpresent_005f7.setRole("DEMO");
/* 3769 */     int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3770 */     if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */       for (;;) {
/* 3772 */         out.write("\"javascript:alertUser();\"");
/* 3773 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3774 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3778 */     if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3779 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3780 */       return true;
/*      */     }
/* 3782 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3783 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3788 */     PageContext pageContext = _jspx_page_context;
/* 3789 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3791 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f18 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3792 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 3793 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3795 */     _jspx_th_fmt_005fmessage_005f18.setKey("am.webclient.as400.tooltip.view");
/* 3796 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 3797 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 3798 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3799 */       return true;
/*      */     }
/* 3801 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 3802 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3807 */     PageContext pageContext = _jspx_page_context;
/* 3808 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3810 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f19 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3811 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 3812 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3814 */     _jspx_th_fmt_005fmessage_005f19.setKey("am.webclient.as400.subsysteminclear");
/* 3815 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 3816 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 3817 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3818 */       return true;
/*      */     }
/* 3820 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 3821 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3826 */     PageContext pageContext = _jspx_page_context;
/* 3827 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3829 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3830 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3831 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3833 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3834 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3835 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3836 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3837 */       return true;
/*      */     }
/* 3839 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3845 */     PageContext pageContext = _jspx_page_context;
/* 3846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3848 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f20 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3849 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 3850 */     _jspx_th_fmt_005fmessage_005f20.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3852 */     _jspx_th_fmt_005fmessage_005f20.setKey("am.webclient.as400.tooltip.view");
/* 3853 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 3854 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 3855 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3856 */       return true;
/*      */     }
/* 3858 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 3859 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3864 */     PageContext pageContext = _jspx_page_context;
/* 3865 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3867 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f21 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3868 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 3869 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3871 */     _jspx_th_fmt_005fmessage_005f21.setKey("am.webclient.as400.subsystemincritical");
/* 3872 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 3873 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 3874 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3875 */       return true;
/*      */     }
/* 3877 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 3878 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3883 */     PageContext pageContext = _jspx_page_context;
/* 3884 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3886 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3887 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3888 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3890 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 3891 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3892 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3893 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3894 */       return true;
/*      */     }
/* 3896 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3897 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3902 */     PageContext pageContext = _jspx_page_context;
/* 3903 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3905 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3906 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3907 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3909 */     _jspx_th_c_005fif_005f1.setTest("${SUBSYSTEM_CLEAR gt 0}");
/* 3910 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3911 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3913 */         out.write("\n                            <td class=\"clearbar\" style=\"cursor: pointer;\" width=\"");
/* 3914 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3915 */           return true;
/* 3916 */         out.write("%\" title=\"");
/* 3917 */         if (_jspx_meth_fmt_005fmessage_005f22(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3918 */           return true;
/* 3919 */         out.write(32);
/* 3920 */         out.write(45);
/* 3921 */         out.write(32);
/* 3922 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3923 */           return true;
/* 3924 */         out.write("%\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=subsystemFilter&resourceid=");
/* 3925 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3926 */           return true;
/* 3927 */         out.write("&status=clear',1050,600,0,0); return false;\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n                        ");
/* 3928 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3929 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3933 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3934 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3935 */       return true;
/*      */     }
/* 3937 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3938 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3943 */     PageContext pageContext = _jspx_page_context;
/* 3944 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3946 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3947 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3948 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3950 */     _jspx_th_c_005fout_005f6.setValue("${SUBSYSTEM_CLEAR_PER}");
/* 3951 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3952 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3953 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3954 */       return true;
/*      */     }
/* 3956 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3957 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3962 */     PageContext pageContext = _jspx_page_context;
/* 3963 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3965 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f22 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3966 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 3967 */     _jspx_th_fmt_005fmessage_005f22.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3969 */     _jspx_th_fmt_005fmessage_005f22.setKey("am.webclient.as400.subsysteminclear");
/* 3970 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 3971 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 3972 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 3973 */       return true;
/*      */     }
/* 3975 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 3976 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3981 */     PageContext pageContext = _jspx_page_context;
/* 3982 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3984 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3985 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3986 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3988 */     _jspx_th_c_005fout_005f7.setValue("${SUBSYSTEM_CLEAR_PER}");
/* 3989 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3990 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3991 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3992 */       return true;
/*      */     }
/* 3994 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3995 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4000 */     PageContext pageContext = _jspx_page_context;
/* 4001 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4003 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4004 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 4005 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 4007 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 4008 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 4009 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 4010 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4011 */       return true;
/*      */     }
/* 4013 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 4014 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4019 */     PageContext pageContext = _jspx_page_context;
/* 4020 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4022 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4023 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 4024 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4026 */     _jspx_th_c_005fif_005f2.setTest("${SUBSYSTEM_CRITICAL gt 0}");
/* 4027 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 4028 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 4030 */         out.write("\n                            <td class=\"criticalbar\" style=\"cursor: pointer;\" width=\"");
/* 4031 */         if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4032 */           return true;
/* 4033 */         out.write("%\" title=\"");
/* 4034 */         if (_jspx_meth_fmt_005fmessage_005f23(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4035 */           return true;
/* 4036 */         out.write(32);
/* 4037 */         out.write(45);
/* 4038 */         out.write(32);
/* 4039 */         if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4040 */           return true;
/* 4041 */         out.write("%\" onClick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=subsystemFilter&resourceid=");
/* 4042 */         if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 4043 */           return true;
/* 4044 */         out.write("&status=critical',1050,600,0,0); return false;\"><img src=\"../images/spacer.gif\"  height=\"9\" width=\"0\"></td>\n                        ");
/* 4045 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 4046 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4050 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 4051 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4052 */       return true;
/*      */     }
/* 4054 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 4055 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4060 */     PageContext pageContext = _jspx_page_context;
/* 4061 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4063 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4064 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 4065 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4067 */     _jspx_th_c_005fout_005f9.setValue("${SUBSYSTEM_CRITICAL_PER}");
/* 4068 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 4069 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 4070 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4071 */       return true;
/*      */     }
/* 4073 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 4074 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4079 */     PageContext pageContext = _jspx_page_context;
/* 4080 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4082 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f23 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4083 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 4084 */     _jspx_th_fmt_005fmessage_005f23.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4086 */     _jspx_th_fmt_005fmessage_005f23.setKey("am.webclient.as400.subsystemincritical");
/* 4087 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 4088 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 4089 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 4090 */       return true;
/*      */     }
/* 4092 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 4093 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4098 */     PageContext pageContext = _jspx_page_context;
/* 4099 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4101 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4102 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 4103 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4105 */     _jspx_th_c_005fout_005f10.setValue("${SUBSYSTEM_CRITICAL_PER}");
/* 4106 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 4107 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 4108 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4109 */       return true;
/*      */     }
/* 4111 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 4112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4117 */     PageContext pageContext = _jspx_page_context;
/* 4118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4120 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4121 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4122 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 4124 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 4125 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4126 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4127 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4128 */       return true;
/*      */     }
/* 4130 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4131 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f24(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4136 */     PageContext pageContext = _jspx_page_context;
/* 4137 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4139 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f24 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4140 */     _jspx_th_fmt_005fmessage_005f24.setPageContext(_jspx_page_context);
/* 4141 */     _jspx_th_fmt_005fmessage_005f24.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4143 */     _jspx_th_fmt_005fmessage_005f24.setKey("Clear");
/* 4144 */     int _jspx_eval_fmt_005fmessage_005f24 = _jspx_th_fmt_005fmessage_005f24.doStartTag();
/* 4145 */     if (_jspx_th_fmt_005fmessage_005f24.doEndTag() == 5) {
/* 4146 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 4147 */       return true;
/*      */     }
/* 4149 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 4150 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4155 */     PageContext pageContext = _jspx_page_context;
/* 4156 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4158 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4159 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4160 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4162 */     _jspx_th_c_005fout_005f12.setValue("${SUBSYSTEM_CLEAR_PER}");
/* 4163 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4164 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4165 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4166 */       return true;
/*      */     }
/* 4168 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4169 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f25(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4174 */     PageContext pageContext = _jspx_page_context;
/* 4175 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4177 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f25 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4178 */     _jspx_th_fmt_005fmessage_005f25.setPageContext(_jspx_page_context);
/* 4179 */     _jspx_th_fmt_005fmessage_005f25.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4181 */     _jspx_th_fmt_005fmessage_005f25.setKey("Critical");
/* 4182 */     int _jspx_eval_fmt_005fmessage_005f25 = _jspx_th_fmt_005fmessage_005f25.doStartTag();
/* 4183 */     if (_jspx_th_fmt_005fmessage_005f25.doEndTag() == 5) {
/* 4184 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 4185 */       return true;
/*      */     }
/* 4187 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 4188 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4193 */     PageContext pageContext = _jspx_page_context;
/* 4194 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4196 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4197 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4198 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 4200 */     _jspx_th_c_005fout_005f13.setValue("${SUBSYSTEM_CRITICAL_PER}");
/* 4201 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4202 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4203 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4204 */       return true;
/*      */     }
/* 4206 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4207 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f26(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4212 */     PageContext pageContext = _jspx_page_context;
/* 4213 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4215 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f26 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4216 */     _jspx_th_fmt_005fmessage_005f26.setPageContext(_jspx_page_context);
/* 4217 */     _jspx_th_fmt_005fmessage_005f26.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4219 */     _jspx_th_fmt_005fmessage_005f26.setKey("am.webclient.as400.subsystemmonitor");
/* 4220 */     int _jspx_eval_fmt_005fmessage_005f26 = _jspx_th_fmt_005fmessage_005f26.doStartTag();
/* 4221 */     if (_jspx_th_fmt_005fmessage_005f26.doEndTag() == 5) {
/* 4222 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 4223 */       return true;
/*      */     }
/* 4225 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 4226 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f27(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4231 */     PageContext pageContext = _jspx_page_context;
/* 4232 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4234 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f27 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4235 */     _jspx_th_fmt_005fmessage_005f27.setPageContext(_jspx_page_context);
/* 4236 */     _jspx_th_fmt_005fmessage_005f27.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4238 */     _jspx_th_fmt_005fmessage_005f27.setKey("am.webclient.as400.tooltip.actions");
/* 4239 */     int _jspx_eval_fmt_005fmessage_005f27 = _jspx_th_fmt_005fmessage_005f27.doStartTag();
/* 4240 */     if (_jspx_th_fmt_005fmessage_005f27.doEndTag() == 5) {
/* 4241 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 4242 */       return true;
/*      */     }
/* 4244 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 4245 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f28(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4250 */     PageContext pageContext = _jspx_page_context;
/* 4251 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4253 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f28 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4254 */     _jspx_th_fmt_005fmessage_005f28.setPageContext(_jspx_page_context);
/* 4255 */     _jspx_th_fmt_005fmessage_005f28.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4257 */     _jspx_th_fmt_005fmessage_005f28.setKey("am.webclient.as400.actions");
/* 4258 */     int _jspx_eval_fmt_005fmessage_005f28 = _jspx_th_fmt_005fmessage_005f28.doStartTag();
/* 4259 */     if (_jspx_th_fmt_005fmessage_005f28.doEndTag() == 5) {
/* 4260 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 4261 */       return true;
/*      */     }
/* 4263 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 4264 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f29(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4269 */     PageContext pageContext = _jspx_page_context;
/* 4270 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4272 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f29 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4273 */     _jspx_th_fmt_005fmessage_005f29.setPageContext(_jspx_page_context);
/* 4274 */     _jspx_th_fmt_005fmessage_005f29.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4276 */     _jspx_th_fmt_005fmessage_005f29.setKey("am.webclient.as400.delete");
/* 4277 */     int _jspx_eval_fmt_005fmessage_005f29 = _jspx_th_fmt_005fmessage_005f29.doStartTag();
/* 4278 */     if (_jspx_th_fmt_005fmessage_005f29.doEndTag() == 5) {
/* 4279 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 4280 */       return true;
/*      */     }
/* 4282 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 4283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f30(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4288 */     PageContext pageContext = _jspx_page_context;
/* 4289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4291 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f30 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4292 */     _jspx_th_fmt_005fmessage_005f30.setPageContext(_jspx_page_context);
/* 4293 */     _jspx_th_fmt_005fmessage_005f30.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4295 */     _jspx_th_fmt_005fmessage_005f30.setKey("am.webclient.as400.end");
/* 4296 */     int _jspx_eval_fmt_005fmessage_005f30 = _jspx_th_fmt_005fmessage_005f30.doStartTag();
/* 4297 */     if (_jspx_th_fmt_005fmessage_005f30.doEndTag() == 5) {
/* 4298 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 4299 */       return true;
/*      */     }
/* 4301 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 4302 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f31(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4307 */     PageContext pageContext = _jspx_page_context;
/* 4308 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4310 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f31 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4311 */     _jspx_th_fmt_005fmessage_005f31.setPageContext(_jspx_page_context);
/* 4312 */     _jspx_th_fmt_005fmessage_005f31.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4314 */     _jspx_th_fmt_005fmessage_005f31.setKey("am.webclient.as400.start");
/* 4315 */     int _jspx_eval_fmt_005fmessage_005f31 = _jspx_th_fmt_005fmessage_005f31.doStartTag();
/* 4316 */     if (_jspx_th_fmt_005fmessage_005f31.doEndTag() == 5) {
/* 4317 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 4318 */       return true;
/*      */     }
/* 4320 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 4321 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f32(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4326 */     PageContext pageContext = _jspx_page_context;
/* 4327 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4329 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f32 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4330 */     _jspx_th_fmt_005fmessage_005f32.setPageContext(_jspx_page_context);
/* 4331 */     _jspx_th_fmt_005fmessage_005f32.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4333 */     _jspx_th_fmt_005fmessage_005f32.setKey("am.webclient.as400.refresh");
/* 4334 */     int _jspx_eval_fmt_005fmessage_005f32 = _jspx_th_fmt_005fmessage_005f32.doStartTag();
/* 4335 */     if (_jspx_th_fmt_005fmessage_005f32.doEndTag() == 5) {
/* 4336 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 4337 */       return true;
/*      */     }
/* 4339 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 4340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f8(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4345 */     PageContext pageContext = _jspx_page_context;
/* 4346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4348 */     PresentTag _jspx_th_logic_005fpresent_005f8 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4349 */     _jspx_th_logic_005fpresent_005f8.setPageContext(_jspx_page_context);
/* 4350 */     _jspx_th_logic_005fpresent_005f8.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4352 */     _jspx_th_logic_005fpresent_005f8.setRole("ADMIN,DEMO");
/* 4353 */     int _jspx_eval_logic_005fpresent_005f8 = _jspx_th_logic_005fpresent_005f8.doStartTag();
/* 4354 */     if (_jspx_eval_logic_005fpresent_005f8 != 0) {
/*      */       for (;;) {
/* 4356 */         out.write("<div style=\"opacity: 0.5;\" id=\"div2\" ><img title=\"");
/* 4357 */         if (_jspx_meth_fmt_005fmessage_005f33(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4358 */           return true;
/* 4359 */         out.write("\" src=\"/images/icon_custom_add_label.gif\" align=\"absmiddle\"><a id=\"addsubmon\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onmouseover=\"ddrivetip(this,event,'");
/* 4360 */         if (_jspx_meth_fmt_005fmessage_005f34(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4361 */           return true;
/* 4362 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=subsystemMonitor&resourceid=");
/* 4363 */         if (_jspx_meth_c_005fout_005f14(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4364 */           return true;
/* 4365 */         out.write("',900,600,0,0)\">");
/* 4366 */         if (_jspx_meth_fmt_005fmessage_005f35(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4367 */           return true;
/* 4368 */         out.write("</a><span style=\"color:#b7b7b7;font-family:Arial,Helvetica,Sans-serif;font-size:15px; padding:0px 5px 0px 5px;\">|</span><img title=\"");
/* 4369 */         if (_jspx_meth_fmt_005fmessage_005f36(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4370 */           return true;
/* 4371 */         out.write("\" src=\"/images/icon_del_jobs.gif\" align=\"absmiddle\"><a id=\"removesub\" class=\"new-monitordiv-link\" onmouseover=\"ddrivetip(this,event,'");
/* 4372 */         if (_jspx_meth_fmt_005fmessage_005f37(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4373 */           return true;
/* 4374 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" onclick=\"javascript:fnGetCheckAndSubmit(this)\">");
/* 4375 */         if (_jspx_meth_fmt_005fmessage_005f38(_jspx_th_logic_005fpresent_005f8, _jspx_page_context))
/* 4376 */           return true;
/* 4377 */         out.write("</a>&nbsp;&nbsp;&nbsp;\n                </div>");
/* 4378 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f8.doAfterBody();
/* 4379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4383 */     if (_jspx_th_logic_005fpresent_005f8.doEndTag() == 5) {
/* 4384 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4385 */       return true;
/*      */     }
/* 4387 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f8);
/* 4388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f33(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4393 */     PageContext pageContext = _jspx_page_context;
/* 4394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4396 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f33 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4397 */     _jspx_th_fmt_005fmessage_005f33.setPageContext(_jspx_page_context);
/* 4398 */     _jspx_th_fmt_005fmessage_005f33.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4400 */     _jspx_th_fmt_005fmessage_005f33.setKey("am.webclient.as400.addsubsystemtomonitor");
/* 4401 */     int _jspx_eval_fmt_005fmessage_005f33 = _jspx_th_fmt_005fmessage_005f33.doStartTag();
/* 4402 */     if (_jspx_th_fmt_005fmessage_005f33.doEndTag() == 5) {
/* 4403 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 4404 */       return true;
/*      */     }
/* 4406 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f33);
/* 4407 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f34(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4412 */     PageContext pageContext = _jspx_page_context;
/* 4413 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4415 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f34 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4416 */     _jspx_th_fmt_005fmessage_005f34.setPageContext(_jspx_page_context);
/* 4417 */     _jspx_th_fmt_005fmessage_005f34.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4419 */     _jspx_th_fmt_005fmessage_005f34.setKey("am.webclient.as400.tooltip.addsubsystem");
/* 4420 */     int _jspx_eval_fmt_005fmessage_005f34 = _jspx_th_fmt_005fmessage_005f34.doStartTag();
/* 4421 */     if (_jspx_th_fmt_005fmessage_005f34.doEndTag() == 5) {
/* 4422 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f34);
/* 4423 */       return true;
/*      */     }
/* 4425 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f34);
/* 4426 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4431 */     PageContext pageContext = _jspx_page_context;
/* 4432 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4434 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4435 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4436 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4438 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 4439 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4440 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4441 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4442 */       return true;
/*      */     }
/* 4444 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4445 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f35(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4450 */     PageContext pageContext = _jspx_page_context;
/* 4451 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4453 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f35 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4454 */     _jspx_th_fmt_005fmessage_005f35.setPageContext(_jspx_page_context);
/* 4455 */     _jspx_th_fmt_005fmessage_005f35.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4457 */     _jspx_th_fmt_005fmessage_005f35.setKey("am.webclient.as400.addsubsystemtomonitor");
/* 4458 */     int _jspx_eval_fmt_005fmessage_005f35 = _jspx_th_fmt_005fmessage_005f35.doStartTag();
/* 4459 */     if (_jspx_th_fmt_005fmessage_005f35.doEndTag() == 5) {
/* 4460 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f35);
/* 4461 */       return true;
/*      */     }
/* 4463 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f35);
/* 4464 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f36(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4469 */     PageContext pageContext = _jspx_page_context;
/* 4470 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4472 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f36 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4473 */     _jspx_th_fmt_005fmessage_005f36.setPageContext(_jspx_page_context);
/* 4474 */     _jspx_th_fmt_005fmessage_005f36.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4476 */     _jspx_th_fmt_005fmessage_005f36.setKey("am.webclient.as400.removesubsystemmonitor");
/* 4477 */     int _jspx_eval_fmt_005fmessage_005f36 = _jspx_th_fmt_005fmessage_005f36.doStartTag();
/* 4478 */     if (_jspx_th_fmt_005fmessage_005f36.doEndTag() == 5) {
/* 4479 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f36);
/* 4480 */       return true;
/*      */     }
/* 4482 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f36);
/* 4483 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f37(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4488 */     PageContext pageContext = _jspx_page_context;
/* 4489 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4491 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f37 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4492 */     _jspx_th_fmt_005fmessage_005f37.setPageContext(_jspx_page_context);
/* 4493 */     _jspx_th_fmt_005fmessage_005f37.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4495 */     _jspx_th_fmt_005fmessage_005f37.setKey("am.webclient.as400.tooltip.removesubsystem");
/* 4496 */     int _jspx_eval_fmt_005fmessage_005f37 = _jspx_th_fmt_005fmessage_005f37.doStartTag();
/* 4497 */     if (_jspx_th_fmt_005fmessage_005f37.doEndTag() == 5) {
/* 4498 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f37);
/* 4499 */       return true;
/*      */     }
/* 4501 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f37);
/* 4502 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f38(JspTag _jspx_th_logic_005fpresent_005f8, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4507 */     PageContext pageContext = _jspx_page_context;
/* 4508 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4510 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f38 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4511 */     _jspx_th_fmt_005fmessage_005f38.setPageContext(_jspx_page_context);
/* 4512 */     _jspx_th_fmt_005fmessage_005f38.setParent((Tag)_jspx_th_logic_005fpresent_005f8);
/*      */     
/* 4514 */     _jspx_th_fmt_005fmessage_005f38.setKey("am.webclient.as400.removesubsystemmonitor");
/* 4515 */     int _jspx_eval_fmt_005fmessage_005f38 = _jspx_th_fmt_005fmessage_005f38.doStartTag();
/* 4516 */     if (_jspx_th_fmt_005fmessage_005f38.doEndTag() == 5) {
/* 4517 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f38);
/* 4518 */       return true;
/*      */     }
/* 4520 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f38);
/* 4521 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f9(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4526 */     PageContext pageContext = _jspx_page_context;
/* 4527 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4529 */     PresentTag _jspx_th_logic_005fpresent_005f9 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4530 */     _jspx_th_logic_005fpresent_005f9.setPageContext(_jspx_page_context);
/* 4531 */     _jspx_th_logic_005fpresent_005f9.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4533 */     _jspx_th_logic_005fpresent_005f9.setRole("ADMIN");
/* 4534 */     int _jspx_eval_logic_005fpresent_005f9 = _jspx_th_logic_005fpresent_005f9.doStartTag();
/* 4535 */     if (_jspx_eval_logic_005fpresent_005f9 != 0) {
/*      */       for (;;) {
/* 4537 */         out.write("<td class=\"monitorinfoodd\" align=\"center\">&nbsp;\n                    <input class=\"checkall\" type=\"checkbox\" name=\"spoolSel\" id=\"spoolSel\" onClick=\"javascript:ToggleAll(this,this.form,'checkuncheck');\" align=\"absmiddle\"/><span style=\"padding-left:8px;\"></span></td>\n                ");
/* 4538 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f9.doAfterBody();
/* 4539 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4543 */     if (_jspx_th_logic_005fpresent_005f9.doEndTag() == 5) {
/* 4544 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 4545 */       return true;
/*      */     }
/* 4547 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f9);
/* 4548 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f39(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4553 */     PageContext pageContext = _jspx_page_context;
/* 4554 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4556 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f39 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4557 */     _jspx_th_fmt_005fmessage_005f39.setPageContext(_jspx_page_context);
/* 4558 */     _jspx_th_fmt_005fmessage_005f39.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4560 */     _jspx_th_fmt_005fmessage_005f39.setKey("am.webclient.as400.subsystemname");
/* 4561 */     int _jspx_eval_fmt_005fmessage_005f39 = _jspx_th_fmt_005fmessage_005f39.doStartTag();
/* 4562 */     if (_jspx_th_fmt_005fmessage_005f39.doEndTag() == 5) {
/* 4563 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f39);
/* 4564 */       return true;
/*      */     }
/* 4566 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f39);
/* 4567 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f40(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4572 */     PageContext pageContext = _jspx_page_context;
/* 4573 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4575 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f40 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4576 */     _jspx_th_fmt_005fmessage_005f40.setPageContext(_jspx_page_context);
/* 4577 */     _jspx_th_fmt_005fmessage_005f40.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4579 */     _jspx_th_fmt_005fmessage_005f40.setKey("am.webclient.as400.library");
/* 4580 */     int _jspx_eval_fmt_005fmessage_005f40 = _jspx_th_fmt_005fmessage_005f40.doStartTag();
/* 4581 */     if (_jspx_th_fmt_005fmessage_005f40.doEndTag() == 5) {
/* 4582 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f40);
/* 4583 */       return true;
/*      */     }
/* 4585 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f40);
/* 4586 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f41(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4591 */     PageContext pageContext = _jspx_page_context;
/* 4592 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4594 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f41 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4595 */     _jspx_th_fmt_005fmessage_005f41.setPageContext(_jspx_page_context);
/* 4596 */     _jspx_th_fmt_005fmessage_005f41.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4598 */     _jspx_th_fmt_005fmessage_005f41.setKey("am.webclient.as400.activejobs");
/* 4599 */     int _jspx_eval_fmt_005fmessage_005f41 = _jspx_th_fmt_005fmessage_005f41.doStartTag();
/* 4600 */     if (_jspx_th_fmt_005fmessage_005f41.doEndTag() == 5) {
/* 4601 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f41);
/* 4602 */       return true;
/*      */     }
/* 4604 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f41);
/* 4605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f42(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4610 */     PageContext pageContext = _jspx_page_context;
/* 4611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4613 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f42 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4614 */     _jspx_th_fmt_005fmessage_005f42.setPageContext(_jspx_page_context);
/* 4615 */     _jspx_th_fmt_005fmessage_005f42.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4617 */     _jspx_th_fmt_005fmessage_005f42.setKey("am.webclient.as400.mactivejobs");
/* 4618 */     int _jspx_eval_fmt_005fmessage_005f42 = _jspx_th_fmt_005fmessage_005f42.doStartTag();
/* 4619 */     if (_jspx_th_fmt_005fmessage_005f42.doEndTag() == 5) {
/* 4620 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f42);
/* 4621 */       return true;
/*      */     }
/* 4623 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f42);
/* 4624 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f43(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4629 */     PageContext pageContext = _jspx_page_context;
/* 4630 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4632 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f43 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4633 */     _jspx_th_fmt_005fmessage_005f43.setPageContext(_jspx_page_context);
/* 4634 */     _jspx_th_fmt_005fmessage_005f43.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4636 */     _jspx_th_fmt_005fmessage_005f43.setKey("am.webclient.as400.status");
/* 4637 */     int _jspx_eval_fmt_005fmessage_005f43 = _jspx_th_fmt_005fmessage_005f43.doStartTag();
/* 4638 */     if (_jspx_th_fmt_005fmessage_005f43.doEndTag() == 5) {
/* 4639 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f43);
/* 4640 */       return true;
/*      */     }
/* 4642 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f43);
/* 4643 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4648 */     PageContext pageContext = _jspx_page_context;
/* 4649 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4651 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4652 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4653 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 4655 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 4656 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4657 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4658 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4659 */       return true;
/*      */     }
/* 4661 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4662 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4667 */     PageContext pageContext = _jspx_page_context;
/* 4668 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4670 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 4671 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 4672 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 4673 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 4674 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 4676 */         out.write("\n                    ");
/* 4677 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4678 */           return true;
/* 4679 */         out.write("\n                    ");
/* 4680 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4681 */           return true;
/* 4682 */         out.write("\n                ");
/* 4683 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 4684 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4688 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 4689 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4690 */       return true;
/*      */     }
/* 4692 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 4693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4698 */     PageContext pageContext = _jspx_page_context;
/* 4699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4701 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 4702 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 4703 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 4705 */     _jspx_th_c_005fwhen_005f0.setTest("${row.count % 2 gt 0}");
/* 4706 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 4707 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 4709 */         out.write(32);
/* 4710 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4711 */           return true;
/* 4712 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 4713 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4717 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 4718 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4719 */       return true;
/*      */     }
/* 4721 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 4722 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4727 */     PageContext pageContext = _jspx_page_context;
/* 4728 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4730 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4731 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4732 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f0);
/*      */     
/* 4734 */     _jspx_th_c_005fset_005f0.setVar("rowbg");
/*      */     
/* 4736 */     _jspx_th_c_005fset_005f0.setValue("evenrow");
/* 4737 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4738 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4739 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4740 */       return true;
/*      */     }
/* 4742 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4743 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4748 */     PageContext pageContext = _jspx_page_context;
/* 4749 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4751 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 4752 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 4753 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 4754 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 4755 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 4757 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4758 */           return true;
/* 4759 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 4760 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4764 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 4765 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4766 */       return true;
/*      */     }
/* 4768 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 4769 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4774 */     PageContext pageContext = _jspx_page_context;
/* 4775 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4777 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4778 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 4779 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 4781 */     _jspx_th_c_005fset_005f1.setVar("rowbg");
/*      */     
/* 4783 */     _jspx_th_c_005fset_005f1.setValue("mondetailsHeader");
/* 4784 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 4785 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 4786 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4787 */       return true;
/*      */     }
/* 4789 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
/* 4790 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4795 */     PageContext pageContext = _jspx_page_context;
/* 4796 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4798 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4799 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4800 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4802 */     _jspx_th_c_005fout_005f16.setValue("${val.SUBSYSTEMRID}");
/* 4803 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4804 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4805 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4806 */       return true;
/*      */     }
/* 4808 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4809 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4814 */     PageContext pageContext = _jspx_page_context;
/* 4815 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4817 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4818 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4819 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4821 */     _jspx_th_c_005fout_005f17.setValue("${val.SUBSYSTEMRID}");
/* 4822 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4823 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4824 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4825 */       return true;
/*      */     }
/* 4827 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4828 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4833 */     PageContext pageContext = _jspx_page_context;
/* 4834 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4836 */     PresentTag _jspx_th_logic_005fpresent_005f10 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4837 */     _jspx_th_logic_005fpresent_005f10.setPageContext(_jspx_page_context);
/* 4838 */     _jspx_th_logic_005fpresent_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4840 */     _jspx_th_logic_005fpresent_005f10.setRole("ADMIN");
/* 4841 */     int _jspx_eval_logic_005fpresent_005f10 = _jspx_th_logic_005fpresent_005f10.doStartTag();
/* 4842 */     if (_jspx_eval_logic_005fpresent_005f10 != 0) {
/*      */       for (;;) {
/* 4844 */         out.write("\n                        <td align=\"center\" class=\"monitorinfoodd\"><input class=\"checkthis\" type=\"checkbox\" name=\"checkuncheck\" value=\"");
/* 4845 */         if (_jspx_meth_c_005fout_005f18(_jspx_th_logic_005fpresent_005f10, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 4846 */           return true;
/* 4847 */         out.write("\" ></td>\n                    ");
/* 4848 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f10.doAfterBody();
/* 4849 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4853 */     if (_jspx_th_logic_005fpresent_005f10.doEndTag() == 5) {
/* 4854 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4855 */       return true;
/*      */     }
/* 4857 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f10);
/* 4858 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_logic_005fpresent_005f10, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4863 */     PageContext pageContext = _jspx_page_context;
/* 4864 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4866 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4867 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4868 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_logic_005fpresent_005f10);
/*      */     
/* 4870 */     _jspx_th_c_005fout_005f18.setValue("${val.SUBSYSTEMRID}");
/* 4871 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4872 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4873 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4874 */       return true;
/*      */     }
/* 4876 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4877 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4882 */     PageContext pageContext = _jspx_page_context;
/* 4883 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4885 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4886 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4887 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4889 */     _jspx_th_c_005fout_005f19.setValue("${val.DISPLAYNAME}");
/* 4890 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4891 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4892 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4893 */       return true;
/*      */     }
/* 4895 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4896 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4901 */     PageContext pageContext = _jspx_page_context;
/* 4902 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4904 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4905 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4906 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4908 */     _jspx_th_c_005fout_005f20.setValue("${val.LIBRARY}");
/* 4909 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4910 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4911 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4912 */       return true;
/*      */     }
/* 4914 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f44(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4920 */     PageContext pageContext = _jspx_page_context;
/* 4921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4923 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f44 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4924 */     _jspx_th_fmt_005fmessage_005f44.setPageContext(_jspx_page_context);
/* 4925 */     _jspx_th_fmt_005fmessage_005f44.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4927 */     _jspx_th_fmt_005fmessage_005f44.setKey("am.webclient.as400.tooltip.view");
/* 4928 */     int _jspx_eval_fmt_005fmessage_005f44 = _jspx_th_fmt_005fmessage_005f44.doStartTag();
/* 4929 */     if (_jspx_th_fmt_005fmessage_005f44.doEndTag() == 5) {
/* 4930 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f44);
/* 4931 */       return true;
/*      */     }
/* 4933 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f44);
/* 4934 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f45(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4939 */     PageContext pageContext = _jspx_page_context;
/* 4940 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4942 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f45 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 4943 */     _jspx_th_fmt_005fmessage_005f45.setPageContext(_jspx_page_context);
/* 4944 */     _jspx_th_fmt_005fmessage_005f45.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4946 */     _jspx_th_fmt_005fmessage_005f45.setKey("am.webclient.as400.jobs");
/* 4947 */     int _jspx_eval_fmt_005fmessage_005f45 = _jspx_th_fmt_005fmessage_005f45.doStartTag();
/* 4948 */     if (_jspx_th_fmt_005fmessage_005f45.doEndTag() == 5) {
/* 4949 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f45);
/* 4950 */       return true;
/*      */     }
/* 4952 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f45);
/* 4953 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4958 */     PageContext pageContext = _jspx_page_context;
/* 4959 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4961 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4962 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4963 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4965 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 4966 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4967 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4968 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4969 */       return true;
/*      */     }
/* 4971 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4977 */     PageContext pageContext = _jspx_page_context;
/* 4978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4980 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4981 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4982 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 4984 */     _jspx_th_c_005fout_005f22.setValue("${val.DISPLAYNAME}");
/* 4985 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4986 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4987 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4988 */       return true;
/*      */     }
/* 4990 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4991 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 4996 */     PageContext pageContext = _jspx_page_context;
/* 4997 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4999 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5000 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 5001 */     _jspx_th_c_005fout_005f23.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5003 */     _jspx_th_c_005fout_005f23.setValue("${val.LIBRARY}");
/* 5004 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 5005 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 5006 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5007 */       return true;
/*      */     }
/* 5009 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 5010 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5015 */     PageContext pageContext = _jspx_page_context;
/* 5016 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5018 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5019 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 5020 */     _jspx_th_c_005fout_005f24.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5022 */     _jspx_th_c_005fout_005f24.setValue("${val.ACTIVE_JOBS}");
/* 5023 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 5024 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 5025 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5026 */       return true;
/*      */     }
/* 5028 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 5029 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5034 */     PageContext pageContext = _jspx_page_context;
/* 5035 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5037 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5038 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 5039 */     _jspx_th_c_005fout_005f25.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5041 */     _jspx_th_c_005fout_005f25.setValue("${val.TOTAL_STORAGE}");
/* 5042 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 5043 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 5044 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5045 */       return true;
/*      */     }
/* 5047 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 5048 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5053 */     PageContext pageContext = _jspx_page_context;
/* 5054 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5056 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5057 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 5058 */     _jspx_th_c_005fout_005f26.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5060 */     _jspx_th_c_005fout_005f26.setValue("${val.STATUS}");
/* 5061 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 5062 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 5063 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5064 */       return true;
/*      */     }
/* 5066 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 5067 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5072 */     PageContext pageContext = _jspx_page_context;
/* 5073 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5075 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 5076 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 5077 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5079 */     _jspx_th_c_005fset_005f2.setVar("subsystemrid");
/*      */     
/* 5081 */     _jspx_th_c_005fset_005f2.setValue("${val.SUBSYSTEMRID}");
/*      */     
/* 5083 */     _jspx_th_c_005fset_005f2.setScope("request");
/* 5084 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 5085 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 5086 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 5087 */       return true;
/*      */     }
/* 5089 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f2);
/* 5090 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5095 */     PageContext pageContext = _jspx_page_context;
/* 5096 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5098 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5099 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 5100 */     _jspx_th_c_005fout_005f27.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5102 */     _jspx_th_c_005fout_005f27.setValue("${subsystemrid}");
/* 5103 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 5104 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 5105 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5106 */       return true;
/*      */     }
/* 5108 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 5109 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5114 */     PageContext pageContext = _jspx_page_context;
/* 5115 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5117 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5118 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 5119 */     _jspx_th_c_005fout_005f28.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 5121 */     _jspx_th_c_005fout_005f28.setValue("${val.SUBSYSTEMRID}");
/* 5122 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 5123 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 5124 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5125 */       return true;
/*      */     }
/* 5127 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 5128 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f12(JspTag _jspx_th_logic_005fpresent_005f11, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 5133 */     PageContext pageContext = _jspx_page_context;
/* 5134 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5136 */     PresentTag _jspx_th_logic_005fpresent_005f12 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5137 */     _jspx_th_logic_005fpresent_005f12.setPageContext(_jspx_page_context);
/* 5138 */     _jspx_th_logic_005fpresent_005f12.setParent((Tag)_jspx_th_logic_005fpresent_005f11);
/*      */     
/* 5140 */     _jspx_th_logic_005fpresent_005f12.setRole("DEMO");
/* 5141 */     int _jspx_eval_logic_005fpresent_005f12 = _jspx_th_logic_005fpresent_005f12.doStartTag();
/* 5142 */     if (_jspx_eval_logic_005fpresent_005f12 != 0) {
/*      */       for (;;) {
/* 5144 */         out.write("\"javascript:alertUser();\"");
/* 5145 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f12.doAfterBody();
/* 5146 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5150 */     if (_jspx_th_logic_005fpresent_005f12.doEndTag() == 5) {
/* 5151 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 5152 */       return true;
/*      */     }
/* 5154 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f12);
/* 5155 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f46(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5160 */     PageContext pageContext = _jspx_page_context;
/* 5161 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5163 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f46 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5164 */     _jspx_th_fmt_005fmessage_005f46.setPageContext(_jspx_page_context);
/* 5165 */     _jspx_th_fmt_005fmessage_005f46.setParent(null);
/*      */     
/* 5167 */     _jspx_th_fmt_005fmessage_005f46.setKey("am.webclient.as400.tooltip.actions");
/* 5168 */     int _jspx_eval_fmt_005fmessage_005f46 = _jspx_th_fmt_005fmessage_005f46.doStartTag();
/* 5169 */     if (_jspx_th_fmt_005fmessage_005f46.doEndTag() == 5) {
/* 5170 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f46);
/* 5171 */       return true;
/*      */     }
/* 5173 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f46);
/* 5174 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f47(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5179 */     PageContext pageContext = _jspx_page_context;
/* 5180 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5182 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f47 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5183 */     _jspx_th_fmt_005fmessage_005f47.setPageContext(_jspx_page_context);
/* 5184 */     _jspx_th_fmt_005fmessage_005f47.setParent(null);
/*      */     
/* 5186 */     _jspx_th_fmt_005fmessage_005f47.setKey("am.webclient.as400.actions");
/* 5187 */     int _jspx_eval_fmt_005fmessage_005f47 = _jspx_th_fmt_005fmessage_005f47.doStartTag();
/* 5188 */     if (_jspx_th_fmt_005fmessage_005f47.doEndTag() == 5) {
/* 5189 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f47);
/* 5190 */       return true;
/*      */     }
/* 5192 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f47);
/* 5193 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f48(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5198 */     PageContext pageContext = _jspx_page_context;
/* 5199 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5201 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f48 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5202 */     _jspx_th_fmt_005fmessage_005f48.setPageContext(_jspx_page_context);
/* 5203 */     _jspx_th_fmt_005fmessage_005f48.setParent(null);
/*      */     
/* 5205 */     _jspx_th_fmt_005fmessage_005f48.setKey("am.webclient.as400.delete");
/* 5206 */     int _jspx_eval_fmt_005fmessage_005f48 = _jspx_th_fmt_005fmessage_005f48.doStartTag();
/* 5207 */     if (_jspx_th_fmt_005fmessage_005f48.doEndTag() == 5) {
/* 5208 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f48);
/* 5209 */       return true;
/*      */     }
/* 5211 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f48);
/* 5212 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f49(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5217 */     PageContext pageContext = _jspx_page_context;
/* 5218 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5220 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f49 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5221 */     _jspx_th_fmt_005fmessage_005f49.setPageContext(_jspx_page_context);
/* 5222 */     _jspx_th_fmt_005fmessage_005f49.setParent(null);
/*      */     
/* 5224 */     _jspx_th_fmt_005fmessage_005f49.setKey("am.webclient.as400.end");
/* 5225 */     int _jspx_eval_fmt_005fmessage_005f49 = _jspx_th_fmt_005fmessage_005f49.doStartTag();
/* 5226 */     if (_jspx_th_fmt_005fmessage_005f49.doEndTag() == 5) {
/* 5227 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f49);
/* 5228 */       return true;
/*      */     }
/* 5230 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f49);
/* 5231 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f50(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5236 */     PageContext pageContext = _jspx_page_context;
/* 5237 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5239 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f50 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5240 */     _jspx_th_fmt_005fmessage_005f50.setPageContext(_jspx_page_context);
/* 5241 */     _jspx_th_fmt_005fmessage_005f50.setParent(null);
/*      */     
/* 5243 */     _jspx_th_fmt_005fmessage_005f50.setKey("am.webclient.as400.start");
/* 5244 */     int _jspx_eval_fmt_005fmessage_005f50 = _jspx_th_fmt_005fmessage_005f50.doStartTag();
/* 5245 */     if (_jspx_th_fmt_005fmessage_005f50.doEndTag() == 5) {
/* 5246 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f50);
/* 5247 */       return true;
/*      */     }
/* 5249 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f50);
/* 5250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f51(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5255 */     PageContext pageContext = _jspx_page_context;
/* 5256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5258 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f51 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5259 */     _jspx_th_fmt_005fmessage_005f51.setPageContext(_jspx_page_context);
/* 5260 */     _jspx_th_fmt_005fmessage_005f51.setParent(null);
/*      */     
/* 5262 */     _jspx_th_fmt_005fmessage_005f51.setKey("am.webclient.as400.refresh");
/* 5263 */     int _jspx_eval_fmt_005fmessage_005f51 = _jspx_th_fmt_005fmessage_005f51.doStartTag();
/* 5264 */     if (_jspx_th_fmt_005fmessage_005f51.doEndTag() == 5) {
/* 5265 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f51);
/* 5266 */       return true;
/*      */     }
/* 5268 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f51);
/* 5269 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f52(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5274 */     PageContext pageContext = _jspx_page_context;
/* 5275 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5277 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f52 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5278 */     _jspx_th_fmt_005fmessage_005f52.setPageContext(_jspx_page_context);
/* 5279 */     _jspx_th_fmt_005fmessage_005f52.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5281 */     _jspx_th_fmt_005fmessage_005f52.setKey("am.webclient.as400.tooltip.configure");
/* 5282 */     int _jspx_eval_fmt_005fmessage_005f52 = _jspx_th_fmt_005fmessage_005f52.doStartTag();
/* 5283 */     if (_jspx_th_fmt_005fmessage_005f52.doEndTag() == 5) {
/* 5284 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f52);
/* 5285 */       return true;
/*      */     }
/* 5287 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f52);
/* 5288 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f15(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5293 */     PageContext pageContext = _jspx_page_context;
/* 5294 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5296 */     PresentTag _jspx_th_logic_005fpresent_005f15 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 5297 */     _jspx_th_logic_005fpresent_005f15.setPageContext(_jspx_page_context);
/* 5298 */     _jspx_th_logic_005fpresent_005f15.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5300 */     _jspx_th_logic_005fpresent_005f15.setRole("DEMO");
/* 5301 */     int _jspx_eval_logic_005fpresent_005f15 = _jspx_th_logic_005fpresent_005f15.doStartTag();
/* 5302 */     if (_jspx_eval_logic_005fpresent_005f15 != 0) {
/*      */       for (;;) {
/* 5304 */         out.write("\"javascript:alertUser();\"");
/* 5305 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f15.doAfterBody();
/* 5306 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5310 */     if (_jspx_th_logic_005fpresent_005f15.doEndTag() == 5) {
/* 5311 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 5312 */       return true;
/*      */     }
/* 5314 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f15);
/* 5315 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5320 */     PageContext pageContext = _jspx_page_context;
/* 5321 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5323 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 5324 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 5325 */     _jspx_th_c_005fif_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5327 */     _jspx_th_c_005fif_005f5.setTest("${empty subsystemToMon}");
/* 5328 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 5329 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 5331 */         out.write("<img title=\"");
/* 5332 */         if (_jspx_meth_fmt_005fmessage_005f53(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5333 */           return true;
/* 5334 */         out.write("\" src=\"/images/icon_custom_add_label.gif\" align=\"absmiddle\"><a id=\"addsubmon\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onmouseover=\"ddrivetip(this,event,'");
/* 5335 */         if (_jspx_meth_fmt_005fmessage_005f54(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5336 */           return true;
/* 5337 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=subsystemMonitor&resourceid=");
/* 5338 */         if (_jspx_meth_c_005fout_005f29(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5339 */           return true;
/* 5340 */         out.write("',900,600,0,0)\">");
/* 5341 */         if (_jspx_meth_fmt_005fmessage_005f55(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 5342 */           return true;
/* 5343 */         out.write("</a><span style=\"color:#b7b7b7;font-family:Arial,Helvetica,Sans-serif;font-size:15px; padding:0px 5px 0px 5px;\">|</span>");
/* 5344 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 5345 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 5349 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 5350 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5351 */       return true;
/*      */     }
/* 5353 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 5354 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f53(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5359 */     PageContext pageContext = _jspx_page_context;
/* 5360 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5362 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f53 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5363 */     _jspx_th_fmt_005fmessage_005f53.setPageContext(_jspx_page_context);
/* 5364 */     _jspx_th_fmt_005fmessage_005f53.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5366 */     _jspx_th_fmt_005fmessage_005f53.setKey("am.webclient.as400.addsubsystemtomonitor");
/* 5367 */     int _jspx_eval_fmt_005fmessage_005f53 = _jspx_th_fmt_005fmessage_005f53.doStartTag();
/* 5368 */     if (_jspx_th_fmt_005fmessage_005f53.doEndTag() == 5) {
/* 5369 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f53);
/* 5370 */       return true;
/*      */     }
/* 5372 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f53);
/* 5373 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f54(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5378 */     PageContext pageContext = _jspx_page_context;
/* 5379 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5381 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f54 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5382 */     _jspx_th_fmt_005fmessage_005f54.setPageContext(_jspx_page_context);
/* 5383 */     _jspx_th_fmt_005fmessage_005f54.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5385 */     _jspx_th_fmt_005fmessage_005f54.setKey("am.webclient.as400.tooltip.addsubsystem");
/* 5386 */     int _jspx_eval_fmt_005fmessage_005f54 = _jspx_th_fmt_005fmessage_005f54.doStartTag();
/* 5387 */     if (_jspx_th_fmt_005fmessage_005f54.doEndTag() == 5) {
/* 5388 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f54);
/* 5389 */       return true;
/*      */     }
/* 5391 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f54);
/* 5392 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f29(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5397 */     PageContext pageContext = _jspx_page_context;
/* 5398 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5400 */     OutTag _jspx_th_c_005fout_005f29 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5401 */     _jspx_th_c_005fout_005f29.setPageContext(_jspx_page_context);
/* 5402 */     _jspx_th_c_005fout_005f29.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5404 */     _jspx_th_c_005fout_005f29.setValue("${param.resourceid}");
/* 5405 */     int _jspx_eval_c_005fout_005f29 = _jspx_th_c_005fout_005f29.doStartTag();
/* 5406 */     if (_jspx_th_c_005fout_005f29.doEndTag() == 5) {
/* 5407 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5408 */       return true;
/*      */     }
/* 5410 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f29);
/* 5411 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f55(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5416 */     PageContext pageContext = _jspx_page_context;
/* 5417 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5419 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f55 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5420 */     _jspx_th_fmt_005fmessage_005f55.setPageContext(_jspx_page_context);
/* 5421 */     _jspx_th_fmt_005fmessage_005f55.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 5423 */     _jspx_th_fmt_005fmessage_005f55.setKey("am.webclient.as400.addsubsystemtomonitor");
/* 5424 */     int _jspx_eval_fmt_005fmessage_005f55 = _jspx_th_fmt_005fmessage_005f55.doStartTag();
/* 5425 */     if (_jspx_th_fmt_005fmessage_005f55.doEndTag() == 5) {
/* 5426 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f55);
/* 5427 */       return true;
/*      */     }
/* 5429 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f55);
/* 5430 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f56(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5435 */     PageContext pageContext = _jspx_page_context;
/* 5436 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5438 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f56 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5439 */     _jspx_th_fmt_005fmessage_005f56.setPageContext(_jspx_page_context);
/* 5440 */     _jspx_th_fmt_005fmessage_005f56.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5442 */     _jspx_th_fmt_005fmessage_005f56.setKey("am.webclient.as400.disable.subsystem");
/* 5443 */     int _jspx_eval_fmt_005fmessage_005f56 = _jspx_th_fmt_005fmessage_005f56.doStartTag();
/* 5444 */     if (_jspx_th_fmt_005fmessage_005f56.doEndTag() == 5) {
/* 5445 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f56);
/* 5446 */       return true;
/*      */     }
/* 5448 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f56);
/* 5449 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f57(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5454 */     PageContext pageContext = _jspx_page_context;
/* 5455 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5457 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f57 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5458 */     _jspx_th_fmt_005fmessage_005f57.setPageContext(_jspx_page_context);
/* 5459 */     _jspx_th_fmt_005fmessage_005f57.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5461 */     _jspx_th_fmt_005fmessage_005f57.setKey("am.webclient.as400.tooltip");
/* 5462 */     int _jspx_eval_fmt_005fmessage_005f57 = _jspx_th_fmt_005fmessage_005f57.doStartTag();
/* 5463 */     if (_jspx_th_fmt_005fmessage_005f57.doEndTag() == 5) {
/* 5464 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f57);
/* 5465 */       return true;
/*      */     }
/* 5467 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f57);
/* 5468 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f58(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5473 */     PageContext pageContext = _jspx_page_context;
/* 5474 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5476 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f58 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5477 */     _jspx_th_fmt_005fmessage_005f58.setPageContext(_jspx_page_context);
/* 5478 */     _jspx_th_fmt_005fmessage_005f58.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5480 */     _jspx_th_fmt_005fmessage_005f58.setKey("am.webclient.as400.disable.subsystem");
/* 5481 */     int _jspx_eval_fmt_005fmessage_005f58 = _jspx_th_fmt_005fmessage_005f58.doStartTag();
/* 5482 */     if (_jspx_th_fmt_005fmessage_005f58.doEndTag() == 5) {
/* 5483 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f58);
/* 5484 */       return true;
/*      */     }
/* 5486 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f58);
/* 5487 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f30(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5492 */     PageContext pageContext = _jspx_page_context;
/* 5493 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5495 */     OutTag _jspx_th_c_005fout_005f30 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5496 */     _jspx_th_c_005fout_005f30.setPageContext(_jspx_page_context);
/* 5497 */     _jspx_th_c_005fout_005f30.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5499 */     _jspx_th_c_005fout_005f30.setValue("${param.resourceid}");
/* 5500 */     int _jspx_eval_c_005fout_005f30 = _jspx_th_c_005fout_005f30.doStartTag();
/* 5501 */     if (_jspx_th_c_005fout_005f30.doEndTag() == 5) {
/* 5502 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5503 */       return true;
/*      */     }
/* 5505 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f30);
/* 5506 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f59(JspTag _jspx_th_logic_005fpresent_005f14, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5511 */     PageContext pageContext = _jspx_page_context;
/* 5512 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5514 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f59 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5515 */     _jspx_th_fmt_005fmessage_005f59.setPageContext(_jspx_page_context);
/* 5516 */     _jspx_th_fmt_005fmessage_005f59.setParent((Tag)_jspx_th_logic_005fpresent_005f14);
/*      */     
/* 5518 */     _jspx_th_fmt_005fmessage_005f59.setKey("am.webclient.as400.disable.subsystem");
/* 5519 */     int _jspx_eval_fmt_005fmessage_005f59 = _jspx_th_fmt_005fmessage_005f59.doStartTag();
/* 5520 */     if (_jspx_th_fmt_005fmessage_005f59.doEndTag() == 5) {
/* 5521 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f59);
/* 5522 */       return true;
/*      */     }
/* 5524 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f59);
/* 5525 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f60(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5530 */     PageContext pageContext = _jspx_page_context;
/* 5531 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5533 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f60 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5534 */     _jspx_th_fmt_005fmessage_005f60.setPageContext(_jspx_page_context);
/* 5535 */     _jspx_th_fmt_005fmessage_005f60.setParent(null);
/*      */     
/* 5537 */     _jspx_th_fmt_005fmessage_005f60.setKey("am.webclient.as400.tooltip.view");
/* 5538 */     int _jspx_eval_fmt_005fmessage_005f60 = _jspx_th_fmt_005fmessage_005f60.doStartTag();
/* 5539 */     if (_jspx_th_fmt_005fmessage_005f60.doEndTag() == 5) {
/* 5540 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f60);
/* 5541 */       return true;
/*      */     }
/* 5543 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f60);
/* 5544 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f61(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5549 */     PageContext pageContext = _jspx_page_context;
/* 5550 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5552 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f61 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5553 */     _jspx_th_fmt_005fmessage_005f61.setPageContext(_jspx_page_context);
/* 5554 */     _jspx_th_fmt_005fmessage_005f61.setParent(null);
/*      */     
/* 5556 */     _jspx_th_fmt_005fmessage_005f61.setKey("am.webclient.as400.jobs");
/* 5557 */     int _jspx_eval_fmt_005fmessage_005f61 = _jspx_th_fmt_005fmessage_005f61.doStartTag();
/* 5558 */     if (_jspx_th_fmt_005fmessage_005f61.doEndTag() == 5) {
/* 5559 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f61);
/* 5560 */       return true;
/*      */     }
/* 5562 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f61);
/* 5563 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f31(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5568 */     PageContext pageContext = _jspx_page_context;
/* 5569 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5571 */     OutTag _jspx_th_c_005fout_005f31 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5572 */     _jspx_th_c_005fout_005f31.setPageContext(_jspx_page_context);
/* 5573 */     _jspx_th_c_005fout_005f31.setParent(null);
/*      */     
/* 5575 */     _jspx_th_c_005fout_005f31.setValue("${param.resourceid}");
/* 5576 */     int _jspx_eval_c_005fout_005f31 = _jspx_th_c_005fout_005f31.doStartTag();
/* 5577 */     if (_jspx_th_c_005fout_005f31.doEndTag() == 5) {
/* 5578 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5579 */       return true;
/*      */     }
/* 5581 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f31);
/* 5582 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f62(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5587 */     PageContext pageContext = _jspx_page_context;
/* 5588 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5590 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f62 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5591 */     _jspx_th_fmt_005fmessage_005f62.setPageContext(_jspx_page_context);
/* 5592 */     _jspx_th_fmt_005fmessage_005f62.setParent(null);
/*      */     
/* 5594 */     _jspx_th_fmt_005fmessage_005f62.setKey("am.webclient.as400.tooltip.actions");
/* 5595 */     int _jspx_eval_fmt_005fmessage_005f62 = _jspx_th_fmt_005fmessage_005f62.doStartTag();
/* 5596 */     if (_jspx_th_fmt_005fmessage_005f62.doEndTag() == 5) {
/* 5597 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f62);
/* 5598 */       return true;
/*      */     }
/* 5600 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f62);
/* 5601 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f63(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5606 */     PageContext pageContext = _jspx_page_context;
/* 5607 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5609 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f63 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5610 */     _jspx_th_fmt_005fmessage_005f63.setPageContext(_jspx_page_context);
/* 5611 */     _jspx_th_fmt_005fmessage_005f63.setParent(null);
/*      */     
/* 5613 */     _jspx_th_fmt_005fmessage_005f63.setKey("am.webclient.as400.actions");
/* 5614 */     int _jspx_eval_fmt_005fmessage_005f63 = _jspx_th_fmt_005fmessage_005f63.doStartTag();
/* 5615 */     if (_jspx_th_fmt_005fmessage_005f63.doEndTag() == 5) {
/* 5616 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f63);
/* 5617 */       return true;
/*      */     }
/* 5619 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f63);
/* 5620 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f64(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5625 */     PageContext pageContext = _jspx_page_context;
/* 5626 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5628 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f64 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5629 */     _jspx_th_fmt_005fmessage_005f64.setPageContext(_jspx_page_context);
/* 5630 */     _jspx_th_fmt_005fmessage_005f64.setParent(null);
/*      */     
/* 5632 */     _jspx_th_fmt_005fmessage_005f64.setKey("am.webclient.as400.delete");
/* 5633 */     int _jspx_eval_fmt_005fmessage_005f64 = _jspx_th_fmt_005fmessage_005f64.doStartTag();
/* 5634 */     if (_jspx_th_fmt_005fmessage_005f64.doEndTag() == 5) {
/* 5635 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f64);
/* 5636 */       return true;
/*      */     }
/* 5638 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f64);
/* 5639 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f65(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5644 */     PageContext pageContext = _jspx_page_context;
/* 5645 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5647 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f65 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5648 */     _jspx_th_fmt_005fmessage_005f65.setPageContext(_jspx_page_context);
/* 5649 */     _jspx_th_fmt_005fmessage_005f65.setParent(null);
/*      */     
/* 5651 */     _jspx_th_fmt_005fmessage_005f65.setKey("am.webclient.as400.end");
/* 5652 */     int _jspx_eval_fmt_005fmessage_005f65 = _jspx_th_fmt_005fmessage_005f65.doStartTag();
/* 5653 */     if (_jspx_th_fmt_005fmessage_005f65.doEndTag() == 5) {
/* 5654 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f65);
/* 5655 */       return true;
/*      */     }
/* 5657 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f65);
/* 5658 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f66(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5663 */     PageContext pageContext = _jspx_page_context;
/* 5664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5666 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f66 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5667 */     _jspx_th_fmt_005fmessage_005f66.setPageContext(_jspx_page_context);
/* 5668 */     _jspx_th_fmt_005fmessage_005f66.setParent(null);
/*      */     
/* 5670 */     _jspx_th_fmt_005fmessage_005f66.setKey("am.webclient.as400.start");
/* 5671 */     int _jspx_eval_fmt_005fmessage_005f66 = _jspx_th_fmt_005fmessage_005f66.doStartTag();
/* 5672 */     if (_jspx_th_fmt_005fmessage_005f66.doEndTag() == 5) {
/* 5673 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f66);
/* 5674 */       return true;
/*      */     }
/* 5676 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f66);
/* 5677 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f67(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5682 */     PageContext pageContext = _jspx_page_context;
/* 5683 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5685 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f67 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 5686 */     _jspx_th_fmt_005fmessage_005f67.setPageContext(_jspx_page_context);
/* 5687 */     _jspx_th_fmt_005fmessage_005f67.setParent(null);
/*      */     
/* 5689 */     _jspx_th_fmt_005fmessage_005f67.setKey("am.webclient.as400.refresh");
/* 5690 */     int _jspx_eval_fmt_005fmessage_005f67 = _jspx_th_fmt_005fmessage_005f67.doStartTag();
/* 5691 */     if (_jspx_th_fmt_005fmessage_005f67.doEndTag() == 5) {
/* 5692 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f67);
/* 5693 */       return true;
/*      */     }
/* 5695 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f67);
/* 5696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5701 */     PageContext pageContext = _jspx_page_context;
/* 5702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5704 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 5705 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 5706 */     _jspx_th_c_005fset_005f3.setParent(null);
/*      */     
/* 5708 */     _jspx_th_c_005fset_005f3.setVar("datatype");
/*      */     
/* 5710 */     _jspx_th_c_005fset_005f3.setValue("10");
/*      */     
/* 5712 */     _jspx_th_c_005fset_005f3.setScope("session");
/* 5713 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 5714 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 5715 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 5716 */       return true;
/*      */     }
/* 5718 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f3);
/* 5719 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\subsystem_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */