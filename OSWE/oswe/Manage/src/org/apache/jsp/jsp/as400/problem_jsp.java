/*      */ package org.apache.jsp.jsp.as400;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
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
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class problem_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  856 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  857 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  860 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  861 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  862 */       set = AMConnectionPool.executeQueryStmt(query);
/*  863 */       if (set.next())
/*      */       {
/*  865 */         String helpLink = set.getString("LINK");
/*  866 */         DBUtil.searchLinks.put(key, helpLink);
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg2_005farg1_005farg0_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2207 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2211 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg2_005farg1_005farg0_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2225 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2229 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2235 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2236 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2240 */     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg2_005farg1_005farg0_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2248 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2251 */     JspWriter out = null;
/* 2252 */     Object page = this;
/* 2253 */     JspWriter _jspx_out = null;
/* 2254 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2258 */       response.setContentType("text/html;charset=UTF-8");
/* 2259 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2261 */       _jspx_page_context = pageContext;
/* 2262 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2263 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2264 */       session = pageContext.getSession();
/* 2265 */       out = pageContext.getOut();
/* 2266 */       _jspx_out = out;
/*      */       
/* 2268 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2269 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2271 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2272 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2277 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2282 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2283 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2284 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2287 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2288 */         String available = null;
/* 2289 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2290 */         out.write(10);
/*      */         
/* 2292 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2293 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2298 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2303 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2304 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2305 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2308 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2309 */           String unavailable = null;
/* 2310 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2311 */           out.write(10);
/*      */           
/* 2313 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2314 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2319 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2324 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2325 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2326 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2329 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2330 */             String unmanaged = null;
/* 2331 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2332 */             out.write(10);
/*      */             
/* 2334 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2335 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2340 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2345 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2346 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2347 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2350 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2351 */               String scheduled = null;
/* 2352 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2353 */               out.write(10);
/*      */               
/* 2355 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2356 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2361 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2366 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2367 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2368 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2371 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2372 */                 String critical = null;
/* 2373 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2374 */                 out.write(10);
/*      */                 
/* 2376 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2377 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2382 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2387 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2388 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2389 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2392 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2393 */                   String clear = null;
/* 2394 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2395 */                   out.write(10);
/*      */                   
/* 2397 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2398 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2403 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2408 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2409 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2410 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2413 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2414 */                     String warning = null;
/* 2415 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2416 */                     out.write(10);
/* 2417 */                     out.write(10);
/*      */                     
/* 2419 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2420 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2422 */                     out.write(10);
/* 2423 */                     out.write(10);
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/* 2426 */                     out.write(10);
/*      */                     
/* 2428 */                     String bgcolor = "";
/* 2429 */                     int tc = 0;
/*      */                     
/* 2431 */                     String resourceid = request.getParameter("resourceid");
/* 2432 */                     String encodeurl = URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid + "&datatype=9");
/* 2433 */                     String troubleshootlink = FormatUtil.getString("am.webclient.as400.troubleshoot.link");
/*      */                     
/* 2435 */                     ArrayList resIDs = new ArrayList();
/* 2436 */                     resIDs.add(resourceid);
/*      */                     
/* 2438 */                     ArrayList<String> attribIDs = new ArrayList();
/* 2439 */                     for (int i = 2752; i < 2755; i++)
/*      */                     {
/* 2441 */                       attribIDs.add("" + i);
/*      */                     }
/* 2443 */                     attribIDs.add("2749");
/* 2444 */                     attribIDs.add("2750");
/* 2445 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2447 */                     out.write("\n\n<br>\n<div style=\"display:none;\" id=\"showoptions\" >\n    <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"apmDblClickMenu\" onClick=\"closeDialog();\">\n        ");
/*      */                     
/* 2449 */                     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2450 */                     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2451 */                     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                     
/* 2453 */                     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN");
/* 2454 */                     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2455 */                     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                       for (;;) {
/* 2457 */                         out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2458 */                         if (_jspx_meth_c_005fout_005f0(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                           return;
/* 2460 */                         out.write("'target=_blank>");
/* 2461 */                         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                           return;
/* 2463 */                         out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onClick='$(\"#configureprb\").click();'>");
/* 2464 */                         out.print(ALERTCONFIG_TEXT);
/* 2465 */                         out.write("</a></td></tr>\n            <tr><td class=\"monitorinfoodd-noborder\"><a href=\"javascript:void(0)\" onclick='$(\"#enabledisable\").click();'>");
/* 2466 */                         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fpresent_005f0, _jspx_page_context))
/*      */                           return;
/* 2468 */                         out.write("</a></td></tr>\n        ");
/* 2469 */                         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2470 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2474 */                     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2475 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                     }
/*      */                     else {
/* 2478 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2479 */                       out.write("\n        ");
/* 2480 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2482 */                       out.write("\n    </table>\n</div>\n\n");
/*      */                       
/* 2484 */                       IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2485 */                       _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2486 */                       _jspx_th_c_005fif_005f0.setParent(null);
/*      */                       
/* 2488 */                       _jspx_th_c_005fif_005f0.setTest("${disable || (not empty monMsg && not empty msgType)}");
/* 2489 */                       int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2490 */                       if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                         for (;;) {
/* 2492 */                           out.write("\n    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"marg-btm\">\n        <tr>\n            <td  class='msg-status-tp-left-corn'></td>\n            <td class='msg-status-top-mid-bg'></td>\n            <td  class='msg-status-tp-right-corn'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-left-bg'>&nbsp;</td>\n            <td  width='98%' class='msg-table-width'>\n                <table cellpadding='0' cellspacing='0' width='98%' border='0'>\n                    <tr>\n                        <td width='3%' class='msg-table-width-bg'>\n                            ");
/*      */                           
/* 2494 */                           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2495 */                           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2496 */                           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_c_005fif_005f0);
/* 2497 */                           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2498 */                           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                             for (;;) {
/* 2500 */                               out.write("\n                            ");
/*      */                               
/* 2502 */                               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2503 */                               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2504 */                               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                               
/* 2506 */                               _jspx_th_c_005fwhen_005f0.setTest("${disable}");
/* 2507 */                               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2508 */                               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                 for (;;) {
/* 2510 */                                   out.write("\n                            <img src='../images/icon_message_success.gif' alt='icon' height='25' width='25'></td>\n                        <td height=\"28\" class=\"msg-table-width\">&nbsp;");
/*      */                                   
/* 2512 */                                   NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2513 */                                   _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2514 */                                   _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                   
/* 2516 */                                   _jspx_th_logic_005fnotPresent_005f0.setRole("ADMIN,DEMO");
/* 2517 */                                   int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2518 */                                   if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                                     for (;;)
/*      */                                     {
/* 2521 */                                       org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f0 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2522 */                                       _jspx_th_bean_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2523 */                                       _jspx_th_bean_005fmessage_005f0.setParent(_jspx_th_logic_005fnotPresent_005f0);
/*      */                                       
/* 2525 */                                       _jspx_th_bean_005fmessage_005f0.setKey("am.webclient.enable.admin.text");
/*      */                                       
/* 2527 */                                       _jspx_th_bean_005fmessage_005f0.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Problem"));
/* 2528 */                                       int _jspx_eval_bean_005fmessage_005f0 = _jspx_th_bean_005fmessage_005f0.doStartTag();
/* 2529 */                                       if (_jspx_th_bean_005fmessage_005f0.doEndTag() == 5) {
/* 2530 */                                         this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0); return;
/*      */                                       }
/*      */                                       
/* 2533 */                                       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f0);
/* 2534 */                                       int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2535 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2539 */                                   if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2540 */                                     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                                   }
/*      */                                   
/* 2543 */                                   this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2544 */                                   out.write("\n                            ");
/*      */                                   
/* 2546 */                                   PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2547 */                                   _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2548 */                                   _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                                   
/* 2550 */                                   _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,DEMO");
/* 2551 */                                   int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2552 */                                   if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                                     for (;;)
/*      */                                     {
/* 2555 */                                       org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f1 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2556 */                                       _jspx_th_bean_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2557 */                                       _jspx_th_bean_005fmessage_005f1.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                                       
/* 2559 */                                       _jspx_th_bean_005fmessage_005f1.setKey("am.webclient.enable.text");
/*      */                                       
/* 2561 */                                       _jspx_th_bean_005fmessage_005f1.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Problem"));
/*      */                                       
/* 2563 */                                       _jspx_th_bean_005fmessage_005f1.setArg1(resourceid);
/* 2564 */                                       int _jspx_eval_bean_005fmessage_005f1 = _jspx_th_bean_005fmessage_005f1.doStartTag();
/* 2565 */                                       if (_jspx_th_bean_005fmessage_005f1.doEndTag() == 5) {
/* 2566 */                                         this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1); return;
/*      */                                       }
/*      */                                       
/* 2569 */                                       this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f1);
/* 2570 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 2571 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2575 */                                   if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 2576 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                                   }
/*      */                                   
/* 2579 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 2580 */                                   out.write("\n                        </td>\n                        ");
/* 2581 */                                   int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2582 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2586 */                               if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2587 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                               }
/*      */                               
/* 2590 */                               this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2591 */                               out.write("\n                        ");
/*      */                               
/* 2593 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2594 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2595 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2596 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2597 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/* 2599 */                                   out.write("\n                            ");
/* 2600 */                                   if (_jspx_meth_c_005fif_005f1(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                     return;
/* 2602 */                                   out.write("\n                            ");
/* 2603 */                                   if (_jspx_meth_c_005fif_005f2(_jspx_th_c_005fotherwise_005f0, _jspx_page_context))
/*      */                                     return;
/* 2605 */                                   out.write("\n                            <td height=\"28\" class=\"msg-table-width\">&nbsp;");
/*      */                                   
/* 2607 */                                   org.apache.struts.taglib.bean.MessageTag _jspx_th_bean_005fmessage_005f2 = (org.apache.struts.taglib.bean.MessageTag)this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg2_005farg1_005farg0_005fnobody.get(org.apache.struts.taglib.bean.MessageTag.class);
/* 2608 */                                   _jspx_th_bean_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2609 */                                   _jspx_th_bean_005fmessage_005f2.setParent(_jspx_th_c_005fotherwise_005f0);
/*      */                                   
/* 2611 */                                   _jspx_th_bean_005fmessage_005f2.setKey("am.webclient.as400.error2");
/*      */                                   
/* 2613 */                                   _jspx_th_bean_005fmessage_005f2.setArg0(FormatUtil.getString("am.webclient.as400innertabs.Problem"));
/*      */                                   
/* 2615 */                                   _jspx_th_bean_005fmessage_005f2.setArg1(request.getAttribute("monMsg").toString());
/*      */                                   
/* 2617 */                                   _jspx_th_bean_005fmessage_005f2.setArg2(troubleshootlink);
/* 2618 */                                   int _jspx_eval_bean_005fmessage_005f2 = _jspx_th_bean_005fmessage_005f2.doStartTag();
/* 2619 */                                   if (_jspx_th_bean_005fmessage_005f2.doEndTag() == 5) {
/* 2620 */                                     this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg2_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2); return;
/*      */                                   }
/*      */                                   
/* 2623 */                                   this._005fjspx_005ftagPool_005fbean_005fmessage_0026_005fkey_005farg2_005farg1_005farg0_005fnobody.reuse(_jspx_th_bean_005fmessage_005f2);
/* 2624 */                                   out.write("</td>\n                        ");
/* 2625 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2626 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2630 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2631 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/* 2634 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2635 */                               out.write("\n                        ");
/* 2636 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2637 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2641 */                           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2642 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                           }
/*      */                           
/* 2645 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2646 */                           out.write("\n                    </tr>\n                </table>\n            </td>\n            <td class='msg-status-right-bg'></td>\n        </tr>\n        <tr>\n            <td class='msg-status-btm-left-corn'>&nbsp;</td>\n            <td class='msg-status-btm-mid-bg'>&nbsp;</td>\n            <td class='msg-status-btm-right-corn'>&nbsp;</td>\n        </tr>\n    </table>\n");
/* 2647 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2648 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2652 */                       if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2653 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                       }
/*      */                       else {
/* 2656 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2657 */                         out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-data-table-border\" onMouseOver=\"$('#div1').css('opacity',1);\" onMouseOut=\"$('#div1').css('opacity',0.5)\">\n    <tr>\n        <td class=\"conf-mon-data-heading\">");
/* 2658 */                         out.print(FormatUtil.getString("am.webclient.as400.problemdetails"));
/* 2659 */                         out.write("</td>\n        <td class=\"conf-mon-data-link\" align=\"right\">");
/*      */                         
/* 2661 */                         PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2662 */                         _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 2663 */                         _jspx_th_logic_005fpresent_005f3.setParent(null);
/*      */                         
/* 2665 */                         _jspx_th_logic_005fpresent_005f3.setRole("ADMIN,DEMO");
/* 2666 */                         int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 2667 */                         if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                           for (;;) {
/* 2669 */                             out.write("<div style=\"opacity: 0.5;\" id=\"div1\" ><img title=\"");
/* 2670 */                             out.print(ALERTCONFIG_TEXT);
/* 2671 */                             out.write("\" src=\"/images/icon_associateaction.gif\" align=\"absmiddle\"><a id=\"configureprb\" onmouseover=\"ddrivetip(this,event,'");
/* 2672 */                             if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2674 */                             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onClick=");
/*      */                             
/* 2676 */                             NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2677 */                             _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2678 */                             _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_logic_005fpresent_005f3);
/*      */                             
/* 2680 */                             _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2681 */                             int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2682 */                             if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                               for (;;) {
/* 2684 */                                 out.write("\"window.location.href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2685 */                                 out.print(resourceid);
/* 2686 */                                 out.write("&attributeIDs=2750&attributeToSelect=2750&redirectto=");
/* 2687 */                                 out.print(encodeurl);
/* 2688 */                                 out.write(39);
/* 2689 */                                 out.write(34);
/* 2690 */                                 int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2691 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2695 */                             if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2696 */                               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                             }
/*      */                             
/* 2699 */                             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2700 */                             if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2702 */                             out.write(32);
/* 2703 */                             out.write(62);
/* 2704 */                             out.print(ALERTCONFIG_TEXT);
/* 2705 */                             out.write("</a><span style=\"color:#b7b7b7;font-family:Arial,Helvetica,Sans-serif;font-size:15px; padding:0px 5px 0px 5px;\">|</span><img title=\"");
/* 2706 */                             if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2708 */                             out.write("\" src=\"/images/icon_disable.gif\" style=\"position:relative; top:1px; left:1px;\"><a id=\"enabledisable\" onmouseover=\"ddrivetip(this,event,'");
/* 2709 */                             if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2711 */                             out.write(32);
/* 2712 */                             if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2714 */                             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" href=\"javascript:void(0)\" class=\"new-monitordiv-link\" onclick=\"fnOpenNewWindowWithHeightWidthPlacement('/as400.do?method=getenabledetails&type=AS400/iSeries&resourceid=");
/* 2715 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2717 */                             out.write("',850,400,0,0)\">");
/* 2718 */                             if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_logic_005fpresent_005f3, _jspx_page_context))
/*      */                               return;
/* 2720 */                             out.write("</a>&nbsp;\n        </div>");
/* 2721 */                             int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 2722 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2726 */                         if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 2727 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/*      */                         }
/*      */                         else {
/* 2730 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 2731 */                           out.write("</td>\n    </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" id=\"problemDetails\" class=\"lrbborder\" onMouseOver=\"$('#div1').css('opacity',1);\" onMouseOut=\"$('#div1').css('opacity',0.5)\" onClick=\"showOptions(this,'showoptions');\">\n    <tr>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2732 */                           out.print(FormatUtil.getString("am.webclient.as400.problemid"));
/* 2733 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"right\" width=\"3%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2734 */                           out.print(resourceid);
/* 2735 */                           out.write("&attributeid=2750')\">");
/* 2736 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "2750")));
/* 2737 */                           out.write("</a></td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2738 */                           out.print(FormatUtil.getString("am.webclient.as400.problemseverity"));
/* 2739 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2740 */                           out.print(FormatUtil.getString("am.webclient.as400.problemtype"));
/* 2741 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2742 */                           out.print(FormatUtil.getString("am.webclient.as400.status"));
/* 2743 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2744 */                           out.print(FormatUtil.getString("am.webclient.as400.dateopened"));
/* 2745 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2746 */                           out.print(FormatUtil.getString("am.webclient.as400.timeopened"));
/* 2747 */                           out.write("</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2748 */                           out.print(FormatUtil.getString("am.webclient.as400.userprepared"));
/* 2749 */                           out.write("</td>\n    </tr>\n    ");
/*      */                           
/* 2751 */                           HashMap data = (HashMap)request.getAttribute("data");
/* 2752 */                           if (!data.isEmpty())
/*      */                           {
/*      */ 
/* 2755 */                             List k = (ArrayList)data.get("problem");
/* 2756 */                             for (int j = 0; j < k.size(); j++)
/*      */                             {
/* 2758 */                               Map p1 = new HashMap();
/* 2759 */                               p1 = (HashMap)k.get(j);
/*      */                               
/*      */ 
/* 2762 */                               out.write("\n\n    <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2763 */                               out.print(p1.get("PROBLEM_ID"));
/* 2764 */                               out.write("&nbsp;</td>\n        <td class=\"monitorinfoodd\">&nbsp;</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2765 */                               out.print(p1.get("PROBLEM_SEVERITY"));
/* 2766 */                               out.write("&nbsp;</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2767 */                               out.print(p1.get("PROBLEM_TYPE"));
/* 2768 */                               out.write("&nbsp;</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2769 */                               out.print(p1.get("PROBLEM_STATUS"));
/* 2770 */                               out.write("&nbsp;</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2771 */                               out.print(p1.get("DATE_OPENED"));
/* 2772 */                               out.write("&nbsp;</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2773 */                               out.print(p1.get("TIME_OPENED"));
/* 2774 */                               out.write("&nbsp;</td>\n        <td class=\"monitorinfoodd\" align=\"left\">");
/* 2775 */                               out.print(p1.get("USER_PREPARED"));
/* 2776 */                               out.write("&nbsp;</td>\n\n    </tr>\n\n    ");
/*      */                               
/* 2778 */                               tc++;
/*      */                             }
/*      */                             
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/* 2785 */                             out.write("\n    <tr onmouseout=\"this.className='mondetailsHeader'\" onmouseover=\"this.className='mondetailsHeaderHover'\" class=\"mondetailsHeader\">\n        <td colspan=\"8\" class=\"monitorinfoodd\" align=\"center\"><b>");
/* 2786 */                             out.print(FormatUtil.getString("am.webclient.common.nodata.text"));
/* 2787 */                             out.write("</b></td>\n    </tr>\n    ");
/*      */                           }
/*      */                           
/* 2790 */                           out.write("\n\n</table>\n<script language=\"javascript\">\n\n    SORTTABLENAME = 'problemDetails'; //No I18N\n    var numberOfColumnsToBeSorted = 7;\n    sortables_init(numberOfColumnsToBeSorted,false,false,true);\n\n</script>\n\n");
/* 2791 */                           if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
/*      */                             return;
/* 2793 */                           out.write(10);
/*      */                         }
/* 2795 */                       } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2796 */         out = _jspx_out;
/* 2797 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2798 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 2799 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2802 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2808 */     PageContext pageContext = _jspx_page_context;
/* 2809 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2811 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2812 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 2813 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 2815 */     _jspx_th_c_005fout_005f0.setValue("${Debug_Info_Problem}");
/* 2816 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 2817 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 2818 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2819 */       return true;
/*      */     }
/* 2821 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 2822 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2827 */     PageContext pageContext = _jspx_page_context;
/* 2828 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2830 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f0 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2831 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 2832 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 2834 */     _jspx_th_fmt_005fmessage_005f0.setKey("am.webclient.as400.debug.info");
/* 2835 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 2836 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 2837 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2838 */       return true;
/*      */     }
/* 2840 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 2841 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fpresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2846 */     PageContext pageContext = _jspx_page_context;
/* 2847 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2849 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f1 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2850 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 2851 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f0);
/*      */     
/* 2853 */     _jspx_th_fmt_005fmessage_005f1.setKey("am.webclient.as400.disable.problem");
/* 2854 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 2855 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 2856 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2857 */       return true;
/*      */     }
/* 2859 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 2860 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2865 */     PageContext pageContext = _jspx_page_context;
/* 2866 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2868 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2869 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2870 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 2872 */     _jspx_th_logic_005fpresent_005f1.setRole("OPERATOR");
/* 2873 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2874 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2876 */         out.write("\n            <tr><td class=\"monitorinfoodd-noborder\"><a class='resourcename' href='");
/* 2877 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2878 */           return true;
/* 2879 */         out.write("'target=_blank>");
/* 2880 */         if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_logic_005fpresent_005f1, _jspx_page_context))
/* 2881 */           return true;
/* 2882 */         out.write("</a></td></tr>\n        ");
/* 2883 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2884 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2888 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2889 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2890 */       return true;
/*      */     }
/* 2892 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2893 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2898 */     PageContext pageContext = _jspx_page_context;
/* 2899 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2901 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 2902 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 2903 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2905 */     _jspx_th_c_005fout_005f1.setValue("${Debug_Info_Problem}");
/* 2906 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 2907 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 2908 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2909 */       return true;
/*      */     }
/* 2911 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 2912 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_logic_005fpresent_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2917 */     PageContext pageContext = _jspx_page_context;
/* 2918 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2920 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f2 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2921 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 2922 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f1);
/*      */     
/* 2924 */     _jspx_th_fmt_005fmessage_005f2.setKey("am.webclient.as400.debug.info");
/* 2925 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 2926 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 2927 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2928 */       return true;
/*      */     }
/* 2930 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 2931 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2936 */     PageContext pageContext = _jspx_page_context;
/* 2937 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2939 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2940 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2941 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2943 */     _jspx_th_c_005fif_005f1.setTest("${msgType!= 1}");
/* 2944 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2945 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 2947 */         out.write("\n                                <img src='../images/icon_message_success.gif' alt='icon' height='25' width='25'></td>\n                            ");
/* 2948 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2949 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2953 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2954 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2955 */       return true;
/*      */     }
/* 2957 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2958 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_c_005fotherwise_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2963 */     PageContext pageContext = _jspx_page_context;
/* 2964 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2966 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2967 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2968 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_c_005fotherwise_005f0);
/*      */     
/* 2970 */     _jspx_th_c_005fif_005f2.setTest("${msgType== 1}");
/* 2971 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2972 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 2974 */         out.write("\n                                <img src='../images/icon_message_failure.gif' alt='icon' height='25' width='25'></td>\n                            ");
/* 2975 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2976 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2980 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2981 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2982 */       return true;
/*      */     }
/* 2984 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2985 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2990 */     PageContext pageContext = _jspx_page_context;
/* 2991 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2993 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f3 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 2994 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 2995 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 2997 */     _jspx_th_fmt_005fmessage_005f3.setKey("am.webclient.as400.tooltip.configure");
/* 2998 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 2999 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 3000 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3001 */       return true;
/*      */     }
/* 3003 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 3004 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3009 */     PageContext pageContext = _jspx_page_context;
/* 3010 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3012 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3013 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3014 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3016 */     _jspx_th_logic_005fpresent_005f4.setRole("DEMO");
/* 3017 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3018 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 3020 */         out.write("\"javascript:alertUser();\"");
/* 3021 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3022 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3026 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3027 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3028 */       return true;
/*      */     }
/* 3030 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3031 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3036 */     PageContext pageContext = _jspx_page_context;
/* 3037 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3039 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f4 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3040 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 3041 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3043 */     _jspx_th_fmt_005fmessage_005f4.setKey("am.webclient.as400.disable.problem");
/* 3044 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 3045 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 3046 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3047 */       return true;
/*      */     }
/* 3049 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 3050 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3055 */     PageContext pageContext = _jspx_page_context;
/* 3056 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3058 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f5 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3059 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 3060 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3062 */     _jspx_th_fmt_005fmessage_005f5.setKey("am.webclient.as400.tooltip");
/* 3063 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 3064 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 3065 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3066 */       return true;
/*      */     }
/* 3068 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 3069 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3074 */     PageContext pageContext = _jspx_page_context;
/* 3075 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3077 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f6 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3078 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 3079 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3081 */     _jspx_th_fmt_005fmessage_005f6.setKey("am.webclient.as400.disable.problem");
/* 3082 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 3083 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 3084 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3085 */       return true;
/*      */     }
/* 3087 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 3088 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3093 */     PageContext pageContext = _jspx_page_context;
/* 3094 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3096 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3097 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3098 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3100 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 3101 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3102 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3103 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3104 */       return true;
/*      */     }
/* 3106 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_logic_005fpresent_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3112 */     PageContext pageContext = _jspx_page_context;
/* 3113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3115 */     org.apache.taglibs.standard.tag.el.fmt.MessageTag _jspx_th_fmt_005fmessage_005f7 = (org.apache.taglibs.standard.tag.el.fmt.MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.get(org.apache.taglibs.standard.tag.el.fmt.MessageTag.class);
/* 3116 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 3117 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_logic_005fpresent_005f3);
/*      */     
/* 3119 */     _jspx_th_fmt_005fmessage_005f7.setKey("am.webclient.as400.disable.problem");
/* 3120 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 3121 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 3122 */       this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3123 */       return true;
/*      */     }
/* 3125 */     this._005fjspx_005ftagPool_005ffmt_005fmessage_0026_005fkey_005fnobody.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 3126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3131 */     PageContext pageContext = _jspx_page_context;
/* 3132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3134 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.get(SetTag.class);
/* 3135 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3136 */     _jspx_th_c_005fset_005f0.setParent(null);
/*      */     
/* 3138 */     _jspx_th_c_005fset_005f0.setVar("datatype");
/*      */     
/* 3140 */     _jspx_th_c_005fset_005f0.setValue("9");
/*      */     
/* 3142 */     _jspx_th_c_005fset_005f0.setScope("session");
/* 3143 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3144 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3145 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3146 */       return true;
/*      */     }
/* 3148 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fscope_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 3149 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\as400\problem_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */