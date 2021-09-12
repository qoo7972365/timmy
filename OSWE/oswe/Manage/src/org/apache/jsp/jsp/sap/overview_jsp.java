/*      */ package org.apache.jsp.jsp.sap;
/*      */ 
/*      */ import com.adventnet.appmanager.client.sap.SAPGraph;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
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
/*      */ import org.apache.struts.taglib.logic.EmptyTag;
/*      */ import org.apache.struts.taglib.logic.NotEmptyTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class overview_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/* 1471 */       message = com.adventnet.appmanager.util.EnterpriseUtil.decodeString(message);
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
/* 1982 */       if ((com.adventnet.appmanager.util.EnterpriseUtil.isAdminServer()) && (mgIDint > com.adventnet.appmanager.util.EnterpriseUtil.RANGE))
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
/* 2181 */   private static Map<String, Long> _jspx_dependants = new HashMap(3);
/* 2182 */   static { _jspx_dependants.put("/jsp/MyField_div.jsp", Long.valueOf(1473429417000L));
/* 2183 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/* 2184 */     _jspx_dependants.put("/jsp/MyField_trstrip.jsp", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fempty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2204 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2208 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2221 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2225 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.release();
/* 2227 */     this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2231 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/* 2232 */     this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.release();
/* 2233 */     this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.release();
/* 2234 */     this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.release();
/* 2235 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.release();
/* 2236 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2243 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2246 */     JspWriter out = null;
/* 2247 */     Object page = this;
/* 2248 */     JspWriter _jspx_out = null;
/* 2249 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2253 */       response.setContentType("text/html;charset=UTF-8");
/* 2254 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2256 */       _jspx_page_context = pageContext;
/* 2257 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2258 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2259 */       session = pageContext.getSession();
/* 2260 */       out = pageContext.getOut();
/* 2261 */       _jspx_out = out;
/*      */       
/* 2263 */       out.write("<!-- $Id$-->\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2264 */       SAPGraph sapGraph = null;
/* 2265 */       sapGraph = (SAPGraph)_jspx_page_context.getAttribute("sapGraph", 2);
/* 2266 */       if (sapGraph == null) {
/* 2267 */         sapGraph = new SAPGraph();
/* 2268 */         _jspx_page_context.setAttribute("sapGraph", sapGraph, 2);
/*      */       }
/* 2270 */       out.write(10);
/* 2271 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2272 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2273 */       if (wlsGraph == null) {
/* 2274 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2275 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2277 */       out.write(10);
/* 2278 */       DialChartSupport dialGraph = null;
/* 2279 */       dialGraph = (DialChartSupport)_jspx_page_context.getAttribute("dialGraph", 1);
/* 2280 */       if (dialGraph == null) {
/* 2281 */         dialGraph = new DialChartSupport();
/* 2282 */         _jspx_page_context.setAttribute("dialGraph", dialGraph, 1);
/*      */       }
/* 2284 */       out.write(10);
/* 2285 */       out.write(10);
/* 2286 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2288 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2290 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2292 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2294 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2296 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2298 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2299 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2300 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2301 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2304 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2305 */         String available = null;
/* 2306 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2307 */         out.write(10);
/*      */         
/* 2309 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2311 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2313 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2315 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2317 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2319 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2320 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2321 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2322 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2325 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2326 */           String unavailable = null;
/* 2327 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2328 */           out.write(10);
/*      */           
/* 2330 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2332 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2334 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2336 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2338 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2340 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2341 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2342 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2343 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2346 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2347 */             String unmanaged = null;
/* 2348 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2349 */             out.write(10);
/*      */             
/* 2351 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2353 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2355 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2357 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2359 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2361 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2362 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2363 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2364 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2367 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2368 */               String scheduled = null;
/* 2369 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2370 */               out.write(10);
/*      */               
/* 2372 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2374 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2376 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2378 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2380 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2382 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2383 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2384 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2385 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2388 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2389 */                 String critical = null;
/* 2390 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2391 */                 out.write(10);
/*      */                 
/* 2393 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2395 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2397 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2399 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2401 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2403 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2404 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2405 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2406 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2409 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2410 */                   String clear = null;
/* 2411 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2412 */                   out.write(10);
/*      */                   
/* 2414 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2416 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2418 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2420 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2422 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2424 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2425 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2426 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2427 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2430 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2431 */                     String warning = null;
/* 2432 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2433 */                     out.write(10);
/* 2434 */                     out.write(10);
/*      */                     
/* 2436 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2437 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2439 */                     out.write(10);
/* 2440 */                     out.write(10);
/* 2441 */                     out.write(10);
/* 2442 */                     out.write(10);
/* 2443 */                     out.write(10);
/*      */                     
/* 2445 */                     String resourceid = request.getParameter("resourceid");
/* 2446 */                     String displayname = (String)request.getAttribute("displayname");
/* 2447 */                     String cpu = (String)request.getAttribute("CPU");
/* 2448 */                     String memory = (String)request.getAttribute("Memory");
/* 2449 */                     String disk = (String)request.getAttribute("Disk");
/* 2450 */                     String programresid = (String)request.getAttribute("ProgramResourceID");
/* 2451 */                     String hitratio = (String)request.getAttribute("HitRatio");
/* 2452 */                     String spaceused = (String)request.getAttribute("SpaceUsed");
/* 2453 */                     String directoryused = (String)request.getAttribute("DirectoryUsed");
/* 2454 */                     String spoolutilization = (String)request.getAttribute("spoolutilization");
/* 2455 */                     String backgroundutilization = (String)request.getAttribute("backgroundutilization");
/*      */                     
/*      */ 
/* 2458 */                     String seven_days_text = FormatUtil.getString("am.webclient.common.sevendays.tooltip.text");
/* 2459 */                     String thiry_days_text = FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text");
/* 2460 */                     String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2461 */                     String yaxis_utilization = FormatUtil.getString("UTILIZATION");
/* 2462 */                     String yaxis_enqueuerequests = FormatUtil.getString("ENQUEUEREQUESTS");
/* 2463 */                     String yaxis_frontendresponsetime = FormatUtil.getString("am.webclient.support.timeinms.text");
/* 2464 */                     String yaxis_esact = FormatUtil.getString("ESACT");
/* 2465 */                     String yaxis_pagingrate = FormatUtil.getString("Pages per second");
/* 2466 */                     String yaxis_syslograte = FormatUtil.getString("SYSLOGRATE");
/* 2467 */                     String yaxis_hitratio = FormatUtil.getString("HITRATIO");
/* 2468 */                     String yaxis_dirused = FormatUtil.getString("DIRECTORYUSED");
/* 2469 */                     String yaxis_spaceused = FormatUtil.getString("SPACEUSED");
/* 2470 */                     String yaxis_syslogfreq = FormatUtil.getString("Logs per minute");
/*      */                     
/* 2472 */                     ArrayList resIDs = (ArrayList)request.getAttribute("resIDs");
/* 2473 */                     ArrayList attribIDs = new ArrayList();
/* 2474 */                     for (int i = 3702; i < 3759; i++)
/*      */                     {
/* 2476 */                       attribIDs.add("" + i);
/*      */                     }
/*      */                     
/* 2479 */                     Properties alert = getStatus(resIDs, attribIDs);
/*      */                     
/* 2481 */                     String encodeurl = java.net.URLEncoder.encode("/showresource.do?method=showResourceForResourceID&resourceid=" + resourceid);
/*      */                     
/* 2483 */                     wlsGraph.setParam(resourceid, "AVAILABILITY");
/* 2484 */                     HashMap systeminfo = (HashMap)request.getAttribute("systeminfo");
/*      */                     
/* 2486 */                     String oscolstate = (String)request.getAttribute("OSCOLState");
/* 2487 */                     if (oscolstate == null)
/*      */                     {
/* 2489 */                       oscolstate = "true";
/*      */                     }
/*      */                     
/*      */ 
/* 2493 */                     out.write("\n<br>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td width=\"55%\" valign=\"top\">\n\t<table width=\"98%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" align=\"left\" class=\"tableheadingbborder\">");
/* 2494 */                     out.print(FormatUtil.getString("am.webclient.common.monitorinformation.text"));
/* 2495 */                     out.write("</td>\n\t</tr>\n\t<tr>\n            <td width=\"30%\" class=\"monitorinfoodd\">");
/* 2496 */                     out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2497 */                     out.write("</td>\n            <td width=\"70%\" class=\"monitorinfoodd\" title=\"");
/* 2498 */                     out.print(displayname);
/* 2499 */                     out.write(34);
/* 2500 */                     out.write(62);
/* 2501 */                     out.print(getTrimmedText(displayname, 40));
/* 2502 */                     out.write("</td>\n\t</tr>\n        <tr>\n            <td class=\"monitorinfoeven\" valign=\"top\">");
/* 2503 */                     out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2504 */                     out.write("</td>\n            <td class=\"monitorinfoeven\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2505 */                     out.print(resourceid);
/* 2506 */                     out.write("&attributeid=3703')\">");
/* 2507 */                     out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + "3703")));
/* 2508 */                     out.write("</a>\n\t\t   ");
/* 2509 */                     out.print(getHideAndShowRCAMessage(alert.getProperty(resourceid + "#" + "3703" + "#" + "MESSAGE"), "3703", alert.getProperty(resourceid + "#" + "3703"), resourceid));
/* 2510 */                     out.write("\n\t\t    ");
/* 2511 */                     if (com.adventnet.appmanager.util.ReportDataUtilities.currentStatus(resourceid, "3703") != 0) {
/* 2512 */                       out.write("\n\t\t   <br>\n                 <span style=\"float: right;\"><a class=\"staticlinks\" href=\"javascript:void(0)\" onClick=\"window.open('fault/AlarmDetails.do?method=traversePage&tab=tabOne&entity=");
/* 2513 */                       out.print(resourceid + "_3703");
/* 2514 */                       out.write("&monitortype=SAP')\">");
/* 2515 */                       out.print(FormatUtil.getString("webclient.fault.alarmdetails.operations.events"));
/* 2516 */                       out.write("</a></span>\n           ");
/*      */                     }
/* 2518 */                     out.write("\n            </td>\n\t</tr>\n\t<tr>\n            <td class=\"monitorinfoodd\">");
/* 2519 */                     out.print(FormatUtil.getString("am.webclient.common.type.text"));
/* 2520 */                     out.write(" </td>\n            <td class=\"monitorinfoodd\">");
/* 2521 */                     out.print(FormatUtil.getString("am.webclient.sap.server.type"));
/* 2522 */                     out.write("</td>\n\t</tr>\n        ");
/*      */                     
/* 2524 */                     EmptyTag _jspx_th_logic_005fempty_005f0 = (EmptyTag)this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.get(EmptyTag.class);
/* 2525 */                     _jspx_th_logic_005fempty_005f0.setPageContext(_jspx_page_context);
/* 2526 */                     _jspx_th_logic_005fempty_005f0.setParent(null);
/*      */                     
/* 2528 */                     _jspx_th_logic_005fempty_005f0.setName("systeminfo");
/* 2529 */                     int _jspx_eval_logic_005fempty_005f0 = _jspx_th_logic_005fempty_005f0.doStartTag();
/* 2530 */                     if (_jspx_eval_logic_005fempty_005f0 != 0) {
/*      */                       for (;;) {
/* 2532 */                         out.write("\n\t<tr>\n            <td class=\"monitorinfoeven\">");
/* 2533 */                         out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2534 */                         out.write("</td>\n            <td class=\"monitorinfoeven\">-&nbsp;</td>\n\t</tr>\n\t<tr>\n            <td class=\"monitorinfoodd\">");
/* 2535 */                         out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2536 */                         out.write("</td>\n            <td class=\"monitorinfoodd\">-</td>\n\t</tr>\n\t<tr>\n            <td class=\"monitorinfoeven\">");
/* 2537 */                         out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2538 */                         out.write("</td>\n            <td class=\"monitorinfoeven\">-</td>\n\t</tr>\n\t<tr>\n            <td class=\"monitorinfoodd\">");
/* 2539 */                         out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2540 */                         out.write("</td>\n            <td class=\"monitorinfoodd\">-</td>\n\t</tr>\n\t");
/* 2541 */                         int evalDoAfterBody = _jspx_th_logic_005fempty_005f0.doAfterBody();
/* 2542 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2546 */                     if (_jspx_th_logic_005fempty_005f0.doEndTag() == 5) {
/* 2547 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/*      */                     }
/*      */                     else {
/* 2550 */                       this._005fjspx_005ftagPool_005flogic_005fempty_0026_005fname.reuse(_jspx_th_logic_005fempty_005f0);
/* 2551 */                       out.write(10);
/* 2552 */                       out.write(9);
/*      */                       
/* 2554 */                       NotEmptyTag _jspx_th_logic_005fnotEmpty_005f0 = (NotEmptyTag)this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.get(NotEmptyTag.class);
/* 2555 */                       _jspx_th_logic_005fnotEmpty_005f0.setPageContext(_jspx_page_context);
/* 2556 */                       _jspx_th_logic_005fnotEmpty_005f0.setParent(null);
/*      */                       
/* 2558 */                       _jspx_th_logic_005fnotEmpty_005f0.setName("systeminfo");
/* 2559 */                       int _jspx_eval_logic_005fnotEmpty_005f0 = _jspx_th_logic_005fnotEmpty_005f0.doStartTag();
/* 2560 */                       if (_jspx_eval_logic_005fnotEmpty_005f0 != 0) {
/*      */                         for (;;) {
/* 2562 */                           out.write("\n\t<tr>\n            <td class=\"monitorinfoeven\">");
/* 2563 */                           out.print(FormatUtil.getString("am.webclient.common.hostname.text"));
/* 2564 */                           out.write("</td>\n            <td class=\"monitorinfoeven\"><a href=\"showresource.do?resourceid=");
/* 2565 */                           out.print(systeminfo.get("host_resid"));
/* 2566 */                           out.write("&method=showResourceForResourceID\" class=\"staticlinks\">");
/* 2567 */                           out.print(getTrimmedText((String)systeminfo.get("HOSTNAME"), 26));
/* 2568 */                           out.write("&nbsp;(");
/* 2569 */                           out.print(systeminfo.get("HOSTIP"));
/* 2570 */                           out.write(")</a></td>\n\t</tr>\n\t<tr>\n            <td class=\"monitorinfoodd\">");
/* 2571 */                           out.print(FormatUtil.getString("am.webclient.common.hostos.text"));
/* 2572 */                           out.write("</td>\n            <td class=\"monitorinfoodd\">");
/* 2573 */                           out.print(FormatUtil.getString((String)systeminfo.get("HOSTOS")));
/* 2574 */                           out.write("</td>\n\t</tr>\n\t<tr>\n\t    <td class=\"monitorinfoeven\">");
/* 2575 */                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2576 */                           out.write("</td>\n\t    <td class=\"monitorinfoeven\">");
/* 2577 */                           out.print(request.getAttribute("LASTDC"));
/* 2578 */                           out.write("</td>\n\t</tr>\n\t<tr>\n\t    <td class=\"monitorinfoodd\">");
/* 2579 */                           out.print(FormatUtil.getString("am.webclient.common.nextpollat.text"));
/* 2580 */                           out.write("</td>\n\t    <td class=\"monitorinfoodd\">");
/* 2581 */                           out.print(request.getAttribute("NEXTDC"));
/* 2582 */                           out.write("</td>\n\t</tr>\n        ");
/* 2583 */                           int evalDoAfterBody = _jspx_th_logic_005fnotEmpty_005f0.doAfterBody();
/* 2584 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2588 */                       if (_jspx_th_logic_005fnotEmpty_005f0.doEndTag() == 5) {
/* 2589 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/*      */                       }
/*      */                       else {
/* 2592 */                         this._005fjspx_005ftagPool_005flogic_005fnotEmpty_0026_005fname.reuse(_jspx_th_logic_005fnotEmpty_005f0);
/* 2593 */                         out.write("\n        ");
/* 2594 */                         out.write("<!--$Id$-->\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"/template/customfield.js\"></SCRIPT>\n<script>\n $(document).ready(function(){\n\n\tvar customFieldsHash = document.location.hash;\n\n\tcustomFieldsHash = customFieldsHash.split(\"/\")\n\n\tif(customFieldsHash.length > 1)\t");
/* 2595 */                         out.write("\n\t{\n\t\t");
/* 2596 */                         if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                           return;
/* 2598 */                         out.write(10);
/* 2599 */                         out.write(9);
/* 2600 */                         out.write(9);
/* 2601 */                         if (_jspx_meth_c_005fif_005f1(_jspx_page_context))
/*      */                           return;
/* 2603 */                         out.write("\n\t\tgetCustomFields('");
/* 2604 */                         if (_jspx_meth_c_005fout_005f2(_jspx_page_context))
/*      */                           return;
/* 2606 */                         out.write("','noalarms',false,customFieldsHash[1],true)\t");
/* 2607 */                         out.write("\n\t}\n\n});\n</script>\n");
/* 2608 */                         if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */                           return;
/* 2610 */                         out.write(10);
/* 2611 */                         out.write(10);
/* 2612 */                         if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                           return;
/* 2614 */                         out.write(10);
/* 2615 */                         out.write(10);
/* 2616 */                         if (_jspx_meth_c_005fset_005f4(_jspx_page_context))
/*      */                           return;
/* 2618 */                         out.write(10);
/* 2619 */                         if (_jspx_meth_c_005fset_005f5(_jspx_page_context))
/*      */                           return;
/* 2621 */                         out.write(10);
/* 2622 */                         out.write(10);
/* 2623 */                         out.write(10);
/* 2624 */                         if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                           return;
/* 2626 */                         out.write(10);
/* 2627 */                         out.write(10);
/* 2628 */                         out.write(10);
/* 2629 */                         if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                           return;
/* 2631 */                         out.write("\n\n\n<tr>\n<td colspan=\"2\" class=\"");
/* 2632 */                         if (_jspx_meth_c_005fout_005f9(_jspx_page_context))
/*      */                           return;
/* 2634 */                         out.write("\" align=\"right\" style=\"padding:2px;\">\n<input type=\"button\" value=\"");
/* 2635 */                         if (_jspx_meth_fmt_005fmessage_005f0(_jspx_page_context))
/*      */                           return;
/* 2637 */                         out.write("\" onclick=\"getCustomFields('");
/* 2638 */                         if (_jspx_meth_c_005fout_005f10(_jspx_page_context))
/*      */                           return;
/* 2640 */                         out.write(39);
/* 2641 */                         out.write(44);
/* 2642 */                         out.write(39);
/* 2643 */                         if (_jspx_meth_c_005fout_005f11(_jspx_page_context))
/*      */                           return;
/* 2645 */                         out.write("',false,'CustomFieldValues',false);\" class=\"buttons btn_custom\"/>");
/* 2646 */                         out.write("\n</td>\n</tr>\n\n\n");
/* 2647 */                         out.write("\n        </table>\n    </td>\n    <td width=\"45%\" valign=\"top\">\n        <table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td class=\"tableheadingbborder\">");
/* 2648 */                         out.print(FormatUtil.getString("am.webclient.common.todaysavailability.text"));
/* 2649 */                         out.write(" <a name=\"Availability\" id=\"Availability\"></a>&nbsp;&nbsp;</td>\n        </tr>\n        <tr>\n          <td align=\"right\" >\n            <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n              <tr>\n                <td width=\"96%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2650 */                         if (_jspx_meth_c_005fout_005f12(_jspx_page_context))
/*      */                           return;
/* 2652 */                         out.write("&period=1&resourcename=");
/* 2653 */                         if (_jspx_meth_c_005fout_005f13(_jspx_page_context))
/*      */                           return;
/* 2655 */                         out.write("')\">\n                    <img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 2656 */                         out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 2657 */                         out.write("\">\n                </td>\n                <td width=\"4%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2658 */                         if (_jspx_meth_c_005fout_005f14(_jspx_page_context))
/*      */                           return;
/* 2660 */                         out.write("&period=2&resourcename=");
/* 2661 */                         if (_jspx_meth_c_005fout_005f15(_jspx_page_context))
/*      */                           return;
/* 2663 */                         out.write("')\">\n                    <img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2664 */                         out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 2665 */                         out.write("\">\n                </td>\n              </tr>\n            </table>\n          </td>\n        </tr>\n        <tr>\n          <td height=\"21\" align=\"center\" >");
/*      */                         
/* 2667 */                         AMWolf _jspx_th_awolf_005fpiechart_005f0 = (AMWolf)this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.get(AMWolf.class);
/* 2668 */                         _jspx_th_awolf_005fpiechart_005f0.setPageContext(_jspx_page_context);
/* 2669 */                         _jspx_th_awolf_005fpiechart_005f0.setParent(null);
/*      */                         
/* 2671 */                         _jspx_th_awolf_005fpiechart_005f0.setDataSetProducer("wlsGraph");
/*      */                         
/* 2673 */                         _jspx_th_awolf_005fpiechart_005f0.setWidth("260");
/*      */                         
/* 2675 */                         _jspx_th_awolf_005fpiechart_005f0.setHeight("180");
/*      */                         
/* 2677 */                         _jspx_th_awolf_005fpiechart_005f0.setLegend("true");
/*      */                         
/* 2679 */                         _jspx_th_awolf_005fpiechart_005f0.setUrl(true);
/*      */                         
/* 2681 */                         _jspx_th_awolf_005fpiechart_005f0.setUnits("%");
/*      */                         
/* 2683 */                         _jspx_th_awolf_005fpiechart_005f0.setDecimal(true);
/* 2684 */                         int _jspx_eval_awolf_005fpiechart_005f0 = _jspx_th_awolf_005fpiechart_005f0.doStartTag();
/* 2685 */                         if (_jspx_eval_awolf_005fpiechart_005f0 != 0) {
/* 2686 */                           if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2687 */                             out = _jspx_page_context.pushBody();
/* 2688 */                             _jspx_th_awolf_005fpiechart_005f0.setBodyContent((BodyContent)out);
/* 2689 */                             _jspx_th_awolf_005fpiechart_005f0.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2692 */                             out.write("\n            ");
/*      */                             
/* 2694 */                             Property _jspx_th_awolf_005fmap_005f0 = (Property)this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.get(Property.class);
/* 2695 */                             _jspx_th_awolf_005fmap_005f0.setPageContext(_jspx_page_context);
/* 2696 */                             _jspx_th_awolf_005fmap_005f0.setParent(_jspx_th_awolf_005fpiechart_005f0);
/*      */                             
/* 2698 */                             _jspx_th_awolf_005fmap_005f0.setId("color");
/* 2699 */                             int _jspx_eval_awolf_005fmap_005f0 = _jspx_th_awolf_005fmap_005f0.doStartTag();
/* 2700 */                             if (_jspx_eval_awolf_005fmap_005f0 != 0) {
/* 2701 */                               if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2702 */                                 out = _jspx_page_context.pushBody();
/* 2703 */                                 _jspx_th_awolf_005fmap_005f0.setBodyContent((BodyContent)out);
/* 2704 */                                 _jspx_th_awolf_005fmap_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 2707 */                                 out.write(32);
/*      */                                 
/* 2709 */                                 AMParam _jspx_th_awolf_005fparam_005f0 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2710 */                                 _jspx_th_awolf_005fparam_005f0.setPageContext(_jspx_page_context);
/* 2711 */                                 _jspx_th_awolf_005fparam_005f0.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                 
/* 2713 */                                 _jspx_th_awolf_005fparam_005f0.setName("1");
/*      */                                 
/* 2715 */                                 _jspx_th_awolf_005fparam_005f0.setValue(available);
/* 2716 */                                 int _jspx_eval_awolf_005fparam_005f0 = _jspx_th_awolf_005fparam_005f0.doStartTag();
/* 2717 */                                 if (_jspx_th_awolf_005fparam_005f0.doEndTag() == 5) {
/* 2718 */                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0); return;
/*      */                                 }
/*      */                                 
/* 2721 */                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f0);
/* 2722 */                                 out.write("\n            ");
/*      */                                 
/* 2724 */                                 AMParam _jspx_th_awolf_005fparam_005f1 = (AMParam)this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.get(AMParam.class);
/* 2725 */                                 _jspx_th_awolf_005fparam_005f1.setPageContext(_jspx_page_context);
/* 2726 */                                 _jspx_th_awolf_005fparam_005f1.setParent(_jspx_th_awolf_005fmap_005f0);
/*      */                                 
/* 2728 */                                 _jspx_th_awolf_005fparam_005f1.setName("0");
/*      */                                 
/* 2730 */                                 _jspx_th_awolf_005fparam_005f1.setValue(unavailable);
/* 2731 */                                 int _jspx_eval_awolf_005fparam_005f1 = _jspx_th_awolf_005fparam_005f1.doStartTag();
/* 2732 */                                 if (_jspx_th_awolf_005fparam_005f1.doEndTag() == 5) {
/* 2733 */                                   this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1); return;
/*      */                                 }
/*      */                                 
/* 2736 */                                 this._005fjspx_005ftagPool_005fawolf_005fparam_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_awolf_005fparam_005f1);
/* 2737 */                                 out.write(32);
/* 2738 */                                 int evalDoAfterBody = _jspx_th_awolf_005fmap_005f0.doAfterBody();
/* 2739 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 2742 */                               if (_jspx_eval_awolf_005fmap_005f0 != 1) {
/* 2743 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 2746 */                             if (_jspx_th_awolf_005fmap_005f0.doEndTag() == 5) {
/* 2747 */                               this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0); return;
/*      */                             }
/*      */                             
/* 2750 */                             this._005fjspx_005ftagPool_005fawolf_005fmap_0026_005fid.reuse(_jspx_th_awolf_005fmap_005f0);
/* 2751 */                             out.write(32);
/* 2752 */                             int evalDoAfterBody = _jspx_th_awolf_005fpiechart_005f0.doAfterBody();
/* 2753 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2756 */                           if (_jspx_eval_awolf_005fpiechart_005f0 != 1) {
/* 2757 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2760 */                         if (_jspx_th_awolf_005fpiechart_005f0.doEndTag() == 5) {
/* 2761 */                           this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/*      */                         }
/*      */                         else {
/* 2764 */                           this._005fjspx_005ftagPool_005fawolf_005fpiechart_0026_005fwidth_005furl_005funits_005flegend_005fheight_005fdecimal_005fdataSetProducer.reuse(_jspx_th_awolf_005fpiechart_005f0);
/* 2765 */                           out.write("\n          </td>\n        </tr>\n        <tr>\n          <td><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n              <tbody>\n                <tr>\n                  <td width=\"39%\" height=\"35\"  class=\"bodytext\">&nbsp;");
/* 2766 */                           out.print(FormatUtil.getString("am.webclient.tomacatdetail.currentstatus"));
/* 2767 */                           out.write("<br> </td>\n                  <td width=\"10%\" height=\"35\" class=\"yellowgrayborder\" ><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2768 */                           out.print(resourceid);
/* 2769 */                           out.write("&attributeid=3702')\">");
/* 2770 */                           out.print(getSeverityImageForAvailability(alert.getProperty(resourceid + "#" + "3702")));
/* 2771 */                           out.write("</a></td>\n                  <td width=\"51%\" align=\"right\" class=\"yellowgrayborder\" ><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2772 */                           out.print(resourceid);
/* 2773 */                           out.write("&attributeIDs=3702,3703&attributeToSelect=3702&redirectto=");
/* 2774 */                           out.print(encodeurl);
/* 2775 */                           out.write("#Availability\" class=\"staticlinks\">");
/* 2776 */                           out.print(ALERTCONFIG_TEXT);
/* 2777 */                           out.write("</a>&nbsp;</td>\n                </tr>\n              </tbody>\n            </table></td>\n        </tr>\n      </table>\n    </td>\n  </tr>\n</table>\n <table width=\"99%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\"><tr><td>");
/* 2778 */                           out.write("<!--$Id$-->\n<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\"><tr><td>\n<div id=\"customfieldsfullListDiv\" style='overflow: auto; display:none; width: 100%;'>\n</div>\n<div id=\"customfieldsloadingdiv\" style='text-align:center;height:200px;width: 100%;display: none;'><img src='/images/LoadingTC.gif' style='margin-top:74px'/></div>\n</td></tr></table>\n");
/* 2779 */                           out.write("</td></tr></table>\n<br>\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td  class=\"tableheadingbborder\">");
/* 2780 */                           out.print(FormatUtil.getString("am.webclient.support.systeminformation"));
/* 2781 */                           out.write("</td>\n    <td  class=\"tableheadingbborder\">");
/* 2782 */                           out.print(FormatUtil.getString("SUTILIZATION"));
/* 2783 */                           out.write("</td>\n    <td  class=\"tableheadingbborder\">");
/* 2784 */                           out.print(FormatUtil.getString("BUTILIZATION"));
/* 2785 */                           out.write("</td>\n  </tr>\n  <tr>\n    <td width=\"50%\" valign=\"top\" class=\"rborder\">\n\n");
/*      */                           
/* 2787 */                           if ((cpu != null) && (!cpu.equals("null")))
/*      */                           {
/*      */ 
/* 2790 */                             out.write("\n\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          ");
/* 2791 */                             if (oscolstate.equals("true")) {
/* 2792 */                               out.write("<td width=\"33%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2793 */                               out.print(resourceid);
/* 2794 */                               out.write("&attributeid=3757&period=-7&resourcename=");
/* 2795 */                               if (_jspx_meth_c_005fout_005f16(_jspx_page_context))
/*      */                                 return;
/* 2797 */                               out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2798 */                               out.print(seven_days_text);
/* 2799 */                               out.write("\"></a></td>");
/*      */                             }
/* 2801 */                             out.write("\n          <td width=\"33%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2802 */                             out.print(resourceid);
/* 2803 */                             out.write("&attributeid=3754&period=-7&resourcename=");
/* 2804 */                             if (_jspx_meth_c_005fout_005f17(_jspx_page_context))
/*      */                               return;
/* 2806 */                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2807 */                             out.print(seven_days_text);
/* 2808 */                             out.write("\"></a></td>\n          ");
/* 2809 */                             if (oscolstate.equals("true")) {
/* 2810 */                               out.write("<td width=\"33%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2811 */                               out.print(resourceid);
/* 2812 */                               out.write("&attributeid=3758&period=-7&resourcename=");
/* 2813 */                               if (_jspx_meth_c_005fout_005f18(_jspx_page_context))
/*      */                                 return;
/* 2815 */                               out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2816 */                               out.print(seven_days_text);
/* 2817 */                               out.write("\"></a></td>");
/*      */                             }
/* 2819 */                             out.write("\n        </tr>\n        <tr>\n        ");
/* 2820 */                             if (oscolstate.equals("true")) {
/* 2821 */                               out.write("\n          <td width=\"23%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n            <fieldset>\n            <legend style=\"width:20%;background-color:white\"><span class=\"bodytext\">");
/* 2822 */                               out.print(FormatUtil.getString("CPU"));
/* 2823 */                               out.write("</span></legend>\n            <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr>\n                <td align=\"center\"> ");
/* 2824 */                               JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(cpu), request.getCharacterEncoding()), out, true);
/* 2825 */                               out.write(" </td>\n              </tr>\n            </table>\n            </fieldset>\n          </td>\n        ");
/*      */                             }
/* 2827 */                             out.write("\n        <td width=\"25%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n        ");
/* 2828 */                             if (oscolstate.equals("false")) {
/* 2829 */                               out.write("\n        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"50%\">\n        <tr>\n        <td>\n        ");
/*      */                             }
/*      */                             
/*      */ 
/* 2833 */                             out.write("\n            <fieldset>\n            <legend style=\"width:40%;background-color:white\"><span class=\"bodytext\">");
/* 2834 */                             out.print(FormatUtil.getString("MEMORY"));
/* 2835 */                             out.write("\n            </span></legend>\n            <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr>\n                <td align=\"center\"> ");
/* 2836 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(memory), request.getCharacterEncoding()), out, true);
/* 2837 */                             out.write(" </td>\n              </tr>\n            </table>\n            </fieldset>\n         ");
/*      */                             
/* 2839 */                             if (oscolstate.equals("false"))
/*      */                             {
/* 2841 */                               out.write("\n         </td></tr></table>\n         ");
/*      */                             }
/*      */                             
/*      */ 
/* 2845 */                             out.write("\n         </td>\n          ");
/* 2846 */                             if (oscolstate.equals("true")) {
/* 2847 */                               out.write("\n          <td align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n            <fieldset>\n            <legend style=\"width:20%;background-color:white\"><span class=\"bodytext\">");
/* 2848 */                               out.print(FormatUtil.getString("DISK"));
/* 2849 */                               out.write("</span></legend>\n          <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr>\n                <td align=\"center\"> ");
/* 2850 */                               JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(disk), request.getCharacterEncoding()), out, true);
/* 2851 */                               out.write(" </td>\n              </tr>\n            </table>\n            </fieldset>\n            </td>\n            ");
/*      */                             }
/* 2853 */                             out.write("\n        </tr>\n\n      </table>\n");
/*      */                           }
/*      */                           
/*      */ 
/* 2857 */                           out.write("\n\n\n    </td>\n    <td width=\"25%\" valign=\"top\" class=\"rborder\">\n");
/*      */                           
/* 2859 */                           if ((spoolutilization != null) && (!spoolutilization.equals("null")))
/*      */                           {
/*      */ 
/* 2862 */                             out.write("\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"100%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2863 */                             if (_jspx_meth_c_005fout_005f19(_jspx_page_context))
/*      */                               return;
/* 2865 */                             out.write("&attributeid=3749&period=-7&resourcename=");
/* 2866 */                             if (_jspx_meth_c_005fout_005f20(_jspx_page_context))
/*      */                               return;
/* 2868 */                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2869 */                             out.print(seven_days_text);
/* 2870 */                             out.write("\"></td>\n        </tr>\n        <tr>\n          <td align=\"center\" colspan=\"1\">\n          <br>\n          <table border=\"0\">\n              <tr>\n                <td align=\"center\">\n                  ");
/*      */                             
/* 2872 */                             dialGraph.setValue(Integer.parseInt(spoolutilization));
/*      */                             
/* 2874 */                             out.write("\n                  ");
/* 2875 */                             if (_jspx_meth_awolf_005fdialchart_005f0(_jspx_page_context))
/*      */                               return;
/* 2877 */                             out.write("\n                  </td>\n              </tr>\n              <tr>\n                <td align=\"center\"><b>");
/* 2878 */                             out.print(spoolutilization);
/* 2879 */                             out.write("%</b><br> <br></td>\n              </tr>\n            </table></td>\n        </tr>\n      </table>\n");
/*      */                           }
/*      */                           
/*      */ 
/* 2883 */                           out.write("\n      </td>\n    <td width=\"25%\" valign=\"top\">\n");
/*      */                           
/* 2885 */                           if ((backgroundutilization != null) && (!backgroundutilization.equals("null")))
/*      */                           {
/*      */ 
/* 2888 */                             out.write("\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"100%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2889 */                             if (_jspx_meth_c_005fout_005f21(_jspx_page_context))
/*      */                               return;
/* 2891 */                             out.write("&attributeid=3709&period=-7&resourcename=");
/* 2892 */                             if (_jspx_meth_c_005fout_005f22(_jspx_page_context))
/*      */                               return;
/* 2894 */                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 2895 */                             out.print(seven_days_text);
/* 2896 */                             out.write("\"></td>\n        </tr>\n        <tr>\n          <td align=\"center\" colspan=\"1\">\n          <br>\n          <table border=\"0\">\n              <tr>\n                <td align=\"center\">\n\n                  ");
/*      */                             
/* 2898 */                             dialGraph.setValue(Integer.parseInt(backgroundutilization));
/*      */                             
/* 2900 */                             out.write("\n                  ");
/* 2901 */                             if (_jspx_meth_awolf_005fdialchart_005f1(_jspx_page_context))
/*      */                               return;
/* 2903 */                             out.write("\n                </td>\n              </tr>\n              <tr>\n                <td align=\"center\"><b>");
/* 2904 */                             out.print(backgroundutilization);
/* 2905 */                             out.write("%</b><br> <br></td>\n              </tr>\n          </table>\n          </td>\n        </tr>\n      </table>\n");
/*      */                           }
/*      */                           
/*      */ 
/* 2909 */                           out.write("\n   </td>\n  </tr>\n  <tr>\n  <td class=\"whitegrayborder\">\n  <table border=\"0\" width=\"100%\" cellspacing=\"0\">\n  <tr>\n          <td style=\"padding-right:5px\" width=\"33%\"><table width=\"100%\" border=\"0\"><tr><td align=\"left\"><span class=\"bodytext\">");
/* 2910 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 2911 */                           out.write("</span></td>");
/* 2912 */                           if (oscolstate.equals("true")) {
/* 2913 */                             out.write("<td align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2914 */                             out.print(resourceid);
/* 2915 */                             out.write("&attributeid=3757')\">");
/* 2916 */                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3757")));
/* 2917 */                             out.write("</a></td>");
/*      */                           }
/* 2919 */                           out.write("</tr></table></td>\n          <td align=\"");
/* 2920 */                           if (oscolstate.equals("true")) {
/* 2921 */                             out.write("right");
/*      */                           } else {
/* 2923 */                             out.write("left");
/*      */                           }
/* 2925 */                           out.write("\" style=\"padding-right:5px\" width=\"33%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2926 */                           out.print(resourceid);
/* 2927 */                           out.write("&attributeid=3754')\">");
/* 2928 */                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3754")));
/* 2929 */                           out.write("</a></td>\n          ");
/* 2930 */                           if (oscolstate.equals("true")) {
/* 2931 */                             out.write("<td align=\"right\" style=\"padding-right:5px\" width=\"33%\" class=\"rborder\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2932 */                             out.print(resourceid);
/* 2933 */                             out.write("&attributeid=3758')\">");
/* 2934 */                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3758")));
/* 2935 */                             out.write("</a></td>");
/*      */                           }
/* 2937 */                           out.write("\n  </tr>\n  </table>\n  </td>\n  <td class=\"whitegrayborder\" colspan=\"2\" cellspacing=\"0\">\n  <table border=\"0\" width=\"100%\">\n  <tr>\n\t<td align=\"center\" width=\"50%\" class=\"rborder\"><table width=\"100%\" border=\"0\"><tr><td align=\"left\"><span class=\"bodytext\">");
/* 2938 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 2939 */                           out.write("</span></td><td align=\"left\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2940 */                           out.print(resourceid);
/* 2941 */                           out.write("&attributeid=3749')\">");
/* 2942 */                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3749")));
/* 2943 */                           out.write("</a></td></tr></table></td>\n\t<td align=\"right\" width=\"50%\"><table width=\"100%\" border=\"0\"><tr><td align=\"left\"><span class=\"bodytext\">");
/* 2944 */                           out.print(FormatUtil.getString("table.heading.status"));
/* 2945 */                           out.write("</span></td><td align=\"left\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2946 */                           out.print(resourceid);
/* 2947 */                           out.write("&attributeid=3709')\">");
/* 2948 */                           out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3709")));
/* 2949 */                           out.write("</a></td></tr></table></td>\n  </tr>\n  </table>\n  </td>\n  </tr>\n  <tr>\n    <td width=\"44%\" align=\"right\" class=\"lightbg\" height=\"22\">\n    ");
/*      */                           
/* 2951 */                           if ((cpu != null) && (!cpu.equals("null")))
/*      */                           {
/*      */ 
/* 2954 */                             out.write("\n    <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2955 */                             out.print(resourceid);
/* 2956 */                             out.write("&attributeIDs=3757,3754,3758&attributeToSelect=3757&redirectto=");
/* 2957 */                             out.print(encodeurl);
/* 2958 */                             out.write("\" class=\"staticlinks\">");
/* 2959 */                             out.print(ALERTCONFIG_TEXT);
/* 2960 */                             out.write("</a>&nbsp;\n    ");
/*      */                           }
/*      */                           
/*      */ 
/* 2964 */                           out.write("\n    </td>\n\n    <td colspan=\"2\" align=\"right\" class=\"lightbg\" height=\"22\">\n  ");
/*      */                           
/* 2966 */                           if ((backgroundutilization != null) && (!backgroundutilization.equals("null")))
/*      */                           {
/*      */ 
/* 2969 */                             out.write("\n  <img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 2970 */                             out.print(resourceid);
/* 2971 */                             out.write("&attributeIDs=3749,3709&attributeToSelect=3749&redirectto=");
/* 2972 */                             out.print(encodeurl);
/* 2973 */                             out.write("\" class=\"staticlinks\">");
/* 2974 */                             out.print(ALERTCONFIG_TEXT);
/* 2975 */                             out.write("</a>&nbsp;\n  ");
/*      */                           }
/*      */                           
/*      */ 
/* 2979 */                           out.write("\n    </td>\n  </tr>\n</table>\n<br>\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    <td  class=\"tableheadingbborder\">");
/* 2980 */                           out.print(FormatUtil.getString("Program Buffer Utilization"));
/* 2981 */                           out.write("</td>\n    <td class=\"tableheadingbborder\">");
/* 2982 */                           out.print(FormatUtil.getString("FRONTENDRESPONSETIME"));
/* 2983 */                           out.write("</td>\n  </tr>\n  <tr>\n    <td width=\"50%\" valign=\"top\" class=\"rborder\">\n\n            ");
/*      */                           
/* 2985 */                           if ((hitratio != null) && (!hitratio.equals("null")))
/*      */                           {
/*      */ 
/* 2988 */                             out.write("\n\n    <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"33%\" align=\"right\"><br></td>\n          <td width=\"33%\" align=\"right\"><br></td>\n          <td width=\"33%\" align=\"right\"><br></td>\n        </tr>\n        <tr>\n          <td nowrap=nowrap width=\"33%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n            <fieldset >\n            <legend style=\"width:42%;background-color:white;\"><span width=\"100%\" class=\"bodytext\">");
/* 2989 */                             out.print(FormatUtil.getString("HITRATIO"));
/* 2990 */                             out.write("</span></legend>\n            <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr>\n                <td align=\"center\"> ");
/* 2991 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(hitratio), request.getCharacterEncoding()), out, true);
/* 2992 */                             out.write(" </td>\n              </tr>\n            </table>\n            </fieldset></td>\n          <td width=\"33%\" align=\"center\" style=\"padding-left:5px;padding-right:5px;\">\n            <fieldset>\n            <legend style=\"width:36%;background-color:white\"><span class=\"bodytext\">");
/* 2993 */                             out.print(FormatUtil.getString("SPACE"));
/* 2994 */                             out.write("</span></legend>\n            <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr>\n                <td align=\"center\"> ");
/* 2995 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(spaceused), request.getCharacterEncoding()), out, true);
/* 2996 */                             out.write(" </td>\n              </tr>\n            </table>\n            </fieldset></td>\n          <td align=\"center\" style=\"padding-left:5px;padding-right:5px;\"> <fieldset >\n            <legend style=\"width:44%;background-color:white\"><span class=\"bodytext\">");
/* 2997 */                             out.print(FormatUtil.getString("DIRECTORY"));
/* 2998 */                             out.write("</span></legend>\n            <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n              <tr>\n                <td align=\"center\"> ");
/* 2999 */                             JspRuntimeLibrary.include(request, response, "/jsp/helicalgraph.jsp" + ("/jsp/helicalgraph.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("percent", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(directoryused), request.getCharacterEncoding()), out, true);
/* 3000 */                             out.write(" </td>\n              </tr>\n            </table>\n            </fieldset></td>\n        </tr>\n      </table>\n\n\t");
/*      */                           }
/*      */                           
/*      */ 
/* 3004 */                           out.write("\n\n      </td>\n\n    <td width=\"50%\" valign=\"top\">\n      <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"100%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showHistoryData.do?method=getData&resourceid=");
/* 3005 */                           if (_jspx_meth_c_005fout_005f23(_jspx_page_context))
/*      */                             return;
/* 3007 */                           out.write("&attributeid=3734&period=-7&resourcename=");
/* 3008 */                           if (_jspx_meth_c_005fout_005f24(_jspx_page_context))
/*      */                             return;
/* 3010 */                           out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3011 */                           out.print(seven_days_text);
/* 3012 */                           out.write("\"></a></td>\n        </tr>\n        <tr>\n          <td align=\"center\">\n            ");
/*      */                           
/* 3014 */                           sapGraph.setParameter(resourceid, "dialogresponsetime");
/*      */                           
/* 3016 */                           out.write("\n            ");
/*      */                           
/* 3018 */                           TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 3019 */                           _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3020 */                           _jspx_th_awolf_005ftimechart_005f0.setParent(null);
/*      */                           
/* 3022 */                           _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("sapGraph");
/*      */                           
/* 3024 */                           _jspx_th_awolf_005ftimechart_005f0.setWidth("300");
/*      */                           
/* 3026 */                           _jspx_th_awolf_005ftimechart_005f0.setHeight("160");
/*      */                           
/* 3028 */                           _jspx_th_awolf_005ftimechart_005f0.setLegend("false");
/*      */                           
/* 3030 */                           _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                           
/* 3032 */                           _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_frontendresponsetime);
/*      */                           
/* 3034 */                           _jspx_th_awolf_005ftimechart_005f0.setCustomDateAxis(true);
/*      */                           
/* 3036 */                           _jspx_th_awolf_005ftimechart_005f0.setCustomAngle(270.0D);
/* 3037 */                           int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3038 */                           if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3039 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3040 */                               out = _jspx_page_context.pushBody();
/* 3041 */                               _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3042 */                               _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 3045 */                               out.write("\n            ");
/* 3046 */                               int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3047 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3050 */                             if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3051 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3054 */                           if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3055 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/*      */                           }
/*      */                           else {
/* 3058 */                             this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3059 */                             out.write(" </td>\n        </tr>\n      </table>\n    </td>\n  </tr>\n  <tr>\n    <td class=\"whitegrayborder\">\n    <table border=\"0\" width=\"100%\" cellspacing=\"0\">\n    <tr>\n\t<td align=\"right\" style=\"padding-right:5px\" width=\"33%\"><table width=\"100%\" border=\"0\"><tr><td align=\"left\"><span class=\"bodytext\">");
/* 3060 */                             out.print(FormatUtil.getString("table.heading.status"));
/* 3061 */                             out.write("</span></td><td align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3062 */                             out.print(programresid);
/* 3063 */                             out.write("&attributeid=3713')\">");
/* 3064 */                             out.print(getSeverityImage(alert.getProperty(programresid + "#" + "3713")));
/* 3065 */                             out.write("</a></td></tr></table></td>\n\t<td align=\"right\" style=\"padding-right:10px\" width=\"33%\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3066 */                             out.print(programresid);
/* 3067 */                             out.write("&attributeid=3714')\">");
/* 3068 */                             out.print(getSeverityImage(alert.getProperty(programresid + "#" + "3714")));
/* 3069 */                             out.write("</a></td>\n\t<td align=\"right\" style=\"padding-right:5px\" width=\"33%\" class=\"rborder\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3070 */                             out.print(programresid);
/* 3071 */                             out.write("&attributeid=3715')\">");
/* 3072 */                             out.print(getSeverityImage(alert.getProperty(programresid + "#" + "3715")));
/* 3073 */                             out.write("</a></td>\n    </tr>\n    </table>\n    </td>\n    <td class=\"whitegrayborder\" cellspacing=\"0\">\n    <table border=\"0\" width=\"100%\">\n    <tr>\n\t<td align=\"right\" style=\"padding-right:5px\"><table width=\"100%\" border=\"0\"><tr><td align=\"right\" width=\"90%\"><span class=\"bodytext\">");
/* 3074 */                             out.print(FormatUtil.getString("table.heading.status"));
/* 3075 */                             out.write("</span></td><td align=\"right\"><a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3076 */                             out.print(resourceid);
/* 3077 */                             out.write("&attributeid=3734')\">");
/* 3078 */                             out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3734")));
/* 3079 */                             out.write("</a></td></tr></table></td>\n    </tr>\n    </table>\n    </td>\n  </tr>\n        <tr align=\"right\" class=\"lightbg\">\n          <td width=\"50%\" align=\"right\" height=\"22\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3080 */                             out.print(programresid);
/* 3081 */                             out.write("&attributeIDs=3713,3714,3715&attributeToSelect=3713&redirectto=");
/* 3082 */                             out.print(encodeurl);
/* 3083 */                             out.write("\" class=\"staticlinks\">");
/* 3084 */                             out.print(ALERTCONFIG_TEXT);
/* 3085 */                             out.write("</a>&nbsp;</td>\n          <td width=\"50%\" align=\"right\" height=\"22\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3086 */                             out.print(resourceid);
/* 3087 */                             out.write("&attributeIDs=3734&attributeToSelect=3734&redirectto=");
/* 3088 */                             out.print(encodeurl);
/* 3089 */                             out.write("\" class=\"staticlinks\">");
/* 3090 */                             out.print(ALERTCONFIG_TEXT);
/* 3091 */                             out.write("</a>&nbsp;</td>\n        </tr>\n\n</table>\n<br>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtbdarkborder\">\n  <tr>\n    ");
/* 3092 */                             if (oscolstate.equals("true")) {
/* 3093 */                               out.write("<td  class=\"tableheadingbborder\">");
/* 3094 */                               out.print(FormatUtil.getString("PAGINGRATE"));
/* 3095 */                               out.write("</td>");
/*      */                             }
/* 3097 */                             out.write("\n    <td class=\"tableheadingbborder\">");
/* 3098 */                             out.print(FormatUtil.getString("SYSLOGFREQ"));
/* 3099 */                             out.write("</td>\n  </tr>\n  <tr>\n  ");
/* 3100 */                             if (oscolstate.equals("true")) {
/* 3101 */                               out.write("\n    <td width=\"50%\" valign=\"top\" class=\"rborder\"> <table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"95%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showHistoryData.do?method=getData&resourceid=");
/* 3102 */                               if (_jspx_meth_c_005fout_005f25(_jspx_page_context))
/*      */                                 return;
/* 3104 */                               out.write("&attributeid=3751&period=-7&resourcename=");
/* 3105 */                               if (_jspx_meth_c_005fout_005f26(_jspx_page_context))
/*      */                                 return;
/* 3107 */                               out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3108 */                               out.print(seven_days_text);
/* 3109 */                               out.write("\"></a></td>\n        </tr>\n        <tr>\n          <td align=\"center\">\n            ");
/*      */                               
/* 3111 */                               sapGraph.setParameter(resourceid, "pageinfo");
/*      */                               
/* 3113 */                               out.write("\n            ");
/*      */                               
/* 3115 */                               TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 3116 */                               _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3117 */                               _jspx_th_awolf_005ftimechart_005f1.setParent(null);
/*      */                               
/* 3119 */                               _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("sapGraph");
/*      */                               
/* 3121 */                               _jspx_th_awolf_005ftimechart_005f1.setWidth("325");
/*      */                               
/* 3123 */                               _jspx_th_awolf_005ftimechart_005f1.setHeight("175");
/*      */                               
/* 3125 */                               _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                               
/* 3127 */                               _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                               
/* 3129 */                               _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_pagingrate);
/*      */                               
/* 3131 */                               _jspx_th_awolf_005ftimechart_005f1.setCustomDateAxis(true);
/*      */                               
/* 3133 */                               _jspx_th_awolf_005ftimechart_005f1.setCustomAngle(270.0D);
/* 3134 */                               int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3135 */                               if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3136 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3137 */                                   out = _jspx_page_context.pushBody();
/* 3138 */                                   _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3139 */                                   _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 3142 */                                   out.write("\n            ");
/* 3143 */                                   int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3144 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 3147 */                                 if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3148 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 3151 */                               if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3152 */                                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                               }
/*      */                               
/* 3155 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3156 */                               out.write(" </td>\n        </tr>\n      </table>\n      </td>\n    ");
/*      */                             }
/* 3158 */                             out.write("\n    <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n        <tr>\n          <td width=\"95%\" height=\"25\" align=\"right\">\n            <!--a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/showHistoryData.do?method=getData&resourceid=");
/* 3159 */                             if (_jspx_meth_c_005fout_005f27(_jspx_page_context))
/*      */                               return;
/* 3161 */                             out.write("&attributeid=3602&period=-7&resourcename=");
/* 3162 */                             if (_jspx_meth_c_005fout_005f28(_jspx_page_context))
/*      */                               return;
/* 3164 */                             out.write("',740,550)\"><img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3165 */                             out.print(seven_days_text);
/* 3166 */                             out.write("\"></a-->\n            &nbsp;</td>\n        </tr>\n        <tr>\n          <td align=\"center\">\n            ");
/*      */                             
/* 3168 */                             sapGraph.setParameter(resourceid, "syslogfreq");
/* 3169 */                             String width = "325";
/* 3170 */                             if (oscolstate.equals("false"))
/*      */                             {
/* 3172 */                               width = "500";
/*      */                             }
/*      */                             
/* 3175 */                             out.write("\n            ");
/*      */                             
/* 3177 */                             TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.get(TimeChart.class);
/* 3178 */                             _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3179 */                             _jspx_th_awolf_005ftimechart_005f2.setParent(null);
/*      */                             
/* 3181 */                             _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("sapGraph");
/*      */                             
/* 3183 */                             _jspx_th_awolf_005ftimechart_005f2.setWidth(width);
/*      */                             
/* 3185 */                             _jspx_th_awolf_005ftimechart_005f2.setHeight("175");
/*      */                             
/* 3187 */                             _jspx_th_awolf_005ftimechart_005f2.setLegend("false");
/*      */                             
/* 3189 */                             _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(xaxis_time);
/*      */                             
/* 3191 */                             _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(yaxis_syslogfreq);
/*      */                             
/* 3193 */                             _jspx_th_awolf_005ftimechart_005f2.setCustomDateAxis(true);
/*      */                             
/* 3195 */                             _jspx_th_awolf_005ftimechart_005f2.setCustomAngle(270.0D);
/* 3196 */                             int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3197 */                             if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3198 */                               if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3199 */                                 out = _jspx_page_context.pushBody();
/* 3200 */                                 _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3201 */                                 _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3204 */                                 out.write("\n            ");
/* 3205 */                                 int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3206 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3209 */                               if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3210 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3213 */                             if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3214 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f2);
/*      */                             }
/*      */                             else {
/* 3217 */                               this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer_005fcustomDateAxis_005fcustomAngle.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3218 */                               out.write(" </td>\n        </tr>\n      </table></td>\n  </tr>\n  <tr>\n  ");
/* 3219 */                               if (oscolstate.equals("true")) {
/* 3220 */                                 out.write("\n    <td align=\"right\" class=\"yellowgrayborder lightbg\">\n    <table border=\"0\" width=\"100%\"><tr><td align=\"left\">\n    <span class=\"bodytext\">");
/* 3221 */                                 out.print(FormatUtil.getString("PAGEIN"));
/* 3222 */                                 out.write("</span>&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3223 */                                 out.print(resourceid);
/* 3224 */                                 out.write("&attributeid=3751')\">");
/* 3225 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3751")));
/* 3226 */                                 out.write("</a>\n    <span class=\"bodytext\">");
/* 3227 */                                 out.print(FormatUtil.getString("PAGEOUT"));
/* 3228 */                                 out.write("</span>&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3229 */                                 out.print(resourceid);
/* 3230 */                                 out.write("&attributeid=3752')\">");
/* 3231 */                                 out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3752")));
/* 3232 */                                 out.write("</a>\n    </td><td align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3233 */                                 out.print(resourceid);
/* 3234 */                                 out.write("&attributeIDs=3751,3752&attributeToSelect=3751&redirectto=");
/* 3235 */                                 out.print(encodeurl);
/* 3236 */                                 out.write("\" class=\"staticlinks\">");
/* 3237 */                                 out.print(ALERTCONFIG_TEXT);
/* 3238 */                                 out.write("</a>&nbsp;\n    </td></tr></table>\n    </td>\n    ");
/*      */                               }
/* 3240 */                               out.write("\n    <td align=\"right\" class=\"yellowgrayborder lightbg\"><table border=\"0\" width=\"100%\"><tr><td align=\"left\"><span class=\"bodytext\">");
/* 3241 */                               out.print(FormatUtil.getString("table.heading.status"));
/* 3242 */                               out.write("</span>&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3243 */                               out.print(resourceid);
/* 3244 */                               out.write("&attributeid=3756')\">");
/* 3245 */                               out.print(getSeverityImage(alert.getProperty(resourceid + "#" + "3756")));
/* 3246 */                               out.write("</a></td><td align=\"right\"><img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3247 */                               out.print(resourceid);
/* 3248 */                               out.write("&attributeIDs=3756&attributeToSelect=3756&redirectto=");
/* 3249 */                               out.print(encodeurl);
/* 3250 */                               out.write("\" class=\"staticlinks\">");
/* 3251 */                               out.print(ALERTCONFIG_TEXT);
/* 3252 */                               out.write("</a>&nbsp;</td></tr></table></td>\n  </tr>\n</table>\n");
/*      */                             }
/* 3254 */                           } } } } } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3255 */         out = _jspx_out;
/* 3256 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3257 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3258 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3261 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3267 */     PageContext pageContext = _jspx_page_context;
/* 3268 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3270 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3271 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3272 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3274 */     _jspx_th_c_005fif_005f0.setTest("${not empty param.haid}");
/* 3275 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3276 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3278 */         out.write(10);
/* 3279 */         out.write(9);
/* 3280 */         out.write(9);
/* 3281 */         if (_jspx_meth_c_005fset_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3282 */           return true;
/* 3283 */         out.write(10);
/* 3284 */         out.write(9);
/* 3285 */         out.write(9);
/* 3286 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3287 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3291 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3292 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3293 */       return true;
/*      */     }
/* 3295 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3296 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3301 */     PageContext pageContext = _jspx_page_context;
/* 3302 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3304 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3305 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 3306 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3308 */     _jspx_th_c_005fset_005f0.setVar("myfield_paramresid");
/*      */     
/* 3310 */     _jspx_th_c_005fset_005f0.setScope("page");
/* 3311 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 3312 */     if (_jspx_eval_c_005fset_005f0 != 0) {
/* 3313 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3314 */         out = _jspx_page_context.pushBody();
/* 3315 */         _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 3316 */         _jspx_th_c_005fset_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3319 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fset_005f0, _jspx_page_context))
/* 3320 */           return true;
/* 3321 */         int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 3322 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3325 */       if (_jspx_eval_c_005fset_005f0 != 1) {
/* 3326 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3329 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 3330 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3331 */       return true;
/*      */     }
/* 3333 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f0);
/* 3334 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fset_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3339 */     PageContext pageContext = _jspx_page_context;
/* 3340 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3342 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3343 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3344 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fset_005f0);
/*      */     
/* 3346 */     _jspx_th_c_005fout_005f0.setValue("${param.haid}");
/* 3347 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3348 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3349 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3350 */       return true;
/*      */     }
/* 3352 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3353 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3358 */     PageContext pageContext = _jspx_page_context;
/* 3359 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3361 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3362 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3363 */     _jspx_th_c_005fif_005f1.setParent(null);
/*      */     
/* 3365 */     _jspx_th_c_005fif_005f1.setTest("${not empty param.resourceid}");
/* 3366 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3367 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3369 */         out.write(10);
/* 3370 */         out.write(9);
/* 3371 */         out.write(9);
/* 3372 */         if (_jspx_meth_c_005fset_005f1(_jspx_th_c_005fif_005f1, _jspx_page_context))
/* 3373 */           return true;
/* 3374 */         out.write(10);
/* 3375 */         out.write(9);
/* 3376 */         out.write(9);
/* 3377 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3378 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3382 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3383 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3384 */       return true;
/*      */     }
/* 3386 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3387 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f1(JspTag _jspx_th_c_005fif_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3392 */     PageContext pageContext = _jspx_page_context;
/* 3393 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3395 */     SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3396 */     _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 3397 */     _jspx_th_c_005fset_005f1.setParent((Tag)_jspx_th_c_005fif_005f1);
/*      */     
/* 3399 */     _jspx_th_c_005fset_005f1.setVar("myfield_paramresid");
/*      */     
/* 3401 */     _jspx_th_c_005fset_005f1.setScope("page");
/* 3402 */     int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 3403 */     if (_jspx_eval_c_005fset_005f1 != 0) {
/* 3404 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3405 */         out = _jspx_page_context.pushBody();
/* 3406 */         _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 3407 */         _jspx_th_c_005fset_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3410 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fset_005f1, _jspx_page_context))
/* 3411 */           return true;
/* 3412 */         int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 3413 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3416 */       if (_jspx_eval_c_005fset_005f1 != 1) {
/* 3417 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3420 */     if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 3421 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3422 */       return true;
/*      */     }
/* 3424 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f1);
/* 3425 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fset_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3430 */     PageContext pageContext = _jspx_page_context;
/* 3431 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3433 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3434 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3435 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fset_005f1);
/*      */     
/* 3437 */     _jspx_th_c_005fout_005f1.setValue("${param.resourceid}");
/* 3438 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3439 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3440 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3441 */       return true;
/*      */     }
/* 3443 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3444 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3449 */     PageContext pageContext = _jspx_page_context;
/* 3450 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3452 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3453 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3454 */     _jspx_th_c_005fout_005f2.setParent(null);
/*      */     
/* 3456 */     _jspx_th_c_005fout_005f2.setValue("${myfield_paramresid}");
/* 3457 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3458 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3459 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3460 */       return true;
/*      */     }
/* 3462 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3468 */     PageContext pageContext = _jspx_page_context;
/* 3469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3471 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3472 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3473 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 3475 */     _jspx_th_c_005fif_005f2.setTest("${not empty param.haid}");
/* 3476 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3477 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3479 */         out.write(10);
/* 3480 */         if (_jspx_meth_c_005fset_005f2(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3481 */           return true;
/* 3482 */         out.write(10);
/* 3483 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3484 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3488 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3489 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3490 */       return true;
/*      */     }
/* 3492 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3493 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f2(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3498 */     PageContext pageContext = _jspx_page_context;
/* 3499 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3501 */     SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3502 */     _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3503 */     _jspx_th_c_005fset_005f2.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3505 */     _jspx_th_c_005fset_005f2.setVar("myfield_resid");
/*      */     
/* 3507 */     _jspx_th_c_005fset_005f2.setScope("page");
/* 3508 */     int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3509 */     if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3510 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3511 */         out = _jspx_page_context.pushBody();
/* 3512 */         _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3513 */         _jspx_th_c_005fset_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3516 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fset_005f2, _jspx_page_context))
/* 3517 */           return true;
/* 3518 */         int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3519 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3522 */       if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3523 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3526 */     if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3527 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 3528 */       return true;
/*      */     }
/* 3530 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f2);
/* 3531 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fset_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3536 */     PageContext pageContext = _jspx_page_context;
/* 3537 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3539 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3540 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3541 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fset_005f2);
/*      */     
/* 3543 */     _jspx_th_c_005fout_005f3.setValue("${param.haid}");
/* 3544 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3545 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3546 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3547 */       return true;
/*      */     }
/* 3549 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3550 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3555 */     PageContext pageContext = _jspx_page_context;
/* 3556 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3558 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3559 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3560 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 3562 */     _jspx_th_c_005fif_005f3.setTest("${not empty param.resourceid}");
/* 3563 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3564 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3566 */         out.write(10);
/* 3567 */         if (_jspx_meth_c_005fset_005f3(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3568 */           return true;
/* 3569 */         out.write(10);
/* 3570 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3571 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3575 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3576 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3577 */       return true;
/*      */     }
/* 3579 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3580 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f3(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3585 */     PageContext pageContext = _jspx_page_context;
/* 3586 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3588 */     SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3589 */     _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3590 */     _jspx_th_c_005fset_005f3.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3592 */     _jspx_th_c_005fset_005f3.setVar("myfield_resid");
/*      */     
/* 3594 */     _jspx_th_c_005fset_005f3.setScope("page");
/* 3595 */     int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3596 */     if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3597 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3598 */         out = _jspx_page_context.pushBody();
/* 3599 */         _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3600 */         _jspx_th_c_005fset_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3603 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fset_005f3, _jspx_page_context))
/* 3604 */           return true;
/* 3605 */         int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3606 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3609 */       if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3610 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3613 */     if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3614 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 3615 */       return true;
/*      */     }
/* 3617 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f3);
/* 3618 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fset_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3623 */     PageContext pageContext = _jspx_page_context;
/* 3624 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3626 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3627 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3628 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fset_005f3);
/*      */     
/* 3630 */     _jspx_th_c_005fout_005f4.setValue("${param.resourceid}");
/* 3631 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3632 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3633 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3634 */       return true;
/*      */     }
/* 3636 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3637 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3642 */     PageContext pageContext = _jspx_page_context;
/* 3643 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3645 */     SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3646 */     _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3647 */     _jspx_th_c_005fset_005f4.setParent(null);
/*      */     
/* 3649 */     _jspx_th_c_005fset_005f4.setVar("trstripclass");
/*      */     
/* 3651 */     _jspx_th_c_005fset_005f4.setScope("page");
/* 3652 */     int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3653 */     if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3654 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3655 */         out = _jspx_page_context.pushBody();
/* 3656 */         _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3657 */         _jspx_th_c_005fset_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3660 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fset_005f4, _jspx_page_context))
/* 3661 */           return true;
/* 3662 */         int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3663 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3666 */       if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3667 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3670 */     if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3671 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 3672 */       return true;
/*      */     }
/* 3674 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f4);
/* 3675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fset_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3680 */     PageContext pageContext = _jspx_page_context;
/* 3681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3683 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3684 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3685 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fset_005f4);
/*      */     
/* 3687 */     _jspx_th_c_005fout_005f5.setValue("");
/* 3688 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3689 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3690 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3691 */       return true;
/*      */     }
/* 3693 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3694 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3699 */     PageContext pageContext = _jspx_page_context;
/* 3700 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3702 */     SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3703 */     _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3704 */     _jspx_th_c_005fset_005f5.setParent(null);
/*      */     
/* 3706 */     _jspx_th_c_005fset_005f5.setVar("myfield_entity");
/*      */     
/* 3708 */     _jspx_th_c_005fset_005f5.setScope("page");
/* 3709 */     int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3710 */     if (_jspx_eval_c_005fset_005f5 != 0) {
/* 3711 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3712 */         out = _jspx_page_context.pushBody();
/* 3713 */         _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 3714 */         _jspx_th_c_005fset_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3717 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fset_005f5, _jspx_page_context))
/* 3718 */           return true;
/* 3719 */         int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 3720 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3723 */       if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3724 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3727 */     if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3728 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 3729 */       return true;
/*      */     }
/* 3731 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f5);
/* 3732 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fset_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3737 */     PageContext pageContext = _jspx_page_context;
/* 3738 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3740 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3741 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3742 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fset_005f5);
/*      */     
/* 3744 */     _jspx_th_c_005fout_005f6.setValue("noalarms");
/* 3745 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3746 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3747 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3748 */       return true;
/*      */     }
/* 3750 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3751 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3756 */     PageContext pageContext = _jspx_page_context;
/* 3757 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3759 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3760 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3761 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 3763 */     _jspx_th_c_005fif_005f4.setTest("${not empty param.entity}");
/* 3764 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3765 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3767 */         out.write(10);
/* 3768 */         if (_jspx_meth_c_005fset_005f6(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3769 */           return true;
/* 3770 */         out.write(10);
/* 3771 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3772 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3776 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3777 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3778 */       return true;
/*      */     }
/* 3780 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3781 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f6(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3786 */     PageContext pageContext = _jspx_page_context;
/* 3787 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3789 */     SetTag _jspx_th_c_005fset_005f6 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3790 */     _jspx_th_c_005fset_005f6.setPageContext(_jspx_page_context);
/* 3791 */     _jspx_th_c_005fset_005f6.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3793 */     _jspx_th_c_005fset_005f6.setVar("myfield_entity");
/*      */     
/* 3795 */     _jspx_th_c_005fset_005f6.setScope("page");
/* 3796 */     int _jspx_eval_c_005fset_005f6 = _jspx_th_c_005fset_005f6.doStartTag();
/* 3797 */     if (_jspx_eval_c_005fset_005f6 != 0) {
/* 3798 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3799 */         out = _jspx_page_context.pushBody();
/* 3800 */         _jspx_th_c_005fset_005f6.setBodyContent((BodyContent)out);
/* 3801 */         _jspx_th_c_005fset_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3804 */         if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fset_005f6, _jspx_page_context))
/* 3805 */           return true;
/* 3806 */         int evalDoAfterBody = _jspx_th_c_005fset_005f6.doAfterBody();
/* 3807 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3810 */       if (_jspx_eval_c_005fset_005f6 != 1) {
/* 3811 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3814 */     if (_jspx_th_c_005fset_005f6.doEndTag() == 5) {
/* 3815 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 3816 */       return true;
/*      */     }
/* 3818 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f6);
/* 3819 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fset_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3824 */     PageContext pageContext = _jspx_page_context;
/* 3825 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3827 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3828 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3829 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fset_005f6);
/*      */     
/* 3831 */     _jspx_th_c_005fout_005f7.setValue("${param.entity}");
/* 3832 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3833 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3834 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3835 */       return true;
/*      */     }
/* 3837 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3838 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3843 */     PageContext pageContext = _jspx_page_context;
/* 3844 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3846 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3847 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3848 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 3850 */     _jspx_th_c_005fif_005f5.setTest("${not empty param.includeClass}");
/* 3851 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3852 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3854 */         out.write(10);
/* 3855 */         if (_jspx_meth_c_005fset_005f7(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 3856 */           return true;
/* 3857 */         out.write(10);
/* 3858 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3859 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3863 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3864 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3865 */       return true;
/*      */     }
/* 3867 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3868 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f7(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3873 */     PageContext pageContext = _jspx_page_context;
/* 3874 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3876 */     SetTag _jspx_th_c_005fset_005f7 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.get(SetTag.class);
/* 3877 */     _jspx_th_c_005fset_005f7.setPageContext(_jspx_page_context);
/* 3878 */     _jspx_th_c_005fset_005f7.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3880 */     _jspx_th_c_005fset_005f7.setVar("trstripclass");
/*      */     
/* 3882 */     _jspx_th_c_005fset_005f7.setScope("page");
/* 3883 */     int _jspx_eval_c_005fset_005f7 = _jspx_th_c_005fset_005f7.doStartTag();
/* 3884 */     if (_jspx_eval_c_005fset_005f7 != 0) {
/* 3885 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3886 */         out = _jspx_page_context.pushBody();
/* 3887 */         _jspx_th_c_005fset_005f7.setBodyContent((BodyContent)out);
/* 3888 */         _jspx_th_c_005fset_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3891 */         if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fset_005f7, _jspx_page_context))
/* 3892 */           return true;
/* 3893 */         int evalDoAfterBody = _jspx_th_c_005fset_005f7.doAfterBody();
/* 3894 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3897 */       if (_jspx_eval_c_005fset_005f7 != 1) {
/* 3898 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3901 */     if (_jspx_th_c_005fset_005f7.doEndTag() == 5) {
/* 3902 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 3903 */       return true;
/*      */     }
/* 3905 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fscope.reuse(_jspx_th_c_005fset_005f7);
/* 3906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fset_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3911 */     PageContext pageContext = _jspx_page_context;
/* 3912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3914 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3915 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3916 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fset_005f7);
/*      */     
/* 3918 */     _jspx_th_c_005fout_005f8.setValue("${param.includeClass}");
/* 3919 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3920 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3921 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3922 */       return true;
/*      */     }
/* 3924 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3925 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3930 */     PageContext pageContext = _jspx_page_context;
/* 3931 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3933 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3934 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3935 */     _jspx_th_c_005fout_005f9.setParent(null);
/*      */     
/* 3937 */     _jspx_th_c_005fout_005f9.setValue("${trstripclass}");
/* 3938 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3939 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3940 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3941 */       return true;
/*      */     }
/* 3943 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3944 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3949 */     PageContext pageContext = _jspx_page_context;
/* 3950 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3952 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 3953 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 3954 */     _jspx_th_fmt_005fmessage_005f0.setParent(null);
/* 3955 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 3956 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 3957 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3958 */         out = _jspx_page_context.pushBody();
/* 3959 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 3960 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 3963 */         out.write("am.myfield.customfield.text");
/* 3964 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 3965 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 3968 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 3969 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 3972 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 3973 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3974 */       return true;
/*      */     }
/* 3976 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 3977 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3982 */     PageContext pageContext = _jspx_page_context;
/* 3983 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3985 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3986 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3987 */     _jspx_th_c_005fout_005f10.setParent(null);
/*      */     
/* 3989 */     _jspx_th_c_005fout_005f10.setValue("${myfield_resid}");
/* 3990 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3991 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3992 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3993 */       return true;
/*      */     }
/* 3995 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3996 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4001 */     PageContext pageContext = _jspx_page_context;
/* 4002 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4004 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4005 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 4006 */     _jspx_th_c_005fout_005f11.setParent(null);
/*      */     
/* 4008 */     _jspx_th_c_005fout_005f11.setValue("${myfield_entity}");
/* 4009 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 4010 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 4011 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4012 */       return true;
/*      */     }
/* 4014 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 4015 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4020 */     PageContext pageContext = _jspx_page_context;
/* 4021 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4023 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4024 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 4025 */     _jspx_th_c_005fout_005f12.setParent(null);
/*      */     
/* 4027 */     _jspx_th_c_005fout_005f12.setValue("${param.resourceid}");
/* 4028 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 4029 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 4030 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4031 */       return true;
/*      */     }
/* 4033 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 4034 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4039 */     PageContext pageContext = _jspx_page_context;
/* 4040 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4042 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4043 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 4044 */     _jspx_th_c_005fout_005f13.setParent(null);
/*      */     
/* 4046 */     _jspx_th_c_005fout_005f13.setValue("${param.resourcename}");
/* 4047 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 4048 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 4049 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4050 */       return true;
/*      */     }
/* 4052 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 4053 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4058 */     PageContext pageContext = _jspx_page_context;
/* 4059 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4061 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4062 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 4063 */     _jspx_th_c_005fout_005f14.setParent(null);
/*      */     
/* 4065 */     _jspx_th_c_005fout_005f14.setValue("${param.resourceid}");
/* 4066 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 4067 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 4068 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4069 */       return true;
/*      */     }
/* 4071 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 4072 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4077 */     PageContext pageContext = _jspx_page_context;
/* 4078 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4080 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4081 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 4082 */     _jspx_th_c_005fout_005f15.setParent(null);
/*      */     
/* 4084 */     _jspx_th_c_005fout_005f15.setValue("${param.resourcename}");
/* 4085 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 4086 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 4087 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4088 */       return true;
/*      */     }
/* 4090 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 4091 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4096 */     PageContext pageContext = _jspx_page_context;
/* 4097 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4099 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4100 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 4101 */     _jspx_th_c_005fout_005f16.setParent(null);
/*      */     
/* 4103 */     _jspx_th_c_005fout_005f16.setValue("${param.resourcename}");
/* 4104 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 4105 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 4106 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4107 */       return true;
/*      */     }
/* 4109 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 4110 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4115 */     PageContext pageContext = _jspx_page_context;
/* 4116 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4118 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4119 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 4120 */     _jspx_th_c_005fout_005f17.setParent(null);
/*      */     
/* 4122 */     _jspx_th_c_005fout_005f17.setValue("${param.resourcename}");
/* 4123 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 4124 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 4125 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4126 */       return true;
/*      */     }
/* 4128 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 4129 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4134 */     PageContext pageContext = _jspx_page_context;
/* 4135 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4137 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4138 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 4139 */     _jspx_th_c_005fout_005f18.setParent(null);
/*      */     
/* 4141 */     _jspx_th_c_005fout_005f18.setValue("${param.resourcename}");
/* 4142 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 4143 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 4144 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4145 */       return true;
/*      */     }
/* 4147 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 4148 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4153 */     PageContext pageContext = _jspx_page_context;
/* 4154 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4156 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4157 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 4158 */     _jspx_th_c_005fout_005f19.setParent(null);
/*      */     
/* 4160 */     _jspx_th_c_005fout_005f19.setValue("${param.resourceid}");
/* 4161 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 4162 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 4163 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4164 */       return true;
/*      */     }
/* 4166 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 4167 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f20(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4172 */     PageContext pageContext = _jspx_page_context;
/* 4173 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4175 */     OutTag _jspx_th_c_005fout_005f20 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4176 */     _jspx_th_c_005fout_005f20.setPageContext(_jspx_page_context);
/* 4177 */     _jspx_th_c_005fout_005f20.setParent(null);
/*      */     
/* 4179 */     _jspx_th_c_005fout_005f20.setValue("${param.resourcename}");
/* 4180 */     int _jspx_eval_c_005fout_005f20 = _jspx_th_c_005fout_005f20.doStartTag();
/* 4181 */     if (_jspx_th_c_005fout_005f20.doEndTag() == 5) {
/* 4182 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4183 */       return true;
/*      */     }
/* 4185 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f20);
/* 4186 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fdialchart_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4191 */     PageContext pageContext = _jspx_page_context;
/* 4192 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4194 */     DialChart _jspx_th_awolf_005fdialchart_005f0 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(DialChart.class);
/* 4195 */     _jspx_th_awolf_005fdialchart_005f0.setPageContext(_jspx_page_context);
/* 4196 */     _jspx_th_awolf_005fdialchart_005f0.setParent(null);
/*      */     
/* 4198 */     _jspx_th_awolf_005fdialchart_005f0.setDataSetProducer("dialGraph");
/*      */     
/* 4200 */     _jspx_th_awolf_005fdialchart_005f0.setWidth("150");
/*      */     
/* 4202 */     _jspx_th_awolf_005fdialchart_005f0.setHeight("148");
/*      */     
/* 4204 */     _jspx_th_awolf_005fdialchart_005f0.setLegend("false");
/*      */     
/* 4206 */     _jspx_th_awolf_005fdialchart_005f0.setXaxisLabel("");
/*      */     
/* 4208 */     _jspx_th_awolf_005fdialchart_005f0.setYaxisLabel("");
/*      */     
/* 4210 */     _jspx_th_awolf_005fdialchart_005f0.setDateFormat("HH:mm");
/* 4211 */     int _jspx_eval_awolf_005fdialchart_005f0 = _jspx_th_awolf_005fdialchart_005f0.doStartTag();
/* 4212 */     if (_jspx_eval_awolf_005fdialchart_005f0 != 0) {
/* 4213 */       if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4214 */         out = _jspx_page_context.pushBody();
/* 4215 */         _jspx_th_awolf_005fdialchart_005f0.setBodyContent((BodyContent)out);
/* 4216 */         _jspx_th_awolf_005fdialchart_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4219 */         out.write("\n                  ");
/* 4220 */         int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f0.doAfterBody();
/* 4221 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4224 */       if (_jspx_eval_awolf_005fdialchart_005f0 != 1) {
/* 4225 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4228 */     if (_jspx_th_awolf_005fdialchart_005f0.doEndTag() == 5) {
/* 4229 */       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4230 */       return true;
/*      */     }
/* 4232 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005fdialchart_005f0);
/* 4233 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f21(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4238 */     PageContext pageContext = _jspx_page_context;
/* 4239 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4241 */     OutTag _jspx_th_c_005fout_005f21 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4242 */     _jspx_th_c_005fout_005f21.setPageContext(_jspx_page_context);
/* 4243 */     _jspx_th_c_005fout_005f21.setParent(null);
/*      */     
/* 4245 */     _jspx_th_c_005fout_005f21.setValue("${param.resourceid}");
/* 4246 */     int _jspx_eval_c_005fout_005f21 = _jspx_th_c_005fout_005f21.doStartTag();
/* 4247 */     if (_jspx_th_c_005fout_005f21.doEndTag() == 5) {
/* 4248 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4249 */       return true;
/*      */     }
/* 4251 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f21);
/* 4252 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f22(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4257 */     PageContext pageContext = _jspx_page_context;
/* 4258 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4260 */     OutTag _jspx_th_c_005fout_005f22 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4261 */     _jspx_th_c_005fout_005f22.setPageContext(_jspx_page_context);
/* 4262 */     _jspx_th_c_005fout_005f22.setParent(null);
/*      */     
/* 4264 */     _jspx_th_c_005fout_005f22.setValue("${param.resourcename}");
/* 4265 */     int _jspx_eval_c_005fout_005f22 = _jspx_th_c_005fout_005f22.doStartTag();
/* 4266 */     if (_jspx_th_c_005fout_005f22.doEndTag() == 5) {
/* 4267 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4268 */       return true;
/*      */     }
/* 4270 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f22);
/* 4271 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_awolf_005fdialchart_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4276 */     PageContext pageContext = _jspx_page_context;
/* 4277 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4279 */     DialChart _jspx_th_awolf_005fdialchart_005f1 = (DialChart)this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.get(DialChart.class);
/* 4280 */     _jspx_th_awolf_005fdialchart_005f1.setPageContext(_jspx_page_context);
/* 4281 */     _jspx_th_awolf_005fdialchart_005f1.setParent(null);
/*      */     
/* 4283 */     _jspx_th_awolf_005fdialchart_005f1.setDataSetProducer("dialGraph");
/*      */     
/* 4285 */     _jspx_th_awolf_005fdialchart_005f1.setWidth("150");
/*      */     
/* 4287 */     _jspx_th_awolf_005fdialchart_005f1.setHeight("148");
/*      */     
/* 4289 */     _jspx_th_awolf_005fdialchart_005f1.setLegend("false");
/*      */     
/* 4291 */     _jspx_th_awolf_005fdialchart_005f1.setXaxisLabel("");
/*      */     
/* 4293 */     _jspx_th_awolf_005fdialchart_005f1.setYaxisLabel("");
/*      */     
/* 4295 */     _jspx_th_awolf_005fdialchart_005f1.setDateFormat("HH:mm");
/* 4296 */     int _jspx_eval_awolf_005fdialchart_005f1 = _jspx_th_awolf_005fdialchart_005f1.doStartTag();
/* 4297 */     if (_jspx_eval_awolf_005fdialchart_005f1 != 0) {
/* 4298 */       if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4299 */         out = _jspx_page_context.pushBody();
/* 4300 */         _jspx_th_awolf_005fdialchart_005f1.setBodyContent((BodyContent)out);
/* 4301 */         _jspx_th_awolf_005fdialchart_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4304 */         out.write("\n                  ");
/* 4305 */         int evalDoAfterBody = _jspx_th_awolf_005fdialchart_005f1.doAfterBody();
/* 4306 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4309 */       if (_jspx_eval_awolf_005fdialchart_005f1 != 1) {
/* 4310 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4313 */     if (_jspx_th_awolf_005fdialchart_005f1.doEndTag() == 5) {
/* 4314 */       this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 4315 */       return true;
/*      */     }
/* 4317 */     this._005fjspx_005ftagPool_005fawolf_005fdialchart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdateFormat_005fdataSetProducer.reuse(_jspx_th_awolf_005fdialchart_005f1);
/* 4318 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f23(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4323 */     PageContext pageContext = _jspx_page_context;
/* 4324 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4326 */     OutTag _jspx_th_c_005fout_005f23 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4327 */     _jspx_th_c_005fout_005f23.setPageContext(_jspx_page_context);
/* 4328 */     _jspx_th_c_005fout_005f23.setParent(null);
/*      */     
/* 4330 */     _jspx_th_c_005fout_005f23.setValue("${param.resourceid}");
/* 4331 */     int _jspx_eval_c_005fout_005f23 = _jspx_th_c_005fout_005f23.doStartTag();
/* 4332 */     if (_jspx_th_c_005fout_005f23.doEndTag() == 5) {
/* 4333 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4334 */       return true;
/*      */     }
/* 4336 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f23);
/* 4337 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f24(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4342 */     PageContext pageContext = _jspx_page_context;
/* 4343 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4345 */     OutTag _jspx_th_c_005fout_005f24 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4346 */     _jspx_th_c_005fout_005f24.setPageContext(_jspx_page_context);
/* 4347 */     _jspx_th_c_005fout_005f24.setParent(null);
/*      */     
/* 4349 */     _jspx_th_c_005fout_005f24.setValue("${param.resourcename}");
/* 4350 */     int _jspx_eval_c_005fout_005f24 = _jspx_th_c_005fout_005f24.doStartTag();
/* 4351 */     if (_jspx_th_c_005fout_005f24.doEndTag() == 5) {
/* 4352 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4353 */       return true;
/*      */     }
/* 4355 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f24);
/* 4356 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f25(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4361 */     PageContext pageContext = _jspx_page_context;
/* 4362 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4364 */     OutTag _jspx_th_c_005fout_005f25 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4365 */     _jspx_th_c_005fout_005f25.setPageContext(_jspx_page_context);
/* 4366 */     _jspx_th_c_005fout_005f25.setParent(null);
/*      */     
/* 4368 */     _jspx_th_c_005fout_005f25.setValue("${param.resourceid}");
/* 4369 */     int _jspx_eval_c_005fout_005f25 = _jspx_th_c_005fout_005f25.doStartTag();
/* 4370 */     if (_jspx_th_c_005fout_005f25.doEndTag() == 5) {
/* 4371 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 4372 */       return true;
/*      */     }
/* 4374 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f25);
/* 4375 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f26(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4380 */     PageContext pageContext = _jspx_page_context;
/* 4381 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4383 */     OutTag _jspx_th_c_005fout_005f26 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4384 */     _jspx_th_c_005fout_005f26.setPageContext(_jspx_page_context);
/* 4385 */     _jspx_th_c_005fout_005f26.setParent(null);
/*      */     
/* 4387 */     _jspx_th_c_005fout_005f26.setValue("${param.resourcename}");
/* 4388 */     int _jspx_eval_c_005fout_005f26 = _jspx_th_c_005fout_005f26.doStartTag();
/* 4389 */     if (_jspx_th_c_005fout_005f26.doEndTag() == 5) {
/* 4390 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 4391 */       return true;
/*      */     }
/* 4393 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f26);
/* 4394 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f27(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4399 */     PageContext pageContext = _jspx_page_context;
/* 4400 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4402 */     OutTag _jspx_th_c_005fout_005f27 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4403 */     _jspx_th_c_005fout_005f27.setPageContext(_jspx_page_context);
/* 4404 */     _jspx_th_c_005fout_005f27.setParent(null);
/*      */     
/* 4406 */     _jspx_th_c_005fout_005f27.setValue("${param.resourceid}");
/* 4407 */     int _jspx_eval_c_005fout_005f27 = _jspx_th_c_005fout_005f27.doStartTag();
/* 4408 */     if (_jspx_th_c_005fout_005f27.doEndTag() == 5) {
/* 4409 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 4410 */       return true;
/*      */     }
/* 4412 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f27);
/* 4413 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f28(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4418 */     PageContext pageContext = _jspx_page_context;
/* 4419 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4421 */     OutTag _jspx_th_c_005fout_005f28 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4422 */     _jspx_th_c_005fout_005f28.setPageContext(_jspx_page_context);
/* 4423 */     _jspx_th_c_005fout_005f28.setParent(null);
/*      */     
/* 4425 */     _jspx_th_c_005fout_005f28.setValue("${param.resourcename}");
/* 4426 */     int _jspx_eval_c_005fout_005f28 = _jspx_th_c_005fout_005f28.doStartTag();
/* 4427 */     if (_jspx_th_c_005fout_005f28.doEndTag() == 5) {
/* 4428 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 4429 */       return true;
/*      */     }
/* 4431 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f28);
/* 4432 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\sap\overview_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */