/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import javax.servlet.http.Cookie;
/*      */ import javax.servlet.http.HttpServletRequest;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.JspRuntimeLibrary;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class NewThresholdConfiguration_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 2186 */   static { _jspx_dependants.put("/jsp/includes/Actions.jspf", Long.valueOf(1473429417000L));
/* 2187 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2188 */     _jspx_dependants.put("/jsp/includes/AlarmConfigurationLeftPage.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2206 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2221 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2227 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2229 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
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
/* 2261 */       out.write("<!DOCTYPE html>\n");
/* 2262 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2263 */       out.write(10);
/* 2264 */       out.write("\n\n\n\n\n");
/* 2265 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2267 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2268 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2270 */       out.write(10);
/* 2271 */       out.write(10);
/* 2272 */       out.write(10);
/* 2273 */       out.write("\n\n\n\n\n\n\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
/*      */       
/* 2275 */       String tabs = "am.webclient.rule.action,am.webclient.rule.defAlarmRules,am.webclient.rule.depDevice,am.webclient.rule.depeMGroups";
/* 2276 */       boolean isdelegatedAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/* 2277 */       String resourceId = request.getParameter("resourceid");
/* 2278 */       String attributeids = request.getParameter("attributeIDs");
/* 2279 */       String[] attributeIDs = attributeids.split(",");
/* 2280 */       int attributeId = Integer.parseInt(request.getParameter("attributeToSelect"));
/* 2281 */       String invokingParams = request.getQueryString();
/* 2282 */       String returnpath = "/jsp/NewThresholdConfiguration.jsp?" + invokingParams;
/* 2283 */       String redirectPage = "/showapplication.do?&method=showApplication&haid=" + resourceId;
/* 2284 */       String tabToSelect = "am.webclient.rule.action";
/* 2285 */       String isSubGroup = "false";
/* 2286 */       if (com.adventnet.appmanager.util.Constants.isSubGroup(resourceId))
/*      */       {
/* 2288 */         isSubGroup = "true";
/*      */       }
/* 2290 */       pageContext.setAttribute("isSubGroup", isSubGroup);
/* 2291 */       boolean isHealth = false;
/* 2292 */       boolean isAvailability = false;
/* 2293 */       if (attributeId == 18)
/*      */       {
/* 2295 */         isHealth = true;
/* 2296 */         tabs = "am.webclient.rule.action,am.webclient.rule.defAlarmRules,am.webclient.rule.depeMGroups";
/*      */       }
/*      */       else
/*      */       {
/* 2300 */         isAvailability = true;
/*      */       }
/*      */       
/* 2303 */       String tabSelected = "am.webclient.rule.action";
/* 2304 */       Cookie[] cookies = request.getCookies();
/* 2305 */       for (int i = 0; i < cookies.length; i++)
/*      */       {
/* 2307 */         if (cookies[i].getName().equals("threshold_view"))
/*      */         {
/* 2309 */           tabSelected = cookies[i].getValue();
/* 2310 */           break;
/*      */         }
/*      */       }
/*      */       
/* 2314 */       if ((isHealth) && (tabSelected.equals("am.webclient.rule.depDevice")))
/*      */       {
/* 2316 */         tabSelected = "am.webclient.rule.action";
/*      */       }
/* 2318 */       if (!com.adventnet.appmanager.util.Constants.isIt360)
/*      */       {
/*      */ 
/* 2321 */         out.write(10);
/*      */         
/* 2323 */         IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2324 */         _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2325 */         _jspx_th_c_005fif_005f0.setParent(null);
/*      */         
/* 2327 */         _jspx_th_c_005fif_005f0.setTest("${empty param.popup || param.popup == 'null' || param.popup == true}");
/* 2328 */         int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2329 */         if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */           for (;;) {
/* 2331 */             out.write("\n<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" height=\"55\" class=\"darkheaderbg\">\n\t<tr>\n");
/*      */             
/* 2333 */             if (isAvailability)
/*      */             {
/* 2335 */               out.write("\n \t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 2336 */               out.print(DBUtil.getDisplaynameforResourceID(resourceId));
/* 2337 */               out.write(32);
/* 2338 */               out.write(58);
/* 2339 */               out.write(32);
/* 2340 */               out.print(FormatUtil.getString("am.webclient.common.alarm.monitorgroup.config.availability.text"));
/* 2341 */               out.write("</span></td>\n");
/*      */             }
/*      */             else
/*      */             {
/* 2345 */               out.write("\n\t\t<td>&nbsp;<span class=\"headingboldwhite\">");
/* 2346 */               out.print(DBUtil.getDisplaynameforResourceID(resourceId));
/* 2347 */               out.write(32);
/* 2348 */               out.write(58);
/* 2349 */               out.write(32);
/* 2350 */               out.print(FormatUtil.getString("am.webclient.common.alarm.monitorgroup.config.health.text"));
/* 2351 */               out.write("</span></td>\n");
/*      */             }
/*      */             
/*      */ 
/* 2355 */             out.write("\n\n \t</tr>\n</table>\n");
/* 2356 */             int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2357 */             if (evalDoAfterBody != 2)
/*      */               break;
/*      */           }
/*      */         }
/* 2361 */         if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2362 */           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */         }
/*      */         
/* 2365 */         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2366 */         out.write(10);
/*      */       }
/*      */       
/*      */ 
/* 2370 */       out.write(10);
/*      */       
/* 2372 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2373 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2374 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2376 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayout.jsp");
/* 2377 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2378 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2380 */           out.write(10);
/* 2381 */           out.write(10);
/*      */           
/* 2383 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2384 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2385 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2387 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2389 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.configurealert.thresholdactionconfigurationtitle"));
/* 2390 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2391 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2392 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2395 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2396 */           out.write(10);
/* 2397 */           out.write(10);
/* 2398 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2400 */           out.write(10);
/* 2401 */           out.write(10);
/*      */           
/* 2403 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2404 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2405 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2407 */           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */           
/* 2409 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2410 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2411 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2412 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2413 */               out = _jspx_page_context.pushBody();
/* 2414 */               _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2415 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2418 */               out.write(10);
/* 2419 */               out.write(9);
/* 2420 */               out.write("<!-- $Id$-->\n\n\n\t\t<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n\n\t\t  <tr align=\"left\">\n\t\t\t\t<td width=\"81%\" height=\"21\" class=\"leftlinksheading\">\n\t\t\t\t\t");
/* 2421 */               out.print(FormatUtil.getString("am.webclient.configurealert.view"));
/* 2422 */               out.write("\n\t\t\t\t</td>\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t\t\t<td height=\"21\" class=\"leftlinkstd\">\n\t\t\t\t\t<a href=\"javascript://\" class=\"new-left-links\" onClick=\"MM_openBrWindow('/jsp/Popup_ThresholdDetails.jsp','ThresholdDetails','resizable=yes,scrollbars=yes,width=420,height=320')\">");
/* 2423 */               out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/* 2424 */               out.write(" </a>\n\t\t\t\t</td>\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t\t\t<td class=\"leftlinkstd\">\n\t\t\t\t\t<a href=\"javascript://\" class=\"new-left-links\" onClick=\"MM_openBrWindow('/jsp/Popup_Actions.jsp','ActionDetails','resizable=yes,scrollbars=yes,width=450,height=300')\">");
/* 2425 */               out.print(FormatUtil.getString("am.webclient.configurealert.actiondetails"));
/* 2426 */               out.write("</a>\n\t\t\t\t</td>\n\t\t  </tr>\n\n\t\t</table>\n\n\t  <br>\n\n\t\t<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n\n\t\t  <tr>\n\t\t\t\t<td width=\"81%\" height=\"21\" class=\"leftlinksheading\">");
/* 2427 */               out.print(FormatUtil.getString("am.webclient.configurealert.create"));
/* 2428 */               out.write(" </td>\n\t\t  </tr>\n\n\t\t  <tr class=\"arial10\">\n\t\t\t\t<td class=\"leftlinkstd\" height=\"21\">\n\t\t\t\t\t<a href=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2429 */               out.print(request.getParameter("haid"));
/* 2430 */               out.write("&returnpath=");
/* 2431 */               out.print(URLEncoder.encode(returnpath));
/* 2432 */               out.write("\" class=\"new-left-links\">");
/* 2433 */               out.print(FormatUtil.getString("am.webclient.common.sendemail.text"));
/* 2434 */               out.write(" </a>\n\t\t\t  \t</td>\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t\t\t<td class=\"leftlinkstd\">\n\t\t\t\t\t<a href=\"/showTile.do?TileName=.SMSActions&haid=");
/* 2435 */               out.print(request.getParameter("haid"));
/* 2436 */               out.write("&returnpath=");
/* 2437 */               out.print(URLEncoder.encode(returnpath));
/* 2438 */               out.write("\" class=\"new-left-links\">");
/* 2439 */               out.print(FormatUtil.getString("am.webclient.common.sendsms.text"));
/* 2440 */               out.write(" </a>\n\t\t\t\t</td>\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t\t\t<td class=\"leftlinkstd\">\n\t\t\t\t\t");
/* 2441 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2443 */               out.write("\n\n\t\t\t\t\t");
/*      */               
/* 2445 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2446 */               _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2447 */               _jspx_th_logic_005fnotPresent_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2449 */               _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2450 */               int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2451 */               if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                 for (;;) {
/* 2453 */                   out.write("\n\t\t\t\t\t\t<a href=\"/showTile.do?TileName=.ExecProg&haid=");
/* 2454 */                   out.print(request.getParameter("haid"));
/* 2455 */                   out.write("&returnpath=");
/* 2456 */                   out.print(URLEncoder.encode(returnpath));
/* 2457 */                   out.write("\" class=\"new-left-links\">\n\t\t\t\t\t");
/* 2458 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2459 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2463 */               if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2464 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */               }
/*      */               
/* 2467 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2468 */               out.write("\n\t\t\t  \t\t");
/* 2469 */               out.print(FormatUtil.getString("am.webclient.common.executeprogram.text"));
/* 2470 */               out.write(" </a>\n\t\t\t  \t </td>\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t\t\t<td class=\"leftlinkstd\">\n\t\t\t\t\t<a href=\"/adminAction.do?method=reloadSendTrapActionForm\" class=\"new-left-links\">");
/* 2471 */               out.print(FormatUtil.getString("am.webclient.common.sendtrap.text"));
/* 2472 */               out.write(" </a>\n\t\t\t\t</td>\n\t\t  </tr>\n\t\t  <tr >\n\t\t\t  \t<td class=\"leftlinkstd\">\n\t\t\t  \t\t<a href=\"/MBeanOperationAction.do?method=showInitialScreen&haid=");
/* 2473 */               out.print(request.getParameter("haid"));
/* 2474 */               out.write("&redirectto=");
/* 2475 */               out.print(URLEncoder.encode(returnpath));
/* 2476 */               out.write("\" class=\"new-left-links\">");
/* 2477 */               out.print(FormatUtil.getString("am.webclient.common.executembeanoperation.text"));
/* 2478 */               out.write("</a></td>\n\t\t\t\t\t");
/* 2479 */               if (!com.adventnet.appmanager.util.OEMUtil.isRemove("am.addonproducts.remove")) {
/* 2480 */                 out.write("</tr>\n\t\t \t\t\t<tr>\n\t\t   \t\t\t\t<td class=\"leftlinkstd\" height=\"21\">\n\t\t   \t\t\t\t\t<a href=\"/adminAction.do?method=showLogTicket&haid=");
/* 2481 */                 out.print(request.getParameter("haid"));
/* 2482 */                 out.write("\" class=\"new-left-links\"> ");
/* 2483 */                 out.print(FormatUtil.getString("am.webclient.common.logaticket.text"));
/* 2484 */                 out.write("</a>\n\t\t   \t\t\t\t</td>\n\t\t \t\t\t");
/*      */               }
/* 2486 */               out.write("\n\t\t  </tr>\n\n\t\t  <tr>\n\t\t\t\t<td class=\"leftlinkstd\">\n\t\t\t\t\t<a href=\"/showTile.do?TileName=.ThresholdConf&haid=");
/* 2487 */               out.print(request.getParameter("haid"));
/* 2488 */               out.write("&returnpath=");
/* 2489 */               out.print(URLEncoder.encode(returnpath));
/* 2490 */               out.write("\" class=\"new-left-links\">");
/* 2491 */               out.print(FormatUtil.getString("am.webclient.configurealert.thresholdprofile"));
/* 2492 */               out.write("</a>\n\t\t\t\t</td>\n\t\t  </tr>\n\n\t\t</table>\n\n\t\t<br>\n\n\t <table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"leftmnutables\">\n\t   <tr><td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2493 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2494 */               out.write("</td>\n    <td width=\"20%\" class=\"leftlinksheading\"><img src=\"/images/");
/* 2495 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2497 */               out.write("/img_quicknote.gif\"      hspace=\"5\"></td>\n  </tr>\n\t  <tr>\n\n    <td height=\"71\" colspan=\"2\" class=\"quicknote\">\n\t\t  ");
/*      */               
/* 2499 */               if (isHealth)
/*      */               {
/* 2501 */                 out.println(FormatUtil.getString("am.webclient.configurealert.quicknotehealth"));
/*      */ 
/*      */               }
/* 2504 */               else if (isAvailability)
/*      */               {
/* 2506 */                 out.println(FormatUtil.getString("am.webclient.configurealert.quicknoteavailability"));
/*      */               }
/*      */               else
/*      */               {
/* 2510 */                 out.println(FormatUtil.getString("am.webclient.configurealert.quicknotethreshold"));
/*      */               }
/*      */               
/* 2513 */               out.write("\n\t\t  <br>\n\t\t  * <i>");
/* 2514 */               out.print(FormatUtil.getString("am.webclient.configurealert.quicknotemessage"));
/* 2515 */               out.write("</i>\n\t\t </td>\n\t  </tr>\n\t</table>\n");
/* 2516 */               out.write(10);
/* 2517 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2518 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2521 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2522 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2525 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2526 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 2529 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2530 */           out.write(10);
/*      */           
/* 2532 */           String isPrint = request.getParameter("PRINTER_FRIENDLY");
/* 2533 */           isPrint = request.getSession().getAttribute("PRINTER_FRIENDLY") != null ? (String)request.getSession().getAttribute("PRINTER_FRIENDLY") : isPrint != null ? isPrint : "false";
/* 2534 */           request.setAttribute("PRINTER_FRIENDLY", isPrint);
/*      */           
/* 2536 */           out.write("\n\n<script>\nvar isHealth=");
/* 2537 */           out.print(isHealth);
/* 2538 */           out.write("; //No I18N\nvar tabSelcted=\"");
/* 2539 */           out.print(tabSelected);
/* 2540 */           out.write("\"; //No I18N\n\nfunction showActionArea()\n{\n\tdocument.getElementById(\"ruleArea\").style.display='none'; //No I18N\n\tdocument.getElementById(\"DependentDeviceTable\").style.display='none'; //No I18N\n\tdocument.getElementById(\"DependentMGroupsTable\").style.display='none'; //No I18N\n\tdocument.getElementById(\"actionArea\").style.display='block'; //No I18N\n\tdocument.getElementById(\"loadingg\").style.display=\"none\"; //No I18N\n\tSet_Cookie('threshold_view','am.webclient.rule.action'); //No I18N\n}\n\nfunction showRuleArea()\n{\n\tdocument.getElementById(\"DependentDeviceTable\").style.display='none'; //No I18N\n\tdocument.getElementById(\"DependentMGroupsTable\").style.display='none'; //No I18N\n\tdocument.getElementById(\"actionArea\").style.display='none'; //No I18N\n\tdocument.getElementById(\"ruleArea\").style.display='block'; //No I18N\n\tdocument.getElementById(\"loadingg\").style.display=\"none\"; //No I18N\n\tSet_Cookie('threshold_view','am.webclient.rule.defAlarmRules'); //No I18N\n}\n\nfunction showDependentDeviceArea()\n{\n\tdocument.getElementById(\"actionArea\").style.display='none'; //No I18N\n");
/* 2541 */           out.write("\tdocument.getElementById(\"ruleArea\").style.display='none'; //No I18N\n\tdocument.getElementById(\"DependentDeviceTable\").style.display='block'; //No I18N\n\tdocument.getElementById(\"DependentMGroupsTable\").style.display='none'; //No I18N\n\tdocument.getElementById(\"loadingg\").style.display=\"none\"; //No I18N\n\tSet_Cookie('threshold_view','am.webclient.rule.depDevice'); //No I18N\n}\nfunction showDependentMGroupsArea()\n{\n\tdocument.getElementById(\"actionArea\").style.display='none'; //No I18N\n\tdocument.getElementById(\"ruleArea\").style.display='none'; //No I18N\n\tdocument.getElementById(\"DependentDeviceTable\").style.display='none'; //No I18N\n\tdocument.getElementById(\"DependentMGroupsTable\").style.display='block'; //No I18N\n\tdocument.getElementById(\"loadingg\").style.display=\"none\"; //No I18N\n\tSet_Cookie('threshold_view','am.webclient.rule.depeMGroups'); //No I18N\n}\n\nfunction setValues()\n{\n\tif(tabSelcted==\"am.webclient.rule.defAlarmRules\")\n\t{\n\t\tshowRuleArea();\n\t}\n\telse if(tabSelcted==\"am.webclient.rule.depDevice\")\n\t{\n\t\tshowDependentDeviceArea();\n");
/* 2542 */           out.write("\t}\n\telse if(tabSelcted==\"am.webclient.rule.depeMGroups\")\n\t{\n\t\tshowDependentMGroupsArea();\n\t}\n\telse\n\t{\n\t\tshowActionArea();\n\t}\n\n\tif(document.alarmConfiguration.attributeSelected1!=undefined && document.alarmConfiguration.attributeSelected2!=undefined)\n\t{\n\n\t\tif(isHealth==true)\n\t\t{\n\t\t\tif(document.alarmConfiguration.attributeSelected1.length>1)\n\t\t\t{\n\t\t\t\tdocument.alarmConfiguration.attributeSelected1[1].selected=true\n\t\t\t\tdocument.alarmConfiguration.attributeSelected2[1].selected=true\n\t\t\t}\n\t\t}\n\t\telse\n\t\t{\n\t\t\tdocument.alarmConfiguration.attributeSelected1[0].selected=true\n\t\t\tdocument.alarmConfiguration.attributeSelected2[0].selected=true\n\t\t}\n\n\t}\n}\n\nfunction backToScreen()\n{\n\t");
/* 2543 */           if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2545 */           out.write("\n\twindow.location=\"");
/* 2546 */           out.print(redirectPage);
/* 2547 */           out.write("\"; //No I18N\n}\n\nfunction formReload(e)\n{\n    window.location=\"/jsp/NewThresholdConfiguration.jsp?resourceid=");
/* 2548 */           out.print(resourceId);
/* 2549 */           out.write("&attributeIDs=");
/* 2550 */           out.print(attributeids);
/* 2551 */           out.write("&attributeToSelect=\"+e.value;  //No I18N\n}\n\n\nfunction setDependentValue(dependentDevice)\n{\n\tdocument.getElementById(\"depDevice\").value=\"\"; //No I18N\n}\n\nfunction fnFormSubmit()\n{\n\n    ");
/*      */           
/* 2553 */           NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2554 */           _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2555 */           _jspx_th_logic_005fnotPresent_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2557 */           _jspx_th_logic_005fnotPresent_005f1.setRole("ADMIN,ENTERPRISEADMIN");
/* 2558 */           int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2559 */           if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */             for (;;) {
/* 2561 */               out.write("\n    ");
/*      */               
/* 2563 */               NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2564 */               _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 2565 */               _jspx_th_logic_005fnotPresent_005f2.setParent(_jspx_th_logic_005fnotPresent_005f1);
/*      */               
/* 2567 */               _jspx_th_logic_005fnotPresent_005f2.setRole("DEMO");
/* 2568 */               int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 2569 */               if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                 for (;;) {
/* 2571 */                   out.write("\n\t     alert('");
/* 2572 */                   out.print(FormatUtil.getString("am.webclient.configurealert.alertnotauthorised"));
/* 2573 */                   out.write("');//no i18n\n\t     return;\n     ");
/* 2574 */                   int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 2575 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2579 */               if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 2580 */                 this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */               }
/*      */               
/* 2583 */               this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 2584 */               out.write(10);
/* 2585 */               out.write(32);
/* 2586 */               out.write(32);
/* 2587 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2588 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2592 */           if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2593 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */           }
/*      */           
/* 2596 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2597 */           out.write("\n\n\t\tif(isHealth)\n\t\t{\n\t\t \tfrmSelectAllIncludingEmpty(document.alarmConfiguration.selectedactions_critical);\n\t \t\tfrmSelectAllIncludingEmpty(document.alarmConfiguration.selectedactions_warning);\n\t   \t \tfrmSelectAllIncludingEmpty(document.alarmConfiguration.selectedactions_clear);\n\t\t}\n\t\telse\n\t\t{\n\t\t \tfrmSelectAllIncludingEmpty(document.alarmConfiguration.selectedactions_down);\n\t \t\tfrmSelectAllIncludingEmpty(document.alarmConfiguration.selectedactions_up);\n\t\t}\n\n\t\tvar validationResult=validateMonitorCount()\n\t\tif(validationResult!='success')\n\t\t{\n\t\t\tif(validationResult=='string')\n\t\t\t{\n\t\t\t\talert('");
/* 2598 */           out.print(FormatUtil.getString("am.webclient.rule.nonnumericalert"));
/* 2599 */           out.write("');//no i18n\n\t\t\t}\n\t\t\telse if(validationResult=='negative')\n\t\t\t{\n\t\t\t\talert('");
/* 2600 */           out.print(FormatUtil.getString("am.webclient.rule.zeroconfiguration.alert"));
/* 2601 */           out.write("');//no i18n\n\t\t\t}\n\t\t\tshowRuleArea();\n\t\t\treturn;\n\t\t}\n\t\tvar validateSelectMon=validateMonitorCount1();\n\t\tif(validateSelectMon!='success'){\n\t\t\tif(validateSelectMon=='false'){\n\t\t\t\talert('");
/* 2602 */           out.print(FormatUtil.getString("am.webclient.rule.select.anyonemonitor.text"));
/* 2603 */           out.write("');\n\t\t\t}\n\t\t\treturn;\n\t\t}\n\n\t\tdocument.alarmConfiguration.submit();\n\t\t");
/* 2604 */           if (_jspx_meth_c_005fif_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2606 */           out.write("\n\n}\n\nfunction setDependentDetails(resName,resID,resType,mas)\n{\n\tdocument.getElementById(\"depDeviceName\").value=resName;\n\tdocument.getElementById(\"depDeviceId\").value=resID;\n\tdocument.getElementById(\"depDeviceType\").value=resType;\n\tdocument.getElementById(\"mas\").value=mas;\n}\n\nfunction fnAddToRightCombo(availableresource,selectedcombo)\n{\n  var count = availableresource.length;\n   for(k=0;k<parseInt(count);k++)\n   {\n       if(availableresource.options[k].selected)\n       {\n\t   value = availableresource.options[k].value; //No I18N\n\t   //alert('calling additoion');\n\t   fnAddOption(selectedcombo,value,availableresource.options[k].text); //No I18N\n       }\n   }\n   return; //No I18N\n}\n\nfunction removeConfiguration()\n{\n    ");
/*      */           
/* 2608 */           NotPresentTag _jspx_th_logic_005fnotPresent_005f3 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2609 */           _jspx_th_logic_005fnotPresent_005f3.setPageContext(_jspx_page_context);
/* 2610 */           _jspx_th_logic_005fnotPresent_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2612 */           _jspx_th_logic_005fnotPresent_005f3.setRole("ADMIN,ENTERPRISEADMIN");
/* 2613 */           int _jspx_eval_logic_005fnotPresent_005f3 = _jspx_th_logic_005fnotPresent_005f3.doStartTag();
/* 2614 */           if (_jspx_eval_logic_005fnotPresent_005f3 != 0) {
/*      */             for (;;) {
/* 2616 */               out.write("\n\n     alert('");
/* 2617 */               out.print(FormatUtil.getString("am.webclient.configurealert.alertnotauthorised"));
/* 2618 */               out.write("');//no i18n\n     return;\n\n  ");
/* 2619 */               int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f3.doAfterBody();
/* 2620 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/*      */           }
/* 2624 */           if (_jspx_th_logic_005fnotPresent_005f3.doEndTag() == 5) {
/* 2625 */             this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3); return;
/*      */           }
/*      */           
/* 2628 */           this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f3);
/* 2629 */           out.write("\n\n\tif(confirm('");
/* 2630 */           out.print(FormatUtil.getString("am.alarmconf.remove.alert"));
/* 2631 */           out.write("'))\n\t{\n\t\tdocument.alarmConfiguration.remConfiguration.value=\"true\"; //No I18N\n\t\tdocument.alarmConfiguration.submit(); //No I18N\n\t}\n\telse //No I18N\n\t{\n\t\treturn; //No I18N\n\t}\n}\n\nfunction getPopUp()\n{\n\t\tfnOpenNewWindow('/showresource.do?method=selectDependentMonitor&haid=");
/* 2632 */           out.print(resourceId);
/* 2633 */           out.write("&selectedMonitors='+encodeURIComponent(document.getElementById('selectedMonitors').value));  //No I18N\n}\n\nfunction showAllGroups()\n{\n\tvar alreadySeleDepMGroups = document.getElementById(\"selectedDepMonGroups\").value;\n\tfnOpenNewWindow('/showresource.do?method=selectDependentMGroups&haid=");
/* 2634 */           out.print(resourceId);
/* 2635 */           out.write("&selectedDepMonGroups='+encodeURIComponent(alreadySeleDepMGroups));  //No I18N\n}\nfunction validateMonitorCount()\n{\n\tvar inputElements=document.alarmConfiguration.getElementsByTagName(\"input\");\n\n\tfor(i=0;i<inputElements.length;i++)\n\t{\n\t\tif(inputElements[i].type=='text' && (inputElements[i].name).match(\"count\")!=null)\n\t\t{\n\t\t\tvar result= validateStringForAlpahbetandNegativeValues(inputElements[i].value);\n\t\t\tif(result!='success')\n\t\t\t{\n\t\t\t\tinputElements[i].focus();\n\t\t\t\treturn result;\n\t\t\t}\n\n\t\t}\n\t}\n\treturn 'success';\n}\n\nfunction validateMonitorCount1()\n{\n\tvar inputElements=document.alarmConfiguration.getElementsByTagName(\"select\");\n\n\tfor(i=0;i<inputElements.length;i++)\n\t{\n\t\tif((inputElements[i].type).match('select') && (inputElements[i].name).match(\"selectMonType\")!=null)\n\t\t{\n\t\t\tif(inputElements[i].value=='1'){\n\t\t\t\tvar countSev=(inputElements[i].name).substring(13);\n\t\t\t\tvar allSelectedMonitors=document.getElementsByName(\"selectedMonitors\"+countSev);\n\t\t\t\tif(allSelectedMonitors.length==0){\n\t\t\t\t\treturn 'false'\n\t\t\t\t}\n\t\t\t}\n");
/* 2636 */           out.write("\t\t\t\n\t\t}\n\t}\n\treturn 'success'; //NO I18N\n}\n\n</script>\n\n<body onLoad=\"setValues()\">\n\n");
/*      */           
/* 2638 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2639 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2640 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2642 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2644 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2645 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2646 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2647 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2648 */               out = _jspx_page_context.pushBody();
/* 2649 */               _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2650 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2653 */               out.write(10);
/* 2654 */               out.write(10);
/*      */               
/* 2656 */               IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2657 */               _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2658 */               _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2660 */               _jspx_th_c_005fif_005f3.setTest("${empty param.hideArea}");
/* 2661 */               int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2662 */               if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                 for (;;) {
/* 2664 */                   out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n      \t\t<td class=\"bcsign\"  height=\"22\" valign=\"top\">\n      \t\t\t<a href=\"/MyPage.do?method=viewDashBoard\" class=\"bcinactive\" >");
/* 2665 */                   out.print(FormatUtil.getString("am.webclient.hometab.text"));
/* 2666 */                   out.write("</a>\n        \t\t&gt;\n        \t\t");
/*      */                   
/* 2668 */                   ArrayList<HashMap> resnameTable = com.adventnet.appmanager.util.BreadcrumbUtil.getBCforHAPage(resourceId, new ArrayList());
/* 2669 */                   for (HashMap<String, String> resInfo : resnameTable)
/*      */                   {
/* 2671 */                     String mgName = (String)resInfo.get("DISPLAYNAME");
/* 2672 */                     String mgId = (String)resInfo.get("RESOURCEID");
/*      */                     
/* 2674 */                     out.write("\n        \t\t\t\t<a href=\"/showapplication.do?method=showApplication&haid=");
/* 2675 */                     out.print(mgId);
/* 2676 */                     out.write("\" class=\"bcinactive\" >");
/* 2677 */                     out.print(mgName);
/* 2678 */                     out.write("</a> &gt;\n        \t\t");
/*      */                   }
/*      */                   
/*      */ 
/*      */ 
/* 2683 */                   out.write("\n\n        \t\t<span class=\"bcactive\">");
/* 2684 */                   out.print(DBUtil.getDisplaynameforResourceID(resourceId));
/* 2685 */                   out.write("</span>\n        \t</td>\n    \t</tr>\n\t<tr>\n\t\t<td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n\t</tr>\n\t<tr>\n\t\t<td  height=\"2\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"9\"></td>\n\t</tr>\n\t</table>\n");
/* 2686 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2687 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2691 */               if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2692 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */               }
/*      */               
/* 2695 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2696 */               out.write("\n\n<form name=\"alarmConfiguration\" action=\"/manageApplications.do\" method=\"post\">\n<input type=\"hidden\" name=\"method\" value=\"addThresholdConfiguration\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2697 */               out.print(resourceId);
/* 2698 */               out.write("\">\n<input type=\"hidden\" name=\"attributeid\" value=\"");
/* 2699 */               out.print(attributeId);
/* 2700 */               out.write("\">\n<input type=\"hidden\" name=\"remConfiguration\" value=\"false\" >\n<input type=\"hidden\" name=\"returnPath\" value=\"");
/* 2701 */               out.print(returnpath);
/* 2702 */               out.write("\">\n<input type=\"hidden\" name=\"popup\" value='");
/* 2703 */               out.print(request.getParameter("popup"));
/* 2704 */               out.write(39);
/* 2705 */               out.write(62);
/* 2706 */               out.write(10);
/*      */               
/* 2708 */               if (isHealth)
/*      */               {
/* 2710 */                 out.write(10);
/* 2711 */                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(tabs), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.rule.action,am.webclient.rule.defAlarmRules,am.webclient.rule.depeMGroups", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("showActionArea,showRuleArea,showDependentMGroupsArea", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(tabSelected), request.getCharacterEncoding()), out, true);
/* 2712 */                 out.write(10);
/*      */               }
/*      */               else
/*      */               {
/* 2716 */                 out.write(10);
/* 2717 */                 JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(tabs), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("am.webclient.rule.action,am.webclient.rule.defAlarmRules,am.webclient.rule.depDevice,am.webclient.rule.depeMGroups", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("showActionArea,showRuleArea,showDependentDeviceArea,showDependentMGroupsArea", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(tabSelected), request.getCharacterEncoding()), out, true);
/* 2718 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/* 2722 */               out.write("\n\n<div id='actionArea' style=\"display:block\">\n<br>\n<table class=\"lrtbdarkborder\" width=\"99%\" cellspacing='0' cellpadding='0' border='0'>\n\t<tr>\n\t\t<td>\n\n\t\t\t<table width=\"100%\" cellspacing='0' cellpadding='0' border='0'>\n\t\t\t\t<tr>\n\t\t\t\t\t\t<td class='tableheadingbborder' colspan='5'>\n\t\t\t\t\t\t\t");
/* 2723 */               out.print(FormatUtil.getString("am.webclient.action.configure"));
/* 2724 */               out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */               
/* 2726 */               IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2727 */               _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2728 */               _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2730 */               _jspx_th_c_005fif_005f4.setTest("${empty param.hideArea}");
/* 2731 */               int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2732 */               if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                 for (;;) {
/* 2734 */                   out.write("\n\n\t\t\t\t<tr >\n\t\t\t\t\t<td colspan='5' class='bodytextbold' height='35'>&nbsp;\n\t\t\t\t\t\t");
/* 2735 */                   out.print(FormatUtil.getString("am.webclient.rule.confmessage"));
/* 2736 */                   out.write(" &nbsp;\n\t\t\t\t\t\t<select id=\"attributeSelected1\" name=\"attributeSelected1\" class=\"formtext\" onChange=\"formReload(this)\">\n\t\t\t\t\t\t");
/* 2737 */                   for (int i = 0; i < attributeIDs.length; i++) {
/* 2738 */                     out.write("\n\t\t\t\t\t\t\t<option value=\"");
/* 2739 */                     out.print(attributeIDs[i]);
/* 2740 */                     out.write(34);
/* 2741 */                     out.write(62);
/* 2742 */                     out.print(FormatUtil.getString(DBUtil.getAttributeDetails(Integer.parseInt(attributeIDs[i]))[1]));
/* 2743 */                     out.write("</option>\n\t\t\t\t\t\t");
/*      */                   }
/* 2745 */                   out.write("\n\t\t\t\t\t\t</select>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\n\t\t\t\t");
/* 2746 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2747 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2751 */               if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2752 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */               }
/*      */               
/* 2755 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2756 */               out.write("\n\n\t\t\t</table>\n\t\t\t<br>\n\t\t</td>\n\n\t</tr>\n\n\t<tr>\n\t\t<td>\n\t\t\t");
/* 2757 */               out.write("<!-- $Id$-->\n\n\n<table cellpadding=\"2\" cellspacing=\"2\" border=\"0\">\n\t<tr>\n\t\t  <td align=\"left\" valign=\"top\" class=\"bodytextbold\" colspan=\"5\">\n\t\t  \t");
/* 2758 */               out.print(FormatUtil.getString("am.webclient.configurealert.associateactions"));
/* 2759 */               out.write("\n\t\t  </td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td colspan=\"5\"></td>\n\t</tr>\n\t\n    <tr>\n     \t\t<td align=\"center\" class=\"bodytext\">&nbsp;</td>\n\t      <td align=\"center\" class=\"bodytextbold\">\n\t      \t\t");
/* 2760 */               out.print(FormatUtil.getString("am.webclient.configurealert.availableactions"));
/* 2761 */               out.write("\n\t      </td>\n\t      <td align=\"center\" class=\"bodytext\">&nbsp;</td>\n\t      <td align=\"center\" class=\"bodytextbold\">\n\t      \t\t");
/* 2762 */               out.print(FormatUtil.getString("am.webclient.configurealert.associatedactions"));
/* 2763 */               out.write("\n\t      </td>\n\t      ");
/*      */               
/* 2765 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2766 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2767 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/* 2768 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2769 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2771 */                   out.write("\n\t\t      ");
/*      */                   
/* 2773 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2774 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2775 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 2777 */                   _jspx_th_c_005fwhen_005f0.setTest("${param.global!='true'}");
/* 2778 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2779 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 2781 */                       out.write("\n\t\t\t      <td align=\"center\" class=\"bodytext\">\n\t\t\t      \t\t<a href=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2782 */                       out.print(request.getParameter("resourceId"));
/* 2783 */                       out.write("&returnpath=");
/* 2784 */                       out.print(URLEncoder.encode(returnpath));
/* 2785 */                       out.write("\"class=\"staticlinks\">");
/* 2786 */                       out.print(FormatUtil.getString("am.webclient.toolbar.newactionlink.text"));
/* 2787 */                       out.write("</a>\n\t\t\t      </td>\n\t\t      ");
/* 2788 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 2789 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2793 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 2794 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 2797 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 2798 */                   out.write("\n\t\t      ");
/*      */                   
/* 2800 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2801 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2802 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2803 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2804 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 2806 */                       out.write("\n\t\t\t      <td align=\"center\" class=\"bodytext\">\n\t\t\t      \t\t<a href=\"/showTile.do?TileName=.EmailActions&haid=");
/* 2807 */                       out.print(request.getParameter("resourceId"));
/* 2808 */                       out.write("&global=true&returnpath=");
/* 2809 */                       out.print(URLEncoder.encode(returnpath));
/* 2810 */                       out.write("&PRINTER_FRIENDLY=true\" class=\"staticlinks\">");
/* 2811 */                       out.print(FormatUtil.getString("am.webclient.toolbar.newactionlink.text"));
/* 2812 */                       out.write("</a>\n\t\t\t      </td>\n\t\t      ");
/* 2813 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2814 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2818 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2819 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 2822 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2823 */                   out.write("\n\t      ");
/* 2824 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2825 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2829 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2830 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 2833 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2834 */               out.write("\n    </tr>\n    \n    ");
/*      */               
/* 2836 */               boolean isDelegatedAdmin = DBUtil.isDelegatedAdmin(request.getRemoteUser());
/* 2837 */               Vector filterActionConfigID = new Vector();
/* 2838 */               String filterActionCondition = "";
/* 2839 */               if (isDelegatedAdmin)
/*      */               {
/*      */ 
/* 2842 */                 if (DBUtil.getGlobalConfigValueasBoolean("allowDAdminViewAllActions"))
/*      */                 {
/* 2844 */                   filterActionConfigID = com.manageengine.appmanager.util.DelegatedUserRoleUtil.getConfigIDsWithViewPerm(com.manageengine.appmanager.util.DelegatedUserRoleUtil.getLoginUserid(request), 2);
/*      */                 }
/*      */                 else
/*      */                 {
/* 2848 */                   filterActionConfigID = com.manageengine.appmanager.util.DelegatedUserRoleUtil.getConfigIDsOwnedByUser(request, 2);
/*      */                 }
/* 2850 */                 filterActionCondition = " and " + DBUtil.getCondition("AM_ACTIONPROFILE.ID", filterActionConfigID);
/*      */               }
/*      */               
/* 2853 */               if (isAvailability) {
/* 2854 */                 out.write("\n    \n   \t<tr>\n   \t\t<td width=\"23%\" align=\"left\" class=\"bodytext\" valign=\"top\">\n\t\t\t");
/* 2855 */                 out.print(FormatUtil.getString("am.webclient.configurealert.availabilitycritical"));
/* 2856 */                 out.write("\n      \t\t</td>\n      \t\t\n\t\t<td align=\"center\" width=\"26%\">\n\t\t\t<select STYLE=\"width:200px\" name=\"availableactions_down\" size=\"8\" multiple class=\"formtextarea\" id=\"select\">\n\t\t\t");
/* 2857 */                 out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 1 + " where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and AM_ACTIONPROFILE.TYPE !=10 and AM_ACTIONPROFILE.TYPE <> " + 18 + filterActionCondition + " order by AM_ACTIONPROFILE.NAME"));
/* 2858 */                 out.write(" \n\t\t\t</select> \n\t\t</td>\n\t\t\n\t\t<td width=\"7%\" align=\"center\" class=\"bodytext\" valign=\"top\">\n\t\t\t <table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\">\n\t          \t\t<tbody>\n\t          \n\t\t\t\t\t  <tr>\n\t\t\t\t\t\t<td align=\"center\" width=\"80%\">\n\t\t\t\t\t\t\t<input  type=\"button\" value=\">\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.availableactions_down,document.alarmConfiguration.selectedactions_down),fnRemoveFromRightCombo(document.alarmConfiguration.availableactions_down);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t  </tr>\n\n\t\t\t\t\t  <tr>\n\t\t\t\t\t\t\t<td align=\"center\">\n\t\t\t\t\t\t\t\t<input type=\"button\" value=\">>\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.availableactions_down,document.alarmConfiguration.selectedactions_down),fnRemoveAllFromCombo(document.alarmConfiguration.availableactions_down);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t  </tr>\n\t\t\t\t\t  \n\t\t\t\t\t  <tr>\n\t\t\t\t\t\t    <td align=\"center\">\n\t\t\t\t\t\t\t<input type=\"button\" value=\"<\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.selectedactions_down,document.alarmConfiguration.availableactions_down),fnRemoveFromRightCombo(document.alarmConfiguration.selectedactions_down);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n");
/* 2859 */                 out.write("\t\t\t\t\t\t    </td>\n\t\t\t\t\t  </tr>\n\t\t\t\t\t  \n\t\t\t\t\t  <tr>\n\t\t\t\t\t\t   <td align=\"center\">\n\t\t\t\t\t\t\t\t<input type=\"button\" value=\"<<\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.selectedactions_down,document.alarmConfiguration.availableactions_down),fnRemoveAllFromCombo(document.alarmConfiguration.selectedactions_down);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t\t\t\t\t\t   </td>\n\t\t\t\t\t  </tr>\n\t          \t\t</tbody>\n\t        \t</table>\n       \t\t</td>\n       \n       \t\t<td align=\"center\" class=\"bodytext\" width=\"26%\">\n\t\t\t<select STYLE=\"width:200px\" name=\"selectedactions_down\" size=\"8\" multiple class=\"formtextarea\">\n\t\t\t\t");
/* 2860 */                 out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 1 + " and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2861 */                 out.write("\n        \t\t</select>       \n       \t\t</td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n\t\n\t<tr>\n\t\n\t\t<td width=\"23%\" valign=\"top\" align=\"left\" class=\"bodytext\">\n        \t");
/* 2862 */                 out.print(FormatUtil.getString("am.webclient.configurealert.availabilityclear"));
/* 2863 */                 out.write("\n      \t</td>\t\n      \t\n\t\t<td align=\"center\" width=\"26%\" align=\"right\">\n\t\t\t<select STYLE=\"width:200px\" name=\"availableactions_up\" size=\"8\" multiple class=\"formtextarea\" id=\"select\">\n\t\t\t");
/* 2864 */                 out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 5 + " where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service'  and AM_ACTIONPROFILE.TYPE !=10 and AM_ACTIONPROFILE.TYPE <> " + 18 + filterActionCondition + " order by AM_ACTIONPROFILE.NAME"));
/* 2865 */                 out.write(" \n\t\t\t</select> \n\t\t</td>\n\n\t\t<td width=\"7%\" align=\"center\" class=\"bodytext\">\n\t\t\t <table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\">\n\t          <tbody>\n\t\t          <tr>\n\t\t            \t<td align=\"center\">\n\t\t            \t\t<input type=\"button\" value=\">\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.availableactions_up,document.alarmConfiguration.selectedactions_up),fnRemoveFromRightCombo(document.alarmConfiguration.availableactions_up);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t            \t</td>\n\t\t          </tr>\n\t\t          \n\t\t          \n\t\t          <tr>\n\t\t          \t\t<td align=\"center\">\n\t\t          \t\t\t<input type=\"button\" value=\">>\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.availableactions_up,document.alarmConfiguration.selectedactions_up),fnRemoveAllFromCombo(document.alarmConfiguration.availableactions_up);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t          \t\t</td>\n\t\t          </tr>\n\t\t         \n\t\t          <tr>\n\t\t\t            <td align=\"center\">\n");
/* 2866 */                 out.write("\t\t\t            \t<input type=\"button\" value=\"<\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.selectedactions_up,document.alarmConfiguration.availableactions_up),fnRemoveFromRightCombo(document.alarmConfiguration.selectedactions_up);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t\t\t            </td>\n\t\t          </tr>\n\t\t  \t\t\n\t          \t<tr>\n\t                   <td align=\"center\">\n\t                 \t\t<input type=\"button\" value=\"<<\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.selectedactions_up,document.alarmConfiguration.availableactions_up),fnRemoveAllFromCombo(document.alarmConfiguration.selectedactions_up);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t                   </td>\n\t          \t</tr>\n\t          </tbody>\n\t        </table>\n       \t\t</td>\n\t\t\n\t\n\t\t<td align=\"center\" class=\"bodytext\" width=\"26%\">\n\t\t\t<select STYLE=\"width:200px\" name=\"selectedactions_up\" size=\"8\" multiple class=\"formtextarea\">\n          \t\t\t");
/* 2867 */                 out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 5 + " and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2868 */                 out.write("\n        \t\t</select>       \n        \t</td>\n\t</tr>\n\t\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n\t");
/*      */               } else {
/* 2870 */                 out.write("\n\t<tr>\n\t\t<td width=\"23%\" valign=\"top\" align=\"left\" class=\"bodytext\">\n        \t");
/* 2871 */                 out.print(FormatUtil.getString("am.webclient.configurealert.healthcritical"));
/* 2872 */                 out.write("\n      \t</td>\n      \t\n \t\t<td align=\"center\" width=\"26%\" align=\"right\">\n\t\t\t<select STYLE=\"width:200px\" name=\"availableactions_critical\" size=\"8\" multiple class=\"formtextarea\" id=\"select\">\n\t\t\t");
/* 2873 */                 out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 1 + " where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service'  and AM_ACTIONPROFILE.TYPE !=10 and AM_ACTIONPROFILE.TYPE <> " + 18 + filterActionCondition + " order by AM_ACTIONPROFILE.NAME"));
/* 2874 */                 out.write(" \n\t\t\t</select> \n\t\t</td>\n\n\t\t<td width=\"7%\" align=\"center\" class=\"bodytext\">\n\t\t\t <table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\">\n\t          <tbody>\n\t\t          <tr>\n\t\t            \t<td align=\"center\">\n\t\t            \t\t<input type=\"button\" value=\">\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.availableactions_critical,document.alarmConfiguration.selectedactions_critical),fnRemoveFromRightCombo(document.alarmConfiguration.availableactions_critical);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t            \t</td>\n\t\t          </tr>\n\t\t          \n\t\t         \n\t\t          <tr>\n\t\t          \t\t<td align=\"center\">\n\t\t          \t\t\t<input type=\"button\" value=\">>\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.availableactions_critical,document.alarmConfiguration.selectedactions_critical),fnRemoveAllFromCombo(document.alarmConfiguration.availableactions_critical);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t          \t\t</td>\n\t\t          </tr>\n\t\t        \n\t\t          <tr>\n");
/* 2875 */                 out.write("\t\t\t            <td align=\"center\">\n\t\t\t            \t<input type=\"button\" value=\"<\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.selectedactions_critical,document.alarmConfiguration.availableactions_critical),fnRemoveFromRightCombo(document.alarmConfiguration.selectedactions_critical);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t\t\t            </td>\n\t\t          </tr>\n\t\t  \t\t\n\t          \t<tr>\n\t                   <td align=\"center\">\n\t                 \t\t<input type=\"button\" value=\"<<\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.selectedactions_critical,document.alarmConfiguration.availableactions_critical),fnRemoveAllFromCombo(document.alarmConfiguration.selectedactions_critical);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t                   </td>\n\t          \t</tr>\n\t          </tbody>\n\t        </table>\n       </td>\n\n\t\t<td align=\"center\" class=\"bodytext\" width=\"26%\">\n\t\t\t<select STYLE=\"width:200px\" name=\"selectedactions_critical\" size=\"8\" multiple class=\"formtextarea\">\n");
/* 2876 */                 out.write("          \t\t");
/* 2877 */                 out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 1 + " and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2878 */                 out.write("\n        \t</select>       \n        </td>\n     </tr>\t\n     \n     <tr>\n     \t<td colspan=\"5\">&nbsp;</td>\n     </tr>\n     \n     <tr>\n     \t<td width=\"23%\" valign=\"top\" align=\"left\" class=\"bodytext\">\n        \t");
/* 2879 */                 out.print(FormatUtil.getString("am.webclient.configurealert.healthwarning"));
/* 2880 */                 out.write("\n      \t</td>\n  \t\t<td align=\"center\" width=\"26%\" align=\"right\">\n\t\t\t<select STYLE=\"width:200px\" name=\"availableactions_warning\" size=\"8\" multiple class=\"formtextarea\" id=\"select\">\n\t\t\t");
/* 2881 */                 out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 4 + " where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and AM_ACTIONPROFILE.TYPE !=10 and AM_ACTIONPROFILE.TYPE <> " + 18 + filterActionCondition + " order by AM_ACTIONPROFILE.NAME"));
/* 2882 */                 out.write(" \n\t\t\t</select> \n\t\t</td>\n \n \t\t<td width=\"7%\" align=\"center\" class=\"bodytext\">\n\t\t\t <table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\">\n\t          <tbody>\n\t\t          <tr>\n\t\t            \t<td align=\"center\">\n\t\t            \t\t<input type=\"button\" value=\">\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.availableactions_warning,document.alarmConfiguration.selectedactions_warning),fnRemoveFromRightCombo(document.alarmConfiguration.availableactions_warning);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t            \t</td>\n\t\t          </tr>\n\t\t          \n\t\t          <tr>\n\t\t          \t\t<td align=\"center\">\n\t\t          \t\t\t<input type=\"button\" value=\">>\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.availableactions_warning,document.alarmConfiguration.selectedactions_warning),fnRemoveAllFromCombo(document.alarmConfiguration.availableactions_warning);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t          \t\t</td>\n\t\t          </tr>\n\n\t\t          <tr>\n\t\t\t            <td align=\"center\">\n");
/* 2883 */                 out.write("\t\t\t            \t<input type=\"button\" value=\"<\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.selectedactions_warning,document.alarmConfiguration.availableactions_warning),fnRemoveFromRightCombo(document.alarmConfiguration.selectedactions_warning);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t\t\t            </td>\n\t\t          </tr>\n\n\t          \t<tr>\n\t                   <td align=\"center\">\n\t                 \t\t<input type=\"button\" value=\"<<\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.selectedactions_warning,document.alarmConfiguration.availableactions_warning),fnRemoveAllFromCombo(document.alarmConfiguration.selectedactions_warning);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t                   </td>\n\t          \t</tr>\n\t          </tbody>\n\t        </table>\n       </td>\n \n     \n \t\t<td align=\"center\" class=\"bodytext\" width=\"26%\">\n\t\t\t<select STYLE=\"width:200px\" name=\"selectedactions_warning\" size=\"8\" multiple class=\"formtextarea\">\n          \t\t");
/* 2884 */                 out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 4 + " and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2885 */                 out.write("\n        \t</select>       \n        </td>\n     \n     </tr>\n     \n     <tr>\n     \t<td colspan=\"5\">&nbsp;</td>\n     </tr>\n     \n     \n     <tr>\n \n \t\t<td width=\"23%\" valign=\"top\" align=\"left\" class=\"bodytext\">\n        \t");
/* 2886 */                 out.print(FormatUtil.getString("am.webclient.configurealert.healthclear"));
/* 2887 */                 out.write("\n\t    </td>    \n     \n  \t\t<td align=\"center\" width=\"26%\" align=\"right\">\n\t\t\t<select STYLE=\"width:200px\" name=\"availableactions_clear\" size=\"8\" multiple class=\"formtextarea\" id=\"select\">\n\t\t\t");
/* 2888 */                 out.print(getOptions("AM_ACTIONPROFILE.ID", "AM_ACTIONPROFILE.NAME,AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE", false, "left outer join AM_ATTRIBUTEACTIONMAPPER on AM_ATTRIBUTEACTIONMAPPER.ACTIONID=AM_ACTIONPROFILE.ID and AM_ATTRIBUTEACTIONMAPPER.ID =" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 5 + " where  AM_ACTIONPROFILE.NAME !='Marker'and ACTIONID is null and AM_ACTIONPROFILE.NAME !='Restart The Service' and AM_ACTIONPROFILE.TYPE !=10 and AM_ACTIONPROFILE.TYPE <> " + 18 + filterActionCondition + " order by AM_ACTIONPROFILE.NAME"));
/* 2889 */                 out.write(" \n\t\t\t</select> \n\t\t</td>\n\n \t\t<td width=\"7%\" align=\"center\" class=\"bodytext\">\n\t\t\t <table width=\"100%\" cellspacing=\"10\" cellpadding=\"0\" border=\"0\">\n\t          <tbody>\n\t\t          <tr>\n\t\t            \t<td align=\"center\">\n\t\t            \t\t<input type=\"button\" value=\">\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.availableactions_clear,document.alarmConfiguration.selectedactions_clear),fnRemoveFromRightCombo(document.alarmConfiguration.availableactions_clear);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t            \t</td>\n\t\t          </tr>\n\t\t          \n\t\t          <tr>\n\t\t          \t\t<td align=\"center\">\n\t\t          \t\t\t<input type=\"button\" value=\">>\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.availableactions_clear,document.alarmConfiguration.selectedactions_clear),fnRemoveAllFromCombo(document.alarmConfiguration.availableactions_clear);\" class=\"action-buttons\" name=\"AddButton2\"/>\n\t\t          \t\t</td>\n\t\t          </tr>\n\n\t\t          <tr>\n\t\t\t            <td align=\"center\">\n\t\t\t            \t<input type=\"button\" value=\"<\" onclick=\"javascript:fnAddToRightCombo(document.alarmConfiguration.selectedactions_clear,document.alarmConfiguration.availableactions_clear),fnRemoveFromRightCombo(document.alarmConfiguration.selectedactions_clear);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n");
/* 2890 */                 out.write("\t\t\t            </td>\n\t\t          </tr>\n\n\t          \t<tr>\n\t                   <td align=\"center\">\n\t                 \t\t<input type=\"button\" value=\"<<\" onclick=\"javascript:fnAddAllFromCombo(document.alarmConfiguration.selectedactions_clear,document.alarmConfiguration.availableactions_clear),fnRemoveAllFromCombo(document.alarmConfiguration.selectedactions_clear);\" class=\"action-buttons\" name=\"RemoveButton2\"/>\n\t                   </td>\n\t          \t</tr>\n\t          </tbody>\n\t        </table>\n       </td>\n \n     \n \t\t<td align=\"center\" class=\"bodytext\" width=\"26%\">\n\t\t\t<select STYLE=\"width:200px\" name=\"selectedactions_clear\" size=\"8\" multiple class=\"formtextarea\">\n          \t\t");
/* 2891 */                 out.print(getOptions("AM_ATTRIBUTEACTIONMAPPER.ACTIONID", "AM_ACTIONPROFILE.NAME", "AM_ACTIONPROFILE,AM_ATTRIBUTEACTIONMAPPER", false, "where  AM_ATTRIBUTEACTIONMAPPER.ID=" + resourceId + " and AM_ATTRIBUTEACTIONMAPPER.ATTRIBUTE =" + attributeId + " and AM_ATTRIBUTEACTIONMAPPER.SEVERITY=" + 5 + " and AM_ACTIONPROFILE.ID=AM_ATTRIBUTEACTIONMAPPER.ACTIONID order by AM_ACTIONPROFILE.NAME"));
/* 2892 */                 out.write("\n        \t</select>       \n        </td>\n     \n     </tr>\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n     \n\t");
/*      */               }
/* 2894 */               out.write("\n</table>\n");
/* 2895 */               out.write("\n\t\t</td>\n\t</tr>\n\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n      \t<td height=\"29\" align=\"center\" class=\"tablebottom\">\n        \t<input type=\"button\" onClick=\"javascript:fnFormSubmit()\" value='");
/* 2896 */               out.print(FormatUtil.getString("am.webclient.rule.conf.saveall"));
/* 2897 */               out.write("' class=\"buttons\" name=\"button\" title=\"submits values in all 3 tabs\"/>&nbsp;\n        \t<input type=\"button\" onClick=\"javascript:backToScreen();\" value='");
/* 2898 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 2899 */               out.write("' class=\"buttons\" name=\"button3\"/>&nbsp;\n       \t \t<input type=\"button\" onClick=\"javascript:removeConfiguration();\" value='");
/* 2900 */               out.print(FormatUtil.getString("am.webclient.rule.conf.removeall"));
/* 2901 */               out.write("' class=\"buttons\" name=\"button4\"/>\n      \t</td>\n    </tr>\n\n</table>\n</div>\n\n<div id='ruleArea' style=\"display:none\">\n<br>\n<table class=\"lrtbdarkborder\" width=\"99%\" cellspacing='0' cellpadding='0' border='0'>\n\t<tr>\n\t\t<td colspan='5'>\n\t\t\t<table width=\"100%\" cellspacing='0' cellpadding='0' border='0'>\n\t\t\t\t<tr>\n\t\t\t\t\t\t<td class='tableheadingbborder' colspan='5'>\n\t\t\t\t\t\t\t");
/* 2902 */               out.print(FormatUtil.getString("am.webclient.alarmrule.configure"));
/* 2903 */               out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\n\t\t\t\t");
/*      */               
/* 2905 */               IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2906 */               _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2907 */               _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 2909 */               _jspx_th_c_005fif_005f5.setTest("${empty param.hideArea}");
/* 2910 */               int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2911 */               if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                 for (;;) {
/* 2913 */                   out.write("\n\n\t\t\t\t<tr >\n\t\t\t\t\t\t<td colspan='5' class='bodytextbold' height='35'>\n\t\t\t\t\t\t\t &nbsp;");
/* 2914 */                   out.print(FormatUtil.getString("am.webclient.rule.confmessage"));
/* 2915 */                   out.write(" &nbsp;\n\t\t\t\t\t\t\t<select id=\"attributeSelected2\" name=\"attributeSelected2\" class=\"formtext\"\tonChange=\"formReload(this)\">\n\t\t\t\t\t\t\t\t");
/* 2916 */                   for (int i = 0; i < attributeIDs.length; i++) {
/* 2917 */                     out.write("\n\t\t\t\t\t\t\t\t");
/* 2918 */                     out.print(attributeIDs[i]);
/* 2919 */                     out.write("\n\t\t\t\t\t\t\t\t<option value=\"");
/* 2920 */                     out.print(attributeIDs[i]);
/* 2921 */                     out.write(34);
/* 2922 */                     out.write(62);
/* 2923 */                     out.print(FormatUtil.getString(DBUtil.getAttributeDetails(Integer.parseInt(attributeIDs[i]))[1]));
/* 2924 */                     out.write("</option>\n\t\t\t\t\t\t\t\t");
/*      */                   }
/* 2926 */                   out.write("\n\t\t\t\t\t\t\t</select>\n\t\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\n\t\t\t\t");
/* 2927 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2928 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2932 */               if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2933 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */               }
/*      */               
/* 2936 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2937 */               out.write("\n\n\t\t\t</table>\n\t\t</td>\n\t</tr>\n\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n\n\t");
/* 2938 */               if (isAvailability) {
/* 2939 */                 out.write("\n\t<tr>\n\t\t  <td align=\"left\"  class=\"bodytextbold\" width=\"20%\">\n\t\t  \t");
/*      */                 
/* 2941 */                 ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2942 */                 _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2943 */                 _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/* 2944 */                 int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2945 */                 if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                   for (;;) {
/* 2947 */                     out.write("\n\t            ");
/*      */                     
/* 2949 */                     WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2950 */                     _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2951 */                     _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                     
/* 2953 */                     _jspx_th_c_005fwhen_005f1.setTest("${isSubGroup=='true'}");
/* 2954 */                     int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2955 */                     if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                       for (;;) {
/* 2957 */                         out.write("\n\t\t  \t \t\t&nbsp;");
/* 2958 */                         out.print(FormatUtil.getString("am.webclient.rule.SGDownMessage"));
/* 2959 */                         out.write("\n\t\t  \t    ");
/* 2960 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2961 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2965 */                     if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2966 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                     }
/*      */                     
/* 2969 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2970 */                     out.write("\n\t\t  \t    ");
/*      */                     
/* 2972 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2973 */                     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2974 */                     _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2975 */                     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2976 */                     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                       for (;;) {
/* 2978 */                         out.write("\n\t\t  \t    \t&nbsp;");
/* 2979 */                         out.print(FormatUtil.getString("am.webclient.rule.MGDownMessage"));
/* 2980 */                         out.write("\n\t\t  \t    ");
/* 2981 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2982 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2986 */                     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2987 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                     }
/*      */                     
/* 2990 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2991 */                     out.write("\n\t\t  \t");
/* 2992 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2993 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2997 */                 if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2998 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                 }
/*      */                 
/* 3001 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 3002 */                 out.write("\n\t\t  </td>\n\t</tr>\n\t<tr>\n\n\t\t<td colspan='5'><span>             </span>\n\t\t  \t");
/* 3003 */                 JspRuntimeLibrary.include(request, response, "/jsp/RulesConstructor.jsp" + ("/jsp/RulesConstructor.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceId), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("severity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("1", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("attribute", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("1", request.getCharacterEncoding()), out, false);
/* 3004 */                 out.write("\n\t\t</td>\n\t</tr>\n\n\n\n\t");
/*      */               } else {
/* 3006 */                 out.write("\n\t<tr>\n\t\t  <td align=\"left\"  class=\"bodytextbold\" width=\"20%\">\n\t\t  \t");
/*      */                 
/* 3008 */                 ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3009 */                 _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3010 */                 _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/* 3011 */                 int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3012 */                 if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                   for (;;) {
/* 3014 */                     out.write("\n\t            ");
/*      */                     
/* 3016 */                     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3017 */                     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3018 */                     _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                     
/* 3020 */                     _jspx_th_c_005fwhen_005f2.setTest("${isSubGroup=='true'}");
/* 3021 */                     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3022 */                     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                       for (;;) {
/* 3024 */                         out.write("\n\t\t  \t \t\t&nbsp;");
/* 3025 */                         out.print(FormatUtil.getString("am.webclient.rule.SGCriticalMessage"));
/* 3026 */                         out.write("\n\t\t  \t    ");
/* 3027 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3028 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3032 */                     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3033 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                     }
/*      */                     
/* 3036 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3037 */                     out.write("\n\t\t  \t    ");
/*      */                     
/* 3039 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3040 */                     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3041 */                     _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 3042 */                     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3043 */                     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                       for (;;) {
/* 3045 */                         out.write("\n\t\t  \t    \t&nbsp;");
/* 3046 */                         out.print(FormatUtil.getString("am.webclient.rule.MGCriticalMessage"));
/* 3047 */                         out.write("\n\t\t  \t    ");
/* 3048 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3049 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3053 */                     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3054 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                     }
/*      */                     
/* 3057 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3058 */                     out.write("\n\t\t  \t");
/* 3059 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3060 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3064 */                 if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3065 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                 }
/*      */                 
/* 3068 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3069 */                 out.write("\n\t\t  </td>\n\t</tr>\n\n\t<tr>\n\t\t<td colspan='5'>\n\t\t  \t");
/* 3070 */                 JspRuntimeLibrary.include(request, response, "/jsp/RulesConstructor.jsp" + ("/jsp/RulesConstructor.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceId), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("severity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("1", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("attribute", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("2", request.getCharacterEncoding()), out, false);
/* 3071 */                 out.write("\n\t\t</td>\n\t</tr>\n\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n\t\t  <td align=\"left\"  class=\"bodytextbold\" width=\"20%\">\n\t\t  \t");
/*      */                 
/* 3073 */                 ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3074 */                 _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 3075 */                 _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/* 3076 */                 int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 3077 */                 if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                   for (;;) {
/* 3079 */                     out.write("\n\t            ");
/*      */                     
/* 3081 */                     WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3082 */                     _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 3083 */                     _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                     
/* 3085 */                     _jspx_th_c_005fwhen_005f3.setTest("${isSubGroup=='true'}");
/* 3086 */                     int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 3087 */                     if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                       for (;;) {
/* 3089 */                         out.write("\n\t\t  \t \t\t&nbsp;");
/* 3090 */                         out.print(FormatUtil.getString("am.webclient.rule.SGWarningMessage"));
/* 3091 */                         out.write("\n\t\t  \t    ");
/* 3092 */                         int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 3093 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3097 */                     if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 3098 */                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3); return;
/*      */                     }
/*      */                     
/* 3101 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 3102 */                     out.write("\n\t\t  \t    ");
/*      */                     
/* 3104 */                     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3105 */                     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3106 */                     _jspx_th_c_005fotherwise_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/* 3107 */                     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3108 */                     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */                       for (;;) {
/* 3110 */                         out.write("\n\t\t  \t    \t&nbsp;");
/* 3111 */                         out.print(FormatUtil.getString("am.webclient.rule.MGWarningMessage"));
/* 3112 */                         out.write("\n\t\t  \t    ");
/* 3113 */                         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3114 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3118 */                     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3119 */                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3); return;
/*      */                     }
/*      */                     
/* 3122 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3123 */                     out.write("\n\t\t  \t");
/* 3124 */                     int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 3125 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3129 */                 if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 3130 */                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3); return;
/*      */                 }
/*      */                 
/* 3133 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 3134 */                 out.write("\n\t\t  </td>\n\t</tr>\n\n\t<tr>\n\t\t<td colspan='5'>\n\t\t  \t");
/* 3135 */                 JspRuntimeLibrary.include(request, response, "/jsp/RulesConstructor.jsp" + ("/jsp/RulesConstructor.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("haid", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(resourceId), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("severity", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("4", request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("attribute", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode("2", request.getCharacterEncoding()), out, false);
/* 3136 */                 out.write("\n\t\t</td>\n\t</tr>\n\n\t");
/*      */               }
/* 3138 */               out.write("\n\n\t<tr>\n\t\t<td colspan=\"5\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n      \t<td height=\"29\" align=\"center\" class=\"tablebottom\" colspan='5'>\n        \t<input type=\"button\" onClick=\"javascript:fnFormSubmit()\" value='");
/* 3139 */               out.print(FormatUtil.getString("am.webclient.rule.conf.saveall"));
/* 3140 */               out.write("' class=\"buttons\" name=\"button\" title=\"submits values in all 3 tabs\"/>&nbsp;\n        \t<input type=\"button\" onClick=\"javascript:backToScreen();\" value='");
/* 3141 */               out.print(FormatUtil.getString("webclient.common.printview.button.close"));
/* 3142 */               out.write("' class=\"buttons\" name=\"button3\"/>&nbsp;\n       \t \t<input type=\"button\" onClick=\"javascript:removeConfiguration();\" value='");
/* 3143 */               out.print(FormatUtil.getString("am.webclient.rule.conf.removeall"));
/* 3144 */               out.write("' class=\"buttons\" name=\"button4\"/>\n     \t</td>\n    </tr>\n\n</table>\n</div>\n\n<div style=\"padding-top:20px;\"></div>\n");
/* 3145 */               JspRuntimeLibrary.include(request, response, "/jsp/DependentDeviceListView.jsp", out, false);
/* 3146 */               out.write(10);
/* 3147 */               JspRuntimeLibrary.include(request, response, "/jsp/DependentMGroupsListView.jsp", out, false);
/* 3148 */               out.write("\n</form>\n\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n\t<tr>\n\t\t<td class=\"helpCardHdrTopLeft\" />\n\t\t<td class=\"helpCardHdrTopBg\">\n\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\n\t\t\t<tr>\n\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardContentBg\">\n\t\t\t\t<span class=\"helpHdrTxt\">");
/* 3149 */               out.print(FormatUtil.getString("am.mypage.healp.card.text"));
/* 3150 */               out.write("</span>\n\t\t\t\t<img width=\"19\" height=\"16\" align=\"texttop\" src=\"/images/helpCue.gif\" /></td>\n\t\t\t\t<td valign=\"middle\" align=\"left\" class=\"helpCardHdrRightEar\">&nbsp;</td>\n\t\t\t\t<td valign=\"middle\" align=\"left\">&nbsp;</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t<td class=\"helpCardHdrRightTop\">&nbsp;</td>\n\t</tr>\n\n\t<tr>\n\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t\t<td valign=\"top\">\n\n\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"2\" border=\"0\"\talign=\"center\">\n\t\t\t<tr>\n\t\t\t\t<td style=\"padding-top: 10px;\" class=\"boxedContent\">\n\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"\talign=\"center\">\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"\talign=\"center\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopLeft\" />\n\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopBg\" />\n\t\t\t\t\t\t\t\t<td class=\"hCardInnerTopRight\" />\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t\t\t\t\t\t");
/* 3151 */               if (isHealth) {
/* 3152 */                 if (!isdelegatedAdmin) {
/* 3153 */                   out.write("\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg product-help\">");
/* 3154 */                   out.print(FormatUtil.getString("am.globalHA.conf.Action.text"));
/* 3155 */                   out.write("<br>");
/* 3156 */                   out.print(FormatUtil.getString("am.globalHA.conf.ruleDef.text"));
/* 3157 */                   out.write("<br>");
/* 3158 */                   out.print(FormatUtil.getString("am.globalHA.conf.depen.monitorGroup.text"));
/* 3159 */                   out.write("<br><br></td>\n\t\t\t\t\t\t\t\t\t");
/*      */                 } else {
/* 3161 */                   out.write("\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg product-help\">");
/* 3162 */                   out.print(FormatUtil.getString("am.globalHA.conf.Action1.text"));
/* 3163 */                   out.write("<br>");
/* 3164 */                   out.print(FormatUtil.getString("am.globalHA.conf.ruleDef.text"));
/* 3165 */                   out.write("<br>");
/* 3166 */                   out.print(FormatUtil.getString("am.globalHA.conf.depen.monitorGroup.text"));
/* 3167 */                   out.write("<br><br></td>\n\t\t\t\t\t\t\t\t\t");
/*      */                 }
/*      */               }
/* 3170 */               else if (!isdelegatedAdmin) {
/* 3171 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg product-help\">");
/* 3172 */                 out.print(FormatUtil.getString("am.globalHA.conf.Action.text"));
/* 3173 */                 out.write("<br>");
/* 3174 */                 out.print(FormatUtil.getString("am.globalHA.conf.ruleDef.text"));
/* 3175 */                 if (!EnterpriseUtil.isAdminServer()) {
/* 3176 */                   out.write("<br>");
/* 3177 */                   out.print(FormatUtil.getString("am.globalHA.conf.depDevice.monitorGroup.text"));
/* 3178 */                   out.write("<br>");
/* 3179 */                   out.print(FormatUtil.getString("am.globalHA.conf.depen.monitorGroup.text"));
/*      */                 }
/* 3181 */                 out.write("<br></td>\n\t\t\t\t\t\t\t\t\t");
/*      */               } else {
/* 3183 */                 out.write("\n\t\t\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg product-help\">");
/* 3184 */                 out.print(FormatUtil.getString("am.globalHA.conf.Action1.text"));
/* 3185 */                 out.write("<br>");
/* 3186 */                 out.print(FormatUtil.getString("am.globalHA.conf.ruleDef.text"));
/* 3187 */                 if (!EnterpriseUtil.isAdminServer()) {
/* 3188 */                   out.write("<br>");
/* 3189 */                   out.print(FormatUtil.getString("am.globalHA.conf.depDevice.monitorGroup.text"));
/* 3190 */                   out.write("<br>");
/* 3191 */                   out.print(FormatUtil.getString("am.globalHA.conf.depen.monitorGroup.text"));
/*      */                 }
/* 3193 */                 out.write("<br></td>\n\t\t\t\t\t\t\t\t\t");
/*      */               }
/*      */               
/* 3196 */               out.write("\n\t\t\t\t\t\t\t\t<td class=\"hCardInnerBoxBg\">&nbsp;</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t\t</td>\n\t\t<td style=\"width: 12px;\" class=\"boxedContent\">&nbsp;</td>\n\n\t</tr>\n\t<tr>\n\t\t<td class=\"helpCardMainBtmLeft\" />\n\t\t<td class=\"helpCardMainBtmBg\" />\n\t\t<td class=\"helpCardMainBtmRight\" />\n\t</tr>\n</table>\n");
/* 3197 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 3198 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3201 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 3202 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3205 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3206 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 3209 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 3210 */           out.write(10);
/* 3211 */           out.write(32);
/* 3212 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3214 */           out.write(32);
/* 3215 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3216 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3220 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3221 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3224 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3225 */         out.write("\n\n </body>\n\n\n");
/*      */       }
/* 3227 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3228 */         out = _jspx_out;
/* 3229 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3230 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3231 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3234 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3240 */     PageContext pageContext = _jspx_page_context;
/* 3241 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3243 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3244 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3245 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3247 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3249 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 3250 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3251 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3252 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3253 */       return true;
/*      */     }
/* 3255 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3256 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3261 */     PageContext pageContext = _jspx_page_context;
/* 3262 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3264 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3265 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3266 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3268 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3269 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3270 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3272 */         out.write("\n\t\t\t\t\t\t<a href=\"alertUser()\" class=\"new-left-links\">\n\t\t\t\t\t");
/* 3273 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3274 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3278 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3279 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3280 */       return true;
/*      */     }
/* 3282 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3283 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3288 */     PageContext pageContext = _jspx_page_context;
/* 3289 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3291 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3292 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3293 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3295 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3297 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3298 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3299 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3300 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3301 */       return true;
/*      */     }
/* 3303 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3304 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3309 */     PageContext pageContext = _jspx_page_context;
/* 3310 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3312 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3313 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3314 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3316 */     _jspx_th_c_005fif_005f1.setTest("${PRINTER_FRIENDLY=='true'}");
/* 3317 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3318 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3320 */         out.write("\n\t\twindow.close();\n\t\treturn;\n\t");
/* 3321 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3322 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3326 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3327 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3328 */       return true;
/*      */     }
/* 3330 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3331 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3336 */     PageContext pageContext = _jspx_page_context;
/* 3337 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3339 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3340 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3341 */     _jspx_th_c_005fif_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3343 */     _jspx_th_c_005fif_005f2.setTest("${PRINTER_FRIENDLY=='true'}");
/* 3344 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3345 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3347 */         out.write("\n\t\t\tif(window.opener.document.forms[1].resourceid!=null)\n\t\t\t{\n\t\t\t\tif(typeof(window.opener.document.forms[1].resourceid.selectedIndex) ==\"undefined\")\n\t\t\t\t{\n\t\t\t\t\t window.opener.location.reload();\n\t\t\t\t}\n\t\t\t\telse if(window.opener.document.forms[1].resourceid.selectedIndex!='0')\n\t\t\t\t{\n\t\t\t\t\twindow.opener.onfnSubmit(window.opener.document.forms[1].resourceid);\n\t\t\t\t}\n\t\t\t\telse\n\t\t\t\t{\n\t\t\t\t\twindow.opener.onfnSubmit(window.opener.document.forms[2].haid);\n\t\t\t\t}\n\t\t\t}\n\t\t\telse\n\t\t\t{\n\t\t\t\twindow.opener.location.reload();\n\t\t\t}\n\t\t");
/* 3348 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3349 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3353 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3354 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3355 */       return true;
/*      */     }
/* 3357 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3363 */     PageContext pageContext = _jspx_page_context;
/* 3364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3366 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3367 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3368 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3370 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3372 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3373 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3374 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3375 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3376 */       return true;
/*      */     }
/* 3378 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3379 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\NewThresholdConfiguration_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */