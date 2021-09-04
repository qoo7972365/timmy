/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.server.mysql.bean.MySqlGraphs;
/*      */ import com.adventnet.appmanager.tags.Truncate;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.data.support.DialChartSupport;
/*      */ import com.adventnet.awolf.tags.AMParam;
/*      */ import com.adventnet.awolf.tags.AMWolf;
/*      */ import com.adventnet.awolf.tags.DialChart;
/*      */ import com.adventnet.awolf.tags.Property;
/*      */ import com.adventnet.awolf.tags.TimeChart;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class MySqlOverview_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*      */   public static String getSingleColumnDisplay(String header, java.util.Vector tableColumns)
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
/* 1704 */           String attributeName = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
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
/* 2145 */           String message = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2146 */           String image = com.adventnet.appmanager.dbcache.ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
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
/* 2175 */   private static final javax.servlet.jsp.JspFactory _jspxFactory = javax.servlet.jsp.JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2181 */   private static Map<String, Long> _jspx_dependants = new HashMap(5);
/* 2182 */   static { _jspx_dependants.put("/jsp/includes/ManagedServerInfo.jspf", Long.valueOf(1473429417000L));
/* 2183 */     _jspx_dependants.put("/jsp/includes/TopBorder.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2185 */     _jspx_dependants.put("/jsp/includes/BottomBorder.jspf", Long.valueOf(1473429417000L));
/* 2186 */     _jspx_dependants.put("/jsp/includes/HostPerformance.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2211 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2215 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2233 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2239 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/* 2240 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2241 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2242 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2245 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2246 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2248 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2249 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2250 */     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.release();
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.release();
/* 2252 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.release();
/* 2253 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2260 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2263 */     JspWriter out = null;
/* 2264 */     Object page = this;
/* 2265 */     JspWriter _jspx_out = null;
/* 2266 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2270 */       response.setContentType("text/html;charset=UTF-8");
/* 2271 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2273 */       _jspx_page_context = pageContext;
/* 2274 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2275 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2276 */       session = pageContext.getSession();
/* 2277 */       out = pageContext.getOut();
/* 2278 */       _jspx_out = out;
/*      */       
/* 2280 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2281 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2283 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2284 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2291 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2293 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2294 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2295 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2296 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2299 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2300 */         String available = null;
/* 2301 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2302 */         out.write(10);
/*      */         
/* 2304 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2305 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2312 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2314 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2315 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2316 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2317 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2320 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2321 */           String unavailable = null;
/* 2322 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2323 */           out.write(10);
/*      */           
/* 2325 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2326 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2333 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2335 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2336 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2337 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2338 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2341 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2342 */             String unmanaged = null;
/* 2343 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2344 */             out.write(10);
/*      */             
/* 2346 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2347 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2354 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2356 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2357 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2358 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2359 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2362 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2363 */               String scheduled = null;
/* 2364 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2365 */               out.write(10);
/*      */               
/* 2367 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2368 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2377 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2378 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2379 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2380 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2383 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2384 */                 String critical = null;
/* 2385 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2386 */                 out.write(10);
/*      */                 
/* 2388 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2389 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2396 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2398 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2399 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2400 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2401 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2404 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2405 */                   String clear = null;
/* 2406 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2407 */                   out.write(10);
/*      */                   
/* 2409 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2410 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2417 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2419 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2420 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2421 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2422 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2425 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2426 */                     String warning = null;
/* 2427 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2428 */                     out.write(10);
/* 2429 */                     out.write(10);
/*      */                     
/* 2431 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2432 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2434 */                     out.write(10);
/* 2435 */                     out.write(10);
/* 2436 */                     out.write(10);
/* 2437 */                     out.write(10);
/* 2438 */                     out.write(10);
/* 2439 */                     com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2440 */                     wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2441 */                     if (wlsGraph == null) {
/* 2442 */                       wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2443 */                       _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */                     }
/* 2445 */                     out.write(10);
/* 2446 */                     MySqlGraphs mysqlgraph = null;
/* 2447 */                     mysqlgraph = (MySqlGraphs)_jspx_page_context.getAttribute("mysqlgraph", 1);
/* 2448 */                     if (mysqlgraph == null) {
/* 2449 */                       mysqlgraph = new MySqlGraphs();
/* 2450 */                       _jspx_page_context.setAttribute("mysqlgraph", mysqlgraph, 1);
/*      */                     }
/* 2452 */                     out.write(10);
/* 2453 */                     PerformanceBean perfgraph = null;
/* 2454 */                     perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2455 */                     if (perfgraph == null) {
/* 2456 */                       perfgraph = new PerformanceBean();
/* 2457 */                       _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */                     }
/* 2459 */                     out.write(10);
/* 2460 */                     out.write(10);
/* 2461 */                     out.write(10);
/*      */                     
/* 2463 */                     String name = null;
/* 2464 */                     float downtime = 0.0F;
/*      */                     
/*      */ 
/*      */ 
/* 2468 */                     name = (String)request.getAttribute("name");
/* 2469 */                     String haid = null;
/* 2470 */                     String appname = null;
/* 2471 */                     String tab = "1";
/* 2472 */                     String bgcolour = "class=\"whitegrayborder\"";
/* 2473 */                     String resourceid = (String)request.getAttribute("resourceid");
/* 2474 */                     haid = (String)request.getAttribute("haid");
/* 2475 */                     appname = (String)request.getAttribute("appName");
/* 2476 */                     String details = (String)request.getAttribute("details");
/* 2477 */                     String maxcollectiontime = (String)request.getAttribute("maxcollectiontime");
/* 2478 */                     String displayname = null;
/* 2479 */                     double totalsize = 0.0D;
/* 2480 */                     int no_of_db = 0;
/* 2481 */                     ArrayList attribIDs = new ArrayList();
/* 2482 */                     ArrayList resIDs = new ArrayList();
/* 2483 */                     Properties queryStats = (Properties)request.getAttribute("QUERY_STATS");
/* 2484 */                     String error = "";
/*      */                     double rateUpdated;
/* 2486 */                     double rateSelected; double rateInserted; double rateDeleted = rateInserted = rateSelected = rateUpdated = -1.0D;
/*      */                     try {
/* 2488 */                       rateDeleted = Double.parseDouble(queryStats.getProperty("RATE_DELETED"));
/* 2489 */                       rateInserted = Double.parseDouble(queryStats.getProperty("RATE_INSERTED"));
/* 2490 */                       rateSelected = Double.parseDouble(queryStats.getProperty("RATE_SELECTED"));
/* 2491 */                       rateUpdated = Double.parseDouble(queryStats.getProperty("RATE_UPDATED"));
/*      */                     } catch (Exception exc) {
/* 2493 */                       exc.printStackTrace();
/*      */                     }
/*      */                     
/* 2496 */                     for (int i = 101; i <= 120; i++) {
/* 2497 */                       attribIDs.add("" + i);
/*      */                     }
/* 2499 */                     for (int i = 3152; i <= 3157; i++) {
/* 2500 */                       attribIDs.add("" + i);
/*      */                     }
/* 2502 */                     resIDs.add(resourceid);
/*      */                     
/* 2504 */                     out.write(10);
/*      */                     
/* 2506 */                     NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2507 */                     _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2508 */                     _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                     
/* 2510 */                     _jspx_th_logic_005fnotEmpty_005f0.setName("dbdetails");
/* 2511 */                     int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2512 */                     if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                       for (;;) {
/* 2514 */                         out.write(10);
/* 2515 */                         out.write(9);
/*      */                         
/* 2517 */                         IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2518 */                         _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2519 */                         _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                         
/* 2521 */                         _jspx_th_logic_005fiterate_005f0.setName("dbdetails");
/*      */                         
/* 2523 */                         _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                         
/* 2525 */                         _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                         
/* 2527 */                         _jspx_th_logic_005fiterate_005f0.setType("java.util.ArrayList");
/* 2528 */                         int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2529 */                         if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2530 */                           ArrayList row = null;
/* 2531 */                           Integer j = null;
/* 2532 */                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2533 */                             out = _jspx_page_context.pushBody();
/* 2534 */                             _jspx_th_logic_005fiterate_005f0.setBodyContent((BodyContent)out);
/* 2535 */                             _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                           }
/* 2537 */                           row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2538 */                           j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                           for (;;) {
/* 2540 */                             out.write(10);
/* 2541 */                             out.write(9);
/* 2542 */                             out.write(9);
/*      */                             
/* 2544 */                             no_of_db++;
/* 2545 */                             String resid = (String)row.get(2);
/* 2546 */                             totalsize += Double.parseDouble((String)row.get(1));
/* 2547 */                             resIDs.add(resid);
/*      */                             
/* 2549 */                             out.write(10);
/* 2550 */                             out.write(9);
/* 2551 */                             int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2552 */                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 2553 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/* 2554 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2557 */                           if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2558 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2561 */                         if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2562 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                         }
/*      */                         
/* 2565 */                         this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2566 */                         out.write(10);
/* 2567 */                         int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2568 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2572 */                     if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2573 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                     }
/*      */                     else {
/* 2576 */                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2577 */                       out.write(10);
/* 2578 */                       out.write(10);
/* 2579 */                       out.write(10);
/*      */                       
/*      */ 
/* 2582 */                       Properties alert = getStatus(resIDs, attribIDs);
/* 2583 */                       HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                       
/* 2585 */                       displayname = (String)request.getAttribute("monitorname");
/* 2586 */                       String encodeurl = java.net.URLEncoder.encode("/showresource.do?haid=" + request.getParameter("haid") + "&type=MYSQL-DB-server&method=showdetails&resourceid=" + resourceid + "&resourcename=" + name);
/* 2587 */                       if (details == null) {
/* 2588 */                         details = "Availability";
/*      */                       }
/*      */                       
/* 2591 */                       request.setAttribute("tabtoselect", tab);
/* 2592 */                       request.setAttribute("configured", "true");
/* 2593 */                       wlsGraph.setParam(resourceid, "AVAILABILITY");
/*      */                       
/* 2595 */                       out.write("\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t\t<td valign=\"top\">\n\t\t<table width=\"96%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\"\n\t\t\tclass=\"lrtbdarkborder\">\n\t\t\t<tr>\n\t\t\t\t<td colspan=\"2\" class=\"tableheadingbborder\">");
/* 2596 */                       out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2597 */                       out.write("\n\t\t\t\t<span class=\"resourceheading\"> </span></td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td width=\"25%\" class=\"monitorinfoodd\">");
/* 2598 */                       out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2599 */                       out.write("\n\t\t\t\t</td>\n\t\t\t\t<td width=\"75%\" class=\"monitorinfoodd\" title=\"");
/* 2600 */                       out.print(displayname);
/* 2601 */                       out.write(34);
/* 2602 */                       out.write(62);
/* 2603 */                       out.print(getTrimmedText(displayname, 60));
/* 2604 */                       out.write("</td>\n\t\t\t</tr>\n\t\t\t");
/* 2605 */                       out.write("<!--$Id$-->\n");
/*      */                       
/* 2607 */                       String hostName = "localhost";
/*      */                       try {
/* 2609 */                         hostName = java.net.InetAddress.getLocalHost().getHostName();
/*      */                       } catch (Exception ex) {
/* 2611 */                         ex.printStackTrace();
/*      */                       }
/* 2613 */                       String portNumber = System.getProperty("webserver.port");
/* 2614 */                       String styleClass = "monitorinfoodd";
/* 2615 */                       if ((request.getAttribute("amcreated") != null) && (((String)request.getAttribute("amcreated")).equals("YES"))) {
/* 2616 */                         styleClass = "whitegrayborder-conf-mon";
/*      */                       }
/*      */                       
/* 2619 */                       out.write(10);
/*      */                       
/* 2621 */                       PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2622 */                       _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 2623 */                       _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */                       
/* 2625 */                       _jspx_th_logic_005fpresent_005f0.setRole("ENTERPRISEADMIN");
/* 2626 */                       int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 2627 */                       if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2629 */                           out.write("\n<tr>\n  <td width=\"30%\" class=\"");
/* 2630 */                           out.print(styleClass);
/* 2631 */                           out.write(34);
/* 2632 */                           out.write(62);
/* 2633 */                           out.print(FormatUtil.getString("am.webclient.managedserver.name"));
/* 2634 */                           out.write(" </td>\n  <td width=\"70%\" class=\"");
/* 2635 */                           out.print(styleClass);
/* 2636 */                           out.write(34);
/* 2637 */                           out.write(62);
/* 2638 */                           out.print(hostName);
/* 2639 */                           out.write(95);
/* 2640 */                           out.print(portNumber);
/* 2641 */                           out.write("</td>\n</tr>\n");
/* 2642 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 2643 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2647 */                       if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 2648 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/*      */                       }
/*      */                       else {
/* 2651 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 2652 */                         out.write(10);
/* 2653 */                         out.write("\n\t\t\t");
/*      */                         
/* 2655 */                         String healthStatus = alert.getProperty(resourceid + "#" + "116");
/*      */                         
/* 2657 */                         out.write("\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\" valign=\"top\">");
/* 2658 */                         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2659 */                         out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\"\n\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2660 */                         out.print(resourceid);
/* 2661 */                         out.write("&attributeid=116')\">");
/* 2662 */                         out.print(getSeverityImageForHealth(healthStatus));
/* 2663 */                         out.write("</a>\n\t\t\t\t");
/* 2664 */                         out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "116" + "#" + "MESSAGE"), "116", alert.getProperty(resourceid + "#" + "116"), resourceid));
/* 2665 */                         out.write("\n\t\t\t\t");
/* 2666 */                         if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "116") != 0) {
/* 2667 */                           out.write("\n\t\t\t\t<br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2668 */                           out.print(resourceid + "_116");
/* 2669 */                           out.write("')\">");
/* 2670 */                           out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2671 */                           out.write("</a></span>\n                 ");
/*      */                         }
/* 2673 */                         out.write("\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2674 */                         out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2675 */                         out.write("\n\t\t\t\t</td>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2676 */                         out.print(FormatUtil.getString("am.webclient.mysql.servertype"));
/* 2677 */                         out.write("</td>\n\t\t\t</tr>\n\t\t\t");
/*      */                         
/* 2679 */                         Properties mysqldetails = (Properties)request.getAttribute("mysqldetails");
/* 2680 */                         if (mysqldetails.size() != 0)
/*      */                         {
/* 2682 */                           out.write("\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2683 */                           out.print(FormatUtil.getString("am.webclient.mysql.version"));
/* 2684 */                           out.write("</td>\n\t\t\t\t<td class=\"monitorinfoeven\" height=\"21\">");
/* 2685 */                           out.print(mysqldetails.getProperty("5258"));
/* 2686 */                           out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2687 */                           out.print(FormatUtil.getString("am.webclient.common.port.text"));
/* 2688 */                           out.write("</td>\n\t\t\t\t<td height=\"21\" class=\"monitorinfoodd\">");
/* 2689 */                           out.print(mysqldetails.getProperty("5219"));
/* 2690 */                           out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2691 */                           out.print(FormatUtil.getString("am.webclient.mysql.basedirectory"));
/* 2692 */                           out.write("</td>\n\t\t\t\t<td height=\"21\" class=\"monitorinfoeven\"\n\t\t\t\t\ttitle=\"");
/* 2693 */                           out.print(mysqldetails.getProperty("5156"));
/* 2694 */                           out.write(34);
/* 2695 */                           out.write(62);
/* 2696 */                           out.print(getTrimmedText(mysqldetails.getProperty("5156"), 40));
/* 2697 */                           out.write("</td>\n\t\t\t</tr>\n\t\t\t<tr>\n\n\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2698 */                           out.print(FormatUtil.getString("am.webclient.mysql.datadirectory"));
/* 2699 */                           out.write("</td>\n\t\t\t\t<td height=\"21\" class=\"monitorinfoeven\"\n\t\t\t\t\ttitle=\"");
/* 2700 */                           out.print(mysqldetails.getProperty("5161"));
/* 2701 */                           out.write(34);
/* 2702 */                           out.write(62);
/* 2703 */                           out.print(getTrimmedText(mysqldetails.getProperty("5161"), 40));
/* 2704 */                           out.write("</td>\n\t\t\t</tr>\n\t\t\t");
/*      */                         }
/*      */                         
/*      */ 
/* 2708 */                         out.write("\n\t\t\t");
/*      */                         
/* 2710 */                         EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2711 */                         _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2712 */                         _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                         
/* 2714 */                         _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2715 */                         int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2716 */                         if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                           for (;;) {
/* 2718 */                             out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2719 */                             out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2720 */                             out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">-&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2721 */                             out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2722 */                             out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2723 */                             out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2724 */                             out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2725 */                             out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2726 */                             out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t</tr>\n\t\t\t");
/* 2727 */                             int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2728 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2732 */                         if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2733 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*      */                         }
/*      */                         else {
/* 2736 */                           this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2737 */                           out.write("\n\t\t\t");
/*      */                           
/* 2739 */                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f1 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2740 */                           _jspx_th_logic_005fnotEmpty_005f1.setPageContext(_jspx_page_context);
/* 2741 */                           _jspx_th_logic_005fnotEmpty_005f1.setParent(null);
/*      */                           
/* 2743 */                           _jspx_th_logic_005fnotEmpty_005f1.setName("systeminfo");
/* 2744 */                           int _jspx_eval_logic_005fnotEmpty_005f1 = _jspx_th_logic_005fnotEmpty_005f1.doStartTag();
/* 2745 */                           if (_jspx_eval_logic_005fnotEmpty_005f1 != 0) {
/*      */                             for (;;) {
/* 2747 */                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2748 */                               out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2749 */                               out.write("</td>\n\t\t\t\t\t");
/*      */                               
/* 2751 */                               if (systeminfo.get("host_resid") != null)
/*      */                               {
/* 2753 */                                 out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\"><a\n\t\t\t\t\t\thref=\"showresource.do?resourceid=");
/* 2754 */                                 out.print(systeminfo.get("host_resid"));
/* 2755 */                                 out.write("&method=showResourceForResourceID\"\n\t\t\t\t\t\tclass=\"staticlinks\" title=\"");
/* 2756 */                                 out.print(systeminfo.get("HOSTNAME"));
/* 2757 */                                 out.write(34);
/* 2758 */                                 out.write(62);
/* 2759 */                                 out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2760 */                                 out.write("&nbsp;(");
/* 2761 */                                 out.print(systeminfo.get("HOSTIP"));
/* 2762 */                                 out.write(")</a></td>\n\t\t\t\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/* 2767 */                                 out.write("\n\t\t\t\t\t<td class=\"monitorinfoodd\" title=\"");
/* 2768 */                                 out.print(systeminfo.get("HOSTNAME"));
/* 2769 */                                 out.write(34);
/* 2770 */                                 out.write(62);
/* 2771 */                                 out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 20));
/* 2772 */                                 out.write("&nbsp;(");
/* 2773 */                                 out.print(systeminfo.get("HOSTIP"));
/* 2774 */                                 out.write(")</td>\n\t\t\t\t\t");
/*      */                               }
/* 2776 */                               out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2777 */                               out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2778 */                               out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2779 */                               out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 2780 */                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                               
/* 2782 */                               NotEmptyTag _jspx_th_logic_005fnotEmpty_005f2 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2783 */                               _jspx_th_logic_005fnotEmpty_005f2.setPageContext(_jspx_page_context);
/* 2784 */                               _jspx_th_logic_005fnotEmpty_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                               
/* 2786 */                               _jspx_th_logic_005fnotEmpty_005f2.setName("recent5Alarms");
/* 2787 */                               int _jspx_eval_logic_005fnotEmpty_005f2 = _jspx_th_logic_005fnotEmpty_005f2.doStartTag();
/* 2788 */                               if (_jspx_eval_logic_005fnotEmpty_005f2 != 0) {
/*      */                                 for (;;) {
/* 2790 */                                   out.write("\n\t\t\t\t\t");
/*      */                                   
/* 2792 */                                   ArrayList recent = (ArrayList)((ArrayList)request.getAttribute("recent5Alarms")).get(0);
/*      */                                   
/* 2794 */                                   out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2795 */                                   out.print(FormatUtil.getString("am.webclient.mysql.lastalarm"));
/* 2796 */                                   out.write("</td>\n\t\t\t\t\t\t<td class=\"monitorinfoodd\"><a\n\t\t\t\t\t\t\thref=\"/fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2797 */                                   out.print(recent.get(2));
/* 2798 */                                   out.write("&source=");
/* 2799 */                                   out.print(recent.get(4));
/* 2800 */                                   out.write("&category=");
/* 2801 */                                   out.print(recent.get(0));
/* 2802 */                                   out.write("&redirectto=");
/* 2803 */                                   out.print(encodeurl);
/* 2804 */                                   out.write("\"\n\t\t\t\t\t\t\tclass=\"resourcename\">");
/* 2805 */                                   out.print(getTruncatedAlertMessage((String)recent.get(3)));
/* 2806 */                                   out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
/* 2807 */                                   int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f2.doAfterBody();
/* 2808 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2812 */                               if (_jspx_th_logic_005fnotEmpty_005f2.doEndTag() == 5) {
/* 2813 */                                 this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2); return;
/*      */                               }
/*      */                               
/* 2816 */                               this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f2);
/* 2817 */                               out.write("\n\t\t\t\t");
/*      */                               
/* 2819 */                               EmptyTag _jspx_th_logic_005fempty_005f1 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2820 */                               _jspx_th_logic_005fempty_005f1.setPageContext(_jspx_page_context);
/* 2821 */                               _jspx_th_logic_005fempty_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                               
/* 2823 */                               _jspx_th_logic_005fempty_005f1.setName("recent5Alarms");
/* 2824 */                               int _jspx_eval_logic_005fempty_005f1 = _jspx_th_logic_005fempty_005f1.doStartTag();
/* 2825 */                               if (_jspx_eval_logic_005fempty_005f1 != 0) {
/*      */                                 for (;;) {
/* 2827 */                                   out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2828 */                                   out.print(FormatUtil.getString("am.webclient.mysql.lastalarm"));
/* 2829 */                                   out.write("</td>\n\t\t\t\t\t\t<td class=\"monitorinfoodd\">-</td>\n\t\t\t\t\t</tr>\n\t\t\t\t");
/* 2830 */                                   int evalDoAfterBody = _jspx_th_logic_005fempty_005f1.doAfterBody();
/* 2831 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2835 */                               if (_jspx_th_logic_005fempty_005f1.doEndTag() == 5) {
/* 2836 */                                 this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1); return;
/*      */                               }
/*      */                               
/* 2839 */                               this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f1);
/* 2840 */                               out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2841 */                               out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2842 */                               out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoeven\">");
/* 2843 */                               out.print(formatDT((Long)systeminfo.get("LASTDC")));
/* 2844 */                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2845 */                               out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2846 */                               out.write("</td>\n\t\t\t\t\t<td class=\"monitorinfoodd\">");
/* 2847 */                               out.print(formatDT(((Long)systeminfo.get("NEXTDC")).toString()));
/* 2848 */                               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t");
/* 2849 */                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f1.doAfterBody();
/* 2850 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2854 */                           if (_jspx_th_logic_005fnotEmpty_005f1.doEndTag() == 5) {
/* 2855 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/*      */                           }
/*      */                           else {
/* 2858 */                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f1);
/* 2859 */                             out.write("\n\t\t\t");
/* 2860 */                             org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_trstrip.jsp", out, false);
/* 2861 */                             out.write("\n\t\t</table>\n\t\t");
/*      */                             
/* 2863 */                             PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 2864 */                             _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 2865 */                             _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */                             
/* 2867 */                             _jspx_th_logic_005fpresent_005f1.setRole("ADMIN");
/* 2868 */                             int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 2869 */                             if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */                               for (;;) {
/* 2871 */                                 out.write("\n\t\t\t");
/*      */                                 
/* 2873 */                                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2874 */                                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2875 */                                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_logic_005fpresent_005f1);
/*      */                                 
/* 2877 */                                 _jspx_th_c_005fif_005f0.setTest("${showdata=='1'}");
/* 2878 */                                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2879 */                                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                                   for (;;) {
/* 2881 */                                     out.write("\n\n\t\t\t\t<div align=\"center\"><a style=\"cursor: pointer;\">\n\t\t\t\t<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\t\t\t\t\tonclick=\"javascript:toggleDiv('edit')\">\n\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td>\n\t\t\t\t\t\t<table width=\"75%\" border=\"0\" align=\"center\" cellpadding=\"0\"\n\t\t\t\t\t\t\tcellspacing=\"0\" class=\"getmoredatatable\">\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"13%\" background=\"../images/getmoredata_bg.gif\"><img\n\t\t\t\t\t\t\t\t\tsrc=\"../images/icon_getmoredata.gif\" width=\"35\" height=\"35\"\n\t\t\t\t\t\t\t\t\tborder=\"0\" vspace=\"2\" hspace=\"5\"></td>\n\t\t\t\t\t\t\t\t<td width=\"87%\" background=\"../images/getmoredata_bg.gif\">");
/* 2882 */                                     out.print(FormatUtil.getString("am.webclient.configureimage.mysql.text"));
/* 2883 */                                     out.write("</td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t\t</a></div>\n\n\t\t\t");
/* 2884 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2885 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2889 */                                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2890 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                                 }
/*      */                                 
/* 2893 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2894 */                                 out.write(10);
/* 2895 */                                 out.write(9);
/* 2896 */                                 out.write(9);
/* 2897 */                                 int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 2898 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2902 */                             if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 2903 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/*      */                             }
/*      */                             else {
/* 2906 */                               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 2907 */                               out.write("</td>\n\t\t<td width=\"40%\" height=\"31\" class=\"bodytextbold\" valign=\"top\"\n\t\t\ttitle=\"");
/* 2908 */                               out.print(FormatUtil.getString("am.webclient.mysql.availgraph.tooltip"));
/* 2909 */                               out.write("\">\n\n\n\n\t\t<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\"\n\t\t\tclass=\"lrbtborder\">\n\t\t\t<tbody>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"4\" height=\"31\" class=\"tableheadingbborder\">");
/* 2910 */                               out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2911 */                               out.write("\n\t\t\t\t\t<a name=\"Availability\" id=\"Availability\"></a></td>\n\t\t\t\t</tr>\n\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"4\">\n\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2912 */                               if (_jspx_meth_c_005fout_005f0(_jspx_page_context))
/*      */                                 return;
/* 2914 */                               out.write("&period=1&resourcename=");
/* 2915 */                               if (_jspx_meth_c_005fout_005f1(_jspx_page_context))
/*      */                                 return;
/* 2917 */                               out.write("')\">\n\t\t\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\t\thspace=\"5\" vspace=\"5\" border=\"0\"\n\t\t\t\t\t\t\t\ttitle='");
/* 2918 */                               out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2919 */                               out.write("'></a></td>\n\t\t\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2920 */                               if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */                                 return;
/* 2922 */                               out.write("&period=2&resourcename=");
/* 2923 */                               if (_jspx_meth_c_005fout_005f3(_jspx_page_context))
/*      */                                 return;
/* 2925 */                               out.write("')\"><img\n\t\t\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\t\thspace=\"5\" vspace=\"5\" border=\"0\"\n\t\t\t\t\t\t\t\ttitle='");
/* 2926 */                               out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2927 */                               out.write("'></a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"4\" align=\"center\">");
/*      */                               
/* 2929 */                               AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 2930 */                               _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2931 */                               _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */                               
/* 2933 */                               _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                               
/* 2935 */                               _jspx_th_awolf_005fpiechart_005f0.setWidth("250");
/*      */                               
/* 2937 */                               _jspx_th_awolf_005fpiechart_005f0.setHeight("200");
/*      */                               
/* 2939 */                               _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                               
/* 2941 */                               _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                               
/* 2943 */                               _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                               
/* 2945 */                               _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 2946 */                               int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 2947 */                               if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 2948 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2949 */                                   out = _jspx_page_context.pushBody();
/* 2950 */                                   _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 2951 */                                   _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2954 */                                   out.write("\n\t\t\t\t\t\t");
/*      */                                   
/* 2956 */                                   Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2957 */                                   _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 2958 */                                   _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                                   
/* 2960 */                                   _jspx_th_awolf_005fmap_005f0.setId("color");
/* 2961 */                                   int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 2962 */                                   if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 2963 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2964 */                                       out = _jspx_page_context.pushBody();
/* 2965 */                                       _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 2966 */                                       _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2969 */                                       out.write("\n\t\t\t\t\t\t\t");
/*      */                                       
/* 2971 */                                       AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2972 */                                       _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2973 */                                       _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 2975 */                                       _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                       
/* 2977 */                                       _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 2978 */                                       int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 2979 */                                       if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 2980 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                       }
/*      */                                       
/* 2983 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 2984 */                                       out.write("\n\t\t\t\t\t\t\t");
/*      */                                       
/* 2986 */                                       AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2987 */                                       _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2988 */                                       _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                       
/* 2990 */                                       _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                       
/* 2992 */                                       _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 2993 */                                       int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 2994 */                                       if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 2995 */                                         this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                       }
/*      */                                       
/* 2998 */                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 2999 */                                       out.write("\n\t\t\t\t\t\t");
/* 3000 */                                       int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 3001 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 3004 */                                     if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 3005 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 3008 */                                   if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 3009 */                                     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                                   }
/*      */                                   
/* 3012 */                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 3013 */                                   out.write(32);
/* 3014 */                                   out.write("\n\t\t\t\t\t");
/* 3015 */                                   int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 3016 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3019 */                                 if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 3020 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3023 */                               if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 3024 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/*      */                               }
/*      */                               else {
/* 3027 */                                 this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 3028 */                                 out.write("</td> ");
/* 3029 */                                 out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<td width=\"49%\" class=\"yellowgrayborder\" colspan=\"2\">");
/* 3030 */                                 out.print(FormatUtil.getString("am.webclient.common.currentstatus.text"));
/* 3031 */                                 out.write("\n\t\t\t\t:\n\t\t\t\t");
/*      */                                 
/* 3033 */                                 String avastatus = alert.getProperty(resourceid + "#" + "115");
/*      */                                 
/* 3035 */                                 out.write("\n\t\t\t\t<a\n\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3036 */                                 out.print(resourceid);
/* 3037 */                                 out.write("&attributeid=115')\">");
/* 3038 */                                 out.print(getSeverityImageForAvailability(avastatus));
/* 3039 */                                 out.write("</a></td>\n\t\t\t\t");
/*      */                                 
/* 3041 */                                 if (!EnterpriseUtil.isAdminServer())
/*      */                                 {
/*      */ 
/* 3044 */                                   out.write("\n\t\t\t\t<td width=\"51%\" class=\"yellowgrayborder\" align=\"right\"><img\n\t\t\t\tsrc=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3045 */                                   out.print(resourceid);
/* 3046 */                                   out.write("&attributeIDs=115,116&attributeToSelect=115&redirectto=");
/* 3047 */                                   out.print(encodeurl);
/* 3048 */                                   out.write("\"\n\t\t\t\tclass=\"links\">");
/* 3049 */                                   out.print(ALERTCONFIG_TEXT);
/* 3050 */                                   out.write("</a>&nbsp;</td>\n\t\t\t\t");
/*      */                                 }
/* 3052 */                                 out.write("\n\t\t\t\t</tr>\n\n\n\n\t\t\t</tbody>\n\t\t</table>\n\t\t</td>\n\t</tr>\n</table>\n<table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 3053 */                                 org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "MyField_div.jsp", out, false);
/* 3054 */                                 out.write("</td></tr></table>\n\n");
/*      */                                 
/* 3056 */                                 Properties p = (Properties)request.getAttribute("perf");
/* 3057 */                                 mysqlgraph.setresid(Integer.parseInt(resourceid));
/* 3058 */                                 mysqlgraph.settype("CONNECTIONTIME");
/*      */                                 
/* 3060 */                                 out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td>&nbsp;</td>\n\t</tr>\n</table>\n<!--table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n <tr>\n    <td width=\"35%\" height=\"18\"> &nbsp;<span class=\"bodytextbold\">Performance\n      ");
/* 3061 */                                 out.print(">");
/* 3062 */                                 out.write("\n      <span class=\"names\">");
/* 3063 */                                 out.print(displayname);
/* 3064 */                                 out.write("</span>\n      </span>\n      </td>\n </tr>\n </table-->\n");
/*      */                                 
/* 3066 */                                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3067 */                                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3068 */                                 _jspx_th_c_005fif_005f1.setParent(null);
/*      */                                 
/* 3070 */                                 _jspx_th_c_005fif_005f1.setTest("${showdata=='1'}");
/* 3071 */                                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3072 */                                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                   for (;;) {
/* 3074 */                                     out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"100%\" height=\"29\" class=\"tableheadingtrans\">");
/* 3075 */                                     out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3076 */                                     out.write("\n\t\t\t- ");
/* 3077 */                                     out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3078 */                                     out.write("&nbsp;</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrborder\">\n\t\t<tr>\n\t\t\t<td width=\"405\" height=\"127\" valign=\"top\">\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"70%\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3079 */                                     if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3081 */                                     out.write("&attributeid=101&period=-7',740,550)\">\n\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3082 */                                     out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3083 */                                     out.write("'></a></td>\n\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3084 */                                     if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3086 */                                     out.write("&attributeid=101&period=-30',740,550)\"><img\n\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3087 */                                     out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3088 */                                     out.write("'></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\">\n\t\t\t\t\t");
/*      */                                     
/* 3090 */                                     perfgraph.setresourceid(Integer.parseInt(resourceid));
/* 3091 */                                     perfgraph.setEntity("Connection Time");
/*      */                                     
/* 3093 */                                     out.write(32);
/*      */                                     
/* 3095 */                                     TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(TimeChart.class);
/* 3096 */                                     _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3097 */                                     _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_c_005fif_005f1);
/*      */                                     
/* 3099 */                                     _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("perfgraph");
/*      */                                     
/* 3101 */                                     _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                                     
/* 3103 */                                     _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                                     
/* 3105 */                                     _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                                     
/* 3107 */                                     _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                     
/* 3109 */                                     _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.timeinms.text"));
/*      */                                     
/* 3111 */                                     _jspx_th_awolf_005ftimechart_005f0.setDateFormat("HH:mm");
/* 3112 */                                     int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3113 */                                     if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3114 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3115 */                                         out = _jspx_page_context.pushBody();
/* 3116 */                                         _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3117 */                                         _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                                       }
/*      */                                       for (;;) {
/* 3120 */                                         out.write("\n\t\t\t\t\t");
/* 3121 */                                         int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3122 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 3125 */                                       if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3126 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 3129 */                                     if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3130 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                                     }
/*      */                                     
/* 3133 */                                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3134 */                                     out.write(32);
/* 3135 */                                     out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td width=\"562\" valign=\"top\"><br>\n\t\t\t<br>\n\t\t\t<table align=\"left\" width=\"95%\" border=\"0\" cellspacing=\"0\"\n\t\t\t\tcellpadding=\"0\" class=\"lrbtborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3136 */                                     if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3138 */                                     out.write("</span></td> ");
/* 3139 */                                     out.write("\n\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3140 */                                     if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3142 */                                     out.write("</span></td> ");
/* 3143 */                                     out.write("\n\t\t\t\t\t<td class=\"columnheadingnotop\" colspan=\"2\"><span\n\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3144 */                                     if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_c_005fif_005f1, _jspx_page_context))
/*      */                                       return;
/* 3146 */                                     out.write("</span></td> ");
/* 3147 */                                     out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"56%\" height=\"19\" class=\"whitegrayborder\">");
/* 3148 */                                     out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3149 */                                     out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"26%\" height=\"19\" class=\"whitegrayborder\">\n\t\t\t\t\t");
/*      */                                     
/* 3151 */                                     if (perfgraph.getResponseTime(Integer.parseInt(resourceid)) != -1L)
/*      */                                     {
/*      */ 
/* 3154 */                                       out.write(32);
/* 3155 */                                       out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(resourceid))));
/* 3156 */                                       out.write("&nbsp;\n\t\t\t\t\t");
/* 3157 */                                       out.print(FormatUtil.getString("ms"));
/* 3158 */                                       out.write(32);
/*      */ 
/*      */                                     }
/*      */                                     else
/*      */                                     {
/*      */ 
/* 3164 */                                       out.write(32);
/* 3165 */                                       out.write(45);
/* 3166 */                                       out.write(32);
/*      */                                     }
/*      */                                     
/*      */ 
/* 3170 */                                     out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"whitegrayborder\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3171 */                                     out.print(resourceid);
/* 3172 */                                     out.write("&attributeid=101')\">");
/* 3173 */                                     out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "101")));
/* 3174 */                                     out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                     
/* 3176 */                                     if (!EnterpriseUtil.isAdminServer())
/*      */                                     {
/*      */ 
/* 3179 */                                       out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\">\n\t\t\t\t\t<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3180 */                                       out.print(resourceid);
/* 3181 */                                       out.write("&attributeIDs=101&attributeToSelect=101&redirectto=");
/* 3182 */                                       out.print(encodeurl);
/* 3183 */                                       out.write("\"\n\t\t\t\t\t\tclass=\"links\">");
/* 3184 */                                       out.print(ALERTCONFIG_TEXT);
/* 3185 */                                       out.write("</a>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                     }
/* 3187 */                                     out.write("\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td height=\"26\" class=\"tablebottom\">&nbsp;</td>\n\t\t</tr>\n\t</table>\n");
/* 3188 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3189 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 3193 */                                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3194 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/*      */                                 }
/*      */                                 else {
/* 3197 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3198 */                                   out.write(10);
/* 3199 */                                   out.write(10);
/*      */                                   
/* 3201 */                                   IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3202 */                                   _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3203 */                                   _jspx_th_c_005fif_005f2.setParent(null);
/*      */                                   
/* 3205 */                                   _jspx_th_c_005fif_005f2.setTest("${showdata!='1'}");
/* 3206 */                                   int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3207 */                                   if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                                     for (;;) {
/* 3209 */                                       out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"51%\" height=\"26\" class=\"tableheading\"\n\t\t\t\ttitle=\"");
/* 3210 */                                       out.print(FormatUtil.getString("am.webclient.mysql.con.tooltip"));
/* 3211 */                                       out.write(34);
/* 3212 */                                       out.write(62);
/* 3213 */                                       out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3214 */                                       out.write("\n\t\t\t- ");
/* 3215 */                                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3216 */                                       out.write("</td>\n\t\t\t<td width=\"49%\" class=\"tableheading\">");
/* 3217 */                                       out.print(FormatUtil.getString("am.webclient.mysql.requeststatistics"));
/* 3218 */                                       out.write("\n\t\t\t- ");
/* 3219 */                                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3220 */                                       out.write("</td>\n\t\t</tr>\n\t</table>\n\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td class=\"rbborder\">\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3221 */                                       if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3223 */                                       out.write("&attributeid=101&period=-7',740,550)\">\n\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3224 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3225 */                                       out.write("'></a></td>\n\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3226 */                                       if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3228 */                                       out.write("&attributeid=101&period=-30',740,550)\"><img\n\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3229 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3230 */                                       out.write("'></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\ttitle=\"");
/* 3231 */                                       out.print(FormatUtil.getString("am.webclient.mysql.constatus.tooltip"));
/* 3232 */                                       out.write("\">\n\n\t\t\t\t\t");
/*      */                                       
/* 3234 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3235 */                                       _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3236 */                                       _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 3238 */                                       _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 3240 */                                       _jspx_th_awolf_005ftimechart_005f1.setWidth("300");
/*      */                                       
/* 3242 */                                       _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                                       
/* 3244 */                                       _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                                       
/* 3246 */                                       _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 3248 */                                       _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.timeinms.text"));
/* 3249 */                                       int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3250 */                                       if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3251 */                                         if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3252 */                                           out = _jspx_page_context.pushBody();
/* 3253 */                                           _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3254 */                                           _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3257 */                                           out.write("\n\t\t\t\t\t");
/* 3258 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3259 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3262 */                                         if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3263 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3266 */                                       if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3267 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                                       }
/*      */                                       
/* 3270 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3271 */                                       out.write("</td> ");
/* 3272 */                                       out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\n\t\t\t<td class=\"bottomborder\">\n\t\t\t");
/*      */                                       
/* 3274 */                                       mysqlgraph.settype("REQUESTRATE");
/*      */                                       
/* 3276 */                                       out.write("\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3277 */                                       if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3279 */                                       out.write("&attributeid=102&period=-7',740,550)\">\n\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3280 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3281 */                                       out.write("'></a></td>\n\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3282 */                                       if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3284 */                                       out.write("&attributeid=102&period=-30',740,550)\"><img\n\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3285 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3286 */                                       out.write("'></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\ttitle=\"");
/* 3287 */                                       out.print(FormatUtil.getString("am.webclient.mysql.reqgraph.tooltip"));
/* 3288 */                                       out.write("\">\n\t\t\t\t\t");
/*      */                                       
/* 3290 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3291 */                                       _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3292 */                                       _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 3294 */                                       _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 3296 */                                       _jspx_th_awolf_005ftimechart_005f2.setWidth("300");
/*      */                                       
/* 3298 */                                       _jspx_th_awolf_005ftimechart_005f2.setHeight("170");
/*      */                                       
/* 3300 */                                       _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                                       
/* 3302 */                                       _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 3304 */                                       _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.mysql.graph.requestspersec"));
/* 3305 */                                       int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3306 */                                       if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3307 */                                         if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3308 */                                           out = _jspx_page_context.pushBody();
/* 3309 */                                           _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3310 */                                           _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3313 */                                           out.write("\n\t\t\t\t\t");
/* 3314 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3315 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3318 */                                         if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3319 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3322 */                                       if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3323 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                                       }
/*      */                                       
/* 3326 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3327 */                                       out.write("</td> ");
/* 3328 */                                       out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td width=\"50%\" align=\"center\" valign=\"top\" class=\"rborder\">\n\t\t\t<table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\"\n\t\t\t\tcellpadding=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3329 */                                       if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3331 */                                       out.write("</span></td> ");
/* 3332 */                                       out.write("\n\t\t\t\t\t<td width=\"28%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3333 */                                       if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3335 */                                       out.write("</span></td> ");
/* 3336 */                                       out.write("\n\t\t\t\t\t<td width=\"29%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3337 */                                       if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3339 */                                       out.write("</span></td> ");
/* 3340 */                                       out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"40%\" class=\"whitegrayborder\"\n\t\t\t\t\t\ttitle=\"");
/* 3341 */                                       out.print(FormatUtil.getString("am.webclient.mysql.con.tooltip"));
/* 3342 */                                       out.write(34);
/* 3343 */                                       out.write(62);
/* 3344 */                                       out.print(FormatUtil.getString("am.webclient.common.connectiontime.text"));
/* 3345 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"32%\" class=\"whitegrayborder\">\n\t\t\t\t\t");
/*      */                                       
/* 3347 */                                       if (p.containsKey("CONNECTIONTIME"))
/*      */                                       {
/*      */ 
/* 3350 */                                         out.write(32);
/* 3351 */                                         out.print(formatNumber(p.getProperty("CONNECTIONTIME")));
/* 3352 */                                         out.write("&nbsp; ");
/* 3353 */                                         out.print(FormatUtil.getString("ms"));
/* 3354 */                                         out.write("\n\t\t\t\t\t");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3360 */                                         out.write(32);
/* 3361 */                                         out.write(45);
/* 3362 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3366 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"whitegrayborder\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3367 */                                       out.print(resourceid);
/* 3368 */                                       out.write("&attributeid=101')\">");
/* 3369 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "101")));
/* 3370 */                                       out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"40%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\ttitle=\"");
/* 3371 */                                       out.print(FormatUtil.getString("am.webclient.mysql.connectiontimeout.tooltip"));
/* 3372 */                                       out.write(34);
/* 3373 */                                       out.write(62);
/* 3374 */                                       out.print(FormatUtil.getString("am.webclient.mysql.connectiontimeout"));
/* 3375 */                                       out.write("</td>\n\t\t\t\t\t<td width=\"32%\" class=\"yellowgrayborder\">\n\t\t\t\t\t");
/*      */                                       
/* 3377 */                                       if (mysqldetails.size() != 0)
/*      */                                       {
/*      */ 
/* 3380 */                                         out.write(32);
/* 3381 */                                         out.print(formatNumber(mysqldetails.getProperty("5160")));
/* 3382 */                                         out.write("\n\t\t\t\t\t");
/* 3383 */                                         out.print(FormatUtil.getString("Sec"));
/* 3384 */                                         out.write("&nbsp; ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3390 */                                         out.write(32);
/* 3391 */                                         out.write(45);
/* 3392 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3396 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                       
/* 3398 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 3401 */                                         out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" align=\"right\" height=\"21\" class=\"whitegrayborder\"\n\t\t\t\t\t\tclass=\"bodytext\"><img src=\"/images/icon_associateaction.gif\"\n\t\t\t\t\t\talign=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3402 */                                         out.print(resourceid);
/* 3403 */                                         out.write("&attributeIDs=101&attributeToSelect=101&redirectto=");
/* 3404 */                                         out.print(encodeurl);
/* 3405 */                                         out.write("\"\n\t\t\t\t\t\tclass=\"links\">");
/* 3406 */                                         out.print(ALERTCONFIG_TEXT);
/* 3407 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */                                       }
/* 3409 */                                       out.write("\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" class=\"yellowgrayborder\">&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td width=\"49%\" align=\"center\" valign=\"top\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3410 */                                       if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3412 */                                       out.write("</span></td> ");
/* 3413 */                                       out.write("\n\t\t\t\t\t\t<td width=\"26%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3414 */                                       if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3416 */                                       out.write("</span></td> ");
/* 3417 */                                       out.write("\n\t\t\t\t\t\t<td width=\"37%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3418 */                                       if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3420 */                                       out.write("</span></td> ");
/* 3421 */                                       out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"40%\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3422 */                                       out.print(FormatUtil.getString("am.webclient.mysql.reqstatus.tooltip"));
/* 3423 */                                       out.write(34);
/* 3424 */                                       out.write(62);
/* 3425 */                                       out.print(FormatUtil.getString("am.webclient.mysql.requestrate"));
/* 3426 */                                       out.write("</td>\n\t\t\t\t\t\t<td width=\"32%\" class=\"whitegrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3428 */                                       if (p.containsKey("REQUESTRATE"))
/*      */                                       {
/*      */ 
/* 3431 */                                         out.write(32);
/* 3432 */                                         out.print(formatNumber(p.getProperty("REQUESTRATE")));
/* 3433 */                                         out.write("&nbsp;");
/* 3434 */                                         out.print(FormatUtil.getString("Req/Sec"));
/* 3435 */                                         out.write("\n\t\t\t\t\t\t");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3441 */                                         out.write(32);
/* 3442 */                                         out.write(45);
/* 3443 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3447 */                                       out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td class=\"whitegrayborder\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3448 */                                       out.print(resourceid);
/* 3449 */                                       out.write("&attributeid=102')\">");
/* 3450 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "102")));
/* 3451 */                                       out.write("\n\t\t\t\t\t\t</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"40%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3452 */                                       out.print(FormatUtil.getString("am.webclient.mysql.bytesrec.tooltip"));
/* 3453 */                                       out.write(34);
/* 3454 */                                       out.write(62);
/* 3455 */                                       out.print(FormatUtil.getString("am.webclient.mysql.bytesreceivedrate"));
/* 3456 */                                       out.write("\n\t\t\t\t\t\t<br>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"32%\" class=\"yellowgrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3458 */                                       if (p.containsKey("BYTESRECEIVEDRATE"))
/*      */                                       {
/*      */ 
/* 3461 */                                         out.write(32);
/* 3462 */                                         out.print(formatNumber(p.getProperty("BYTESRECEIVEDRATE")));
/* 3463 */                                         out.write("&nbsp; ");
/* 3464 */                                         out.print(FormatUtil.getString("Bytes/Sec"));
/* 3465 */                                         out.write("\n\t\t\t\t\t\t");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3471 */                                         out.write(32);
/* 3472 */                                         out.write(45);
/* 3473 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3477 */                                       out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td class=\"yellowgrayborder\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3478 */                                       out.print(resourceid);
/* 3479 */                                       out.write("&attributeid=103')\">");
/* 3480 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "103")));
/* 3481 */                                       out.write("</a>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"40%\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3482 */                                       out.print(FormatUtil.getString("am.webclient.mysql.send.tooltip"));
/* 3483 */                                       out.write(34);
/* 3484 */                                       out.write(62);
/* 3485 */                                       out.print(FormatUtil.getString("am.webclient.mysql.bytessendrate"));
/* 3486 */                                       out.write("</td>\n\t\t\t\t\t\t<td width=\"32%\" class=\"whitegrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3488 */                                       if (p.containsKey("BYTESSENDRATE"))
/*      */                                       {
/*      */ 
/* 3491 */                                         out.write(32);
/* 3492 */                                         out.print(formatNumber(p.getProperty("BYTESSENDRATE")));
/* 3493 */                                         out.write("&nbsp; ");
/* 3494 */                                         out.print(FormatUtil.getString("Bytes/Sec"));
/* 3495 */                                         out.write("\n\t\t\t\t\t\t");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3501 */                                         out.write(32);
/* 3502 */                                         out.write(45);
/* 3503 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3507 */                                       out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td class=\"whitegrayborder\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3508 */                                       out.print(resourceid);
/* 3509 */                                       out.write("&attributeid=104')\">");
/* 3510 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "104")));
/* 3511 */                                       out.write("</a>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       
/* 3513 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 3516 */                                         out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\" height=\"21\" align=\"right\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\tclass=\"bodytext\"><img src=\"/images/icon_associateaction.gif\"\n\t\t\t\t\t\t\talign=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3517 */                                         out.print(resourceid);
/* 3518 */                                         out.write("&attributeIDs=102,103,104&attributeToSelect=102&redirectto=");
/* 3519 */                                         out.print(encodeurl);
/* 3520 */                                         out.write("\"\n\t\t\t\t\t\t\tclass=\"links\">");
/* 3521 */                                         out.print(ALERTCONFIG_TEXT);
/* 3522 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       }
/* 3524 */                                       out.write("\n\t\t\t\t</tbody>\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\n\t");
/*      */                                       
/* 3526 */                                       p = new Properties();
/* 3527 */                                       p = (Properties)request.getAttribute("servertuning");
/*      */                                       
/* 3529 */                                       mysqlgraph.setresid(Integer.parseInt(resourceid));
/* 3530 */                                       mysqlgraph.settype("MAXCONNECTION");
/*      */                                       
/* 3532 */                                       out.write("\n\t<br>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"26\" class=\"tableheadingtrans\">");
/* 3533 */                                       out.print(FormatUtil.getString("am.webclient.mysql.connectionstatistics"));
/* 3534 */                                       out.write("\n\t\t\t- ");
/* 3535 */                                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3536 */                                       out.write("</td>\n\t\t\t<td width=\"50%\" class=\"tableheadingtrans\">");
/* 3537 */                                       out.print(FormatUtil.getString("am.webclient.mysql.threaddetails"));
/* 3538 */                                       out.write("\n\t\t\t- ");
/* 3539 */                                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3540 */                                       out.write("</td>\n\t\t</tr>\n\t</table>\n\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td class=\"rbborder\">\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3541 */                                       if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3543 */                                       out.write("&attributeid=105&period=-7',740,550)\">\n\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3544 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3545 */                                       out.write("'></a></td>\n\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3546 */                                       if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3548 */                                       out.write("&attributeid=105&period=-30',740,550)\"><img\n\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3549 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3550 */                                       out.write("'></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\ttitle=\"");
/* 3551 */                                       out.print(FormatUtil.getString("am.webclient.mysql.connectiongrph.tooltip"));
/* 3552 */                                       out.write("\">\n\n\t\t\t\t\t");
/*      */                                       
/* 3554 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3555 */                                       _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3556 */                                       _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 3558 */                                       _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 3560 */                                       _jspx_th_awolf_005ftimechart_005f3.setWidth("300");
/*      */                                       
/* 3562 */                                       _jspx_th_awolf_005ftimechart_005f3.setHeight("170");
/*      */                                       
/* 3564 */                                       _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                                       
/* 3566 */                                       _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 3568 */                                       _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 3569 */                                       int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3570 */                                       if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3571 */                                         if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3572 */                                           out = _jspx_page_context.pushBody();
/* 3573 */                                           _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3574 */                                           _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3577 */                                           out.write("\n\t\t\t\t\t");
/* 3578 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3579 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3582 */                                         if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3583 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3586 */                                       if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3587 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                                       }
/*      */                                       
/* 3590 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3591 */                                       out.write("</td> ");
/* 3592 */                                       out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\n\t\t\t<td class=\"bottomborder\">\n\t\t\t");
/*      */                                       
/* 3594 */                                       mysqlgraph.settype("THREADS");
/* 3595 */                                       if (mysqldetails.containsKey("5264")) {
/* 3596 */                                         mysqlgraph.setthreadcachesize(Long.parseLong(mysqldetails.getProperty("5264")));
/*      */                                       } else {
/* 3598 */                                         mysqlgraph.setthreadcachesize(0L);
/*      */                                       }
/*      */                                       
/* 3601 */                                       out.write("\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3602 */                                       if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3604 */                                       out.write("&attributeid=108&period=-7',740,550)\">\n\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3605 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3606 */                                       out.write("'></a></td>\n\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3607 */                                       if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3609 */                                       out.write("&attributeid=108&period=-30',740,550)\"><img\n\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 3610 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3611 */                                       out.write("'></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\ttitle=\"");
/* 3612 */                                       out.print(FormatUtil.getString("am.webclient.mysql.threadgraph.tooltip"));
/* 3613 */                                       out.write("\">\n\t\t\t\t\t");
/*      */                                       
/* 3615 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3616 */                                       _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 3617 */                                       _jspx_th_awolf_005ftimechart_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 3619 */                                       _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 3621 */                                       _jspx_th_awolf_005ftimechart_005f4.setWidth("300");
/*      */                                       
/* 3623 */                                       _jspx_th_awolf_005ftimechart_005f4.setHeight("170");
/*      */                                       
/* 3625 */                                       _jspx_th_awolf_005ftimechart_005f4.setLegend("true");
/*      */                                       
/* 3627 */                                       _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 3629 */                                       _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 3630 */                                       int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 3631 */                                       if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 3632 */                                         if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3633 */                                           out = _jspx_page_context.pushBody();
/* 3634 */                                           _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 3635 */                                           _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 3638 */                                           out.write("\n\t\t\t\t\t");
/* 3639 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 3640 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 3643 */                                         if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 3644 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 3647 */                                       if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 3648 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                                       }
/*      */                                       
/* 3651 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 3652 */                                       out.write("</td> ");
/* 3653 */                                       out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td width=\"50%\" align=\"center\" valign=\"top\" class=\"rborder\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3654 */                                       if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3656 */                                       out.write("</span></td>");
/* 3657 */                                       out.write("\n\t\t\t\t\t\t<td width=\"20%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3658 */                                       if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3660 */                                       out.write("</span></td> ");
/* 3661 */                                       out.write("\n\t\t\t\t\t\t<td width=\"30%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3662 */                                       if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3664 */                                       out.write("</span></td> ");
/* 3665 */                                       out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t\t<td width=\"50%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\ttitle=\"");
/* 3666 */                                       out.print(FormatUtil.getString("am.webclient.mysql.opencon.tooltip"));
/* 3667 */                                       out.write(34);
/* 3668 */                                       out.write(62);
/* 3669 */                                       out.print(FormatUtil.getString("am.webclient.mysql.openconnections"));
/* 3670 */                                       out.write("</td>\n\t\t\t\t\t<td width=\"20%\" class=\"whitegrayborder\">\n\t\t\t\t\t");
/*      */                                       
/* 3672 */                                       if (p.containsKey("OPENCONNECTIONS"))
/*      */                                       {
/*      */ 
/* 3675 */                                         out.write(32);
/* 3676 */                                         out.print(formatNumber(p.getProperty("OPENCONNECTIONS")));
/* 3677 */                                         out.write("&nbsp; ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3683 */                                         out.write(32);
/* 3684 */                                         out.write(45);
/* 3685 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3689 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"30%\" class=\"whitegrayborder\"><a\n\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3690 */                                       out.print(resourceid);
/* 3691 */                                       out.write("&attributeid=105')\">");
/* 3692 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "105")));
/* 3693 */                                       out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"50%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3694 */                                       out.print(FormatUtil.getString("am.webclient.mysql.acon.tooltip"));
/* 3695 */                                       out.write(34);
/* 3696 */                                       out.write(62);
/* 3697 */                                       out.print(FormatUtil.getString("am.webclient.mysql.abortedconnections"));
/* 3698 */                                       out.write("<br>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"20%\" class=\"yellowgrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3700 */                                       if (p.containsKey("ABORTEDCONNECTIONS"))
/*      */                                       {
/*      */ 
/* 3703 */                                         out.write(32);
/* 3704 */                                         out.print(formatNumber(p.getProperty("ABORTEDCONNECTIONS")));
/* 3705 */                                         out.write(32);
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3711 */                                         out.write(32);
/* 3712 */                                         out.write(45);
/* 3713 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3717 */                                       out.write(" <br>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"30%\" class=\"yellowgrayborder\"><a\n\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3718 */                                       out.print(resourceid);
/* 3719 */                                       out.write("&attributeid=106')\">");
/* 3720 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "106")));
/* 3721 */                                       out.write("</a>&nbsp;&nbsp;<a\n\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3722 */                                       if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3724 */                                       out.write("&attributeid=106&period=-7',740,550)\"><img\n\t\t\t\t\t\t\tsrc=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\ttitle='");
/* 3725 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3726 */                                       out.write("'></a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"50%\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3727 */                                       out.print(FormatUtil.getString("am.webclient.mysql.aclients.tooltip"));
/* 3728 */                                       out.write(34);
/* 3729 */                                       out.write(62);
/* 3730 */                                       out.print(FormatUtil.getString("am.webclient.mysql.abortedclients"));
/* 3731 */                                       out.write("</td>\n\t\t\t\t\t\t<td width=\"20%\" class=\"whitegrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3733 */                                       if (p.containsKey("ABORTEDCLIENTS"))
/*      */                                       {
/*      */ 
/* 3736 */                                         out.write(32);
/* 3737 */                                         out.print(formatNumber(p.getProperty("ABORTEDCLIENTS")));
/* 3738 */                                         out.write(32);
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3744 */                                         out.write(32);
/* 3745 */                                         out.write(45);
/* 3746 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3750 */                                       out.write(" <br>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"30%\" class=\"whitegrayborder\"><a\n\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3751 */                                       out.print(resourceid);
/* 3752 */                                       out.write("&attributeid=107')\">");
/* 3753 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "107")));
/* 3754 */                                       out.write("</a>&nbsp;&nbsp;<a\n\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3755 */                                       if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3757 */                                       out.write("&attributeid=107&period=-7',740,550)\"><img\n\t\t\t\t\t\t\tsrc=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\ttitle='");
/* 3758 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3759 */                                       out.write("'></a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       
/* 3761 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 3764 */                                         out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img\n\t\t\t\t\t\t\tsrc=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3765 */                                         out.print(resourceid);
/* 3766 */                                         out.write("&attributeIDs=105,106,107&attributeToSelect=105&redirectto=");
/* 3767 */                                         out.print(encodeurl);
/* 3768 */                                         out.write("\"\n\t\t\t\t\t\t\tclass=\"links\">");
/* 3769 */                                         out.print(ALERTCONFIG_TEXT);
/* 3770 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       }
/* 3772 */                                       out.write("\n\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td width=\"50%\" valign=\"top\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t<tbody>\n\t\t\t\t\t<tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3773 */                                       if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3775 */                                       out.write("</span></td> ");
/* 3776 */                                       out.write("\n\t\t\t\t\t\t<td width=\"20%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3777 */                                       if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3779 */                                       out.write("</span></td> ");
/* 3780 */                                       out.write("\n\t\t\t\t\t\t<td width=\"30%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 3781 */                                       if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3783 */                                       out.write("</span></td> ");
/* 3784 */                                       out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t\t<td width=\"50%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\ttitle=\"");
/* 3785 */                                       out.print(FormatUtil.getString("am.webclient.mysql.threadsused.tooltip"));
/* 3786 */                                       out.write(34);
/* 3787 */                                       out.write(62);
/* 3788 */                                       out.print(FormatUtil.getString("am.webclient.mysql.threadsused"));
/* 3789 */                                       out.write("</td>\n\t\t\t\t\t<td width=\"20%\" height=\"35\" class=\"whitegrayborder\">\n\t\t\t\t\t");
/*      */                                       
/* 3791 */                                       if (p.containsKey("THREADSUSED"))
/*      */                                       {
/*      */ 
/* 3794 */                                         out.write(32);
/* 3795 */                                         out.print(formatNumber(p.getProperty("THREADSUSED")));
/* 3796 */                                         out.write("&nbsp; ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3802 */                                         out.write(32);
/* 3803 */                                         out.write(45);
/* 3804 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3808 */                                       out.write("\n\t\t\t\t\t</td>\n\t\t\t\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3809 */                                       out.print(resourceid);
/* 3810 */                                       out.write("&attributeid=108')\">");
/* 3811 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "108")));
/* 3812 */                                       out.write("</a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td height=\"35\" width=\"50%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3813 */                                       out.print(FormatUtil.getString("am.webclient.mysql.cache.tooltip"));
/* 3814 */                                       out.write(34);
/* 3815 */                                       out.write(62);
/* 3816 */                                       out.print(FormatUtil.getString("am.webclient.mysql.threadsincache"));
/* 3817 */                                       out.write("<br>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td height=\"35\" width=\"20%\" class=\"yellowgrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3819 */                                       if (p.containsKey("THREADSINCACHE"))
/*      */                                       {
/*      */ 
/* 3822 */                                         out.write(32);
/* 3823 */                                         out.print(formatNumber(p.getProperty("THREADSINCACHE")));
/* 3824 */                                         out.write(32);
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3830 */                                         out.write(32);
/* 3831 */                                         out.write(45);
/* 3832 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3836 */                                       out.write("\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"30%\" height=\"35\" class=\"yellowgrayborder\"><a\n\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3837 */                                       out.print(resourceid);
/* 3838 */                                       out.write("&attributeid=109')\">");
/* 3839 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "109")));
/* 3840 */                                       out.write("</a>&nbsp;&nbsp;<a\n\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3841 */                                       if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 3843 */                                       out.write("&attributeid=109&period=-7',740,550)\"><img\n\t\t\t\t\t\t\tsrc=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\ttitle='");
/* 3844 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3845 */                                       out.write("'></a></td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"50%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\ttitle=\"");
/* 3846 */                                       out.print(FormatUtil.getString("am.webclient.mysql.cachesize.tooltip"));
/* 3847 */                                       out.write(34);
/* 3848 */                                       out.write(62);
/* 3849 */                                       out.print(FormatUtil.getString("am.webclient.mysql.threadcachesize"));
/* 3850 */                                       out.write("</td>\n\t\t\t\t\t\t<td width=\"20%\" height=\"35\" class=\"whitegrayborder\">\n\t\t\t\t\t\t");
/*      */                                       
/* 3852 */                                       if (mysqldetails.containsKey("5264"))
/*      */                                       {
/*      */ 
/* 3855 */                                         out.write(32);
/* 3856 */                                         out.print(formatNumber(mysqldetails.getProperty("5264")));
/* 3857 */                                         out.write("\n\t\t\t\t\t\t");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 3863 */                                         out.write(32);
/* 3864 */                                         out.write(45);
/* 3865 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 3869 */                                       out.write(" <br>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\">-</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       
/* 3871 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 3874 */                                         out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\" align=\"right\"><img\n\t\t\t\t\t\t\tsrc=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3875 */                                         out.print(resourceid);
/* 3876 */                                         out.write("&attributeIDs=108,109&attributeToSelect=108&redirectto=");
/* 3877 */                                         out.print(encodeurl);
/* 3878 */                                         out.write("\"\n\t\t\t\t\t\t\tclass=\"links\">");
/* 3879 */                                         out.print(ALERTCONFIG_TEXT);
/* 3880 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       }
/* 3882 */                                       out.write("\n\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\t<br>\n\t");
/*      */                                       
/* 3884 */                                       Properties replicationProperties = (Properties)request.getAttribute("SLAVE_STATUS");
/* 3885 */                                       String slaveid = replicationProperties.getProperty("RESOURCEID");
/* 3886 */                                       String masterHost = replicationProperties.getProperty("MASTER_HOST");
/* 3887 */                                       String masterUser = replicationProperties.getProperty("MASTER_USER");
/* 3888 */                                       String masterPort = replicationProperties.getProperty("MASTER_PORT");
/* 3889 */                                       String IORunning = replicationProperties.getProperty("SLAVE_IO_RUNNING");
/* 3890 */                                       String SQLRunning = replicationProperties.getProperty("SLAVE_SQL_RUNNING");
/* 3891 */                                       String lastError = replicationProperties.getProperty("LAST_ERROR");
/* 3892 */                                       String timeBehindMaster = replicationProperties.getProperty("SECONDS_BEHIND_MASTER");
/* 3893 */                                       if ((resourceid.equals(slaveid)) && (masterPort != null)) {
/* 3894 */                                         StringBuffer errorMessage = new StringBuffer();
/* 3895 */                                         if ((IORunning.equalsIgnoreCase("No")) || (SQLRunning.equalsIgnoreCase("No")) || (!lastError.equalsIgnoreCase("No Errors"))) {
/* 3896 */                                           if (IORunning.equalsIgnoreCase("No")) {
/* 3897 */                                             errorMessage.append(FormatUtil.getString("Slave IO is not Running."));
/*      */                                           }
/* 3899 */                                           if (SQLRunning.equalsIgnoreCase("No")) {
/* 3900 */                                             errorMessage.append(FormatUtil.getString("Slave SQL is not Running."));
/*      */                                           }
/* 3902 */                                           if (!lastError.equalsIgnoreCase("No Errors")) {
/* 3903 */                                             errorMessage.append(lastError);
/*      */                                           }
/*      */                                         } else {
/* 3906 */                                           errorMessage.append(FormatUtil.getString("Up"));
/*      */                                         }
/* 3908 */                                         error = errorMessage.toString();
/*      */                                         
/* 3910 */                                         out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"100%\" height=\"26\" class=\"tableheadingtrans\">");
/* 3911 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.tableheading"));
/* 3912 */                                         out.write("</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr>\n\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"26\" class=\"class=\"rborder\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\t\tclass=\"rborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"columnheadingnotop\" width=\"34%\" height=\"26\"\n\t\t\t\t\t\talign=\"middle\"><span class=\"bodytextbold\">");
/* 3913 */                                         if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 3915 */                                         out.write("</span></td> ");
/* 3916 */                                         out.write("\n\t\t\t\t\t<td width=\"34%\" class=\"columnheadingnotop\" height=\"26\"\n\t\t\t\t\t\talign=\"middle\"><span class=\"bodytextbold\">");
/* 3917 */                                         if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 3919 */                                         out.write("</span></td> ");
/* 3920 */                                         out.write("\n\t\t\t\t\t<td width=\"33%\" class=\"columnheadingnotop\" height=\"26\"\n\t\t\t\t\t\talign=\"middle\"><span class=\"bodytextbold\">");
/* 3921 */                                         if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 3923 */                                         out.write("</span></td> ");
/* 3924 */                                         out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3925 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.status"));
/* 3926 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">");
/*      */                                         
/* 3928 */                                         Truncate _jspx_th_am_005fTruncate_005f0 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 3929 */                                         _jspx_th_am_005fTruncate_005f0.setPageContext(_jspx_page_context);
/* 3930 */                                         _jspx_th_am_005fTruncate_005f0.setParent(_jspx_th_c_005fif_005f2);
/*      */                                         
/* 3932 */                                         _jspx_th_am_005fTruncate_005f0.setLength(25);
/* 3933 */                                         int _jspx_eval_am_005fTruncate_005f0 = _jspx_th_am_005fTruncate_005f0.doStartTag();
/* 3934 */                                         if (_jspx_eval_am_005fTruncate_005f0 != 0) {
/* 3935 */                                           if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3936 */                                             out = _jspx_page_context.pushBody();
/* 3937 */                                             _jspx_th_am_005fTruncate_005f0.setBodyContent((BodyContent)out);
/* 3938 */                                             _jspx_th_am_005fTruncate_005f0.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 3941 */                                             out.write("&nbsp;");
/* 3942 */                                             out.print(error);
/* 3943 */                                             int evalDoAfterBody = _jspx_th_am_005fTruncate_005f0.doAfterBody();
/* 3944 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 3947 */                                           if (_jspx_eval_am_005fTruncate_005f0 != 1) {
/* 3948 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 3951 */                                         if (_jspx_th_am_005fTruncate_005f0.doEndTag() == 5) {
/* 3952 */                                           this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0); return;
/*      */                                         }
/*      */                                         
/* 3955 */                                         this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f0);
/* 3956 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\"><a\n\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3957 */                                         out.print(resourceid);
/* 3958 */                                         out.write("&attributeid=3156')\">");
/* 3959 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3156")));
/* 3960 */                                         out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3961 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.slave.io.running"));
/* 3962 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 3963 */                                         out.print(IORunning);
/* 3964 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3965 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.slave.sql.running"));
/* 3966 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 3967 */                                         out.print(SQLRunning);
/* 3968 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3969 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.last.error"));
/* 3970 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 3971 */                                         out.print(lastError);
/* 3972 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" class=\"yellowgrayborder\" width=\"100%\" height=\"26\"\n\t\t\t\t\t\talign=\"right\">&nbsp;&nbsp; <img\n\t\t\t\t\t\tsrc=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\thref='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3973 */                                         out.print(resourceid);
/* 3974 */                                         out.write("&attributeIDs=3156&attributeToSelect=3156&redirectto=");
/* 3975 */                                         out.print(encodeurl);
/* 3976 */                                         out.write("'\n\t\t\t\t\t\tclass=\"staticlinks\">");
/* 3977 */                                         out.print(ALERTCONFIG_TEXT);
/* 3978 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td width=\"50%\" height=\"26\" align=\"top\" class=\"class=\"rborder\">\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\t\tclass=\"rborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"columnheadingnotop\" width=\"34%\" height=\"26\"\n\t\t\t\t\t\talign=\"middle\"><span class=\"bodytextbold\">");
/* 3979 */                                         if (_jspx_meth_fmt_005fmessage_005f18(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 3981 */                                         out.write("</span></td> ");
/* 3982 */                                         out.write("\n\t\t\t\t\t<td width=\"34%\" class=\"columnheadingnotop\" height=\"26\"\n\t\t\t\t\t\talign=\"middle\"><span class=\"bodytextbold\">");
/* 3983 */                                         if (_jspx_meth_fmt_005fmessage_005f19(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 3985 */                                         out.write("</span></td> ");
/* 3986 */                                         out.write("\n\t\t\t\t\t<td width=\"33%\" class=\"columnheadingnotop\" height=\"26\"\n\t\t\t\t\t\talign=\"middle\"><span class=\"bodytextbold\">");
/* 3987 */                                         if (_jspx_meth_fmt_005fmessage_005f20(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 3989 */                                         out.write("</span></td> ");
/* 3990 */                                         out.write("\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3991 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.master.host"));
/* 3992 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 3993 */                                         out.print(masterHost);
/* 3994 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3995 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.master.user"));
/* 3996 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 3997 */                                         out.print(masterUser);
/* 3998 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 3999 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.master.port"));
/* 4000 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 4001 */                                         out.print(masterPort);
/* 4002 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\">-</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"45%\" height=\"26\" align=\"left\">&nbsp;");
/* 4003 */                                         out.print(FormatUtil.getString("am.webclient.mysql.replication.time.behind.master"));
/* 4004 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"55%\" height=\"26\" align=\"middle\">&nbsp;");
/* 4005 */                                         out.print(timeBehindMaster);
/* 4006 */                                         out.write("</td>\n\t\t\t\t\t<td class=\"yellowgrayborder\" width=\"5%\" height=\"26\" align=\"middle\"><a\n\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4007 */                                         out.print(resourceid);
/* 4008 */                                         out.write("&attributeid=3157')\">");
/* 4009 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3157")));
/* 4010 */                                         out.write("</a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"3\" class=\"yellowgrayborder\" width=\"100%\" height=\"26\"\n\t\t\t\t\t\talign=\"right\">&nbsp;&nbsp; <img\n\t\t\t\t\t\tsrc=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\thref='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4011 */                                         out.print(resourceid);
/* 4012 */                                         out.write("&attributeIDs=3157&attributeToSelect=3157&redirectto=");
/* 4013 */                                         out.print(encodeurl);
/* 4014 */                                         out.write("'\n\t\t\t\t\t\tclass=\"staticlinks\">");
/* 4015 */                                         out.print(ALERTCONFIG_TEXT);
/* 4016 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\t");
/*      */                                       }
/*      */                                       
/* 4019 */                                       p = new Properties();
/* 4020 */                                       if (mysqlgraph.formatnumber(totalsize / 1048576.0D) != 0.0D)
/*      */                                       {
/* 4022 */                                         mysqlgraph.setresid(Integer.parseInt(resourceid));
/* 4023 */                                         mysqlgraph.settype("DBSIZE");
/* 4024 */                                         mysqlgraph.setresourcename(name);
/* 4025 */                                         mysqlgraph.setcollectiontime(maxcollectiontime);
/*      */                                       }
/*      */                                       
/* 4028 */                                       out.write(10);
/* 4029 */                                       out.write(9);
/*      */                                       
/* 4031 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f3 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4032 */                                       _jspx_th_logic_005fnotEmpty_005f3.setPageContext(_jspx_page_context);
/* 4033 */                                       _jspx_th_logic_005fnotEmpty_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 4035 */                                       _jspx_th_logic_005fnotEmpty_005f3.setName("dbdetails");
/* 4036 */                                       int _jspx_eval_logic_005fnotEmpty_005f3 = _jspx_th_logic_005fnotEmpty_005f3.doStartTag();
/* 4037 */                                       if (_jspx_eval_logic_005fnotEmpty_005f3 != 0) {
/*      */                                         for (;;) {
/* 4039 */                                           out.write("\n\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\tclass=\"lrtbdarkborder\">\n\t\t\t<tr>\n\t\t\t\t<td width=\"50%\" height=\"26\" class=\"tableheadingtrans\">");
/* 4040 */                                           out.print(FormatUtil.getString("am.webclient.mysql.databasedetails"));
/* 4041 */                                           out.write("</td>\n\t\t\t</tr>\n\t\t</table>\n\n\t\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\tclass=\"lrbborder\">\n\t\t\t");
/*      */                                           
/* 4043 */                                           if (mysqlgraph.formatnumber(totalsize / 1048576.0D) != 0.0D)
/*      */                                           {
/* 4045 */                                             out.write("\n\t\t\t<tr>\n\t\t\t\t<td align=\"center\"\n\t\t\t\t\ttitle=\"");
/* 4046 */                                             out.print(FormatUtil.getString("am.webclient.mysql.dbgrah.tooltip"));
/* 4047 */                                             out.write("\">\n\t\t\t\t");
/*      */                                             
/* 4049 */                                             AMWolf _jspx_th_awolf_005fpiechart_005f1 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.get(AMWolf.class);
/* 4050 */                                             _jspx_th_awolf_005fpiechart_005f1.setPageContext(_jspx_page_context);
/* 4051 */                                             _jspx_th_awolf_005fpiechart_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                                             
/* 4053 */                                             _jspx_th_awolf_005fpiechart_005f1.setDataSetProducer("mysqlgraph");
/*      */                                             
/* 4055 */                                             _jspx_th_awolf_005fpiechart_005f1.setWidth("575");
/*      */                                             
/* 4057 */                                             _jspx_th_awolf_005fpiechart_005f1.setHeight("275");
/*      */                                             
/* 4059 */                                             _jspx_th_awolf_005fpiechart_005f1.setLegend("true");
/*      */                                             
/* 4061 */                                             _jspx_th_awolf_005fpiechart_005f1.setLegendanchor("SOUTH");
/*      */                                             
/* 4063 */                                             _jspx_th_awolf_005fpiechart_005f1.setUnits(" MB");
/*      */                                             
/* 4065 */                                             _jspx_th_awolf_005fpiechart_005f1.setCircular(true);
/*      */                                             
/* 4067 */                                             _jspx_th_awolf_005fpiechart_005f1.setDecimal(true);
/* 4068 */                                             int _jspx_eval_awolf_005fpiechart_005f1 = _jspx_th_awolf_005fpiechart_005f1.doStartTag();
/* 4069 */                                             if (_jspx_eval_awolf_005fpiechart_005f1 != 0) {
/* 4070 */                                               if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4071 */                                                 out = _jspx_page_context.pushBody();
/* 4072 */                                                 _jspx_th_awolf_005fpiechart_005f1.setBodyContent((BodyContent)out);
/* 4073 */                                                 _jspx_th_awolf_005fpiechart_005f1.doInitBody();
/*      */                                               }
/*      */                                               for (;;) {
/* 4076 */                                                 out.write("\n\n\t\t\t\t\t");
/*      */                                                 
/* 4078 */                                                 Property _jspx_th_awolf_005fmap_005f1 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 4079 */                                                 _jspx_th_awolf_005fmap_005f1.setPageContext(_jspx_page_context);
/* 4080 */                                                 _jspx_th_awolf_005fmap_005f1.setParent(_jspx_th_awolf_005fpiechart_005f1);
/*      */                                                 
/* 4082 */                                                 _jspx_th_awolf_005fmap_005f1.setId("color");
/* 4083 */                                                 int _jspx_eval_awolf_005fmap_005f1 = _jspx_th_awolf_005fmap_005f1.doStartTag();
/* 4084 */                                                 if (_jspx_eval_awolf_005fmap_005f1 != 0) {
/* 4085 */                                                   if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4086 */                                                     out = _jspx_page_context.pushBody();
/* 4087 */                                                     _jspx_th_awolf_005fmap_005f1.setBodyContent((BodyContent)out);
/* 4088 */                                                     _jspx_th_awolf_005fmap_005f1.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 4091 */                                                     out.write("\n\t\t\t\t\t\t");
/*      */                                                     
/* 4093 */                                                     AMParam _jspx_th_awolf_005fparam_005f2 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4094 */                                                     _jspx_th_awolf_005fparam_005f2.setPageContext(_jspx_page_context);
/* 4095 */                                                     _jspx_th_awolf_005fparam_005f2.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                     
/* 4097 */                                                     _jspx_th_awolf_005fparam_005f2.setName("0");
/*      */                                                     
/* 4099 */                                                     _jspx_th_awolf_005fparam_005f2.setValue("#FFF8C6");
/* 4100 */                                                     int _jspx_eval_awolf_005fparam_005f2 = _jspx_th_awolf_005fparam_005f2.doStartTag();
/* 4101 */                                                     if (_jspx_th_awolf_005fparam_005f2.doEndTag() == 5) {
/* 4102 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2); return;
/*      */                                                     }
/*      */                                                     
/* 4105 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f2);
/* 4106 */                                                     out.write("\n\t\t\t\t\t\t");
/*      */                                                     
/* 4108 */                                                     AMParam _jspx_th_awolf_005fparam_005f3 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4109 */                                                     _jspx_th_awolf_005fparam_005f3.setPageContext(_jspx_page_context);
/* 4110 */                                                     _jspx_th_awolf_005fparam_005f3.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                     
/* 4112 */                                                     _jspx_th_awolf_005fparam_005f3.setName("0");
/*      */                                                     
/* 4114 */                                                     _jspx_th_awolf_005fparam_005f3.setValue("#F660AB");
/* 4115 */                                                     int _jspx_eval_awolf_005fparam_005f3 = _jspx_th_awolf_005fparam_005f3.doStartTag();
/* 4116 */                                                     if (_jspx_th_awolf_005fparam_005f3.doEndTag() == 5) {
/* 4117 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3); return;
/*      */                                                     }
/*      */                                                     
/* 4120 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f3);
/* 4121 */                                                     out.write("\n\t\t\t\t\t\t");
/*      */                                                     
/* 4123 */                                                     AMParam _jspx_th_awolf_005fparam_005f4 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4124 */                                                     _jspx_th_awolf_005fparam_005f4.setPageContext(_jspx_page_context);
/* 4125 */                                                     _jspx_th_awolf_005fparam_005f4.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                     
/* 4127 */                                                     _jspx_th_awolf_005fparam_005f4.setName("0");
/*      */                                                     
/* 4129 */                                                     _jspx_th_awolf_005fparam_005f4.setValue("#C9BE62");
/* 4130 */                                                     int _jspx_eval_awolf_005fparam_005f4 = _jspx_th_awolf_005fparam_005f4.doStartTag();
/* 4131 */                                                     if (_jspx_th_awolf_005fparam_005f4.doEndTag() == 5) {
/* 4132 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4); return;
/*      */                                                     }
/*      */                                                     
/* 4135 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f4);
/* 4136 */                                                     out.write("\n\t\t\t\t\t\t");
/*      */                                                     
/* 4138 */                                                     AMParam _jspx_th_awolf_005fparam_005f5 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4139 */                                                     _jspx_th_awolf_005fparam_005f5.setPageContext(_jspx_page_context);
/* 4140 */                                                     _jspx_th_awolf_005fparam_005f5.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                     
/* 4142 */                                                     _jspx_th_awolf_005fparam_005f5.setName("1");
/*      */                                                     
/* 4144 */                                                     _jspx_th_awolf_005fparam_005f5.setValue("#FDD017");
/* 4145 */                                                     int _jspx_eval_awolf_005fparam_005f5 = _jspx_th_awolf_005fparam_005f5.doStartTag();
/* 4146 */                                                     if (_jspx_th_awolf_005fparam_005f5.doEndTag() == 5) {
/* 4147 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5); return;
/*      */                                                     }
/*      */                                                     
/* 4150 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f5);
/* 4151 */                                                     out.write("\n\t\t\t\t\t\t");
/*      */                                                     
/* 4153 */                                                     AMParam _jspx_th_awolf_005fparam_005f6 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 4154 */                                                     _jspx_th_awolf_005fparam_005f6.setPageContext(_jspx_page_context);
/* 4155 */                                                     _jspx_th_awolf_005fparam_005f6.setParent(_jspx_th_awolf_005fmap_005f1);
/*      */                                                     
/* 4157 */                                                     _jspx_th_awolf_005fparam_005f6.setName("0");
/*      */                                                     
/* 4159 */                                                     _jspx_th_awolf_005fparam_005f6.setValue("#00CCFF");
/* 4160 */                                                     int _jspx_eval_awolf_005fparam_005f6 = _jspx_th_awolf_005fparam_005f6.doStartTag();
/* 4161 */                                                     if (_jspx_th_awolf_005fparam_005f6.doEndTag() == 5) {
/* 4162 */                                                       this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f6); return;
/*      */                                                     }
/*      */                                                     
/* 4165 */                                                     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f6);
/* 4166 */                                                     out.write("\n\t\t\t\t\t");
/* 4167 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fmap_005f1.doAfterBody();
/* 4168 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 4171 */                                                   if (_jspx_eval_awolf_005fmap_005f1 != 1) {
/* 4172 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 4175 */                                                 if (_jspx_th_awolf_005fmap_005f1.doEndTag() == 5) {
/* 4176 */                                                   this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1); return;
/*      */                                                 }
/*      */                                                 
/* 4179 */                                                 this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f1);
/* 4180 */                                                 out.write(32);
/* 4181 */                                                 out.write("\n\n\t\t\t\t");
/* 4182 */                                                 int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f1.doAfterBody();
/* 4183 */                                                 if (evalDoAfterBody != 2)
/*      */                                                   break;
/*      */                                               }
/* 4186 */                                               if (_jspx_eval_awolf_005fpiechart_005f1 != 1) {
/* 4187 */                                                 out = _jspx_page_context.popBody();
/*      */                                               }
/*      */                                             }
/* 4190 */                                             if (_jspx_th_awolf_005fpiechart_005f1.doEndTag() == 5) {
/* 4191 */                                               this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.reuse(_jspx_th_awolf_005fpiechart_005f1); return;
/*      */                                             }
/*      */                                             
/* 4194 */                                             this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005funits_005flegendanchor_005flegend_005fheight_005fdecimal_005fdataSetProducer_005fcircular.reuse(_jspx_th_awolf_005fpiechart_005f1);
/* 4195 */                                             out.write("</td> ");
/* 4196 */                                             out.write("\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t\t<td align=\"center\" class=\"bottomborder\" height=\"35\"><span\n\t\t\t\t\tclass=\"bodytextbold\">");
/* 4197 */                                             out.print(FormatUtil.getString("am.webclient.mysql.totalsizeofdatabases"));
/* 4198 */                                             out.write("\n\t\t\t\t: ");
/* 4199 */                                             out.print(mysqlgraph.formatnumber(totalsize / 1048576.0D));
/* 4200 */                                             out.write(32);
/* 4201 */                                             out.print(FormatUtil.getString("MB"));
/* 4202 */                                             out.write("</span></td>\n\t\t\t</tr>\n\t\t\t");
/*      */                                           }
/* 4204 */                                           out.write("\n\t\t\t<tr>\n\t\t\t\t<td>\n\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td width=\"16%\" height=\"35\" class=\"columnheadingnotop\"\n\t\t\t\t\t\t\ttitle=\"");
/* 4205 */                                           out.print(FormatUtil.getString("am.webclient.mysql.tooltip"));
/* 4206 */                                           out.write("\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4207 */                                           out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 4208 */                                           out.write("</span></td>\n\t\t\t\t\t\t<td width=\"22%\" height=\"35\" class=\"columnheadingnotop\"\n\t\t\t\t\t\t\ttitle=\"");
/* 4209 */                                           out.print(FormatUtil.getString("am.webclient.mysql.dbsize.tooltip"));
/* 4210 */                                           out.write("\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4211 */                                           out.print(FormatUtil.getString("am.webclient.mysql.databasesize"));
/* 4212 */                                           out.write("\n\t\t\t\t\t\t");
/* 4213 */                                           out.print(FormatUtil.getString("MB"));
/* 4214 */                                           out.write("</span></td>\n\t\t\t\t\t\t<td width=\"22%\" height=\"35\" class=\"columnheadingnotop\"\n\t\t\t\t\t\t\ttitle=\"");
/* 4215 */                                           out.print(FormatUtil.getString("am.webclient.mysql.dbsize.tooltip"));
/* 4216 */                                           out.write("\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4217 */                                           out.print(FormatUtil.getString("am.webclient.mysql.databasetables"));
/* 4218 */                                           out.write("</span></td>\n\t\t\t\t\t\t<td width=\"18%\" height=\"35\" class=\"columnheadingnotop\"\n\t\t\t\t\t\t\ttitle=\"");
/* 4219 */                                           out.print(FormatUtil.getString("am.webclient.mysql.dbhealth.tooltip"));
/* 4220 */                                           out.write("\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4221 */                                           out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 4222 */                                           out.write("</span></td>\n\t\t\t\t\t\t");
/*      */                                           
/* 4224 */                                           if (!EnterpriseUtil.isAdminServer())
/*      */                                           {
/*      */ 
/* 4227 */                                             out.write("\n\t\t\t\t\t\t<td width=\"22%\" height=\"35\" class=\"columnheadingnotop\"\n\t\t\t\t\t\t\ttitle=\"");
/* 4228 */                                             out.print(FormatUtil.getString("am.webclient.mysql.dbconfig.tooltip"));
/* 4229 */                                             out.write("\"><span\n\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4230 */                                             out.print(ALERTCONFIG_TEXT);
/* 4231 */                                             out.write("</span></td>\n\t\t\t\t\t\t");
/*      */ 
/*      */                                           }
/*      */                                           else
/*      */                                           {
/* 4236 */                                             out.write("\n\t\t\t\t\t\t<td width=\"22%\" height=\"35\" class=\"columnheadingnotop\"></td>\n\t\t\t\t\t\t");
/*      */                                           }
/* 4238 */                                           out.write("\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                           
/* 4240 */                                           NotEmptyTag _jspx_th_logic_005fnotEmpty_005f4 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4241 */                                           _jspx_th_logic_005fnotEmpty_005f4.setPageContext(_jspx_page_context);
/* 4242 */                                           _jspx_th_logic_005fnotEmpty_005f4.setParent(_jspx_th_logic_005fnotEmpty_005f3);
/*      */                                           
/* 4244 */                                           _jspx_th_logic_005fnotEmpty_005f4.setName("dbdetails");
/* 4245 */                                           int _jspx_eval_logic_005fnotEmpty_005f4 = _jspx_th_logic_005fnotEmpty_005f4.doStartTag();
/* 4246 */                                           if (_jspx_eval_logic_005fnotEmpty_005f4 != 0) {
/*      */                                             for (;;) {
/* 4248 */                                               out.write("\n\t\t\t\t\t\t");
/*      */                                               
/* 4250 */                                               IterateTag _jspx_th_logic_005fiterate_005f1 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4251 */                                               _jspx_th_logic_005fiterate_005f1.setPageContext(_jspx_page_context);
/* 4252 */                                               _jspx_th_logic_005fiterate_005f1.setParent(_jspx_th_logic_005fnotEmpty_005f4);
/*      */                                               
/* 4254 */                                               _jspx_th_logic_005fiterate_005f1.setName("dbdetails");
/*      */                                               
/* 4256 */                                               _jspx_th_logic_005fiterate_005f1.setId("row");
/*      */                                               
/* 4258 */                                               _jspx_th_logic_005fiterate_005f1.setIndexId("j");
/*      */                                               
/* 4260 */                                               _jspx_th_logic_005fiterate_005f1.setType("java.util.ArrayList");
/* 4261 */                                               int _jspx_eval_logic_005fiterate_005f1 = _jspx_th_logic_005fiterate_005f1.doStartTag();
/* 4262 */                                               if (_jspx_eval_logic_005fiterate_005f1 != 0) {
/* 4263 */                                                 ArrayList row = null;
/* 4264 */                                                 Integer j = null;
/* 4265 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4266 */                                                   out = _jspx_page_context.pushBody();
/* 4267 */                                                   _jspx_th_logic_005fiterate_005f1.setBodyContent((BodyContent)out);
/* 4268 */                                                   _jspx_th_logic_005fiterate_005f1.doInitBody();
/*      */                                                 }
/* 4270 */                                                 row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4271 */                                                 j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                                 for (;;) {
/* 4273 */                                                   out.write("\n\t\t\t\t\t\t\t");
/*      */                                                   
/* 4275 */                                                   String resid = (String)row.get(2);
/* 4276 */                                                   if (j.intValue() % 2 == 0)
/*      */                                                   {
/* 4278 */                                                     bgcolour = "whitegrayborder";
/*      */                                                   }
/*      */                                                   else
/*      */                                                   {
/* 4282 */                                                     bgcolour = "yellowgrayborder";
/*      */                                                   }
/*      */                                                   
/* 4285 */                                                   out.write("\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td height=\"35\" class=\"");
/* 4286 */                                                   out.print(bgcolour);
/* 4287 */                                                   out.write(34);
/* 4288 */                                                   out.write(62);
/*      */                                                   
/* 4290 */                                                   Truncate _jspx_th_am_005fTruncate_005f1 = (Truncate)this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.get(Truncate.class);
/* 4291 */                                                   _jspx_th_am_005fTruncate_005f1.setPageContext(_jspx_page_context);
/* 4292 */                                                   _jspx_th_am_005fTruncate_005f1.setParent(_jspx_th_logic_005fiterate_005f1);
/*      */                                                   
/* 4294 */                                                   _jspx_th_am_005fTruncate_005f1.setLength(25);
/* 4295 */                                                   int _jspx_eval_am_005fTruncate_005f1 = _jspx_th_am_005fTruncate_005f1.doStartTag();
/* 4296 */                                                   if (_jspx_eval_am_005fTruncate_005f1 != 0) {
/* 4297 */                                                     if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 4298 */                                                       out = _jspx_page_context.pushBody();
/* 4299 */                                                       _jspx_th_am_005fTruncate_005f1.setBodyContent((BodyContent)out);
/* 4300 */                                                       _jspx_th_am_005fTruncate_005f1.doInitBody();
/*      */                                                     }
/*      */                                                     for (;;) {
/* 4303 */                                                       out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4304 */                                                       out.print(formatNumber((String)row.get(0)));
/* 4305 */                                                       out.write("\n\t\t\t\t\t\t\t\t");
/* 4306 */                                                       int evalDoAfterBody = _jspx_th_am_005fTruncate_005f1.doAfterBody();
/* 4307 */                                                       if (evalDoAfterBody != 2)
/*      */                                                         break;
/*      */                                                     }
/* 4310 */                                                     if (_jspx_eval_am_005fTruncate_005f1 != 1) {
/* 4311 */                                                       out = _jspx_page_context.popBody();
/*      */                                                     }
/*      */                                                   }
/* 4314 */                                                   if (_jspx_th_am_005fTruncate_005f1.doEndTag() == 5) {
/* 4315 */                                                     this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1); return;
/*      */                                                   }
/*      */                                                   
/* 4318 */                                                   this._005fjspx_005ftagPool_005fam_005fTruncate_0026_005flength.reuse(_jspx_th_am_005fTruncate_005f1);
/* 4319 */                                                   out.write("</td> ");
/* 4320 */                                                   out.write("\n\t\t\t\t\t\t\t\t<td height=\"35\" class=\"");
/* 4321 */                                                   out.print(bgcolour);
/* 4322 */                                                   out.write("\"\n\t\t\t\t\t\t\t\t\ttitle=\"");
/* 4323 */                                                   out.print(formatNumber((String)row.get(1)));
/* 4324 */                                                   out.write(" Bytes\">");
/* 4325 */                                                   out.print(FormatUtil.formatBytesToMB((String)row.get(1)));
/* 4326 */                                                   out.write("</td>\n\t\t\t\t\t\t\t\t<td height=\"35\" class=\"");
/* 4327 */                                                   out.print(bgcolour);
/* 4328 */                                                   out.write(34);
/* 4329 */                                                   out.write(62);
/* 4330 */                                                   out.print((String)row.get(3));
/* 4331 */                                                   out.write("</td>\n\t\t\t\t\t\t\t\t<td height=\"35\" class=\"");
/* 4332 */                                                   out.print(bgcolour);
/* 4333 */                                                   out.write("\"><a\n\t\t\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4334 */                                                   out.print(resid);
/* 4335 */                                                   out.write("&attributeid=117')\">");
/* 4336 */                                                   out.print(getSeverityImageForHealth(alert.getProperty(resid + "#" + "117")));
/* 4337 */                                                   out.write("</a></td>\n\t\t\t\t\t\t\t\t");
/*      */                                                   
/* 4339 */                                                   if (!EnterpriseUtil.isAdminServer())
/*      */                                                   {
/*      */ 
/* 4342 */                                                     out.write("\n\t\t\t\t\t\t\t\t<td height=\"35\" align=\"center\" class=\"");
/* 4343 */                                                     out.print(bgcolour);
/* 4344 */                                                     out.write("\"><a\n\t\t\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4345 */                                                     out.print(resid);
/* 4346 */                                                     out.write("&attributeIDs=111,112,117&attributeToSelect=111&redirectto=");
/* 4347 */                                                     out.print(encodeurl);
/* 4348 */                                                     out.write("\"\n\t\t\t\t\t\t\t\t\tclass=\"staticlinks\"><img\n\t\t\t\t\t\t\t\t\tsrc=\"/images/icon_associateaction.gif\" align=\"absmiddle\"\n\t\t\t\t\t\t\t\t\tborder=\"0\"></a></td>\n\t\t\t\t\t\t\t\t");
/*      */                                                   }
/*      */                                                   else
/*      */                                                   {
/* 4352 */                                                     out.write("\n\t\t\t\t\t\t\t\t<td height=\"35\" align=\"center\" class=\"");
/* 4353 */                                                     out.print(bgcolour);
/* 4354 */                                                     out.write("\"></td>\n\t\t\t\t\t\t\t\t");
/*      */                                                   }
/* 4356 */                                                   out.write("\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/* 4357 */                                                   int evalDoAfterBody = _jspx_th_logic_005fiterate_005f1.doAfterBody();
/* 4358 */                                                   row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4359 */                                                   j = (Integer)_jspx_page_context.findAttribute("j");
/* 4360 */                                                   if (evalDoAfterBody != 2)
/*      */                                                     break;
/*      */                                                 }
/* 4363 */                                                 if (_jspx_eval_logic_005fiterate_005f1 != 1) {
/* 4364 */                                                   out = _jspx_page_context.popBody();
/*      */                                                 }
/*      */                                               }
/* 4367 */                                               if (_jspx_th_logic_005fiterate_005f1.doEndTag() == 5) {
/* 4368 */                                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1); return;
/*      */                                               }
/*      */                                               
/* 4371 */                                               this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f1);
/* 4372 */                                               out.write("\n\t\t\t\t\t");
/* 4373 */                                               int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f4.doAfterBody();
/* 4374 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 4378 */                                           if (_jspx_th_logic_005fnotEmpty_005f4.doEndTag() == 5) {
/* 4379 */                                             this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4); return;
/*      */                                           }
/*      */                                           
/* 4382 */                                           this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f4);
/* 4383 */                                           out.write("\n\t\t\t\t</table>\n\t\t\t\t</td>\n\t\t\t</tr>\n\t\t</table>\n\t");
/* 4384 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f3.doAfterBody();
/* 4385 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4389 */                                       if (_jspx_th_logic_005fnotEmpty_005f3.doEndTag() == 5) {
/* 4390 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3); return;
/*      */                                       }
/*      */                                       
/* 4393 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f3);
/* 4394 */                                       out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t\t<td width=\"72%\">&nbsp;</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"26\" class=\"tableheading\">");
/* 4395 */                                       out.print(FormatUtil.getString("am.webclient.mysql.tablelocksstatistics"));
/* 4396 */                                       out.write("\n\t\t\t- ");
/* 4397 */                                       out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4398 */                                       out.write("</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr class=\"lrborder\">\n\t\t\t<td width=\"36%\" height=\"121\" valign=\"top\">\n\t\t\t");
/*      */                                       
/* 4400 */                                       mysqlgraph.settype("LOCKS");
/*      */                                       
/* 4402 */                                       out.write("\n\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4403 */                                       if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4405 */                                       out.write("&attributeid=113&period=-7',740,550)\">\n\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 4406 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4407 */                                       out.write("'></a></td>\n\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4408 */                                       if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4410 */                                       out.write("&attributeid=113&period=-30',740,550)\"><img\n\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\ttitle='");
/* 4411 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4412 */                                       out.write("'></a></td>\n\t\t\t\t</tr>\n\t\t\t\t<tr>\n\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\ttitle=\"");
/* 4413 */                                       out.print(FormatUtil.getString("am.webclient.mysql.lockgraph.tooltip"));
/* 4414 */                                       out.write("\">\n\t\t\t\t\t");
/*      */                                       
/* 4416 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f5 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4417 */                                       _jspx_th_awolf_005ftimechart_005f5.setPageContext(_jspx_page_context);
/* 4418 */                                       _jspx_th_awolf_005ftimechart_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 4420 */                                       _jspx_th_awolf_005ftimechart_005f5.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 4422 */                                       _jspx_th_awolf_005ftimechart_005f5.setWidth("300");
/*      */                                       
/* 4424 */                                       _jspx_th_awolf_005ftimechart_005f5.setHeight("170");
/*      */                                       
/* 4426 */                                       _jspx_th_awolf_005ftimechart_005f5.setLegend("true");
/*      */                                       
/* 4428 */                                       _jspx_th_awolf_005ftimechart_005f5.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 4430 */                                       _jspx_th_awolf_005ftimechart_005f5.setYaxisLabel(FormatUtil.getString("am.webclient.mysql.graph.count"));
/* 4431 */                                       int _jspx_eval_awolf_005ftimechart_005f5 = _jspx_th_awolf_005ftimechart_005f5.doStartTag();
/* 4432 */                                       if (_jspx_eval_awolf_005ftimechart_005f5 != 0) {
/* 4433 */                                         if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 4434 */                                           out = _jspx_page_context.pushBody();
/* 4435 */                                           _jspx_th_awolf_005ftimechart_005f5.setBodyContent((BodyContent)out);
/* 4436 */                                           _jspx_th_awolf_005ftimechart_005f5.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4439 */                                           out.write("\n\t\t\t\t\t");
/* 4440 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f5.doAfterBody();
/* 4441 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4444 */                                         if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 4445 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4448 */                                       if (_jspx_th_awolf_005ftimechart_005f5.doEndTag() == 5) {
/* 4449 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5); return;
/*      */                                       }
/*      */                                       
/* 4452 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5);
/* 4453 */                                       out.write("</td> ");
/* 4454 */                                       out.write("\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t</td>\n\t\t\t<td width=\"36%\" align=\"center\" valign=\"top\"><br>\n\t\t\t<table width=\"95%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\t\tclass=\"grayfullborder\">\n\t\t\t\t<tbody>\n\t\t\t\t\t");
/*      */                                       
/* 4456 */                                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f5 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 4457 */                                       _jspx_th_logic_005fnotEmpty_005f5.setPageContext(_jspx_page_context);
/* 4458 */                                       _jspx_th_logic_005fnotEmpty_005f5.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 4460 */                                       _jspx_th_logic_005fnotEmpty_005f5.setName("tablelocks");
/* 4461 */                                       int _jspx_eval_logic_005fnotEmpty_005f5 = _jspx_th_logic_005fnotEmpty_005f5.doStartTag();
/* 4462 */                                       if (_jspx_eval_logic_005fnotEmpty_005f5 != 0) {
/*      */                                         for (;;) {
/* 4464 */                                           out.write("\n\t\t\t\t\t\t");
/*      */                                           
/* 4466 */                                           IterateTag _jspx_th_logic_005fiterate_005f2 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 4467 */                                           _jspx_th_logic_005fiterate_005f2.setPageContext(_jspx_page_context);
/* 4468 */                                           _jspx_th_logic_005fiterate_005f2.setParent(_jspx_th_logic_005fnotEmpty_005f5);
/*      */                                           
/* 4470 */                                           _jspx_th_logic_005fiterate_005f2.setName("tablelocks");
/*      */                                           
/* 4472 */                                           _jspx_th_logic_005fiterate_005f2.setId("row");
/*      */                                           
/* 4474 */                                           _jspx_th_logic_005fiterate_005f2.setIndexId("j");
/*      */                                           
/* 4476 */                                           _jspx_th_logic_005fiterate_005f2.setType("java.util.ArrayList");
/* 4477 */                                           int _jspx_eval_logic_005fiterate_005f2 = _jspx_th_logic_005fiterate_005f2.doStartTag();
/* 4478 */                                           if (_jspx_eval_logic_005fiterate_005f2 != 0) {
/* 4479 */                                             ArrayList row = null;
/* 4480 */                                             Integer j = null;
/* 4481 */                                             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 4482 */                                               out = _jspx_page_context.pushBody();
/* 4483 */                                               _jspx_th_logic_005fiterate_005f2.setBodyContent((BodyContent)out);
/* 4484 */                                               _jspx_th_logic_005fiterate_005f2.doInitBody();
/*      */                                             }
/* 4486 */                                             row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4487 */                                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                             for (;;) {
/* 4489 */                                               out.write("\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4490 */                                               if (_jspx_meth_fmt_005fmessage_005f21(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                                                 return;
/* 4492 */                                               out.write("</span></td>  ");
/* 4493 */                                               out.write("\n\t\t\t\t\t\t\t\t<td width=\"28%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4494 */                                               if (_jspx_meth_fmt_005fmessage_005f22(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                                                 return;
/* 4496 */                                               out.write("</span></td>  ");
/* 4497 */                                               out.write("\n\t\t\t\t\t\t\t\t<td width=\"29%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4498 */                                               if (_jspx_meth_fmt_005fmessage_005f23(_jspx_th_logic_005fiterate_005f2, _jspx_page_context))
/*      */                                                 return;
/* 4500 */                                               out.write("</span></td>  ");
/* 4501 */                                               out.write("\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td width=\"38%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\t\ttitle=\"");
/* 4502 */                                               out.print(FormatUtil.getString("am.webclient.mysql.imm.tooltip"));
/* 4503 */                                               out.write(34);
/* 4504 */                                               out.write(62);
/* 4505 */                                               out.print(FormatUtil.getString("am.webclient.mysql.immediatelocks"));
/* 4506 */                                               out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"10%\" height=\"35\" class=\"whitegrayborder\">");
/* 4507 */                                               out.print(formatNumber((String)row.get(0)));
/* 4508 */                                               out.write("&nbsp;</td>\n\t\t\t\t\t\t\t\t<td width=\"52%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4509 */                                               out.print(resourceid);
/* 4510 */                                               out.write("&attributeid=113')\">");
/* 4511 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "113")));
/* 4512 */                                               out.write("</a></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t<td height=\"35\" width=\"38%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\t\ttitle=\"");
/* 4513 */                                               out.print(FormatUtil.getString("am.webclient.mysql.wait.tooltip"));
/* 4514 */                                               out.write(34);
/* 4515 */                                               out.write(62);
/* 4516 */                                               out.print(FormatUtil.getString("am.webclient.mysql.lockswait"));
/* 4517 */                                               out.write("\n\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t<td height=\"35\" width=\"10%\" class=\"yellowgrayborder\">");
/* 4518 */                                               out.print(formatNumber((String)row.get(1)));
/* 4519 */                                               out.write("</td>\n\t\t\t\t\t\t\t\t<td width=\"52%\" height=\"35\" class=\"yellowgrayborder\"><a\n\t\t\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4520 */                                               out.print(resourceid);
/* 4521 */                                               out.write("&attributeid=114')\">");
/* 4522 */                                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "114")));
/* 4523 */                                               out.write("</a></td>\n\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/* 4524 */                                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f2.doAfterBody();
/* 4525 */                                               row = (ArrayList)_jspx_page_context.findAttribute("row");
/* 4526 */                                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 4527 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/* 4530 */                                             if (_jspx_eval_logic_005fiterate_005f2 != 1) {
/* 4531 */                                               out = _jspx_page_context.popBody();
/*      */                                             }
/*      */                                           }
/* 4534 */                                           if (_jspx_th_logic_005fiterate_005f2.doEndTag() == 5) {
/* 4535 */                                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2); return;
/*      */                                           }
/*      */                                           
/* 4538 */                                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f2);
/* 4539 */                                           out.write("\n\t\t\t\t\t");
/* 4540 */                                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f5.doAfterBody();
/* 4541 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 4545 */                                       if (_jspx_th_logic_005fnotEmpty_005f5.doEndTag() == 5) {
/* 4546 */                                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5); return;
/*      */                                       }
/*      */                                       
/* 4549 */                                       this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f5);
/* 4550 */                                       out.write("\n\t\t\t\t\t");
/*      */                                       
/* 4552 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 4555 */                                         out.write("\n\t\t\t\t\t<tr>\n\t\t\t\t\t\t<td colspan=\"3\" height=\"21\" class=\"bodytext\" align=\"right\"><img\n\t\t\t\t\t\t\tsrc=\"/images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4556 */                                         out.print(resourceid);
/* 4557 */                                         out.write("&attributeIDs=113,114&attributeToSelect=113&redirectto=");
/* 4558 */                                         out.print(encodeurl);
/* 4559 */                                         out.write("\"\n\t\t\t\t\t\t\tclass=\"links\">");
/* 4560 */                                         out.print(ALERTCONFIG_TEXT);
/* 4561 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t\t</tr>\n\t\t\t\t\t");
/*      */                                       }
/* 4563 */                                       out.write("\n\t\t\t\t</tbody>\n\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</table>\n\n\t<br>\n\t");
/*      */                                       
/* 4565 */                                       p = new Properties();
/* 4566 */                                       p = (Properties)request.getAttribute("memoryhealth");
/* 4567 */                                       if ((p.containsKey("QUERYCACHEHITRATE")) && (!p.getProperty("QUERYCACHEHITRATE").equals("-1")))
/*      */                                       {
/* 4569 */                                         out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"26\" class=\"tableheading\">");
/* 4570 */                                         out.print(FormatUtil.getString("am.webclient.mysql.keyefficiency"));
/* 4571 */                                         out.write("\n\t\t\t- ");
/* 4572 */                                         out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4573 */                                         out.write("</td>\n\t\t\t<td width=\"50%\" class=\"tableheading\">");
/* 4574 */                                         out.print(FormatUtil.getString("am.webclient.mysql.querycachehitrate"));
/* 4575 */                                         out.write("\n\t\t\t- ");
/* 4576 */                                         out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4577 */                                         out.write("</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td width=\"50%\" class=\"rborder\">\n\t\t\t");
/*      */                                       } else {
/* 4579 */                                         out.write("\n\t\t\t<table width=\"50%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\t\tclass=\"lrtbdarkborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td height=\"26\" class=\"tableheading\">");
/* 4580 */                                         out.print(FormatUtil.getString("am.webclient.mysql.keyefficiency"));
/* 4581 */                                         out.write("\n\t\t\t\t\t- ");
/* 4582 */                                         out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4583 */                                         out.write("</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t\t<table width=\"50%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\t\t\tclass=\"lrborder\">\n\t\t\t\t<tr>\n\t\t\t\t\t<td width=\"100%\">\n\t\t\t\t\t");
/*      */                                       }
/* 4585 */                                       out.write("\n\t\t\t\t\t<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t");
/*      */                                       
/* 4587 */                                       mysqlgraph.setresid(Integer.parseInt(resourceid));
/* 4588 */                                       mysqlgraph.settype("KEY");
/*      */                                       
/* 4590 */                                       out.write("\n\t\t\t\t\t\t\t<td height=\"21\" colspan=\"3\" class=\"bottomborder\">\n\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4591 */                                       if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4593 */                                       out.write("&attributeid=118&period=-7',740,550)\">\n\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\t\t\t\ttitle='");
/* 4594 */                                       out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4595 */                                       out.write("'></a></td>\n\t\t\t\t\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4596 */                                       if (_jspx_meth_c_005fout_005f20(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4598 */                                       out.write("&attributeid=118&period=-30',740,550)\"><img\n\t\t\t\t\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\t\t\t\ttitle='");
/* 4599 */                                       out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4600 */                                       out.write("'></a></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\t\t\t\t\ttitle=\"");
/* 4601 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keyhitrate.tooltip"));
/* 4602 */                                       out.write("\">\n\t\t\t\t\t\t\t\t\t");
/*      */                                       
/* 4604 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f6 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4605 */                                       _jspx_th_awolf_005ftimechart_005f6.setPageContext(_jspx_page_context);
/* 4606 */                                       _jspx_th_awolf_005ftimechart_005f6.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 4608 */                                       _jspx_th_awolf_005ftimechart_005f6.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 4610 */                                       _jspx_th_awolf_005ftimechart_005f6.setWidth("300");
/*      */                                       
/* 4612 */                                       _jspx_th_awolf_005ftimechart_005f6.setHeight("170");
/*      */                                       
/* 4614 */                                       _jspx_th_awolf_005ftimechart_005f6.setLegend("true");
/*      */                                       
/* 4616 */                                       _jspx_th_awolf_005ftimechart_005f6.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 4618 */                                       _jspx_th_awolf_005ftimechart_005f6.setYaxisLabel(FormatUtil.getString("am.webclient.mysql.graph.keyhitrate"));
/* 4619 */                                       int _jspx_eval_awolf_005ftimechart_005f6 = _jspx_th_awolf_005ftimechart_005f6.doStartTag();
/* 4620 */                                       if (_jspx_eval_awolf_005ftimechart_005f6 != 0) {
/* 4621 */                                         if (_jspx_eval_awolf_005ftimechart_005f6 != 1) {
/* 4622 */                                           out = _jspx_page_context.pushBody();
/* 4623 */                                           _jspx_th_awolf_005ftimechart_005f6.setBodyContent((BodyContent)out);
/* 4624 */                                           _jspx_th_awolf_005ftimechart_005f6.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4627 */                                           out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4628 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f6.doAfterBody();
/* 4629 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4632 */                                         if (_jspx_eval_awolf_005ftimechart_005f6 != 1) {
/* 4633 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4636 */                                       if (_jspx_th_awolf_005ftimechart_005f6.doEndTag() == 5) {
/* 4637 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f6); return;
/*      */                                       }
/*      */                                       
/* 4640 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f6);
/* 4641 */                                       out.write("</td> ");
/* 4642 */                                       out.write("\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4643 */                                       if (_jspx_meth_fmt_005fmessage_005f24(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4645 */                                       out.write("</span></td> ");
/* 4646 */                                       out.write("\n\t\t\t\t\t\t\t<td width=\"20%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4647 */                                       if (_jspx_meth_fmt_005fmessage_005f25(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4649 */                                       out.write("</span></td> ");
/* 4650 */                                       out.write("\n\t\t\t\t\t\t\t<td width=\"30%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4651 */                                       if (_jspx_meth_fmt_005fmessage_005f26(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4653 */                                       out.write("</span></td> ");
/* 4654 */                                       out.write("\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr></tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"50%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4655 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keyhitrate.tooltip"));
/* 4656 */                                       out.write(34);
/* 4657 */                                       out.write(62);
/* 4658 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keyhitrate"));
/* 4659 */                                       out.write("</td>\n\t\t\t\t\t\t\t<td width=\"20%\" height=\"35\" class=\"whitegrayborder\">\n\t\t\t\t\t\t\t");
/*      */                                       
/* 4661 */                                       if ((p.containsKey("KEYHITRATE")) && (!p.getProperty("KEYHITRATE").equals("-1")))
/*      */                                       {
/*      */ 
/* 4664 */                                         out.write(32);
/* 4665 */                                         out.print(formatNumber(p.getProperty("KEYHITRATE")));
/* 4666 */                                         out.write("%&nbsp; ");
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 4672 */                                         out.write(32);
/* 4673 */                                         out.write(45);
/* 4674 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 4678 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4679 */                                       out.print(resourceid);
/* 4680 */                                       out.write("&attributeid=118')\">");
/* 4681 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "118")));
/* 4682 */                                       out.write("</a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td height=\"35\" width=\"50%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4683 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keybufferused.tooltip"));
/* 4684 */                                       out.write(34);
/* 4685 */                                       out.write(62);
/* 4686 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keybufferused"));
/* 4687 */                                       out.write("</td>\n\t\t\t\t\t\t\t<td height=\"35\" width=\"20%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4688 */                                       out.print(formatBytesToKB(p.getProperty("KEYBUFFERUSED")));
/* 4689 */                                       out.write("\">\n\t\t\t\t\t\t\t");
/*      */                                       
/* 4691 */                                       if ((p.containsKey("KEYBUFFERUSED")) && (!p.getProperty("KEYBUFFERUSED").equals("-1")))
/*      */                                       {
/*      */ 
/* 4694 */                                         out.write(32);
/* 4695 */                                         out.print(formatNumber(p.getProperty("KEYBUFFERUSED")));
/* 4696 */                                         out.write(32);
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 4702 */                                         out.write(32);
/* 4703 */                                         out.write(45);
/* 4704 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 4708 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"30%\" height=\"35\" class=\"yellowgrayborder\"><a\n\t\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4709 */                                       out.print(resourceid);
/* 4710 */                                       out.write("&attributeid=119')\">");
/* 4711 */                                       out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "119")));
/* 4712 */                                       out.write("</a></td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"50%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4713 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keybuffersize.tooltip"));
/* 4714 */                                       out.write(34);
/* 4715 */                                       out.write(62);
/* 4716 */                                       out.print(FormatUtil.getString("am.webclient.mysql.keybuffersize"));
/* 4717 */                                       out.write("</td>\n\t\t\t\t\t\t\t<td width=\"20%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4718 */                                       out.print(formatBytesToKB(mysqldetails.getProperty("5188")));
/* 4719 */                                       out.write("\">\n\t\t\t\t\t\t\t");
/*      */                                       
/* 4721 */                                       if ((mysqldetails.containsKey("5188")) && (!mysqldetails.getProperty("5188").equals("-1")))
/*      */                                       {
/*      */ 
/* 4724 */                                         out.write(32);
/* 4725 */                                         out.print(formatNumber(mysqldetails.getProperty("5188")));
/* 4726 */                                         out.write(32);
/*      */ 
/*      */                                       }
/*      */                                       else
/*      */                                       {
/*      */ 
/* 4732 */                                         out.write(32);
/* 4733 */                                         out.write(45);
/* 4734 */                                         out.write(32);
/*      */                                       }
/*      */                                       
/*      */ 
/* 4738 */                                       out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"30%\" height=\"35\" class=\"whitegrayborder\">-</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */                                       
/* 4740 */                                       if (!EnterpriseUtil.isAdminServer())
/*      */                                       {
/*      */ 
/* 4743 */                                         out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\talign=\"right\"><img src=\"/images/icon_associateaction.gif\"\n\t\t\t\t\t\t\t\talign=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4744 */                                         out.print(resourceid);
/* 4745 */                                         out.write("&attributeIDs=118,119&attributeToSelect=118&redirectto=");
/* 4746 */                                         out.print(encodeurl);
/* 4747 */                                         out.write("\"\n\t\t\t\t\t\t\t\tclass=\"links\">");
/* 4748 */                                         out.print(ALERTCONFIG_TEXT);
/* 4749 */                                         out.write("</a>&nbsp;</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */                                       }
/* 4751 */                                       out.write("\n\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t\t");
/*      */                                       
/* 4753 */                                       if ((p.containsKey("QUERYCACHEHITRATE")) && (!p.getProperty("QUERYCACHEHITRATE").equals("-1")))
/*      */                                       {
/* 4755 */                                         out.write("\n\t\t\t\t\t<td width=\"50%\">\n\t\t\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t");
/* 4756 */                                         mysqlgraph.settype("QCACHE");
/* 4757 */                                         out.write("\n\t\t\t\t\t\t\t<td height=\"26\" colspan=\"3\" class=\"bottomborder\">\n\t\t\t\t\t\t\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4758 */                                         if (_jspx_meth_c_005fout_005f21(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 4760 */                                         out.write("&attributeid=120&period=-7',740,550)\">\n\t\t\t\t\t\t\t\t\t<img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\t\t\t\ttitle='");
/* 4761 */                                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4762 */                                         out.write("'></a></td>\n\t\t\t\t\t\t\t\t\t<td width=\"4%\"><a href=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4763 */                                         if (_jspx_meth_c_005fout_005f22(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 4765 */                                         out.write("&attributeid=120&period=-30',740,550)\"><img\n\t\t\t\t\t\t\t\t\t\tsrc=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\"\n\t\t\t\t\t\t\t\t\t\thspace=\"5\" vspace=\"3\" border=\"0\"\n\t\t\t\t\t\t\t\t\t\ttitle='");
/* 4766 */                                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 4767 */                                         out.write("'></a></td>\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t\t\t<td colspan=\"2\"\n\t\t\t\t\t\t\t\t\t\ttitle=\"");
/* 4768 */                                         out.print(FormatUtil.getString("am.webclient.mysql.qcachehitrate.tooltip"));
/* 4769 */                                         out.write("\">\n\t\t\t\t\t\t\t\t\t");
/*      */                                         
/* 4771 */                                         TimeChart _jspx_th_awolf_005ftimechart_005f7 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4772 */                                         _jspx_th_awolf_005ftimechart_005f7.setPageContext(_jspx_page_context);
/* 4773 */                                         _jspx_th_awolf_005ftimechart_005f7.setParent(_jspx_th_c_005fif_005f2);
/*      */                                         
/* 4775 */                                         _jspx_th_awolf_005ftimechart_005f7.setDataSetProducer("mysqlgraph");
/*      */                                         
/* 4777 */                                         _jspx_th_awolf_005ftimechart_005f7.setWidth("300");
/*      */                                         
/* 4779 */                                         _jspx_th_awolf_005ftimechart_005f7.setHeight("170");
/*      */                                         
/* 4781 */                                         _jspx_th_awolf_005ftimechart_005f7.setLegend("true");
/*      */                                         
/* 4783 */                                         _jspx_th_awolf_005ftimechart_005f7.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                         
/* 4785 */                                         _jspx_th_awolf_005ftimechart_005f7.setYaxisLabel(FormatUtil.getString("am.webclient.mysql.graph.queryhitrate"));
/* 4786 */                                         int _jspx_eval_awolf_005ftimechart_005f7 = _jspx_th_awolf_005ftimechart_005f7.doStartTag();
/* 4787 */                                         if (_jspx_eval_awolf_005ftimechart_005f7 != 0) {
/* 4788 */                                           if (_jspx_eval_awolf_005ftimechart_005f7 != 1) {
/* 4789 */                                             out = _jspx_page_context.pushBody();
/* 4790 */                                             _jspx_th_awolf_005ftimechart_005f7.setBodyContent((BodyContent)out);
/* 4791 */                                             _jspx_th_awolf_005ftimechart_005f7.doInitBody();
/*      */                                           }
/*      */                                           for (;;) {
/* 4794 */                                             out.write("\n\t\t\t\t\t\t\t\t\t");
/* 4795 */                                             int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f7.doAfterBody();
/* 4796 */                                             if (evalDoAfterBody != 2)
/*      */                                               break;
/*      */                                           }
/* 4799 */                                           if (_jspx_eval_awolf_005ftimechart_005f7 != 1) {
/* 4800 */                                             out = _jspx_page_context.popBody();
/*      */                                           }
/*      */                                         }
/* 4803 */                                         if (_jspx_th_awolf_005ftimechart_005f7.doEndTag() == 5) {
/* 4804 */                                           this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f7); return;
/*      */                                         }
/*      */                                         
/* 4807 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f7);
/* 4808 */                                         out.write("</td>  ");
/* 4809 */                                         out.write("\n\t\t\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t\t</table>\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4810 */                                         if (_jspx_meth_fmt_005fmessage_005f27(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 4812 */                                         out.write("</span></td>  ");
/* 4813 */                                         out.write("\n\t\t\t\t\t\t\t<td width=\"20%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4814 */                                         if (_jspx_meth_fmt_005fmessage_005f28(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 4816 */                                         out.write("</span></td>  ");
/* 4817 */                                         out.write("\n\t\t\t\t\t\t\t<td width=\"30%\" class=\"columnheadingnotop\"><span\n\t\t\t\t\t\t\t\tclass=\"bodytextbold\">");
/* 4818 */                                         if (_jspx_meth_fmt_005fmessage_005f29(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                           return;
/* 4820 */                                         out.write("</span></td>  ");
/* 4821 */                                         out.write("\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr></tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"50%\" height=\"35\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4822 */                                         out.print(FormatUtil.getString("am.webclient.mysql.qcachehitrate.tooltip"));
/* 4823 */                                         out.write(34);
/* 4824 */                                         out.write(62);
/* 4825 */                                         out.print(FormatUtil.getString("am.webclient.mysql.querycachehitrate"));
/* 4826 */                                         out.write("</td>\n\t\t\t\t\t\t\t<td width=\"20%\" class=\"whitegrayborder\">\n\t\t\t\t\t\t\t");
/*      */                                         
/* 4828 */                                         if ((p.containsKey("QUERYCACHEHITRATE")) && (!p.getProperty("QUERYCACHEHITRATE").equals("-1")))
/*      */                                         {
/*      */ 
/* 4831 */                                           out.write(32);
/* 4832 */                                           out.print(formatNumber(p.getProperty("QUERYCACHEHITRATE")));
/* 4833 */                                           out.write("%&nbsp;</td>\n\t\t\t\t\t\t\t<td width=\"30%\" class=\"whitegrayborder\"><a\n\t\t\t\t\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\t\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4834 */                                           out.print(resourceid);
/* 4835 */                                           out.write("&attributeid=120')\">");
/* 4836 */                                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "120")));
/* 4837 */                                           out.write("</a></td>\n\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 4843 */                                           out.write("\n\t\t\t\t\t\t\t-\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"30%\" class=\"whitegrayborder\">-</td>\n\t\t\t\t\t\t\t");
/*      */                                         }
/*      */                                         
/*      */ 
/* 4847 */                                         out.write("\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"50%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4848 */                                         out.print(FormatUtil.getString("am.webclient.mysql.qcachesize.tooltip"));
/* 4849 */                                         out.write(34);
/* 4850 */                                         out.write(62);
/* 4851 */                                         out.print(FormatUtil.getString("am.webclient.mysql.querycachesize"));
/* 4852 */                                         out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"20%\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4853 */                                         out.print(formatBytesToKB(mysqldetails.getProperty("5223")));
/* 4854 */                                         out.write("\">\n\t\t\t\t\t\t\t");
/*      */                                         
/* 4856 */                                         if ((mysqldetails.containsKey("5223")) && (!mysqldetails.getProperty("5223").equals("-1")))
/*      */                                         {
/*      */ 
/* 4859 */                                           out.write(32);
/* 4860 */                                           out.print(formatNumber(mysqldetails.getProperty("5223")));
/* 4861 */                                           out.write(32);
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 4867 */                                           out.write(32);
/* 4868 */                                           out.write(45);
/* 4869 */                                           out.write(32);
/*      */                                         }
/*      */                                         
/*      */ 
/* 4873 */                                         out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"30%\" class=\"yellowgrayborder\">-</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td width=\"50%\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4874 */                                         out.print(FormatUtil.getString("am.webclient.mysql.qcachelimit.tooltip"));
/* 4875 */                                         out.write(34);
/* 4876 */                                         out.write(62);
/* 4877 */                                         out.print(FormatUtil.getString("am.webclient.mysql.querycachelimit"));
/* 4878 */                                         out.write("</td>\n\t\t\t\t\t\t\t<td width=\"20%\" class=\"whitegrayborder\"\n\t\t\t\t\t\t\t\ttitle=\"");
/* 4879 */                                         out.print(formatBytesToKB(mysqldetails.getProperty("5188")));
/* 4880 */                                         out.write("\">\n\t\t\t\t\t\t\t");
/*      */                                         
/* 4882 */                                         if ((mysqldetails.containsKey("5188")) && (!mysqldetails.getProperty("5188").equals("-1")))
/*      */                                         {
/*      */ 
/* 4885 */                                           out.write(32);
/* 4886 */                                           out.print(formatNumber(mysqldetails.getProperty("5188")));
/* 4887 */                                           out.write("\n\t\t\t\t\t\t\t");
/*      */ 
/*      */                                         }
/*      */                                         else
/*      */                                         {
/*      */ 
/* 4893 */                                           out.write(32);
/* 4894 */                                           out.write(45);
/* 4895 */                                           out.write(32);
/*      */                                         }
/*      */                                         
/*      */ 
/* 4899 */                                         out.write("\n\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t<td width=\"30%\" class=\"whitegrayborder\">-</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */                                         
/* 4901 */                                         if (!EnterpriseUtil.isAdminServer())
/*      */                                         {
/*      */ 
/* 4904 */                                           out.write("\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<td colspan=\"3\" height=\"21\" class=\"yellowgrayborder\"\n\t\t\t\t\t\t\t\talign=\"right\"><img src=\"/images/icon_associateaction.gif\"\n\t\t\t\t\t\t\t\talign=\"absmiddle\">&nbsp;<a\n\t\t\t\t\t\t\t\thref=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4905 */                                           out.print(resourceid);
/* 4906 */                                           out.write("&attributeIDs=120&attributeToSelect=120&redirectto=");
/* 4907 */                                           out.print(encodeurl);
/* 4908 */                                           out.write("\"\n\t\t\t\t\t\t\t\tclass=\"links\">");
/* 4909 */                                           out.print(ALERTCONFIG_TEXT);
/* 4910 */                                           out.write("</a>&nbsp;</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t\t");
/*      */                                         }
/* 4912 */                                         out.write("\n\t\t\t\t\t</table>\n\t\t\t\t\t</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\n\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4916 */                                         out.write("\n\n\t\t</tr>\n\t</table>\n\t<table width=\"50%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<tr>\n\t\t\t<td class=\"tablebottom\">&nbsp;</td>\n\t\t</tr>\n\t</table>\n\t");
/*      */                                       }
/* 4918 */                                       out.write("\n\t<br>\n\n\t");
/*      */                                       
/* 4920 */                                       mysqlgraph.settype("QUERY_STATS");
/*      */                                       
/* 4922 */                                       out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t<tr>\n\t\t\t<td width=\"50%\" height=\"26\" class=\"tableheadingtrans\">");
/* 4923 */                                       out.print(FormatUtil.getString("am.webclient.mysql.querystats.tableheading"));
/* 4924 */                                       out.write("</td>\n\t\t</tr>\n\t</table>\n\t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\n\t\tclass=\"lrbborder\">\n\t\t<td align=\"center\" width=\"100%\" colspan=\"3\">\n\t\t");
/*      */                                       
/* 4926 */                                       TimeChart _jspx_th_awolf_005ftimechart_005f8 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4927 */                                       _jspx_th_awolf_005ftimechart_005f8.setPageContext(_jspx_page_context);
/* 4928 */                                       _jspx_th_awolf_005ftimechart_005f8.setParent(_jspx_th_c_005fif_005f2);
/*      */                                       
/* 4930 */                                       _jspx_th_awolf_005ftimechart_005f8.setDataSetProducer("mysqlgraph");
/*      */                                       
/* 4932 */                                       _jspx_th_awolf_005ftimechart_005f8.setWidth("600");
/*      */                                       
/* 4934 */                                       _jspx_th_awolf_005ftimechart_005f8.setHeight("185");
/*      */                                       
/* 4936 */                                       _jspx_th_awolf_005ftimechart_005f8.setLegend("true");
/*      */                                       
/* 4938 */                                       _jspx_th_awolf_005ftimechart_005f8.setXaxisLabel(FormatUtil.getString("am.webclient.common.axisname.time.text"));
/*      */                                       
/* 4940 */                                       _jspx_th_awolf_005ftimechart_005f8.setYaxisLabel(FormatUtil.getString("webclient.performance.reports.index.transmittraffic.yaxisname"));
/* 4941 */                                       int _jspx_eval_awolf_005ftimechart_005f8 = _jspx_th_awolf_005ftimechart_005f8.doStartTag();
/* 4942 */                                       if (_jspx_eval_awolf_005ftimechart_005f8 != 0) {
/* 4943 */                                         if (_jspx_eval_awolf_005ftimechart_005f8 != 1) {
/* 4944 */                                           out = _jspx_page_context.pushBody();
/* 4945 */                                           _jspx_th_awolf_005ftimechart_005f8.setBodyContent((BodyContent)out);
/* 4946 */                                           _jspx_th_awolf_005ftimechart_005f8.doInitBody();
/*      */                                         }
/*      */                                         for (;;) {
/* 4949 */                                           out.write(10);
/* 4950 */                                           out.write(9);
/* 4951 */                                           out.write(9);
/* 4952 */                                           int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f8.doAfterBody();
/* 4953 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/* 4956 */                                         if (_jspx_eval_awolf_005ftimechart_005f8 != 1) {
/* 4957 */                                           out = _jspx_page_context.popBody();
/*      */                                         }
/*      */                                       }
/* 4960 */                                       if (_jspx_th_awolf_005ftimechart_005f8.doEndTag() == 5) {
/* 4961 */                                         this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f8); return;
/*      */                                       }
/*      */                                       
/* 4964 */                                       this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f8);
/* 4965 */                                       out.write("\n\t\t</td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"columnheadingnotop\" width=\"37%\" align=\"left\"><span\n\t\t\t\tclass=\"bodytextbold\">");
/* 4966 */                                       if (_jspx_meth_fmt_005fmessage_005f30(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4968 */                                       out.write("</span></td>  ");
/* 4969 */                                       out.write("\n\t\t\t<td class=\"columnheadingnotop\" width=\"27%\" align=\"left\"><span\n\t\t\t\tclass=\"bodytextbold\">");
/* 4970 */                                       if (_jspx_meth_fmt_005fmessage_005f31(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4972 */                                       out.write("</span></td>  ");
/* 4973 */                                       out.write("\n\t\t\t<td class=\"columnheadingnotop\" width=\"36%\" align=\"left\"><span\n\t\t\t\tclass=\"bodytextbold\">");
/* 4974 */                                       if (_jspx_meth_fmt_005fmessage_005f32(_jspx_th_c_005fif_005f2, _jspx_page_context))
/*      */                                         return;
/* 4976 */                                       out.write("</span></td>  ");
/* 4977 */                                       out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"whitegrayborder\" width=\"37%\">");
/* 4978 */                                       out.print(FormatUtil.getString("am.webclient.mysql.querystats.delected"));
/* 4979 */                                       out.write("</td>\n\t\t\t");
/*      */                                       
/* 4981 */                                       if (rateDeleted == -1.0D)
/*      */                                       {
/* 4983 */                                         out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"27%\" align=\"center\">-</td>\n\t\t\t<td width=\"52%\" height=\"35\" align=\"center\" class=\"whitegrayborder\">-</td>\n\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 4987 */                                         out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 4988 */                                         out.print(rateDeleted);
/* 4989 */                                         out.write("</td>\n\t\t\t<td width=\"52%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4990 */                                         out.print(resourceid);
/* 4991 */                                         out.write("&attributeid=3152')\">");
/* 4992 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3152")));
/* 4993 */                                         out.write("</a></td>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 4997 */                                       out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"whitegrayborder\" width=\"37%\">");
/* 4998 */                                       out.print(FormatUtil.getString("am.webclient.mysql.querystats.inserted"));
/* 4999 */                                       out.write("</td>\n\t\t\t");
/*      */                                       
/* 5001 */                                       if (rateInserted == -1.0D)
/*      */                                       {
/* 5003 */                                         out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"27%\" align=\"center\">-</td>\n\t\t\t<td width=\"52%\" height=\"35\" align=\"center\" class=\"whitegrayborder\">-</td>\n\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5007 */                                         out.write("\n\t\t\t<td class=\"yellowgrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 5008 */                                         out.print(rateInserted);
/* 5009 */                                         out.write("</td>\n\t\t\t<td width=\"52%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5010 */                                         out.print(resourceid);
/* 5011 */                                         out.write("&attributeid=3153')\">");
/* 5012 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3153")));
/* 5013 */                                         out.write("</a></td>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 5017 */                                       out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"whitegrayborder\" width=\"37%\">");
/* 5018 */                                       out.print(FormatUtil.getString("am.webclient.mysql.querystats.selected"));
/* 5019 */                                       out.write("</td>\n\t\t\t");
/*      */                                       
/* 5021 */                                       if (rateSelected == -1.0D)
/*      */                                       {
/* 5023 */                                         out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"27%\" align=\"center\">-</td>\n\t\t\t<td width=\"52%\" height=\"35\" align=\"center\" class=\"whitegrayborder\">-</td>\n\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5027 */                                         out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 5028 */                                         out.print(rateSelected);
/* 5029 */                                         out.write("</td>\n\t\t\t<td width=\"52%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5030 */                                         out.print(resourceid);
/* 5031 */                                         out.write("&attributeid=3154')\">");
/* 5032 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3154")));
/* 5033 */                                         out.write("</a></td>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 5037 */                                       out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td class=\"yellowgrayborder\" width=\"37%\">");
/* 5038 */                                       out.print(FormatUtil.getString("am.webclient.mysql.querystats.updated"));
/* 5039 */                                       out.write("</td>\n\t\t\t");
/*      */                                       
/* 5041 */                                       if (rateUpdated == -1.0D)
/*      */                                       {
/* 5043 */                                         out.write("\n\t\t\t<td class=\"whitegrayborder\" width=\"27%\" align=\"center\">-</td>\n\t\t\t<td width=\"52%\" height=\"35\" align=\"center\" class=\"whitegrayborder\">-</td>\n\t\t\t");
/*      */                                       }
/*      */                                       else
/*      */                                       {
/* 5047 */                                         out.write("\n\t\t\t<td class=\"yellowgrayborder\" width=\"27%\" align=\"left\">&nbsp;");
/* 5048 */                                         out.print(rateUpdated);
/* 5049 */                                         out.write("</td>\n\t\t\t<td width=\"52%\" height=\"35\" class=\"whitegrayborder\"><a\n\t\t\t\thref=\"javascript:void(0)\"\n\t\t\t\tonClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 5050 */                                         out.print(resourceid);
/* 5051 */                                         out.write("&attributeid=3155')\">");
/* 5052 */                                         out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3155")));
/* 5053 */                                         out.write("</a></td>\n\t\t\t");
/*      */                                       }
/*      */                                       
/*      */ 
/* 5057 */                                       out.write("\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img\n\t\t\t\tsrc=\"../images/icon_associateaction.gif\" align=\"absmiddle\">&nbsp;<a\n\t\t\t\thref='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 5058 */                                       out.print(resourceid);
/* 5059 */                                       out.write("&attributeIDs=3152,3153,3154,3155&attributeToSelect=3152&redirectto=");
/* 5060 */                                       out.print(encodeurl);
/* 5061 */                                       out.write("'\n\t\t\t\tclass=\"staticlinks\">");
/* 5062 */                                       out.print(ALERTCONFIG_TEXT);
/* 5063 */                                       out.write("</a></td>\n\t\t</tr>\n\t</table>\n");
/* 5064 */                                       int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 5065 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 5069 */                                   if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 5070 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/*      */                                   }
/*      */                                   else {
/* 5073 */                                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 5074 */                                     out.write("\n<br>\n");
/*      */                                     
/* 5076 */                                     IterateTag _jspx_th_logic_005fiterate_005f3 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 5077 */                                     _jspx_th_logic_005fiterate_005f3.setPageContext(_jspx_page_context);
/* 5078 */                                     _jspx_th_logic_005fiterate_005f3.setParent(null);
/*      */                                     
/* 5080 */                                     _jspx_th_logic_005fiterate_005f3.setName("script_ids");
/*      */                                     
/* 5082 */                                     _jspx_th_logic_005fiterate_005f3.setId("attribute");
/*      */                                     
/* 5084 */                                     _jspx_th_logic_005fiterate_005f3.setIndexId("j");
/*      */                                     
/* 5086 */                                     _jspx_th_logic_005fiterate_005f3.setType("java.lang.String");
/* 5087 */                                     int _jspx_eval_logic_005fiterate_005f3 = _jspx_th_logic_005fiterate_005f3.doStartTag();
/* 5088 */                                     if (_jspx_eval_logic_005fiterate_005f3 != 0) {
/* 5089 */                                       String attribute = null;
/* 5090 */                                       Integer j = null;
/* 5091 */                                       if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5092 */                                         out = _jspx_page_context.pushBody();
/* 5093 */                                         _jspx_th_logic_005fiterate_005f3.setBodyContent((BodyContent)out);
/* 5094 */                                         _jspx_th_logic_005fiterate_005f3.doInitBody();
/*      */                                       }
/* 5096 */                                       attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5097 */                                       j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                       for (;;) {
/* 5099 */                                         out.write(10);
/* 5100 */                                         out.write(9);
/*      */                                         
/* 5102 */                                         String query = "select scriptname,displayname from AM_ScriptArgs where resourceid=" + attribute;
/* 5103 */                                         String monitorname1 = null;
/* 5104 */                                         String displayname1 = null;
/*      */                                         try
/*      */                                         {
/* 5107 */                                           AMConnectionPool cp = AMConnectionPool.getInstance();
/* 5108 */                                           ResultSet rs = AMConnectionPool.executeQueryStmt(query);
/* 5109 */                                           if (rs.next())
/*      */                                           {
/* 5111 */                                             monitorname1 = rs.getString("scriptname");
/* 5112 */                                             displayname1 = rs.getString("displayname");
/*      */                                           }
/* 5114 */                                           rs.close();
/*      */                                         }
/*      */                                         catch (Exception exc) {}
/*      */                                         
/*      */ 
/* 5119 */                                         String url2 = "/showresource.do?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true";
/* 5120 */                                         String url3 = "/jsp/HostScript.jsp?resourceid=" + attribute + "&type=Script Monitor&moname=" + monitorname1 + "&method=showScriptMonitorDetails&resourcename=" + displayname1 + "&fromhost=true&hostid=" + resourceid;
/*      */                                         
/* 5122 */                                         out.write("\n\t<table width=\"99%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\"\n\t\tclass=\"lrtbdarkborder\">\n\t\t");
/* 5123 */                                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url2, out, false);
/* 5124 */                                         out.write("\n\t\t<tr>\n\t\t\t<td width=\"99%\" class=\"tableheadingtrans\"><a\n\t\t\t\thref=\"showresource.do?method=showResourceForResourceID&resourceid=");
/* 5125 */                                         out.print(attribute);
/* 5126 */                                         out.write("\"\n\t\t\t\tclass=\"staticlinks\">");
/* 5127 */                                         out.print(FormatUtil.getString("am.webclient.hostResource.servers.scriptmonitor"));
/* 5128 */                                         out.write("\n\t\t\t- ");
/* 5129 */                                         out.print(displayname1);
/* 5130 */                                         out.write("</a></td>\n\t\t</tr>\n\t\t<tr>\n\t\t\t<td>");
/* 5131 */                                         org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, url3, out, false);
/* 5132 */                                         out.write("</td>\n\t\t</tr>\n\t\t<br>\n\t</table>\n\t<br>\n");
/* 5133 */                                         int evalDoAfterBody = _jspx_th_logic_005fiterate_005f3.doAfterBody();
/* 5134 */                                         attribute = (String)_jspx_page_context.findAttribute("attribute");
/* 5135 */                                         j = (Integer)_jspx_page_context.findAttribute("j");
/* 5136 */                                         if (evalDoAfterBody != 2)
/*      */                                           break;
/*      */                                       }
/* 5139 */                                       if (_jspx_eval_logic_005fiterate_005f3 != 1) {
/* 5140 */                                         out = _jspx_page_context.popBody();
/*      */                                       }
/*      */                                     }
/* 5143 */                                     if (_jspx_th_logic_005fiterate_005f3.doEndTag() == 5) {
/* 5144 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/*      */                                     }
/*      */                                     else {
/* 5147 */                                       this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f3);
/* 5148 */                                       out.write(10);
/* 5149 */                                       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n");
/* 5150 */                                       DialChartSupport dialGraph = null;
/* 5151 */                                       dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 5152 */                                       if (dialGraph == null) {
/* 5153 */                                         dialGraph = new DialChartSupport();
/* 5154 */                                         _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */                                       }
/* 5156 */                                       out.write(10);
/*      */                                       
/*      */                                       try
/*      */                                       {
/* 5160 */                                         String hostos = (String)systeminfo.get("HOSTOS");
/* 5161 */                                         String hostname = (String)systeminfo.get("HOSTNAME");
/* 5162 */                                         String hostid = (String)systeminfo.get("host_resid");
/* 5163 */                                         boolean isConf = false;
/* 5164 */                                         if ((systeminfo.get("isConf") != null) && (((String)systeminfo.get("isConf")).equals("true"))) {
/* 5165 */                                           isConf = true;
/*      */                                         }
/* 5167 */                                         com.adventnet.appmanager.db.RepairTables rt = new com.adventnet.appmanager.db.RepairTables();
/* 5168 */                                         Properties property = new Properties();
/* 5169 */                                         if ((hostos != null) && (!hostos.equalsIgnoreCase("unknown")) && (!hostos.equalsIgnoreCase("node")))
/*      */                                         {
/* 5171 */                                           property = com.adventnet.appmanager.db.RepairTables.getValuesForHost(hostname, hostos);
/* 5172 */                                           if ((property != null) && (property.size() > 0))
/*      */                                           {
/* 5174 */                                             String cpuid = property.getProperty("cpuid");
/* 5175 */                                             String memid = property.getProperty("memid");
/* 5176 */                                             String diskid = property.getProperty("diskid");
/* 5177 */                                             String cpuvalue = property.getProperty("CPU Utilization");
/* 5178 */                                             String memvalue = property.getProperty("Memory Utilization");
/* 5179 */                                             String memurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + memid + "&period=0')";
/* 5180 */                                             String cpuurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + cpuid + "&period=0')";
/* 5181 */                                             String diskvalue = property.getProperty("Disk Utilization");
/* 5182 */                                             String diskurl = "fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=" + hostid + "&attributeid=" + diskid + "&period=0')";
/*      */                                             
/* 5184 */                                             if (!isConf) {
/* 5185 */                                               out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td height=\"26\" class=\"tableheading\">");
/* 5186 */                                               out.print(FormatUtil.getString("am.webclient.serversnapshot.heading"));
/* 5187 */                                               out.write(45);
/* 5188 */                                               if (systeminfo.get("host_resid") != null) {
/* 5189 */                                                 out.write("<a href=\"showresource.do?resourceid=");
/* 5190 */                                                 out.print(hostid);
/* 5191 */                                                 out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5192 */                                                 out.print(hostname);
/* 5193 */                                                 out.write("</a>");
/* 5194 */                                               } else { out.println(hostname); }
/* 5195 */                                               out.write("</td>\t");
/* 5196 */                                               out.write("\n  </tr>\n</table>\n\n\n<table width=\"99%\" border=\"0\" cellpadding=\"10\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n    <td width=\"30%\" valign=\"top\">\n    ");
/* 5197 */                                               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5198 */                                               out.write("\n    <table  cellspacing=\"0\" cellpadding=\"3\" border=\"0\" width=\"99%\">\n\n        <tr>\n         ");
/*      */                                               
/*      */ 
/* 5201 */                                               if (cpuvalue != null)
/*      */                                               {
/*      */ 
/* 5204 */                                                 dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5205 */                                                 out.write("\n         <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5206 */                                                 out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5207 */                                                 out.write(45);
/* 5208 */                                                 out.print(cpuvalue);
/* 5209 */                                                 out.write(" %'>\n\n");
/*      */                                                 
/* 5211 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5212 */                                                 _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 5213 */                                                 _jspx_th_awolf_005fdialchart_005f0.setParent(null);
/*      */                                                 
/* 5215 */                                                 _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5217 */                                                 _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */                                                 
/* 5219 */                                                 _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */                                                 
/* 5221 */                                                 _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */                                                 
/* 5223 */                                                 _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */                                                 
/* 5225 */                                                 _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */                                                 
/* 5227 */                                                 _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/*      */                                                 
/* 5229 */                                                 _jspx_th_awolf_005fdialchart_005f0.setLink(cpuurl);
/*      */                                                 
/* 5231 */                                                 _jspx_th_awolf_005fdialchart_005f0.setResourceId(hostid);
/*      */                                                 
/* 5233 */                                                 _jspx_th_awolf_005fdialchart_005f0.setAttributeId(cpuid);
/* 5234 */                                                 int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 5235 */                                                 if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 5236 */                                                   if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5237 */                                                     out = _jspx_page_context.pushBody();
/* 5238 */                                                     _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 5239 */                                                     _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5242 */                                                     out.write(10);
/* 5243 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 5244 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5247 */                                                   if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 5248 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5251 */                                                 if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 5252 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0); return;
/*      */                                                 }
/*      */                                                 
/* 5255 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 5256 */                                                 out.write("\n         </td>\n            ");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5260 */                                                 out.write("\n\n\t<tr>\n\t\t<td><img src=\"../images/spacer.gif\" height=\"30\" ></td></tr>\n\n\n<tr>  \t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5261 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5262 */                                                 out.write(32);
/* 5263 */                                                 out.write(62);
/* 5264 */                                                 out.write(10);
/* 5265 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5266 */                                                 out.write("</td></tr>\n \t\t<!--img src='../images/nodata.gif'-->\n<tr>\t\t<td><img src=\"../images/spacer.gif\" height=\"30\"></td></tr>\n\n\n  ");
/*      */                                               }
/* 5268 */                                               out.write("\n      </tr>\n       <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5269 */                                               if (cpuvalue != null)
/*      */                                               {
/* 5271 */                                                 out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5272 */                                                 out.print(hostid);
/* 5273 */                                                 out.write("&attributeid=");
/* 5274 */                                                 out.print(cpuid);
/* 5275 */                                                 out.write("&period=-7')\" class='bodytextbold'>");
/* 5276 */                                                 out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5277 */                                                 out.write(32);
/* 5278 */                                                 out.write(45);
/* 5279 */                                                 out.write(32);
/* 5280 */                                                 out.print(cpuvalue);
/* 5281 */                                                 out.write("</a> %\n");
/*      */                                               }
/* 5283 */                                               out.write("\n  </td>\n       </tr>\n       </table>");
/* 5284 */                                               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5285 */                                               out.write("</td>\n      <td width=\"30%\"> ");
/* 5286 */                                               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5287 */                                               out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n             <tr>\n");
/*      */                                               
/* 5289 */                                               if (memvalue != null)
/*      */                                               {
/*      */ 
/* 5292 */                                                 dialGraph.setValue(Long.parseLong(memvalue));
/* 5293 */                                                 out.write("\n            <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5294 */                                                 out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5295 */                                                 out.write(45);
/* 5296 */                                                 out.print(memvalue);
/* 5297 */                                                 out.write(" %' >\n\n");
/*      */                                                 
/* 5299 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5300 */                                                 _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 5301 */                                                 _jspx_th_awolf_005fdialchart_005f1.setParent(null);
/*      */                                                 
/* 5303 */                                                 _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5305 */                                                 _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */                                                 
/* 5307 */                                                 _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */                                                 
/* 5309 */                                                 _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */                                                 
/* 5311 */                                                 _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */                                                 
/* 5313 */                                                 _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */                                                 
/* 5315 */                                                 _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/*      */                                                 
/* 5317 */                                                 _jspx_th_awolf_005fdialchart_005f1.setLink(memurl);
/*      */                                                 
/* 5319 */                                                 _jspx_th_awolf_005fdialchart_005f1.setResourceId(hostid);
/*      */                                                 
/* 5321 */                                                 _jspx_th_awolf_005fdialchart_005f1.setAttributeId(memid);
/* 5322 */                                                 int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 5323 */                                                 if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 5324 */                                                   if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5325 */                                                     out = _jspx_page_context.pushBody();
/* 5326 */                                                     _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 5327 */                                                     _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5330 */                                                     out.write(32);
/* 5331 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 5332 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5335 */                                                   if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 5336 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5339 */                                                 if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 5340 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1); return;
/*      */                                                 }
/*      */                                                 
/* 5343 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 5344 */                                                 out.write(32);
/* 5345 */                                                 out.write("\n            </td>\n            ");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5349 */                                                 out.write("\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n<tr>    <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5350 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5351 */                                                 out.write(" >\n\n");
/* 5352 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5353 */                                                 out.write("</td></tr>\n<!--img src='../images/nodata.gif'-->\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                               }
/* 5355 */                                               out.write("\n  </tr>\n   <tr>\n        <td align='center' class='bodytextbold'>\n ");
/* 5356 */                                               if (memvalue != null)
/*      */                                               {
/* 5358 */                                                 out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5359 */                                                 out.print(hostid);
/* 5360 */                                                 out.write("&attributeid=");
/* 5361 */                                                 out.print(memid);
/* 5362 */                                                 out.write("&period=-7')\" class='bodytextbold'>");
/* 5363 */                                                 out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5364 */                                                 out.write(45);
/* 5365 */                                                 out.print(memvalue);
/* 5366 */                                                 out.write("</a> %\n  ");
/*      */                                               }
/* 5368 */                                               out.write("\n  </td>\n       </tr>\n    </table>");
/* 5369 */                                               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5370 */                                               out.write("</td>\n      <td width=\"30%\">");
/* 5371 */                                               out.write("<!--$Id$-->\n\n<table border=0 cellspacing=0 cellpadding=0 class=dashboard width=100%>\n\t<tr>\n\t\t<td class=dashTopLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTop width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashTopRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashboard width=100% align=center>\n");
/* 5372 */                                               out.write(" <table cellspacing=\"0\" cellpadding=\"3\" border=\"0\">\n       <tr>\n  ");
/*      */                                               
/*      */ 
/* 5375 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                               {
/*      */ 
/*      */ 
/* 5379 */                                                 dialGraph.setValue(Long.parseLong(diskvalue));
/* 5380 */                                                 out.write("\n\n             <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5381 */                                                 out.print(FormatUtil.getString("am.reporttab.shortname.disk.text"));
/* 5382 */                                                 out.write(45);
/* 5383 */                                                 out.print(diskvalue);
/* 5384 */                                                 out.write("%' >\n");
/*      */                                                 
/* 5386 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f2 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.get(DialChart.class);
/* 5387 */                                                 _jspx_th_awolf_005fdialchart_005f2.setPageContext(_jspx_page_context);
/* 5388 */                                                 _jspx_th_awolf_005fdialchart_005f2.setParent(null);
/*      */                                                 
/* 5390 */                                                 _jspx_th_awolf_005fdialchart_005f2.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5392 */                                                 _jspx_th_awolf_005fdialchart_005f2.setWidth("150");
/*      */                                                 
/* 5394 */                                                 _jspx_th_awolf_005fdialchart_005f2.setHeight("148");
/*      */                                                 
/* 5396 */                                                 _jspx_th_awolf_005fdialchart_005f2.setLegend("false");
/*      */                                                 
/* 5398 */                                                 _jspx_th_awolf_005fdialchart_005f2.setXaxisLabel("");
/*      */                                                 
/* 5400 */                                                 _jspx_th_awolf_005fdialchart_005f2.setYaxisLabel("");
/*      */                                                 
/* 5402 */                                                 _jspx_th_awolf_005fdialchart_005f2.setDateFormat("HH:mm");
/*      */                                                 
/* 5404 */                                                 _jspx_th_awolf_005fdialchart_005f2.setLink(diskurl);
/*      */                                                 
/* 5406 */                                                 _jspx_th_awolf_005fdialchart_005f2.setResourceId(hostid);
/*      */                                                 
/* 5408 */                                                 _jspx_th_awolf_005fdialchart_005f2.setAttributeId(diskid);
/* 5409 */                                                 int _jspx_eval_awolf_005fdialchart_005f2 = _jspx_th_awolf_005fdialchart_005f2.doStartTag();
/* 5410 */                                                 if (_jspx_eval_awolf_005fdialchart_005f2 != 0) {
/* 5411 */                                                   if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5412 */                                                     out = _jspx_page_context.pushBody();
/* 5413 */                                                     _jspx_th_awolf_005fdialchart_005f2.setBodyContent((BodyContent)out);
/* 5414 */                                                     _jspx_th_awolf_005fdialchart_005f2.doInitBody();
/*      */                                                   }
/*      */                                                   for (;;) {
/* 5417 */                                                     out.write(32);
/* 5418 */                                                     int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f2.doAfterBody();
/* 5419 */                                                     if (evalDoAfterBody != 2)
/*      */                                                       break;
/*      */                                                   }
/* 5422 */                                                   if (_jspx_eval_awolf_005fdialchart_005f2 != 1) {
/* 5423 */                                                     out = _jspx_page_context.popBody();
/*      */                                                   }
/*      */                                                 }
/* 5426 */                                                 if (_jspx_th_awolf_005fdialchart_005f2.doEndTag() == 5) {
/* 5427 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2); return;
/*      */                                                 }
/*      */                                                 
/* 5430 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId.reuse(_jspx_th_awolf_005fdialchart_005f2);
/* 5431 */                                                 out.write(32);
/* 5432 */                                                 out.write(32);
/* 5433 */                                                 out.write("\n    </td>\n            ");
/*      */                                               }
/*      */                                               else
/*      */                                               {
/* 5437 */                                                 out.write("\n\n\t<tr>\n<td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n   <tr> <td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title=");
/* 5438 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5439 */                                                 out.write(32);
/* 5440 */                                                 out.write(62);
/* 5441 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5442 */                                                 out.write("\n <!--img src='../images/nodata.gif'--></td></tr>\n<tr><td height=\"40\"> <img src='../images/spacer.gif'></td></tr>\n\n\n  ");
/*      */                                               }
/* 5444 */                                               out.write("\n  </tr>\n  <tr>\n\n\n\n  <td align='center'  class='bodytextbold'>\n");
/* 5445 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1")))
/*      */                                               {
/* 5447 */                                                 out.write("\n<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5448 */                                                 out.print(hostid);
/* 5449 */                                                 out.write("&attributeid=");
/* 5450 */                                                 out.print(diskid);
/* 5451 */                                                 out.write("&period=-7')\" class='bodytextbold'>");
/* 5452 */                                                 out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5453 */                                                 out.write(45);
/* 5454 */                                                 out.print(diskvalue);
/* 5455 */                                                 out.write("</a> %\n     ");
/*      */                                               }
/* 5457 */                                               out.write("\n  </td>\n  </tr>\n</table>");
/* 5458 */                                               out.write("<!--$Id$-->\n\n\t\t</td>\n\t\t<td class=dashRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n\t<tr>\n\t\t<td class=dashBottomLeft><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottom width=100%><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t\t<td class=dashBottomRight><img src=\"../images/spacer.gif\" width=7 height=7></td>\n\t</tr>\n</table>\n");
/* 5459 */                                               out.write("</td></tr></table>\n\n");
/*      */                                             } else {
/* 5461 */                                               out.write("\n\n\t<table cellpadding=\"0\" cellspacing=\"0\" class=\"conf-mon-table\" width=\"100%\" onMouseOver=\"ShowPicture('configureIcons_ifany',1,'hostresource')\" onMouseOut=\"ShowPicture('configureIcons_ifany',0,'hostresource')\">\n\t<tr><td class=\"conf-mon-heading\" align=\"left\" colspan=\"3\">");
/* 5462 */                                               out.print(FormatUtil.getString("am.webclient.serversnapshot.allCaps.heading"));
/* 5463 */                                               out.write("&nbsp;-&nbsp;<a href=\"showresource.do?resourceid=");
/* 5464 */                                               out.print(systeminfo.get("host_resid"));
/* 5465 */                                               out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 5466 */                                               out.print(hostname);
/* 5467 */                                               out.write("</a></td></tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"><div id=\"configureIcons_ifany\"></div></td></tr>\n\t<tr>\n");
/* 5468 */                                               if (cpuvalue != null)
/*      */                                               {
/*      */ 
/* 5471 */                                                 dialGraph.setValue(Long.parseLong(cpuvalue));
/* 5472 */                                                 out.write("\n         <td align=\"center\" valign=\"center\">\n\t\t\t");
/*      */                                                 
/* 5474 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f3 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5475 */                                                 _jspx_th_awolf_005fdialchart_005f3.setPageContext(_jspx_page_context);
/* 5476 */                                                 _jspx_th_awolf_005fdialchart_005f3.setParent(null);
/*      */                                                 
/* 5478 */                                                 _jspx_th_awolf_005fdialchart_005f3.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5480 */                                                 _jspx_th_awolf_005fdialchart_005f3.setWidth("150");
/*      */                                                 
/* 5482 */                                                 _jspx_th_awolf_005fdialchart_005f3.setHeight("148");
/*      */                                                 
/* 5484 */                                                 _jspx_th_awolf_005fdialchart_005f3.setLegend("false");
/*      */                                                 
/* 5486 */                                                 _jspx_th_awolf_005fdialchart_005f3.setXaxisLabel("");
/*      */                                                 
/* 5488 */                                                 _jspx_th_awolf_005fdialchart_005f3.setYaxisLabel("");
/*      */                                                 
/* 5490 */                                                 _jspx_th_awolf_005fdialchart_005f3.setDateFormat("HH:mm");
/*      */                                                 
/* 5492 */                                                 _jspx_th_awolf_005fdialchart_005f3.setLink(cpuurl);
/*      */                                                 
/* 5494 */                                                 _jspx_th_awolf_005fdialchart_005f3.setResourceId(hostid);
/*      */                                                 
/* 5496 */                                                 _jspx_th_awolf_005fdialchart_005f3.setAttributeId(cpuid);
/* 5497 */                                                 int _jspx_eval_awolf_005fdialchart_005f3 = _jspx_th_awolf_005fdialchart_005f3.doStartTag();
/* 5498 */                                                 if (_jspx_th_awolf_005fdialchart_005f3.doEndTag() == 5) {
/* 5499 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3); return;
/*      */                                                 }
/*      */                                                 
/* 5502 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f3);
/* 5503 */                                                 out.write("\n         </td>\n     ");
/*      */                                               }
/*      */                                               else {
/* 5506 */                                                 out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5507 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5508 */                                                 out.write(39);
/* 5509 */                                                 out.write(32);
/* 5510 */                                                 out.write(62);
/* 5511 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5512 */                                                 out.write("\n \t\t</td>\n\t\t");
/*      */                                               }
/* 5514 */                                               if (memvalue != null) {
/* 5515 */                                                 dialGraph.setValue(Long.parseLong(memvalue));
/* 5516 */                                                 out.write("\n            <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                                 
/* 5518 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f4 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5519 */                                                 _jspx_th_awolf_005fdialchart_005f4.setPageContext(_jspx_page_context);
/* 5520 */                                                 _jspx_th_awolf_005fdialchart_005f4.setParent(null);
/*      */                                                 
/* 5522 */                                                 _jspx_th_awolf_005fdialchart_005f4.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5524 */                                                 _jspx_th_awolf_005fdialchart_005f4.setWidth("150");
/*      */                                                 
/* 5526 */                                                 _jspx_th_awolf_005fdialchart_005f4.setHeight("148");
/*      */                                                 
/* 5528 */                                                 _jspx_th_awolf_005fdialchart_005f4.setLegend("false");
/*      */                                                 
/* 5530 */                                                 _jspx_th_awolf_005fdialchart_005f4.setXaxisLabel("");
/*      */                                                 
/* 5532 */                                                 _jspx_th_awolf_005fdialchart_005f4.setYaxisLabel("");
/*      */                                                 
/* 5534 */                                                 _jspx_th_awolf_005fdialchart_005f4.setDateFormat("HH:mm");
/*      */                                                 
/* 5536 */                                                 _jspx_th_awolf_005fdialchart_005f4.setLink(memurl);
/*      */                                                 
/* 5538 */                                                 _jspx_th_awolf_005fdialchart_005f4.setResourceId(hostid);
/*      */                                                 
/* 5540 */                                                 _jspx_th_awolf_005fdialchart_005f4.setAttributeId(memid);
/* 5541 */                                                 int _jspx_eval_awolf_005fdialchart_005f4 = _jspx_th_awolf_005fdialchart_005f4.doStartTag();
/* 5542 */                                                 if (_jspx_th_awolf_005fdialchart_005f4.doEndTag() == 5) {
/* 5543 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4); return;
/*      */                                                 }
/*      */                                                 
/* 5546 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f4);
/* 5547 */                                                 out.write("\n            </td>\n         ");
/*      */                                               }
/*      */                                               else {
/* 5550 */                                                 out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5551 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5552 */                                                 out.write(39);
/* 5553 */                                                 out.write(32);
/* 5554 */                                                 out.write(62);
/* 5555 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5556 */                                                 out.write("\n \t\t</td>\n\t\t");
/*      */                                               }
/* 5558 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5559 */                                                 dialGraph.setValue(Long.parseLong(diskvalue));
/* 5560 */                                                 out.write("\n             <td align=\"center\" valign=\"center\">\n\t\t\t\t");
/*      */                                                 
/* 5562 */                                                 DialChart _jspx_th_awolf_005fdialchart_005f5 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.get(DialChart.class);
/* 5563 */                                                 _jspx_th_awolf_005fdialchart_005f5.setPageContext(_jspx_page_context);
/* 5564 */                                                 _jspx_th_awolf_005fdialchart_005f5.setParent(null);
/*      */                                                 
/* 5566 */                                                 _jspx_th_awolf_005fdialchart_005f5.setDataSetProducer("dialGraph");
/*      */                                                 
/* 5568 */                                                 _jspx_th_awolf_005fdialchart_005f5.setWidth("150");
/*      */                                                 
/* 5570 */                                                 _jspx_th_awolf_005fdialchart_005f5.setHeight("148");
/*      */                                                 
/* 5572 */                                                 _jspx_th_awolf_005fdialchart_005f5.setLegend("false");
/*      */                                                 
/* 5574 */                                                 _jspx_th_awolf_005fdialchart_005f5.setXaxisLabel("");
/*      */                                                 
/* 5576 */                                                 _jspx_th_awolf_005fdialchart_005f5.setYaxisLabel("");
/*      */                                                 
/* 5578 */                                                 _jspx_th_awolf_005fdialchart_005f5.setDateFormat("HH:mm");
/*      */                                                 
/* 5580 */                                                 _jspx_th_awolf_005fdialchart_005f5.setLink(diskurl);
/*      */                                                 
/* 5582 */                                                 _jspx_th_awolf_005fdialchart_005f5.setResourceId(hostid);
/*      */                                                 
/* 5584 */                                                 _jspx_th_awolf_005fdialchart_005f5.setAttributeId(diskid);
/* 5585 */                                                 int _jspx_eval_awolf_005fdialchart_005f5 = _jspx_th_awolf_005fdialchart_005f5.doStartTag();
/* 5586 */                                                 if (_jspx_th_awolf_005fdialchart_005f5.doEndTag() == 5) {
/* 5587 */                                                   this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5); return;
/*      */                                                 }
/*      */                                                 
/* 5590 */                                                 this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005fresourceId_005flink_005flegend_005fheight_005fdateFormat_005fdataSetProducer_005fattributeId_005fnobody.reuse(_jspx_th_awolf_005fdialchart_005f5);
/* 5591 */                                                 out.write(32);
/* 5592 */                                                 out.write("\n\t          </td>\n\t  ");
/*      */                                               }
/*      */                                               else {
/* 5595 */                                                 out.write("\n\t\t<td height=\"28\" colspan=\"0\" class=\"bodytext\" align='center' title='");
/* 5596 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5597 */                                                 out.write(39);
/* 5598 */                                                 out.write(32);
/* 5599 */                                                 out.write(62);
/* 5600 */                                                 out.print(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 5601 */                                                 out.write("\n \t\t</td>\n\t\t");
/*      */                                               }
/* 5603 */                                               out.write("\n         \t</tr>\n\t<tr id=\"showLinks_hostresource\">\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5604 */                                               out.print(hostid);
/* 5605 */                                               out.write("&attributeid=");
/* 5606 */                                               out.print(cpuid);
/* 5607 */                                               out.write("&period=-7')\" class='tooltip'>");
/* 5608 */                                               out.print(FormatUtil.getString("webclient.performance.reports.index.cpuutilization"));
/* 5609 */                                               out.write(32);
/* 5610 */                                               out.write(45);
/* 5611 */                                               out.write(32);
/* 5612 */                                               if (cpuvalue != null) {
/* 5613 */                                                 out.print(cpuvalue);
/*      */                                               }
/* 5615 */                                               out.write(" %</a>\n\t\t</span>\n\t\t</td>\n\t\t<td align=\"center\" >\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5616 */                                               out.print(hostid);
/* 5617 */                                               out.write("&attributeid=");
/* 5618 */                                               out.print(memid);
/* 5619 */                                               out.write("&period=-7')\" class='tooltip'>");
/* 5620 */                                               out.print(FormatUtil.getString("am.webclient.memoryutilization.heading"));
/* 5621 */                                               out.write(45);
/* 5622 */                                               if (memvalue != null) {
/* 5623 */                                                 out.print(memvalue);
/*      */                                               }
/* 5625 */                                               out.write(" %</a>\n  \t\t</span>\n\t\t</td>\n\t\t<td align=\"center\">\n\t\t<span>\n\t\t\t<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 5626 */                                               out.print(hostid);
/* 5627 */                                               out.write("&attributeid=");
/* 5628 */                                               out.print(diskid);
/* 5629 */                                               out.write("&period=-7')\" class='tooltip'>");
/* 5630 */                                               out.print(FormatUtil.getString("am.webclient.hostResource.servers.diskutil"));
/* 5631 */                                               out.write(45);
/* 5632 */                                               if ((diskvalue != null) && (!diskvalue.equals("-1"))) {
/* 5633 */                                                 out.print(diskvalue);
/*      */                                               }
/* 5635 */                                               out.write(" %</a>\n     \t</span>\n\t\t</td>\n\t</tr>\n\t<tr><td height=\"10\"><img src=\"/images/spacer.gif\"  height=\"12\" width=\"1\"></td></tr>\n</table>\n         \t\n");
/*      */                                             }
/* 5637 */                                             out.write(10);
/* 5638 */                                             out.write(10);
/*      */                                           }
/*      */                                           
/*      */                                         }
/*      */                                       }
/*      */                                       catch (Exception e)
/*      */                                       {
/* 5645 */                                         e.printStackTrace();
/*      */                                       }
/* 5647 */                                       out.write(10);
/* 5648 */                                       out.write(10);
/*      */                                     }
/* 5650 */                                   } } } } } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 5651 */         out = _jspx_out;
/* 5652 */         if ((out != null) && (out.getBufferSize() != 0))
/* 5653 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 5654 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 5657 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5663 */     PageContext pageContext = _jspx_page_context;
/* 5664 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5666 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5667 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 5668 */     _jspx_th_c_005fout_005f0.setParent(null);
/*      */     
/* 5670 */     _jspx_th_c_005fout_005f0.setValue("${param.resourceid}");
/* 5671 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 5672 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 5673 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5674 */       return true;
/*      */     }
/* 5676 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 5677 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5682 */     PageContext pageContext = _jspx_page_context;
/* 5683 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5685 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5686 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 5687 */     _jspx_th_c_005fout_005f1.setParent(null);
/*      */     
/* 5689 */     _jspx_th_c_005fout_005f1.setValue("${param.resourcename}");
/* 5690 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 5691 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 5692 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5693 */       return true;
/*      */     }
/* 5695 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 5696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5701 */     PageContext pageContext = _jspx_page_context;
/* 5702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5704 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5705 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 5706 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 5708 */     _jspx_th_c_005fout_005f2.setValue("${param.resourceid}");
/* 5709 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 5710 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 5711 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5712 */       return true;
/*      */     }
/* 5714 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 5715 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5720 */     PageContext pageContext = _jspx_page_context;
/* 5721 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5723 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5724 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 5725 */     _jspx_th_c_005fout_005f3.setParent(null);
/*      */     
/* 5727 */     _jspx_th_c_005fout_005f3.setValue("${param.resourcename}");
/* 5728 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 5729 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 5730 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5731 */       return true;
/*      */     }
/* 5733 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 5734 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5739 */     PageContext pageContext = _jspx_page_context;
/* 5740 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5742 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5743 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 5744 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5746 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 5747 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 5748 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 5749 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5750 */       return true;
/*      */     }
/* 5752 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 5753 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5758 */     PageContext pageContext = _jspx_page_context;
/* 5759 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5761 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5762 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 5763 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 5765 */     _jspx_th_c_005fout_005f5.setValue("${param.resourceid}");
/* 5766 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 5767 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 5768 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5769 */       return true;
/*      */     }
/* 5771 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 5772 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5777 */     PageContext pageContext = _jspx_page_context;
/* 5778 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5780 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5781 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 5782 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 5783 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 5784 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 5785 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5786 */         out = _jspx_page_context.pushBody();
/* 5787 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 5788 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5791 */         out.write("table.heading.attribute");
/* 5792 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 5793 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5796 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 5797 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5800 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 5801 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5802 */       return true;
/*      */     }
/* 5804 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 5805 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5810 */     PageContext pageContext = _jspx_page_context;
/* 5811 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5813 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5814 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 5815 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 5816 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 5817 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 5818 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5819 */         out = _jspx_page_context.pushBody();
/* 5820 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 5821 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5824 */         out.write("table.heading.value");
/* 5825 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 5826 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5829 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 5830 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5833 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 5834 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5835 */       return true;
/*      */     }
/* 5837 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 5838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5843 */     PageContext pageContext = _jspx_page_context;
/* 5844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5846 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5847 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 5848 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_c_005fif_005f1);
/* 5849 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 5850 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 5851 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 5852 */         out = _jspx_page_context.pushBody();
/* 5853 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 5854 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5857 */         out.write("table.heading.status");
/* 5858 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 5859 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5862 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 5863 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5866 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 5867 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5868 */       return true;
/*      */     }
/* 5870 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 5871 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5876 */     PageContext pageContext = _jspx_page_context;
/* 5877 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5879 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5880 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 5881 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5883 */     _jspx_th_c_005fout_005f6.setValue("${param.resourceid}");
/* 5884 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 5885 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 5886 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5887 */       return true;
/*      */     }
/* 5889 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 5890 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5895 */     PageContext pageContext = _jspx_page_context;
/* 5896 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5898 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5899 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 5900 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5902 */     _jspx_th_c_005fout_005f7.setValue("${param.resourceid}");
/* 5903 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 5904 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 5905 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5906 */       return true;
/*      */     }
/* 5908 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 5909 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5914 */     PageContext pageContext = _jspx_page_context;
/* 5915 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5917 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5918 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 5919 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5921 */     _jspx_th_c_005fout_005f8.setValue("${param.resourceid}");
/* 5922 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 5923 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 5924 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5925 */       return true;
/*      */     }
/* 5927 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 5928 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5933 */     PageContext pageContext = _jspx_page_context;
/* 5934 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5936 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 5937 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 5938 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 5940 */     _jspx_th_c_005fout_005f9.setValue("${param.resourceid}");
/* 5941 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 5942 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 5943 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5944 */       return true;
/*      */     }
/* 5946 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 5947 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5952 */     PageContext pageContext = _jspx_page_context;
/* 5953 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5955 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5956 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 5957 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 5958 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 5959 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 5960 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 5961 */         out = _jspx_page_context.pushBody();
/* 5962 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 5963 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5966 */         out.write("table.heading.attribute");
/* 5967 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 5968 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5971 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 5972 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5975 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 5976 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5977 */       return true;
/*      */     }
/* 5979 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 5980 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5985 */     PageContext pageContext = _jspx_page_context;
/* 5986 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5988 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5989 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 5990 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 5991 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 5992 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 5993 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 5994 */         out = _jspx_page_context.pushBody();
/* 5995 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 5996 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5999 */         out.write("table.heading.value");
/* 6000 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 6001 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6004 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 6005 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6008 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 6009 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6010 */       return true;
/*      */     }
/* 6012 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 6013 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6018 */     PageContext pageContext = _jspx_page_context;
/* 6019 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6021 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6022 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 6023 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6024 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 6025 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 6026 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6027 */         out = _jspx_page_context.pushBody();
/* 6028 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 6029 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6032 */         out.write("table.heading.status");
/* 6033 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 6034 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6037 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 6038 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6041 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 6042 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6043 */       return true;
/*      */     }
/* 6045 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 6046 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6051 */     PageContext pageContext = _jspx_page_context;
/* 6052 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6054 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6055 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 6056 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6057 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 6058 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 6059 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6060 */         out = _jspx_page_context.pushBody();
/* 6061 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 6062 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6065 */         out.write("table.heading.attribute");
/* 6066 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 6067 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6070 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 6071 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6074 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 6075 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6076 */       return true;
/*      */     }
/* 6078 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 6079 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6084 */     PageContext pageContext = _jspx_page_context;
/* 6085 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6087 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6088 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 6089 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6090 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 6091 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 6092 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6093 */         out = _jspx_page_context.pushBody();
/* 6094 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 6095 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6098 */         out.write("table.heading.value");
/* 6099 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 6100 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6103 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 6104 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6107 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 6108 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6109 */       return true;
/*      */     }
/* 6111 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 6112 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6117 */     PageContext pageContext = _jspx_page_context;
/* 6118 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6120 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6121 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 6122 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6123 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 6124 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 6125 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6126 */         out = _jspx_page_context.pushBody();
/* 6127 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 6128 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6131 */         out.write("table.heading.status");
/* 6132 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 6133 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6136 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 6137 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6140 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 6141 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6142 */       return true;
/*      */     }
/* 6144 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 6145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6150 */     PageContext pageContext = _jspx_page_context;
/* 6151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6153 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6154 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 6155 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6157 */     _jspx_th_c_005fout_005f10.setValue("${param.resourceid}");
/* 6158 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 6159 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 6160 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6161 */       return true;
/*      */     }
/* 6163 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 6164 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6169 */     PageContext pageContext = _jspx_page_context;
/* 6170 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6172 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6173 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 6174 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6176 */     _jspx_th_c_005fout_005f11.setValue("${param.resourceid}");
/* 6177 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 6178 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 6179 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6180 */       return true;
/*      */     }
/* 6182 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 6183 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6188 */     PageContext pageContext = _jspx_page_context;
/* 6189 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6191 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6192 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 6193 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6195 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 6196 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 6197 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 6198 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6199 */       return true;
/*      */     }
/* 6201 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 6202 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6207 */     PageContext pageContext = _jspx_page_context;
/* 6208 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6210 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6211 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 6212 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6214 */     _jspx_th_c_005fout_005f13.setValue("${param.resourceid}");
/* 6215 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 6216 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 6217 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6218 */       return true;
/*      */     }
/* 6220 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 6221 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6226 */     PageContext pageContext = _jspx_page_context;
/* 6227 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6229 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6230 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 6231 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6232 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 6233 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 6234 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6235 */         out = _jspx_page_context.pushBody();
/* 6236 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 6237 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6240 */         out.write("table.heading.attribute");
/* 6241 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 6242 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6245 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 6246 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6249 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 6250 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6251 */       return true;
/*      */     }
/* 6253 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 6254 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6259 */     PageContext pageContext = _jspx_page_context;
/* 6260 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6262 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6263 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 6264 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6265 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 6266 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 6267 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6268 */         out = _jspx_page_context.pushBody();
/* 6269 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 6270 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6273 */         out.write("table.heading.value");
/* 6274 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 6275 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6278 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 6279 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6282 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 6283 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6284 */       return true;
/*      */     }
/* 6286 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 6287 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6292 */     PageContext pageContext = _jspx_page_context;
/* 6293 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6295 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6296 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 6297 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6298 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 6299 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 6300 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6301 */         out = _jspx_page_context.pushBody();
/* 6302 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 6303 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6306 */         out.write("table.heading.status");
/* 6307 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 6308 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6311 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 6312 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6315 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 6316 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6317 */       return true;
/*      */     }
/* 6319 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 6320 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6325 */     PageContext pageContext = _jspx_page_context;
/* 6326 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6328 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6329 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 6330 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6332 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 6333 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 6334 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 6335 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6336 */       return true;
/*      */     }
/* 6338 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 6339 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6344 */     PageContext pageContext = _jspx_page_context;
/* 6345 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6347 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6348 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 6349 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6351 */     _jspx_th_c_005fout_005f15.setValue("${param.resourceid}");
/* 6352 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 6353 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 6354 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6355 */       return true;
/*      */     }
/* 6357 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 6358 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6363 */     PageContext pageContext = _jspx_page_context;
/* 6364 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6366 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6367 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 6368 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6369 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 6370 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 6371 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6372 */         out = _jspx_page_context.pushBody();
/* 6373 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 6374 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6377 */         out.write("table.heading.attribute");
/* 6378 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 6379 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6382 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 6383 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6386 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 6387 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6388 */       return true;
/*      */     }
/* 6390 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 6391 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6396 */     PageContext pageContext = _jspx_page_context;
/* 6397 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6399 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6400 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 6401 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6402 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 6403 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 6404 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6405 */         out = _jspx_page_context.pushBody();
/* 6406 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 6407 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6410 */         out.write("table.heading.value");
/* 6411 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 6412 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6415 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 6416 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6419 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 6420 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6421 */       return true;
/*      */     }
/* 6423 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 6424 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6429 */     PageContext pageContext = _jspx_page_context;
/* 6430 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6432 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6433 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 6434 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6435 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 6436 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 6437 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 6438 */         out = _jspx_page_context.pushBody();
/* 6439 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 6440 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6443 */         out.write("table.heading.status");
/* 6444 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 6445 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6448 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 6449 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6452 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 6453 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 6454 */       return true;
/*      */     }
/* 6456 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 6457 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6462 */     PageContext pageContext = _jspx_page_context;
/* 6463 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6465 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6466 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 6467 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6469 */     _jspx_th_c_005fout_005f16.setValue("${param.resourceid}");
/* 6470 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 6471 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 6472 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6473 */       return true;
/*      */     }
/* 6475 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 6476 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6481 */     PageContext pageContext = _jspx_page_context;
/* 6482 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6484 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6485 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 6486 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6487 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 6488 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 6489 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 6490 */         out = _jspx_page_context.pushBody();
/* 6491 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 6492 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6495 */         out.write("table.heading.attribute");
/* 6496 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 6497 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6500 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 6501 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6504 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 6505 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 6506 */       return true;
/*      */     }
/* 6508 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 6509 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6514 */     PageContext pageContext = _jspx_page_context;
/* 6515 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6517 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6518 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 6519 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6520 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 6521 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 6522 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 6523 */         out = _jspx_page_context.pushBody();
/* 6524 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 6525 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6528 */         out.write("table.heading.value");
/* 6529 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 6530 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6533 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 6534 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6537 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 6538 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 6539 */       return true;
/*      */     }
/* 6541 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 6542 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6547 */     PageContext pageContext = _jspx_page_context;
/* 6548 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6550 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6551 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 6552 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6553 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 6554 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 6555 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 6556 */         out = _jspx_page_context.pushBody();
/* 6557 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 6558 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6561 */         out.write("table.heading.status");
/* 6562 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 6563 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6566 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 6567 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6570 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 6571 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 6572 */       return true;
/*      */     }
/* 6574 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 6575 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f18(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6580 */     PageContext pageContext = _jspx_page_context;
/* 6581 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6583 */     MessageTag _jspx_th_fmt_005fmessage_005f18 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6584 */     _jspx_th_fmt_005fmessage_005f18.setPageContext(_jspx_page_context);
/* 6585 */     _jspx_th_fmt_005fmessage_005f18.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6586 */     int _jspx_eval_fmt_005fmessage_005f18 = _jspx_th_fmt_005fmessage_005f18.doStartTag();
/* 6587 */     if (_jspx_eval_fmt_005fmessage_005f18 != 0) {
/* 6588 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 6589 */         out = _jspx_page_context.pushBody();
/* 6590 */         _jspx_th_fmt_005fmessage_005f18.setBodyContent((BodyContent)out);
/* 6591 */         _jspx_th_fmt_005fmessage_005f18.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6594 */         out.write("table.heading.attribute");
/* 6595 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f18.doAfterBody();
/* 6596 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6599 */       if (_jspx_eval_fmt_005fmessage_005f18 != 1) {
/* 6600 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6603 */     if (_jspx_th_fmt_005fmessage_005f18.doEndTag() == 5) {
/* 6604 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 6605 */       return true;
/*      */     }
/* 6607 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f18);
/* 6608 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f19(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6613 */     PageContext pageContext = _jspx_page_context;
/* 6614 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6616 */     MessageTag _jspx_th_fmt_005fmessage_005f19 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6617 */     _jspx_th_fmt_005fmessage_005f19.setPageContext(_jspx_page_context);
/* 6618 */     _jspx_th_fmt_005fmessage_005f19.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6619 */     int _jspx_eval_fmt_005fmessage_005f19 = _jspx_th_fmt_005fmessage_005f19.doStartTag();
/* 6620 */     if (_jspx_eval_fmt_005fmessage_005f19 != 0) {
/* 6621 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 6622 */         out = _jspx_page_context.pushBody();
/* 6623 */         _jspx_th_fmt_005fmessage_005f19.setBodyContent((BodyContent)out);
/* 6624 */         _jspx_th_fmt_005fmessage_005f19.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6627 */         out.write("table.heading.value");
/* 6628 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f19.doAfterBody();
/* 6629 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6632 */       if (_jspx_eval_fmt_005fmessage_005f19 != 1) {
/* 6633 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6636 */     if (_jspx_th_fmt_005fmessage_005f19.doEndTag() == 5) {
/* 6637 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 6638 */       return true;
/*      */     }
/* 6640 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f19);
/* 6641 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f20(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6646 */     PageContext pageContext = _jspx_page_context;
/* 6647 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6649 */     MessageTag _jspx_th_fmt_005fmessage_005f20 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6650 */     _jspx_th_fmt_005fmessage_005f20.setPageContext(_jspx_page_context);
/* 6651 */     _jspx_th_fmt_005fmessage_005f20.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6652 */     int _jspx_eval_fmt_005fmessage_005f20 = _jspx_th_fmt_005fmessage_005f20.doStartTag();
/* 6653 */     if (_jspx_eval_fmt_005fmessage_005f20 != 0) {
/* 6654 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 6655 */         out = _jspx_page_context.pushBody();
/* 6656 */         _jspx_th_fmt_005fmessage_005f20.setBodyContent((BodyContent)out);
/* 6657 */         _jspx_th_fmt_005fmessage_005f20.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6660 */         out.write("table.heading.status");
/* 6661 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f20.doAfterBody();
/* 6662 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6665 */       if (_jspx_eval_fmt_005fmessage_005f20 != 1) {
/* 6666 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6669 */     if (_jspx_th_fmt_005fmessage_005f20.doEndTag() == 5) {
/* 6670 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 6671 */       return true;
/*      */     }
/* 6673 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f20);
/* 6674 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6679 */     PageContext pageContext = _jspx_page_context;
/* 6680 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6682 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6683 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 6684 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6686 */     _jspx_th_c_005fout_005f17.setValue("${param.resourceid}");
/* 6687 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 6688 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 6689 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6690 */       return true;
/*      */     }
/* 6692 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 6693 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6698 */     PageContext pageContext = _jspx_page_context;
/* 6699 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6701 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6702 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 6703 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6705 */     _jspx_th_c_005fout_005f18.setValue("${param.resourceid}");
/* 6706 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 6707 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 6708 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6709 */       return true;
/*      */     }
/* 6711 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 6712 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f21(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6717 */     PageContext pageContext = _jspx_page_context;
/* 6718 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6720 */     MessageTag _jspx_th_fmt_005fmessage_005f21 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6721 */     _jspx_th_fmt_005fmessage_005f21.setPageContext(_jspx_page_context);
/* 6722 */     _jspx_th_fmt_005fmessage_005f21.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/* 6723 */     int _jspx_eval_fmt_005fmessage_005f21 = _jspx_th_fmt_005fmessage_005f21.doStartTag();
/* 6724 */     if (_jspx_eval_fmt_005fmessage_005f21 != 0) {
/* 6725 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 6726 */         out = _jspx_page_context.pushBody();
/* 6727 */         _jspx_th_fmt_005fmessage_005f21.setBodyContent((BodyContent)out);
/* 6728 */         _jspx_th_fmt_005fmessage_005f21.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6731 */         out.write("table.heading.attribute");
/* 6732 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f21.doAfterBody();
/* 6733 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6736 */       if (_jspx_eval_fmt_005fmessage_005f21 != 1) {
/* 6737 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6740 */     if (_jspx_th_fmt_005fmessage_005f21.doEndTag() == 5) {
/* 6741 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 6742 */       return true;
/*      */     }
/* 6744 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f21);
/* 6745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f22(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6750 */     PageContext pageContext = _jspx_page_context;
/* 6751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6753 */     MessageTag _jspx_th_fmt_005fmessage_005f22 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6754 */     _jspx_th_fmt_005fmessage_005f22.setPageContext(_jspx_page_context);
/* 6755 */     _jspx_th_fmt_005fmessage_005f22.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/* 6756 */     int _jspx_eval_fmt_005fmessage_005f22 = _jspx_th_fmt_005fmessage_005f22.doStartTag();
/* 6757 */     if (_jspx_eval_fmt_005fmessage_005f22 != 0) {
/* 6758 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 6759 */         out = _jspx_page_context.pushBody();
/* 6760 */         _jspx_th_fmt_005fmessage_005f22.setBodyContent((BodyContent)out);
/* 6761 */         _jspx_th_fmt_005fmessage_005f22.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6764 */         out.write("table.heading.value");
/* 6765 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f22.doAfterBody();
/* 6766 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6769 */       if (_jspx_eval_fmt_005fmessage_005f22 != 1) {
/* 6770 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6773 */     if (_jspx_th_fmt_005fmessage_005f22.doEndTag() == 5) {
/* 6774 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 6775 */       return true;
/*      */     }
/* 6777 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f22);
/* 6778 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f23(JspTag _jspx_th_logic_005fiterate_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6783 */     PageContext pageContext = _jspx_page_context;
/* 6784 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6786 */     MessageTag _jspx_th_fmt_005fmessage_005f23 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6787 */     _jspx_th_fmt_005fmessage_005f23.setPageContext(_jspx_page_context);
/* 6788 */     _jspx_th_fmt_005fmessage_005f23.setParent((Tag)_jspx_th_logic_005fiterate_005f2);
/* 6789 */     int _jspx_eval_fmt_005fmessage_005f23 = _jspx_th_fmt_005fmessage_005f23.doStartTag();
/* 6790 */     if (_jspx_eval_fmt_005fmessage_005f23 != 0) {
/* 6791 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 6792 */         out = _jspx_page_context.pushBody();
/* 6793 */         _jspx_th_fmt_005fmessage_005f23.setBodyContent((BodyContent)out);
/* 6794 */         _jspx_th_fmt_005fmessage_005f23.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6797 */         out.write("table.heading.status");
/* 6798 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f23.doAfterBody();
/* 6799 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6802 */       if (_jspx_eval_fmt_005fmessage_005f23 != 1) {
/* 6803 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6806 */     if (_jspx_th_fmt_005fmessage_005f23.doEndTag() == 5) {
/* 6807 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 6808 */       return true;
/*      */     }
/* 6810 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f23);
/* 6811 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6816 */     PageContext pageContext = _jspx_page_context;
/* 6817 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6819 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6820 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 6821 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6823 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 6824 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 6825 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 6826 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6827 */       return true;
/*      */     }
/* 6829 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 6830 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6835 */     PageContext pageContext = _jspx_page_context;
/* 6836 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6838 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6839 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 6840 */     _jspx_th_c_005fout_005f20.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6842 */     _jspx_th_c_005fout_005f20.setValue("${param.resourceid}");
/* 6843 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 6844 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 6845 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6846 */       return true;
/*      */     }
/* 6848 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 6849 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f24(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6854 */     PageContext pageContext = _jspx_page_context;
/* 6855 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6857 */     MessageTag _jspx_th_fmt_005fmessage_005f24 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6858 */     _jspx_th_fmt_005fmessage_005f24.setPageContext(_jspx_page_context);
/* 6859 */     _jspx_th_fmt_005fmessage_005f24.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6860 */     int _jspx_eval_fmt_005fmessage_005f24 = _jspx_th_fmt_005fmessage_005f24.doStartTag();
/* 6861 */     if (_jspx_eval_fmt_005fmessage_005f24 != 0) {
/* 6862 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 6863 */         out = _jspx_page_context.pushBody();
/* 6864 */         _jspx_th_fmt_005fmessage_005f24.setBodyContent((BodyContent)out);
/* 6865 */         _jspx_th_fmt_005fmessage_005f24.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6868 */         out.write("table.heading.attribute");
/* 6869 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f24.doAfterBody();
/* 6870 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6873 */       if (_jspx_eval_fmt_005fmessage_005f24 != 1) {
/* 6874 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6877 */     if (_jspx_th_fmt_005fmessage_005f24.doEndTag() == 5) {
/* 6878 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 6879 */       return true;
/*      */     }
/* 6881 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f24);
/* 6882 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f25(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6887 */     PageContext pageContext = _jspx_page_context;
/* 6888 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6890 */     MessageTag _jspx_th_fmt_005fmessage_005f25 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6891 */     _jspx_th_fmt_005fmessage_005f25.setPageContext(_jspx_page_context);
/* 6892 */     _jspx_th_fmt_005fmessage_005f25.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6893 */     int _jspx_eval_fmt_005fmessage_005f25 = _jspx_th_fmt_005fmessage_005f25.doStartTag();
/* 6894 */     if (_jspx_eval_fmt_005fmessage_005f25 != 0) {
/* 6895 */       if (_jspx_eval_fmt_005fmessage_005f25 != 1) {
/* 6896 */         out = _jspx_page_context.pushBody();
/* 6897 */         _jspx_th_fmt_005fmessage_005f25.setBodyContent((BodyContent)out);
/* 6898 */         _jspx_th_fmt_005fmessage_005f25.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6901 */         out.write("table.heading.value");
/* 6902 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f25.doAfterBody();
/* 6903 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6906 */       if (_jspx_eval_fmt_005fmessage_005f25 != 1) {
/* 6907 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6910 */     if (_jspx_th_fmt_005fmessage_005f25.doEndTag() == 5) {
/* 6911 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 6912 */       return true;
/*      */     }
/* 6914 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f25);
/* 6915 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f26(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6920 */     PageContext pageContext = _jspx_page_context;
/* 6921 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6923 */     MessageTag _jspx_th_fmt_005fmessage_005f26 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6924 */     _jspx_th_fmt_005fmessage_005f26.setPageContext(_jspx_page_context);
/* 6925 */     _jspx_th_fmt_005fmessage_005f26.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6926 */     int _jspx_eval_fmt_005fmessage_005f26 = _jspx_th_fmt_005fmessage_005f26.doStartTag();
/* 6927 */     if (_jspx_eval_fmt_005fmessage_005f26 != 0) {
/* 6928 */       if (_jspx_eval_fmt_005fmessage_005f26 != 1) {
/* 6929 */         out = _jspx_page_context.pushBody();
/* 6930 */         _jspx_th_fmt_005fmessage_005f26.setBodyContent((BodyContent)out);
/* 6931 */         _jspx_th_fmt_005fmessage_005f26.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 6934 */         out.write("table.heading.status");
/* 6935 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f26.doAfterBody();
/* 6936 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 6939 */       if (_jspx_eval_fmt_005fmessage_005f26 != 1) {
/* 6940 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 6943 */     if (_jspx_th_fmt_005fmessage_005f26.doEndTag() == 5) {
/* 6944 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 6945 */       return true;
/*      */     }
/* 6947 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f26);
/* 6948 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6953 */     PageContext pageContext = _jspx_page_context;
/* 6954 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6956 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6957 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 6958 */     _jspx_th_c_005fout_005f21.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6960 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 6961 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 6962 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 6963 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6964 */       return true;
/*      */     }
/* 6966 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 6967 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6972 */     PageContext pageContext = _jspx_page_context;
/* 6973 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6975 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 6976 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 6977 */     _jspx_th_c_005fout_005f22.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 6979 */     _jspx_th_c_005fout_005f22.setValue("${param.resourceid}");
/* 6980 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 6981 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 6982 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6983 */       return true;
/*      */     }
/* 6985 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 6986 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f27(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 6991 */     PageContext pageContext = _jspx_page_context;
/* 6992 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 6994 */     MessageTag _jspx_th_fmt_005fmessage_005f27 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 6995 */     _jspx_th_fmt_005fmessage_005f27.setPageContext(_jspx_page_context);
/* 6996 */     _jspx_th_fmt_005fmessage_005f27.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 6997 */     int _jspx_eval_fmt_005fmessage_005f27 = _jspx_th_fmt_005fmessage_005f27.doStartTag();
/* 6998 */     if (_jspx_eval_fmt_005fmessage_005f27 != 0) {
/* 6999 */       if (_jspx_eval_fmt_005fmessage_005f27 != 1) {
/* 7000 */         out = _jspx_page_context.pushBody();
/* 7001 */         _jspx_th_fmt_005fmessage_005f27.setBodyContent((BodyContent)out);
/* 7002 */         _jspx_th_fmt_005fmessage_005f27.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7005 */         out.write("table.heading.attribute");
/* 7006 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f27.doAfterBody();
/* 7007 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7010 */       if (_jspx_eval_fmt_005fmessage_005f27 != 1) {
/* 7011 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7014 */     if (_jspx_th_fmt_005fmessage_005f27.doEndTag() == 5) {
/* 7015 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 7016 */       return true;
/*      */     }
/* 7018 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f27);
/* 7019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f28(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7024 */     PageContext pageContext = _jspx_page_context;
/* 7025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7027 */     MessageTag _jspx_th_fmt_005fmessage_005f28 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7028 */     _jspx_th_fmt_005fmessage_005f28.setPageContext(_jspx_page_context);
/* 7029 */     _jspx_th_fmt_005fmessage_005f28.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 7030 */     int _jspx_eval_fmt_005fmessage_005f28 = _jspx_th_fmt_005fmessage_005f28.doStartTag();
/* 7031 */     if (_jspx_eval_fmt_005fmessage_005f28 != 0) {
/* 7032 */       if (_jspx_eval_fmt_005fmessage_005f28 != 1) {
/* 7033 */         out = _jspx_page_context.pushBody();
/* 7034 */         _jspx_th_fmt_005fmessage_005f28.setBodyContent((BodyContent)out);
/* 7035 */         _jspx_th_fmt_005fmessage_005f28.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7038 */         out.write("table.heading.value");
/* 7039 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f28.doAfterBody();
/* 7040 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7043 */       if (_jspx_eval_fmt_005fmessage_005f28 != 1) {
/* 7044 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7047 */     if (_jspx_th_fmt_005fmessage_005f28.doEndTag() == 5) {
/* 7048 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 7049 */       return true;
/*      */     }
/* 7051 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f28);
/* 7052 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f29(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7057 */     PageContext pageContext = _jspx_page_context;
/* 7058 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7060 */     MessageTag _jspx_th_fmt_005fmessage_005f29 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7061 */     _jspx_th_fmt_005fmessage_005f29.setPageContext(_jspx_page_context);
/* 7062 */     _jspx_th_fmt_005fmessage_005f29.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 7063 */     int _jspx_eval_fmt_005fmessage_005f29 = _jspx_th_fmt_005fmessage_005f29.doStartTag();
/* 7064 */     if (_jspx_eval_fmt_005fmessage_005f29 != 0) {
/* 7065 */       if (_jspx_eval_fmt_005fmessage_005f29 != 1) {
/* 7066 */         out = _jspx_page_context.pushBody();
/* 7067 */         _jspx_th_fmt_005fmessage_005f29.setBodyContent((BodyContent)out);
/* 7068 */         _jspx_th_fmt_005fmessage_005f29.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7071 */         out.write("table.heading.status");
/* 7072 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f29.doAfterBody();
/* 7073 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7076 */       if (_jspx_eval_fmt_005fmessage_005f29 != 1) {
/* 7077 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7080 */     if (_jspx_th_fmt_005fmessage_005f29.doEndTag() == 5) {
/* 7081 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 7082 */       return true;
/*      */     }
/* 7084 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f29);
/* 7085 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f30(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7090 */     PageContext pageContext = _jspx_page_context;
/* 7091 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7093 */     MessageTag _jspx_th_fmt_005fmessage_005f30 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7094 */     _jspx_th_fmt_005fmessage_005f30.setPageContext(_jspx_page_context);
/* 7095 */     _jspx_th_fmt_005fmessage_005f30.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 7096 */     int _jspx_eval_fmt_005fmessage_005f30 = _jspx_th_fmt_005fmessage_005f30.doStartTag();
/* 7097 */     if (_jspx_eval_fmt_005fmessage_005f30 != 0) {
/* 7098 */       if (_jspx_eval_fmt_005fmessage_005f30 != 1) {
/* 7099 */         out = _jspx_page_context.pushBody();
/* 7100 */         _jspx_th_fmt_005fmessage_005f30.setBodyContent((BodyContent)out);
/* 7101 */         _jspx_th_fmt_005fmessage_005f30.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7104 */         out.write("table.heading.attribute");
/* 7105 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f30.doAfterBody();
/* 7106 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7109 */       if (_jspx_eval_fmt_005fmessage_005f30 != 1) {
/* 7110 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7113 */     if (_jspx_th_fmt_005fmessage_005f30.doEndTag() == 5) {
/* 7114 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 7115 */       return true;
/*      */     }
/* 7117 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f30);
/* 7118 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f31(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7123 */     PageContext pageContext = _jspx_page_context;
/* 7124 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7126 */     MessageTag _jspx_th_fmt_005fmessage_005f31 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7127 */     _jspx_th_fmt_005fmessage_005f31.setPageContext(_jspx_page_context);
/* 7128 */     _jspx_th_fmt_005fmessage_005f31.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 7129 */     int _jspx_eval_fmt_005fmessage_005f31 = _jspx_th_fmt_005fmessage_005f31.doStartTag();
/* 7130 */     if (_jspx_eval_fmt_005fmessage_005f31 != 0) {
/* 7131 */       if (_jspx_eval_fmt_005fmessage_005f31 != 1) {
/* 7132 */         out = _jspx_page_context.pushBody();
/* 7133 */         _jspx_th_fmt_005fmessage_005f31.setBodyContent((BodyContent)out);
/* 7134 */         _jspx_th_fmt_005fmessage_005f31.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7137 */         out.write("table.heading.value");
/* 7138 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f31.doAfterBody();
/* 7139 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7142 */       if (_jspx_eval_fmt_005fmessage_005f31 != 1) {
/* 7143 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7146 */     if (_jspx_th_fmt_005fmessage_005f31.doEndTag() == 5) {
/* 7147 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 7148 */       return true;
/*      */     }
/* 7150 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f31);
/* 7151 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f32(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 7156 */     PageContext pageContext = _jspx_page_context;
/* 7157 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 7159 */     MessageTag _jspx_th_fmt_005fmessage_005f32 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 7160 */     _jspx_th_fmt_005fmessage_005f32.setPageContext(_jspx_page_context);
/* 7161 */     _jspx_th_fmt_005fmessage_005f32.setParent((Tag)_jspx_th_c_005fif_005f2);
/* 7162 */     int _jspx_eval_fmt_005fmessage_005f32 = _jspx_th_fmt_005fmessage_005f32.doStartTag();
/* 7163 */     if (_jspx_eval_fmt_005fmessage_005f32 != 0) {
/* 7164 */       if (_jspx_eval_fmt_005fmessage_005f32 != 1) {
/* 7165 */         out = _jspx_page_context.pushBody();
/* 7166 */         _jspx_th_fmt_005fmessage_005f32.setBodyContent((BodyContent)out);
/* 7167 */         _jspx_th_fmt_005fmessage_005f32.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 7170 */         out.write("table.heading.status");
/* 7171 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f32.doAfterBody();
/* 7172 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 7175 */       if (_jspx_eval_fmt_005fmessage_005f32 != 1) {
/* 7176 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 7179 */     if (_jspx_th_fmt_005fmessage_005f32.doEndTag() == 5) {
/* 7180 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 7181 */       return true;
/*      */     }
/* 7183 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f32);
/* 7184 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\MySqlOverview_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */