/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.bean.WriteTag;
/*      */ import org.apache.struts.taglib.html.ButtonTag;
/*      */ import org.apache.struts.taglib.html.CheckboxTag;
/*      */ import org.apache.struts.taglib.html.FileTag;
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.HiddenTag;
/*      */ import org.apache.struts.taglib.html.MultiboxTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ 
/*      */ public final class RealBrowserSeleniumTCImporter_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   47 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   50 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   51 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   52 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   59 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   64 */     ArrayList list = null;
/*   65 */     StringBuffer sbf = new StringBuffer();
/*   66 */     com.adventnet.appmanager.client.resourcemanagement.ManagedApplication mo = new com.adventnet.appmanager.client.resourcemanagement.ManagedApplication();
/*   67 */     if (distinct)
/*      */     {
/*   69 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   73 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   76 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   78 */       ArrayList row = (ArrayList)list.get(i);
/*   79 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   80 */       if (distinct) {
/*   81 */         sbf.append(row.get(0));
/*      */       } else
/*   83 */         sbf.append(row.get(1));
/*   84 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   87 */     return sbf.toString(); }
/*      */   
/*   89 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   92 */     if (severity == null)
/*      */     {
/*   94 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   96 */     if (severity.equals("5"))
/*      */     {
/*   98 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  100 */     if (severity.equals("1"))
/*      */     {
/*  102 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  107 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  114 */     if (severity == null)
/*      */     {
/*  116 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  118 */     if (severity.equals("1"))
/*      */     {
/*  120 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  122 */     if (severity.equals("4"))
/*      */     {
/*  124 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  126 */     if (severity.equals("5"))
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  133 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  139 */     if (severity == null)
/*      */     {
/*  141 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  143 */     if (severity.equals("5"))
/*      */     {
/*  145 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  147 */     if (severity.equals("1"))
/*      */     {
/*  149 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  153 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  159 */     if (severity == null)
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  163 */     if (severity.equals("1"))
/*      */     {
/*  165 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  167 */     if (severity.equals("4"))
/*      */     {
/*  169 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  171 */     if (severity.equals("5"))
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  177 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  183 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  189 */     if (severity == 5)
/*      */     {
/*  191 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  193 */     if (severity == 1)
/*      */     {
/*  195 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  200 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  206 */     if (severity == null)
/*      */     {
/*  208 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  210 */     if (severity.equals("5"))
/*      */     {
/*  212 */       if (isAvailability) {
/*  213 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  216 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  219 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  221 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  223 */     if (severity.equals("1"))
/*      */     {
/*  225 */       if (isAvailability) {
/*  226 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  229 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  236 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  243 */     if (severity == null)
/*      */     {
/*  245 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  247 */     if (severity.equals("5"))
/*      */     {
/*  249 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  251 */     if (severity.equals("4"))
/*      */     {
/*  253 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  255 */     if (severity.equals("1"))
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  262 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  268 */     if (severity == null)
/*      */     {
/*  270 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  272 */     if (severity.equals("5"))
/*      */     {
/*  274 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  276 */     if (severity.equals("4"))
/*      */     {
/*  278 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  280 */     if (severity.equals("1"))
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  287 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  294 */     if (severity == null)
/*      */     {
/*  296 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  298 */     if (severity.equals("5"))
/*      */     {
/*  300 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  302 */     if (severity.equals("4"))
/*      */     {
/*  304 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  306 */     if (severity.equals("1"))
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  313 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  321 */     StringBuffer out = new StringBuffer();
/*  322 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  323 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  324 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  325 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  326 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  327 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  328 */     out.append("</tr>");
/*  329 */     out.append("</form></table>");
/*  330 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  337 */     if (val == null)
/*      */     {
/*  339 */       return "-";
/*      */     }
/*      */     
/*  342 */     String ret = FormatUtil.formatNumber(val);
/*  343 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  344 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  347 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  351 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  359 */     StringBuffer out = new StringBuffer();
/*  360 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  361 */     out.append("<tr>");
/*  362 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  364 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  366 */     out.append("</tr>");
/*  367 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  371 */       if (j % 2 == 0)
/*      */       {
/*  373 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  377 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  380 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  382 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  385 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  389 */       out.append("</tr>");
/*      */     }
/*  391 */     out.append("</table>");
/*  392 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  393 */     out.append("<tr>");
/*  394 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  395 */     out.append("</tr>");
/*  396 */     out.append("</table>");
/*  397 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  403 */     StringBuffer out = new StringBuffer();
/*  404 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  405 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  406 */     out.append("<tr>");
/*  407 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  408 */     out.append("<tr>");
/*  409 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  410 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  411 */     out.append("</tr>");
/*  412 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  415 */       out.append("<tr>");
/*  416 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  417 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  418 */       out.append("</tr>");
/*      */     }
/*      */     
/*  421 */     out.append("</table>");
/*  422 */     out.append("</table>");
/*  423 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  428 */     if (severity.equals("0"))
/*      */     {
/*  430 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  434 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  441 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  454 */     StringBuffer out = new StringBuffer();
/*  455 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  456 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  458 */       out.append("<tr>");
/*  459 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  460 */       out.append("</tr>");
/*      */       
/*      */ 
/*  463 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  465 */         String borderclass = "";
/*      */         
/*      */ 
/*  468 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  470 */         out.append("<tr>");
/*      */         
/*  472 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  473 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  474 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  480 */     out.append("</table><br>");
/*  481 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  482 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  484 */       List sLinks = secondLevelOfLinks[0];
/*  485 */       List sText = secondLevelOfLinks[1];
/*  486 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  489 */         out.append("<tr>");
/*  490 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  491 */         out.append("</tr>");
/*  492 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  494 */           String borderclass = "";
/*      */           
/*      */ 
/*  497 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  499 */           out.append("<tr>");
/*      */           
/*  501 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  502 */           if (sLinks.get(i).toString().length() == 0) {
/*  503 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  506 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  508 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  512 */     out.append("</table>");
/*  513 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  520 */     StringBuffer out = new StringBuffer();
/*  521 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  522 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  524 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  526 */         out.append("<tr>");
/*  527 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  528 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  532 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  534 */           String borderclass = "";
/*      */           
/*      */ 
/*  537 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  539 */           out.append("<tr>");
/*      */           
/*  541 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  542 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  543 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  546 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  549 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  554 */     out.append("</table><br>");
/*  555 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  556 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  558 */       List sLinks = secondLevelOfLinks[0];
/*  559 */       List sText = secondLevelOfLinks[1];
/*  560 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  563 */         out.append("<tr>");
/*  564 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  565 */         out.append("</tr>");
/*  566 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  568 */           String borderclass = "";
/*      */           
/*      */ 
/*  571 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  573 */           out.append("<tr>");
/*      */           
/*  575 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  576 */           if (sLinks.get(i).toString().length() == 0) {
/*  577 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  580 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  582 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  586 */     out.append("</table>");
/*  587 */     return out.toString();
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
/*  600 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  603 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  606 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  609 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  615 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  618 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  621 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  629 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  634 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  639 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  644 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  649 */     if (val != null)
/*      */     {
/*  651 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  655 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  660 */     if (val == null) {
/*  661 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  665 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  670 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  676 */     if (val != null)
/*      */     {
/*  678 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  682 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  688 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  693 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  697 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  702 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  707 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  712 */     String hostaddress = "";
/*  713 */     String ip = request.getHeader("x-forwarded-for");
/*  714 */     if (ip == null)
/*  715 */       ip = request.getRemoteAddr();
/*  716 */     java.net.InetAddress add = null;
/*  717 */     if (ip.equals("127.0.0.1")) {
/*  718 */       add = java.net.InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  722 */       add = java.net.InetAddress.getByName(ip);
/*      */     }
/*  724 */     hostaddress = add.getHostName();
/*  725 */     if (hostaddress.indexOf('.') != -1) {
/*  726 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  727 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  731 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  736 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  742 */     if (severity == null)
/*      */     {
/*  744 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  746 */     if (severity.equals("5"))
/*      */     {
/*  748 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  750 */     if (severity.equals("1"))
/*      */     {
/*  752 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  757 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  762 */     ResultSet set = null;
/*  763 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  764 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  766 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  767 */       if (set.next()) { String str1;
/*  768 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  769 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  772 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  777 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  780 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  782 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  786 */     StringBuffer rca = new StringBuffer();
/*  787 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  788 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  791 */     int rcalength = key.length();
/*  792 */     String split = "6. ";
/*  793 */     int splitPresent = key.indexOf(split);
/*  794 */     String div1 = "";String div2 = "";
/*  795 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  797 */       if (rcalength > 180) {
/*  798 */         rca.append("<span class=\"rca-critical-text\">");
/*  799 */         getRCATrimmedText(key, rca);
/*  800 */         rca.append("</span>");
/*      */       } else {
/*  802 */         rca.append("<span class=\"rca-critical-text\">");
/*  803 */         rca.append(key);
/*  804 */         rca.append("</span>");
/*      */       }
/*  806 */       return rca.toString();
/*      */     }
/*  808 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  809 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  810 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  811 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  812 */     getRCATrimmedText(div1, rca);
/*  813 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  816 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  817 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  818 */     getRCATrimmedText(div2, rca);
/*  819 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  821 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  826 */     String[] st = msg.split("<br>");
/*  827 */     for (int i = 0; i < st.length; i++) {
/*  828 */       String s = st[i];
/*  829 */       if (s.length() > 180) {
/*  830 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  832 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  836 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  837 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  839 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  843 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  844 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  845 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  848 */       if (key == null) {
/*  849 */         return ret;
/*      */       }
/*      */       
/*  852 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  853 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  856 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  857 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  858 */       set = AMConnectionPool.executeQueryStmt(query);
/*  859 */       if (set.next())
/*      */       {
/*  861 */         String helpLink = set.getString("LINK");
/*  862 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  865 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  871 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  890 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  881 */         if (set != null) {
/*  882 */           AMConnectionPool.closeStatement(set);
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
/*  896 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  897 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  899 */       String entityStr = (String)keys.nextElement();
/*  900 */       String mmessage = temp.getProperty(entityStr);
/*  901 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  902 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  904 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  910 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  911 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  913 */       String entityStr = (String)keys.nextElement();
/*  914 */       String mmessage = temp.getProperty(entityStr);
/*  915 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  916 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  918 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  923 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  933 */     String des = new String();
/*  934 */     while (str.indexOf(find) != -1) {
/*  935 */       des = des + str.substring(0, str.indexOf(find));
/*  936 */       des = des + replace;
/*  937 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  939 */     des = des + str;
/*  940 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  947 */       if (alert == null)
/*      */       {
/*  949 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  951 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  953 */         return "&nbsp;";
/*      */       }
/*      */       
/*  956 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  958 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  961 */       int rcalength = test.length();
/*  962 */       if (rcalength < 300)
/*      */       {
/*  964 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  968 */       StringBuffer out = new StringBuffer();
/*  969 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  970 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  971 */       out.append("</div>");
/*  972 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  973 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  974 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  979 */       ex.printStackTrace();
/*      */     }
/*  981 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  987 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  992 */     ArrayList attribIDs = new ArrayList();
/*  993 */     ArrayList resIDs = new ArrayList();
/*  994 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  996 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/*  998 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1000 */       String resourceid = "";
/* 1001 */       String resourceType = "";
/* 1002 */       if (type == 2) {
/* 1003 */         resourceid = (String)row.get(0);
/* 1004 */         resourceType = (String)row.get(3);
/*      */       }
/* 1006 */       else if (type == 3) {
/* 1007 */         resourceid = (String)row.get(0);
/* 1008 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1011 */         resourceid = (String)row.get(6);
/* 1012 */         resourceType = (String)row.get(7);
/*      */       }
/* 1014 */       resIDs.add(resourceid);
/* 1015 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1016 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1018 */       String healthentity = null;
/* 1019 */       String availentity = null;
/* 1020 */       if (healthid != null) {
/* 1021 */         healthentity = resourceid + "_" + healthid;
/* 1022 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1025 */       if (availid != null) {
/* 1026 */         availentity = resourceid + "_" + availid;
/* 1027 */         entitylist.add(availentity);
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
/* 1041 */     Properties alert = getStatus(entitylist);
/* 1042 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1047 */     int size = monitorList.size();
/*      */     
/* 1049 */     String[] severity = new String[size];
/*      */     
/* 1051 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1053 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1054 */       String resourceName1 = (String)row1.get(7);
/* 1055 */       String resourceid1 = (String)row1.get(6);
/* 1056 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1057 */       if (severity[j] == null)
/*      */       {
/* 1059 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1063 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1065 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1067 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1070 */         if (sev > 0) {
/* 1071 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1072 */           monitorList.set(k, monitorList.get(j));
/* 1073 */           monitorList.set(j, t);
/* 1074 */           String temp = severity[k];
/* 1075 */           severity[k] = severity[j];
/* 1076 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1082 */     int z = 0;
/* 1083 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1086 */       int i = 0;
/* 1087 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1090 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1094 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1098 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1100 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1103 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1107 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1110 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1111 */       String resourceName1 = (String)row1.get(7);
/* 1112 */       String resourceid1 = (String)row1.get(6);
/* 1113 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1114 */       if (hseverity[j] == null)
/*      */       {
/* 1116 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1121 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1123 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1126 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1129 */         if (hsev > 0) {
/* 1130 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1131 */           monitorList.set(k, monitorList.get(j));
/* 1132 */           monitorList.set(j, t);
/* 1133 */           String temp1 = hseverity[k];
/* 1134 */           hseverity[k] = hseverity[j];
/* 1135 */           hseverity[j] = temp1;
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
/* 1147 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1148 */     boolean forInventory = false;
/* 1149 */     String trdisplay = "none";
/* 1150 */     String plusstyle = "inline";
/* 1151 */     String minusstyle = "none";
/* 1152 */     String haidTopLevel = "";
/* 1153 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1155 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1157 */         haidTopLevel = request.getParameter("haid");
/* 1158 */         forInventory = true;
/* 1159 */         trdisplay = "table-row;";
/* 1160 */         plusstyle = "none";
/* 1161 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1168 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1171 */     ArrayList listtoreturn = new ArrayList();
/* 1172 */     StringBuffer toreturn = new StringBuffer();
/* 1173 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1174 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1175 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1177 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1179 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1180 */       String childresid = (String)singlerow.get(0);
/* 1181 */       String childresname = (String)singlerow.get(1);
/* 1182 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1183 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1184 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1185 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1186 */       String unmanagestatus = (String)singlerow.get(5);
/* 1187 */       String actionstatus = (String)singlerow.get(6);
/* 1188 */       String linkclass = "monitorgp-links";
/* 1189 */       String titleforres = childresname;
/* 1190 */       String titilechildresname = childresname;
/* 1191 */       String childimg = "/images/trcont.png";
/* 1192 */       String flag = "enable";
/* 1193 */       String dcstarted = (String)singlerow.get(8);
/* 1194 */       String configMonitor = "";
/* 1195 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1196 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1198 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1200 */       if (singlerow.get(7) != null)
/*      */       {
/* 1202 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1204 */       String haiGroupType = "0";
/* 1205 */       if ("HAI".equals(childtype))
/*      */       {
/* 1207 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1209 */       childimg = "/images/trend.png";
/* 1210 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1211 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1212 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1214 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1216 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1218 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1219 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1222 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1224 */         linkclass = "disabledtext";
/* 1225 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1227 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1228 */       String availmouseover = "";
/* 1229 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1231 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1233 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1234 */       String healthmouseover = "";
/* 1235 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1237 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1240 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1241 */       int spacing = 0;
/* 1242 */       if (level >= 1)
/*      */       {
/* 1244 */         spacing = 40 * level;
/*      */       }
/* 1246 */       if (childtype.equals("HAI"))
/*      */       {
/* 1248 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1249 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1250 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1252 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1253 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1254 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1255 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1256 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1257 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1258 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1259 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1260 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1261 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1262 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1264 */         if (!forInventory)
/*      */         {
/* 1266 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1269 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1271 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1273 */           actions = editlink + actions;
/*      */         }
/* 1275 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1277 */           actions = actions + associatelink;
/*      */         }
/* 1279 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1280 */         String arrowimg = "";
/* 1281 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1283 */           actions = "";
/* 1284 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1285 */           checkbox = "";
/* 1286 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1288 */         if (isIt360)
/*      */         {
/* 1290 */           actionimg = "";
/* 1291 */           actions = "";
/* 1292 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1293 */           checkbox = "";
/*      */         }
/*      */         
/* 1296 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1298 */           actions = "";
/*      */         }
/* 1300 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1302 */           checkbox = "";
/*      */         }
/*      */         
/* 1305 */         String resourcelink = "";
/*      */         
/* 1307 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1309 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1313 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1316 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1317 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1318 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1322 */         if (!isIt360)
/*      */         {
/* 1324 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1328 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1331 */         toreturn.append("</tr>");
/* 1332 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1334 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1335 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1339 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1340 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1343 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1347 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1349 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1350 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1351 */             toreturn.append(assocMessage);
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1353 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1361 */         String resourcelink = null;
/* 1362 */         boolean hideEditLink = false;
/* 1363 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1365 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1366 */           hideEditLink = true;
/* 1367 */           if (isIt360)
/*      */           {
/* 1369 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1373 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1375 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1377 */           hideEditLink = true;
/* 1378 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1379 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1384 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1387 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1388 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1389 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1390 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1391 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1392 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1393 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1394 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1395 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1396 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1397 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1398 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1399 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1401 */         if (hideEditLink)
/*      */         {
/* 1403 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1405 */         if (!forInventory)
/*      */         {
/* 1407 */           removefromgroup = "";
/*      */         }
/* 1409 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1410 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1411 */           actions = actions + configcustomfields;
/*      */         }
/* 1413 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1415 */           actions = editlink + actions;
/*      */         }
/* 1417 */         String managedLink = "";
/* 1418 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1420 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1421 */           actions = "";
/* 1422 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1423 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1426 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1428 */           checkbox = "";
/*      */         }
/*      */         
/* 1431 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1433 */           actions = "";
/*      */         }
/* 1435 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1436 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1437 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1438 */         if (isIt360)
/*      */         {
/* 1440 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1444 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1447 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1448 */         if (!isIt360)
/*      */         {
/* 1450 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1454 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1456 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1459 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1466 */       StringBuilder toreturn = new StringBuilder();
/* 1467 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1468 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1469 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1470 */       String title = "";
/* 1471 */       message = EnterpriseUtil.decodeString(message);
/* 1472 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1473 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1474 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1476 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1478 */       else if ("5".equals(severity))
/*      */       {
/* 1480 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1484 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1486 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1487 */       toreturn.append(v);
/*      */       
/* 1489 */       toreturn.append(link);
/* 1490 */       if (severity == null)
/*      */       {
/* 1492 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1494 */       else if (severity.equals("5"))
/*      */       {
/* 1496 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1498 */       else if (severity.equals("4"))
/*      */       {
/* 1500 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1502 */       else if (severity.equals("1"))
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       toreturn.append("</a>");
/* 1512 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1516 */       ex.printStackTrace();
/*      */     }
/* 1518 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1525 */       StringBuilder toreturn = new StringBuilder();
/* 1526 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1527 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1528 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1529 */       if (message == null)
/*      */       {
/* 1531 */         message = "";
/*      */       }
/*      */       
/* 1534 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1535 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1537 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1538 */       toreturn.append(v);
/*      */       
/* 1540 */       toreturn.append(link);
/*      */       
/* 1542 */       if (severity == null)
/*      */       {
/* 1544 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1546 */       else if (severity.equals("5"))
/*      */       {
/* 1548 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1550 */       else if (severity.equals("1"))
/*      */       {
/* 1552 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       toreturn.append("</a>");
/* 1560 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1566 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1569 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1570 */     if (invokeActions != null) {
/* 1571 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1572 */       while (iterator.hasNext()) {
/* 1573 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1574 */         if (actionmap.containsKey(actionid)) {
/* 1575 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1580 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1584 */     String actionLink = "";
/* 1585 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1586 */     String query = "";
/* 1587 */     ResultSet rs = null;
/* 1588 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1589 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1590 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1591 */       actionLink = "method=" + methodName;
/*      */     }
/* 1593 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1594 */       actionLink = methodName;
/*      */     }
/* 1596 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1597 */     Iterator itr = methodarglist.iterator();
/* 1598 */     boolean isfirstparam = true;
/* 1599 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1600 */     while (itr.hasNext()) {
/* 1601 */       HashMap argmap = (HashMap)itr.next();
/* 1602 */       String argtype = (String)argmap.get("TYPE");
/* 1603 */       String argname = (String)argmap.get("IDENTITY");
/* 1604 */       String paramname = (String)argmap.get("PARAMETER");
/* 1605 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1606 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1607 */         isfirstparam = false;
/* 1608 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1610 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1614 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1618 */         actionLink = actionLink + "&";
/*      */       }
/* 1620 */       String paramValue = null;
/* 1621 */       String tempargname = argname;
/* 1622 */       if (commonValues.getProperty(tempargname) != null) {
/* 1623 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1626 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1627 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1628 */           if (dbType.equals("mysql")) {
/* 1629 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1632 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1634 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1636 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1637 */             if (rs.next()) {
/* 1638 */               paramValue = rs.getString("VALUE");
/* 1639 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (java.sql.SQLException e) {
/* 1643 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1647 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1650 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1655 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1656 */           paramValue = rowId;
/*      */         }
/* 1658 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1659 */           paramValue = managedObjectName;
/*      */         }
/* 1661 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1662 */           paramValue = resID;
/*      */         }
/* 1664 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1665 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1668 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1670 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1671 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1672 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1674 */     return actionLink;
/*      */   }
/*      */   
/* 1677 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1678 */     String dependentAttribute = null;
/* 1679 */     String align = "left";
/*      */     
/* 1681 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1682 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1683 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1684 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1685 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1686 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1687 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1688 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1689 */       align = "center";
/*      */     }
/*      */     
/* 1692 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1693 */     String actualdata = "";
/*      */     
/* 1695 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1696 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1697 */         actualdata = availValue;
/*      */       }
/* 1699 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1700 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1704 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1705 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1708 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1714 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1715 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1716 */       toreturn.append("<table>");
/* 1717 */       toreturn.append("<tr>");
/* 1718 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1719 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1720 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1721 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1722 */         String toolTip = "";
/* 1723 */         String hideClass = "";
/* 1724 */         String textStyle = "";
/* 1725 */         boolean isreferenced = true;
/* 1726 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1727 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1728 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1729 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1731 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1732 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1733 */           while (valueList.hasMoreTokens()) {
/* 1734 */             String dependentVal = valueList.nextToken();
/* 1735 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1736 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1737 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1739 */               toolTip = "";
/* 1740 */               hideClass = "";
/* 1741 */               isreferenced = false;
/* 1742 */               textStyle = "disabledtext";
/* 1743 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1747 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1748 */           toolTip = "";
/* 1749 */           hideClass = "";
/* 1750 */           isreferenced = false;
/* 1751 */           textStyle = "disabledtext";
/* 1752 */           if (dependentImageMap != null) {
/* 1753 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1754 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1757 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1761 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1762 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1763 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1764 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1765 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1766 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1768 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1769 */           if (isreferenced) {
/* 1770 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1774 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1775 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1776 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1777 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1778 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1779 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1781 */           toreturn.append("</span>");
/* 1782 */           toreturn.append("</a>");
/* 1783 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1786 */       toreturn.append("</tr>");
/* 1787 */       toreturn.append("</table>");
/* 1788 */       toreturn.append("</td>");
/*      */     } else {
/* 1790 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1793 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1797 */     String colTime = null;
/* 1798 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1799 */     if ((rows != null) && (rows.size() > 0)) {
/* 1800 */       Iterator<String> itr = rows.iterator();
/* 1801 */       String maxColQuery = "";
/* 1802 */       for (;;) { if (itr.hasNext()) {
/* 1803 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1804 */           ResultSet maxCol = null;
/*      */           try {
/* 1806 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1807 */             while (maxCol.next()) {
/* 1808 */               if (colTime == null) {
/* 1809 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1812 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1821 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1823 */               if (maxCol != null)
/* 1824 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1826 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1821 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1823 */               if (maxCol != null)
/* 1824 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1826 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1831 */     return colTime;
/*      */   }
/*      */   
/* 1834 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1835 */     tablename = null;
/* 1836 */     ResultSet rsTable = null;
/* 1837 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1839 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1840 */       while (rsTable.next()) {
/* 1841 */         tablename = rsTable.getString("DATATABLE");
/* 1842 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1843 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1856 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1847 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1850 */         if (rsTable != null)
/* 1851 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1853 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1859 */     String argsList = "";
/* 1860 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1862 */       if (showArgsMap.get(row) != null) {
/* 1863 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1864 */         if (showArgslist != null) {
/* 1865 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1866 */             if (argsList.trim().equals("")) {
/* 1867 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1870 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1877 */       e.printStackTrace();
/* 1878 */       return "";
/*      */     }
/* 1880 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1885 */     String argsList = "";
/* 1886 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1889 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1891 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1892 */         if (hideArgsList != null)
/*      */         {
/* 1894 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1896 */             if (argsList.trim().equals(""))
/*      */             {
/* 1898 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1902 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1910 */       ex.printStackTrace();
/*      */     }
/* 1912 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1916 */     StringBuilder toreturn = new StringBuilder();
/* 1917 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1924 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1925 */       Iterator itr = tActionList.iterator();
/* 1926 */       while (itr.hasNext()) {
/* 1927 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1928 */         String confirmmsg = "";
/* 1929 */         String link = "";
/* 1930 */         String isJSP = "NO";
/* 1931 */         HashMap tactionMap = (HashMap)itr.next();
/* 1932 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1933 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1934 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1935 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1936 */           (actionmap.containsKey(actionId))) {
/* 1937 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1938 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1939 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1940 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1941 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1943 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1949 */           if (isTableAction) {
/* 1950 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1953 */             tableName = "Link";
/* 1954 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1955 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1956 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1957 */             toreturn.append("</a></td>");
/*      */           }
/* 1959 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1960 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1968 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1974 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1976 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1977 */       Properties prop = (Properties)node.getUserObject();
/* 1978 */       String mgID = prop.getProperty("label");
/* 1979 */       String mgName = prop.getProperty("value");
/* 1980 */       String isParent = prop.getProperty("isParent");
/* 1981 */       int mgIDint = Integer.parseInt(mgID);
/* 1982 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1984 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1986 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1987 */       if (node.getChildCount() > 0)
/*      */       {
/* 1989 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1991 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1993 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1995 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 1999 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2004 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2006 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2008 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2010 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2014 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2017 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2018 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2020 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2024 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2026 */       if (node.getChildCount() > 0)
/*      */       {
/* 2028 */         builder.append("<UL>");
/* 2029 */         printMGTree(node, builder);
/* 2030 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2035 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2036 */     StringBuffer toReturn = new StringBuffer();
/* 2037 */     String table = "-";
/*      */     try {
/* 2039 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2040 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2041 */       float total = 0.0F;
/* 2042 */       while (it.hasNext()) {
/* 2043 */         String attName = (String)it.next();
/* 2044 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2045 */         boolean roundOffData = false;
/* 2046 */         if ((data != null) && (!data.equals(""))) {
/* 2047 */           if (data.indexOf(",") != -1) {
/* 2048 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2051 */             float value = Float.parseFloat(data);
/* 2052 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2055 */             total += value;
/* 2056 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2059 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2064 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2065 */       while (attVsWidthList.hasNext()) {
/* 2066 */         String attName = (String)attVsWidthList.next();
/* 2067 */         String data = (String)attVsWidthProps.get(attName);
/* 2068 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2069 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2070 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2071 */         String className = (String)graphDetails.get("ClassName");
/* 2072 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2073 */         if (percentage < 1.0F)
/*      */         {
/* 2075 */           data = percentage + "";
/*      */         }
/* 2077 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2079 */       if (toReturn.length() > 0) {
/* 2080 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2084 */       e.printStackTrace();
/*      */     }
/* 2086 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2092 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2093 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2094 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2095 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2096 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2097 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2098 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2099 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2100 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2103 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2104 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2105 */       splitvalues[0] = multiplecondition.toString();
/* 2106 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2109 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2114 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2115 */     if (thresholdType != 3) {
/* 2116 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2117 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2118 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2119 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2120 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2121 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2123 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2124 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2125 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2126 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2127 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2128 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2130 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2131 */     if (updateSelected != null) {
/* 2132 */       updateSelected[0] = "selected";
/*      */     }
/* 2134 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2139 */       StringBuffer toreturn = new StringBuffer("");
/* 2140 */       if (commaSeparatedMsgId != null) {
/* 2141 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2142 */         int count = 0;
/* 2143 */         while (msgids.hasMoreTokens()) {
/* 2144 */           String id = msgids.nextToken();
/* 2145 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2146 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2147 */           count++;
/* 2148 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2149 */             if (toreturn.length() == 0) {
/* 2150 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2152 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2153 */             if (!image.trim().equals("")) {
/* 2154 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2156 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2157 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2160 */         if (toreturn.length() > 0) {
/* 2161 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2165 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2168 */       e.printStackTrace(); }
/* 2169 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2175 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2181 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2182 */   static { _jspx_dependants.put("/jsp/includes/agentLocations.jspf", Long.valueOf(1473429417000L));
/* 2183 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005fenctype_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2207 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2211 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005fenctype_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2228 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2232 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2233 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2234 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2236 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005fenctype_005faction.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.release();
/* 2242 */     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.release();
/* 2243 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.release();
/* 2244 */     this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.release();
/* 2245 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.release();
/* 2246 */     this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.release();
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
/* 2274 */       out.write("\n\n\n\n\n\n\n");
/* 2275 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2277 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2279 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2288 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2289 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2290 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2293 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2294 */         String available = null;
/* 2295 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2296 */         out.write(10);
/*      */         
/* 2298 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2300 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2309 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2310 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2311 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2314 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2315 */           String unavailable = null;
/* 2316 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2317 */           out.write(10);
/*      */           
/* 2319 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2321 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2330 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2331 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2332 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2335 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2336 */             String unmanaged = null;
/* 2337 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2338 */             out.write(10);
/*      */             
/* 2340 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2342 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2351 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2352 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2353 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2356 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2357 */               String scheduled = null;
/* 2358 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2359 */               out.write(10);
/*      */               
/* 2361 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2363 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2372 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2373 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2374 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2377 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2378 */                 String critical = null;
/* 2379 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2380 */                 out.write(10);
/*      */                 
/* 2382 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2384 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2393 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2394 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2395 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2398 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2399 */                   String clear = null;
/* 2400 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2401 */                   out.write(10);
/*      */                   
/* 2403 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2405 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2414 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2415 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2416 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2419 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2420 */                     String warning = null;
/* 2421 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2422 */                     out.write(10);
/* 2423 */                     out.write(10);
/*      */                     
/* 2425 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2426 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/* 2430 */                     out.write(10);
/* 2431 */                     out.write("\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
/*      */                     
/* 2433 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2434 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2435 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2437 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2438 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2439 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2441 */                         out.write(10);
/*      */                         
/* 2443 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2444 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2445 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2447 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2449 */                         _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.rbmurlmonitor.type.text"));
/* 2450 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2451 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2452 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2455 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2456 */                         out.write(10);
/* 2457 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2459 */                         out.write(10);
/*      */                         
/* 2461 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2462 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2463 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2465 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2467 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2468 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2469 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2470 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2471 */                             out = _jspx_page_context.pushBody();
/* 2472 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2473 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2476 */                             out.write("\n<html>\n<head>\n\t<link href=\"/images/commonstyle.css\" rel=\"stylesheet\" type=\"text/css\">\n\t<link href=\"/images/");
/* 2477 */                             if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                               return;
/* 2479 */                             out.write("/style.css\" rel=\"stylesheet\" type=\"text/css\">\n        <style>\n    \t.lrborder,.lrbborder {\n\t\t\tborder:1px solid #e3e3e3 !important;\n\t\t}\n\t\n\t\t.tablebottom {\n\t\t\tbackground-color:#f6f6f6;\n\t\t\tborder:none;\n\t\t}\n\t\t.tableheading-monitor-config {\n\t\t\tborder: none;\n\t\t}\n    </style>\n\t\n\t");
/* 2480 */                             String fromWhere = "CONF";
/* 2481 */                             request.setAttribute("isRBM", "true");
/* 2482 */                             out.write("\n</head>\n<body>\n");
/*      */                             
/* 2484 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005fenctype_005faction.get(FormTag.class);
/* 2485 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2486 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                             
/* 2488 */                             _jspx_th_html_005fform_005f0.setAction("/SeleniumActions.do");
/*      */                             
/* 2490 */                             _jspx_th_html_005fform_005f0.setMethod("post");
/*      */                             
/* 2492 */                             _jspx_th_html_005fform_005f0.setEnctype("multipart/form-data");
/* 2493 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2494 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2496 */                                 out.write("\n\n<div class=\"notification\" style=\"display:none\" id=\"msgDiv\">\n\t<div class=\"message\"><span><img src=\"/images/icon_cogwheel.gif\" width=\"20\" height=\"20\" class=\"info-icon\">");
/* 2497 */                                 out.print(FormatUtil.getString("am.webclient.rbm.prompt.testPlayback2"));
/* 2498 */                                 out.write("</span></div>\n</div>\n\n");
/* 2499 */                                 if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2501 */                                 out.write(10);
/* 2502 */                                 out.write(10);
/* 2503 */                                 if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2505 */                                 out.write("\n  \n <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n \t<tr>\n \t\t<td width=\"65%\" valign=\"top\">\n \t\t\t<table id=\"newResourceTypes\" class=\"lrborder\" width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n \t\t\t\t<tr>\n \t\t\t\t\t<td class=\"tableheading-monitor-config bodytext addmonitor-label\" width=\"25%\" colspan=\"2\">");
/* 2506 */                                 out.print(FormatUtil.getString("am.webclient.seleniumide.tc.import"));
/* 2507 */                                 out.write("</td>\n \t\t\t\t</tr>\n \t\t\t</table>\n \t\t\t\n \t\t\t<table class=\"lrborder\" width=\"99%\" cellspacing=\"1\" cellpadding=\"6\" border=\"0\">\n \t\t\t\t<tbody>\n \t\t\t\t\t<tr> \n \t\t\t\t\t\t<td class=\"bodytext label-align addmonitor-label\" height=\"60\" width=\"40%\">\n \t\t\t\t\t\t\t<lable>");
/* 2508 */                                 out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2509 */                                 out.write("</lable> ");
/* 2510 */                                 out.write("\n \t\t\t\t\t\t\t<span class=\"mandatory\">*</span>\n \t\t\t\t\t\t</td>\n \t\t\t\t\t\t\n \t\t\t\t\t\t<td height=\"25\" class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 2511 */                                 if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2513 */                                 out.write("\n\t\t\t\t\t\t</td>\n \t\t\t\t\t</tr>\n \t\t\t\t\t<tr>\n \t\t\t\t\t\t<td class=\"bodytext label-align addmonitor-label\" height=\"20\">\n \t\t\t\t\t\t\t<lable>");
/* 2514 */                                 out.print(FormatUtil.getString("am.webclient.fileupload.filetoupload.text"));
/* 2515 */                                 out.write("</lable> ");
/* 2516 */                                 out.write("\n \t\t\t\t\t\t\t<span class=\"mandatory\">*</span>\n \t\t\t\t\t\t</td>\n \t\t\t\t\t\t\n \t\t\t\t\t\t<td height=\"25\" class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 2517 */                                 if (_jspx_meth_html_005ffile_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2519 */                                 out.write("\n\t\t\t\t\t\t</td>\n \t\t\t\t\t</tr>\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"bodytext label-align addmonitor-label\" height=\"20\">\n \t\t\t\t\t\t\t<lable>");
/* 2520 */                                 out.print(FormatUtil.getString("am.webclient.common.pollinginterval.text"));
/* 2521 */                                 out.write("</lable>  ");
/* 2522 */                                 out.write("\n \t\t\t\t\t\t\t<span class=\"mandatory\">*</span>\n \t\t\t\t\t\t</td>\n \t\t\t\t\t\t<td height=\"25\" class=\"bodytext\">\n\t\t\t\t\t\t\t");
/* 2523 */                                 if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2525 */                                 out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"2\"></td>\n\t\t\t\t\t</tr>\n \t\t\t\t</tbody>\n \t\t\t</table>\n\t\t\t");
/* 2526 */                                 out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n");
/*      */                                 
/* 2528 */                                 String isRBM = (String)request.getAttribute("isRBM");
/* 2529 */                                 String selQuery = "select AGENTID,DISPLAYNAME,STATUS,DESCRIPTION,AGENTVERSION from AM_RBMAGENTDATA where AGENTID >" + EnterpriseUtil.getDistributedStartResourceId();
/* 2530 */                                 if ((isRBM != null) && (isRBM.equals("true")))
/*      */                                 {
/* 2532 */                                   selQuery = "select AGENTID,DISPLAYNAME,STATUS,DESCRIPTION,AGENTVERSION from AM_RBMAGENTDATA where AGENTID NOT IN(" + EnterpriseUtil.getDistributedStartResourceId() + ") and AGENTNAME NOT LIKE ('%(Local)')";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 2536 */                                   isRBM = "false";
/*      */                                 }
/* 2538 */                                 System.out.println("is RBM : " + isRBM);
/* 2539 */                                 ArrayList agentLocation = new ArrayList();
/* 2540 */                                 ArrayList agentStatus = new ArrayList();
/* 2541 */                                 ArrayList<String> isDisabled = new ArrayList();
/* 2542 */                                 ResultSet rs = AMConnectionPool.executeQueryStmt(selQuery);
/* 2543 */                                 while (rs.next())
/*      */                                 {
/* 2545 */                                   String agentId = rs.getString("AGENTID");
/* 2546 */                                   String displayName = FormatUtil.getTrimmedText(rs.getString("DISPLAYNAME"), 23);
/* 2547 */                                   String version = rs.getString("AGENTVERSION");
/* 2548 */                                   if (isRBM.equals("true"))
/*      */                                   {
/* 2550 */                                     String browserType = "1";
/* 2551 */                                     Properties argProps = (Properties)request.getAttribute("argsasprops");
/*      */                                     try {
/* 2553 */                                       browserType = argProps.getProperty("browserType");
/*      */                                     }
/*      */                                     catch (NullPointerException npe) {}
/* 2556 */                                     String desc = rs.getString("DESCRIPTION");
/* 2557 */                                     if (("1".equals(browserType)) && (version != null) && (Integer.parseInt(version.replace(".", "")) <= com.adventnet.appmanager.util.Constants.eumAppAgent))
/*      */                                     {
/* 2559 */                                       isDisabled.add("true");
/*      */                                     } else {
/* 2561 */                                       isDisabled.add("false");
/*      */                                     }
/*      */                                   } else {
/* 2564 */                                     isDisabled.add("false");
/*      */                                   }
/* 2566 */                                   agentLocation.add(new org.apache.struts.util.LabelValueBean(displayName, agentId));
/* 2567 */                                   agentStatus.add(rs.getString("STATUS"));
/*      */                                 }
/*      */                                 
/* 2570 */                                 pageContext.setAttribute("agentLocation", agentLocation);
/* 2571 */                                 String disable = "";
/* 2572 */                                 String licenseTooltip = "";
/* 2573 */                                 String hideClass = "";
/* 2574 */                                 if ((!com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails().isEUMAllowed()) || (EnterpriseUtil.isCloudEdition())) {
/* 2575 */                                   disable = "disabled='disabled'";
/* 2576 */                                   licenseTooltip = FormatUtil.getString("am.webclient.eumdashboard.license.addon");
/* 2577 */                                   hideClass = "hideddrivetip()";
/*      */                                 }
/*      */                                 
/*      */ 
/* 2581 */                                 out.write("\n\n\n<style type=\"text/css\">\n.upgradeAmountBg1 {\n\tbackground-color: #f7f7f7;\n}\n\n.labeltd {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #333333;\n\theight: 25px;\n\tpadding-left: 10px;\n}\n</style>\n\n\n<table width='");
/* 2582 */                                 out.print(fromWhere.equals("CONF") ? "99%" : "100%");
/* 2583 */                                 out.write("' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class='");
/* 2584 */                                 out.print(fromWhere.equals("CONF") ? "lrbborder" : "");
/* 2585 */                                 out.write("'>\n\t\n\t<tr>\n\t\t<td height=\"25\" class=\"plainheading1\">");
/* 2586 */                                 out.print(FormatUtil.getString("am.webclient.eumagent.associate"));
/* 2587 */                                 out.write("</td>\n\t</tr>\n\t<tr height=\"25\">\n\t\t<td style=\"font-size: 12px;padding: 10px;\" nowrap>\n\t\t\t");
/* 2588 */                                 if (fromWhere.equals("CONF")) {
/* 2589 */                                   out.write("\n\t\t\t");
/*      */                                   
/* 2591 */                                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2592 */                                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2593 */                                   _jspx_th_c_005fif_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2595 */                                   _jspx_th_c_005fif_005f2.setTest("${!isRBM}");
/* 2596 */                                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2597 */                                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                     for (;;) {
/* 2599 */                                       out.write("\n\t\t\t\t<span style=\"padding-left:10px;\" class=\"bcactive\"><input style=\"position:relative;\" type=\"checkbox\" name=\"runOnServer\" value=\"runOnServer\" checked='checked'/>");
/* 2600 */                                       out.print(FormatUtil.getString("am.webclient.eumagent.runonserver"));
/* 2601 */                                       out.write("</span>\n\t\t\t");
/* 2602 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2603 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2607 */                                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2608 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                                   }
/*      */                                   
/* 2611 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2612 */                                   out.write("\n\t\t\t<span style=\"padding-left:10px;\" class=\"bcactive\"  onMouseOver=\"ddrivetip(this,event,'");
/* 2613 */                                   out.print(FormatUtil.getString(licenseTooltip));
/* 2614 */                                   out.write("',false,true,'#000000',150,'lightyellow')\" onmouseout=\"");
/* 2615 */                                   out.print(licenseTooltip.equals("") ? "" : "hideddrivetip();");
/* 2616 */                                   out.write("\">\t\t\t\t\n\t\t\t\t<input style=\"position:relative;\" type=\"checkbox\" class=\"agentLocationCheckbox\" name=\"runOnAgent\" id=\"runOnAgent\" value=\"runOnAgent\" ");
/* 2617 */                                   out.print(disable);
/* 2618 */                                   out.write("/>\t\t\t\n\t\t\t\t");
/* 2619 */                                   out.print(FormatUtil.getString("am.webclient.eumagent.runonagent"));
/* 2620 */                                   out.write("&nbsp;&nbsp;(");
/* 2621 */                                   out.print(FormatUtil.getString("am.webclient.common.eum"));
/* 2622 */                                   out.write(41);
/* 2623 */                                   if (!com.adventnet.appmanager.util.Constants.isIt360) {
/* 2624 */                                     out.write("&nbsp;<img border=\"0\" align=\"middle\" title=\"Add On is Enabled for Evaluation / Trial Versions\" style=\"position: relative; bottom: 2px;\" src=\"/images/icon_addon.gif\"></span>");
/*      */                                   }
/* 2626 */                                   out.write("\n\t\t\t\t&nbsp;<span>");
/* 2627 */                                   out.print(FormatUtil.getString("am.webclient.eum.learneumlink"));
/* 2628 */                                   out.write("\n\t\t\t\t\n\t\t\t</span>\n\t\t\t<div class=\"agentLoactionDiv\" style=\"padding: 10px;display: none;width: 99%;\">\n\t\t\t");
/*      */                                 } else {
/* 2630 */                                   out.write("\n\t\t\t\t<div class=\"agentLoactionDiv\" style=\"padding: 10px;display: block;width: 99%;\">\n\t\t\t\t\n\t\t\t");
/*      */                                 }
/* 2632 */                                 if (!agentLocation.isEmpty()) {
/* 2633 */                                   out.write("\n\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">\n\t\t\t\t\t<tr><td>\n\t\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"55%\">\n\t\t\t\t\t\n\t\t\t\t\t<tr height=\"28\">\n\t\t\t\t\t\t<span class=\"bodytext\">\n\t\t\t\t\t\t");
/*      */                                   
/* 2635 */                                   CheckboxTag _jspx_th_html_005fcheckbox_005f0 = (CheckboxTag)this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.get(CheckboxTag.class);
/* 2636 */                                   _jspx_th_html_005fcheckbox_005f0.setPageContext(_jspx_page_context);
/* 2637 */                                   _jspx_th_html_005fcheckbox_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2639 */                                   _jspx_th_html_005fcheckbox_005f0.setProperty("selectAllAgents");
/*      */                                   
/* 2641 */                                   _jspx_th_html_005fcheckbox_005f0.setOnclick("toggleChecked(this.checked)");
/* 2642 */                                   int _jspx_eval_html_005fcheckbox_005f0 = _jspx_th_html_005fcheckbox_005f0.doStartTag();
/* 2643 */                                   if (_jspx_eval_html_005fcheckbox_005f0 != 0) {
/* 2644 */                                     if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 2645 */                                       out = _jspx_page_context.pushBody();
/* 2646 */                                       _jspx_th_html_005fcheckbox_005f0.setBodyContent((BodyContent)out);
/* 2647 */                                       _jspx_th_html_005fcheckbox_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2650 */                                       out.print(FormatUtil.getString("am.webclient.eumagent.selectall"));
/* 2651 */                                       int evalDoAfterBody = _jspx_th_html_005fcheckbox_005f0.doAfterBody();
/* 2652 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2655 */                                     if (_jspx_eval_html_005fcheckbox_005f0 != 1) {
/* 2656 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2659 */                                   if (_jspx_th_html_005fcheckbox_005f0.doEndTag() == 5) {
/* 2660 */                                     this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.reuse(_jspx_th_html_005fcheckbox_005f0); return;
/*      */                                   }
/*      */                                   
/* 2663 */                                   this._005fjspx_005ftagPool_005fhtml_005fcheckbox_0026_005fproperty_005fonclick.reuse(_jspx_th_html_005fcheckbox_005f0);
/* 2664 */                                   out.write("</span><br><br>\n\t\t\t\t\t\t");
/*      */                                   
/* 2666 */                                   IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2667 */                                   _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2668 */                                   _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2670 */                                   _jspx_th_logic_005fiterate_005f0.setName("agentLocation");
/*      */                                   
/* 2672 */                                   _jspx_th_logic_005fiterate_005f0.setId("userAgent");
/*      */                                   
/* 2674 */                                   _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/* 2675 */                                   int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2676 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2677 */                                     Object userAgent = null;
/* 2678 */                                     Integer j = null;
/* 2679 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2680 */                                       out = _jspx_page_context.pushBody();
/* 2681 */                                       _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2682 */                                       _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                     }
/* 2684 */                                     userAgent = _jspx_page_context.findAttribute("userAgent");
/* 2685 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                     for (;;) {
/* 2687 */                                       out.write("\n\t\t\t\t\t\t\t");
/*      */                                       
/* 2689 */                                       String textclass = "color='black'";
/* 2690 */                                       String status = "";
/* 2691 */                                       if (!((String)agentStatus.get(j.intValue())).equals("0")) {
/* 2692 */                                         textclass = "color='red'";
/* 2693 */                                         status = FormatUtil.getString("am.webclient.eumdashboard.agentdown");
/*      */                                       }
/*      */                                       
/*      */ 
/* 2697 */                                       out.write("\n\t\t\t\t\t\t\t<td nowrap=\"nowrap\" style=\"padding-left:18px;\">");
/*      */                                       
/* 2699 */                                       MultiboxTag _jspx_th_html_005fmultibox_005f0 = (MultiboxTag)this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.get(MultiboxTag.class);
/* 2700 */                                       _jspx_th_html_005fmultibox_005f0.setPageContext(_jspx_page_context);
/* 2701 */                                       _jspx_th_html_005fmultibox_005f0.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                       
/* 2703 */                                       _jspx_th_html_005fmultibox_005f0.setProperty("selectedAgents");
/*      */                                       
/* 2705 */                                       _jspx_th_html_005fmultibox_005f0.setOnclick("javascript:findSelectedIndex('" + j + "');");
/*      */                                       
/* 2707 */                                       _jspx_th_html_005fmultibox_005f0.setDisabled(Boolean.parseBoolean((String)isDisabled.get(j.intValue())));
/* 2708 */                                       int _jspx_eval_html_005fmultibox_005f0 = _jspx_th_html_005fmultibox_005f0.doStartTag();
/* 2709 */                                       if (_jspx_eval_html_005fmultibox_005f0 != 0) {
/* 2710 */                                         if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 2711 */                                           out = _jspx_page_context.pushBody();
/* 2712 */                                           _jspx_th_html_005fmultibox_005f0.setBodyContent((BodyContent)out);
/* 2713 */                                           _jspx_th_html_005fmultibox_005f0.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 2716 */                                           out.write("\n\t\t\t\t\t\t\t\t");
/* 2717 */                                           if (_jspx_meth_bean_005fwrite_005f0(_jspx_th_html_005fmultibox_005f0, _jspx_page_context))
/*      */                                             return;
/* 2719 */                                           out.write("\n\t\t\t\t\t\t\t\t");
/* 2720 */                                           int evalDoAfterBody = _jspx_th_html_005fmultibox_005f0.doAfterBody();
/* 2721 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 2724 */                                         if (_jspx_eval_html_005fmultibox_005f0 != 1) {
/* 2725 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 2728 */                                       if (_jspx_th_html_005fmultibox_005f0.doEndTag() == 5) {
/* 2729 */                                         this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.reuse(_jspx_th_html_005fmultibox_005f0); return;
/*      */                                       }
/*      */                                       
/* 2732 */                                       this._005fjspx_005ftagPool_005fhtml_005fmultibox_0026_005fproperty_005fonclick_005fdisabled.reuse(_jspx_th_html_005fmultibox_005f0);
/* 2733 */                                       out.write("\n\t\t\t\t\t\t\t\t<a style=\"cursor:pointer\" class=\"tooltip\" onMouseOver=\"ddrivetip(this,event,'");
/* 2734 */                                       out.print(status);
/* 2735 */                                       out.write("',false,true,'#000000',100,'lightyellow')\" onmouseout='");
/* 2736 */                                       out.print(status.equals("") ? "" : "hideddrivetip()");
/* 2737 */                                       out.write("'>\n\t\t\t\t\t\t\t\t\t<font ");
/* 2738 */                                       out.print(textclass);
/* 2739 */                                       out.write(62);
/* 2740 */                                       if (_jspx_meth_bean_005fwrite_005f1(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                         return;
/* 2742 */                                       out.write("</font>\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t");
/* 2743 */                                       if (_jspx_meth_c_005fif_005f3(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                         return;
/* 2745 */                                       out.write("\n\t\t\t\t\t\t");
/* 2746 */                                       int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2747 */                                       userAgent = _jspx_page_context.findAttribute("userAgent");
/* 2748 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/* 2749 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2752 */                                     if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2753 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2756 */                                   if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2757 */                                     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                   }
/*      */                                   
/* 2760 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2761 */                                   out.write("\n\t\t\t\t\t</table></td></tr>\t\n\t\t\t\t</table>\n\t\t\t\t\n\t\t\t\t\n\t\t\t\t");
/*      */                                 } else {
/* 2763 */                                   out.write("\n\t\t\t\t\t<table class=\"bodytext upgradeAmountBg1\" width=\"100%\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td><span class=\"bodytext\">");
/* 2764 */                                   out.print(FormatUtil.getString("am.webclient.eumdashboard.noagents"));
/* 2765 */                                   out.write("</span></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t");
/*      */                                 }
/* 2767 */                                 out.write("\n\t\t\t\t\n\t\t\t</div>\n\t\t</td>\n\t</tr>\n\t<tr>\n\t</tr>\n</table>\n\n<script>\n\n$(document).ready(function(){\n\t$('.agentLocationCheckbox').click(function() //NO I18N \n   \t{\n\t\tif($(this).attr(\"checked\"))\n\t\t{\n\t\t\t");
/*      */                                 
/* 2769 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/* 2771 */                                   out.write("\t\n\t\t\t\t\tvar masObj = document.AMActionForm.selectedServer;\t\t\t\t\t\n\t\t\t\t\tif(masObj != 'undefined' && masObj.value == '-')\n\t\t\t\t\t{\n\t\t\t\t\t\talert('");
/* 2772 */                                   out.print(FormatUtil.getString("am.webclient.admin.addmonitor.select.mas.text"));
/* 2773 */                                   out.write("');\n\t\t\t\t\t\treturn false;\n\t\t\t\t\t}\n\t\t\t\t\telse if(masObj != 'undefined')\n\t\t\t\t\t{\n\t\t\t\t\t\t$.post(\"/showAgent.do?method=getAgentDetails&selectedServer=\"+masObj.value+\"&ajaxRequest=true\", function (data){$('.agentLoactionDiv').html(data);});//NO I18N\n\t\t\t\t\t}\n\t\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2777 */                                 out.write("\n\t\t\t$('.agentLoactionDiv').slideDown(\"slow\"); //NO I18N\n\t\t}\n\t\telse\n\t\t{\n\t\t\ttoggleChecked(false);\n\t\t\t$('.agentLoactionDiv').slideUp(\"slow\"); //NO I18N\n\t\t}\n\t})\n  }\n);\n\nfunction toggleChecked(status) \n{\n\t$(\".agentLoactionDiv input:enabled\").each( function() { //NO I18N \n\t{\n\t\t$(this).attr(\"checked\",status); //NO I18N\n\t}})\n}\n\nfunction findSelectedIndex(id){\n\t");
/* 2778 */                                 if (fromWhere.equals("CONF")) {
/* 2779 */                                   out.write("\n\tif(document.AMActionForm.selectAllAgents.checked == true && document.AMActionForm.selectedAgents[id].checked==false)\n\t{\n\t\tdocument.AMActionForm.selectAllAgents.checked =false;\n\t}\n\t");
/*      */                                 } else {
/* 2781 */                                   out.write("\n\t\tif(document.RbmForm.selectAllAgents.checked == true && document.RbmForm.selectedAgents[id].checked==false)\n\t\t{\n\t\tdocument.RbmForm.selectAllAgents.checked =false;\n\t\t}\t\n\t");
/*      */                                 }
/* 2783 */                                 out.write("\n}\nfunction checkAgentSelected(){\n\tif(document.AMActionForm.runOnServer && document.AMActionForm.runOnServer.checked == true){\n\t\treturn true;\n\t}\n\telse if(document.AMActionForm.runOnAgent.checked == true){\n\t\tif(document.AMActionForm.selectAllAgents){\n\t\t\tnoOfAgents=");
/* 2784 */                                 out.print(agentLocation.size());
/* 2785 */                                 out.write(";\t\t\t\n\t\t\tif(document.AMActionForm.selectAllAgents.checked == true){\n\t\t\t\treturn true;\n\t\t\t}\t\t\t\n\t\t\telse if(noOfAgents>1){\t\t\t\t\n\t\t\t\t");
/*      */                                 
/* 2787 */                                 IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2788 */                                 _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 2789 */                                 _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2791 */                                 _jspx_th_logic_005fiterate_005f1.setName("agentLocation");
/*      */                                 
/* 2793 */                                 _jspx_th_logic_005fiterate_005f1.setId("userAgent");
/*      */                                 
/* 2795 */                                 _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/* 2796 */                                 int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 2797 */                                 if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 2798 */                                   Object userAgent = null;
/* 2799 */                                   Integer j = null;
/* 2800 */                                   if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2801 */                                     out = _jspx_page_context.pushBody();
/* 2802 */                                     _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 2803 */                                     _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                   }
/* 2805 */                                   userAgent = _jspx_page_context.findAttribute("userAgent");
/* 2806 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 2808 */                                     out.write("\n\t\t\t\t\tif(document.AMActionForm.selectedAgents[");
/* 2809 */                                     out.print(j);
/* 2810 */                                     out.write("].checked==true){\n\t\t\t\t\t\treturn true;\n\t\t\t\t\t}\n\t\t\t\t\n\t\t\t\t");
/* 2811 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 2812 */                                     userAgent = _jspx_page_context.findAttribute("userAgent");
/* 2813 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 2814 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2817 */                                   if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 2818 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2821 */                                 if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 2822 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                 }
/*      */                                 
/* 2825 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 2826 */                                 out.write("\n\t\t\t}else{\n\t\t\t\tif(document.AMActionForm.selectedAgents.checked==true){\n\t\t\t\t\treturn true;\n\t\t\t\t}\t\t\t\t\n\t\t\t}\n\t\t}\n\t\telse{\n\t\t\talert(\"");
/* 2827 */                                 out.print(FormatUtil.getString("am.webclient.eumagent.validate.noagent"));
/* 2828 */                                 out.write("\");\n\t\t\tnoOfAgents=0;\n\t\t}\n\t}\n\t\n\treturn false;\n}\nfunction checkAgentSelectedForRBM(){\n\tif(document.RbmForm.selectAllAgents){\n\t\tnoOfAgents=");
/* 2829 */                                 out.print(agentLocation.size());
/* 2830 */                                 out.write(";\t\n\t\tif(document.RbmForm.selectAllAgents.checked == true){\n\t\t\t\treturn true;\n\t\t}\n\t\telse if(noOfAgents>1){\t\t\t\t\n\t\t\t");
/*      */                                 
/* 2832 */                                 IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.get(IterateTag.class);
/* 2833 */                                 _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 2834 */                                 _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2836 */                                 _jspx_th_logic_005fiterate_005f2.setName("agentLocation");
/*      */                                 
/* 2838 */                                 _jspx_th_logic_005fiterate_005f2.setId("userAgent");
/*      */                                 
/* 2840 */                                 _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/* 2841 */                                 int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 2842 */                                 if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 2843 */                                   Object userAgent = null;
/* 2844 */                                   Integer j = null;
/* 2845 */                                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 2846 */                                     out = _jspx_page_context.pushBody();
/* 2847 */                                     _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 2848 */                                     _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                   }
/* 2850 */                                   userAgent = _jspx_page_context.findAttribute("userAgent");
/* 2851 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 2853 */                                     out.write("\n\t\t\t\tif(document.RbmForm.selectedAgents[");
/* 2854 */                                     out.print(j);
/* 2855 */                                     out.write("].checked==true){\n\t\t\t\t\t\t\treturn true;\n\t\t\t\t}\t\t\t\t\t\n\t\t\t");
/* 2856 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 2857 */                                     userAgent = _jspx_page_context.findAttribute("userAgent");
/* 2858 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 2859 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2862 */                                   if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 2863 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2866 */                                 if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 2867 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                 }
/*      */                                 
/* 2870 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 2871 */                                 out.write("\n\t\t}else if(document.RbmForm.selectedAgents.checked==true){\n\t\t\t\treturn true;\n\t\t\t\t\t\t\t\n\t\t}\n\t}\n\t\n\treturn false;\n}\n</script>\n");
/* 2872 */                                 out.write("\n \t\t\t<table class=\"lrbborder \" width=\"99%\" cellspacing=\"0\" cellpadding=\"6\" border=\"0\" cellpading=\"4\">\n \t\t\t\t\n\t\t\t\t\n\t\t\t\t\n \n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"tablebottom\" colspan=\"2\" align=\"center\">\n\t\t\t\t\t\t\t");
/*      */                                 
/* 2874 */                                 ButtonTag _jspx_th_html_005fbutton_005f0 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 2875 */                                 _jspx_th_html_005fbutton_005f0.setPageContext(_jspx_page_context);
/* 2876 */                                 _jspx_th_html_005fbutton_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2878 */                                 _jspx_th_html_005fbutton_005f0.setProperty("save");
/*      */                                 
/* 2880 */                                 _jspx_th_html_005fbutton_005f0.setValue(FormatUtil.getString("am.webclient.common.save.text"));
/*      */                                 
/* 2882 */                                 _jspx_th_html_005fbutton_005f0.setStyleClass("btn");
/*      */                                 
/* 2884 */                                 _jspx_th_html_005fbutton_005f0.setOnclick("formSubmit()");
/* 2885 */                                 int _jspx_eval_html_005fbutton_005f0 = _jspx_th_html_005fbutton_005f0.doStartTag();
/* 2886 */                                 if (_jspx_th_html_005fbutton_005f0.doEndTag() == 5) {
/* 2887 */                                   this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0); return;
/*      */                                 }
/*      */                                 
/* 2890 */                                 this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f0);
/* 2891 */                                 out.write("\n\t\t\t\t\t\t\t");
/*      */                                 
/* 2893 */                                 ButtonTag _jspx_th_html_005fbutton_005f1 = (ButtonTag)this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.get(ButtonTag.class);
/* 2894 */                                 _jspx_th_html_005fbutton_005f1.setPageContext(_jspx_page_context);
/* 2895 */                                 _jspx_th_html_005fbutton_005f1.setParent(_jspx_th_html_005fform_005f0);
/*      */                                 
/* 2897 */                                 _jspx_th_html_005fbutton_005f1.setProperty("testButton");
/*      */                                 
/* 2899 */                                 _jspx_th_html_005fbutton_005f1.setValue(FormatUtil.getString("am.vm.action.test"));
/*      */                                 
/* 2901 */                                 _jspx_th_html_005fbutton_005f1.setStyleClass("btn");
/*      */                                 
/* 2903 */                                 _jspx_th_html_005fbutton_005f1.setOnclick("formTestSubmit()");
/* 2904 */                                 int _jspx_eval_html_005fbutton_005f1 = _jspx_th_html_005fbutton_005f1.doStartTag();
/* 2905 */                                 if (_jspx_th_html_005fbutton_005f1.doEndTag() == 5) {
/* 2906 */                                   this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1); return;
/*      */                                 }
/*      */                                 
/* 2909 */                                 this._005fjspx_005ftagPool_005fhtml_005fbutton_0026_005fvalue_005fstyleClass_005fproperty_005fonclick_005fnobody.reuse(_jspx_th_html_005fbutton_005f1);
/* 2910 */                                 out.write("\n    \t\t\t\t\t\t<input type=\"button\" value=\"");
/* 2911 */                                 out.print(FormatUtil.getString("am.vm.action.cancel"));
/* 2912 */                                 out.write("\" class=\"buttons btn_link\" onclick=\"javascript:history.back()\" name=\"cancel\">\n    \t\t\t\t\t </td>\n \t\t\t\t\t</tr>\n \t\t\t</table>\n \t\t</td>\n \t\t<td width=\"30%\" valign=\"top\">\n\t\t\t");
/* 2913 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/includes/HelpCard.jsp" + ("/jsp/includes/HelpCard.jsp".indexOf('?') > 0 ? '&' : '?') + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("helpcardKey", request.getCharacterEncoding()) + "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode(String.valueOf(FormatUtil.getString("am.webclient.admin.seleniumTCImport.helpcard")), request.getCharacterEncoding()), out, false);
/* 2914 */                                 out.write("\n\t\t</td>\n \t</tr>\n </table>\n\n \n \n\n");
/* 2915 */                                 if (_jspx_meth_html_005fhidden_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2917 */                                 out.write(10);
/* 2918 */                                 if (_jspx_meth_html_005fhidden_005f1(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                   return;
/* 2920 */                                 out.write(10);
/* 2921 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2922 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2926 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2927 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 2930 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005fenctype_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2931 */                             out.write(10);
/* 2932 */                             out.write(10);
/* 2933 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2934 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2937 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2938 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2941 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2942 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 2945 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2946 */                         out.write(10);
/* 2947 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2949 */                         out.write(32);
/* 2950 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2951 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2955 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2956 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 2959 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2960 */                       out.write("\n</body>\n</html>\n\n<script type=\"text/javascript\">\n\nfunction formTestSubmit() {\n\n\tvar fileLength=document.getElementsByName(\"rbmTCfile\")[0].files.length;\n\tif(fileLength!=1) {\n\t\talert('");
/* 2961 */                       out.print(FormatUtil.getString("am.webclient.admin.upload.file"));
/* 2962 */                       out.write("');\n\t\treturn;\n\t}\n\tvar chkbx=document.getElementsByName(\"selectedAgents\");\n\tvar isChecked=false;\n\tfor(i=0;i<chkbx.length;i++) {\n\t\tif(chkbx[i].checked) {\n\t\t\tisChecked=true;\n\t\t\tbreak;\n\t\t}\n\t}\n\tif(!isChecked) {\n\t\talert('");
/* 2963 */                       out.print(FormatUtil.getString("am.webclient.rbm.noagents"));
/* 2964 */                       out.write("');\n\t\treturn;\n\t}\n\tdocument.getElementById(\"msgDiv\").style.display=\"block\";\n\tdocument.getElementsByName(\"testPlay\")[0].value=true;\n\tvar form=document.getElementsByName(\"AMActionForm\")[0];\n\tdocument.AMActionForm.submit();\n}\n\nfunction formSubmit() {\n\tvar displayName=document.getElementsByName(\"rbmDisplayName\")[0].value;\n\tif(displayName==null || displayName==\"\") {\n\t\talert('");
/* 2965 */                       out.print(FormatUtil.getString("am.webclient.eumdashboard.edit.displayname.cantempty"));
/* 2966 */                       out.write("');\n\t\treturn;\n\t}\n\tvar fileLength=document.getElementsByName(\"rbmTCfile\")[0].files.length;\n\tif(fileLength!=1) {\n\t\talert('");
/* 2967 */                       out.print(FormatUtil.getString("am.webclient.admin.upload.file"));
/* 2968 */                       out.write("');\n\t\treturn;\n\t}\n\tvar pollinterval=document.getElementsByName(\"rbmPollingInterval\")[0].value;\n    if (pollinterval === \"\" || !(isPositiveInteger(pollinterval)) || pollinterval === '0') {\n        alert('");
/* 2969 */                       out.print(FormatUtil.getString("am.webclient.newscript.alert.pollingintervalzero.text"));
/* 2970 */                       out.write("');\n        document.AMActionForm.pollInterval.focus();\n        return;\n        }\n    if(pollinterval < 10)\n    {\n       alert('");
/* 2971 */                       out.print(FormatUtil.getString("am.webclient.rbm.alert.pollinginterval.text"));
/* 2972 */                       out.write("');\n       document.AMActionForm.pollInterval.focus();\n        return;\n    }\n    \n    var chkbx=document.getElementsByName(\"selectedAgents\");\n\tvar isChecked=false;\n\tfor(i=0;i<chkbx.length;i++) {\n\t\tif(chkbx[i].checked) {\n\t\t\tisChecked=true;\n\t\t\tbreak;\n\t\t}\n\t}\n\tif(!isChecked) {\n\t\talert('");
/* 2973 */                       out.print(FormatUtil.getString("am.webclient.rbm.noagents"));
/* 2974 */                       out.write("');\n\t\treturn;\n\t}\n\tdocument.AMActionForm.submit();\n}\n</script>\n");
/*      */                     }
/* 2976 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2977 */         out = _jspx_out;
/* 2978 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2979 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2980 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2983 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2989 */     PageContext pageContext = _jspx_page_context;
/* 2990 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2992 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2993 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2994 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2996 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2998 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 2999 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3000 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3001 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3002 */       return true;
/*      */     }
/* 3004 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3010 */     PageContext pageContext = _jspx_page_context;
/* 3011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3013 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 3014 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3015 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3017 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 3019 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 3020 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3021 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3022 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3023 */       return true;
/*      */     }
/* 3025 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3026 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3031 */     PageContext pageContext = _jspx_page_context;
/* 3032 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3034 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3035 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3036 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3038 */     _jspx_th_c_005fif_005f0.setTest("${not empty errMessage}");
/* 3039 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3040 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3042 */         out.write("\n\t<div class=\"notification\" style=\"display:block\" id=\"errMsgDiv\">\n\t\t<div class=\"message\">\n\t\t\t<span><img src=\"/images/icon_message_failure.gif\" width=\"20\" height=\"20\" class=\"info-icon\"> ");
/* 3043 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3044 */           return true;
/* 3045 */         out.write(" </span> \n\t\t</div>\n\t</div>\n");
/* 3046 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3047 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3051 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3052 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3053 */       return true;
/*      */     }
/* 3055 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3056 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3061 */     PageContext pageContext = _jspx_page_context;
/* 3062 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3064 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3065 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3066 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3068 */     _jspx_th_c_005fout_005f1.setValue("${errMessage}");
/* 3069 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3070 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3071 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3072 */       return true;
/*      */     }
/* 3074 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3075 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3080 */     PageContext pageContext = _jspx_page_context;
/* 3081 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3083 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3084 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3085 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3087 */     _jspx_th_c_005fif_005f1.setTest("${not empty message}");
/* 3088 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3089 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3091 */         out.write("\n\t<div class=\"notification\" style=\"display:block\" id=\"errMsgDiv\">\n\t\t<div class=\"message\">\n\t\t\t<span><img src=\"/images/icon_message_success.gif\" width=\"20\" height=\"20\" class=\"info-icon\">");
/* 3092 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3093 */           return true;
/* 3094 */         out.write("</span> \n\t\t</div>\n\t</div>\n");
/* 3095 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3096 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3100 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3101 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3102 */       return true;
/*      */     }
/* 3104 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3105 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3110 */     PageContext pageContext = _jspx_page_context;
/* 3111 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3113 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3114 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3115 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3117 */     _jspx_th_c_005fout_005f2.setValue("${message}");
/* 3118 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3119 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3120 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3121 */       return true;
/*      */     }
/* 3123 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3124 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3129 */     PageContext pageContext = _jspx_page_context;
/* 3130 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3132 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.get(TextTag.class);
/* 3133 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3134 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3136 */     _jspx_th_html_005ftext_005f0.setProperty("rbmDisplayName");
/* 3137 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3138 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3139 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3140 */       return true;
/*      */     }
/* 3142 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3143 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ffile_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3148 */     PageContext pageContext = _jspx_page_context;
/* 3149 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3151 */     FileTag _jspx_th_html_005ffile_005f0 = (FileTag)this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.get(FileTag.class);
/* 3152 */     _jspx_th_html_005ffile_005f0.setPageContext(_jspx_page_context);
/* 3153 */     _jspx_th_html_005ffile_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3155 */     _jspx_th_html_005ffile_005f0.setProperty("rbmTCfile");
/* 3156 */     int _jspx_eval_html_005ffile_005f0 = _jspx_th_html_005ffile_005f0.doStartTag();
/* 3157 */     if (_jspx_th_html_005ffile_005f0.doEndTag() == 5) {
/* 3158 */       this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 3159 */       return true;
/*      */     }
/* 3161 */     this._005fjspx_005ftagPool_005fhtml_005ffile_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ffile_005f0);
/* 3162 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3167 */     PageContext pageContext = _jspx_page_context;
/* 3168 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3170 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.get(TextTag.class);
/* 3171 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3172 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3174 */     _jspx_th_html_005ftext_005f1.setProperty("rbmPollingInterval");
/* 3175 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3176 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3177 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3178 */       return true;
/*      */     }
/* 3180 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fproperty_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3181 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f0(JspTag _jspx_th_html_005fmultibox_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3186 */     PageContext pageContext = _jspx_page_context;
/* 3187 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3189 */     WriteTag _jspx_th_bean_005fwrite_005f0 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 3190 */     _jspx_th_bean_005fwrite_005f0.setPageContext(_jspx_page_context);
/* 3191 */     _jspx_th_bean_005fwrite_005f0.setParent((Tag)_jspx_th_html_005fmultibox_005f0);
/*      */     
/* 3193 */     _jspx_th_bean_005fwrite_005f0.setName("userAgent");
/*      */     
/* 3195 */     _jspx_th_bean_005fwrite_005f0.setProperty("value");
/* 3196 */     int _jspx_eval_bean_005fwrite_005f0 = _jspx_th_bean_005fwrite_005f0.doStartTag();
/* 3197 */     if (_jspx_th_bean_005fwrite_005f0.doEndTag() == 5) {
/* 3198 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3199 */       return true;
/*      */     }
/* 3201 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f0);
/* 3202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_bean_005fwrite_005f1(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3207 */     PageContext pageContext = _jspx_page_context;
/* 3208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3210 */     WriteTag _jspx_th_bean_005fwrite_005f1 = (WriteTag)this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.get(WriteTag.class);
/* 3211 */     _jspx_th_bean_005fwrite_005f1.setPageContext(_jspx_page_context);
/* 3212 */     _jspx_th_bean_005fwrite_005f1.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3214 */     _jspx_th_bean_005fwrite_005f1.setName("userAgent");
/*      */     
/* 3216 */     _jspx_th_bean_005fwrite_005f1.setProperty("label");
/* 3217 */     int _jspx_eval_bean_005fwrite_005f1 = _jspx_th_bean_005fwrite_005f1.doStartTag();
/* 3218 */     if (_jspx_th_bean_005fwrite_005f1.doEndTag() == 5) {
/* 3219 */       this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3220 */       return true;
/*      */     }
/* 3222 */     this._005fjspx_005ftagPool_005fbean_005fwrite_0026_005fproperty_005fname_005fnobody.reuse(_jspx_th_bean_005fwrite_005f1);
/* 3223 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3228 */     PageContext pageContext = _jspx_page_context;
/* 3229 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3231 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3232 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3233 */     _jspx_th_c_005fif_005f3.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3235 */     _jspx_th_c_005fif_005f3.setTest("${((j+1)%4) == 0}");
/* 3236 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3237 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3239 */         out.write("\n\t\t\t\t\t\t\t</tr><tr height=\"5px\"><td colspan=\"3\"><img src=\"/images/spacer.gif\"></img></tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t");
/* 3240 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3241 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3245 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3246 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3247 */       return true;
/*      */     }
/* 3249 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3250 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3255 */     PageContext pageContext = _jspx_page_context;
/* 3256 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3258 */     HiddenTag _jspx_th_html_005fhidden_005f0 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 3259 */     _jspx_th_html_005fhidden_005f0.setPageContext(_jspx_page_context);
/* 3260 */     _jspx_th_html_005fhidden_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3262 */     _jspx_th_html_005fhidden_005f0.setProperty("testPlay");
/*      */     
/* 3264 */     _jspx_th_html_005fhidden_005f0.setValue("false");
/* 3265 */     int _jspx_eval_html_005fhidden_005f0 = _jspx_th_html_005fhidden_005f0.doStartTag();
/* 3266 */     if (_jspx_th_html_005fhidden_005f0.doEndTag() == 5) {
/* 3267 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3268 */       return true;
/*      */     }
/* 3270 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f0);
/* 3271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005fhidden_005f1(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3276 */     PageContext pageContext = _jspx_page_context;
/* 3277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3279 */     HiddenTag _jspx_th_html_005fhidden_005f1 = (HiddenTag)this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.get(HiddenTag.class);
/* 3280 */     _jspx_th_html_005fhidden_005f1.setPageContext(_jspx_page_context);
/* 3281 */     _jspx_th_html_005fhidden_005f1.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3283 */     _jspx_th_html_005fhidden_005f1.setProperty("method");
/*      */     
/* 3285 */     _jspx_th_html_005fhidden_005f1.setValue("importSeleniumTestCase");
/* 3286 */     int _jspx_eval_html_005fhidden_005f1 = _jspx_th_html_005fhidden_005f1.doStartTag();
/* 3287 */     if (_jspx_th_html_005fhidden_005f1.doEndTag() == 5) {
/* 3288 */       this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3289 */       return true;
/*      */     }
/* 3291 */     this._005fjspx_005ftagPool_005fhtml_005fhidden_0026_005fvalue_005fproperty_005fnobody.reuse(_jspx_th_html_005fhidden_005f1);
/* 3292 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3297 */     PageContext pageContext = _jspx_page_context;
/* 3298 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3300 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3301 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3302 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3304 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3306 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3307 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3308 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3309 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3310 */       return true;
/*      */     }
/* 3312 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3313 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\RealBrowserSeleniumTCImporter_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */