/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.logging.AMLog;
/*      */ import com.adventnet.appmanager.server.framework.comm.CommDBUtil;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.awolf.tags.MultiPieChart;
/*      */ import com.adventnet.utilities.stringutils.StrUtil;
/*      */ import com.manageengine.it360.util.SLAUtil;
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
/*      */ import javax.servlet.http.HttpServletResponse;
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
/*      */ import org.apache.struts.taglib.html.FormTag;
/*      */ import org.apache.struts.taglib.html.OptionTag;
/*      */ import org.apache.struts.taglib.html.SelectTag;
/*      */ import org.apache.struts.taglib.html.TextTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ 
/*      */ public final class BussinessApplication_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   56 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   59 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   60 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   61 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   68 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   73 */     ArrayList list = null;
/*   74 */     StringBuffer sbf = new StringBuffer();
/*   75 */     ManagedApplication mo = new ManagedApplication();
/*   76 */     if (distinct)
/*      */     {
/*   78 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   82 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   85 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   87 */       ArrayList row = (ArrayList)list.get(i);
/*   88 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   89 */       if (distinct) {
/*   90 */         sbf.append(row.get(0));
/*      */       } else
/*   92 */         sbf.append(row.get(1));
/*   93 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   96 */     return sbf.toString(); }
/*      */   
/*   98 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  101 */     if (severity == null)
/*      */     {
/*  103 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  105 */     if (severity.equals("5"))
/*      */     {
/*  107 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  109 */     if (severity.equals("1"))
/*      */     {
/*  111 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  116 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  123 */     if (severity == null)
/*      */     {
/*  125 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  127 */     if (severity.equals("1"))
/*      */     {
/*  129 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  131 */     if (severity.equals("4"))
/*      */     {
/*  133 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  135 */     if (severity.equals("5"))
/*      */     {
/*  137 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  142 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  148 */     if (severity == null)
/*      */     {
/*  150 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  152 */     if (severity.equals("5"))
/*      */     {
/*  154 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  156 */     if (severity.equals("1"))
/*      */     {
/*  158 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  162 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  168 */     if (severity == null)
/*      */     {
/*  170 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  172 */     if (severity.equals("1"))
/*      */     {
/*  174 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  176 */     if (severity.equals("4"))
/*      */     {
/*  178 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  180 */     if (severity.equals("5"))
/*      */     {
/*  182 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  186 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  192 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  198 */     if (severity == 5)
/*      */     {
/*  200 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  202 */     if (severity == 1)
/*      */     {
/*  204 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  209 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  215 */     if (severity == null)
/*      */     {
/*  217 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  219 */     if (severity.equals("5"))
/*      */     {
/*  221 */       if (isAvailability) {
/*  222 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  225 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  228 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  230 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  232 */     if (severity.equals("1"))
/*      */     {
/*  234 */       if (isAvailability) {
/*  235 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  238 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  245 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  252 */     if (severity == null)
/*      */     {
/*  254 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  256 */     if (severity.equals("5"))
/*      */     {
/*  258 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  260 */     if (severity.equals("4"))
/*      */     {
/*  262 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  264 */     if (severity.equals("1"))
/*      */     {
/*  266 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  271 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  277 */     if (severity == null)
/*      */     {
/*  279 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  281 */     if (severity.equals("5"))
/*      */     {
/*  283 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  285 */     if (severity.equals("4"))
/*      */     {
/*  287 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  289 */     if (severity.equals("1"))
/*      */     {
/*  291 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  296 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  303 */     if (severity == null)
/*      */     {
/*  305 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  307 */     if (severity.equals("5"))
/*      */     {
/*  309 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  311 */     if (severity.equals("4"))
/*      */     {
/*  313 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  315 */     if (severity.equals("1"))
/*      */     {
/*  317 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  322 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  330 */     StringBuffer out = new StringBuffer();
/*  331 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  332 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  333 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  334 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  335 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  336 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  337 */     out.append("</tr>");
/*  338 */     out.append("</form></table>");
/*  339 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  346 */     if (val == null)
/*      */     {
/*  348 */       return "-";
/*      */     }
/*      */     
/*  351 */     String ret = FormatUtil.formatNumber(val);
/*  352 */     String troubleshootlink = com.adventnet.appmanager.util.OEMUtil.getOEMString("company.troubleshoot.link");
/*  353 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  356 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  360 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  368 */     StringBuffer out = new StringBuffer();
/*  369 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  370 */     out.append("<tr>");
/*  371 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  373 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  375 */     out.append("</tr>");
/*  376 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  380 */       if (j % 2 == 0)
/*      */       {
/*  382 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  386 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  389 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  391 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  394 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  398 */       out.append("</tr>");
/*      */     }
/*  400 */     out.append("</table>");
/*  401 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  402 */     out.append("<tr>");
/*  403 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  404 */     out.append("</tr>");
/*  405 */     out.append("</table>");
/*  406 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  412 */     StringBuffer out = new StringBuffer();
/*  413 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  414 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  415 */     out.append("<tr>");
/*  416 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  417 */     out.append("<tr>");
/*  418 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  419 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  420 */     out.append("</tr>");
/*  421 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  424 */       out.append("<tr>");
/*  425 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  426 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  427 */       out.append("</tr>");
/*      */     }
/*      */     
/*  430 */     out.append("</table>");
/*  431 */     out.append("</table>");
/*  432 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  437 */     if (severity.equals("0"))
/*      */     {
/*  439 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  443 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  450 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  463 */     StringBuffer out = new StringBuffer();
/*  464 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  465 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  467 */       out.append("<tr>");
/*  468 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  469 */       out.append("</tr>");
/*      */       
/*      */ 
/*  472 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  474 */         String borderclass = "";
/*      */         
/*      */ 
/*  477 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  479 */         out.append("<tr>");
/*      */         
/*  481 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  482 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  483 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  489 */     out.append("</table><br>");
/*  490 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  491 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  493 */       List sLinks = secondLevelOfLinks[0];
/*  494 */       List sText = secondLevelOfLinks[1];
/*  495 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  498 */         out.append("<tr>");
/*  499 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  500 */         out.append("</tr>");
/*  501 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  503 */           String borderclass = "";
/*      */           
/*      */ 
/*  506 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  508 */           out.append("<tr>");
/*      */           
/*  510 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  511 */           if (sLinks.get(i).toString().length() == 0) {
/*  512 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  515 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  517 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  521 */     out.append("</table>");
/*  522 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  529 */     StringBuffer out = new StringBuffer();
/*  530 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  531 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  533 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  535 */         out.append("<tr>");
/*  536 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  537 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  541 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  543 */           String borderclass = "";
/*      */           
/*      */ 
/*  546 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  548 */           out.append("<tr>");
/*      */           
/*  550 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  551 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  552 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  555 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  558 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  563 */     out.append("</table><br>");
/*  564 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  565 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  567 */       List sLinks = secondLevelOfLinks[0];
/*  568 */       List sText = secondLevelOfLinks[1];
/*  569 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  572 */         out.append("<tr>");
/*  573 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  574 */         out.append("</tr>");
/*  575 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  577 */           String borderclass = "";
/*      */           
/*      */ 
/*  580 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  582 */           out.append("<tr>");
/*      */           
/*  584 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  585 */           if (sLinks.get(i).toString().length() == 0) {
/*  586 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  589 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  591 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  595 */     out.append("</table>");
/*  596 */     return out.toString();
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
/*  609 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  612 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  624 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  627 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  630 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  638 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  643 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  648 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  653 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  658 */     if (val != null)
/*      */     {
/*  660 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  664 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  669 */     if (val == null) {
/*  670 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  674 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  679 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  685 */     if (val != null)
/*      */     {
/*  687 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  691 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  697 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  702 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  706 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  711 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  716 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  721 */     String hostaddress = "";
/*  722 */     String ip = request.getHeader("x-forwarded-for");
/*  723 */     if (ip == null)
/*  724 */       ip = request.getRemoteAddr();
/*  725 */     InetAddress add = null;
/*  726 */     if (ip.equals("127.0.0.1")) {
/*  727 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  731 */       add = InetAddress.getByName(ip);
/*      */     }
/*  733 */     hostaddress = add.getHostName();
/*  734 */     if (hostaddress.indexOf('.') != -1) {
/*  735 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  736 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  740 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  745 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  751 */     if (severity == null)
/*      */     {
/*  753 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  755 */     if (severity.equals("5"))
/*      */     {
/*  757 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  759 */     if (severity.equals("1"))
/*      */     {
/*  761 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  766 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  771 */     ResultSet set = null;
/*  772 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  773 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  775 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  776 */       if (set.next()) { String str1;
/*  777 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  778 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  781 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  786 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  789 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  791 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  795 */     StringBuffer rca = new StringBuffer();
/*  796 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  797 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  800 */     int rcalength = key.length();
/*  801 */     String split = "6. ";
/*  802 */     int splitPresent = key.indexOf(split);
/*  803 */     String div1 = "";String div2 = "";
/*  804 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  806 */       if (rcalength > 180) {
/*  807 */         rca.append("<span class=\"rca-critical-text\">");
/*  808 */         getRCATrimmedText(key, rca);
/*  809 */         rca.append("</span>");
/*      */       } else {
/*  811 */         rca.append("<span class=\"rca-critical-text\">");
/*  812 */         rca.append(key);
/*  813 */         rca.append("</span>");
/*      */       }
/*  815 */       return rca.toString();
/*      */     }
/*  817 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  818 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  819 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  820 */     String rcaMesg = StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  821 */     getRCATrimmedText(div1, rca);
/*  822 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  825 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  826 */     rcaMesg = StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  827 */     getRCATrimmedText(div2, rca);
/*  828 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  830 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  835 */     String[] st = msg.split("<br>");
/*  836 */     for (int i = 0; i < st.length; i++) {
/*  837 */       String s = st[i];
/*  838 */       if (s.length() > 180) {
/*  839 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  841 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  845 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  846 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  848 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  852 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  853 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  854 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  857 */       if (key == null) {
/*  858 */         return ret;
/*      */       }
/*      */       
/*  861 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  862 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  865 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  866 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  867 */       set = AMConnectionPool.executeQueryStmt(query);
/*  868 */       if (set.next())
/*      */       {
/*  870 */         String helpLink = set.getString("LINK");
/*  871 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  874 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  880 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  899 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  890 */         if (set != null) {
/*  891 */           AMConnectionPool.closeStatement(set);
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
/*  905 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
/*  906 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  908 */       String entityStr = (String)keys.nextElement();
/*  909 */       String mmessage = temp.getProperty(entityStr);
/*  910 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  911 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  913 */     return temp;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  919 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  920 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  922 */       String entityStr = (String)keys.nextElement();
/*  923 */       String mmessage = temp.getProperty(entityStr);
/*  924 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  925 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  927 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  932 */     AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  942 */     String des = new String();
/*  943 */     while (str.indexOf(find) != -1) {
/*  944 */       des = des + str.substring(0, str.indexOf(find));
/*  945 */       des = des + replace;
/*  946 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  948 */     des = des + str;
/*  949 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  956 */       if (alert == null)
/*      */       {
/*  958 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  960 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  962 */         return "&nbsp;";
/*      */       }
/*      */       
/*  965 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  967 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  970 */       int rcalength = test.length();
/*  971 */       if (rcalength < 300)
/*      */       {
/*  973 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  977 */       StringBuffer out = new StringBuffer();
/*  978 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  979 */       out.append(StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  980 */       out.append("</div>");
/*  981 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  982 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  983 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  988 */       ex.printStackTrace();
/*      */     }
/*  990 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  996 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1001 */     ArrayList attribIDs = new ArrayList();
/* 1002 */     ArrayList resIDs = new ArrayList();
/* 1003 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1005 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1007 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1009 */       String resourceid = "";
/* 1010 */       String resourceType = "";
/* 1011 */       if (type == 2) {
/* 1012 */         resourceid = (String)row.get(0);
/* 1013 */         resourceType = (String)row.get(3);
/*      */       }
/* 1015 */       else if (type == 3) {
/* 1016 */         resourceid = (String)row.get(0);
/* 1017 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1020 */         resourceid = (String)row.get(6);
/* 1021 */         resourceType = (String)row.get(7);
/*      */       }
/* 1023 */       resIDs.add(resourceid);
/* 1024 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1025 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1027 */       String healthentity = null;
/* 1028 */       String availentity = null;
/* 1029 */       if (healthid != null) {
/* 1030 */         healthentity = resourceid + "_" + healthid;
/* 1031 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1034 */       if (availid != null) {
/* 1035 */         availentity = resourceid + "_" + availid;
/* 1036 */         entitylist.add(availentity);
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
/* 1050 */     Properties alert = getStatus(entitylist);
/* 1051 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1056 */     int size = monitorList.size();
/*      */     
/* 1058 */     String[] severity = new String[size];
/*      */     
/* 1060 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1062 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1063 */       String resourceName1 = (String)row1.get(7);
/* 1064 */       String resourceid1 = (String)row1.get(6);
/* 1065 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1066 */       if (severity[j] == null)
/*      */       {
/* 1068 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1072 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1074 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1076 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1079 */         if (sev > 0) {
/* 1080 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1081 */           monitorList.set(k, monitorList.get(j));
/* 1082 */           monitorList.set(j, t);
/* 1083 */           String temp = severity[k];
/* 1084 */           severity[k] = severity[j];
/* 1085 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1091 */     int z = 0;
/* 1092 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1095 */       int i = 0;
/* 1096 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1099 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1103 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1107 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1109 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1112 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1116 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1119 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1120 */       String resourceName1 = (String)row1.get(7);
/* 1121 */       String resourceid1 = (String)row1.get(6);
/* 1122 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1123 */       if (hseverity[j] == null)
/*      */       {
/* 1125 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1130 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1132 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1135 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1138 */         if (hsev > 0) {
/* 1139 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1140 */           monitorList.set(k, monitorList.get(j));
/* 1141 */           monitorList.set(j, t);
/* 1142 */           String temp1 = hseverity[k];
/* 1143 */           hseverity[k] = hseverity[j];
/* 1144 */           hseverity[j] = temp1;
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
/* 1156 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1157 */     boolean forInventory = false;
/* 1158 */     String trdisplay = "none";
/* 1159 */     String plusstyle = "inline";
/* 1160 */     String minusstyle = "none";
/* 1161 */     String haidTopLevel = "";
/* 1162 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1164 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1166 */         haidTopLevel = request.getParameter("haid");
/* 1167 */         forInventory = true;
/* 1168 */         trdisplay = "table-row;";
/* 1169 */         plusstyle = "none";
/* 1170 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1177 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1180 */     ArrayList listtoreturn = new ArrayList();
/* 1181 */     StringBuffer toreturn = new StringBuffer();
/* 1182 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1183 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1184 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1186 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1188 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1189 */       String childresid = (String)singlerow.get(0);
/* 1190 */       String childresname = (String)singlerow.get(1);
/* 1191 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1192 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1193 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1194 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1195 */       String unmanagestatus = (String)singlerow.get(5);
/* 1196 */       String actionstatus = (String)singlerow.get(6);
/* 1197 */       String linkclass = "monitorgp-links";
/* 1198 */       String titleforres = childresname;
/* 1199 */       String titilechildresname = childresname;
/* 1200 */       String childimg = "/images/trcont.png";
/* 1201 */       String flag = "enable";
/* 1202 */       String dcstarted = (String)singlerow.get(8);
/* 1203 */       String configMonitor = "";
/* 1204 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1205 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1207 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1209 */       if (singlerow.get(7) != null)
/*      */       {
/* 1211 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1213 */       String haiGroupType = "0";
/* 1214 */       if ("HAI".equals(childtype))
/*      */       {
/* 1216 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1218 */       childimg = "/images/trend.png";
/* 1219 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1220 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1221 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1223 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1225 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1227 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1228 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1231 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1233 */         linkclass = "disabledtext";
/* 1234 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1236 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1237 */       String availmouseover = "";
/* 1238 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1240 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1242 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1243 */       String healthmouseover = "";
/* 1244 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1246 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1249 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1250 */       int spacing = 0;
/* 1251 */       if (level >= 1)
/*      */       {
/* 1253 */         spacing = 40 * level;
/*      */       }
/* 1255 */       if (childtype.equals("HAI"))
/*      */       {
/* 1257 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1258 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1259 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1261 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1262 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1263 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1264 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1265 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1266 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1267 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1268 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1269 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1270 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1271 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1273 */         if (!forInventory)
/*      */         {
/* 1275 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1278 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1280 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1282 */           actions = editlink + actions;
/*      */         }
/* 1284 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1286 */           actions = actions + associatelink;
/*      */         }
/* 1288 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1289 */         String arrowimg = "";
/* 1290 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1292 */           actions = "";
/* 1293 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1294 */           checkbox = "";
/* 1295 */           childresname = childresname + "_" + CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1297 */         if (isIt360)
/*      */         {
/* 1299 */           actionimg = "";
/* 1300 */           actions = "";
/* 1301 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1302 */           checkbox = "";
/*      */         }
/*      */         
/* 1305 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1307 */           actions = "";
/*      */         }
/* 1309 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1311 */           checkbox = "";
/*      */         }
/*      */         
/* 1314 */         String resourcelink = "";
/*      */         
/* 1316 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1318 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1322 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1325 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1326 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1327 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1328 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1331 */         if (!isIt360)
/*      */         {
/* 1333 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1337 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1340 */         toreturn.append("</tr>");
/* 1341 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1343 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1344 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1348 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1349 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1352 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1356 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1358 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1359 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1360 */             toreturn.append(assocMessage);
/* 1361 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1363 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1364 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1370 */         String resourcelink = null;
/* 1371 */         boolean hideEditLink = false;
/* 1372 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1374 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1375 */           hideEditLink = true;
/* 1376 */           if (isIt360)
/*      */           {
/* 1378 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1382 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1384 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1386 */           hideEditLink = true;
/* 1387 */           String link2 = URLEncoder.encode((String)site24x7List.get(childresid));
/* 1388 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1393 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1396 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1397 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1398 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1399 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1400 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1401 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1402 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1403 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1404 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1405 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1406 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1407 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1408 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1410 */         if (hideEditLink)
/*      */         {
/* 1412 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1414 */         if (!forInventory)
/*      */         {
/* 1416 */           removefromgroup = "";
/*      */         }
/* 1418 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1419 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1420 */           actions = actions + configcustomfields;
/*      */         }
/* 1422 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1424 */           actions = editlink + actions;
/*      */         }
/* 1426 */         String managedLink = "";
/* 1427 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1429 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1430 */           actions = "";
/* 1431 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1432 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + URLEncoder.encode(childresname) + "&resourcename=" + URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1435 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1437 */           checkbox = "";
/*      */         }
/*      */         
/* 1440 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1442 */           actions = "";
/*      */         }
/* 1444 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1445 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1446 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1447 */         if (isIt360)
/*      */         {
/* 1449 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1453 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1455 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1456 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1457 */         if (!isIt360)
/*      */         {
/* 1459 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1463 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1465 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1468 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1475 */       StringBuilder toreturn = new StringBuilder();
/* 1476 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1477 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1478 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1479 */       String title = "";
/* 1480 */       message = EnterpriseUtil.decodeString(message);
/* 1481 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1482 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1483 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1485 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1487 */       else if ("5".equals(severity))
/*      */       {
/* 1489 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1493 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1495 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1496 */       toreturn.append(v);
/*      */       
/* 1498 */       toreturn.append(link);
/* 1499 */       if (severity == null)
/*      */       {
/* 1501 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1503 */       else if (severity.equals("5"))
/*      */       {
/* 1505 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1507 */       else if (severity.equals("4"))
/*      */       {
/* 1509 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1511 */       else if (severity.equals("1"))
/*      */       {
/* 1513 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1518 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1520 */       toreturn.append("</a>");
/* 1521 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1525 */       ex.printStackTrace();
/*      */     }
/* 1527 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1534 */       StringBuilder toreturn = new StringBuilder();
/* 1535 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1536 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1537 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1538 */       if (message == null)
/*      */       {
/* 1540 */         message = "";
/*      */       }
/*      */       
/* 1543 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1544 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1546 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1547 */       toreturn.append(v);
/*      */       
/* 1549 */       toreturn.append(link);
/*      */       
/* 1551 */       if (severity == null)
/*      */       {
/* 1553 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1555 */       else if (severity.equals("5"))
/*      */       {
/* 1557 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1559 */       else if (severity.equals("1"))
/*      */       {
/* 1561 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1566 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1568 */       toreturn.append("</a>");
/* 1569 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1575 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1578 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1579 */     if (invokeActions != null) {
/* 1580 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1581 */       while (iterator.hasNext()) {
/* 1582 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1583 */         if (actionmap.containsKey(actionid)) {
/* 1584 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1589 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1593 */     String actionLink = "";
/* 1594 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1595 */     String query = "";
/* 1596 */     ResultSet rs = null;
/* 1597 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1598 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1599 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1600 */       actionLink = "method=" + methodName;
/*      */     }
/* 1602 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1603 */       actionLink = methodName;
/*      */     }
/* 1605 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1606 */     Iterator itr = methodarglist.iterator();
/* 1607 */     boolean isfirstparam = true;
/* 1608 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1609 */     while (itr.hasNext()) {
/* 1610 */       HashMap argmap = (HashMap)itr.next();
/* 1611 */       String argtype = (String)argmap.get("TYPE");
/* 1612 */       String argname = (String)argmap.get("IDENTITY");
/* 1613 */       String paramname = (String)argmap.get("PARAMETER");
/* 1614 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1615 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1616 */         isfirstparam = false;
/* 1617 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1619 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1623 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1627 */         actionLink = actionLink + "&";
/*      */       }
/* 1629 */       String paramValue = null;
/* 1630 */       String tempargname = argname;
/* 1631 */       if (commonValues.getProperty(tempargname) != null) {
/* 1632 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1635 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1636 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1637 */           if (dbType.equals("mysql")) {
/* 1638 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1641 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1643 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1645 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1646 */             if (rs.next()) {
/* 1647 */               paramValue = rs.getString("VALUE");
/* 1648 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1652 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1656 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1659 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1664 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1665 */           paramValue = rowId;
/*      */         }
/* 1667 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1668 */           paramValue = managedObjectName;
/*      */         }
/* 1670 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1671 */           paramValue = resID;
/*      */         }
/* 1673 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1674 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1677 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1679 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1680 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1681 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1683 */     return actionLink;
/*      */   }
/*      */   
/* 1686 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1687 */     String dependentAttribute = null;
/* 1688 */     String align = "left";
/*      */     
/* 1690 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1691 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1692 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1693 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1694 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1695 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1696 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1697 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1698 */       align = "center";
/*      */     }
/*      */     
/* 1701 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1702 */     String actualdata = "";
/*      */     
/* 1704 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1705 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1706 */         actualdata = availValue;
/*      */       }
/* 1708 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1709 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1713 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1714 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1717 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1723 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1724 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1725 */       toreturn.append("<table>");
/* 1726 */       toreturn.append("<tr>");
/* 1727 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1728 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1729 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1730 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1731 */         String toolTip = "";
/* 1732 */         String hideClass = "";
/* 1733 */         String textStyle = "";
/* 1734 */         boolean isreferenced = true;
/* 1735 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1736 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1737 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1738 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1740 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1741 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1742 */           while (valueList.hasMoreTokens()) {
/* 1743 */             String dependentVal = valueList.nextToken();
/* 1744 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1745 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1746 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1748 */               toolTip = "";
/* 1749 */               hideClass = "";
/* 1750 */               isreferenced = false;
/* 1751 */               textStyle = "disabledtext";
/* 1752 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1756 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1757 */           toolTip = "";
/* 1758 */           hideClass = "";
/* 1759 */           isreferenced = false;
/* 1760 */           textStyle = "disabledtext";
/* 1761 */           if (dependentImageMap != null) {
/* 1762 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1763 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1766 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1770 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1771 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1772 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1773 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1774 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1775 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1777 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1778 */           if (isreferenced) {
/* 1779 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1783 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1784 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1785 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1786 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1787 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1788 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1790 */           toreturn.append("</span>");
/* 1791 */           toreturn.append("</a>");
/* 1792 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1795 */       toreturn.append("</tr>");
/* 1796 */       toreturn.append("</table>");
/* 1797 */       toreturn.append("</td>");
/*      */     } else {
/* 1799 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1802 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1806 */     String colTime = null;
/* 1807 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1808 */     if ((rows != null) && (rows.size() > 0)) {
/* 1809 */       Iterator<String> itr = rows.iterator();
/* 1810 */       String maxColQuery = "";
/* 1811 */       for (;;) { if (itr.hasNext()) {
/* 1812 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1813 */           ResultSet maxCol = null;
/*      */           try {
/* 1815 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1816 */             while (maxCol.next()) {
/* 1817 */               if (colTime == null) {
/* 1818 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1821 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1830 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1830 */             AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1832 */               if (maxCol != null)
/* 1833 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1835 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1840 */     return colTime;
/*      */   }
/*      */   
/* 1843 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1844 */     tablename = null;
/* 1845 */     ResultSet rsTable = null;
/* 1846 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1848 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1849 */       while (rsTable.next()) {
/* 1850 */         tablename = rsTable.getString("DATATABLE");
/* 1851 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1852 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1865 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1856 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1859 */         if (rsTable != null)
/* 1860 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1862 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1868 */     String argsList = "";
/* 1869 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1871 */       if (showArgsMap.get(row) != null) {
/* 1872 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1873 */         if (showArgslist != null) {
/* 1874 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1875 */             if (argsList.trim().equals("")) {
/* 1876 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1879 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1886 */       e.printStackTrace();
/* 1887 */       return "";
/*      */     }
/* 1889 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1894 */     String argsList = "";
/* 1895 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1898 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1900 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1901 */         if (hideArgsList != null)
/*      */         {
/* 1903 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1905 */             if (argsList.trim().equals(""))
/*      */             {
/* 1907 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1911 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1919 */       ex.printStackTrace();
/*      */     }
/* 1921 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1925 */     StringBuilder toreturn = new StringBuilder();
/* 1926 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1933 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1934 */       Iterator itr = tActionList.iterator();
/* 1935 */       while (itr.hasNext()) {
/* 1936 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1937 */         String confirmmsg = "";
/* 1938 */         String link = "";
/* 1939 */         String isJSP = "NO";
/* 1940 */         HashMap tactionMap = (HashMap)itr.next();
/* 1941 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1942 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1943 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1944 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1945 */           (actionmap.containsKey(actionId))) {
/* 1946 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1947 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1948 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1949 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1950 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1952 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1958 */           if (isTableAction) {
/* 1959 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1962 */             tableName = "Link";
/* 1963 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1964 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1965 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1966 */             toreturn.append("</a></td>");
/*      */           }
/* 1968 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1969 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1970 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1977 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1983 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1985 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1986 */       Properties prop = (Properties)node.getUserObject();
/* 1987 */       String mgID = prop.getProperty("label");
/* 1988 */       String mgName = prop.getProperty("value");
/* 1989 */       String isParent = prop.getProperty("isParent");
/* 1990 */       int mgIDint = Integer.parseInt(mgID);
/* 1991 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1993 */         mgName = mgName + "(" + CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1995 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1996 */       if (node.getChildCount() > 0)
/*      */       {
/* 1998 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2000 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2002 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2004 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2008 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2013 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2015 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2017 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2019 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2023 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2026 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2027 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2029 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2033 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2035 */       if (node.getChildCount() > 0)
/*      */       {
/* 2037 */         builder.append("<UL>");
/* 2038 */         printMGTree(node, builder);
/* 2039 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2044 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2045 */     StringBuffer toReturn = new StringBuffer();
/* 2046 */     String table = "-";
/*      */     try {
/* 2048 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2049 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2050 */       float total = 0.0F;
/* 2051 */       while (it.hasNext()) {
/* 2052 */         String attName = (String)it.next();
/* 2053 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2054 */         boolean roundOffData = false;
/* 2055 */         if ((data != null) && (!data.equals(""))) {
/* 2056 */           if (data.indexOf(",") != -1) {
/* 2057 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2060 */             float value = Float.parseFloat(data);
/* 2061 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2064 */             total += value;
/* 2065 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2068 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2073 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2074 */       while (attVsWidthList.hasNext()) {
/* 2075 */         String attName = (String)attVsWidthList.next();
/* 2076 */         String data = (String)attVsWidthProps.get(attName);
/* 2077 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2078 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2079 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2080 */         String className = (String)graphDetails.get("ClassName");
/* 2081 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2082 */         if (percentage < 1.0F)
/*      */         {
/* 2084 */           data = percentage + "";
/*      */         }
/* 2086 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2088 */       if (toReturn.length() > 0) {
/* 2089 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2093 */       e.printStackTrace();
/*      */     }
/* 2095 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2101 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2102 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2103 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2104 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2105 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2106 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2107 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2108 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2109 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2112 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2113 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2114 */       splitvalues[0] = multiplecondition.toString();
/* 2115 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2118 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2123 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2124 */     if (thresholdType != 3) {
/* 2125 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2126 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2127 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2128 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2129 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2130 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2132 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2133 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2134 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2135 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2136 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2137 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2139 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2140 */     if (updateSelected != null) {
/* 2141 */       updateSelected[0] = "selected";
/*      */     }
/* 2143 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2148 */       StringBuffer toreturn = new StringBuffer("");
/* 2149 */       if (commaSeparatedMsgId != null) {
/* 2150 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2151 */         int count = 0;
/* 2152 */         while (msgids.hasMoreTokens()) {
/* 2153 */           String id = msgids.nextToken();
/* 2154 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2155 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2156 */           count++;
/* 2157 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2158 */             if (toreturn.length() == 0) {
/* 2159 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2161 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2162 */             if (!image.trim().equals("")) {
/* 2163 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2165 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2166 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2169 */         if (toreturn.length() > 0) {
/* 2170 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2174 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2177 */       e.printStackTrace(); }
/* 2178 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2184 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2190 */   private static Map<String, Long> _jspx_dependants = new HashMap(2);
/* 2191 */   static { _jspx_dependants.put("/jsp/includes/SlaLinks.jspf", Long.valueOf(1473429417000L));
/* 2192 */     _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L));
/*      */   }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2210 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2214 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2215 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2216 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2217 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2218 */     this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2219 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2220 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2225 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2229 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2230 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2231 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2232 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2233 */     this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.release();
/* 2234 */     this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.release();
/* 2235 */     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.release();
/* 2236 */     this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.release();
/* 2237 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2238 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, HttpServletResponse response)
/*      */     throws IOException, javax.servlet.ServletException
/*      */   {
/* 2245 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2248 */     JspWriter out = null;
/* 2249 */     Object page = this;
/* 2250 */     JspWriter _jspx_out = null;
/* 2251 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2255 */       response.setContentType("text/html;charset=UTF-8");
/* 2256 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2258 */       _jspx_page_context = pageContext;
/* 2259 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2260 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2261 */       session = pageContext.getSession();
/* 2262 */       out = pageContext.getOut();
/* 2263 */       _jspx_out = out;
/*      */       
/* 2265 */       out.write("<!DOCTYPE html>\n");
/* 2266 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2267 */       out.write(10);
/* 2268 */       out.write(10);
/*      */       
/* 2270 */       request.setAttribute("HelpKey", "Manager Console");
/* 2271 */       boolean isIT360SLAEnabled = SLAUtil.isIT360SLAEnabled(request);
/* 2272 */       boolean isIT360SPAdminServer = EnterpriseUtil.isIt360MSPAdminServer();
/* 2273 */       String isdefaultview = request.getAttribute("defaultView") != null ? (String)request.getAttribute("defaultView") : "";
/* 2274 */       boolean isAdminUser = request.isUserInRole("ADMIN");
/*      */       
/* 2276 */       out.write("\n\n\n\n\n\n\n\n\n<SCRIPT src=\"template/calendar.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-en.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/calendar-setup.js\" type=text/javascript></SCRIPT>\n<SCRIPT src=\"template/listview.js\" type=text/javascript></SCRIPT>\n<link href=\"/images/calendar-win2k-1.css\" rel=\"stylesheet\" type=\"text/css\">\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2277 */       out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */       
/* 2279 */       DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2280 */       _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2281 */       _jspx_th_bean_005fdefine_005f0.setParent(null);
/*      */       
/* 2283 */       _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */       
/* 2285 */       _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */       
/* 2287 */       _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */       
/* 2289 */       _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2290 */       int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2291 */       if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2292 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/*      */       }
/*      */       else {
/* 2295 */         this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2296 */         String available = null;
/* 2297 */         available = (String)_jspx_page_context.findAttribute("available");
/* 2298 */         out.write(10);
/*      */         
/* 2300 */         DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2301 */         _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2302 */         _jspx_th_bean_005fdefine_005f1.setParent(null);
/*      */         
/* 2304 */         _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */         
/* 2306 */         _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */         
/* 2308 */         _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */         
/* 2310 */         _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2311 */         int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2312 */         if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2313 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/*      */         }
/*      */         else {
/* 2316 */           this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2317 */           String unavailable = null;
/* 2318 */           unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2319 */           out.write(10);
/*      */           
/* 2321 */           DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2322 */           _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2323 */           _jspx_th_bean_005fdefine_005f2.setParent(null);
/*      */           
/* 2325 */           _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */           
/* 2327 */           _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */           
/* 2329 */           _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */           
/* 2331 */           _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2332 */           int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2333 */           if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2334 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/*      */           }
/*      */           else {
/* 2337 */             this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2338 */             String unmanaged = null;
/* 2339 */             unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2340 */             out.write(10);
/*      */             
/* 2342 */             DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2343 */             _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2344 */             _jspx_th_bean_005fdefine_005f3.setParent(null);
/*      */             
/* 2346 */             _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */             
/* 2348 */             _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */             
/* 2350 */             _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */             
/* 2352 */             _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2353 */             int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2354 */             if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2355 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/*      */             }
/*      */             else {
/* 2358 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2359 */               String scheduled = null;
/* 2360 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2361 */               out.write(10);
/*      */               
/* 2363 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2364 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2365 */               _jspx_th_bean_005fdefine_005f4.setParent(null);
/*      */               
/* 2367 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2369 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2371 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2373 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2374 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2375 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2376 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/*      */               }
/*      */               else {
/* 2379 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2380 */                 String critical = null;
/* 2381 */                 critical = (String)_jspx_page_context.findAttribute("critical");
/* 2382 */                 out.write(10);
/*      */                 
/* 2384 */                 DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2385 */                 _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2386 */                 _jspx_th_bean_005fdefine_005f5.setParent(null);
/*      */                 
/* 2388 */                 _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */                 
/* 2390 */                 _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */                 
/* 2392 */                 _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */                 
/* 2394 */                 _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2395 */                 int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2396 */                 if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2397 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/*      */                 }
/*      */                 else {
/* 2400 */                   this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2401 */                   String clear = null;
/* 2402 */                   clear = (String)_jspx_page_context.findAttribute("clear");
/* 2403 */                   out.write(10);
/*      */                   
/* 2405 */                   DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2406 */                   _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2407 */                   _jspx_th_bean_005fdefine_005f6.setParent(null);
/*      */                   
/* 2409 */                   _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */                   
/* 2411 */                   _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */                   
/* 2413 */                   _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */                   
/* 2415 */                   _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2416 */                   int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2417 */                   if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2418 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/*      */                   }
/*      */                   else {
/* 2421 */                     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2422 */                     String warning = null;
/* 2423 */                     warning = (String)_jspx_page_context.findAttribute("warning");
/* 2424 */                     out.write(10);
/* 2425 */                     out.write(10);
/*      */                     
/* 2427 */                     String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2428 */                     boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */                     
/* 2430 */                     out.write(10);
/* 2431 */                     out.write(10);
/* 2432 */                     out.write(10);
/* 2433 */                     out.write("\n\n<script>\nvar searchForm;\nvar reportForm1;\nvar reportForm2;\nvar showReportsForm;\n\nfunction myOnLoad()\n{\n\treportForm1 = document.ReportForm[0];\n\treportForm2 = document.ReportForm[1];\n\tshowReportsForm = document.showReportForm;\n   SORTTABLENAME = 'slaAvailabilityTable';\n   numberOfColumnsToBeSorted=1;\n   ignoreCheckBox=false;\n    ");
/* 2434 */                     if (isIT360SLAEnabled) {
/* 2435 */                       out.write("\n        searchForm = document.forms[0];\n        reportForm1 = document.forms[1];\n        reportForm2 = document.forms[2];\n        showReportsForm = document.forms[3];\n\tjQuery(\"#loadingg\").hide();//No I18N\n    ");
/*      */                     }
/* 2437 */                     else if ((isdefaultview.equals("true")) && (isAdminUser)) {
/* 2438 */                       out.write("\n    \t searchForm = document.forms[0];\n        reportForm1 = document.forms[1];\n        reportForm2 = document.forms[2];\n        showReportsForm = document.forms[3];\n\n    ");
/*      */                     }
/* 2440 */                     out.write("\n   sortables_init(numberOfColumnsToBeSorted,ignoreCheckBox);\n}\n\n\nfunction fnCheckCustomTime(f)\n {\n if(f.startDate.value=='')\n {\n alert(\"");
/* 2441 */                     out.print(FormatUtil.getString("am.webclient.historydata.jsalertforstarttime"));
/* 2442 */                     out.write("\");\n return false\n }\n else if(f.endDate.value=='')\n {\n alert(\"");
/* 2443 */                     out.print(FormatUtil.getString("am.webclient.historydata.jsalertforendtime"));
/* 2444 */                     out.write("\")\n return false\n }\n var s=f.startDate.value;\n var e=f.endDate.value;\n    if(s>e)\n    {\n     alert(\"");
/* 2445 */                     out.print(FormatUtil.getString("am.webclient.historydata.jsalertforgtstartime"));
/* 2446 */                     out.write("\" );\n     return false;\n    }\n    f.reportType.value=\"\";\t \n return true\n }\n function fnSetEndTime(a)\n{\n\n\treportForm2.endDate.value=a;\n}\nfunction fnSetStartTime(a)\n{\n\n\treportForm2.startDate.value=a;\n}\n\n function fnPeriod(periodcombo)\n{\n\t reportForm1.reportType.value=\"\";\t \n\t reportForm1.submit();\n}\n function generateReportFile(reportType){\n\t if(reportForm2.startDate && reportForm2.startDate.value==\"\" && reportForm2.endDate.value==\"\"){\n\t \treportForm1.reportType.value=reportType;\t \n\t \treportForm1.submit();\n\t }\n\t else{\n\t\t\treportForm2.reportType.value=reportType;\t \n\t\t \treportForm2.submit();\n\t }\t \n }\n \n \nfunction getNewWindow(url, title, width, height, params)\n{\n\n        var left = Math.floor( (screen.width - width) / 2);\n        var top = Math.floor( (screen.height - height) / 2);\n        var winParms = \"top=\" + top + \",left=\" + left + \",height=\" + height + \",width=\" + width+\" ,resizable=yes,scrollbars=yes\";\n        if (params) { winParms += \",\" + params; }\n        try {\n            newwindow = window.open(url, title, winParms);\n");
/* 2447 */                     out.write("            return newwindow;\n        } catch(e) {\n            newwindow = window.open(url, 'ww', winParms);\n            return newwindow;\n        }\n}\n\n\n\n\nfunction fnshowData(b,c)\n{\n        showReportsForm.action='/showReports.do';\n        showReportsForm.haid.value=b;\n\tshowReportsForm.actionMethod.value='generateHAAvailabilityReport';//No I18N\n\n\tshowReportsForm.period.value=c;\n\t   if(c==4)\n\t   {\n\t     showReportsForm.strTime.value=reportForm2.startDate.value;\n             showReportsForm.endTime.value=reportForm2.endDate.value;\n           }\n\n\tshowReportsForm.PRINTER_FRIENDLY.value=true;\n\t //var newwindow = getNewWindow('#','Reports','800','900','true');\n\n          var newwindow =window.open('#','Reports','resizable=yes,scrollbars=yes,width=800,height=900')\n\tshowReportsForm.target=newwindow.name;\n\tshowReportsForm.submit();\n\n\n}\nfunction fnshowData1(b,c)\n{\n        showReportsForm.action='/showReports.do';\n\n\tshowReportsForm.haid.value=b;\n\tshowReportsForm.actionMethod.value='generateEventSummary';//No I18N\n\tif(c==4)\n");
/* 2448 */                     out.write("\t   {\n\t     showReportsForm.strTime.value=reportForm2.startDate.value;\n             showReportsForm.endTime.value=reportForm2.endDate.value;\n           }\n\n\n\tshowReportsForm.period.value=c;\n\tshowReportsForm.PRINTER_FRIENDLY.value=true;\n\t //var newwindow = getNewWindow('#','Reports','800','900','true');\n\n           var newwindow =window.open('#','Reports','resizable=yes,scrollbars=yes,width=800,height=900')\n\tshowReportsForm.target=newwindow.name;\n\tshowReportsForm.submit();\n\n\n}\n//TODO: Need to betterment the implementation by using obj ref 'this' rather than using 4 js method.\nfunction showAvailabilitySLATab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").show(); //No I18N\n\tjQuery(\"#showServerSLATab\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n\tdocument.location.href=\"/showBussiness.do?method=generateApplicationAvailablity\";\n}\nfunction showServerSLATab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n");
/* 2449 */                     out.write("\tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n\tjQuery(\"#showServerSLATab\").show(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n\tdocument.location.href=\"/showBussiness.do?method=generateSystemAvailablity\";\n}\nfunction showEventVolumeSLATab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showServerSLATab\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").show(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n\tdocument.location.href=\"/showBussiness.do?method=generateTroubleTicket\";\n}\nfunction showSLASettingsTab()\n{\n\tjQuery(\"#actionButtonDiv\").hide(); //No I18N\n\tjQuery(\"#showSLASettingsTab\").show(); //No I18N\n\tjQuery(\"#showServerSLATab\").hide(); //No I18N\n\tjQuery(\"#showEventVolumeSLATab\").hide(); //No I18N\n\tjQuery(\"#showAvailabilitySLATab\").hide(); //No I18N\n\tjQuery(\"#loadingg\").show(); //No I18N\n");
/* 2450 */                     out.write("\tdocument.location.href=\"/showBussiness.do?method=generateSLA&sla=true\";\n}\n</script>\n\n<SCRIPT LANGUAGE=\"JavaScript1.2\" SRC=\"../template/sortTable.js\">myOnLoad()</SCRIPT>\n\n");
/*      */                     
/* 2452 */                     String layOutPage = null;
/* 2453 */                     String titleName = null;
/* 2454 */                     String headerPage = null;
/*      */                     
/* 2456 */                     if (isIT360SLAEnabled)
/*      */                     {
/* 2458 */                       layOutPage = "/jsp/BasicLayoutNoLeft.jsp";
/* 2459 */                       titleName = FormatUtil.getString("am.webclient.sla.slaview.txt");
/* 2460 */                       headerPage = "/jsp/header.jsp";
/* 2461 */                       request.setAttribute("oldtab", "7");
/*      */                     }
/* 2463 */                     else if ((isdefaultview != null) && (isdefaultview.trim().equalsIgnoreCase("true")))
/*      */                     {
/* 2465 */                       layOutPage = "/jsp/ManagerLayout.jsp";
/* 2466 */                       titleName = FormatUtil.getString("am.webclient.sla.slaview.txt");
/* 2467 */                       headerPage = "/jsp/header.jsp";
/*      */                     }
/*      */                     else
/*      */                     {
/* 2471 */                       layOutPage = "/jsp/ManagerLayout.jsp";
/* 2472 */                       titleName = FormatUtil.getString("am.webclient.manager.slatab.title.text");
/* 2473 */                       headerPage = "/jsp/ManagerHeader.jsp";
/*      */                     }
/*      */                     
/* 2476 */                     out.write(10);
/* 2477 */                     out.write(10);
/*      */                     
/* 2479 */                     InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2480 */                     _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2481 */                     _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */                     
/* 2483 */                     _jspx_th_tiles_005finsert_005f0.setPage(layOutPage);
/* 2484 */                     int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2485 */                     if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */                       for (;;) {
/* 2487 */                         out.write(10);
/*      */                         
/* 2489 */                         PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2490 */                         _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2491 */                         _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2493 */                         _jspx_th_tiles_005fput_005f0.setName("title");
/*      */                         
/* 2495 */                         _jspx_th_tiles_005fput_005f0.setValue(titleName);
/* 2496 */                         int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2497 */                         if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2498 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */                         }
/*      */                         
/* 2501 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2502 */                         out.write(32);
/*      */                         
/* 2504 */                         PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2505 */                         _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 2506 */                         _jspx_th_tiles_005fput_005f1.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2508 */                         _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */                         
/* 2510 */                         _jspx_th_tiles_005fput_005f1.setValue(headerPage);
/* 2511 */                         int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 2512 */                         if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 2513 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1); return;
/*      */                         }
/*      */                         
/* 2516 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 2517 */                         out.write(10);
/* 2518 */                         out.write(10);
/* 2519 */                         out.write(32);
/*      */                         
/* 2521 */                         PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2522 */                         _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2523 */                         _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */                         
/* 2525 */                         _jspx_th_tiles_005fput_005f2.setName("UserArea");
/*      */                         
/* 2527 */                         _jspx_th_tiles_005fput_005f2.setType("string");
/* 2528 */                         int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2529 */                         if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2530 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2531 */                             out = _jspx_page_context.pushBody();
/* 2532 */                             _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2533 */                             _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */                           }
/*      */                           for (;;) {
/* 2536 */                             out.write(10);
/* 2537 */                             out.write(10);
/* 2538 */                             if (isIT360SLAEnabled)
/*      */                             {
/* 2540 */                               out.write("\n\t\n\t");
/*      */                               
/* 2542 */                               String titles = SLAUtil.getSLASubTabsTitle();
/* 2543 */                               String functions = SLAUtil.getSLASubTabsJS();
/* 2544 */                               String selectedTab = "am.webclient.dasboard.availabilitytab.title";
/*      */                               
/* 2546 */                               out.write(10);
/* 2547 */                               out.write(9);
/* 2548 */                               JspRuntimeLibrary.include(request, response, "/jsp/tabs.jsp" + ("/jsp/tabs.jsp".indexOf('?') > 0 ? '&' : '?') + JspRuntimeLibrary.URLEncode("titles", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("tooltip", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(titles), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("functions", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(functions), request.getCharacterEncoding()) + "&" + JspRuntimeLibrary.URLEncode("selected", request.getCharacterEncoding()) + "=" + JspRuntimeLibrary.URLEncode(String.valueOf(selectedTab), request.getCharacterEncoding()), out, true);
/* 2549 */                               out.write(10);
/*      */                             }
/* 2551 */                             out.write("\n\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\" id=\"showAvailabilitySLATab\">\n");
/*      */                             try
/*      */                             {
/* 2554 */                               ArrayList availdata = (ArrayList)request.getAttribute("checklist");
/* 2555 */                               String Periodvalue = (String)request.getAttribute("pvalue");
/*      */                               int wide;
/* 2557 */                               int wide; if ((availdata.size() == 0) || (availdata.size() == 1))
/*      */                               {
/* 2559 */                                 wide = 300;
/*      */                               } else { int wide;
/* 2561 */                                 if ((availdata.size() > 1) && (availdata.size() < 5))
/*      */                                 {
/* 2563 */                                   wide = availdata.size() * 150;
/*      */                                 } else { int wide;
/* 2565 */                                   if ((availdata.size() > 5) && (availdata.size() < 10))
/*      */                                   {
/* 2567 */                                     wide = availdata.size() * 60;
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 2571 */                                     wide = availdata.size() * 38; }
/*      */                                 }
/*      */                               }
/* 2574 */                               String width = String.valueOf(wide);
/* 2575 */                               String sla = request.getParameter("sla");
/*      */                               
/*      */ 
/* 2578 */                               out.write("\n\n<tr>\n<td width='80%' valign=\"top\">\n\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" class='lrbtborder'>\n\n<tr><td width='80%'>\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  align=\"left\" >\n\n    <tr>\n\t      <td  width=\"80%\" class=\"tableheadingbborder\">");
/* 2579 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.heading.text", new String[] { Periodvalue }));
/* 2580 */                               out.write("\n\t      </td>\n\n\t </tr>\n</table>\n</td>\n</tr>\n\n\n\n\n\n   <tr>\n          <td height=\"170\" align=\"center\" class=\"yellowgrayborder\">\n\n          ");
/*      */                               
/* 2582 */                               MultiPieChart _jspx_th_awolf_005fmultipiechart_005f0 = (MultiPieChart)this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.get(MultiPieChart.class);
/* 2583 */                               _jspx_th_awolf_005fmultipiechart_005f0.setPageContext(_jspx_page_context);
/* 2584 */                               _jspx_th_awolf_005fmultipiechart_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2586 */                               _jspx_th_awolf_005fmultipiechart_005f0.setDataSetProducer("availgraph");
/*      */                               
/* 2588 */                               _jspx_th_awolf_005fmultipiechart_005f0.setWidth(width);
/*      */                               
/* 2590 */                               _jspx_th_awolf_005fmultipiechart_005f0.setHeight("195");
/*      */                               
/* 2592 */                               _jspx_th_awolf_005fmultipiechart_005f0.setLegend("true");
/*      */                               
/* 2594 */                               _jspx_th_awolf_005fmultipiechart_005f0.setUrl(false);
/*      */                               
/* 2596 */                               _jspx_th_awolf_005fmultipiechart_005f0.setNodatamessage(FormatUtil.getString("am.webclient.manager.slatab.nodatamessage.text"));
/* 2597 */                               int _jspx_eval_awolf_005fmultipiechart_005f0 = _jspx_th_awolf_005fmultipiechart_005f0.doStartTag();
/* 2598 */                               if (_jspx_eval_awolf_005fmultipiechart_005f0 != 0) {
/* 2599 */                                 if (_jspx_eval_awolf_005fmultipiechart_005f0 != 1) {
/* 2600 */                                   out = _jspx_page_context.pushBody();
/* 2601 */                                   _jspx_th_awolf_005fmultipiechart_005f0.setBodyContent((BodyContent)out);
/* 2602 */                                   _jspx_th_awolf_005fmultipiechart_005f0.doInitBody();
/*      */                                 }
/*      */                                 for (;;) {
/* 2605 */                                   out.write("\n            ");
/* 2606 */                                   int evalDoAfterBody = _jspx_th_awolf_005fmultipiechart_005f0.doAfterBody();
/* 2607 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/* 2610 */                                 if (_jspx_eval_awolf_005fmultipiechart_005f0 != 1) {
/* 2611 */                                   out = _jspx_page_context.popBody();
/*      */                                 }
/*      */                               }
/* 2614 */                               if (_jspx_th_awolf_005fmultipiechart_005f0.doEndTag() == 5) {
/* 2615 */                                 this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fmultipiechart_005f0); return;
/*      */                               }
/*      */                               
/* 2618 */                               this._005fjspx_005ftagPool_005fawolf_005fmultipiechart_0026_005fwidth_005furl_005fnodatamessage_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005fmultipiechart_005f0);
/* 2619 */                               out.write("\n          </td>\n\n        </tr>\n\n      </table></td>\n\n<td width=\"20%\" valign='top'>\n");
/* 2620 */                               out.write("<!--$Id$-->\n\n");
/*      */                               
/* 2622 */                               String uri = request.getRequestURI();
/* 2623 */                               if (com.manageengine.appmanager.plugin.PluginUtil.isPlugin())
/*      */                               {
/* 2625 */                                 out.write("\n<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  style=\"Display:inline: padding:5px;margin-bottom:10px;\" class=\"lrtbdarkborder\">\n    <tr>\n    <td class=\"leftlinksheading\">");
/* 2626 */                                 out.print(FormatUtil.getString("am.webclient.jboss.quicklinks.text"));
/* 2627 */                                 out.write("</td>\n    </tr>\n    <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSLA&showdiv=true\" class=\"new-left-links\">");
/* 2628 */                                 out.print(FormatUtil.getString("am.webclient.manager.newsla.text"));
/* 2629 */                                 out.write("</a></td>\n   </tr>\n   ");
/* 2630 */                                 if (!uri.contains("Bussiness")) {
/* 2631 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateApplicationAvailablity\" class=\"new-left-links\">");
/* 2632 */                                   out.print(FormatUtil.getString("Application"));
/* 2633 */                                   out.write(32);
/* 2634 */                                   out.write(32);
/* 2635 */                                   out.print(FormatUtil.getString("am.webclient.manager.sla.text"));
/* 2636 */                                   out.write("</a></td>\n   </tr>\n   "); }
/* 2637 */                                 if (!uri.contains("SystemAvailablity")) {
/* 2638 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSystemAvailablity\" class=\"new-left-links\">");
/* 2639 */                                   out.print(FormatUtil.getString("am.webclient.manager.serversla.text"));
/* 2640 */                                   out.write("</a></td>\n   </tr>\n   "); }
/* 2641 */                                 if (!uri.contains("BAEventReport")) {
/* 2642 */                                   out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateTroubleTicket\" class=\"new-left-links\">");
/* 2643 */                                   out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/* 2644 */                                   out.write("</a></td>\n   </tr>\n   ");
/*      */                                 }
/* 2646 */                                 out.write("\n   <tr>\n    <td align=\"left\" height=\"27px\" class=\"leftlinkstd\"><a href=\"/showBussiness.do?method=generateSLA&sla=true\" class=\"new-left-links\">");
/* 2647 */                                 out.print(FormatUtil.getString("am.webclient.manager.settings.text"));
/* 2648 */                                 out.write("</a></td>\n   </tr>\n</table>\n");
/*      */                               }
/* 2650 */                               out.write(" \n <table width=\"95%\" class=\"lrtbdarkborder\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align='right'>\n <tr>\n     <td  align='left' >\n     \t\t");
/*      */                               
/* 2652 */                               FormTag _jspx_th_html_005fform_005f0 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2653 */                               _jspx_th_html_005fform_005f0.setPageContext(_jspx_page_context);
/* 2654 */                               _jspx_th_html_005fform_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2656 */                               _jspx_th_html_005fform_005f0.setAction("/showBussiness.do?method=generateApplicationAvailablity");
/*      */                               
/* 2658 */                               _jspx_th_html_005fform_005f0.setStyle("Display:inline");
/* 2659 */                               int _jspx_eval_html_005fform_005f0 = _jspx_th_html_005fform_005f0.doStartTag();
/* 2660 */                               if (_jspx_eval_html_005fform_005f0 != 0) {
/*      */                                 for (;;) {
/* 2662 */                                   out.write("\n               <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables marg-btm\">\n                <tr>\n                <td class=\"tableheadingbborder\"  >");
/* 2663 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.text"));
/* 2664 */                                   out.write("</td>\n              </tr>\n              <tr>\n\n\n\n               <td class=\"leftlinkstd\" > ");
/*      */                                   
/* 2666 */                                   SelectTag _jspx_th_html_005fselect_005f0 = (SelectTag)this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.get(SelectTag.class);
/* 2667 */                                   _jspx_th_html_005fselect_005f0.setPageContext(_jspx_page_context);
/* 2668 */                                   _jspx_th_html_005fselect_005f0.setParent(_jspx_th_html_005fform_005f0);
/*      */                                   
/* 2670 */                                   _jspx_th_html_005fselect_005f0.setProperty("period");
/*      */                                   
/* 2672 */                                   _jspx_th_html_005fselect_005f0.setOnchange("javascript:fnPeriod(this)");
/*      */                                   
/* 2674 */                                   _jspx_th_html_005fselect_005f0.setStyleClass("formtextselected");
/*      */                                   
/* 2676 */                                   _jspx_th_html_005fselect_005f0.setStyle("position:relative;margin-top:8px;width:155px");
/* 2677 */                                   int _jspx_eval_html_005fselect_005f0 = _jspx_th_html_005fselect_005f0.doStartTag();
/* 2678 */                                   if (_jspx_eval_html_005fselect_005f0 != 0) {
/* 2679 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2680 */                                       out = _jspx_page_context.pushBody();
/* 2681 */                                       _jspx_th_html_005fselect_005f0.setBodyContent((BodyContent)out);
/* 2682 */                                       _jspx_th_html_005fselect_005f0.doInitBody();
/*      */                                     }
/*      */                                     for (;;) {
/* 2685 */                                       out.write("\n                  ");
/*      */                                       
/* 2687 */                                       OptionTag _jspx_th_html_005foption_005f0 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2688 */                                       _jspx_th_html_005foption_005f0.setPageContext(_jspx_page_context);
/* 2689 */                                       _jspx_th_html_005foption_005f0.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2691 */                                       _jspx_th_html_005foption_005f0.setKey(FormatUtil.getString("am.webclient.historydata.period.today.text"));
/*      */                                       
/* 2693 */                                       _jspx_th_html_005foption_005f0.setValue("0");
/* 2694 */                                       int _jspx_eval_html_005foption_005f0 = _jspx_th_html_005foption_005f0.doStartTag();
/* 2695 */                                       if (_jspx_th_html_005foption_005f0.doEndTag() == 5) {
/* 2696 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0); return;
/*      */                                       }
/*      */                                       
/* 2699 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f0);
/* 2700 */                                       out.write(32);
/*      */                                       
/* 2702 */                                       OptionTag _jspx_th_html_005foption_005f1 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2703 */                                       _jspx_th_html_005foption_005f1.setPageContext(_jspx_page_context);
/* 2704 */                                       _jspx_th_html_005foption_005f1.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2706 */                                       _jspx_th_html_005foption_005f1.setKey(FormatUtil.getString("am.webclient.historydata.period.yesterday.text"));
/*      */                                       
/* 2708 */                                       _jspx_th_html_005foption_005f1.setValue("3");
/* 2709 */                                       int _jspx_eval_html_005foption_005f1 = _jspx_th_html_005foption_005f1.doStartTag();
/* 2710 */                                       if (_jspx_th_html_005foption_005f1.doEndTag() == 5) {
/* 2711 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1); return;
/*      */                                       }
/*      */                                       
/* 2714 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f1);
/* 2715 */                                       out.write("\n                  ");
/*      */                                       
/* 2717 */                                       OptionTag _jspx_th_html_005foption_005f2 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2718 */                                       _jspx_th_html_005foption_005f2.setPageContext(_jspx_page_context);
/* 2719 */                                       _jspx_th_html_005foption_005f2.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2721 */                                       _jspx_th_html_005foption_005f2.setKey(FormatUtil.getString("am.webclient.historydata.period.thisweek.text"));
/*      */                                       
/* 2723 */                                       _jspx_th_html_005foption_005f2.setValue("6");
/* 2724 */                                       int _jspx_eval_html_005foption_005f2 = _jspx_th_html_005foption_005f2.doStartTag();
/* 2725 */                                       if (_jspx_th_html_005foption_005f2.doEndTag() == 5) {
/* 2726 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2); return;
/*      */                                       }
/*      */                                       
/* 2729 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f2);
/*      */                                       
/* 2731 */                                       OptionTag _jspx_th_html_005foption_005f3 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2732 */                                       _jspx_th_html_005foption_005f3.setPageContext(_jspx_page_context);
/* 2733 */                                       _jspx_th_html_005foption_005f3.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2735 */                                       _jspx_th_html_005foption_005f3.setKey(FormatUtil.getString("am.webclient.historydata.period.7days.text"));
/*      */                                       
/* 2737 */                                       _jspx_th_html_005foption_005f3.setValue("1");
/* 2738 */                                       int _jspx_eval_html_005foption_005f3 = _jspx_th_html_005foption_005f3.doStartTag();
/* 2739 */                                       if (_jspx_th_html_005foption_005f3.doEndTag() == 5) {
/* 2740 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3); return;
/*      */                                       }
/*      */                                       
/* 2743 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f3);
/* 2744 */                                       out.write(32);
/*      */                                       
/* 2746 */                                       OptionTag _jspx_th_html_005foption_005f4 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2747 */                                       _jspx_th_html_005foption_005f4.setPageContext(_jspx_page_context);
/* 2748 */                                       _jspx_th_html_005foption_005f4.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2750 */                                       _jspx_th_html_005foption_005f4.setKey(FormatUtil.getString("am.webclient.historydata.period.lastweek.text"));
/*      */                                       
/* 2752 */                                       _jspx_th_html_005foption_005f4.setValue("12");
/* 2753 */                                       int _jspx_eval_html_005foption_005f4 = _jspx_th_html_005foption_005f4.doStartTag();
/* 2754 */                                       if (_jspx_th_html_005foption_005f4.doEndTag() == 5) {
/* 2755 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4); return;
/*      */                                       }
/*      */                                       
/* 2758 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f4);
/* 2759 */                                       out.write("\n                   ");
/*      */                                       
/* 2761 */                                       OptionTag _jspx_th_html_005foption_005f5 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2762 */                                       _jspx_th_html_005foption_005f5.setPageContext(_jspx_page_context);
/* 2763 */                                       _jspx_th_html_005foption_005f5.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2765 */                                       _jspx_th_html_005foption_005f5.setKey(FormatUtil.getString("am.webclient.historydata.period.thismonth.text"));
/*      */                                       
/* 2767 */                                       _jspx_th_html_005foption_005f5.setValue("7");
/* 2768 */                                       int _jspx_eval_html_005foption_005f5 = _jspx_th_html_005foption_005f5.doStartTag();
/* 2769 */                                       if (_jspx_th_html_005foption_005f5.doEndTag() == 5) {
/* 2770 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5); return;
/*      */                                       }
/*      */                                       
/* 2773 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f5);
/* 2774 */                                       out.write(32);
/*      */                                       
/* 2776 */                                       OptionTag _jspx_th_html_005foption_005f6 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2777 */                                       _jspx_th_html_005foption_005f6.setPageContext(_jspx_page_context);
/* 2778 */                                       _jspx_th_html_005foption_005f6.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2780 */                                       _jspx_th_html_005foption_005f6.setKey(FormatUtil.getString("am.webclient.historydata.period.lastmonth.text"));
/*      */                                       
/* 2782 */                                       _jspx_th_html_005foption_005f6.setValue("11");
/* 2783 */                                       int _jspx_eval_html_005foption_005f6 = _jspx_th_html_005foption_005f6.doStartTag();
/* 2784 */                                       if (_jspx_th_html_005foption_005f6.doEndTag() == 5) {
/* 2785 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6); return;
/*      */                                       }
/*      */                                       
/* 2788 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f6);
/*      */                                       
/* 2790 */                                       OptionTag _jspx_th_html_005foption_005f7 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2791 */                                       _jspx_th_html_005foption_005f7.setPageContext(_jspx_page_context);
/* 2792 */                                       _jspx_th_html_005foption_005f7.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2794 */                                       _jspx_th_html_005foption_005f7.setKey(FormatUtil.getString("am.webclient.historydata.period.30days.text"));
/*      */                                       
/* 2796 */                                       _jspx_th_html_005foption_005f7.setValue("2");
/* 2797 */                                       int _jspx_eval_html_005foption_005f7 = _jspx_th_html_005foption_005f7.doStartTag();
/* 2798 */                                       if (_jspx_th_html_005foption_005f7.doEndTag() == 5) {
/* 2799 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7); return;
/*      */                                       }
/*      */                                       
/* 2802 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f7);
/* 2803 */                                       out.write(32);
/*      */                                       
/* 2805 */                                       OptionTag _jspx_th_html_005foption_005f8 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2806 */                                       _jspx_th_html_005foption_005f8.setPageContext(_jspx_page_context);
/* 2807 */                                       _jspx_th_html_005foption_005f8.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2809 */                                       _jspx_th_html_005foption_005f8.setKey(FormatUtil.getString("am.webclient.historydata.period.thisquarter.text"));
/*      */                                       
/* 2811 */                                       _jspx_th_html_005foption_005f8.setValue("9");
/* 2812 */                                       int _jspx_eval_html_005foption_005f8 = _jspx_th_html_005foption_005f8.doStartTag();
/* 2813 */                                       if (_jspx_th_html_005foption_005f8.doEndTag() == 5) {
/* 2814 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8); return;
/*      */                                       }
/*      */                                       
/* 2817 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f8);
/* 2818 */                                       out.write("\n                    ");
/*      */                                       
/* 2820 */                                       OptionTag _jspx_th_html_005foption_005f9 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2821 */                                       _jspx_th_html_005foption_005f9.setPageContext(_jspx_page_context);
/* 2822 */                                       _jspx_th_html_005foption_005f9.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2824 */                                       _jspx_th_html_005foption_005f9.setKey(FormatUtil.getString("am.webclient.historydata.period.thisyear.text"));
/*      */                                       
/* 2826 */                                       _jspx_th_html_005foption_005f9.setValue("8");
/* 2827 */                                       int _jspx_eval_html_005foption_005f9 = _jspx_th_html_005foption_005f9.doStartTag();
/* 2828 */                                       if (_jspx_th_html_005foption_005f9.doEndTag() == 5) {
/* 2829 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9); return;
/*      */                                       }
/*      */                                       
/* 2832 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f9);
/*      */                                       
/* 2834 */                                       OptionTag _jspx_th_html_005foption_005f10 = (OptionTag)this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.get(OptionTag.class);
/* 2835 */                                       _jspx_th_html_005foption_005f10.setPageContext(_jspx_page_context);
/* 2836 */                                       _jspx_th_html_005foption_005f10.setParent(_jspx_th_html_005fselect_005f0);
/*      */                                       
/* 2838 */                                       _jspx_th_html_005foption_005f10.setKey(FormatUtil.getString("am.webclient.historydata.period.last1year.text"));
/*      */                                       
/* 2840 */                                       _jspx_th_html_005foption_005f10.setValue("5");
/* 2841 */                                       int _jspx_eval_html_005foption_005f10 = _jspx_th_html_005foption_005f10.doStartTag();
/* 2842 */                                       if (_jspx_th_html_005foption_005f10.doEndTag() == 5) {
/* 2843 */                                         this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10); return;
/*      */                                       }
/*      */                                       
/* 2846 */                                       this._005fjspx_005ftagPool_005fhtml_005foption_0026_005fvalue_005fkey_005fnobody.reuse(_jspx_th_html_005foption_005f10);
/* 2847 */                                       out.write("\n                  ");
/* 2848 */                                       int evalDoAfterBody = _jspx_th_html_005fselect_005f0.doAfterBody();
/* 2849 */                                       if (evalDoAfterBody != 2)
/*      */                                         break;
/*      */                                     }
/* 2852 */                                     if (_jspx_eval_html_005fselect_005f0 != 1) {
/* 2853 */                                       out = _jspx_page_context.popBody();
/*      */                                     }
/*      */                                   }
/* 2856 */                                   if (_jspx_th_html_005fselect_005f0.doEndTag() == 5) {
/* 2857 */                                     this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0); return;
/*      */                                   }
/*      */                                   
/* 2860 */                                   this._005fjspx_005ftagPool_005fhtml_005fselect_0026_005fstyleClass_005fstyle_005fproperty_005fonchange.reuse(_jspx_th_html_005fselect_005f0);
/* 2861 */                                   out.write(" <input type=hidden name=\"resourcename\" value=\"");
/* 2862 */                                   out.print(request.getParameter("resourcename"));
/* 2863 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 2864 */                                   out.print(request.getParameter("resourceid"));
/* 2865 */                                   out.write("\" >\n                    <input type=hidden name=\"reportType\" id=\"reportType\" value=\"\" >\n                  <input type=hidden name=\"defaultView\" value=\"");
/* 2866 */                                   out.print(isdefaultview);
/* 2867 */                                   out.write("\" >\n                  ");
/*      */                                   
/* 2869 */                                   if ((sla != null) && (sla.trim().equals("true"))) {
/* 2870 */                                     out.write("\n                  <input type=hidden name='sla' value='true'>\n                  ");
/*      */                                   }
/* 2872 */                                   out.write(10);
/* 2873 */                                   out.write(9);
/* 2874 */                                   out.write(9);
/* 2875 */                                   if (_jspx_meth_c_005fif_005f0(_jspx_th_html_005fform_005f0, _jspx_page_context))
/*      */                                     return;
/* 2877 */                                   out.write("\n\t\t <input type='hidden' name='per'>\n            </td></tr></table>\n            ");
/* 2878 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f0.doAfterBody();
/* 2879 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2883 */                               if (_jspx_th_html_005fform_005f0.doEndTag() == 5) {
/* 2884 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0); return;
/*      */                               }
/*      */                               
/* 2887 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f0);
/* 2888 */                               out.write(" <br></td>\n            </tr>\n\n\n             <tr>\n          <td  align='left' > ");
/*      */                               
/* 2890 */                               FormTag _jspx_th_html_005fform_005f1 = (FormTag)this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.get(FormTag.class);
/* 2891 */                               _jspx_th_html_005fform_005f1.setPageContext(_jspx_page_context);
/* 2892 */                               _jspx_th_html_005fform_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */                               
/* 2894 */                               _jspx_th_html_005fform_005f1.setAction("/showBussiness.do?method=generateApplicationAvailablity&value=4");
/*      */                               
/* 2896 */                               _jspx_th_html_005fform_005f1.setStyle("Display:inline");
/* 2897 */                               int _jspx_eval_html_005fform_005f1 = _jspx_th_html_005fform_005f1.doStartTag();
/* 2898 */                               if (_jspx_eval_html_005fform_005f1 != 0) {
/*      */                                 for (;;) {
/* 2900 */                                   out.write("\n           <input type=hidden id=\"reportType\" name=\"reportType\" value=\"\" >\n            <table width=\"100%\" class=\"\" height=\"30%\" border=\"0\" cellpadding=\"3\" cellspacing=\"0\" >\n\n\n              <tr>\n                <td height=\"22\" class=\"leftlinksheading\"  >");
/* 2901 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.customtime.text"));
/* 2902 */                                   out.write("</td>\n              </tr>\n              <tr >\n                <td width=\"50%\" height=\"24\"  class=\"columnheading\" >");
/* 2903 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.starttime.text"));
/* 2904 */                                   out.write("\n                </td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"38\"> &nbsp;");
/* 2905 */                                   if (_jspx_meth_html_005ftext_005f0(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2907 */                                   out.write("\n                  <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=startTrigger title=\"");
/* 2908 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 2909 */                                   out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"start\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"startTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT></td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" class=\"columnheading\" width=\"50%\">");
/* 2910 */                                   out.print(FormatUtil.getString("am.webclient.historydata.period.endtime.text"));
/* 2911 */                                   out.write("</td>\n              </tr>\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" >&nbsp;");
/* 2912 */                                   if (_jspx_meth_html_005ftext_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2914 */                                   out.write("\n                 <a href=\"#\" ><IMG src=\"images/calendar-button.gif\" border=\"0\" id=endTrigger title=\"");
/* 2915 */                                   out.print(FormatUtil.getString("am.webclient.common.dateselector.text"));
/* 2916 */                                   out.write("\"></a>\n                  <SCRIPT type=text/javascript>\n                                            Calendar.setup({\n                                        inputField     :    \"end\",     // id of the input field\n                                        ifFormat       :    \"%Y-%m-%d %H:%M\",      // format of the input field\n                                                showsTime          :    true,\n                                        button         :    \"endTrigger\",  // trigger for the calendar (button ID)\n                                                timeFormat     :    \"24\",\n                                        align          :    \"Bl\",           // alignment (defaults to \"Bl\")\n                                        singleClick    :    true\n                                            });\n                                         </SCRIPT> </td>\n              </tr>\n              <input type=hidden name=\"resourcename\" value=\"");
/* 2917 */                                   out.print(request.getParameter("resourcename"));
/* 2918 */                                   out.write("\" >\n                  <input type=hidden name=\"resourceid\" value=\"");
/* 2919 */                                   out.print(request.getParameter("resourceid"));
/* 2920 */                                   out.write("\" >\n                  <input type=hidden name='defaultView' value='");
/* 2921 */                                   out.print(isdefaultview);
/* 2922 */                                   out.write("'>\n                  ");
/*      */                                   
/* 2924 */                                   if ((sla != null) && (sla.trim().equals("true"))) {
/* 2925 */                                     out.write("\n                  <input type=hidden name='sla' value='true'>\n                  ");
/*      */                                   }
/* 2927 */                                   out.write("\n              <tr class=\"whitegrayborder\">\n                <td height=\"24\" align=\"center\" class=\"tablebottom\"> <input type=\"submit\" name=\"show\" value=\"");
/* 2928 */                                   out.print(FormatUtil.getString("am.webclient.historydata.showreport.text"));
/* 2929 */                                   out.write("\" class=\"buttons btn_highlt\" onclick=\"return fnCheckCustomTime(this.form)\">\n                </td>\n              </tr>\n            </table>\n\t\t");
/* 2930 */                                   if (_jspx_meth_c_005fif_005f1(_jspx_th_html_005fform_005f1, _jspx_page_context))
/*      */                                     return;
/* 2932 */                                   out.write("\n            ");
/* 2933 */                                   int evalDoAfterBody = _jspx_th_html_005fform_005f1.doAfterBody();
/* 2934 */                                   if (evalDoAfterBody != 2)
/*      */                                     break;
/*      */                                 }
/*      */                               }
/* 2938 */                               if (_jspx_th_html_005fform_005f1.doEndTag() == 5) {
/* 2939 */                                 this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1); return;
/*      */                               }
/*      */                               
/* 2942 */                               this._005fjspx_005ftagPool_005fhtml_005fform_0026_005fstyle_005faction.reuse(_jspx_th_html_005fform_005f1);
/* 2943 */                               out.write(" </td>\n        </tr>\n      </table></td>\n\n\n      </tr>\n\n<tr>\n<td width='100%' colspan='2'>\n  <form name=\"showReportForm\" action='/showReports.do'>\n<table width=\"100%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n             <input type=\"hidden\" name=\"actionMethod\" value=\"\">\n             <input type=\"hidden\" name=\"method\" value=\"\">\n             <input type=\"hidden\" id=\"period\" name=\"period\" value=\"\"><input type=\"hidden\" name=\"haid\" value=\"\">\n                  <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n                  <input type=hidden name='strTime' value=''>\n                  <input type=hidden name='bsm' value='true'>\n                    <input type=hidden name='endTime' value=''>\n\n\n  <tr >\n <td width=\"95%\" valign='top' >\n\n <br>\n <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"  align=\"left\" class='lrtborder' >\n  <tr>\n\t      <td  width=\"60%\" class=\"tableheadingbborder\" colspan=\"9\" >");
/* 2944 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.slastatistics.text"));
/* 2945 */                               out.write("\n\t      </td>\n\n                <td class=\"tableheadingbborder\" align=\"right\">\n\n\t\t");
/* 2946 */                               if ((sla != null) && (sla.trim().equals("true"))) {
/* 2947 */                                 out.write("\n                        <a href=\"/showBussiness.do?method=generateApplicationAvailablity&defaultView=");
/* 2948 */                                 out.print(isdefaultview.equals("true") ? isdefaultview : "false");
/* 2949 */                                 out.write("\" class=\"staticlinks\">");
/* 2950 */                                 out.print(FormatUtil.getString("am.webclient.manager.all.ba"));
/* 2951 */                                 out.write("</a> &nbsp;&nbsp;&nbsp; |&nbsp;&nbsp;&nbsp;\n\n                        <a href=\"#\" class=\"disabledlink\">");
/* 2952 */                                 out.print(FormatUtil.getString("am.webclient.manager.sla.ba"));
/* 2953 */                                 out.write("</a>\n\n\t\t");
/*      */                               } else {
/* 2955 */                                 out.write("\n                        <a href=\"#\" class=\"disabledlink\">");
/* 2956 */                                 out.print(FormatUtil.getString("am.webclient.manager.all.ba"));
/* 2957 */                                 out.write("</a> &nbsp;&nbsp;&nbsp; |&nbsp;&nbsp;&nbsp;\n\n                        <a href=\"/showBussiness.do?method=generateApplicationAvailablity&sla=true&defaultView=");
/* 2958 */                                 out.print(isdefaultview.equals("true") ? isdefaultview : "false");
/* 2959 */                                 out.write("\"  class=\"staticlinks\">");
/* 2960 */                                 out.print(FormatUtil.getString("am.webclient.manager.sla.ba"));
/* 2961 */                                 out.write("</a>\n\n\n\t\t");
/*      */                               }
/* 2963 */                               out.write("\n\t\t\t<a href=\"javascript:generateReportFile('pdf');\"><img border=\"0\" align=\"top\" title=\"PDF Report\" alt=\"PDF Report\" src=\"../images/icon_pdf.gif\"></a>\n\t\t\t<a href=\"javascript:generateReportFile('csv');\"><img border=\"0\" align=\"top\" title=\"CSV Report\" alt=\"CSV Report\" src=\"../images/icon_csv.gif\"></a>\n                </td>\n\n\t </tr>\n\t </table>\n\n\n <tr>\n\n <table id=\"slaAvailabilityTable\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\"  align=\"left\" class=\"lrbborder\" >\n <tr>\n      <td width=\"16%\" height=\"28\"  class=\"columnheading\">");
/* 2964 */                               out.print(FormatUtil.getString("am.webclient.hometab.monitorgroups.columnheader.name"));
/* 2965 */                               out.write("</td>\n      <td width=\"10%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2966 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.slaname.text"));
/* 2967 */                               out.write("</td>\n      <td width=\"8%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2968 */                               out.print(FormatUtil.getString("am.webclient.manager.slatab.meetssla.text"));
/* 2969 */                               out.write("</td>\n      <td width=\"18%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2970 */                               out.print(FormatUtil.getString("am.webclient.historydata.totaldown.text"));
/* 2971 */                               out.write("</td>\n      <td width=\"8%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2972 */                               out.print(FormatUtil.getString("am.webclient.common.availability.text"));
/* 2973 */                               out.write("%</td>\n\n       <td width=\"18%\" align=\"left\" height=\"28\"  class=\"columnheading\" title='");
/* 2974 */                               out.print(FormatUtil.getString("am.webclient.historydata.mttr.text"));
/* 2975 */                               out.write(39);
/* 2976 */                               out.write(62);
/* 2977 */                               out.print(FormatUtil.getString("am.webclient.historydata.capmttr.text"));
/* 2978 */                               out.write("</td>\n      <td width=\"16%\" align=\"left\" height=\"28\"  class=\"columnheading\" title='");
/* 2979 */                               out.print(FormatUtil.getString("am.webclient.historydata.mtbf.text"));
/* 2980 */                               out.write(39);
/* 2981 */                               out.write(62);
/* 2982 */                               out.print(FormatUtil.getString("am.webclient.historydata.capmtbf.text"));
/* 2983 */                               out.write("</td>\n      <td width=\"13%\" align=\"left\" height=\"28\"  class=\"columnheading\">");
/* 2984 */                               out.print(FormatUtil.getString("am.webclient.manager.troubletickets.text"));
/* 2985 */                               out.write("</td>\n       <td width=\"4%\" align=\"left\" height=\"28\"   colspan='2' class=\"columnheading\">&nbsp;</td>\n\n\n    </tr>\n\n\n");
/*      */                               
/* 2987 */                               ArrayList groupsList = DBUtil.getRows("select DISPLAYNAME,RESOURCEID,PARENTID from AM_HOLISTICAPPLICATION left outer join AM_ManagedObject on AM_ManagedObject.RESOURCEID=AM_HOLISTICAPPLICATION.HAID left outer join AM_PARENTCHILDMAPPER on AM_PARENTCHILDMAPPER.CHILDID=AM_ManagedObject.RESOURCEID order by PARENTID");
/* 2988 */                               groupsList = com.adventnet.appmanager.util.ReportUtil.getGroupOrigin(groupsList, groupsList);
/*      */                               
/* 2990 */                               ArrayList alist = new ArrayList();
/*      */                               
/* 2992 */                               for (int g = 0; g < availdata.size(); g++)
/*      */                               {
/* 2994 */                                 String bgcolor1 = null;
/* 2995 */                                 if (g % 2 == 0)
/*      */                                 {
/* 2997 */                                   bgcolor1 = "class=\"whitegrayborder\"";
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3001 */                                   bgcolor1 = "class=\"yellowgrayborder\"";
/*      */                                 }
/* 3003 */                                 alist = (ArrayList)availdata.get(g);
/* 3004 */                                 String resid = (String)alist.get(0);
/*      */                                 
/* 3006 */                                 String resname = (String)alist.get(1);
/* 3007 */                                 String mttr = (String)alist.get(2);
/* 3008 */                                 String mtbf = (String)alist.get(3);
/* 3009 */                                 String totaldown = (String)alist.get(4);
/* 3010 */                                 String avblity = (String)alist.get(5);
/* 3011 */                                 String sid = (String)alist.get(6);
/* 3012 */                                 String sname = (String)alist.get(7);
/*      */                                 
/*      */ 
/* 3015 */                                 String availop = (String)alist.get(8);
/* 3016 */                                 String availval = (String)alist.get(9);
/* 3017 */                                 Properties props = (Properties)alist.get(10);
/* 3018 */                                 String availsla = props.getProperty("SLA");
/* 3019 */                                 String per = props.getProperty("period");
/*      */                                 
/* 3021 */                                 String tickop = (String)alist.get(11);
/* 3022 */                                 String tickval = (String)alist.get(12);
/* 3023 */                                 String tickdur = (String)alist.get(13);
/* 3024 */                                 ArrayList a1 = (ArrayList)alist.get(14);
/* 3025 */                                 String ticksla = "";
/* 3026 */                                 String ttvalue = "";
/*      */                                 
/* 3028 */                                 for (int h = 0; h < a1.size(); h++)
/*      */                                 {
/* 3030 */                                   ttvalue = (String)a1.get(1);
/* 3031 */                                   ticksla = (String)a1.get(3);
/*      */                                 }
/*      */                                 
/* 3034 */                                 out.write("\n         <tr ");
/* 3035 */                                 out.print(bgcolor1);
/* 3036 */                                 out.write(" class=\"alarm-links \" onmouseout=\"this.className='alarmheader'\" onmouseover=\"this.className='alarmHeaderHover'\" class=\"alarmheader\">\n\t\t ");
/*      */                                 
/* 3038 */                                 String title = resname;
/* 3039 */                                 if (com.adventnet.appmanager.util.Constants.subGroupsEnabled.equals("true"))
/*      */                                 {
/* 3041 */                                   for (int i = 0; i < groupsList.size(); i++)
/*      */                                   {
/* 3043 */                                     String targetID = (String)((ArrayList)groupsList.get(i)).get(1);
/* 3044 */                                     if (resid.equals(targetID))
/*      */                                     {
/* 3046 */                                       title = (String)((ArrayList)groupsList.get(i)).get(0);
/*      */                                       
/* 3048 */                                       break;
/*      */                                     }
/*      */                                   }
/*      */                                 }
/* 3052 */                                 if (EnterpriseUtil.isAdminServer())
/*      */                                 {
/* 3054 */                                   if (CommDBUtil.getManagedServerNameWithPort(resid).equals("Admin Server"))
/*      */                                   {
/* 3056 */                                     title = FormatUtil.getString("am.webclient.gettingstarted.adminserver.text");
/* 3057 */                                     resname = resname + "_" + title;
/*      */                                   }
/*      */                                   else
/*      */                                   {
/* 3061 */                                     title = FormatUtil.getString("am.webclient.managedserver.tooltip.monitorgroupname", new String[] { CommDBUtil.getManagedServerNameWithPort(resid) });
/* 3062 */                                     resname = resname + "_" + CommDBUtil.getManagedServerNameWithPort(resid);
/*      */                                   }
/*      */                                 }
/*      */                                 
/* 3066 */                                 out.write("\n                        <td title=\"");
/* 3067 */                                 out.print(title);
/* 3068 */                                 out.write("\" class=\"yellowgrayborder\">");
/* 3069 */                                 out.print(resname);
/* 3070 */                                 out.write("</td>\n                        ");
/*      */                                 
/* 3072 */                                 if (!sname.equals("not set"))
/*      */                                 {
/* 3074 */                                   out.write("\n                            <td align=\"left\" class='yellowgrayborder' ><a href=\"javascript:void(0)\" onClick=\"javascript:window.open('../jsp/Popup_SLA.jsp?sid=");
/* 3075 */                                   out.print(sid);
/* 3076 */                                   out.write("','','resizable=yes,width=390,height=170')\" class=\"alarm-links \">");
/* 3077 */                                   out.print(FormatUtil.getString(sname));
/* 3078 */                                   out.write("</a></td>\n                        ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3082 */                                   out.write("\n                            <td align=\"left\" class='yellowgrayborder' >");
/* 3083 */                                   out.print(FormatUtil.getString(sname));
/* 3084 */                                   out.write("</td>\n                        ");
/*      */                                 }
/* 3086 */                                 out.write("\n                    ");
/* 3087 */                                 if ((availsla == null) || ((avblity != null) && (avblity.equals("NA"))))
/*      */                                 {
/* 3089 */                                   out.write("\n       \t      \t\t\t\t<td align=\"left\" class='yellowgrayborder'><a href='javascript:void(0)' onclick=\"javascript:fnshowData('");
/* 3090 */                                   out.print(resid);
/* 3091 */                                   out.write(39);
/* 3092 */                                   out.write(44);
/* 3093 */                                   out.write(39);
/* 3094 */                                   out.print(per);
/* 3095 */                                   out.write("')\" class=\"alarm-links \">");
/* 3096 */                                   out.print(avblity);
/* 3097 */                                   out.write("</a></td>\n       \t\t\t\t");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3101 */                                   out.write("\n        ");
/*      */                                   
/* 3103 */                                   if ((availsla.equals("PASS")) && (ticksla.equals("PASS")) && (!sname.equals("not set")))
/*      */                                   {
/*      */ 
/* 3106 */                                     out.write("\n           <td align=\"left\" class='yellowgrayborder' ><img border=\"0\" src=\"/images/icon_tickmark.gif\"></td>\n           ");
/*      */ 
/*      */                                   }
/* 3109 */                                   else if ((availsla.equals("FAIL")) || (ticksla.equals("FAIL")))
/*      */                                   {
/*      */ 
/* 3112 */                                     out.write("\n                   <td align=\"left\" class='yellowgrayborder' ><img border=\"0\" src=\"/images/cross.gif\"></td>\n                     ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 3118 */                                     out.write("\n                   <td align=\"left\" class='yellowgrayborder' ><img border=\"0\" src=\"/images/questionmark.gif\"></td>\n                   ");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/* 3123 */                                 out.write("\n       <td class=\"yellowgrayborder\">");
/* 3124 */                                 out.print(totaldown);
/* 3125 */                                 out.write("</td>\n       ");
/* 3126 */                                 if ((availsla == null) || ((avblity != null) && (avblity.equals("NA"))))
/*      */                                 {
/* 3128 */                                   out.write("\n          \t<td align=\"left\" class=\"yellowgrayborder\"><a href='javascript:void(0)' onclick=\"javascript:fnshowData('");
/* 3129 */                                   out.print(resid);
/* 3130 */                                   out.write(39);
/* 3131 */                                   out.write(44);
/* 3132 */                                   out.write(39);
/* 3133 */                                   out.print(per);
/* 3134 */                                   out.write("')\" class=\"alarm-links \">");
/* 3135 */                                   out.print(avblity);
/* 3136 */                                   out.write("</a></td>\n       ");
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 3140 */                                   out.write("\n       ");
/*      */                                   
/* 3142 */                                   if (availsla.equals("PASS"))
/*      */                                   {
/*      */ 
/* 3145 */                                     out.write("\n      <td align=\"left\" class=\"yellowgrayborder\"><a href='javascript:void(0)' onclick=\"javascript:fnshowData('");
/* 3146 */                                     out.print(resid);
/* 3147 */                                     out.write(39);
/* 3148 */                                     out.write(44);
/* 3149 */                                     out.write(39);
/* 3150 */                                     out.print(per);
/* 3151 */                                     out.write("')\" class=\"alarm-links \">");
/* 3152 */                                     out.print(avblity);
/* 3153 */                                     out.write("</a></td>\n      ");
/*      */ 
/*      */                                   }
/*      */                                   else
/*      */                                   {
/*      */ 
/* 3159 */                                     out.write("\n           <td align=\"left\"  class=\"yellowgrayborder\"><span class=\"navigation\" ><a style=\"color:#595959; background-color:#FFAAAA;\" href='javascript:void(0)' onclick=\"javascript:fnshowData('");
/* 3160 */                                     out.print(resid);
/* 3161 */                                     out.write(39);
/* 3162 */                                     out.write(44);
/* 3163 */                                     out.write(39);
/* 3164 */                                     out.print(per);
/* 3165 */                                     out.write("')\" class=\"alarm-links \">");
/* 3166 */                                     out.print(avblity);
/* 3167 */                                     out.write("</a></span></td>\n          ");
/*      */                                   }
/*      */                                 }
/*      */                                 
/*      */ 
/* 3172 */                                 out.write("\n\n\n\n       <td class=\"yellowgrayborder\">");
/* 3173 */                                 out.print(mttr);
/* 3174 */                                 out.write("</td>\n       <td align=\"left\" class='yellowgrayborder' >");
/* 3175 */                                 out.print(mtbf);
/* 3176 */                                 out.write("</td>\n       ");
/*      */                                 
/* 3178 */                                 if (ticksla.equals("PASS"))
/*      */                                 {
/*      */ 
/* 3181 */                                   out.write("\n      <td align=\"left\" class=\"yellowgrayborder\"><a href='javascript:void(0)' onclick=\"javascript:fnshowData1('");
/* 3182 */                                   out.print(resid);
/* 3183 */                                   out.write(39);
/* 3184 */                                   out.write(44);
/* 3185 */                                   out.write(39);
/* 3186 */                                   out.print(per);
/* 3187 */                                   out.write("')\" class=\"alarm-links \">");
/* 3188 */                                   out.print(ttvalue);
/* 3189 */                                   out.write("</a></td>\n      ");
/*      */ 
/*      */                                 }
/*      */                                 else
/*      */                                 {
/*      */ 
/* 3195 */                                   out.write("\n           <td align=\"left\"  class=\"yellowgrayborder\"><span class=\"navigation\" ><a style=\"color:#595959; background-color:#FFAAAA;\" href='javascript:void(0)' onclick=\"javascript:fnshowData1('");
/* 3196 */                                   out.print(resid);
/* 3197 */                                   out.write(39);
/* 3198 */                                   out.write(44);
/* 3199 */                                   out.write(39);
/* 3200 */                                   out.print(per);
/* 3201 */                                   out.write("')\" class=\"alarm-links \">");
/* 3202 */                                   out.print(ttvalue);
/* 3203 */                                   out.write("</a></span></td>\n          ");
/*      */                                 }
/*      */                                 
/*      */ 
/* 3207 */                                 out.write("\n\n               <td align=\"left\" class=\"yellowgrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getAvailabilityData&resourceid=");
/* 3208 */                                 out.print(resid);
/* 3209 */                                 out.write("&period=1')\" class=\"ResourceName1\"><img border=\"0\" src=\"../images/icon_7daysdata.gif\" title=\"");
/* 3210 */                                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3211 */                                 out.write("\"></a></td>\n         </tr>\n         ");
/*      */                               }
/*      */                             } catch (NullPointerException exx) {}catch (Exception e) {
/* 3214 */                               e.printStackTrace();
/*      */                             }
/* 3216 */                             out.write(32);
/* 3217 */                             out.write("\n</table></form>\n</tr>\n\n</td></tr></table></td></tr>\n");
/* 3218 */                             int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 3219 */                             if (evalDoAfterBody != 2)
/*      */                               break;
/*      */                           }
/* 3222 */                           if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 3223 */                             out = _jspx_page_context.popBody();
/*      */                           }
/*      */                         }
/* 3226 */                         if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 3227 */                           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */                         }
/*      */                         
/* 3230 */                         this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 3231 */                         out.write(10);
/* 3232 */                         out.write(10);
/* 3233 */                         if (_jspx_meth_tiles_005fput_005f3(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3235 */                         out.write(10);
/* 3236 */                         if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */                           return;
/* 3238 */                         out.write(10);
/* 3239 */                         int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 3240 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3244 */                     if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 3245 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */                     }
/*      */                     else {
/* 3248 */                       this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 3249 */                       out.write(10);
/*      */                     }
/* 3251 */                   } } } } } } } } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 3252 */         out = _jspx_out;
/* 3253 */         if ((out != null) && (out.getBufferSize() != 0))
/* 3254 */           try { out.clearBuffer(); } catch (IOException e) {}
/* 3255 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 3258 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f0(JspTag _jspx_th_html_005fform_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3264 */     PageContext pageContext = _jspx_page_context;
/* 3265 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3267 */     IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3268 */     _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 3269 */     _jspx_th_c_005fif_005f0.setParent((Tag)_jspx_th_html_005fform_005f0);
/*      */     
/* 3271 */     _jspx_th_c_005fif_005f0.setTest("${PRINTER_FRIENDLY != null && PRINTER_FRIENDLY == 'true'}");
/* 3272 */     int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 3273 */     if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */       for (;;) {
/* 3275 */         out.write("\n                 <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n\t\t");
/* 3276 */         int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 3277 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3281 */     if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 3282 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3283 */       return true;
/*      */     }
/* 3285 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 3286 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f0(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3291 */     PageContext pageContext = _jspx_page_context;
/* 3292 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3294 */     TextTag _jspx_th_html_005ftext_005f0 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3295 */     _jspx_th_html_005ftext_005f0.setPageContext(_jspx_page_context);
/* 3296 */     _jspx_th_html_005ftext_005f0.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3298 */     _jspx_th_html_005ftext_005f0.setSize("15");
/*      */     
/* 3300 */     _jspx_th_html_005ftext_005f0.setProperty("startDate");
/*      */     
/* 3302 */     _jspx_th_html_005ftext_005f0.setStyleId("start");
/*      */     
/* 3304 */     _jspx_th_html_005ftext_005f0.setReadonly(true);
/*      */     
/* 3306 */     _jspx_th_html_005ftext_005f0.setOnchange("javascript:fnSetStartTime(this.value)");
/* 3307 */     int _jspx_eval_html_005ftext_005f0 = _jspx_th_html_005ftext_005f0.doStartTag();
/* 3308 */     if (_jspx_th_html_005ftext_005f0.doEndTag() == 5) {
/* 3309 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3310 */       return true;
/*      */     }
/* 3312 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f0);
/* 3313 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_html_005ftext_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3318 */     PageContext pageContext = _jspx_page_context;
/* 3319 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3321 */     TextTag _jspx_th_html_005ftext_005f1 = (TextTag)this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.get(TextTag.class);
/* 3322 */     _jspx_th_html_005ftext_005f1.setPageContext(_jspx_page_context);
/* 3323 */     _jspx_th_html_005ftext_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3325 */     _jspx_th_html_005ftext_005f1.setProperty("endDate");
/*      */     
/* 3327 */     _jspx_th_html_005ftext_005f1.setSize("15");
/*      */     
/* 3329 */     _jspx_th_html_005ftext_005f1.setStyleId("end");
/*      */     
/* 3331 */     _jspx_th_html_005ftext_005f1.setReadonly(true);
/*      */     
/* 3333 */     _jspx_th_html_005ftext_005f1.setOnchange("javascript:fnSetEndTime(this.value)");
/* 3334 */     int _jspx_eval_html_005ftext_005f1 = _jspx_th_html_005ftext_005f1.doStartTag();
/* 3335 */     if (_jspx_th_html_005ftext_005f1.doEndTag() == 5) {
/* 3336 */       this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3337 */       return true;
/*      */     }
/* 3339 */     this._005fjspx_005ftagPool_005fhtml_005ftext_0026_005fstyleId_005fsize_005freadonly_005fproperty_005fonchange_005fnobody.reuse(_jspx_th_html_005ftext_005f1);
/* 3340 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fif_005f1(JspTag _jspx_th_html_005fform_005f1, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3345 */     PageContext pageContext = _jspx_page_context;
/* 3346 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3348 */     IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3349 */     _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 3350 */     _jspx_th_c_005fif_005f1.setParent((Tag)_jspx_th_html_005fform_005f1);
/*      */     
/* 3352 */     _jspx_th_c_005fif_005f1.setTest("${PRINTER_FRIENDLY != null && PRINTER_FRIENDLY == 'true'}");
/* 3353 */     int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 3354 */     if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */       for (;;) {
/* 3356 */         out.write("\n                 <input type='hidden' name='PRINTER_FRIENDLY' value='true'>\n\t\t");
/* 3357 */         int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 3358 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 3362 */     if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 3363 */       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3364 */       return true;
/*      */     }
/* 3366 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 3367 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f3(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3372 */     PageContext pageContext = _jspx_page_context;
/* 3373 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3375 */     PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3376 */     _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 3377 */     _jspx_th_tiles_005fput_005f3.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3379 */     _jspx_th_tiles_005fput_005f3.setName("HelpContent");
/*      */     
/* 3381 */     _jspx_th_tiles_005fput_005f3.setValue("/jsp/test.jsp");
/* 3382 */     int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 3383 */     if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 3384 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3385 */       return true;
/*      */     }
/* 3387 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f3);
/* 3388 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 3393 */     PageContext pageContext = _jspx_page_context;
/* 3394 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 3396 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 3397 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 3398 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 3400 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 3402 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 3403 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 3404 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 3405 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3406 */       return true;
/*      */     }
/* 3408 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 3409 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\BussinessApplication_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */