/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.tags.AdminLink;
/*      */ import com.adventnet.appmanager.tags.HiddenParam;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import java.io.IOException;
/*      */ import java.net.InetAddress;
/*      */ import java.net.URLEncoder;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
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
/*      */ import javax.servlet.jsp.tagext.BodyContent;
/*      */ import javax.servlet.jsp.tagext.JspTag;
/*      */ import javax.servlet.jsp.tagext.Tag;
/*      */ import javax.swing.tree.DefaultMutableTreeNode;
/*      */ import org.apache.jasper.runtime.TagHandlerPool;
/*      */ import org.apache.struts.taglib.bean.DefineTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ 
/*      */ public final class vmlistview_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
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
/*  813 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  814 */     getRCATrimmedText(div1, rca);
/*  815 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  818 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  819 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
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
/*  972 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
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
/* 2041 */       DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
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
/*      */ 
/*      */   private String getSeverity(int status)
/*      */   {
/* 2180 */     switch (status)
/*      */     {
/*      */     case 1: 
/* 2183 */       return "<span class=\"alerttext\">Error</span>";
/*      */     case 2: 
/* 2185 */       return "<span class=\"alerttext\">Error</span>";
/*      */     case 3: 
/* 2187 */       return "<span class=\"alerttext\">Error</span>";
/*      */     case 4: 
/* 2189 */       return "<span class=\"alerttext\">Warning</span>";
/*      */     case 5: 
/* 2191 */       return "Running";
/*      */     case 6: 
/* 2193 */       return "Running";
/*      */     case 7: 
/* 2195 */       return "UnManaged";
/*      */     }
/*      */     
/* 2198 */     return null;
/*      */   }
/*      */   
/* 2201 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2207 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2208 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2222 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2226 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2233 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2237 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2238 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2239 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2240 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.release();
/* 2242 */     this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2249 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2252 */     JspWriter out = null;
/* 2253 */     Object page = this;
/* 2254 */     JspWriter _jspx_out = null;
/* 2255 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2259 */       response.setContentType("text/html;charset=UTF-8");
/* 2260 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2262 */       _jspx_page_context = pageContext;
/* 2263 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2264 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2265 */       session = pageContext.getSession();
/* 2266 */       out = pageContext.getOut();
/* 2267 */       _jspx_out = out;
/*      */       
/* 2269 */       out.write("<!--$Id$-->\n\n\n\n\n\n\n\n\n\n\n");
/* 2270 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2272 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2273 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2274 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2276 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2278 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2282 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2283 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2284 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2285 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2288 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2289 */         String available = null;
/* 2290 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2291 */         out.write(10);
/*      */         
/* 2293 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2294 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2295 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2297 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2299 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2303 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2304 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2305 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2306 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2309 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2310 */           String unavailable = null;
/* 2311 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2312 */           out.write(10);
/*      */           
/* 2314 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2315 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2316 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2318 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2320 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2324 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2325 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2326 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2327 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2330 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2331 */             String unmanaged = null;
/* 2332 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2333 */             out.write(10);
/*      */             
/* 2335 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2336 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2337 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2339 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2341 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2345 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2346 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2347 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2348 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2351 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2352 */               String scheduled = null;
/* 2353 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2354 */               out.write(10);
/*      */               
/* 2356 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2357 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2358 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2360 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2362 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2366 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2367 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2368 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2369 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2372 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2373 */                 String critical = null;
/* 2374 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2375 */                 out.write(10);
/*      */                 
/* 2377 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2378 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2379 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2381 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2383 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2387 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2388 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2389 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2390 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2393 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2394 */                   String clear = null;
/* 2395 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2396 */                   out.write(10);
/*      */                   
/* 2398 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2399 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2400 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2402 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2404 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2408 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2409 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2410 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2411 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2414 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2415 */                     String warning = null;
/* 2416 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2417 */                     out.write(10);
/* 2418 */                     out.write(10);
/*      */                     
/* 2420 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2421 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2423 */                     out.write(10);
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/* 2426 */                     out.write("\n\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\"></SCRIPT>\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/appmanager.js\"></SCRIPT>\n<body onLoad=\"javascript:myOnLoad()\"></body>\n");
/* 2427 */                     ManagedApplication mo = null;
/* 2428 */                     mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 2);
/* 2429 */                     if (mo == null) {
/* 2430 */                       mo = new ManagedApplication();
/* 2431 */                       _jspx_page_context.setAttribute("mo", mo, 2);
/*      */                     }
/* 2433 */                     out.write(10);
/* 2434 */                     Hashtable motypedisplaynames = null;
/* 2435 */                     synchronized (application) {
/* 2436 */                       motypedisplaynames = (Hashtable)_jspx_page_context.getAttribute("motypedisplaynames", 4);
/* 2437 */                       if (motypedisplaynames == null) {
/* 2438 */                         motypedisplaynames = new Hashtable();
/* 2439 */                         _jspx_page_context.setAttribute("motypedisplaynames", motypedisplaynames, 4);
/*      */                       }
/*      */                     }
/* 2442 */                     out.write(10);
/* 2443 */                     Hashtable availabilitykeys = null;
/* 2444 */                     synchronized (application) {
/* 2445 */                       availabilitykeys = (Hashtable)_jspx_page_context.getAttribute("availabilitykeys", 4);
/* 2446 */                       if (availabilitykeys == null) {
/* 2447 */                         availabilitykeys = new Hashtable();
/* 2448 */                         _jspx_page_context.setAttribute("availabilitykeys", availabilitykeys, 4);
/*      */                       }
/*      */                     }
/* 2451 */                     out.write(10);
/* 2452 */                     Hashtable healthkeys = null;
/* 2453 */                     synchronized (application) {
/* 2454 */                       healthkeys = (Hashtable)_jspx_page_context.getAttribute("healthkeys", 4);
/* 2455 */                       if (healthkeys == null) {
/* 2456 */                         healthkeys = new Hashtable();
/* 2457 */                         _jspx_page_context.setAttribute("healthkeys", healthkeys, 4);
/*      */                       }
/*      */                     }
/* 2460 */                     out.write("\n<script>\nfunction showDiva(ids,idh,idc)\n{\n\t\tif(document.getElementById(idh).style.display == 'none')\n\t\t{\n\t\t\tdocument.getElementById(ids).style.display='none';\n\t\t\tdocument.getElementById(idh).style.display='block';\n\t\t\tdocument.getElementById(idc).style.display='block';\n\t\t}\n\n\n}\nfunction hideDiva(ids,idh,idc)\n{\n\t\tif(document.getElementById(idh).style.display == 'block')\n\t\t{\n\t\t\tdocument.getElementById(ids).style.display='block';\n\t\t\tdocument.getElementById(idh).style.display='none';\n\t\t\tdocument.getElementById(idc).style.display='none';\n\t\t}\n}\n\n</script>\n");
/*      */                     
/*      */ 
/*      */                     try
/*      */                     {
/* 2465 */                       ArrayList list = (ArrayList)request.getAttribute("resourcetable");
/* 2466 */                       ArrayList list1 = (ArrayList)request.getAttribute("resourcetableunmanagecount");
/* 2467 */                       ArrayList agentList = (ArrayList)request.getAttribute("rbmagenttable");
/* 2468 */                       ArrayList resIDs = new ArrayList();
/* 2469 */                       ArrayList vmList = (ArrayList)request.getAttribute("ChildList");
/*      */                       
/* 2471 */                       ArrayList attribIDs = new ArrayList();
/*      */                       
/* 2473 */                       Properties alert = getAlerts(list, availabilitykeys, healthkeys);
/* 2474 */                       Properties vmAlerts = getAlerts(vmList, availabilitykeys, healthkeys);
/* 2475 */                       alert.putAll(vmAlerts);
/* 2476 */                       request.setAttribute("alert", alert);
/*      */                       
/* 2478 */                       String resourceName = request.getParameter("network");
/* 2479 */                       String network = request.getParameter("network");
/* 2480 */                       String group = request.getParameter("group");
/* 2481 */                       if (group != null)
/*      */                       {
/* 2483 */                         resourceName = group;
/*      */                       }
/* 2485 */                       String applicationName = (String)request.getAttribute("appName");
/* 2486 */                       String haid = (String)request.getAttribute("haid");
/* 2487 */                       boolean displayresourcetype = false;
/*      */                       
/* 2489 */                       int rowcount = list.size();
/*      */                       
/* 2491 */                       out.write(10);
/* 2492 */                       if (_jspx_meth_c_005fif_005f0(_jspx_page_context))
/*      */                         return;
/* 2494 */                       out.write("\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >\n<tr><td>\n\t\t\t\n\t\t\t\n\n\n\n\t \n\t   \n\t\t\t\n\n\t\t\t\n\n\n<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\">\n    <tr>\n\n       \n                <td valign=\"top\" width=\"100%\">\n\t\t<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" >\n\t\t   <tr>\n\t\t\t <td colspan=\"4\" valign=\"top\" >\n");
/*      */                       
/* 2496 */                       if (rowcount > 0)
/*      */                       {
/* 2498 */                         String restype = request.getParameter("network");
/*      */                         
/* 2500 */                         out.write("\n\t\t         <input type=\"hidden\" name=\"type\" value=\"");
/* 2501 */                         out.print(restype);
/* 2502 */                         out.write("\" />\n\t\t\t");
/*      */                         
/* 2504 */                         IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2505 */                         _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2506 */                         _jspx_th_c_005fif_005f1.setParent(null);
/*      */                         
/* 2508 */                         _jspx_th_c_005fif_005f1.setTest("${!empty param.group}");
/* 2509 */                         int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2510 */                         if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                           for (;;) {
/* 2512 */                             out.write("\n\t\t\t<input type=\"hidden\" name=\"group\" value=\"");
/* 2513 */                             out.print(request.getParameter("group"));
/* 2514 */                             out.write("\" />\n\t\t\t");
/* 2515 */                             int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2516 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/*      */                         }
/* 2520 */                         if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2521 */                           this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                         }
/*      */                         
/* 2524 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2525 */                         out.write(10);
/* 2526 */                         out.write(9);
/* 2527 */                         if (_jspx_meth_am_005fhiddenparam_005f0(_jspx_page_context))
/*      */                           return;
/* 2529 */                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n\t<tr>\n\t<td width=\"100%\" valign=\"top\">\n\t<table width=\"100%\" id=\"allResourceTable\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n\t<tr class=\"bodytextbold\">\n\t<td width=\"9\" height=\"28\" valign=\"center\"  class=\"columnheadingnotop\"><img src=\"../images/spacer.gif\" width=\"9px\" height=\"25px\" valign=\"center\">&nbsp;</td>\n\t<td height=\"28\" width=\"25%\" class=\"columnheadingnotop\" >");
/* 2530 */                         out.print(FormatUtil.getString("am.webclient.common.name.text"));
/* 2531 */                         out.write("</td>\n\n\t<td height=\"28\"  class=\"columnheadingnotop\" align=\"center\" width=\"10%\">");
/* 2532 */                         out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2533 */                         out.write("</td>\n\t<td  height=\"28\"  class=\"columnheadingnotop\" align=\"center\"  width=\"10%\">");
/* 2534 */                         out.print(FormatUtil.getString("am.webclient.common.health.text"));
/* 2535 */                         out.write("</td>\n\n\t<td  height=\"28\"  class=\"columnheadingnotop\" align=\"center\"  width=\"14%\" nowrap>");
/* 2536 */                         out.print(FormatUtil.getString("am.vm.listview.cpuutil.text"));
/* 2537 */                         out.write("(%)</td>\n\t");
/*      */                         
/* 2539 */                         if (!restype.equals("XenServerHost"))
/*      */                         {
/*      */ 
/* 2542 */                           out.write("\n\t<td  height=\"28\"  class=\"columnheadingnotop\" align=\"center\"  width=\"14%\" nowrap>");
/* 2543 */                           out.print(FormatUtil.getString("am.vm.listview.memutil.text"));
/* 2544 */                           out.write("(%)</td>\n\t<td  height=\"28\"  class=\"columnheadingnotop\" align=\"center\"  width=\"12%\" nowrap>");
/* 2545 */                           out.print(FormatUtil.getString("am.vm.listview.diskusage.text"));
/* 2546 */                           out.write(40);
/* 2547 */                           out.print(FormatUtil.getString("am.webclient.kbps.text"));
/* 2548 */                           out.write(")</td>\n\t<td  height=\"28\"  class=\"columnheadingnotop\" align=\"center\"  width=\"12%\" nowrap>");
/* 2549 */                           out.print(FormatUtil.getString("am.vm.listview.netusage.text"));
/* 2550 */                           out.write(40);
/* 2551 */                           out.print(FormatUtil.getString("am.webclient.kbps.text"));
/* 2552 */                           out.write(")</td>\n\t");
/*      */ 
/*      */                         }
/*      */                         else
/*      */                         {
/*      */ 
/* 2558 */                           out.write("\n\t<td  height=\"28\"  class=\"columnheadingnotop\" align=\"center\"  width=\"14%\" nowrap>");
/* 2559 */                           out.print(FormatUtil.getString("am.vm.listview.memutil.text"));
/* 2560 */                           out.write(40);
/* 2561 */                           out.print(FormatUtil.getString("MB"));
/* 2562 */                           out.write(")</td><!-- NO I18N -->\n\t");
/*      */                         }
/*      */                         
/*      */ 
/* 2566 */                         out.write("\n\t</tr>\n\t");
/*      */                         
/* 2568 */                         DecimalFormat twoDecPer = new DecimalFormat("###,###.##");
/* 2569 */                         getSortedMonitorList(list, alert, availabilitykeys, healthkeys);
/* 2570 */                         String method = request.getParameter("method");
/* 2571 */                         Hashtable listHash = (Hashtable)request.getAttribute("listView");
/*      */                         
/* 2573 */                         Hashtable guestHostMap = (Hashtable)request.getAttribute("GuesHostMapping");
/* 2574 */                         for (int n = 0; n < list.size(); n++)
/*      */                         {
/* 2576 */                           ArrayList row = (ArrayList)list.get(n);
/* 2577 */                           String displayname = (String)row.get(5);
/* 2578 */                           String resourceid = (String)row.get(6);
/* 2579 */                           String resourcename = (String)row.get(7);
/* 2580 */                           String sClass = null;
/* 2581 */                           String cpuUtilz = "-";
/* 2582 */                           String memUtilz = "-";
/* 2583 */                           String diskUsage = "-";
/* 2584 */                           String netUsage = "-";
/* 2585 */                           String link = (String)row.get(0);
/* 2586 */                           String temp = link;
/* 2587 */                           String link1 = "/showresource.do?resourceid=" + resourceid + "&type=" + resourcename + "&moname=" + URLEncoder.encode(link) + "&method=showdetails&resourcename=" + URLEncoder.encode(displayname) + "&viewType=" + method;
/* 2588 */                           String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + resourceid;
/* 2589 */                           if (n % 2 == 0)
/*      */                           {
/* 2591 */                             sClass = "class=\"whitegrayborder\"";
/*      */                           }
/*      */                           else
/*      */                           {
/* 2595 */                             sClass = "class=\"yellowgrayborder\"";
/*      */                           }
/* 2597 */                           String class1 = "ResourceName";
/* 2598 */                           String tooltip = displayname;
/* 2599 */                           if (com.adventnet.appmanager.server.framework.datacollection.DataCollectionControllerUtil.isUnManaged(String.valueOf(resourceid)))
/*      */                           {
/* 2601 */                             class1 = "disabledtext";
/* 2602 */                             tooltip = displayname + " - " + FormatUtil.getString("UnManaged");
/*      */                           }
/* 2604 */                           if (resourcename.equals("XenServerHost"))
/*      */                           {
/* 2606 */                             if (listHash.get(resourceid + "#" + "15005") != null)
/*      */                             {
/* 2608 */                               cpuUtilz = listHash.get(resourceid + "#" + "15005").toString();
/* 2609 */                               cpuUtilz = twoDecPer.format(Double.parseDouble(cpuUtilz));
/*      */                             }
/* 2611 */                             if (listHash.get(resourceid + "#" + "15010") != null)
/*      */                             {
/* 2613 */                               memUtilz = listHash.get(resourceid + "#" + "15010").toString();
/* 2614 */                               memUtilz = twoDecPer.format(Double.parseDouble(memUtilz));
/*      */                             }
/*      */                           }
/*      */                           else
/*      */                           {
/* 2619 */                             if (listHash.get(resourceid + "#" + "7520") != null)
/*      */                             {
/* 2621 */                               cpuUtilz = listHash.get(resourceid + "#" + "7520").toString();
/*      */                             }
/* 2623 */                             if (listHash.get(resourceid + "#" + "7522") != null)
/*      */                             {
/* 2625 */                               memUtilz = listHash.get(resourceid + "#" + "7522").toString();
/*      */                             }
/* 2627 */                             if (listHash.get(resourceid + "#" + "7523") != null)
/*      */                             {
/* 2629 */                               diskUsage = listHash.get(resourceid + "#" + "7523").toString();
/*      */                             }
/* 2631 */                             if (listHash.get(resourceid + "#" + "7524") != null)
/*      */                             {
/* 2633 */                               netUsage = listHash.get(resourceid + "#" + "7524").toString();
/*      */                             }
/*      */                           }
/*      */                           
/* 2637 */                           out.write("\n\t\t\t<tr height=\"30\">\n\t\t\t");
/*      */                           
/* 2639 */                           if (resourcename.equals("XenServerHost"))
/*      */                           {
/*      */ 
/* 2642 */                             out.write("\n\t\t\t<td height=\"30\" width=\"2%\" class=\"whitegrayborder\" align=\"center\">\n\t\t\t");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2648 */                             out.write("\n\t\t\t<td height=\"30\" width=\"9\" class=\"whitegrayborder\" align=\"center\">\n\t\t\t");
/*      */                           }
/*      */                           
/* 2651 */                           String ids = resourceid + "s";
/* 2652 */                           String idh = resourceid + "h";
/* 2653 */                           String idc = resourceid + "c";
/*      */                           
/* 2655 */                           out.write("\n\t\t\t<div  id=\"");
/* 2656 */                           out.print(ids);
/* 2657 */                           out.write("\" style=\"display:block;\" onclick=\"javascript:showDiva('");
/* 2658 */                           out.print(ids);
/* 2659 */                           out.write(39);
/* 2660 */                           out.write(44);
/* 2661 */                           out.write(39);
/* 2662 */                           out.print(idh);
/* 2663 */                           out.write(39);
/* 2664 */                           out.write(44);
/* 2665 */                           out.write(39);
/* 2666 */                           out.print(idc);
/* 2667 */                           out.write("');\">\n\t\t\t<span>\n\t\t\t<img src=\"../images/icon_plus.gif\"   width=\"9px\" height=\"9px\">\n\t\t\t</span>\n\t\t\t</div>\n\n\t\t\t<div  id=\"");
/* 2668 */                           out.print(idh);
/* 2669 */                           out.write("\" style=\"display:none;\" onclick=\"javascript:hideDiva('");
/* 2670 */                           out.print(ids);
/* 2671 */                           out.write(39);
/* 2672 */                           out.write(44);
/* 2673 */                           out.write(39);
/* 2674 */                           out.print(idh);
/* 2675 */                           out.write(39);
/* 2676 */                           out.write(44);
/* 2677 */                           out.write(39);
/* 2678 */                           out.print(idc);
/* 2679 */                           out.write("');\">\n\t\t\t<span>\n\t\t\t<img src=\"../images/icon_minus.gif\"  width=\"9\" height=\"9\">\n\t\t\t</span>\n\t\t\t</div>\n\t\t \t</td>\n\t\t\t<td height=\"30\" title=\"");
/* 2680 */                           out.print(tooltip);
/* 2681 */                           out.write(34);
/* 2682 */                           out.write(32);
/* 2683 */                           out.print(sClass);
/* 2684 */                           out.write("><a href='");
/* 2685 */                           out.print(link1);
/* 2686 */                           out.write("' class='");
/* 2687 */                           out.print(class1);
/* 2688 */                           out.write(39);
/* 2689 */                           out.write(62);
/* 2690 */                           out.write(32);
/* 2691 */                           out.print(displayname);
/* 2692 */                           out.write("</a>&nbsp;\n\n\t\t\t");
/*      */                           
/* 2694 */                           if (((EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN"))) && (
/* 2695 */                             (Integer.parseInt(resourceid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) || (request.getRemoteUser().equals("systemadmin_enterprise"))))
/*      */                           {
/* 2697 */                             String link2 = "/showresource.do?resourceid=" + resourceid + "&type=" + resourceName + "&moname=" + URLEncoder.encode(link) + "&method=showdetails&resourcename=" + URLEncoder.encode(displayname) + "&aam_jump=true&useHTTP=true";
/*      */                             
/*      */ 
/*      */ 
/*      */ 
/* 2702 */                             out.write("\n\t\t\t<a target=\"mas_window\" href=\"");
/* 2703 */                             out.print(link2);
/* 2704 */                             out.write("\">\n  \t        \t\t<img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/>\n  \t        \t</a>\n\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/* 2709 */                           out.write("\n\n\n\t\t\t&nbsp;</td>\n\t\t\t ");
/*      */                           
/* 2711 */                           SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2712 */                           _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 2713 */                           _jspx_th_c_005fset_005f0.setParent(null);
/*      */                           
/* 2715 */                           _jspx_th_c_005fset_005f0.setVar("key");
/* 2716 */                           int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 2717 */                           if (_jspx_eval_c_005fset_005f0 != 0) {
/* 2718 */                             if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2719 */                               out = _jspx_page_context.pushBody();
/* 2720 */                               _jspx_th_c_005fset_005f0.setBodyContent((BodyContent)out);
/* 2721 */                               _jspx_th_c_005fset_005f0.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2724 */                               out.write(32);
/* 2725 */                               out.print(resourceid + "#" + availabilitykeys.get(resourcename) + "#" + "MESSAGE");
/* 2726 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f0.doAfterBody();
/* 2727 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2730 */                             if (_jspx_eval_c_005fset_005f0 != 1) {
/* 2731 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2734 */                           if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 2735 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0); return;
/*      */                           }
/*      */                           
/* 2738 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f0);
/* 2739 */                           out.write("\n          \t\t<td height=\"30\" align=\"center\" ");
/* 2740 */                           out.print(sClass);
/* 2741 */                           out.write("><a href=\"javascript:void(0)\" ");
/* 2742 */                           if (_jspx_meth_c_005fif_005f2(_jspx_page_context))
/*      */                             return;
/* 2744 */                           out.write(" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 2745 */                           out.print(resourceid);
/* 2746 */                           out.write("&period=0')\">");
/* 2747 */                           out.print(getSeverityImageForMssqlAvailability(alert.getProperty(resourceid + "#" + availabilitykeys.get(resourceName))));
/* 2748 */                           out.write("</a></td>\n\t\t\t ");
/*      */                           
/* 2750 */                           SetTag _jspx_th_c_005fset_005f1 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 2751 */                           _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
/* 2752 */                           _jspx_th_c_005fset_005f1.setParent(null);
/*      */                           
/* 2754 */                           _jspx_th_c_005fset_005f1.setVar("key");
/* 2755 */                           int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
/* 2756 */                           if (_jspx_eval_c_005fset_005f1 != 0) {
/* 2757 */                             if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2758 */                               out = _jspx_page_context.pushBody();
/* 2759 */                               _jspx_th_c_005fset_005f1.setBodyContent((BodyContent)out);
/* 2760 */                               _jspx_th_c_005fset_005f1.doInitBody();
/*      */                             }
/*      */                             for (;;) {
/* 2763 */                               out.write(32);
/* 2764 */                               out.print(resourceid + "#" + healthkeys.get(resourceName) + "#" + "MESSAGE");
/* 2765 */                               int evalDoAfterBody = _jspx_th_c_005fset_005f1.doAfterBody();
/* 2766 */                               if (evalDoAfterBody != 2)
/*      */                                 break;
/*      */                             }
/* 2769 */                             if (_jspx_eval_c_005fset_005f1 != 1) {
/* 2770 */                               out = _jspx_page_context.popBody();
/*      */                             }
/*      */                           }
/* 2773 */                           if (_jspx_th_c_005fset_005f1.doEndTag() == 5) {
/* 2774 */                             this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1); return;
/*      */                           }
/*      */                           
/* 2777 */                           this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f1);
/* 2778 */                           out.write("\n\t\t\t<td height=\"30\" align=\"center\" ");
/* 2779 */                           out.print(sClass);
/* 2780 */                           out.write("> <a href=\"javascript:void(0)\" ");
/* 2781 */                           if (_jspx_meth_c_005fif_005f3(_jspx_page_context))
/*      */                             return;
/* 2783 */                           out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 2784 */                           out.print(resourceid);
/* 2785 */                           out.write("&attributeid=");
/* 2786 */                           out.print(healthkeys.get(resourceName));
/* 2787 */                           out.write("&alertconfigurl=");
/* 2788 */                           out.print(URLEncoder.encode(thresholdurl));
/* 2789 */                           out.write("')\">");
/* 2790 */                           out.print(getSeverityImageForHealth(alert.getProperty(resourceid + "#" + healthkeys.get(resourceName))));
/* 2791 */                           out.write("</a></td>\n\n\t\t\t");
/*      */                           
/* 2793 */                           String cpuId = "";
/* 2794 */                           if (restype.equals("XenServerHost"))
/*      */                           {
/* 2796 */                             cpuId = "15005";
/*      */                           }
/*      */                           else
/*      */                           {
/* 2800 */                             cpuId = "7520";
/*      */                           }
/* 2802 */                           String severity = alert.getProperty(resourceid + "#" + cpuId);
/* 2803 */                           String bcolor = null;
/* 2804 */                           if (severity == null)
/*      */                           {
/* 2806 */                             bcolor = "mon_greenbg";
/*      */                           }
/* 2808 */                           else if (severity.equals("5"))
/*      */                           {
/* 2810 */                             bcolor = "mon_greenbg";
/*      */                           }
/* 2812 */                           else if (severity.equals("1"))
/*      */                           {
/* 2814 */                             bcolor = "mon_redbg";
/*      */                           }
/* 2816 */                           else if (severity.equals("4"))
/*      */                           {
/* 2818 */                             bcolor = "mon_orangebg";
/*      */                           }
/*      */                           else
/*      */                           {
/* 2822 */                             bcolor = "mon_greenbg";
/*      */                           }
/*      */                           
/* 2825 */                           out.write("\n\t\t\t<td height=\"30\" align=\"center\" ");
/* 2826 */                           out.print(sClass);
/* 2827 */                           out.write(">\n\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2828 */                           out.print(resourceid);
/* 2829 */                           out.write("&attributeid=");
/* 2830 */                           out.print(cpuId);
/* 2831 */                           out.write("&period=0')\" class=\"mon_bg_bdr\">\n\t\t\t<div style=\"width:");
/* 2832 */                           out.print(cpuUtilz);
/* 2833 */                           out.write("%\" class=\"");
/* 2834 */                           out.print(bcolor);
/* 2835 */                           out.write("\">\n\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 2836 */                           out.print(cpuUtilz);
/* 2837 */                           out.write("</span>\n\t\t\t</div>\n\t\t\t</div>\n\t\t\t</td>\n\n\t\t\t");
/*      */                           
/* 2839 */                           String memId = "";
/* 2840 */                           if (restype.equals("XenServerHost"))
/*      */                           {
/* 2842 */                             memId = "15010";
/*      */                           }
/*      */                           else
/*      */                           {
/* 2846 */                             memId = "7522";
/*      */                           }
/* 2848 */                           severity = alert.getProperty(resourceid + "#" + memId);
/* 2849 */                           bcolor = null;
/* 2850 */                           if (severity == null)
/*      */                           {
/* 2852 */                             bcolor = "mon_greenbg";
/*      */                           }
/* 2854 */                           else if (severity.equals("5"))
/*      */                           {
/* 2856 */                             bcolor = "mon_greenbg";
/*      */                           }
/* 2858 */                           else if (severity.equals("1"))
/*      */                           {
/* 2860 */                             bcolor = "mon_redbg";
/*      */                           }
/* 2862 */                           else if (severity.equals("4"))
/*      */                           {
/* 2864 */                             bcolor = "mon_orangebg";
/*      */                           }
/*      */                           else
/*      */                           {
/* 2868 */                             bcolor = "mon_greenbg";
/*      */                           }
/*      */                           
/* 2871 */                           out.write("\n\t\t\t<td height=\"30\" align=\"center\" ");
/* 2872 */                           out.print(sClass);
/* 2873 */                           out.write(">\n\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2874 */                           out.print(resourceid);
/* 2875 */                           out.write("&attributeid=");
/* 2876 */                           out.print(memId);
/* 2877 */                           out.write("&period=0')\" class=\"mon_bg_bdr\">\n\t\t\t");
/*      */                           
/* 2879 */                           if (restype.equals("XenServerHost"))
/*      */                           {
/*      */ 
/* 2882 */                             out.write("\n\t\t\t<div style=\"width:100%\" class=\"");
/* 2883 */                             out.print(bcolor);
/* 2884 */                             out.write("\">\n\t\t\t<span class=\"mon_txt\" style=\"left:10px;\">&nbsp;");
/* 2885 */                             out.print(memUtilz);
/* 2886 */                             out.write("</span>\n\t\t\t</div>\n\t\t\t");
/*      */ 
/*      */                           }
/*      */                           else
/*      */                           {
/*      */ 
/* 2892 */                             out.write("\n\t\t\t<div style=\"width:");
/* 2893 */                             out.print(memUtilz);
/* 2894 */                             out.write("%\" class=\"");
/* 2895 */                             out.print(bcolor);
/* 2896 */                             out.write("\">\n\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 2897 */                             out.print(memUtilz);
/* 2898 */                             out.write("</span>\n\t\t\t</div>\n\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 2902 */                           out.write("\n\t\t\t</div>\n\t\t\t</td>\n\n\t\t\t");
/*      */                           
/* 2904 */                           if (!restype.equals("XenServerHost"))
/*      */                           {
/*      */ 
/* 2907 */                             out.write("\n\t\t\t<td height=\"30\" align=\"center\" ");
/* 2908 */                             out.print(sClass);
/* 2909 */                             out.write(">\n\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2910 */                             out.print(resourceid);
/* 2911 */                             out.write("&attributeid=7523&period=0')\" onmouseover=\"this.style.cursor='pointer'\">");
/* 2912 */                             out.print(diskUsage);
/* 2913 */                             out.write("</div>\n\n\t\t\t</td>\n\n\t\t\t<td height=\"30\" align=\"center\" ");
/* 2914 */                             out.print(sClass);
/* 2915 */                             out.write(">\n\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 2916 */                             out.print(resourceid);
/* 2917 */                             out.write("&attributeid=7524&period=0')\" onmouseover=\"this.style.cursor='pointer'\">");
/* 2918 */                             out.print(netUsage);
/* 2919 */                             out.write("</div>\n\t\t\t</td>\n\t\t\t");
/*      */                           }
/*      */                           
/*      */ 
/* 2923 */                           out.write("\n\t\t\t</tr>\n\t\t\t<tr>\n\t\t\t<td colspan='8'>\n\t\t\t<div  id=\"");
/* 2924 */                           out.print(idc);
/* 2925 */                           out.write("\" style=\"display:none;\">\n\t\t\t<table width=\"100%\"  border=\"0\" cellpadding=\"0\"   cellspacing=\"0\">\n\n\t");
/*      */                           
/* 2927 */                           if (guestHostMap.get(resourceid) != null)
/*      */                           {
/*      */ 
/* 2930 */                             ArrayList childList = (ArrayList)guestHostMap.get(resourceid);
/* 2931 */                             for (int k = 0; k < childList.size(); k++)
/*      */                             {
/* 2933 */                               cpuUtilz = "-";
/* 2934 */                               memUtilz = "-";
/* 2935 */                               diskUsage = "-";
/* 2936 */                               netUsage = "-";
/* 2937 */                               String[] childDetails = ((String)childList.get(k)).split("##");
/* 2938 */                               thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childDetails[0];
/* 2939 */                               String vmlink = "/showresource.do?resourceid=" + childDetails[0] + "&type=VirtualMachine&moname=" + URLEncoder.encode(childDetails[1]) + "&method=showdetails&resourcename=" + URLEncoder.encode(childDetails[1]) + "&viewType=" + method + "&fromwhere=infrastructure";
/* 2940 */                               if (resourcename.equals("XenServerHost"))
/*      */                               {
/* 2942 */                                 vmlink = "/showresource.do?resourceid=" + childDetails[0] + "&type=XenServerVM&moname=" + URLEncoder.encode(childDetails[1]) + "&method=showdetails&resourcename=" + URLEncoder.encode(childDetails[1]) + "&viewType=" + method + "&fromwhere=infrastructure";
/* 2943 */                                 if (listHash.get(childDetails[0] + "#" + "15509") != null)
/*      */                                 {
/* 2945 */                                   cpuUtilz = listHash.get(childDetails[0] + "#" + "15509").toString();
/* 2946 */                                   cpuUtilz = twoDecPer.format(Double.parseDouble(cpuUtilz));
/*      */                                 }
/* 2948 */                                 if (listHash.get(childDetails[0] + "#" + "15513") != null)
/*      */                                 {
/* 2950 */                                   memUtilz = listHash.get(childDetails[0] + "#" + "15513").toString();
/* 2951 */                                   memUtilz = twoDecPer.format(Double.parseDouble(memUtilz));
/*      */                                 }
/*      */                               }
/*      */                               else
/*      */                               {
/* 2956 */                                 if (listHash.get(childDetails[0] + "#" + "7624") != null)
/*      */                                 {
/* 2958 */                                   cpuUtilz = listHash.get(childDetails[0] + "#" + "7624").toString();
/*      */                                 }
/* 2960 */                                 if (listHash.get(childDetails[0] + "#" + "7626") != null)
/*      */                                 {
/* 2962 */                                   memUtilz = listHash.get(childDetails[0] + "#" + "7626").toString();
/*      */                                 }
/* 2964 */                                 if (listHash.get(childDetails[0] + "#" + "7627") != null)
/*      */                                 {
/* 2966 */                                   diskUsage = listHash.get(childDetails[0] + "#" + "7627").toString();
/*      */                                 }
/* 2968 */                                 if (listHash.get(childDetails[0] + "#" + "7628") != null)
/*      */                                 {
/* 2970 */                                   netUsage = listHash.get(childDetails[0] + "#" + "7628").toString();
/*      */                                 }
/*      */                               }
/*      */                               
/* 2974 */                               out.write("\n\t\t\t\t<tr height=\"30\">\n\t\t\t\t");
/*      */                               
/* 2976 */                               if (resourcename.equals("XenServerHost"))
/*      */                               {
/*      */ 
/* 2979 */                                 out.write("\n\t\t\t\t<td width=\"2%\" height=\"30\" class=\"yellowbg\"><img src=\"../images/spacer.gif\" width=\"9px\" height=\"9px\" valign=\"center\">&nbsp;</td>\n\t\t\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 2985 */                                 out.write("\n\t\t\t\t<td width=\"9\" height=\"30\" class=\"yellowbg\"><img src=\"../images/spacer.gif\" width=\"9px\" height=\"9px\" valign=\"center\">&nbsp;</td>\n\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 2989 */                               out.write("\n\t\t\t\t<td width=\"25%\" height=\"30\" title=\"");
/* 2990 */                               out.print(childDetails[1]);
/* 2991 */                               out.write("\" class=\"yellowbg\"><a href='");
/* 2992 */                               out.print(vmlink);
/* 2993 */                               out.write("' class='");
/* 2994 */                               out.print(class1);
/* 2995 */                               out.write(39);
/* 2996 */                               out.write(62);
/* 2997 */                               out.write(32);
/* 2998 */                               out.print(childDetails[1]);
/* 2999 */                               out.write("</a>&nbsp;\n\t\t\t\t");
/*      */                               
/* 3001 */                               if (((EnterpriseUtil.isAdminServer()) || (request.isUserInRole("ENTERPRISEADMIN"))) && (
/* 3002 */                                 (Integer.parseInt(resourceid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) || (request.getRemoteUser().equals("systemadmin_enterprise"))))
/*      */                               {
/* 3004 */                                 String vmlink2 = null;
/*      */                                 
/* 3006 */                                 vmlink2 = "/showresource.do?resourceid=" + childDetails[0] + "&type=" + resourcename + "&moname=" + URLEncoder.encode(childDetails[1]) + "&method=showdetails&resourcename=" + URLEncoder.encode(childDetails[1]) + "&aam_jump=true&useHTTP=true";
/*      */                                 
/*      */ 
/*      */ 
/*      */ 
/* 3011 */                                 out.write("\n\t\t\t\t\t<a target=\"mas_window\" href=\"");
/* 3012 */                                 out.print(vmlink2);
/* 3013 */                                 out.write("\">\n  \t        \t\t\t\t<img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/>\n  \t        \t\t\t</a>\n\t\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/*      */ 
/* 3018 */                               out.write("\n\n\t\t\t\t&nbsp;</td>\n\t\t\t\t");
/*      */                               
/* 3020 */                               if (resourcename.equals("XenServerHost"))
/*      */                               {
/*      */ 
/* 3023 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 3025 */                                 SetTag _jspx_th_c_005fset_005f2 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3026 */                                 _jspx_th_c_005fset_005f2.setPageContext(_jspx_page_context);
/* 3027 */                                 _jspx_th_c_005fset_005f2.setParent(null);
/*      */                                 
/* 3029 */                                 _jspx_th_c_005fset_005f2.setVar("key");
/* 3030 */                                 int _jspx_eval_c_005fset_005f2 = _jspx_th_c_005fset_005f2.doStartTag();
/* 3031 */                                 if (_jspx_eval_c_005fset_005f2 != 0) {
/* 3032 */                                   if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3033 */                                     out = _jspx_page_context.pushBody();
/* 3034 */                                     _jspx_th_c_005fset_005f2.setBodyContent((BodyContent)out);
/* 3035 */                                     _jspx_th_c_005fset_005f2.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3038 */                                     out.write(32);
/* 3039 */                                     out.print(childDetails[0] + "#" + "15500" + "#" + "MESSAGE");
/* 3040 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f2.doAfterBody();
/* 3041 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3044 */                                   if (_jspx_eval_c_005fset_005f2 != 1) {
/* 3045 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3048 */                                 if (_jspx_th_c_005fset_005f2.doEndTag() == 5) {
/* 3049 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2); return;
/*      */                                 }
/*      */                                 
/* 3052 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f2);
/* 3053 */                                 out.write("\n          \t\t\t<td  width=\"10%\" height=\"30\" align=\"center\" class=\"yellowbg\"><a href=\"javascript:void(0)\" ");
/* 3054 */                                 if (_jspx_meth_c_005fif_005f4(_jspx_page_context))
/*      */                                   return;
/* 3056 */                                 out.write(" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3057 */                                 out.print(childDetails[0]);
/* 3058 */                                 out.write("&period=0')\">");
/* 3059 */                                 out.print(getSeverityImageForMssqlAvailability(alert.getProperty(childDetails[0] + "#" + "15500")));
/* 3060 */                                 out.write("</a></td>");
/* 3061 */                                 out.write("\n\n\t\t\t\t ");
/*      */                                 
/* 3063 */                                 SetTag _jspx_th_c_005fset_005f3 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3064 */                                 _jspx_th_c_005fset_005f3.setPageContext(_jspx_page_context);
/* 3065 */                                 _jspx_th_c_005fset_005f3.setParent(null);
/*      */                                 
/* 3067 */                                 _jspx_th_c_005fset_005f3.setVar("key");
/* 3068 */                                 int _jspx_eval_c_005fset_005f3 = _jspx_th_c_005fset_005f3.doStartTag();
/* 3069 */                                 if (_jspx_eval_c_005fset_005f3 != 0) {
/* 3070 */                                   if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3071 */                                     out = _jspx_page_context.pushBody();
/* 3072 */                                     _jspx_th_c_005fset_005f3.setBodyContent((BodyContent)out);
/* 3073 */                                     _jspx_th_c_005fset_005f3.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3076 */                                     out.write(32);
/* 3077 */                                     out.print(childDetails[0] + "#" + "15501" + "#" + "MESSAGE");
/* 3078 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f3.doAfterBody();
/* 3079 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3082 */                                   if (_jspx_eval_c_005fset_005f3 != 1) {
/* 3083 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3086 */                                 if (_jspx_th_c_005fset_005f3.doEndTag() == 5) {
/* 3087 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3); return;
/*      */                                 }
/*      */                                 
/* 3090 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f3);
/* 3091 */                                 out.write("\n\t\t\t\t<td  width=\"10%\" height=\"30\" align=\"center\" class=\"yellowbg\"> <a href=\"javascript:void(0)\" ");
/* 3092 */                                 if (_jspx_meth_c_005fif_005f5(_jspx_page_context))
/*      */                                   return;
/* 3094 */                                 out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3095 */                                 out.print(childDetails[0]);
/* 3096 */                                 out.write("&attributeid=15501&alertconfigurl=");
/* 3097 */                                 out.print(URLEncoder.encode(thresholdurl));
/* 3098 */                                 out.write("')\">");
/* 3099 */                                 out.print(getSeverityImageForHealth(alert.getProperty(childDetails[0] + "#" + "15501")));
/* 3100 */                                 out.write("</a></td>");
/* 3101 */                                 out.write("\n\n\t\t\t\t");
/*      */                                 
/* 3103 */                                 severity = alert.getProperty(childDetails[0] + "#" + "15509");
/* 3104 */                                 bcolor = null;
/* 3105 */                                 if (severity == null)
/*      */                                 {
/* 3107 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3109 */                                 else if (severity.equals("5"))
/*      */                                 {
/* 3111 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3113 */                                 else if (severity.equals("1"))
/*      */                                 {
/* 3115 */                                   bcolor = "mon_redbg";
/*      */                                 }
/* 3117 */                                 else if (severity.equals("4"))
/*      */                                 {
/* 3119 */                                   bcolor = "mon_orangebg";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3123 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/*      */                                 
/* 3126 */                                 out.write("\n\t\t\t\t<td width=\"14%\" height=\"30\" align=\"center\" class=\"yellowbg\">\n\t\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3127 */                                 out.print(childDetails[0]);
/* 3128 */                                 out.write("&attributeid=15509&period=0')\" class=\"mon_bg_bdr\">\n\t\t\t\t");
/*      */                                 
/* 3130 */                                 if ("-".equals(cpuUtilz))
/*      */                                 {
/*      */ 
/* 3133 */                                   out.write("\n\t\t\t\t<div style=\"width:0%\" class=\"");
/* 3134 */                                   out.print(bcolor);
/* 3135 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3136 */                                   out.print(cpuUtilz);
/* 3137 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3142 */                                   out.write("\n\t\t\t\t<div style=\"width:");
/* 3143 */                                   out.print(cpuUtilz);
/* 3144 */                                   out.write("%\" class=\"");
/* 3145 */                                   out.print(bcolor);
/* 3146 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3147 */                                   out.print(cpuUtilz);
/* 3148 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                                 }
/*      */                                 
/* 3151 */                                 out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\n\t\t\t\t");
/*      */                                 
/* 3153 */                                 severity = alert.getProperty(childDetails[0] + "#" + "15513");
/* 3154 */                                 bcolor = null;
/* 3155 */                                 if (severity == null)
/*      */                                 {
/* 3157 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3159 */                                 else if (severity.equals("5"))
/*      */                                 {
/* 3161 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3163 */                                 else if (severity.equals("1"))
/*      */                                 {
/* 3165 */                                   bcolor = "mon_redbg";
/*      */                                 }
/* 3167 */                                 else if (severity.equals("4"))
/*      */                                 {
/* 3169 */                                   bcolor = "mon_orangebg";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3173 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/*      */                                 
/* 3176 */                                 out.write("\n\t\t\t\t<td width=\"14%\" height=\"30\" align=\"center\" class=\"yellowbg\">\n\t\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3177 */                                 out.print(childDetails[0]);
/* 3178 */                                 out.write("&attributeid=15513&period=0')\" class=\"mon_bg_bdr\">\n\t\t\t\t");
/*      */                                 
/* 3180 */                                 if ("-".equals(memUtilz))
/*      */                                 {
/*      */ 
/* 3183 */                                   out.write("\n\t\t\t\t<div style=\"width:0%\" class=\"");
/* 3184 */                                   out.print(bcolor);
/* 3185 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3186 */                                   out.print(memUtilz);
/* 3187 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                                 }
/*      */                                 else {
/* 3190 */                                   out.write("\n\t\t\t\t<div style=\"width:");
/* 3191 */                                   out.print(memUtilz);
/* 3192 */                                   out.write("%\" class=\"");
/* 3193 */                                   out.print(bcolor);
/* 3194 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3195 */                                   out.print(memUtilz);
/* 3196 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                                 }
/* 3198 */                                 out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\n\n\t\t\t\t");
/*      */ 
/*      */                               }
/*      */                               else
/*      */                               {
/*      */ 
/* 3204 */                                 out.write("\n\t\t\t\t");
/*      */                                 
/* 3206 */                                 SetTag _jspx_th_c_005fset_005f4 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3207 */                                 _jspx_th_c_005fset_005f4.setPageContext(_jspx_page_context);
/* 3208 */                                 _jspx_th_c_005fset_005f4.setParent(null);
/*      */                                 
/* 3210 */                                 _jspx_th_c_005fset_005f4.setVar("key");
/* 3211 */                                 int _jspx_eval_c_005fset_005f4 = _jspx_th_c_005fset_005f4.doStartTag();
/* 3212 */                                 if (_jspx_eval_c_005fset_005f4 != 0) {
/* 3213 */                                   if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3214 */                                     out = _jspx_page_context.pushBody();
/* 3215 */                                     _jspx_th_c_005fset_005f4.setBodyContent((BodyContent)out);
/* 3216 */                                     _jspx_th_c_005fset_005f4.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3219 */                                     out.write(32);
/* 3220 */                                     out.print(childDetails[0] + "#" + "7600" + "#" + "MESSAGE");
/* 3221 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f4.doAfterBody();
/* 3222 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3225 */                                   if (_jspx_eval_c_005fset_005f4 != 1) {
/* 3226 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3229 */                                 if (_jspx_th_c_005fset_005f4.doEndTag() == 5) {
/* 3230 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4); return;
/*      */                                 }
/*      */                                 
/* 3233 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f4);
/* 3234 */                                 out.write("\n          \t\t\t<td  width=\"10%\" height=\"30\" align=\"center\" class=\"yellowbg\"><a href=\"javascript:void(0)\" ");
/* 3235 */                                 if (_jspx_meth_c_005fif_005f6(_jspx_page_context))
/*      */                                   return;
/* 3237 */                                 out.write(" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3238 */                                 out.print(childDetails[0]);
/* 3239 */                                 out.write("&period=0')\">");
/* 3240 */                                 out.print(getSeverityImageForMssqlAvailability(alert.getProperty(childDetails[0] + "#" + "7600")));
/* 3241 */                                 out.write("</a></td>\n\n\t\t\t\t ");
/*      */                                 
/* 3243 */                                 SetTag _jspx_th_c_005fset_005f5 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.get(SetTag.class);
/* 3244 */                                 _jspx_th_c_005fset_005f5.setPageContext(_jspx_page_context);
/* 3245 */                                 _jspx_th_c_005fset_005f5.setParent(null);
/*      */                                 
/* 3247 */                                 _jspx_th_c_005fset_005f5.setVar("key");
/* 3248 */                                 int _jspx_eval_c_005fset_005f5 = _jspx_th_c_005fset_005f5.doStartTag();
/* 3249 */                                 if (_jspx_eval_c_005fset_005f5 != 0) {
/* 3250 */                                   if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3251 */                                     out = _jspx_page_context.pushBody();
/* 3252 */                                     _jspx_th_c_005fset_005f5.setBodyContent((BodyContent)out);
/* 3253 */                                     _jspx_th_c_005fset_005f5.doInitBody();
/*      */                                   }
/*      */                                   for (;;) {
/* 3256 */                                     out.write(32);
/* 3257 */                                     out.print(childDetails[0] + "#" + "7601" + "#" + "MESSAGE");
/* 3258 */                                     int evalDoAfterBody = _jspx_th_c_005fset_005f5.doAfterBody();
/* 3259 */                                     if (evalDoAfterBody != 2)
/*      */                                       break;
/*      */                                   }
/* 3262 */                                   if (_jspx_eval_c_005fset_005f5 != 1) {
/* 3263 */                                     out = _jspx_page_context.popBody();
/*      */                                   }
/*      */                                 }
/* 3266 */                                 if (_jspx_th_c_005fset_005f5.doEndTag() == 5) {
/* 3267 */                                   this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5); return;
/*      */                                 }
/*      */                                 
/* 3270 */                                 this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar.reuse(_jspx_th_c_005fset_005f5);
/* 3271 */                                 out.write("\n\t\t\t\t<td  width=\"10%\" height=\"30\" align=\"center\" class=\"yellowbg\"> <a href=\"javascript:void(0)\" ");
/* 3272 */                                 if (_jspx_meth_c_005fif_005f7(_jspx_page_context))
/*      */                                   return;
/* 3274 */                                 out.write(" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3275 */                                 out.print(childDetails[0]);
/* 3276 */                                 out.write("&attributeid=");
/* 3277 */                                 out.print("7601");
/* 3278 */                                 out.write("&alertconfigurl=");
/* 3279 */                                 out.print(URLEncoder.encode(thresholdurl));
/* 3280 */                                 out.write("')\">");
/* 3281 */                                 out.print(getSeverityImageForHealth(alert.getProperty(childDetails[0] + "#" + "7601")));
/* 3282 */                                 out.write("</a></td>\n\n\t\t\t\t");
/*      */                                 
/* 3284 */                                 severity = alert.getProperty(childDetails[0] + "#" + "7624");
/* 3285 */                                 bcolor = null;
/* 3286 */                                 if (severity == null)
/*      */                                 {
/* 3288 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3290 */                                 else if (severity.equals("5"))
/*      */                                 {
/* 3292 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3294 */                                 else if (severity.equals("1"))
/*      */                                 {
/* 3296 */                                   bcolor = "mon_redbg";
/*      */                                 }
/* 3298 */                                 else if (severity.equals("4"))
/*      */                                 {
/* 3300 */                                   bcolor = "mon_orangebg";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3304 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/*      */                                 
/* 3307 */                                 out.write("\n\t\t\t\t<td width=\"14%\" height=\"30\" align=\"center\" class=\"yellowbg\">\n\t\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3308 */                                 out.print(childDetails[0]);
/* 3309 */                                 out.write("&attributeid=7624&period=0')\" class=\"mon_bg_bdr\">\n\t\t\t\t");
/*      */                                 
/* 3311 */                                 if ("-".equals(cpuUtilz))
/*      */                                 {
/*      */ 
/* 3314 */                                   out.write("\n\t\t\t\t<div style=\"width:0%\" class=\"");
/* 3315 */                                   out.print(bcolor);
/* 3316 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3317 */                                   out.print(cpuUtilz);
/* 3318 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3323 */                                   out.write("\n\t\t\t\t<div style=\"width:");
/* 3324 */                                   out.print(cpuUtilz);
/* 3325 */                                   out.write("%\" class=\"");
/* 3326 */                                   out.print(bcolor);
/* 3327 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3328 */                                   out.print(cpuUtilz);
/* 3329 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                                 }
/*      */                                 
/* 3332 */                                 out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\n\t\t\t\t");
/*      */                                 
/* 3334 */                                 severity = alert.getProperty(childDetails[0] + "#" + "7626");
/* 3335 */                                 bcolor = null;
/* 3336 */                                 if (severity == null)
/*      */                                 {
/* 3338 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3340 */                                 else if (severity.equals("5"))
/*      */                                 {
/* 3342 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/* 3344 */                                 else if (severity.equals("1"))
/*      */                                 {
/* 3346 */                                   bcolor = "mon_redbg";
/*      */                                 }
/* 3348 */                                 else if (severity.equals("4"))
/*      */                                 {
/* 3350 */                                   bcolor = "mon_orangebg";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3354 */                                   bcolor = "mon_greenbg";
/*      */                                 }
/*      */                                 
/* 3357 */                                 out.write("\n\t\t\t\t<td width=\"14%\" height=\"30\" align=\"center\" class=\"yellowbg\">\n\t\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3358 */                                 out.print(childDetails[0]);
/* 3359 */                                 out.write("&attributeid=7626&period=0')\" class=\"mon_bg_bdr\">\n\t\t\t\t");
/*      */                                 
/* 3361 */                                 if ("-".equals(memUtilz))
/*      */                                 {
/*      */ 
/* 3364 */                                   out.write("\n\t\t\t\t<div style=\"width:0%\" class=\"");
/* 3365 */                                   out.print(bcolor);
/* 3366 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3367 */                                   out.print(memUtilz);
/* 3368 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                                 }
/*      */                                 else {
/* 3371 */                                   out.write("\n\t\t\t\t<div style=\"width:");
/* 3372 */                                   out.print(memUtilz);
/* 3373 */                                   out.write("%\" class=\"");
/* 3374 */                                   out.print(bcolor);
/* 3375 */                                   out.write("\">\n\t\t\t\t<span class=\"mon_txt\">&nbsp;");
/* 3376 */                                   out.print(memUtilz);
/* 3377 */                                   out.write("</span>\n\t\t\t\t</div>\n\t\t\t\t");
/*      */                                 }
/* 3379 */                                 out.write("\n\t\t\t\t</div>\n\t\t\t\t</td>\n\n\n\t\t\t\t<td width=\"12%\" height=\"30\" align=\"center\" class=\"yellowbg\">\n\t\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3380 */                                 out.print(childDetails[0]);
/* 3381 */                                 out.write("&attributeid=7627&period=0')\" onmouseover=\"this.style.cursor='pointer'\">");
/* 3382 */                                 out.print(diskUsage);
/* 3383 */                                 out.write("</div>\n\n\t\t\t\t</td>\n\n\t\t\t\t<td width=\"12%\" height=\"30\" align=\"center\" class=\"yellowbg\">\n\t\t\t\t<div onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3384 */                                 out.print(childDetails[0]);
/* 3385 */                                 out.write("&attributeid=7628&period=0')\" onmouseover=\"this.style.cursor='pointer'\">");
/* 3386 */                                 out.print(netUsage);
/* 3387 */                                 out.write("</div>\n\t\t\t\t</td>\n\t\t\t\t");
/*      */                               }
/*      */                               
/*      */ 
/* 3391 */                               out.write("\n\t\t\t\t</tr>\n\n\n\t");
/*      */                             }
/*      */                           }
/*      */                           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3399 */                           out.write("\n\t\t</table>\n\t\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t</td>\n\t\t\t\t\t\t\t\t</tr>\n\t");
/*      */                         }
/*      */                         
/*      */ 
/*      */ 
/* 3404 */                         out.write("\n</table>\n\t\t\t\t\t\t</td>\n\t\t\t\t\t</tr>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t\t<tr>\n    \t\t<td  valign=\"top\" align=\"center\" width=\"25%\"></td>\n    \t\t<td  valign=\"top\" align=\"center\" width=\"30%\"></td>\n    \t</tr>\n\t</table>\n\t</td>\n\t</tr>\n</table>\n</td>\n</tr>\n</table>\n");
/*      */ 
/*      */                       }
/*      */                       else
/*      */                       {
/* 3409 */                         boolean isCustomMonitor = (request.getParameter("isCustomMonitor") != null) && (request.getParameter("isCustomMonitor").equals("true"));
/*      */                         
/* 3411 */                         if ((com.adventnet.appmanager.util.Constants.isIt360) && (!isCustomMonitor)) {
/* 3412 */                           String tmpnet = request.getParameter("network");
/* 3413 */                           String servApplLink = "/adminAction.do?method=reloadHostDiscoveryForm&type=" + tmpnet + "&haid=" + request.getParameter("haid");
/* 3414 */                           String nwDevLink = "/showIT360Tile.do?TileName=IT360.QActions&service=OpManager&tabtoselect=6&url=/topo/AddNodeAction.do?requestid=addDevice&qAction_product=OpManager_Admin";
/*      */                           
/*      */ 
/* 3417 */                           out.write("\n\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\" height=\"30\" align=\"center\">\n       \t<tr>\n       \t\t<td class=\"emptyTableMsg\">\n                ");
/* 3418 */                           out.print(FormatUtil.getString("it360.nosearchresult.txt"));
/* 3419 */                           out.write("<br><br>\n    \t\t");
/* 3420 */                           out.print(FormatUtil.getString("it360.add.servappl.txt", new String[] { servApplLink }));
/* 3421 */                           out.write("\n    \t\t");
/* 3422 */                           out.print(FormatUtil.getString("it360.add.nwdevice.txt", new String[] { nwDevLink }));
/* 3423 */                           out.write("\n         \t</td>\n\t\t </tr>\n\t</table>\n\t");
/*      */                         } else {
/* 3425 */                           out.write("\n\n   \t<table width=\"99%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\"  style=\"background-color:#fff;\">\n\t   <tr class=\"bodytextbold\">\n\t\t<td width=\"1%\"height=\"28\" valign=\"center\"  class=\"columnheading\"></td></tr>\n<tr>\n  <td   ");
/* 3426 */                           if (group != null) out.println("colspan='6'"); else out.println("colspan='5'");
/* 3427 */                           out.write(" height=30  align=center><span class=\"bodytext\">");
/* 3428 */                           out.print(FormatUtil.getString("am.monitortab.nomonitors.text"));
/* 3429 */                           out.write(".\n  ");
/*      */                           
/* 3431 */                           String tmpnet = request.getParameter("network");
/* 3432 */                           if (tmpnet == null) {
/* 3433 */                             tmpnet = "SYSTEM:9999";
/*      */                           }
/* 3435 */                           if ((tmpnet.trim().equals("Custom-Application")) && (com.adventnet.appmanager.util.Constants.getCategorytype().equals("DATABASE")))
/*      */                           {
/* 3437 */                             tmpnet = "Script Monitor";
/*      */                           }
/* 3439 */                           else if (tmpnet.equals("JBOSS")) {
/* 3440 */                             tmpnet = "JBOSS-server";
/*      */                           }
/* 3442 */                           else if (tmpnet.equals("WTA")) {
/* 3443 */                             tmpnet = "WTA";
/*      */                           }
/* 3445 */                           else if (tmpnet.equals("DB2-server")) {
/* 3446 */                             tmpnet = "DB2-server";
/*      */                           }
/* 3448 */                           else if (tmpnet.equals("SYBASE-DB-server")) {
/* 3449 */                             tmpnet = "SYBASE-DB-server";
/*      */                           }
/* 3451 */                           else if (tmpnet.equals("MAIL-server")) {
/* 3452 */                             tmpnet = "MAIL-server";
/*      */                           }
/* 3454 */                           else if (tmpnet.equals("MX1.2-MX4J-RMI:1099")) {
/* 3455 */                             tmpnet = "JMX1.2-MX4J-RMI";
/*      */                           }
/* 3457 */                           else if (tmpnet.equals("SYS")) {
/* 3458 */                             tmpnet = "Windows";
/*      */                           }
/* 3460 */                           else if (tmpnet.equals("UrlMonitor")) {
/* 3461 */                             tmpnet = "UrlMonitor";
/*      */                           }
/* 3463 */                           else if (tmpnet.equals("WLS-Cluster")) {
/* 3464 */                             tmpnet = "WEBLOGIC-server";
/*      */                           }
/* 3466 */                           if (tmpnet != null)
/*      */                           {
/* 3468 */                             tmpnet = tmpnet.trim();
/*      */                           }
/* 3470 */                           String link = "/adminAction.do?method=reloadHostDiscoveryForm&type=" + tmpnet + "&haid=" + request.getParameter("haid");
/* 3471 */                           if (!EnterpriseUtil.isAdminServer())
/*      */                           {
/*      */ 
/* 3474 */                             out.write("\n    ");
/* 3475 */                             out.print(FormatUtil.getString("am.monitortab.all.message.text"));
/* 3476 */                             out.write(32);
/*      */                             
/* 3478 */                             AdminLink _jspx_th_am_005fadminlink_005f0 = (AdminLink)this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.get(AdminLink.class);
/* 3479 */                             _jspx_th_am_005fadminlink_005f0.setPageContext(_jspx_page_context);
/* 3480 */                             _jspx_th_am_005fadminlink_005f0.setParent(null);
/*      */                             
/* 3482 */                             _jspx_th_am_005fadminlink_005f0.setHref(link);
/*      */                             
/* 3484 */                             _jspx_th_am_005fadminlink_005f0.setEnableClass("staticlinks");
/* 3485 */                             int _jspx_eval_am_005fadminlink_005f0 = _jspx_th_am_005fadminlink_005f0.doStartTag();
/* 3486 */                             if (_jspx_eval_am_005fadminlink_005f0 != 0) {
/* 3487 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3488 */                                 out = _jspx_page_context.pushBody();
/* 3489 */                                 _jspx_th_am_005fadminlink_005f0.setBodyContent((BodyContent)out);
/* 3490 */                                 _jspx_th_am_005fadminlink_005f0.doInitBody();
/*      */                               }
/*      */                               for (;;) {
/* 3493 */                                 out.print(FormatUtil.getString("am.webclient.toolbar.newmonitorlink.text"));
/* 3494 */                                 int evalDoAfterBody = _jspx_th_am_005fadminlink_005f0.doAfterBody();
/* 3495 */                                 if (evalDoAfterBody != 2)
/*      */                                   break;
/*      */                               }
/* 3498 */                               if (_jspx_eval_am_005fadminlink_005f0 != 1) {
/* 3499 */                                 out = _jspx_page_context.popBody();
/*      */                               }
/*      */                             }
/* 3502 */                             if (_jspx_th_am_005fadminlink_005f0.doEndTag() == 5) {
/* 3503 */                               this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0); return;
/*      */                             }
/*      */                             
/* 3506 */                             this._005fjspx_005ftagPool_005fam_005fadminlink_0026_005fhref_005fenableClass.reuse(_jspx_th_am_005fadminlink_005f0);
/* 3507 */                             out.write(32);
/* 3508 */                             out.print(FormatUtil.getString("am.monitortab.all.add.text"));
/* 3509 */                             out.write(". </span></td>\n");
/*      */                           } else {
/* 3511 */                             out.write(10);
/* 3512 */                             out.write(9);
/* 3513 */                             out.print(FormatUtil.getString("am.monitortab.nomonitors.admin.text"));
/* 3514 */                             out.write("</span></td>\n");
/*      */                           }
/* 3516 */                           out.write("\n</tr>\n</table>\n</td>\n\t</tr>\n    \t<td  valign=\"top\" align=\"center\" width=\"25%\">\n\t\t</td>\n\t\t<td  valign=\"top\" align=\"center\" width=\"30%\">\t</td>\n\t</tr>\n\t</table>\n");
/* 3517 */                           if (list.size() <= 10)
/*      */                           {
/* 3519 */                             out.write("\n<tr>\n          <td class=\"AlarmInnerTopLeft\"/>\n          <td class=\"AlarmInnerTopBg\"/>\n          <td class=\"AlarmInnerTopRight\"/>\n        </tr>\n\t</table></td>\n      </tr>\n     </table>\n     </td>\n  </tr>\n</table>\n</td>\n\t\t\t<td style=\"width: 12px;\" class=\"AlarmboxedContent\">&nbsp;</td>\n\t\t\t</tr>\n<tr>\n\t\t\t<td class=\"AlarmCardMainBtmLeft\"/>\n\t\t\t<td class=\"AlarmCardMainBtmBg\"/>\n\t\t\t<td class=\"AlarmCardMainBtmRight\"/>\n\n\t\t\t</tr></table>\n");
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                       
/*      */ 
/* 3525 */                       out.write(32);
/* 3526 */                       out.write(10);
/*      */                     }
/*      */                     catch (Exception ee)
/*      */                     {
/* 3530 */                       ee.printStackTrace();
/*      */                     }
/*      */                     
/*      */ 
/*      */ 
/* 3535 */                     out.write(10);
/* 3536 */                     out.write(10);
/* 3537 */                     out.write(10);
/*      */                   }
/* 3539 */                 } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3540 */         out = _jspx_out;
/* 3541 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3542 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3543 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3546 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3552 */     PageContext pageContext = _jspx_page_context;
/* 3553 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3555 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3556 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3557 */     _jspx_th_c_005fif_005f0.setParent(null);
/*      */     
/* 3559 */     _jspx_th_c_005fif_005f0.setTest("${param.search=='true'}");
/* 3560 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3561 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3563 */         out.write("\n\t<input type=\"hidden\" name=\"searchword\" id=\"searchword\" value='");
/* 3564 */         if (_jspx_meth_c_005fout_005f0(_jspx_th_c_005fif_005f0, _jspx_page_context))
/* 3565 */           return true;
/* 3566 */         out.write(39);
/* 3567 */         out.write(62);
/* 3568 */         out.write(10);
/* 3569 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3570 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3574 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3575 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3576 */       return true;
/*      */     }
/* 3578 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3579 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_c_005fif_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3584 */     PageContext pageContext = _jspx_page_context;
/* 3585 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3587 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3588 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 3589 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_c_005fif_005f0);
/*      */     
/* 3591 */     _jspx_th_c_005fout_005f0.setValue("${param.query}");
/* 3592 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 3593 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 3594 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3595 */       return true;
/*      */     }
/* 3597 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 3598 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_am_005fhiddenparam_005f0(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3603 */     PageContext pageContext = _jspx_page_context;
/* 3604 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3606 */     HiddenParam _jspx_th_am_005fhiddenparam_005f0 = (HiddenParam)this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.get(HiddenParam.class);
/* 3607 */     _jspx_th_am_005fhiddenparam_005f0.setPageContext(_jspx_page_context);
/* 3608 */     _jspx_th_am_005fhiddenparam_005f0.setParent(null);
/*      */     
/* 3610 */     _jspx_th_am_005fhiddenparam_005f0.setName("selectedNetwork");
/* 3611 */     int _jspx_eval_am_005fhiddenparam_005f0 = _jspx_th_am_005fhiddenparam_005f0.doStartTag();
/* 3612 */     if (_jspx_th_am_005fhiddenparam_005f0.doEndTag() == 5) {
/* 3613 */       this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 3614 */       return true;
/*      */     }
/* 3616 */     this._005fjspx_005ftagPool_005fam_005fhiddenparam_0026_005fname_005fnobody.reuse(_jspx_th_am_005fhiddenparam_005f0);
/* 3617 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f2(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3622 */     PageContext pageContext = _jspx_page_context;
/* 3623 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3625 */     IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3626 */     _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 3627 */     _jspx_th_c_005fif_005f2.setParent(null);
/*      */     
/* 3629 */     _jspx_th_c_005fif_005f2.setTest("${alert[key]!=null}");
/* 3630 */     int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 3631 */     if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */       for (;;) {
/* 3633 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3634 */         if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f2, _jspx_page_context))
/* 3635 */           return true;
/* 3636 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3637 */         int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 3638 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3642 */     if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 3643 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3644 */       return true;
/*      */     }
/* 3646 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 3647 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3652 */     PageContext pageContext = _jspx_page_context;
/* 3653 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3655 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3656 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 3657 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f2);
/*      */     
/* 3659 */     _jspx_th_c_005fout_005f1.setValue("${alert[key]}");
/* 3660 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 3661 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 3662 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3663 */       return true;
/*      */     }
/* 3665 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 3666 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f3(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3671 */     PageContext pageContext = _jspx_page_context;
/* 3672 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3674 */     IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3675 */     _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 3676 */     _jspx_th_c_005fif_005f3.setParent(null);
/*      */     
/* 3678 */     _jspx_th_c_005fif_005f3.setTest("${alert[key]!=null}");
/* 3679 */     int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 3680 */     if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */       for (;;) {
/* 3682 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3683 */         if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f3, _jspx_page_context))
/* 3684 */           return true;
/* 3685 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3686 */         int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 3687 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3691 */     if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 3692 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3693 */       return true;
/*      */     }
/* 3695 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 3696 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3701 */     PageContext pageContext = _jspx_page_context;
/* 3702 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3704 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3705 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 3706 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f3);
/*      */     
/* 3708 */     _jspx_th_c_005fout_005f2.setValue("${alert[key]}");
/* 3709 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 3710 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 3711 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3712 */       return true;
/*      */     }
/* 3714 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 3715 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f4(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3720 */     PageContext pageContext = _jspx_page_context;
/* 3721 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3723 */     IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3724 */     _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 3725 */     _jspx_th_c_005fif_005f4.setParent(null);
/*      */     
/* 3727 */     _jspx_th_c_005fif_005f4.setTest("${alert[key]!=null}");
/* 3728 */     int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 3729 */     if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */       for (;;) {
/* 3731 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3732 */         if (_jspx_meth_c_005fout_005f3(_jspx_th_c_005fif_005f4, _jspx_page_context))
/* 3733 */           return true;
/* 3734 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3735 */         int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 3736 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3740 */     if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 3741 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3742 */       return true;
/*      */     }
/* 3744 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 3745 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f3(JspTag _jspx_th_c_005fif_005f4, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3750 */     PageContext pageContext = _jspx_page_context;
/* 3751 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3753 */     OutTag _jspx_th_c_005fout_005f3 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3754 */     _jspx_th_c_005fout_005f3.setPageContext(_jspx_page_context);
/* 3755 */     _jspx_th_c_005fout_005f3.setParent((Tag)_jspx_th_c_005fif_005f4);
/*      */     
/* 3757 */     _jspx_th_c_005fout_005f3.setValue("${alert[key]}");
/* 3758 */     int _jspx_eval_c_005fout_005f3 = _jspx_th_c_005fout_005f3.doStartTag();
/* 3759 */     if (_jspx_th_c_005fout_005f3.doEndTag() == 5) {
/* 3760 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3761 */       return true;
/*      */     }
/* 3763 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f3);
/* 3764 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f5(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3769 */     PageContext pageContext = _jspx_page_context;
/* 3770 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3772 */     IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3773 */     _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 3774 */     _jspx_th_c_005fif_005f5.setParent(null);
/*      */     
/* 3776 */     _jspx_th_c_005fif_005f5.setTest("${alert[key]!=null}");
/* 3777 */     int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 3778 */     if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */       for (;;) {
/* 3780 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3781 */         if (_jspx_meth_c_005fout_005f4(_jspx_th_c_005fif_005f5, _jspx_page_context))
/* 3782 */           return true;
/* 3783 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3784 */         int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 3785 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3789 */     if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 3790 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3791 */       return true;
/*      */     }
/* 3793 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 3794 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f4(JspTag _jspx_th_c_005fif_005f5, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3799 */     PageContext pageContext = _jspx_page_context;
/* 3800 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3802 */     OutTag _jspx_th_c_005fout_005f4 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3803 */     _jspx_th_c_005fout_005f4.setPageContext(_jspx_page_context);
/* 3804 */     _jspx_th_c_005fout_005f4.setParent((Tag)_jspx_th_c_005fif_005f5);
/*      */     
/* 3806 */     _jspx_th_c_005fout_005f4.setValue("${alert[key]}");
/* 3807 */     int _jspx_eval_c_005fout_005f4 = _jspx_th_c_005fout_005f4.doStartTag();
/* 3808 */     if (_jspx_th_c_005fout_005f4.doEndTag() == 5) {
/* 3809 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3810 */       return true;
/*      */     }
/* 3812 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f4);
/* 3813 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f6(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3818 */     PageContext pageContext = _jspx_page_context;
/* 3819 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3821 */     IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3822 */     _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 3823 */     _jspx_th_c_005fif_005f6.setParent(null);
/*      */     
/* 3825 */     _jspx_th_c_005fif_005f6.setTest("${alert[key]!=null}");
/* 3826 */     int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 3827 */     if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */       for (;;) {
/* 3829 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3830 */         if (_jspx_meth_c_005fout_005f5(_jspx_th_c_005fif_005f6, _jspx_page_context))
/* 3831 */           return true;
/* 3832 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3833 */         int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 3834 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3838 */     if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 3839 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3840 */       return true;
/*      */     }
/* 3842 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 3843 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f5(JspTag _jspx_th_c_005fif_005f6, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3848 */     PageContext pageContext = _jspx_page_context;
/* 3849 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3851 */     OutTag _jspx_th_c_005fout_005f5 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3852 */     _jspx_th_c_005fout_005f5.setPageContext(_jspx_page_context);
/* 3853 */     _jspx_th_c_005fout_005f5.setParent((Tag)_jspx_th_c_005fif_005f6);
/*      */     
/* 3855 */     _jspx_th_c_005fout_005f5.setValue("${alert[key]}");
/* 3856 */     int _jspx_eval_c_005fout_005f5 = _jspx_th_c_005fout_005f5.doStartTag();
/* 3857 */     if (_jspx_th_c_005fout_005f5.doEndTag() == 5) {
/* 3858 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3859 */       return true;
/*      */     }
/* 3861 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f5);
/* 3862 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f7(PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3867 */     PageContext pageContext = _jspx_page_context;
/* 3868 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3870 */     IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3871 */     _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 3872 */     _jspx_th_c_005fif_005f7.setParent(null);
/*      */     
/* 3874 */     _jspx_th_c_005fif_005f7.setTest("${alert[key]!=null}");
/* 3875 */     int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 3876 */     if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */       for (;;) {
/* 3878 */         out.write("onmouseover=\"ddrivetip(this,event,'");
/* 3879 */         if (_jspx_meth_c_005fout_005f6(_jspx_th_c_005fif_005f7, _jspx_page_context))
/* 3880 */           return true;
/* 3881 */         out.write("',null,true,'#000000')\" onmouseout=\"hideddrivetip()\"");
/* 3882 */         int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 3883 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3887 */     if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 3888 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3889 */       return true;
/*      */     }
/* 3891 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 3892 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f6(JspTag _jspx_th_c_005fif_005f7, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3897 */     PageContext pageContext = _jspx_page_context;
/* 3898 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3900 */     OutTag _jspx_th_c_005fout_005f6 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 3901 */     _jspx_th_c_005fout_005f6.setPageContext(_jspx_page_context);
/* 3902 */     _jspx_th_c_005fout_005f6.setParent((Tag)_jspx_th_c_005fif_005f7);
/*      */     
/* 3904 */     _jspx_th_c_005fout_005f6.setValue("${alert[key]}");
/* 3905 */     int _jspx_eval_c_005fout_005f6 = _jspx_th_c_005fout_005f6.doStartTag();
/* 3906 */     if (_jspx_th_c_005fout_005f6.doEndTag() == 5) {
/* 3907 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3908 */       return true;
/*      */     }
/* 3910 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f6);
/* 3911 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\vmlistview_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */