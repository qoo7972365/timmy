/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
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
/*      */ import org.apache.struts.taglib.logic.IterateTag;
/*      */ import org.apache.struts.taglib.logic.NotPresentTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class ManagedServers_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  854 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  855 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  858 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  859 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  860 */       set = AMConnectionPool.executeQueryStmt(query);
/*  861 */       if (set.next())
/*      */       {
/*  863 */         String helpLink = set.getString("LINK");
/*  864 */         DBUtil.searchLinks.put(key, helpLink);
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2202 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2206 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2207 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2217 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2221 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2222 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2223 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.release();
/* 2224 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.release();
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2226 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2228 */     this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2230 */     this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
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
/* 2257 */       out.write("\n\n<!--~#sb#~-->\n");
/* 2258 */       out.write("\n\n\n\n\n\n\n\n");
/* 2259 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2261 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2262 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2263 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2265 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2267 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2269 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2271 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2272 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2273 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2274 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2277 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2278 */         String available = null;
/* 2279 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2280 */         out.write(10);
/*      */         
/* 2282 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2283 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2284 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2286 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2288 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2290 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2292 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2293 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2294 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2295 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2298 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2299 */           String unavailable = null;
/* 2300 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2301 */           out.write(10);
/*      */           
/* 2303 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2304 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2305 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2307 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2309 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2311 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2313 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2314 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2315 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2316 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2319 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2320 */             String unmanaged = null;
/* 2321 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2322 */             out.write(10);
/*      */             
/* 2324 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2325 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2326 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2328 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2330 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2332 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2334 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2335 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2336 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2337 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2340 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2341 */               String scheduled = null;
/* 2342 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2343 */               out.write(10);
/*      */               
/* 2345 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2346 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2347 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2349 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2351 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2353 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2355 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2356 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2357 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2358 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2361 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2362 */                 String critical = null;
/* 2363 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2364 */                 out.write(10);
/*      */                 
/* 2366 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2367 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2368 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2370 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2372 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2374 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2376 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2377 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2378 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2379 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2382 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2383 */                   String clear = null;
/* 2384 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2385 */                   out.write(10);
/*      */                   
/* 2387 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2388 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2389 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2391 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2393 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2395 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2397 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2398 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2399 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2400 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2403 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2404 */                     String warning = null;
/* 2405 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2406 */                     out.write(10);
/* 2407 */                     out.write(10);
/*      */                     
/* 2409 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2410 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2412 */                     out.write(10);
/* 2413 */                     out.write(10);
/* 2414 */                     out.write(10);
/* 2415 */                     out.write("\n<head>\n<META HTTP-EQUIV=\"REFRESH\" CONTENT=\"600\">\n");
/*      */                     
/* 2417 */                     request.setAttribute("HelpKey", "Managed Servers");
/*      */                     
/* 2419 */                     out.write("\n\n\n\n\n");
/*      */                     
/* 2421 */                     if (com.adventnet.appmanager.util.Constants.isIt360)
/*      */                     {
/* 2423 */                       out.write(10);
/* 2424 */                       out.write(9);
/* 2425 */                       org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/it360/jsp/probeSettings.jsp", out, false);
/* 2426 */                       out.write(10);
/*      */                     }
/*      */                     else
/*      */                     {
/* 2430 */                       out.write("\n<style>\n.managedServers td {\nfont-size: 12px;\n}\n.serverBlock {\npadding: 0;\n}\n.serverSec td {\nborder-bottom: none;\n}\n.leftBorder {\nborder-left: 1px solid #e3e3e3;\n}\n.rightBorder {\nborder-right: 1px solid #e3e3e3;\n}\n}\n</style>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<script>\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.form1,\"checkbox\"); //No I18N\n}\n\nfunction refreshServers()\n{\n\tfor(i=0;i<document.form1.elements.length;i++)\n\t{\n\t\tif(document.form1.elements[i].type==\"checkbox\")\n\t\t{\n\t\t\tvar checkboxName = document.form1.elements[i].name;\n\t\t\tif(checkboxName==\"checkbox\")\n\t\t\t{\n\t\t\t\tvar value = document.form1.elements[i].value;\n\t\t\t\tselected=document.form1.elements[i].checked;\n\t\t\t\tif(selected)\n\t\t\t\t{\n\t\t\t\t\tbreak;\n\t\t\t\t}\n\t\t\t}\n\t\t}\n\t}\n\tif(!selected)\n\t{\n\t\talert(\"");
/* 2431 */                       out.print(FormatUtil.getString("am.webclient.adminserver.fetchnow.nothingselected.message"));
/* 2432 */                       out.write("\");\n\t}\n\telse\n\t{\n\t  document.form1.action=\"/adminAction.do?method=fetchDataNowForManagedServer\";\n\t  document.form1.method=\"Post\"\n\t  document.form1.submit();\n\t}\n}\n\nfunction swAction(action)\n{\n ");
/* 2433 */                       if (_jspx_meth_logic_005fpresent_005f0(_jspx_page_context))
/*      */                         return;
/* 2435 */                       out.write(10);
/* 2436 */                       out.write(32);
/*      */                       
/* 2438 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f0 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2439 */                       _jspx_th_logic_005fnotPresent_005f0.setPageContext(_jspx_page_context);
/* 2440 */                       _jspx_th_logic_005fnotPresent_005f0.setParent(null);
/*      */                       
/* 2442 */                       _jspx_th_logic_005fnotPresent_005f0.setRole("DEMO");
/* 2443 */                       int _jspx_eval_logic_005fnotPresent_005f0 = _jspx_th_logic_005fnotPresent_005f0.doStartTag();
/* 2444 */                       if (_jspx_eval_logic_005fnotPresent_005f0 != 0) {
/*      */                         for (;;) {
/* 2446 */                           out.write("\n   for(i=0;i<document.form1.elements.length;i++)\n       {\n\t\t   if(document.form1.elements[i].type==\"checkbox\")\n\t\t   {\n\t\t\t   var name = document.form1.elements[i].name;\n\t\t\t   if(name==\"checkbox\")\n\t\t\t   {\n\t\t\t\t   var value = document.form1.elements[i].value;\n\t\t\t\t   sel=document.form1.elements[i].checked;\n\t\t\t\t   if(sel)\n\t\t\t\t   {\n\t\t\t\t\t   break;\n\t\t\t\t   }\n\t\t\t   }\n\t\t   }\n\t   }\n    if(action == 'download')\n    {\n\t\t  var it360 = '");
/* 2447 */                           out.print(com.adventnet.appmanager.util.Constants.isIt360);
/* 2448 */                           out.write("';\n\t\t  var alertMessage1 = '");
/* 2449 */                           out.print(FormatUtil.getString("am.product.sp.download.select.alert.text"));
/* 2450 */                           out.write("';\n\t\t  var alertMessage2 = '");
/* 2451 */                           out.print(FormatUtil.getString("am.product.sp.download.condn.confirm.alert.text"));
/* 2452 */                           out.write("';\n\t\t  if (it360 == 'true') {\n\t\t\t  alertMessage1 = '");
/* 2453 */                           out.print(FormatUtil.getString("am.product.sp.download.select"));
/* 2454 */                           out.write("';\n\t\t\t  alertMessage2 = '");
/* 2455 */                           out.print(FormatUtil.getString("am.product.sp.download.condn"));
/* 2456 */                           out.write("';\n\t\t  }\n         if(!sel)\n          {\n        \t alert(alertMessage1);//No I18N\n          }\n        else if(confirm(alertMessage2))\n           {\n\t       document.form1.action=\"/swMgmt.do?method=initiateDownload\"; \n\t       document.form1.method=\"Post\"; //No I18N\n\t       document.form1.submit();\n           }\n    }\n    else if( action == 'upgrade')\n    {\n    \t  var isAmsExpired = '");
/* 2457 */                           out.print(com.adventnet.appmanager.util.Constants.isAMSExpired());
/* 2458 */                           out.write("';\n\t\t  var it360 = '");
/* 2459 */                           out.print(com.adventnet.appmanager.util.Constants.isIt360);
/* 2460 */                           out.write("';\n\t\t\t  var alertMessage1 = '");
/* 2461 */                           out.print(FormatUtil.getString("am.product.sp.upgrade.select.alert.text"));
/* 2462 */                           out.write("';\n\t\t\t  var alertMessage2 = '");
/* 2463 */                           out.print(FormatUtil.getString("am.product.sp.upgrade.condn.confirm.alert.text"));
/* 2464 */                           out.write("';\n\t\t\t  var alertMessage3 = '");
/* 2465 */                           out.print(FormatUtil.getString("am.product.sp.backup.managedserver.confirm.alert.text"));
/* 2466 */                           out.write("';\n\t\t\t  if (it360 == 'true') {\n\t\t\t\t  alertMessage1 = '");
/* 2467 */                           out.print(FormatUtil.getString("am.product.sp.upgrade.select"));
/* 2468 */                           out.write("';\n\t\t\t\t  alertMessage2 = '");
/* 2469 */                           out.print(FormatUtil.getString("am.product.sp.upgrade.condn"));
/* 2470 */                           out.write("';\n\t\t\t  }\t    \n     if(!sel)\n       {\n\t     alert(alertMessage1);//No I18N\n       }\n     else if (isAmsExpired == 'true') {\n \t\talertMessage = '");
/* 2471 */                           out.print(FormatUtil.getString("am.ams.expired.message.js.text"));
/* 2472 */                           out.write("';\n\t\talert(alertMessage);\n       }\n     else if(confirm(alertMessage2))\n        {\n         document.form1.action=\"/swMgmt.do?method=initiateUpgrade\"; \n\t     document.form1.method=\"Post\"; //No I18N\n\t     document.form1.submit();\n        /*\n        \tCommented because backup will be handled and tested later (Vijay)\n        \tif(confirm(alertMessage3))\n        \t{\n        \t\tdocument.form1.action=\"/swMgmt.do?method=initiateUpgrade&backupRequired=true\"; \n\t\t\t    document.form1.method=\"Post\"; //No I18N\n\t\t\t    document.form1.submit();\n        \t}\n        \telse\n        \t{\n        \t\tdocument.form1.action=\"/swMgmt.do?method=initiateUpgrade&backupRequired=false\";\n\t\t\t    document.form1.method=\"Post\"; //No I18N\n\t\t\t    document.form1.submit();\n        \t}\n        */\n        }\n    }\n \n\t \n ");
/* 2473 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f0.doAfterBody();
/* 2474 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2478 */                       if (_jspx_th_logic_005fnotPresent_005f0.doEndTag() == 5) {
/* 2479 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0); return;
/*      */                       }
/*      */                       
/* 2482 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f0);
/* 2483 */                       out.write("\n\n}\n\n\nfunction deleteSelections()\n{\n ");
/* 2484 */                       if (_jspx_meth_logic_005fpresent_005f1(_jspx_page_context))
/*      */                         return;
/* 2486 */                       out.write(10);
/* 2487 */                       out.write(32);
/*      */                       
/* 2489 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f1 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.get(NotPresentTag.class);
/* 2490 */                       _jspx_th_logic_005fnotPresent_005f1.setPageContext(_jspx_page_context);
/* 2491 */                       _jspx_th_logic_005fnotPresent_005f1.setParent(null);
/*      */                       
/* 2493 */                       _jspx_th_logic_005fnotPresent_005f1.setRole("DEMO");
/* 2494 */                       int _jspx_eval_logic_005fnotPresent_005f1 = _jspx_th_logic_005fnotPresent_005f1.doStartTag();
/* 2495 */                       if (_jspx_eval_logic_005fnotPresent_005f1 != 0) {
/*      */                         for (;;) {
/* 2497 */                           out.write("\n   for(i=0;i<document.form1.elements.length;i++)\n       {\n\t\t   if(document.form1.elements[i].type==\"checkbox\")\n\t\t   {\n\t\t\t   var name = document.form1.elements[i].name;\n\t\t\t   if(name==\"checkbox\")\n\t\t\t   {\n\t\t\t\t   var value = document.form1.elements[i].value;\n\t\t\t\t   sel=document.form1.elements[i].checked;\n\t\t\t\t   if(sel)\n\t\t\t\t   {\n\t\t\t\t\t   break;\n\t\t\t\t   }\n\t\t\t   }\n\t\t   }\n\t   }\n if(!sel)\n {\n\t alert(\"");
/* 2498 */                           out.print(FormatUtil.getString("am.webclient.managedserver.delete.alert"));
/* 2499 */                           out.write("\");\n }\n else if(confirm('");
/* 2500 */                           out.print(FormatUtil.getString("am.webclient.managedserver.delete.confirm"));
/* 2501 */                           out.write("'))\n {\n\t document.form1.action=\"/adminAction.do?method=deleteManagedServers\";\n\t document.form1.method=\"Post\"\n\t document.form1.submit();\n }\n ");
/* 2502 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f1.doAfterBody();
/* 2503 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 2507 */                       if (_jspx_th_logic_005fnotPresent_005f1.doEndTag() == 5) {
/* 2508 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1); return;
/*      */                       }
/*      */                       
/* 2511 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005frole.reuse(_jspx_th_logic_005fnotPresent_005f1);
/* 2512 */                       out.write("\n}\n\n</script>\n\n\n");
/*      */                       
/* 2514 */                       boolean easyUpgradeEnabled = DBUtil.getGlobalConfigValueasBoolean("easyUpgrade");
/* 2515 */                       Boolean isSecServerExist = Boolean.valueOf(false);
/* 2516 */                       String rowspan = "1";
/* 2517 */                       ResultSet r = null;
/*      */                       try {
/* 2519 */                         String secServreExistQuery = "select * from AM_MAS_SERVER where SERVERTYPE='SY'";
/* 2520 */                         r = AMConnectionPool.executeQueryStmt(secServreExistQuery);
/* 2521 */                         while (r.next()) {
/* 2522 */                           isSecServerExist = Boolean.valueOf(true);
/*      */                         }
/* 2524 */                         if (isSecServerExist.booleanValue()) {
/* 2525 */                           rowspan = "2";
/*      */                         }
/*      */                       } catch (Exception e) {
/* 2528 */                         e.printStackTrace();
/*      */                       } finally {
/* 2530 */                         AMConnectionPool.closeStatement(r);
/*      */                       }
/* 2532 */                       request.setAttribute("enableUpdateOperation", Boolean.valueOf(!com.adventnet.appmanager.util.Constants.isPrivilegedUser(request)));
/* 2533 */                       if (request.getAttribute("ErrorMessage") != null)
/*      */                       {
/*      */ 
/* 2536 */                         out.write("\n<table width=\"99%\" border=\"0\" cellspacing=\"2\" cellpadding=\"2\" class=\"messagebox\">\n \t<tr>\n   \t\t<td width=\"5%\" align=\"center\"><img src=\"../images/icon_message_failure.gif\" alt=\"Icon\" width=\"25\" height=\"25\"></td>\n   \t\t<td width=\"95%\" class=\"message\"> ");
/* 2537 */                         out.print(request.getAttribute("ErrorMessage"));
/* 2538 */                         out.write("</td>\n \t </tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n \t<tr>\n  \t\t<td  height=\"10\" ><img src=\"../images/spacer.gif\" ></td>\n\t </tr>\n</table>\n");
/*      */                       }
/* 2540 */                       out.write("\n<table width=\"99%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t<tr>\n\t\t<td class=\"bcsign breadcrumb_btm_space\" height=\"22\" valign=\"top\"> ");
/* 2541 */                       out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2542 */                       out.write(" &gt; <span class=\"bcactive\">");
/* 2543 */                       out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 2544 */                       out.write("</span></td>\n    \t</tr>\n</table>\n\n<form name=\"form1\" style=\"display:inline\">\n  <table width=\"99%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n    <tr>\n      <td width=\"72%\" height=\"31\" class=\"tableheading\" >&nbsp;");
/* 2545 */                       out.print(FormatUtil.getString("am.webclient.managedserver.title"));
/* 2546 */                       out.write("</td>\n    </tr>\n  </table>\n  <table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n   <tr> \n    <td height=\"28\"> \n\t");
/*      */                       
/* 2548 */                       PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.get(PresentTag.class);
/* 2549 */                       _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 2550 */                       _jspx_th_logic_005fpresent_005f2.setParent(null);
/*      */                       
/* 2552 */                       _jspx_th_logic_005fpresent_005f2.setName("ManagedServerDetails");
/* 2553 */                       int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 2554 */                       if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                         for (;;) {
/* 2556 */                           out.write("\n  \t<table valign=\"center\" width=\"100%\" cellpadding=\"1\" cellspacing=\"0\"  border=\"0\" >\n         <tr class=\"bodytextbold managedServers\">\n\t     ");
/*      */                           
/* 2558 */                           ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2559 */                           _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2560 */                           _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_logic_005fpresent_005f2);
/* 2561 */                           int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2562 */                           if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                             for (;;) {
/* 2564 */                               out.write("\n\t     \t");
/*      */                               
/* 2566 */                               WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2567 */                               _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 2568 */                               _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                               
/* 2570 */                               _jspx_th_c_005fwhen_005f0.setTest("${enableUpdateOperation eq true}");
/* 2571 */                               int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 2572 */                               if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                                 for (;;) {
/* 2574 */                                   out.write("\n\t     \t\t<td rowspan=\"");
/* 2575 */                                   out.print(rowspan);
/* 2576 */                                   out.write("\" width=\"2%\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"><input type=\"checkbox\" name=\"headercheckbox\"  onClick=\"javascript:fnSelectAll(this)\"></td>\n\t     \t\t<td rowspan=\"");
/* 2577 */                                   out.print(rowspan);
/* 2578 */                                   out.write("\" width=\"3%\" height=\"28\" valign=\"center\"  align=\"center\" class=\"columnheadingnotop\">");
/* 2579 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.serverid"));
/* 2580 */                                   out.write("</td>\n\t     \t");
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
/* 2591 */                               out.write("\n\t     \t");
/*      */                               
/* 2593 */                               OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2594 */                               _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2595 */                               _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2596 */                               int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2597 */                               if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                                 for (;;) {
/* 2599 */                                   out.write("\n\t     \t\t<td rowspan=\"");
/* 2600 */                                   out.print(rowspan);
/* 2601 */                                   out.write("\" width=\"5%\" height=\"28\" valign=\"center\" class=\"columnheadingnotop\" colspan=\"2\">");
/* 2602 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.serverid"));
/* 2603 */                                   out.write("</td>\n\t     \t");
/* 2604 */                                   int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2605 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2609 */                               if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2610 */                                 this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                               }
/*      */                               
/* 2613 */                               this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2614 */                               out.write("\n\t     ");
/* 2615 */                               int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2616 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 2620 */                           if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2621 */                             this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */                           }
/*      */                           
/* 2624 */                           this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2625 */                           out.write("\n\t     \t\t<td rowspan=\"");
/* 2626 */                           out.print(rowspan);
/* 2627 */                           out.write("\" width=\"17%\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\">");
/* 2628 */                           out.print(FormatUtil.getString("am.webclient.common.displayname.text"));
/* 2629 */                           out.write("</td>\n\t     \t\t<td rowspan=\"");
/* 2630 */                           out.print(rowspan);
/* 2631 */                           out.write("\" width=\"15%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 2632 */                           out.print(FormatUtil.getString("am.webclient.admin.add.masgroup.heading.text"));
/* 2633 */                           out.write("</td>\n\t\t        <td rowspan=\"");
/* 2634 */                           out.print(rowspan);
/* 2635 */                           out.write("\" width=\"2%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 2636 */                           out.print(FormatUtil.getString("am.webclient.managedserver.noofmonitor"));
/* 2637 */                           out.write("</td>\n\t\t        <td rowspan=\"");
/* 2638 */                           out.print(rowspan);
/* 2639 */                           out.write("\" width=\"3%\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\" nowrap>");
/* 2640 */                           out.print(FormatUtil.getString("am.webclient.support.loadfactor"));
/* 2641 */                           out.write("</td>\n\t\t        <td rowspan=\"");
/* 2642 */                           out.print(rowspan);
/* 2643 */                           out.write("\" width=\"18%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 2644 */                           out.print(FormatUtil.getString("am.webclient.common.lastpolledat.text"));
/* 2645 */                           out.write("</td>\n\t      \t\t<!--<td rowspan=\"");
/* 2646 */                           out.print(rowspan);
/* 2647 */                           out.write("\" width=\"8%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\">");
/* 2648 */                           out.print(FormatUtil.getString("Synching"));
/* 2649 */                           out.write("</td>-->\n\t      \t");
/*      */                           
/* 2651 */                           if (isSecServerExist.booleanValue())
/*      */                           {
/* 2653 */                             out.write("\n\t\t\t<td width=\"16%\" align=\"center\"  class=\"columnheadingrightborder leftBorder\" colspan='");
/* 2654 */                             out.print(easyUpgradeEnabled ? "3" : "2");
/* 2655 */                             out.write(39);
/* 2656 */                             out.write(32);
/* 2657 */                             out.write(62);
/* 2658 */                             out.print(FormatUtil.getString("am.webclient.managedserver.primaryserver"));
/* 2659 */                             out.write("</td>\n\t\t\t<td width=\"16%\" align=\"center\"  class=\"columnheadingnotop rightBorder\" colspan='");
/* 2660 */                             out.print(easyUpgradeEnabled ? "3" : "2");
/* 2661 */                             out.write(39);
/* 2662 */                             out.write(32);
/* 2663 */                             out.write(62);
/* 2664 */                             out.print(FormatUtil.getString("am.webclient.managedserver.secondaryserver"));
/* 2665 */                             out.write("</td>\n\t\t");
/*      */                           }
/*      */                           else
/*      */                           {
/* 2669 */                             out.write("\n\t\t\t<td  width=\"8%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\" >");
/* 2670 */                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2671 */                             out.write("</td>\n\t      \t\t<td  width=\"8%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\" >");
/* 2672 */                             out.print(FormatUtil.getString("am.webclient.managedserver.version"));
/* 2673 */                             out.write("</td>\n\t      \t");
/* 2674 */                             if (easyUpgradeEnabled) {
/* 2675 */                               out.write("\n \t\t\t<td  width=\"8%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\" >");
/* 2676 */                               out.print(FormatUtil.getString("am.webclient.managedserver.autoupgrade"));
/* 2677 */                               out.write("</td>\t\n \t\t\t");
/*      */                             }
/* 2679 */                             out.write("\n \t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 2683 */                           out.write("\t\t\n\t \t\t<td rowspan=\"");
/* 2684 */                           out.print(rowspan);
/* 2685 */                           out.write("\" class=\"columnheadingnotop\" height=\"28\" align=\"center\" width=\"2%\">&nbsp;</td>\n\t\t \t<td rowspan=\"");
/* 2686 */                           out.print(rowspan);
/* 2687 */                           out.write("\" class=\"columnheadingnotop\" height=\"28\" align=\"left\" width=\"3%\">");
/* 2688 */                           out.print(FormatUtil.getString("am.webclient.admin.actions.link"));
/* 2689 */                           out.write("</td>\n\t\t \t<td rowspan=\"");
/* 2690 */                           out.print(rowspan);
/* 2691 */                           out.write("\" class=\"columnheadingnotop\" height=\"28\" align=\"center\" width=\"2%\">&nbsp;</td>\n\t\t</tr>\n\t\t");
/*      */                           
/* 2693 */                           if (isSecServerExist.booleanValue())
/*      */                           {
/* 2695 */                             out.write("\n\t\t<tr>\n\t\t\t<td  width=\"6%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop leftBorder\" >");
/* 2696 */                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2697 */                             out.write("</td>\n\t      \t<td  width=\"6%\" height=\"28\" align=\"center\"  class='");
/* 2698 */                             out.print(easyUpgradeEnabled ? "columnheadingnotop" : "columnheadingrightborder");
/* 2699 */                             out.write(39);
/* 2700 */                             out.write(32);
/* 2701 */                             out.write(62);
/* 2702 */                             out.print(FormatUtil.getString("am.webclient.managedserver.version"));
/* 2703 */                             out.write("</td>\n\t      \t\t");
/* 2704 */                             if (easyUpgradeEnabled) {
/* 2705 */                               out.write("\n \t\t\t<td  width=\"6%\" height=\"28\" align=\"center\"  class=\"columnheadingrightborder\" >");
/* 2706 */                               out.print(FormatUtil.getString("am.webclient.managedserver.autoupgrade"));
/* 2707 */                               out.write("</td>\t\n \t\t\t");
/*      */                             }
/* 2709 */                             out.write("\t\t\n\t\t \t<td  width=\"6%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop\" >");
/* 2710 */                             out.print(FormatUtil.getString("am.webclient.oracle.Status"));
/* 2711 */                             out.write("</td>\n\t      \t<td  width=\"6%\" height=\"28\" align=\"center\"  class='");
/* 2712 */                             out.print(easyUpgradeEnabled ? "columnheadingnotop" : "columnheadingnotop rightBorder");
/* 2713 */                             out.write(39);
/* 2714 */                             out.write(32);
/* 2715 */                             out.write(62);
/* 2716 */                             out.print(FormatUtil.getString("am.webclient.managedserver.version"));
/* 2717 */                             out.write("</td>\n\t      \t\t");
/* 2718 */                             if (easyUpgradeEnabled) {
/* 2719 */                               out.write("\n \t\t\t<td  width=\"6%\" height=\"28\" align=\"center\"  class=\"columnheadingnotop rightBorder\" >");
/* 2720 */                               out.print(FormatUtil.getString("am.webclient.managedserver.autoupgrade"));
/* 2721 */                               out.write("</td> \n \t\t\t");
/*      */                             }
/* 2723 */                             out.write("\t\t\n\t\t</tr>\n\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 2727 */                           out.write(10);
/* 2728 */                           out.write(9);
/*      */                           
/* 2730 */                           IterateTag _jspx_th_logic_005fiterate_005f0 = (IterateTag)this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.get(IterateTag.class);
/* 2731 */                           _jspx_th_logic_005fiterate_005f0.setPageContext(_jspx_page_context);
/* 2732 */                           _jspx_th_logic_005fiterate_005f0.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                           
/* 2734 */                           _jspx_th_logic_005fiterate_005f0.setName("ManagedServerDetails");
/*      */                           
/* 2736 */                           _jspx_th_logic_005fiterate_005f0.setScope("request");
/*      */                           
/* 2738 */                           _jspx_th_logic_005fiterate_005f0.setId("row");
/*      */                           
/* 2740 */                           _jspx_th_logic_005fiterate_005f0.setIndexId("j");
/* 2741 */                           int _jspx_eval_logic_005fiterate_005f0 = _jspx_th_logic_005fiterate_005f0.doStartTag();
/* 2742 */                           if (_jspx_eval_logic_005fiterate_005f0 != 0) {
/* 2743 */                             Object row = null;
/* 2744 */                             Integer j = null;
/* 2745 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 2746 */                               out = _jspx_page_context.pushBody();
/* 2747 */                               _jspx_th_logic_005fiterate_005f0.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2748 */                               _jspx_th_logic_005fiterate_005f0.doInitBody();
/*      */                             }
/* 2750 */                             row = _jspx_page_context.findAttribute("row");
/* 2751 */                             j = (Integer)_jspx_page_context.findAttribute("j");
/*      */                             for (;;) {
/* 2753 */                               out.write("\n\t\t\n\t");
/*      */                               
/* 2755 */                               String bgclass = "whitegrayborderbr";
/* 2756 */                               if (j.intValue() % 2 != 0)
/*      */                               {
/* 2758 */                                 bgclass = "yellowgrayborderbr";
/*      */                               }
/*      */                               
/* 2761 */                               String masResId = (String)((ArrayList)row).get(0);
/*      */                               
/*      */ 
/* 2764 */                               String swSynchPropsQry1 = "Select RUNNINGVERSION,DOWNLOADSTATUS,UPGRADESTATUS from SWDetails where SERVERID=" + masResId;
/*      */                               
/* 2766 */                               ResultSet rs1 = null;
/* 2767 */                               ResultSet rs2 = null;
/* 2768 */                               String runningVersion = "-";
/* 2769 */                               String downloadStatus = "-";
/* 2770 */                               String upgradeStatus = "-";
/* 2771 */                               String autoUpgradeStatus = "-";
/* 2772 */                               String chkboxStatus = "enabled";
/*      */                               try
/*      */                               {
/* 2775 */                                 rs1 = AMConnectionPool.executeQueryStmt(swSynchPropsQry1);
/* 2776 */                                 if (rs1.next())
/*      */                                 {
/* 2778 */                                   runningVersion = rs1.getString(1);
/* 2779 */                                   downloadStatus = rs1.getString(2);
/* 2780 */                                   upgradeStatus = rs1.getString(3);
/*      */                                 }
/*      */                                 
/* 2783 */                                 if ((downloadStatus.equals("COMPLETED")) && (upgradeStatus.equals("COMPLETED")))
/*      */                                 {
/* 2785 */                                   chkboxStatus = "disabled";
/* 2786 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgraded");
/* 2787 */                                 } else if ((downloadStatus.equals("COMPLETED")) && (upgradeStatus.equals("FAILED")))
/*      */                                 {
/* 2789 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgrade.failed");
/*      */                                 }
/* 2791 */                                 else if ((downloadStatus.equals("COMPLETED")) && (upgradeStatus.equals("IN_PROGRESS")))
/*      */                                 {
/* 2793 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgrading");
/* 2794 */                                 } else if ((downloadStatus.equals("COMPLETED")) && (upgradeStatus.equals("STARTED")))
/*      */                                 {
/* 2796 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgrading");
/* 2797 */                                 } else if ((downloadStatus.equals("COMPLETED")) && (upgradeStatus.equals("NONE")))
/*      */                                 {
/* 2799 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.downloaded");
/* 2800 */                                 } else if ((downloadStatus.equals("FAILED")) && (upgradeStatus.equals("NONE")))
/*      */                                 {
/* 2802 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.download.failed");
/*      */                                 }
/* 2804 */                                 else if ((downloadStatus.equals("IN_PROGRESS")) && (upgradeStatus.equals("NONE")))
/*      */                                 {
/* 2806 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.downloading");
/* 2807 */                                 } else if ((downloadStatus.equals("STARTED")) && (upgradeStatus.equals("NONE")))
/*      */                                 {
/* 2809 */                                   autoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.downloading");
/* 2810 */                                 } else if ((downloadStatus.equals("NONE")) && (upgradeStatus.equals("NONE")))
/*      */                                 {
/* 2812 */                                   autoUpgradeStatus = "-";
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 2817 */                                 e.printStackTrace();
/*      */                               } finally {
/* 2819 */                                 AMConnectionPool.closeStatement(rs1);
/*      */                               }
/*      */                               
/*      */ 
/* 2823 */                               String secRunningVersion = "-";
/* 2824 */                               String secDownloadStatus = "-";
/* 2825 */                               String secUpgradeStatus = "-";
/* 2826 */                               String secAutoUpgradeStatus = "-";
/* 2827 */                               String secChkboxStatus = "enabled";
/* 2828 */                               String secMasResId = (String)((ArrayList)row).get(13);
/*      */                               
/* 2830 */                               if (!"-".equals(secMasResId)) {
/* 2831 */                                 String secswSynchPropsQry = "Select RUNNINGVERSION,DOWNLOADSTATUS,UPGRADESTATUS from SWDetails where SERVERID=" + secMasResId;
/*      */                                 try {
/* 2833 */                                   rs2 = AMConnectionPool.executeQueryStmt(secswSynchPropsQry);
/* 2834 */                                   if (rs2.next())
/*      */                                   {
/* 2836 */                                     secRunningVersion = rs2.getString(1);
/* 2837 */                                     secDownloadStatus = rs2.getString(2);
/* 2838 */                                     secUpgradeStatus = rs2.getString(3);
/*      */                                   }
/*      */                                   
/*      */ 
/* 2842 */                                   if ((secDownloadStatus.equals("COMPLETED")) && (secUpgradeStatus.equals("COMPLETED")))
/*      */                                   {
/* 2844 */                                     secChkboxStatus = "disabled";
/* 2845 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgraded");
/* 2846 */                                   } else if ((secDownloadStatus.equals("COMPLETED")) && (secUpgradeStatus.equals("FAILED")))
/*      */                                   {
/* 2848 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgrade.failed");
/*      */                                   }
/* 2850 */                                   else if ((secDownloadStatus.equals("COMPLETED")) && (secUpgradeStatus.equals("IN_PROGRESS")))
/*      */                                   {
/* 2852 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgrading");
/* 2853 */                                   } else if ((secDownloadStatus.equals("COMPLETED")) && (secUpgradeStatus.equals("STARTED")))
/*      */                                   {
/* 2855 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.upgrading");
/* 2856 */                                   } else if ((secDownloadStatus.equals("COMPLETED")) && (secUpgradeStatus.equals("NONE")))
/*      */                                   {
/* 2858 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.downloaded");
/* 2859 */                                   } else if ((secDownloadStatus.equals("FAILED")) && (secUpgradeStatus.equals("NONE")))
/*      */                                   {
/* 2861 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.download.failed");
/*      */                                   }
/* 2863 */                                   else if ((secDownloadStatus.equals("IN_PROGRESS")) && (secUpgradeStatus.equals("NONE")))
/*      */                                   {
/* 2865 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.downloading");
/* 2866 */                                   } else if ((secDownloadStatus.equals("STARTED")) && (secUpgradeStatus.equals("NONE")))
/*      */                                   {
/* 2868 */                                     secAutoUpgradeStatus = FormatUtil.getString("am.webclient.managedserver.downloading");
/* 2869 */                                   } else if ((secDownloadStatus.equals("NONE")) && (secUpgradeStatus.equals("NONE")))
/*      */                                   {
/* 2871 */                                     secAutoUpgradeStatus = "-";
/*      */                                   }
/*      */                                   
/*      */ 
/*      */                                 }
/*      */                                 catch (Exception e)
/*      */                                 {
/* 2878 */                                   e.printStackTrace();
/*      */                                 } finally {
/* 2880 */                                   AMConnectionPool.closeStatement(rs2);
/*      */                                 }
/*      */                               }
/*      */                               
/*      */ 
/* 2885 */                               out.write("\t\n\t<tr>\n\t\t");
/*      */                               
/* 2887 */                               int id = Integer.parseInt((String)((ArrayList)row).get(8)) / com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*      */                               
/* 2889 */                               out.write("\n\t     ");
/*      */                               
/* 2891 */                               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2892 */                               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2893 */                               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_logic_005fiterate_005f0);
/* 2894 */                               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2895 */                               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                                 for (;;) {
/* 2897 */                                   out.write("\n\t     \t");
/*      */                                   
/* 2899 */                                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2900 */                                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2901 */                                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                                   
/* 2903 */                                   _jspx_th_c_005fwhen_005f1.setTest("${enableUpdateOperation eq true}");
/* 2904 */                                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2905 */                                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                                     for (;;) {
/* 2907 */                                       out.write("\n\t     \t\n\t\t\t\t<td width=\"2%\" height=\"22\" class=\"");
/* 2908 */                                       out.print(bgclass);
/* 2909 */                                       out.write("\"><input type=\"checkbox\" name=\"checkbox\" value=\"");
/* 2910 */                                       out.print(((ArrayList)row).get(0));
/* 2911 */                                       out.write("\"></td>\n\t\t\t\t<td width=\"3%\" height=\"22\" align=\"center\" class=\"");
/* 2912 */                                       out.print(bgclass);
/* 2913 */                                       out.write("\" title=\"");
/* 2914 */                                       out.print(id);
/* 2915 */                                       out.write(34);
/* 2916 */                                       out.write(62);
/* 2917 */                                       out.print(id);
/* 2918 */                                       out.write("</td>\n\t     \t");
/* 2919 */                                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2920 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2924 */                                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2925 */                                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                                   }
/*      */                                   
/* 2928 */                                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2929 */                                   out.write("\n\t     \t");
/*      */                                   
/* 2931 */                                   OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2932 */                                   _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 2933 */                                   _jspx_th_c_005fotherwise_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/* 2934 */                                   int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 2935 */                                   if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */                                     for (;;) {
/* 2937 */                                       out.write("\n\t     \t\t\t<td width=\"5%\" height=\"22\" class=\"");
/* 2938 */                                       out.print(bgclass);
/* 2939 */                                       out.write("\" title=\"");
/* 2940 */                                       out.print(id);
/* 2941 */                                       out.write("\" colspan=\"2\">");
/* 2942 */                                       out.print(id);
/* 2943 */                                       out.write("</td>\n\t     \t");
/* 2944 */                                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 2945 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 2949 */                                   if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 2950 */                                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1); return;
/*      */                                   }
/*      */                                   
/* 2953 */                                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 2954 */                                   out.write("\n\t     ");
/* 2955 */                                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2956 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2960 */                               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2961 */                                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */                               }
/*      */                               
/* 2964 */                               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2965 */                               out.write("\t\n\t     ");
/*      */                               
/* 2967 */                               String hostname = (String)((ArrayList)row).get(2);
/* 2968 */                               String port = (String)((ArrayList)row).get(4);
/*      */                               try {
/* 2970 */                                 if ((!"-".equals(secMasResId)) || (secMasResId == null))
/*      */                                 {
/*      */ 
/* 2973 */                                   if (("1000".equals(((ArrayList)row).get(16))) && (!"1000".equals(((ArrayList)row).get(9)))) {
/* 2974 */                                     hostname = (String)((ArrayList)row).get(14);
/* 2975 */                                     port = (String)((ArrayList)row).get(15);
/*      */                                   } }
/*      */                               } catch (Exception e) {
/* 2978 */                                 e.printStackTrace();
/*      */                               }
/* 2980 */                               out.write("\t\t\n\t\t\t\t<td width=\"17%\" height=\"22\" class=\"");
/* 2981 */                               out.print(bgclass);
/* 2982 */                               out.write("\" title=\"");
/* 2983 */                               out.print(hostname);
/* 2984 */                               out.write(95);
/* 2985 */                               out.print(port);
/* 2986 */                               out.write("\" nowrap>\n\t\t\t\t\t<a href=\"https://");
/* 2987 */                               out.print(hostname);
/* 2988 */                               out.write(58);
/* 2989 */                               out.print(port);
/* 2990 */                               out.write("\" target=\"_blank\" class=\"bodytext\">");
/* 2991 */                               out.print(getTrimmedText((String)((ArrayList)row).get(1), 25));
/* 2992 */                               out.write("</a>\n\t\t\t\t</td>\n\t\t\t\t<td width=\"15%\" height=\"22\" align=\"center\" class=\"");
/* 2993 */                               out.print(bgclass);
/* 2994 */                               out.write("\" title=\"\">");
/* 2995 */                               out.print(((ArrayList)row).get(12));
/* 2996 */                               out.write("</td>\n\t\t");
/*      */                               
/* 2998 */                               int count = 0;
/* 2999 */                               ResultSet rs = null;
/*      */                               try
/*      */                               {
/* 3002 */                                 int startingId = Integer.parseInt((String)((ArrayList)row).get(8));
/* 3003 */                                 int endingId = startingId + com.adventnet.appmanager.server.framework.comm.Constants.RANGE;
/*      */                                 
/* 3005 */                                 String eumChildListCond = "AM_ManagedObject.resourceid NOT IN (" + com.adventnet.appmanager.util.Constants.getEUMChildString() + ")";
/* 3006 */                                 String selectQuery = "select count(*) AS COUNT from AM_ManagedObject where TYPE IN " + com.adventnet.appmanager.util.Constants.resourceTypes + " " + com.adventnet.appmanager.util.Constants.excludeIntf + " and " + eumChildListCond + " and AM_ManagedObject.RESOURCEID between " + startingId + " and " + endingId;
/*      */                                 
/*      */ 
/* 3009 */                                 rs = AMConnectionPool.executeQueryStmt(selectQuery);
/* 3010 */                                 if (rs.next())
/*      */                                 {
/* 3012 */                                   count = rs.getInt("COUNT");
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception e)
/*      */                               {
/* 3017 */                                 e.printStackTrace();
/*      */                               } finally {
/* 3019 */                                 AMConnectionPool.closeStatement(rs);
/*      */                               }
/*      */                               
/* 3022 */                               out.write("\n        \t\t\t<td width=\"2%\" height=\"22\" align=\"center\" class=\"");
/* 3023 */                               out.print(bgclass);
/* 3024 */                               out.write("\" title=\"");
/* 3025 */                               out.print(count);
/* 3026 */                               out.write(34);
/* 3027 */                               out.write(62);
/* 3028 */                               out.print(count);
/* 3029 */                               out.write("</td>\n        \t\t\t<td width=\"3%\" height=\"22\" class=\"");
/* 3030 */                               out.print(bgclass);
/* 3031 */                               out.write("\" align=\"center\" title=\"");
/* 3032 */                               out.print(((ArrayList)row).get(10));
/* 3033 */                               out.write(34);
/* 3034 */                               out.write(62);
/* 3035 */                               out.print(((ArrayList)row).get(10));
/* 3036 */                               out.write("</td>\n        \t");
/*      */                               
/*      */ 
/* 3039 */                               String serverStatus = "-";
/* 3040 */                               String synchImg = "";
/* 3041 */                               String sychStatus = FormatUtil.formatDT((String)((ArrayList)row).get(6));
/*      */                               try
/*      */                               {
/* 3044 */                                 serverStatus = (String)com.adventnet.appmanager.server.framework.comm.AMDistributionProcess.serverStatus.get(((ArrayList)row).get(0));
/*      */                                 
/* 3046 */                                 if ((serverStatus != null) && (serverStatus.equals("ended"))) {
/* 3047 */                                   synchImg = "/images/Alarm-green-tick.gif";
/* 3048 */                                   sychStatus = FormatUtil.getString("am.webclient.managedserver.synching.completed", new String[] { FormatUtil.formatDT((String)((ArrayList)row).get(6)) });
/* 3049 */                                 } else if ((serverStatus != null) && (serverStatus.equals("progress"))) {
/* 3050 */                                   synchImg = "/images/in_progress.gif";
/* 3051 */                                   sychStatus = FormatUtil.getString("am.webclient.managedserver.synching.inprogress");
/*      */                                 }
/* 3053 */                                 else if ((serverStatus != null) && (serverStatus.equals("started"))) {
/* 3054 */                                   synchImg = "/images/in_progress.gif";
/* 3055 */                                   sychStatus = FormatUtil.getString("Sam.webclient.managedserver.synching.inprogress");
/*      */                                 }
/*      */                               }
/*      */                               catch (Exception ex) {
/* 3059 */                                 ex.printStackTrace();
/*      */                               }
/*      */                               
/* 3062 */                               String lastDc = "-1";
/* 3063 */                               if (((ArrayList)row).get(6).equals(lastDc))
/*      */                               {
/*      */ 
/* 3066 */                                 out.write("\n\t\t\t\t<td width=\"15%\" height=\"22\" class=\"");
/* 3067 */                                 out.print(bgclass);
/* 3068 */                                 out.write("\" align=\"center\">  - </td>\n\t\t");
/*      */                               }
/*      */                               else
/*      */                               {
/* 3072 */                                 out.write("\n\t\t\t\t<td width=\"18%\" height=\"22\" class=\"");
/* 3073 */                                 out.print(bgclass);
/* 3074 */                                 out.write("\" title=\"");
/* 3075 */                                 out.print(sychStatus);
/* 3076 */                                 out.write("\" align=\"center\">  ");
/* 3077 */                                 out.print(FormatUtil.formatDT((String)((ArrayList)row).get(6)));
/* 3078 */                                 out.write(" &nbsp;<img src=\"");
/* 3079 */                                 out.print(synchImg);
/* 3080 */                                 out.write("\"></td>\t\t\n\t\t");
/*      */                               }
/*      */                               
/* 3083 */                               String serverName = ((ArrayList)row).get(2) + "_" + ((ArrayList)row).get(3);
/* 3084 */                               if (((ArrayList)row).get(9).equals("1000"))
/*      */                               {
/*      */ 
/* 3087 */                                 out.write("\n\t\t  \t\t<td width=\"6%\" height=\"22\" align=\"center\"  class=\"");
/* 3088 */                                 out.print(bgclass);
/* 3089 */                                 out.write("\" align=\"center\" title=\"");
/* 3090 */                                 out.print(FormatUtil.getString((String)((ArrayList)row).get(9), new String[] { serverName }));
/* 3091 */                                 out.write("\"><img src=\"/images/icon_availability_up.gif\"  border=\"0\"></td>\n\t\t");
/*      */                               }
/* 3093 */                               else if (((ArrayList)row).get(9).equals("1008"))
/*      */                               {
/* 3095 */                                 out.write("\n\t\t   \t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3096 */                                 out.print(bgclass);
/* 3097 */                                 out.write("\" align=\"center\" title=\"");
/* 3098 */                                 out.print(FormatUtil.getString((String)((ArrayList)row).get(9), new String[] { serverName }));
/* 3099 */                                 out.write("\"><img src=\"/images/icon_availability_standby.gif\"  border=\"0\"></td>\n\t\t");
/*      */                               } else {
/* 3101 */                                 out.write("\n\t\t   \t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3102 */                                 out.print(bgclass);
/* 3103 */                                 out.write("\" align=\"center\" title=\"");
/* 3104 */                                 out.print(FormatUtil.getString((String)((ArrayList)row).get(9), new String[] { serverName }));
/* 3105 */                                 out.write("\"><img src=\"/images/icon_availability_down.gif\"  border=\"0\"></td>\n\t\t");
/*      */                               }
/*      */                               
/* 3108 */                               out.write("\n\t\t\t\t\n \t\t\t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3109 */                               out.print(bgclass);
/* 3110 */                               out.write("\" title=\"");
/* 3111 */                               out.print(runningVersion);
/* 3112 */                               out.write("\" align=\"center\"> ");
/* 3113 */                               out.print(runningVersion);
/* 3114 */                               out.write("</td>\n\t\t\t\t\n\t\t\t\t");
/* 3115 */                               if (easyUpgradeEnabled) {
/* 3116 */                                 out.write("\n\t\t\t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3117 */                                 out.print(bgclass);
/* 3118 */                                 out.write("\" title=\"");
/* 3119 */                                 out.print(autoUpgradeStatus);
/* 3120 */                                 out.write("\" align=\"center\">\n\t\t\t\t");
/*      */                                 
/* 3122 */                                 if ((autoUpgradeStatus.equalsIgnoreCase("Download Failed")) || (autoUpgradeStatus.equalsIgnoreCase("Upgrade Failed")))
/*      */                                 {
/*      */ 
/* 3125 */                                   out.write("\n\t\t\t\t\t<font color=\"gray\"> ");
/* 3126 */                                   out.print(autoUpgradeStatus + "");
/* 3127 */                                   out.write("</font>\n\t\t\t\t");
/*      */                                 } else {
/* 3129 */                                   out.write("\n\t\t\t\t\t");
/* 3130 */                                   out.print(autoUpgradeStatus);
/* 3131 */                                   out.write("\t\n\t\t\t\t");
/*      */                                 }
/* 3133 */                                 out.write("\n\t\t\t\t</td>\n\t\t\t\t");
/*      */                               }
/* 3135 */                               out.write(10);
/* 3136 */                               out.write(9);
/* 3137 */                               out.write(9);
/*      */                               
/*      */ 
/* 3140 */                               if (isSecServerExist.booleanValue())
/*      */                               {
/* 3142 */                                 if (!"-".equals(secMasResId)) {
/* 3143 */                                   String sec_ServerName = ((ArrayList)row).get(14) + "_" + ((ArrayList)row).get(15);
/* 3144 */                                   if ("1000".equals(((ArrayList)row).get(16)))
/*      */                                   {
/*      */ 
/* 3147 */                                     out.write("\n\t\t  \t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3148 */                                     out.print(bgclass);
/* 3149 */                                     out.write("\" align=\"center\" title=\"");
/* 3150 */                                     out.print(FormatUtil.getString((String)((ArrayList)row).get(16), new String[] { sec_ServerName }));
/* 3151 */                                     out.write("\"><img src=\"/images/icon_availability_up.gif\"  border=\"0\"></td>\n\t\t");
/*      */                                   }
/* 3153 */                                   else if ("1008".equals(((ArrayList)row).get(16)))
/*      */                                   {
/* 3155 */                                     out.write("\n\t\t   \t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3156 */                                     out.print(bgclass);
/* 3157 */                                     out.write("\" align=\"center\" title=\"");
/* 3158 */                                     out.print(FormatUtil.getString((String)((ArrayList)row).get(16), new String[] { sec_ServerName }));
/* 3159 */                                     out.write("\"><img src=\"/images/icon_availability_standby.gif\"  border=\"0\"></td>\n\t\t");
/*      */                                   } else {
/* 3161 */                                     out.write("\n\t\t   \t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3162 */                                     out.print(bgclass);
/* 3163 */                                     out.write("\" align=\"center\" title=\"");
/* 3164 */                                     out.print(FormatUtil.getString((String)((ArrayList)row).get(16), new String[] { sec_ServerName }));
/* 3165 */                                     out.write("\"><img src=\"/images/icon_availability_down.gif\"  border=\"0\"></td>\n\t\t");
/*      */                                   }
/*      */                                 } else {
/* 3168 */                                   out.write("\n\t\t\t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3169 */                                   out.print(bgclass);
/* 3170 */                                   out.write("\" align=\"center\" title=\"");
/* 3171 */                                   out.print(FormatUtil.getString("am.webclient.managedserver.nosecondaryserver"));
/* 3172 */                                   out.write("\"> - </td>\n\t\t");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3176 */                                 out.write("\n\t\t\t\t\n\t\t\t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3177 */                                 out.print(bgclass);
/* 3178 */                                 out.write("\" title=\"");
/* 3179 */                                 out.print(secRunningVersion);
/* 3180 */                                 out.write("\" align=\"center\"> ");
/* 3181 */                                 out.print(secRunningVersion);
/* 3182 */                                 out.write("</td>\t\t\t\t\n\t\t\t\t");
/* 3183 */                                 if (easyUpgradeEnabled) {
/* 3184 */                                   out.write("\t\n\t\t\t\t<td width=\"6%\" height=\"22\" align=\"center\" class=\"");
/* 3185 */                                   out.print(bgclass);
/* 3186 */                                   out.write("\" title=\"");
/* 3187 */                                   out.print(secAutoUpgradeStatus);
/* 3188 */                                   out.write("\" align=\"center\">\n\t\t\t\t");
/*      */                                   
/* 3190 */                                   if ((secAutoUpgradeStatus.equalsIgnoreCase("Download Failed")) || (secAutoUpgradeStatus.equalsIgnoreCase("Upgrade Failed")))
/*      */                                   {
/* 3192 */                                     out.write("\n\t\t\t\t\t<font color=\"gray\"> ");
/* 3193 */                                     out.print(secAutoUpgradeStatus);
/* 3194 */                                     out.write("</font>\n\t\t\t\t");
/*      */                                   } else {
/* 3196 */                                     out.write("\n\t\t\t\t\t");
/* 3197 */                                     out.print(secAutoUpgradeStatus);
/* 3198 */                                     out.write("\t\n\t\t\t\t");
/*      */                                   }
/* 3200 */                                   out.write("\n\t\t\t\t</td>\n\t\t\t\t");
/*      */                                 }
/* 3202 */                                 out.write("\n\t\t\t\n\t\t");
/*      */                               }
/*      */                               
/* 3205 */                               String Enable = FormatUtil.getString("am.webclient.maintenance.enable");
/* 3206 */                               if (((String)((ArrayList)row).get(11)).equals("PY")) {
/* 3207 */                                 if (((ArrayList)row).get(7).equals("TRUE"))
/*      */                                 {
/* 3209 */                                   out.write("\n\t\t \t    ");
/*      */                                   
/* 3211 */                                   ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3212 */                                   _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 3213 */                                   _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_logic_005fiterate_005f0);
/* 3214 */                                   int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 3215 */                                   if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                                     for (;;) {
/* 3217 */                                       out.write("\n\t     \t\t      ");
/*      */                                       
/* 3219 */                                       WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3220 */                                       _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3221 */                                       _jspx_th_c_005fwhen_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/*      */                                       
/* 3223 */                                       _jspx_th_c_005fwhen_005f2.setTest("${enableUpdateOperation eq true}");
/* 3224 */                                       int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3225 */                                       if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */                                         for (;;) {
/* 3227 */                                           out.write("\n\t\t\t\t<td width=\"2%\" height=\"22\" class=\"");
/* 3228 */                                           out.print(bgclass);
/* 3229 */                                           out.write("\" align=\"center\" title=\"");
/* 3230 */                                           out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.enable.text"));
/* 3231 */                                           out.write(34);
/* 3232 */                                           out.write(62);
/*      */                                           
/* 3234 */                                           PresentTag _jspx_th_logic_005fpresent_005f3 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3235 */                                           _jspx_th_logic_005fpresent_005f3.setPageContext(_jspx_page_context);
/* 3236 */                                           _jspx_th_logic_005fpresent_005f3.setParent(_jspx_th_c_005fwhen_005f2);
/*      */                                           
/* 3238 */                                           _jspx_th_logic_005fpresent_005f3.setRole("ENTERPRISEADMIN");
/* 3239 */                                           int _jspx_eval_logic_005fpresent_005f3 = _jspx_th_logic_005fpresent_005f3.doStartTag();
/* 3240 */                                           if (_jspx_eval_logic_005fpresent_005f3 != 0) {
/*      */                                             for (;;) {
/* 3242 */                                               out.write("<a href=\"/adminAction.do?method=showManagedServers&serverID=");
/* 3243 */                                               out.print(((ArrayList)row).get(0));
/* 3244 */                                               out.write("&boolValue=");
/* 3245 */                                               out.print(((ArrayList)row).get(7));
/* 3246 */                                               out.write("&serverType=");
/* 3247 */                                               out.print(((ArrayList)row).get(11));
/* 3248 */                                               out.write("\" class=\"bodytext\"><img src=\"/images/icon_tickmark.gif\"  vspace=\"5\" border=\"0\">");
/* 3249 */                                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f3.doAfterBody();
/* 3250 */                                               if (evalDoAfterBody != 2)
/*      */                                                 break;
/*      */                                             }
/*      */                                           }
/* 3254 */                                           if (_jspx_th_logic_005fpresent_005f3.doEndTag() == 5) {
/* 3255 */                                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3); return;
/*      */                                           }
/*      */                                           
/* 3258 */                                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f3);
/* 3259 */                                           out.write("</td>\t     \t\n\t     \t\t      ");
/* 3260 */                                           int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3261 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3265 */                                       if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3266 */                                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2); return;
/*      */                                       }
/*      */                                       
/* 3269 */                                       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3270 */                                       out.write("\n\t     \t\t      ");
/*      */                                       
/* 3272 */                                       OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3273 */                                       _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 3274 */                                       _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 3275 */                                       int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 3276 */                                       if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                                         for (;;) {
/* 3278 */                                           out.write("\n\t     \t\t\t<td width=\"2%\" height=\"22\" class=\"");
/* 3279 */                                           out.print(bgclass);
/* 3280 */                                           out.write("\" align=\"center\">");
/* 3281 */                                           if (_jspx_meth_logic_005fpresent_005f4(_jspx_th_c_005fotherwise_005f2, _jspx_page_context))
/*      */                                             return;
/* 3283 */                                           out.write("</td>\n\t     \t\t      ");
/* 3284 */                                           int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 3285 */                                           if (evalDoAfterBody != 2)
/*      */                                             break;
/*      */                                         }
/*      */                                       }
/* 3289 */                                       if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 3290 */                                         this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                                       }
/*      */                                       
/* 3293 */                                       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 3294 */                                       out.write("\n\t     \t\t    ");
/* 3295 */                                       int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 3296 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3300 */                                   if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 3301 */                                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */                                   }
/*      */                                   
/* 3304 */                                   this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 3305 */                                   out.write("\t\n\t\t \t");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3309 */                                   out.write("\n\t       \t\t\t<td width=\"2%\" height=\"22\" class=\"");
/* 3310 */                                   out.print(bgclass);
/* 3311 */                                   out.write("\" align=\"center\" title=\"");
/* 3312 */                                   out.print(FormatUtil.getString("am.webclient.schedulereport.showwschedule.disable.text"));
/* 3313 */                                   out.write(34);
/* 3314 */                                   out.write(62);
/*      */                                   
/* 3316 */                                   PresentTag _jspx_th_logic_005fpresent_005f5 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3317 */                                   _jspx_th_logic_005fpresent_005f5.setPageContext(_jspx_page_context);
/* 3318 */                                   _jspx_th_logic_005fpresent_005f5.setParent(_jspx_th_logic_005fiterate_005f0);
/*      */                                   
/* 3320 */                                   _jspx_th_logic_005fpresent_005f5.setRole("ENTERPRISEADMIN");
/* 3321 */                                   int _jspx_eval_logic_005fpresent_005f5 = _jspx_th_logic_005fpresent_005f5.doStartTag();
/* 3322 */                                   if (_jspx_eval_logic_005fpresent_005f5 != 0) {
/*      */                                     for (;;) {
/* 3324 */                                       out.write("<a href=\"/adminAction.do?method=showManagedServers&serverID=");
/* 3325 */                                       out.print(((ArrayList)row).get(0));
/* 3326 */                                       out.write("&boolValue=");
/* 3327 */                                       out.print(((ArrayList)row).get(7));
/* 3328 */                                       out.write("&serverType=");
/* 3329 */                                       out.print(((ArrayList)row).get(11));
/* 3330 */                                       out.write("\" class=\"bodytext\"><img src=\"/images/cross.gif\" vspace=\"5\" border=\"0\">");
/* 3331 */                                       int evalDoAfterBody = _jspx_th_logic_005fpresent_005f5.doAfterBody();
/* 3332 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/*      */                                   }
/* 3336 */                                   if (_jspx_th_logic_005fpresent_005f5.doEndTag() == 5) {
/* 3337 */                                     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5); return;
/*      */                                   }
/*      */                                   
/* 3340 */                                   this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f5);
/* 3341 */                                   out.write("</td>\t \n\t    \t\t");
/*      */                                 }
/* 3343 */                                 out.write("\n\t    \t\t\n\t\t   \t\t<td width=\"3%\" height=\"22\" class=\"");
/* 3344 */                                 out.print(bgclass);
/* 3345 */                                 out.write("\" align=\"center\" title=\"");
/* 3346 */                                 out.print(FormatUtil.getString("am.webclient.managedserver.collectdatanow"));
/* 3347 */                                 out.write("\"><a href=\"/adminAction.do?method=fetchDataNowForManagedServer&serverID=");
/* 3348 */                                 out.print(((ArrayList)row).get(0));
/* 3349 */                                 out.write("&serverType=");
/* 3350 */                                 out.print(((ArrayList)row).get(11));
/* 3351 */                                 out.write("\" class=\"bodytext\"><img src=\"/images/icon_refresh.gif\"  border=\"0\"></td>\n\t\t\t\t<td width=\"2%\" height=\"22\" class=\"");
/* 3352 */                                 out.print(bgclass);
/* 3353 */                                 out.write("\" align=\"center\" title=\"Edit\"><a href=\"/adminAction.do?method=showManagedServers&serverID=");
/* 3354 */                                 out.print(((ArrayList)row).get(0));
/* 3355 */                                 out.write("&edit=TRUE&serverType=");
/* 3356 */                                 out.print(((ArrayList)row).get(11));
/* 3357 */                                 out.write("\">\n\t\t\t\t");
/* 3358 */                                 if (_jspx_meth_logic_005fpresent_005f6(_jspx_th_logic_005fiterate_005f0, _jspx_page_context))
/*      */                                   return;
/* 3360 */                                 out.write("</a></td>");
/*      */                               }
/* 3362 */                               out.write("\n      \t\t</tr>\n        \n\t");
/* 3363 */                               int evalDoAfterBody = _jspx_th_logic_005fiterate_005f0.doAfterBody();
/* 3364 */                               row = _jspx_page_context.findAttribute("row");
/* 3365 */                               j = (Integer)_jspx_page_context.findAttribute("j");
/* 3366 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 3369 */                             if (_jspx_eval_logic_005fiterate_005f0 != 1) {
/* 3370 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 3373 */                           if (_jspx_th_logic_005fiterate_005f0.doEndTag() == 5) {
/* 3374 */                             this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0); return;
/*      */                           }
/*      */                           
/* 3377 */                           this._005fjspx_005ftagPool_005flogic_005fiterate_0026_005fscope_005fname_005findexId_005fid.reuse(_jspx_th_logic_005fiterate_005f0);
/* 3378 */                           out.write("        \n\t</table>\n \n\t<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"tablebottom\" >\n\t<tr>\n    \t<td class=\"bodytext\" height=\"14\" align=\"left\">\n    \t\t");
/*      */                           
/* 3380 */                           PresentTag _jspx_th_logic_005fpresent_005f7 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3381 */                           _jspx_th_logic_005fpresent_005f7.setPageContext(_jspx_page_context);
/* 3382 */                           _jspx_th_logic_005fpresent_005f7.setParent(_jspx_th_logic_005fpresent_005f2);
/*      */                           
/* 3384 */                           _jspx_th_logic_005fpresent_005f7.setRole("ENTERPRISEADMIN");
/* 3385 */                           int _jspx_eval_logic_005fpresent_005f7 = _jspx_th_logic_005fpresent_005f7.doStartTag();
/* 3386 */                           if (_jspx_eval_logic_005fpresent_005f7 != 0) {
/*      */                             for (;;) {
/* 3388 */                               out.write("\n    \t\t\t");
/*      */                               
/* 3390 */                               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3391 */                               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3392 */                               _jspx_th_c_005fif_005f1.setParent(_jspx_th_logic_005fpresent_005f7);
/*      */                               
/* 3394 */                               _jspx_th_c_005fif_005f1.setTest("${enableUpdateOperation}");
/* 3395 */                               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3396 */                               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                                 for (;;) {
/* 3398 */                                   out.write("\n    \t\t\t<A HREF=\"javascript:deleteSelections();\" class=\"staticlinks\">");
/* 3399 */                                   out.print(FormatUtil.getString("am.webclient.fault.alarm.operations.delete"));
/* 3400 */                                   out.write("</a>\n\t\t \t\t&nbsp;&nbsp;|&nbsp;&nbsp;\n\t\t \t\t<a href=\"/showTile.do?TileName=.NewManagedServer\" class=\"staticlinks\">");
/* 3401 */                                   out.print(FormatUtil.getString("am.webclient.adminserver.addnew.link"));
/* 3402 */                                   out.write("</a>\n\t\t \t\t&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;\n\t\t \t\t<A HREF=\"javascript:refreshServers();\" class=\"staticlinks\">");
/* 3403 */                                   out.print(FormatUtil.getString("managedserver.fetch.now"));
/* 3404 */                                   out.write("</a>\n\t\t \t\t&nbsp;&nbsp;\n\t\t \t\t");
/* 3405 */                                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3406 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 3410 */                               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3411 */                                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                               }
/*      */                               
/* 3414 */                               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3415 */                               out.write("\n\t\t \t\t");
/*      */                               
/* 3417 */                               if ((request.getUserPrincipal().getName().equalsIgnoreCase("admin")) && (easyUpgradeEnabled))
/*      */                               {
/*      */ 
/* 3420 */                                 out.write("\n\t\t \t\t|&nbsp;&nbsp;\n\t\t \t\t<a href=\"javascript:swAction('download')\" class=\"staticlinks\">");
/* 3421 */                                 out.print(FormatUtil.getString("am.product.sp.download.now"));
/* 3422 */                                 out.write("</a>\n\t\t \t\t&nbsp;&nbsp;|&nbsp;&nbsp;\n\t\t \t\t<a href=\"javascript:swAction('upgrade')\" class=\"staticlinks\">");
/* 3423 */                                 out.print(FormatUtil.getString("am.product.sp.upgrade.now"));
/* 3424 */                                 out.write("</a>\n\t\t \t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 3428 */                               out.write("\n    \t\t\t&nbsp;&nbsp;\n    \t\t");
/* 3429 */                               int evalDoAfterBody = _jspx_th_logic_005fpresent_005f7.doAfterBody();
/* 3430 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/*      */                           }
/* 3434 */                           if (_jspx_th_logic_005fpresent_005f7.doEndTag() == 5) {
/* 3435 */                             this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7); return;
/*      */                           }
/*      */                           
/* 3438 */                           this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f7);
/* 3439 */                           out.write("\n    \t</td>\n\t<td class=\"bodytext\">&nbsp;</td>\n          </tr>\n\t</table> \n\t                \n");
/* 3440 */                           int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3441 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3445 */                       if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3446 */                         this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */                       }
/*      */                       
/* 3449 */                       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005fname.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3450 */                       out.write("\n           \n");
/*      */                       
/* 3452 */                       NotPresentTag _jspx_th_logic_005fnotPresent_005f2 = (NotPresentTag)this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.get(NotPresentTag.class);
/* 3453 */                       _jspx_th_logic_005fnotPresent_005f2.setPageContext(_jspx_page_context);
/* 3454 */                       _jspx_th_logic_005fnotPresent_005f2.setParent(null);
/*      */                       
/* 3456 */                       _jspx_th_logic_005fnotPresent_005f2.setName("ManagedServerDetails");
/* 3457 */                       int _jspx_eval_logic_005fnotPresent_005f2 = _jspx_th_logic_005fnotPresent_005f2.doStartTag();
/* 3458 */                       if (_jspx_eval_logic_005fnotPresent_005f2 != 0) {
/*      */                         for (;;) {
/* 3460 */                           out.write("\n        <table cellpadding=\"0\">\n          <tr>\n\t\t\t<td class=\"bodytext\">&nbsp;&nbsp;");
/* 3461 */                           out.print(FormatUtil.getString("am.webclient.managedserver.nomanagedserver"));
/* 3462 */                           out.write("&nbsp;<a href=\"/showTile.do?TileName=.NewManagedServer\" class=\"staticlinks\">");
/* 3463 */                           out.print(FormatUtil.getString("am.webclient.threshold.addnew"));
/* 3464 */                           out.write("</a></td>\n          </tr></table>\n");
/* 3465 */                           int evalDoAfterBody = _jspx_th_logic_005fnotPresent_005f2.doAfterBody();
/* 3466 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3470 */                       if (_jspx_th_logic_005fnotPresent_005f2.doEndTag() == 5) {
/* 3471 */                         this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2); return;
/*      */                       }
/*      */                       
/* 3474 */                       this._005fjspx_005ftagPool_005flogic_005fnotPresent_0026_005fname.reuse(_jspx_th_logic_005fnotPresent_005f2);
/* 3475 */                       out.write("                      \n            \n          </td>\n          </tr>\n        </table>\n\t\t<table width=\"100%\" border=\"0\">\n\t\t  <tr>&nbsp;&nbsp;\n\t\t  </tr>\n\t\t  <tr>\n\t\t   <td height=\"26\" class=\"bodytext\">");
/* 3476 */                       out.print(FormatUtil.getString("am.webclient.managedserver.loadfactor.note", new String[] { com.adventnet.appmanager.util.OEMUtil.getOEMString("product.name") }));
/* 3477 */                       out.write("</td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t  \t  <td height=\"26\" class=\"bodytext\">&nbsp;&nbsp;<br>");
/* 3478 */                       out.print(FormatUtil.getString("am.webclient.managedserver.adminloadfactor"));
/* 3479 */                       out.write(" : <b>");
/* 3480 */                       out.print(com.adventnet.appmanager.util.LoadFactorCalculator.getLoadFactor());
/* 3481 */                       out.write("</b></td>\n\t\t\t</tr>\n\t\t</table>          \n  <p class=\"tooltip\">&nbsp;</p>\n</form>\n  ");
/*      */                     }
/*      */                     
/*      */ 
/* 3485 */                     out.write(10);
/* 3486 */                     out.write(10);
/*      */                   }
/* 3488 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3489 */         out = _jspx_out;
/* 3490 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3491 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3492 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3495 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3501 */     PageContext pageContext = _jspx_page_context;
/* 3502 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3504 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3505 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3506 */     _jspx_th_logic_005fpresent_005f0.setParent(null);
/*      */     
/* 3508 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3509 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3510 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3512 */         out.write("\n alertUser();\n return;\n ");
/* 3513 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3514 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3518 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3519 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3520 */       return true;
/*      */     }
/* 3522 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3523 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3528 */     PageContext pageContext = _jspx_page_context;
/* 3529 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3531 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3532 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 3533 */     _jspx_th_logic_005fpresent_005f1.setParent(null);
/*      */     
/* 3535 */     _jspx_th_logic_005fpresent_005f1.setRole("DEMO");
/* 3536 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 3537 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 3539 */         out.write("\n alertUser();\n return;\n ");
/* 3540 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 3541 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3545 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 3546 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3547 */       return true;
/*      */     }
/* 3549 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 3550 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f4(JspTag _jspx_th_c_005fotherwise_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3555 */     PageContext pageContext = _jspx_page_context;
/* 3556 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3558 */     PresentTag _jspx_th_logic_005fpresent_005f4 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3559 */     _jspx_th_logic_005fpresent_005f4.setPageContext(_jspx_page_context);
/* 3560 */     _jspx_th_logic_005fpresent_005f4.setParent((Tag)_jspx_th_c_005fotherwise_005f2);
/*      */     
/* 3562 */     _jspx_th_logic_005fpresent_005f4.setRole("ENTERPRISEADMIN");
/* 3563 */     int _jspx_eval_logic_005fpresent_005f4 = _jspx_th_logic_005fpresent_005f4.doStartTag();
/* 3564 */     if (_jspx_eval_logic_005fpresent_005f4 != 0) {
/*      */       for (;;) {
/* 3566 */         out.write("&nbsp;");
/* 3567 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f4.doAfterBody();
/* 3568 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3572 */     if (_jspx_th_logic_005fpresent_005f4.doEndTag() == 5) {
/* 3573 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3574 */       return true;
/*      */     }
/* 3576 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f4);
/* 3577 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f6(JspTag _jspx_th_logic_005fiterate_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3582 */     PageContext pageContext = _jspx_page_context;
/* 3583 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3585 */     PresentTag _jspx_th_logic_005fpresent_005f6 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3586 */     _jspx_th_logic_005fpresent_005f6.setPageContext(_jspx_page_context);
/* 3587 */     _jspx_th_logic_005fpresent_005f6.setParent((Tag)_jspx_th_logic_005fiterate_005f0);
/*      */     
/* 3589 */     _jspx_th_logic_005fpresent_005f6.setRole("ENTERPRISEADMIN");
/* 3590 */     int _jspx_eval_logic_005fpresent_005f6 = _jspx_th_logic_005fpresent_005f6.doStartTag();
/* 3591 */     if (_jspx_eval_logic_005fpresent_005f6 != 0) {
/*      */       for (;;) {
/* 3593 */         if (_jspx_meth_c_005fif_005f0(_jspx_th_logic_005fpresent_005f6, _jspx_page_context))
/* 3594 */           return true;
/* 3595 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f6.doAfterBody();
/* 3596 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3600 */     if (_jspx_th_logic_005fpresent_005f6.doEndTag() == 5) {
/* 3601 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3602 */       return true;
/*      */     }
/* 3604 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f6);
/* 3605 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_logic_005fpresent_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3610 */     PageContext pageContext = _jspx_page_context;
/* 3611 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3613 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3614 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3615 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_logic_005fpresent_005f6);
/*      */     
/* 3617 */     _jspx_th_c_005fif_005f0.setTest("${enableUpdateOperation}");
/* 3618 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3619 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3621 */         out.write("<img src=\"/images/icon_edit.gif\"  border=\"0\">");
/* 3622 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3623 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3627 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3628 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3629 */       return true;
/*      */     }
/* 3631 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3632 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\ManagedServers_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */