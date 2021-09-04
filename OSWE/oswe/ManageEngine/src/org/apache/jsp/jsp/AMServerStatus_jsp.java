/*      */ package org.apache.jsp.jsp;
/*      */ 
/*      */ import com.adventnet.appmanager.bean.CpuGraph;
/*      */ import com.adventnet.appmanager.bean.HostResourceBean;
/*      */ import com.adventnet.appmanager.bean.PerformanceBean;
/*      */ import com.adventnet.appmanager.client.resourcemanagement.ManagedApplication;
/*      */ import com.adventnet.appmanager.db.AMConnectionPool;
/*      */ import com.adventnet.appmanager.dbcache.ConfMonitorConfiguration;
/*      */ import com.adventnet.appmanager.server.framework.AMAutomaticPortChanger;
/*      */ import com.adventnet.appmanager.server.mssql.bean.MsSqlGraphs;
/*      */ import com.adventnet.appmanager.server.mysql.bean.MySqlGraphs;
/*      */ import com.adventnet.appmanager.util.DBUtil;
/*      */ import com.adventnet.appmanager.util.EnterpriseUtil;
/*      */ import com.adventnet.appmanager.util.FormatUtil;
/*      */ import com.adventnet.appmanager.util.OEMUtil;
/*      */ import com.adventnet.appmanager.util.UserUtil;
/*      */ import com.adventnet.awolf.tags.TimeChart;
/*      */ import java.io.File;
/*      */ import java.net.InetAddress;
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
/*      */ import org.apache.struts.taglib.logic.PresentTag;
/*      */ import org.apache.struts.taglib.tiles.InsertTag;
/*      */ import org.apache.struts.taglib.tiles.PutTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.ChooseTag;
/*      */ import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.IfTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.OutTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.SetTag;
/*      */ import org.apache.taglibs.standard.tag.el.core.WhenTag;
/*      */ import org.apache.taglibs.standard.tag.el.fmt.MessageTag;
/*      */ 
/*      */ public final class AMServerStatus_jsp extends org.apache.jasper.runtime.HttpJspBase implements org.apache.jasper.runtime.JspSourceDependent
/*      */ {
/*      */   public static final String NAME_SEPARATOR = ">";
/*   59 */   public static final String ALERTCONFIG_TEXT = FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT");
/*      */   public static final String STATUS_SEPARATOR = "#";
/*      */   public static final String MESSAGE_SEPARATOR = "MESSAGE";
/*   62 */   public static final String MGSTR = FormatUtil.getString("am.webclient.common.util.MGSTR");
/*   63 */   public static final String WEBMG = FormatUtil.getString("am.webclient.common.util.WEBMG");
/*   64 */   public static final String MGSTRs = FormatUtil.getString("am.webclient.common.util.MGSTRs");
/*      */   public static final String MISTR = "Monitor";
/*      */   public static final String MISTRs = "Monitors";
/*      */   public static final String RCA_SEPARATOR = " --> ";
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct)
/*      */   {
/*   71 */     return getOptions(value, text, tableName, distinct, "");
/*      */   }
/*      */   
/*      */   public String getOptions(String value, String text, String tableName, boolean distinct, String condition)
/*      */   {
/*   76 */     ArrayList list = null;
/*   77 */     StringBuffer sbf = new StringBuffer();
/*   78 */     ManagedApplication mo = new ManagedApplication();
/*   79 */     if (distinct)
/*      */     {
/*   81 */       list = mo.getRows("SELECT distinct(" + value + ") FROM " + tableName);
/*      */     }
/*      */     else
/*      */     {
/*   85 */       list = mo.getRows("SELECT " + value + "," + text + " FROM " + tableName + " " + condition);
/*      */     }
/*      */     
/*   88 */     for (int i = 0; i < list.size(); i++)
/*      */     {
/*   90 */       ArrayList row = (ArrayList)list.get(i);
/*   91 */       sbf.append("<option value='" + row.get(0) + "'>");
/*   92 */       if (distinct) {
/*   93 */         sbf.append(row.get(0));
/*      */       } else
/*   95 */         sbf.append(row.get(1));
/*   96 */       sbf.append("</option>");
/*      */     }
/*      */     
/*   99 */     return sbf.toString(); }
/*      */   
/*  101 */   long j = 0L;
/*      */   
/*      */   private String getSeverityImageForAvailability(Object severity) {
/*  104 */     if (severity == null)
/*      */     {
/*  106 */       return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  108 */     if (severity.equals("5"))
/*      */     {
/*  110 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  112 */     if (severity.equals("1"))
/*      */     {
/*  114 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  119 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImage(Object severity)
/*      */   {
/*  126 */     if (severity == null)
/*      */     {
/*  128 */       return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown") + "\" align=\"absmiddle\">";
/*      */     }
/*  130 */     if (severity.equals("1"))
/*      */     {
/*  132 */       return "<img border=\"0\" src=\"/images/icon_critical.gif\" alt=\"Critical\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical") + "\" align=\"absmiddle\">";
/*      */     }
/*  134 */     if (severity.equals("4"))
/*      */     {
/*  136 */       return "<img border=\"0\" src=\"/images/icon_warning.gif\" alt=\"Warning\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning") + "\" align=\"absmiddle\">";
/*      */     }
/*  138 */     if (severity.equals("5"))
/*      */     {
/*  140 */       return "<img border=\"0\" src=\"/images/icon_clear.gif\"  alt=\"Clear\" title=\"" + FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear") + "\" align=\"absmiddle\" >";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  145 */     return "<img border=\"0\" src=\"/images/icon_unknown_status.gif\" alt=\"Unknown\" title=\"Unknown\" align=\"absmiddle\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityStateForAvailability(Object severity)
/*      */   {
/*  151 */     if (severity == null)
/*      */     {
/*  153 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  155 */     if (severity.equals("5"))
/*      */     {
/*  157 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.up");
/*      */     }
/*  159 */     if (severity.equals("1"))
/*      */     {
/*  161 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.down");
/*      */     }
/*      */     
/*      */ 
/*  165 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityState(Object severity)
/*      */   {
/*  171 */     if (severity == null)
/*      */     {
/*  173 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */     }
/*  175 */     if (severity.equals("1"))
/*      */     {
/*  177 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.critical");
/*      */     }
/*  179 */     if (severity.equals("4"))
/*      */     {
/*  181 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.warning");
/*      */     }
/*  183 */     if (severity.equals("5"))
/*      */     {
/*  185 */       return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.clear");
/*      */     }
/*      */     
/*      */ 
/*  189 */     return FormatUtil.getString("am.webclient.hometab.monitorssnapshot.key.unknown");
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImage(int severity)
/*      */   {
/*  195 */     return getSeverityImage("" + severity);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForAvailability(int severity)
/*      */   {
/*  201 */     if (severity == 5)
/*      */     {
/*  203 */       return "<img border=\"0\" src=\"/images/icon_availability_up.gif\"  alt=\"Up\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  205 */     if (severity == 1)
/*      */     {
/*  207 */       return "<img border=\"0\" src=\"/images/icon_availability_down.gif\" alt=\"Down\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  212 */     return "<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" alt=\"Unknown\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForConfMonitor(String severity, boolean isAvailability)
/*      */   {
/*  218 */     if (severity == null)
/*      */     {
/*  220 */       return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */     }
/*  222 */     if (severity.equals("5"))
/*      */     {
/*  224 */       if (isAvailability) {
/*  225 */         return "<img border=\"0\" src=\"/images/icon_up_conf.png\" alt='" + FormatUtil.getString("Up") + "' >";
/*      */       }
/*      */       
/*  228 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_clear.png\" alt='" + FormatUtil.getString("Clear") + "' >";
/*      */     }
/*      */     
/*  231 */     if ((severity.equals("4")) && (!isAvailability))
/*      */     {
/*  233 */       return "<img border=\"0\" src=\"/images/icon_warning_conf.png\" alt=\"Warning\">";
/*      */     }
/*  235 */     if (severity.equals("1"))
/*      */     {
/*  237 */       if (isAvailability) {
/*  238 */         return "<img border=\"0\" src=\"/images/icon_down_conf.png\" alt=\"Down\">";
/*      */       }
/*      */       
/*  241 */       return "<img border=\"0\" src=\"/images/icon_conf_hlt_critical.png\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  248 */     return "<img border=\"0\" src=\"/images/icon_unknown_conf.png\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealth(String severity)
/*      */   {
/*  255 */     if (severity == null)
/*      */     {
/*  257 */       return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  259 */     if (severity.equals("5"))
/*      */     {
/*  261 */       return "<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  263 */     if (severity.equals("4"))
/*      */     {
/*  265 */       return "<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  267 */     if (severity.equals("1"))
/*      */     {
/*  269 */       return "<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  274 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */   private String getas400SeverityImageForHealth(String severity)
/*      */   {
/*  280 */     if (severity == null)
/*      */     {
/*  282 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  284 */     if (severity.equals("5"))
/*      */     {
/*  286 */       return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\"  name=\"Image" + ++this.j + "\">";
/*      */     }
/*  288 */     if (severity.equals("4"))
/*      */     {
/*  290 */       return "<img border=\"0\" src=\"/images/icon_as400health_warning.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*  292 */     if (severity.equals("1"))
/*      */     {
/*  294 */       return "<img border=\"0\" src=\"/images/icon_as400health_critical.gif\" name=\"Image" + ++this.j + "\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  299 */     return "<img border=\"0\" src=\"/images/icon_as400health_clear.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getSeverityImageForHealthWithoutMouseOver(String severity)
/*      */   {
/*  306 */     if (severity == null)
/*      */     {
/*  308 */       return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */     }
/*  310 */     if (severity.equals("5"))
/*      */     {
/*  312 */       return "<img border=\"0\" src=\"/images/icon_health_clear_nm.gif\" alt=\"Clear\" >";
/*      */     }
/*  314 */     if (severity.equals("4"))
/*      */     {
/*  316 */       return "<img border=\"0\" src=\"/images/icon_health_warning_nm.gif\" alt=\"Warning\">";
/*      */     }
/*  318 */     if (severity.equals("1"))
/*      */     {
/*  320 */       return "<img border=\"0\" src=\"/images/icon_health_critical_nm.gif\" alt=\"Critical\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  325 */     return "<img border=\"0\" src=\"/images/icon_health_unknown_nm.gif\" alt=\"Unknown\">";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String getSearchStrip(String map)
/*      */   {
/*  333 */     StringBuffer out = new StringBuffer();
/*  334 */     out.append("<form action=\"/Search.do\" style=\"display:inline;\" >");
/*  335 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"breadcrumbs\">");
/*  336 */     out.append("<tr class=\"breadcrumbs\"> ");
/*  337 */     out.append("  <td width=\"72%\" height=\"20\">&nbsp;&nbsp;&nbsp;&nbsp;" + map + "&nbsp; &nbsp;&nbsp;</td>");
/*  338 */     out.append("  <td width=\"9%\"> <input name=\"query\" type=\"text\" class=\"formtextsearch\" size=\"15\"></td>");
/*  339 */     out.append("  <td width=\"5%\"> &nbsp; <input name=\"Submit\" type=\"submit\" class=\"buttons\" value=\"Find\"></td>");
/*  340 */     out.append("</tr>");
/*  341 */     out.append("</form></table>");
/*  342 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String formatNumberForDotNet(String val)
/*      */   {
/*  349 */     if (val == null)
/*      */     {
/*  351 */       return "-";
/*      */     }
/*      */     
/*  354 */     String ret = FormatUtil.formatNumber(val);
/*  355 */     String troubleshootlink = OEMUtil.getOEMString("company.troubleshoot.link");
/*  356 */     if (ret.indexOf("-1") != -1)
/*      */     {
/*      */ 
/*  359 */       return "- &nbsp;<a class=\"staticlinks\" href=\"http://" + troubleshootlink + "#m44\" target=\"_blank\">" + FormatUtil.getString("am.webclient.dotnet.troubleshoot") + "</a>";
/*      */     }
/*      */     
/*      */ 
/*  363 */     return ret;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHTMLTable(String[] headers, List tableData, String link, String deleteLink)
/*      */   {
/*  371 */     StringBuffer out = new StringBuffer();
/*  372 */     out.append("<table align=\"center\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\"  border=\"0\">");
/*  373 */     out.append("<tr>");
/*  374 */     for (int i = 0; i < headers.length; i++)
/*      */     {
/*  376 */       out.append("<td valign=\"center\"height=\"28\" bgcolor=\"ACD5FE\" class=\"columnheading\">" + headers[i] + "</td>");
/*      */     }
/*  378 */     out.append("</tr>");
/*  379 */     for (int j = 0; j < tableData.size(); j++)
/*      */     {
/*      */ 
/*      */ 
/*  383 */       if (j % 2 == 0)
/*      */       {
/*  385 */         out.append("<tr class=\"whitegrayborder\">");
/*      */       }
/*      */       else
/*      */       {
/*  389 */         out.append("<tr class=\"yellowgrayborder\">");
/*      */       }
/*      */       
/*  392 */       List rowVector = (List)tableData.get(j);
/*      */       
/*  394 */       for (int k = 0; k < rowVector.size(); k++)
/*      */       {
/*      */ 
/*  397 */         out.append("<td height=\"22\" >" + rowVector.get(k) + "</td>");
/*      */       }
/*      */       
/*      */ 
/*  401 */       out.append("</tr>");
/*      */     }
/*  403 */     out.append("</table>");
/*  404 */     out.append("<table align=\"center\" width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"tablebottom\">");
/*  405 */     out.append("<tr>");
/*  406 */     out.append("<td width=\"72%\" height=\"26\" ><A HREF=\"" + deleteLink + "\" class=\"staticlinks\">Delete</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href=\"" + link + "\" class=\"staticlinks\">Add New</a>&nbsp;&nbsp;</td>");
/*  407 */     out.append("</tr>");
/*  408 */     out.append("</table>");
/*  409 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getSingleColumnDisplay(String header, Vector tableColumns)
/*      */   {
/*  415 */     StringBuffer out = new StringBuffer();
/*  416 */     out.append("<table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
/*  417 */     out.append("<table width=\"95%\" height=\"5\" cellpadding=\"5\" cellspacing=\"5\" class=\"lrbborder\">");
/*  418 */     out.append("<tr>");
/*  419 */     out.append("<td align=\"center\"> <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrborder\">");
/*  420 */     out.append("<tr>");
/*  421 */     out.append("<td width=\"3%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> <input type=\"checkbox\" name=\"maincheckbox\" value=\"checkbox\"></td>");
/*  422 */     out.append("<td width=\"15%\" bgcolor=\"ACD5FE\" class=\"columnheading\"> Name </td>");
/*  423 */     out.append("</tr>");
/*  424 */     for (int k = 0; k < tableColumns.size(); k++)
/*      */     {
/*      */ 
/*  427 */       out.append("<tr>");
/*  428 */       out.append("<td class=\"yellowgrayborder\"><input type=\"checkbox\" name=\"checkbox" + k + "\" value=\"checkbox\"></td>");
/*  429 */       out.append("<td height=\"22\" class=\"yellowgrayborder\">" + tableColumns.elementAt(k) + "</td>");
/*  430 */       out.append("</tr>");
/*      */     }
/*      */     
/*  433 */     out.append("</table>");
/*  434 */     out.append("</table>");
/*  435 */     return out.toString();
/*      */   }
/*      */   
/*      */   private String getAvailabilityImage(String severity)
/*      */   {
/*  440 */     if (severity.equals("0"))
/*      */     {
/*  442 */       return "<img src=\"/images/icon_critical.gif\" alt=\"Critical\" border=0 >";
/*      */     }
/*      */     
/*      */ 
/*  446 */     return "<img src=\"/images/icon_clear.gif\" alt=\"Clear\"  border=0>";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNotes(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String quickNote, HttpSession session)
/*      */   {
/*  453 */     return getQuickLinksAndNotes(quickLinkHeader, quickLinkText, quickLink, null, null, quickNote, session);
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
/*  466 */     StringBuffer out = new StringBuffer();
/*  467 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  468 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  470 */       out.append("<tr>");
/*  471 */       out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "d,.mfnjh.mdfnh.m,dfnh,.dfmn</td>");
/*  472 */       out.append("</tr>");
/*      */       
/*      */ 
/*  475 */       for (int i = 0; i < quickLinkText.size(); i++)
/*      */       {
/*  477 */         String borderclass = "";
/*      */         
/*      */ 
/*  480 */         borderclass = "class=\"leftlinkstd\"";
/*      */         
/*  482 */         out.append("<tr>");
/*      */         
/*  484 */         out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  485 */         out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"staticlinks\">" + (String)quickLinkText.get(i) + "</a></td>");
/*  486 */         out.append("</tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  492 */     out.append("</table><br>");
/*  493 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  494 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  496 */       List sLinks = secondLevelOfLinks[0];
/*  497 */       List sText = secondLevelOfLinks[1];
/*  498 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  501 */         out.append("<tr>");
/*  502 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  503 */         out.append("</tr>");
/*  504 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  506 */           String borderclass = "";
/*      */           
/*      */ 
/*  509 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  511 */           out.append("<tr>");
/*      */           
/*  513 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  514 */           if (sLinks.get(i).toString().length() == 0) {
/*  515 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  518 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"staticlinks\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  520 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  524 */     out.append("</table>");
/*  525 */     return out.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public String getQuickLinksAndNote(String quickLinkHeader, ArrayList quickLinkText, ArrayList quickLink, String secondLevelLinkTitle, List[] secondLevelOfLinks, String quickNote, HttpSession session, HttpServletRequest request)
/*      */   {
/*  532 */     StringBuffer out = new StringBuffer();
/*  533 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  534 */     if ((quickLinkText != null) && (quickLink != null) && (quickLinkText.size() == quickLink.size()))
/*      */     {
/*  536 */       if ((request.isUserInRole("DEMO")) || (request.isUserInRole("ADMIN")) || (request.isUserInRole("ENTERPRISEADMIN")))
/*      */       {
/*  538 */         out.append("<tr>");
/*  539 */         out.append("<td   class=\"leftlinksheading\">" + quickLinkHeader + "</td>");
/*  540 */         out.append("</tr>");
/*      */         
/*      */ 
/*      */ 
/*  544 */         for (int i = 0; i < quickLinkText.size(); i++)
/*      */         {
/*  546 */           String borderclass = "";
/*      */           
/*      */ 
/*  549 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  551 */           out.append("<tr>");
/*      */           
/*  553 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  554 */           if (((String)quickLinkText.get(i)).indexOf("a href") == -1) {
/*  555 */             out.append("<a href=\"" + (String)quickLink.get(i) + "\" class=\"new-left-links\">" + (String)quickLinkText.get(i) + "</a>");
/*      */           }
/*      */           else {
/*  558 */             out.append((String)quickLinkText.get(i));
/*      */           }
/*      */           
/*  561 */           out.append("</td></tr>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  566 */     out.append("</table><br>");
/*  567 */     out.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\">");
/*  568 */     if ((secondLevelOfLinks != null) && (secondLevelLinkTitle != null))
/*      */     {
/*  570 */       List sLinks = secondLevelOfLinks[0];
/*  571 */       List sText = secondLevelOfLinks[1];
/*  572 */       if ((sText != null) && (sLinks != null) && (sLinks.size() == sText.size()))
/*      */       {
/*      */ 
/*  575 */         out.append("<tr>");
/*  576 */         out.append("<td   class=\"leftlinksheading\">" + secondLevelLinkTitle + "</td>");
/*  577 */         out.append("</tr>");
/*  578 */         for (int i = 0; i < sText.size(); i++)
/*      */         {
/*  580 */           String borderclass = "";
/*      */           
/*      */ 
/*  583 */           borderclass = "class=\"leftlinkstd\"";
/*      */           
/*  585 */           out.append("<tr>");
/*      */           
/*  587 */           out.append("<td width=\"81%\" height=\"21\" " + borderclass + ">");
/*  588 */           if (sLinks.get(i).toString().length() == 0) {
/*  589 */             out.append((String)sText.get(i) + "</td>");
/*      */           }
/*      */           else {
/*  592 */             out.append("<a href=\"" + (String)sLinks.get(i) + "\" class=\"new-left-links\">" + (String)sText.get(i) + "</a></td>");
/*      */           }
/*  594 */           out.append("</tr>");
/*      */         }
/*      */       }
/*      */     }
/*  598 */     out.append("</table>");
/*  599 */     return out.toString();
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
/*  612 */     switch (status)
/*      */     {
/*      */     case 1: 
/*  615 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 2: 
/*  618 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 3: 
/*  621 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 4: 
/*  624 */       return "class=\"errorgrayborder\"";
/*      */     
/*      */     case 5: 
/*  627 */       return "class=\"whitegrayborder\"";
/*      */     
/*      */     case 6: 
/*  630 */       return "class=\"whitegrayborder\"";
/*      */     }
/*      */     
/*  633 */     return "class=\"whitegrayborder\"";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getTrimmedText(String stringToTrim, int lengthOfTrimmedString)
/*      */   {
/*  641 */     return FormatUtil.getTrimmedText(stringToTrim, lengthOfTrimmedString);
/*      */   }
/*      */   
/*      */   public String getformatedText(String stringToTrim, int lengthOfTrimmedString, int lastPartStartsfrom)
/*      */   {
/*  646 */     return FormatUtil.getformatedText(stringToTrim, lengthOfTrimmedString, lastPartStartsfrom);
/*      */   }
/*      */   
/*      */   private String getTruncatedAlertMessage(String messageArg)
/*      */   {
/*  651 */     return FormatUtil.getTruncatedAlertMessage(messageArg);
/*      */   }
/*      */   
/*      */   private String formatDT(String val)
/*      */   {
/*  656 */     return FormatUtil.formatDT(val);
/*      */   }
/*      */   
/*      */   private String formatDT(Long val)
/*      */   {
/*  661 */     if (val != null)
/*      */     {
/*  663 */       return FormatUtil.formatDT(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  667 */     return "-";
/*      */   }
/*      */   
/*      */   private String formatDTwithOutYear(String val)
/*      */   {
/*  672 */     if (val == null) {
/*  673 */       return val;
/*      */     }
/*      */     try
/*      */     {
/*  677 */       val = new java.text.SimpleDateFormat("MMM d h:mm a").format(new Date(Long.parseLong(val)));
/*      */     }
/*      */     catch (Exception e) {}
/*      */     
/*      */ 
/*  682 */     return val;
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatDTwithOutYear(Long val)
/*      */   {
/*  688 */     if (val != null)
/*      */     {
/*  690 */       return formatDTwithOutYear(val.toString());
/*      */     }
/*      */     
/*      */ 
/*  694 */     return "-";
/*      */   }
/*      */   
/*      */ 
/*      */   private String formatAlertDT(String val)
/*      */   {
/*  700 */     return val.substring(0, val.lastIndexOf(":")) + val.substring(val.lastIndexOf(":") + 3);
/*      */   }
/*      */   
/*      */   private String formatNumber(Object val)
/*      */   {
/*  705 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatNumber(long val) {
/*  709 */     return FormatUtil.formatNumber(val);
/*      */   }
/*      */   
/*      */   private String formatBytesToKB(String val)
/*      */   {
/*  714 */     return FormatUtil.formatBytesToKB(val) + " " + FormatUtil.getString("KB");
/*      */   }
/*      */   
/*      */   private String formatBytesToMB(String val)
/*      */   {
/*  719 */     return FormatUtil.formatBytesToMB(val) + " " + FormatUtil.getString("MB");
/*      */   }
/*      */   
/*      */   private String getHostAddress(HttpServletRequest request) throws Exception
/*      */   {
/*  724 */     String hostaddress = "";
/*  725 */     String ip = request.getHeader("x-forwarded-for");
/*  726 */     if (ip == null)
/*  727 */       ip = request.getRemoteAddr();
/*  728 */     InetAddress add = null;
/*  729 */     if (ip.equals("127.0.0.1")) {
/*  730 */       add = InetAddress.getLocalHost();
/*      */     }
/*      */     else
/*      */     {
/*  734 */       add = InetAddress.getByName(ip);
/*      */     }
/*  736 */     hostaddress = add.getHostName();
/*  737 */     if (hostaddress.indexOf('.') != -1) {
/*  738 */       StringTokenizer st = new StringTokenizer(hostaddress, ".");
/*  739 */       hostaddress = st.nextToken();
/*      */     }
/*      */     
/*      */ 
/*  743 */     return hostaddress;
/*      */   }
/*      */   
/*      */   private String removeBr(String arg)
/*      */   {
/*  748 */     return FormatUtil.replaceStringBySpecifiedString(arg, "<br>", "", 0);
/*      */   }
/*      */   
/*      */ 
/*      */   private String getSeverityImageForMssqlAvailability(Object severity)
/*      */   {
/*  754 */     if (severity == null)
/*      */     {
/*  756 */       return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */     }
/*  758 */     if (severity.equals("5"))
/*      */     {
/*  760 */       return "<img border=\"0\" src=\"/images/up_icon.gif\"  name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/up_icon.gif',1)\">";
/*      */     }
/*  762 */     if (severity.equals("1"))
/*      */     {
/*  764 */       return "<img border=\"0\" src=\"/images/down_icon.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/down_icon.gif',1)\">";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  769 */     return "<img border=\"0\" src=\"/images/icon_esx_unknown.gif\" name=\"Image" + ++this.j + "\"  onMouseOut=\"javascript:MM_swapImgRestore()\" onMouseOver=\"javascript:MM_swapImage('Image" + this.j + "','','/images/icon_esx_unknown.gif',1)\">";
/*      */   }
/*      */   
/*      */   public String getDependentChildAttribs(String resid, String attributeId)
/*      */   {
/*  774 */     ResultSet set = null;
/*  775 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*  776 */     String dependentChildQry = "select ANYCONDITIONVALUE from AM_RCARULESMAPPER where RESOURCEID=" + resid + " and ATTRIBUTE=" + attributeId;
/*      */     try {
/*  778 */       set = AMConnectionPool.executeQueryStmt(dependentChildQry);
/*  779 */       if (set.next()) { String str1;
/*  780 */         if (set.getString("ANYCONDITIONVALUE").equals("-1")) {
/*  781 */           return FormatUtil.getString("am.fault.rcatree.allrule.text");
/*      */         }
/*      */         
/*  784 */         return FormatUtil.getString("am.fault.rcatree.anyrule.text", new String[] { set.getString("ANYCONDITIONVALUE") });
/*      */       }
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*  789 */       e.printStackTrace();
/*      */     }
/*      */     finally {
/*  792 */       AMConnectionPool.closeStatement(set);
/*      */     }
/*  794 */     return FormatUtil.getString("am.fault.rcatree.anyonerule.text");
/*      */   }
/*      */   
/*      */   public String getConfHealthRCA(String key) {
/*  798 */     StringBuffer rca = new StringBuffer();
/*  799 */     if ((key != null) && (key.indexOf("Root Cause :") != -1)) {
/*  800 */       key = key.substring(key.indexOf("Root Cause :") + 17, key.length());
/*      */     }
/*      */     
/*  803 */     int rcalength = key.length();
/*  804 */     String split = "6. ";
/*  805 */     int splitPresent = key.indexOf(split);
/*  806 */     String div1 = "";String div2 = "";
/*  807 */     if ((rcalength < 300) || (splitPresent < 0))
/*      */     {
/*  809 */       if (rcalength > 180) {
/*  810 */         rca.append("<span class=\"rca-critical-text\">");
/*  811 */         getRCATrimmedText(key, rca);
/*  812 */         rca.append("</span>");
/*      */       } else {
/*  814 */         rca.append("<span class=\"rca-critical-text\">");
/*  815 */         rca.append(key);
/*  816 */         rca.append("</span>");
/*      */       }
/*  818 */       return rca.toString();
/*      */     }
/*  820 */     div1 = key.substring(0, key.indexOf(split) - 4);
/*  821 */     div2 = key.substring(key.indexOf(split), rcalength - 4);
/*  822 */     rca.append("<div style='overflow: hidden; display: block; width: 100%; height: auto;'>");
/*  823 */     String rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div1, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  824 */     getRCATrimmedText(div1, rca);
/*  825 */     rca.append("<span id=\"confrcashow\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcahide','confrcashow','confrcahidden');\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span></div>");
/*      */     
/*      */ 
/*  828 */     rca.append("<div id='confrcahidden' style='display: none; width: 100%;'>");
/*  829 */     rcaMesg = com.adventnet.utilities.stringutils.StrUtil.findReplace(div2, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;");
/*  830 */     getRCATrimmedText(div2, rca);
/*  831 */     rca.append("<span id=\"confrcahide\" class=\"confrcashow\" onclick=\"javascript:toggleSlide('confrcashow','confrcahide','confrcahidden')\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"7\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span></div>");
/*      */     
/*  833 */     return rca.toString();
/*      */   }
/*      */   
/*      */   public void getRCATrimmedText(String msg, StringBuffer rca)
/*      */   {
/*  838 */     String[] st = msg.split("<br>");
/*  839 */     for (int i = 0; i < st.length; i++) {
/*  840 */       String s = st[i];
/*  841 */       if (s.length() > 180) {
/*  842 */         s = s.substring(0, 175) + ".....";
/*      */       }
/*  844 */       rca.append(s + "<br>");
/*      */     }
/*      */   }
/*      */   
/*  848 */   public String getConfHealthTime(String time) { if ((time != null) && (!time.trim().equals(""))) {
/*  849 */       return new Date(com.adventnet.appmanager.reporting.ReportUtilities.roundOffToNearestSeconds(Long.parseLong(time))).toString();
/*      */     }
/*  851 */     return "";
/*      */   }
/*      */   
/*      */   public String getHelpLink(String key) {
/*  855 */     String helpText = FormatUtil.getString("am.webclient.contexthelplink.text");
/*  856 */     ret = "<a href=\"/help/index.html\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*  857 */     ResultSet set = null;
/*      */     try {
/*      */       String str1;
/*  860 */       if (key == null) {
/*  861 */         return ret;
/*      */       }
/*      */       
/*  864 */       if (DBUtil.searchLinks.containsKey(key)) {
/*  865 */         return "<a href=\"" + (String)DBUtil.searchLinks.get(key) + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
/*      */       }
/*      */       
/*  868 */       String query = "select LINK from AM_SearchDocLinks where NAME ='" + key + "'";
/*  869 */       AMConnectionPool cp = AMConnectionPool.getInstance();
/*  870 */       set = AMConnectionPool.executeQueryStmt(query);
/*  871 */       if (set.next())
/*      */       {
/*  873 */         String helpLink = set.getString("LINK");
/*  874 */         DBUtil.searchLinks.put(key, helpLink);
/*      */         try
/*      */         {
/*  877 */           AMConnectionPool.closeStatement(set);
/*      */         }
/*      */         catch (Exception exc) {}
/*      */         
/*      */ 
/*      */ 
/*  883 */         return "<a href=\"" + helpLink + "\" title=\"" + helpText + "\" target=\"newwindow\" class=\"headerwhitelinks\" ><img src=\"/images/helpIcon.png\"/></a>";
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
/*  902 */       return ret;
/*      */     }
/*      */     catch (Exception ex) {}finally
/*      */     {
/*      */       try
/*      */       {
/*  893 */         if (set != null) {
/*  894 */           AMConnectionPool.closeStatement(set);
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
/*  908 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(entitylist, false);
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
/*      */ 
/*      */   public Properties getStatus(List listOfResourceIDs, List listOfAttributeIDs)
/*      */   {
/*  922 */     Properties temp = com.adventnet.appmanager.fault.FaultUtil.getStatus(listOfResourceIDs, listOfAttributeIDs);
/*  923 */     for (Enumeration keys = temp.propertyNames(); keys.hasMoreElements();)
/*      */     {
/*  925 */       String entityStr = (String)keys.nextElement();
/*  926 */       String mmessage = temp.getProperty(entityStr);
/*  927 */       mmessage = mmessage.replaceAll("\"", "&quot;");
/*  928 */       temp.setProperty(entityStr, mmessage);
/*      */     }
/*  930 */     return temp;
/*      */   }
/*      */   
/*      */   private void debug(String debugMessage)
/*      */   {
/*  935 */     com.adventnet.appmanager.logging.AMLog.debug("JSP : " + debugMessage);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private String findReplace(String str, String find, String replace)
/*      */   {
/*  945 */     String des = new String();
/*  946 */     while (str.indexOf(find) != -1) {
/*  947 */       des = des + str.substring(0, str.indexOf(find));
/*  948 */       des = des + replace;
/*  949 */       str = str.substring(str.indexOf(find) + find.length());
/*      */     }
/*  951 */     des = des + str;
/*  952 */     return des;
/*      */   }
/*      */   
/*      */   private String getHideAndShowRCAMessage(String test, String id, String alert, String resourceid)
/*      */   {
/*      */     try
/*      */     {
/*  959 */       if (alert == null)
/*      */       {
/*  961 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text");
/*      */       }
/*  963 */       if ((test == null) || (test.equals("")))
/*      */       {
/*  965 */         return "&nbsp;";
/*      */       }
/*      */       
/*  968 */       if ((alert != null) && (alert.equals("5")))
/*      */       {
/*  970 */         return "&nbsp;&nbsp;" + FormatUtil.getString("am.fault.rca.healthisclear.text") + ".&nbsp;" + FormatUtil.getString("am.webclient.nocriticalalarms.current.text");
/*      */       }
/*      */       
/*  973 */       int rcalength = test.length();
/*  974 */       if (rcalength < 300)
/*      */       {
/*  976 */         return test;
/*      */       }
/*      */       
/*      */ 
/*  980 */       StringBuffer out = new StringBuffer();
/*  981 */       out.append("<div id='rcahidden' style='overflow: hidden; display: block; word-wrap: break-word; width: 500px; height: 100px'>");
/*  982 */       out.append(com.adventnet.utilities.stringutils.StrUtil.findReplace(test, " --> ", "&nbsp;<img src=\"/images/img_arrow.gif\">&nbsp;"));
/*  983 */       out.append("</div>");
/*  984 */       out.append("<div align=\"right\" id=\"rcashow" + id + "\" style=\"display:block;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='auto';hideDiv('rcashow" + id + "');showDiv('rcahide" + id + "');\"><span class=\"bcactive\"><img src=\"/images/icon_plus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.show.text") + " </span> </div>");
/*  985 */       out.append("<div align=\"right\" id=\"rcahide" + id + "\" style=\"display:none;\" onclick=\"javascript:document.getElementById('rcahidden').style.height='100px',hideDiv('rcahide" + id + "');showDiv('rcashow" + id + "')\"><span class=\"bcactive\"><img src=\"/images/icon_minus.gif\" width=\"9\" height=\"9\"> " + FormatUtil.getString("am.webclient.monitorinformation.hide.text") + " </span> </div>");
/*  986 */       return out.toString();
/*      */ 
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/*  991 */       ex.printStackTrace();
/*      */     }
/*  993 */     return test;
/*      */   }
/*      */   
/*      */ 
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/*  999 */     return getAlerts(monitorList, availabilitykeys, healthkeys, 1);
/*      */   }
/*      */   
/*      */   public Properties getAlerts(List monitorList, Hashtable availabilitykeys, Hashtable healthkeys, int type)
/*      */   {
/* 1004 */     ArrayList attribIDs = new ArrayList();
/* 1005 */     ArrayList resIDs = new ArrayList();
/* 1006 */     ArrayList entitylist = new ArrayList();
/*      */     
/* 1008 */     for (int j = 0; (monitorList != null) && (j < monitorList.size()); j++)
/*      */     {
/* 1010 */       ArrayList row = (ArrayList)monitorList.get(j);
/*      */       
/* 1012 */       String resourceid = "";
/* 1013 */       String resourceType = "";
/* 1014 */       if (type == 2) {
/* 1015 */         resourceid = (String)row.get(0);
/* 1016 */         resourceType = (String)row.get(3);
/*      */       }
/* 1018 */       else if (type == 3) {
/* 1019 */         resourceid = (String)row.get(0);
/* 1020 */         resourceType = "EC2Instance";
/*      */       }
/*      */       else {
/* 1023 */         resourceid = (String)row.get(6);
/* 1024 */         resourceType = (String)row.get(7);
/*      */       }
/* 1026 */       resIDs.add(resourceid);
/* 1027 */       String healthid = com.adventnet.appmanager.dbcache.AMAttributesCache.getHealthId(resourceType);
/* 1028 */       String availid = com.adventnet.appmanager.dbcache.AMAttributesCache.getAvailabilityId(resourceType);
/*      */       
/* 1030 */       String healthentity = null;
/* 1031 */       String availentity = null;
/* 1032 */       if (healthid != null) {
/* 1033 */         healthentity = resourceid + "_" + healthid;
/* 1034 */         entitylist.add(healthentity);
/*      */       }
/*      */       
/* 1037 */       if (availid != null) {
/* 1038 */         availentity = resourceid + "_" + availid;
/* 1039 */         entitylist.add(availentity);
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
/* 1053 */     Properties alert = getStatus(entitylist);
/* 1054 */     return alert;
/*      */   }
/*      */   
/*      */   public void getSortedMonitorList(ArrayList monitorList, Properties alert, Hashtable availabilitykeys, Hashtable healthkeys)
/*      */   {
/* 1059 */     int size = monitorList.size();
/*      */     
/* 1061 */     String[] severity = new String[size];
/*      */     
/* 1063 */     for (int j = 0; j < monitorList.size(); j++)
/*      */     {
/* 1065 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1066 */       String resourceName1 = (String)row1.get(7);
/* 1067 */       String resourceid1 = (String)row1.get(6);
/* 1068 */       severity[j] = alert.getProperty(resourceid1 + "#" + availabilitykeys.get(resourceName1));
/* 1069 */       if (severity[j] == null)
/*      */       {
/* 1071 */         severity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/* 1075 */     for (j = 0; j < severity.length; j++)
/*      */     {
/* 1077 */       for (int k = j + 1; k < severity.length; k++)
/*      */       {
/* 1079 */         int sev = severity[j].compareTo(severity[k]);
/*      */         
/*      */ 
/* 1082 */         if (sev > 0) {
/* 1083 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1084 */           monitorList.set(k, monitorList.get(j));
/* 1085 */           monitorList.set(j, t);
/* 1086 */           String temp = severity[k];
/* 1087 */           severity[k] = severity[j];
/* 1088 */           severity[j] = temp;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1094 */     int z = 0;
/* 1095 */     for (j = 0; j < monitorList.size(); j++)
/*      */     {
/*      */ 
/* 1098 */       int i = 0;
/* 1099 */       if ((!severity[j].equals("0")) && (!severity[j].equals("1")) && (!severity[j].equals("4")))
/*      */       {
/*      */ 
/* 1102 */         i++;
/*      */       }
/*      */       else
/*      */       {
/* 1106 */         z++;
/*      */       }
/*      */     }
/*      */     
/* 1110 */     String[] hseverity = new String[monitorList.size()];
/*      */     
/* 1112 */     for (j = 0; j < z; j++)
/*      */     {
/*      */ 
/* 1115 */       hseverity[j] = severity[j];
/*      */     }
/*      */     
/*      */ 
/* 1119 */     for (j = z; j < severity.length; j++)
/*      */     {
/*      */ 
/* 1122 */       ArrayList row1 = (ArrayList)monitorList.get(j);
/* 1123 */       String resourceName1 = (String)row1.get(7);
/* 1124 */       String resourceid1 = (String)row1.get(6);
/* 1125 */       hseverity[j] = alert.getProperty(resourceid1 + "#" + healthkeys.get(resourceName1));
/* 1126 */       if (hseverity[j] == null)
/*      */       {
/* 1128 */         hseverity[j] = "6";
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1133 */     for (j = 0; j < hseverity.length; j++)
/*      */     {
/* 1135 */       for (int k = j + 1; k < hseverity.length; k++)
/*      */       {
/*      */ 
/* 1138 */         int hsev = hseverity[j].compareTo(hseverity[k]);
/*      */         
/*      */ 
/* 1141 */         if (hsev > 0) {
/* 1142 */           ArrayList t = (ArrayList)monitorList.get(k);
/* 1143 */           monitorList.set(k, monitorList.get(j));
/* 1144 */           monitorList.set(j, t);
/* 1145 */           String temp1 = hseverity[k];
/* 1146 */           hseverity[k] = hseverity[j];
/* 1147 */           hseverity[j] = temp1;
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
/* 1159 */     boolean isIt360 = com.adventnet.appmanager.util.Constants.isIt360;
/* 1160 */     boolean forInventory = false;
/* 1161 */     String trdisplay = "none";
/* 1162 */     String plusstyle = "inline";
/* 1163 */     String minusstyle = "none";
/* 1164 */     String haidTopLevel = "";
/* 1165 */     if (request.getAttribute("forInventory") != null)
/*      */     {
/* 1167 */       if ("true".equals((String)request.getAttribute("forInventory")))
/*      */       {
/* 1169 */         haidTopLevel = request.getParameter("haid");
/* 1170 */         forInventory = true;
/* 1171 */         trdisplay = "table-row;";
/* 1172 */         plusstyle = "none";
/* 1173 */         minusstyle = "inline";
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1180 */       haidTopLevel = resIdTOCheck;
/*      */     }
/*      */     
/* 1183 */     ArrayList listtoreturn = new ArrayList();
/* 1184 */     StringBuffer toreturn = new StringBuffer();
/* 1185 */     Hashtable availabilitykeys = (Hashtable)availhealth.get("avail");
/* 1186 */     Hashtable healthkeys = (Hashtable)availhealth.get("health");
/* 1187 */     Properties alert = (Properties)availhealth.get("alert");
/*      */     
/* 1189 */     for (int j = 0; j < singlechilmos.size(); j++)
/*      */     {
/* 1191 */       ArrayList singlerow = (ArrayList)singlechilmos.get(j);
/* 1192 */       String childresid = (String)singlerow.get(0);
/* 1193 */       String childresname = (String)singlerow.get(1);
/* 1194 */       childresname = com.adventnet.appmanager.util.ExtProdUtil.decodeString(childresname);
/* 1195 */       String childtype = ((String)singlerow.get(2) + "").trim();
/* 1196 */       String imagepath = ((String)singlerow.get(3) + "").trim();
/* 1197 */       String shortname = ((String)singlerow.get(4) + "").trim();
/* 1198 */       String unmanagestatus = (String)singlerow.get(5);
/* 1199 */       String actionstatus = (String)singlerow.get(6);
/* 1200 */       String linkclass = "monitorgp-links";
/* 1201 */       String titleforres = childresname;
/* 1202 */       String titilechildresname = childresname;
/* 1203 */       String childimg = "/images/trcont.png";
/* 1204 */       String flag = "enable";
/* 1205 */       String dcstarted = (String)singlerow.get(8);
/* 1206 */       String configMonitor = "";
/* 1207 */       String configmsg = FormatUtil.getString("am.webclient.vcenter.esx.notconfigured.text");
/* 1208 */       if (("VMWare ESX/ESXi".equals(childtype)) && (!"2".equals(dcstarted)))
/*      */       {
/* 1210 */         configMonitor = "&nbsp;&nbsp;<img src='/images/icon_ack.gif' align='absmiddle' style='width=16px;heigth:16px' border='0' title='" + configmsg + "' />";
/*      */       }
/* 1212 */       if (singlerow.get(7) != null)
/*      */       {
/* 1214 */         flag = (String)singlerow.get(7);
/*      */       }
/* 1216 */       String haiGroupType = "0";
/* 1217 */       if ("HAI".equals(childtype))
/*      */       {
/* 1219 */         haiGroupType = (String)singlerow.get(9);
/*      */       }
/* 1221 */       childimg = "/images/trend.png";
/* 1222 */       String actionmsg = FormatUtil.getString("Actions Enabled");
/* 1223 */       String actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\"  title=\"" + actionmsg + "\"  />";
/* 1224 */       if ((actionstatus == null) || (actionstatus.equalsIgnoreCase("null")) || (actionstatus.equals("1")))
/*      */       {
/* 1226 */         actionimg = "<img src=\"/images/alarm-icon.png\" border=\"0\" title=\"" + actionmsg + "\" />";
/*      */       }
/* 1228 */       else if (actionstatus.equals("0"))
/*      */       {
/* 1230 */         actionmsg = FormatUtil.getString("Actions Disabled");
/* 1231 */         actionimg = "<img src=\"/images/icon_actions_disabled.gif\" border=\"0\"   title=\"" + actionmsg + "\" />";
/*      */       }
/*      */       
/* 1234 */       if ((unmanagestatus != null) && (!unmanagestatus.trim().equalsIgnoreCase("null")))
/*      */       {
/* 1236 */         linkclass = "disabledtext";
/* 1237 */         titleforres = titleforres + "-UnManaged";
/*      */       }
/* 1239 */       String availkey = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1240 */       String availmouseover = "";
/* 1241 */       if (alert.getProperty(availkey) != null)
/*      */       {
/* 1243 */         availmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(availkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/* 1245 */       String healthkey = childresid + "#" + healthkeys.get(childtype) + "#" + "MESSAGE";
/* 1246 */       String healthmouseover = "";
/* 1247 */       if (alert.getProperty(healthkey) != null)
/*      */       {
/* 1249 */         healthmouseover = "onmouseover=\"ddrivetip(this,event,'" + alert.getProperty(healthkey).replace("\"", "&quot;") + "<br><span style=color: #000000;font-weight:bold;>" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\"  onmouseout=\"hideddrivetip()\" ";
/*      */       }
/*      */       
/* 1252 */       String tempbgcolor = "class=\"whitegrayrightalign\"";
/* 1253 */       int spacing = 0;
/* 1254 */       if (level >= 1)
/*      */       {
/* 1256 */         spacing = 40 * level;
/*      */       }
/* 1258 */       if (childtype.equals("HAI"))
/*      */       {
/* 1260 */         ArrayList singlechilmos1 = (ArrayList)childmos.get(childresid + "");
/* 1261 */         String tempresourceidtree = currentresourceidtree + "|" + childresid;
/* 1262 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/*      */         
/* 1264 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1265 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1266 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1267 */         String editlink = "<a href=\"/showapplication.do?method=editApplication&fromwhere=allmonitorgroups&haid=" + childresid + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1268 */         String imglink = "<img src=\"" + childimg + "\" align=\"center\"    align=\"left\" border=\"0\" height=\"24\" width=\"24\">";
/* 1269 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + tempresourceidtree + "\" value=\"" + childresid + "\"  onclick=\"selectAllChildCKbs('" + tempresourceidtree + "',this,this.form),deselectParentCKbs('" + tempresourceidtree + "',this,this.form)\"  >";
/* 1270 */         String thresholdurl = "/showActionProfiles.do?method=getHAProfiles&haid=" + childresid;
/* 1271 */         String configalertslink = " <a title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "' href=\"" + thresholdurl + "\" ><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" title=\"" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "\" /></a>";
/* 1272 */         String associatelink = "<a href=\"/showresource.do?method=getMonitorForm&type=All&fromwhere=monitorgroupview&haid=" + childresid + "\" title=\"" + FormatUtil.getString("am.webclient.monitorgroupdetails.associatemonitors.text") + "\" ><img align=\"center\" src=\"images/icon_assoicatemonitors.gif\" border=\"0\" /></a>";
/* 1273 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>&nbsp;&nbsp;";
/* 1274 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1276 */         if (!forInventory)
/*      */         {
/* 1278 */           removefromgroup = "";
/*      */         }
/*      */         
/* 1281 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/*      */         
/* 1283 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1285 */           actions = editlink + actions;
/*      */         }
/* 1287 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1289 */           actions = actions + associatelink;
/*      */         }
/* 1291 */         actions = actions + "&nbsp;&nbsp;&nbsp;&nbsp;" + configcustomfields;
/* 1292 */         String arrowimg = "";
/* 1293 */         if (request.isUserInRole("ENTERPRISEADMIN"))
/*      */         {
/* 1295 */           actions = "";
/* 1296 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1297 */           checkbox = "";
/* 1298 */           childresname = childresname + "_" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(childresid);
/*      */         }
/* 1300 */         if (isIt360)
/*      */         {
/* 1302 */           actionimg = "";
/* 1303 */           actions = "";
/* 1304 */           arrowimg = "<img align=\"center\" hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1305 */           checkbox = "";
/*      */         }
/*      */         
/* 1308 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1310 */           actions = "";
/*      */         }
/* 1312 */         if (request.isUserInRole("OPERATOR"))
/*      */         {
/* 1314 */           checkbox = "";
/*      */         }
/*      */         
/* 1317 */         String resourcelink = "";
/*      */         
/* 1319 */         if ((flag != null) && (flag.equals("enable")))
/*      */         {
/* 1321 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "<a href=\"/showapplication.do?haid=" + childresid + "&method=showApplication\" class=\"" + linkclass + "\">" + getTrimmedText(titilechildresname, 45) + "</a> ";
/*      */         }
/*      */         else
/*      */         {
/* 1325 */           resourcelink = "<a href=\"javascript:void(0);\" onclick=\"toggleChildMos('#monitor" + tempresourceidtree + "'),toggleTreeImage('" + tempresourceidtree + "');\"><div id=\"monitorShow" + tempresourceidtree + "\" style=\"display:" + plusstyle + ";\"><img src=\"/images/icon_plus.gif\" border=\"0\" hspace=\"5\"></div><div id=\"monitorHide" + tempresourceidtree + "\" style=\"display:" + minusstyle + ";\"><img src=\"/images/icon_minus.gif\" border=\"0\" hspace=\"5\"></div> </a>" + checkbox + "" + getTrimmedText(titilechildresname, 45);
/*      */         }
/*      */         
/* 1328 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display:" + trdisplay + ";\" width='100%'>");
/* 1329 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\" >&nbsp;</td> ");
/* 1330 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\"  style=\"padding-left: " + spacing + "px !important;\" title=" + childresname + ">" + arrowimg + resourcelink + "</td>");
/* 1331 */         toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/* 1332 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1333 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1334 */         if (!isIt360)
/*      */         {
/* 1336 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1340 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         
/* 1343 */         toreturn.append("</tr>");
/* 1344 */         if (childmos.get(childresid + "") != null)
/*      */         {
/* 1346 */           String toappend = getAllChildNodestoDisplay(singlechilmos1, childresid + "", tempresourceidtree, childmos, availhealth, level + 1, request, extDeviceMap, site24x7List);
/* 1347 */           toreturn.append(toappend);
/*      */         }
/*      */         else
/*      */         {
/* 1351 */           String assocMessage = "<td  " + tempbgcolor + " colspan=\"2\"><span class=\"bodytext\" style=\"padding-left: " + (spacing + 10) + "px !important;\"> &nbsp;&nbsp;&nbsp;&nbsp;" + FormatUtil.getString("am.webclient.monitorgroupdetails.nomonitormessage.text") + "</span><span class=\"bodytext\">";
/* 1352 */           if ((!request.isUserInRole("ENTERPRISEADMIN")) && (!request.isUserInRole("DEMO")) && (!request.isUserInRole("OPERATOR")))
/*      */           {
/*      */ 
/* 1355 */             assocMessage = assocMessage + FormatUtil.getString("am.webclient.monitorgroupdetails.click.text") + " <a href=\"/showresource.do?method=getMonitorForm&type=All&haid=" + childresid + "&fromwhere=monitorgroupview\" class=\"staticlinks\" >" + FormatUtil.getString("am.webclient.monitorgroupdetails.linktoadd.text") + "</span></td>";
/*      */           }
/*      */           
/*      */ 
/* 1359 */           if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"3".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */           {
/* 1361 */             toreturn.append("<tr  " + tempbgcolor + "  id=\"#monitor" + tempresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1362 */             toreturn.append("<td  " + tempbgcolor + "  width=\"3%\" >&nbsp;</td> ");
/* 1363 */             toreturn.append(assocMessage);
/* 1364 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1365 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1366 */             toreturn.append("<td  " + tempbgcolor + "  >&nbsp;</td> ");
/* 1367 */             toreturn.append("</tr>");
/*      */           }
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1373 */         String resourcelink = null;
/* 1374 */         boolean hideEditLink = false;
/* 1375 */         if ((extDeviceMap != null) && (extDeviceMap.get(childresid) != null))
/*      */         {
/* 1377 */           String link1 = (String)extDeviceMap.get(childresid);
/* 1378 */           hideEditLink = true;
/* 1379 */           if (isIt360)
/*      */           {
/* 1381 */             resourcelink = "<a href=" + link1 + "  class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/*      */           else
/*      */           {
/* 1385 */             resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link1 + "','ExternalDevice','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */           }
/* 1387 */         } else if ((site24x7List != null) && (site24x7List.containsKey(childresid)))
/*      */         {
/* 1389 */           hideEditLink = true;
/* 1390 */           String link2 = java.net.URLEncoder.encode((String)site24x7List.get(childresid));
/* 1391 */           resourcelink = "<a href=\"javascript:MM_openBrWindow('" + link2 + "','Site24x7','width=950,height=600,top=50,left=75,scrollbars=yes,resizable=yes')\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1396 */           resourcelink = "<a href=\"/showresource.do?resourceid=" + childresid + "&method=showResourceForResourceID&haid=" + resIdTOCheck + "\" class=\"" + linkclass + "\" title=\"" + titleforres + "\">" + getTrimmedText(childresname, 45) + "</a>";
/*      */         }
/*      */         
/* 1399 */         String imglink = "<img src=\"" + childimg + "\"  align=\"left\" border=\"0\" height=\"24\" width=\"24\"  />";
/* 1400 */         String checkbox = "<input type=\"checkbox\" name=\"select\" id=\"" + currentresourceidtree + "|" + childresid + "\"  value=\"" + childresid + "\"  onclick=\"deselectParentCKbs('" + currentresourceidtree + "|" + childresid + "',this,this.form);\" >";
/* 1401 */         String key = childresid + "#" + availabilitykeys.get(childtype) + "#" + "MESSAGE";
/* 1402 */         String availimage = getSeverityImageForAvailability(alert.getProperty(childresid + "#" + availabilitykeys.get(childtype)));
/* 1403 */         String healthimage = getSeverityImageForHealth(alert.getProperty(childresid + "#" + healthkeys.get(childtype)));
/* 1404 */         String availlink = "<a href=\"javascript:void(0);\" " + availmouseover + "onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + availabilitykeys.get(childtype) + "')\"> " + availimage + "</a>";
/* 1405 */         String healthlink = "<a href=\"javascript:void(0);\" " + healthmouseover + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + childresid + "&attributeid=" + healthkeys.get(childtype) + "')\"> " + healthimage + "</a>";
/* 1406 */         String editlink = "<a href=\"/showresource.do?haid=" + resIdTOCheck + "&resourceid=" + childresid + "&resourcename=" + childresname + "&type=" + childtype + "&method=showdetails&editPage=true&moname=" + childresname + "\" class=\"staticlinks\" title=\"" + FormatUtil.getString("am.webclient.maintenance.edit") + "\"><img align=\"center\" src=\"/images/icon_edit.gif\" border=\"0\" /></a>";
/* 1407 */         String thresholdurl = "/showActionProfiles.do?method=getResourceProfiles&admin=true&all=true&resourceid=" + childresid;
/* 1408 */         String configalertslink = " <a href=\"" + thresholdurl + "\" title='" + FormatUtil.getString("am.webclient.common.util.ALERTCONFIG_TEXT") + "'><img src=\"images/icon_associateaction.gif\" align=\"center\" border=\"0\" /></a>";
/* 1409 */         String img2 = "<img src=\"/images/trvline.png\" align=\"absmiddle\" border=\"0\" height=\"15\" width=\"15\"/>";
/* 1410 */         String removefromgroup = "<a class='staticlinks' href=\"javascript: removeMonitorFromGroup ('" + resIdTOCheck + "','" + childresid + "','" + haidTopLevel + "');\" title='" + FormatUtil.getString("am.webclient.monitorgroupdetails.remove.text") + "'><img width='13' align=\"center\"  height='14' border='0' src='/images/icon_removefromgroup.gif'/></a>";
/* 1411 */         String configcustomfields = "<a href=\"javascript:void(0)\" onclick=\"fnOpenNewWindow('/jsp/MyField_Alarms.jsp?resourceid=" + childresid + "&mgview=true')\" title='" + FormatUtil.getString("am.myfield.assign.text") + "'><img align=\"center\" src=\"/images/icon_assigncustomfields.gif\" border=\"0\" /></a>";
/*      */         
/* 1413 */         if (hideEditLink)
/*      */         {
/* 1415 */           editlink = "&nbsp;&nbsp;&nbsp;";
/*      */         }
/* 1417 */         if (!forInventory)
/*      */         {
/* 1419 */           removefromgroup = "";
/*      */         }
/* 1421 */         String actions = "&nbsp;&nbsp;" + configalertslink + "&nbsp;&nbsp;" + removefromgroup + "&nbsp;&nbsp;";
/* 1422 */         if (!com.adventnet.appmanager.util.Constants.sqlManager) {
/* 1423 */           actions = actions + configcustomfields;
/*      */         }
/* 1425 */         if ((haiGroupType == null) || ((haiGroupType != null) && (!"1009".equals(haiGroupType)) && (!"1010".equals(haiGroupType)) && (!"1012".equals(haiGroupType))))
/*      */         {
/* 1427 */           actions = editlink + actions;
/*      */         }
/* 1429 */         String managedLink = "";
/* 1430 */         if ((request.isUserInRole("ENTERPRISEADMIN")) && (!com.adventnet.appmanager.util.Constants.isIt360))
/*      */         {
/* 1432 */           checkbox = "<img hspace=\"3\" border=\"0\" src=\"/images/icon_arrow_childattribute_grey.gif\"/>";
/* 1433 */           actions = "";
/* 1434 */           if (Integer.parseInt(childresid) >= com.adventnet.appmanager.server.framework.comm.Constants.RANGE) {
/* 1435 */             managedLink = "&nbsp; <a target=\"mas_window\" href=\"/showresource.do?resourceid=" + childresid + "&type=" + childtype + "&moname=" + java.net.URLEncoder.encode(childresname) + "&resourcename=" + java.net.URLEncoder.encode(childresname) + "&method=showdetails&aam_jump=true&useHTTP=" + (!isIt360) + "\"><img border=\"0\" title=\"View Monitor details in Managed Server console\" src=\"/images/jump.gif\"/></a>";
/*      */           }
/*      */         }
/* 1438 */         if ((isIt360) || (request.isUserInRole("OPERATOR")))
/*      */         {
/* 1440 */           checkbox = "";
/*      */         }
/*      */         
/* 1443 */         if (!request.isUserInRole("ADMIN"))
/*      */         {
/* 1445 */           actions = "";
/*      */         }
/* 1447 */         toreturn.append("<tr " + tempbgcolor + " id=\"#monitor" + currentresourceidtree + "\" style=\"display: " + trdisplay + ";\" width='100%'>");
/* 1448 */         toreturn.append("<td " + tempbgcolor + " width=\"3%\"  >&nbsp;</td> ");
/* 1449 */         toreturn.append("<td " + tempbgcolor + " width=\"47%\" nowrap=\"false\" style=\"padding-left: " + spacing + "px !important;\" >" + checkbox + "&nbsp;<img align='absmiddle' border=\"0\"  title='" + shortname + "' src=\"" + imagepath + "\"/>&nbsp;" + resourcelink + managedLink + configMonitor + "</td>");
/* 1450 */         if (isIt360)
/*      */         {
/* 1452 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">&nbsp;</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1456 */           toreturn.append("<td " + tempbgcolor + " width=\"15%\" align=\"left\">" + "<table><tr class='whitegrayrightalign'><td><div id='mgaction'>" + actions + "</div></td></tr></table></td>");
/*      */         }
/* 1458 */         toreturn.append("<td " + tempbgcolor + " width=\"8%\" align=\"center\">" + availlink + "</td>");
/* 1459 */         toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"center\">" + healthlink + "</td>");
/* 1460 */         if (!isIt360)
/*      */         {
/* 1462 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">" + actionimg + "</td>");
/*      */         }
/*      */         else
/*      */         {
/* 1466 */           toreturn.append("<td " + tempbgcolor + " width=\"7%\" align=\"left\">&nbsp;</td>");
/*      */         }
/* 1468 */         toreturn.append("</tr>");
/*      */       }
/*      */     }
/* 1471 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getSeverityImageForHealthWithLink(Properties alert, String resourceid, String healthid)
/*      */   {
/*      */     try
/*      */     {
/* 1478 */       StringBuilder toreturn = new StringBuilder();
/* 1479 */       String severity = alert.getProperty(resourceid + "#" + healthid);
/* 1480 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1481 */       String message = alert.getProperty(resourceid + "#" + healthid + "#" + "MESSAGE");
/* 1482 */       String title = "";
/* 1483 */       message = EnterpriseUtil.decodeString(message);
/* 1484 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1485 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/* 1486 */       if (("1".equals(severity)) || ("4".equals(severity)))
/*      */       {
/* 1488 */         title = " onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()'";
/*      */       }
/* 1490 */       else if ("5".equals(severity))
/*      */       {
/* 1492 */         title = "title='" + FormatUtil.getString("am.fault.rca.healthisclear.text") + "'";
/*      */       }
/*      */       else
/*      */       {
/* 1496 */         title = "title='" + FormatUtil.getString("am.webclient.rcamessage.healthunknown.text") + "'";
/*      */       }
/* 1498 */       String link = "<a href='javascript:void(0)' " + title + " onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + healthid + "')\">";
/* 1499 */       toreturn.append(v);
/*      */       
/* 1501 */       toreturn.append(link);
/* 1502 */       if (severity == null)
/*      */       {
/* 1504 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1506 */       else if (severity.equals("5"))
/*      */       {
/* 1508 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_clear.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1510 */       else if (severity.equals("4"))
/*      */       {
/* 1512 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_warning.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1514 */       else if (severity.equals("1"))
/*      */       {
/* 1516 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_critical.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1521 */         toreturn.append("<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1523 */       toreturn.append("</a>");
/* 1524 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1528 */       ex.printStackTrace();
/*      */     }
/* 1530 */     return "<img border=\"0\" src=\"/images/icon_health_unknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/*      */   private String getSeverityImageForAvailabilitywithLink(Properties alert, String resourceid, String availabilityid)
/*      */   {
/*      */     try
/*      */     {
/* 1537 */       StringBuilder toreturn = new StringBuilder();
/* 1538 */       String severity = alert.getProperty(resourceid + "#" + availabilityid);
/* 1539 */       String v = "<script>var v = '<span style=\"color: #000000;font-weight:bold;\">';</script>";
/* 1540 */       String message = alert.getProperty(resourceid + "#" + availabilityid + "#" + "MESSAGE");
/* 1541 */       if (message == null)
/*      */       {
/* 1543 */         message = "";
/*      */       }
/*      */       
/* 1546 */       message = FormatUtil.findReplace(message, "'", "\\'");
/* 1547 */       message = FormatUtil.findReplace(message, "\"", "&quot;");
/*      */       
/* 1549 */       String link = "<a href='javascript:void(0)'  onmouseover=\"ddrivetip(this,event,'" + message + "<br>'+v+'" + FormatUtil.getString("am.webclient.tooltip.text") + "</span>',null,true,'#000000')\" onmouseout='hideddrivetip()' onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=" + resourceid + "&attributeid=" + availabilityid + "')\">";
/* 1550 */       toreturn.append(v);
/*      */       
/* 1552 */       toreturn.append(link);
/*      */       
/* 1554 */       if (severity == null)
/*      */       {
/* 1556 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1558 */       else if (severity.equals("5"))
/*      */       {
/* 1560 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_up.gif\"  name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1562 */       else if (severity.equals("1"))
/*      */       {
/* 1564 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_down.gif\" name=\"Image" + ++this.j + "\">");
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1569 */         toreturn.append("<img border=\"0\" src=\"/images/icon_availability_unknown.gif\" name=\"Image" + ++this.j + "\">");
/*      */       }
/* 1571 */       toreturn.append("</a>");
/* 1572 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception ex) {}
/*      */     
/*      */ 
/*      */ 
/* 1578 */     return "<img border=\"0\" src=\"/images/icon_availabilitynunknown.gif\" name=\"Image" + ++this.j + "\">";
/*      */   }
/*      */   
/* 1581 */   public ArrayList getPermittedActions(HashMap actionmap, HashMap invokeActions) { ArrayList actionsavailable = new ArrayList();
/* 1582 */     if (invokeActions != null) {
/* 1583 */       Iterator iterator = invokeActions.keySet().iterator();
/* 1584 */       while (iterator.hasNext()) {
/* 1585 */         String actionid = (String)invokeActions.get((String)iterator.next());
/* 1586 */         if (actionmap.containsKey(actionid)) {
/* 1587 */           actionsavailable.add(actionid);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1592 */     return actionsavailable;
/*      */   }
/*      */   
/*      */   public String getActionParams(HashMap methodArgumentsMap, String rowId, String managedObjectName, String resID, String resourcetype, Properties commonValues) {
/* 1596 */     String actionLink = "";
/* 1597 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1598 */     String query = "";
/* 1599 */     ResultSet rs = null;
/* 1600 */     String methodName = (String)methodArgumentsMap.get("METHODNAME");
/* 1601 */     String isJsp = (String)methodArgumentsMap.get("ISPOPUPJSP");
/* 1602 */     if ((isJsp != null) && (isJsp.equalsIgnoreCase("No"))) {
/* 1603 */       actionLink = "method=" + methodName;
/*      */     }
/* 1605 */     else if ((isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1606 */       actionLink = methodName;
/*      */     }
/* 1608 */     ArrayList methodarglist = (ArrayList)methodArgumentsMap.get(methodName);
/* 1609 */     Iterator itr = methodarglist.iterator();
/* 1610 */     boolean isfirstparam = true;
/* 1611 */     HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1612 */     while (itr.hasNext()) {
/* 1613 */       HashMap argmap = (HashMap)itr.next();
/* 1614 */       String argtype = (String)argmap.get("TYPE");
/* 1615 */       String argname = (String)argmap.get("IDENTITY");
/* 1616 */       String paramname = (String)argmap.get("PARAMETER");
/* 1617 */       String typeId = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/* 1618 */       if ((isfirstparam) && (isJsp != null) && (isJsp.equalsIgnoreCase("Yes"))) {
/* 1619 */         isfirstparam = false;
/* 1620 */         if (actionLink.indexOf("?") > 0)
/*      */         {
/* 1622 */           actionLink = actionLink + "&";
/*      */         }
/*      */         else
/*      */         {
/* 1626 */           actionLink = actionLink + "?";
/*      */         }
/*      */       }
/*      */       else {
/* 1630 */         actionLink = actionLink + "&";
/*      */       }
/* 1632 */       String paramValue = null;
/* 1633 */       String tempargname = argname;
/* 1634 */       if (commonValues.getProperty(tempargname) != null) {
/* 1635 */         paramValue = commonValues.getProperty(tempargname);
/*      */       }
/*      */       else {
/* 1638 */         if (argtype.equalsIgnoreCase("Argument")) {
/* 1639 */           String dbType = com.adventnet.appmanager.db.DBQueryUtil.getDBType();
/* 1640 */           if (dbType.equals("mysql")) {
/* 1641 */             argname = "`" + argname + "`";
/*      */           }
/*      */           else {
/* 1644 */             argname = "\"" + argname + "\"";
/*      */           }
/* 1646 */           query = "select " + argname + " as VALUE from AM_ARGS_" + typeId + " where RESOURCEID=" + resID;
/*      */           try {
/* 1648 */             rs = AMConnectionPool.executeQueryStmt(query);
/* 1649 */             if (rs.next()) {
/* 1650 */               paramValue = rs.getString("VALUE");
/* 1651 */               commonValues.setProperty(tempargname, paramValue);
/*      */             }
/*      */           }
/*      */           catch (SQLException e) {
/* 1655 */             e.printStackTrace();
/*      */           }
/*      */           finally {
/*      */             try {
/* 1659 */               AMConnectionPool.closeStatement(rs);
/*      */             }
/*      */             catch (Exception exc) {
/* 1662 */               exc.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */         
/* 1667 */         if ((argtype.equalsIgnoreCase("Rowid")) && (rowId != null)) {
/* 1668 */           paramValue = rowId;
/*      */         }
/* 1670 */         else if ((argtype.equalsIgnoreCase("MO")) && (managedObjectName != null)) {
/* 1671 */           paramValue = managedObjectName;
/*      */         }
/* 1673 */         else if (argtype.equalsIgnoreCase("ResourceId")) {
/* 1674 */           paramValue = resID;
/*      */         }
/* 1676 */         else if (argtype.equalsIgnoreCase("TypeId")) {
/* 1677 */           paramValue = com.adventnet.appmanager.util.Constants.getTypeId(resourcetype);
/*      */         }
/*      */       }
/* 1680 */       actionLink = actionLink + paramname + "=" + paramValue;
/*      */     }
/* 1682 */     if ((popupProps != null) && (popupProps.size() > 0)) {
/* 1683 */       actionLink = actionLink + "|" + (String)popupProps.get("WinName") + "|";
/* 1684 */       actionLink = actionLink + "width=" + (String)popupProps.get("Width") + ",height=" + (String)popupProps.get("Height") + ",Top=" + (String)popupProps.get("Top") + ",Left=" + (String)popupProps.get("Left") + ",scrollbars=" + (String)popupProps.get("IsScrollBar") + ",resizable=" + (String)popupProps.get("IsResizable");
/*      */     }
/* 1686 */     return actionLink;
/*      */   }
/*      */   
/* 1689 */   public String getActionColDetails(HashMap columnDetails, ArrayList actionsavailable, HashMap actionmap, float width, HashMap rowDetails, String rowid, String resourcetype, String resID, String id1, String availValue, String healthValue, String bgclass, Boolean isdisable, String primaryColId, Properties commonValues) { StringBuilder toreturn = new StringBuilder();
/* 1690 */     String dependentAttribute = null;
/* 1691 */     String align = "left";
/*      */     
/* 1693 */     dependentAttribute = (String)columnDetails.get("DEPENDENTATTRIBUTE");
/* 1694 */     String displayType = (String)columnDetails.get("DISPLAYTYPE");
/* 1695 */     HashMap invokeActionsMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("ACTIONS");
/* 1696 */     HashMap invokeTooltip = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("TOOLTIP");
/* 1697 */     HashMap textOrImageValue = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("VALUES");
/* 1698 */     HashMap dependentValueMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTVALUE");
/* 1699 */     HashMap dependentImageMap = (HashMap)((HashMap)columnDetails.get("INVOCATION")).get("DEPENDENTIMAGE");
/* 1700 */     if ((displayType != null) && (displayType.equals("Image"))) {
/* 1701 */       align = "center";
/*      */     }
/*      */     
/* 1704 */     boolean iscolumntoDisplay = actionsavailable != null;
/* 1705 */     String actualdata = "";
/*      */     
/* 1707 */     if ((dependentAttribute != null) && (!dependentAttribute.trim().equals(""))) {
/* 1708 */       if (dependentAttribute.equalsIgnoreCase("Availability")) {
/* 1709 */         actualdata = availValue;
/*      */       }
/* 1711 */       else if (dependentAttribute.equalsIgnoreCase("Health")) {
/* 1712 */         actualdata = healthValue;
/*      */       } else {
/*      */         try
/*      */         {
/* 1716 */           String attributeName = ConfMonitorConfiguration.getInstance().getAttributeName(resourcetype, dependentAttribute).toUpperCase();
/* 1717 */           actualdata = (String)rowDetails.get(attributeName);
/*      */         }
/*      */         catch (Exception e) {
/* 1720 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1726 */     if ((actionmap != null) && (actionmap.size() > 0) && (iscolumntoDisplay)) {
/* 1727 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' >");
/* 1728 */       toreturn.append("<table>");
/* 1729 */       toreturn.append("<tr>");
/* 1730 */       for (int orderId = 1; orderId <= textOrImageValue.size(); orderId++) {
/* 1731 */         String displayValue = (String)textOrImageValue.get(Integer.toString(orderId));
/* 1732 */         String actionName = (String)invokeActionsMap.get(Integer.toString(orderId));
/* 1733 */         String dependentValue = (String)dependentValueMap.get(Integer.toString(orderId));
/* 1734 */         String toolTip = "";
/* 1735 */         String hideClass = "";
/* 1736 */         String textStyle = "";
/* 1737 */         boolean isreferenced = true;
/* 1738 */         if (invokeTooltip.get(Integer.toString(orderId)) != null) {
/* 1739 */           toolTip = (String)invokeTooltip.get(Integer.toString(orderId));
/* 1740 */           toolTip = toolTip.replaceAll("\"", "&quot;");
/* 1741 */           hideClass = "hideddrivetip()";
/*      */         }
/* 1743 */         if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals(""))) {
/* 1744 */           StringTokenizer valueList = new StringTokenizer(dependentValue, ",");
/* 1745 */           while (valueList.hasMoreTokens()) {
/* 1746 */             String dependentVal = valueList.nextToken();
/* 1747 */             if ((actualdata != null) && (actualdata.equals(dependentVal))) {
/* 1748 */               if ((dependentImageMap != null) && (dependentImageMap.get(dependentValue) != null)) {
/* 1749 */                 displayValue = (String)dependentImageMap.get(dependentValue);
/*      */               }
/* 1751 */               toolTip = "";
/* 1752 */               hideClass = "";
/* 1753 */               isreferenced = false;
/* 1754 */               textStyle = "disabledtext";
/* 1755 */               break;
/*      */             }
/*      */           }
/*      */         }
/* 1759 */         if ((isdisable.booleanValue()) || (actualdata == null)) {
/* 1760 */           toolTip = "";
/* 1761 */           hideClass = "";
/* 1762 */           isreferenced = false;
/* 1763 */           textStyle = "disabledtext";
/* 1764 */           if (dependentImageMap != null) {
/* 1765 */             if ((dependentValue != null) && (!dependentValue.equals("Null")) && (!dependentValue.equals("")) && (dependentImageMap.get(dependentValue) != null)) {
/* 1766 */               displayValue = (String)dependentImageMap.get(dependentValue);
/*      */             }
/*      */             else {
/* 1769 */               displayValue = (String)dependentImageMap.get(Integer.toString(orderId));
/*      */             }
/*      */           }
/*      */         }
/* 1773 */         if ((actionsavailable.contains(actionName)) && (actionmap.get(actionName) != null)) {
/* 1774 */           Boolean confirmBox = (Boolean)((HashMap)actionmap.get(actionName)).get("CONFIRMATION");
/* 1775 */           String confirmmsg = (String)((HashMap)actionmap.get(actionName)).get("MESSAGE");
/* 1776 */           String isJSP = (String)((HashMap)actionmap.get(actionName)).get("ISPOPUPJSP");
/* 1777 */           String managedObject = (String)rowDetails.get(primaryColId);
/* 1778 */           String actionLinks = getActionParams((HashMap)actionmap.get(actionName), rowid, managedObject, resID, resourcetype, commonValues);
/*      */           
/* 1780 */           toreturn.append("<td width='" + width / actionsavailable.size() + "%' align='" + align + "' class='staticlinks'>");
/* 1781 */           if (isreferenced) {
/* 1782 */             toreturn.append("<a href=\"javascript:triggerAction('" + actionLinks + "','" + id1 + "','" + confirmBox + "','" + FormatUtil.getString(confirmmsg) + "','" + isJSP + "');\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">");
/*      */           }
/*      */           else
/*      */           {
/* 1786 */             toreturn.append("<a href=\"javascript:void(0);\" class='staticlinks' onMouseOver=\"ddrivetip(this,event,'" + FormatUtil.getString(toolTip).replace("\"", "&quot;") + "',false,true,'#000000',100,'lightyellow')\" onmouseout=\"" + hideClass + "\">"); }
/* 1787 */           if ((displayValue != null) && (displayType != null) && (displayType.equals("Image"))) {
/* 1788 */             toreturn.append("<img src=\"" + displayValue + "\" hspace=\"4\" border=\"0\" align=\"absmiddle\"/>");
/* 1789 */           } else if ((displayValue != null) && (displayType != null) && (displayType.equals("Text"))) {
/* 1790 */             toreturn.append("<span class=\"" + textStyle + "\">");
/* 1791 */             toreturn.append(FormatUtil.getString(displayValue));
/*      */           }
/* 1793 */           toreturn.append("</span>");
/* 1794 */           toreturn.append("</a>");
/* 1795 */           toreturn.append("</td>");
/*      */         }
/*      */       }
/* 1798 */       toreturn.append("</tr>");
/* 1799 */       toreturn.append("</table>");
/* 1800 */       toreturn.append("</td>");
/*      */     } else {
/* 1802 */       toreturn.append("<td width='" + width + "%'  align='" + align + "' class='" + bgclass + "' > - </td>");
/*      */     }
/*      */     
/* 1805 */     return toreturn.toString();
/*      */   }
/*      */   
/*      */   public String getMOCollectioTime(ArrayList rows, String tablename, String attributeid, String resColumn) {
/* 1809 */     String colTime = null;
/* 1810 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/* 1811 */     if ((rows != null) && (rows.size() > 0)) {
/* 1812 */       Iterator<String> itr = rows.iterator();
/* 1813 */       String maxColQuery = "";
/* 1814 */       for (;;) { if (itr.hasNext()) {
/* 1815 */           maxColQuery = "select max(COLLECTIONTIME) from " + tablename + " where ATTRIBUTEID=" + attributeid + " and " + resColumn + "=" + (String)itr.next();
/* 1816 */           ResultSet maxCol = null;
/*      */           try {
/* 1818 */             maxCol = AMConnectionPool.executeQueryStmt(maxColQuery);
/* 1819 */             while (maxCol.next()) {
/* 1820 */               if (colTime == null) {
/* 1821 */                 colTime = Long.toString(maxCol.getLong(1));
/*      */               }
/*      */               else {
/* 1824 */                 colTime = colTime + "," + Long.toString(maxCol.getLong(1));
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1833 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1835 */               if (maxCol != null)
/* 1836 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1838 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */           catch (Exception e) {}finally
/*      */           {
/* 1833 */             com.adventnet.appmanager.logging.AMLog.debug("Graph Query for att " + attributeid + " :" + maxColQuery);
/*      */             try {
/* 1835 */               if (maxCol != null)
/* 1836 */                 AMConnectionPool.closeStatement(maxCol);
/*      */             } catch (Exception e) {
/* 1838 */               e.printStackTrace();
/*      */             }
/*      */           }
/*      */         }
/*      */       } }
/* 1843 */     return colTime;
/*      */   }
/*      */   
/* 1846 */   public String getTableName(String attributeid, String baseid) { String tablenameqry = "select ATTRIBUTEID,DATATABLE,VALUE_COL from AM_ATTRIBUTES_EXT where ATTRIBUTEID=" + attributeid;
/* 1847 */     tablename = null;
/* 1848 */     ResultSet rsTable = null;
/* 1849 */     AMConnectionPool cp = AMConnectionPool.getInstance();
/*      */     try {
/* 1851 */       rsTable = AMConnectionPool.executeQueryStmt(tablenameqry);
/* 1852 */       while (rsTable.next()) {
/* 1853 */         tablename = rsTable.getString("DATATABLE");
/* 1854 */         if ((tablename.equals("AM_ManagedObjectData")) && (rsTable.getString("VALUE_COL").equals("RESPONSETIME"))) {
/* 1855 */           tablename = "AM_Script_Numeric_Data_" + baseid;
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
/* 1868 */       return tablename;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1859 */       e.printStackTrace();
/*      */     } finally {
/*      */       try {
/* 1862 */         if (rsTable != null)
/* 1863 */           AMConnectionPool.closeStatement(rsTable);
/*      */       } catch (Exception e) {
/* 1865 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public String getArgsListtoShowonClick(HashMap showArgsMap, String row) {
/* 1871 */     String argsList = "";
/* 1872 */     ArrayList showArgslist = new ArrayList();
/*      */     try {
/* 1874 */       if (showArgsMap.get(row) != null) {
/* 1875 */         showArgslist = (ArrayList)showArgsMap.get(row);
/* 1876 */         if (showArgslist != null) {
/* 1877 */           for (int i = 0; i < showArgslist.size(); i++) {
/* 1878 */             if (argsList.trim().equals("")) {
/* 1879 */               argsList = (String)showArgslist.get(i);
/*      */             }
/*      */             else {
/* 1882 */               argsList = argsList + "," + (String)showArgslist.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1889 */       e.printStackTrace();
/* 1890 */       return "";
/*      */     }
/* 1892 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getArgsListToHideOnClick(HashMap hideArgsMap, String row)
/*      */   {
/* 1897 */     String argsList = "";
/* 1898 */     ArrayList hideArgsList = new ArrayList();
/*      */     try
/*      */     {
/* 1901 */       if (hideArgsMap.get(row) != null)
/*      */       {
/* 1903 */         hideArgsList = (ArrayList)hideArgsMap.get(row);
/* 1904 */         if (hideArgsList != null)
/*      */         {
/* 1906 */           for (int i = 0; i < hideArgsList.size(); i++)
/*      */           {
/* 1908 */             if (argsList.trim().equals(""))
/*      */             {
/* 1910 */               argsList = (String)hideArgsList.get(i);
/*      */             }
/*      */             else
/*      */             {
/* 1914 */               argsList = argsList + "," + (String)hideArgsList.get(i);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 1922 */       ex.printStackTrace();
/*      */     }
/* 1924 */     return argsList;
/*      */   }
/*      */   
/*      */   public String getTableActionsList(ArrayList tActionList, HashMap actionmap, String tableName, Properties commonValues, String resourceId, String resourceType) {
/* 1928 */     StringBuilder toreturn = new StringBuilder();
/* 1929 */     StringBuilder addtoreturn = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1936 */     if ((tActionList != null) && (tActionList.size() > 0)) {
/* 1937 */       Iterator itr = tActionList.iterator();
/* 1938 */       while (itr.hasNext()) {
/* 1939 */         Boolean confirmBox = Boolean.valueOf(false);
/* 1940 */         String confirmmsg = "";
/* 1941 */         String link = "";
/* 1942 */         String isJSP = "NO";
/* 1943 */         HashMap tactionMap = (HashMap)itr.next();
/* 1944 */         boolean isTableAction = tactionMap.containsKey("ACTION-NAME");
/* 1945 */         String actionName = isTableAction ? (String)tactionMap.get("ACTION-NAME") : (String)tactionMap.get("LINK-NAME");
/* 1946 */         String actionId = (String)tactionMap.get("ACTIONID");
/* 1947 */         if ((actionId != null) && (actionName != null) && (!actionName.trim().equals("")) && (!actionId.trim().equals("")) && 
/* 1948 */           (actionmap.containsKey(actionId))) {
/* 1949 */           HashMap methodArgumentsMap = (HashMap)actionmap.get(actionId);
/* 1950 */           HashMap popupProps = (HashMap)methodArgumentsMap.get("POPUP-PROPS");
/* 1951 */           confirmBox = (Boolean)methodArgumentsMap.get("CONFIRMATION");
/* 1952 */           confirmmsg = (String)methodArgumentsMap.get("MESSAGE");
/* 1953 */           isJSP = (String)methodArgumentsMap.get("ISPOPUPJSP");
/*      */           
/* 1955 */           link = getActionParams(methodArgumentsMap, null, null, resourceId, resourceType, commonValues);
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1961 */           if (isTableAction) {
/* 1962 */             toreturn.append("<option value=" + actionId + ">" + FormatUtil.getString(actionName) + "</option>");
/*      */           }
/*      */           else {
/* 1965 */             tableName = "Link";
/* 1966 */             toreturn.append("<td align=\"right\" style=\"padding-right:10px\">");
/* 1967 */             toreturn.append("<a class=\"bodytextboldwhiteun\" style='cursor:pointer' ");
/* 1968 */             toreturn.append("onClick=\"javascript:customLinks('" + actionId + "','" + resourceId + "')\">" + FormatUtil.getString(actionName));
/* 1969 */             toreturn.append("</a></td>");
/*      */           }
/* 1971 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_isJSP' value='" + isJSP + "'/>");
/* 1972 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmBox' value='" + confirmBox + "'/>");
/* 1973 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_confirmmsg' value='" + FormatUtil.getString(confirmmsg) + "'/>");
/* 1974 */           addtoreturn.append("<input type='hidden' id='" + tableName + "_" + actionId + "_link' value='" + link + "'/>");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1980 */     return toreturn.toString() + addtoreturn.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void printMGTree(DefaultMutableTreeNode rootNode, StringBuilder builder)
/*      */   {
/* 1986 */     for (Enumeration<DefaultMutableTreeNode> enu = rootNode.children(); enu.hasMoreElements();)
/*      */     {
/* 1988 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)enu.nextElement();
/* 1989 */       Properties prop = (Properties)node.getUserObject();
/* 1990 */       String mgID = prop.getProperty("label");
/* 1991 */       String mgName = prop.getProperty("value");
/* 1992 */       String isParent = prop.getProperty("isParent");
/* 1993 */       int mgIDint = Integer.parseInt(mgID);
/* 1994 */       if ((EnterpriseUtil.isAdminServer()) && (mgIDint > EnterpriseUtil.RANGE))
/*      */       {
/* 1996 */         mgName = mgName + "(" + com.adventnet.appmanager.server.framework.comm.CommDBUtil.getManagedServerNameWithPort(mgID) + ")";
/*      */       }
/* 1998 */       builder.append("<LI id='" + prop.getProperty("label") + "_list' ><A ");
/* 1999 */       if (node.getChildCount() > 0)
/*      */       {
/* 2001 */         if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */         {
/* 2003 */           builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */         }
/* 2005 */         else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */         {
/* 2007 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         else
/*      */         {
/* 2011 */           builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2016 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2018 */         builder.append("style='background-color:#f9f9f9;border-bottom: 1px solid #ECECEC;border-top:none; border-right:none; border-left:none;'");
/*      */       }
/* 2020 */       else if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("false")))
/*      */       {
/* 2022 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       else
/*      */       {
/* 2026 */         builder.append(" style='padding-left:").append(node.getLevel() * 15 + "px;'");
/*      */       }
/*      */       
/* 2029 */       builder.append(" onmouseout=\"changeStyle(this);\" onmouseover=\"SetSelected(this)\" onclick=\"SelectMonitorGroup('service_list_left1','" + prop.getProperty("value") + "','" + prop.getProperty("label") + "','leftimage1')\"> ");
/* 2030 */       if ((prop.getProperty("isParent") != null) && (prop.getProperty("isParent").equals("true")))
/*      */       {
/* 2032 */         builder.append("<img src='images/icon_monitors_mg.png' alt='' style='position:relative; top:5px;'/><b>" + prop.getProperty("value") + "</b></a></li>");
/*      */       }
/*      */       else
/*      */       {
/* 2036 */         builder.append(prop.getProperty("value") + "</a></li>");
/*      */       }
/* 2038 */       if (node.getChildCount() > 0)
/*      */       {
/* 2040 */         builder.append("<UL>");
/* 2041 */         printMGTree(node, builder);
/* 2042 */         builder.append("</UL>");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/* 2047 */   public String getColumnGraph(LinkedHashMap graphData, HashMap attidMap) { Iterator it = graphData.keySet().iterator();
/* 2048 */     StringBuffer toReturn = new StringBuffer();
/* 2049 */     String table = "-";
/*      */     try {
/* 2051 */       java.text.DecimalFormat twoDecPer = new java.text.DecimalFormat("###,###.##");
/* 2052 */       LinkedHashMap attVsWidthProps = new LinkedHashMap();
/* 2053 */       float total = 0.0F;
/* 2054 */       while (it.hasNext()) {
/* 2055 */         String attName = (String)it.next();
/* 2056 */         String data = (String)attidMap.get(attName.toUpperCase());
/* 2057 */         boolean roundOffData = false;
/* 2058 */         if ((data != null) && (!data.equals(""))) {
/* 2059 */           if (data.indexOf(",") != -1) {
/* 2060 */             data = data.replaceAll(",", "");
/*      */           }
/*      */           try {
/* 2063 */             float value = Float.parseFloat(data);
/* 2064 */             if (value == 0.0F) {
/*      */               continue;
/*      */             }
/* 2067 */             total += value;
/* 2068 */             attVsWidthProps.put(attName, value + "");
/*      */           }
/*      */           catch (Exception e) {
/* 2071 */             e.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 2076 */       Iterator attVsWidthList = attVsWidthProps.keySet().iterator();
/* 2077 */       while (attVsWidthList.hasNext()) {
/* 2078 */         String attName = (String)attVsWidthList.next();
/* 2079 */         String data = (String)attVsWidthProps.get(attName);
/* 2080 */         HashMap graphDetails = (HashMap)graphData.get(attName);
/* 2081 */         String unit = graphDetails.get("Unit") != null ? "(" + FormatUtil.getString((String)graphDetails.get("Unit")) + ")" : "";
/* 2082 */         String toolTip = graphDetails.get("ToolTip") != null ? "title=\"" + FormatUtil.getString((String)graphDetails.get("ToolTip")) + " - " + data + unit + "\"" : "";
/* 2083 */         String className = (String)graphDetails.get("ClassName");
/* 2084 */         float percentage = Float.parseFloat(data) * 100.0F / total;
/* 2085 */         if (percentage < 1.0F)
/*      */         {
/* 2087 */           data = percentage + "";
/*      */         }
/* 2089 */         toReturn.append("<td class=\"" + className + "\" width=\"" + twoDecPer.format(percentage) + "%\"" + toolTip + "><img src=\"/images/spacer.gif\"  height=\"10\" width=\"90%\"></td>");
/*      */       }
/* 2091 */       if (toReturn.length() > 0) {
/* 2092 */         table = "<table align=\"center\" width =\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"graphborder\"><tr>" + toReturn.toString() + "</tr></table>";
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2096 */       e.printStackTrace();
/*      */     }
/* 2098 */     return table;
/*      */   }
/*      */   
/*      */ 
/*      */   public String[] splitMultiConditionThreshold(String criticalcondition, String criticalThValue)
/*      */   {
/* 2104 */     String[] splitvalues = { criticalcondition, criticalThValue };
/* 2105 */     List<String> criticalThresholdValues = com.adventnet.appmanager.util.AMRegexUtil.getThresholdGroups(criticalcondition, true);
/* 2106 */     System.out.println("CRITICALTHGROPS " + criticalThresholdValues);
/* 2107 */     if ((criticalThresholdValues != null) && (criticalThresholdValues.size() > 5)) {
/* 2108 */       String condition1 = (String)criticalThresholdValues.get(0);
/* 2109 */       String thvalue1 = (String)criticalThresholdValues.get(1);
/* 2110 */       String conditionjoiner = (String)criticalThresholdValues.get(4);
/* 2111 */       String condition2 = (String)criticalThresholdValues.get(5);
/* 2112 */       String thvalue2 = (String)criticalThresholdValues.get(6);
/*      */       
/*      */ 
/* 2115 */       StringBuilder multiplecondition = new StringBuilder(condition1);
/* 2116 */       multiplecondition.append(" ").append(thvalue1).append(" ").append(conditionjoiner).append(" ").append(condition2).append(" ").append(thvalue2);
/* 2117 */       splitvalues[0] = multiplecondition.toString();
/* 2118 */       splitvalues[1] = "";
/*      */     }
/*      */     
/* 2121 */     return splitvalues;
/*      */   }
/*      */   
/*      */   public Map<String, String[]> setSelectedCondition(String condition, int thresholdType)
/*      */   {
/* 2126 */     LinkedHashMap<String, String[]> conditionsMap = new LinkedHashMap();
/* 2127 */     if (thresholdType != 3) {
/* 2128 */       conditionsMap.put("LT", new String[] { "", "<" });
/* 2129 */       conditionsMap.put("GT", new String[] { "", ">" });
/* 2130 */       conditionsMap.put("EQ", new String[] { "", "=" });
/* 2131 */       conditionsMap.put("LE", new String[] { "", "<=" });
/* 2132 */       conditionsMap.put("GE", new String[] { "", ">=" });
/* 2133 */       conditionsMap.put("NE", new String[] { "", "!=" });
/*      */     } else {
/* 2135 */       conditionsMap.put("CT", new String[] { "", "am.fault.conditions.string.contains" });
/* 2136 */       conditionsMap.put("DC", new String[] { "", "am.fault.conditions.string.doesnotcontain" });
/* 2137 */       conditionsMap.put("QL", new String[] { "", "am.fault.conditions.string.equalto" });
/* 2138 */       conditionsMap.put("NQ", new String[] { "", "am.fault.conditions.string.notequalto" });
/* 2139 */       conditionsMap.put("SW", new String[] { "", "am.fault.conditions.string.startswith" });
/* 2140 */       conditionsMap.put("EW", new String[] { "", "am.fault.conditions.string.endswith" });
/*      */     }
/* 2142 */     String[] updateSelected = (String[])conditionsMap.get(condition);
/* 2143 */     if (updateSelected != null) {
/* 2144 */       updateSelected[0] = "selected";
/*      */     }
/* 2146 */     return conditionsMap;
/*      */   }
/*      */   
/*      */   public String getCustomMessage(String monitorType, String commaSeparatedMsgId, String uiElement, ArrayList<String> listOfIdsToRemove) {
/*      */     try {
/* 2151 */       StringBuffer toreturn = new StringBuffer("");
/* 2152 */       if (commaSeparatedMsgId != null) {
/* 2153 */         StringTokenizer msgids = new StringTokenizer(commaSeparatedMsgId, ",");
/* 2154 */         int count = 0;
/* 2155 */         while (msgids.hasMoreTokens()) {
/* 2156 */           String id = msgids.nextToken();
/* 2157 */           String message = ConfMonitorConfiguration.getInstance().getMessageTextForId(monitorType, id);
/* 2158 */           String image = ConfMonitorConfiguration.getInstance().getMessageImageForId(monitorType, id);
/* 2159 */           count++;
/* 2160 */           if (!listOfIdsToRemove.contains("MESSAGE_" + id)) {
/* 2161 */             if (toreturn.length() == 0) {
/* 2162 */               toreturn.append("<table width=\"100%\">");
/*      */             }
/* 2164 */             toreturn.append("<tr><td width=\"100%\" class=\"msg-table-width\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");
/* 2165 */             if (!image.trim().equals("")) {
/* 2166 */               toreturn.append("<td class=\"msg-table-width-bg\"><img width=\"18\" height=\"18\" alt=\"Icon\" src=\"" + image + "\">&nbsp;</td>");
/*      */             }
/* 2168 */             toreturn.append("<td class=\"msg-table-width\"><div id=\"htmlMessage\">" + message + "</div></td>");
/* 2169 */             toreturn.append("</tr></tbody></table></td></tr>");
/*      */           }
/*      */         }
/* 2172 */         if (toreturn.length() > 0) {
/* 2173 */           toreturn.append("TABLE".equals(uiElement) ? "<tr><td><img src=\"../images/spacer.gif\" width=\"10\"></td></tr></table>" : "</table>");
/*      */         }
/*      */       }
/*      */       
/* 2177 */       return toreturn.toString();
/*      */     }
/*      */     catch (Exception e) {
/* 2180 */       e.printStackTrace(); }
/* 2181 */     return "";
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/* 2187 */   private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2193 */   private static Map<String, Long> _jspx_dependants = new HashMap(1);
/* 2194 */   static { _jspx_dependants.put("/jsp/util.jspf", Long.valueOf(1473429417000L)); }
/*      */   
/*      */ 
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage;
/*      */   
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fif_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fchoose;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fc_005fotherwise;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer;
/*      */   private TagHandlerPool _005fjspx_005ftagPool_005ffmt_005fmessage;
/*      */   private javax.el.ExpressionFactory _el_expressionfactory;
/*      */   private org.apache.tomcat.InstanceManager _jsp_instancemanager;
/*      */   public Map<String, Long> getDependants()
/*      */   {
/* 2216 */     return _jspx_dependants;
/*      */   }
/*      */   
/*      */   public void _jspInit() {
/* 2220 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2221 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2222 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2223 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2224 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2225 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2226 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2227 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2228 */     this._005fjspx_005ftagPool_005fc_005fchoose = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2229 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2230 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2231 */     this._005fjspx_005ftagPool_005fc_005fotherwise = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2232 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2233 */     this._005fjspx_005ftagPool_005ffmt_005fmessage = TagHandlerPool.getTagHandlerPool(getServletConfig());
/* 2234 */     this._el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
/* 2235 */     this._jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
/*      */   }
/*      */   
/*      */   public void _jspDestroy() {
/* 2239 */     this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.release();
/* 2240 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.release();
/* 2241 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.release();
/* 2242 */     this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.release();
/* 2243 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.release();
/* 2244 */     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.release();
/* 2245 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.release();
/* 2246 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
/* 2247 */     this._005fjspx_005ftagPool_005fc_005fchoose.release();
/* 2248 */     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.release();
/* 2249 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.release();
/* 2250 */     this._005fjspx_005ftagPool_005fc_005fotherwise.release();
/* 2251 */     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.release();
/* 2252 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.release();
/*      */   }
/*      */   
/*      */ 
/*      */   public void _jspService(HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
/*      */     throws java.io.IOException, javax.servlet.ServletException
/*      */   {
/* 2259 */     HttpSession session = null;
/*      */     
/*      */ 
/* 2262 */     JspWriter out = null;
/* 2263 */     Object page = this;
/* 2264 */     JspWriter _jspx_out = null;
/* 2265 */     PageContext _jspx_page_context = null;
/*      */     
/*      */     try
/*      */     {
/* 2269 */       response.setContentType("text/html;charset=UTF-8");
/* 2270 */       PageContext pageContext = _jspxFactory.getPageContext(this, request, response, "/jsp/ErrorPage.jsp", true, 8192, true);
/*      */       
/* 2272 */       _jspx_page_context = pageContext;
/* 2273 */       javax.servlet.ServletContext application = pageContext.getServletContext();
/* 2274 */       javax.servlet.ServletConfig config = pageContext.getServletConfig();
/* 2275 */       session = pageContext.getSession();
/* 2276 */       out = pageContext.getOut();
/* 2277 */       _jspx_out = out;
/*      */       
/* 2279 */       out.write("<!DOCTYPE html>\n");
/* 2280 */       out.write("\n<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n");
/* 2281 */       out.write(10);
/*      */       
/* 2283 */       request.setAttribute("HelpKey", "Server Information");
/*      */       
/* 2285 */       out.write(10);
/* 2286 */       com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph wlsGraph = null;
/* 2287 */       wlsGraph = (com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph)_jspx_page_context.getAttribute("wlsGraph", 1);
/* 2288 */       if (wlsGraph == null) {
/* 2289 */         wlsGraph = new com.adventnet.appmanager.server.wlogic.bean.GetWLSGraph();
/* 2290 */         _jspx_page_context.setAttribute("wlsGraph", wlsGraph, 1);
/*      */       }
/* 2292 */       out.write(10);
/* 2293 */       MySqlGraphs mysqlgraph = null;
/* 2294 */       mysqlgraph = (MySqlGraphs)_jspx_page_context.getAttribute("mysqlgraph", 1);
/* 2295 */       if (mysqlgraph == null) {
/* 2296 */         mysqlgraph = new MySqlGraphs();
/* 2297 */         _jspx_page_context.setAttribute("mysqlgraph", mysqlgraph, 1);
/*      */       }
/* 2299 */       out.write(10);
/* 2300 */       CpuGraph cpugraph = null;
/* 2301 */       cpugraph = (CpuGraph)_jspx_page_context.getAttribute("cpugraph", 1);
/* 2302 */       if (cpugraph == null) {
/* 2303 */         cpugraph = new CpuGraph();
/* 2304 */         _jspx_page_context.setAttribute("cpugraph", cpugraph, 1);
/*      */       }
/* 2306 */       out.write(10);
/* 2307 */       HostResourceBean hrbean = null;
/* 2308 */       hrbean = (HostResourceBean)_jspx_page_context.getAttribute("hrbean", 1);
/* 2309 */       if (hrbean == null) {
/* 2310 */         hrbean = new HostResourceBean();
/* 2311 */         _jspx_page_context.setAttribute("hrbean", hrbean, 1);
/*      */       }
/* 2313 */       out.write(10);
/* 2314 */       MsSqlGraphs mssqlgraph = null;
/* 2315 */       mssqlgraph = (MsSqlGraphs)_jspx_page_context.getAttribute("mssqlgraph", 1);
/* 2316 */       if (mssqlgraph == null) {
/* 2317 */         mssqlgraph = new MsSqlGraphs();
/* 2318 */         _jspx_page_context.setAttribute("mssqlgraph", mssqlgraph, 1);
/*      */       }
/* 2320 */       out.write(10);
/* 2321 */       PerformanceBean perfgraph = null;
/* 2322 */       perfgraph = (PerformanceBean)_jspx_page_context.getAttribute("perfgraph", 2);
/* 2323 */       if (perfgraph == null) {
/* 2324 */         perfgraph = new PerformanceBean();
/* 2325 */         _jspx_page_context.setAttribute("perfgraph", perfgraph, 2);
/*      */       }
/* 2327 */       out.write("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/* 2328 */       ManagedApplication mo = null;
/* 2329 */       mo = (ManagedApplication)_jspx_page_context.getAttribute("mo", 1);
/* 2330 */       if (mo == null) {
/* 2331 */         mo = new ManagedApplication();
/* 2332 */         _jspx_page_context.setAttribute("mo", mo, 1);
/*      */       }
/* 2334 */       out.write("\n\n\n\n\n\n\n<script>\nfunction ShowProcessingMessage()\n{\n\ttoggleDiv('showmessage');\n\tdocument.form2.action=\"/showresource.do?method=sendSupport\";\n\tdocument.form2.submit();\n}\nfunction fnRemove()\n{\n\n   location.href=\"/jsp/UpdateGlobalSettings.jsp?key=freeuser&value=false&redirect=support\";\n}\n\nfunction triggerHeapDump() {     \n        $.ajax({\n        type: \"POST\", //No I18n\n        url: \"/DebugInfo.do\",  //No I18n\n        data: \"&method=triggerHeapDump&resourceid=-1\",  //NO I18N             \n        success: function(response)\n        {\n         $(\"#testCredentialResult\").html(\"<table style='padding-top:5px;padding-bottom:5px;' width='100%' cellpadding='0' cellspacing='0'><tr><td class='monitorsheading'> \"+response+\"</td></tr></table>\").fadeIn().delay(6000).fadeOut(); //NO I18N  \n        }\n});\n}\n\n </script>\n\n");
/*      */       
/* 2336 */       com.adventnet.appmanager.mssql.struts.MsSQLAction mssql = new com.adventnet.appmanager.mssql.struts.MsSQLAction();
/* 2337 */       Map amMysqlDetails = (Map)request.getAttribute("ammysqlmap");
/* 2338 */       int mysqlResourceID = Integer.parseInt((String)request.getAttribute("am.mysql.resourceid"));
/* 2339 */       int mssqlResourceID = Integer.parseInt((String)request.getAttribute("am.mssql.resourceid"));
/* 2340 */       Properties data = mssql.getPerformance(mssqlResourceID);
/* 2341 */       mysqlgraph.setresid(mysqlResourceID);
/* 2342 */       String congraph = FormatUtil.getString("am.webclient.serverstatus.congrapg");
/* 2343 */       String reqrate = FormatUtil.getString("am.webclient.serverstatus.reqrate");
/* 2344 */       String reqgraph = FormatUtil.getString("am.webclient.serverstatus.reqgraph");
/* 2345 */       String con = FormatUtil.getString("am.webclient.serverstatus.con");
/* 2346 */       String send = FormatUtil.getString("am.webclient.serverstatus.send");
/* 2347 */       String rec = FormatUtil.getString("am.webclient.serverstatus.rec");
/* 2348 */       com.adventnet.appmanager.server.framework.FreeEditionDetails fd = com.adventnet.appmanager.server.framework.FreeEditionDetails.getFreeEditionDetails();
/*      */       
/*      */ 
/*      */ 
/* 2352 */       String hostresourcename = (String)request.getAttribute("hostresourcename");
/* 2353 */       String hostresourceid = (String)request.getAttribute("hostresourceid");
/* 2354 */       String hosttype = (String)request.getAttribute("hosttype");
/* 2355 */       Throwable exception = (Throwable)request.getAttribute("amexception");
/* 2356 */       String showsupport = (String)request.getAttribute("showsendsupport");
/* 2357 */       String proxy = (String)request.getAttribute("proxynotenabled");
/* 2358 */       if (proxy != null)
/*      */       {
/* 2360 */         request.setAttribute("proxy", proxy);
/*      */       }
/* 2362 */       boolean showdiv = false;
/* 2363 */       if ((exception != null) || (showsupport != null))
/*      */       {
/* 2365 */         showdiv = true;
/*      */       }
/* 2367 */       hrbean.setresourceName(hostresourcename);
/* 2368 */       hrbean.setResourceId(hostresourceid);
/* 2369 */       long hostmaxcollectiontime = hrbean.getmaxcollectiontime();
/*      */       
/* 2371 */       perfgraph.setresourceid(Integer.parseInt(hostresourceid));
/* 2372 */       perfgraph.setEntity("Response Time");
/*      */       
/* 2374 */       com.adventnet.appmanager.hostresource.struts.HostResourceAction hra = new com.adventnet.appmanager.hostresource.struts.HostResourceAction();
/* 2375 */       Properties ids = hra.getAttributeIDS(hosttype);
/* 2376 */       request.setAttribute("ids", ids);
/* 2377 */       ArrayList attribIDs = new ArrayList();
/* 2378 */       ArrayList resIDs = new ArrayList();
/* 2379 */       resIDs.add(hostresourceid);
/* 2380 */       resIDs.add(String.valueOf(mysqlResourceID));
/* 2381 */       attribIDs.add("101");
/* 2382 */       attribIDs.add("102");
/* 2383 */       attribIDs.add(ids.getProperty("CPU Utilization"));
/* 2384 */       attribIDs.add(ids.getProperty("Response Time"));
/* 2385 */       Properties alert = getStatus(resIDs, attribIDs);
/* 2386 */       String redirect = "/common/serverinfo.do";
/*      */       
/* 2388 */       String xaxis_time = FormatUtil.getString("am.webclient.common.axisname.time.text");
/* 2389 */       String yaxis_timeinms = FormatUtil.getString("am.webclient.support.timeinms.text");
/* 2390 */       String yaxis_request = FormatUtil.getString("am.webclient.support.requestpersec.text");
/*      */       
/*      */ 
/* 2393 */       out.write("\n<script language=\"JavaScript\" type=\"text/JavaScript\">\n<!--\nfunction MM_reloadPage(init) {  //reloads the window if Nav4 resized\n  if (init==true) with (navigator) {if ((appName==\"Netscape\")&&(parseInt(appVersion)==4)) {\n    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}\n  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();\n}\nMM_reloadPage(true);\n//-->\n</script>\n<style>\na {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #595959;\n\ttext-decoration: underline;\n}\n.txtGlobal {\n\tfont-family: Arial, Helvetica, sans-serif;\n\tfont-size: 11px;\n\tcolor: #5959595;\n\ttext-decoration: underline;\n}\n</style>\n<body leftmargin=\"0\" topmargin=\"0\" marginwidth=\"0\" marginheight=\"0\" >\n");
/*      */       
/* 2395 */       InsertTag _jspx_th_tiles_005finsert_005f0 = (InsertTag)this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.get(InsertTag.class);
/* 2396 */       _jspx_th_tiles_005finsert_005f0.setPageContext(_jspx_page_context);
/* 2397 */       _jspx_th_tiles_005finsert_005f0.setParent(null);
/*      */       
/* 2399 */       _jspx_th_tiles_005finsert_005f0.setPage("/jsp/BasicLayoutNoLeft.jsp");
/* 2400 */       int _jspx_eval_tiles_005finsert_005f0 = _jspx_th_tiles_005finsert_005f0.doStartTag();
/* 2401 */       if (_jspx_eval_tiles_005finsert_005f0 != 0) {
/*      */         for (;;) {
/* 2403 */           out.write(32);
/*      */           
/* 2405 */           PutTag _jspx_th_tiles_005fput_005f0 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 2406 */           _jspx_th_tiles_005fput_005f0.setPageContext(_jspx_page_context);
/* 2407 */           _jspx_th_tiles_005fput_005f0.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2409 */           _jspx_th_tiles_005fput_005f0.setName("title");
/*      */           
/* 2411 */           _jspx_th_tiles_005fput_005f0.setValue(FormatUtil.getString("am.webclient.title.productdetails.text"));
/* 2412 */           int _jspx_eval_tiles_005fput_005f0 = _jspx_th_tiles_005fput_005f0.doStartTag();
/* 2413 */           if (_jspx_th_tiles_005fput_005f0.doEndTag() == 5) {
/* 2414 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0); return;
/*      */           }
/*      */           
/* 2417 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f0);
/* 2418 */           out.write(10);
/* 2419 */           if (_jspx_meth_tiles_005fput_005f1(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 2421 */           out.write(32);
/*      */           
/* 2423 */           PutTag _jspx_th_tiles_005fput_005f2 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2424 */           _jspx_th_tiles_005fput_005f2.setPageContext(_jspx_page_context);
/* 2425 */           _jspx_th_tiles_005fput_005f2.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2427 */           _jspx_th_tiles_005fput_005f2.setName("LeftArea");
/*      */           
/* 2429 */           _jspx_th_tiles_005fput_005f2.setType("string");
/* 2430 */           int _jspx_eval_tiles_005fput_005f2 = _jspx_th_tiles_005fput_005f2.doStartTag();
/* 2431 */           if (_jspx_eval_tiles_005fput_005f2 != 0) {
/* 2432 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2433 */               out = _jspx_page_context.pushBody();
/* 2434 */               _jspx_th_tiles_005fput_005f2.setBodyContent((BodyContent)out);
/* 2435 */               _jspx_th_tiles_005fput_005f2.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2438 */               out.write(10);
/* 2439 */               out.write("<!--$Id$ -->\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
/*      */               
/* 2441 */               DefineTag _jspx_th_bean_005fdefine_005f0 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2442 */               _jspx_th_bean_005fdefine_005f0.setPageContext(_jspx_page_context);
/* 2443 */               _jspx_th_bean_005fdefine_005f0.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2445 */               _jspx_th_bean_005fdefine_005f0.setId("available");
/*      */               
/* 2447 */               _jspx_th_bean_005fdefine_005f0.setName("colors");
/*      */               
/* 2449 */               _jspx_th_bean_005fdefine_005f0.setProperty("AVAILABLE");
/*      */               
/* 2451 */               _jspx_th_bean_005fdefine_005f0.setType("java.lang.String");
/* 2452 */               int _jspx_eval_bean_005fdefine_005f0 = _jspx_th_bean_005fdefine_005f0.doStartTag();
/* 2453 */               if (_jspx_th_bean_005fdefine_005f0.doEndTag() == 5) {
/* 2454 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0); return;
/*      */               }
/*      */               
/* 2457 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f0);
/* 2458 */               String available = null;
/* 2459 */               available = (String)_jspx_page_context.findAttribute("available");
/* 2460 */               out.write(10);
/*      */               
/* 2462 */               DefineTag _jspx_th_bean_005fdefine_005f1 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2463 */               _jspx_th_bean_005fdefine_005f1.setPageContext(_jspx_page_context);
/* 2464 */               _jspx_th_bean_005fdefine_005f1.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2466 */               _jspx_th_bean_005fdefine_005f1.setId("unavailable");
/*      */               
/* 2468 */               _jspx_th_bean_005fdefine_005f1.setName("colors");
/*      */               
/* 2470 */               _jspx_th_bean_005fdefine_005f1.setProperty("UNAVAILABLE");
/*      */               
/* 2472 */               _jspx_th_bean_005fdefine_005f1.setType("java.lang.String");
/* 2473 */               int _jspx_eval_bean_005fdefine_005f1 = _jspx_th_bean_005fdefine_005f1.doStartTag();
/* 2474 */               if (_jspx_th_bean_005fdefine_005f1.doEndTag() == 5) {
/* 2475 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1); return;
/*      */               }
/*      */               
/* 2478 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f1);
/* 2479 */               String unavailable = null;
/* 2480 */               unavailable = (String)_jspx_page_context.findAttribute("unavailable");
/* 2481 */               out.write(10);
/*      */               
/* 2483 */               DefineTag _jspx_th_bean_005fdefine_005f2 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2484 */               _jspx_th_bean_005fdefine_005f2.setPageContext(_jspx_page_context);
/* 2485 */               _jspx_th_bean_005fdefine_005f2.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2487 */               _jspx_th_bean_005fdefine_005f2.setId("unmanaged");
/*      */               
/* 2489 */               _jspx_th_bean_005fdefine_005f2.setName("colors");
/*      */               
/* 2491 */               _jspx_th_bean_005fdefine_005f2.setProperty("UNMANAGED");
/*      */               
/* 2493 */               _jspx_th_bean_005fdefine_005f2.setType("java.lang.String");
/* 2494 */               int _jspx_eval_bean_005fdefine_005f2 = _jspx_th_bean_005fdefine_005f2.doStartTag();
/* 2495 */               if (_jspx_th_bean_005fdefine_005f2.doEndTag() == 5) {
/* 2496 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2); return;
/*      */               }
/*      */               
/* 2499 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f2);
/* 2500 */               String unmanaged = null;
/* 2501 */               unmanaged = (String)_jspx_page_context.findAttribute("unmanaged");
/* 2502 */               out.write(10);
/*      */               
/* 2504 */               DefineTag _jspx_th_bean_005fdefine_005f3 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2505 */               _jspx_th_bean_005fdefine_005f3.setPageContext(_jspx_page_context);
/* 2506 */               _jspx_th_bean_005fdefine_005f3.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2508 */               _jspx_th_bean_005fdefine_005f3.setId("scheduled");
/*      */               
/* 2510 */               _jspx_th_bean_005fdefine_005f3.setName("colors");
/*      */               
/* 2512 */               _jspx_th_bean_005fdefine_005f3.setProperty("SCHEDULED");
/*      */               
/* 2514 */               _jspx_th_bean_005fdefine_005f3.setType("java.lang.String");
/* 2515 */               int _jspx_eval_bean_005fdefine_005f3 = _jspx_th_bean_005fdefine_005f3.doStartTag();
/* 2516 */               if (_jspx_th_bean_005fdefine_005f3.doEndTag() == 5) {
/* 2517 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3); return;
/*      */               }
/*      */               
/* 2520 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f3);
/* 2521 */               String scheduled = null;
/* 2522 */               scheduled = (String)_jspx_page_context.findAttribute("scheduled");
/* 2523 */               out.write(10);
/*      */               
/* 2525 */               DefineTag _jspx_th_bean_005fdefine_005f4 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2526 */               _jspx_th_bean_005fdefine_005f4.setPageContext(_jspx_page_context);
/* 2527 */               _jspx_th_bean_005fdefine_005f4.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2529 */               _jspx_th_bean_005fdefine_005f4.setId("critical");
/*      */               
/* 2531 */               _jspx_th_bean_005fdefine_005f4.setName("colors");
/*      */               
/* 2533 */               _jspx_th_bean_005fdefine_005f4.setProperty("CRITICAL");
/*      */               
/* 2535 */               _jspx_th_bean_005fdefine_005f4.setType("java.lang.String");
/* 2536 */               int _jspx_eval_bean_005fdefine_005f4 = _jspx_th_bean_005fdefine_005f4.doStartTag();
/* 2537 */               if (_jspx_th_bean_005fdefine_005f4.doEndTag() == 5) {
/* 2538 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4); return;
/*      */               }
/*      */               
/* 2541 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f4);
/* 2542 */               String critical = null;
/* 2543 */               critical = (String)_jspx_page_context.findAttribute("critical");
/* 2544 */               out.write(10);
/*      */               
/* 2546 */               DefineTag _jspx_th_bean_005fdefine_005f5 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2547 */               _jspx_th_bean_005fdefine_005f5.setPageContext(_jspx_page_context);
/* 2548 */               _jspx_th_bean_005fdefine_005f5.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2550 */               _jspx_th_bean_005fdefine_005f5.setId("clear");
/*      */               
/* 2552 */               _jspx_th_bean_005fdefine_005f5.setName("colors");
/*      */               
/* 2554 */               _jspx_th_bean_005fdefine_005f5.setProperty("CLEAR");
/*      */               
/* 2556 */               _jspx_th_bean_005fdefine_005f5.setType("java.lang.String");
/* 2557 */               int _jspx_eval_bean_005fdefine_005f5 = _jspx_th_bean_005fdefine_005f5.doStartTag();
/* 2558 */               if (_jspx_th_bean_005fdefine_005f5.doEndTag() == 5) {
/* 2559 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5); return;
/*      */               }
/*      */               
/* 2562 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f5);
/* 2563 */               String clear = null;
/* 2564 */               clear = (String)_jspx_page_context.findAttribute("clear");
/* 2565 */               out.write(10);
/*      */               
/* 2567 */               DefineTag _jspx_th_bean_005fdefine_005f6 = (DefineTag)this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.get(DefineTag.class);
/* 2568 */               _jspx_th_bean_005fdefine_005f6.setPageContext(_jspx_page_context);
/* 2569 */               _jspx_th_bean_005fdefine_005f6.setParent(_jspx_th_tiles_005fput_005f2);
/*      */               
/* 2571 */               _jspx_th_bean_005fdefine_005f6.setId("warning");
/*      */               
/* 2573 */               _jspx_th_bean_005fdefine_005f6.setName("colors");
/*      */               
/* 2575 */               _jspx_th_bean_005fdefine_005f6.setProperty("WARNING");
/*      */               
/* 2577 */               _jspx_th_bean_005fdefine_005f6.setType("java.lang.String");
/* 2578 */               int _jspx_eval_bean_005fdefine_005f6 = _jspx_th_bean_005fdefine_005f6.doStartTag();
/* 2579 */               if (_jspx_th_bean_005fdefine_005f6.doEndTag() == 5) {
/* 2580 */                 this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6); return;
/*      */               }
/*      */               
/* 2583 */               this._005fjspx_005ftagPool_005fbean_005fdefine_0026_005ftype_005fproperty_005fname_005fid_005fnobody.reuse(_jspx_th_bean_005fdefine_005f6);
/* 2584 */               String warning = null;
/* 2585 */               warning = (String)_jspx_page_context.findAttribute("warning");
/* 2586 */               out.write(10);
/* 2587 */               out.write(10);
/*      */               
/* 2589 */               String isTabletStr = (String)request.getSession().getAttribute("isTablet");
/* 2590 */               boolean isTablet = (isTabletStr != null) && (isTabletStr.trim().equals("true"));
/*      */               
/* 2592 */               out.write(10);
/* 2593 */               out.write(10);
/* 2594 */               out.write(10);
/* 2595 */               out.write("\n\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"leftmnutables\"><tr>\n    <td width=\"80%\" class=\"leftlinksquicknote\">");
/* 2596 */               out.print(FormatUtil.getString("am.webclient.gettingstarted.quicknote.lefttabletitle"));
/* 2597 */               out.write("</td>\n    <td width=\"20%\"  align=\"right\" class=\"leftlinksheading\"><img src=\"../images/");
/* 2598 */               if (_jspx_meth_c_005fout_005f0(_jspx_th_tiles_005fput_005f2, _jspx_page_context))
/*      */                 return;
/* 2600 */               out.write("/img_quicknote.gif\" hspace=\"5\"></td>\n\n  </tr>\n  <tr>\n    <td colspan=\"2\" class=\"quicknote\"> ");
/* 2601 */               out.print(FormatUtil.getString("am.webclient.support.quicknote", new String[] { OEMUtil.getOEMString("product.name") }));
/* 2602 */               out.write("</td>\n  </tr>\n</table>\n");
/* 2603 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f2.doAfterBody();
/* 2604 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 2607 */             if (_jspx_eval_tiles_005fput_005f2 != 1) {
/* 2608 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 2611 */           if (_jspx_th_tiles_005fput_005f2.doEndTag() == 5) {
/* 2612 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2); return;
/*      */           }
/*      */           
/* 2615 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f2);
/* 2616 */           out.write(" \n\n\n\n\n\n\n");
/*      */           
/* 2618 */           PutTag _jspx_th_tiles_005fput_005f3 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.get(PutTag.class);
/* 2619 */           _jspx_th_tiles_005fput_005f3.setPageContext(_jspx_page_context);
/* 2620 */           _jspx_th_tiles_005fput_005f3.setParent(_jspx_th_tiles_005finsert_005f0);
/*      */           
/* 2622 */           _jspx_th_tiles_005fput_005f3.setName("UserArea");
/*      */           
/* 2624 */           _jspx_th_tiles_005fput_005f3.setType("string");
/* 2625 */           int _jspx_eval_tiles_005fput_005f3 = _jspx_th_tiles_005fput_005f3.doStartTag();
/* 2626 */           if (_jspx_eval_tiles_005fput_005f3 != 0) {
/* 2627 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 2628 */               out = _jspx_page_context.pushBody();
/* 2629 */               _jspx_th_tiles_005fput_005f3.setBodyContent((BodyContent)out);
/* 2630 */               _jspx_th_tiles_005fput_005f3.doInitBody();
/*      */             }
/*      */             for (;;) {
/* 2633 */               out.write("\n\n\n\n\n\n\n\n\n\n<table cellpadding=\"0\" cellspacing=\"0\" align=\"center\" width=\"100%\">\n<tr>\n<td class=\"vcenter-shadow-tp-lcorn\" width=\"8\"><img src=\"/images/spacer.gif\" width=\"7\" height=\"9\" /></td>\n<td class=\"vcenter-shadow-tp-mtile\"></td>\n<td class=\"vcenter-shadow-tp-rcorn\" width=\"7\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n</tr>\n<tr>\n<td class=\"vcenter-shadow-lfttile\" ><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n\n\n<!-- Inner tabel starts!-->\n<td width=\"100%\" valign=\"top\">\n\n\n\n\n\n\n\n\n<table width='100%' border=\"0\" cellpadding=\"0\" cellspacing=\"0\" align='center' style=\"padding:0px 0px 0px 0px;\">\n<tr><td>\n\n\n");
/*      */               
/* 2635 */               ArrayList buildinfo = (ArrayList)request.getAttribute("buildinfo");
/* 2636 */               ArrayList serverdetails = (ArrayList)request.getAttribute("serverdetails");
/* 2637 */               ArrayList sysinfo = (ArrayList)request.getAttribute("sysinfo");
/* 2638 */               ArrayList heapinfo = (ArrayList)request.getAttribute("heapinfo");
/*      */               
/* 2640 */               out.write("\n<!--\n<table width=\"563\" cellpadding=\"0\" cellspacing=\"0\">\n  <tr>\n    <td width=\"226\"><div id='application' style='display: inline;'> <a href=\"#\" onclick=\"javascript:showServerDiagView();\" class=\"bcactive\"> Applications Manager Diagnostics </a></div><div id='application1' style='display: none;'><a href=\"#\" onclick=\"javascript:showServerDiagView(),hideDiv('support1$application1'),showDiv('support$application');\" class=\"bodytext\"> Applications Manager Diagnostics </div> </a></td>\n    <td width=\"14\" class=\"bodytext\">|</td>\n    <td width=\"321\"><div id='support' style='display: inline;'> <a href=\"#\" onclick=\"javascript:showProductInfoView(),hideDiv('application$support'),showDiv('application1$support1');\" class=\"bodytext\">Product Support</a> </div> <div id='support1' style='display: none;'> <a href=\"#\" onclick=\"javascript:showProductInfoView();\" class=\"bcactive\">Product Support</a> </div></td>\n  </tr>\n</table>\n\n\n<div id='SERVER_DIAG'  style='display: inline;'> -->\n<div id=\"showmessage\" style=\"DISPLAY: none\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"messagebox\" >\n");
/* 2641 */               out.write("    <tr>\n      <td class=\"message\"><img src=\"/images/icon_message_success.gif\" hspace=\"6\" align=\"left\">\n        ");
/* 2642 */               out.print(FormatUtil.getString("am.webclient.support.supportfile.message"));
/* 2643 */               out.write(" </td>\n    </tr>\n  </table>\n  <br>\n</div>\n");
/*      */               
/* 2645 */               StringBuffer pathinfo = (StringBuffer)request.getAttribute("pathinfo");
/* 2646 */               String querystring = (String)request.getAttribute("querystring");
/* 2647 */               if (showdiv)
/*      */               {
/*      */ 
/* 2650 */                 out.write("\n<script>\n  \t\t function myOnLoad()\n  \t\t {\n  \t\t\tdocument.mainform.mailMsg.focus();\n  \t\t }\n  \t\tfunction showProxyMessage()\n\t\t{\n            alert(\"");
/* 2651 */                 out.print(FormatUtil.getString("am.webclient.support.proxymessage"));
/* 2652 */                 out.write("\");\n\t\t\tdocument.mainform.supporttype[1].checked='false';\n\t\t\tdocument.mainform.supporttype[0].checked='true';\n\t\t}\n\t\tfunction validateform()\n\t\t{\n\t\t\t\n\t\t\tif(document.mainform.supporttype[1].checked)\n\t\t\t{\n\t\t\t\tif(trimAll(document.mainform.customermail.value) == '')\n\t\t\t\t {\n\t\t\t\n\t\t\t\t\talert(\"Please enter your EMail id.\");\n\t\t\t\t\tdocument.mainform.customermail.select();\n\t\t\t\t\treturn false;\n\t\t\t\t}\n\t\t\t}\n\t\t\tdocument.mainform.submit();\n\t\t}\n\t\tfunction submitmainform()\n\t\t{\n\n\t\t\tdocument.mainform.submit();\n\n\t\t}\n  </script>\n<form action=\"/showresource.do\" method=\"POST\" name=\"mainform\" style=\"display:inline\" onSubmit=\"return javascript:validateform();\">\n  <input type=\"hidden\" name=\"method\" value=\"sendSupport1\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n    <tr>\n      <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\"> <img src=\"/images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"8\"></td>\n      <td width=\"95%\" class=\"bodytext\"  > ");
/*      */                 
/* 2654 */                 IfTag _jspx_th_c_005fif_005f0 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2655 */                 _jspx_th_c_005fif_005f0.setPageContext(_jspx_page_context);
/* 2656 */                 _jspx_th_c_005fif_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2658 */                 _jspx_th_c_005fif_005f0.setTest("${!empty exception}");
/* 2659 */                 int _jspx_eval_c_005fif_005f0 = _jspx_th_c_005fif_005f0.doStartTag();
/* 2660 */                 if (_jspx_eval_c_005fif_005f0 != 0) {
/*      */                   for (;;) {
/* 2662 */                     out.write(32);
/* 2663 */                     out.print(FormatUtil.getString("am.webclient.support.createsupportfile.message.text"));
/* 2664 */                     out.write(32);
/* 2665 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f0.doAfterBody();
/* 2666 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2670 */                 if (_jspx_th_c_005fif_005f0.doEndTag() == 5) {
/* 2671 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0); return;
/*      */                 }
/*      */                 
/* 2674 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f0);
/* 2675 */                 out.write(" <br> \n      \t");
/*      */                 
/* 2677 */                 IfTag _jspx_th_c_005fif_005f1 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2678 */                 _jspx_th_c_005fif_005f1.setPageContext(_jspx_page_context);
/* 2679 */                 _jspx_th_c_005fif_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2681 */                 _jspx_th_c_005fif_005f1.setTest("${licenseinfo.userType=='F'}");
/* 2682 */                 int _jspx_eval_c_005fif_005f1 = _jspx_th_c_005fif_005f1.doStartTag();
/* 2683 */                 if (_jspx_eval_c_005fif_005f1 != 0) {
/*      */                   for (;;) {
/* 2685 */                     out.print(FormatUtil.getString("am.webclient.support.createsupportfile.freeedition.msg"));
/* 2686 */                     out.write("\n        ");
/* 2687 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f1.doAfterBody();
/* 2688 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2692 */                 if (_jspx_th_c_005fif_005f1.doEndTag() == 5) {
/* 2693 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1); return;
/*      */                 }
/*      */                 
/* 2696 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f1);
/* 2697 */                 out.write(" \n        ");
/*      */                 
/* 2699 */                 IfTag _jspx_th_c_005fif_005f2 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2700 */                 _jspx_th_c_005fif_005f2.setPageContext(_jspx_page_context);
/* 2701 */                 _jspx_th_c_005fif_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2703 */                 _jspx_th_c_005fif_005f2.setTest("${licenseinfo.userType=='T'}");
/* 2704 */                 int _jspx_eval_c_005fif_005f2 = _jspx_th_c_005fif_005f2.doStartTag();
/* 2705 */                 if (_jspx_eval_c_005fif_005f2 != 0) {
/*      */                   for (;;) {
/* 2707 */                     out.write(32);
/* 2708 */                     out.print(FormatUtil.getString("am.webclient.support.createsupportfile.message"));
/* 2709 */                     out.write("\n        ");
/* 2710 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f2.doAfterBody();
/* 2711 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2715 */                 if (_jspx_th_c_005fif_005f2.doEndTag() == 5) {
/* 2716 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2); return;
/*      */                 }
/*      */                 
/* 2719 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f2);
/* 2720 */                 out.write("\n        ");
/*      */                 
/* 2722 */                 IfTag _jspx_th_c_005fif_005f3 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2723 */                 _jspx_th_c_005fif_005f3.setPageContext(_jspx_page_context);
/* 2724 */                 _jspx_th_c_005fif_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 2726 */                 _jspx_th_c_005fif_005f3.setTest("${licenseinfo.userType=='R'}");
/* 2727 */                 int _jspx_eval_c_005fif_005f3 = _jspx_th_c_005fif_005f3.doStartTag();
/* 2728 */                 if (_jspx_eval_c_005fif_005f3 != 0) {
/*      */                   for (;;) {
/* 2730 */                     out.write(32);
/* 2731 */                     out.print(FormatUtil.getString("am.webclient.support.createsupportfile.message.registered"));
/* 2732 */                     out.write(" \n        ");
/* 2733 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f3.doAfterBody();
/* 2734 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 2738 */                 if (_jspx_th_c_005fif_005f3.doEndTag() == 5) {
/* 2739 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3); return;
/*      */                 }
/*      */                 
/* 2742 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f3);
/* 2743 */                 out.write("\n        <a href=\"mailto:");
/* 2744 */                 out.print(UserUtil.getSupportMailID());
/* 2745 */                 out.write("\" class=\"bodytextbold\">");
/* 2746 */                 out.print(UserUtil.getSupportMailID());
/* 2747 */                 out.write("</a> </td>\n    </tr>\n  </table>\n  <br>\n\n  <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n    <tr>\n      <td class=\"tableheadingbborder\" colspan=\"2\"> ");
/* 2748 */                 out.print(FormatUtil.getString("am.webclient.support.createsupportfile.heading"));
/* 2749 */                 out.write(" </tr>\n    <tr>\n\t");
/*      */                 
/* 2751 */                 if (!OEMUtil.isOEM())
/*      */                 {
/*      */ 
/* 2754 */                   out.write("\n      <td class=\"rayborderbr\" align=\"center\" colspan=\"2\"> <textarea rows=10 cols=80 name=\"mailMsg\">\n\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 2760 */                   out.write("\n\t\t<td class=\"bodytext\" align=\"center\" colspan=\"2\">\n\t  ");
/*      */                 }
/*      */                 
/* 2763 */                 if (showsupport != null)
/*      */                 {
/*      */ 
/* 2766 */                   out.write("\n\t\t               ");
/* 2767 */                   out.print(FormatUtil.getString("am.webclient.supporttab.entermessage.text"));
/* 2768 */                   out.write("\n\n\n\n\n\t\t  \n\t\n");
/* 2769 */                   out.print(FormatUtil.getString("am.webclient.supporttab.sendmessage.text"));
/* 2770 */                   out.write("&nbsp;");
/* 2771 */                   out.print(UserUtil.getSupportMailID());
/* 2772 */                   out.write("\n\t   ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/* 2777 */                   out.write("\n\t\t\t");
/* 2778 */                   out.print(FormatUtil.getString("am.webclient.supporttab.erroroccured.text"));
/* 2779 */                   out.write(10);
/* 2780 */                   out.write(10);
/* 2781 */                   out.print(FormatUtil.getString("am.webclient.supporttab.reportbug.text"));
/* 2782 */                   out.write("\n\n\n\n\n\n\n1. ");
/* 2783 */                   out.print(FormatUtil.getString("am.webclient.supporttab.bugstep1.text"));
/* 2784 */                   out.write("\n2. ");
/* 2785 */                   out.print(FormatUtil.getString("am.webclient.supporttab.bugstep2.text"));
/* 2786 */                   out.write(32);
/* 2787 */                   out.print(UserUtil.getSupportMailID());
/* 2788 */                   out.write(10);
/*      */                 }
/* 2790 */                 out.write(10);
/* 2791 */                 out.write(10);
/* 2792 */                 out.print(FormatUtil.getString("am.webclient.supporttab.bugmessage1.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 2793 */                 out.write(10);
/* 2794 */                 out.write(10);
/* 2795 */                 out.print(FormatUtil.getString("am.webclient.supporttab.bugmessage2.text"));
/* 2796 */                 out.write(10);
/* 2797 */                 out.write(9);
/*      */                 
/* 2799 */                 if (showsupport == null)
/*      */                 {
/*      */ 
/* 2802 */                   out.write(10);
/* 2803 */                   out.write(9);
/* 2804 */                   out.print(FormatUtil.getString("am.webclient.supporttab.bugurl.text"));
/* 2805 */                   out.write(32);
/* 2806 */                   out.print(pathinfo);
/* 2807 */                   out.write(38);
/* 2808 */                   out.print(querystring);
/* 2809 */                   out.write(10);
/* 2810 */                   out.write(9);
/*      */                 }
/* 2812 */                 out.write("\n</textarea>\n      </td>\n    </tr>\n    <tr>\n    ");
/*      */                 
/* 2814 */                 if (!OEMUtil.isOEM())
/*      */                 {
/*      */ 
/* 2817 */                   out.write("\n   <tr>\n      <td width=\"35%\" class=\"bodytext\" align=\"right\" ><a href=\"/jsp/threaddump.jsp\"  target=\"none\" class=\"bodytextboldunderline\" title=\"");
/* 2818 */                   out.print(FormatUtil.getString("am.webclient.support.threaddump.text"));
/* 2819 */                   out.write(34);
/* 2820 */                   out.write(62);
/* 2821 */                   out.print(FormatUtil.getString("am.webclient.support.threaddump.link"));
/* 2822 */                   out.write("</a> </td>\n\t    </tr>\n      <td class=\"bodytext\" align=\"center\" colspan=\"2\" > ");
/*      */                   
/* 2824 */                   IfTag _jspx_th_c_005fif_005f4 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2825 */                   _jspx_th_c_005fif_005f4.setPageContext(_jspx_page_context);
/* 2826 */                   _jspx_th_c_005fif_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2828 */                   _jspx_th_c_005fif_005f4.setTest("${!empty proxy }");
/* 2829 */                   int _jspx_eval_c_005fif_005f4 = _jspx_th_c_005fif_005f4.doStartTag();
/* 2830 */                   if (_jspx_eval_c_005fif_005f4 != 0) {
/*      */                     for (;;) {
/* 2832 */                       out.write("\n        <input type=radio size=40 name=\"supporttype\" value=\"save\" checked />\n        ");
/* 2833 */                       out.print(FormatUtil.getString("am.webclient.support.createsupportinformationfile.checkbox"));
/* 2834 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f4.doAfterBody();
/* 2835 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2839 */                   if (_jspx_th_c_005fif_005f4.doEndTag() == 5) {
/* 2840 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4); return;
/*      */                   }
/*      */                   
/* 2843 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f4);
/* 2844 */                   out.write(32);
/*      */                   
/* 2846 */                   IfTag _jspx_th_c_005fif_005f5 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2847 */                   _jspx_th_c_005fif_005f5.setPageContext(_jspx_page_context);
/* 2848 */                   _jspx_th_c_005fif_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2850 */                   _jspx_th_c_005fif_005f5.setTest("${empty proxy }");
/* 2851 */                   int _jspx_eval_c_005fif_005f5 = _jspx_th_c_005fif_005f5.doStartTag();
/* 2852 */                   if (_jspx_eval_c_005fif_005f5 != 0) {
/*      */                     for (;;) {
/* 2854 */                       out.write("\n        <input type=radio size=40 name=\"supporttype\" value=\"save\" />\n        ");
/* 2855 */                       out.print(FormatUtil.getString("am.webclient.support.createsupportinformationfile.checkbox"));
/* 2856 */                       out.write(32);
/* 2857 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f5.doAfterBody();
/* 2858 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2862 */                   if (_jspx_th_c_005fif_005f5.doEndTag() == 5) {
/* 2863 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5); return;
/*      */                   }
/*      */                   
/* 2866 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f5);
/* 2867 */                   out.write(32);
/*      */                   
/* 2869 */                   IfTag _jspx_th_c_005fif_005f6 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2870 */                   _jspx_th_c_005fif_005f6.setPageContext(_jspx_page_context);
/* 2871 */                   _jspx_th_c_005fif_005f6.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2873 */                   _jspx_th_c_005fif_005f6.setTest("${empty proxy }");
/* 2874 */                   int _jspx_eval_c_005fif_005f6 = _jspx_th_c_005fif_005f6.doStartTag();
/* 2875 */                   if (_jspx_eval_c_005fif_005f6 != 0) {
/*      */                     for (;;) {
/* 2877 */                       out.write("\n        <input type=radio size=40 name=\"supporttype\" value=\"direct\" checked/>\n        ");
/* 2878 */                       out.print(FormatUtil.getString("am.webclient.support.upload.checkbox", new String[] { OEMUtil.getOEMString("company.name") }));
/* 2879 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f6.doAfterBody();
/* 2880 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2884 */                   if (_jspx_th_c_005fif_005f6.doEndTag() == 5) {
/* 2885 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6); return;
/*      */                   }
/*      */                   
/* 2888 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f6);
/* 2889 */                   out.write(32);
/*      */                   
/* 2891 */                   IfTag _jspx_th_c_005fif_005f7 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 2892 */                   _jspx_th_c_005fif_005f7.setPageContext(_jspx_page_context);
/* 2893 */                   _jspx_th_c_005fif_005f7.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 2895 */                   _jspx_th_c_005fif_005f7.setTest("${!empty proxy }");
/* 2896 */                   int _jspx_eval_c_005fif_005f7 = _jspx_th_c_005fif_005f7.doStartTag();
/* 2897 */                   if (_jspx_eval_c_005fif_005f7 != 0) {
/*      */                     for (;;) {
/* 2899 */                       out.write("\n        <input type=radio size=40 name=\"supporttype\" value=\"direct\" onClick=\"javascript:showProxyMessage()\"/>\n        ");
/* 2900 */                       out.print(FormatUtil.getString("am.webclient.support.upload.checkbox", new String[] { OEMUtil.getOEMString("company.name") }));
/* 2901 */                       out.write(32);
/* 2902 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f7.doAfterBody();
/* 2903 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 2907 */                   if (_jspx_th_c_005fif_005f7.doEndTag() == 5) {
/* 2908 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7); return;
/*      */                   }
/*      */                   
/* 2911 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f7);
/* 2912 */                   out.write(" </td>\n    </tr>\n    <tr>\n      <td class=\"bodytext\" width=\"30%\" align=\"right\" >");
/* 2913 */                   out.print(FormatUtil.getString("am.webclient.support.createsupportfile.name"));
/* 2914 */                   out.write("</td>\n      <td class=\"bodytext\" width=\"70%\" > <input type=text size=40 name=\"customername\" value=\"\" class=\"formtext\" />\n      </td>\n    </tr>\n    <tr>\n      <td class=\"bodytext\" width=\"30%\" align=\"right\" >");
/* 2915 */                   out.print(FormatUtil.getString("am.webclient.support.createsupportfile.name.mailid"));
/* 2916 */                   out.write("<span class=\"mandatory\">*</span>\n      </td>\n      <td class=\"bodytext\" width=\"70%\" > <input type=text size=40 name=\"customermail\" value=\"\" class=\"formtext\" />\n      </td>\n    </tr>\n    <tr>\n      <td class=\"bodytext\" width=\"30%\" align=\"right\" >");
/* 2917 */                   out.print(FormatUtil.getString("am.webclient.support.createsupportfile.contactno"));
/* 2918 */                   out.write("</td>\n      <td class=\"bodytext\" width=\"70%\" > <input type=text size=40 name=\"customerno\" value=\"\" class=\"formtext\" />\n      </td>\n    </tr>\n\t\t");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 2924 */                   out.write("\n\t\t<input type=\"hidden\" name=\"supporttype\" value=\"save\">\n\t\t");
/*      */                 }
/*      */                 
/*      */ 
/* 2928 */                 out.write("\n    <tr>\n      <td class=\"rayborderbr\" width=\"30%\" align=\"right\"> &nbsp;&nbsp; </td>\n      <td class=\"rayborderbr\" width=\"70%\" align=\"left\">\n      ");
/*      */                 
/* 2930 */                 if (!OEMUtil.isOEM())
/*      */                 {
/*      */ 
/* 2933 */                   out.write("\n\t<input type=\"button\" onClick=\"javascript:validateform()\" name=\"sendsupport\" value=\"");
/* 2934 */                   out.print(FormatUtil.getString("am.webclient.support.createsupportfile.button"));
/* 2935 */                   out.write("\" class=\"buttons btn_highlt\">\n      ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 2941 */                   out.write("\n\t<input type=\"button\" onClick=\"javascript:submitmainform()\" name=\"sendsupport\" value=\"");
/* 2942 */                   out.print(FormatUtil.getString("am.webclient.support.createsupportfile.button"));
/* 2943 */                   out.write("\" class=\"buttons btn_highlt\">\n\t");
/*      */                 }
/* 2945 */                 out.write("\n        &nbsp;&nbsp; <input type=\"button\" onClick=\"javascript:history.back();\" name=\"cancel\" value=\"");
/* 2946 */                 out.print(FormatUtil.getString("am.webclient.common.cancel.text"));
/* 2947 */                 out.write("\" class=\"buttons btn_link\">\n      </td>\n    </tr>\n  </table>\n\n  <br>\n</form>\n");
/*      */               }
/* 2949 */               out.write(10);
/* 2950 */               out.write(10);
/* 2951 */               long time = System.currentTimeMillis();
/*      */               
/* 2953 */               String server = "local";
/*      */               
/*      */               try
/*      */               {
/* 2957 */                 server = InetAddress.getLocalHost().getHostAddress();
/* 2958 */                 server = com.adventnet.appmanager.util.SupportZipUtil.getAddrLong(server) + "";
/* 2959 */                 int length = server.length();
/* 2960 */                 server = server.substring(length - 4, length);
/*      */               }
/*      */               catch (Exception ee)
/*      */               {
/* 2964 */                 ee.printStackTrace();
/*      */               }
/*      */               
/*      */ 
/* 2968 */               long endtime = 1146335464196L;
/* 2969 */               long apps = 0L;
/* 2970 */               long dbs = 0L;
/* 2971 */               long sys = 0L;
/* 2972 */               long total = 0L;
/* 2973 */               ArrayList lists = mo.getRows("select AM_ManagedResourceType.RESOURCEGROUP , count(*) from AM_ManagedObject , AM_ManagedResourceType where AM_ManagedResourceType.RESOURCETYPE=AM_ManagedObject.TYPE  and AM_ManagedResourceType.RESOURCEGROUP in ('DBS','APP','SYS') group by AM_ManagedResourceType.RESOURCEGROUP");
/* 2974 */               for (int k = 0; k < lists.size(); k++)
/*      */               {
/* 2976 */                 ArrayList list = (ArrayList)lists.get(k);
/* 2977 */                 String type = (String)list.get(0);
/* 2978 */                 if (type.equals("APP"))
/*      */                 {
/* 2980 */                   apps += Long.parseLong((String)list.get(1));
/*      */                 }
/* 2982 */                 if (type.equals("DBS"))
/*      */                 {
/* 2984 */                   dbs += Long.parseLong((String)list.get(1));
/*      */                 }
/* 2986 */                 if (type.equals("SYS"))
/*      */                 {
/* 2988 */                   sys += Long.parseLong((String)list.get(1));
/*      */                 }
/*      */               }
/* 2991 */               total = DBUtil.getNumberOfMonitors();
/* 2992 */               String url = "&as=" + apps + "&ds=" + dbs + "&ss=" + sys + "&tl=" + total + "&si=" + server + "&pi=1";
/* 2993 */               String AM_HOME = ".." + File.separator;
/* 2994 */               long installtime = 0L;
/* 2995 */               File f = new File(AM_HOME + File.separator + "working" + File.separator + "classes" + File.separator + "ManagementServer.jar");
/* 2996 */               if (f.exists())
/*      */               {
/* 2998 */                 installtime = new Date(f.lastModified()).getTime();
/*      */               }
/*      */               else
/*      */               {
/* 3002 */                 System.out.println("File does not exists");
/*      */               }
/* 3004 */               long serverrunningtime = time - installtime;
/* 3005 */               long fortyfivedays = 3888000000L;
/*      */               
/* 3007 */               if ((time <= endtime) && (installtime != 0L) && (serverrunningtime >= fortyfivedays))
/*      */               {
/*      */ 
/*      */ 
/* 3011 */                 out.write(10);
/*      */                 
/* 3013 */                 IfTag _jspx_th_c_005fif_005f8 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3014 */                 _jspx_th_c_005fif_005f8.setPageContext(_jspx_page_context);
/* 3015 */                 _jspx_th_c_005fif_005f8.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3017 */                 _jspx_th_c_005fif_005f8.setTest("${applicationScope.globalconfig.freeuser == 'true'}");
/* 3018 */                 int _jspx_eval_c_005fif_005f8 = _jspx_th_c_005fif_005f8.doStartTag();
/* 3019 */                 if (_jspx_eval_c_005fif_005f8 != 0) {
/*      */                   for (;;) {
/* 3021 */                     out.write(32);
/*      */                     
/* 3023 */                     IfTag _jspx_th_c_005fif_005f9 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3024 */                     _jspx_th_c_005fif_005f9.setPageContext(_jspx_page_context);
/* 3025 */                     _jspx_th_c_005fif_005f9.setParent(_jspx_th_c_005fif_005f8);
/*      */                     
/* 3027 */                     _jspx_th_c_005fif_005f9.setTest("${licenseinfo.userType=='F'}");
/* 3028 */                     int _jspx_eval_c_005fif_005f9 = _jspx_th_c_005fif_005f9.doStartTag();
/* 3029 */                     if (_jspx_eval_c_005fif_005f9 != 0) {
/*      */                       for (;;) {
/* 3031 */                         out.write("\n<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"messagebox\">\n  <tr>\n    <td width=\"5%\" align=\"center\" valign=\"top\" class=\"bodytext\"> <img src=\"../images/icon_message_success.gif\" width=\"25\" height=\"25\" vspace=\"8\"></td>\n    <td width=\"90%\" class=\"bodytext\"  align=\"left\" valign=\"top\"> <br>\n      ");
/* 3032 */                         out.print(FormatUtil.getString("am.webclient.support.freeuser.register.message1"));
/* 3033 */                         out.write("\n      </td>\n    <td><br>\n      <a href='javascript:fnRemove()' ><img src=\"/images/icon_removefromgroup.gif\"  border=\"0\" hspace=\"5\" align=\"right\"  title='Never show this again'></a></td>\n  </tr>\n</table>\n<br>\n");
/* 3034 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f9.doAfterBody();
/* 3035 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 3039 */                     if (_jspx_th_c_005fif_005f9.doEndTag() == 5) {
/* 3040 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9); return;
/*      */                     }
/*      */                     
/* 3043 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f9);
/* 3044 */                     out.write(32);
/* 3045 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f8.doAfterBody();
/* 3046 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3050 */                 if (_jspx_th_c_005fif_005f8.doEndTag() == 5) {
/* 3051 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8); return;
/*      */                 }
/*      */                 
/* 3054 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f8);
/* 3055 */                 out.write(10);
/*      */               }
/*      */               
/*      */ 
/*      */ 
/* 3060 */               out.write("\n\n              ");
/* 3061 */               if (!OEMUtil.isRemove("am.supporttab.remove")) {
/* 3062 */                 out.write("\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"reports-head-tile\">\n  <tr>\n    <td colspan=\"4\" class=\"tableheadingbborder\" style=\"height:50px;\"> <img src=\"/images/icon_customfields_user.png\" style=\"position:relative; top:5px; left:8px; \"   border=\"0\"> <b style=\"position:relative; bottom:7px; left:11px; font-size:15px;\">");
/* 3063 */                 out.print(FormatUtil.getString("am.webclient.support.heading", new String[] { OEMUtil.getOEMString("product.name") }));
/* 3064 */                 out.write("</b></td>\n  </tr>\n  <tr height=\"70\" >\n\t");
/* 3065 */                 if (!OEMUtil.isRemove("am.supporttab.blognforums.remove")) {
/* 3066 */                   out.write("\n    <td width=\"34%\">\n      <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"10%\" align=\"center\" valign=\"middle\" style=\"padding-left:10px;\"><a href=\"https://www.manageengine.com/products/applications_manager/request-support.html\" target=\"_blank\"><img src=\"../images/support_supportreq.gif\" width=\"28\" height=\"32\" border=\"0\"></a></td>\n          <td width=\"90%\" style=\"padding-left:10px;\"><span class=\"bodytextbold\"><a href=\"https://www.manageengine.com/products/applications_manager/request-support.html\" target=\"_blank\"  class=\"bodytextboldunderline\" >");
/* 3067 */                   out.print(FormatUtil.getString("am.webclient.support.reqtechsupport"));
/* 3068 */                   out.write("</td>\n        </tr>\n      </table></td>\n    <td width=\"1%\">&nbsp;</td>\n\t\t ");
/*      */                 }
/* 3070 */                 out.write("\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"15%\" align=\"center\" valign=\"top\"><a href=\"https://www.manageengine.com/products/applications_manager/demo.html\" target=\"_blank\"><img src=\"../images/support_howtodemo.gif\" width=\"32\" height=\"31\" vspace=\"10\" border=\"0\" ></a></td>\n          <td width=\"85%\"><span class=\"bodytextbold\"><a href=\"https://www.manageengine.com/products/applications_manager/demo.html\" target=\"_blank\" class=\"bodytextboldunderline\">");
/* 3071 */                 out.print(FormatUtil.getString("am.webclient.support.howtodemos"));
/* 3072 */                 out.write("</a></span><br>\n\n            <span class=\"bodytext\">");
/* 3073 */                 out.print(FormatUtil.getString("am.webclient.support.howtodemos.text", new String[] { OEMUtil.getOEMString("product.name") }));
/* 3074 */                 out.write("\n            </span></td>\n        </tr>\n      </table></td>\n\t");
/* 3075 */                 if (!OEMUtil.isRemove("am.supporttab.blognforums.remove")) {
/* 3076 */                   out.write("\n    <td><table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">\n        <tr>\n          <td width=\"10%\" align=\"center\" valign=\"middle\"><img src=\"../images/support_testimonial.gif\" width=\"32\" height=\"27\" vspace=\"10\"></td>\n          <td width=\"90%\" valign=\"top\"><span class=\"bodytextbold\"><a href=\"https://www.manageengine.com/products/applications_manager/customer-quotes.html\"  target=\"_blank\" class=\"bodytextboldunderline\"><br>");
/* 3077 */                   out.print(FormatUtil.getString("am.webclient.support.testimonials"));
/* 3078 */                   out.write("</a></span><br>\n                ");
/* 3079 */                   out.print(FormatUtil.getString("am.webclient.support.testimonials.message", new String[] { OEMUtil.getOEMString("product.name") }));
/* 3080 */                   out.write("</td>\n        </tr>\n      </table></td>\n  </tr>\n  <tr>\n\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n  </tr>\n  <tr height=\"70\">\n\t ");
/*      */                 }
/* 3082 */                 out.write("\n\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"10%\" align=\"center\" valign=\"middle\" style=\"padding-left:10px;\"><img src=\"../images/telephone_go.png\" width=\"32\" height=\"32\" border=\"0\"></td>\n\t  <td width=\"90%\" valign=\"top\" style=\"padding-left:10px;\"><span class=\"bodytextbold\">");
/* 3083 */                 out.print(FormatUtil.getString("Email"));
/* 3084 */                 out.write(" : </span><span class=\"bodytext\"><a class=\"ResourceName\" href=\"mailto:");
/* 3085 */                 out.print(UserUtil.getSupportMailID());
/* 3086 */                 out.write(34);
/* 3087 */                 out.write(62);
/* 3088 */                 out.print(UserUtil.getSupportMailID());
/* 3089 */                 out.write("</a></span>  <span class=\"bodytextbold\" style=\"line-height:20px;\"><br>");
/* 3090 */                 out.print(FormatUtil.getString("am.webclient.support.tollnumber.heading"));
/* 3091 */                 out.write(" : </span>\n        <span class=\"bodytext\">");
/* 3092 */                 out.print(OEMUtil.getOEMString("product.tollfree.number"));
/* 3093 */                 out.write("</span></td>\n        </tr>\n      </table></td>\n\t\t");
/* 3094 */                 if (!OEMUtil.isRemove("am.supporttab.blognforums.remove")) {
/* 3095 */                   out.write("\n    <td>&nbsp;</td>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">\n        <tr>\n          <td width=\"15%\"  align=\"center\" valign=\"middle\"><a href=\"");
/* 3096 */                   out.print(FormatUtil.getString("am.product.sp.upgrade.link"));
/* 3097 */                   out.write("\" target=\"_blank\"><img src=\"../images/support_download.gif\" width=\"30\" vspace=\"15\" height=\"31\" border=\"0\"></a></td>\n          <td width=\"85%\" valign=\"top\"><span class=\"bodytextbold\"><a href=\"");
/* 3098 */                   out.print(FormatUtil.getString("am.product.sp.upgrade.link"));
/* 3099 */                   out.write("\" target=\"_blank\" class=\"bodytextboldunderline\"><br>");
/* 3100 */                   out.print(FormatUtil.getString("am.webclient.support.DownloadServicePack"));
/* 3101 */                   out.write("</a> </span><br> <span class=\"bodytext\">");
/* 3102 */                   out.print(FormatUtil.getString("am.webclient.support.DownloadServicePack.message"));
/* 3103 */                   out.write("</span></td>\n        </tr>\n      </table></td>\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\">\n        <tr>\n          <td width=\"10%\"  align=\"center\" valign=\"middle\"><a href=\"https://www.manageengine.com/products/applications_manager/need-features.html\" target=\"_blank\"><img src=\"../images/support_requestfeature.gif\" width=\"23\" height=\"28\" vspace=\"15\" border=\"0\"></a></td>\n          <td width=\"90%\" valign=\"top\"><span class=\"bodytextbold\"><a href=\"https://www.manageengine.com/products/applications_manager/need-features.html\" target=\"_blank\" class=\"bodytextboldunderline\"><br>");
/* 3104 */                   out.print(FormatUtil.getString("am.webclient.support.features"));
/* 3105 */                   out.write("</a> </span><br> <span class=\"bodytext\">");
/* 3106 */                   out.print(FormatUtil.getString("am.webclient.support.features.message"));
/* 3107 */                   out.write("</span></td>\n        </tr>\n      </table></td>\n\t  ");
/*      */                 }
/* 3109 */                 out.write("\n  </tr>\n  ");
/* 3110 */                 if (!OEMUtil.isRemove("am.supporttab.blognforums.remove")) {
/* 3111 */                   out.write("\n  <tr>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n    <td style=\"border-bottom:1px solid #f5f5f5\"><img src=\"../images/spacer.gif\" width=\"1\" height=\"7\"></td>\n  </tr>\n  <tr height=\"80\">\n    <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <form action=\"/adminAction.do\" method=\"POST\" name=\"form2\">\n          <tr>\n            <td width=\"10%\"  align=\"center\" valign=\"middle\" style=\"padding-left:10px;\"><a ");
/* 3112 */                   if (_jspx_meth_logic_005fpresent_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 3114 */                   out.write("><img src=\"../images/support_senderror.gif\" width=\"23\" height=\"25\" border=\"0\"></a></td>\n            <td width=\"90%\" style=\"padding-left:10px;\"><span class=\"bodytextbold\"><a ");
/* 3115 */                   if (_jspx_meth_logic_005fpresent_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 3117 */                   out.write(" class=\"bodytextboldunderline\">");
/* 3118 */                   out.print(FormatUtil.getString("am.webclient.support.supportfile.heading"));
/* 3119 */                   out.write(" </a></span><br> <span class=\"bodytext\"> ");
/* 3120 */                   out.print(FormatUtil.getString("am.webclient.support.supportfile"));
/* 3121 */                   out.write("</span></td>\n          </tr>\n        </form>\n      </table></td>\n\t  <td></td>\n\t    <td width=\"34%\" > <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"15%\"  align=\"center\" valign=\"middle\"><a href=\"https://apm.manageengine.com/index.html\" target=\"_blank\"><img src=\"../images/support_kbase.gif\" width=\"28\" height=\"30\" border=\"0\" ></a></td>\n          <td width=\"85%\"><span class=\"bodytextbold\"><a href=\"https://apm.manageengine.com/index.html\" target=\"_blank\" class=\"bodytextboldunderline\">");
/* 3122 */                   out.print(FormatUtil.getString("am.webclient.support.troubleshooting"));
/* 3123 */                   out.write("</a></span><br><span class=\"bodytext\">");
/* 3124 */                   out.print(FormatUtil.getString("am.webclient.support.troubleshooting.tip"));
/* 3125 */                   out.write(" </span></td>\n        </tr>\n      </table></td>\n\t  <td><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"10%\" align=\"center\" valign=\"middle\"><a href=\"https://forums.manageengine.com/appmanager/type/ideas\" target=\"_blank\"><img src=\"../images/support_roadmap.gif\" width=\"24\" height=\"30\" vspace=\"10\" border=\"0\" ></a></td>\n          <td width=\"90%\"><span class=\"bodytextbold\"><a href=\"https://forums.manageengine.com/appmanager/type/ideas\" target=\"_blank\" class=\"bodytextboldunderline\">");
/* 3126 */                   out.print(FormatUtil.getString("am.webclient.support.ProductRoadmap.text"));
/* 3127 */                   out.write("</a></span>\n            <br>\n            <span class=\"bodytext\">");
/* 3128 */                   out.print(FormatUtil.getString("am.webclient.support.ProductRoadmap.message"));
/* 3129 */                   out.write("\n            </span></td>\n        </tr>\n      </table></td>\n  </tr>\n   ");
/*      */                 }
/* 3131 */                 out.write("\n</table>\n  ");
/*      */               }
/* 3133 */               out.write("\n<table width=\"98%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n");
/* 3134 */               if ((!OEMUtil.isRemove("am.supporttab.remove")) && 
/* 3135 */                 (!OEMUtil.isRemove("am.supporttab.blognforums.remove"))) {
/* 3136 */                 out.write("\n  <tr>\n    <td valign=top> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n        <tr>\n          <td width=\"50%\" valign=\"top\">\n            <table width=98% cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" border=\"0\" >\n              <tr>\n                <td class=\"tableheadingbborder\" colspan=\"2\"><a href=\"https://forums.manageengine.com/appmanager/#Forum");
/* 3137 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 3138 */                 out.write("\" target=\"_blank\" class=\"bodytextboldwhiteun\">");
/* 3139 */                 out.print(FormatUtil.getString("am.webclient.support.userform.heading"));
/* 3140 */                 out.write(" </td>\n              </tr>\n              <tr>\n                  <td class=\"bodytextbold yellowgrayborder\" width=\"77%\" align=\"left\"  style=\"position:relative;padding-left:15px;\"><b>");
/* 3141 */                 out.print(FormatUtil.getString("am.webclient.supporttab.topics.text"));
/* 3142 */                 out.write("</b>   </td>\n                  <td class=\"bodytextbold yellowgrayborder\" width=\"23%\" align=\"left\" ><b>");
/* 3143 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 3144 */                 out.write("</b> </td>\n              </tr>\n              <tr>\n                  <td class=\"bodytextboldunderline\" colspan=\"2\">\n                  \t<iframe src=\"https://www.manageengine.com/products/applications_manager/apm-forums.html");
/* 3145 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 3146 */                 out.write("\" frameborder=\"0\" width=\"100%\" height=\"150px\" scrolling=\"no\"></iframe>\n                  </td>\n              </tr>\n              <tr>\n                <td colspan=\"2\" class=\"tablebottom\" align=\"right\"><a href=\"https://forums.manageengine.com/appmanager/#Forum");
/* 3147 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 3148 */                 out.write("\" target=\"_blank\" class=\"resourcename\">\n                  ");
/* 3149 */                 out.print(FormatUtil.getString("am.webclient.support.userform.link"));
/* 3150 */                 out.write(" >></a> &nbsp;&nbsp; </td>\n              </tr>\n            </table>\n          </td>\n\t\t<td width=\"50%\" valign=\"top\">\n             <table width=100% cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\" border=\"0\" >\n\t\t   <tr>\n\t\t    <td colspan=6 class=\"tableheadingbborder\"><a href=\"https://blogs.manageengine.com/appmanager");
/* 3151 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 3152 */                 out.write("\" target=\"_blank\" class=\"bodytextboldwhiteun\">");
/* 3153 */                 out.print(FormatUtil.getString("am.webclient.support.userblog.heading"));
/* 3154 */                 out.write("</a></td>\n\t\t  </tr>\n                  <tr>\n\t\t\t<td class=\"bodytextbold yellowgrayborder\" align=\"left\" width=\"77%\" style=\"position:relative;padding-left:15px;\"><b>");
/* 3155 */                 out.print(FormatUtil.getString("am.webclient.supporttab.topics.text"));
/* 3156 */                 out.write(" </b></td>\n\t\t\t<td class=\"bodytextbold yellowgrayborder\" align=\"left\" width=\"23%\" ><b>");
/* 3157 */                 out.print(FormatUtil.getString("am.webclient.historydata.date.text"));
/* 3158 */                 out.write("</b></td>\n\t\t   </tr>\n\t\t   <tr>\n                        <td class=\"bodytextboldunderline\" width=\"50%\" colspan='2'><iframe src=\"https://www.manageengine.com/products/applications_manager/apm-blogs.html");
/* 3159 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 3160 */                 out.write("\" width=\"100%\" height=\"150px\" scrolling=\"no\" frameborder=\"0\" class=\"staticlinks\"></iframe>\n\t\t  </td>\n\t\t  </tr>\n\t\t  <tr>\n\t\t  <td class=\"tablebottom\" colspan='2' align=\"right\"><a href=\"https://blogs.manageengine.com/appmanager");
/* 3161 */                 out.print(System.getProperty("did") != null ? "?" + System.getProperty("did") : "");
/* 3162 */                 out.write("\" target=\"_blank\" class=\"resourcename\">");
/* 3163 */                 out.print(FormatUtil.getString("am.webclient.support.userform.link"));
/* 3164 */                 out.write(" >></a> &nbsp;&nbsp; </td>\n\t\t  </tr>\n\t\t  </table>\n          </td>\n\t\t </tr>\n\t\t</table>\n\t\t <br>\n\t\t</td>\n\t\t</tr>\n\t\t<br>\n\t\t");
/*      */               }
/* 3166 */               out.write("\n\t\t<tr>\n\t\t <td valign=\"top\">\n");
/*      */               
/* 3168 */               String buildnumber = "";
/* 3169 */               if (com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */               {
/* 3171 */                 buildnumber = com.adventnet.appmanager.util.Constants.getCategorytype();
/*      */               }
/*      */               
/*      */ 
/* 3175 */               out.write("\n         <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n\t      <tr>\n\t       <td height=\"18\" width=\"50%\" valign=\"top\"> <table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbtborder\">\n\t   <tr>\n\t    <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3176 */               out.print(FormatUtil.getString("am.webclient.support.systeminformation.installation"));
/* 3177 */               out.write("</td>\n\t   </tr>\n\t   <tr>\n\t    <td width=\"35%\" class=\"yellowgrayborder\">");
/* 3178 */               out.print(FormatUtil.getString("am.webclient.support.systeminformation.hostname"));
/* 3179 */               out.write("</td>\n\t   <td width=\"65%\" class=\"yellowgrayborder\">");
/* 3180 */               out.print(sysinfo.get(0));
/* 3181 */               out.write("</td>\n</tr>\n\t   ");
/* 3182 */               if (!OEMUtil.isOEM()) {
/* 3183 */                 out.write("\n\t   <tr>\n\t   <td class=\"whitegrayborder\">");
/* 3184 */                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.version"));
/* 3185 */                 out.write("</td>\n \t   <td class=\"whitegrayborder\">");
/* 3186 */                 out.print(sysinfo.get(1));
/* 3187 */                 out.write(44);
/* 3188 */                 out.write(32);
/* 3189 */                 out.print(sysinfo.get(2));
/* 3190 */                 out.write("</td>\n\t   </tr>\n\t   <tr>\n\t   <td class=\"whitegrayborder\">");
/* 3191 */                 out.print(FormatUtil.getString("am.webclient.support.OS.version"));
/* 3192 */                 out.write("</td>\n\t   ");
/* 3193 */                 if (System.getProperty("sun.arch.data.model").equals("32")) {
/* 3194 */                   out.write("\n \t   <td class=\"whitegrayborder\">");
/* 3195 */                   out.print(FormatUtil.getString("am.webclient.support.32bitVersion"));
/* 3196 */                   out.write("</td>\n\t   ");
/*      */                 } else {
/* 3198 */                   out.write("\n \t   <td class=\"whitegrayborder\">");
/* 3199 */                   out.print(FormatUtil.getString("am.webclient.support.64bitVersion"));
/* 3200 */                   out.write("</td>\n\t   ");
/*      */                 }
/* 3202 */                 out.write("\n\t   </tr>\n\t   <tr>\n\t   <td class=\"whitegrayborder\">");
/* 3203 */                 out.print(FormatUtil.getString("am.webclient.support.dbserver.type"));
/* 3204 */                 out.write("</td>\n\t   ");
/* 3205 */                 String dbserverType = System.getProperty("am.dbserver.type");
/* 3206 */                 if ((dbserverType != null) && (dbserverType.equalsIgnoreCase("mssql"))) {
/* 3207 */                   out.write(10);
/* 3208 */                   out.write(9);
/* 3209 */                   out.write(9);
/* 3210 */                   if (_jspx_meth_c_005fset_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 3212 */                   out.write("\n \t   <td class=\"whitegrayborder\">");
/* 3213 */                   out.print(FormatUtil.getString("am.webclient.support.mssqldb"));
/* 3214 */                   out.write("</td>\n\t   ");
/* 3215 */                 } else if ((dbserverType != null) && (dbserverType.equalsIgnoreCase("pgsql"))) {
/* 3216 */                   out.write("\n \t   <td class=\"whitegrayborder\">");
/* 3217 */                   out.print(FormatUtil.getString("PostgreSQL"));
/* 3218 */                   out.write("</td>\n\t   ");
/*      */                 } else {
/* 3220 */                   out.write("\n \t   <td class=\"whitegrayborder\">");
/* 3221 */                   out.print(FormatUtil.getString("MySQL"));
/* 3222 */                   out.write("</td>\n\t   ");
/*      */                 }
/* 3224 */                 out.write("\n\t   </tr>\n\t   <tr>\n\t   <td width=\"27%\" class=\"yellowgrayborder\">");
/* 3225 */                 out.print(FormatUtil.getString("am.webclient.support.systeminformation.installationdir"));
/* 3226 */                 out.write("</td>\n\t\t   <td width=\"73%\" class=\"yellowgrayborder\" title=\"");
/* 3227 */                 out.print(serverdetails.get(0));
/* 3228 */                 out.write(34);
/* 3229 */                 out.write(62);
/* 3230 */                 out.print(getTrimmedText((String)serverdetails.get(0), 45));
/* 3231 */                 out.write("</td>\n\t\t   </tr>\n\t\t   ");
/*      */               }
/* 3233 */               out.write("\n\t\t   <tr>\n\t\t   <td class=\"whitegrayborder\">");
/* 3234 */               out.print(FormatUtil.getString("am.webclient.support.systeminformation.time"));
/* 3235 */               out.write(" </td>\n\t\t   <td class=\"whitegrayborder\">");
/* 3236 */               out.print(formatDT(((Long)serverdetails.get(1)).longValue() + ""));
/* 3237 */               out.write("</td>\n\t\t   </tr>\n\t\t<tr>\n                <td class=\"whitegrayborder\">");
/* 3238 */               out.print(FormatUtil.getString("am.webclient.support.buildnumber.heading"));
/* 3239 */               out.write("</td>\n                    <td class=\"whitegrayborder\">");
/* 3240 */               out.print(OEMUtil.getOEMString("product.build.number"));
/* 3241 */               out.write("</td>\n              </tr>\n              <td class=\"whitegrayborder\">");
/* 3242 */               out.print(FormatUtil.getString("am.webclient.support.productedition.text"));
/* 3243 */               out.write("</td>\n                    <td class=\"whitegrayborder\">");
/* 3244 */               out.print(serverdetails.get(5));
/* 3245 */               out.write("</td>\n              </tr>\n              <td class=\"whitegrayborder\">");
/* 3246 */               out.print(FormatUtil.getString("am.webclient.support.javaversion.text"));
/* 3247 */               out.write("</td>\n                    <td class=\"whitegrayborder\">");
/* 3248 */               out.print(serverdetails.get(3));
/* 3249 */               out.write("</td>\n              </tr>\n        <!--  <tr>\n                <td class=\"yellowgrayborder\">");
/* 3250 */               out.print(FormatUtil.getString("am.webclient.support.servicepack.heading"));
/* 3251 */               out.write("</td>\n                <td class=\"yellowgrayborder\">");
/* 3252 */               out.print(AMAutomaticPortChanger.getServicePackVersion());
/* 3253 */               out.write("</td>\n              </tr>-->\n\t\t   <tr>\n\t\t   <td class=\"yellowgrayborder\">");
/* 3254 */               out.print(FormatUtil.getString("am.webclient.support.systeminformation.port"));
/* 3255 */               out.write("</td>\n\t\t   <td class=\"yellowgrayborder\">");
/* 3256 */               out.print(System.getProperty("webserver.port"));
/* 3257 */               out.write("</td>\n\t\t   </tr>\n\n\t\t   <tr>\n\t\t   <td class=\"whitegrayborder\">");
/* 3258 */               out.print(FormatUtil.getString("am.webclient.support.systeminformation.servertype"));
/* 3259 */               out.write(" </td>\n\t\t   ");
/*      */               
/* 3261 */               if ((request.getRemoteUser() != null) && (request.getRemoteUser().equals("systemadmin_enterprise")))
/*      */               {
/*      */ 
/* 3264 */                 out.write("\n\t\t\t <td class=\"whitegrayborder\">");
/* 3265 */                 out.print(FormatUtil.getString("am.webclient.admintab.text"));
/* 3266 */                 out.write("&nbsp;&nbsp;&nbsp;</td>\n\t\t   ");
/*      */ 
/*      */               }
/*      */               else
/*      */               {
/* 3271 */                 out.write("\n\t\t   <td class=\"whitegrayborder\">");
/* 3272 */                 out.print(EnterpriseUtil.getServerTypeDisplayName());
/* 3273 */                 out.write("&nbsp;&nbsp;&nbsp;</td>\n\t\t   ");
/*      */               }
/* 3275 */               out.write("\n\t\t   </tr>\n\t\t   <tr>\n\t\t\t <td class=\"yellowgrayborder\">");
/* 3276 */               out.print(FormatUtil.getString("am.webclient.support.loadfactor"));
/* 3277 */               out.write("</td>\n\t\t\t <td class=\"yellowgrayborder\">");
/* 3278 */               out.print(com.adventnet.appmanager.util.LoadFactorCalculator.getLoadFactor());
/* 3279 */               out.write("</td>\n\t\t   </tr>\n\t   ");
/*      */               
/* 3281 */               if (EnterpriseUtil.isManagedServer())
/*      */               {
/*      */ 
/* 3284 */                 out.write("\n\t     <tr>\n\t       <td class=\"whitegrayborder\">");
/* 3285 */                 out.print(FormatUtil.getString("am.webclient.managedserver.serverid"));
/* 3286 */                 out.write("</td>\n\t       <td class=\"whitegrayborder\">");
/* 3287 */                 out.print(EnterpriseUtil.getManagedServerIndex());
/* 3288 */                 out.write("</td>\n\t     </td>\n\t     </tr>\n\t     ");
/*      */               }
/*      */               
/*      */ 
/* 3292 */               out.write("\n\t\t   </table></td>\n\n\t\t   <td width=\"50%\" valign=\"top\">\n\t\t");
/*      */               
/* 3294 */               String categorytype = "";
/* 3295 */               if (com.adventnet.appmanager.util.Constants.isMinimizedversion())
/*      */               {
/* 3297 */                 categorytype = com.adventnet.appmanager.util.Constants.getCategorytype();
/*      */               }
/*      */               
/* 3300 */               out.write("\n\n            <a name=\"evaluate\"> </a>\n\n            ");
/* 3301 */               org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/jsp/licenseinfo.jsp", out, true);
/* 3302 */               out.write("\n\t\t\t</td>\n        </tr>\n\t\t<tr>\n\t\t<td>\n\t\t\t<br>\n\t\t\t<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n        <tr>\n          <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3303 */               out.print(FormatUtil.getString("am.webclient.support.jvmdetails"));
/* 3304 */               out.write("</td>\n        </tr>\n        <tr>\n          <td width=\"42%\" class=\"yellowgrayborder\">");
/* 3305 */               out.print(FormatUtil.getString("am.webclient.support.jvmdetails.total"));
/* 3306 */               out.write("</td>\n          <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3307 */               out.print(heapinfo.get(0));
/* 3308 */               out.write("&nbsp;\n            MB</td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3309 */               out.print(FormatUtil.getString("am.webclient.support.jvmdetails.used"));
/* 3310 */               out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 3311 */               out.print(heapinfo.get(1));
/* 3312 */               out.write("&nbsp;MB</td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 3313 */               out.print(FormatUtil.getString("am.webclient.support.jvmdetails.free"));
/* 3314 */               out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 3315 */               out.print(heapinfo.get(2));
/* 3316 */               out.write("&nbsp;MB</td>\n        </tr>\n\t<tr>\n\t\t");
/*      */               
/* 3318 */               PresentTag _jspx_th_logic_005fpresent_005f2 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 3319 */               _jspx_th_logic_005fpresent_005f2.setPageContext(_jspx_page_context);
/* 3320 */               _jspx_th_logic_005fpresent_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3322 */               _jspx_th_logic_005fpresent_005f2.setRole("ADMIN,ENTERPRISEADMIN");
/* 3323 */               int _jspx_eval_logic_005fpresent_005f2 = _jspx_th_logic_005fpresent_005f2.doStartTag();
/* 3324 */               if (_jspx_eval_logic_005fpresent_005f2 != 0) {
/*      */                 for (;;) {
/* 3326 */                   out.write("\n\t       <td class=\"tablebottom\" colspan=\"2\"> ");
/* 3327 */                   if (!OEMUtil.isRemove("am.threaddump.remove")) {
/* 3328 */                     out.write("<a href=\"/jsp/threaddump.jsp\"  target=\"none\" class=\"staticlinks\" title=\"");
/* 3329 */                     out.print(FormatUtil.getString("am.webclient.support.threaddump.text"));
/* 3330 */                     out.write(34);
/* 3331 */                     out.write(62);
/* 3332 */                     out.write(32);
/* 3333 */                     out.print(FormatUtil.getString("am.webclient.support.threaddump.link"));
/* 3334 */                     out.write("</a>&nbsp;&nbsp&nbsp;");
/*      */                   }
/* 3336 */                   out.write("<a href=\"/jsp/monitorerrors.jsp\"  target=\"none\" class=\"staticlinks\" title=\"");
/* 3337 */                   out.print(FormatUtil.getString("am.viewmonitor.errors"));
/* 3338 */                   out.write(34);
/* 3339 */                   out.write(62);
/* 3340 */                   out.print(FormatUtil.getString("am.viewmonitor.errors"));
/* 3341 */                   out.write("</a>&nbsp;&nbsp;&nbsp;\t");
/* 3342 */                   if (!OEMUtil.isRemove("am.threaddump.remove")) {
/* 3343 */                     out.write("\n\t  <a href=\"/jsp/dump.jsp\"  target=\"none\" class=\"staticlinks\" title=\"");
/* 3344 */                     out.print(FormatUtil.getString("am.webclient.support.DBstatus.text"));
/* 3345 */                     out.write(34);
/* 3346 */                     out.write(62);
/* 3347 */                     out.write(32);
/* 3348 */                     out.print(FormatUtil.getString("am.webclient.support.DBstatus.details.text"));
/* 3349 */                     out.write("</a>&nbsp;&nbsp;&nbsp;\t");
/*      */                   }
/* 3351 */                   out.write("<a href=\"/viewLogs.do\"  target=\"none\" class=\"staticlinks\" title=\"");
/* 3352 */                   out.print(FormatUtil.getString("am.title.viewlogs"));
/* 3353 */                   out.write(34);
/* 3354 */                   out.write(62);
/* 3355 */                   out.print(FormatUtil.getString("am.title.viewlogs"));
/* 3356 */                   out.write("</a>&nbsp;&nbsp;&nbsp;<a href='javascript:triggerHeapDump()' class=\"staticlinks\" title=\"");
/* 3357 */                   out.print(FormatUtil.getString("am.webclient.heapdump.success"));
/* 3358 */                   out.write(34);
/* 3359 */                   out.write(62);
/* 3360 */                   out.print(FormatUtil.getString("am.webclient.trigger.heapdump"));
/* 3361 */                   out.write("</a></td>\n\t\t");
/* 3362 */                   int evalDoAfterBody = _jspx_th_logic_005fpresent_005f2.doAfterBody();
/* 3363 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3367 */               if (_jspx_th_logic_005fpresent_005f2.doEndTag() == 5) {
/* 3368 */                 this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2); return;
/*      */               }
/*      */               
/* 3371 */               this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f2);
/* 3372 */               out.write("\n\t\t<div id=\"testCredentialResult\">\t\t\t \n\t\t</div>\t\n\t\t</td>\n\t</tr>\n      </table>\n\n\t\t  </td>\n\t\t  <td valign=\"top\">\n\t\t\t<br>\n\t\t\t<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrtbdarkborder\">\n\t\t\t\t<tr>\n\t\t\t\t  <td colspan=\"2\" class=\"tableheadingbborder\">");
/* 3373 */               out.print(FormatUtil.getString("am.webclient.support.dbdetails"));
/* 3374 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t<tr> \n\t\t\t\t  <td width=\"42%\" class=\"yellowgrayborderbr\">");
/* 3375 */               out.print(FormatUtil.getString("am.webclient.support.systeminformation.hostname"));
/* 3376 */               out.write("</td>\n\t\t\t\t  <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3377 */               out.print(AMAutomaticPortChanger.getHostName());
/* 3378 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/*      */               
/* 3380 */               ChooseTag _jspx_th_c_005fchoose_005f0 = (ChooseTag)this._005fjspx_005ftagPool_005fc_005fchoose.get(ChooseTag.class);
/* 3381 */               _jspx_th_c_005fchoose_005f0.setPageContext(_jspx_page_context);
/* 3382 */               _jspx_th_c_005fchoose_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/* 3383 */               int _jspx_eval_c_005fchoose_005f0 = _jspx_th_c_005fchoose_005f0.doStartTag();
/* 3384 */               if (_jspx_eval_c_005fchoose_005f0 != 0) {
/*      */                 for (;;) {
/* 3386 */                   out.write("\n\t\t\t\t");
/*      */                   
/* 3388 */                   WhenTag _jspx_th_c_005fwhen_005f0 = (WhenTag)this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.get(WhenTag.class);
/* 3389 */                   _jspx_th_c_005fwhen_005f0.setPageContext(_jspx_page_context);
/* 3390 */                   _jspx_th_c_005fwhen_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/*      */                   
/* 3392 */                   _jspx_th_c_005fwhen_005f0.setTest("${!empty databaseMsSQL}");
/* 3393 */                   int _jspx_eval_c_005fwhen_005f0 = _jspx_th_c_005fwhen_005f0.doStartTag();
/* 3394 */                   if (_jspx_eval_c_005fwhen_005f0 != 0) {
/*      */                     for (;;) {
/* 3396 */                       out.write("\n\t\t\t\t\t");
/*      */                       
/* 3398 */                       IfTag _jspx_th_c_005fif_005f10 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3399 */                       _jspx_th_c_005fif_005f10.setPageContext(_jspx_page_context);
/* 3400 */                       _jspx_th_c_005fif_005f10.setParent(_jspx_th_c_005fwhen_005f0);
/*      */                       
/* 3402 */                       _jspx_th_c_005fif_005f10.setTest("${!empty databasePort}");
/* 3403 */                       int _jspx_eval_c_005fif_005f10 = _jspx_th_c_005fif_005f10.doStartTag();
/* 3404 */                       if (_jspx_eval_c_005fif_005f10 != 0) {
/*      */                         for (;;) {
/* 3406 */                           out.write("\n\t\t\t\t\t\t<tr> \n\t\t\t\t\t\t  <td width=\"42%\" class=\"yellowgrayborderbr\">");
/* 3407 */                           out.print(FormatUtil.getString("Port Number"));
/* 3408 */                           out.write("</td>\n\t\t\t\t\t\t  <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3409 */                           if (_jspx_meth_c_005fout_005f1(_jspx_th_c_005fif_005f10, _jspx_page_context))
/*      */                             return;
/* 3411 */                           out.write("</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t");
/* 3412 */                           int evalDoAfterBody = _jspx_th_c_005fif_005f10.doAfterBody();
/* 3413 */                           if (evalDoAfterBody != 2)
/*      */                             break;
/*      */                         }
/*      */                       }
/* 3417 */                       if (_jspx_th_c_005fif_005f10.doEndTag() == 5) {
/* 3418 */                         this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10); return;
/*      */                       }
/*      */                       
/* 3421 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f10);
/* 3422 */                       out.write("\n\t\t\t\t");
/* 3423 */                       int evalDoAfterBody = _jspx_th_c_005fwhen_005f0.doAfterBody();
/* 3424 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3428 */                   if (_jspx_th_c_005fwhen_005f0.doEndTag() == 5) {
/* 3429 */                     this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0); return;
/*      */                   }
/*      */                   
/* 3432 */                   this._005fjspx_005ftagPool_005fc_005fwhen_0026_005ftest.reuse(_jspx_th_c_005fwhen_005f0);
/* 3433 */                   out.write("\n\t\t\t\t");
/*      */                   
/* 3435 */                   OtherwiseTag _jspx_th_c_005fotherwise_005f0 = (OtherwiseTag)this._005fjspx_005ftagPool_005fc_005fotherwise.get(OtherwiseTag.class);
/* 3436 */                   _jspx_th_c_005fotherwise_005f0.setPageContext(_jspx_page_context);
/* 3437 */                   _jspx_th_c_005fotherwise_005f0.setParent(_jspx_th_c_005fchoose_005f0);
/* 3438 */                   int _jspx_eval_c_005fotherwise_005f0 = _jspx_th_c_005fotherwise_005f0.doStartTag();
/* 3439 */                   if (_jspx_eval_c_005fotherwise_005f0 != 0) {
/*      */                     for (;;) {
/* 3441 */                       out.write("\n\t\t\t\t<tr> \n\t\t\t\t  <td width=\"42%\" class=\"yellowgrayborderbr\">");
/* 3442 */                       out.print(FormatUtil.getString("Port Number"));
/* 3443 */                       out.write("</td>\n\t\t\t\t  <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3444 */                       out.print(AMAutomaticPortChanger.getDBPort());
/* 3445 */                       out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3446 */                       int evalDoAfterBody = _jspx_th_c_005fotherwise_005f0.doAfterBody();
/* 3447 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3451 */                   if (_jspx_th_c_005fotherwise_005f0.doEndTag() == 5) {
/* 3452 */                     this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0); return;
/*      */                   }
/*      */                   
/* 3455 */                   this._005fjspx_005ftagPool_005fc_005fotherwise.reuse(_jspx_th_c_005fotherwise_005f0);
/* 3456 */                   out.write("\n\t\t\t\t");
/* 3457 */                   int evalDoAfterBody = _jspx_th_c_005fchoose_005f0.doAfterBody();
/* 3458 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3462 */               if (_jspx_th_c_005fchoose_005f0.doEndTag() == 5) {
/* 3463 */                 this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0); return;
/*      */               }
/*      */               
/* 3466 */               this._005fjspx_005ftagPool_005fc_005fchoose.reuse(_jspx_th_c_005fchoose_005f0);
/* 3467 */               out.write("\n\t\t\t\t");
/*      */               
/* 3469 */               IfTag _jspx_th_c_005fif_005f11 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3470 */               _jspx_th_c_005fif_005f11.setPageContext(_jspx_page_context);
/* 3471 */               _jspx_th_c_005fif_005f11.setParent(_jspx_th_tiles_005fput_005f3);
/*      */               
/* 3473 */               _jspx_th_c_005fif_005f11.setTest("${!empty databaseVersion}");
/* 3474 */               int _jspx_eval_c_005fif_005f11 = _jspx_th_c_005fif_005f11.doStartTag();
/* 3475 */               if (_jspx_eval_c_005fif_005f11 != 0) {
/*      */                 for (;;) {
/* 3477 */                   out.write("\n\t\t\t\t<tr> \n\t\t\t\t  <td width=\"42%\" class=\"yellowgrayborderbr\">");
/* 3478 */                   out.print(FormatUtil.getString("am.webclient.mssqldetails.version"));
/* 3479 */                   out.write("</td>\n\t\t\t\t  <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3480 */                   if (_jspx_meth_c_005fout_005f2(_jspx_th_c_005fif_005f11, _jspx_page_context))
/*      */                     return;
/* 3482 */                   out.write("</td>\n\t\t\t\t</tr>\n\t\t\t\t");
/* 3483 */                   int evalDoAfterBody = _jspx_th_c_005fif_005f11.doAfterBody();
/* 3484 */                   if (evalDoAfterBody != 2)
/*      */                     break;
/*      */                 }
/*      */               }
/* 3488 */               if (_jspx_th_c_005fif_005f11.doEndTag() == 5) {
/* 3489 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11); return;
/*      */               }
/*      */               
/* 3492 */               this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f11);
/* 3493 */               out.write("\n\t\t\t\t<tr> \n\t\t\t\t  <td width=\"42%\" class=\"yellowgrayborderbr\">");
/* 3494 */               out.print(FormatUtil.getString("Database Name"));
/* 3495 */               out.write("</td>\n\t\t\t\t  <td width=\"58%\" class=\"yellowgrayborder\">");
/* 3496 */               out.print(AMAutomaticPortChanger.getDatabaseName());
/* 3497 */               out.write("</td>\n\t\t\t\t</tr>\n\t\t\t</table>\n\t\t  </td>\n        </tr>\n      </table></td>\n  </tr>\n</table>\n\n\n<br>\n");
/* 3498 */               if (mysqlResourceID != -1) {
/* 3499 */                 out.write("\n<!-- MYSQL STUFF STARTS HERE  -->\n<table width=\"98%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"lrtborder\" align=\"center\">\n  <tr>\n    <td class=\"tableheading\">");
/* 3500 */                 out.print(FormatUtil.getString("am.webclient.support.dbconnectiontime"));
/* 3501 */                 out.write(45);
/* 3502 */                 out.write(32);
/* 3503 */                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3504 */                 out.write("</td>\n    <td width=\"50%\" class=\"tableheading\">");
/* 3505 */                 out.print(FormatUtil.getString("am.webclient.support.dbreq"));
/* 3506 */                 out.write(32);
/* 3507 */                 out.write(45);
/* 3508 */                 out.write(32);
/* 3509 */                 out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 3510 */                 out.write("</td>\n  </tr>\n</table>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" align=\"center\">\n  <tr>\n    <td class=\"rbborder\"> <table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n        <tr>\n          <td width=\"94%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3511 */                 out.print(mysqlResourceID);
/* 3512 */                 out.write("&attributeid=101&period=-7',740,550)\">\n            <img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"3\" border=\"0\" title=\"Click to view last seven days data\"></a></td>\n          <td width=\"6%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3513 */                 out.print(mysqlResourceID);
/* 3514 */                 out.write("&attributeid=101&period=-30',740,550)\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"3\" border=\"0\" title=\"Click to view last thirty days data\"></a></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\" title=\"");
/* 3515 */                 out.print(congraph);
/* 3516 */                 out.write("\" >\n            ");
/*      */                 
/* 3518 */                 mysqlgraph.setresid(mysqlResourceID);
/* 3519 */                 mysqlgraph.settype("CONNECTIONTIME");
/*      */                 
/* 3521 */                 out.write("\n            ");
/*      */                 
/* 3523 */                 TimeChart _jspx_th_awolf_005ftimechart_005f0 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3524 */                 _jspx_th_awolf_005ftimechart_005f0.setPageContext(_jspx_page_context);
/* 3525 */                 _jspx_th_awolf_005ftimechart_005f0.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3527 */                 _jspx_th_awolf_005ftimechart_005f0.setDataSetProducer("mysqlgraph");
/*      */                 
/* 3529 */                 _jspx_th_awolf_005ftimechart_005f0.setWidth("400");
/*      */                 
/* 3531 */                 _jspx_th_awolf_005ftimechart_005f0.setHeight("170");
/*      */                 
/* 3533 */                 _jspx_th_awolf_005ftimechart_005f0.setLegend("true");
/*      */                 
/* 3535 */                 _jspx_th_awolf_005ftimechart_005f0.setXaxisLabel(xaxis_time);
/*      */                 
/* 3537 */                 _jspx_th_awolf_005ftimechart_005f0.setYaxisLabel(yaxis_timeinms);
/* 3538 */                 int _jspx_eval_awolf_005ftimechart_005f0 = _jspx_th_awolf_005ftimechart_005f0.doStartTag();
/* 3539 */                 if (_jspx_eval_awolf_005ftimechart_005f0 != 0) {
/* 3540 */                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3541 */                     out = _jspx_page_context.pushBody();
/* 3542 */                     _jspx_th_awolf_005ftimechart_005f0.setBodyContent((BodyContent)out);
/* 3543 */                     _jspx_th_awolf_005ftimechart_005f0.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3546 */                     out.write("\n            ");
/* 3547 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f0.doAfterBody();
/* 3548 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3551 */                   if (_jspx_eval_awolf_005ftimechart_005f0 != 1) {
/* 3552 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3555 */                 if (_jspx_th_awolf_005ftimechart_005f0.doEndTag() == 5) {
/* 3556 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0); return;
/*      */                 }
/*      */                 
/* 3559 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f0);
/* 3560 */                 out.write(" </td>\n        </tr>\n      </table></td>\n    <td class=\"bottomborder\" width=\"50%\">\n      ");
/*      */                 
/* 3562 */                 mysqlgraph.setresid(mysqlResourceID);
/* 3563 */                 mysqlgraph.settype("REQUESTRATE");
/*      */                 
/* 3565 */                 out.write("\n      <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n        <tr>\n          <td width=\"94%\" align=\"right\">");
/*      */                 
/* 3567 */                 IfTag _jspx_th_c_005fif_005f12 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3568 */                 _jspx_th_c_005fif_005f12.setPageContext(_jspx_page_context);
/* 3569 */                 _jspx_th_c_005fif_005f12.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3571 */                 _jspx_th_c_005fif_005f12.setTest("${! empty ammysqlmap}");
/* 3572 */                 int _jspx_eval_c_005fif_005f12 = _jspx_th_c_005fif_005f12.doStartTag();
/* 3573 */                 if (_jspx_eval_c_005fif_005f12 != 0) {
/*      */                   for (;;) {
/* 3575 */                     out.write("<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3576 */                     out.print(mysqlResourceID);
/* 3577 */                     out.write("&attributeid=102&period=-7',740,550)\">\n            <img src=\"/images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"3\" border=\"0\" title=\"Click to view last seven days data\"></a>");
/* 3578 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f12.doAfterBody();
/* 3579 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3583 */                 if (_jspx_th_c_005fif_005f12.doEndTag() == 5) {
/* 3584 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12); return;
/*      */                 }
/*      */                 
/* 3587 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f12);
/* 3588 */                 out.write("</td>\n          <td width=\"6%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3589 */                 out.print(mysqlResourceID);
/* 3590 */                 out.write("&attributeid=102&period=-30',740,550)\"><img src=\"/images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"3\" border=\"0\" title=\"Click to view last thirty days data\"></a></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\" title=\"");
/* 3591 */                 out.print(reqgraph);
/* 3592 */                 out.write("\" > ");
/*      */                 
/* 3594 */                 TimeChart _jspx_th_awolf_005ftimechart_005f1 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3595 */                 _jspx_th_awolf_005ftimechart_005f1.setPageContext(_jspx_page_context);
/* 3596 */                 _jspx_th_awolf_005ftimechart_005f1.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3598 */                 _jspx_th_awolf_005ftimechart_005f1.setDataSetProducer("mysqlgraph");
/*      */                 
/* 3600 */                 _jspx_th_awolf_005ftimechart_005f1.setWidth("400");
/*      */                 
/* 3602 */                 _jspx_th_awolf_005ftimechart_005f1.setHeight("170");
/*      */                 
/* 3604 */                 _jspx_th_awolf_005ftimechart_005f1.setLegend("true");
/*      */                 
/* 3606 */                 _jspx_th_awolf_005ftimechart_005f1.setXaxisLabel(xaxis_time);
/*      */                 
/* 3608 */                 _jspx_th_awolf_005ftimechart_005f1.setYaxisLabel(yaxis_request);
/* 3609 */                 int _jspx_eval_awolf_005ftimechart_005f1 = _jspx_th_awolf_005ftimechart_005f1.doStartTag();
/* 3610 */                 if (_jspx_eval_awolf_005ftimechart_005f1 != 0) {
/* 3611 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3612 */                     out = _jspx_page_context.pushBody();
/* 3613 */                     _jspx_th_awolf_005ftimechart_005f1.setBodyContent((BodyContent)out);
/* 3614 */                     _jspx_th_awolf_005ftimechart_005f1.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3617 */                     out.write("\n            ");
/* 3618 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f1.doAfterBody();
/* 3619 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3622 */                   if (_jspx_eval_awolf_005ftimechart_005f1 != 1) {
/* 3623 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3626 */                 if (_jspx_th_awolf_005ftimechart_005f1.doEndTag() == 5) {
/* 3627 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1); return;
/*      */                 }
/*      */                 
/* 3630 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f1);
/* 3631 */                 out.write(" </td>\n        </tr>\n      </table></td>\n  </tr><tr><td width=\"50%\" align=\"center\" valign=\"top\" class=\"rborder\">\n  <table align=\"left\" width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >\n    <tr>\n      <td width=\"50%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3632 */                 if (_jspx_meth_fmt_005fmessage_005f0(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3634 */                 out.write("</span></td>\n      <td width=\"25%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3635 */                 if (_jspx_meth_fmt_005fmessage_005f1(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3637 */                 out.write("</span></td>\n      <td class=\"columnheadingnotop\" ><span class=\"bodytextbold\">");
/* 3638 */                 if (_jspx_meth_fmt_005fmessage_005f2(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3640 */                 out.write("</span></td>\n    </tr>\n    <tr>\n      <td width=\"50%\" class=\"whitegrayborder\" title=\"");
/* 3641 */                 out.print(con);
/* 3642 */                 out.write("\" > ");
/* 3643 */                 out.print(FormatUtil.getString("Connection Time"));
/* 3644 */                 out.write("\n      </td>\n      <td width=\"25%\" title=\"");
/* 3645 */                 out.print(con);
/* 3646 */                 out.write("\" class=\"whitegrayborder\">\n        ");
/*      */                 
/* 3648 */                 if (amMysqlDetails.containsKey("CONNECTIONTIME"))
/*      */                 {
/*      */ 
/* 3651 */                   out.write("\n        ");
/* 3652 */                   out.print(formatNumber((String)amMysqlDetails.get("CONNECTIONTIME")));
/* 3653 */                   out.write("&nbsp;\n        ");
/* 3654 */                   out.print(FormatUtil.getString("ms"));
/* 3655 */                   out.write("\n        ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3661 */                   out.write("\n        -\n        ");
/*      */                 }
/*      */                 
/*      */ 
/* 3665 */                 out.write("\n      </td>\n      <td itle=\"");
/* 3666 */                 out.print(con);
/* 3667 */                 out.write("\" class=\"whitegrayborder\">");
/*      */                 
/* 3669 */                 IfTag _jspx_th_c_005fif_005f13 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3670 */                 _jspx_th_c_005fif_005f13.setPageContext(_jspx_page_context);
/* 3671 */                 _jspx_th_c_005fif_005f13.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3673 */                 _jspx_th_c_005fif_005f13.setTest("${! empty ammysqlmap}");
/* 3674 */                 int _jspx_eval_c_005fif_005f13 = _jspx_th_c_005fif_005f13.doStartTag();
/* 3675 */                 if (_jspx_eval_c_005fif_005f13 != 0) {
/*      */                   for (;;) {
/* 3677 */                     out.write("<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3678 */                     out.print(mysqlResourceID);
/* 3679 */                     out.write("&attributeid=101')\">");
/* 3680 */                     out.print(getSeverityImage(alert.getProperty(mysqlResourceID + "#" + "101")));
/* 3681 */                     out.write("</a>");
/* 3682 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f13.doAfterBody();
/* 3683 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3687 */                 if (_jspx_th_c_005fif_005f13.doEndTag() == 5) {
/* 3688 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13); return;
/*      */                 }
/*      */                 
/* 3691 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f13);
/* 3692 */                 out.write("</td>\n    </tr>\n\t");
/*      */                 
/* 3694 */                 if (!EnterpriseUtil.isAdminServer())
/*      */                 {
/*      */ 
/* 3697 */                   out.write("\n    <tr> <td colspan=\"3\" align=\"right\" height=\"21\" class=\"bodytext\">");
/*      */                   
/* 3699 */                   IfTag _jspx_th_c_005fif_005f14 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3700 */                   _jspx_th_c_005fif_005f14.setPageContext(_jspx_page_context);
/* 3701 */                   _jspx_th_c_005fif_005f14.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 3703 */                   _jspx_th_c_005fif_005f14.setTest("${! empty ammysqlmap}");
/* 3704 */                   int _jspx_eval_c_005fif_005f14 = _jspx_th_c_005fif_005f14.doStartTag();
/* 3705 */                   if (_jspx_eval_c_005fif_005f14 != 0) {
/*      */                     for (;;) {
/* 3707 */                       out.write("<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3708 */                       out.print(mysqlResourceID);
/* 3709 */                       out.write("&attributeIDs=101&attributeToSelect=101&redirectto=");
/* 3710 */                       out.print(redirect);
/* 3711 */                       out.write("\" class=\"links\">");
/* 3712 */                       out.print(ALERTCONFIG_TEXT);
/* 3713 */                       out.write("</a>");
/* 3714 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f14.doAfterBody();
/* 3715 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3719 */                   if (_jspx_th_c_005fif_005f14.doEndTag() == 5) {
/* 3720 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14); return;
/*      */                   }
/*      */                   
/* 3723 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f14);
/* 3724 */                   out.write("&nbsp;</td> </tr>\n  ");
/*      */                 }
/* 3726 */                 out.write("\n  </table></td><td width=\"50%\" height=\"21\"  class=\"bodytext\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n    <tbody>\n      <tr>\n        <td width=\"50%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3727 */                 if (_jspx_meth_fmt_005fmessage_005f3(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3729 */                 out.write("</span></td>\n        <td width=\"25%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3730 */                 if (_jspx_meth_fmt_005fmessage_005f4(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3732 */                 out.write("</span></td>\n        <td class=\"columnheadingnotop\" ><span class=\"bodytextbold\">");
/* 3733 */                 if (_jspx_meth_fmt_005fmessage_005f5(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3735 */                 out.write("</span></td>\n      </tr>\n      <tr>\n        <td class=\"whitegrayborder\" title=\"");
/* 3736 */                 out.print(reqrate);
/* 3737 */                 out.write(34);
/* 3738 */                 out.write(32);
/* 3739 */                 out.write(62);
/* 3740 */                 out.print(FormatUtil.getString("Request Rate"));
/* 3741 */                 out.write("</td>\n        <td title=\"");
/* 3742 */                 out.print(reqrate);
/* 3743 */                 out.write("\" class=\"whitegrayborder\" >\n          ");
/*      */                 
/* 3745 */                 if (amMysqlDetails.containsKey("REQUESTRATE"))
/*      */                 {
/*      */ 
/* 3748 */                   out.write("\n          ");
/* 3749 */                   out.print(formatNumber((String)amMysqlDetails.get("REQUESTRATE")));
/* 3750 */                   out.write("&nbsp;");
/* 3751 */                   out.print(FormatUtil.getString("Req/Sec"));
/* 3752 */                   out.write("\n          ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3758 */                   out.write("\n          -\n          ");
/*      */                 }
/*      */                 
/*      */ 
/* 3762 */                 out.write("\n        </td>\n        <td  title=\"");
/* 3763 */                 out.print(reqrate);
/* 3764 */                 out.write("\" class=\"whitegrayborder\" > ");
/*      */                 
/* 3766 */                 IfTag _jspx_th_c_005fif_005f15 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3767 */                 _jspx_th_c_005fif_005f15.setPageContext(_jspx_page_context);
/* 3768 */                 _jspx_th_c_005fif_005f15.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3770 */                 _jspx_th_c_005fif_005f15.setTest("${! empty ammysqlmap}");
/* 3771 */                 int _jspx_eval_c_005fif_005f15 = _jspx_th_c_005fif_005f15.doStartTag();
/* 3772 */                 if (_jspx_eval_c_005fif_005f15 != 0) {
/*      */                   for (;;) {
/* 3774 */                     out.write("<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3775 */                     out.print(mysqlResourceID);
/* 3776 */                     out.write("&attributeid=102')\">");
/* 3777 */                     out.print(getSeverityImage(alert.getProperty(mysqlResourceID + "#" + "102")));
/* 3778 */                     out.write("</a>");
/* 3779 */                     int evalDoAfterBody = _jspx_th_c_005fif_005f15.doAfterBody();
/* 3780 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/*      */                 }
/* 3784 */                 if (_jspx_th_c_005fif_005f15.doEndTag() == 5) {
/* 3785 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15); return;
/*      */                 }
/*      */                 
/* 3788 */                 this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f15);
/* 3789 */                 out.write("\n        </td>\n      </tr>\n      <tr>\n        <td class=\"yellowgrayborder\" title=\"");
/* 3790 */                 out.print(rec);
/* 3791 */                 out.write(34);
/* 3792 */                 out.write(32);
/* 3793 */                 out.write(62);
/* 3794 */                 out.print(FormatUtil.getString("Bytes Received Rate"));
/* 3795 */                 out.write("<br>\n        </td>\n        <td class=\"yellowgrayborder\" colspan=\"2\">\n          ");
/*      */                 
/* 3797 */                 if (amMysqlDetails.containsKey("BYTESRECEIVEDRATE"))
/*      */                 {
/*      */ 
/* 3800 */                   out.write("\n          ");
/* 3801 */                   out.print(formatNumber((String)amMysqlDetails.get("BYTESRECEIVEDRATE")));
/* 3802 */                   out.write("&nbsp;\n          ");
/* 3803 */                   out.print(FormatUtil.getString("Bytes/Sec"));
/* 3804 */                   out.write("\n          ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3810 */                   out.write("\n          -\n          ");
/*      */                 }
/*      */                 
/*      */ 
/* 3814 */                 out.write("\n        </td>\n      </tr>\n      <tr>\n        <td class=\"whitegrayborder\" title=\"");
/* 3815 */                 out.print(send);
/* 3816 */                 out.write(34);
/* 3817 */                 out.write(32);
/* 3818 */                 out.write(62);
/* 3819 */                 out.print(FormatUtil.getString("Bytes Send Rate"));
/* 3820 */                 out.write("</td>\n        <td class=\"whitegrayborder\" colspan=\"2\">\n          ");
/*      */                 
/* 3822 */                 if (amMysqlDetails.containsKey("BYTESSENDRATE"))
/*      */                 {
/*      */ 
/* 3825 */                   out.write("\n          ");
/* 3826 */                   out.print(formatNumber((String)amMysqlDetails.get("BYTESSENDRATE")));
/* 3827 */                   out.write("&nbsp;\n          ");
/* 3828 */                   out.print(FormatUtil.getString("Bytes/Sec"));
/* 3829 */                   out.write("\n          ");
/*      */ 
/*      */                 }
/*      */                 else
/*      */                 {
/*      */ 
/* 3835 */                   out.write("\n          -\n          ");
/*      */                 }
/*      */                 
/*      */ 
/* 3839 */                 out.write("\n        </td>\n      </tr>\n\t  ");
/*      */                 
/* 3841 */                 if (!EnterpriseUtil.isAdminServer())
/*      */                 {
/*      */ 
/* 3844 */                   out.write("\n      <tr> <td colspan=\"3\" align=\"right\" height=\"31\"  class=\"bodytext\">");
/*      */                   
/* 3846 */                   IfTag _jspx_th_c_005fif_005f16 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 3847 */                   _jspx_th_c_005fif_005f16.setPageContext(_jspx_page_context);
/* 3848 */                   _jspx_th_c_005fif_005f16.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 3850 */                   _jspx_th_c_005fif_005f16.setTest("${! empty ammysqlmap}");
/* 3851 */                   int _jspx_eval_c_005fif_005f16 = _jspx_th_c_005fif_005f16.doStartTag();
/* 3852 */                   if (_jspx_eval_c_005fif_005f16 != 0) {
/*      */                     for (;;) {
/* 3854 */                       out.write("<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 3855 */                       out.print(mysqlResourceID);
/* 3856 */                       out.write("&attributeIDs=102&attributeToSelect=102&redirectto=");
/* 3857 */                       out.print(redirect);
/* 3858 */                       out.write("\" class=\"links\">");
/* 3859 */                       out.print(ALERTCONFIG_TEXT);
/* 3860 */                       out.write("</a>");
/* 3861 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f16.doAfterBody();
/* 3862 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 3866 */                   if (_jspx_th_c_005fif_005f16.doEndTag() == 5) {
/* 3867 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16); return;
/*      */                   }
/*      */                   
/* 3870 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f16);
/* 3871 */                   out.write("&nbsp;</td> </tr>\n\t  ");
/*      */                 }
/* 3873 */                 out.write("\n\t  </tbody>\n  </table></td>\n  </tr>\n</table>\n\n");
/*      */               }
/*      */               
/* 3876 */               if (mssqlResourceID != -1) {
/* 3877 */                 out.write("\n\n\n <table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\" >\n  <tr>\n    <td width=\"50%\" height=\"31\" class=\"tableheadingtrans\">");
/* 3878 */                 out.print(FormatUtil.getString("am.webclient.mysql.connectionstatistics"));
/* 3879 */                 out.write(" </td>\n    <td width=\"50%\" height=\"31\" align=\"left\" class=\"tableheading\">");
/* 3880 */                 out.print(FormatUtil.getString("am.webclient.mssqldetails.cachedetails"));
/* 3881 */                 out.write("\n    </td>\n  </tr>\n  </table>\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\">\n  <tr>\n  <td width=\"50%\" height=\"38\"  class=\"rbborder\"><table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3882 */                 out.print(mssqlResourceID);
/* 3883 */                 out.write("&attributeid=3102&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3884 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3885 */                 out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3886 */                 out.print(mssqlResourceID);
/* 3887 */                 out.write("&attributeid=3102&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3888 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3889 */                 out.write("\"></a></td>\n  </tr>\n  <tr>\n   ");
/* 3890 */                 mssqlgraph.settype("CONNECTION");
/* 3891 */                 mssqlgraph.setresid(mssqlResourceID);
/*      */                 
/* 3893 */                 out.write("\n  <td>");
/*      */                 
/* 3895 */                 TimeChart _jspx_th_awolf_005ftimechart_005f2 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3896 */                 _jspx_th_awolf_005ftimechart_005f2.setPageContext(_jspx_page_context);
/* 3897 */                 _jspx_th_awolf_005ftimechart_005f2.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3899 */                 _jspx_th_awolf_005ftimechart_005f2.setDataSetProducer("mssqlgraph");
/*      */                 
/* 3901 */                 _jspx_th_awolf_005ftimechart_005f2.setWidth("330");
/*      */                 
/* 3903 */                 _jspx_th_awolf_005ftimechart_005f2.setHeight("185");
/*      */                 
/* 3905 */                 _jspx_th_awolf_005ftimechart_005f2.setLegend("true");
/*      */                 
/* 3907 */                 _jspx_th_awolf_005ftimechart_005f2.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                 
/* 3909 */                 _jspx_th_awolf_005ftimechart_005f2.setYaxisLabel(FormatUtil.getString("am.webclient.mssqldetails.connectiontime"));
/* 3910 */                 int _jspx_eval_awolf_005ftimechart_005f2 = _jspx_th_awolf_005ftimechart_005f2.doStartTag();
/* 3911 */                 if (_jspx_eval_awolf_005ftimechart_005f2 != 0) {
/* 3912 */                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3913 */                     out = _jspx_page_context.pushBody();
/* 3914 */                     _jspx_th_awolf_005ftimechart_005f2.setBodyContent((BodyContent)out);
/* 3915 */                     _jspx_th_awolf_005ftimechart_005f2.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3918 */                     out.write("\n\t        ");
/* 3919 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f2.doAfterBody();
/* 3920 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3923 */                   if (_jspx_eval_awolf_005ftimechart_005f2 != 1) {
/* 3924 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3927 */                 if (_jspx_th_awolf_005ftimechart_005f2.doEndTag() == 5) {
/* 3928 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2); return;
/*      */                 }
/*      */                 
/* 3931 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f2);
/* 3932 */                 out.write("</td>\n  </tr>\n  </table>\n  <td width=\"50%\" height=\"38\" class=\"bottomborder\"> <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3933 */                 out.print(mssqlResourceID);
/* 3934 */                 out.write("&attributeid=3138&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"");
/* 3935 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 3936 */                 out.write("\"></a><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 3937 */                 out.print(mssqlResourceID);
/* 3938 */                 out.write("&attributeid=3138&period=-30',740,550)\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\"  title=\"");
/* 3939 */                 out.print(FormatUtil.getString("am.webclient.common.thirtydays.tooltip.text"));
/* 3940 */                 out.write("\"></a></td>\n  </tr>\n  <tr>\n   ");
/* 3941 */                 mssqlgraph.settype("CACHE");
/* 3942 */                 out.write("\n  <td>");
/*      */                 
/* 3944 */                 TimeChart _jspx_th_awolf_005ftimechart_005f3 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 3945 */                 _jspx_th_awolf_005ftimechart_005f3.setPageContext(_jspx_page_context);
/* 3946 */                 _jspx_th_awolf_005ftimechart_005f3.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                 
/* 3948 */                 _jspx_th_awolf_005ftimechart_005f3.setDataSetProducer("mssqlgraph");
/*      */                 
/* 3950 */                 _jspx_th_awolf_005ftimechart_005f3.setWidth("330");
/*      */                 
/* 3952 */                 _jspx_th_awolf_005ftimechart_005f3.setHeight("170");
/*      */                 
/* 3954 */                 _jspx_th_awolf_005ftimechart_005f3.setLegend("true");
/*      */                 
/* 3956 */                 _jspx_th_awolf_005ftimechart_005f3.setXaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.time"));
/*      */                 
/* 3958 */                 _jspx_th_awolf_005ftimechart_005f3.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/* 3959 */                 int _jspx_eval_awolf_005ftimechart_005f3 = _jspx_th_awolf_005ftimechart_005f3.doStartTag();
/* 3960 */                 if (_jspx_eval_awolf_005ftimechart_005f3 != 0) {
/* 3961 */                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3962 */                     out = _jspx_page_context.pushBody();
/* 3963 */                     _jspx_th_awolf_005ftimechart_005f3.setBodyContent((BodyContent)out);
/* 3964 */                     _jspx_th_awolf_005ftimechart_005f3.doInitBody();
/*      */                   }
/*      */                   for (;;) {
/* 3967 */                     out.write("\n\t        ");
/* 3968 */                     int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f3.doAfterBody();
/* 3969 */                     if (evalDoAfterBody != 2)
/*      */                       break;
/*      */                   }
/* 3972 */                   if (_jspx_eval_awolf_005ftimechart_005f3 != 1) {
/* 3973 */                     out = _jspx_page_context.popBody();
/*      */                   }
/*      */                 }
/* 3976 */                 if (_jspx_th_awolf_005ftimechart_005f3.doEndTag() == 5) {
/* 3977 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3); return;
/*      */                 }
/*      */                 
/* 3980 */                 this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f3);
/* 3981 */                 out.write("</td>\n  </tr>\n  </table></td>\n  <tr>\n  <td valign=\"top\" class=\"rborder\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3982 */                 if (_jspx_meth_fmt_005fmessage_005f6(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3984 */                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3985 */                 if (_jspx_meth_fmt_005fmessage_005f7(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3987 */                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 3988 */                 if (_jspx_meth_fmt_005fmessage_005f8(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 3990 */                 out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 3991 */                 out.print(FormatUtil.getString("Connection Time"));
/* 3992 */                 out.write("</td>\n          <td width=\"27%\" class=\"whitegrayborder\">");
/* 3993 */                 out.print(data.getProperty("CONNECTIONTIME"));
/* 3994 */                 out.write("\n            &nbsp; ");
/* 3995 */                 out.print(FormatUtil.getString("ms"));
/* 3996 */                 out.write("</td>\n          <td width=\"36%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 3997 */                 out.print(mssqlResourceID);
/* 3998 */                 out.write("&attributeid=3102')\">");
/* 3999 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3102")));
/* 4000 */                 out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 4001 */                 out.print(FormatUtil.getString("Active Connections"));
/* 4002 */                 out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4003 */                 out.print(data.getProperty("CONNECTIONS"));
/* 4004 */                 out.write("&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4005 */                 out.print(mssqlResourceID);
/* 4006 */                 out.write("&attributeid=3110&period=-7',740,550)\"><img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"0\" vspace=\"0\" border=\"0\"  title=\"");
/* 4007 */                 out.print(FormatUtil.getString("am.webclient.common.sevendays.tooltip.text"));
/* 4008 */                 out.write("\" align='absmiddle'></a>\n</td>\n\n\t <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4009 */                 out.print(mssqlResourceID);
/* 4010 */                 out.write("&attributeid=3110')\">");
/* 4011 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3110")));
/* 4012 */                 out.write("</a></td>\n\n\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4013 */                 out.print(FormatUtil.getString("Logins/Min"));
/* 4014 */                 out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4015 */                 out.print(data.getProperty("LOGINSPERMIN"));
/* 4016 */                 out.write("</td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4017 */                 out.print(mssqlResourceID);
/* 4018 */                 out.write("&attributeid=3111')\">");
/* 4019 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3111")));
/* 4020 */                 out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"37%\" class=\"yellowgrayborder\">");
/* 4021 */                 out.print(FormatUtil.getString("Logouts/Min"));
/* 4022 */                 out.write(" </td>\n          <td class=\"yellowgrayborder\">");
/* 4023 */                 out.print(data.getProperty("LOGOUTSPERMIN"));
/* 4024 */                 out.write(" </td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4025 */                 out.print(mssqlResourceID);
/* 4026 */                 out.write("&attributeid=3112')\">");
/* 4027 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3112")));
/* 4028 */                 out.write("</a></td>\n        </tr>\n        <tr>\n");
/*      */                 
/* 4030 */                 if (!EnterpriseUtil.isAdminServer())
/*      */                 {
/* 4032 */                   out.write("\n\n          <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4033 */                   out.print(mssqlResourceID);
/* 4034 */                   out.write("&attributeIDs=3102,3110,3111,3112&attributeToSelect=3102&redirectto=");
/* 4035 */                   out.print(redirect);
/* 4036 */                   out.write("'class=\"staticlinks\">");
/* 4037 */                   out.print(ALERTCONFIG_TEXT);
/* 4038 */                   out.write("</a></td>\n");
/*      */                 }
/* 4040 */                 out.write("\n        </tr>\n      </table></td>\n  <td valign=\"top\"> <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\" align=\"center\">\n        <tr>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4041 */                 if (_jspx_meth_fmt_005fmessage_005f9(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 4043 */                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4044 */                 if (_jspx_meth_fmt_005fmessage_005f10(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 4046 */                 out.write("</span></td>\n          <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4047 */                 if (_jspx_meth_fmt_005fmessage_005f11(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                   return;
/* 4049 */                 out.write("</span></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4050 */                 out.print(FormatUtil.getString("Cache Hit Ratio"));
/* 4051 */                 out.write("</td>\n          <td width=\"27%\" class=\"whitegrayborder\">");
/* 4052 */                 out.print(data.getProperty("CACHEHITRATIO"));
/* 4053 */                 out.write("</td>\n          <td width=\"37%\" class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4054 */                 out.print(mssqlResourceID);
/* 4055 */                 out.write("&attributeid=3138')\">");
/* 4056 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3138")));
/* 4057 */                 out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"yellowgrayborder\">");
/* 4058 */                 out.print(FormatUtil.getString("Cache Used/Min"));
/* 4059 */                 out.write("</td>\n          <td class=\"yellowgrayborder\">");
/* 4060 */                 out.print(data.getProperty("CACHEUSEDPERMIN"));
/* 4061 */                 out.write("</td>\n          <td class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4062 */                 out.print(mssqlResourceID);
/* 4063 */                 out.write("&attributeid=3139')\">");
/* 4064 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3139")));
/* 4065 */                 out.write("</a></td>\n        </tr>\n        <tr>\n          <td class=\"whitegrayborder\">");
/* 4066 */                 out.print(FormatUtil.getString("Cache Count"));
/* 4067 */                 out.write("</td>\n          <td class=\"whitegrayborder\">");
/* 4068 */                 out.print(data.getProperty("CACHECOUNT"));
/* 4069 */                 out.write("</td>\n          <td class=\"whitegrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4070 */                 out.print(mssqlResourceID);
/* 4071 */                 out.write("&attributeid=3140')\">");
/* 4072 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3140")));
/* 4073 */                 out.write("</a></td>\n        </tr>\n        <tr>\n          <td width=\"36%\" class=\"yellowgrayborder\">");
/* 4074 */                 out.print(FormatUtil.getString("Cache Pages"));
/* 4075 */                 out.write("</td>\n          <td  class=\"yellowgrayborder\">");
/* 4076 */                 out.print(data.getProperty("CACHEPAGES"));
/* 4077 */                 out.write("&nbsp;</td>\n          <td  class=\"yellowgrayborder\">&nbsp;<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4078 */                 out.print(mssqlResourceID);
/* 4079 */                 out.write("&attributeid=3141')\">");
/* 4080 */                 out.print(getSeverityImage(alert.getProperty(mssqlResourceID + "#" + "3141")));
/* 4081 */                 out.write("</a></td>\n        </tr>\n        <tr>\n");
/*      */                 
/* 4083 */                 if (!EnterpriseUtil.isAdminServer())
/*      */                 {
/* 4085 */                   out.write("\n\n          <td colspan=\"3\" align=\"right\" class=\"whitegrayborder\"><img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href='/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4086 */                   out.print(mssqlResourceID);
/* 4087 */                   out.write("&attributeIDs=3138,3139,3140,3141&attributeToSelect=3138&redirectto=");
/* 4088 */                   out.print(redirect);
/* 4089 */                   out.write("'class=\"staticlinks\">");
/* 4090 */                   out.print(ALERTCONFIG_TEXT);
/* 4091 */                   out.write("</a></td>\n");
/*      */                 }
/* 4093 */                 out.write("\n        </tr>\n      </table></td>\n  </tr>\n  </table>\n\n\n\n\n");
/*      */               }
/* 4095 */               out.write("\n\n <table width=\"95%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n  <td>&nbsp;</td>\n  </tr>\n  </table>\n\n\n\n");
/* 4096 */               if (!hostresourceid.equals("-1")) {
/* 4097 */                 out.write("\n<!-- HOST INFO -->\n");
/*      */                 
/* 4099 */                 boolean cpudata = true;
/* 4100 */                 if (cpudata)
/*      */                 {
/* 4102 */                   cpugraph.setresourceName(hostresourcename);
/* 4103 */                   cpugraph.setCategory("CPU Utilization");
/* 4104 */                   cpugraph.setEntity("CPUUtilization");
/* 4105 */                   cpugraph.setResourceId(hostresourceid);
/* 4106 */                   Properties cpuprops = cpugraph.getCpuData(hostresourcename, hostmaxcollectiontime, hostresourceid);
/*      */                   
/*      */ 
/* 4109 */                   out.write("\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\"  class=\"lrtborder\" align=\"center\">\n  <tr>\n    <td width=\"50%\" height=\"29\" class=\"tableheadingbborder\" >");
/* 4110 */                   out.print(FormatUtil.getString("am.webclient.support.systemcpu"));
/* 4111 */                   out.write(" -\n      ");
/* 4112 */                   out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4113 */                   out.write("<a name=\"CPU Utilization\"></a> </td>\n    <td width=\"50%\" height=\"29\" class=\"tableheadingbborder\">");
/* 4114 */                   out.print(FormatUtil.getString("am.webclient.support.systemresponse"));
/* 4115 */                   out.write(32);
/* 4116 */                   out.write(45);
/* 4117 */                   out.write(32);
/* 4118 */                   out.print(FormatUtil.getString("am.webclient.common.lastonehour.text"));
/* 4119 */                   out.write("&nbsp;</td>\n  </tr>\n</table>\n<table width=\"98%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"lrbborder\" height=\"200\" align=\"center\">\n  <tr>\n    <td  class=\"rbborder\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"80%\">\n        <tr>\n          <td width=\"94%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4120 */                   out.print(hostresourceid);
/* 4121 */                   out.write("&attributeid=");
/* 4122 */                   out.print(ids.getProperty("CPU Utilization"));
/* 4123 */                   out.write("&period=-7')\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last seven days data\"></a></td>\n          <td width=\"6%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4124 */                   out.print(hostresourceid);
/* 4125 */                   out.write("&attributeid=");
/* 4126 */                   out.print(ids.getProperty("CPU Utilization"));
/* 4127 */                   out.write("&period=-30')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thirty days data\"></a></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                   
/* 4129 */                   TimeChart _jspx_th_awolf_005ftimechart_005f4 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4130 */                   _jspx_th_awolf_005ftimechart_005f4.setPageContext(_jspx_page_context);
/* 4131 */                   _jspx_th_awolf_005ftimechart_005f4.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 4133 */                   _jspx_th_awolf_005ftimechart_005f4.setDataSetProducer("cpugraph");
/*      */                   
/* 4135 */                   _jspx_th_awolf_005ftimechart_005f4.setWidth("400");
/*      */                   
/* 4137 */                   _jspx_th_awolf_005ftimechart_005f4.setHeight("170");
/*      */                   
/* 4139 */                   _jspx_th_awolf_005ftimechart_005f4.setLegend("false");
/*      */                   
/* 4141 */                   _jspx_th_awolf_005ftimechart_005f4.setXaxisLabel(xaxis_time);
/*      */                   
/* 4143 */                   _jspx_th_awolf_005ftimechart_005f4.setYaxisLabel(FormatUtil.getString("am.webclient.common.axisname.valueinper.text"));
/* 4144 */                   int _jspx_eval_awolf_005ftimechart_005f4 = _jspx_th_awolf_005ftimechart_005f4.doStartTag();
/* 4145 */                   if (_jspx_eval_awolf_005ftimechart_005f4 != 0) {
/* 4146 */                     if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 4147 */                       out = _jspx_page_context.pushBody();
/* 4148 */                       _jspx_th_awolf_005ftimechart_005f4.setBodyContent((BodyContent)out);
/* 4149 */                       _jspx_th_awolf_005ftimechart_005f4.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 4152 */                       out.write("\n            ");
/* 4153 */                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f4.doAfterBody();
/* 4154 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 4157 */                     if (_jspx_eval_awolf_005ftimechart_005f4 != 1) {
/* 4158 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 4161 */                   if (_jspx_th_awolf_005ftimechart_005f4.doEndTag() == 5) {
/* 4162 */                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4); return;
/*      */                   }
/*      */                   
/* 4165 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f4);
/* 4166 */                   out.write(" </td>\n        </tr>\n      </table></td>\n    <td width=\"50%\" valign=\"top\" class=\"bottomborder\"> <table  cellspacing=\"0\" cellpadding=\"0\" border=\"0\">\n        <tr>\n          <td width=\"94%\" align=\"right\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4167 */                   out.print(hostresourceid);
/* 4168 */                   out.write("&attributeid=");
/* 4169 */                   out.print(ids.getProperty("Response Time"));
/* 4170 */                   out.write("&period=-7')\">\n            <img src=\"../images/icon_7daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last seven days data\"></a></td>\n          <td width=\"6%\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('../showHistoryData.do?method=getData&resourceid=");
/* 4171 */                   out.print(hostresourceid);
/* 4172 */                   out.write("&attributeid=");
/* 4173 */                   out.print(ids.getProperty("Response Time"));
/* 4174 */                   out.write("&period=-30')\"><img src=\"../images/icon_30daysdata.gif\" width=\"24\" height=\"16\" hspace=\"5\" vspace=\"5\" border=\"0\" title=\"Click to view last thirty days data\"></a></td>\n        </tr>\n        <tr>\n          <td colspan=\"2\"> ");
/*      */                   
/* 4176 */                   TimeChart _jspx_th_awolf_005ftimechart_005f5 = (TimeChart)this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.get(TimeChart.class);
/* 4177 */                   _jspx_th_awolf_005ftimechart_005f5.setPageContext(_jspx_page_context);
/* 4178 */                   _jspx_th_awolf_005ftimechart_005f5.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 4180 */                   _jspx_th_awolf_005ftimechart_005f5.setDataSetProducer("perfgraph");
/*      */                   
/* 4182 */                   _jspx_th_awolf_005ftimechart_005f5.setWidth("400");
/*      */                   
/* 4184 */                   _jspx_th_awolf_005ftimechart_005f5.setHeight("170");
/*      */                   
/* 4186 */                   _jspx_th_awolf_005ftimechart_005f5.setLegend("false");
/*      */                   
/* 4188 */                   _jspx_th_awolf_005ftimechart_005f5.setXaxisLabel(xaxis_time);
/*      */                   
/* 4190 */                   _jspx_th_awolf_005ftimechart_005f5.setYaxisLabel(FormatUtil.getString("am.webclient.tomacatdetail.responsetimeinms"));
/* 4191 */                   int _jspx_eval_awolf_005ftimechart_005f5 = _jspx_th_awolf_005ftimechart_005f5.doStartTag();
/* 4192 */                   if (_jspx_eval_awolf_005ftimechart_005f5 != 0) {
/* 4193 */                     if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 4194 */                       out = _jspx_page_context.pushBody();
/* 4195 */                       _jspx_th_awolf_005ftimechart_005f5.setBodyContent((BodyContent)out);
/* 4196 */                       _jspx_th_awolf_005ftimechart_005f5.doInitBody();
/*      */                     }
/*      */                     for (;;) {
/* 4199 */                       out.write("\n            ");
/* 4200 */                       int evalDoAfterBody = _jspx_th_awolf_005ftimechart_005f5.doAfterBody();
/* 4201 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/* 4204 */                     if (_jspx_eval_awolf_005ftimechart_005f5 != 1) {
/* 4205 */                       out = _jspx_page_context.popBody();
/*      */                     }
/*      */                   }
/* 4208 */                   if (_jspx_th_awolf_005ftimechart_005f5.doEndTag() == 5) {
/* 4209 */                     this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5); return;
/*      */                   }
/*      */                   
/* 4212 */                   this._005fjspx_005ftagPool_005fawolf_005ftimechart_0026_005fyaxisLabel_005fxaxisLabel_005fwidth_005flegend_005fheight_005fdataSetProducer.reuse(_jspx_th_awolf_005ftimechart_005f5);
/* 4213 */                   out.write(" </td>\n        </tr>\n      </table></td>\n  </tr><tr>\n  <td class=\"rborder\" valign=\"top\">\n    ");
/*      */                   
/* 4215 */                   if (cpuprops.getProperty("CURVALUE") != null)
/*      */                   {
/*      */ 
/* 4218 */                     out.write("\n    <table width=\"100%\" border=\"0\" align=center cellpadding=\"0\" cellspacing=\"0\" >\n      <tr>\n        <td width=\"50%\" height=\"22\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4219 */                     if (_jspx_meth_fmt_005fmessage_005f12(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/* 4221 */                     out.write("</span>\n        </td>\n        <td width=\"25%\" height=\"22\" class=\"columnheadingnotop\"> <span class=\"bodytextbold\">");
/* 4222 */                     if (_jspx_meth_fmt_005fmessage_005f13(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/* 4224 */                     out.write("</span></td>\n        <td width=\"25%\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4225 */                     if (_jspx_meth_fmt_005fmessage_005f14(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                       return;
/* 4227 */                     out.write("</span></td>\n      </tr>\n      <tr>\n        <td height=\"50\" class=\"whitegrayborder\">");
/* 4228 */                     out.print(FormatUtil.getString("Current Value"));
/* 4229 */                     out.write("</td>\n        <td width=\"25%\" height=\"22\" class=\"whitegrayborder\">");
/* 4230 */                     out.print(formatNumber(cpuprops.getProperty("CURVALUE")));
/* 4231 */                     out.write("%</td>\n        <td width=\"25%\" class=\"whitegrayborder\"><a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4232 */                     out.print(hostresourceid);
/* 4233 */                     out.write("&attributeid=");
/* 4234 */                     out.print(ids.getProperty("CPU Utilization"));
/* 4235 */                     out.write("')\">");
/* 4236 */                     out.print(getSeverityImage(alert.getProperty(hostresourceid + "#" + ids.getProperty("CPU Utilization"))));
/* 4237 */                     out.write("</a></td>\n      </tr>\n      <tr>\n        <td height=\"22\" class=\"yellowgrayborder\">");
/* 4238 */                     out.print(FormatUtil.getString("Peak Value"));
/* 4239 */                     out.write(" </td>\n        <td height=\"22\" class=\"yellowgrayborder\">");
/* 4240 */                     out.print(formatNumber(cpuprops.getProperty("MAXVALUE")));
/* 4241 */                     out.write("%</td>\n        <td class=\"yellowgrayborder\">&nbsp;</td>\n      </tr>\n\t  ");
/*      */                     
/* 4243 */                     if (!EnterpriseUtil.isAdminServer())
/*      */                     {
/*      */ 
/* 4246 */                       out.write("\n      <tr align=\"right\">\n        <td height=\"32\" colspan=\"3\" class=\"bodytext\"> <img src=\"../images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4247 */                       out.print(hostresourceid);
/* 4248 */                       out.write("&attributeIDs=");
/* 4249 */                       out.print(ids.getProperty("CPU Utilization"));
/* 4250 */                       out.write("&attributeToSelect=");
/* 4251 */                       out.print(ids.getProperty("CPU Utilization"));
/* 4252 */                       out.write("&redirectto=");
/* 4253 */                       out.print(redirect);
/* 4254 */                       out.write("\" class=\"staticlinks\">");
/* 4255 */                       out.print(ALERTCONFIG_TEXT);
/* 4256 */                       out.write("</a>&nbsp;</td>\n      </tr>\n\t  ");
/*      */                     }
/* 4258 */                     out.write("\n    </table>\n    ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/*      */ 
/* 4264 */                     out.write("\n    <table width=\"100%\" border=\"0\" align=center cellpadding=\"0\" cellspacing=\"0\" >\n      <tr>\n        <td width=\"60%\" height=\"22\" class=\"columnheadingnotop\">");
/* 4265 */                     out.print(FormatUtil.getString("am.webclient.hostResource.servers.cpumet"));
/* 4266 */                     out.write("\n        </td>\n        <td width=\"40%\" height=\"22\" class=\"columnheadingnotop\">");
/* 4267 */                     out.print(FormatUtil.getString("table.heading.value"));
/* 4268 */                     out.write("</td>\n      </tr>\n      <tr>\n        <td width=\"40%\" height=\"22\" class=\"whitegrayborder\" colspan=\"2\" align=\"center\">\n          ");
/* 4269 */                     out.print(FormatUtil.getString("am.webclient.nodata.text"));
/* 4270 */                     out.write("</td>\n      </tr>\n    </table>\n    ");
/*      */                   }
/*      */                   
/*      */ 
/* 4274 */                   out.write("\n  </td><td valign=\"top\">\n  <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" >\n    <tr>\n      <td height=\"22\" class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4275 */                   if (_jspx_meth_fmt_005fmessage_005f15(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 4277 */                   out.write("</span>\n      </td>\n      <td height=\"22\" class=\"columnheadingnotop\"> <span class=\"bodytextbold\">");
/* 4278 */                   if (_jspx_meth_fmt_005fmessage_005f16(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 4280 */                   out.write("</span></td>\n      <td class=\"columnheadingnotop\"><span class=\"bodytextbold\">");
/* 4281 */                   if (_jspx_meth_fmt_005fmessage_005f17(_jspx_th_tiles_005fput_005f3, _jspx_page_context))
/*      */                     return;
/* 4283 */                   out.write("</span></td>\n    </tr>\n    <tbody>\n      <tr>\n        <td width=\"50%\" height=\"19\" class=\"whitegrayborder\" >");
/* 4284 */                   out.print(FormatUtil.getString("Current Response Time"));
/* 4285 */                   out.write(" </td>\n        <td width=\"25%\" height=\"19\" class=\"whitegrayborder\">\n          ");
/*      */                   
/* 4287 */                   if (perfgraph.getResponseTime(Integer.parseInt(hostresourceid)) != -1L)
/*      */                   {
/* 4289 */                     out.write("\n          ");
/* 4290 */                     out.print(formatNumber(perfgraph.getResponseTime(Integer.parseInt(hostresourceid))));
/* 4291 */                     out.write("\n          ");
/* 4292 */                     out.print(FormatUtil.getString("ms"));
/* 4293 */                     out.write("\n          ");
/*      */ 
/*      */                   }
/*      */                   else
/*      */                   {
/* 4298 */                     out.write("\n          -\n          ");
/*      */                   }
/*      */                   
/*      */ 
/* 4302 */                   out.write("\n        </td>\n        <td class=\"whitegrayborder\">\n          ");
/*      */                   
/* 4304 */                   int resTimeStatus = -1;
/* 4305 */                   if (alert.getProperty("Response Time") != null)
/*      */                   {
/* 4307 */                     resTimeStatus = Integer.parseInt(alert.getProperty("Response Time"));
/*      */                   }
/*      */                   
/* 4310 */                   out.write("\n          ");
/*      */                   
/* 4312 */                   IfTag _jspx_th_c_005fif_005f17 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4313 */                   _jspx_th_c_005fif_005f17.setPageContext(_jspx_page_context);
/* 4314 */                   _jspx_th_c_005fif_005f17.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                   
/* 4316 */                   _jspx_th_c_005fif_005f17.setTest("${hostresourcename!=''}");
/* 4317 */                   int _jspx_eval_c_005fif_005f17 = _jspx_th_c_005fif_005f17.doStartTag();
/* 4318 */                   if (_jspx_eval_c_005fif_005f17 != 0) {
/*      */                     for (;;) {
/* 4320 */                       out.write("<a href=\"javascript:void(0)\" onClick=\"fnOpenNewWindow('/jsp/RCA.jsp?resourceid=");
/* 4321 */                       out.print(hostresourceid);
/* 4322 */                       out.write("&attributeid=");
/* 4323 */                       out.print(ids.getProperty("Response Time"));
/* 4324 */                       out.write("')\">");
/* 4325 */                       out.print(getSeverityImage(alert.getProperty(hostresourceid + "#" + ids.getProperty("Response Time"))));
/* 4326 */                       out.write("</a>");
/* 4327 */                       int evalDoAfterBody = _jspx_th_c_005fif_005f17.doAfterBody();
/* 4328 */                       if (evalDoAfterBody != 2)
/*      */                         break;
/*      */                     }
/*      */                   }
/* 4332 */                   if (_jspx_th_c_005fif_005f17.doEndTag() == 5) {
/* 4333 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17); return;
/*      */                   }
/*      */                   
/* 4336 */                   this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f17);
/* 4337 */                   out.write("\n        </td>\n      </tr>\n\t  ");
/*      */                   
/* 4339 */                   if (!EnterpriseUtil.isAdminServer())
/*      */                   {
/*      */ 
/* 4342 */                     out.write("\n      <tr>\n        <td height=\"32\" colspan=\"3\" class=\"yellowgrayborder\" align=\"right\"> ");
/*      */                     
/* 4344 */                     IfTag _jspx_th_c_005fif_005f18 = (IfTag)this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.get(IfTag.class);
/* 4345 */                     _jspx_th_c_005fif_005f18.setPageContext(_jspx_page_context);
/* 4346 */                     _jspx_th_c_005fif_005f18.setParent(_jspx_th_tiles_005fput_005f3);
/*      */                     
/* 4348 */                     _jspx_th_c_005fif_005f18.setTest("${hostresourcename!=''}");
/* 4349 */                     int _jspx_eval_c_005fif_005f18 = _jspx_th_c_005fif_005f18.doStartTag();
/* 4350 */                     if (_jspx_eval_c_005fif_005f18 != 0) {
/*      */                       for (;;) {
/* 4352 */                         out.write("<img src=\"/images/icon_associateaction.gif\" align=\"absmiddle\" >&nbsp;<a href=\"/jsp/ThresholdActionConfiguration.jsp?resourceid=");
/* 4353 */                         out.print(hostresourceid);
/* 4354 */                         out.write("&attributeIDs=");
/* 4355 */                         out.print(ids.getProperty("Response Time"));
/* 4356 */                         out.write("&attributeToSelect=");
/* 4357 */                         out.print(ids.getProperty("Response Time"));
/* 4358 */                         out.write("&redirectto=");
/* 4359 */                         out.print(redirect);
/* 4360 */                         out.write("\" class=\"staticlinks\">");
/* 4361 */                         out.print(ALERTCONFIG_TEXT);
/* 4362 */                         out.write("</a>");
/* 4363 */                         int evalDoAfterBody = _jspx_th_c_005fif_005f18.doAfterBody();
/* 4364 */                         if (evalDoAfterBody != 2)
/*      */                           break;
/*      */                       }
/*      */                     }
/* 4368 */                     if (_jspx_th_c_005fif_005f18.doEndTag() == 5) {
/* 4369 */                       this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18); return;
/*      */                     }
/*      */                     
/* 4372 */                     this._005fjspx_005ftagPool_005fc_005fif_0026_005ftest.reuse(_jspx_th_c_005fif_005f18);
/* 4373 */                     out.write("&nbsp;</td></td></tr>\n     ");
/*      */                   }
/* 4375 */                   out.write("\n\t</tbody>\n  </table></td></tr>\n</table>\n<table width=\"99%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n  <tr>\n    <td>&nbsp;</td>\n  </tr>\n</table>\n");
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/* 4381 */                 out.write("\n<!-- HOST INFO END -->\n");
/*      */               }
/* 4383 */               out.write("\n\n</td>\n</tr>\n</table>\n\n\n\n</td>\n\n<!-- Inner tabel ends!-->\n\n<td class=\"vcenter-shadow-rigtile\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n</tr>\n<tr>\n<td class=\"vcenter-shadow-btm-lcorn\" width=\"8\"><img src=\"/images/spacer.gif\" width=\"8\" /></td>\n<td class=\"vcenter-shadow-btm-mtile\"></td>\n<td class=\"vcenter-shadow-btm-rcorn\" width=\"7\"><img src=\"/images/spacer.gif\" width=\"7\" /></td>\n</tr>\n</table>\n\n");
/* 4384 */               int evalDoAfterBody = _jspx_th_tiles_005fput_005f3.doAfterBody();
/* 4385 */               if (evalDoAfterBody != 2)
/*      */                 break;
/*      */             }
/* 4388 */             if (_jspx_eval_tiles_005fput_005f3 != 1) {
/* 4389 */               out = _jspx_page_context.popBody();
/*      */             }
/*      */           }
/* 4392 */           if (_jspx_th_tiles_005fput_005f3.doEndTag() == 5) {
/* 4393 */             this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3); return;
/*      */           }
/*      */           
/* 4396 */           this._005fjspx_005ftagPool_005ftiles_005fput_0026_005ftype_005fname.reuse(_jspx_th_tiles_005fput_005f3);
/* 4397 */           out.write(32);
/* 4398 */           if (_jspx_meth_tiles_005fput_005f4(_jspx_th_tiles_005finsert_005f0, _jspx_page_context))
/*      */             return;
/* 4400 */           out.write(10);
/* 4401 */           int evalDoAfterBody = _jspx_th_tiles_005finsert_005f0.doAfterBody();
/* 4402 */           if (evalDoAfterBody != 2)
/*      */             break;
/*      */         }
/*      */       }
/* 4406 */       if (_jspx_th_tiles_005finsert_005f0.doEndTag() == 5) {
/* 4407 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/*      */       }
/*      */       else {
/* 4410 */         this._005fjspx_005ftagPool_005ftiles_005finsert_0026_005fpage.reuse(_jspx_th_tiles_005finsert_005f0);
/* 4411 */         out.write(10);
/*      */       }
/* 4413 */     } catch (Throwable t) { if (!(t instanceof javax.servlet.jsp.SkipPageException)) {
/* 4414 */         out = _jspx_out;
/* 4415 */         if ((out != null) && (out.getBufferSize() != 0))
/* 4416 */           try { out.clearBuffer(); } catch (java.io.IOException e) {}
/* 4417 */         if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
/*      */       }
/*      */     } finally {
/* 4420 */       _jspxFactory.releasePageContext(_jspx_page_context);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f1(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4426 */     PageContext pageContext = _jspx_page_context;
/* 4427 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4429 */     PutTag _jspx_th_tiles_005fput_005f1 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 4430 */     _jspx_th_tiles_005fput_005f1.setPageContext(_jspx_page_context);
/* 4431 */     _jspx_th_tiles_005fput_005f1.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 4433 */     _jspx_th_tiles_005fput_005f1.setName("Header");
/*      */     
/* 4435 */     _jspx_th_tiles_005fput_005f1.setValue("/jsp/header.jsp?tabtoselect=6");
/* 4436 */     int _jspx_eval_tiles_005fput_005f1 = _jspx_th_tiles_005fput_005f1.doStartTag();
/* 4437 */     if (_jspx_th_tiles_005fput_005f1.doEndTag() == 5) {
/* 4438 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4439 */       return true;
/*      */     }
/* 4441 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f1);
/* 4442 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f0(JspTag _jspx_th_tiles_005fput_005f2, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4447 */     PageContext pageContext = _jspx_page_context;
/* 4448 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4450 */     OutTag _jspx_th_c_005fout_005f0 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.get(OutTag.class);
/* 4451 */     _jspx_th_c_005fout_005f0.setPageContext(_jspx_page_context);
/* 4452 */     _jspx_th_c_005fout_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f2);
/*      */     
/* 4454 */     _jspx_th_c_005fout_005f0.setValue("${selectedskin}");
/*      */     
/* 4456 */     _jspx_th_c_005fout_005f0.setDefault("${initParam.defaultSkin}");
/* 4457 */     int _jspx_eval_c_005fout_005f0 = _jspx_th_c_005fout_005f0.doStartTag();
/* 4458 */     if (_jspx_th_c_005fout_005f0.doEndTag() == 5) {
/* 4459 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4460 */       return true;
/*      */     }
/* 4462 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fdefault_005fnobody.reuse(_jspx_th_c_005fout_005f0);
/* 4463 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4468 */     PageContext pageContext = _jspx_page_context;
/* 4469 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4471 */     PresentTag _jspx_th_logic_005fpresent_005f0 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4472 */     _jspx_th_logic_005fpresent_005f0.setPageContext(_jspx_page_context);
/* 4473 */     _jspx_th_logic_005fpresent_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4475 */     _jspx_th_logic_005fpresent_005f0.setRole("ADMIN,ENTERPRISEADMIN,OPERATOR");
/* 4476 */     int _jspx_eval_logic_005fpresent_005f0 = _jspx_th_logic_005fpresent_005f0.doStartTag();
/* 4477 */     if (_jspx_eval_logic_005fpresent_005f0 != 0) {
/*      */       for (;;) {
/* 4479 */         out.write("href=\"javascript:ShowProcessingMessage();\"");
/* 4480 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f0.doAfterBody();
/* 4481 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4485 */     if (_jspx_th_logic_005fpresent_005f0.doEndTag() == 5) {
/* 4486 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4487 */       return true;
/*      */     }
/* 4489 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f0);
/* 4490 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_logic_005fpresent_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4495 */     PageContext pageContext = _jspx_page_context;
/* 4496 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4498 */     PresentTag _jspx_th_logic_005fpresent_005f1 = (PresentTag)this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.get(PresentTag.class);
/* 4499 */     _jspx_th_logic_005fpresent_005f1.setPageContext(_jspx_page_context);
/* 4500 */     _jspx_th_logic_005fpresent_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4502 */     _jspx_th_logic_005fpresent_005f1.setRole("ADMIN,ENTERPRISEADMIN,OPERATOR");
/* 4503 */     int _jspx_eval_logic_005fpresent_005f1 = _jspx_th_logic_005fpresent_005f1.doStartTag();
/* 4504 */     if (_jspx_eval_logic_005fpresent_005f1 != 0) {
/*      */       for (;;) {
/* 4506 */         out.write("href=\"javascript:ShowProcessingMessage();\"");
/* 4507 */         int evalDoAfterBody = _jspx_th_logic_005fpresent_005f1.doAfterBody();
/* 4508 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/*      */     }
/* 4512 */     if (_jspx_th_logic_005fpresent_005f1.doEndTag() == 5) {
/* 4513 */       this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4514 */       return true;
/*      */     }
/* 4516 */     this._005fjspx_005ftagPool_005flogic_005fpresent_0026_005frole.reuse(_jspx_th_logic_005fpresent_005f1);
/* 4517 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fset_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4522 */     PageContext pageContext = _jspx_page_context;
/* 4523 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4525 */     SetTag _jspx_th_c_005fset_005f0 = (SetTag)this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(SetTag.class);
/* 4526 */     _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
/* 4527 */     _jspx_th_c_005fset_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/*      */     
/* 4529 */     _jspx_th_c_005fset_005f0.setVar("databaseMsSQL");
/*      */     
/* 4531 */     _jspx_th_c_005fset_005f0.setValue("true");
/* 4532 */     int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
/* 4533 */     if (_jspx_th_c_005fset_005f0.doEndTag() == 5) {
/* 4534 */       this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4535 */       return true;
/*      */     }
/* 4537 */     this._005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
/* 4538 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f1(JspTag _jspx_th_c_005fif_005f10, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4543 */     PageContext pageContext = _jspx_page_context;
/* 4544 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4546 */     OutTag _jspx_th_c_005fout_005f1 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4547 */     _jspx_th_c_005fout_005f1.setPageContext(_jspx_page_context);
/* 4548 */     _jspx_th_c_005fout_005f1.setParent((Tag)_jspx_th_c_005fif_005f10);
/*      */     
/* 4550 */     _jspx_th_c_005fout_005f1.setValue("${databasePort}");
/* 4551 */     int _jspx_eval_c_005fout_005f1 = _jspx_th_c_005fout_005f1.doStartTag();
/* 4552 */     if (_jspx_th_c_005fout_005f1.doEndTag() == 5) {
/* 4553 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4554 */       return true;
/*      */     }
/* 4556 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f1);
/* 4557 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_c_005fout_005f2(JspTag _jspx_th_c_005fif_005f11, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4562 */     PageContext pageContext = _jspx_page_context;
/* 4563 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4565 */     OutTag _jspx_th_c_005fout_005f2 = (OutTag)this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.get(OutTag.class);
/* 4566 */     _jspx_th_c_005fout_005f2.setPageContext(_jspx_page_context);
/* 4567 */     _jspx_th_c_005fout_005f2.setParent((Tag)_jspx_th_c_005fif_005f11);
/*      */     
/* 4569 */     _jspx_th_c_005fout_005f2.setValue("${databaseVersion}");
/* 4570 */     int _jspx_eval_c_005fout_005f2 = _jspx_th_c_005fout_005f2.doStartTag();
/* 4571 */     if (_jspx_th_c_005fout_005f2.doEndTag() == 5) {
/* 4572 */       this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4573 */       return true;
/*      */     }
/* 4575 */     this._005fjspx_005ftagPool_005fc_005fout_0026_005fvalue_005fnobody.reuse(_jspx_th_c_005fout_005f2);
/* 4576 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f0(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4581 */     PageContext pageContext = _jspx_page_context;
/* 4582 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4584 */     MessageTag _jspx_th_fmt_005fmessage_005f0 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4585 */     _jspx_th_fmt_005fmessage_005f0.setPageContext(_jspx_page_context);
/* 4586 */     _jspx_th_fmt_005fmessage_005f0.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4587 */     int _jspx_eval_fmt_005fmessage_005f0 = _jspx_th_fmt_005fmessage_005f0.doStartTag();
/* 4588 */     if (_jspx_eval_fmt_005fmessage_005f0 != 0) {
/* 4589 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4590 */         out = _jspx_page_context.pushBody();
/* 4591 */         _jspx_th_fmt_005fmessage_005f0.setBodyContent((BodyContent)out);
/* 4592 */         _jspx_th_fmt_005fmessage_005f0.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4595 */         out.write("table.heading.attribute");
/* 4596 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f0.doAfterBody();
/* 4597 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4600 */       if (_jspx_eval_fmt_005fmessage_005f0 != 1) {
/* 4601 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4604 */     if (_jspx_th_fmt_005fmessage_005f0.doEndTag() == 5) {
/* 4605 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4606 */       return true;
/*      */     }
/* 4608 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f0);
/* 4609 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f1(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4614 */     PageContext pageContext = _jspx_page_context;
/* 4615 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4617 */     MessageTag _jspx_th_fmt_005fmessage_005f1 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4618 */     _jspx_th_fmt_005fmessage_005f1.setPageContext(_jspx_page_context);
/* 4619 */     _jspx_th_fmt_005fmessage_005f1.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4620 */     int _jspx_eval_fmt_005fmessage_005f1 = _jspx_th_fmt_005fmessage_005f1.doStartTag();
/* 4621 */     if (_jspx_eval_fmt_005fmessage_005f1 != 0) {
/* 4622 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4623 */         out = _jspx_page_context.pushBody();
/* 4624 */         _jspx_th_fmt_005fmessage_005f1.setBodyContent((BodyContent)out);
/* 4625 */         _jspx_th_fmt_005fmessage_005f1.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4628 */         out.write("table.heading.value");
/* 4629 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f1.doAfterBody();
/* 4630 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4633 */       if (_jspx_eval_fmt_005fmessage_005f1 != 1) {
/* 4634 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4637 */     if (_jspx_th_fmt_005fmessage_005f1.doEndTag() == 5) {
/* 4638 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4639 */       return true;
/*      */     }
/* 4641 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f1);
/* 4642 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f2(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4647 */     PageContext pageContext = _jspx_page_context;
/* 4648 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4650 */     MessageTag _jspx_th_fmt_005fmessage_005f2 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4651 */     _jspx_th_fmt_005fmessage_005f2.setPageContext(_jspx_page_context);
/* 4652 */     _jspx_th_fmt_005fmessage_005f2.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4653 */     int _jspx_eval_fmt_005fmessage_005f2 = _jspx_th_fmt_005fmessage_005f2.doStartTag();
/* 4654 */     if (_jspx_eval_fmt_005fmessage_005f2 != 0) {
/* 4655 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4656 */         out = _jspx_page_context.pushBody();
/* 4657 */         _jspx_th_fmt_005fmessage_005f2.setBodyContent((BodyContent)out);
/* 4658 */         _jspx_th_fmt_005fmessage_005f2.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4661 */         out.write("table.heading.status");
/* 4662 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f2.doAfterBody();
/* 4663 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4666 */       if (_jspx_eval_fmt_005fmessage_005f2 != 1) {
/* 4667 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4670 */     if (_jspx_th_fmt_005fmessage_005f2.doEndTag() == 5) {
/* 4671 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4672 */       return true;
/*      */     }
/* 4674 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f2);
/* 4675 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f3(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4680 */     PageContext pageContext = _jspx_page_context;
/* 4681 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4683 */     MessageTag _jspx_th_fmt_005fmessage_005f3 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4684 */     _jspx_th_fmt_005fmessage_005f3.setPageContext(_jspx_page_context);
/* 4685 */     _jspx_th_fmt_005fmessage_005f3.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4686 */     int _jspx_eval_fmt_005fmessage_005f3 = _jspx_th_fmt_005fmessage_005f3.doStartTag();
/* 4687 */     if (_jspx_eval_fmt_005fmessage_005f3 != 0) {
/* 4688 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4689 */         out = _jspx_page_context.pushBody();
/* 4690 */         _jspx_th_fmt_005fmessage_005f3.setBodyContent((BodyContent)out);
/* 4691 */         _jspx_th_fmt_005fmessage_005f3.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4694 */         out.write("table.heading.attribute");
/* 4695 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f3.doAfterBody();
/* 4696 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4699 */       if (_jspx_eval_fmt_005fmessage_005f3 != 1) {
/* 4700 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4703 */     if (_jspx_th_fmt_005fmessage_005f3.doEndTag() == 5) {
/* 4704 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4705 */       return true;
/*      */     }
/* 4707 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f3);
/* 4708 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f4(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4713 */     PageContext pageContext = _jspx_page_context;
/* 4714 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4716 */     MessageTag _jspx_th_fmt_005fmessage_005f4 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4717 */     _jspx_th_fmt_005fmessage_005f4.setPageContext(_jspx_page_context);
/* 4718 */     _jspx_th_fmt_005fmessage_005f4.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4719 */     int _jspx_eval_fmt_005fmessage_005f4 = _jspx_th_fmt_005fmessage_005f4.doStartTag();
/* 4720 */     if (_jspx_eval_fmt_005fmessage_005f4 != 0) {
/* 4721 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 4722 */         out = _jspx_page_context.pushBody();
/* 4723 */         _jspx_th_fmt_005fmessage_005f4.setBodyContent((BodyContent)out);
/* 4724 */         _jspx_th_fmt_005fmessage_005f4.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4727 */         out.write("table.heading.value");
/* 4728 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f4.doAfterBody();
/* 4729 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4732 */       if (_jspx_eval_fmt_005fmessage_005f4 != 1) {
/* 4733 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4736 */     if (_jspx_th_fmt_005fmessage_005f4.doEndTag() == 5) {
/* 4737 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4738 */       return true;
/*      */     }
/* 4740 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f4);
/* 4741 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f5(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4746 */     PageContext pageContext = _jspx_page_context;
/* 4747 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4749 */     MessageTag _jspx_th_fmt_005fmessage_005f5 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4750 */     _jspx_th_fmt_005fmessage_005f5.setPageContext(_jspx_page_context);
/* 4751 */     _jspx_th_fmt_005fmessage_005f5.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4752 */     int _jspx_eval_fmt_005fmessage_005f5 = _jspx_th_fmt_005fmessage_005f5.doStartTag();
/* 4753 */     if (_jspx_eval_fmt_005fmessage_005f5 != 0) {
/* 4754 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 4755 */         out = _jspx_page_context.pushBody();
/* 4756 */         _jspx_th_fmt_005fmessage_005f5.setBodyContent((BodyContent)out);
/* 4757 */         _jspx_th_fmt_005fmessage_005f5.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4760 */         out.write("table.heading.status");
/* 4761 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f5.doAfterBody();
/* 4762 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4765 */       if (_jspx_eval_fmt_005fmessage_005f5 != 1) {
/* 4766 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4769 */     if (_jspx_th_fmt_005fmessage_005f5.doEndTag() == 5) {
/* 4770 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 4771 */       return true;
/*      */     }
/* 4773 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f5);
/* 4774 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f6(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4779 */     PageContext pageContext = _jspx_page_context;
/* 4780 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4782 */     MessageTag _jspx_th_fmt_005fmessage_005f6 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4783 */     _jspx_th_fmt_005fmessage_005f6.setPageContext(_jspx_page_context);
/* 4784 */     _jspx_th_fmt_005fmessage_005f6.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4785 */     int _jspx_eval_fmt_005fmessage_005f6 = _jspx_th_fmt_005fmessage_005f6.doStartTag();
/* 4786 */     if (_jspx_eval_fmt_005fmessage_005f6 != 0) {
/* 4787 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 4788 */         out = _jspx_page_context.pushBody();
/* 4789 */         _jspx_th_fmt_005fmessage_005f6.setBodyContent((BodyContent)out);
/* 4790 */         _jspx_th_fmt_005fmessage_005f6.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4793 */         out.write("table.heading.attribute");
/* 4794 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f6.doAfterBody();
/* 4795 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4798 */       if (_jspx_eval_fmt_005fmessage_005f6 != 1) {
/* 4799 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4802 */     if (_jspx_th_fmt_005fmessage_005f6.doEndTag() == 5) {
/* 4803 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4804 */       return true;
/*      */     }
/* 4806 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f6);
/* 4807 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f7(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4812 */     PageContext pageContext = _jspx_page_context;
/* 4813 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4815 */     MessageTag _jspx_th_fmt_005fmessage_005f7 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4816 */     _jspx_th_fmt_005fmessage_005f7.setPageContext(_jspx_page_context);
/* 4817 */     _jspx_th_fmt_005fmessage_005f7.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4818 */     int _jspx_eval_fmt_005fmessage_005f7 = _jspx_th_fmt_005fmessage_005f7.doStartTag();
/* 4819 */     if (_jspx_eval_fmt_005fmessage_005f7 != 0) {
/* 4820 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 4821 */         out = _jspx_page_context.pushBody();
/* 4822 */         _jspx_th_fmt_005fmessage_005f7.setBodyContent((BodyContent)out);
/* 4823 */         _jspx_th_fmt_005fmessage_005f7.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4826 */         out.write("table.heading.value");
/* 4827 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f7.doAfterBody();
/* 4828 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4831 */       if (_jspx_eval_fmt_005fmessage_005f7 != 1) {
/* 4832 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4835 */     if (_jspx_th_fmt_005fmessage_005f7.doEndTag() == 5) {
/* 4836 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4837 */       return true;
/*      */     }
/* 4839 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f7);
/* 4840 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f8(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4845 */     PageContext pageContext = _jspx_page_context;
/* 4846 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4848 */     MessageTag _jspx_th_fmt_005fmessage_005f8 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4849 */     _jspx_th_fmt_005fmessage_005f8.setPageContext(_jspx_page_context);
/* 4850 */     _jspx_th_fmt_005fmessage_005f8.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4851 */     int _jspx_eval_fmt_005fmessage_005f8 = _jspx_th_fmt_005fmessage_005f8.doStartTag();
/* 4852 */     if (_jspx_eval_fmt_005fmessage_005f8 != 0) {
/* 4853 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 4854 */         out = _jspx_page_context.pushBody();
/* 4855 */         _jspx_th_fmt_005fmessage_005f8.setBodyContent((BodyContent)out);
/* 4856 */         _jspx_th_fmt_005fmessage_005f8.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4859 */         out.write("table.heading.status");
/* 4860 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f8.doAfterBody();
/* 4861 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4864 */       if (_jspx_eval_fmt_005fmessage_005f8 != 1) {
/* 4865 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4868 */     if (_jspx_th_fmt_005fmessage_005f8.doEndTag() == 5) {
/* 4869 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4870 */       return true;
/*      */     }
/* 4872 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f8);
/* 4873 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f9(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4878 */     PageContext pageContext = _jspx_page_context;
/* 4879 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4881 */     MessageTag _jspx_th_fmt_005fmessage_005f9 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4882 */     _jspx_th_fmt_005fmessage_005f9.setPageContext(_jspx_page_context);
/* 4883 */     _jspx_th_fmt_005fmessage_005f9.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4884 */     int _jspx_eval_fmt_005fmessage_005f9 = _jspx_th_fmt_005fmessage_005f9.doStartTag();
/* 4885 */     if (_jspx_eval_fmt_005fmessage_005f9 != 0) {
/* 4886 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 4887 */         out = _jspx_page_context.pushBody();
/* 4888 */         _jspx_th_fmt_005fmessage_005f9.setBodyContent((BodyContent)out);
/* 4889 */         _jspx_th_fmt_005fmessage_005f9.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4892 */         out.write("table.heading.attribute");
/* 4893 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f9.doAfterBody();
/* 4894 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4897 */       if (_jspx_eval_fmt_005fmessage_005f9 != 1) {
/* 4898 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4901 */     if (_jspx_th_fmt_005fmessage_005f9.doEndTag() == 5) {
/* 4902 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4903 */       return true;
/*      */     }
/* 4905 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f9);
/* 4906 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f10(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4911 */     PageContext pageContext = _jspx_page_context;
/* 4912 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4914 */     MessageTag _jspx_th_fmt_005fmessage_005f10 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4915 */     _jspx_th_fmt_005fmessage_005f10.setPageContext(_jspx_page_context);
/* 4916 */     _jspx_th_fmt_005fmessage_005f10.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4917 */     int _jspx_eval_fmt_005fmessage_005f10 = _jspx_th_fmt_005fmessage_005f10.doStartTag();
/* 4918 */     if (_jspx_eval_fmt_005fmessage_005f10 != 0) {
/* 4919 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 4920 */         out = _jspx_page_context.pushBody();
/* 4921 */         _jspx_th_fmt_005fmessage_005f10.setBodyContent((BodyContent)out);
/* 4922 */         _jspx_th_fmt_005fmessage_005f10.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4925 */         out.write("table.heading.value");
/* 4926 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f10.doAfterBody();
/* 4927 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4930 */       if (_jspx_eval_fmt_005fmessage_005f10 != 1) {
/* 4931 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4934 */     if (_jspx_th_fmt_005fmessage_005f10.doEndTag() == 5) {
/* 4935 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4936 */       return true;
/*      */     }
/* 4938 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f10);
/* 4939 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f11(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4944 */     PageContext pageContext = _jspx_page_context;
/* 4945 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4947 */     MessageTag _jspx_th_fmt_005fmessage_005f11 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4948 */     _jspx_th_fmt_005fmessage_005f11.setPageContext(_jspx_page_context);
/* 4949 */     _jspx_th_fmt_005fmessage_005f11.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4950 */     int _jspx_eval_fmt_005fmessage_005f11 = _jspx_th_fmt_005fmessage_005f11.doStartTag();
/* 4951 */     if (_jspx_eval_fmt_005fmessage_005f11 != 0) {
/* 4952 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4953 */         out = _jspx_page_context.pushBody();
/* 4954 */         _jspx_th_fmt_005fmessage_005f11.setBodyContent((BodyContent)out);
/* 4955 */         _jspx_th_fmt_005fmessage_005f11.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4958 */         out.write("table.heading.status");
/* 4959 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f11.doAfterBody();
/* 4960 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4963 */       if (_jspx_eval_fmt_005fmessage_005f11 != 1) {
/* 4964 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 4967 */     if (_jspx_th_fmt_005fmessage_005f11.doEndTag() == 5) {
/* 4968 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4969 */       return true;
/*      */     }
/* 4971 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f11);
/* 4972 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f12(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 4977 */     PageContext pageContext = _jspx_page_context;
/* 4978 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 4980 */     MessageTag _jspx_th_fmt_005fmessage_005f12 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 4981 */     _jspx_th_fmt_005fmessage_005f12.setPageContext(_jspx_page_context);
/* 4982 */     _jspx_th_fmt_005fmessage_005f12.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 4983 */     int _jspx_eval_fmt_005fmessage_005f12 = _jspx_th_fmt_005fmessage_005f12.doStartTag();
/* 4984 */     if (_jspx_eval_fmt_005fmessage_005f12 != 0) {
/* 4985 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4986 */         out = _jspx_page_context.pushBody();
/* 4987 */         _jspx_th_fmt_005fmessage_005f12.setBodyContent((BodyContent)out);
/* 4988 */         _jspx_th_fmt_005fmessage_005f12.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 4991 */         out.write("table.heading.attribute");
/* 4992 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f12.doAfterBody();
/* 4993 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 4996 */       if (_jspx_eval_fmt_005fmessage_005f12 != 1) {
/* 4997 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5000 */     if (_jspx_th_fmt_005fmessage_005f12.doEndTag() == 5) {
/* 5001 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 5002 */       return true;
/*      */     }
/* 5004 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f12);
/* 5005 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f13(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5010 */     PageContext pageContext = _jspx_page_context;
/* 5011 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5013 */     MessageTag _jspx_th_fmt_005fmessage_005f13 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5014 */     _jspx_th_fmt_005fmessage_005f13.setPageContext(_jspx_page_context);
/* 5015 */     _jspx_th_fmt_005fmessage_005f13.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5016 */     int _jspx_eval_fmt_005fmessage_005f13 = _jspx_th_fmt_005fmessage_005f13.doStartTag();
/* 5017 */     if (_jspx_eval_fmt_005fmessage_005f13 != 0) {
/* 5018 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 5019 */         out = _jspx_page_context.pushBody();
/* 5020 */         _jspx_th_fmt_005fmessage_005f13.setBodyContent((BodyContent)out);
/* 5021 */         _jspx_th_fmt_005fmessage_005f13.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5024 */         out.write("table.heading.value");
/* 5025 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f13.doAfterBody();
/* 5026 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5029 */       if (_jspx_eval_fmt_005fmessage_005f13 != 1) {
/* 5030 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5033 */     if (_jspx_th_fmt_005fmessage_005f13.doEndTag() == 5) {
/* 5034 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 5035 */       return true;
/*      */     }
/* 5037 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f13);
/* 5038 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f14(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5043 */     PageContext pageContext = _jspx_page_context;
/* 5044 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5046 */     MessageTag _jspx_th_fmt_005fmessage_005f14 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5047 */     _jspx_th_fmt_005fmessage_005f14.setPageContext(_jspx_page_context);
/* 5048 */     _jspx_th_fmt_005fmessage_005f14.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5049 */     int _jspx_eval_fmt_005fmessage_005f14 = _jspx_th_fmt_005fmessage_005f14.doStartTag();
/* 5050 */     if (_jspx_eval_fmt_005fmessage_005f14 != 0) {
/* 5051 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 5052 */         out = _jspx_page_context.pushBody();
/* 5053 */         _jspx_th_fmt_005fmessage_005f14.setBodyContent((BodyContent)out);
/* 5054 */         _jspx_th_fmt_005fmessage_005f14.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5057 */         out.write("table.heading.status");
/* 5058 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f14.doAfterBody();
/* 5059 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5062 */       if (_jspx_eval_fmt_005fmessage_005f14 != 1) {
/* 5063 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5066 */     if (_jspx_th_fmt_005fmessage_005f14.doEndTag() == 5) {
/* 5067 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 5068 */       return true;
/*      */     }
/* 5070 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f14);
/* 5071 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f15(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5076 */     PageContext pageContext = _jspx_page_context;
/* 5077 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5079 */     MessageTag _jspx_th_fmt_005fmessage_005f15 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5080 */     _jspx_th_fmt_005fmessage_005f15.setPageContext(_jspx_page_context);
/* 5081 */     _jspx_th_fmt_005fmessage_005f15.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5082 */     int _jspx_eval_fmt_005fmessage_005f15 = _jspx_th_fmt_005fmessage_005f15.doStartTag();
/* 5083 */     if (_jspx_eval_fmt_005fmessage_005f15 != 0) {
/* 5084 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 5085 */         out = _jspx_page_context.pushBody();
/* 5086 */         _jspx_th_fmt_005fmessage_005f15.setBodyContent((BodyContent)out);
/* 5087 */         _jspx_th_fmt_005fmessage_005f15.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5090 */         out.write("table.heading.attribute");
/* 5091 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f15.doAfterBody();
/* 5092 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5095 */       if (_jspx_eval_fmt_005fmessage_005f15 != 1) {
/* 5096 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5099 */     if (_jspx_th_fmt_005fmessage_005f15.doEndTag() == 5) {
/* 5100 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 5101 */       return true;
/*      */     }
/* 5103 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f15);
/* 5104 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f16(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5109 */     PageContext pageContext = _jspx_page_context;
/* 5110 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5112 */     MessageTag _jspx_th_fmt_005fmessage_005f16 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5113 */     _jspx_th_fmt_005fmessage_005f16.setPageContext(_jspx_page_context);
/* 5114 */     _jspx_th_fmt_005fmessage_005f16.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5115 */     int _jspx_eval_fmt_005fmessage_005f16 = _jspx_th_fmt_005fmessage_005f16.doStartTag();
/* 5116 */     if (_jspx_eval_fmt_005fmessage_005f16 != 0) {
/* 5117 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 5118 */         out = _jspx_page_context.pushBody();
/* 5119 */         _jspx_th_fmt_005fmessage_005f16.setBodyContent((BodyContent)out);
/* 5120 */         _jspx_th_fmt_005fmessage_005f16.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5123 */         out.write("table.heading.value");
/* 5124 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f16.doAfterBody();
/* 5125 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5128 */       if (_jspx_eval_fmt_005fmessage_005f16 != 1) {
/* 5129 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5132 */     if (_jspx_th_fmt_005fmessage_005f16.doEndTag() == 5) {
/* 5133 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 5134 */       return true;
/*      */     }
/* 5136 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f16);
/* 5137 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_fmt_005fmessage_005f17(JspTag _jspx_th_tiles_005fput_005f3, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5142 */     PageContext pageContext = _jspx_page_context;
/* 5143 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5145 */     MessageTag _jspx_th_fmt_005fmessage_005f17 = (MessageTag)this._005fjspx_005ftagPool_005ffmt_005fmessage.get(MessageTag.class);
/* 5146 */     _jspx_th_fmt_005fmessage_005f17.setPageContext(_jspx_page_context);
/* 5147 */     _jspx_th_fmt_005fmessage_005f17.setParent((Tag)_jspx_th_tiles_005fput_005f3);
/* 5148 */     int _jspx_eval_fmt_005fmessage_005f17 = _jspx_th_fmt_005fmessage_005f17.doStartTag();
/* 5149 */     if (_jspx_eval_fmt_005fmessage_005f17 != 0) {
/* 5150 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 5151 */         out = _jspx_page_context.pushBody();
/* 5152 */         _jspx_th_fmt_005fmessage_005f17.setBodyContent((BodyContent)out);
/* 5153 */         _jspx_th_fmt_005fmessage_005f17.doInitBody();
/*      */       }
/*      */       for (;;) {
/* 5156 */         out.write("table.heading.status");
/* 5157 */         int evalDoAfterBody = _jspx_th_fmt_005fmessage_005f17.doAfterBody();
/* 5158 */         if (evalDoAfterBody != 2)
/*      */           break;
/*      */       }
/* 5161 */       if (_jspx_eval_fmt_005fmessage_005f17 != 1) {
/* 5162 */         out = _jspx_page_context.popBody();
/*      */       }
/*      */     }
/* 5165 */     if (_jspx_th_fmt_005fmessage_005f17.doEndTag() == 5) {
/* 5166 */       this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 5167 */       return true;
/*      */     }
/* 5169 */     this._005fjspx_005ftagPool_005ffmt_005fmessage.reuse(_jspx_th_fmt_005fmessage_005f17);
/* 5170 */     return false;
/*      */   }
/*      */   
/*      */   private boolean _jspx_meth_tiles_005fput_005f4(JspTag _jspx_th_tiles_005finsert_005f0, PageContext _jspx_page_context) throws Throwable
/*      */   {
/* 5175 */     PageContext pageContext = _jspx_page_context;
/* 5176 */     JspWriter out = _jspx_page_context.getOut();
/*      */     
/* 5178 */     PutTag _jspx_th_tiles_005fput_005f4 = (PutTag)this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.get(PutTag.class);
/* 5179 */     _jspx_th_tiles_005fput_005f4.setPageContext(_jspx_page_context);
/* 5180 */     _jspx_th_tiles_005fput_005f4.setParent((Tag)_jspx_th_tiles_005finsert_005f0);
/*      */     
/* 5182 */     _jspx_th_tiles_005fput_005f4.setName("footer");
/*      */     
/* 5184 */     _jspx_th_tiles_005fput_005f4.setValue("/jsp/footer.jsp");
/* 5185 */     int _jspx_eval_tiles_005fput_005f4 = _jspx_th_tiles_005fput_005f4.doStartTag();
/* 5186 */     if (_jspx_th_tiles_005fput_005f4.doEndTag() == 5) {
/* 5187 */       this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5188 */       return true;
/*      */     }
/* 5190 */     this._005fjspx_005ftagPool_005ftiles_005fput_0026_005fvalue_005fname_005fnobody.reuse(_jspx_th_tiles_005fput_005f4);
/* 5191 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Program Files (x86)\ManageEngine\AppManager12\working\WEB-INF\lib\AdventNetAppManagerWebClient.jar!\org\apache\jsp\jsp\AMServerStatus_jsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */