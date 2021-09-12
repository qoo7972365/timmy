/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.cam.CAMDBUtil;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.BreadcrumbUtil;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.CatchTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.ParseNumberTag;
/*      */ 
/*      */ public final class cam_005fchoosedomain_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   49 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   52 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   53 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   54 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   61 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   66 */     ArrayList list = null;
/*   67 */     StringBuffer sbf = new StringBuffer();
/*   68 */     ManagedApplication mo = new ManagedApplication();
/*   69 */     if (distinct)
/*      */     {
/*   71 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   75 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   78 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   80 */       ArrayList row = (ArrayList)list.get(i);
/*   81 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   82 */       if (distinct) {
/*   83 */         sbf.append(row.get(0));
/*      */       } else
/*   85 */         sbf.append(row.get(1));
/*   86 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   89 */     return sbf.toString(); }
/*      */   
/*   91 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*   94 */     if (severity == null)
/*      */     {
/*   96 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*   98 */     if (severity.equals("5"))
/*      */     {
/*  100 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  102 */     if (severity.equals("1"))
/*      */     {
/*  104 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  109 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  116 */     if (severity == null)
/*      */     {
/*  118 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  120 */     if (severity.equals("1"))
/*      */     {
/*  122 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  124 */     if (severity.equals("4"))
/*      */     {
/*  126 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  128 */     if (severity.equals("5"))
/*      */     {
/*  130 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  135 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  141 */     if (severity == null)
/*      */     {
/*  143 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  145 */     if (severity.equals("5"))
/*      */     {
/*  147 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  149 */     if (severity.equals("1"))
/*      */     {
/*  151 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  155 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  161 */     if (severity == null)
/*      */     {
/*  163 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  165 */     if (severity.equals("1"))
/*      */     {
/*  167 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  169 */     if (severity.equals("4"))
/*      */     {
/*  171 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  173 */     if (severity.equals("5"))
/*      */     {
/*  175 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  179 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  185 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  191 */     if (severity == 5)
/*      */     {
/*  193 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  195 */     if (severity == 1)
/*      */     {
/*  197 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  202 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  208 */     if (severity == null)
/*      */     {
/*  210 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  212 */     if (severity.equals("5"))
/*      */     {
/*  214 */       if (isAvailability) {
/*  215 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  218 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  221 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  223 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  225 */     if (severity.equals("1"))
/*      */     {
/*  227 */       if (isAvailability) {
/*  228 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  231 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  238 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  245 */     if (severity == null)
/*      */     {
/*  247 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  249 */     if (severity.equals("5"))
/*      */     {
/*  251 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  253 */     if (severity.equals("4"))
/*      */     {
/*  255 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  257 */     if (severity.equals("1"))
/*      */     {
/*  259 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  264 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  270 */     if (severity == null)
/*      */     {
/*  272 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  274 */     if (severity.equals("5"))
/*      */     {
/*  276 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  278 */     if (severity.equals("4"))
/*      */     {
/*  280 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  282 */     if (severity.equals("1"))
/*      */     {
/*  284 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  289 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  296 */     if (severity == null)
/*      */     {
/*  298 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  300 */     if (severity.equals("5"))
/*      */     {
/*  302 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  304 */     if (severity.equals("4"))
/*      */     {
/*  306 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  308 */     if (severity.equals("1"))
/*      */     {
/*  310 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  315 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  323 */     StringBuffer out = new StringBuffer();
/*  324 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  325 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  326 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  327 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  328 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  329 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  330 */     out.append("</tr>");
/*  331 */     out.append("</form></table>");
/*  332 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  339 */     if (val == null)
/*      */     {
/*  341 */       return "-";
/*      */     }
/*      */     
/*  344 */     String ret = FormatUtil.formatNumber(val);
/*  345 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  346 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  349 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  353 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  361 */     StringBuffer out = new StringBuffer();
/*  362 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  363 */     out.append("<tr>");
/*  364 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  366 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  368 */     out.append("</tr>");
/*  369 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  373 */       if (j % 2 == 0)
/*      */       {
/*  375 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  379 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  382 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  384 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  387 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  391 */       out.append("</tr>");
/*      */     }
/*  393 */     out.append("</table>");
/*  394 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  395 */     out.append("<tr>");
/*  396 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  397 */     out.append("</tr>");
/*  398 */     out.append("</table>");
/*  399 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  405 */     StringBuffer out = new StringBuffer();
/*  406 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  407 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  408 */     out.append("<tr>");
/*  409 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  410 */     out.append("<tr>");
/*  411 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  412 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  413 */     out.append("</tr>");
/*  414 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  417 */       out.append("<tr>");
/*  418 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  419 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  420 */       out.append("</tr>");
/*      */     }
/*      */     
/*  423 */     out.append("</table>");
/*  424 */     out.append("</table>");
/*  425 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  430 */     if (severity.equals("0"))
/*      */     {
/*  432 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  436 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  443 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  456 */     StringBuffer out = new StringBuffer();
/*  457 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  458 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  460 */       out.append("<tr>");
/*  461 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  462 */       out.append("</tr>");
/*      */       
/*      */ 
/*  465 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  467 */         String borderclass = "";
/*      */         
/*      */ 
/*  470 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  472 */         out.append("<tr>");
/*      */         
/*  474 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  475 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  476 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  482 */     out.append("</table><br>");
/*  483 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  484 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  486 */       List sLinks = secondLevelOfLinks[0];
/*  487 */       List sText = secondLevelOfLinks[1];
/*  488 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  491 */         out.append("<tr>");
/*  492 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  493 */         out.append("</tr>");
/*  494 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  496 */           String borderclass = "";
/*      */           
/*      */ 
/*  499 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  501 */           out.append("<tr>");
/*      */           
/*  503 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  504 */           if (sLinks.get(i).toString().length() == 0) {
/*  505 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  508 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  510 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  514 */     out.append("</table>");
/*  515 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  522 */     StringBuffer out = new StringBuffer();
/*  523 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  524 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  526 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  528 */         out.append("<tr>");
/*  529 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  530 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  534 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  536 */           String borderclass = "";
/*      */           
/*      */ 
/*  539 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  541 */           out.append("<tr>");
/*      */           
/*  543 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  544 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  545 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  548 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  551 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  556 */     out.append("</table><br>");
/*  557 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  558 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  560 */       List sLinks = secondLevelOfLinks[0];
/*  561 */       List sText = secondLevelOfLinks[1];
/*  562 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  565 */         out.append("<tr>");
/*  566 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  567 */         out.append("</tr>");
/*  568 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  570 */           String borderclass = "";
/*      */           
/*      */ 
/*  573 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  575 */           out.append("<tr>");
/*      */           
/*  577 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  578 */           if (sLinks.get(i).toString().length() == 0) {
/*  579 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  582 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  584 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  588 */     out.append("</table>");
/*  589 */     return out.toString();
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
/*  602 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  605 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  608 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  611 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  614 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  617 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  620 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  623 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  631 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  636 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  641 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  646 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  651 */     if (val != null)
/*      */     {
/*  653 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  657 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  662 */     if (val == null) {
/*  663 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  667 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  672 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  678 */     if (val != null)
/*      */     {
/*  680 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  684 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  690 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  695 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  699 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  704 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  709 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  714 */     String hostaddress = "";
/*  715 */     String ip = request.getHeader("x-forwarded-for");
/*  716 */     if (ip == null)
/*  717 */       ip = request.getRemoteAddr();
/*  718 */     InetAddress add = null;
/*  719 */     if (ip.equals("127.0.0.1")) {
/*  720 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  724 */       add = InetAddress.getByName(ip);
/*      */     }
/*  726 */     hostaddress = add.getHostName();
/*  727 */     if (hostaddress.indexOf('.') != -1) {
/*  728 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  729 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  733 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  738 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  744 */     if (severity == null)
/*      */     {
/*  746 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  748 */     if (severity.equals("5"))
/*      */     {
/*  750 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  752 */     if (severity.equals("1"))
/*      */     {
/*  754 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  759 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  764 */     ResultSet set = null;
/*  765 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  766 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  768 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  769 */       if (set.next()) { String str1;
/*  770 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  771 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  774 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  779 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  782 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  784 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  788 */     StringBuffer rca = new StringBuffer();
/*  789 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  790 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  793 */     int rcalength = key.length();
/*  794 */     String split = "6. ";
/*  795 */     int splitPresent = key.indexOf(split);
/*  796 */     String div1 = "";String div2 = "";
/*  797 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  799 */       if (rcalength > 180) {
/*  800 */         rca.append("<span class=\"rca-critical-text\">");
/*  801 */         getRCATrimmedText(key, rca);
/*  802 */         rca.append("</span>");
/*      */       } else {
/*  804 */         rca.append("<span class=\"rca-critical-text\">");
/*  805 */         rca.append(key);
/*  806 */         rca.append("</span>");
/*      */       }
/*  808 */       return rca.toString();
/*      */     }
/*  810 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  811 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  812 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  813 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  814 */     getRCATrimmedText(div1, rca);
/*  815 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  818 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  819 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  820 */     getRCATrimmedText(div2, rca);
/*  821 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  823 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  828 */     String[] st = msg.split("<br>");
/*  829 */     for (int i = 0; i < st.length; i++) {
/*  830 */       String s = st[i];
/*  831 */       if (s.length() > 180) {
/*  832 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  834 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  838 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  839 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  841 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  845 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  846 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  847 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  850 */       if (key == null) {
/*  851 */         return ret;
/*      */       }
/*      */       
/*  854 */       if (com.adventnet.appmanager.util.DBUtil.searchLinks.containsKey(key)) {
/*  855 */         return "<a href=\"" + (String)com.adventnet.appmanager.util.DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  858 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  859 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  860 */       set = AMConnectionPool.executeQueryStmt(query);
/*  861 */       if (set.next())
/*      */       {
/*  863 */         String helpLink = set.getString("LINK");
/*  864 */         com.adventnet.appmanager.util.DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  867 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  873 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  892 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  883 */         if (set != null) {
/*  884 */           AMConnectionPool.closeStatement(set);
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
/*  898 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  899 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  901 */       String entityStr = (String)keys.nextElement();
/*  902 */       String mmessage = temp.getProperty(entityStr);
/*  903 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  904 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  906 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  912 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  913 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  915 */       String entityStr = (String)keys.nextElement();
/*  916 */       String mmessage = temp.getProperty(entityStr);
/*  917 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  918 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  920 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  925 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  935 */     String des = new String();
/*  936 */     while (str.indexOf(find) != -1) {
/*  937 */       des = des + str.substring(0, str.indexOf(find));
/*  938 */       des = des + replace;
/*  939 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  941 */     des = des + str;
/*  942 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  949 */       if (alert == null)
/*      */       {
/*  951 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  953 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  955 */         return "&nbsp;";
/*      */       }
/*      */       
/*  958 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  960 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  963 */       int rcalength = test.length();
/*  964 */       if (rcalength < 300)
/*      */       {
/*  966 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  970 */       StringBuffer out = new StringBuffer();
/*  971 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  972 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  973 */       out.append("</div>");
/*  974 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  975 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  976 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  981 */       ex.printStackTrace();
/*      */     }
/*  983 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  989 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/*  994 */     ArrayList attribIDs = new ArrayList();
/*  995 */     ArrayList resIDs = new ArrayList();
/*  996 */     ArrayList entitylist = new ArrayList();
/*      */     
/*  998 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1000 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1002 */       String resourceid = "";
/* 1003 */       String resourceType = "";
/* 1004 */       if (type == 2) {
/* 1005 */         resourceid = (String)row.get(0);
/* 1006 */         resourceType = (String)row.get(3);
/*      */       }
/* 1008 */       else if (type == 3) {
/* 1009 */         resourceid = (String)row.get(0);
/* 1010 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1013 */         resourceid = (String)row.get(6);
/* 1014 */         resourceType = (String)row.get(7);
/*      */       }
/* 1016 */       resIDs.add(resourceid);
/* 1017 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1018 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1020 */       String healthentity = null;
/* 1021 */       String availentity = null;
/* 1022 */       if (healthid != null) {
/* 1023 */         healthentity = resourceid + "_" + healthid;
/* 1024 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1027 */       if (availid != null) {
/* 1028 */         availentity = resourceid + "_" + availid;
/* 1029 */         entitylist.add(availentity);
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
/* 1043 */     Properties alert = getStatus(entitylist);
/* 1044 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1049 */     int size = monitorList.size();
/*      */     
/* 1051 */     String[] severity = new String[size];
/*      */     
/* 1053 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1055 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1056 */       String resourceName1 = (String)row1.get(7);
/* 1057 */       String resourceid1 = (String)row1.get(6);
/* 1058 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1059 */       if (severity[j] == null)
/*      */       {
/* 1061 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1065 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1067 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1069 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1072 */         if (sev > 0) {
/* 1073 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1074 */           monitorList.set(k, monitorList.get(j));
/* 1075 */           monitorList.set(j, t);
/* 1076 */           String temp = severity[k];
/* 1077 */           severity[k] = severity[j];
/* 1078 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1084 */     int z = 0;
/* 1085 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1088 */       int i = 0;
/* 1089 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1092 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1096 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1100 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1102 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1105 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1109 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1112 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1113 */       String resourceName1 = (String)row1.get(7);
/* 1114 */       String resourceid1 = (String)row1.get(6);
/* 1115 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1116 */       if (hseverity[j] == null)
/*      */       {
/* 1118 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1123 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1125 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1128 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1131 */         if (hsev > 0) {
/* 1132 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1133 */           monitorList.set(k, monitorList.get(j));
/* 1134 */           monitorList.set(j, t);
/* 1135 */           String temp1 = hseverity[k];
/* 1136 */           hseverity[k] = hseverity[j];
/* 1137 */           hseverity[j] = temp1;
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
/* 1149 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1150 */     boolean forInventory = false;
/* 1151 */     String trdisplay = "none";
/* 1152 */     String plusstyle = "inline";
/* 1153 */     String minusstyle = "none";
/* 1154 */     String haidTopLevel = "";
/* 1155 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1157 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1159 */         haidTopLevel = request.getParameter("haid");
/* 1160 */         forInventory = true;
/* 1161 */         trdisplay = "table-row;";
/* 1162 */         plusstyle = "none";
/* 1163 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1170 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1173 */     ArrayList listtoreturn = new ArrayList();
/* 1174 */     StringBuffer toreturn = new StringBuffer();
/* 1175 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1176 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1177 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1179 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1181 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1182 */       String childresid = (String)singlerow.get(0);
/* 1183 */       String childresname = (String)singlerow.get(1);
/* 1184 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1185 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1186 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1187 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1188 */       String unmanagestatus = (String)singlerow.get(5);
/* 1189 */       String actionstatus = (String)singlerow.get(6);
/* 1190 */       String linkclass = "monitorgp-links";
/* 1191 */       String titleforres = childresname;
/* 1192 */       String titilechildresname = childresname;
/* 1193 */       String childimg = "/images/trcont.png";
/* 1194 */       String flag = "enable";
/* 1195 */       String dcstarted = (String)singlerow.get(8);
/* 1196 */       String configMonitor = "";
/* 1197 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1198 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1200 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1202 */       if (singlerow.get(7) != null)
/*      */       {
/* 1204 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1206 */       String haiGroupType = "0";
/* 1207 */       if ("HAI".equals(childtype))
/*      */       {
/* 1209 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1211 */       childimg = "/images/trend.png";
/* 1212 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1213 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1214 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1216 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1218 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1220 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1221 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1224 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1226 */         linkclass = "disabledtext";
/* 1227 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1229 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1230 */       String availmouseover = "";
/* 1231 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1233 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1235 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1236 */       String healthmouseover = "";
/* 1237 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1239 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1242 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1243 */       int spacing = 0;
/* 1244 */       if (level >= 1)
/*      */       {
/* 1246 */         spacing = 40 * level;
/*      */       }
/* 1248 */       if (childtype.equals("HAI"))
/*      */       {
/* 1250 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1251 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1252 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1254 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1255 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1256 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1257 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1258 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1259 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1260 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1261 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1262 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1263 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1264 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1266 */         if (!forInventory)
/*      */         {
/* 1268 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1271 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1273 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1275 */           actions = editlink + actions;
/*      */         }
/* 1277 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1279 */           actions = actions + associatelink;
/*      */         }
/* 1281 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1282 */         String arrowimg = "";
/* 1283 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1285 */           actions = "";
/* 1286 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1287 */           checkbox = "";
/* 1288 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1290 */         if (isIt360)
/*      */         {
/* 1292 */           actionimg = "";
/* 1293 */           actions = "";
/* 1294 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1295 */           checkbox = "";
/*      */         }
/*      */         
/* 1298 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1300 */           actions = "";
/*      */         }
/* 1302 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1304 */           checkbox = "";
/*      */         }
/*      */         
/* 1307 */         String resourcelink = "";
/*      */         
/* 1309 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1311 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1315 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1318 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1319 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1320 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1321 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1322 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1323 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1324 */         if (!isIt360)
/*      */         {
/* 1326 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1330 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1333 */         toreturn.append("</tr>");
/* 1334 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1336 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1337 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1341 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1342 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1345 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1349 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1351 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1352 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1353 */             toreturn.append(assocMessage);
/* 1354 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1355 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1356 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1357 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1363 */         String resourcelink = null;
/* 1364 */         boolean hideEditLink = false;
/* 1365 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1367 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1368 */           hideEditLink = true;
/* 1369 */           if (isIt360)
/*      */           {
/* 1371 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1375 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1377 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1379 */           hideEditLink = true;
/* 1380 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1381 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1386 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1389 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1390 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1391 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1392 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1393 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1394 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1395 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1396 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1397 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1398 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1399 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1400 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1401 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1403 */         if (hideEditLink)
/*      */         {
/* 1405 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1407 */         if (!forInventory)
/*      */         {
/* 1409 */           removefromgroup = "";
/*      */         }
/* 1411 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1412 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1413 */           actions = actions + configcustomfields;
/*      */         }
/* 1415 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1417 */           actions = editlink + actions;
/*      */         }
/* 1419 */         String managedLink = "";
/* 1420 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1422 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1423 */           actions = "";
/* 1424 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1425 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1428 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1430 */           checkbox = "";
/*      */         }
/*      */         
/* 1433 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1435 */           actions = "";
/*      */         }
/* 1437 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1438 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1439 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1440 */         if (isIt360)
/*      */         {
/* 1442 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1446 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1450 */         if (!isIt360)
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1458 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1461 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1468 */       StringBuilder toreturn = new StringBuilder();
/* 1469 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1470 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1471 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1472 */       String title = "";
/* 1473 */       message = EnterpriseUtil.decodeString(message);
/* 1474 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1475 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1476 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1478 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1480 */       else if ("5".equals(severity))
/*      */       {
/* 1482 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1486 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1488 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1489 */       toreturn.append(v);
/*      */       
/* 1491 */       toreturn.append(link);
/* 1492 */       if (severity == null)
/*      */       {
/* 1494 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1496 */       else if (severity.equals("5"))
/*      */       {
/* 1498 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1500 */       else if (severity.equals("4"))
/*      */       {
/* 1502 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1504 */       else if (severity.equals("1"))
/*      */       {
/* 1506 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1511 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1513 */       toreturn.append("</a>");
/* 1514 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1518 */       ex.printStackTrace();
/*      */     }
/* 1520 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1527 */       StringBuilder toreturn = new StringBuilder();
/* 1528 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1529 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1530 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1531 */       if (message == null)
/*      */       {
/* 1533 */         message = "";
/*      */       }
/*      */       
/* 1536 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1537 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1539 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1540 */       toreturn.append(v);
/*      */       
/* 1542 */       toreturn.append(link);
/*      */       
/* 1544 */       if (severity == null)
/*      */       {
/* 1546 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1548 */       else if (severity.equals("5"))
/*      */       {
/* 1550 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1552 */       else if (severity.equals("1"))
/*      */       {
/* 1554 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1559 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1561 */       toreturn.append("</a>");
/* 1562 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1568 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1571 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1572 */     if (invokeActions != null) {
/* 1573 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1574 */       while (iterator.hasNext()) {
/* 1575 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1576 */         if (actionmap.containsKey(actionid)) {
/* 1577 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1582 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1586 */     String actionLink = "";
/* 1587 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1588 */     String query = "";
/* 1589 */     ResultSet rs = null;
/* 1590 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1591 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1592 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1593 */       actionLink = "method=" + methodName;
/*      */     }
/* 1595 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1596 */       actionLink = methodName;
/*      */     }
/* 1598 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1599 */     Iterator itr = methodarglist.iterator();
/* 1600 */     boolean isfirstparam = true;
/* 1601 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1602 */     while (itr.hasNext()) {
/* 1603 */       HashMap argmap = (HashMap)itr.next();
/* 1604 */       String argtype = (String)argmap.get("TYPE");
/* 1605 */       String argname = (String)argmap.get("IDENTITY");
/* 1606 */       String paramname = (String)argmap.get("PARAMETER");
/* 1607 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1608 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1609 */         isfirstparam = false;
/* 1610 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1612 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1616 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1620 */         actionLink = actionLink + "&";
/*      */       }
/* 1622 */       String paramValue = null;
/* 1623 */       String tempargname = argname;
/* 1624 */       if (commonValues.getProperty(tempargname) != null) {
/* 1625 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1628 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1629 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1630 */           if (dbType.equals("mysql")) {
/* 1631 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1634 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1636 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1638 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1639 */             if (rs.next()) {
/* 1640 */               paramValue = rs.getString("VALUE");
/* 1641 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1645 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1649 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1652 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1657 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1658 */           paramValue = rowId;
/*      */         }
/* 1660 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1661 */           paramValue = managedObjectName;
/*      */         }
/* 1663 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1664 */           paramValue = resID;
/*      */         }
/* 1666 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1667 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1670 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1672 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1673 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1674 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1676 */     return actionLink;
/*      */   }
/*      */   
/* 1679 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1680 */     String dependentAttribute = null;
/* 1681 */     String align = "left";
/*      */     
/* 1683 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1684 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1685 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1686 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1687 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1688 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1689 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1690 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1691 */       align = "center";
/*      */     }
/*      */     
/* 1694 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1695 */     String actualdata = "";
/*      */     
/* 1697 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1698 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1699 */         actualdata = availValue;
/*      */       }
/* 1701 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1702 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1706 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1707 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1710 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1716 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1717 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1718 */       toreturn.append("<table>");
/* 1719 */       toreturn.append("<tr>");
/* 1720 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1721 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1722 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1723 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1724 */         String toolTip = "";
/* 1725 */         String hideClass = "";
/* 1726 */         String textStyle = "";
/* 1727 */         boolean isreferenced = true;
/* 1728 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1729 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1730 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1731 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1733 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1734 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1735 */           while (valueList.hasMoreTokens()) {
/* 1736 */             String dependentVal = valueList.nextToken();
/* 1737 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1738 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1739 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1741 */               toolTip = "";
/* 1742 */               hideClass = "";
/* 1743 */               isreferenced = false;
/* 1744 */               textStyle = "disabledtext";
/* 1745 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1749 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1750 */           toolTip = "";
/* 1751 */           hideClass = "";
/* 1752 */           isreferenced = false;
/* 1753 */           textStyle = "disabledtext";
/* 1754 */           if (dependentImageMap != null) {
/* 1755 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1756 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1759 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1763 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1764 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1765 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1766 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1767 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1768 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1770 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1771 */           if (isreferenced) {
/* 1772 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1776 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1777 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1778 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1779 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1780 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1781 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1783 */           toreturn.append("</span>");
/* 1784 */           toreturn.append("</a>");
/* 1785 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1788 */       toreturn.append("</tr>");
/* 1789 */       toreturn.append("</table>");
/* 1790 */       toreturn.append("</td>");
/*      */     } else {
/* 1792 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1795 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1799 */     String colTime = null;
/* 1800 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1801 */     if ((rows != null) && (rows.size() > 0)) {
/* 1802 */       Iterator<String> itr = rows.iterator();
/* 1803 */       String maxColQuery = "";
/* 1804 */       for (;;) { if (itr.hasNext()) {
/* 1805 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1806 */           ResultSet maxCol = null;
/*      */           try {
/* 1808 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1809 */             while (maxCol.next()) {
/* 1810 */               if (colTime == null) {
/* 1811 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1814 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1823 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1825 */               if (maxCol != null)
/* 1826 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1828 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1823 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1825 */               if (maxCol != null)
/* 1826 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1828 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1833 */     return colTime;
/*      */   }
/*      */   
/* 1836 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1837 */     tablename = null;
/* 1838 */     ResultSet rsTable = null;
/* 1839 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1841 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1842 */       while (rsTable.next()) {
/* 1843 */         tablename = rsTable.getString("DATATABLE");
/* 1844 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1845 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1858 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1849 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1852 */         if (rsTable != null)
/* 1853 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1855 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1861 */     String argsList = "";
/* 1862 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1864 */       if (showArgsMap.get(row) != null) {
/* 1865 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1866 */         if (showArgslist != null) {
/* 1867 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1868 */             if (argsList.trim().equals("")) {
/* 1869 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1872 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1879 */       e.printStackTrace();
/* 1880 */       return "";
/*      */     }
/* 1882 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1887 */     String argsList = "";
/* 1888 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1891 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1893 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1894 */         if (hideArgsList != null)
/*      */         {
/* 1896 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1898 */             if (argsList.trim().equals(""))
/*      */             {
/* 1900 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1904 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1912 */       ex.printStackTrace();
/*      */     }
/* 1914 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1918 */     StringBuilder toreturn = new StringBuilder();
/* 1919 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1926 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1927 */       Iterator itr = tActionList.iterator();
/* 1928 */       while (itr.hasNext()) {
/* 1929 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1930 */         String confirmmsg = "";
/* 1931 */         String link = "";
/* 1932 */         String isJSP = "NO";
/* 1933 */         HashMap tactionMap = (HashMap)itr.next();
/* 1934 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1935 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1936 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1937 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1938 */           (actionmap.containsKey(actionId))) {
/* 1939 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1940 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1941 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1942 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1943 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1945 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1951 */           if (isTableAction) {
/* 1952 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1955 */             tableName = "Link";
/* 1956 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1957 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1958 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1959 */             toreturn.append("</a></td>");
/*      */           }
/* 1961 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1962 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1963 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1964 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1970 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1976 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1978 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1979 */       Properties prop = (Properties)node.getUserObject();
/* 1980 */       String mgID = prop.getProperty("label");
/* 1981 */       String mgName = prop.getProperty("value");
/* 1982 */       String isParent = prop.getProperty("isParent");
/* 1983 */       int mgIDint = Integer.parseInt(mgID);
/* 1984 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1986 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1988 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1989 */       if (node.getChildCount() > 0)
/*      */       {
/* 1991 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 1993 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 1995 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 1997 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2001 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2006 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2008 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2010 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2012 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2016 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2019 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2020 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2022 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2026 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2028 */       if (node.getChildCount() > 0)
/*      */       {
/* 2030 */         builder.append("<UL>");
/* 2031 */         printMGTree(node, builder);
/* 2032 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2037 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2038 */     StringBuffer toReturn = new StringBuffer();
/* 2039 */     String table = "-";
/*      */     try {
/* 2041 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2042 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2043 */       float total = 0.0F;
/* 2044 */       while (it.hasNext()) {
/* 2045 */         String attName = (String)it.next();
/* 2046 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2047 */         boolean roundOffData = false;
/* 2048 */         if ((data != null) && (!data.equals(""))) {
/* 2049 */           if (data.indexOf(",") != -1) {
/* 2050 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2053 */             float value = Float.parseFloat(data);
/* 2054 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2057 */             total += value;
/* 2058 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2061 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2066 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2067 */       while (attVsWidthList.hasNext()) {
/* 2068 */         String attName = (String)attVsWidthList.next();
/* 2069 */         String data = (String)attVsWidthProps.get(attName);
/* 2070 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2071 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2072 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2073 */         String className = (String)graphDetails.get("ClassName");
/* 2074 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2075 */         if (percentage < 1.0F)
/*      */         {
/* 2077 */           data = percentage + "";
/*      */         }
/* 2079 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2081 */       if (toReturn.length() > 0) {
/* 2082 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2086 */       e.printStackTrace();
/*      */     }
/* 2088 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2094 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2095 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2096 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2097 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2098 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2099 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2100 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2101 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2102 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2105 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2106 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2107 */       splitvalues[0] = multiplecondition.toString();
/* 2108 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2111 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2116 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2117 */     if (thresholdType != 3) {
/* 2118 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2119 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2120 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2121 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2122 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2123 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2125 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2126 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2127 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2128 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2129 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2130 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2132 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2133 */     if (updateSelected != null) {
/* 2134 */       updateSelected[0] = "selected";
/*      */     }
/* 2136 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2141 */       StringBuffer toreturn = new StringBuffer("");
/* 2142 */       if (commaSeparatedMsgId != null) {
/* 2143 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2144 */         int count = 0;
/* 2145 */         while (msgids.hasMoreTokens()) {
/* 2146 */           String id = msgids.nextToken();
/* 2147 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2148 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2149 */           count++;
/* 2150 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2151 */             if (toreturn.length() == 0) {
/* 2152 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2154 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2155 */             if (!image.trim().equals("")) {
/* 2156 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2158 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2159 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2162 */         if (toreturn.length() > 0) {
/* 2163 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2167 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2170 */       e.printStackTrace(); }
/* 2171 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2177 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2183 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2184 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2202 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2206 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2217 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2221 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2224 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.release();
/* 2227 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.release();
/* 2229 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2237 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2240 */     JspWriter out = null;
/* 2241 */     Object page = this;
/* 2242 */     JspWriter _jspx_out = null;
/* 2243 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2247 */       response.setContentType("text/html;charset=UTF-8");
/* 2248 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2250 */       _jspx_page_context = pageContext;
/* 2251 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2252 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2253 */       session = pageContext.getSession();
/* 2254 */       out = pageContext.getOut();
/* 2255 */       _jspx_out = out;
/*      */       
/* 2257 */       out.write("<!DOCTYPE html>\n");
/* 2258 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n<!-- $Id$ -->\n\n");
/*      */       
/* 2260 */       request.setAttribute("HelpKey", "Adding JMX MBeans Attributes");
/*      */       
/* 2262 */       out.write("\n\n\n\n\n\n\n\n \n \n\n\n\n\n\n");
/* 2263 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2265 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2266 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2269 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2271 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2275 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2276 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2277 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2278 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2281 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2282 */         String available = null;
/* 2283 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2284 */         out.write(10);
/*      */         
/* 2286 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2287 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2290 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2292 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2296 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2297 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2298 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2299 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2302 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2303 */           String unavailable = null;
/* 2304 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2305 */           out.write(10);
/*      */           
/* 2307 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2308 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2311 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2313 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2317 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2318 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2319 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2320 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2323 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2324 */             String unmanaged = null;
/* 2325 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2326 */             out.write(10);
/*      */             
/* 2328 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2329 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2332 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2334 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2338 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2339 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2340 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2341 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2344 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2345 */               String scheduled = null;
/* 2346 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2347 */               out.write(10);
/*      */               
/* 2349 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2350 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2355 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2359 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2360 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2361 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2362 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2365 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2366 */                 String critical = null;
/* 2367 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2368 */                 out.write(10);
/*      */                 
/* 2370 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2371 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2374 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2376 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2380 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2381 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2382 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2383 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2386 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2387 */                   String clear = null;
/* 2388 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2389 */                   out.write(10);
/*      */                   
/* 2391 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2392 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2395 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2397 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2401 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2402 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2403 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2404 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2407 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2408 */                     String warning = null;
/* 2409 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2410 */                     out.write(10);
/* 2411 */                     out.write(10);
/*      */                     
/* 2413 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2414 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2416 */                     out.write(10);
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/* 2419 */                     out.write(10);
/* 2420 */                     out.write(10);
/* 2421 */                     out.write(10);
/*      */                     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2427 */                     String camID = (String)request.getAttribute("camid");
/* 2428 */                     String haid = (String)request.getAttribute("haid");
/* 2429 */                     String screenID = (String)request.getAttribute("screenid");
/*      */                     
/* 2431 */                     String camname = "";
/* 2432 */                     String camDescription = "";
/* 2433 */                     Map resourceInfoFromResourcePage = null;
/*      */                     
/* 2435 */                     if ("true".equals((String)request.getAttribute("isfromresourcepage"))) {
/* 2436 */                       resourceInfoFromResourcePage = CAMDBUtil.getAMMOInfo(Integer.parseInt(camID));
/*      */                     } else {
/* 2438 */                       List list = CAMDBUtil.getCAMDetails(camID);
/* 2439 */                       camname = (String)list.get(0);
/* 2440 */                       camDescription = (String)list.get(2);
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 2445 */                     String hostName = (String)request.getAttribute("hostname");
/* 2446 */                     String portNumber = (String)request.getAttribute("portnumber");
/* 2447 */                     String resourceType = (String)request.getAttribute("resourcetype");
/* 2448 */                     String resourceID = (String)request.getAttribute("resourceid");
/* 2449 */                     String quickNote = FormatUtil.getString("am.webclient.cam.choosedomain.quicknote");
/* 2450 */                     String topDesc = FormatUtil.getString("a.webclient.cam.choosedomain.quicknote");
/* 2451 */                     request.setAttribute("quicknote", quickNote);
/* 2452 */                     List screenInfo = CAMDBUtil.getScreenInfo(Long.parseLong(screenID));
/*      */                     
/* 2454 */                     request.setAttribute("needCustomizeScreen", "hint for cam_leftlinks.jsp");
/* 2455 */                     request.setAttribute("needDeleteScreen", "hint for cam_leftlinks.jsp");
/*      */                     
/*      */ 
/*      */ 
/* 2459 */                     out.write(10);
/* 2460 */                     out.write(10);
/* 2461 */                     out.write(10);
/*      */                     
/* 2463 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2464 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2465 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2467 */                     _jspx_th_tiles_005finsert_005f0.setPage("/jsp/ServerLayout.jsp");
/* 2468 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2469 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2471 */                         out.write(10);
/* 2472 */                         if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2474 */                         out.write(10);
/* 2475 */                         if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2477 */                         out.write(10);
/* 2478 */                         out.write(10);
/* 2479 */                         if (_jspx_meth_tiles_005fput_005f2(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2481 */                         out.write(10);
/* 2482 */                         out.write(10);
/*      */                         
/* 2484 */                         PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2485 */                         _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2486 */                         _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2488 */                         _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */                         
/* 2490 */                         _jspx_th_tiles_005fput_005f3.setType("string");
/* 2491 */                         int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2492 */                         if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2493 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2494 */                             out = _jspx_page_context.pushBody();
/* 2495 */                             _jspx_th_tiles_005fput_005f3.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2496 */                             _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2499 */                             out.write("\n\n<SCRIPT>\n\nfunction submitAttribs() \n{\n    if(document.CAMShowMBeansOfDomainForm.modeOfSubmission.value==\"2\")\n    {\n\tif(trimAll(document.CAMShowMBeansOfDomainForm.groupName.value)==\"\")\n\t{\n\t\talert('");
/* 2500 */                             out.print(FormatUtil.getString("am.webclient.cam.alertmbeanobjectname"));
/* 2501 */                             out.write("');\n\t\treturn;\n\t }\n\t if(trimAll(document.CAMShowMBeansOfDomainForm.attributenames.value)==\"\")\n\t {\n\t    alert('");
/* 2502 */                             out.print(FormatUtil.getString("am.webclient.cam.alertmbeanattribute"));
/* 2503 */                             out.write("');\n\t    return;\n\t  }\n\t  document.CAMShowMBeansOfDomainForm.method.value='addMBeanAttributesDirectly';\n     }\n     document.CAMShowMBeansOfDomainForm.submit();\n}\n\n\nfunction cancel() {\n  \n");
/*      */                             
/* 2505 */                             IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2506 */                             _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2507 */                             _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2509 */                             _jspx_th_c_005fif_005f0.setTest("${(!empty isfromresourcepage)}");
/* 2510 */                             int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2511 */                             if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                               for (;;) {
/* 2513 */                                 out.write("    \n    var url = \"/showresource.do?resourceid=");
/* 2514 */                                 out.print(camID);
/* 2515 */                                 out.write("&method=showResourceForResourceID\";\n    document.CAMShowMBeansOfDomainForm.action = url;\n");
/* 2516 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2517 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2521 */                             if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2522 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                             }
/*      */                             
/* 2525 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2526 */                             out.write("\n  \n");
/* 2527 */                             if (_jspx_meth_c_005fif_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2529 */                             out.write("\n  \n\t\n}\n\nfunction handleOpen(radbutton)\n{\n    \n  if(document.getElementById('directadd').style.display == 'none')\n  {\n      document.getElementById('directadd').style.display='block';\n      document.CAMShowMBeansOfDomainForm.submitbutton.value='");
/* 2530 */                             out.print(FormatUtil.getString("am.webclient.cam.showmbeans.addattributes"));
/* 2531 */                             out.write("';\n      document.CAMShowMBeansOfDomainForm.modeOfSubmission.value=2;\n  }\n}\n\nfunction handleClose()\n{\n   if(document.getElementById('directadd').style.display == 'block')\n   {\n  \tdocument.getElementById('directadd').style.display='none';\n  \tdocument.CAMShowMBeansOfDomainForm.submitbutton.value='");
/* 2532 */                             out.print(FormatUtil.getString("am.webclient.cam.showmbeans.button"));
/* 2533 */                             out.write("';\n  \tdocument.CAMShowMBeansOfDomainForm.modeOfSubmission.value=1;\n   }\n}\n\n</SCRIPT>\n\n\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    ");
/*      */                             
/* 2535 */                             Hashtable ht = (Hashtable)pageContext.findAttribute("applications");
/* 2536 */                             String aid = request.getParameter("haid");
/* 2537 */                             String haName = null;
/* 2538 */                             if (aid != null)
/*      */                             {
/* 2540 */                               haName = (String)ht.get(aid);
/*      */                             }
/*      */                             
/* 2543 */                             out.write("\n    ");
/* 2544 */                             if (_jspx_meth_c_005fcatch_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                               return;
/* 2546 */                             out.write(" \n    ");
/*      */                             
/* 2548 */                             IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2549 */                             _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2550 */                             _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2552 */                             _jspx_th_c_005fif_005f2.setTest("${!empty param.haid && (empty invalidhaid)}");
/* 2553 */                             int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2554 */                             if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                               for (;;) {
/* 2556 */                                 out.write(" \n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2557 */                                 out.print(BreadcrumbUtil.getHomePage(request));
/* 2558 */                                 out.write(" \n      &gt; ");
/* 2559 */                                 out.print(BreadcrumbUtil.getHAPage(aid, haName));
/* 2560 */                                 out.write(" &gt; \n      \n\t");
/*      */                                 
/* 2562 */                                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2563 */                                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2564 */                                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_c_005fif_005f2);
/*      */                                 
/* 2566 */                                 _jspx_th_c_005fif_005f3.setTest("${('true'!=isfromresourcepage)}");
/* 2567 */                                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2568 */                                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                                   for (;;) {
/* 2570 */                                     out.write("      \n\t      ");
/* 2571 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2572 */                                     out.write(32);
/* 2573 */                                     out.write(10);
/* 2574 */                                     out.write(9);
/* 2575 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2576 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2580 */                                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2581 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                                 }
/*      */                                 
/* 2584 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2585 */                                 out.write("      \n\t");
/*      */                                 
/* 2587 */                                 IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2588 */                                 _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2589 */                                 _jspx_th_c_005fif_005f4.setParent(_jspx_th_c_005fif_005f2);
/*      */                                 
/* 2591 */                                 _jspx_th_c_005fif_005f4.setTest("${('true'==isfromresourcepage)}");
/* 2592 */                                 int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2593 */                                 if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                                   for (;;) {
/* 2595 */                                     out.write("      \n\t      ");
/* 2596 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, (String)resourceInfoFromResourcePage.get("DISPLAYNAME")));
/* 2597 */                                     out.write(32);
/* 2598 */                                     out.write(10);
/* 2599 */                                     out.write(9);
/* 2600 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2601 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2605 */                                 if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2606 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                                 }
/*      */                                 
/* 2609 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2610 */                                 out.write("      \n      \n      &gt; <span class=\"bcactive\"> ");
/* 2611 */                                 out.print(FormatUtil.getString("Custom Attributes"));
/* 2612 */                                 out.write(" </span></td>\n    ");
/* 2613 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2614 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2618 */                             if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2619 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                             }
/*      */                             
/* 2622 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2623 */                             out.write(" \n    \n    ");
/*      */                             
/* 2625 */                             IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2626 */                             _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2627 */                             _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2629 */                             _jspx_th_c_005fif_005f5.setTest("${(!empty param.haid && (!empty invalidhaid)) || (empty param.haid)}");
/* 2630 */                             int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2631 */                             if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                               for (;;) {
/* 2633 */                                 out.write("\t\n    <td class=\"bcsign\"  height=\"22\" valign=\"top\"> ");
/* 2634 */                                 out.print(BreadcrumbUtil.getMonitorsPage());
/* 2635 */                                 out.write(" \n      &gt; \n      \n");
/*      */                                 
/* 2637 */                                 IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2638 */                                 _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2639 */                                 _jspx_th_c_005fif_005f6.setParent(_jspx_th_c_005fif_005f5);
/*      */                                 
/* 2641 */                                 _jspx_th_c_005fif_005f6.setTest("${('true'!=isfromresourcepage)}");
/* 2642 */                                 int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2643 */                                 if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                                   for (;;) {
/* 2645 */                                     out.write("      \n      ");
/* 2646 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes("Custom-Application"));
/* 2647 */                                     out.write(" \n      &gt; ");
/* 2648 */                                     out.print(BreadcrumbUtil.getCAMDetailsPage(camID, aid, camname));
/* 2649 */                                     out.write(32);
/* 2650 */                                     out.write(10);
/* 2651 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2652 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2656 */                                 if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2657 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                                 }
/*      */                                 
/* 2660 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2661 */                                 out.write("      \n \n \n");
/*      */                                 
/* 2663 */                                 IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2664 */                                 _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2665 */                                 _jspx_th_c_005fif_005f7.setParent(_jspx_th_c_005fif_005f5);
/*      */                                 
/* 2667 */                                 _jspx_th_c_005fif_005f7.setTest("${('true'==isfromresourcepage)}");
/* 2668 */                                 int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2669 */                                 if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                                   for (;;) {
/* 2671 */                                     out.write(10);
/* 2672 */                                     out.write(9);
/* 2673 */                                     out.print(BreadcrumbUtil.getMonitorResourceTypes((String)resourceInfoFromResourcePage.get("TYPE")));
/* 2674 */                                     out.write(" \n\t&gt;\n\t\n\t");
/* 2675 */                                     out.print(BreadcrumbUtil.getServerDetailsPage(camID, (String)resourceInfoFromResourcePage.get("DISPLAYNAME")));
/* 2676 */                                     out.write(10);
/* 2677 */                                     int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2678 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/*      */                                 }
/* 2682 */                                 if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2683 */                                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                                 }
/*      */                                 
/* 2686 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2687 */                                 out.write("\n\t&gt; <span class=\"bcactive\"> \n      ");
/* 2688 */                                 out.print(FormatUtil.getString("am.webclient.cam.addattributes.link"));
/* 2689 */                                 out.write("</span> \n      </td>\n    ");
/* 2690 */                                 int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2691 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2695 */                             if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2696 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                             }
/*      */                             
/* 2699 */                             this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2700 */                             out.write(" </tr>\n  <tr> \n    <td  height=\"2\" class=\"bcstrip\"><img src=\"../images/spacer.gif\" width=\"10\" height=\"2\"></td>\n  </tr>\n</table>\n  \n<script>\nfunction validateAndSubmit()\n{\n   if(trimAll(document.AMActionForm.camname.value)==\"\")\n   {\n        alert('");
/* 2701 */                             out.print(FormatUtil.getString("am.webclient.cam.namealert"));
/* 2702 */                             out.write("');\n        return;\n   }\n   document.AMActionForm.submit();\n}\n\n</script>\n\n\n\n\n<div id=\"edit\" style=\"DISPLAY: none\">\n\n");
/*      */                             
/* 2704 */                             FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.get(FormTag.class);
/* 2705 */                             _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2706 */                             _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2708 */                             _jspx_th_html_005fform_005f0.setAction("/adminAction");
/*      */                             
/* 2710 */                             _jspx_th_html_005fform_005f0.setMethod("get");
/*      */                             
/* 2712 */                             _jspx_th_html_005fform_005f0.setStyle("display:inline");
/* 2713 */                             int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2714 */                             if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                               for (;;) {
/* 2716 */                                 out.write("\n\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\n<tr>\n<td height=\"28\"   class=\"tableheading\">");
/* 2717 */                                 out.print(FormatUtil.getString("am.webclient.common.configurationdetails.text"));
/* 2718 */                                 out.write("\n\n<input type=\"hidden\" name=\"applicationname\" value=\"");
/* 2719 */                                 out.print(request.getParameter("name"));
/* 2720 */                                 out.write("\">\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2721 */                                 out.print(request.getParameter("haid"));
/* 2722 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2723 */                                 out.print(request.getParameter("type"));
/* 2724 */                                 out.write("\">\n<input type=\"hidden\" name=\"type\" value=\"");
/* 2725 */                                 out.print(request.getParameter("type"));
/* 2726 */                                 out.write("\">\n<input type=\"hidden\" name=\"method\" value=\"configureJMX\">\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2727 */                                 out.print(request.getParameter("resourceid"));
/* 2728 */                                 out.write("\">\n<input type=\"hidden\" name=\"resourcename\" value=\"");
/* 2729 */                                 out.print(request.getParameter("resourcename"));
/* 2730 */                                 out.write("\">\n<input type=\"hidden\" name=\"moname\" value=\"");
/* 2731 */                                 out.print(request.getParameter("moname"));
/* 2732 */                                 out.write("\">\n\n</td>\n<td height=\"28\"   class=\"tableheading\" align=\"right\" onClick=\"javascript:hideDiv('edit')\">\n<span class=\"bodytextboldwhiteun\" ><img src=\"../images/icon_minus.gif\" width=\"9\" height=\"9\" hspace=\"5\">\n");
/* 2733 */                                 out.print(FormatUtil.getString("am.webclient.common.editmonitor.hide.text"));
/* 2734 */                                 out.write("</span>\n</td>\n</tr>\n</table>\n<table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrborder\">\n<tr>\n    <td width=\"20%\" height=\"32\" valign=='top' class=\"bodytext\"> ");
/* 2735 */                                 out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2736 */                                 out.write("\n</td>\n    <td width=\"80%\" class=\"bodytext\"> <textarea name=\"camname\" cols=\"38\" rows=\"1\" class=\"formtextarea\">");
/* 2737 */                                 out.print(camname);
/* 2738 */                                 out.write(" </textarea>\n</td>\n</tr>\n\n<tr>\n    <td valign='top'  class=\"bodytext\"> ");
/* 2739 */                                 out.print(FormatUtil.getString("Description"));
/* 2740 */                                 out.write("</td>\n    <td  class=\"bodytext\"> <textarea name=\"camdesc\" cols=\"38\" rows=\"3\" class=\"formtextarea\" >");
/* 2741 */                                 out.print(camDescription);
/* 2742 */                                 out.write("</textarea>\n    </td>\n  </tr>\n</table>\n");
/*      */                                 
/* 2744 */                                 String cancel = FormatUtil.getString("am.webclient.common.cancel.text");
/*      */                                 
/* 2746 */                                 out.write("\n<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbborder\">\n  <tr>\n    <td width=\"20%\" class=\"tablebottom\">&nbsp;</td>\n    <td width=\"80%\" class=\"tablebottom\" >\n<input name=\"addcustomapp\" type=\"button\" class=\"buttons btn_highlt\" \" value=\"");
/* 2747 */                                 out.print(FormatUtil.getString("am.webclient.common.startmonitoring.text"));
/* 2748 */                                 out.write("\" onClick=\"validateAndSubmit()\"/>\n      &nbsp; <input name=\"reset\" type=\"reset\" class=\"buttons btn_link\" value=\"");
/* 2749 */                                 out.print(cancel);
/* 2750 */                                 out.write("\" onClick=\"javascript:toggleDiv('edit')\"/>\n     </td>\n  </tr>\n</table>\n");
/* 2751 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2752 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2756 */                             if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2757 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                             }
/*      */                             
/* 2760 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2761 */                             out.write("\n</div>\n\n\n");
/*      */                             
/* 2763 */                             FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.get(FormTag.class);
/* 2764 */                             _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2765 */                             _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                             
/* 2767 */                             _jspx_th_html_005fform_005f1.setAction("/CAMShowMBeansOfDomain");
/*      */                             
/* 2769 */                             _jspx_th_html_005fform_005f1.setMethod("POST");
/* 2770 */                             int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2771 */                             if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                               for (;;) {
/* 2773 */                                 out.write(" \n<input type=\"hidden\" name=\"modeOfSubmission\" value=\"1\"/>\n<input type=\"hidden\" name=\"camid\" value=\"");
/* 2774 */                                 out.print(camID);
/* 2775 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"haid\" value=\"");
/* 2776 */                                 out.print(haid);
/* 2777 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"screenid\" value=\"");
/* 2778 */                                 out.print(screenID);
/* 2779 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"hostname\" value=\"");
/* 2780 */                                 out.print(hostName);
/* 2781 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"portnumber\" value=\"");
/* 2782 */                                 out.print(portNumber);
/* 2783 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourcetype\" value=\"");
/* 2784 */                                 out.print(resourceType);
/* 2785 */                                 out.write("\"/>\n<input type=\"hidden\" name=\"resourceid\" value=\"");
/* 2786 */                                 out.print(resourceID);
/* 2787 */                                 out.write("\"/>\n<input type=hidden name=\"method\" value=\"showMBeansOfDomain\"/>\n\n<input type=\"hidden\" name=\"isfromresourcepage\" value=\"");
/* 2788 */                                 out.print(request.getAttribute("isfromresourcepage"));
/* 2789 */                                 out.write("\"/>\n\n<table valign=\"top\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrbtborder\" width=\"99%\" bgcolor='FFFFFF'>\n  <tr> \n        <td height=\"32\"  colspan=\"3\"   class=\"tableheadingbborder\"> ");
/* 2790 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.header"));
/* 2791 */                                 out.write(" * </td>\n  </tr>\n  <tr> \n    <td height=\"19\"  colspan=\"3\"   > \n      <table width=\"100%\" valign=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n   <tr> \n          <td width=\"10%\" height=\"27\" class=\"yellowgrayborder\"> <b>");
/* 2792 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.agent.text"));
/* 2793 */                                 out.write(" :</b></td>\n    <td width=\"35%\" class=\"yellowgrayborder\"> ");
/* 2794 */                                 out.print(getTrimmedText(hostName, 45));
/* 2795 */                                 out.write("</td>\n          <td width=\"7%\" class=\"yellowgrayborder\"> <b>");
/* 2796 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.port.text"));
/* 2797 */                                 out.write(" :</b></td>\n    <td width=\"21%\" class=\"yellowgrayborder\"> ");
/* 2798 */                                 out.print(portNumber);
/* 2799 */                                 out.write("</td>\n          <td width=\"9%\" class=\"yellowgrayborder\"><b>");
/* 2800 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.type.text"));
/* 2801 */                                 out.write(" :</b></td>\n    <td width=\"24%\" class=\"yellowgrayborder\">");
/* 2802 */                                 out.print(resourceType);
/* 2803 */                                 out.write("</td>\n  </tr>\n</table>\n</td></tr>\n\n  ");
/*      */                                 
/* 2805 */                                 IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.get(IterateTag.class);
/* 2806 */                                 _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2807 */                                 _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_html_005fform_005f1);
/*      */                                 
/* 2809 */                                 _jspx_th_logic_005fiterate_005f0.setName("domains");
/*      */                                 
/* 2811 */                                 _jspx_th_logic_005fiterate_005f0.setId("domain");
/*      */                                 
/* 2813 */                                 _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/*      */                                 
/* 2815 */                                 _jspx_th_logic_005fiterate_005f0.setType("java.lang.String");
/* 2816 */                                 int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2817 */                                 if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2818 */                                   String domain = null;
/* 2819 */                                   Integer j = null;
/* 2820 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2821 */                                     out = _jspx_page_context.pushBody();
/* 2822 */                                     _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2823 */                                     _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                                   }
/* 2825 */                                   domain = (String)_jspx_page_context.findAttribute("domain");
/* 2826 */                                   j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                                   for (;;) {
/* 2828 */                                     out.write(" \n  <tr> \n    <td width=\"4%\"height='25' class=\"bodytext\" >&nbsp;</td>\n    <td class=\"bodytext\" width=\"1%\" ><input type=\"radio\" name=\"domain\" value=\"");
/* 2829 */                                     out.print(domain);
/* 2830 */                                     out.write("\" onClick=\"javascript:handleClose()\"></td>\n    <td width=\"95%\" class=\"bodytext\" > ");
/* 2831 */                                     out.print(domain);
/* 2832 */                                     out.write("</td>\n  </tr>\n  ");
/* 2833 */                                     int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 2834 */                                     domain = (String)_jspx_page_context.findAttribute("domain");
/* 2835 */                                     j = (Integer)_jspx_page_context.findAttribute("j");
/* 2836 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 2839 */                                   if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2840 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 2843 */                                 if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 2844 */                                   this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                                 }
/*      */                                 
/* 2847 */                                 this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005ftype_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 2848 */                                 out.write(" \n  <tr> \n  \t<td height=\"36\"  >&nbsp;</td>\n    <td  ><input type=\"radio\" name=\"domain\" value=\"adventnet_meam_cam_domainfilter\" onClick=\"javascript:handleClose()\" ></td>\n    <td><span class=\"bodytext\">");
/* 2849 */                                 out.print(FormatUtil.getString("am.webclient.cam.mx4j.filter.text"));
/* 2850 */                                 out.write(" </span> <input type=\"text\" name=\"domainfilter\" value=\"\" class='formtext'/>\n    </td>\n  </tr>\n  \n\t");
/*      */                                 
/* 2852 */                                 if (resourceType.equals("WebSphere-server"))
/*      */                                 {
/*      */ 
/* 2855 */                                   out.write("\n\t<tr>\n\t<td colspan=\"3\" >\n            <span class=\"bodytext\">");
/* 2856 */                                   out.print(FormatUtil.getString("am.webclient.cam.choosedomain.websphere.note"));
/* 2857 */                                   out.write(" </span>\n\t</td>\n\t</tr>\n\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 2861 */                                 out.write("\n\t&nbsp;\n       <tr> \n       \n\t  <td height=\"36\" >&nbsp;</td>\n\t    <td >\n\t    <input type=\"radio\" name=\"domain\" value=\"nothing\" onClick=\"javascript:handleOpen(this);\">\n\t    </td>\n\t       <td class=\"bodytext\" width=\"95%\">\n\t    \n\t    <b>");
/* 2862 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.addmbeandirectly.text"));
/* 2863 */                                 out.write(" </b> *\n\t    </td>\n\t    </tr>\n\t    <tr>\n\t    <td colspan=\"3\">\n     \t   <div id=\"directadd\" style=\"display:none\">\n     \t   <br>\n\t    <table width=\"80%\" class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\">\n\t    <tr class=\"whitegrayborder\">\n\t    <td width=\"25%\"> ");
/* 2864 */                                 out.print(FormatUtil.getString("am.webclient.cam.mbeanobjectname"));
/* 2865 */                                 out.write(" : </td>\n\t       <td>\n\t       <input type=\"text\" name=\"groupName\" value=\"\" size=\"50\">\n\t      </td>\n\t     </tr>\n\t      <tr class=\"yellowgrayborder\">\n\t       <td>\n\t       ");
/* 2866 */                                 out.print(FormatUtil.getString("am.webclient.camscreen.attributename"));
/* 2867 */                                 out.write("(s) : \n\t       </td>\n\t        <td>\n\t           <input type=\"text\"  size=\"80\" name=\"attributenames\" value=\"\" />* ");
/* 2868 */                                 out.print(FormatUtil.getString("am.webclient.cam.multipleattribute"));
/* 2869 */                                 out.write("\n\t        </td>\n\t       </tr>\n\t      <tr class=\"whitegrayborder\">\n\t     <td>\n\t     ");
/* 2870 */                                 out.print(FormatUtil.getString("am.webclient.cam.attributetype"));
/* 2871 */                                 out.write(" :\n\t      </td>\n\t       <td>\n\t     <select name=\"attributeType\">\n\t     <option value=\"1\">String</option>\n\t     <option value=\"0\">Numeric</option>\n\t     </select>\n\t      </td>\n\t     </tr>\n\t      <table>\n\t    </div>\n\t    \n\t    \n\t    </td>\n  \t</tr>\n</table>\n  \t\n  \t\n  \t\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr> \n    <td>&nbsp;</td>\n  </tr>\n</table>\n\n\n  </td>\n  </tr>\n  </table>\n  \n<table width=\"99%\" border=\"0\" class=\"lrbborder\" cellpadding=2 cellspacing=0>\n    <tr> \n      <td height=\"30\" class=\"tablebottom\" width=\"5%\">&nbsp;</td>\n      <td class=\"tablebottom\" >&nbsp;</td>\n      <td class=\"tablebottom\" > <input name=\"submitbutton\" type=\"button\" onClick=\"javascript:submitAttribs();\" class='buttons btn_highlt' value=\"");
/* 2872 */                                 out.print(FormatUtil.getString("am.webclient.cam.showmbeans.button"));
/* 2873 */                                 out.write("\" /> \n        &nbsp;&nbsp; <input type=\"submit\" value=\"");
/* 2874 */                                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2875 */                                 out.write("\" class='buttons btn_link' onClick=\"cancel();\" /> \n      </td>\n    </tr>\n</table>\n<br>\n* ");
/* 2876 */                                 out.print(FormatUtil.getString("am.webclient.cam.choosedomain.note"));
/* 2877 */                                 out.write(10);
/* 2878 */                                 out.write(10);
/* 2879 */                                 int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 2880 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/*      */                             }
/* 2884 */                             if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 2885 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                             }
/*      */                             
/* 2888 */                             this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fmethod_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 2889 */                             out.write(32);
/* 2890 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 2891 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 2894 */                           if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2895 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 2898 */                         if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 2899 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */                         }
/*      */                         
/* 2902 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 2903 */                         out.write(32);
/* 2904 */                         out.write(10);
/* 2905 */                         out.write(32);
/* 2906 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 2908 */                         out.write(10);
/* 2909 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 2910 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2914 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 2915 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 2918 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 2919 */                       out.write(10);
/*      */                     }
/* 2921 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 2922 */         out = _jspx_out;
/* 2923 */         if ((out != null) && (out.getBufferSize() != 0))
/* 2924 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 2925 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 2928 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2934 */     PageContext pageContext = _jspx_page_context;
/* 2935 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2937 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2938 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2939 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2941 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 2943 */     _jspx_th_tiles_005fput_005f0.setValue("Choose JMX Domain");
/* 2944 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2945 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2946 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2947 */       return true;
/*      */     }
/* 2949 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2950 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2955 */     PageContext pageContext = _jspx_page_context;
/* 2956 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2958 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2959 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2960 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2962 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 2964 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp");
/* 2965 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2966 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2967 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2968 */       return true;
/*      */     }
/* 2970 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2971 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f2(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2976 */     PageContext pageContext = _jspx_page_context;
/* 2977 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 2979 */     PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2980 */     _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2981 */     _jspx_th_tiles_005fput_005f2.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 2983 */     _jspx_th_tiles_005fput_005f2.setName("ServerLeftArea");
/*      */     
/* 2985 */     _jspx_th_tiles_005fput_005f2.setValue("/jsp/cam_leftlinksarea.jsp");
/* 2986 */     int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2987 */     if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2988 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2989 */       return true;
/*      */     }
/* 2991 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f2);
/* 2992 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 2997 */     PageContext pageContext = _jspx_page_context;
/* 2998 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3000 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3001 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3002 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3004 */     _jspx_th_c_005fif_005f1.setTest("${(empty isfromresourcepage)}");
/* 3005 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3006 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3008 */         out.write("    \n    javascript:history.back();    \n");
/* 3009 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3010 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3014 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3015 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3016 */       return true;
/*      */     }
/* 3018 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3019 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fcatch_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3024 */     PageContext pageContext = _jspx_page_context;
/* 3025 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3027 */     CatchTag _jspx_th_c_005fcatch_005f0 = (CatchTag)this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.get(CatchTag.class);
/* 3028 */     _jspx_th_c_005fcatch_005f0.setPageContext(_jspx_page_context);
/* 3029 */     _jspx_th_c_005fcatch_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 3031 */     _jspx_th_c_005fcatch_005f0.setVar("invalidhaid");
/* 3032 */     int[] _jspx_push_body_count_c_005fcatch_005f0 = { 0 };
/*      */     try {
/* 3034 */       int _jspx_eval_c_005fcatch_005f0 = _jspx_th_c_005fcatch_005f0.doStartTag();
/* 3035 */       int evalDoAfterBody; if (_jspx_eval_c_005fcatch_005f0 != 0) {
/*      */         for (;;) {
/* 3037 */           out.write(32);
/* 3038 */           if (_jspx_meth_fmt_005fparseNumber_005f0(_jspx_th_c_005fcatch_005f0, _jspx_page_context, _jspx_push_body_count_c_005fcatch_005f0))
/* 3039 */             return true;
/* 3040 */           out.write(" \n    ");
/* 3041 */           evalDoAfterBody = _jspx_th_c_005fcatch_005f0.doAfterBody();
/* 3042 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3046 */       if (_jspx_th_c_005fcatch_005f0.doEndTag() == 5)
/* 3047 */         return 1;
/*      */     } catch (Throwable _jspx_exception) {
/*      */       for (;;) {
/* 3050 */         int tmp184_183 = 0; int[] tmp184_181 = _jspx_push_body_count_c_005fcatch_005f0; int tmp186_185 = tmp184_181[tmp184_183];tmp184_181[tmp184_183] = (tmp186_185 - 1); if (tmp186_185 <= 0) break;
/* 3051 */         out = _jspx_page_context.popBody(); }
/* 3052 */       _jspx_th_c_005fcatch_005f0.doCatch(_jspx_exception);
/*      */     } finally {
/* 3054 */       _jspx_th_c_005fcatch_005f0.doFinally();
/* 3055 */       this._005fjspx_005ftagPool_005fc_005fcatch_0026_005fvar.reuse(_jspx_th_c_005fcatch_005f0);
/*      */     }
/* 3057 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fparseNumber_005f0(JspTag _jspx_th_c_005fcatch_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fcatch_005f0) throws Throwable
/*      */   {
/* 3062 */     PageContext pageContext = _jspx_page_context;
/* 3063 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3065 */     ParseNumberTag _jspx_th_fmt_005fparseNumber_005f0 = (ParseNumberTag)this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.get(ParseNumberTag.class);
/* 3066 */     _jspx_th_fmt_005fparseNumber_005f0.setPageContext(_jspx_page_context);
/* 3067 */     _jspx_th_fmt_005fparseNumber_005f0.setParent((Tag)_jspx_th_c_005fcatch_005f0);
/*      */     
/* 3069 */     _jspx_th_fmt_005fparseNumber_005f0.setVar("wnhaid");
/*      */     
/* 3071 */     _jspx_th_fmt_005fparseNumber_005f0.setValue("${param.haid}");
/* 3072 */     int _jspx_eval_fmt_005fparseNumber_005f0 = _jspx_th_fmt_005fparseNumber_005f0.doStartTag();
/* 3073 */     if (_jspx_th_fmt_005fparseNumber_005f0.doEndTag() == 5) {
/* 3074 */       this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3075 */       return true;
/*      */     }
/* 3077 */     this._005fjspx_005ftagPool_005ffmt_005fparseNumber_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_fmt_005fparseNumber_005f0);
/* 3078 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3083 */     PageContext pageContext = _jspx_page_context;
/* 3084 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3086 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3087 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3088 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3090 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3092 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3093 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3094 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3095 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3096 */       return true;
/*      */     }
/* 3098 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3099 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\cam_005fchoosedomain_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */