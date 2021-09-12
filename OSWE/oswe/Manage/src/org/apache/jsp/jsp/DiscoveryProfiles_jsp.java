/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.discovery.NetworkDiscoveryHandler;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import java.net.InetAddress;
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
/*      */ import javax.servlet.http.HttpServletResponse;
/*      */ import javax.servlet.http.HttpSession;
/*      */ import javax.servlet.jsp.JspFactory;
/*      */ import javax.servlet.jsp.JspWriter;
/*      */ import javax.servlet.jsp.PageContext;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.ForEachTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ 
/*      */ public final class DiscoveryProfiles_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  667 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new java.util.Date(Long.parseLong(val)));
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
/*  839 */       return new java.util.Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
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
/* 1380 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
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
/* 1425 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
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
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2203 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2207 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2208 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2209 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2210 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2211 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2212 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2213 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2214 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2219 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2223 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2224 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2225 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2226 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2232 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2233 */     this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
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
/* 2260 */       out.write("<!DOCTYPE html>\n");
/* 2261 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2262 */       out.write(10);
/*      */       
/* 2264 */       if (DBUtil.isDelegatedAdmin(request.getRemoteUser())) {
/* 2265 */         response.sendRedirect("/jsp/formpages/AccessRestricted.jsp");
/*      */       }
/*      */       
/* 2268 */       out.write("\n\n\n\n\n\n");
/*      */       
/* 2270 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2271 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2272 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2274 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2275 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2276 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2278 */           out.write(32);
/* 2279 */           if (_jspx_meth_tiles_005fput_005f0(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2281 */           out.write(10);
/* 2282 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2284 */           out.write(10);
/*      */           
/* 2286 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2287 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2288 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2290 */           _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */           
/* 2292 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2293 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2294 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2295 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2296 */               out = _jspx_page_context.pushBody();
/* 2297 */               _jspx_th_tiles_005fput_005f2.setBodyContent((javax.servlet.jsp.tagext.BodyContent)out);
/* 2298 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2301 */               out.write(10);
/* 2302 */               out.write(10);
/* 2303 */               out.write(10);
/* 2304 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2306 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2307 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2308 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2310 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2312 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2314 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2316 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2317 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2318 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2319 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2322 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2323 */               String available = null;
/* 2324 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2325 */               out.write(10);
/*      */               
/* 2327 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2328 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2329 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2331 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2333 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2335 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2337 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2338 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2339 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2340 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2343 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2344 */               String unavailable = null;
/* 2345 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2346 */               out.write(10);
/*      */               
/* 2348 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2349 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2350 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2352 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2354 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2356 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2358 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2359 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2360 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2361 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2364 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2365 */               String unmanaged = null;
/* 2366 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2367 */               out.write(10);
/*      */               
/* 2369 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2370 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2371 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2375 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2377 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2379 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2380 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2381 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2382 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2385 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2386 */               String scheduled = null;
/* 2387 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2388 */               out.write(10);
/*      */               
/* 2390 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2391 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2392 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2394 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2396 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2398 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2400 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2401 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2402 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2403 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2406 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2407 */               String critical = null;
/* 2408 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2409 */               out.write(10);
/*      */               
/* 2411 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2412 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2413 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2415 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2417 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2419 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2421 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2422 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2423 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2424 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2427 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2428 */               String clear = null;
/* 2429 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2430 */               out.write(10);
/*      */               
/* 2432 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2433 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2434 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2436 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2438 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2440 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2442 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2443 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2444 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2445 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2448 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2449 */               String warning = null;
/* 2450 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2451 */               out.write(10);
/* 2452 */               out.write(10);
/*      */               
/* 2454 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2455 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2457 */               out.write(10);
/* 2458 */               out.write(10);
/* 2459 */               out.write(10);
/* 2460 */               out.write("\n\n\n\n\n\n\n");
/* 2461 */               NetworkDiscoveryHandler addnetworkfordiscovery = null;
/* 2462 */               addnetworkfordiscovery = (NetworkDiscoveryHandler)_jspx_page_context.getAttribute("addnetworkfordiscovery", 1);
/* 2463 */               if (addnetworkfordiscovery == null) {
/* 2464 */                 addnetworkfordiscovery = new NetworkDiscoveryHandler();
/* 2465 */                 _jspx_page_context.setAttribute("addnetworkfordiscovery", addnetworkfordiscovery, 1);
/*      */               }
/* 2467 */               out.write("\n\n\n<script>\n/*changes for showing Discovery cancel msg:Start */\n$(document).ready(function()\n\t\t{\n\t        var discoveryStopped=");
/* 2468 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2470 */               out.write(";\n\t        if(discoveryStopped===true)\n\t        {\n\t        \t// alert(discoveryStopped);\n\t        \t var discName=\"");
/* 2471 */               if (_jspx_meth_c_005fout_005f1(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2473 */               out.write("\";\n\t        \t var text=\"");
/* 2474 */               out.print(FormatUtil.getString("am.discovery.process.stop.start"));
/* 2475 */               out.write("\"+' '+discName+' '+\"");
/* 2476 */               out.print(FormatUtil.getString("am.discovery.process.stoppedend"));
/* 2477 */               out.write("\";\n\t              ");
/*      */               
/* 2479 */               IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2480 */               _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2481 */               _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2483 */               _jspx_th_c_005fif_005f0.setTest("${isAdminServer== 'true'}");
/* 2484 */               int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2485 */               if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                 for (;;) {
/* 2487 */                   out.write("\n\t                    var text=\"");
/* 2488 */                   out.print(FormatUtil.getString("am.discovery.process.stop.start"));
/* 2489 */                   out.write("\"+' '+discName+' '+\"");
/* 2490 */                   out.print(FormatUtil.getString("am.discovery.process.stopped.managedserver"));
/* 2491 */                   out.write("\";\n    \t          ");
/* 2492 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2493 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2497 */               if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2498 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */               }
/*      */               
/* 2501 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2502 */               out.write("\n    \t  \n    \t          $(\"#operationCompletionStatusId\").show();\n    \t          $(\"#operationCompletionStatusId span\").html(text);\n    \t          $( \"#operationCompletionStatusId \" ).fadeOut(5000);\n\t        }        \t\n\t        \n\t        jQuery('.rediscoverForm :radio').click(function(){ //No I18N\n\t\t\t\t\n\t\t\t\tjQuery('.schedule-filters > div').hide(); //No I18N\n\t\t\t\t\n\t\t\t\tvar clickId = jQuery(this).attr('id'); //No I18N\n\t\t\n\t\t\t\tif(clickId == 'weekly') {\n\t\t\t\t\tjQuery('.'+clickId).show();\n\t\t\t\t\tjQuery('.daily').show();  //No I18N\n\t\t\t\t\n\t\t\t\t}\n\t\t\t\telse if(clickId == 'monthly') {\n\t\t\t\t\tjQuery('.'+clickId).show();\n\t\t\t\t\tjQuery('.daily').show();  //No I18N\n\t\t\t\t\n\t\t\t\t}\n\t\t\t\telse if (clickId == 'daily') {\n\t\t\t\t\tjQuery('.'+clickId).show();\n\t\t\t\t}\n\t\t\t\n\t\t\t});\n\t        setHours();\n\t        setMins();\n\t        setDate();\n\t        \n\t\t\t});\n/*changes for showing Discovery cancel msg:End */\n \n function setHours()\n {\n\tfor(var i=0;i<24;i++)\n\t{\n\t\t\t $('select[name=scheduleHour]').append('<option>'+getNum(i)+'</option>');\n\t}\n }\n \nfunction setMins()\n");
/* 2503 */               out.write(" {\n\t for(var m=0;m<60;m+=5)\n\t\t{\n\t\t $('select[name=scheduleMin]').append('<option>'+getNum(m)+'</option>');\n\t\t}\n}\n function setDate()\n {\n\tfor(var d=1;d<32;d++)\n\t {\n\t\t $('select[name=scheduleDate]').append('<option>'+getNum(d)+'</option>');\n\t}\n }\n function getNum(number) {\n\t   return (number < 10 ? '0' : '') + number;\n\t}\n\nfunction editSelection()\n{\n\talert('Functionality under construction!!');\n\treturn;\n}\n\n\nfunction saveScheduleDetails()\n{\n\tvar dataString;\n\t//alert(\"save clicked..\");\n\t//alert(discId);\n\tvar clickId=$(\".radio input[type='radio']:checked\").val()\n\t//var clickId = jQuery('.rediscoverForm :radio :checked' ).attr('id');\n\t var hour=$('#scheduleHour :selected').text();\n\t var min=$('#scheduleMin :selected').text();\n\t dataString=\"method=saveScheduleDiscoveryDetails&hour=\"+hour+\"&min=\"+min+\"&scheduleType=\"+clickId+\"&discoveryID=\"+discId;//No I18N\n\t if(clickId=='weekly')\n\t\t{\n\t\t var day=$('#scheduleDay :selected').val();\t\n\t\t// alert(day);\n\t\t dataString=dataString+\"&day=\"+day;//No I18N\n\t\t}\n\t else if(clickId=='monthly')\n");
/* 2504 */               out.write("\t{\n\t\t var date=$('#scheduleDate :selected').text();\t\n\t\t dataString=dataString+\"&date=\"+date; //No I18N\n\t}\n\t\n\tsendAjaxReq(dataString);\n\t\n}\n\nfunction sendAjaxReq(dataString)\n{\n\t//alert(dataString);\n    $.ajax(\n    {\n                type:\"POST\", //No I18N\n                url:\"/newDiscoveryAction.do\", //No I18N\n                data:dataString,\n                success: function(response)\n                {\n                   // var discoveryID = response.trim();\n                   var result=response.trim();\n                 //alert(result);\n                    //alert('success');\n                   $(\"#dialogPort\").dialog('close');  //No I18N\n                     if(result == '-1')\n                    {\n                    \t  var masDownMSg=\"");
/* 2505 */               out.print(FormatUtil.getString("am.discovery.masdown.alert.msg"));
/* 2506 */               out.write(" \";\n                          //alert(masDownMSg);\n                          result=masDownMSg;\n                        //window.location.href=\"/newDiscoveryAction.do?method=getDiscoveryDetails\";//No I18N\n                    }\n                    \n                  \n                   \n                   ");
/*      */               
/* 2508 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2509 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 2510 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/* 2511 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 2512 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 2514 */                   out.write(" \n                   ");
/* 2515 */                   if (_jspx_meth_c_005fwhen_005f0(_jspx_th_c_005fchoose_005f0, _jspx_page_context))
/*      */                     return;
/* 2517 */                   out.write("\n                   ");
/*      */                   
/* 2519 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2520 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 2521 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 2522 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 2523 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 2525 */                       out.write("\n                     if(result=='false')\n                \t   {\n                \t   alert('");
/* 2526 */                       out.print(FormatUtil.getString("am.webclient.discovery.scheduled.failed"));
/* 2527 */                       out.write("');\n                \t   }\n                   else{\n                   window.location.href='newDiscoveryAction.do?method=getDiscoveryDetails';\n                   }\n                   ");
/* 2528 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 2529 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2533 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 2534 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 2537 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 2538 */                   out.write("\n                 ");
/* 2539 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 2540 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2544 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 2545 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 2548 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 2549 */               out.write("\n                }\n    });                   \n}\n\nfunction fnSelectAll(e)\n{\n\tToggleAll(e,document.form2,\"checkbox\")\n}\nfunction deleteSelections()\n{\n\talert('Functionality under construction!!');\n\treturn;\n\tdocument.form2.action=\"\";\n\tdocument.form2.method=\"Post\"\n\tdocument.form2.submit();\n}\nfunction delete1(net)\n{\n\t ");
/* 2550 */               if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2552 */               out.write("\t\n\tif(confirm('");
/* 2553 */               out.print(FormatUtil.getString("am.webclient.admin.networkdiscovery.alert1.text"));
/* 2554 */               out.write("'))\n\t{\n\tdocument.discoveryform.networks.value=net;\n\tdocument.discoveryform.action=\"/adminAction.do\";\n\tdocument.discoveryform.submit();\n\t}\n}\n\nfunction showHide(show)\n{\n\tif(show==\"netdiscovery\")\n\t{\n\t\tdocument.getElementById(\"netdiscovery-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"netdiscovery\").className = \"tbSelected_Middle\";\n\t\tdocument.getElementById(\"netdiscovery-right\").className = \"tbSelected_Right\";\n\n\t\tdocument.getElementById(\"servicediscovery-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"servicediscovery\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"servicediscovery-right\").className = \"tbUnSelected_Right\";\n\n        javascript:showDiv('networkdiscoverydiv');\n\t\tjavascript:hideDiv('servicediscoverydiv');\n\t\tjavascript:showDiv('networkDiscoveryHelp');\n\t\tjavascript:hideDiv('servicesHelp');\n\t\t\n\t}\n\telse if(show=='servicediscovery')\n\t{\n\n\n\t\tdocument.getElementById(\"servicediscovery-left\").className = \"tbSelected_Left\";\n\t\tdocument.getElementById(\"servicediscovery\").className = \"tbSelected_Middle\";\n");
/* 2555 */               out.write("\t\tdocument.getElementById(\"servicediscovery-right\").className = \"tbSelected_Right\";\n\n\t\tdocument.getElementById(\"netdiscovery-left\").className = \"tbUnSelected_Left\";\n\t\tdocument.getElementById(\"netdiscovery\").className = \"tbUnSelected_Middle\";\n\t\tdocument.getElementById(\"netdiscovery-right\").className = \"tbUnSelected_Right\";\n\n        \tjavascript:hideDiv('networkdiscoverydiv');\n\t\tjavascript:showDiv('servicediscoverydiv');\n\t\tjavascript:hideDiv('networkDiscoveryHelp');\n\t\tjavascript:showDiv('servicesHelp');\n\t}\n}\n\nfunction selectAll()\n{\n\tif(document.AMActionForm.selection.checked==true)\n\t{\n\t\tfor(var i=0;i<document.AMActionForm.discoverservice.length;i++){\n\t\t\t\tdocument.AMActionForm.discoverservice[i].checked=true;\n\t\t}\n\t}\n\telse\n\t{\n\t\tfor(var i=0;i<document.AMActionForm.discoverservice.length;i++){\n\t\t\t\tdocument.AMActionForm.discoverservice[i].checked=false;\n\t\t}\n\t}\n}\nfunction editDiscovery(discoveryID)\n{\n\t");
/*      */               
/* 2557 */               ChooseTag _jspx_th_c_005fchoose_005f1 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2558 */               _jspx_th_c_005fchoose_005f1.setPageContext(_jspx_page_context);
/* 2559 */               _jspx_th_c_005fchoose_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/* 2560 */               int _jspx_eval_c_005fchoose_005f1 = _jspx_th_c_005fchoose_005f1.doStartTag();
/* 2561 */               if (_jspx_eval_c_005fchoose_005f1 != 0) {
/*      */                 for (;;) {
/* 2563 */                   out.write(" \n    ");
/*      */                   
/* 2565 */                   WhenTag _jspx_th_c_005fwhen_005f1 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2566 */                   _jspx_th_c_005fwhen_005f1.setPageContext(_jspx_page_context);
/* 2567 */                   _jspx_th_c_005fwhen_005f1.setParent(_jspx_th_c_005fchoose_005f1);
/*      */                   
/* 2569 */                   _jspx_th_c_005fwhen_005f1.setTest("${isAdminServer== 'true'}");
/* 2570 */                   int _jspx_eval_c_005fwhen_005f1 = _jspx_th_c_005fwhen_005f1.doStartTag();
/* 2571 */                   if (_jspx_eval_c_005fwhen_005f1 != 0) {
/*      */                     for (;;) {
/* 2573 */                       out.write("\n    alert('");
/* 2574 */                       out.print(FormatUtil.getString("am.webclient.discovery.masRestriction"));
/* 2575 */                       out.write("');\n    ");
/* 2576 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f1.doAfterBody();
/* 2577 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2581 */                   if (_jspx_th_c_005fwhen_005f1.doEndTag() == 5) {
/* 2582 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1); return;
/*      */                   }
/*      */                   
/* 2585 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f1);
/* 2586 */                   out.write("\n    ");
/* 2587 */                   if (_jspx_meth_c_005fotherwise_005f1(_jspx_th_c_005fchoose_005f1, _jspx_page_context))
/*      */                     return;
/* 2589 */                   out.write("\n   ");
/* 2590 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f1.doAfterBody();
/* 2591 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2595 */               if (_jspx_th_c_005fchoose_005f1.doEndTag() == 5) {
/* 2596 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1); return;
/*      */               }
/*      */               
/* 2599 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f1);
/* 2600 */               out.write("\n\t//alert(\"edit ddiscovery\");\n\t//window.location.href='/newDiscoveryAction.do?method=showEditDiscoveryPage&discoveryID='+discoveryID;\n}\nfunction editAdminCreatedDiscovery(discoveryID)\n{\n\t//alert(\"admin creaeted\");\n\t");
/*      */               
/* 2602 */               ChooseTag _jspx_th_c_005fchoose_005f2 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2603 */               _jspx_th_c_005fchoose_005f2.setPageContext(_jspx_page_context);
/* 2604 */               _jspx_th_c_005fchoose_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/* 2605 */               int _jspx_eval_c_005fchoose_005f2 = _jspx_th_c_005fchoose_005f2.doStartTag();
/* 2606 */               if (_jspx_eval_c_005fchoose_005f2 != 0) {
/*      */                 for (;;) {
/* 2608 */                   out.write(" \n    ");
/* 2609 */                   if (_jspx_meth_c_005fwhen_005f2(_jspx_th_c_005fchoose_005f2, _jspx_page_context))
/*      */                     return;
/* 2611 */                   out.write("\n    ");
/*      */                   
/* 2613 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f2 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 2614 */                   _jspx_th_c_005fotherwise_005f2.setPageContext(_jspx_page_context);
/* 2615 */                   _jspx_th_c_005fotherwise_005f2.setParent(_jspx_th_c_005fchoose_005f2);
/* 2616 */                   int _jspx_eval_c_005fotherwise_005f2 = _jspx_th_c_005fotherwise_005f2.doStartTag();
/* 2617 */                   if (_jspx_eval_c_005fotherwise_005f2 != 0) {
/*      */                     for (;;) {
/* 2619 */                       out.write("\n    alert('");
/* 2620 */                       out.print(FormatUtil.getString("am.webclient.discovery.adminRestriction"));
/* 2621 */                       out.write("');\n    ");
/* 2622 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f2.doAfterBody();
/* 2623 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2627 */                   if (_jspx_th_c_005fotherwise_005f2.doEndTag() == 5) {
/* 2628 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2); return;
/*      */                   }
/*      */                   
/* 2631 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f2);
/* 2632 */                   out.write(10);
/* 2633 */                   out.write(32);
/* 2634 */                   out.write(32);
/* 2635 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f2.doAfterBody();
/* 2636 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2640 */               if (_jspx_th_c_005fchoose_005f2.doEndTag() == 5) {
/* 2641 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2); return;
/*      */               }
/*      */               
/* 2644 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f2);
/* 2645 */               out.write("\n\n}\nfunction deleteDiscovery(discoveryID)\n{    \n    //var confirmDelete = confirm('Do you want to delete this credential?');\n    var confirmDelete = confirm('");
/* 2646 */               out.print(FormatUtil.getString("am.webclient.discovery.delete"));
/* 2647 */               out.write("');\n    if(confirmDelete)\n    {\n\n       \t var dataString = \"&method=deleteDiscoveryDetails&discoveryID=\"+discoveryID; //No I18N\n    \t $.ajax({\n \t         type: \"POST\", //No I18N\n \t         url: \"/newDiscoveryAction.do\",//No I18N\n \t         data: dataString,            // Query String parameters\n \t         success: function(response)\n \t         {\n                var isDeleted=response.trim();\n                if(isDeleted=='true')\n                \t{\n                \t  var text=\"");
/* 2648 */               out.print(FormatUtil.getString("am.discovery.details.deleted"));
/* 2649 */               out.write("\";\n              \t       ");
/*      */               
/* 2651 */               IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2652 */               _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2653 */               _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2655 */               _jspx_th_c_005fif_005f1.setTest("${isAdminServer== 'true'}");
/* 2656 */               int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2657 */               if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                 for (;;) {
/* 2659 */                   out.write("\n         \t                  text=\"");
/* 2660 */                   out.print(FormatUtil.getString("am.discovery.details.deletion.managedserver"));
/* 2661 */                   out.write("\";\n         \t          ");
/* 2662 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2663 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 2667 */               if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2668 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */               }
/*      */               
/* 2671 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2672 */               out.write("\n         \t          $(\"#operationCompletionStatusId\").show();\n         \t          $(\"#operationCompletionStatusId span\").html(text);\n         \t          $(\"#\"+discoveryID).remove();\n                \t}\n                else\n                \t{\n                    $(\"#operationCompletionStatusId\").removeClass().addClass(\"msgError msgBox\");//No I18N\n                    $(\"#operationCompletionStatusId\").show();\n          \t        $(\"#operationCompletionStatusId span\").html(\"");
/* 2673 */               out.print(FormatUtil.getString("am.discovery.details.deletion.error"));
/* 2674 */               out.write("\");\n          \t         updateDiscoveryInfo(discoveryID);\n                \t}\n                $( \"#operationCompletionStatusId \" ).fadeOut(5000);\n \t         }\n                \n \t });\n       \n}\n\n}\n\n\nfunction reDiscovery(id)\n{\n\t  \tvar redirect =\"/newDiscoveryAction.do?method=rediscovery\";//No I18N\n        $.redirectPost(redirect, {\"method\":\"rediscovery\",\"discoveryID\":id});//No I18N\n}\n\n$.extend({\n    redirectPost: function(location, args)\n    {\n        var form = '';\n     //   alert(location);\n      //  alert(args);\n        $.each( args, function( key, value ) {\n            value = value.split('\"').join('\\\"')\n            form += '<input type=\"hidden\" name=\"'+key+'\" value=\"'+value+'\">';\n        });\n        $('<form action=\"' + location + '\" method=\"POST\">' + form + '</form>').appendTo($(document.body)).submit();\n    }\n});\n\n\n\nfunction updateDiscoveryInfo(discoveryID)\n{\n\tvar dataString = \"&method=updateDiscoveryInfo&discoveryID=\"+discoveryID; //No I18N\n\t $.ajax({\n\t         type: \"POST\", //No I18N\n\t         url: \"/newDiscoveryAction.do\",//No I18N\n");
/* 2675 */               out.write("\t         data: dataString,\n\t         success: function(response)\n \t         {\n\t        \t// alert(\"updated\");\n \t         }\n\t });\n\t\n}\n\n//enable/disable schedule discovery::START\nvar discId;\n$(function() {\n\t\n  //$(\"#scheduleDiscovery\").click(function (e) {\n\t $('[name=\"scheduleDiscovery\"]').click(function(e) {\n   // alert(\"sdkfj\");\n\t\t discId=$(this).attr(\"id\");  //No I18N\n\t\t getScheduledDetails(discId);\n  //  alert(discId);\n    $(\"#dialogPort\").dialog({\n                                modal   : true, \n                                height  : 270, \n                                width   : 500,\n              show    : {effect: 'drop', direction: \"left\", duration: 500},       //No I18N\n              hide    : {effect: 'drop', direction: \"right\", duration: 500},       //No I18N\n                                dialogClass : 'dialogCustom dialogPort'               //No I18N\n                            });\n          e.preventDefault();\n    });\n\n    $(\"#closePortDialog\").click(function(e) {\n    \t//alert(\"discId\"+discId);\n");
/* 2676 */               out.write("                          $(\"#dialogPort\").dialog('close');                       //No I18N\n        e.preventDefault();\n    });\n   \n                      });\n  \n  function getScheduledDetails(discId)\n  {\n  // alert(\"getAPI\"+discId);\n   var dataString = \"&method=getScheduleDetails&discoveryID=\"+discId; //No I18N\n\t $.ajax({\n\t         type: \"POST\", //No I18N\n\t         url: \"/newDiscoveryAction.do\",//No I18N\n\t         data: dataString,\n\t         success: function(response)\n \t         {\n\t        \t for(var key in response)\n                 {\n\t        \t\t//alert(\"key\"+key);\n\t        \t     \tvar scheduleType = response[key].scheduleType;\n                     \t//alert(scheduleType);\n                        if(scheduleType=='disabled')\n                        \t{\n                           \t  $('#disabled').attr('checked', true);//No I18N\n                        \t  $('.daily').hide();  \n                        \t  $('.weekly').hide();\n                        \t  $('.monthly').hide();\n                        \t}\n                        else\n");
/* 2677 */               out.write("                        \t{\n                            \t$('.daily').show();  \n                        \t    var hours=response[key].hours;\n                        \t    var min=response[key].min;\n                        \t  \n                        \t    if(scheduleType=='weekly')\n                        \t\t{\n                        \t \t  $('#weekly').attr('checked',true);//No I18N\n                        \t\t  var day=response[key].day;\n                        \t\t  $(\"#scheduleDay\").val(day);\n                        \t\t  $('.weekly').show();  \n                        \t\t  $('.monthly').hide();  \n                        \t\t\n                        \t\t}\n                               else if(scheduleType=='monthly')\n                        \t    {\n                        \t\t  $('#monthly').attr('checked',true);//No I18N\n                        \t\t  var date=response[key].date;\n                         \t  \t  $(\"#scheduleDate\").val(date);\n                        \t\t  $('.monthly').show();  \n                        \t\t  $('.weekly').hide();\n");
/* 2678 */               out.write("                        \t\t}\n                        \t  else\n                        \t\t{\n                        \t\t  $('#daily').attr('checked',true);//No I18N\n                        \t\t  $('.weekly').hide();\n                            \t  $('.monthly').hide();\n                        \t\t}\n                        \t  $(\"#scheduleHour\").val(hours);\n                        \t  $(\"#scheduleMin\").val(min);\n                        \t}\n\t                 }\n                        \t\n                 \n \t         }\n\t });\n   \n }\n  \n \n//enable/disable schedule discovery::END\n\n</script>\n\n<table width=\"100%\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"itadmin-hide\">\n\t<tr>\n\t ");
/*      */               
/* 2680 */               if (EnterpriseUtil.isAdminServer())
/*      */               {
/*      */ 
/* 2683 */                 out.write("\n\t  <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2684 */                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getEnterpriseAdminPage(request));
/* 2685 */                 out.write(" &gt; <span class=\"bcactive\">");
/* 2686 */                 out.print(FormatUtil.getString("am.webclient.admin.networkdiscovery.link"));
/* 2687 */                 out.write("</span></td>\n\t ");
/*      */               }
/*      */               else
/*      */               {
/* 2691 */                 out.write("\n\t\t <td class=\"bcsign breadcrumb_btm_space\"  height=\"22\" valign=\"top\"> ");
/* 2692 */                 out.print(com.adventnet.appmanager.util.BreadcrumbUtil.getAdminPage(request));
/* 2693 */                 out.write(" &gt; <span class=\"bcactive\">");
/* 2694 */                 out.print(FormatUtil.getString("am.webclient.admin.networkdiscovery.link"));
/* 2695 */                 out.write("</span></td>\n\t ");
/*      */               }
/* 2697 */               out.write("\n\n\t</tr>\n</table>\n\n\n");
/*      */               
/* 2699 */               org.apache.struts.util.TokenProcessor token = org.apache.struts.util.TokenProcessor.getInstance();
/* 2700 */               token.saveToken(request);
/* 2701 */               String services = request.getParameter("showlink");
/* 2702 */               String appTemplates = "false";
/* 2703 */               if ((request.getParameter("isAppTemplates") != null) && (request.getParameter("isAppTemplates").equals("true")))
/*      */               {
/* 2705 */                 appTemplates = request.getParameter("isAppTemplates");
/*      */               }
/*      */               
/* 2708 */               String edition = com.adventnet.appmanager.util.Constants.getCategorytype();
/*      */               
/* 2710 */               out.write("\n\n  <!--This code was introduced because html tags were not properly closed.UI collapse in IT360 fixed-->\n\n </td>\n </tr>\n </table>\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t<tr>\n\t<td valign=\"top\" height=\"100%\" class=\"basicLayoutNoLeftWidth\">\n\t \t  <img src=\"/images/spacer.gif\">\n\t \t  </td>\n\t\t<td width=\"70%\" valign=\"top\">\n     <!--% if(services != null && services.equals(\"network\")) { %-->\n      \n<div id=\"networkdiscoverydiv\" style=");
/* 2711 */               out.print(services.equals("network") ? "display:block" : "display:none");
/* 2712 */               out.write(" >\n<div class=\"msgSuccess msgBox\" id=\"operationCompletionStatusId\" style=\"display:none\"><i class=\"iconSprite iconInfo\"></i><span></span></div>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtbdarkborder\">\n  <tr>\n    <td width=\"72%\" height=\"31\" class=\"tableheading\" >");
/* 2713 */               out.print(FormatUtil.getString("am.webclient.networkdiscovery.networksdiscovery.text"));
/* 2714 */               out.write("</td>\n  </tr>\n</table>\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n<form name=\"discoveryform\" method=\"post\">\n<input type=\"hidden\" name=\"method\" value=\"deleteNetwork\">\n  <tr>\n    <td height=\"28\">\n     <!-- removed -->\n    \n\t\t<table class=\"networkDiscovery\" border=\"0\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n\t\t<tr>\n\t\t<td class=\"columnheading\" valign=\"center\" height=\"28\">");
/* 2715 */               out.print(FormatUtil.getString("am.webclient.discovery.name"));
/* 2716 */               out.write("</td>\n<td class=\"columnheading\" valign=\"center\" height=\"28\">");
/* 2717 */               out.print(FormatUtil.getString("am.webclient.discovery.iprange"));
/* 2718 */               out.write("</td>\n\t\t<td class=\"columnheading\" valign=\"center\" align=\"center\" height=\"28\">");
/* 2719 */               out.print(FormatUtil.getString("am.webclient.discovery.starttime"));
/* 2720 */               out.write("</td>\n<td class=\"columnheading\" valign=\"center\" align=\"center\" height=\"28\">");
/* 2721 */               out.print(FormatUtil.getString("am.webclient.discovery.finishedtime"));
/* 2722 */               out.write("</td>\n                <td class=\"columnheading\" valign=\"center\" align=\"center\" height=\"28\">");
/* 2723 */               out.print(FormatUtil.getString("am.webclient.discovery.scheduletime"));
/* 2724 */               out.write("</td>\n              <td class=\"columnheading\" valign=\"center\" align=\"center\" height=\"28\">");
/* 2725 */               out.print(FormatUtil.getString("am.webclient.discovery.scansummary"));
/* 2726 */               out.write("</td>\n                ");
/* 2727 */               if (!EnterpriseUtil.isProfEdition()) {
/* 2728 */                 out.write("\n          \t  \n                <td class=\"columnheading\" valign=\"center\" align=\"center\" height=\"28\">");
/* 2729 */                 out.print(FormatUtil.getString("am.webclient.discovery.triggeredfrom"));
/* 2730 */                 out.write("</td> \n   ");
/*      */               }
/* 2732 */               out.write("\n\t\t</tr>\n               \n                ");
/*      */               
/* 2734 */               ForEachTag _jspx_th_c_005fforEach_005f0 = (ForEachTag)this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(ForEachTag.class);
/* 2735 */               _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
/* 2736 */               _jspx_th_c_005fforEach_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2738 */               _jspx_th_c_005fforEach_005f0.setVar("prop");
/*      */               
/* 2740 */               _jspx_th_c_005fforEach_005f0.setItems("${discoveryTableList}");
/*      */               
/* 2742 */               _jspx_th_c_005fforEach_005f0.setVarStatus("status");
/* 2743 */               int[] _jspx_push_body_count_c_005fforEach_005f0 = { 0 };
/*      */               try {
/* 2745 */                 int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
/* 2746 */                 if (_jspx_eval_c_005fforEach_005f0 != 0) {
/*      */                   for (;;) {
/* 2748 */                     out.write("\n                    <tr id=\"");
/* 2749 */                     if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2751 */                     out.write("\">\n\t\t<td valign=\"center\" height=\"28\" class=\"yellowgrayborder\"> \n\t\t<div class=\"actionBox\">\n\t\t\t\t<a class=\"iconDelete\" onclick=\"");
/*      */                     
/* 2753 */                     ChooseTag _jspx_th_c_005fchoose_005f3 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2754 */                     _jspx_th_c_005fchoose_005f3.setPageContext(_jspx_page_context);
/* 2755 */                     _jspx_th_c_005fchoose_005f3.setParent(_jspx_th_c_005fforEach_005f0);
/* 2756 */                     int _jspx_eval_c_005fchoose_005f3 = _jspx_th_c_005fchoose_005f3.doStartTag();
/* 2757 */                     if (_jspx_eval_c_005fchoose_005f3 != 0) {
/*      */                       for (;;)
/*      */                       {
/* 2760 */                         WhenTag _jspx_th_c_005fwhen_005f3 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2761 */                         _jspx_th_c_005fwhen_005f3.setPageContext(_jspx_page_context);
/* 2762 */                         _jspx_th_c_005fwhen_005f3.setParent(_jspx_th_c_005fchoose_005f3);
/*      */                         
/* 2764 */                         _jspx_th_c_005fwhen_005f3.setTest("${!isAdminServer && prop.TRIGGEREDFROM=='Admin Server'}");
/* 2765 */                         int _jspx_eval_c_005fwhen_005f3 = _jspx_th_c_005fwhen_005f3.doStartTag();
/* 2766 */                         if (_jspx_eval_c_005fwhen_005f3 != 0) {
/*      */                           for (;;) {
/* 2768 */                             out.write(" alert('");
/* 2769 */                             out.print(FormatUtil.getString("am.webclient.discovery.adminRestriction"));
/* 2770 */                             out.write(39);
/* 2771 */                             out.write(41);
/* 2772 */                             out.write(32);
/* 2773 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f3.doAfterBody();
/* 2774 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2778 */                         if (_jspx_th_c_005fwhen_005f3.doEndTag() == 5) {
/* 2779 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2782 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f3);
/* 2783 */                         if (_jspx_meth_c_005fotherwise_005f3(_jspx_th_c_005fchoose_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2785 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f3.doAfterBody();
/* 2786 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2790 */                     if (_jspx_th_c_005fchoose_005f3.doEndTag() == 5) {
/* 2791 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2794 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f3);
/* 2795 */                     out.write("\" title=\"");
/* 2796 */                     out.print(FormatUtil.getString("am.webclient.common.delete"));
/* 2797 */                     out.write("\"></a>  ");
/* 2798 */                     out.write("\n\t\t <a class=\"iconEdit\" id=\"editIcon\" onclick=\"");
/* 2799 */                     if (_jspx_meth_c_005fchoose_005f4(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2801 */                     out.write("\" title=\"");
/* 2802 */                     out.print(FormatUtil.getString("am.webclient.networkdiscovery.edit.txt"));
/* 2803 */                     out.write("\"></a> ");
/* 2804 */                     out.write(10);
/* 2805 */                     out.write(9);
/* 2806 */                     out.write(9);
/* 2807 */                     out.write("\n\t\t\n\t    <a class=\"iconView\" href=\"/newDiscoveryAction.do?method=discoveredDevicesTableView&fromView=true&discoveryID=");
/* 2808 */                     if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2810 */                     out.write("\" title=\"");
/* 2811 */                     out.print(FormatUtil.getString("am.discovery.view.option"));
/* 2812 */                     out.write("\"></a> ");
/* 2813 */                     out.write("\n\n\t\t</div>  \n\t\t");
/* 2814 */                     if (_jspx_meth_c_005fout_005f7(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2816 */                     out.write(" \n\t\t</td>\n\t\t<td valign=\"center\" height=\"28\" class=\"yellowgrayborder\">");
/* 2817 */                     if (_jspx_meth_c_005fout_005f8(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2819 */                     out.write("</td>\n\t\t<td valign=\"center\" align=\"center\" height=\"28\" class=\"yellowgrayborder\">");
/* 2820 */                     if (_jspx_meth_c_005fout_005f9(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2822 */                     out.write("</td>\n        <td valign=\"center\" align=\"center\" height=\"28\" class=\"yellowgrayborder\">");
/* 2823 */                     if (_jspx_meth_c_005fout_005f10(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2825 */                     out.write("</td>\n       ");
/* 2826 */                     out.write("\n        <td valign=\"center\" align=\"center\" height=\"28\" class=\"yellowgrayborder\">  \n      ");
/* 2827 */                     out.write("\n     \n        \t");
/*      */                     
/* 2829 */                     ChooseTag _jspx_th_c_005fchoose_005f5 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 2830 */                     _jspx_th_c_005fchoose_005f5.setPageContext(_jspx_page_context);
/* 2831 */                     _jspx_th_c_005fchoose_005f5.setParent(_jspx_th_c_005fforEach_005f0);
/* 2832 */                     int _jspx_eval_c_005fchoose_005f5 = _jspx_th_c_005fchoose_005f5.doStartTag();
/* 2833 */                     if (_jspx_eval_c_005fchoose_005f5 != 0) {
/*      */                       for (;;) {
/* 2835 */                         out.write(10);
/* 2836 */                         out.write(9);
/* 2837 */                         out.write(9);
/*      */                         
/* 2839 */                         WhenTag _jspx_th_c_005fwhen_005f5 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2840 */                         _jspx_th_c_005fwhen_005f5.setPageContext(_jspx_page_context);
/* 2841 */                         _jspx_th_c_005fwhen_005f5.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                         
/* 2843 */                         _jspx_th_c_005fwhen_005f5.setTest("${!isAdminServer && prop.TRIGGEREDFROM=='Admin Server'}");
/* 2844 */                         int _jspx_eval_c_005fwhen_005f5 = _jspx_th_c_005fwhen_005f5.doStartTag();
/* 2845 */                         if (_jspx_eval_c_005fwhen_005f5 != 0) {
/*      */                           for (;;) {
/* 2847 */                             out.write("\n\t\t <a onclick=\"alert('");
/* 2848 */                             out.print(FormatUtil.getString("am.webclient.discovery.schedule.adminRestriction"));
/* 2849 */                             out.write("')\" href=\"#\" id=\"");
/* 2850 */                             if (_jspx_meth_c_005fout_005f11(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2945 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2852 */                             out.write(34);
/* 2853 */                             out.write(32);
/* 2854 */                             out.write(62);
/* 2855 */                             if (_jspx_meth_c_005fout_005f12(_jspx_th_c_005fwhen_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2945 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2857 */                             out.write("</a> ");
/* 2858 */                             out.write("\n\t\t ");
/* 2859 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f5.doAfterBody();
/* 2860 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2864 */                         if (_jspx_th_c_005fwhen_005f5.doEndTag() == 5) {
/* 2865 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2868 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f5);
/* 2869 */                         out.write("\n\t\t   ");
/*      */                         
/* 2871 */                         WhenTag _jspx_th_c_005fwhen_005f6 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 2872 */                         _jspx_th_c_005fwhen_005f6.setPageContext(_jspx_page_context);
/* 2873 */                         _jspx_th_c_005fwhen_005f6.setParent(_jspx_th_c_005fchoose_005f5);
/*      */                         
/* 2875 */                         _jspx_th_c_005fwhen_005f6.setTest("${isAdminServer && prop.TRIGGEREDFROM!='Admin Server'}");
/* 2876 */                         int _jspx_eval_c_005fwhen_005f6 = _jspx_th_c_005fwhen_005f6.doStartTag();
/* 2877 */                         if (_jspx_eval_c_005fwhen_005f6 != 0) {
/*      */                           for (;;) {
/* 2879 */                             out.write("\n         <a onclick=\"alert('");
/* 2880 */                             out.print(FormatUtil.getString("am.webclient.discovery.schedule.masRestriction"));
/* 2881 */                             out.write("')\" href=\"#\" id=\"");
/* 2882 */                             if (_jspx_meth_c_005fout_005f13(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2945 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2884 */                             out.write(34);
/* 2885 */                             out.write(32);
/* 2886 */                             out.write(62);
/* 2887 */                             if (_jspx_meth_c_005fout_005f14(_jspx_th_c_005fwhen_005f6, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
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
/* 2945 */                               _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                               this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                             }
/* 2889 */                             out.write("</a> ");
/* 2890 */                             out.write("\n         ");
/* 2891 */                             int evalDoAfterBody = _jspx_th_c_005fwhen_005f6.doAfterBody();
/* 2892 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2896 */                         if (_jspx_th_c_005fwhen_005f6.doEndTag() == 5) {
/* 2897 */                           this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2900 */                         this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f6);
/* 2901 */                         out.write("\n\t\t ");
/* 2902 */                         if (_jspx_meth_c_005fotherwise_005f5(_jspx_th_c_005fchoose_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                           _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                           this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                         }
/* 2904 */                         out.write("\n\t\t ");
/* 2905 */                         int evalDoAfterBody = _jspx_th_c_005fchoose_005f5.doAfterBody();
/* 2906 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 2910 */                     if (_jspx_th_c_005fchoose_005f5.doEndTag() == 5) {
/* 2911 */                       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/*      */                       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2914 */                     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f5);
/* 2915 */                     out.write("\n\t\t <div class=\"actionBox\"> <a onclick=\"javascript:reDiscovery('");
/* 2916 */                     if (_jspx_meth_c_005fout_005f17(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2918 */                     out.write("')\" class=\"iconDiscover\" title=\"");
/* 2919 */                     out.print(FormatUtil.getString("am.discovery.rediscover.option"));
/* 2920 */                     out.write("\"></a> </div>");
/* 2921 */                     out.write("\n        </td>\n        <td valign=\"center\" align=\"center\" height=\"28\" class=\"yellowgrayborder\">");
/* 2922 */                     if (_jspx_meth_c_005fout_005f18(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                       _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                       this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                     }
/* 2924 */                     out.write("</td>\n           ");
/* 2925 */                     if (!EnterpriseUtil.isProfEdition()) {
/* 2926 */                       out.write("\n        <td valign=\"center\" align=\"center\" height=\"28\" class=\"yellowgrayborder\">");
/* 2927 */                       if (_jspx_meth_c_005fout_005f19(_jspx_th_c_005fforEach_005f0, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/*      */                       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                         _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                         this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                       }
/* 2929 */                       out.write("</td>\n         ");
/*      */                     }
/* 2931 */                     out.write("       \n\t\t</tr>\n\t\t\t\t\n\t\t\t\t\n                ");
/* 2932 */                     int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
/* 2933 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2937 */                 if (_jspx_th_c_005fforEach_005f0.doEndTag() == 5)
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2945 */                   _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                   this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0); return;
/*      */                 }
/*      */               }
/*      */               catch (Throwable _jspx_exception)
/*      */               {
/*      */                 for (;;)
/*      */                 {
/* 2941 */                   int tmp4943_4942 = 0; int[] tmp4943_4940 = _jspx_push_body_count_c_005fforEach_005f0; int tmp4945_4944 = tmp4943_4940[tmp4943_4942];tmp4943_4940[tmp4943_4942] = (tmp4945_4944 - 1); if (tmp4945_4944 <= 0) break;
/* 2942 */                   out = _jspx_page_context.popBody(); }
/* 2943 */                 _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
/*      */               } finally {
/* 2945 */                 _jspx_th_c_005fforEach_005f0.doFinally();
/* 2946 */                 this._005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
/*      */               }
/* 2948 */               out.write("\n\t\t\t</table>\n\t\t\t<table  width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">\n\t\t\t<tr>\n\t\t\t<td width=\"72%\" height=\"22\">&nbsp;<a href=\"/newDiscoveryAction.do?method=showDiscoveryPage\" class=\"btnAddNew staticlinks\"><span class=\"iconAdd\">+</span> ");
/* 2949 */               out.print(FormatUtil.getString("am.webclient.user.addnew.text"));
/* 2950 */               out.write("</a></td>\n\t\t\t</tr>\n\t\t\t</table>\n    </td>\n  </tr>\n  </form>\n</table>\n\n</div>\n\n<div id=\"dialogPort\" class=\"dialogPort dialogCustom\">\n    <h2 class=\"dialogHeading\">");
/* 2951 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.head"));
/* 2952 */               out.write("</h2> ");
/* 2953 */               out.write("\n    <a href=\"#\" id=\"closePortDialog\" class=\"btnClose\">X</a> ");
/* 2954 */               out.write("\n \n \n <div class=\"popupCon rediscoverForm\">\n <div class=\"radio\">\n  <label>\n    <input type=\"radio\" name=\"rediscover\" id=\"disabled\" value=\"disabled\" checked=\"checked\">\n    ");
/* 2955 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.disabled"));
/* 2956 */               out.write("</label>\n  <label>\n    <input type=\"radio\" name=\"rediscover\" id=\"daily\" value=\"daily\" >\n    ");
/* 2957 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.daily"));
/* 2958 */               out.write("</label>");
/* 2959 */               out.write("\n  <label>\n    <input type=\"radio\" name=\"rediscover\" id=\"weekly\" value=\"weekly\">\n    ");
/* 2960 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.weekly"));
/* 2961 */               out.write("</label>");
/* 2962 */               out.write("\n  <label>\n    <input type=\"radio\" name=\"rediscover\" id=\"monthly\" value=\"monthly\">\n    ");
/* 2963 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.monthly"));
/* 2964 */               out.write("</label>");
/* 2965 */               out.write("\n   </div>\n\n<div class=\"schedule-filters\">\n  <div class=\"form-group daily\">\n    <label class=\"control-label\"> ");
/* 2966 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.scheduletime"));
/* 2967 */               out.write(":</label>");
/* 2968 */               out.write("\n      <div class=\"inputBlock\">\n      <label>\n      \n        <select name=\"scheduleHour\" id=\"scheduleHour\" class=\" chzn-select\">\n       \n      </select>\n      ");
/* 2969 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.hours"));
/* 2970 */               out.write("</label>");
/* 2971 */               out.write("\n    <label>\n      <select name=\"scheduleMin\" id=\"scheduleMin\" class=\" chzn-select\">\n      \n        \n      </select>\n       ");
/* 2972 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.mins"));
/* 2973 */               out.write("</label>");
/* 2974 */               out.write("\n      </div>\n  </div>\n  \n  \n  <div class=\"weekly\">\n    <div class=\"form-group pL20\">\n      <label class=\"control-label\"> ");
/* 2975 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.schedule.day"));
/* 2976 */               out.write(":</label>");
/* 2977 */               out.write("\n       <div class=\"inputBlock\"><select name=\"scheduleDay\" id=\"scheduleDay\" class=\" chzn-select\">\n         <option value=\"2\">");
/* 2978 */               out.print(FormatUtil.getString("Monday"));
/* 2979 */               out.write("</option>\n         <option value=\"3\">");
/* 2980 */               out.print(FormatUtil.getString("Tuesday"));
/* 2981 */               out.write("</option>\n         <option value=\"4\">");
/* 2982 */               out.print(FormatUtil.getString("Wednesday"));
/* 2983 */               out.write("</option>\n         <option value=\"5\">");
/* 2984 */               out.print(FormatUtil.getString("Thursday"));
/* 2985 */               out.write("</option>\n         <option value=\"6\">");
/* 2986 */               out.print(FormatUtil.getString("Friday"));
/* 2987 */               out.write("</option>\n         <option value=\"7\">");
/* 2988 */               out.print(FormatUtil.getString("Saturday"));
/* 2989 */               out.write("</option>\n          <option value=\"1\">");
/* 2990 */               out.print(FormatUtil.getString("Sunday"));
/* 2991 */               out.write("</option>\n        </select>\n      </div>\n    </div>\n    \n  </div>\n  <div class=\"monthly\">\n    <div class=\"form-group\">\n      <label class=\"control-label\">");
/* 2992 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.schedule.date"));
/* 2993 */               out.write(" :</label>");
/* 2994 */               out.write("\n       <div class=\"inputBlock\">\n        <select name=\"scheduleDate\" id=\"scheduleDate\" class=\" chzn-select\">\n   \n        </select>\n      </div>\n    </div>\n    \n  </div>\n</div>\n  <div class=\"form-group\">\n    <div class=\"inputBlock\">\n      <input name=\"save\" type=\"button\" class=\"btn\" value=");
/* 2995 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.save"));
/* 2996 */               out.write(" onclick=\"javascript:saveScheduleDetails()\">\n      <input name=\"cancel\" class=\"btn\" value=");
/* 2997 */               out.print(FormatUtil.getString("am.webclient.schedule.discovery.cancel"));
/* 2998 */               out.write(" type=\"button\" onclick=\"javascript:window.location.href='newDiscoveryAction.do?method=getDiscoveryDetails'\">\n    </div>\n  </div>\n</div>\n\n</div>\n\n\n\n");
/*      */               
/* 3000 */               IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3001 */               _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3002 */               _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 3004 */               _jspx_th_c_005fif_005f2.setTest("${discoveryTableList == '[]'}");
/* 3005 */               int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3006 */               if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                 for (;;) {
/* 3008 */                   out.write("\n                <tr>\n  \t\t\t\t<td colspan=\"5\" class=\"noHover\"><div class=\"infoBlock\">");
/* 3009 */                   out.print(FormatUtil.getString("am.discovery.add.new.discovery.message.txt"));
/* 3010 */                   out.write("</div></td> ");
/* 3011 */                   out.write("\n  \t\t\t\t</tr>\n  \t\t\t\t");
/* 3012 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3013 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3017 */               if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3018 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */               }
/*      */               
/* 3021 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3022 */               out.write("\n</td>\n</tr>\n</table>\n\t\t\t");
/* 3023 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3024 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 3027 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3028 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 3031 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3032 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 3035 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3036 */           out.write("\n\n\t\t\t");
/* 3037 */           if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 3039 */           out.write(32);
/* 3040 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3041 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 3045 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3046 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 3049 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3050 */         out.write("\n\n<script>\n");
/* 3051 */         if ((request.getParameter("method") != null) && (request.getParameter("method").equals("updateServiceDiscovery"))) {
/* 3052 */           out.write("\nshowHide('servicediscovery') //No I18N\n");
/*      */         }
/*      */         
/* 3055 */         out.write("\n\n\n</script>\n<link href=\"/images/discovery_styles.css\" rel=\"stylesheet\" type=\"text/css\">\n\n");
/*      */       }
/* 3057 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3058 */         out = _jspx_out;
/* 3059 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3060 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 3061 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3064 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f0(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3070 */     PageContext pageContext = _jspx_page_context;
/* 3071 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3073 */     PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3074 */     _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 3075 */     _jspx_th_tiles_005fput_005f0.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3077 */     _jspx_th_tiles_005fput_005f0.setName("title");
/*      */     
/* 3079 */     _jspx_th_tiles_005fput_005f0.setValue("Discovery Profiles");
/* 3080 */     int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 3081 */     if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 3082 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3083 */       return true;
/*      */     }
/* 3085 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 3086 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3091 */     PageContext pageContext = _jspx_page_context;
/* 3092 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3094 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3095 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 3096 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3098 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 3100 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=3");
/* 3101 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 3102 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 3103 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3104 */       return true;
/*      */     }
/* 3106 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 3107 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3112 */     PageContext pageContext = _jspx_page_context;
/* 3113 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3115 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3116 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3117 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3119 */     _jspx_th_c_005fout_005f0.setValue("${discoveryStopped}");
/* 3120 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3121 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3122 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3123 */       return true;
/*      */     }
/* 3125 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3126 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3131 */     PageContext pageContext = _jspx_page_context;
/* 3132 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3134 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3135 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3136 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3138 */     _jspx_th_c_005fout_005f1.setValue("${discName}");
/* 3139 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3140 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3141 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3142 */       return true;
/*      */     }
/* 3144 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3145 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f0(JspTag _jspx_th_c_005fchoose_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3150 */     PageContext pageContext = _jspx_page_context;
/* 3151 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3153 */     WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3154 */     _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3155 */     _jspx_th_c_005fwhen_005f0.setParent((Tag)_jspx_th_c_005fchoose_005f0);
/*      */     
/* 3157 */     _jspx_th_c_005fwhen_005f0.setTest("${isAdminServer== 'true'}");
/* 3158 */     int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3159 */     if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */       for (;;) {
/* 3161 */         out.write("\n                       $(\"#operationCompletionStatusId\").show();\n\t      \t           $(\"#operationCompletionStatusId span\").html(result);\n\t      \t           $( \"#operationCompletionStatusId \" ).fadeOut(5000);\n                   ");
/* 3162 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3163 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3167 */     if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3168 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3169 */       return true;
/*      */     }
/* 3171 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3172 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3177 */     PageContext pageContext = _jspx_page_context;
/* 3178 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3180 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3181 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 3182 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 3184 */     _jspx_th_logic_005fpresent_005f0.setRole("DEMO");
/* 3185 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 3186 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 3188 */         out.write("\n\t alertUser();\n\t return;\n\t ");
/* 3189 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 3190 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3194 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 3195 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3196 */       return true;
/*      */     }
/* 3198 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 3199 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f1(JspTag _jspx_th_c_005fchoose_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3204 */     PageContext pageContext = _jspx_page_context;
/* 3205 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3207 */     OtherwiseTag _jspx_th_c_005fotherwise_005f1 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3208 */     _jspx_th_c_005fotherwise_005f1.setPageContext(_jspx_page_context);
/* 3209 */     _jspx_th_c_005fotherwise_005f1.setParent((Tag)_jspx_th_c_005fchoose_005f1);
/* 3210 */     int _jspx_eval_c_005fotherwise_005f1 = _jspx_th_c_005fotherwise_005f1.doStartTag();
/* 3211 */     if (_jspx_eval_c_005fotherwise_005f1 != 0) {
/*      */       for (;;) {
/* 3213 */         out.write("\n    window.location.href='/newDiscoveryAction.do?method=showEditDiscoveryPage&discoveryID='+discoveryID;\n    ");
/* 3214 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f1.doAfterBody();
/* 3215 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3219 */     if (_jspx_th_c_005fotherwise_005f1.doEndTag() == 5) {
/* 3220 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3221 */       return true;
/*      */     }
/* 3223 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f1);
/* 3224 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f2(JspTag _jspx_th_c_005fchoose_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3229 */     PageContext pageContext = _jspx_page_context;
/* 3230 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3232 */     WhenTag _jspx_th_c_005fwhen_005f2 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3233 */     _jspx_th_c_005fwhen_005f2.setPageContext(_jspx_page_context);
/* 3234 */     _jspx_th_c_005fwhen_005f2.setParent((Tag)_jspx_th_c_005fchoose_005f2);
/*      */     
/* 3236 */     _jspx_th_c_005fwhen_005f2.setTest("${isAdminServer== 'true'}");
/* 3237 */     int _jspx_eval_c_005fwhen_005f2 = _jspx_th_c_005fwhen_005f2.doStartTag();
/* 3238 */     if (_jspx_eval_c_005fwhen_005f2 != 0) {
/*      */       for (;;) {
/* 3240 */         out.write("\n    window.location.href='/newDiscoveryAction.do?method=showEditDiscoveryPage&discoveryID='+discoveryID;\n    ");
/* 3241 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f2.doAfterBody();
/* 3242 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3246 */     if (_jspx_th_c_005fwhen_005f2.doEndTag() == 5) {
/* 3247 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3248 */       return true;
/*      */     }
/* 3250 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f2);
/* 3251 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3256 */     PageContext pageContext = _jspx_page_context;
/* 3257 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3259 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3260 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3261 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3263 */     _jspx_th_c_005fout_005f2.setValue("${prop.discoveryID}");
/* 3264 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3265 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3266 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3267 */       return true;
/*      */     }
/* 3269 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3270 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f3(JspTag _jspx_th_c_005fchoose_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3275 */     PageContext pageContext = _jspx_page_context;
/* 3276 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3278 */     OtherwiseTag _jspx_th_c_005fotherwise_005f3 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3279 */     _jspx_th_c_005fotherwise_005f3.setPageContext(_jspx_page_context);
/* 3280 */     _jspx_th_c_005fotherwise_005f3.setParent((Tag)_jspx_th_c_005fchoose_005f3);
/* 3281 */     int _jspx_eval_c_005fotherwise_005f3 = _jspx_th_c_005fotherwise_005f3.doStartTag();
/* 3282 */     if (_jspx_eval_c_005fotherwise_005f3 != 0) {
/*      */       for (;;) {
/* 3284 */         out.write("javascript:deleteDiscovery('");
/* 3285 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fotherwise_005f3, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3286 */           return true;
/* 3287 */         out.write(39);
/* 3288 */         out.write(41);
/* 3289 */         out.write(32);
/* 3290 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f3.doAfterBody();
/* 3291 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3295 */     if (_jspx_th_c_005fotherwise_005f3.doEndTag() == 5) {
/* 3296 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3297 */       return true;
/*      */     }
/* 3299 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f3);
/* 3300 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fotherwise_005f3, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3305 */     PageContext pageContext = _jspx_page_context;
/* 3306 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3308 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3309 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3310 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fotherwise_005f3);
/*      */     
/* 3312 */     _jspx_th_c_005fout_005f3.setValue("${prop.discoveryID}");
/* 3313 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3314 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3315 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3316 */       return true;
/*      */     }
/* 3318 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3319 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fchoose_005f4(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3324 */     PageContext pageContext = _jspx_page_context;
/* 3325 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3327 */     ChooseTag _jspx_th_c_005fchoose_005f4 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3328 */     _jspx_th_c_005fchoose_005f4.setPageContext(_jspx_page_context);
/* 3329 */     _jspx_th_c_005fchoose_005f4.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/* 3330 */     int _jspx_eval_c_005fchoose_005f4 = _jspx_th_c_005fchoose_005f4.doStartTag();
/* 3331 */     if (_jspx_eval_c_005fchoose_005f4 != 0) {
/*      */       for (;;) {
/* 3333 */         if (_jspx_meth_c_005fwhen_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3334 */           return true;
/* 3335 */         if (_jspx_meth_c_005fotherwise_005f4(_jspx_th_c_005fchoose_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3336 */           return true;
/* 3337 */         int evalDoAfterBody = _jspx_th_c_005fchoose_005f4.doAfterBody();
/* 3338 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3342 */     if (_jspx_th_c_005fchoose_005f4.doEndTag() == 5) {
/* 3343 */       this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3344 */       return true;
/*      */     }
/* 3346 */     this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f4);
/* 3347 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fwhen_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3352 */     PageContext pageContext = _jspx_page_context;
/* 3353 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3355 */     WhenTag _jspx_th_c_005fwhen_005f4 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3356 */     _jspx_th_c_005fwhen_005f4.setPageContext(_jspx_page_context);
/* 3357 */     _jspx_th_c_005fwhen_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/*      */     
/* 3359 */     _jspx_th_c_005fwhen_005f4.setTest("${prop.TRIGGEREDFROM=='Admin Server'}");
/* 3360 */     int _jspx_eval_c_005fwhen_005f4 = _jspx_th_c_005fwhen_005f4.doStartTag();
/* 3361 */     if (_jspx_eval_c_005fwhen_005f4 != 0) {
/*      */       for (;;) {
/* 3363 */         out.write(" javascript:editAdminCreatedDiscovery('");
/* 3364 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fwhen_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3365 */           return true;
/* 3366 */         out.write(39);
/* 3367 */         out.write(41);
/* 3368 */         int evalDoAfterBody = _jspx_th_c_005fwhen_005f4.doAfterBody();
/* 3369 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3373 */     if (_jspx_th_c_005fwhen_005f4.doEndTag() == 5) {
/* 3374 */       this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3375 */       return true;
/*      */     }
/* 3377 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f4);
/* 3378 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fwhen_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3383 */     PageContext pageContext = _jspx_page_context;
/* 3384 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3386 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3387 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3388 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fwhen_005f4);
/*      */     
/* 3390 */     _jspx_th_c_005fout_005f4.setValue("${prop.discoveryID}");
/* 3391 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3392 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3393 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3394 */       return true;
/*      */     }
/* 3396 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3397 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f4(JspTag _jspx_th_c_005fchoose_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3402 */     PageContext pageContext = _jspx_page_context;
/* 3403 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3405 */     OtherwiseTag _jspx_th_c_005fotherwise_005f4 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3406 */     _jspx_th_c_005fotherwise_005f4.setPageContext(_jspx_page_context);
/* 3407 */     _jspx_th_c_005fotherwise_005f4.setParent((Tag)_jspx_th_c_005fchoose_005f4);
/* 3408 */     int _jspx_eval_c_005fotherwise_005f4 = _jspx_th_c_005fotherwise_005f4.doStartTag();
/* 3409 */     if (_jspx_eval_c_005fotherwise_005f4 != 0) {
/*      */       for (;;) {
/* 3411 */         out.write("javascript:editDiscovery('");
/* 3412 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fotherwise_005f4, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3413 */           return true;
/* 3414 */         out.write(39);
/* 3415 */         out.write(41);
/* 3416 */         out.write(32);
/* 3417 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f4.doAfterBody();
/* 3418 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3422 */     if (_jspx_th_c_005fotherwise_005f4.doEndTag() == 5) {
/* 3423 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3424 */       return true;
/*      */     }
/* 3426 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f4);
/* 3427 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fotherwise_005f4, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3432 */     PageContext pageContext = _jspx_page_context;
/* 3433 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3435 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3436 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3437 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fotherwise_005f4);
/*      */     
/* 3439 */     _jspx_th_c_005fout_005f5.setValue("${prop.discoveryID}");
/* 3440 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3441 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3442 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3443 */       return true;
/*      */     }
/* 3445 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3446 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3451 */     PageContext pageContext = _jspx_page_context;
/* 3452 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3454 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3455 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3456 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3458 */     _jspx_th_c_005fout_005f6.setValue("${prop.discoveryID}");
/* 3459 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3460 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3461 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3462 */       return true;
/*      */     }
/* 3464 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3465 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f7(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3470 */     PageContext pageContext = _jspx_page_context;
/* 3471 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3473 */     OutTag _jspx_th_c_005fout_005f7 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3474 */     _jspx_th_c_005fout_005f7.setPageContext(_jspx_page_context);
/* 3475 */     _jspx_th_c_005fout_005f7.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3477 */     _jspx_th_c_005fout_005f7.setValue("${prop.discoveryName}");
/* 3478 */     int _jspx_eval_c_005fout_005f7 = _jspx_th_c_005fout_005f7.doStartTag();
/* 3479 */     if (_jspx_th_c_005fout_005f7.doEndTag() == 5) {
/* 3480 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3481 */       return true;
/*      */     }
/* 3483 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f7);
/* 3484 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f8(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3489 */     PageContext pageContext = _jspx_page_context;
/* 3490 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3492 */     OutTag _jspx_th_c_005fout_005f8 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3493 */     _jspx_th_c_005fout_005f8.setPageContext(_jspx_page_context);
/* 3494 */     _jspx_th_c_005fout_005f8.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3496 */     _jspx_th_c_005fout_005f8.setValue("${prop.rangeToShow}");
/* 3497 */     int _jspx_eval_c_005fout_005f8 = _jspx_th_c_005fout_005f8.doStartTag();
/* 3498 */     if (_jspx_th_c_005fout_005f8.doEndTag() == 5) {
/* 3499 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3500 */       return true;
/*      */     }
/* 3502 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f8);
/* 3503 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f9(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3508 */     PageContext pageContext = _jspx_page_context;
/* 3509 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3511 */     OutTag _jspx_th_c_005fout_005f9 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3512 */     _jspx_th_c_005fout_005f9.setPageContext(_jspx_page_context);
/* 3513 */     _jspx_th_c_005fout_005f9.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3515 */     _jspx_th_c_005fout_005f9.setValue("${prop.startTime}");
/* 3516 */     int _jspx_eval_c_005fout_005f9 = _jspx_th_c_005fout_005f9.doStartTag();
/* 3517 */     if (_jspx_th_c_005fout_005f9.doEndTag() == 5) {
/* 3518 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3519 */       return true;
/*      */     }
/* 3521 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f9);
/* 3522 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f10(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3527 */     PageContext pageContext = _jspx_page_context;
/* 3528 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3530 */     OutTag _jspx_th_c_005fout_005f10 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3531 */     _jspx_th_c_005fout_005f10.setPageContext(_jspx_page_context);
/* 3532 */     _jspx_th_c_005fout_005f10.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3534 */     _jspx_th_c_005fout_005f10.setValue("${prop.endTime}");
/* 3535 */     int _jspx_eval_c_005fout_005f10 = _jspx_th_c_005fout_005f10.doStartTag();
/* 3536 */     if (_jspx_th_c_005fout_005f10.doEndTag() == 5) {
/* 3537 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3538 */       return true;
/*      */     }
/* 3540 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f10);
/* 3541 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f11(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3546 */     PageContext pageContext = _jspx_page_context;
/* 3547 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3549 */     OutTag _jspx_th_c_005fout_005f11 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3550 */     _jspx_th_c_005fout_005f11.setPageContext(_jspx_page_context);
/* 3551 */     _jspx_th_c_005fout_005f11.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 3553 */     _jspx_th_c_005fout_005f11.setValue("${prop.discoveryID}");
/* 3554 */     int _jspx_eval_c_005fout_005f11 = _jspx_th_c_005fout_005f11.doStartTag();
/* 3555 */     if (_jspx_th_c_005fout_005f11.doEndTag() == 5) {
/* 3556 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3557 */       return true;
/*      */     }
/* 3559 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f11);
/* 3560 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f12(JspTag _jspx_th_c_005fwhen_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3565 */     PageContext pageContext = _jspx_page_context;
/* 3566 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3568 */     OutTag _jspx_th_c_005fout_005f12 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3569 */     _jspx_th_c_005fout_005f12.setPageContext(_jspx_page_context);
/* 3570 */     _jspx_th_c_005fout_005f12.setParent((Tag)_jspx_th_c_005fwhen_005f5);
/*      */     
/* 3572 */     _jspx_th_c_005fout_005f12.setValue("${prop.scheduleTime}");
/* 3573 */     int _jspx_eval_c_005fout_005f12 = _jspx_th_c_005fout_005f12.doStartTag();
/* 3574 */     if (_jspx_th_c_005fout_005f12.doEndTag() == 5) {
/* 3575 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3576 */       return true;
/*      */     }
/* 3578 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f12);
/* 3579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f13(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3584 */     PageContext pageContext = _jspx_page_context;
/* 3585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3587 */     OutTag _jspx_th_c_005fout_005f13 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3588 */     _jspx_th_c_005fout_005f13.setPageContext(_jspx_page_context);
/* 3589 */     _jspx_th_c_005fout_005f13.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 3591 */     _jspx_th_c_005fout_005f13.setValue("${prop.discoveryID}");
/* 3592 */     int _jspx_eval_c_005fout_005f13 = _jspx_th_c_005fout_005f13.doStartTag();
/* 3593 */     if (_jspx_th_c_005fout_005f13.doEndTag() == 5) {
/* 3594 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3595 */       return true;
/*      */     }
/* 3597 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f13);
/* 3598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f14(JspTag _jspx_th_c_005fwhen_005f6, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     OutTag _jspx_th_c_005fout_005f14 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3607 */     _jspx_th_c_005fout_005f14.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_c_005fout_005f14.setParent((Tag)_jspx_th_c_005fwhen_005f6);
/*      */     
/* 3610 */     _jspx_th_c_005fout_005f14.setValue("${prop.scheduleTime}");
/* 3611 */     int _jspx_eval_c_005fout_005f14 = _jspx_th_c_005fout_005f14.doStartTag();
/* 3612 */     if (_jspx_th_c_005fout_005f14.doEndTag() == 5) {
/* 3613 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3614 */       return true;
/*      */     }
/* 3616 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f14);
/* 3617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fotherwise_005f5(JspTag _jspx_th_c_005fchoose_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3622 */     PageContext pageContext = _jspx_page_context;
/* 3623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3625 */     OtherwiseTag _jspx_th_c_005fotherwise_005f5 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3626 */     _jspx_th_c_005fotherwise_005f5.setPageContext(_jspx_page_context);
/* 3627 */     _jspx_th_c_005fotherwise_005f5.setParent((Tag)_jspx_th_c_005fchoose_005f5);
/* 3628 */     int _jspx_eval_c_005fotherwise_005f5 = _jspx_th_c_005fotherwise_005f5.doStartTag();
/* 3629 */     if (_jspx_eval_c_005fotherwise_005f5 != 0) {
/*      */       for (;;) {
/* 3631 */         out.write("\n\t\t <a href=\"#\" id=\"");
/* 3632 */         if (_jspx_meth_c_005fout_005f15(_jspx_th_c_005fotherwise_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3633 */           return true;
/* 3634 */         out.write("\" name=\"scheduleDiscovery\">");
/* 3635 */         if (_jspx_meth_c_005fout_005f16(_jspx_th_c_005fotherwise_005f5, _jspx_page_context, _jspx_push_body_count_c_005fforEach_005f0))
/* 3636 */           return true;
/* 3637 */         out.write("</a>");
/* 3638 */         out.write("\n\t\t ");
/* 3639 */         int evalDoAfterBody = _jspx_th_c_005fotherwise_005f5.doAfterBody();
/* 3640 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3644 */     if (_jspx_th_c_005fotherwise_005f5.doEndTag() == 5) {
/* 3645 */       this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3646 */       return true;
/*      */     }
/* 3648 */     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f5);
/* 3649 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f15(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3654 */     PageContext pageContext = _jspx_page_context;
/* 3655 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3657 */     OutTag _jspx_th_c_005fout_005f15 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3658 */     _jspx_th_c_005fout_005f15.setPageContext(_jspx_page_context);
/* 3659 */     _jspx_th_c_005fout_005f15.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3661 */     _jspx_th_c_005fout_005f15.setValue("${prop.discoveryID}");
/* 3662 */     int _jspx_eval_c_005fout_005f15 = _jspx_th_c_005fout_005f15.doStartTag();
/* 3663 */     if (_jspx_th_c_005fout_005f15.doEndTag() == 5) {
/* 3664 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3665 */       return true;
/*      */     }
/* 3667 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f15);
/* 3668 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f16(JspTag _jspx_th_c_005fotherwise_005f5, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3673 */     PageContext pageContext = _jspx_page_context;
/* 3674 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3676 */     OutTag _jspx_th_c_005fout_005f16 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3677 */     _jspx_th_c_005fout_005f16.setPageContext(_jspx_page_context);
/* 3678 */     _jspx_th_c_005fout_005f16.setParent((Tag)_jspx_th_c_005fotherwise_005f5);
/*      */     
/* 3680 */     _jspx_th_c_005fout_005f16.setValue("${prop.scheduleTime}");
/* 3681 */     int _jspx_eval_c_005fout_005f16 = _jspx_th_c_005fout_005f16.doStartTag();
/* 3682 */     if (_jspx_th_c_005fout_005f16.doEndTag() == 5) {
/* 3683 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3684 */       return true;
/*      */     }
/* 3686 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f16);
/* 3687 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f17(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3692 */     PageContext pageContext = _jspx_page_context;
/* 3693 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3695 */     OutTag _jspx_th_c_005fout_005f17 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3696 */     _jspx_th_c_005fout_005f17.setPageContext(_jspx_page_context);
/* 3697 */     _jspx_th_c_005fout_005f17.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3699 */     _jspx_th_c_005fout_005f17.setValue("${prop.discoveryID}");
/* 3700 */     int _jspx_eval_c_005fout_005f17 = _jspx_th_c_005fout_005f17.doStartTag();
/* 3701 */     if (_jspx_th_c_005fout_005f17.doEndTag() == 5) {
/* 3702 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 3703 */       return true;
/*      */     }
/* 3705 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f17);
/* 3706 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f18(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3711 */     PageContext pageContext = _jspx_page_context;
/* 3712 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3714 */     OutTag _jspx_th_c_005fout_005f18 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3715 */     _jspx_th_c_005fout_005f18.setPageContext(_jspx_page_context);
/* 3716 */     _jspx_th_c_005fout_005f18.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3718 */     _jspx_th_c_005fout_005f18.setValue("${prop.scanSummary}");
/* 3719 */     int _jspx_eval_c_005fout_005f18 = _jspx_th_c_005fout_005f18.doStartTag();
/* 3720 */     if (_jspx_th_c_005fout_005f18.doEndTag() == 5) {
/* 3721 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 3722 */       return true;
/*      */     }
/* 3724 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f18);
/* 3725 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f19(JspTag _jspx_th_c_005fforEach_005f0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_005fforEach_005f0) throws Throwable
/*      */   {
/* 3730 */     PageContext pageContext = _jspx_page_context;
/* 3731 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3733 */     OutTag _jspx_th_c_005fout_005f19 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3734 */     _jspx_th_c_005fout_005f19.setPageContext(_jspx_page_context);
/* 3735 */     _jspx_th_c_005fout_005f19.setParent((Tag)_jspx_th_c_005fforEach_005f0);
/*      */     
/* 3737 */     _jspx_th_c_005fout_005f19.setValue("${prop.TRIGGEREDFROM}");
/* 3738 */     int _jspx_eval_c_005fout_005f19 = _jspx_th_c_005fout_005f19.doStartTag();
/* 3739 */     if (_jspx_th_c_005fout_005f19.doEndTag() == 5) {
/* 3740 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 3741 */       return true;
/*      */     }
/* 3743 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f19);
/* 3744 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3749 */     PageContext pageContext = _jspx_page_context;
/* 3750 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3752 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3753 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3754 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3756 */     _jspx_th_tiles_005fput_005f3.setName("footer");
/*      */     
/* 3758 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/footer.jsp");
/* 3759 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3760 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3761 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3762 */       return true;
/*      */     }
/* 3764 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3765 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\DiscoveryProfiles_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */