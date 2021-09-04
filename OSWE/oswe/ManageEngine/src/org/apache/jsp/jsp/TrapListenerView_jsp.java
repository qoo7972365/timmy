/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
/*      */ import java.sql.ResultSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
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
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class TrapListenerView_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   45 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   48 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   49 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   50 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   57 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   62 */     ArrayList list = null;
/*   63 */     StringBuffer sbf = new StringBuffer();
/*   64 */     ManagedApplication mo = new ManagedApplication();
/*   65 */     if (distinct)
/*      */     {
/*   67 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   71 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   74 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   76 */       ArrayList row = (ArrayList)list.get(i);
/*   77 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   78 */       if (distinct) {
/*   79 */         sbf.append(row.get(0));
/*      */       } else
/*   81 */         sbf.append(row.get(1));
/*   82 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   85 */     return sbf.toString(); }
/*      */   
/*   87 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   90 */     if (severity == null)
/*      */     {
/*   92 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   94 */     if (severity.equals("5"))
/*      */     {
/*   96 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*   98 */     if (severity.equals("1"))
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  105 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  112 */     if (severity == null)
/*      */     {
/*  114 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  116 */     if (severity.equals("1"))
/*      */     {
/*  118 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  120 */     if (severity.equals("4"))
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("5"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  131 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  137 */     if (severity == null)
/*      */     {
/*  139 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  141 */     if (severity.equals("5"))
/*      */     {
/*  143 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  145 */     if (severity.equals("1"))
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  151 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  157 */     if (severity == null)
/*      */     {
/*  159 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  161 */     if (severity.equals("1"))
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  165 */     if (severity.equals("4"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  169 */     if (severity.equals("5"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  175 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  181 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  187 */     if (severity == 5)
/*      */     {
/*  189 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  191 */     if (severity == 1)
/*      */     {
/*  193 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  198 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  204 */     if (severity == null)
/*      */     {
/*  206 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  208 */     if (severity.equals("5"))
/*      */     {
/*  210 */       if (isAvailability) {
/*  211 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  214 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  217 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  219 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  221 */     if (severity.equals("1"))
/*      */     {
/*  223 */       if (isAvailability) {
/*  224 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  227 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  234 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  241 */     if (severity == null)
/*      */     {
/*  243 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  245 */     if (severity.equals("5"))
/*      */     {
/*  247 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  249 */     if (severity.equals("4"))
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("1"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  260 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  266 */     if (severity == null)
/*      */     {
/*  268 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  270 */     if (severity.equals("5"))
/*      */     {
/*  272 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  274 */     if (severity.equals("4"))
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("1"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  285 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  292 */     if (severity == null)
/*      */     {
/*  294 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  296 */     if (severity.equals("5"))
/*      */     {
/*  298 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  300 */     if (severity.equals("4"))
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  304 */     if (severity.equals("1"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  311 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  319 */     StringBuffer out = new StringBuffer();
/*  320 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  321 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  322 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  323 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  324 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  325 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  326 */     out.append("</tr>");
/*  327 */     out.append("</form></table>");
/*  328 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  335 */     if (val == null)
/*      */     {
/*  337 */       return "-";
/*      */     }
/*      */     
/*  340 */     String ret = FormatUtil.formatNumber(val);
/*  341 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  342 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  345 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  349 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  357 */     StringBuffer out = new StringBuffer();
/*  358 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  359 */     out.append("<tr>");
/*  360 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  362 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  364 */     out.append("</tr>");
/*  365 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  369 */       if (j % 2 == 0)
/*      */       {
/*  371 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  375 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  378 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  380 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  383 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  387 */       out.append("</tr>");
/*      */     }
/*  389 */     out.append("</table>");
/*  390 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  391 */     out.append("<tr>");
/*  392 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  393 */     out.append("</tr>");
/*  394 */     out.append("</table>");
/*  395 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  401 */     StringBuffer out = new StringBuffer();
/*  402 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  403 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  404 */     out.append("<tr>");
/*  405 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  408 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  409 */     out.append("</tr>");
/*  410 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  413 */       out.append("<tr>");
/*  414 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  415 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  416 */       out.append("</tr>");
/*      */     }
/*      */     
/*  419 */     out.append("</table>");
/*  420 */     out.append("</table>");
/*  421 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  426 */     if (severity.equals("0"))
/*      */     {
/*  428 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  432 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  439 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  452 */     StringBuffer out = new StringBuffer();
/*  453 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  454 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  456 */       out.append("<tr>");
/*  457 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  458 */       out.append("</tr>");
/*      */       
/*      */ 
/*  461 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  463 */         String borderclass = "";
/*      */         
/*      */ 
/*  466 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  468 */         out.append("<tr>");
/*      */         
/*  470 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  471 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  472 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  478 */     out.append("</table><br>");
/*  479 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  480 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  482 */       List sLinks = secondLevelOfLinks[0];
/*  483 */       List sText = secondLevelOfLinks[1];
/*  484 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  487 */         out.append("<tr>");
/*  488 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  489 */         out.append("</tr>");
/*  490 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  492 */           String borderclass = "";
/*      */           
/*      */ 
/*  495 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  497 */           out.append("<tr>");
/*      */           
/*  499 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  500 */           if (sLinks.get(i).toString().length() == 0) {
/*  501 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  504 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  506 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  510 */     out.append("</table>");
/*  511 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  518 */     StringBuffer out = new StringBuffer();
/*  519 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  520 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  522 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  524 */         out.append("<tr>");
/*  525 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  526 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  530 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  532 */           String borderclass = "";
/*      */           
/*      */ 
/*  535 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  537 */           out.append("<tr>");
/*      */           
/*  539 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  540 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  541 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  544 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  547 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  552 */     out.append("</table><br>");
/*  553 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  554 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  556 */       List sLinks = secondLevelOfLinks[0];
/*  557 */       List sText = secondLevelOfLinks[1];
/*  558 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  561 */         out.append("<tr>");
/*  562 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  563 */         out.append("</tr>");
/*  564 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  566 */           String borderclass = "";
/*      */           
/*      */ 
/*  569 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  571 */           out.append("<tr>");
/*      */           
/*  573 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  574 */           if (sLinks.get(i).toString().length() == 0) {
/*  575 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  578 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  580 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  584 */     out.append("</table>");
/*  585 */     return out.toString();
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
/*  598 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  601 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  604 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  607 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  610 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  613 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  616 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  619 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  627 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  632 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  637 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  642 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  647 */     if (val != null)
/*      */     {
/*  649 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  653 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  658 */     if (val == null) {
/*  659 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  663 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  668 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  674 */     if (val != null)
/*      */     {
/*  676 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  680 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  686 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  691 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  695 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  700 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  705 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  710 */     String hostaddress = "";
/*  711 */     String ip = request.getHeader("x-forwarded-for");
/*  712 */     if (ip == null)
/*  713 */       ip = request.getRemoteAddr();
/*  714 */     InetAddress add = null;
/*  715 */     if (ip.equals("127.0.0.1")) {
/*  716 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  720 */       add = InetAddress.getByName(ip);
/*      */     }
/*  722 */     hostaddress = add.getHostName();
/*  723 */     if (hostaddress.indexOf('.') != -1) {
/*  724 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  725 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  729 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  734 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  740 */     if (severity == null)
/*      */     {
/*  742 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  744 */     if (severity.equals("5"))
/*      */     {
/*  746 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  748 */     if (severity.equals("1"))
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  755 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  760 */     ResultSet set = null;
/*  761 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  762 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  764 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  765 */       if (set.next()) { String str1;
/*  766 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  767 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  770 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  775 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  778 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  780 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  784 */     StringBuffer rca = new StringBuffer();
/*  785 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  786 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  789 */     int rcalength = key.length();
/*  790 */     String split = "6. ";
/*  791 */     int splitPresent = key.indexOf(split);
/*  792 */     String div1 = "";String div2 = "";
/*  793 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  795 */       if (rcalength > 180) {
/*  796 */         rca.append("<span class=\"rca-critical-text\">");
/*  797 */         getRCATrimmedText(key, rca);
/*  798 */         rca.append("</span>");
/*      */       } else {
/*  800 */         rca.append("<span class=\"rca-critical-text\">");
/*  801 */         rca.append(key);
/*  802 */         rca.append("</span>");
/*      */       }
/*  804 */       return rca.toString();
/*      */     }
/*  806 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  807 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  808 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  809 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  810 */     getRCATrimmedText(div1, rca);
/*  811 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  814 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  815 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  816 */     getRCATrimmedText(div2, rca);
/*  817 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  819 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  824 */     String[] st = msg.split("<br>");
/*  825 */     for (int i = 0; i < st.length; i++) {
/*  826 */       String s = st[i];
/*  827 */       if (s.length() > 180) {
/*  828 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  830 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  834 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  835 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  837 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  841 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  842 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  843 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  846 */       if (key == null) {
/*  847 */         return ret;
/*      */       }
/*      */       
/*  850 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  851 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  854 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  855 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  856 */       set = AMConnectionPool.executeQueryStmt(query);
/*  857 */       if (set.next())
/*      */       {
/*  859 */         String helpLink = set.getString("LINK");
/*  860 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  863 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  869 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  888 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  879 */         if (set != null) {
/*  880 */           AMConnectionPool.closeStatement(set);
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
/*  894 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  895 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  897 */       String entityStr = (String)keys.nextElement();
/*  898 */       String mmessage = temp.getProperty(entityStr);
/*  899 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  900 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  902 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  908 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  909 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  911 */       String entityStr = (String)keys.nextElement();
/*  912 */       String mmessage = temp.getProperty(entityStr);
/*  913 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  914 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  916 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  921 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  931 */     String des = new String();
/*  932 */     while (str.indexOf(find) != -1) {
/*  933 */       des = des + str.substring(0, str.indexOf(find));
/*  934 */       des = des + replace;
/*  935 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  937 */     des = des + str;
/*  938 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  945 */       if (alert == null)
/*      */       {
/*  947 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  949 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  951 */         return "&nbsp;";
/*      */       }
/*      */       
/*  954 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  956 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  959 */       int rcalength = test.length();
/*  960 */       if (rcalength < 300)
/*      */       {
/*  962 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  966 */       StringBuffer out = new StringBuffer();
/*  967 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  968 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  969 */       out.append("</div>");
/*  970 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  971 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  972 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  977 */       ex.printStackTrace();
/*      */     }
/*  979 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  985 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  990 */     ArrayList attribIDs = new ArrayList();
/*  991 */     ArrayList resIDs = new ArrayList();
/*  992 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  994 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  996 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/*  998 */       String resourceid = "";
/*  999 */       String resourceType = "";
/* 1000 */       if (type == 2) {
/* 1001 */         resourceid = (String)row.get(0);
/* 1002 */         resourceType = (String)row.get(3);
/*      */       }
/* 1004 */       else if (type == 3) {
/* 1005 */         resourceid = (String)row.get(0);
/* 1006 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1009 */         resourceid = (String)row.get(6);
/* 1010 */         resourceType = (String)row.get(7);
/*      */       }
/* 1012 */       resIDs.add(resourceid);
/* 1013 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1014 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1016 */       String healthentity = null;
/* 1017 */       String availentity = null;
/* 1018 */       if (healthid != null) {
/* 1019 */         healthentity = resourceid + "_" + healthid;
/* 1020 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1023 */       if (availid != null) {
/* 1024 */         availentity = resourceid + "_" + availid;
/* 1025 */         entitylist.add(availentity);
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
/* 1039 */     Properties alert = getStatus(entitylist);
/* 1040 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1045 */     int size = monitorList.size();
/*      */     
/* 1047 */     String[] severity = new String[size];
/*      */     
/* 1049 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1051 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1052 */       String resourceName1 = (String)row1.get(7);
/* 1053 */       String resourceid1 = (String)row1.get(6);
/* 1054 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1055 */       if (severity[j] == null)
/*      */       {
/* 1057 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1061 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1063 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1065 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1068 */         if (sev > 0) {
/* 1069 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1070 */           monitorList.set(k, monitorList.get(j));
/* 1071 */           monitorList.set(j, t);
/* 1072 */           String temp = severity[k];
/* 1073 */           severity[k] = severity[j];
/* 1074 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1080 */     int z = 0;
/* 1081 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1084 */       int i = 0;
/* 1085 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1088 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1092 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1096 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1098 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1101 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1105 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1108 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1109 */       String resourceName1 = (String)row1.get(7);
/* 1110 */       String resourceid1 = (String)row1.get(6);
/* 1111 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1112 */       if (hseverity[j] == null)
/*      */       {
/* 1114 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1119 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1121 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1124 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1127 */         if (hsev > 0) {
/* 1128 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1129 */           monitorList.set(k, monitorList.get(j));
/* 1130 */           monitorList.set(j, t);
/* 1131 */           String temp1 = hseverity[k];
/* 1132 */           hseverity[k] = hseverity[j];
/* 1133 */           hseverity[j] = temp1;
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
/* 1145 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1146 */     boolean forInventory = false;
/* 1147 */     String trdisplay = "none";
/* 1148 */     String plusstyle = "inline";
/* 1149 */     String minusstyle = "none";
/* 1150 */     String haidTopLevel = "";
/* 1151 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1153 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1155 */         haidTopLevel = request.getParameter("haid");
/* 1156 */         forInventory = true;
/* 1157 */         trdisplay = "table-row;";
/* 1158 */         plusstyle = "none";
/* 1159 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1166 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1169 */     ArrayList listtoreturn = new ArrayList();
/* 1170 */     StringBuffer toreturn = new StringBuffer();
/* 1171 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1172 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1173 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1175 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1177 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1178 */       String childresid = (String)singlerow.get(0);
/* 1179 */       String childresname = (String)singlerow.get(1);
/* 1180 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1181 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1182 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1183 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1184 */       String unmanagestatus = (String)singlerow.get(5);
/* 1185 */       String actionstatus = (String)singlerow.get(6);
/* 1186 */       String linkclass = "monitorgp-links";
/* 1187 */       String titleforres = childresname;
/* 1188 */       String titilechildresname = childresname;
/* 1189 */       String childimg = "/images/trcont.png";
/* 1190 */       String flag = "enable";
/* 1191 */       String dcstarted = (String)singlerow.get(8);
/* 1192 */       String configMonitor = "";
/* 1193 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1194 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1196 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1198 */       if (singlerow.get(7) != null)
/*      */       {
/* 1200 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1202 */       String haiGroupType = "0";
/* 1203 */       if ("HAI".equals(childtype))
/*      */       {
/* 1205 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1207 */       childimg = "/images/trend.png";
/* 1208 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1209 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1210 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1212 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1214 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1216 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1217 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1220 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1222 */         linkclass = "disabledtext";
/* 1223 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1225 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1226 */       String availmouseover = "";
/* 1227 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1229 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1231 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1232 */       String healthmouseover = "";
/* 1233 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1235 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1238 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1239 */       int spacing = 0;
/* 1240 */       if (level >= 1)
/*      */       {
/* 1242 */         spacing = 40 * level;
/*      */       }
/* 1244 */       if (childtype.equals("HAI"))
/*      */       {
/* 1246 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1247 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1248 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1250 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1251 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1252 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1253 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1254 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1255 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1256 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1257 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1258 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1259 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1260 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1262 */         if (!forInventory)
/*      */         {
/* 1264 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1267 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1269 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1271 */           actions = editlink + actions;
/*      */         }
/* 1273 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1275 */           actions = actions + associatelink;
/*      */         }
/* 1277 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1278 */         String arrowimg = "";
/* 1279 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1281 */           actions = "";
/* 1282 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1283 */           checkbox = "";
/* 1284 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1286 */         if (isIt360)
/*      */         {
/* 1288 */           actionimg = "";
/* 1289 */           actions = "";
/* 1290 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1291 */           checkbox = "";
/*      */         }
/*      */         
/* 1294 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1296 */           actions = "";
/*      */         }
/* 1298 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1300 */           checkbox = "";
/*      */         }
/*      */         
/* 1303 */         String resourcelink = "";
/*      */         
/* 1305 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1307 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1311 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1314 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1315 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1316 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1320 */         if (!isIt360)
/*      */         {
/* 1322 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1326 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1329 */         toreturn.append("</tr>");
/* 1330 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1332 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1333 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1337 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1338 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1341 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1345 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1347 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1348 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1349 */             toreturn.append(assocMessage);
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1351 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1359 */         String resourcelink = null;
/* 1360 */         boolean hideEditLink = false;
/* 1361 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1363 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1364 */           hideEditLink = true;
/* 1365 */           if (isIt360)
/*      */           {
/* 1367 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1371 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1373 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1375 */           hideEditLink = true;
/* 1376 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1377 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1382 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1385 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1386 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1387 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1388 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1389 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1390 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1391 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1392 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1393 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1394 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1395 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1396 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1397 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1399 */         if (hideEditLink)
/*      */         {
/* 1401 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1403 */         if (!forInventory)
/*      */         {
/* 1405 */           removefromgroup = "";
/*      */         }
/* 1407 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1408 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1409 */           actions = actions + configcustomfields;
/*      */         }
/* 1411 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1413 */           actions = editlink + actions;
/*      */         }
/* 1415 */         String managedLink = "";
/* 1416 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1418 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1419 */           actions = "";
/* 1420 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1421 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1424 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1426 */           checkbox = "";
/*      */         }
/*      */         
/* 1429 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1431 */           actions = "";
/*      */         }
/* 1433 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1434 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1435 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1436 */         if (isIt360)
/*      */         {
/* 1438 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1442 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1444 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1446 */         if (!isIt360)
/*      */         {
/* 1448 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1454 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1457 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1464 */       StringBuilder toreturn = new StringBuilder();
/* 1465 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1466 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1467 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1468 */       String title = "";
/* 1469 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
/* 1470 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1471 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1472 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1474 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1476 */       else if ("5".equals(severity))
/*      */       {
/* 1478 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1482 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1484 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1485 */       toreturn.append(v);
/*      */       
/* 1487 */       toreturn.append(link);
/* 1488 */       if (severity == null)
/*      */       {
/* 1490 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1492 */       else if (severity.equals("5"))
/*      */       {
/* 1494 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1496 */       else if (severity.equals("4"))
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("1"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1507 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1509 */       toreturn.append("</a>");
/* 1510 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1514 */       ex.printStackTrace();
/*      */     }
/* 1516 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1523 */       StringBuilder toreturn = new StringBuilder();
/* 1524 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1525 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1526 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1527 */       if (message == null)
/*      */       {
/* 1529 */         message = "";
/*      */       }
/*      */       
/* 1532 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1533 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1535 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1536 */       toreturn.append(v);
/*      */       
/* 1538 */       toreturn.append(link);
/*      */       
/* 1540 */       if (severity == null)
/*      */       {
/* 1542 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1544 */       else if (severity.equals("5"))
/*      */       {
/* 1546 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1548 */       else if (severity.equals("1"))
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1555 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1557 */       toreturn.append("</a>");
/* 1558 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1564 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1567 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1568 */     if (invokeActions != null) {
/* 1569 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1570 */       while (iterator.hasNext()) {
/* 1571 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1572 */         if (actionmap.containsKey(actionid)) {
/* 1573 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1578 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1582 */     String actionLink = "";
/* 1583 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1584 */     String query = "";
/* 1585 */     ResultSet rs = null;
/* 1586 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1587 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1588 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1589 */       actionLink = "method=" + methodName;
/*      */     }
/* 1591 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1592 */       actionLink = methodName;
/*      */     }
/* 1594 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1595 */     Iterator itr = methodarglist.iterator();
/* 1596 */     boolean isfirstparam = true;
/* 1597 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1598 */     while (itr.hasNext()) {
/* 1599 */       HashMap argmap = (HashMap)itr.next();
/* 1600 */       String argtype = (String)argmap.get("TYPE");
/* 1601 */       String argname = (String)argmap.get("IDENTITY");
/* 1602 */       String paramname = (String)argmap.get("PARAMETER");
/* 1603 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1604 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1605 */         isfirstparam = false;
/* 1606 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1608 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1612 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1616 */         actionLink = actionLink + "&";
/*      */       }
/* 1618 */       String paramValue = null;
/* 1619 */       String tempargname = argname;
/* 1620 */       if (commonValues.getProperty(tempargname) != null) {
/* 1621 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1624 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1625 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1626 */           if (dbType.equals("mysql")) {
/* 1627 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1630 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1632 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1634 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1635 */             if (rs.next()) {
/* 1636 */               paramValue = rs.getString("VALUE");
/* 1637 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1641 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1645 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1648 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1653 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1654 */           paramValue = rowId;
/*      */         }
/* 1656 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1657 */           paramValue = managedObjectName;
/*      */         }
/* 1659 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1660 */           paramValue = resID;
/*      */         }
/* 1662 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1663 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1666 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1668 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1669 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1670 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1672 */     return actionLink;
/*      */   }
/*      */   
/* 1675 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1676 */     String dependentAttribute = null;
/* 1677 */     String align = "left";
/*      */     
/* 1679 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1680 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1681 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1682 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1683 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1684 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1685 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1686 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1687 */       align = "center";
/*      */     }
/*      */     
/* 1690 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1691 */     String actualdata = "";
/*      */     
/* 1693 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1694 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1695 */         actualdata = availValue;
/*      */       }
/* 1697 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1698 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1702 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1703 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1706 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1712 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1713 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1714 */       toreturn.append("<table>");
/* 1715 */       toreturn.append("<tr>");
/* 1716 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1717 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1718 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1719 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1720 */         String toolTip = "";
/* 1721 */         String hideClass = "";
/* 1722 */         String textStyle = "";
/* 1723 */         boolean isreferenced = true;
/* 1724 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1725 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1726 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1727 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1729 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1730 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1731 */           while (valueList.hasMoreTokens()) {
/* 1732 */             String dependentVal = valueList.nextToken();
/* 1733 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1734 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1735 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1737 */               toolTip = "";
/* 1738 */               hideClass = "";
/* 1739 */               isreferenced = false;
/* 1740 */               textStyle = "disabledtext";
/* 1741 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1745 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1746 */           toolTip = "";
/* 1747 */           hideClass = "";
/* 1748 */           isreferenced = false;
/* 1749 */           textStyle = "disabledtext";
/* 1750 */           if (dependentImageMap != null) {
/* 1751 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1752 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1755 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1759 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1760 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1761 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1762 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1763 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1764 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1766 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1767 */           if (isreferenced) {
/* 1768 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1772 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1773 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1774 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1775 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1776 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1777 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1779 */           toreturn.append("</span>");
/* 1780 */           toreturn.append("</a>");
/* 1781 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1784 */       toreturn.append("</tr>");
/* 1785 */       toreturn.append("</table>");
/* 1786 */       toreturn.append("</td>");
/*      */     } else {
/* 1788 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1791 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1795 */     String colTime = null;
/* 1796 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1797 */     if ((rows != null) && (rows.size() > 0)) {
/* 1798 */       Iterator<String> itr = rows.iterator();
/* 1799 */       String maxColQuery = "";
/* 1800 */       for (;;) { if (itr.hasNext()) {
/* 1801 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1802 */           ResultSet maxCol = null;
/*      */           try {
/* 1804 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1805 */             while (maxCol.next()) {
/* 1806 */               if (colTime == null) {
/* 1807 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1810 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1819 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1821 */               if (maxCol != null)
/* 1822 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1824 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1819 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1821 */               if (maxCol != null)
/* 1822 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1824 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1829 */     return colTime;
/*      */   }
/*      */   
/* 1832 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1833 */     tablename = null;
/* 1834 */     ResultSet rsTable = null;
/* 1835 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1837 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1838 */       while (rsTable.next()) {
/* 1839 */         tablename = rsTable.getString("DATATABLE");
/* 1840 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1841 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1854 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1845 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1848 */         if (rsTable != null)
/* 1849 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1851 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1857 */     String argsList = "";
/* 1858 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1860 */       if (showArgsMap.get(row) != null) {
/* 1861 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1862 */         if (showArgslist != null) {
/* 1863 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1864 */             if (argsList.trim().equals("")) {
/* 1865 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1868 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1875 */       e.printStackTrace();
/* 1876 */       return "";
/*      */     }
/* 1878 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1883 */     String argsList = "";
/* 1884 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1887 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1889 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1890 */         if (hideArgsList != null)
/*      */         {
/* 1892 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1894 */             if (argsList.trim().equals(""))
/*      */             {
/* 1896 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1900 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1908 */       ex.printStackTrace();
/*      */     }
/* 1910 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1914 */     StringBuilder toreturn = new StringBuilder();
/* 1915 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1922 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1923 */       Iterator itr = tActionList.iterator();
/* 1924 */       while (itr.hasNext()) {
/* 1925 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1926 */         String confirmmsg = "";
/* 1927 */         String link = "";
/* 1928 */         String isJSP = "NO";
/* 1929 */         HashMap tactionMap = (HashMap)itr.next();
/* 1930 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1931 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1932 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1933 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1934 */           (actionmap.containsKey(actionId))) {
/* 1935 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1936 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1937 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1938 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1939 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1941 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1947 */           if (isTableAction) {
/* 1948 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1951 */             tableName = "Link";
/* 1952 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1953 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1954 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1955 */             toreturn.append("</a></td>");
/*      */           }
/* 1957 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1958 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1966 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1972 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1974 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1975 */       Properties prop = (Properties)node.getUserObject();
/* 1976 */       String mgID = prop.getProperty("label");
/* 1977 */       String mgName = prop.getProperty("value");
/* 1978 */       String isParent = prop.getProperty("isParent");
/* 1979 */       int mgIDint = Integer.parseInt(mgID);
/* 1980 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
/*      */       {
/* 1982 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1984 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1985 */       if (node.getChildCount() > 0)
/*      */       {
/* 1987 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1989 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1991 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1993 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1997 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2002 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2004 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2006 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2008 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2012 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2015 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2016 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2018 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2022 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2024 */       if (node.getChildCount() > 0)
/*      */       {
/* 2026 */         builder.append("<UL>");
/* 2027 */         printMGTree(node, builder);
/* 2028 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2033 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2034 */     StringBuffer toReturn = new StringBuffer();
/* 2035 */     String table = "-";
/*      */     try {
/* 2037 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2038 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2039 */       float total = 0.0F;
/* 2040 */       while (it.hasNext()) {
/* 2041 */         String attName = (String)it.next();
/* 2042 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2043 */         boolean roundOffData = false;
/* 2044 */         if ((data != null) && (!data.equals(""))) {
/* 2045 */           if (data.indexOf(",") != -1) {
/* 2046 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2049 */             float value = Float.parseFloat(data);
/* 2050 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2053 */             total += value;
/* 2054 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2057 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2062 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2063 */       while (attVsWidthList.hasNext()) {
/* 2064 */         String attName = (String)attVsWidthList.next();
/* 2065 */         String data = (String)attVsWidthProps.get(attName);
/* 2066 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2067 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2068 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2069 */         String className = (String)graphDetails.get("ClassName");
/* 2070 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2071 */         if (percentage < 1.0F)
/*      */         {
/* 2073 */           data = percentage + "";
/*      */         }
/* 2075 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2077 */       if (toReturn.length() > 0) {
/* 2078 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2082 */       e.printStackTrace();
/*      */     }
/* 2084 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2090 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2091 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2092 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2093 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2094 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2095 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2096 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2097 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2098 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2101 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2102 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2103 */       splitvalues[0] = multiplecondition.toString();
/* 2104 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2107 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public java.util.Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2112 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2113 */     if (thresholdType != 3) {
/* 2114 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2115 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2116 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2117 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2118 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2119 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2121 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2122 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2123 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2124 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2125 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2126 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2128 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2129 */     if (updateSelected != null) {
/* 2130 */       updateSelected[0] = "selected";
/*      */     }
/* 2132 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2137 */       StringBuffer toreturn = new StringBuffer("");
/* 2138 */       if (commaSeparatedMsgId != null) {
/* 2139 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2140 */         int count = 0;
/* 2141 */         while (msgids.hasMoreTokens()) {
/* 2142 */           String id = msgids.nextToken();
/* 2143 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2144 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2145 */           count++;
/* 2146 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2147 */             if (toreturn.length() == 0) {
/* 2148 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2150 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2151 */             if (!image.trim().equals("")) {
/* 2152 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2154 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2155 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2158 */         if (toreturn.length() > 0) {
/* 2159 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2163 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2166 */       e.printStackTrace(); }
/* 2167 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2173 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2179 */   private static java.util.Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2180 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public java.util.Map<String, Long> getDependants()
/*      */   {
/* 2201 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2205 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2206 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2219 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2223 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2225 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2226 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2227 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/* 2232 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2234 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2242 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2245 */     JspWriter out = null;
/* 2246 */     Object page = this;
/* 2247 */     JspWriter _jspx_out = null;
/* 2248 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2252 */       response.setContentType("text/html;charset=UTF-8");
/* 2253 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2255 */       _jspx_page_context = pageContext;
/* 2256 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2257 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2258 */       session = pageContext.getSession();
/* 2259 */       out = pageContext.getOut();
/* 2260 */       _jspx_out = out;
/*      */       
/* 2262 */       out.write("<!DOCTYPE html>\n");
/* 2263 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2264 */       out.write(10);
/*      */       
/* 2266 */       request.setAttribute("HelpKey", "SNMP Trap Listener");
/*      */       
/* 2268 */       out.write("\n\n\n\n\n\n\n\n");
/* 2269 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n<bean:define id=\"available\" name=\"colors\" property=\"AVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unavailable\" name=\"colors\" property=\"UNAVAILABLE\" type=\"java.lang.String\"/>\n<bean:define id=\"unmanaged\" name=\"colors\" property=\"UNMANAGED\" type=\"java.lang.String\"/>\n<bean:define id=\"scheduled\" name=\"colors\" property=\"SCHEDULED\" type=\"java.lang.String\"/>\n<bean:define id=\"critical\" name=\"colors\" property=\"CRITICAL\" type=\"java.lang.String\"/>\n<bean:define id=\"clear\" name=\"colors\" property=\"CLEAR\" type=\"java.lang.String\"/>\n<bean:define id=\"warning\" name=\"colors\" property=\"WARNING\" type=\"java.lang.String\"/>\n\n");
/*      */       
/* 2271 */       String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2272 */       boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */       
/* 2274 */       out.write(10);
/* 2275 */       out.write(10);
/* 2276 */       out.write(10);
/* 2277 */       out.write(10);
/* 2278 */       out.write(10);
/* 2279 */       out.write(10);
/* 2280 */       Hashtable availabilitykeys = null;
/* 2281 */       synchronized (application) {
/* 2282 */         availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2283 */         if (availabilitykeys == null) {
/* 2284 */           availabilitykeys = new Hashtable();
/* 2285 */           _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */         }
/*      */       }
/* 2288 */       out.write(10);
/* 2289 */       Hashtable healthkeys = null;
/* 2290 */       synchronized (application) {
/* 2291 */         healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2292 */         if (healthkeys == null) {
/* 2293 */           healthkeys = new Hashtable();
/* 2294 */           _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */         }
/*      */       }
/* 2297 */       out.write(10);
/* 2298 */       out.write(10);
/*      */       
/* 2300 */       if (session.getAttribute("org.apache.struts.action.TOKEN") == null)
/*      */       {
/* 2302 */         org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2303 */         token.saveToken(request);
/*      */       }
/*      */       
/* 2306 */       out.write("\n\n<script language=\"javascript\" src=\"/webclient/fault/js/fault.js\" type=\"text/javascript\"></script>\n<script>\n\n\nfunction setAsClear(trapid,trapname,disabled){\n\t");
/* 2307 */       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */         return;
/* 2309 */       out.write("\n\tif(disabled){\n\n\t\treturn;\n\t\n\t}else{\n\t\n\n\t\tif(confirm('");
/* 2310 */       out.print(FormatUtil.getString("am.webclient.traplistner.alert"));
/* 2311 */       out.write("')){\n\n\t\turl = '/fault/AlarmOperations.do?methodCall=clearAlarm&viewId=Alerts.5&selectedEntity='+trapid+'_3200&source='+trapname+'&category=Trap&org.apache.struts.taglib.html.TOKEN=");
/* 2312 */       out.print(session.getAttribute("org.apache.struts.action.TOKEN"));
/* 2313 */       out.write("&alertdetails=true'; //NO I18N\n\n\thttp.open(\"GET\",url,true);\n\thttp.onreadystatechange = refreshStatus ;\n\thttp.send(null);\n\n\n\n\t}else{\n\n\t\treturn;\n\n\n\t}\n\t}\n\n}\n\n\nfunction refreshStatus(){\n\n\t if(http.readyState == 4)\n        {\n\t\tlocation.reload(true);\n\t}\n\n}\n\nfunction fnSelectAll(e,name)\n{\n    ToggleAll(e,document.form1,name)\n}\n\nfunction deleteSelections()\n{\n\t");
/* 2314 */       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */         return;
/* 2316 */       out.write(10);
/* 2317 */       out.write(9);
/* 2318 */       if (_jspx_meth_logic_005fnotPresent_005f0(_jspx_page_context))
/*      */         return;
/* 2320 */       out.write("\n}\n</script>\n\n");
/*      */       
/* 2322 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2323 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2324 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2326 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/NewAdminLayout.jsp");
/* 2327 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2328 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2330 */           out.write("\n    \n");
/*      */           
/* 2332 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2333 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2334 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2336 */           _jspx_th_tiles_005fput_005f0.setName("UserArea");
/*      */           
/* 2338 */           _jspx_th_tiles_005fput_005f0.setType("string");
/* 2339 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2340 */           if (_jspx_eval_tiles_005fput_005f0 != 0) {
/* 2341 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 2342 */               out = _jspx_page_context.pushBody();
/* 2343 */               _jspx_th_tiles_005fput_005f0.setBodyContent((BodyContent)out);
/* 2344 */               _jspx_th_tiles_005fput_005f0.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2347 */               out.write(32);
/* 2348 */               out.write(10);
/* 2349 */               out.write(10);
/* 2350 */               HashMap threshold = (HashMap)request.getAttribute("threshold");
/*      */               
/* 2352 */               out.write("\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2353 */               out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2354 */               out.write(" &gt;<span class=\"bcactive\"> ");
/* 2355 */               out.print(FormatUtil.getString("am.webclient.traplistener.viewtrap"));
/* 2356 */               out.write("</span></td>\n\t</tr>\n</table>\n");
/*      */               
/* 2358 */               String bgcolor = "";
/*      */               
/* 2360 */               ArrayList trapList = new ArrayList();
/* 2361 */               ArrayList healthKeys = new ArrayList();
/* 2362 */               healthKeys.add("3200");
/* 2363 */               for (int i = 0; i < ((ArrayList)request.getAttribute("list")).size(); i++)
/*      */               {
/* 2365 */                 trapList.add(((Properties)((ArrayList)request.getAttribute("list")).get(i)).get("TRAPID").toString());
/*      */               }
/*      */               
/*      */ 
/* 2369 */               Properties alert = getStatus(trapList, healthKeys);
/*      */               
/* 2371 */               out.write("\n<form name=\"form1\" style=\"display:inline\">\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n  \t<td width=\"3%\" height=\"28\" valign=\"center\"  class=\"tableheadingbborder\">");
/* 2372 */               if (_jspx_meth_c_005fchoose_005f0(_jspx_th_tiles_005fput_005f0, _jspx_page_context))
/*      */                 return;
/* 2374 */               out.write("</td>  ");
/* 2375 */               out.write("\n    <td class=\"tableheadingbborder\" width=\"15%\" align=\"left\">");
/* 2376 */               out.print(FormatUtil.getString("am.webclient.traplistener.trapname"));
/* 2377 */               out.write("</td>\n    <!--td class=\"columnheading\" align=\"center\">");
/* 2378 */               out.print(FormatUtil.getString("am.webclient.traplistener.status"));
/* 2379 */               out.write("</td-->\n    <td width=\"20%\" class=\"tableheadingbborder\" align=\"left\">");
/* 2380 */               out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2381 */               out.write("</td>\n    <td width=\"30%\" class=\"tableheadingbborder\" align=\"left\">");
/* 2382 */               out.print(FormatUtil.getString("Severity"));
/* 2383 */               out.write("</td>\n\n\n\t<!--table bordercolor=\"#000000\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" height=\"30\" width=\"100%\" align=\"center\" >\n                <tr>\n                          <td colspan=\"3\" align=\"center\" height=\"15\" class=\"bodytextbold\">");
/* 2384 */               out.print(FormatUtil.getString("am.webclient.threshold.criteria"));
/* 2385 */               out.write("</td>\n                </tr><tr>\n                          <td class=\"sptrborder\" width=\"33%\">");
/* 2386 */               out.print(FormatUtil.getString("am.webclient.threshold.criticalalert"));
/* 2387 */               out.write("</td>\n                          <td class=\"sptrborder\" width=\"38%\">");
/* 2388 */               out.print(FormatUtil.getString("am.webclient.threshold.warningalert"));
/* 2389 */               out.write("</td>\n                          <td class=\"sptborder\" width=\"29%\">");
/* 2390 */               out.print(FormatUtil.getString("am.webclient.threshold.clearalert"));
/* 2391 */               out.write("</td>\n                </tr>\n                </table-->\n\n<!--%=FormatUtil.getString(\"am.webclient.traplistener.severity\")%--></td>\n\t<td class=\"tableheadingbborder\" align=\"center\" width=\"15%\">");
/* 2392 */               out.print(FormatUtil.getString("am.webclient.traplistener.status"));
/* 2393 */               out.write("</td>\n    <td class=\"tableheadingbborder\" align=\"center\">");
/* 2394 */               out.print(FormatUtil.getString("am.webclient.traplistener.edit"));
/* 2395 */               out.write("</td>\n  </tr> \n  ");
/*      */               
/* 2397 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2398 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2399 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f0);
/* 2400 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2401 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/* 2403 */                   out.write(10);
/* 2404 */                   out.write(32);
/* 2405 */                   out.write(32);
/*      */                   
/* 2407 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2408 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2409 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/* 2411 */                   _jspx_th_c_005fwhen_005f1.setTest("${!empty list}");
/* 2412 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2413 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/* 2415 */                       out.write(10);
/* 2416 */                       out.write(32);
/* 2417 */                       out.write(32);
/*      */                       
/* 2419 */                       ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2420 */                       _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2421 */                       _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_c_005fwhen_005f1);
/*      */                       
/* 2423 */                       _jspx_th_c_005fforEach_005f0.setVar("props");
/*      */                       
/* 2425 */                       _jspx_th_c_005fforEach_005f0.setItems("${list}");
/*      */                       
/* 2427 */                       _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 2428 */                       int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */                       try {
/* 2430 */                         int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2431 */                         if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                           for (;;) {
/* 2433 */                             out.write(10);
/* 2434 */                             String grayed = "";
/* 2435 */                             String grayedLink = "staticlinks";
/* 2436 */                             String disabled = "false";
/*      */                             
/* 2438 */                             out.write(" \n\t  ");
/*      */                             
/* 2440 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2441 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2442 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 2444 */                             _jspx_th_c_005fif_005f0.setTest("${status.count %2 == 1}");
/* 2445 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2446 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2448 */                                 out.write("\n\t  ");
/* 2449 */                                 bgcolor = "whitegrayborder";
/* 2450 */                                 out.write("\n\t  ");
/* 2451 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2452 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2456 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2457 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2460 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2461 */                             out.write("\n\t  ");
/*      */                             
/* 2463 */                             IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2464 */                             _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2465 */                             _jspx_th_c_005fif_005f1.setParent(_jspx_th_c_005fforEach_005f0);
/*      */                             
/* 2467 */                             _jspx_th_c_005fif_005f1.setTest("${status.count %2 == 0}");
/* 2468 */                             int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2469 */                             if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                               for (;;) {
/* 2471 */                                 out.write(" \n\t  ");
/* 2472 */                                 bgcolor = "yellowgrayborder";
/* 2473 */                                 out.write("\n\t  ");
/* 2474 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2475 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2479 */                             if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2480 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2483 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2484 */                             out.write(" \t \t\n\t\t\t  ");
/*      */                             
/* 2486 */                             ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2487 */                             _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2488 */                             _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_c_005fforEach_005f0);
/* 2489 */                             int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2490 */                             if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                               for (;;)
/*      */                               {
/* 2493 */                                 WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2494 */                                 _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 2495 */                                 _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                 
/* 2497 */                                 _jspx_th_c_005fwhen_005f2.setTest("${!empty props.TRAPSTATUS}");
/* 2498 */                                 int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 2499 */                                 if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                   for (;;) {
/* 2501 */                                     out.write("\n                \n                \n                ");
/* 2502 */                                     if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fwhen_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 2504 */                                     out.write("\n        ");
/* 2505 */                                     String status = (String)pageContext.getAttribute("status");
/*      */                                     
/* 2507 */                                     out.write("\n\t\n\t");
/* 2508 */                                     if (status.equalsIgnoreCase("Disabled"))
/*      */                                     {
/* 2510 */                                       grayed = "graycolor";
/* 2511 */                                       grayedLink = "disabledtext ";
/* 2512 */                                       disabled = "true";
/*      */                                     }
/*      */                                     
/* 2515 */                                     out.write("\n\t\t  ");
/* 2516 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 2517 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2521 */                                 if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 2522 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2525 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 2526 */                                 if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2528 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2529 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2533 */                             if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2534 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2537 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2538 */                             out.write("\n\n\t  <tr class=\"");
/* 2539 */                             out.print(grayed);
/* 2540 */                             out.write("\">\n\t\t<td class=\"");
/* 2541 */                             out.print(bgcolor);
/* 2542 */                             out.write("\">\n\t\t\t");
/* 2543 */                             if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2545 */                             out.write(32);
/* 2546 */                             out.write("\n\t\t\t<input type=\"checkbox\" name=\"checkbox\" value='");
/* 2547 */                             if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2549 */                             out.write("'>\n\t\t</td>  \t\n\t\t<td class=\"");
/* 2550 */                             out.print(bgcolor);
/* 2551 */                             out.write("\" align=\"left\">\n\t\t  ");
/*      */                             
/* 2553 */                             ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2554 */                             _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2555 */                             _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/* 2556 */                             int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2557 */                             if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                               for (;;)
/*      */                               {
/* 2560 */                                 WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2561 */                                 _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2562 */                                 _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                                 
/* 2564 */                                 _jspx_th_c_005fwhen_005f3.setTest("${!empty props.TRAPNAME}");
/* 2565 */                                 int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2566 */                                 if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                                   for (;;) {
/* 2568 */                                     out.write("<a href=\"#\" onclick='javascript:fnOpenNewWindow(\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2569 */                                     if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 2571 */                                     out.write("_3200&source=");
/* 2572 */                                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 2574 */                                     out.write("&category=3200&monitortype=Trap&PRINTER_FRIENDLY=true\")' class=\"");
/* 2575 */                                     out.print(grayedLink);
/* 2576 */                                     out.write(34);
/* 2577 */                                     out.write(62);
/* 2578 */                                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fwhen_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 2580 */                                     out.write("</a>");
/* 2581 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2582 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2586 */                                 if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2587 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2590 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2591 */                                 if (_jspx_meth_c_005fotherwise_005f2(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2593 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2594 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2598 */                             if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2599 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2602 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2603 */                             out.write(32);
/* 2604 */                             out.write("\n\t\t</td>\n\t\t<td class=\"");
/* 2605 */                             out.print(bgcolor);
/* 2606 */                             out.write("\" align=\"left\">\n\t\t  ");
/* 2607 */                             if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2609 */                             out.write("\n\t\t</td>\n\t\t  ");
/*      */                             
/* 2611 */                             ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2612 */                             _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2613 */                             _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fforEach_005f0);
/* 2614 */                             int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2615 */                             if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                               for (;;)
/*      */                               {
/* 2618 */                                 WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2619 */                                 _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2620 */                                 _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                                 
/* 2622 */                                 _jspx_th_c_005fwhen_005f5.setTest("${!empty props.TRAPSEVERITY}");
/* 2623 */                                 int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2624 */                                 if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                                   for (;;) {
/* 2626 */                                     out.write("\n                   \n                     ");
/* 2627 */                                     if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                     }
/* 2629 */                                     out.write("\n\t\t<!--table border=\"0\" width=\"100%\">\n\t\t<tr-->\n        ");
/* 2630 */                                     String severity = (String)pageContext.getAttribute("severity");
/*      */                                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2649 */                                     if ((!severity.equals("Critical")) && (!severity.equals("Warning")) && (!severity.equals("Clear")))
/*      */                                     {
/* 2651 */                                       ArrayList details = (ArrayList)threshold.get(severity);
/*      */                                       
/*      */ 
/* 2654 */                                       String tip = FormatUtil.getString("am.fault.threshold.critical") + " ";
/* 2655 */                                       for (int i = 1; i < details.size(); i++)
/*      */                                       {
/*      */ 
/* 2658 */                                         tip = tip + FormatUtil.findReplace((String)details.get(i), "\"", "&quot;") + " ";
/* 2659 */                                         if ((i != 1) && (i % 2 == 0)) {
/* 2660 */                                           tip = tip + "&lt;br&gt;";
/*      */                                         }
/* 2662 */                                         if (i == 2)
/*      */                                         {
/* 2664 */                                           tip = tip + FormatUtil.getString("am.fault.threshold.warning") + " ";
/*      */                                         }
/* 2666 */                                         if (i == 4)
/*      */                                         {
/* 2668 */                                           tip = tip + FormatUtil.getString("am.fault.threshold.clear") + " "; }
/*      */                                       }
/* 2670 */                                       out.write("\n\n\t\t\t<td class=\"");
/* 2671 */                                       out.print(bgcolor);
/* 2672 */                                       out.write("\" onmouseover=\"ddrivetip(this,event,'");
/* 2673 */                                       out.print(tip);
/* 2674 */                                       out.write("',false,true,'#000000',230,'lightyellow')\" onmouseout=\"hideddrivetip()\"> \n\n\t\t\t\t");
/* 2675 */                                       out.print(FormatUtil.getString("am.webclient.traplistener.threshold", new String[] { "\"" + (String)details.get(0) + "\"" }));
/* 2676 */                                       out.write(10);
/* 2677 */                                       out.write(10);
/*      */                                     } else {
/* 2679 */                                       out.write("\n\n\t\t\n\t\t<td class=\"");
/* 2680 */                                       out.print(bgcolor);
/* 2681 */                                       out.write("\" align=\"left\">\n\t\t");
/* 2682 */                                       out.println(FormatUtil.getString(severity));
/*      */                                     }
/*      */                                     
/*      */ 
/*      */ 
/* 2687 */                                     out.write("\n\t\t<!--/tr>\n\t\t</table-->\n\t\t</td>\n\t\t  ");
/* 2688 */                                     int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2689 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2693 */                                 if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2694 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*      */                                   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2697 */                                 this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2698 */                                 if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                                 }
/* 2700 */                                 int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2701 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2705 */                             if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2706 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*      */                               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2709 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2710 */                             out.write(10);
/* 2711 */                             String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + (String)pageContext.getAttribute("trapid");
/* 2712 */                             String key = alert.getProperty((String)pageContext.getAttribute("trapid") + "#" + 3200 + "#" + "MESSAGE");
/*      */                             
/* 2714 */                             out.write("\n\n\t\t<td class=\"");
/* 2715 */                             out.print(bgcolor);
/* 2716 */                             out.write("\" align=\"center\">\n<!--<a href=\"javascript:void(0)\" onmouseover=\"ddrivetip(this,event,'");
/*      */                             
/* 2718 */                             out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/*      */                             
/* 2720 */                             out.write("&attributeid=3200&alertconfigurl=");
/*      */                             
/* 2722 */                             out.write("')\">\t\t");
/*      */                             
/* 2724 */                             out.write(" </a>-->\n\n");
/* 2725 */                             if (!((String)pageContext.getAttribute("status")).equalsIgnoreCase("Disabled")) {
/* 2726 */                               out.write("\n\n\n<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2727 */                               out.print((String)pageContext.getAttribute("trapid"));
/* 2728 */                               out.write("&attributeid=3200&alertconfigurl=");
/* 2729 */                               out.print(java.net.URLEncoder.encode(thresholdurl));
/* 2730 */                               out.write("')\">\n\n\n\t\t");
/* 2731 */                               out.print(getSeverityImageForHealth(alert.getProperty((String)pageContext.getAttribute("trapid") + "#" + 3200)));
/* 2732 */                               out.write(" </a>&nbsp;&nbsp; \n\n\n\t");
/*      */                             } else {
/* 2734 */                               out.write("\n\n\t\t<a href=\"javascript:void(0)\" >\n\n\n\n\t\t");
/* 2735 */                               out.print(getSeverityImageForHealth("-1#3200"));
/* 2736 */                               out.write(" </a>&nbsp;&nbsp;\n\n\t\t");
/*      */                             }
/* 2738 */                             out.write("\n\t\n\t\t  <a class=\"");
/* 2739 */                             out.print(grayedLink);
/* 2740 */                             out.write("\" href=\"#\" onClick=\"setAsClear('");
/* 2741 */                             if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2743 */                             out.write(39);
/* 2744 */                             out.write(44);
/* 2745 */                             out.write(39);
/* 2746 */                             if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2748 */                             out.write(39);
/* 2749 */                             out.write(44);
/* 2750 */                             out.print(disabled);
/* 2751 */                             out.write(")\" >");
/* 2752 */                             out.print(FormatUtil.getString("am.webclient.alerttab.setasclear.text"));
/* 2753 */                             out.write("</a>\n\n\t\t</td>\t\n\t\t<td class=\"");
/* 2754 */                             out.print(bgcolor);
/* 2755 */                             out.write("\" align=\"center\">\n\t\t\t<a href='/adminAction.do?method=editTrapListener&trapid=");
/* 2756 */                             if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                             {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2758 */                             out.write("&edit=true'><img src=\"/images/icon_edit.gif\"  border=\"0\"></a>    \n\t\t</td>\t\n\t  </tr>\n  ");
/* 2759 */                             int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2760 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2764 */                         if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2772 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/*      */                       }
/*      */                       catch (Throwable _jspx_exception)
/*      */                       {
/*      */                         for (;;)
/*      */                         {
/* 2768 */                           int tmp3932_3931 = 0; int[] tmp3932_3929 = _jspx_push_body_count_c_005fforEach_005f0; int tmp3934_3933 = tmp3932_3929[tmp3932_3931];tmp3932_3929[tmp3932_3931] = (tmp3934_3933 - 1); if (tmp3934_3933 <= 0) break;
/* 2769 */                           out = _jspx_page_context.popBody(); }
/* 2770 */                         _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */                       } finally {
/* 2772 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 2773 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */                       }
/* 2775 */                       out.write(10);
/* 2776 */                       out.write(32);
/* 2777 */                       out.write(32);
/* 2778 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2779 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2783 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2784 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/* 2787 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2788 */                   out.write(10);
/* 2789 */                   out.write(32);
/* 2790 */                   out.write(32);
/*      */                   
/* 2792 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2793 */                   _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 2794 */                   _jspx_th_c_005fotherwise_005f5.setParent(_jspx_th_c_005fchoose_005f1);
/* 2795 */                   int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 2796 */                   if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */                     for (;;) {
/* 2798 */                       out.write("\n\t  <tr>\n\t\t<td colspan=\"7\" height=\"32\" class=\"bodytext\" align=\"center\">");
/* 2799 */                       out.print(FormatUtil.getString("am.webclient.traplistener.notrap"));
/* 2800 */                       out.write(". <a href=\"/adminAction.do?method=editTrapListener\" class=\"staticlinks\">");
/* 2801 */                       out.print(FormatUtil.getString("am.webclient.traplistener.newtrap"));
/* 2802 */                       out.write("</a></td>\n\t  </tr>\n  ");
/* 2803 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 2804 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2808 */                   if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 2809 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5); return;
/*      */                   }
/*      */                   
/* 2812 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 2813 */                   out.write(10);
/* 2814 */                   out.write(32);
/* 2815 */                   out.write(32);
/* 2816 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2817 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2821 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2822 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/* 2825 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2826 */               out.write("\n  <tr>\n  <tr class=\"tablebottom\">\n<td height=\"29\" colspan=\"7\" class=\"bodytext bg-lite\">");
/*      */               
/* 2828 */               ChooseTag _jspx_th_c_005fchoose_005f6 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2829 */               _jspx_th_c_005fchoose_005f6.setPageContext(_jspx_page_context);
/* 2830 */               _jspx_th_c_005fchoose_005f6.setParent(_jspx_th_tiles_005fput_005f0);
/* 2831 */               int _jspx_eval_c_005fchoose_005f6 = _jspx_th_c_005fchoose_005f6.doStartTag();
/* 2832 */               if (_jspx_eval_c_005fchoose_005f6 != 0) {
/*      */                 for (;;)
/*      */                 {
/* 2835 */                   WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2836 */                   _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2837 */                   _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f6);
/*      */                   
/* 2839 */                   _jspx_th_c_005fwhen_005f6.setTest("${!empty list}");
/* 2840 */                   int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2841 */                   if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                     for (;;) {
/* 2843 */                       out.write("&nbsp;&nbsp;<a href=\"javascript:deleteSelections();\" class=\"staticlinks\">");
/* 2844 */                       out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 2845 */                       out.write("</a>&nbsp;&nbsp;|&nbsp;");
/* 2846 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2847 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2851 */                   if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2852 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6); return;
/*      */                   }
/*      */                   
/* 2855 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2856 */                   if (_jspx_meth_c_005fotherwise_005f6(_jspx_th_c_005fchoose_005f6, _jspx_page_context))
/*      */                     return;
/* 2858 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f6.doAfterBody();
/* 2859 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2863 */               if (_jspx_th_c_005fchoose_005f6.doEndTag() == 5) {
/* 2864 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6); return;
/*      */               }
/*      */               
/* 2867 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f6);
/* 2868 */               out.write(32);
/* 2869 */               out.write("\n\t<a href=\"/adminAction.do?method=editTrapListener\" class=\"staticlinks\">");
/* 2870 */               out.print(FormatUtil.getString("am.webclient.traplistener.addnew"));
/* 2871 */               out.write("</a>&nbsp;&nbsp;</td>\t\n  </tr>\n  </tr>\n</table>\n</form>\n");
/* 2872 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f0.doAfterBody();
/* 2873 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2876 */             if (_jspx_eval_tiles_005fput_005f0 != 1) {
/* 2877 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2880 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2881 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2884 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f0);
/* 2885 */           out.write(10);
/* 2886 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2888 */           out.write("\n    ");
/* 2889 */           if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2891 */           out.write(10);
/* 2892 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2893 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 2897 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2898 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 2901 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2902 */         out.write(32);
/* 2903 */         out.write(10);
/*      */       }
/* 2905 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2906 */         out = _jspx_out;
/* 2907 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2908 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2909 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2912 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2918 */     PageContext pageContext = _jspx_page_context;
/* 2919 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2921 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2922 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2923 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 2925 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 2926 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2927 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 2929 */         out.write("\n\talertUser();\n\treturn;\n");
/* 2930 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2931 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2935 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2936 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2937 */       return true;
/*      */     }
/* 2939 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2940 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2945 */     PageContext pageContext = _jspx_page_context;
/* 2946 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2948 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2949 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2950 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 2952 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 2953 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2954 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 2956 */         out.write(" \n\talertUser();\n\treturn;\n\t");
/* 2957 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2958 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2962 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2963 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2964 */       return true;
/*      */     }
/* 2966 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fnotPresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2972 */     PageContext pageContext = _jspx_page_context;
/* 2973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2975 */     NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2976 */     _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2977 */     _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */     
/* 2979 */     _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2980 */     int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2981 */     if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */       for (;;) {
/* 2983 */         out.write(" \n\tif(!checkforOneSelected(document.form1,\"checkbox\"))\n\t{\n\t\talert('");
/* 2984 */         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2985 */           return true;
/* 2986 */         out.write("');\n\t\treturn;\n\t}\n\tif(confirm('");
/* 2987 */         if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_logic_005fnotPresent_005f0, _jspx_page_context))
/* 2988 */           return true;
/* 2989 */         out.write("'))\n    {\n\t\tdocument.form1.action=\"/adminAction.do?method=deleteTrapListener\";\n\t\tdocument.form1.method=\"Post\"\n\t\tdocument.form1.submit();\n\t}\n\t");
/* 2990 */         int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2991 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 2995 */     if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2996 */       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2997 */       return true;
/*      */     }
/* 2999 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 3000 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3005 */     PageContext pageContext = _jspx_page_context;
/* 3006 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3008 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3009 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3010 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3011 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3012 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3013 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3014 */         out = _jspx_page_context.pushBody();
/* 3015 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3016 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3019 */         out.write("am.webclient.traplistener.delete");
/* 3020 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3021 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3024 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3025 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3028 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3029 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3030 */       return true;
/*      */     }
/* 3032 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3033 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_logic_005fnotPresent_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3038 */     PageContext pageContext = _jspx_page_context;
/* 3039 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3041 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3042 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 3043 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_logic_005fnotPresent_005f0);
/* 3044 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 3045 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 3046 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3047 */         out = _jspx_page_context.pushBody();
/* 3048 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 3049 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3052 */         out.write("am.webclient.traplistener.confirmdelete");
/* 3053 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 3054 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3057 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 3058 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3061 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 3062 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3063 */       return true;
/*      */     }
/* 3065 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 3066 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f0(JspTag _jspx_th_tiles_005fput_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3071 */     PageContext pageContext = _jspx_page_context;
/* 3072 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3074 */     ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3075 */     _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3076 */     _jspx_th_c_005fchoose_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f0);
/* 3077 */     int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3078 */     if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */       for (;;) {
/* 3080 */         if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3081 */           return true;
/* 3082 */         if (_jspx_meth_c_005fotherwise_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/* 3083 */           return true;
/* 3084 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3085 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3089 */     if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3090 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3091 */       return true;
/*      */     }
/* 3093 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3094 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3099 */     PageContext pageContext = _jspx_page_context;
/* 3100 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3102 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3103 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3104 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3106 */     _jspx_th_c_005fwhen_005f0.setTest("${!empty list}");
/* 3107 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3108 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3110 */         out.write("<input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this,'checkbox');\">");
/* 3111 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3112 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3116 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3117 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3118 */       return true;
/*      */     }
/* 3120 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3121 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3126 */     PageContext pageContext = _jspx_page_context;
/* 3127 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3129 */     OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3130 */     _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3131 */     _jspx_th_c_005fotherwise_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/* 3132 */     int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3133 */     if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */       for (;;) {
/* 3135 */         out.write("&nbsp;");
/* 3136 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3137 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3141 */     if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3142 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3143 */       return true;
/*      */     }
/* 3145 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3146 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fwhen_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3151 */     PageContext pageContext = _jspx_page_context;
/* 3152 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3154 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3155 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3156 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fwhen_005f2);
/*      */     
/* 3158 */     _jspx_th_c_005fset_005f0.setVar("status");
/* 3159 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3160 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3161 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3162 */         out = _jspx_page_context.pushBody();
/* 3163 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3164 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3165 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3168 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3169 */           return true;
/* 3170 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3171 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3174 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3175 */         out = _jspx_page_context.popBody();
/* 3176 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3179 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3180 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3181 */       return true;
/*      */     }
/* 3183 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 3184 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3189 */     PageContext pageContext = _jspx_page_context;
/* 3190 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3192 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3193 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3194 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3196 */     _jspx_th_c_005fout_005f0.setValue("${props.TRAPSTATUS}");
/* 3197 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3198 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3199 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3200 */       return true;
/*      */     }
/* 3202 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3203 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3208 */     PageContext pageContext = _jspx_page_context;
/* 3209 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3211 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3212 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3213 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/* 3214 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3215 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3217 */         out.write(45);
/* 3218 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3219 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3223 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3224 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3225 */       return true;
/*      */     }
/* 3227 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3228 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3233 */     PageContext pageContext = _jspx_page_context;
/* 3234 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3236 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3237 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3238 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3240 */     _jspx_th_c_005fset_005f1.setVar("trapid");
/* 3241 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3242 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3243 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3244 */         out = _jspx_page_context.pushBody();
/* 3245 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3246 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3247 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3250 */         out.write(32);
/* 3251 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3252 */           return true;
/* 3253 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3254 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3257 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3258 */         out = _jspx_page_context.popBody();
/* 3259 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3262 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3263 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3264 */       return true;
/*      */     }
/* 3266 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 3267 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3272 */     PageContext pageContext = _jspx_page_context;
/* 3273 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3275 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3276 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3277 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3279 */     _jspx_th_c_005fout_005f1.setValue("${props.TRAPID}");
/* 3280 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3281 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3282 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3283 */       return true;
/*      */     }
/* 3285 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3291 */     PageContext pageContext = _jspx_page_context;
/* 3292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3294 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3295 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3296 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3298 */     _jspx_th_c_005fout_005f2.setValue("${props.TRAPID}");
/* 3299 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3300 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3301 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3302 */       return true;
/*      */     }
/* 3304 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3305 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3310 */     PageContext pageContext = _jspx_page_context;
/* 3311 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3313 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3314 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3315 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3317 */     _jspx_th_c_005fout_005f3.setValue("${props.TRAPID}");
/* 3318 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3319 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3320 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3321 */       return true;
/*      */     }
/* 3323 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3324 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3329 */     PageContext pageContext = _jspx_page_context;
/* 3330 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3332 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3333 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3334 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3336 */     _jspx_th_c_005fout_005f4.setValue("${props.TRAPNAME}");
/* 3337 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3338 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3339 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3340 */       return true;
/*      */     }
/* 3342 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3343 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fwhen_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3348 */     PageContext pageContext = _jspx_page_context;
/* 3349 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3351 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3352 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3353 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fwhen_005f3);
/*      */     
/* 3355 */     _jspx_th_c_005fout_005f5.setValue("${props.TRAPNAME}");
/* 3356 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3357 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3358 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3359 */       return true;
/*      */     }
/* 3361 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3362 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f2(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3367 */     PageContext pageContext = _jspx_page_context;
/* 3368 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3370 */     OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3371 */     _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3372 */     _jspx_th_c_005fotherwise_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3373 */     int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3374 */     if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */       for (;;) {
/* 3376 */         out.write(45);
/* 3377 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3378 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3382 */     if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3383 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3384 */       return true;
/*      */     }
/* 3386 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3392 */     PageContext pageContext = _jspx_page_context;
/* 3393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3395 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3396 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3397 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 3398 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3399 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 3401 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3402 */           return true;
/* 3403 */         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3404 */           return true;
/* 3405 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3406 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3410 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3411 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3412 */       return true;
/*      */     }
/* 3414 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3415 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3420 */     PageContext pageContext = _jspx_page_context;
/* 3421 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3423 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3424 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3425 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 3427 */     _jspx_th_c_005fwhen_005f4.setTest("${!empty props.TRAPVERSION}");
/* 3428 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3429 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 3431 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3432 */           return true;
/* 3433 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3434 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3438 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3439 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3440 */       return true;
/*      */     }
/* 3442 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3443 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3448 */     PageContext pageContext = _jspx_page_context;
/* 3449 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3451 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3452 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3453 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 3455 */     _jspx_th_c_005fout_005f6.setValue("${props.TRAPVERSION}");
/* 3456 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3457 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3458 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3459 */       return true;
/*      */     }
/* 3461 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3462 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3467 */     PageContext pageContext = _jspx_page_context;
/* 3468 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3470 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3471 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3472 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 3473 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3474 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3476 */         out.write(45);
/* 3477 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3478 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3482 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3483 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3484 */       return true;
/*      */     }
/* 3486 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3487 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3492 */     PageContext pageContext = _jspx_page_context;
/* 3493 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3495 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3496 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3497 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 3499 */     _jspx_th_c_005fset_005f2.setVar("severity");
/* 3500 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3501 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3502 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3503 */         out = _jspx_page_context.pushBody();
/* 3504 */         _jspx_push_body_count_c_005fforEach_005f0[0] += 1;
/* 3505 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3506 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3509 */         out.write(32);
/* 3510 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f2, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3511 */           return true;
/* 3512 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3513 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3516 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3517 */         out = _jspx_page_context.popBody();
/* 3518 */         _jspx_push_body_count_c_005fforEach_005f0[0] -= 1;
/*      */       }
/*      */     }
/* 3521 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3522 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3523 */       return true;
/*      */     }
/* 3525 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3526 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3531 */     PageContext pageContext = _jspx_page_context;
/* 3532 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3534 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3535 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3536 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 3538 */     _jspx_th_c_005fout_005f7.setValue("${props.TRAPSEVERITY}");
/* 3539 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3540 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3541 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3542 */       return true;
/*      */     }
/* 3544 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3545 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3550 */     PageContext pageContext = _jspx_page_context;
/* 3551 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3553 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3554 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3555 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 3556 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3557 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 3559 */         out.write(45);
/* 3560 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3561 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3565 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3566 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3567 */       return true;
/*      */     }
/* 3569 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3570 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3575 */     PageContext pageContext = _jspx_page_context;
/* 3576 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3578 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3579 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3580 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3582 */     _jspx_th_c_005fout_005f8.setValue("${props.TRAPID}");
/* 3583 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3584 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3585 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3586 */       return true;
/*      */     }
/* 3588 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3589 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3594 */     PageContext pageContext = _jspx_page_context;
/* 3595 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3597 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3598 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3599 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3601 */     _jspx_th_c_005fout_005f9.setValue("${props.TRAPNAME}");
/* 3602 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3603 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3604 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3605 */       return true;
/*      */     }
/* 3607 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3613 */     PageContext pageContext = _jspx_page_context;
/* 3614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3616 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3617 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3618 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3620 */     _jspx_th_c_005fout_005f10.setValue("${props.TRAPID}");
/* 3621 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3622 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3623 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3624 */       return true;
/*      */     }
/* 3626 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3627 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f6(JspTag _jspx_th_c_005fchoose_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3632 */     PageContext pageContext = _jspx_page_context;
/* 3633 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3635 */     OtherwiseTag _jspx_th_c_005fotherwise_005f6 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3636 */     _jspx_th_c_005fotherwise_005f6.setPageContext(_jspx_page_context);
/* 3637 */     _jspx_th_c_005fotherwise_005f6.setParent((Tag)_jspx_th_c_005fchoose_005f6);
/* 3638 */     int _jspx_eval_c_005fotherwise_005f6 = _jspx_th_c_005fotherwise_005f6.doStartTag();
/* 3639 */     if (_jspx_eval_c_005fotherwise_005f6 != 0) {
/*      */       for (;;) {
/* 3641 */         out.write("&nbsp;");
/* 3642 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f6.doAfterBody();
/* 3643 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3647 */     if (_jspx_th_c_005fotherwise_005f6.doEndTag() == 5) {
/* 3648 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3649 */       return true;
/*      */     }
/* 3651 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f6);
/* 3652 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3657 */     PageContext pageContext = _jspx_page_context;
/* 3658 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3660 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3661 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3662 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3664 */     _jspx_th_tiles_005fput_005f1.setName("HelpContent");
/*      */     
/* 3666 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/test.jsp");
/* 3667 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3668 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3669 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3670 */       return true;
/*      */     }
/* 3672 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3673 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3678 */     PageContext pageContext = _jspx_page_context;
/* 3679 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3681 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3682 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 3683 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3685 */     _jspx_th_tiles_005fput_005f2.setName("Footer");
/*      */     
/* 3687 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/footer.jsp");
/* 3688 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 3689 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3690 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3691 */       return true;
/*      */     }
/* 3693 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 3694 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\TrapListenerView_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */